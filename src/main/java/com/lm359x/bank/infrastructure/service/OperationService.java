package com.lm359x.bank.infrastructure.service;

import com.lm359x.bank.entity.Account;
import com.lm359x.bank.entity.Operation;
import com.lm359x.bank.infrastructure.jpa.OperationRepository;
import com.lm359x.bank.use_case.account.ValidateAccountCreationUseCase;
import com.lm359x.bank.use_case.operation.CreateOperationUseCase;
import com.lm359x.bank.use_case.operation.GetAllOperationsByAccountUseCase;
import com.lm359x.bank.use_case.operation.ValidateOperationUseCase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperationService {
    private final OperationRepository operationRepository;
    private final CreateOperationUseCase createOperationUC;
    private final GetAllOperationsByAccountUseCase getAllOpsUC;

    private final ValidateOperationUseCase validateOperationUC;

    private final AccountService accountService;

    public OperationService(OperationRepository operationRepository, CreateOperationUseCase createOperationUC, GetAllOperationsByAccountUseCase getAllOpsUC, ValidateOperationUseCase validateOperationUC, AccountService accountService) {
        this.operationRepository = operationRepository;
        this.createOperationUC = createOperationUC;
        this.getAllOpsUC = getAllOpsUC;
        this.validateOperationUC = validateOperationUC;
        this.accountService = accountService;
    }

    public Operation createOperation(Long fromId,Long toId, Long balance){
        Account from = accountService.getAccountById(fromId);
        Account to = accountService.getAccountById(toId);
        if(validateOperationUC.execute(new ValidateOperationUseCase.InputValues(from,to,balance)).getResult()){
            Operation operation = createOperationUC.execute(new CreateOperationUseCase.InputValues(from,to,balance)).getOp();
            accountService.updateAccount(fromId,from);
            accountService.updateAccount(toId,to);
            operationRepository.save(operation);
        }
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
