package fi.ahlgren.moneymanager.web;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fi.ahlgren.moneymanager.domain.Transaction;
import fi.ahlgren.moneymanager.service.TransactionService;

@Controller
public class MoneyManagerController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/moneymanager")
    public String home() {
        return "moneymanager";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/spendings")
    public String spendings() {
        return "spendings";
    }

    @GetMapping("/search")
    public String searchSpendings(@RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            Model model) {

        List<Transaction> transactions;
        Map<String, BigDecimal> spendingByCategory;

        if (startDate != null && endDate != null) {
            transactions = transactionService.getTransactionsByDate(startDate, endDate);
            spendingByCategory = transactionService.calculateTotalByDate(transactions);
            model.addAttribute("spendingByCategory", spendingByCategory);
        } 
        return "search";
    }

}
