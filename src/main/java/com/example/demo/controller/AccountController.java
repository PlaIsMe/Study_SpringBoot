package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Account;
import com.example.demo.service.interfaces.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;    

    @GetMapping
	public ResponseEntity<List<Account>> getAllAccounts() {
        @Valid List<Account> accountList = accountService.getAllAccounts();
        return ResponseEntity.ok().body(accountList);
	}

    @GetMapping(path = "{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long accountId) throws Exception{
        Account account = accountService.getAccountById(accountId);
        return ResponseEntity.ok(account);
    }

    @PostMapping
    public ResponseEntity<Object> createAccount(@Valid @RequestBody Account account, BindingResult result) throws Exception {
        if (result.hasErrors()){
            throw new Exception(result.getAllErrors().get(0).getDefaultMessage());
        }
        return ResponseEntity.ok().body(accountService.createAccount(account));
    }

    @DeleteMapping(path = "{accountId}")
    public void deleteAccount(@PathVariable("accountId") Long accountId) throws Exception {
        accountService.deleteAccount(accountId);
    }

    @PutMapping(path = "{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable("accountId") Long accountId, @Valid @RequestBody Account account, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new Exception(result.getAllErrors().get(0).getDefaultMessage());
        }
        return ResponseEntity.ok().body(accountService.updateAccount(accountId, account));        
    }
}
