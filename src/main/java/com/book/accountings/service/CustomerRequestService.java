package com.book.accountings.service;

import com.book.accountings.entity.AccountingTask;
import com.book.accountings.entity.ActiveTask;
import com.book.accountings.entity.CustomerRequest;
import com.book.accountings.entity.Expert;
import com.book.accountings.repository.AccountingTaskRepository;
import com.book.accountings.repository.ActiveTaskRepository;
import com.book.accountings.repository.CustomerRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerRequestService {

    public static final Queue<ActiveTask> activeTasks = new LinkedList<>();

    @Autowired
    private ExpertService expertService;

    @Autowired
    private CustomerRequestRepository customerRequestRepository;

    @Autowired
    private AccountingTaskRepository accountingTaskRepository;

    @Autowired
    private ActiveTaskRepository activeTaskRepository;

    public List<CustomerRequest> getCustomerRequestsByCustomerId(Integer customerId) {

        return Objects.requireNonNull(customerRequestRepository.findAll())
                .stream().filter(customerRequest -> Objects.equals(customerId, customerRequest.getCustomerId()))
                .collect(Collectors.toList());
    }

    public CustomerRequest saveOrUpdate(CustomerRequest customerRequest) {
        return customerRequestRepository.save(customerRequest);
    }

    public CustomerRequest getCustomerRequest(String requestId) {
        List<CustomerRequest> customerRequests =
                customerRequestRepository.findAll();

        Optional<CustomerRequest> customerRequestOptional =
                customerRequests.stream().filter(customerRequest ->
                        requestId.equals(customerRequest.getRequestId())).findFirst();

        return customerRequestOptional.orElse(null);
    }

    public Map<String, Object> placeCustomerRequest(CustomerRequest customerRequest) {

        customerRequest.setRequestId(UUID.randomUUID().toString());
        customerRequest.setRequestStatus("CREATED");
        customerRequest = customerRequestRepository.save(customerRequest);

        Optional<AccountingTask> accountingTaskOptional =
                accountingTaskRepository.findById("Task 1");

        AccountingTask accountingTask = accountingTaskOptional.get();

        ActiveTask activeTask = new ActiveTask();
        activeTask.setAccountingTaskId(accountingTask.getAccountingTaskId());
        activeTask.setTaskStatus("CREATED");
        activeTask.setRequestId(customerRequest.getRequestId());
        activeTask.setDeadlineDateTime(LocalDateTime.now().plusDays(accountingTask.getDeadline()));
        activeTaskRepository.save(activeTask);

        Map<String, Object> response = new HashMap<>();
        List<Expert> experts = expertService.getAllAvailableExperts();
        for (Expert expert : experts) {
            if (expert.getAvailableHoursForDay() >= accountingTask.getTimeNeeded()) {
                activeTask.setExpertId(expert.getExpertId());
                activeTask.setTaskStatus("ASSIGNED");
                expert.setAvailableHoursForDay(expert.getAvailableHoursForDay() - accountingTask.getTimeNeeded());
                activeTaskRepository.save(activeTask);
                expertService.saveOrUpdate(expert);
                response.put("SUCCESS", "Expert is allocated");
                return response;
            }
            if (expert.getAvailableHoursForDay() <= 0) {
                expert.setExpertStatus("EXHAUSTED");
                expertService.saveOrUpdate(expert);
            }
        }
        addNewTasksToQueue(activeTask);
        response.put("SUCCESS", "Customer is queued");
        return response;
    }

    /**
     * This is the cron job which assigns the experts to the queued customers.
     * These are the customer's requests tasks which are not being assigned with experts & are
     * queued into the system to that they can be assigned with experts.
     * This job runs every hour.
     */
    @Scheduled(cron = "0 0 * * * *")
    public void expertAssigner() {

        while (activeTasks.size() > 0) {
            ActiveTask activeTask = activeTasks.peek();
            Optional<AccountingTask> accountingTaskOptional =
                    accountingTaskRepository.findById(activeTask.getAccountingTaskId());
            AccountingTask accountingTask = accountingTaskOptional.get();
            List<Expert> experts = expertService.getAllAvailableExperts();
            for (Expert expert : experts) {
                if (expert.getAvailableHoursForDay() >= accountingTask.getTimeNeeded()) {
                    activeTask.setExpertId(expert.getExpertId());
                    activeTask.setTaskStatus("ASSIGNED");
                    expert.setAvailableHoursForDay(expert.getAvailableHoursForDay() - accountingTask.getTimeNeeded());
                    activeTaskRepository.save(activeTask);
                    expertService.saveOrUpdate(expert);
                    activeTasks.poll();
                    break;
                }
                if (expert.getAvailableHoursForDay() <= 0) {
                    expert.setExpertStatus("EXHAUSTED");
                    expertService.saveOrUpdate(expert);
                }
            }
        }
    }

    public void addNewTasksToQueue(ActiveTask activeTask){
        activeTask.setTaskStatus("QUEUED");
        activeTaskRepository.save(activeTask);
        activeTasks.add(activeTask);
    }
}
