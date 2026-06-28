package com.thanakornget.backendjavaapi.controllers;

import com.thanakornget.backendjavaapi.model.request.TripsRequest;
import com.thanakornget.backendjavaapi.model.response.APIResponse;
import com.thanakornget.backendjavaapi.model.response.TripsResponse;
import com.thanakornget.backendjavaapi.service.TripsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
public class TripsController {

    @Autowired
    private TripsService service;

    @PostMapping("/list")
    public ResponseEntity<APIResponse> getListTrips(@RequestBody TripsRequest body) throws IOException {
        try {
            System.out.println("test ::>>>>" + body);
            List<TripsResponse> responsesData = service.getListTrips(body);
            System.out.println("responsesData ::>>>>" + responsesData);
            return ResponseEntity.ok(APIResponse.success("Get data Success", responsesData));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
