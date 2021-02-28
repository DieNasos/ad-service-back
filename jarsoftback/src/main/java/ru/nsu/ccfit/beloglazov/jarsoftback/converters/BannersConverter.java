package ru.nsu.ccfit.beloglazov.jarsoftback.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.beloglazov.jarsoftback.dto.BannerDTO;
import ru.nsu.ccfit.beloglazov.jarsoftback.entities.*;
import ru.nsu.ccfit.beloglazov.jarsoftback.exceptions.InvalidIDException;
import ru.nsu.ccfit.beloglazov.jarsoftback.repositories.CategoriesRepository;
import java.util.Optional;

@AllArgsConstructor
@Component
public class BannersConverter {
    private final CategoriesRepository categoriesRepository;

    public Banner dtoToBanner(BannerDTO dto) throws InvalidIDException {
        if (dto == null) {
            return null;
        }
        Optional<Category> category = categoriesRepository.findById(dto.getCategory_id());
        if (category.isEmpty()) {
            throw new InvalidIDException("Category with id " + dto.getCategory_id() + " not found");
        }
        Banner banner = new Banner();
        banner.setId(dto.getId());
        banner.setName(dto.getName());
        banner.setPrice(dto.getPrice());
        banner.setCategory(category.get());
        banner.setContent(dto.getContent());
        banner.setDeleted(dto.getDeleted());
        return banner;
    }

    public BannerDTO bannerToDTO(Banner banner) {
        if (banner == null) {
            return null;
        }
        BannerDTO dto = new BannerDTO();
        dto.setId(banner.getId());
        dto.setName(banner.getName());
        dto.setPrice(banner.getPrice());
        dto.setCategory_id(banner.getCategory().getId());
        dto.setContent(banner.getContent());
        dto.setDeleted(banner.getDeleted());
        return dto;
    }
}