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

import javafx.scene.control.CheckBox;

import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.graphics.Color;

public class SetupScreen implements Screen {

	private PandemicGame parent;
	private Stage stage;
	private Skin skin;
	
	boolean bioterroristOpt = false;
	
	SetupScreen( PandemicGame _parent )
	{
		parent = _parent;
		stage = new Stage(parent.screen);
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.input.setInputProcessor(stage);
		
		// Create a table that fills the screen. Everything else will go inside this table.
		final int numPlayers = parent.getNumPlayers();
		final PlayerInfo[] players = parent.getPlayers();
		Table table = new Table();
	    table.clear();
		table.setFillParent(true);
		stage.addActor(table);
		
		
		Label nameLabel = new Label("Name:", skin);
		nameLabel.setAlignment(Align.center); // Align center
	    TextField nameText = new TextField("", skin);
	    Label addressLabel = new Label("IP Address:", skin);
	    addressLabel.setAlignment(Align.center); // Align center
	    TextField addressText = new TextField("", skin);
	    
	    //Table table = new Table();
	    table.add(nameLabel).width(100).spaceBottom(10);;
	    table.add(nameText).width(100);
	    table.row();
	    table.add(addressLabel);
	    table.add(addressText).width(100).spaceBottom(10);;
	    
	    table.setDebug(false); // Show or hide ugly red and blue lines.
	    
	    table.row();
		
		
		
//		Label label1 = new Label("Players Ready?", skin);
//		label1.setAlignment(Align.center); // Align center
//		table.add(label1).width(150).height(90);;
		
		for (int i=0; i<numPlayers; i++){
		    Label labelL = new Label(players[i].getName() + ", ready?", skin);
		    labelL.setAlignment(Align.center); // Align
		    labelL.setColor(Color.WHITE);

		    table.row();

		    table.add(labelL).width(150).height(55).spaceBottom(10);;
//		    table.add(labelR).width(100).height(55);
//		    labelR.setText( Boolean.toString(players[i].getReady())) ;
		    table.setFillParent(true);
		    //table.debugAll();
		    
		    Label labelR = new Label( "" ,skin );
		    labelR.setAlignment(Align.center); // Align
		    labelR.setColor(Color.WHITE);
		    table.add(labelR).width(100).height(55).spaceBottom(10);
		    String toPrint = "";
		    if (players[i].getReady() == true) {
		    	toPrint = "true";
		    }
		    else {
		    	toPrint = "?";
		    }
		    labelR.setText( toPrint ) ;
		    
		}
		
		TextButton toggleReady = new TextButton("YES", skin);
		toggleReady.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				PlayerInfo currentPlayer = parent.getCurrentPlayer();		
				currentPlayer.toggleReady();
				//ClientComm.send("ToggleReady");
				// HAVE TO TAKE OFF ALL MESSAGES FOR NOW ^^^^^^^^^
				
				stage.clear();
				Table table = new Table();
			    table.clear();
				table.setFillParent(true);
				table.setDebug(true);
				stage.addActor(table);
				
				for (int i=0; i<numPlayers; i++){
				    Label labelL = new Label(players[i].getName() , skin);
				    labelL.setAlignment(Align.center); // Align
				    labelL.setColor(Color.WHITE);

				    table.row();

				    table.add(labelL).width(150).height(55).spaceBottom(10);;
				    
				    table.setFillParent(true);
				    table.debugAll();
				    
				    Label labelR = new Label( "" ,skin );
				    labelR.setAlignment(Align.center); // Align
				    labelR.setColor(Color.WHITE);
				    table.add(labelR).width(100).height(55);
				    String toPrint = "";
				    if (players[i].getReady() == true) {
				    	toPrint = "Ready";
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
		
		
		table.row();
		TextButton toggleBioterrorist = new TextButton("YES", skin);
		toggleReady.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				bioterroristOpt = true;
				// SEND SERVER MESSAGE
			}
		});
		table.add(toggleBioterrorist);
		
		
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
		

		if ( ClientComm.messageQueue != null ) {
			if ( ClientComm.messageQueue.size() > 0  ) {
				//Eat message
				if ( ClientComm.possibleActions != null ) {
					String[] message = ClientComm.messageQueue.get(0);
					if ( message != null ) {
						if ( message.length > 0 ) {
							if( message[0].equals( "StartGame" ) ) {
								String[] pNames = message[1].split("[,]");
								for( int i = 0; i < pNames.length; i++ ) {
									if( !parent.getCurrentPlayer().getName().equals(pNames[i]) ) {
										parent.addPlayer( new PlayerInfo( pNames[i], PawnColour.values()[i], false ) );
									}
									else {
										parent.getCurrentPlayer().setColour( PawnColour.values()[i] );
									}
									GameScreen.currentPlayer = parent.getPlayers()[0];
								}
								//parent.changeScreen( Screens.GAME );
								parent.changeScreen( PandemicGame.Screens.GAME_DEBUG );	
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
