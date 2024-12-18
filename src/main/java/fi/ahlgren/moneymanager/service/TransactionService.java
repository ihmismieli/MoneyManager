package fi.ahlgren.moneymanager.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

//this class is in charge of saving data to the database.
//makes it easier to maintain the code, because the database actions are seraparated from the controller

import org.springframework.stereotype.Service;

import fi.ahlgren.moneymanager.domain.AppUser;
import fi.ahlgren.moneymanager.domain.AppUserRepository;
import fi.ahlgren.moneymanager.domain.Transaction;
import fi.ahlgren.moneymanager.domain.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");

    DecimalFormat des = new DecimalFormat("0.00");

    // checks duplicates and saves transactions to the database
    public void saveAll(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            boolean exists = transactionRepository.existsByTransactionDateAndAmountAndRecipientAndCategoryAndUser(
                    transaction.getTransactionDate(),
                    transaction.getAmount(),
                    transaction.getRecipient(),
                    transaction.getCategory(),
                    transaction.getUser());
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

    public List<Transaction> getTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findAllByTransactionDateBetween(startDate, endDate);
    }


    // Retrieves transactions by user and date range
    public List<Transaction> getUserTransactionsInRange(LocalDate starDate, LocalDate endDate) {
        // logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // search the user from the db by username
        AppUser currentUser = appUserRepository.findByUsername(username);
        if (currentUser == null) {
            throw new IllegalStateException("User not found");
        }
        return transactionRepository.findByUserAndDateRange(currentUser, starDate, endDate);
    }

    // Calculates the total sums of all categories
    public Map<String, Double> calculateTotalByCategory(AppUser currentUser, LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionRepository.findAllByUserAndTransactionDateBetween(currentUser, startDate, endDate);
        Map<String, Double> totalByCategory = new HashMap<>();

        for (Transaction transaction : transactions) {
            String categoryName = transaction.getCategory().getName();
            double amount = transaction.getAmount();
            // getOrDefault ensures that value is never null
            double currentTotal = totalByCategory.getOrDefault(categoryName, 0.0);
            totalByCategory.put(categoryName, currentTotal + amount);
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
    public double calculateTotalSpendings(AppUser currentUser, LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionRepository.findAllByUserAndTransactionDateBetween(currentUser,startDate, endDate);

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
    public double calculateTotalIncome(AppUser currentUser, LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionRepository.findAllByUserAndTransactionDateBetween(currentUser, startDate, endDate);
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

    // Returns timeline for the CSV and transactions by paymentDate
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

    /////// Searh endpoint//////

    // Calculates the total sums of all categories
    public Map<String, Double> calculateTotalByCategorySearch(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = getUserTransactionsInRange(startDate, endDate);
        Map<String, Double> totalByCategory = new HashMap<>();

        for (Transaction transaction : transactions) {
            String categoryName = transaction.getCategory().getName();
            double amount = transaction.getAmount();
            // getOrDefault ensures that value is never null
            double currentTotal = totalByCategory.getOrDefault(categoryName, 0.0);
            totalByCategory.put(categoryName, currentTotal + amount);
        }

        Map<String, Double> formattedTotals = new HashMap<>();
        for (Map.Entry<String, Double> entry : totalByCategory.entrySet()) {
            BigDecimal roundedValue = BigDecimal.valueOf(entry.getValue()).setScale(2, RoundingMode.HALF_UP);
            formattedTotals.put(entry.getKey(), roundedValue.doubleValue());
        }
        return formattedTotals;
    }

    // DATE range calculation for Search spendings
    public Map<String, BigDecimal> calculateTotalByDate(List<Transaction> transactions) {
        Map<String, BigDecimal> spendingsByCategory = new HashMap<>();

        for (Transaction transaction : transactions) {
            String category = transaction.getCategory().getName();
            BigDecimal amount = BigDecimal.valueOf(transaction.getAmount());

            BigDecimal updatedAmount = spendingsByCategory.getOrDefault(category, BigDecimal.ZERO).add(amount)
                    .setScale(2, RoundingMode.HALF_UP);
            spendingsByCategory.put(category, updatedAmount);
        }
        return spendingsByCategory;

    }

    // calculates total spendings without income and personal transfer in search
    public double calculateTotalSpendingsByDateRange(AppUser currentUser, LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionRepository.findAllByUserAndTransactionDateBetween(currentUser,
                startDate, endDate);

        BigDecimal total = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            BigDecimal amount = BigDecimal.valueOf(transaction.getAmount());
            if (transaction.getAmount() < 0 &&
                    !(transaction.getCategory().getName().equalsIgnoreCase("Income") ||
                            transaction.getCategory().getName().equalsIgnoreCase("Personal transfer"))) {
                total = total.add(amount).setScale(2, RoundingMode.HALF_UP);
            }
        }
        return total.doubleValue();

    }

    // Date range calculation for total income in search
    public double calculateTotalIncomeByDateRange(AppUser currentUser, LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionRepository.findAllByUserAndTransactionDateBetween(currentUser,
                startDate, endDate);

        System.out.println("Transactions found: " + transactions.size());

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

}