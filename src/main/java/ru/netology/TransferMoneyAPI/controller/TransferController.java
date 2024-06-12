package ru.netology.TransferMoneyAPI.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.netology.TransferMoneyAPI.dto.ConfirmationDTO;
import ru.netology.TransferMoneyAPI.dto.TransferDTO;
import ru.netology.TransferMoneyAPI.mapper.Mapper;
import ru.netology.TransferMoneyAPI.models.ConfirmOperationRequest;
import ru.netology.TransferMoneyAPI.models.TransferRequest;
import ru.netology.TransferMoneyAPI.service.TransferService;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class TransferController {
    private final TransferService transferService;

    private static final Logger log = LoggerFactory.getLogger(TransferController.class);

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<Map<String, Object>> transferMoney(@RequestBody TransferDTO transferDTO) {
        try {
            log.info("Получен запрос на перевод: {}", transferDTO.toString());
            TransferRequest transferRequest = Mapper.toTransferRequest(transferDTO);
            String operationId = transferService.transferMoney(transferRequest);
            log.info("Новый запрос на перевод сохранен с ID: {}", operationId);
            Map<String, Object> response = new ConcurrentHashMap<>();
            response.put("operationId", operationId);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException ex) {
            log.warn("Ошибка при обработке запроса на перевод: {}", ex.getReason());
            Map<String, Object> errorResponse = new ConcurrentHashMap<>();
            errorResponse.put("message", ex.getReason());
            errorResponse.put("id", ex.getStatusCode().value());
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        } catch (Exception ex) {
            log.error("Внутренняя ошибка при обработке запроса на перевод: {}", ex.getMessage(), ex);
            Map<String, Object> errorResponse = new ConcurrentHashMap<>();
            errorResponse.put("message", "Error during transfer");
            errorResponse.put("id", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    @PostMapping("/confirmOperation")
    public ResponseEntity<Map<String, Object>> confirmTransfer(@RequestBody ConfirmationDTO confirmationDTO) {
        try {
            log.info("Получен запрос на подтверждение операции: {}", confirmationDTO.toString());
            ConfirmOperationRequest confirmOperationRequest = Mapper.toConfirmOperationRequest(confirmationDTO);
            String operationId = confirmOperationRequest.getOperationId();
            if (transferService.checkOperationId(confirmOperationRequest.getOperationId())) {
                log.info("Операция с ID {} найдена, выполняется подтверждение", operationId);
                Map<String, Object> response = new ConcurrentHashMap<>();
                response.put("operationId", operationId);
                return ResponseEntity.ok(response);
            } else {
                log.warn("Операция с ID {} не найдена", operationId);
                Map<String, Object> errorResponse = new ConcurrentHashMap<>();
                errorResponse.put("message", "Operation with ID " + operationId + " not found.");
                errorResponse.put("id", HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (ResponseStatusException ex) {
            log.warn("Ошибка при обработке запроса на подтверждение операции: {}", ex.getReason());
            Map<String, Object> errorResponse = new ConcurrentHashMap<>();
            errorResponse.put("message", ex.getReason());
            errorResponse.put("id", ex.getStatusCode().value());
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        } catch (Exception ex) {
            log.error("Внутренняя ошибка при обработке запроса на подтверждение операции: {}", ex.getMessage(), ex);
            Map<String, Object> errorResponse = new ConcurrentHashMap<>();
            errorResponse.put("message", "Error during transfer");
            errorResponse.put("id", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
