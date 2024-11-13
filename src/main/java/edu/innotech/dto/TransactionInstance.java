package edu.innotech.dto;

import edu.innotech.model.TypeTransaction;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Data
public class TransactionInstance {

    // Идентификатор экземпляра Transaction
    // Если есть, то обновляется информация по транзакции
    // Если NULL/пусто, то создаётся новя транзакция
    private Long instanceId;

    // Сумма транзакци
    @NotNull
    @Valid
    private Double sumTransaction;

    // Дата транзакци
    @NotNull
    @Valid
    private Date dateTransaction;

    // Тип транзакци
    @NotNull
    @Valid
    private TypeTransaction typeTransaction;

    // ID User
    // Ссылка пользователя, которому принадлежит тразакция
    @NotNull
    @Valid
    private Long userId;

}
