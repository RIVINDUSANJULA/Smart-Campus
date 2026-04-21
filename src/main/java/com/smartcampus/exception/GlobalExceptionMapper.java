package com.smartcampus.exception;

import com.smartcampus.model.ErrorMessage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable ex) {
        ErrorMessage errorInfo;
        Response.Status status;

        if (ex instanceof RoomNotEmptyException) {
            errorInfo = new ErrorMessage(ex.getMessage(), 409, "https://api.smartcampus.com/docs/errors/409");
            status = Response.Status.CONFLICT;
        }
        // Delete Attempt - Room BUT Sensor IS THERE --> Resource Conflict (409)


        else if (ex instanceof LinkedResourceNotFoundException) {
            errorInfo = new ErrorMessage(ex.getMessage(), 422, "https://api.smartcampus.com/docs/errors/422");
            return Response.status(422)
                    .entity(errorInfo)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        // Dependency Validation (422) - Create - Sensor (NEW) BUT NO ROOM


        // 403 - MAINTENANCE
        else if (ex instanceof SensorUnavailableException) {
            errorInfo = new ErrorMessage(ex.getMessage(), 403, "https://api.smartcampus.com/docs/errors/403");
            status = Response.Status.FORBIDDEN;
        }

        else {
            errorInfo = new ErrorMessage("Internal Server Error: An unexpected failure occurred.", 500, "https://api.smartcampus.com/docs/errors/500");
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        // HTTP 500 Internal Server Error
    }
}
