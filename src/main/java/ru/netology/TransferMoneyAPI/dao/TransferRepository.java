package ru.netology.TransferMoneyAPI.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.netology.TransferMoneyAPI.models.TransferRequest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TransferRepository {
    private final Map<String, TransferRequest> transferRequests = new ConcurrentHashMap<>();

    private static final Logger log = LoggerFactory.getLogger(TransferRepository.class);

    public String save(TransferRequest transferRequest) {
        String operationId = UUID.randomUUID().toString();
        transferRequests.put(operationId, transferRequest);
        log.info("Сохранен новый запрос на перевод с ID: {}", operationId);
        return operationId;
    }

    public TransferRequest getTransferRequest(String operationId) {
        if(transferRequests.get(operationId) == null) log.warn("Не найден запрос на перевод с ID: {}", operationId);
        log.trace("Найден запрос на перевод с ID: {}", operationId);
        return transferRequests.get(operationId);
    }

    public void deleteTransferRequest(String operationId) {
        transferRequests.remove(operationId);
    }
}
