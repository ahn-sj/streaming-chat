package tally.chatting.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErrorTypeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ErrorResponse> handleErrorTypeException(
            final ErrorTypeException e,
            final ServerWebExchange exchange
    ) {
        final ServerHttpRequest request = exchange.getRequest();

        final URI uri = request.getURI();
        final HttpMethod method = request.getMethod();
        final String message = e.getMessage();
        log.error("[General exception occurred] Request URI: {}, Method: {}, Error: {}", uri, method, message, e);

        final CommonError commonError = new CommonError(e.getErrorType().getCode(), e.getMessage());
        final ErrorResponse errorResponse = new ErrorResponse(commonError);

        return Mono.just(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ErrorResponse> handleGeneralException(
            final Exception e,
            final ServerWebExchange exchange
    ) {
        final ServerHttpRequest request = exchange.getRequest();

        final URI uri = request.getURI();
        final HttpMethod method = request.getMethod();
        final String message = e.getMessage();
        log.error("[General exception occurred] Request URI: {}, Method: {}, Error: {}", uri, method, message, e);

        final CommonError commonError = new CommonError("500", e.getMessage());
        final ErrorResponse errorResponse = new ErrorResponse(commonError);

        return Mono.just(errorResponse);
    }
}
