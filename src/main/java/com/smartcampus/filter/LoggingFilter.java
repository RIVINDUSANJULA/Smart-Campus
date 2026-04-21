package com.smartcampus.filter;


import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {
    // ContainerRequestFilter - Req
    // ContainerResponseFilter - Respond

    private static final Logger logger = Logger.getLogger(LoggingFilter.class.getName());
    // Logger

}
