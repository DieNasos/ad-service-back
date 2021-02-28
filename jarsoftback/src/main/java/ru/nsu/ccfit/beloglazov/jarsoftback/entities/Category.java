package ru.nsu.ccfit.beloglazov.jarsoftback.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String reqname;

    @Column(columnDefinition = "BIT(1)")
    private Boolean deleted;

    @OneToMany(mappedBy = "category")
    private List<Banner> banners;
}