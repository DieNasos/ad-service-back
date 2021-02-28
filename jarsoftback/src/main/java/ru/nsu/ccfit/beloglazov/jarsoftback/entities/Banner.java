package ru.nsu.ccfit.beloglazov.jarsoftback.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "banners")
@Data
@NoArgsConstructor
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(precision = 8, scale = 2, nullable = false)
    private BigDecimal price;

    @ManyToOne
    private Category category;

    @Type(type = "text")
    @Column(nullable = false)
    private String content;

    @Column(columnDefinition = "BIT(1)")
    private Boolean deleted;

    @OneToMany(mappedBy = "banner")
    private List<Request> requests;
}