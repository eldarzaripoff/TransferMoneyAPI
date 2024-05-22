package ru.netology.TransferMoneyAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.netology.TransferMoneyAPI.dao.TransferRepository;
import ru.netology.TransferMoneyAPI.models.TransferRequest;
@Service
public class TransferService {
    private final TransferRepository transferRepository;

    @Autowired
    public TransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public String transferMoney(TransferRequest transferRequest) {
        // Реализуйте логику перевода денежных средств
        // Например, можно проверить валидность карточных данных, достаточность средств и т.д.

        // Сохраняем запрос в репозитории и возвращаем идентификатор операции
        String operationId = transferRepository.save(transferRequest);
        return operationId;
    }

    public TransferRequest getTransferRequest(String operationId) {
        TransferRequest transferRequest = transferRepository.getTransferRequest(operationId);
        if (transferRequest == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer request not found");
        }
        return transferRequest;
    }

    public boolean checkOperationId(String operationId) {
        return transferRepository.checkOperationId(operationId);
    }

    public void deleteTransferRequest(String operationId) {
        transferRepository.deleteTransferRequest(operationId);
    }
}
