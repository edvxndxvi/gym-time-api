package br.com.fiap.gym_time.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.gym_time.models.Account;
import br.com.fiap.gym_time.repository.AccountRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountRepository repository;

    @GetMapping()
    public List<Account> getAccounts() {
        return repository.findAll();
    }

    @PostMapping()
    @CacheEvict(value = "accounts", allEntries = true)
    @Operation(summary = "Create a new account", description = "Create a new account", responses = {
        @ApiResponse(responseCode = "201"),
        @ApiResponse(responseCode = "400"),
    })
    public ResponseEntity<Account> createAccount(@RequestBody @Valid Account account) {
        log.info("Creating account...");
        repository.save(account);
        return ResponseEntity.status(201).body(account);
    }
    
    @GetMapping("/{id}")
    public Account getAccountByID(@PathVariable Long id) {
        log.info("Searching account " + id);
        return getAccount(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        log.info("Deleting account " + id);
        repository.delete(getAccount(id));
    }    

    @PutMapping("/{id}")
    public void updateAccount(@PathVariable Long id, @RequestBody @Valid Account account) {
        log.info("Updating account " + id);
        repository.delete(getAccount(id));
        account.setId(id);
        repository.save(account);
    }

    private Account getAccount(Long id) {
        return repository
            .findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
            );
    }
}
