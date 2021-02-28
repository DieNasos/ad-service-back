package ru.nsu.ccfit.beloglazov.jarsoftback.services;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import ru.nsu.ccfit.beloglazov.jarsoftback.converters.CategoriesConverter;
import ru.nsu.ccfit.beloglazov.jarsoftback.dto.*;
import ru.nsu.ccfit.beloglazov.jarsoftback.entities.Category;
import ru.nsu.ccfit.beloglazov.jarsoftback.exceptions.*;
import ru.nsu.ccfit.beloglazov.jarsoftback.repositories.CategoriesRepository;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Log
public class DefaultCategoriesService implements CategoriesService {
    private final BannersService bannersService;
    private final CategoriesRepository categoriesRepository;
    private final CategoriesConverter categoriesConverter;

    @Override
    public CategoryDTO saveCategory(CategoryDTO dto) throws ValidationException, NullParamException, NullDTOException {
        validateCategoryDTO(dto);
        Category savedCategory = categoriesRepository.save(categoriesConverter.dtoToCategory(dto));
        return categoriesConverter.categoryToDTO(savedCategory);
    }

    @Override
    public void deleteCategory(Integer id) throws ItemsAssociatedException {
        List<BannerDTO> bannersAssociated = bannersService.findByCategoryID(id);
        if (bannersAssociated != null) {
            throw new ItemsAssociatedException("Could not delete category with banners associated");
        }
        Optional<Category> category = categoriesRepository.findById(id);
        if (category.isPresent()) {
            Category oldCategory = category.get();
            Category newCategory = new Category();
            newCategory.setId(oldCategory.getId());
            newCategory.setName(oldCategory.getName());
            newCategory.setReqname(oldCategory.getReqname());
            newCategory.setDeleted(true);
            categoriesRepository.save(newCategory);
        }
    }

    @Override
    public CategoryDTO findByName(String name) {
        Category category = categoriesRepository.findByName(name);
        return categoriesConverter.categoryToDTO(category);
    }

    @Override
    public List<CategoryDTO> findByNameContaining(String name) {
        return categoriesRepository.findByNameContaining(name)
                .stream()
                .map(categoriesConverter::categoryToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findByReqName(String reqName) {
        Category category = categoriesRepository.findByReqname(reqName);
        return categoriesConverter.categoryToDTO(category);
    }

    @Override
    public List<CategoryDTO> findAll() {
        return categoriesRepository
                .findAll()
                .stream()
                .map(categoriesConverter::categoryToDTO)
                .collect(Collectors.toList());
    }

    private void validateCategoryDTO(CategoryDTO categoryDTO) throws ValidationException, NullParamException, NullDTOException {
        if (categoryDTO == null) {
            throw new NullDTOException("Category DTO is null");
        }
        if (categoryDTO.getName() == null || categoryDTO.getName().isEmpty()) {
            throw new NullParamException("Name in category is empty");
        }
        if (categoryDTO.getReqname() == null || categoryDTO.getReqname().isEmpty()) {
            throw new NullParamException("Request name in category is empty");
        }
        CategoryDTO withName = findByName(categoryDTO.getName());
        if (withName != null && !withName.getId().equals(categoryDTO.getId())) {
            throw new ValidationException("Category with name '" + categoryDTO.getName() + "' already exists in database");
        }
        CategoryDTO withReqName = findByReqName(categoryDTO.getReqname());
        if (withReqName != null && !withReqName.getId().equals(categoryDTO.getId())) {
            throw new ValidationException("Category with request name '" + categoryDTO.getReqname() + "' already exists in database");
        }
    }
}