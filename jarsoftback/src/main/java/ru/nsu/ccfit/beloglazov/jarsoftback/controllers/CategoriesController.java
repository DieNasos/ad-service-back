package ru.nsu.ccfit.beloglazov.jarsoftback.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.nsu.ccfit.beloglazov.jarsoftback.dto.*;
import ru.nsu.ccfit.beloglazov.jarsoftback.exceptions.*;
import ru.nsu.ccfit.beloglazov.jarsoftback.services.*;
import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
@Log
public class CategoriesController {
    private final CategoriesService categoriesService;

    @PostMapping("/save")
    public CategoryDTO saveCategory(@RequestBody CategoryDTO dto) {
        log.info("Categories :: handling :: save :: " + dto);
        try {
            return categoriesService.saveCategory(dto);
        } catch (ValidationException e) {
            log.info("Error :: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (NullDTOException | NullParamException e) {
            log.info("Error :: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/findAll")
    public List<CategoryDTO> findAllCategories() {
        log.info("Categories :: handling :: find all");
        return categoriesService.findAll();
    }

    @GetMapping("/findByName")
    public List<CategoryDTO> findByName(@RequestParam String name) {
        log.info("Categories :: handling :: find by name like :: " + name);
        return categoriesService.findByNameContaining(name);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        log.info("Categories :: handling :: delete :: " + id);
        try {
            categoriesService.deleteCategory(id);
        } catch (ItemsAssociatedException e) {
            log.info("Error :: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        }
        return ResponseEntity.ok().build();
    }
}