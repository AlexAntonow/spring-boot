package com.springboot.demo.exception;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldExceptionMessage {
    private String field;
    private String message;
}
