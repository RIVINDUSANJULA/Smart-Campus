package com.smartcampus.config;


// JAX-RS --> Java API (REST Api - Web Services)
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


/*
    ApplicationPath - Base URI - Api
    For Endpoints
    API Versioning - v1
    If Api Change - This Version will not break the app.
*/

@ApplicationPath("/api/v1")
public class ApiApplication extends Application {
}