package com.smartcampus.resource;


import com.smartcampus.model.Room;
import com.smartcampus.repository.DataStore;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/rooms")
//Path - room

@Produces(MediaType.APPLICATION_JSON)
// JSON GET
// JSON POST
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    @GET
    public Response getAllRooms() {
        List<Room> allRooms = new ArrayList<>(DataStore.rooms.values());

        return Response.ok(allRooms).build();
        //200 --> Build
    }
    // Get all The Room - HashMap --> List
}
