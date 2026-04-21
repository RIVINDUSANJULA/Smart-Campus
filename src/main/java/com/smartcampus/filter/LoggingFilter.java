package com.smartcampus.filter;


import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {
    // ContainerRequestFilter - Req
    // ContainerResponseFilter - Respond

    private static final Logger logger = Logger.getLogger(LoggingFilter.class.getName());
    // Logger


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String method = requestContext.getMethod();
        String path = requestContext.getUriInfo().getPath();
        logger.info("Incoming Request: " + method + " & Path:  " + path);
    }

    // API Enter - get (Capture) DATA --> Out


}
