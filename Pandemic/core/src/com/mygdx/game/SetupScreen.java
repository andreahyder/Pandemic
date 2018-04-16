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
	
	String bioterroristOpt;
	Label showBioTOpt;
	
	String otbOpt;
	Label otbOptLabel;
	
	String virOpt;
	Label virOptLabel;
	
	int diffOpt;
	String difficulty;
	Label diffOptLabel;
	
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
		Gdx.input.setInputProcessor(stage);
		
		this.bioterroristOpt = "No";
		this.showBioTOpt = new Label(bioterroristOpt, skin);
		
		this.otbOpt = "No";
		this.otbOptLabel = new Label(otbOpt, skin);
		
		this.virOpt = "No";
		this.virOptLabel = new Label(virOpt, skin);
		
		this.diffOpt = 0;
		difficulty = diffOpt + "";
		this.diffOptLabel = new Label(difficulty, skin);
		
	    Label addressLabel = new Label("IP Address:", skin);
	    addressLabel.setAlignment(Align.center); // Align center
	    TextField addressText = new TextField("", skin);
	    
	    table.add(addressLabel).width(100);
	    table.add(addressText).width(100).spaceBottom(10);;
	    
	    table.setDebug(false); // Show or hide ugly red and blue lines.
	    
	    
	    
	    table.row();
		Label diffLabel = new Label("Difficulty Level:", skin);
		diffLabel.setAlignment(Align.center); // Align center
		table.add(diffLabel).spaceBottom(10);
		table.add(diffOptLabel);
		TextButton diff0 = new TextButton("0", skin);
		diff0.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				stage.addActor(table);
				diffOpt = 0;
				difficulty = diffOpt + "";
				diffOptLabel.setText(difficulty);
				// SEND SERVER MESSAGE
			}
		});
		table.add(diff0).spaceRight(7).uniform() ;
		TextButton diff1 = new TextButton("1", skin);
		diff1.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				stage.addActor(table);
				diffOpt = 1;
				difficulty = diffOpt + "";
				diffOptLabel.setText(difficulty);
				// SEND SERVER MESSAGE
			}
		});
		table.add(diff1).uniform();
		TextButton diff2 = new TextButton("2", skin);
		diff2.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				stage.addActor(table);
				diffOpt = 2;
				difficulty = diffOpt + "";
				diffOptLabel.setText(difficulty);
				// SEND SERVER MESSAGE
			}
		});
		table.add(diff2).uniform();
		TextButton diff3 = new TextButton("3", skin);
		diff3.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				stage.addActor(table);
				diffOpt = 3;
				difficulty = diffOpt + "";
				diffOptLabel.setText(difficulty);
				// SEND SERVER MESSAGE
			}
		});
		table.add(diff3).uniform();
		
	    
		
		
		table.row();
		Label bioTLabel = new Label("Bioterrorist Expansion:", skin);
		bioTLabel.setAlignment(Align.center); // Align center
		table.add(bioTLabel).spaceBottom(10);
		table.add(showBioTOpt);
		TextButton toggleBioterroristY = new TextButton("YES", skin);
		toggleBioterroristY.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				stage.addActor(table);
				bioterroristOpt = "Yes";
				showBioTOpt.setText(bioterroristOpt);
				// SEND SERVER MESSAGE
			}
		});
		table.add(toggleBioterroristY);
		TextButton toggleBioterroristN = new TextButton("NO", skin);
		toggleBioterroristN.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				stage.addActor(table);
				bioterroristOpt = "No";
				showBioTOpt.setText(bioterroristOpt);
				// SEND SERVER MESSAGE
			}
		});
		table.add(toggleBioterroristN);
		
		
		table.row();
		Label otbLabel = new Label("On The Brink Expansion:", skin);
		otbLabel.setAlignment(Align.center); // Align center
		table.add(otbLabel).spaceBottom(10);
		table.add(otbOptLabel);
		TextButton otbY = new TextButton("YES", skin);
		otbY.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				stage.addActor(table);
				otbOpt = "Yes";
				otbOptLabel.setText(otbOpt);
				// SEND SERVER MESSAGE
			}
		});
		table.add(otbY);
		TextButton otbN = new TextButton("NO", skin);
		otbN.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				stage.addActor(table);
				otbOpt = "No";
				otbOptLabel.setText(otbOpt);
				// SEND SERVER MESSAGE
			}
		});
		table.add(otbN);
		
		
		table.row();
		Label virLabel = new Label("Virulent Strain Expansion:", skin);
		virLabel.setAlignment(Align.center); // Align center
		table.add(virLabel).spaceBottom(10);
		table.add(virOptLabel);
		TextButton virY = new TextButton("YES", skin);
		virY.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				stage.addActor(table);
				virOpt = "Yes";
				virOptLabel.setText(virOpt);
				// SEND SERVER MESSAGE
			}
		});
		table.add(virY);
		TextButton virN = new TextButton("NO", skin);
		virN.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				stage.addActor(table);
				virOpt = "No";
				virOptLabel.setText(virOpt);
				// SEND SERVER MESSAGE
			}
		});
		table.add(virN);
		
		
		
		table.row();
		
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
		    	toPrint = "No";
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
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		//showBioTOpt.setText(bioterroristOpt);
		
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
