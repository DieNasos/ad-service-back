package ru.nsu.ccfit.beloglazov.jarsoftback.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "requests")
@Data
@NoArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Banner banner;

    @Type(type = "text")
    @Column(nullable = false)
    private String userAgent;

    @Column(nullable = false)
    private String ipAddress;

    @Column(nullable = false)
    private Date date;
}