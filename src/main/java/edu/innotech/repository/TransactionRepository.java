package edu.innotech.repository;

import edu.innotech.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findFirstById(Long var1);

    @Transactional(readOnly = true)
    @Query(value = """
            select transaction
            from Transaction transaction
            join User user on transaction.userId = user.id
            where user.id = :userId
            """
    )
    List<Transaction> findAllByUserId(@Param("userId") Long userId);
}
