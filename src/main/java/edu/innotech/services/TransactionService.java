package edu.innotech.services;

import edu.innotech.model.Transaction;
import edu.innotech.model.TypeTransaction;
import edu.innotech.model.User;
import edu.innotech.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction get(Long transactionId) {
        return transactionRepository.findFirstById(transactionId);
    }

    public List<Transaction> getByUser(Long userId){
        return (List<Transaction>)transactionRepository.findAllByUserId(userId);
    }

    public List<Transaction> get(){
        return (List<Transaction>)transactionRepository.findAll();
    }

    public void delete(Long transactionId){
        transactionRepository.deleteById(transactionId);
    }

    private void  saveTransaction(Transaction transaction
            , Double sumTransaction
            , Date dateTransaction
            , TypeTransaction typeTransaction
            , User user) {
        transaction.setSumTransaction(sumTransaction);
        transaction.setDateTransaction(dateTransaction);
        transaction.setTypeTransaction(typeTransaction);
        transaction.setUserId(user.getId());
        transactionRepository.save(transaction);
    }

    public Transaction put(Long transactionId
                    , Double sumTransaction
                    , Date dateTransaction
                    , TypeTransaction typeTransaction
                    , User user){
        var transaction = transactionRepository.getById(transactionId);
        saveTransaction(transaction, sumTransaction, dateTransaction, typeTransaction, user);
        return transaction;
    }
    public Transaction post(Double sumTransaction
            , Date dateTransaction
            , TypeTransaction typeTransaction
            , User user){
        var transaction = new Transaction();
        saveTransaction(transaction, sumTransaction, dateTransaction, typeTransaction, user);
        return transaction;
    }
}
