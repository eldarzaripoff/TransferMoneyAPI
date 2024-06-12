package ru.netology.TransferMoneyAPI.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.netology.TransferMoneyAPI.dao.TransferRepository;
import ru.netology.TransferMoneyAPI.models.Amount;
import ru.netology.TransferMoneyAPI.models.TransferRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TransferService {
    private final TransferRepository transferRepository;

    private static final Logger log = LoggerFactory.getLogger(TransferService.class);

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public TransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public String transferMoney(TransferRequest transferRequest) {
        try {
            // Сохраняем запрос в репозитории и возвращаем идентификатор операции
            String operationId = transferRepository.save(transferRequest);
            logTransferOperation(operationId, transferRequest.getCardFromNumber(), transferRequest.getCardToNumber(), transferRequest.getAmount(), "Success");
            return operationId;
        } catch (Exception e) {
            logTransferOperation(null, transferRequest.getCardFromNumber(), transferRequest.getCardToNumber(), transferRequest.getAmount(), "Error: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при сохранении запроса на перевод");
        }
    }

    public TransferRequest getTransferRequest(String operationId) {
        try {
            TransferRequest transferRequest = transferRepository.getTransferRequest(operationId);
            if (transferRequest == null) {
                log.warn("Не найден запрос на перевод с ID: {}", operationId);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer request not found");
            }
            log.trace("Найден запрос на перевод с ID: {}", operationId);
            return transferRequest;
        } catch (Exception e) {
            log.error("Ошибка при получении запроса на перевод: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при получении запроса на перевод");
        }

    }

    public boolean checkOperationId(String operationId) {
        try {
            return transferRepository.getTransferRequest(operationId) != null;
        } catch (Exception e) {
            log.error("Ошибка при проверке операции: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при проверке операции");
        }
    }

    private void logTransferOperation(String operationId ,String cardFromNumber, String cardToNumber, Amount amount, String result) {
        LocalDateTime now = LocalDateTime.now();
        String logEntry = "Operation ID: " + operationId + "\n" + "Data and time: " + dateTimeFormatter.format(now) + "\n"
                + "cardFromNumber: " + cardFromNumber + "\n" + "cardToNumber: " + cardToNumber
                + "\n" + "amount: " + amount.getValue() + " " + amount.getValue() + "\n" + result;
        log.info(logEntry);
    }

    public void deleteTransferRequest(String operationId) {
        transferRepository.deleteTransferRequest(operationId);
    }
}
