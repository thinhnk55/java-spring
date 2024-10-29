package work.vietdefi.spring.common.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.NoHandlerFoundException;
import work.vietdefi.spring.common.dto.BaseResponse;
import work.vietdefi.spring.common.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodNotAllowedException.class)
    public BaseResponse<ErrorResponse> handleMethodNotAllowed(MethodNotAllowedException exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), "method_not_allowed");
        BaseResponse<ErrorResponse> response = new BaseResponse<>(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                "method_not_allowed",
                errorResponse
        );
        return response;
    }
    @ExceptionHandler(NoHandlerFoundException.class)
    public BaseResponse<ErrorResponse> handleNotFound(NoHandlerFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "handler_not_found");
        BaseResponse<ErrorResponse> response = new BaseResponse<>(
                HttpStatus.NOT_FOUND.value(),
                "handler_not_found",
                errorResponse
        );
        return response;
    }
    @ExceptionHandler(AuthenticationException.class)
    public BaseResponse<ErrorResponse> handleUnauthorized(AuthenticationException exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "unauthorized");
        BaseResponse<ErrorResponse> response = new BaseResponse<>(
                HttpStatus.UNAUTHORIZED.value(),
                "unauthorized",
                errorResponse
        );
        return response;
    }
    @ExceptionHandler(Exception.class)
    public BaseResponse<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(1, "system_error");
        BaseResponse<ErrorResponse> response = new BaseResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "system_error",
                errorResponse
        );
        return response;
    }
}
