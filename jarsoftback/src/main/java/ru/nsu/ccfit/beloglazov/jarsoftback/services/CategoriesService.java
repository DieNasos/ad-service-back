package ru.nsu.ccfit.beloglazov.jarsoftback.services;

import ru.nsu.ccfit.beloglazov.jarsoftback.dto.CategoryDTO;
import ru.nsu.ccfit.beloglazov.jarsoftback.exceptions.*;
import java.util.List;

public interface CategoriesService {
    CategoryDTO saveCategory(CategoryDTO dto) throws ValidationException, NullParamException, NullDTOException;
    void deleteCategory(Integer id) throws ItemsAssociatedException;
    CategoryDTO findByName(String name);
    List<CategoryDTO> findByNameContaining(String name);
    CategoryDTO findByReqName(String reqName);
    List<CategoryDTO> findAll();
}