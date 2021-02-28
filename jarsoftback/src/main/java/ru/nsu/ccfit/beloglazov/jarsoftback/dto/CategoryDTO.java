package ru.nsu.ccfit.beloglazov.jarsoftback.dto;

import lombok.Data;

@Data
public class CategoryDTO {
    private Integer id;
    private String name;
    private String reqname;
    private Boolean deleted;
}