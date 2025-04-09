package com.quocdoansam.walletx.exception;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.quocdoansam.walletx.dto.response.ApiResponse;
import com.quocdoansam.walletx.dto.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<BaseResponse<Object>> handleGenericException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.builder()
                        .success(false)
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("An unexpected error occurred.")
                        .data(null).build());
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<Object>> handleAppException(AppException exception) {
        log.error("AppException occurred: {}", exception.getMessage(), exception);

        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = BaseException.class)
    ResponseEntity<BaseResponse<Object>> handlingBaseException(BaseException exception) {
        return ResponseEntity.status(exception.getStatus()).body(
                BaseResponse.builder()
                        .success(false)
                        .statusCode(exception.getStatus().value())
                        .message(exception.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Object>> handlingValidation(MethodArgumentNotValidException exception) {

        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
        FieldError fieldError = exception.getFieldError();

        if (fieldError != null) {
            String enumKey = fieldError.getDefaultMessage();
            errorCode = ErrorCode.valueOf(enumKey);
        }

        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

}
