package com.mygdx.game;

import java.util.ArrayList;

public class PlayerInfo {
	boolean readyStatus = false;
	boolean isClientPlayer = false;
	PawnColour colour;
	int slotNum = -1;
	String name;
	String currCity = "Atlanta";
	public int cardsToCure = 5;
	String role;
	public PlayerCardInfo eventCardOnRoleCard;
	public ArrayList <DiseaseCubeInfo> diseaseCubesOnRoleCard = new ArrayList<DiseaseCubeInfo>();
	boolean roleActionUsed = false;
	boolean visible = true;
	
	ArrayList<PlayerCardInfo> hand = new ArrayList<PlayerCardInfo>();
	
	PlayerInfo( String _name, boolean _isClintPlayer )
	{
		name 	= _name;
		isClientPlayer = _isClintPlayer;
	}
	
	public PlayerInfo(String _name, PawnColour _colour, boolean _isClintPlayer ) 
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
		return this.readyStatus;
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

	public String[] getHandOfStrings(){

		String[] handStrings = new String[hand.size()];
		for(int i = 0; i < handStrings.length; i++){
			handStrings[i] = hand.get(i).getName();
		}

		return handStrings;
	}
	
	public ArrayList<String> getCardNamesByColour( DiseaseColour color )
	{
		ArrayList<String> retArr = new ArrayList<String>();
		
		for( PlayerCardInfo card : hand )
		{
			if( GameScreen.lookupCity( card.getName() ) == null )
				continue;
			
			if( color == DiseaseColour.PURPLE )
			{	
				if( GameScreen.lookupCity( card.getName() ).getCubesByColor( DiseaseColour.getDiseaseName( color ) ).size() > 0  )
					retArr.add( card.getName() );
			}
			else
			{
				if( GameScreen.lookupCity( card.getName() ).getColour().equalsIgnoreCase( DiseaseColour.getDiseaseName( color )) )
					retArr.add( card.getName() );
			}
		}
		
		return retArr;
	}
	
	public String[] getCurableDiseases( )
	{
		ArrayList<String> curableDiseases = new ArrayList<String>();
		for( int i = 0; i < 5; i++ )
		{
			String diseaseName = DiseaseColour.getDiseaseName( DiseaseColour.values()[i] );
			if( !diseaseName.equalsIgnoreCase("purple") )
			{
				int vsMod = ( GameScreen.virulentStrainStatuses.get("ComplexMolecularStructure") && diseaseName.equalsIgnoreCase( GameScreen.virulentStrainDisease ) ) ? 1 : 0; 
				int foMod = ( getNumStoredCubes( DiseaseColour.values()[i] ) );
				foMod = foMod / 3;
				foMod = -Math.min(foMod, 2);
				
				if( getCardNamesByColour( DiseaseColour.values()[i] ).size() >= cardsToCure + vsMod + foMod )
					curableDiseases.add( diseaseName );
			}
			else
			{
				if( getCardNamesByColour( DiseaseColour.values()[i] ).size() > 0 && hand.size() >= cardsToCure )
					curableDiseases.add( diseaseName );
			}
		}
		
		return curableDiseases.toArray( new String[ curableDiseases.size() ] );
	}
	
	public int getNumStoredCubes( DiseaseColour colour )
	{
		if( !role.equalsIgnoreCase( "FieldOperative" ) )
			return 0;
		
		int numCubes = 0;
		for( DiseaseCubeInfo cube : diseaseCubesOnRoleCard )
			if( cube.getColour() == colour )
				numCubes++;
		
		return numCubes;
	}
	
	public int getNumCureCardReducedByStoredCubes( String disease )
	{
		if( !role.equalsIgnoreCase( "FieldOperative" ) )
			return 0;
	
		int foMod = ( getNumStoredCubes( DiseaseColour.lookupColourByName( disease ) ) );
		foMod = foMod / 3;
		foMod = Math.min(foMod, 2);
		return foMod;
	}

}
