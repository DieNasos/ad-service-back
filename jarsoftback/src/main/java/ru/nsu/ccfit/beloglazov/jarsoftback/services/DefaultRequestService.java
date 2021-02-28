package ru.nsu.ccfit.beloglazov.jarsoftback.services;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import ru.nsu.ccfit.beloglazov.jarsoftback.converters.RequestsConverter;
import ru.nsu.ccfit.beloglazov.jarsoftback.dto.*;
import ru.nsu.ccfit.beloglazov.jarsoftback.entities.Request;
import ru.nsu.ccfit.beloglazov.jarsoftback.exceptions.InvalidIDException;
import ru.nsu.ccfit.beloglazov.jarsoftback.repositories.RequestRepository;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.*;

@AllArgsConstructor
@Service
@Log
public class DefaultRequestService implements RequestsService {
    private final CategoriesService categoriesService;
    private final BannersService bannersService;
    private final RequestRepository repository;
    private final RequestsConverter converter;

    @Override
    public RequestDTO saveRequest(RequestDTO dto) throws InvalidIDException {
        Request savedRequest = repository.save(converter.dtoToRequest(dto));
        return converter.requestToDTO(savedRequest);
    }

    @Override
    public BannerDTO findBestBannerForCategory(String categoryName, HttpServletRequest request) throws InvalidIDException {
        CategoryDTO categoryDTO = categoriesService.findByReqName(categoryName);
        if (categoryDTO == null) {
            return null;
        }

        List<BannerDTO> bannerDTOs = bannersService.findByCategoryID(categoryDTO.getId());
        if (bannerDTOs == null) {
            return null;
        }

        String userAgent = request.getHeader("User-Agent");
        String ip = request.getRemoteAddr();
        Date currentDate = new Date(Calendar.getInstance().getTime().getTime());

        List<RequestDTO> requestDTOs = findByIPAddress(ip);
        requestDTOs.removeIf(dto -> !(dto.getUserAgent().equals(userAgent) || dto.getDate().equals(currentDate)));

        Set<Integer> shownBannersIDs = new HashSet<>();
        for (RequestDTO dto : requestDTOs) {
            shownBannersIDs.add(dto.getBannerID());
        }

        bannerDTOs.removeIf(dto -> (shownBannersIDs.contains(dto.getId()) || dto.getDeleted()));
        if (bannerDTOs.isEmpty()) {
            return null;
        }

        BannerDTO best = bannerDTOs.get(0);
        for (BannerDTO bannerDTO : bannerDTOs) {
            if (bannerDTO.getPrice().compareTo(best.getPrice()) > 0) {
                best = bannerDTO;
            }
        }

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setBannerID(best.getId());
        requestDTO.setUserAgent(userAgent);
        requestDTO.setIpAddress(ip);
        requestDTO.setDate(new Date(Calendar.getInstance().getTime().getTime()));
        saveRequest(requestDTO);

        return best;
    }

    @Override
    public List<RequestDTO> findByIPAddress(String ip) {
        List<Request> requests = repository.findByIpAddress(ip);
        List<RequestDTO> DTOs = new LinkedList<>();
        if (requests != null) {
            for (Request request : requests) {
                DTOs.add(converter.requestToDTO(request));
            }
            return DTOs;
        }
        return null;
    }
}