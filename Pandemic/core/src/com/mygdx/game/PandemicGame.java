package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PandemicGame extends Game {

	public enum Screens
	{
		MENU, GAME, SETUP, LOAD, JOINLIST
	}
	
	public static ClientComm clientComm;
	
	private JoinListScreen joinListScreen;
	private SetupScreen	setupScreen;
	//private JoinScreen joinScreen;
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private LoadScreen loadScreen;
	
	private PlayerInfo[] players;
	private int numPlayers;
	
	
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
				gameScreen.clientComm = clientComm;
				setScreen( gameScreen );
				break;
				
			case LOAD:
				if ( loadScreen == null ) loadScreen = new LoadScreen( this );
				setScreen( loadScreen );
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
	
	@Override
	public void create() {
		numPlayers = 0;
		players = new PlayerInfo[5];

		
		
		loadScreen = new LoadScreen( this );
		setScreen( loadScreen );

		this.addPlayer( new PlayerInfo( "Joined Player 1", false ) );
		this.addPlayer( new PlayerInfo( "Joined Player 2", false ) );
		
		this.players[0].setColour( PawnColour.CYAN );
		this.players[1].setColour( PawnColour.GREEN );
		this.players[2].setColour( PawnColour.PINK );
		
	}
}
