package ru.nsu.ccfit.beloglazov.jarsoftback.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.beloglazov.jarsoftback.dto.RequestDTO;
import ru.nsu.ccfit.beloglazov.jarsoftback.entities.*;
import ru.nsu.ccfit.beloglazov.jarsoftback.exceptions.InvalidIDException;
import ru.nsu.ccfit.beloglazov.jarsoftback.repositories.BannersRepository;
import java.util.Optional;

@AllArgsConstructor
@Component
public class RequestsConverter {
    private final BannersRepository bannersRepository;

    public Request dtoToRequest(RequestDTO dto) throws InvalidIDException {
        if (dto == null) {
            return null;
        }
        Optional<Banner> banner = bannersRepository.findById(dto.getBannerID());
        if (banner.isEmpty()) {
            throw new InvalidIDException("Banner with id " + dto.getBannerID() + " not found");
        }
        Request request = new Request();
        request.setId(dto.getId());
        request.setBanner(banner.get());
        request.setUserAgent(dto.getUserAgent());
        request.setIpAddress(dto.getIpAddress());
        request.setDate(dto.getDate());
        return request;
    }

    public RequestDTO requestToDTO(Request request) {
        if (request == null) {
            return null;
        }
        RequestDTO dto = new RequestDTO();
        dto.setId(request.getId());
        dto.setBannerID(request.getBanner().getId());
        dto.setUserAgent(request.getUserAgent());
        dto.setIpAddress(request.getIpAddress());
        dto.setDate(request.getDate());
        return dto;
    }
}