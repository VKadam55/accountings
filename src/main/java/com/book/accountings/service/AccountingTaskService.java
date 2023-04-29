package com.book.accountings.service;

import com.book.accountings.entity.AccountingTask;
import com.book.accountings.repository.AccountingTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountingTaskService {

    @Autowired
    private AccountingTaskRepository accountingTaskRepository;

    public List<AccountingTask> getAllAccountingTasks() {
        return accountingTaskRepository.findAll();
    }

    public AccountingTask getAccountingTaskById(String accountingTaskId) {
        return accountingTaskRepository.findById(accountingTaskId).get();
    }

    public AccountingTask getTaskWhoseDependentTaskIsCompleted(String dependentTaskId) {

        Optional<AccountingTask> accountingTaskOptional = Objects.requireNonNull(accountingTaskRepository.findAll())
                .stream().filter(accountingTask ->
                        dependentTaskId.equals(accountingTask.getDependentTaskId())).findFirst();

        return accountingTaskOptional.orElse(null);
    }
}
