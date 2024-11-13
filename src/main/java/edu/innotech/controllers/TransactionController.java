package edu.innotech.controllers;

import edu.innotech.dto.*;
import edu.innotech.model.Transaction;
import edu.innotech.model.User;
import edu.innotech.services.TransactionService;
import edu.innotech.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TransactionController {
    @Autowired
    Environment environment;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @GetMapping("/transactions/getId")
    public ResponseEntity<TransactionResponseData> get(@Valid @RequestBody TransactionInstance transactionInstance) {
        Transaction transaction = transactionService.get(transactionInstance.getInstanceId());

        if (transaction == null){
            return new ResponseEntity("Не найдена транзакция по параметрам запроса", HttpStatus.NOT_FOUND);
        }

        TransactionResponseData transactionResponseData = new TransactionResponseData();
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setTransactionId(transaction.getId().toString());
        transactionResponseData.setData(transactionResponse);

        return ResponseEntity.ok(transactionResponseData);
    }

    @GetMapping("/transactions/getAll")
    public ResponseEntity<List<TransactionResponseData>> getAll(@Valid @RequestBody TransactionInstance transactionInstance) {
        List<Transaction> transactionList = transactionService.get();

        if (transactionList.size() == 0){
            return new ResponseEntity("Не найдено ни одого пользователя", HttpStatus.NOT_FOUND);
        }
        List<TransactionResponseData> transactionResponseDataList = new ArrayList<>();
        TransactionResponseData transactionResponseData = null;
        TransactionResponse transactionResponse = null;
        for(Transaction transaction: transactionList ){
            transactionResponse = new TransactionResponse();
            transactionResponseData = new TransactionResponseData();
            transactionResponse.setTransactionId(transaction.getId().toString());
            transactionResponseData.setData(transactionResponse);
            transactionResponseDataList.add(transactionResponseData);
        }
        return ResponseEntity.ok(transactionResponseDataList);
    }

    @PostMapping("/transactions/post")
    public ResponseEntity<TransactionResponseData> post(@Valid @RequestBody TransactionInstance transactionInstance) {
        Transaction transaction  = transactionService.get(transactionInstance.getInstanceId());

        if (transaction != null){
            return new ResponseEntity("Транзакция с таким идентификатотром уже существует", HttpStatus.IM_USED);
        }
        User user =  userService.get(transactionInstance.getUserId());
        if (user == null){
            return new ResponseEntity("Пользователь с таким идентификатотром не существует", HttpStatus.NOT_FOUND);
        }

        transaction = transactionService.post(transactionInstance.getSumTransaction()
                , transactionInstance.getDateTransaction()
                , transactionInstance.getTypeTransaction()
                , user
        );

        TransactionResponseData transactionResponseData = new TransactionResponseData();
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setTransactionId(transaction.getId().toString());
        transactionResponseData.setData(transactionResponse);

        return ResponseEntity.ok(transactionResponseData);
    }

    @PutMapping("/transactions/put")
    public ResponseEntity<TransactionResponseData> put(@Valid @RequestBody TransactionInstance transactionInstance) {
        Transaction transaction = transactionService.get(transactionInstance.getInstanceId());

        if (transaction == null){
            return new ResponseEntity("Не найдена транзакция с таким идентификатотром", HttpStatus.NOT_FOUND);
        }

        User user =  userService.get(transactionInstance.getUserId());
        if (user == null){
            return new ResponseEntity("Пользователь с таким идентификатотром не существует", HttpStatus.NOT_FOUND);
        }

        transaction = transactionService.put(transactionInstance.getInstanceId()
                , transactionInstance.getSumTransaction()
                , transactionInstance.getDateTransaction()
                , transactionInstance.getTypeTransaction()
                , user
        );

        TransactionResponseData transactionResponseData = new TransactionResponseData();
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setTransactionId(transaction.getId().toString());
        transactionResponseData.setData(transactionResponse);

        return ResponseEntity.ok(transactionResponseData);
    }

    @DeleteMapping("/transactions/delete")
    public ResponseEntity<TransactionResponseData> delete(@Valid @RequestBody TransactionInstance transactionInstance) {
        Transaction transaction = transactionService.get(transactionInstance.getInstanceId());

        if (transaction == null){
            return new ResponseEntity("Не найдена транзакция с таким идентификатотром", HttpStatus.NOT_FOUND);
        }

        transactionService.delete(transactionInstance.getInstanceId());

        TransactionResponseData transactionResponseData = new TransactionResponseData();
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setTransactionId(transactionInstance.getInstanceId().toString());
        transactionResponseData.setData(transactionResponse);

        return ResponseEntity.ok(transactionResponseData);
    }

}
