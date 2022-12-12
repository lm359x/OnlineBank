package com.lm359x.bank.infrastructure.api;

import com.lm359x.bank.entity.Operation;
import com.lm359x.bank.infrastructure.service.OperationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/operations")
public class OperationController {
    private final OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping
    public List<Operation> showOperations(){
        return operationService.showOperations();
    }

    @GetMapping("/create")
    public ResponseEntity<Operation> createOperation(@RequestParam Long fromid, @RequestParam Long toId, @RequestParam Long amount){
        return ResponseEntity.ok(operationService.createOperation(fromid, toId, amount));
    }


}
