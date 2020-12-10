package org.tms.finalproject.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class OrderDto {
    @NotEmpty(message = "It's required field!")
    @NotBlank(message = "It's required field!")
    private String title;
    private String description;

    @NotEmpty(message = "It's required field!")
    @NotBlank(message = "It's required field!")
    private String locationCity;
    @NotEmpty(message = "It's required field!")
    @NotBlank(message = "It's required field!")
    private String locationStreet;
    @Positive(message = "It's required field!")
    private int locationHome;
    private int locationFlat;
}
