package br.com.naiconmartins.todolist.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * A anotação @ControllerAdvice indica que esta classe vai "observar"
 * todos os controllers da aplicação (ou seja, todas as classes com @RestController ou @Controller).
 *
 * Isso é usado principalmente para:
 *  - Centralizar o tratamento de erros (exceptions)
 *  - Evitar duplicar blocos try/catch em cada controller
 *  - Personalizar mensagens de erro para o cliente (frontend ou API consumer)
 *
 * Em resumo: esta classe será automaticamente "chamada" sempre que
 * alguma exceção for lançada dentro de qualquer controller.
 *
 * Exemplo:
 * Se algum método do TaskController lançar uma exceção (como IllegalArgumentException),
 * o Spring pode redirecionar essa exceção para um método aqui dentro
 * anotado com @ExceptionHandler(IllegalArgumentException.class)
 *
 * Assim, você pode tratar o erro em um só lugar.
 */
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMostSpecificCause().getMessage());

    }
}
