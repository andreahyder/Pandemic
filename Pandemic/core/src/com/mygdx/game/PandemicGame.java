package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PandemicGame extends Game {

	public enum Screens
	{
		MENU, GAME, SETUP, NAME, JOINLIST
	}
	
	private JoinListScreen joinListScreen;
	private SetupScreen	setupScreen;
	//private JoinScreen joinScreen;
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private NameScreen nameScreen;
	
	private PlayerInfo[] players;
	private int numPlayers;
	
	private PlayerInfo currentPlayer;
	
	
	public void changeScreen( Screens screen )
	{
		switch( screen )
		{
			case MENU:
				if ( menuScreen == null ) menuScreen = new MenuScreen( this );
				setScreen( menuScreen );
				break;
				
			case GAME:
				if ( gameScreen == null ) 
				{
					gameScreen = new GameScreen( this, players );
				}
				setScreen( gameScreen );
				break;
				
			case NAME:
				if ( nameScreen == null ) nameScreen = new NameScreen( this );
				setScreen( nameScreen );
				break;
				
			case SETUP:
				if ( setupScreen == null ) setupScreen = new SetupScreen( this );
				setScreen( setupScreen );
				break;
		
			case JOINLIST:
				if ( joinListScreen == null ) joinListScreen = new JoinListScreen( this );
				setScreen( joinListScreen );
				break;
				
		}
		
	}

	public boolean addPlayer( PlayerInfo newPlayer )
	{
		if ( numPlayers > 4 ) return false;
		players[ numPlayers ] = newPlayer;
		numPlayers++;
		return true;
	}
	
	public PlayerInfo getCurrentPlayer(){
		return this.currentPlayer;
	}
	
	public void setCurrentPlayer(PlayerInfo player) {
		this.currentPlayer = player;
	}
	
	public int getNumPlayers() {
		return this.numPlayers;
	}
	
	public PlayerInfo[] getPlayers() {
		return this.players;
	}
	
	@Override
	public void create() {
		numPlayers = 0;
		players = new PlayerInfo[5];

		
		
		nameScreen = new NameScreen( this );
		setScreen( nameScreen );

		this.addPlayer( new PlayerInfo( "Joined Player 1", false ) );
		this.addPlayer( new PlayerInfo( "Joined Player 2", false ) );
		
		this.players[0].setColour( PawnColour.CYAN );
		this.players[1].setColour( PawnColour.GREEN );
		
	}
}
