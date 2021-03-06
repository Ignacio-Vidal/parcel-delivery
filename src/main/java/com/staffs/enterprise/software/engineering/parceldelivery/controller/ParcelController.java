package com.staffs.enterprise.software.engineering.parceldelivery.controller;

import com.staffs.enterprise.software.engineering.parceldelivery.domain.AppUser;
import com.staffs.enterprise.software.engineering.parceldelivery.domain.Parcel;
import com.staffs.enterprise.software.engineering.parceldelivery.domain.ParcelUpdateAction;
import com.staffs.enterprise.software.engineering.parceldelivery.domain.UpdateActions;
import com.staffs.enterprise.software.engineering.parceldelivery.dto.parcel.RegisterParcelDTO;
import com.staffs.enterprise.software.engineering.parceldelivery.dto.updateParcelAction.ParcelUpdateActionDTO;
import com.staffs.enterprise.software.engineering.parceldelivery.dto.parcel.ParcelResponseDTO;
import com.staffs.enterprise.software.engineering.parceldelivery.exceptions.NotFoundException;
import com.staffs.enterprise.software.engineering.parceldelivery.dto.mapper.ParcelMapper;
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

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String registerParcel(@Valid @RequestBody RegisterParcelDTO dto, HttpServletRequest request) {
        AppUser user = (AppUser) request.getAttribute("user");
        Parcel parcel = parcelMapper.toParcel(dto, user);
        String parcelUuid = parcelService.registerParcel(parcel);
        log.info("Parcel created with uuid={}", parcelUuid);
        return parcelUuid;
    }

    @GetMapping(value = "/{parcelId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ParcelResponseDTO getParcelById(@PathVariable String parcelId) {
        return parcelService.getParcelById(parcelId).map(parcelMapper::toDTO).orElseThrow(() -> new NotFoundException("Parcel not found"));
    }

    @PostMapping(value = "/{parcelId}:updateAction", produces = MediaType.APPLICATION_JSON_VALUE)
    public ParcelResponseDTO updateParcel(@Valid @PathVariable String parcelId, @RequestBody ParcelUpdateActionDTO dto, HttpServletRequest request) {
        AppUser user = (AppUser) request.getAttribute("user");
        ParcelUpdateAction action = ParcelUpdateAction.create(parcelId, UpdateActions.valueOf(dto.getAction()), user);
        Parcel updatedParcel = parcelService.updateParcel(action);
        return parcelMapper.toDTO(updatedParcel);
    }

    @DeleteMapping(value="/{parcelId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ParcelResponseDTO deleteParcel(@PathVariable("parcelId") String id) {
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
