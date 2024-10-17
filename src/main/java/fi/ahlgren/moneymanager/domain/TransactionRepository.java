package fi.ahlgren.moneymanager.domain;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    
}