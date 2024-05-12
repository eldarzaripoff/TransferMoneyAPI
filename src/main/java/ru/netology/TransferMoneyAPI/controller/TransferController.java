package ru.netology.TransferMoneyAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.netology.TransferMoneyAPI.models.TransferRequest;
import ru.netology.TransferMoneyAPI.service.TransferService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TransferController {
    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<Map<String, Object>> transferMoney(@RequestBody TransferRequest transferRequest) {
        try {
            String operationId = transferService.transferMoney(transferRequest);
            Map<String, Object> response = new HashMap<>();
            response.put("operationId", operationId);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException ex) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", ex.getReason());
            errorResponse.put("id", ex.getStatusCode().value());
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        } catch (Exception ex) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error during transfer");
            errorResponse.put("id", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
