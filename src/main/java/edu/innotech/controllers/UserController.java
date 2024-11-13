package edu.innotech.controllers;

import edu.innotech.dto.UserInstance;
import edu.innotech.dto.UserResponse;
import edu.innotech.dto.UserResponseData;
import edu.innotech.model.User;
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
public class UserController {

    @Autowired
    Environment environment;

    @Autowired
    private UserService userService;

    @GetMapping("/users/getId")
    public ResponseEntity<UserResponseData> get(@Valid @RequestBody UserInstance userInstance) {

        User user = userService.get(userInstance.getInstanceId());

        if (user == null){
            return new ResponseEntity("Не найден пользователь по параметрам запроса", HttpStatus.NOT_FOUND);
        }

        UserResponseData userResponseData = new UserResponseData();
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getId().toString());
        userResponseData.setData(userResponse);

        return ResponseEntity.ok(userResponseData);
    }

    @GetMapping("/users/getAll")
    public ResponseEntity<List<UserResponseData>> getAll(@RequestBody UserInstance userInstance) {
        List<User> userList = userService.get();

        if (userList.size() == 0){
            return new ResponseEntity("Не найдено ни одого пользователя", HttpStatus.NOT_FOUND);
        }

        List<UserResponseData> userResponceDataList = new ArrayList();
        UserResponseData userResponceData = null;
        UserResponse userResponse = null;
        for(User user: userList ){
            userResponceData = new UserResponseData();
            userResponse = new UserResponse();
            userResponse.setUserId(user.getId().toString());
            userResponceData.setData(userResponse);
            userResponceDataList.add(userResponceData);
        }
        return ResponseEntity.ok(userResponceDataList);
    }

    @PostMapping("/users/post")
    public ResponseEntity<UserResponseData> post(@Valid @RequestBody UserInstance userInstance) {
        User user = userService.get(userInstance.getInstanceId());

        if (user != null){
            return new ResponseEntity("Пользователь с таким идентификатотром уже существует", HttpStatus.IM_USED);
        }

        user = userService.post(userInstance.getName()
                ,userInstance.getLastName()
                ,userInstance.getEMail()
                ,userInstance.getRegistarationDate());

        UserResponseData userResponceData = new UserResponseData();
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getId().toString());
        userResponceData.setData(userResponse);

        return ResponseEntity.ok(userResponceData);
    }

    @PutMapping("/users/put")
    public ResponseEntity<UserResponseData> put(@Valid @RequestBody UserInstance userInstance) {
        User user = userService.get(userInstance.getInstanceId());

        if (user == null){
            return new ResponseEntity("Не найден пользователь с таким идентификатотром", HttpStatus.NOT_FOUND);
        }

        user = userService.put(userInstance.getInstanceId()
                , userInstance.getName()
                , userInstance.getLastName()
                , userInstance.getEMail()
                , userInstance.getRegistarationDate());

        UserResponseData userResponseData = new UserResponseData();
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getId().toString());
        userResponseData.setData(userResponse);

        return ResponseEntity.ok(userResponseData);
    }

    @DeleteMapping("/users/delete")
    public ResponseEntity<UserResponseData> delete(@Valid @RequestBody UserInstance userInstance) {
        User user = userService.get(userInstance.getInstanceId());

        if (user == null){
            return new ResponseEntity("Не найден пользователь с таким идентификатотром", HttpStatus.NOT_FOUND);
        }

        userService.delete(userInstance.getInstanceId());

        UserResponseData userResponseData = new UserResponseData();
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(userInstance.getInstanceId().toString());
        userResponseData.setData(userResponse);

        return ResponseEntity.ok(userResponseData);
    }

}
