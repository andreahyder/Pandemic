package com.mygdx.game;

public class PlayerCardInfo {
	String name;
	//Boolean isOnRoleCard = false;
	
	PlayerCardInfo( CityNode city )
	{
		name = city.getName();
	}
	
	PlayerCardInfo( String _name )
	{
		name = _name;
	}
	
	public String getName()
	{
		return name;
	}
}
