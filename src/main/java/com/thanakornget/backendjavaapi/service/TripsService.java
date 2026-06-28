package com.thanakornget.backendjavaapi.service;

import com.thanakornget.backendjavaapi.model.request.TripsRequest;
import com.thanakornget.backendjavaapi.model.response.TripsResponse;

import java.util.List;

public interface TripsService {
    public List<TripsResponse> getListTrips(TripsRequest body) throws Exception;
}
