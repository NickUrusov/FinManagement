package edu.innotech;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.innotech.dto.UserInstance;
import edu.innotech.dto.UserResponseData;
import edu.innotech.model.User;
import edu.innotech.repository.UserRepository;
import edu.innotech.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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
public class TestUser{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserService userService;

    private UserInstance userInstance = null;
    private User user = null;

    @BeforeEach
    void initReq() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        user = new User();
        user.setId(1L);
        user.setName("Vasya");
        user.setLastName("Vasilkov");
        user.setEMail("vasya@vasilkov.com");
        user.setRegistarationDate(formatter.parse("2024-10-12", new ParsePosition(0)));
        userInstance = new UserInstance();
        userInstance.setInstanceId(user.getId());
        userInstance.setName(user.getName());
        userInstance.setLastName(user.getLastName());
        userInstance.setEMail(user.getEMail());
        userInstance.setRegistarationDate(user.getRegistarationDate());
    }

    @Test
    public void testGetRequestFound() throws Exception {

        Mockito.when(userService.get(Mockito.any())).thenReturn(user);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userInstance);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/getId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        UserResponseData userResponseData = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserResponseData.class);
        Assertions.assertEquals(user.getId().toString(), userResponseData.getData().getUserId());

    }

    @Test
    public void testGetListRequest() throws Exception {
        List userList = new ArrayList();
        userList.add(user);

        Mockito.when(userService.get()).thenReturn(userList);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userInstance);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/getAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        List<UserResponseData> userResponseDataList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        Assertions.assertEquals(1, userResponseDataList.size());
    }

    @Test
    public void testPostRequest() throws Exception {
        Mockito.when(userService.post(Mockito.anyString()
                                    ,Mockito.anyString()
                                    ,Mockito.anyString()
                                    ,Mockito.any(Date.class))
                    ).thenReturn(user);
        Mockito.when(userService.get(Mockito.any())).thenReturn(null);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userInstance);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        UserResponseData userResponseData = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserResponseData.class);
        Assertions.assertEquals(user.getId().toString(), userResponseData.getData().getUserId());
    }

    @Test
    public void testPutRequest() throws Exception {
        Mockito.when(userService.put(Mockito.any()
                                    ,Mockito.anyString()
                                    ,Mockito.anyString()
                                    ,Mockito.anyString()
                                    ,Mockito.any(Date.class))
                    ).thenReturn(user);
        Mockito.when(userService.get(Mockito.any())).thenReturn(user);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userInstance);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/users/put")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        UserResponseData userResponseData = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserResponseData.class);
        Assertions.assertEquals(user.getId().toString(), userResponseData.getData().getUserId());
    }

    @Test
    public void testDeleteRequest() throws Exception {

        Mockito.when(userService.get(Mockito.any())).thenReturn(user);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userInstance);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        UserResponseData userResponseData = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserResponseData.class);
        Assertions.assertEquals(user.getId().toString(), userResponseData.getData().getUserId());
    }
}
