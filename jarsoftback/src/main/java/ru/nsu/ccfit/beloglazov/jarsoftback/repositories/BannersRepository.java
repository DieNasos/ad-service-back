package ru.nsu.ccfit.beloglazov.jarsoftback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.ccfit.beloglazov.jarsoftback.entities.Banner;
import java.util.List;

public interface BannersRepository extends JpaRepository<Banner, Integer> {
    Banner findByName(String name);
    List<Banner> findByNameContaining(String name);
    List<Banner> findByCategory_id(Integer id);
}