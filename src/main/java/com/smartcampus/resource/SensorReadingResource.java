package com.smartcampus.resource;

import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;
import com.smartcampus.repository.DataStore;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
// GET & POST
public class SensorReadingResource {

    private String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    public Response getHistory() {
        List<SensorReading> history = DataStore.readings.getOrDefault(sensorId, new ArrayList<>());
        return Response.ok(history).build();
    }
    //Fetch Data
    // All The Old Data of The Sensor that's alread in JSON
    // For New -> orDefault



    @POST
    public Response addReading(SensorReading reading) {
        Sensor parentSensor = DataStore.sensors.get(sensorId);

        if (parentSensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Parent Sensor - Not Found.")
                    .build();
        }


    }
}
