package org.tms.finalproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class CommentDto {
    private long commentOwnerId; // hidden field in form
    private String pagLink; // hidden field in form

    private String title;
    private String description;
    private double rating;
}
