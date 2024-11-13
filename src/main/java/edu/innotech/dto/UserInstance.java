package edu.innotech.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Data
public class UserInstance {

    // Идентификатор экземпляра User
    // Если есть, то обновляется информация по пользователю
    // Если NULL/пусто, то создаётся новый пользователь
    private Long instanceId;

    // Имя пользователя
    @NotNull
    @Valid
    private String name;

    // Фамилия пользователя
    @NotNull
    @Valid
    private String lastName;

    // EMail пользователя
    @Pattern(regexp = "^[ A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    private String eMail;

    // Дата регистрации
    @NotNull
    @Valid
    private Date registarationDate;

}
