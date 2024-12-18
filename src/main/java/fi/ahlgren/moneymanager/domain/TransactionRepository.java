package fi.ahlgren.moneymanager.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    
    //find transactions by user
    List<Transaction> findAllByUser(AppUser user);

    //lists transactions between start and end date
    List<Transaction> findAllByTransactionDateBetween(LocalDate startDate, LocalDate endDate);

    List<Transaction> findAllByUserAndTransactionDateBetween(AppUser user, LocalDate startDate, LocalDate endDate);
    
    //checks if transaction already exists. this reduces duplicates in the database
    boolean existsByTransactionDateAndAmountAndRecipientAndCategoryAndUser(
        LocalDate transactionDate,
        double amount,
        String recipient,
        Category category,
        AppUser user
    );

    //retrieves only transactions by a logged in user within start date and end date
    @Query("Select t from Transaction t WHERE t.user = :user AND t.transactionDate BETWEEN :startDate AND :endDate")
    List<Transaction> findByUserAndDateRange(
        @Param("user") AppUser user,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

}