package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MenuScreen.JoinGameTextInput;
import com.mygdx.game.PandemicGame.Screens;

import javafx.scene.control.CheckBox;

import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.graphics.Color;

public class SetupScreen implements Screen {
	
	private String testGameList = "UpdateSetting/ReceiveGames/game0/game1/game2"; //This will be a message from server
	private String[] savedGames = new String[20];
	private int numSavedGames=0;

	private PandemicGame parent;
	private Stage stage;
	private Skin skin;
	
	private String gameSelected;
	private Label showGameSelected;
	private int stupidCounter = 0;
	private String currentGame;
	
	public String bioterroristOpt;
	private Label showBioTOpt;
	
	private String otbOpt;
	private Label otbOptLabel;
	
	private String virOpt;
	private Label virOptLabel;
	
	public String mutOpt;
	private Label mutOptLabel;
	
	private int diffOpt;
	private String difficulty;
	private Label diffOptLabel;
	
	static boolean showPurpleDisease = false;
	
	public void gameStringListToArray(String testGameList) {
		String delims = "[/]";
		String[] tokens = testGameList.split(delims);
		for (int i=2; i<tokens.length; i++) {
			numSavedGames ++;
			savedGames[i-2] = tokens[i];
		}
	}
	
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
		
		this.gameSelected = "None";
		this.showGameSelected = new Label(gameSelected, skin);;
		
		this.bioterroristOpt = "No";
		this.showBioTOpt = new Label(bioterroristOpt, skin);
		
		this.otbOpt = "No";
		this.otbOptLabel = new Label(otbOpt, skin);
		
		this.virOpt = "No";
		this.virOptLabel = new Label(virOpt, skin);
		
		this.mutOpt = "No";
		this.mutOptLabel = new Label(mutOpt, skin);
		
		this.diffOpt = 0;
		difficulty = diffOpt + "";
		this.diffOptLabel = new Label(difficulty, skin);
		
//	    Label addressLabel = new Label("IP Address:", skin);
//	    addressLabel.setAlignment(Align.center); // Align center
//	    TextField addressText = new TextField("", skin);
		
//		JoinGameTextInput2 listener = new JoinGameTextInput2();
//		Gdx.input.getTextInput(listener, "Please enter the IP", "", "Host IP");

	    
	    
	    table.setDebug(false); // Show or hide ugly red and blue lines.
	    
	    
	    
