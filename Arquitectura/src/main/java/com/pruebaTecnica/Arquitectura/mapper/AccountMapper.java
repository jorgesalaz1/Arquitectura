package com.pruebatecnica.arquitectura.mapper;

import com.pruebatecnica.arquitectura.dto.AccountDto;
import com.pruebatecnica.arquitectura.entity.persistence.Account;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountMapper {
    public static AccountDto convertToDto(Account account){
        if(account == null){
            return null;
        }

        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setNumberAccount(account.getNumberAccount());
        accountDto.setType(account.getType());
        accountDto.setInitialBalance(account.getInitialBalance());
        accountDto.setState(account.isState());
        accountDto.setClientId(account.getClientId());
        return accountDto;
    }

    public static Account convertToEntity(AccountDto accountDto){
        if(accountDto == null){
            return null;
        }
        return new Account (
                accountDto.getId(),
                accountDto.getNumberAccount(),
                accountDto.getType(),
                accountDto.getInitialBalance(),
                accountDto.isState(),
                accountDto.getClientId()
        );

    }
}
