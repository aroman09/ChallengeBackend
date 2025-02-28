package com.nttdata.app.account.transaction.mapper;

import com.nttdata.app.account.transaction.model.AccountDto;
import com.nttdata.app.account.transaction.model.Client;
import com.nttdata.app.account.transaction.model.TransactionClientResponse;
import org.mapstruct.*;
import com.nttdata.app.account.transaction.model.TransactionDto;
import com.nttdata.app.account.transaction.model.entity.Transaction;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mappings({
            @Mapping(source = "transaction.transactionId", target = "idTransaction"),
            @Mapping(source = "transaction.dateTransaction", target = "dateTransaction", qualifiedByName = "convertToLocalDate"),
            @Mapping(source = "transaction.amount", target = "mount"),
            @Mapping(source = "transaction.balance", target = "balance"),
            @Mapping(source = "transaction.initialBalance", target = "initialBalance"),
            @Mapping(source = "client.name", target = "name"),
            @Mapping(source = "accountDto.number", target = "number"),
            @Mapping(source = "accountDto.type", target = "typeAccount"),
            @Mapping(source = "accountDto.status", target = "status"),
    })
    TransactionClientResponse toDTOResponse(Transaction transaction, AccountDto accountDto, Client client);

    @Mappings({
            @Mapping(source = "transactionDto.type", target = "type"),
            @Mapping(source = "transactionDto.mount", target = "amount"),
            @Mapping(source = "transactionDto.balance", target = "balance"),
            @Mapping(source = "transactionDto.initialBalance", target = "initialBalance"),
            @Mapping(source = "account", target = "account"),
    })
    Transaction toEntity(TransactionDto transactionDto, Long account);

    @Named("convertToLocalDate")
    default LocalDate formatDate(LocalDateTime date) {
        return (date != null) ? date.toLocalDate() : null;
    }
}
