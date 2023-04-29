package com.book.accountings.api;

import com.book.accountings.entity.ActiveTask;
import com.book.accountings.entity.Expert;
import com.book.accountings.service.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExpertController {

    @Autowired
    private ExpertService expertService;

    @GetMapping(value = "/v1/available-experts")
    public ResponseEntity<List<Expert>> getAvailableExperts() {
        return new ResponseEntity<>(expertService.getAllAvailableExperts(), HttpStatus.OK);
    }

    @GetMapping(value = "/v1/experts/{expertId}/active-tasks")
    public ResponseEntity<List<ActiveTask>> getActiveTasks(@PathVariable Integer expertId) {
        return new ResponseEntity<>(expertService.getAllActiveTasks(expertId), HttpStatus.OK);
    }
}
