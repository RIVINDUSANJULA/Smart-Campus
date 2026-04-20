package com.smartcampus.model;

import java.util.Map;


// Plain Old Java Object
// Java --> JSON
// JSON - Easy Read by Browser & Mobile

public class DiscoveryInfo {
    private String version;
    private String adminContact;
    private Map<String, String> endpoints;
    // Above MAP - API route


    public DiscoveryInfo() {}

    public DiscoveryInfo(String version, String adminContact, Map<String, String> endpoints) {
        this.version = version;
        this.adminContact = adminContact;
        this.endpoints = endpoints;
    }

}