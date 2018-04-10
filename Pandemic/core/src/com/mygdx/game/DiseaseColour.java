package com.mygdx.game;

public enum DiseaseColour {
	BLUE, YELLOW, BLACK, RED;
	private static String[] names = { "Blue", "Yellow", "Black", "Red" };
	
	public static String getDiseaseName( DiseaseColour disease )
	{
		return names[ disease.ordinal() ];
	}
	
	public static DiseaseColour lookupColourByName( String name )
	{
		for( int i = 0; i < names.length; i++ )
		{
			if( names[i].equalsIgnoreCase( name ) )
				return DiseaseColour.values()[i];
		}
		
		return null;	
	}
}
