package carmenromano.gestione_dispositivi.exceptions;


import carmenromano.gestione_dispositivi.payloads.ErrorMessagePayload;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessagePayload handleBadRequest(BadRequestException ex) {
        if (ex.getErrorsList() != null) {
            String message = ex.getErrorsList().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(". "));
            return new ErrorMessagePayload(message);
        } else {
            return new ErrorMessagePayload(ex.getMessage());
        }
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessagePayload handleNotFound(NotFoundException ex) {
        return new ErrorMessagePayload(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessagePayload handleGenericErrors(Exception ex) {
        ex.printStackTrace();
        return new ErrorMessagePayload("Errore server!");
    }
}
