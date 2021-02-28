package ru.nsu.ccfit.beloglazov.jarsoftback.services;

import ru.nsu.ccfit.beloglazov.jarsoftback.dto.BannerDTO;
import ru.nsu.ccfit.beloglazov.jarsoftback.dto.RequestDTO;
import ru.nsu.ccfit.beloglazov.jarsoftback.exceptions.InvalidIDException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface RequestsService {
    RequestDTO saveRequest(RequestDTO dto) throws InvalidIDException;
    BannerDTO findBestBannerForCategory(String categoryName, HttpServletRequest request) throws InvalidIDException;
    List<RequestDTO> findByIPAddress(String ip);
}