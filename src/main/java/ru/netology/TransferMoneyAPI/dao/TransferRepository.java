package ru.netology.TransferMoneyAPI.dao;

import org.springframework.stereotype.Repository;
import ru.netology.TransferMoneyAPI.models.TransferRequest;

import java.util.*;
@Repository
public class TransferRepository {
    private final Map<String, TransferRequest> transferRequests = new HashMap<>();

    public String save(TransferRequest transferRequest) {
        String operationId = UUID.randomUUID().toString();
        transferRequests.put(operationId, transferRequest);
        return operationId;
    }

    public TransferRequest getTransferRequest(String operationId) {
        return transferRequests.get(operationId);
    }

    public void deleteTransferRequest(String operationId) {
        transferRequests.remove(operationId);
    }
}
