package com.smartcampus.exception;

import com.smartcampus.model.ErrorMessage;

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
    }
}
