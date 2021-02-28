package ru.nsu.ccfit.beloglazov.jarsoftback.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BannerDTO {
    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer category_id;
    private String content;
    private Boolean deleted;
}