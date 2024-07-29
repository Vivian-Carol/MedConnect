package org.me.gcu.medconnect.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RegionService {
    @GET("/api/regions")
    Call<List<String>> getRegions();
}
