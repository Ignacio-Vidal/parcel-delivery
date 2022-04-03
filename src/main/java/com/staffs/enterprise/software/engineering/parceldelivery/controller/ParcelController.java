package com.staffs.enterprise.software.engineering.parceldelivery.controller;

import com.staffs.enterprise.software.engineering.parceldelivery.repository.ParcelRepository;
import io.swagger.client.model.ParcelRequestResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/parcel")
public class ParcelController {
    private final ParcelRepository parcelRepository;

    public ParcelController(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ParcelRequestResponse> getParcels(){
        return null;
    }
}
