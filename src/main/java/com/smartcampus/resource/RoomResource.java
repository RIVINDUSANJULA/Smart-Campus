package com.smartcampus.resource;


import com.smartcampus.model.Room;
import com.smartcampus.repository.DataStore;

import javax.ws.rs.*;
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


    @GET
    @Path("/{roomId}")
    //Value of PATH above Only
    public Response getRoom(@PathParam("roomId") String roomId) {
        Room room = DataStore.rooms.get(roomId);
        // If Path - roomId in HashMap
        // It Shoudl be a O(1)

        //If Not FOund
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("NO ROOM").build();
        }
        return Response.ok(room).build();
        //if 200 --> Build
    }
    //Room - ID Specific


    @POST
    public Response createRoom(Room room) {
        //Input JSON --> Room NEW


        DataStore.rooms.put(room.getId(), room);
        // Datastore - Room Var (HashMap) --> roomId (Room.Java) - ID & room (Inputed JSON - @Consume JSON FIle)

        // Like 202
        return Response.status(Response.Status.CREATED).entity(room).build();
    }
    // Room Create NEW
}
