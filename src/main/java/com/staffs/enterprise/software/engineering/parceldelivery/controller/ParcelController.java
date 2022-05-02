package com.staffs.enterprise.software.engineering.parceldelivery.controller;

import com.staffs.enterprise.software.engineering.parceldelivery.domain.AppUser;
import com.staffs.enterprise.software.engineering.parceldelivery.domain.Parcel;
import com.staffs.enterprise.software.engineering.parceldelivery.dto.CreateParcelDTO;
import com.staffs.enterprise.software.engineering.parceldelivery.dto.ParcelDTO;
import com.staffs.enterprise.software.engineering.parceldelivery.exceptions.NotFoundException;
import com.staffs.enterprise.software.engineering.parceldelivery.mapper.ParcelMapper;
import com.staffs.enterprise.software.engineering.parceldelivery.service.ParcelService;
import com.staffs.enterprise.software.engineering.parceldelivery.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parcels")
public class ParcelController {
    private final ParcelService parcelService;
    private final ParcelMapper parcelMapper;
    private final UserService userService;
    private final Logger log = LoggerFactory.getLogger(ParcelController.class);

    public ParcelController(ParcelService parcelService, ParcelMapper parcelMapper, UserService userService) {
        this.parcelService = parcelService;
        this.parcelMapper = parcelMapper;
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParcelDTO> getParcels(@Param("status") String status) {
        return parcelService.getParcelsByStatus(status).stream().map(parcelMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping(value = "/{parcelId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ParcelDTO getParcelById(@PathVariable String parcelId) {
        return parcelService.getParcelById(parcelId).map(parcelMapper::toDTO).orElseThrow(() -> new NotFoundException("Parcel not found"));
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createParcel(@RequestBody CreateParcelDTO dto, HttpServletRequest request) {
        AppUser user = (AppUser) request.getAttribute("user");
        Parcel parcel = parcelMapper.toParcel(dto, user);
        String parcelUuid = parcelService.registerParcel(parcel);
        log.info("Parcel created with uuid={}", parcelUuid);
        return parcelUuid;
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ParcelDTO updateParcel(@PathVariable("id") String id, @RequestBody ParcelDTO dto, HttpServletRequest request) {
        Parcel parcel = parcelMapper.toParcel(dto, (AppUser) request.getAttribute("user"));
        Parcel updatedParcel = parcelService.updateParcel(parcel);
        return parcelMapper.toDTO(updatedParcel);
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ParcelDTO deleteParcel(@PathVariable("id") String id) {
        Parcel deletedParcel = parcelService.deleteParcel(id);
        return parcelMapper.toDTO(deletedParcel);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
