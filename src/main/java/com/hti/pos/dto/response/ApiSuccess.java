package com.hti.pos.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter
@Getter
public class ApiSuccess<T> {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private int code;
    private HttpStatus status;
    private String message;
    private T data;

    private ApiSuccess() {
        timestamp = LocalDateTime.now();
    }

    public ApiSuccess(HttpStatus status) {
        this();
        this.code = status.value();
        this.status = status;
    }

    public ApiSuccess(HttpStatus status, T data) {
        this();
        this.code = status.value();
        this.status = status;
        this.message = "";
        this.data = data;
    }

    public ApiSuccess(HttpStatus status, String message) {
        this();
        this.code = status.value();
        this.status = status;
        this.message = message;
    }

    public ApiSuccess(HttpStatus status, String message, Throwable ex) {
        this();
        this.code = status.value();
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
