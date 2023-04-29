package com.book.accountings.api;

import com.book.accountings.service.ActiveTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActiveTaskController {

    @Autowired
    private ActiveTaskService activeTaskService;

    @PatchMapping(value = "/v1/active-tasks/{activeTaskId}")
    public ResponseEntity<Void> executeActiveTaskAction(@PathVariable Integer activeTaskId) {
        activeTaskService.executeActiveTaskAction(activeTaskId);
        return ResponseEntity.noContent().build();
    }
}
