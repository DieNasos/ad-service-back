package ru.nsu.ccfit.beloglazov.jarsoftback.converters;

import org.springframework.stereotype.Component;
import ru.nsu.ccfit.beloglazov.jarsoftback.dto.CategoryDTO;
import ru.nsu.ccfit.beloglazov.jarsoftback.entities.Category;

@Component
public class CategoriesConverter {
    public Category dtoToCategory(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setReqname(dto.getReqname());
        category.setDeleted(dto.getDeleted());
        return category;
    }

    public CategoryDTO categoryToDTO(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setReqname(category.getReqname());
        dto.setDeleted(category.getDeleted());
        return dto;
    }
}