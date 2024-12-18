package fi.ahlgren.moneymanager.web;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fi.ahlgren.moneymanager.domain.AppUser;
import fi.ahlgren.moneymanager.domain.AppUserRepository;
import fi.ahlgren.moneymanager.domain.Transaction;
import fi.ahlgren.moneymanager.domain.TransactionRepository;
import fi.ahlgren.moneymanager.service.TransactionService;

@Controller
public class MoneyManagerController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/spendings")
    public String spendings() {
        return "spendings";
    }

    @GetMapping("/search")
    public String searchSpendings(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            Model model) {

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findByUsername(currentUsername);

        List<Transaction> transactions;
        Map<String, BigDecimal> spendingByCategory;

        if (startDate != null && endDate != null) {
            transactions = transactionService.getUserTransactionsInRange(startDate, endDate);
            if (transactions.isEmpty()) {
                model.addAttribute("error", "The selected date is invalid!");
                return "search";
            }

            // Spendings total by a category between minDate and maxDate
            spendingByCategory = transactionService.calculateTotalByDate(transactions);
            model.addAttribute("spendingByCategory", spendingByCategory);

            // Spendings total without income and personal transfer between minDate and
            // maxDate
            double totalSpendings = transactionService.calculateTotalSpendingsByDateRange(currentUser, startDate,
                    endDate);
            model.addAttribute("totalSpendings", totalSpendings);

            // Total income between minDate and maxDate
            double totalIncome = transactionService.calculateTotalIncomeByDateRange(currentUser, startDate, endDate);
            model.addAttribute("totalIncome", totalIncome);

            // Date timeline for seach
            String timeline = transactionService.getTimelineforCSV(transactions);
            model.addAttribute("timeline", timeline);

        } else if (startDate != null || endDate != null) {
            model.addAttribute("error", "The selected date is invalid!");
            return "search";
        }
        return "search";
    }

}
