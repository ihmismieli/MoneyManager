package fi.ahlgren.moneymanager.domain;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    

    //checks if transaction already exists. this reduces duplicates in the database
    boolean existsByTransactionDateAndAmountAndRecipientAndCategory(
        LocalDate transactionDate,
        double amount,
        String recipient,
        Category category
    );

}