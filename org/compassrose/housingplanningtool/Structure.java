package org.compassrose.housingplanningtool;

import java.util.ArrayList;

public abstract class Structure {
    private String name;
    private Structure parent;
    private ArrayList<Structure> children;
    private String specialChildren;
    private int special;
    private String mapURL;
    private Description description;
    
    public Structure(int size)
    {
    	children = new ArrayList<Structure>();
        int i;
        for(i=0; i<size; ++i)
            children.add(null);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String n)
    {
        name = n;
    }

    public Structure getParent()
    {
        return parent;
    }

    public void setParent(Structure p)
    {
        parent = p;
    }

    public ArrayList<Structure> getChildren()
    {
        return children;
    }

    public Structure getChild(int i)
    {
        if(i < children.size())
            return children.get(i);
        else
            return null;
    }

    public void addChild(int i, Structure s)
    {
        if(i < children.size())
            children.add(i, s);
    }

    public void removeChild(int i)
    {
        if(i < children.size())
            children.set(i, null);
    }

    public Description getDescription()
    {
        return description;
    }

    public void setDescription(Description d)
    {
        description = d;
    }

    public String getMapURL()
    {
        return mapURL;
    }

    public void setMapURL(String m)
    {
        mapURL = m;
    }

    public String getSpecialChildren()
    {
        return specialChildren;
    }

    public void setSpecialChildren(String s)
    {
        specialChildren = s;
    }

    public int getSpecialStatus()
    {
        return special;
    }

    public void setSpecialStatus(int s)
    {
        special = s;
    }
}

