package com.hti.pos.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Created by ravuthz
 * Date : 12/4/2020, 2:59 PM
 * Email : ravuthz@gmail.com, yovannaravuth@gmail.com
 */

@Data
@Builder
public class FieldDTO {
    private String field;
    private String message;
}
