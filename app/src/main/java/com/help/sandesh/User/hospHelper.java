package com.help.sandesh.User;

public class hospHelper {
    private String Name;
    private String City;
    private String State;
    private String Description;
    private String Phno;
    private LocationHelper Location;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPhno() {
        return Phno;
    }

    public void setPhno(String phno) {
        Phno = phno;
    }

    public LocationHelper getLocation() {
        return Location;
    }

    public void setLocation(LocationHelper location) {
        Location = location;
    }

    public hospHelper(String name, String city, String state, String description, String phno, LocationHelper location) {
        Name = name;
        City = city;
        State = state;
        Description = description;
        Phno = phno;
        Location = location;
    }
}
