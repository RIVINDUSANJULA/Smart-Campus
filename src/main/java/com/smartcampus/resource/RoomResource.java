package com.smartcampus.resource;


import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/rooms")
//Path - room

@Produces(MediaType.APPLICATION_JSON)
// JSON GET
// JSON POST
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {
}
