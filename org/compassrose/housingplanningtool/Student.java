package org.compassrose.housingplanningtool;

import java.util.ArrayList;

public class Student extends User {	
	private ArrayList<Student> roommates;
    private ArrayList<Student> pendingRoommates;
    private ArrayList<Building> buildingList;
    private ArrayList<Room> roomList;
    private int lottoNum;
    
    public Student(String r)
    {
        super(r);
    }

    public void addRoommate(Student r)
    {
        roommates.add(r);
    }

    public void removeRoommate(Student r)
    {
        roommates.remove(r);
    }

    public ArrayList<Student> getRoommates()
    {
        return roommates;
    }

    public void addPendingRoommate(Student r)
    {
        pendingRoommates.add(r);
    }

    public void removePendingRoommate(Student r)
    {
        pendingRoommates.remove(r);
    }

    public ArrayList<Student> getPendingRoommates()
    {
        return pendingRoommates;
    }

    public void addBuilding(Building b)
    {
        buildingList.add(b);
    }

    public void removeBuilding(Building b)
    {
        buildingList.add(b);
    }

    public ArrayList<Building> getBuildings()
    {
        return buildingList;
    }

    public void addRoom(Room r)
    {
        roomList.add(r);
    }

    public void removeRoom(Room r)
    {
        roomList.remove(r);
    }

    public ArrayList<Room> getRooms()
    {
        return roomList;
    }

    public int getLottoNumber()
    {
        return lottoNum;
    }

    public void setLottoNumber(int l)
    {
        lottoNum = l;
    }

    
}
