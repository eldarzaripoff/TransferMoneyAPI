package ru.netology.TransferMoneyAPI.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.TransferMoneyAPI.models.Amount;
import ru.netology.TransferMoneyAPI.models.TransferRequest;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransferRepositoryTest {
    @Mock
    private Map<String, TransferRequest> transferRequests;

    @InjectMocks
    private TransferRepository transferRepository;

    @Test
    void save_shouldReturnUniqueOperationId() {
        // Arrange
        TransferRequest transferRequest = new TransferRequest("2202123443215555",
                "0224", "963", "9876678955556464",
                new Amount(5000, "USD"));

        // Act
        String operationId = transferRepository.save(transferRequest);

        // Assert
        assertNotNull(operationId);
        assertNotEquals("", operationId);
    }
    @Test
    void getTransferRequest_shouldReturnTransferRequestWhenExists() {
        // Arrange
        String operationId = "123456";
        TransferRequest expectedTransferRequest = new TransferRequest("2202123443215555",
                "0224", "963", "9876678955556464",
                new Amount(5000, "USD"));
        //transferRequests.put(operationId, expectedTransferRequest);
        Mockito.when(transferRequests.get(operationId)).thenReturn(expectedTransferRequest);

        // Act
        TransferRequest actualTransferRequest = transferRepository.getTransferRequest(operationId);

        // Assert
        Assertions.assertNotNull(actualTransferRequest);
        Assertions.assertEquals(expectedTransferRequest, actualTransferRequest);
    }

    @Test
    void getTransferRequest_shouldReturnNullWhenNotExists() {
        // Arrange
        String nonExistentOperationId = "123456789";

        // Act
        TransferRequest actualTransferRequest = transferRepository.getTransferRequest(nonExistentOperationId);

        // Assert
        Assertions.assertNull(actualTransferRequest);
    }


}