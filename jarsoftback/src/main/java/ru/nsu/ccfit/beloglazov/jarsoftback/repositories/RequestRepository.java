package ru.nsu.ccfit.beloglazov.jarsoftback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.ccfit.beloglazov.jarsoftback.entities.Request;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findByIpAddress(String ip);
}