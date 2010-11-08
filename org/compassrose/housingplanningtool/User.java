package org.compassrose.housingplanningtool;

public abstract class User {
    private String rcsID;
    
    public User(String r)
    {
        rcsID = r;
    }

    public String getRCSID()
    {
        return rcsID;
    }

    public void setRCSID(String r)
    {
        rcsID = r;
    }
}
