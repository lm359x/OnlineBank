package com.lm359x.bank.infrastructure.service;

import com.lm359x.bank.entity.Account;
import com.lm359x.bank.entity.Operation;
import com.lm359x.bank.entity.User;
import com.lm359x.bank.infrastructure.jpa.OperationRepository;
import com.lm359x.bank.use_case.operation.CreateOperationUseCase;
import com.lm359x.bank.use_case.operation.FindAllOperationsUseCase;
import com.lm359x.bank.use_case.operation.GetAllOperationsByAccountUseCase;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperationService {
    private final OperationRepository operationRepository;
    private final CreateOperationUseCase createOperationUC;
    private final GetAllOperationsByAccountUseCase getAllOpsUC;

    private final AccountService accountService;

    public OperationService(OperationRepository operationRepository, CreateOperationUseCase createOperationUC,  GetAllOperationsByAccountUseCase getAllOpsUC,  AccountService accountService) {
        this.operationRepository = operationRepository;
        this.createOperationUC = createOperationUC;
        this.getAllOpsUC = getAllOpsUC;
        this.accountService = accountService;
    }

    public Operation createOperation(){
        return null;
    }

    public List<Operation> showOperations(){
        List<Operation> operationList = new ArrayList<>();
        List<Account> accountList = accountService.showAccounts();
        for(Account acc: accountList){
            operationList.addAll(getAllOpsUC
                    .execute(new GetAllOperationsByAccountUseCase.InputValues(acc)).getOperationList());
        }
        return operationList;
    }




}
