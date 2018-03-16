package com.mygdx.game;

public enum DiseaseColour {
	BLUE, YELLOW, BLACK, RED;
	private static String[] names = { "Blue", "Yellow", "Black", "Red" };
	
	public static String getDiseaseName( DiseaseColour disease )
	{
		return names[ disease.ordinal() ];
	}
}
