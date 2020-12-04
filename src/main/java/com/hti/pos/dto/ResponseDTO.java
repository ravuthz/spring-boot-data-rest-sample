package com.hti.pos.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Created by ravuthz
 * Date : 12/4/2020, 1:45 PM
 * Email : ravuthz@gmail.com, yovannaravuth@gmail.com
 */

@Data
@Builder
public class ResponseDTO<T> {
    @Builder.Default
    private String status = HttpStatus.OK.toString();
    private T message;
    private T content;
}
