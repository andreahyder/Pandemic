package com.mygdx.game;

import java.util.ArrayList;

public class CityNode {
	ArrayList<String> connectedCities;
	String colour;
	String name;
	float x, y;
	
	CityNode( String[] nameData, float[] position )
	{
		connectedCities = new ArrayList<String>();
		name 	= nameData[0];
		colour 	= nameData[1];
		x		= position[0];
		y		= position[1];
		
		for( int i = 2; i < nameData.length; i++ )
		{
			connectedCities.add( nameData[i] );
		}
	}
	
	public ArrayList<String> getConnectedCities()
	{
		return connectedCities;
	}

	public String getColour() 
	{
		return colour;
	}

	public String getName() 
	{
		return name;
	}

	public float getX() 
	{
		return x;
	}

	public float getY() 
	{
		return y;
	}
	
	public float getXInWindowCoords( float windowWidth )
	{
		return ( ( x + 1.0f ) / 2.0f ) * windowWidth;
	}

	public float getYInWindowCoords( float windowHeight )
	{
		return ( ( y + 1.0f ) / 2.0f ) * windowHeight;
	}
}
