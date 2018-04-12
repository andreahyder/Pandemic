package com.mygdx.game;

import java.util.ArrayList;

public class CityNode {
	ArrayList<CityNode> connectedCities;
	ArrayList<String> connectedCityNames;
	ArrayList<DiseaseCubeInfo> cubes;
	String colour;
	String name;
	float x, y;
	boolean hasResearchStation;
	
	CityNode( String[] nameData, float[] position )
	{
		connectedCities = new ArrayList<CityNode>();
		cubes 			= new ArrayList<DiseaseCubeInfo>();
		name 	= nameData[0];
		colour 	= nameData[1];
		x		= position[0];
		y		= position[1];
		
		connectedCityNames = new ArrayList<String>();
		for( int i = 2; i < nameData.length; i++ )
		{
			connectedCityNames.add( nameData[i] );
		}
	}
	
	public void addConnectedCities( CityNode[] cities )
	{
		for( String cityName : connectedCityNames )
		{
			connectedCities.add( GameScreen.lookupCity( cityName ) );
		}
	}
	
	public void addCube( DiseaseCubeInfo cube )
	{
		cubes.add( cube );
	}
		
	public ArrayList<DiseaseCubeInfo> getCubes()
	{
		return cubes;
	}
	
	public ArrayList<CityNode> getConnectedCities()
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
	
	public boolean[] getDiseaseColours()
	{
		boolean[] coloursPresent = { false, false , false, false };
		for ( DiseaseCubeInfo cube : cubes )
		{
			coloursPresent[ cube.getColourIndex() ] |= true;
		}
		return coloursPresent;
	}
	
	public void removeCubeByColour( DiseaseColour lookupColour )
	{
		for ( int i = 0; i < cubes.size(); i++  )
		{
			DiseaseCubeInfo cube = cubes.get(i);
			if ( cube.getColour() == lookupColour )
			{
				cubes.remove(i);
				return;
			}
		}
	}
	
	public void putResearchStation() 
	{
		hasResearchStation = true;
	}
	
	public void removeResearchStation() 
	{
		hasResearchStation = false;
	}
}
