package com.mygdx.game;

public class PlayerInfo {
	boolean readyStatus = false;
	boolean isClientPlayer = false;
	PawnColour colour;
	int slotNum = -1;
	String name;
	String currCity;
	
	PlayerInfo( String _name, boolean _isClintPlayer )
	{
		name 	= _name;
		isClientPlayer = _isClintPlayer;
	}
	
	PlayerInfo( String _name, PawnColour _colour, boolean _isClintPlayer )
	{
		name 	= _name;
		colour 	= _colour; 
		isClientPlayer = _isClintPlayer;
	}
	
	public void setCity( String newCity )
	{
		currCity = newCity;
	}
	
	public String getCity() 
	{
		return currCity;
	}
	
	public void setSlot( int newSlotNum )
	{
		slotNum = newSlotNum;
	}
	
	public int getSlot()
	{
		return slotNum;
	}
	
	public boolean toggleReady()
	{
		this.readyStatus = !this.readyStatus;
		return readyStatus;
	}
	
	public boolean getReady() 
	{
		return readyStatus;
	}

	public PawnColour getColour() {
		return colour;
	}

	public void setColour(PawnColour colour) {
		this.colour = colour;
	}

	public int getSlotNum() {
		return slotNum;
	}

	public void setSlotNum(int slotNum) {
		this.slotNum = slotNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isClientPlayer() 
	{
		return isClientPlayer;
	}
	
}
