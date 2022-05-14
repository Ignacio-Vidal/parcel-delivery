package com.staffs.enterprise.software.engineering.parceldelivery.controller;

import com.staffs.enterprise.software.engineering.parceldelivery.domain.AppUser;
import com.staffs.enterprise.software.engineering.parceldelivery.domain.Parcel;
import com.staffs.enterprise.software.engineering.parceldelivery.dto.parcel.BaseParcelDTO;
import com.staffs.enterprise.software.engineering.parceldelivery.dto.parcel.RegisterParcelDTO;
import com.staffs.enterprise.software.engineering.parceldelivery.dto.updateParcelAction.BaseUpdateParcelAction;
import com.staffs.enterprise.software.engineering.parceldelivery.dto.parcel.ParcelResponseDTO;
import com.staffs.enterprise.software.engineering.parceldelivery.exceptions.NotFoundException;
import com.staffs.enterprise.software.engineering.parceldelivery.mapper.ParcelMapper;
import com.staffs.enterprise.software.engineering.parceldelivery.service.ParcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parcels")
public class ParcelController {
    private final ParcelService parcelService;
    private final ParcelMapper parcelMapper;
    private final Logger log = LoggerFactory.getLogger(ParcelController.class);

    public ParcelController(ParcelService parcelService, ParcelMapper parcelMapper) {
        this.parcelService = parcelService;
        this.parcelMapper = parcelMapper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParcelResponseDTO> getParcels(@Param("status") String status) {
        return parcelService.getParcelsByStatus(status).stream().map(parcelMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping(value = "/{parcelId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ParcelResponseDTO getParcelById(@PathVariable String parcelId) {
        return parcelService.getParcelById(parcelId).map(parcelMapper::toDTO).orElseThrow(() -> new NotFoundException("Parcel not found"));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String registerParcel(@Valid @RequestBody RegisterParcelDTO dto, HttpServletRequest request) {
        AppUser user = (AppUser) request.getAttribute("user");
        Parcel parcel = parcelMapper.toParcel(dto, user);
        String parcelUuid = parcelService.registerParcel(parcel);
        log.info("Parcel created with uuid={}", parcelUuid);
        return parcelUuid;
    }

    @PostMapping(value = "/{parcelId}:updateAction", produces = MediaType.APPLICATION_JSON_VALUE)
    public ParcelResponseDTO updateParcel(@Valid @PathVariable String parcelId, @RequestBody BaseUpdateParcelAction dto, HttpServletRequest request) {
        dto.setParcelUuid(parcelId);
        Parcel updatedParcel = parcelService.updateParcel(dto);
        return parcelMapper.toDTO(updatedParcel);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ParcelResponseDTO deleteParcel(@PathVariable("id") String id) {
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
