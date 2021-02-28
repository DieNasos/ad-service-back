package ru.nsu.ccfit.beloglazov.jarsoftback.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.nsu.ccfit.beloglazov.jarsoftback.dto.BannerDTO;
import ru.nsu.ccfit.beloglazov.jarsoftback.exceptions.InvalidIDException;
import ru.nsu.ccfit.beloglazov.jarsoftback.services.RequestsService;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
@AllArgsConstructor
@Log
public class RequestsController {
    private final RequestsService requestsService;

    @GetMapping("/bid")
    public String findBestBannerForCategory(@RequestParam String category, HttpServletRequest request) {
        log.info("Requests :: handling :: find best banner for category :: " + category);
        BannerDTO best = null;
        try {
             best = requestsService.findBestBannerForCategory(category, request);
        } catch (InvalidIDException e) {
            log.info("Error :: " + e.getMessage());
        }
        if (best == null) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, "No banners available", null);
        } else {
            return best.getContent();
        }
    }
}