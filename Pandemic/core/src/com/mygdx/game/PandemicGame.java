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
	
	private JoinListScreen joinListScreen;
	private SetupScreen	setupScreen;
	//private JoinScreen joinScreen;
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private LoadScreen loadScreen;
	
	
	public void changeScreen( Screens screen )
	{
		switch( screen )
		{
			case MENU:
				if ( menuScreen == null ) menuScreen = new MenuScreen( this );
				setScreen( menuScreen );
				break;
				
			case GAME:
				if ( gameScreen == null ) gameScreen = new GameScreen( this );
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


	@Override
	public void create() {
		loadScreen = new LoadScreen( this );
		setScreen( loadScreen );
		
	}
}
