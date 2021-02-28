package ru.nsu.ccfit.beloglazov.jarsoftback.dto;

import lombok.Data;
import java.sql.Date;

@Data
public class RequestDTO {
    private Integer id;
    private Integer bannerID;
    private String userAgent;
    private String ipAddress;
    private Date date;
}