package org.tms.finalproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MassageDto {
    private long orderAuthorId; // hidden field in form
    private long orderExecutorId; // hidden field in form
    private long massageAuthorId; // hidden field in form

    private String text;
}
