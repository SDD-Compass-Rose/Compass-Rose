package org.compassrose.housingplanningtool;

public class Description
{
	private int capacity;
	private int numberOfFloors;
	private int numberOfRooms;
	private boolean handicapAcessable;
	private boolean airConditioning;
	private String bathroomType;
	private String distanceToCampus;
	private int price;
	private boolean reqMealPlan;
	private String closestDiningHall;
    public Description()
    {
    	capacity = 0;
		numberOfFloors = 0;
		numberOfRooms=0;
		handicapAcessable=false;
		airConditioning = false;
		bathroomType = "";
		distanceToCampus = "";
		price = 0;
		reqMealPlan = true;
		closestDiningHall = "";
    }
    public int getCapacity(){
		return capacity;
	}
	public int getNumOfFloors(){
		return numberOfFloors;
	}
	public int getNumOfRooms(){
		return numberOfRooms;
	}
	public boolean getHandicap(){
		return handicapAcessable;
	}
	public boolean getAirCond(){
		return airConditioning;
	}
	public String getBathType(){
		return bathroomType;
	}
	public String getDistToCampus(){
		return distanceToCampus;
	}
	public int getPrice(){
		return price;
	}
	public boolean getReqMealPlan(){
		return reqMealPlan;
	}
	public String getClosestDiningHall(){
		return closestDiningHall;
	}
	public void setCapacity(int i){
		capacity=i;
	}
	public void setNumOfFloors(int i){
		numberOfFloors = i;
	}
	public void setNumOfRooms(int i){
		numberOfRooms = i;
	}
	public void setHandicap(boolean q){
		handicapAcessable = q;
	}
	public void setAirCond(boolean q){
		airConditioning=q;
	}
	public void setBathType(String s){
		bathroomType = s;
	}
	public void setDistToCampus(String s){
		distanceToCampus = s;
	}
	public void setPrice(int i){
		price = i;
	}
	public void setReqMealPlan(boolean q){
		reqMealPlan = q;
	}
	public void setClosestDiningHall(String s){
		closestDiningHall = s;
	}

}
