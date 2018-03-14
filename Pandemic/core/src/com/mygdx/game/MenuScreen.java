package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen implements Screen 
{

	private PandemicGame parent;
	private Stage stage;
	private Skin skin;
	
	MenuScreen( PandemicGame _parent )
	{
		parent = _parent;
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		// Create a table that fills the screen. Everything else will go inside this table.
		Table table = new Table();
		table.setFillParent(true);
		table.setDebug(true);
		stage.addActor(table);
		
		skin = new Skin(Gdx.files.internal("plain-james-ui/plain-james-ui.json"));
		
		TextButton newGame = new TextButton("New Game", skin);
		newGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen( PandemicGame.Screens.GAME );	
			}
		});
		
		TextButton joinGame = new TextButton("Join Game", skin);
		joinGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				JoinGameTextInput listener = new JoinGameTextInput();
				Gdx.input.getTextInput(listener, "Please enter the IP", "", "Host IP");
			}
		});
		
		TextButton exit = new TextButton("Exit", skin);
		exit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();				
			}
		});
		
		table.add(newGame).fillX().uniformX();
		table.row();
		table.add(joinGame).fillX().uniformX();
		table.row();
		table.add(exit).fillX().uniformX();
		
	}
	
	public class JoinGameTextInput implements TextInputListener 
	{
		@Override
		public void input (String text) 
		{
			//Check if it is a valid IP
			boolean validIP = true;
			
			String[] parts = text.split("[.]"); 
			validIP &= ( parts.length == 4 );
			if( validIP )
			{
				validIP &= ( Integer.parseInt( parts[ 0 ] ) >= 0 && Integer.parseInt( parts[ 0 ] ) <= 255 );
			}
			
			if( validIP )
			{
				//NetworkManager.joinGame( text );
			}
			else 
			{
		        Dialog wrongIP = new Dialog("Invalid IP",skin)
		        {
		        	@Override
		        	protected void result(java.lang.Object object)
		        	{
		        		if( object != null )
		        		{
		    				JoinGameTextInput listener = new JoinGameTextInput();
		    				Gdx.input.getTextInput(listener, "Please enter the IP", "", "Host IP");
		        		}
		        		
		        	}
		        };
		        wrongIP.button("Try Again", wrongIP );
		        wrongIP.button("Cancel", null );
		        
		        wrongIP.show(stage);
			}
			
			
		}
	
		@Override
		public void canceled () 
		{
		   
		}
	}
	
	@Override
	public void show() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) 
	{
		stage.getViewport().update(width, height, true);

	}

	@Override
	public void pause() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void hide()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() 
	{
		stage.dispose();
	}

}
