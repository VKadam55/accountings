//
//package com.book.accountings.assigner;
//
//import com.book.accountings.entity.AccountingTask;
//import com.book.accountings.entity.ActiveTask;
//import com.book.accountings.entity.Expert;
//import com.book.accountings.service.AccountingTaskService;
//import com.book.accountings.service.ActiveTaskService;
//import com.book.accountings.service.CustomerRequestService;
//import com.book.accountings.service.ExpertService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.core.task.TaskExecutor;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class TaskAssigner {
//
//    private static final Logger logger = LoggerFactory.getLogger(TaskAssigner.class);
//
//    @Autowired
//    private TaskExecutor taskExecutor;
//
//    @Autowired
//    private ExpertService expertService;
//
//    @Autowired
//    private ActiveTaskService activeTaskService;
//
//    @Autowired
//    private AccountingTaskService accountingTaskService;
//
//    @Autowired
//    private CustomerRequestService customerRequestService;
//
//    @Value("${is-run-with-minutes}")
//    private Boolean isRunWithMinutes;
//
////    @EventListener(ApplicationReadyEvent.class)
//    public void assignTasks(){
//
//        logger.info("Started with assigning tasks");
//
//        while(true) {
////            logger.info("Checking for new requests & experts availability.");
//
//            List<Expert> availableExperts = expertService.getAllAvailableExperts();
//            List<ActiveTask> activeTasks = activeTaskService.getActiveTasks("CREATED");
//            if (CollectionUtils.isEmpty(activeTasks) || CollectionUtils.isEmpty(availableExperts))
//                continue;
//
//            logger.info("Found new active tasks & experts availability");
//            ActiveTask currentActiveTask = activeTasks.get(0);
//            Expert currentAvailableExpert = availableExperts.get(0);
//
//            AccountingTask currentAccountingTask =
//                    accountingTaskService.getAccountingTaskById(currentActiveTask.getAccountingTaskId());
//
//            currentActiveTask.setTaskStatus("QUEUED");
//            activeTaskService.saveOrUpdate(currentActiveTask);
//
//            currentAvailableExpert.setExpertStatus("ALLOCATED");
//            expertService.saveOrUpdate(currentAvailableExpert);
//
//            taskExecutor.execute(new ExpertTask(expertService, activeTaskService, accountingTaskService,
//                    customerRequestService, currentActiveTask, currentAvailableExpert,
//                    currentAccountingTask, isRunWithMinutes));
//        }
//    }
//}
