package edu.innotech.services;

import edu.innotech.model.User;
import edu.innotech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User get(Long userId) {
        User user = userRepository.findByUserId(userId);
        System.out.println("user = "+user);
        return user;
    }

    public List<User> get(){
        return (List<User>)userRepository.findAll();
    }

    public void delete(Long userId){
        userRepository.deleteById(userId);
    }


    private void saveUser(User user
            , String name
            , String lastName
            , String eMail
            , Date registarationDate
            ) {
        user.setName(name);
        user.setLastName(lastName);
        user.setEMail(eMail);
        user.setRegistarationDate(registarationDate);
        userRepository.save(user);
    }

    public User put(Long id
                , String name
                , String lastName
                , String eMail
                , Date registarationDate
               ){
    var user = userRepository.findByUserId(id);
    saveUser(user, name, lastName, eMail, registarationDate);
    return user;
    }

    public User post(String name
            , String lastName
            , String eMail
            , Date registarationDate
            ){
        User user = new User();
        saveUser(user, name, lastName, eMail, registarationDate);
        return user;
    }
}
