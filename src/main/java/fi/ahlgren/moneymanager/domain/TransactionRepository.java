package fi.ahlgren.moneymanager.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    
    //find transactions by user
    List<Transaction> findAllByUser(AppUser user);

    //lists transactions between start and end date
    List<Transaction> findAllByTransactionDateBetween(LocalDate startDate, LocalDate endDate);
    
    //checks if transaction already exists. this reduces duplicates in the database
    boolean existsByTransactionDateAndAmountAndRecipientAndCategory(
        LocalDate transactionDate,
        double amount,
        String recipient,
        Category category
    );

}