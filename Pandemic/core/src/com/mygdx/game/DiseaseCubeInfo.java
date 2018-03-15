package com.mygdx.game;

public class DiseaseCubeInfo {
	DiseaseColour colour;
	
	DiseaseCubeInfo( DiseaseColour _colour )
	{
		colour = _colour;
	}
	
	public int getColourIndex()
	{
		return colour.ordinal();
	}
}
