package com.smartcampus.resource;

import com.smartcampus.model.DiscoveryInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;


// when someone visit PATH (/api/v1) --> This Answer
// NEED TO CREATE ROOM CLASS
@Path("")
public class DiscoveryResource {

    @GET
    //Pnly Get Work. No one Can POST req to this. NO DATA SEND TO THIS URL

    //Java Obj --> JSON
    @Produces(MediaType.APPLICATION_JSON)


    public Response getDiscoveryInfo(){
        Map<String, String> resourceLinks = new HashMap<>();

        resourceLinks.put("rooms", "/api/v1/rooms");
        resourceLinks.put("sensors", "/api/v1/sensors");
        // HashMap - Above 2 --> room, sensors
        // ENDPOINTS

        DiscoveryInfo info = new DiscoveryInfo(
                "1.0.0",
                "admin@smartcampus.westminster.ac.uk",
                resourceLinks
        );
        // Version,Admin,MAP (PATHS - ROOMS & SENSORS) --> DiscoveryInfo

    }
}
