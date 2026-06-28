package com.thanakornget.backendjavaapi.repository;

import com.thanakornget.backendjavaapi.model.request.TripsRequest;
import com.thanakornget.backendjavaapi.model.response.TripsResponse;

import java.util.List;

public interface TripsRepository {
    public List<TripsResponse> getListTrips(TripsRequest body) throws Exception;
}
