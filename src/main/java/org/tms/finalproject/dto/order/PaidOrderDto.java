package org.tms.finalproject.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaidOrderDto extends OrderDto {

    @Positive(message = "It's required field!")
    private double price;
//    private LocalDateTime orderDeadlineDateTime;


    public PaidOrderDto(@NotEmpty(message = "It's required field!") @NotBlank(message = "It's required field!") String title,
                        String description,
                        @NotEmpty(message = "It's required field!") @NotBlank(message = "It's required field!") String locationCity,
                        @NotEmpty(message = "It's required field!") @NotBlank(message = "It's required field!") String locationStreet,
                        @NotEmpty(message = "It's required field!") @NotBlank(message = "It's required field!") int locationHome,
                        int locationFlat,
                        @NotEmpty(message = "It's required field!") @NotBlank(message = "It's required field!") double price) {
        super(title, description, locationCity, locationStreet, locationHome, locationFlat);
        this.price = price;
    }
}
