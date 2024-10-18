package fi.ahlgren.moneymanager.web;

import java.nio.charset.StandardCharsets;

import org.springframework.ui.Model; 
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fi.ahlgren.moneymanager.domain.Category;
import fi.ahlgren.moneymanager.domain.Transaction;
import fi.ahlgren.moneymanager.service.CategoryService;
import fi.ahlgren.moneymanager.service.TransactionService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CSVcontroller {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CategoryService categoryService;


    // MultipartFile is Spring's class that is used to handle files
    @PostMapping("/moneymanager")
    public String uploadCSV(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Choose a file that is not empty!");
            return "redirect:/moneymanager";
        }

        // transactionService.deleteAllTransactions();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");

        // file.getInputStream() creates an inputStream object that is given to the scanner so that scanner can read the file & UTF makes sure that special characters are scanned right
        try (Scanner scanner = new Scanner(file.getInputStream(), StandardCharsets.UTF_8)) {
            List<Transaction> transactions = new ArrayList<>();
            scanner.nextLine();

            //
            LocalDate minDate = LocalDate.MAX;
            LocalDate maxDate = LocalDate.MIN;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(";");
                Transaction transaction = new Transaction();
                transaction.setTransactionDate(LocalDate.parse(data[0], formatter));
                transaction.setPaymentDate(LocalDate.parse(data[1], formatter));
                String amount = data[2].replace(",", ".");
                transaction.setAmount(Double.parseDouble(amount));
                transaction.setTransactionType(data[3]);
                transaction.setPayer(replaceDiacritics(data[4]));
                transaction.setRecipient(data[5]);
                transaction.setRecipientIBAN(data[6]);
                transaction.setReferenceNumber(data[7]);
                transaction.setMessage(data[8]);
                transaction.setArchiveId(data[9]);

                //checks if transaction is before current minDate and updates it if it is
                if (transaction.getTransactionDate().isBefore(minDate)) {
                    minDate = transaction.getTransactionDate();
                }

                //checks if transaction is after current maxDate
                if (transaction.getTransactionDate().isAfter(maxDate)) {
                    maxDate = transaction.getTransactionDate();
                }

                // defining the category by recipient, payer or transactionType
                String recipient = data[5];
                Category category = categoryService.getCategoryByRecipientObject(recipient, transaction.getPayer(), transaction.getTransactionType());
                transaction.setCategory(category);
                transactions.add(transaction);
            }

            transactionService.saveAll(transactions);
            redirectAttributes.addFlashAttribute("message", "File data has been saved successfully!");

            //Spendings total by a category between minDate and maxDate
            Map<String, Double> totalByCategory = transactionService.calculateTotalByCategory(minDate, maxDate);
            redirectAttributes.addFlashAttribute("totals", totalByCategory);

            //Spendings total without income and personal transfer between minDate and maxDate
            double totalSpendings = transactionService.calculateTotalSpendings(minDate, maxDate);
            redirectAttributes.addFlashAttribute("totalSpendings", totalSpendings);

            //Total income between minDate and maxDate
            double totalIncome = transactionService.calculateTotalIncome(minDate, maxDate);
            redirectAttributes.addFlashAttribute("totalIncome", totalIncome);

            //Date timeline for spendings
            String timeline = transactionService.getTimelineforCSV(transactions);
            redirectAttributes.addFlashAttribute("timeline", timeline);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Failed to read the file: " + e.getMessage());
        }

        return "redirect:/spendings";
    }

    private String replaceDiacritics(String input){
        if (input == null) {
            return null;
        }
        return input.replace("ä", "a")
                    .replace("Ä", "A")
                    .replace("ö", "o")
                    .replace("Ö", "O")
                    .replace("å", "a")
                    .replace("Å", "A");

    }
}
