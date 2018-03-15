package com.mygdx.game;

import java.util.ArrayList;

public class PlayerInfo {
	boolean readyStatus = false;
	boolean isClientPlayer = false;
	PawnColour colour;
	int slotNum = -1;
	String name;
	String currCity;
	
	ArrayList<PlayerCardInfo> hand = new ArrayList<PlayerCardInfo>();
	
	PlayerInfo( String _name, boolean _isClintPlayer )
	{
		name 	= _name;
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
		readyStatus = !readyStatus;
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
	
	public void addCardToHand( PlayerCardInfo card )
	{
		hand.add( card );
	}
	
	public void removeCardFromHand( String cardName )
	{
		for ( int i = 0; i < hand.size(); i++ )
		{
			if ( hand.get(i).getName().equals( cardName ) )
				hand.remove( i );
		}
	}
	
	public int getHandSize()
	{
		return hand.size();
	}
	
	public ArrayList<PlayerCardInfo> getHand()
	{
		return hand;
	}
	
}
