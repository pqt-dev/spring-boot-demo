package com.demo.spring_boot.exception;

import com.demo.spring_boot.response.ApiResponse;
import com.demo.spring_boot.response.DetailsErrorResponse;
import com.google.api.gax.rpc.UnauthenticatedException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<DetailsErrorResponse>> handleValidationErrors(
            MethodArgumentNotValidException ex) {
        final List<String> errorMessages = new ArrayList<>();
        ex.getBindingResult()
          .getAllErrors()
          .forEach(error -> {
              String message = error.getDefaultMessage();
              errorMessages.add(message);
          });
        final var response = ApiResponse.<DetailsErrorResponse>builder()
                                        .data(DetailsErrorResponse.builder()
                                                                  .details(errorMessages)
                                                                  .build())
                                        .code(ErrorType.VALIDATION_ERROR.getCode())
                                        .message(ex.getMessage())
                                        .build();
        return ResponseEntity.badRequest()
                             .body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<DetailsErrorResponse>> handleResourceNotFound(ResourceNotFoundException ex) {
        final var response = ApiResponse.<DetailsErrorResponse>builder()
                                        .data(DetailsErrorResponse.builder()
                                                                  .details(List.of(ex.getMessage()))
                                                                  .build())
                                        .code(ErrorType.RESOURCE_NOT_FOUND.getCode())
                                        .message(ex.getMessage())
                                        .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<DetailsErrorResponse>> handleBadCredentialsException(BadCredentialsException ex) {
        final var response = ApiResponse.<DetailsErrorResponse>builder()
                                        .data(DetailsErrorResponse.builder()
                                                                  .details(
                                                                          List.of(ErrorType.AUTH_INVALID_CREDENTIALS.getDetails()))
                                                                  .build())
                                        .code(ErrorType.AUTH_INVALID_CREDENTIALS.getCode())
                                        .message(ex.getMessage())
                                        .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(response);
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseEntity<ApiResponse<DetailsErrorResponse>> handleUnauthenticatedException(
            UnauthenticatedException ex) {
        final var response = ApiResponse.<DetailsErrorResponse>builder()
                                        .data(DetailsErrorResponse.builder()
                                                                  .details(
                                                                          List.of(ErrorType.AUTH_FORBIDDEN.getDetails()))
                                                                  .build())
                                        .code(ErrorType.AUTH_FORBIDDEN.getCode())
                                        .message(ex.getMessage())
                                        .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(response);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<DetailsErrorResponse>> handleMaxUploadSizeExceededException(
            MaxUploadSizeExceededException ex) {
        final var cause = ex.getCause()
                            .getCause();
        if (cause instanceof FileSizeLimitExceededException) {
            final var response = ApiResponse.<DetailsErrorResponse>builder()
                                            .data(DetailsErrorResponse.builder()
                                                                      .details(
                                                                              List.of(ErrorType.FILE_SIZE_LIMIT.getDetails()))
                                                                      .build())
                                            .code(ErrorType.FILE_SIZE_LIMIT.getCode())
                                            .message(ex.getMessage())
                                            .build();
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE.value())
                                 .body(response);
        }
        final var response = ApiResponse.<DetailsErrorResponse>builder()
                                        .data(DetailsErrorResponse.builder()
                                                                  .details(
                                                                          List.of(ErrorType.REQUEST_SIZE_LIMIT.getDetails()))
                                                                  .build())
                                        .code(ErrorType.REQUEST_SIZE_LIMIT.getCode())
                                        .message(ex.getMessage())
                                        .build();
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE.value())
                             .body(response);
    }

    @ExceptionHandler(ImageTypeException.class)
    public ResponseEntity<ApiResponse<DetailsErrorResponse>> handleImageTypeException(ImageTypeException ex) {
        final var response = ApiResponse.<DetailsErrorResponse>builder()
                                        .data(DetailsErrorResponse.builder()
                                                                  .details(
                                                                          List.of(ErrorType.FILE_TYPE_INVALID.getDetails()))
                                                                  .build())
                                        .code(ErrorType.FILE_TYPE_INVALID.getCode())
                                        .message(ex.getMessage())
                                        .build();
        return ResponseEntity.badRequest()
                             .body(response);
    }
}
