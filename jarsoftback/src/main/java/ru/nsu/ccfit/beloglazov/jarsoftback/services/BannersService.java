package ru.nsu.ccfit.beloglazov.jarsoftback.services;

import ru.nsu.ccfit.beloglazov.jarsoftback.dto.BannerDTO;
import ru.nsu.ccfit.beloglazov.jarsoftback.exceptions.*;
import java.util.List;

public interface BannersService {
    BannerDTO saveBanner(BannerDTO dto) throws ValidationException, InvalidIDException, NullDTOException, NullParamException;
    void deleteBanner(Integer id);
    BannerDTO findByName(String name);
    List<BannerDTO> findByNameContaining(String name);
    List<BannerDTO> findByCategoryID(Integer id);
    List<BannerDTO> findAll();
}