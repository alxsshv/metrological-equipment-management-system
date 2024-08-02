package main.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import main.service.ServiceMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> catchDtoCompositionException(DtoCompositionException ex){
        log.error(ex.getMessage());
        return ResponseEntity.status(400).body(new ServiceMessage(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<?> catchParameterNotValidException(ParameterNotValidException ex){
        log.error(ex.getMessage());
        return ResponseEntity.status(400).body(new ServiceMessage(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<?> catchEntityAlreadyExistException(EntityAlreadyExistException ex){
        log.error(ex.getMessage());
        return ResponseEntity.status(400).body(new ServiceMessage(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<?> catchEntityNotFoundException(EntityNotFoundException ex){
        log.error(ex.getMessage());
        return ResponseEntity.status(404).body(new ServiceMessage(ex.getMessage()));
    }

//    @ExceptionHandler
//    public ResponseEntity<?> catchRuntimeException(RuntimeException ex){
//        log.error(ex.getMessage());
//        return ResponseEntity.status(500).body(new ServiceMessage(ex.getMessage()));
//    }

}
