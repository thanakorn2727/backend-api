package com.thanakornget.backendjavaapi.service.impl;

import com.thanakornget.backendjavaapi.model.request.TripsRequest;
import com.thanakornget.backendjavaapi.model.response.TripsResponse;
import com.thanakornget.backendjavaapi.repository.TripsRepository;
import com.thanakornget.backendjavaapi.service.TripsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripsImpl implements TripsService {
    @Autowired
    private TripsRepository repository;

    @Override
    public List<TripsResponse> getListTrips(TripsRequest body) throws Exception{
        System.out.println("body ::>>>>" + body);
        return repository.getListTrips(body);
    }
}
