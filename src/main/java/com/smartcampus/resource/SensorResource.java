package com.smartcampus.resource;

import com.smartcampus.exception.LinkedResourceNotFoundException;
import com.smartcampus.model.Sensor;
import com.smartcampus.repository.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
// Path
// JSON Get
// JSON Post

public class SensorResource {

    @GET
    public Response getSensors(@QueryParam("type") String type) {
        List<Sensor> allSensors = new ArrayList<>(DataStore.sensors.values());

        if (type != null && !type.trim().isEmpty()) {
            List<Sensor> filtered = allSensors.stream()
                    .filter(s -> type.equalsIgnoreCase(s.getType()))
                    .collect(Collectors.toList());
            return Response.ok(filtered).build();
        }

        return Response.ok(allSensors).build();
    }

    @POST
    public Response registerSensor(Sensor sensor) {

        if (!DataStore.rooms.containsKey(sensor.getRoomId())) {
            throw new LinkedResourceNotFoundException(sensor.getRoomId() + " Not Exist, Therefore Can't Save Sensor ID");
        }


        // Save Sensor & Room Data -- New
        DataStore.sensors.put(sensor.getId(), sensor);
        DataStore.rooms.get(sensor.getRoomId()).getSensorIds().add(sensor.getId());


        return Response.status(Response.Status.CREATED).entity(sensor).build();

    }


    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadingsResource(@PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }

    // No Get or Post
    // ID (Parmeter, Just Call it Like param (Node.js)) --> Object


}
