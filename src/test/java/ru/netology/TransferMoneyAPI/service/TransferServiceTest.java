package ru.netology.TransferMoneyAPI.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.netology.TransferMoneyAPI.dao.TransferRepository;
import ru.netology.TransferMoneyAPI.models.Amount;
import ru.netology.TransferMoneyAPI.models.TransferRequest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {
    @Mock
    private TransferRepository transferRepository;

    @InjectMocks
    private TransferService transferService;

    @Test
    public void testTransferMoneySuccess() {
        TransferRequest transferRequest = new TransferRequest("2202123443215555",
                "0224", "963", "9876678955556464",
                new Amount(5000, "USD"));
        Mockito.when(transferRepository.save(transferRequest)).thenReturn("123456");

        String operationId = transferService.transferMoney(transferRequest);

        assertEquals("123456", operationId);
        Mockito.verify(transferRepository, Mockito.times(1)).save(transferRequest);

    }
    @Test
    void testTransferMoneyException() {
        TransferRequest transferRequest = new TransferRequest("2202123443215555",
                "0224", "963", "9876678955556464",
                new Amount(5000, "USD"));
        Mockito.when(transferRepository.save(transferRequest)).thenThrow(new RuntimeException("Ошибка при сохранении запроса на перевод"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> transferService.transferMoney(transferRequest));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Ошибка при сохранении запроса на перевод", exception.getReason());
        Mockito.verify(transferRepository, Mockito.times(1)).save(transferRequest);
    }

    @Test
    void testGetTransferRequestSuccess() {
        String operationId = "123456";
        TransferRequest transferRequest = new TransferRequest("2202123443215555",
                "0224", "963", "9876678955556464",
                new Amount(5000, "USD"));
        Mockito.when(transferRepository.getTransferRequest(operationId)).thenReturn(transferRequest);

        TransferRequest result = transferService.getTransferRequest(operationId);

        assertEquals(transferRequest, result);
        Mockito.verify(transferRepository, Mockito.times(1)).getTransferRequest(operationId);

    }

    @Test
    void testGetTransferRequestNotFound() {
        // Arrange
        String operationId = "123456";
        Mockito.when(transferRepository.getTransferRequest(operationId)).thenReturn(null);

        // Act and Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> transferService.getTransferRequest(operationId));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Transfer request not found", exception.getReason());
        Mockito.verify(transferRepository, Mockito.times(1)).getTransferRequest(operationId);
    }

    @Test
    void testCheckOperationIdNotFound() {
        // Arrange
        String operationId = "123456";
        Mockito.when(transferRepository.getTransferRequest(operationId)).thenReturn(null);

        // Act
        boolean result = transferService.checkOperationId(operationId);

        // Assert
        assertFalse(result);
        Mockito.verify(transferRepository, Mockito.times(1)).getTransferRequest(operationId);
    }

    @Test
    void testCheckOperationIdException() {
        // Arrange
        String operationId = "123456";
        Mockito.when(transferRepository.getTransferRequest(operationId)).thenThrow(new RuntimeException("Ошибка при проверке операции"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> transferService.checkOperationId(operationId));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Ошибка при проверке операции", exception.getReason());
        Mockito.verify(transferRepository, Mockito.times(1)).getTransferRequest(operationId);
    }

}