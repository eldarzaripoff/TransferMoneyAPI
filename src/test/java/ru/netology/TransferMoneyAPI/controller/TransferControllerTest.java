package ru.netology.TransferMoneyAPI.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import ru.netology.TransferMoneyAPI.dto.ConfirmationDTO;
import ru.netology.TransferMoneyAPI.dto.TransferDTO;
import ru.netology.TransferMoneyAPI.models.Amount;
import ru.netology.TransferMoneyAPI.models.ConfirmOperationRequest;
import ru.netology.TransferMoneyAPI.models.TransferRequest;
import ru.netology.TransferMoneyAPI.service.TransferService;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class TransferControllerTest {

    @Mock
    TransferService transferService;

    @InjectMocks
    TransferController transferController;

    @Test
    public void transferMoney_ReturnsValidResponseEntity() {
        // Arrange
        TransferDTO transferDTO = new TransferDTO("2202123443215555",
                "0224", "963", "9876678955556464",
                new Amount(5000, "USD"));
        String operationId = "123456";
        Mockito.when(transferService.transferMoney(Mockito.any(TransferRequest.class))).thenReturn(operationId);

        // Act
        ResponseEntity <Map<String, Object>> response = transferController.transferMoney(transferDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.containsKey("operationId"));
        assertEquals(operationId, responseBody.get("operationId"));

    }

    @Test
    public void transferMoneyWithResponseStatusException() {
        // Arrange
        TransferDTO transferDTO = new TransferDTO("2202123443215555",
                "0224", "963", "9876678955556464",
                new Amount(5000, "USD"));
        ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");

        Mockito.when(transferService.transferMoney(Mockito.any(TransferRequest.class))).thenThrow(responseStatusException);

        // Act
        ResponseEntity<Map<String, Object>> response = transferController.transferMoney(transferDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.containsKey("message"));
        assertEquals(responseStatusException.getReason(), responseBody.get("message"));
        assertTrue(responseBody.containsKey("id"));
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseBody.get("id"));
    }

    @Test
    public void transferMoneyWithGenericException() {
        // Arrange
        TransferDTO transferDTO = new TransferDTO("2202123443215555",
                "0224", "963", "9876678955556464",
                new Amount(5000, "USD"));
        RuntimeException runtimeException = new RuntimeException("Internal exception");
        Mockito.when(transferService.transferMoney(Mockito.any(TransferRequest.class))).thenThrow(runtimeException);

        // Act
        ResponseEntity<Map<String, Object>> response = transferController.transferMoney(transferDTO);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<String, Object> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.containsKey("message"));
        assertEquals("Error during transfer", responseBody.get("message"));
        assertTrue(responseBody.containsKey("id"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseBody.get("id"));
    }

    @Test
    public void confirmOperationWithValidEntity() {
        String operationId = "123456";
        ConfirmationDTO confirmationDTO = new ConfirmationDTO(operationId, "123");
        Mockito.when(transferService.checkOperationId(operationId)).thenReturn(true);

        ResponseEntity<Map<String, Object>> response = transferController.confirmTransfer(confirmationDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.containsKey("operationId"));
        assertEquals(operationId, responseBody.get("operationId"));
    }

    @Test
    public void testConfirmTransferNotFound() {
        String operationId = "123456";
        ConfirmationDTO confirmationDTO = new ConfirmationDTO(operationId, "123");
        Mockito.when(transferService.checkOperationId(operationId)).thenReturn(false);

        ResponseEntity<Map<String, Object>> response = transferController.confirmTransfer(confirmationDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Map<String, Object> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.containsKey("message"));
        assertTrue(responseBody.containsKey("id"));
        assertEquals("Operation with ID " + operationId + " not found.", responseBody.get("message"));
        assertEquals(HttpStatus.NOT_FOUND.value(), responseBody.get("id"));
    }

    @Test
    public void testConfirmTransferException() {
        String operationId = "123456";
        ConfirmationDTO confirmationDTO = new ConfirmationDTO(operationId, "123");
        Mockito.when(transferService.checkOperationId(operationId)).
                thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid operation"));

        ResponseEntity<Map<String, Object>> response = transferController.confirmTransfer(confirmationDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.containsKey("message"));
        assertTrue(responseBody.containsKey("id"));
        assertEquals("Invalid operation", responseBody.get("message"));
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseBody.get("id"));
    }

    @Test
    public void confirmTransferWithGenericException() {
        String operationId = "123456";
        ConfirmationDTO confirmationDTO = new ConfirmationDTO(operationId, "123");
        RuntimeException runtimeException = new RuntimeException("Internal exception");
        Mockito.when(transferService.checkOperationId(operationId)).
                thenThrow(runtimeException);

        ResponseEntity<Map<String, Object>> response = transferController.confirmTransfer(confirmationDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<String, Object> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.containsKey("message"));
        assertEquals("Error during confirm", responseBody.get("message"));
        assertTrue(responseBody.containsKey("id"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseBody.get("id"));

    }

}