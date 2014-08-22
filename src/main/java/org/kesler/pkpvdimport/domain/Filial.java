package org.kesler.pkpvdimport.domain;

public class Filial {
    private String id;
    private String name;
    private String ipAddress;
    private Boolean active;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

}
