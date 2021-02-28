package ru.nsu.ccfit.beloglazov.jarsoftback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.ccfit.beloglazov.jarsoftback.entities.Category;
import java.util.List;

public interface CategoriesRepository extends JpaRepository<Category, Integer> {
    Category findByName(String name);
    List<Category> findByNameContaining(String name);
    Category findByReqname(String reqname);
}