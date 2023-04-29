package com.book.accountings.api;

import com.book.accountings.entity.CustomerRequest;
import com.book.accountings.service.CustomerRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CustomerRequestController {

    @Autowired
    private CustomerRequestService customerRequestService;

    @GetMapping(value = "/v1/customers/{customerId}/customer-requests")
    public List<CustomerRequest> getCustomerRequests(@PathVariable Integer customerId) {
        return customerRequestService.getCustomerRequestsByCustomerId(customerId);
    }

    @PostMapping(value = "/v1/customer-requests")
    public Map<String, Object> createCustomerRequest(@RequestBody CustomerRequest customerRequest) {
        return customerRequestService.placeCustomerRequest(customerRequest);
    }
}