	    gameStringListToArray(testGameList);
	    table.row();
	    Label selectLabel = new Label("Select Saved Game:", skin);
	    selectLabel.setAlignment(Align.center); // Align center
		table.add(selectLabel).spaceBottom(10);
		table.add(showGameSelected);
		// "None" button: 
		TextButton none = new TextButton("NONE", skin);
		none.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				stage.addActor(table);
				gameSelected = "None";
				showGameSelected.setText(gameSelected);
				// SEND SERVER MESSAGE:
				ClientComm.send("ToggleSetting/ChoseSavedGame/null");
			}
		});
		table.add(none);
		if (0<=numSavedGames-1) {
			//System.out.println(savedGames[i]);
			TextButton gameNum0 = new TextButton(savedGames[0], skin);
			gameNum0.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					stage.addActor(table);
					gameSelected = "game0";
					showGameSelected.setText(gameSelected);
					// SEND SERVER MESSAGE:
					ClientComm.send("ToggleSetting/ChoseSavedGame/game0");
				}
			});
			table.add(gameNum0).spaceRight(7).uniform();
		}
		if (1<=numSavedGames-1) {
			//System.out.println(savedGames[i]);
			TextButton gameNum1 = new TextButton(savedGames[1], skin);
			gameNum1.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					stage.addActor(table);
					gameSelected = "game1";
					showGameSelected.setText(gameSelected);
					// SEND SERVER MESSAGE:
					ClientComm.send("ToggleSetting/ChoseSavedGame/1");
				}
			});
			table.add(gameNum1).spaceRight(7).uniform();
		}
		if (2<=numSavedGames-1) {
			//System.out.println(savedGames[i]);
			TextButton gameNum2 = new TextButton(savedGames[2], skin);
			gameNum2.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					stage.addActor(table);
					gameSelected = "game2";
					showGameSelected.setText(gameSelected);
					// SEND SERVER MESSAGE:
					ClientComm.send("ToggleSetting/ChoseSavedGame/2");
				}
			});
			table.add(gameNum2).spaceRight(7).uniform();
		}
		if (3<=numSavedGames-1) {
			//System.out.println(savedGames[i]);
			TextButton gameNum3 = new TextButton(savedGames[3], skin);
			gameNum3.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					stage.addActor(table);
					gameSelected = "game3";
					showGameSelected.setText(gameSelected);
					// SEND SERVER MESSAGE:
					ClientComm.send("ToggleSetting/ChoseSavedGame/3");
				}
			});
			table.add(gameNum3).spaceRight(7).uniform();
		}
		if (4<=numSavedGames-1) {
			//System.out.println(savedGames[i]);
			TextButton gameNum4 = new TextButton(savedGames[4], skin);
			gameNum4.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					stage.addActor(table);
					gameSelected = "game4";
					showGameSelected.setText(gameSelected);
					// SEND SERVER MESSAGE:
					ClientComm.send("ToggleSetting/ChoseSavedGame/4");
				}
			});
			table.add(gameNum4).spaceRight(7).uniform();
		}
		if (5<=numSavedGames-1) {
			//System.out.println(savedGames[i]);
			TextButton gameNum5 = new TextButton(savedGames[5], skin);
			gameNum5.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					stage.addActor(table);
					gameSelected = "game5";
					showGameSelected.setText(gameSelected);
					// SEND SERVER MESSAGE:
					ClientComm.send("ToggleSetting/ChoseSavedGame/5");
				}
			});
			table.add(gameNum5).spaceRight(7).uniform();
		}
		if (6<=numSavedGames-1) {
			//System.out.println(savedGames[i]);
			TextButton gameNum6 = new TextButton(savedGames[6], skin);
			gameNum6.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					stage.addActor(table);
					gameSelected = "game6";
					showGameSelected.setText(gameSelected);
					// SEND SERVER MESSAGE:
					ClientComm.send("ToggleSetting/ChoseSavedGame/6");
				}
			});
			table.add(gameNum6).spaceRight(7).uniform();
		}
		if (7<=numSavedGames-1) {
			//System.out.println(savedGames[i]);
			TextButton gameNum7 = new TextButton(savedGames[7], skin);
			gameNum7.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					stage.addActor(table);
					gameSelected = "game7";
					showGameSelected.setText(gameSelected);
					// SEND SERVER MESSAGE:
					ClientComm.send("ToggleSetting/ChoseSavedGame/7");
				}
			});
			table.add(gameNum7).spaceRight(7).uniform();
		}
		if (8<=numSavedGames-1) {
			//System.out.println(savedGames[i]);
			TextButton gameNum8 = new TextButton(savedGames[8], skin);
			gameNum8.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					stage.addActor(table);
					gameSelected = "game8";
					showGameSelected.setText(gameSelected);
					// SEND SERVER MESSAGE:
					ClientComm.send("ToggleSetting/ChoseSavedGame/8");
				}
			});
			table.add(gameNum8).spaceRight(7).uniform();
		}
		if (9<=numSavedGames-1) {
			//System.out.println(savedGames[i]);
			TextButton gameNum9 = new TextButton(savedGames[9], skin);
			gameNum9.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					stage.addActor(table);
					gameSelected = "game9";
					showGameSelected.setText(gameSelected);
					// SEND SERVER MESSAGE:
					ClientComm.send("ToggleSetting/ChoseSavedGame/9");
				}
			});
			table.add(gameNum9).spaceRight(7).uniform();
		}
		if (10<=numSavedGames-1) {
			//System.out.println(savedGames[i]);
			TextButton gameNum10 = new TextButton(savedGames[10], skin);
			gameNum10.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					stage.addActor(table);
					gameSelected = "game10";
					showGameSelected.setText(gameSelected);
					// SEND SERVER MESSAGE:
					ClientComm.send("ToggleSetting/ChoseSavedGame/10");
				}
			});
			table.add(gameNum10).spaceRight(7).uniform();
		}
		if (11<=numSavedGames-1) {
			//System.out.println(savedGames[i]);
			TextButton gameNum11 = new TextButton(savedGames[11], skin);
			gameNum11.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					stage.addActor(table);
					gameSelected = "game11";
					showGameSelected.setText(gameSelected);
					// SEND SERVER MESSAGE:
					ClientComm.send("ToggleSetting/ChoseSavedGame/11");
				}
			});
			table.add(gameNum11).spaceRight(7).uniform();
		}
		if (12<=numSavedGames-1) {
			//System.out.println(savedGames[i]);
			TextButton gameNum12 = new TextButton(savedGames[12], skin);
			gameNum12.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					stage.addActor(table);
					gameSelected = "game12";
					showGameSelected.setText(gameSelected);
					// SEND SERVER MESSAGE:
					ClientComm.send("ToggleSetting/ChoseSavedGame/12");
				}
			});
			table.add(gameNum12).spaceRight(7).uniform();
		}
		if (13<=numSavedGames-1) {
			//System.out.println(savedGames[i]);
			TextButton gameNum13 = new TextButton(savedGames[13], skin);
			gameNum13.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					stage.addActor(table);
					gameSelected = "game13";
					showGameSelected.setText(gameSelected);
					// SEND SERVER MESSAGE:
					ClientComm.send("ToggleSetting/ChoseSavedGame/13");
				}
			});
			table.add(gameNum13).spaceRight(7).uniform();
		}
		if (14<=numSavedGames-1) {
			//System.out.println(savedGames[i]);
			TextButton gameNum14 = new TextButton(savedGames[14], skin);
			gameNum14.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					stage.addActor(table);
					gameSelected = "game14";
					showGameSelected.setText(gameSelected);
					// SEND SERVER MESSAGE:
					ClientComm.send("ToggleSetting/ChoseSavedGame/14");
				}
			});
			table.add(gameNum14).spaceRight(7).uniform();
		}
		if (15<=numSavedGames-1) {
			//System.out.println(savedGames[i]);
			TextButton gameNum15 = new TextButton(savedGames[15], skin);
			gameNum15.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					stage.addActor(table);
					gameSelected = "game15";
					showGameSelected.setText(gameSelected);
					// SEND SERVER MESSAGE:
					ClientComm.send("ToggleSetting/ChoseSavedGame/15");
				}
			});
			table.add(gameNum15).spaceRight(7).uniform();
		}

	    
	    
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
				// SEND SERVER MESSAGE:
				ClientComm.send("ToggleSetting/Diff/0");
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
				// SEND SERVER MESSAGE:
				ClientComm.send("ToggleSetting/Diff/1");
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
				// SEND SERVER MESSAGE:
				ClientComm.send("ToggleSetting/Diff/2");
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
				// SEND SERVER MESSAGE:
				ClientComm.send("ToggleSetting/Diff/3");
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
				if (mutOpt.equals("No")) {
					bioterroristOpt = "Yes";
					showBioTOpt.setText(bioterroristOpt);
					showPurpleDisease = true;
					// SEND SERVER MESSAGE:
					ClientComm.send("ToggleSetting/Bio/true");
				}
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
				// SEND SERVER MESSAGE:
				ClientComm.send("ToggleSetting/Bio/false");
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
				// SEND SERVER MESSAGE:
				ClientComm.send("ToggleSetting/Otb/true");
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
				// SEND SERVER MESSAGE:
				ClientComm.send("ToggleSetting/Otb/true");
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
				// SEND SERVER MESSAGE:
				ClientComm.send("ToggleSetting/Vir/true");
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
				// SEND SERVER MESSAGE:
				ClientComm.send("ToggleSetting/Vir/false");
			}
		});
		table.add(virN);
		
		
		table.row();
		Label mutLabel = new Label("Mutation Expansion:", skin);
		mutLabel.setAlignment(Align.center); // Align center
		table.add(mutLabel).spaceBottom(10);
		table.add(mutOptLabel);
		TextButton mutY = new TextButton("YES", skin);
		mutY.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (bioterroristOpt.equals("No")) {
					stage.addActor(table);
					mutOpt = "Yes";
					mutOptLabel.setText(mutOpt);
					showPurpleDisease = true;
					// SEND SERVER MESSAGE:
					ClientComm.send("ToggleSetting/Mut/true");
				}
			}
		});
		table.add(mutY);
		TextButton mutN = new TextButton("NO", skin);
		mutN.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				stage.addActor(table);
				mutOpt = "No";
				mutOptLabel.setText(mutOpt);
				// SEND SERVER MESSAGE:
				ClientComm.send("ToggleSetting/Mut/false");
			}
		});
		table.add(mutN);
		
		
		
		table.row();
		
		for (int i=0; i<numPlayers; i++){
		    Label labelL = new Label(players[i].getName() + ", ready?", skin);
		    labelL.setAlignment(Align.center); // Align
		    labelL.setColor(Color.WHITE);

		    table.row();

		    table.add(labelL).width(150).height(55).spaceBottom(10);;
		    table.setFillParent(true);
		    
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
				ClientComm.send("ToggleReady");
				
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
								parent.changeScreen( Screens.GAME );
								//parent.changeScreen( PandemicGame.Screens.GAME_DEBUG );	
							}
							else if ( message[0].equalsIgnoreCase( "UpdateSetting") )
							{
								if( message[1].equalsIgnoreCase( "ReceiveGames" )  )
								{
									for( int i = 2; i < message.length; i++ )
									{
										numSavedGames = i;
										savedGames[i-2] = message[i];
									}
								}
								else if( message[1].equalsIgnoreCase( "BioOption" )  )
								{
									for( int i = 2; i < message.length; i++ )
									{
										if (message[i].equals("true")) {
											bioterroristOpt = "Yes";
										}
										else if (message[i].equals("false")) {
											bioterroristOpt = "No";
										}
									}
								}
								else if( message[1].equalsIgnoreCase( "DiffOption" )  )
								{
									for( int i = 2; i < message.length; i++ )
									{
										if (message[i].equals("0")) {
											diffOpt = 0;
											difficulty = "0";
										}
										else if (message[i].equals("1")) {
											diffOpt = 1;
											difficulty = "1";
										}
										else if (message[i].equals("2")) {
											diffOpt = 2;
											difficulty = "2";
										}
										else if (message[i].equals("3")) {
											diffOpt = 3;
											difficulty = "3";
										}
									}
								}
								else if( message[1].equalsIgnoreCase( "VirOption" )  )
								{
									for( int i = 2; i < message.length; i++ )
									{
										if (message[i].equals("true")) {
											virOpt = "Yes";
										}
										else if (message[i].equals("false")) {
											virOpt = "No";
										}
									}
								}
								else if( message[1].equalsIgnoreCase( "OtbOption" )  )
								{
									for( int i = 2; i < message.length; i++ )
									{
										if (message[i].equals("true")) {
											otbOpt = "Yes";
										}
										else if (message[i].equals("false")) {
											otbOpt = "No";
										}
									}
								}
								else if( message[1].equalsIgnoreCase( "MutOption" )  )
								{
									for( int i = 2; i < message.length; i++ )
									{
										if (message[i].equals("true")) {
											mutOpt = "Yes";
										}
										else if (message[i].equals("false")) {
											mutOpt = "No";
										}
									}
								}
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
	
	
	public class JoinGameTextInput2 implements TextInputListener 
	{
		public boolean valid = false;
		
		String IP;
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
				valid = validIP;
			}
			
			if( validIP )
			{
				IP = text;
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
		    				JoinGameTextInput2 listener = new JoinGameTextInput2();
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
