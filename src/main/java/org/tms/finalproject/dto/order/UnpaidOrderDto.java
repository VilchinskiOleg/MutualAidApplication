package org.tms.finalproject.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class UnpaidOrderDto extends OrderDto {
    @NotEmpty(message = "It's required field!")
    @NotBlank(message = "It's required field!")
    private String priority;
}
