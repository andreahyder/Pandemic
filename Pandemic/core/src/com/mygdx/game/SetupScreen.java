package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.PandemicGame.Screens;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.graphics.Color;

public class SetupScreen implements Screen {

	private PandemicGame parent;
	private Stage stage;
	private Skin skin;
	
	SetupScreen( PandemicGame _parent )
	{
		parent = _parent;
		stage = new Stage(new ScreenViewport());
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.input.setInputProcessor(stage);
		
		// Create a table that fills the screen. Everything else will go inside this table.
		int numPlayers = parent.getNumPlayers();
		PlayerInfo[] players = parent.getPlayers();
		Table table = new Table();
	    table.clear();
		table.setFillParent(true);
		table.setDebug(true);
		stage.addActor(table);
		
		Label label1 = new Label("Players Ready?", skin);
		label1.setAlignment(Align.center); // Align
		table.add(label1).width(150).height(90);;
		
		for (int i=0; i<numPlayers; i++){
		    Label labelL = new Label(players[i].getName(), skin);
		    labelL.setAlignment(Align.center); // Align
		    labelL.setColor(Color.CYAN);
//		    Label labelR = new Label( "" ,skin );
//		    labelR.setAlignment(Align.center); // Align
//		    labelR.setColor(Color.WHITE);
		    
//		    Label labelM = new Label( "" ,skin );
//		    labelM.setAlignment(Align.center); // Align
//		    labelM.setColor(Color.WHITE);
//		    table.add(labelM).width(100).height(55);
//		    labelM.setText( "") ;

		    table.row();

		    table.add(labelL).width(150).height(55);
//		    table.add(labelR).width(100).height(55);
//		    labelR.setText( Boolean.toString(players[i].getReady())) ;
		    table.setFillParent(true);
		    table.debugAll();
		    
		    Label labelR = new Label( "" ,skin );
		    labelR.setAlignment(Align.center); // Align
		    labelR.setColor(Color.WHITE);
		    table.add(labelR).width(100).height(55);
		    String toPrint = "";
		    if (players[i].getReady() == true) {
		    	toPrint = "true";
		    }
		    else {
		    	toPrint = "?";
		    }
		    labelR.setText( toPrint ) ;
		    
		}
		
		TextButton toggleReady = new TextButton("Ready?", skin);
		toggleReady.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				PlayerInfo currentPlayer = parent.getCurrentPlayer();		
				currentPlayer.toggleReady();
				ClientComm.send("ToggleReady");
				
//				for (int i=0; i<1; i++){
//					Label labelR = new Label( "" ,skin );
//				    labelR.setAlignment(Align.center); // Align
//				    labelR.setColor(Color.WHITE);
//				    table.add(labelR).width(100).height(55);
//				    labelR.setText( Boolean.toString(currentPlayer.getReady())) ;
//				}
				stage.clear();
				Table table = new Table();
			    table.clear();
				table.setFillParent(true);
				table.setDebug(true);
				stage.addActor(table);
				
				Label label1 = new Label("Players Ready?", skin);
				label1.setAlignment(Align.center); // Align
				table.add(label1).width(150).height(90);;
				for (int i=0; i<numPlayers; i++){
				    Label labelL = new Label(players[i].getName(), skin);
				    labelL.setAlignment(Align.center); // Align
				    labelL.setColor(Color.CYAN);
//				    Label labelR = new Label( "" ,skin );
//				    labelR.setAlignment(Align.center); // Align
//				    labelR.setColor(Color.WHITE);
				    
//				    Label labelM = new Label( "" ,skin );
//				    labelM.setAlignment(Align.center); // Align
//				    labelM.setColor(Color.WHITE);
//				    table.add(labelM).width(100).height(55);
//				    labelM.setText( "") ;

				    table.row();

				    table.add(labelL).width(150).height(55);
//				    table.add(labelR).width(100).height(55);
//				    labelR.setText( Boolean.toString(players[i].getReady())) ;
				    table.setFillParent(true);
				    table.debugAll();
				    
				    Label labelR = new Label( "" ,skin );
				    labelR.setAlignment(Align.center); // Align
				    labelR.setColor(Color.WHITE);
				    table.add(labelR).width(100).height(55);
				    String toPrint = "";
				    if (players[i].getReady() == true) {
				    	toPrint = "true";
				    }
				    else {
				    	toPrint = "?";
				    }
				    labelR.setText( toPrint ) ;
				    
				}
				TextField txtWait = new TextField("", skin);
				txtWait.setMessageText("Waiting for players...");
				txtWait.setPosition(450, 190);
				stage.addActor(txtWait);
			}
		});

		table.add(toggleReady);
		stage.draw();
		stage.act();
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.draw();
		stage.act();
		

		if ( ClientComm.messageQueue != null )
		{
			if ( ClientComm.messageQueue.size() > 0  )
			{
				//Eat message
				if ( ClientComm.possibleActions != null )
				{
					String[] message = ClientComm.messageQueue.get(0);
					if ( message != null )
					{
						if ( message.length > 0 )
						{
							if( message[0].equals( "StartGame" ) )
							{
								String[] pNames = message[1].split("[,]");
								for( int i = 0; i < pNames.length; i++ )
								{
									if( !parent.getCurrentPlayer().getName().equals(pNames[i]) )
									{
										parent.addPlayer( new PlayerInfo( pNames[i], PawnColour.values()[i], false ) );
										
									}
									else
									{
										parent.getCurrentPlayer().setColour( PawnColour.values()[i] );
									}
								}
								parent.changeScreen( Screens.GAME );
							} 
						}
					}
				}
			}
		}
		
	}
	
	public boolean checkAllReady() {  //checks if all players in player array are ready.
		int numPlayers = parent.getNumPlayers();
		PlayerInfo[] players = parent.getPlayers();
		boolean allPlayersReady = true;
		for (int i=0; i<numPlayers; i++){
			if (players[i].getReady() == false){
				allPlayersReady = false;
			}
		}
		return allPlayersReady;
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
