package ru.nsu.ccfit.beloglazov.jarsoftback.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.nsu.ccfit.beloglazov.jarsoftback.dto.BannerDTO;
import ru.nsu.ccfit.beloglazov.jarsoftback.exceptions.*;
import ru.nsu.ccfit.beloglazov.jarsoftback.services.BannersService;
import java.util.List;

@RestController
@RequestMapping("/banners")
@AllArgsConstructor
@Log
public class BannersController {
    private final BannersService service;

    @PostMapping("/save")
    public BannerDTO saveBanner(@RequestBody BannerDTO dto) {
        log.info("Banners :: handling :: save :: " + dto);
        try {
            return service.saveBanner(dto);
        } catch (ValidationException e) {
            log.info("Error :: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (NullParamException | NullDTOException | InvalidIDException e) {
            log.info("Error :: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/findAll")
    public List<BannerDTO> findAllBanners() {
        log.info("Banners :: handling :: find all");
        return service.findAll();
    }

    @GetMapping("/findByName")
    public List<BannerDTO> findByName(@RequestParam String name) {
        log.info("Banners :: handling :: find by name like :: " + name);
        return service.findByNameContaining(name);
    }

    @GetMapping("/findByCategoryID/{id}")
    public List<BannerDTO> findByCategoryID(@PathVariable Integer id) {
        log.info("Banners :: handling :: find by category id :: " + id);
        return service.findByCategoryID(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Integer id) {
        log.info("Banners :: handling :: delete :: " + id);
        service.deleteBanner(id);
        return ResponseEntity.ok().build();
    }
}