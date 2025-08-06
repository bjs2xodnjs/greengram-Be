package com.green.greengram.config.exception;

import lombok.*;
import org.springframework.validation.FieldError;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ValidationError {
    private String field;
    private String message;



    public static ValidationError of/*static 메소드(리턴타입, 위에 객체화)*/(final FieldError fieldError) {
        return ValidationError.builder()
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .build();
    }

    @Override
    public String toString() {
        return String.format("field: %s, message: %s", field, message);
    }
}
