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


    // Get
    public String getVersion() {
        return version;
    }

    public String getAdminContact() {
        return adminContact;
    }

    public Map<String, String> getEndpoints() {
        return endpoints;
    }

    // Set
    public void setVersion(String version) {
        this.version = version;
    }
    public void setAdminContact(String adminContact) {
        this.adminContact = adminContact;
    }
    public void setEndpoints(Map<String, String> endpoints) {
        this.endpoints = endpoints;
    }

}