package ru.nsu.ccfit.beloglazov.jarsoftback.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.ccfit.beloglazov.jarsoftback.converters.BannersConverter;
import ru.nsu.ccfit.beloglazov.jarsoftback.dto.BannerDTO;
import ru.nsu.ccfit.beloglazov.jarsoftback.entities.Banner;
import ru.nsu.ccfit.beloglazov.jarsoftback.exceptions.*;
import ru.nsu.ccfit.beloglazov.jarsoftback.repositories.BannersRepository;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DefaultBannersService implements BannersService {
    private final BannersRepository repository;
    private final BannersConverter converter;

    @Override
    public BannerDTO saveBanner(BannerDTO dto) throws ValidationException, InvalidIDException, NullDTOException, NullParamException {
        validateBannerDTO(dto);
        Banner savedBanner = repository.save(converter.dtoToBanner(dto));
        return converter.bannerToDTO(savedBanner);
    }

    @Override
    public void deleteBanner(Integer id) {
        Optional<Banner> banner = repository.findById(id);
        if (banner.isPresent()) {
            Banner oldBanner = banner.get();
            Banner newBanner = new Banner();
            newBanner.setId(oldBanner.getId());
            newBanner.setName(oldBanner.getName());
            newBanner.setPrice(oldBanner.getPrice());
            newBanner.setCategory(oldBanner.getCategory());
            newBanner.setContent(oldBanner.getContent());
            newBanner.setDeleted(true);
            repository.save(newBanner);
        }
    }

    @Override
    public BannerDTO findByName(String name) {
        Banner banner = repository.findByName(name);
        return converter.bannerToDTO(banner);
    }

    @Override
    public List<BannerDTO> findByNameContaining(String name) {
        return repository.findByNameContaining(name)
                .stream()
                .map(converter::bannerToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BannerDTO> findByCategoryID(Integer id) {
        List<Banner> banners = repository.findByCategory_id(id);
        List<BannerDTO> DTOs = new LinkedList<>();
        if (banners != null) {
            for (Banner banner : banners) {
                DTOs.add(converter.bannerToDTO(banner));
            }
            return DTOs;
        }
        return null;
    }

    @Override
    public List<BannerDTO> findAll() {
        return repository
                .findAll()
                .stream()
                .map(converter::bannerToDTO)
                .collect(Collectors.toList());
    }

    private void validateBannerDTO(BannerDTO bannerDTO) throws ValidationException, NullParamException, NullDTOException {
        if (bannerDTO == null) {
            throw new NullDTOException("Banner DTO is null");
        }
        if (bannerDTO.getName() == null || bannerDTO.getName().isEmpty()) {
            throw new NullParamException("Name in banner is empty");
        }
        if (bannerDTO.getPrice() == null) {
            throw new NullParamException("Banner has no price");
        }
        if (bannerDTO.getCategory_id() == null) {
            throw new NullParamException("Banner has no category");
        }
        if (bannerDTO.getContent() == null) {
            throw new NullParamException("Banner has no content");
        }
        BannerDTO withName = findByName(bannerDTO.getName());
        if (withName != null && !withName.getId().equals(bannerDTO.getId())) {
            throw new ValidationException("Banner with name '" + bannerDTO.getName() + "' already exists in database");
        }
    }
}