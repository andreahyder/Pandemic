package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
//import com.mygdx.game.MenuScreen.JoinGameTextInput;
//import com.badlogic.gdx.scenes.scene2d.ui.TextField;
//import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.mygdx.game.PandemicGame.Screens;

public class NameScreen implements Screen {
	
	private PandemicGame parent;
	private Stage stage;
	private Skin skin;
	String name = "";
	
	NameScreen( PandemicGame _parent )
	{
		NameTextInput listener = new NameTextInput();
		Gdx.input.getTextInput(listener, "Please enter your name", "", "Name");
		
		parent = _parent;
		stage = new Stage(parent.screen);
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		Gdx.input.setInputProcessor(stage);
		
	}
	
	public class NameTextInput implements TextInputListener 
	{
		@Override
		public void input (String text) 
		{
			boolean validName = true;
			
			if (text.length() > 22) {
				validName = false;
			}
			else if (text.trim().length() == 0) { //trim is to take off blank spaces
				validName = false;
			}

			if (validName == false) {
				Dialog invalidName = new Dialog("Invalid Name",skin)
		        {
		        	@Override
		        	protected void result(java.lang.Object object)
		        	{
		        		if( object != null )
		        		{
		    				NameTextInput listener = new NameTextInput();
		    				Gdx.input.getTextInput(listener, "Please enter your name", "", "Name");
		        		}
		        		
		        	}
		        };
		        invalidName.button("Try Again", invalidName );
		        invalidName.button("Cancel", null );
		        
		        invalidName.show(stage);
			}
			else {
				name = text;
			}
			
			
		}
	
		@Override
		public void canceled () 
		{
		   
		}
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) { 	
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		if ( !name.equals( "" ) )
		{
			PlayerInfo newPlayer = new PlayerInfo( name, true, "Researcher");	// ADDED
			newPlayer.setColour(PawnColour.values()[5]);
			parent.setCurrentPlayer( newPlayer );
			parent.addPlayer( newPlayer );
			parent.changeScreen( Screens.MENU );
		}
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
