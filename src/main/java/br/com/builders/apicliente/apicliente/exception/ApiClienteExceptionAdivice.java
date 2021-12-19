package br.com.builders.apicliente.apicliente.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

import static br.com.builders.apicliente.apicliente.config.OpenAPIConfiguration.MSG_BAD_REQUEST;
import static br.com.builders.apicliente.apicliente.config.OpenAPIConfiguration.MSG_NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ApiClienteExceptionAdivice extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MSG_BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(BAD_REQUEST).body(MSG_BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(ClienteExistenteException.class)
    ResponseEntity<Object> handleClienteExistenteException(ClienteExistenteException e) {
        return ResponseEntity.status(BAD_REQUEST).body(MSG_BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(ClienteNaoExisteException.class)
    ResponseEntity<Object> handleClienteNaoExisteException(ClienteNaoExisteException e) {
        return ResponseEntity.status(BAD_REQUEST).body(MSG_BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(RequisicaoInvalidaExeception.class)
    ResponseEntity<Object> handleRequisicaoInvalidaExeception(RequisicaoInvalidaExeception e) {
        return ResponseEntity.status(BAD_REQUEST).body(MSG_BAD_REQUEST);
    }
}
