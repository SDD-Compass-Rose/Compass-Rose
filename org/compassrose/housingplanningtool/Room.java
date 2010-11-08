package org.compassrose.housingplanningtool;

import java.util.ArrayList;

public class Room extends Structure {
	private ArrayList<Rating> ratings;
	
    public Room(int size)
    {
        super(size);
    }

    public ArrayList<Rating> getRatingList()
    {
        return ratings;
    }

    public double getAverageStar()
    {
        int i;
        double average=0;
        for(i=0; i<ratings.size(); ++i)
        {
            average += ratings.get(i).getStars();
        }
        average /= (double)ratings.size();
        return average;
    }
}
