package ru.netology.TransferMoneyAPI.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.netology.TransferMoneyAPI.dto.ConfirmationDTO;
import ru.netology.TransferMoneyAPI.dto.TransferDTO;
import ru.netology.TransferMoneyAPI.models.ConfirmOperationRequest;
import ru.netology.TransferMoneyAPI.models.TransferRequest;

public class Mapper {
    private static final Logger log = LoggerFactory.getLogger(Mapper.class);
    public static TransferRequest toTransferRequest(TransferDTO transferDTO) {
        try {
            log.debug("Преобразование DTO в модель запроса на перевод: {}", transferDTO);
            TransferRequest transferRequest = new TransferRequest(
                    transferDTO.getCardFromNumber(),
                    transferDTO.getCardFromValidTill(),
                    transferDTO.getCardFromCVV(),
                    transferDTO.getCardToNumber(),
                    transferDTO.getAmount()
            );
            log.debug("Преобразование завершено: {}", transferRequest);
            return transferRequest;

        } catch (Exception e) {
            log.error("Ошибка при преобразовании DTO в модель запроса на перевод: {}", e.getMessage(), e);
            throw e;
        }
    }

    public static ConfirmOperationRequest toConfirmOperationRequest(ConfirmationDTO confirmationDTO) {
        try {
            log.debug("Преобразование DTO в модель запроса на подтверждение операции: {}", confirmationDTO);
            ConfirmOperationRequest confirmOperationRequest = new ConfirmOperationRequest(
                    confirmationDTO.getOperationId(),
                    confirmationDTO.getCode()
            );
            log.debug("Преобразование завершено: {}", confirmOperationRequest);
            return confirmOperationRequest;
        } catch (Exception e) {
            log.error("Ошибка при преобразовании DTO в модель запроса на подтверждение операции: {}", e.getMessage(), e);
            throw e;
        }
    }
}
