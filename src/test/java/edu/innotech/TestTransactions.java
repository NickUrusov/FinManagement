package edu.innotech;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.innotech.dto.*;
import edu.innotech.model.Transaction;
import edu.innotech.model.TypeTransaction;
import edu.innotech.model.User;
import edu.innotech.repository.TransactionRepository;
import edu.innotech.services.TransactionService;
import edu.innotech.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class TestTransactions {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionRepository transactionRepository;
    @MockBean
    private TransactionService transactionService;
    @MockBean
    private UserService userService;

    private TransactionInstance transactionInstance = null;
    private Transaction transaction= null;
    private User user  = null;

    @BeforeEach
    void initReq() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        user = new User();
        user.setId(1L);
        user.setName("Vasya");
        user.setLastName("Vasilkov");
        user.setEMail("vasya@vasilkov.com");
        user.setRegistarationDate(formatter.parse("2024-10-12", new ParsePosition(0)));

        transaction = new Transaction();
        transaction.setId(2L);
        transaction.setSumTransaction(255.03);
        transaction.setDateTransaction(formatter.parse("2024-10-12", new ParsePosition(0)));
        transaction.setTypeTransaction(TypeTransaction.CREDITING);
        transaction.setUserId(user.getId());
        transactionInstance = new TransactionInstance();
        transactionInstance.setInstanceId(transaction.getId());
        transactionInstance.setSumTransaction(transaction.getSumTransaction());
        transactionInstance.setDateTransaction(transaction.getDateTransaction());
        transactionInstance.setTypeTransaction(transaction.getTypeTransaction());
        transactionInstance.setUserId(transaction.getUserId());
    }

    @Test
    public void testGetRequestFound() throws Exception {

        Mockito.when(transactionService.get(Mockito.any())).thenReturn(transaction);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(transactionInstance);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/transactions/getId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        TransactionResponseData transactionResponseData = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TransactionResponseData.class);
        Assertions.assertEquals(transaction.getId().toString(), transactionResponseData.getData().getTransactionId());

    }

    @Test
    public void testGetListRequest() throws Exception {
        List<Transaction> transactionList = new ArrayList();
        transactionList.add(transaction);

        Mockito.when(transactionService.get()).thenReturn(transactionList);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(transactionInstance);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/transactions/getAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        List<TransactionResponseData> transactionResponseDataList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        Assertions.assertEquals(1, transactionResponseDataList.size());
    }

    @Test
    public void testPostRequest() throws Exception {
        Mockito.when(transactionService.post(Mockito.any()
                ,Mockito.any(Date.class)
                ,Mockito.any()
                ,Mockito.any(User.class))
        ).thenReturn(transaction);
        Mockito.when(transactionService.get(Mockito.any())).thenReturn(null);
        Mockito.when(userService.get(Mockito.any())).thenReturn(user);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(transactionInstance);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transactions/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        TransactionResponseData transactionResponseData = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TransactionResponseData.class);
        Assertions.assertEquals(transaction.getId().toString(), transactionResponseData.getData().getTransactionId());
    }

    @Test
    public void testPutRequest() throws Exception {
        Mockito.when(transactionService.put(Mockito.any()
                ,Mockito.any()
                ,Mockito.any(Date.class)
                ,Mockito.any()
                , Mockito.any(User.class))
        ).thenReturn(transaction);
        Mockito.when(transactionService.get(Mockito.any())).thenReturn(transaction);
        Mockito.when(userService.get(Mockito.any())).thenReturn(user);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(transactionInstance);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/transactions/put")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        TransactionResponseData transactionResponseData = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TransactionResponseData.class);
        Assertions.assertEquals(transaction.getId().toString(), transactionResponseData.getData().getTransactionId());
    }

    @Test
    public void testDeleteRequest() throws Exception {

        Mockito.when(transactionService.get(Mockito.any())).thenReturn(transaction);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(transactionInstance);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/transactions/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        TransactionResponseData transactionResponseData = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TransactionResponseData.class);
        Assertions.assertEquals(transaction.getId().toString(), transactionResponseData.getData().getTransactionId());
    }

}
