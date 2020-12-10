package org.tms.finalproject.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterOrderDto {

    private String locationCity;
    private String locationStreet;

    private String orderType;

    private double fromPrice;
    private double toPrice;

}
