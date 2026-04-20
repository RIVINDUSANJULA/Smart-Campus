package com.smartcampus.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// when someone visit PATH (/api/v1) --> This Answer
// NEED TO CREATE ROOM CLASS
@Path("")
public class DiscoveryResource {

    @GET
    //Pnly Get Work. No one Can POST req to this. NO DATA SEND TO THIS URL

    //Java Obj --> JSON
    @Produces(MediaType.APPLICATION_JSON)


    public Response getDiscoveryInfo(){

    }
}
