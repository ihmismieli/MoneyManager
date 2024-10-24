package fi.ahlgren.moneymanager.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

//this class is in charge of saving data to the database.
//makes it easier to maintain the code, because the database actions are seraparated from the controller

import org.springframework.stereotype.Service;

import fi.ahlgren.moneymanager.domain.AppUser;
import fi.ahlgren.moneymanager.domain.Transaction;
import fi.ahlgren.moneymanager.domain.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");

    // checks duplicates and saves transactions to the database
    public void saveAll(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            boolean exists = transactionRepository.existsByTransactionDateAndAmountAndRecipientAndCategory(
                    transaction.getTransactionDate(),
                    transaction.getAmount(),
                    transaction.getRecipient(),
                    transaction.getCategory());
            if (!exists) {
                transactionRepository.save(transaction);
            }

        }
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public void deleteAllTransactions() {
        transactionRepository.deleteAll();
    }

    // Calculates the total sums of all categories
    public Map<String, Double> calculateTotalByCategory(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionRepository.findAllByTransactionDateBetween(startDate, endDate);
        Map<String, Double> totalByCategory = new HashMap<>();

        for (Transaction transaction : transactions) {
            String categoryName = transaction.getCategory().getName();
            double amount = transaction.getAmount();
            // getOrDefault ensures that value is never null
            totalByCategory.put(categoryName, totalByCategory.getOrDefault(categoryName, 0.0) + amount);
        }

        // Formats total sums
        // Uses BigDecimal instead of Decimal format because the value has to be a
        // double not a string
        Map<String, Double> formattedTotals = new HashMap<>();
        for (Map.Entry<String, Double> entry : totalByCategory.entrySet()) {
            BigDecimal roundedValue = BigDecimal.valueOf(entry.getValue()).setScale(2, RoundingMode.HALF_UP);
            formattedTotals.put(entry.getKey(), roundedValue.doubleValue());
        }
        return formattedTotals;
    }

    // calculates total spendings without income and personal transfer
    public double calculateTotalSpendings(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionRepository.findAllByTransactionDateBetween(startDate, endDate);

        BigDecimal total = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            BigDecimal amount = BigDecimal.valueOf(transaction.getAmount());
            if (transaction.getAmount() < 0 &&
                    !(transaction.getCategory().getName().equalsIgnoreCase("Income") ||
                            transaction.getCategory().getName().equalsIgnoreCase("Personal transfer"))) {
                total = total.add(amount);
            }
        }
        return total.doubleValue();

    }

    // calculates totalIncome
    public double calculateTotalIncome(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionRepository.findAllByTransactionDateBetween(startDate, endDate);
        BigDecimal totalIncome = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            BigDecimal incomeAmount = BigDecimal.valueOf(transaction.getAmount());
            if (transaction.getAmount() > 0 &&
                    transaction.getCategory().getName().equalsIgnoreCase("Income")) {
                totalIncome = totalIncome.add(incomeAmount);
            }
        }
        return totalIncome.doubleValue();
    }

    //Returns timeline for the CSV and transactions by paymentdate
    public String getTimelineforCSV(List<Transaction> transactions) {
        if (!transactions.isEmpty()) {
            List<LocalDate> paymentDates = transactions.stream()
                                                        .map(Transaction::getPaymentDate)
                                                        .collect(Collectors.toList());
            LocalDate minDate = Collections.min(paymentDates);
            LocalDate maxDate = Collections.max(paymentDates);

            String formattedMinDate = minDate.format(formatter);
            String formattedMaxDate = maxDate.format(formatter);
            String timeline = "Spendings " + formattedMinDate + " - " + formattedMaxDate;
            return timeline; 
        }
        
        return "No spendings timeline";
    }


}