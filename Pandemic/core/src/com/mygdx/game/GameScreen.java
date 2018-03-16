package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.Angular;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MenuScreen.JoinGameTextInput;
import com.mygdx.game.Actions.Action;
import com.sun.xml.internal.ws.assembler.dev.ServerTubelineAssemblyContext;


public class GameScreen implements Screen {
	final static float nodeSize = 17.50f;
	final static float pawnXSize = 15.0f;
	final static float pawnYSize = pawnXSize * 2f;
	final static float cubeSize = 25.f;
	final static float cubeOrbitOffset = 2.5f;
	final static float cubeOrbitRate = 0.5f;
	final static float actionButtonXSize = 200f;
	final static float actionButtonYSize = 50f;
	final static float actionButtonFontScaleFactor = 0.75f;
	final static float playerCardXSize = 100.0f;
	final static float playerCardYSize = playerCardXSize*1.5f;
	final static float playerCardGap = 5.0f;
	final static float playerCardXOffset = 25.f;
	final static float playerCardYOffset = 45.f;
	final static float handPanelXSize = 800.f;
	final static float handPanelYSize = 150.f;
	final static float handButtonXOffset = 15f;
	final static float handButtonYOffset = 15f;
	final static float handButtonXSize = 50f;
	final static float handButtonYSize = 25f;
	
	final static int[] infectionRateTrack = { 2, 2, 2, 3, 3, 4 ,4 };
	
	static float[][] positions = new float[][]
			{
				new float[]{ -0.5f, 0.275f }, //Atlanta
				new float[]{ -0.55f, 0.45f }, //Chicago
				new float[]{ 0.035f, 0.575f }, //Essen
				new float[]{ -0.09f, 0.55f }, //London
				new float[]{ -0.11f, 0.35f }, //Madrid
				new float[]{ 0.0825f, 0.475f }, //Milan
				new float[]{ -0.35f, 0.445f }, //NYC
				new float[]{ 0.0f, 0.45f }, //Paris
				new float[]{ -0.7f, 0.375f }, //San Francisco
				new float[]{ 0.16f, 0.615f }, //St Petersburg
				new float[]{ -0.425f, 0.455f }, //Toronto
				new float[]{ -0.4f, 0.3f }, //Washington
				new float[]{ -0.425f, -0.1f }, //Bogota
				new float[]{ -.335f, -0.575f }, //Buenos Aires
				new float[]{ 0.13f, -0.515f }, //Johannesburg
				new float[]{ 0.18f, -0.05f }, //Khartoum
				new float[]{ 0.06f, -0.26125f }, //Kinshasa
				new float[]{ -0.02f, -0.0725f }, //Lagos
				new float[]{ -0.485f, -0.35f }, //Lima
				new float[]{ -0.675f, 0.175f }, //Los Angeles
				new float[]{ -0.56f, 0.09f }, //Mexico City
				new float[]{ -0.42f, 0.12f }, //Miami
				new float[]{ -0.455f, -0.55f }, //Santiago
				new float[]{ -0.255f, -0.41f }, //Sao Paolo
				new float[]{ 0.0175f, 0.275f }, //Algiers
				new float[]{ 0.1275f, 0.375f }, //Istanbul
				new float[]{ 0.11f, 0.215f }, //Cairo
				new float[]{ 0.24f, 0.478f }, //Moscow
				new float[]{ 0.3f, 0.415f }, //Tehran
				new float[]{ 0.22f, 0.255f }, //Baghdad
				new float[]{ 0.24f, 0.08f }, //Riyahd
				new float[]{ 0.34f, 0.2f }, //Karachi
				new float[]{ 0.35f, 0.06f }, //Mumbai
				new float[]{ 0.43f, 0.22f }, //Dehli
				new float[]{ 0.5f, 0.17f }, //Kolkata
				new float[]{ 0.45f, -0.06f }, //Chennai
				new float[]{ 0.59f, 0.45f }, //Beijing
				new float[]{ 0.69f, 0.46f }, //Seoul
				new float[]{ 0.58f, 0.32f }, //Shanghai
				new float[]{ 0.78f, 0.37f }, //Tokyo
				new float[]{ 0.79f, 0.25f }, //Osaka
				new float[]{ 0.72f, 0.18f }, //Taipei
				new float[]{ 0.60f, 0.12f }, //Hong Kong
				new float[]{ 0.535f, 0.04f }, //Bangkok
				new float[]{ 0.60f, -0.12f }, //Ho Chi Min City
				new float[]{ 0.5f, -0.20f }, //Jakarta
				new float[]{ 0.73f, -0.13f }, //Manilla
				new float[]{ 0.8f, -0.55f } //Sydney
			};
	static String[][] names = new String[][]
	{
		new String[] {"Atlanta","blue","Chicago","Washington","Miami"},	
		new String[] {"Chicago","blue","San Francisco","Los Angeles","Atlanta","Toronto"},
		new String[] {"Essen","blue","London","St. Petersburg","Milan","Paris"},
		new String[] {"London","blue","New York","Essen","Madrid","Paris"},
		new String[] {"Madrid","blue","New York","Sao Paolo","Algiers","London","Paris"},
		new String[] {"Milan","blue","Essen","St. Petersburg","Istanbul","Paris"},
		new String[] {"New York","blue","Toronto","Washington","Madrid","London"},
		new String[] {"Paris","blue","London","Essen","Milan","Algiers","Madrid"},
		new String[] {"San Francisco","blue","Chicago","Los Angeles", "Manila", "Tokyo"},
		new String[] {"St. Petersburg","blue","Essen","Moscow","Istanbul","Milan"},
		new String[] {"Toronto","blue","Chicago","New York","Washington"},
		new String[] {"Washington","blue","New York","Toronto","Atlanta","Miami"},
		new String[] {"Bogota","yellow","Miami","Mexico City","Lima","Buenos Aires","Sao Paolo"},
		new String[] {"Buenos Aires","yellow","Sao Paolo","Bogota"},
		new String[] {"Johannesburg","yellow","Kinshasa","Khartoum"},
		new String[] {"Khartoum","yellow","Johannesburg","Kinshasa","Lagos","Cairo"},
		new String[] {"Kinshasa","yellow","Lagos","Khartoum","Johannesburg"},
		new String[] {"Lagos","yellow","Sao Paolo","Kinshasa","Khartoum"},
		new String[] {"Lima","yellow","Mexico City","Bogota","Santiago"},
		new String[] {"Los Angeles","yellow","San Francisco","Chicago","Mexico City", "Sydney"},
		new String[] {"Mexico City","yellow","Los Angeles","Chicago","Miami","Bogota","Lima"},
		new String[] {"Miami","yellow","Bogota","Mexico City","Atlanta","Washington"},
		new String[] {"Santiago","yellow","Lima"},
		new String[] {"Sao Paolo","yellow","Buenos Aires","Bogota","Madrid","Lagos"},
		new String[] {"Algiers","black","Madrid","Paris","Istanbul","Cairo"},
		new String[] {"Istanbul","black","Milan","Moscow","Baghdad","Cairo","Algiers","St. Petersburg"},
		new String[] {"Cairo","black","Algiers","Istanbul","Baghdad","Riyadh","Khartoum"},
		new String[] {"Moscow","black","St. Petersburg","Tehran","Istanbul"},
		new String[] {"Tehran","black","Moscow","Delhi","Baghdad","Karachi"},
		new String[] {"Baghdad","black","Istanbul","Tehran","Karachi","Riyadh","Cairo"},
		new String[] {"Riyadh","black","Karachi","Baghdad","Cairo"},
		new String[] {"Karachi","black","Mumbai","Riyadh","Baghdad","Tehran","Delhi"},
		new String[] {"Mumbai","black","Karachi","Delhi","Chennai"},
		new String[] {"Delhi","black","Tehran","Karachi","Mumbai","Kolkata","Chennai"},
		new String[] {"Kolkata","black","Delhi","Chennai","Bangkok","Hong Kong"},
		new String[] {"Chennai","black","Mumbai","Delhi","Kolkata","Bangkok","Jakarta"},
		new String[] {"Beijing","red","Shanghai","Seoul"},
		new String[] {"Seoul","red","Beijing","Shanghai","Tokyo"},
		new String[] {"Shanghai","red","Beijing","Seoul","Tokyo","Taipei","Hong Kong"},
		new String[] {"Tokyo","red","Seoul","Shanghai","Osaka", "San Francisco"},
		new String[] {"Osaka","red","Tokyo","Taipei"},
		new String[] {"Taipei","red","Osaka","Shanghai","Hong Kong","Manila"},
		new String[] {"Hong Kong","red","Kolkata","Bangkok","Shanghai","Taipei","Ho Chi Minh City"},
		new String[] {"Bangkok","red","Kolkata","Hong Kong","Chennai","Ho Chi Minh City","Jakarta"},
		new String[] {"Ho Chi Minh City","red","Jakarta","Bangkok","Hong Kong","Manila"},
		new String[] {"Jakarta","red","Chennai","Bangkok","Ho Chi Minh City","Sydney"},
		new String[] {"Manila","red","Sydney","Ho Chi Minh City","Hong Kong","Taipei", "San Francisco"},
		new String[] {"Sydney","red","Jakarta","Manila", "Los Angeles" }
	};
	

	Texture[] infectRateCircles = new Texture[] 
	{
		new Texture(Gdx.files.internal("infectRate2.png")),
		new Texture(Gdx.files.internal("infectRate2.png")),
		new Texture(Gdx.files.internal("infectRate2.png")),
		new Texture(Gdx.files.internal("infectRate3.png")),
		new Texture(Gdx.files.internal("infectRate3.png")),
		new Texture(Gdx.files.internal("infectRate4.png")),
		new Texture(Gdx.files.internal("infectRate4.png"))	
	};
	
	Texture[] outbreakRateCircles = new Texture[] 
	{
		new Texture(Gdx.files.internal("outBreak.png")),
		new Texture(Gdx.files.internal("outBreak.png")),
		new Texture(Gdx.files.internal("outBreak.png")),
		new Texture(Gdx.files.internal("outBreak.png")),
		new Texture(Gdx.files.internal("outBreak.png")),
		new Texture(Gdx.files.internal("outBreak.png")),
		new Texture(Gdx.files.internal("outBreak.png")),
		new Texture(Gdx.files.internal("outBreak.png"))
	};
	
	Texture greyBarOfNumbers = new Texture(Gdx.files.internal("numbersBar.png"));
	Texture deck = new Texture(Gdx.files.internal("faceDownCard.png"));
	Texture yellow = new Texture(Gdx.files.internal("YellowDiseaseCube.png"));
	Texture red = new Texture(Gdx.files.internal("RedDiseaseCube.png"));
	Texture blue = new Texture(Gdx.files.internal("BlueDiseaseCube.png"));
	Texture black = new Texture(Gdx.files.internal("BlackDiseaseCube.png"));
	
	public static ClientComm clientComm;
	
	static CityNode[] cityNodes;
	static PlayerInfo[] players;
	static PlayerInfo clientPlayer;
	static PlayerInfo currentPlayer;
	static int outbreaks = 0;
	static int currentInfectionRateIdx = 0;
	static int actionsRemaining = 4;
	static boolean turnEnded = false;
	
	static PandemicGame parent;

	static Texture[] playerTextures;
	static Texture[] diseaseCubeTextures;
	static Texture[] cityCardTextures;
	
	static Texture	handPanelTexture;
	static float windWidth, windHeight;
	static Texture background;
	static SpriteBatch batch;
	static Stage buttonStage, pawnStage, diseaseStage;
	static Stage dialogStage;
	static Stage handPanelStage;
	static Table infectionDiscardTable;
	static Table playerDiscardTable;
	static Group buttonGroup, handPanelGroup;
	static Skin skin;
	static Skin altSkin = new Skin( Gdx.files.internal( "plain-james-ui/plain-james-ui.json" ) );
    static Button button;
    static boolean isMyTurn = true;
    static float cubeOrbitRotation = 0;
	static PlayerInfo handShownPlayer;
    
    static ArrayList<Button> cityButtons 			= new ArrayList<Button>();
    static ArrayList<String> infectionDiscardPile	= new ArrayList<String>();
    static ArrayList<String> playerDiscardPile 		= new ArrayList<String>();
    
	
	
	GameScreen( PandemicGame _parent, PlayerInfo[] _players )
	{
		parent 	= _parent;
		players = _players;
		batch 	= new SpriteBatch();
		skin 	= new Skin( Gdx.files.internal( "plain-james-ui/plain-james-ui.json" ) );

		windWidth 	= Gdx.graphics.getWidth();
		windHeight 	= Gdx.graphics.getHeight();

		initGameState();
		initGeneralTextures();

		//TESTER DISEASES
		CityNode diseasedTest = lookupCity( "Tokyo" );
		diseasedTest.addCube( new DiseaseCubeInfo( DiseaseColour.RED ) );
		diseasedTest.addCube( new DiseaseCubeInfo( DiseaseColour.RED ) );
		diseasedTest.addCube( new DiseaseCubeInfo( DiseaseColour.RED ) );
		diseasedTest.addCube( new DiseaseCubeInfo( DiseaseColour.YELLOW ) );
		diseasedTest.addCube( new DiseaseCubeInfo( DiseaseColour.BLUE ) );
		diseasedTest.addCube( new DiseaseCubeInfo( DiseaseColour.BLACK ) );
		/////////////////
		
		//TESTER INFECTION DISCARD////////
		infectionDiscardPile.add("Atlanta");
		infectionDiscardPile.add("Tokyo");
		infectionDiscardPile.add("Johannesburg");
		infectionDiscardPile.add("Tehran");
		//////////////////////////////////
		
		//TESTER PLAYER DISCARD////////
		playerDiscardPile.add("Jakarta");
		playerDiscardPile.add("Santiago");
		playerDiscardPile.add("Karachi");
		playerDiscardPile.add("Beijing");
		//////////////////////////////////
		
		//TESTER CARDS
		int i = 0;
		for ( PlayerInfo player : players )
		{
			if ( player == null ) continue;
			
			player.addCardToHand( new PlayerCardInfo( cityNodes[i] ) );
			i++;
			player.addCardToHand( new PlayerCardInfo( cityNodes[i] ) );
			i++;
			player.addCardToHand( new PlayerCardInfo( cityNodes[i] ) );
			i++;
		}
		//////////////
				
		updateDiseaseStage();
		
		initButtonStage();
		initHandButtonStage();
		
		updatePawnStage();
		
	}
	
	static void initGeneralTextures()
	{
		background 	= new Texture(Gdx.files.internal( "board.png" ) );
		
		playerTextures = new Texture[ PawnColour.values().length ];
		playerTextures[ 0 ] = new Texture( Gdx.files.internal("GreenPlayer.png") );
		playerTextures[ 1 ] = new Texture( Gdx.files.internal("CyanPlayer.png") );
		playerTextures[ 2 ] = new Texture( Gdx.files.internal("PurplePlayer.png") );
		playerTextures[ 3 ] = new Texture( Gdx.files.internal("PinkPlayer.png") );
		playerTextures[ 4 ] = new Texture( Gdx.files.internal("MaroonPlayer.png") );
		playerTextures[ 5 ] = new Texture( Gdx.files.internal("BrownPlayer.png") );
		
		diseaseCubeTextures = new Texture[ DiseaseColour.values().length ];
		diseaseCubeTextures[ 0 ] = new Texture( Gdx.files.internal( "BlueDiseaseCube.png" ) );
		diseaseCubeTextures[ 1 ] = new Texture( Gdx.files.internal( "YellowDiseaseCube.png" ) );
		diseaseCubeTextures[ 2 ] = new Texture( Gdx.files.internal( "BlackDiseaseCube.png" ) );
		diseaseCubeTextures[ 3 ] = new Texture( Gdx.files.internal( "RedDiseaseCube.png" ) );
		
		cityCardTextures = new Texture[ cityNodes.length ];
		for ( int i = 0; i < cityNodes.length; i++)
		{
			CityNode city = cityNodes[ i ]; 
			cityCardTextures[ i ] = new Texture( Gdx.files.internal( "citycards/"+ city.getName() +".png" ) );
		}
		
		handPanelTexture = new Texture( Gdx.files.internal( "handPanel.png") );
	}
	
	static void initGameState()
	{

		for( int i = 0; i < players.length; i++ )
		{
			if( players[i] != null )
			{
				if ( players[i].isClientPlayer() )
				{
					clientPlayer = players[i];
					currentPlayer = clientPlayer; // TEST CURRENT PLAYER
				}
				
				players[i].setCity("Tokyo");
			}
		}
		
		cityNodes = new CityNode[ names.length ];
		for ( int i = 0; i < names.length; i++ )
		{
			cityNodes[ i ] = new CityNode( names[i], positions[i] ); 
		}
		
		for ( int i = 0; i < names.length; i++ )
		{
			cityNodes[ i ].addConnectedCities( cityNodes ); 
		}
	}
	
	static void initHandButtonStage()
	{
		int buttNum = 0;
		for ( final PlayerInfo player : players )
		{
			if ( player == null )
				continue;
			
			String buttText;
			if ( player == clientPlayer ) 
			{
				buttText = "Display your cards";
			}
			else
			{
				buttText = "Display " +player.getColour().name() + " player's cards";
			}
			
			TextButton showCardsButton = new TextButton( buttText, skin );
			showCardsButton.addListener( new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					handShownPlayer = player;
				}
			});
			float x = handButtonXOffset;
			float y = playerCardYOffset + handButtonYOffset + playerCardYSize + ( handButtonYSize + handButtonYOffset ) * buttNum;
			
			
			showCardsButton.setBounds( x, y, 300, 37.5f );
			showCardsButton.setChecked(true);
			buttonGroup.addActor( showCardsButton );
			buttNum++;
			
		}
	}	
			
	static void updatePawnStage()	
	{
		pawnStage = new Stage( new ScreenViewport() );
		
		HashMap<CityNode, ArrayList<PlayerInfo>> countMap = new HashMap<CityNode, ArrayList<PlayerInfo>>();
		for( PlayerInfo player : players )
		{
			if (player != null)
			{
				CityNode occupyingCity = lookupCity( player.getCity() );
				if( countMap.containsKey( occupyingCity ) ) countMap.get( occupyingCity ).add( player );
				else 
				{
					 ArrayList<PlayerInfo> newList = new ArrayList<PlayerInfo>();
					 newList.add(player);
					 countMap.put( occupyingCity, newList );
				}
			}
		}
		
		for ( CityNode curr : countMap.keySet() )
		{
			ArrayList<PlayerInfo> playersAtCurr = countMap.get( curr );
			int numPlayers = playersAtCurr.size();
			float xLeftShift = (numPlayers-1) * (pawnXSize/2) / 2;
			for ( int i = 0; i < playersAtCurr.size(); i++ )
			{
				int x = (int)(curr.getXInWindowCoords(windWidth) + nodeSize/2 - pawnXSize/2 + (i * pawnXSize/2));
				int y = (int)(curr.getYInWindowCoords(windHeight) + nodeSize/2);
				
				
				
				Image pawn = new Image( playerTextures[ playersAtCurr.get( i ).getColour().ordinal() ] );
				pawn.setBounds( x - xLeftShift ,y, pawnXSize, pawnYSize);
				pawnStage.addActor( pawn );
				/*
				final Sprite pawn = new Sprite( playerTextures[ playersAtCurr.get( i ).getColour().ordinal() ] );
				//pawn.setBounds( x + (pawnXSize/2) - xLeftShift, y, pawnXSize, pawnYSize);
				pawn.setBounds( 0, 0, pawnXSize, pawnYSize);
				pawnStage.addActor( new Actor(){
					@Override
					public void draw(Batch batch, float parentAlpha) {
						pawn.draw( batch );
						super.draw(batch, parentAlpha);
					}
				});
				*/
			}
			
		}
	}
	
	static void updateDiseaseStage()
	{
		diseaseStage = new Stage( new ScreenViewport() );
		for ( CityNode curr : cityNodes )
		{
			ArrayList<DiseaseCubeInfo> localCubes = curr.getCubes();
			float arc = (float)(2*Math.PI / localCubes.size());
			for( int i = 0; i < localCubes.size(); i++ )
			{
				int x = (int)(curr.getXInWindowCoords(windWidth) + nodeSize/2 - cubeSize/2 + ( ( nodeSize/2 + cubeOrbitOffset )*Math.cos( arc*i + cubeOrbitRotation ) ) );
				int y = (int)(curr.getYInWindowCoords(windHeight) + nodeSize/2 - cubeSize/2 + ( ( nodeSize/2 + cubeOrbitOffset )*Math.sin( arc*i + cubeOrbitRotation ) ) );

				Image cube = new Image( diseaseCubeTextures[ localCubes.get( i ).getColourIndex() ] );
				cube.setBounds( x ,y, cubeSize, cubeSize);
				diseaseStage.addActor( cube );
			}
		}
	}
	
	static void updateHandPanelStage()
	{	
		handPanelGroup = new Group();
		
		if ( handShownPlayer == null )
			return;
		
		ArrayList<PlayerCardInfo> playerHand = handShownPlayer.getHand();
		int handIdx = 0;
		for( final PlayerCardInfo card : playerHand )
		{
			Texture cardTexture	 							= cityCardTextures[ lookupCityIndex( card.getName() ) ];
			TextureRegion TR_cardTexture 					= new TextureRegion( cardTexture );
			final TextureRegionDrawable Draw_cardTexture 	= new TextureRegionDrawable( TR_cardTexture );
			
			ButtonStyle cityButtonStyle = new ButtonStyle();
			cityButtonStyle.up			= Draw_cardTexture;
			cityButtonStyle.down		= Draw_cardTexture;
			
	        button = new Button( cityButtonStyle );
	        button.addCaptureListener( new InputListener() {
	            @Override
	            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
	            }

	            @Override
	            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
	            }
	        });
	        
	        if( handShownPlayer == clientPlayer && actionsRemaining > 0 && !turnEnded && clientPlayer == currentPlayer)
		    {
		        button.addListener( new ChangeListener() {
		            @Override
					public void changed(ChangeEvent event, Actor actor) {
		            	Dialog confirmFlight = new Dialog("Fly to "+ card.getName() + "?" , skin ){
		            		@Override
		            		protected void result(Object object) {
		            			if ( (boolean) ( object ) )
		            			{
		            				ClientComm.send("DirectFlight/"+card.getName());
		            			}
		        	            Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
		        	            dialogStage = null;
		            		}
		            	};
	
		        		dialogStage = new Stage( new ScreenViewport() );
		        		
		        		confirmFlight.button( "Yes", true );
		        		confirmFlight.button( "No", false );
		        		
		                Gdx.input.setInputProcessor(dialogStage); //Start taking input from the ui
		                confirmFlight.show( dialogStage );
					}
		        });
	        }
	        
			float x = playerCardXOffset + playerCardGap + (handIdx*(playerCardXSize+ playerCardGap) );
			float y = playerCardYOffset/2;
	        button.setBounds(x, y, playerCardXSize, playerCardYSize);
	        handPanelGroup.addActor(button); //Add the button to the stage to perform rendering and take input.
	        handIdx++;
		}
		buttonStage.addActor( handPanelGroup );
		
	}
	
	static void updateCityTouchability()
	{
		ArrayList<CityNode> adjCities = lookupCity( currentPlayer.getCity() ).getConnectedCities();
		for ( CityNode curr : adjCities )
		{
			cityButtons.get( lookupCityIndex( curr.getName() ) ).setTouchable( Touchable.enabled );
		}
	}
	
	static void initButtonStage()
	{

		buttonStage = new Stage(new ScreenViewport()); //Set up a stage for the ui
		buttonGroup = new Group(); //Set up a stage for the ui
		
		createCityButtons();
		createActionButtons();
		
		buttonStage.addActor( buttonGroup );
		
		updateHandPanelStage();
		
        Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
	}
	
	static void createCityButtons()
	{
        for( final CityNode curr : cityNodes )
		{
        	Label cityLabel = new Label( curr.getName().toUpperCase(), skin );
			cityLabel.getStyle().fontColor = Color.WHITE;
        	
			Texture UpDown	 						= new Texture( Gdx.files.internal( curr.getColour() + "City.png") );
			TextureRegion TR_UD 					= new TextureRegion( UpDown );
			final TextureRegionDrawable Draw_UD 	= new TextureRegionDrawable( TR_UD );
			
			Texture Over	 						= new Texture( Gdx.files.internal( curr.getColour() + "CityOver.png") );
			TextureRegion TR_O 						= new TextureRegion( Over );
			final TextureRegionDrawable Draw_O	 	= new TextureRegionDrawable( TR_O );
			
			ButtonStyle cityButtonStyle = new ButtonStyle();
			cityButtonStyle.up			= Draw_UD;
			cityButtonStyle.down		= Draw_UD;
			cityButtonStyle.over 		= Draw_O;
			cityButtonStyle.checkedOver = Draw_UD;
			
	        final Button cityButton = new Button( cityButtonStyle );
	       
	        cityButton.addListener( new ChangeListener() {
	            @Override
				public void changed(ChangeEvent event, Actor actor) {
	            	if ( actionsRemaining > 0 && clientPlayer == currentPlayer )
	            	{
		            	String cityName = curr.getName();
		            	ClientComm.send( "Drive/"+cityName );
		            	// IMPLEMENT Call Drive( CityName ) on Server
	            	}
				}
	        });
	        
			float x = curr.getXInWindowCoords( windWidth );
			float y = curr.getYInWindowCoords( windHeight );
			cityButton.setBounds(x, y, nodeSize, nodeSize);
			cityButton.setTouchable( Touchable.disabled );
			
			
			cityLabel.setFontScale(0.75f);
			cityLabel.setBounds( x - cityLabel.getWidth()/4, y - nodeSize*1.5f, cityLabel.getWidth(), cityLabel.getHeight() );
			buttonGroup.addActor( cityLabel);
			
	        
	        cityButtons.add( cityButton );
	        buttonGroup.addActor(cityButton); //Add the button to the stage to perform rendering and take input.

			cityLabel.getStyle().fontColor = Color.WHITE;
		}
	}
	
	static void createActionButtons()
	{

        skin.getFont( "font").getData().setScale( actionButtonFontScaleFactor, actionButtonFontScaleFactor);
        
        TextButton treatDiseaseButton = new TextButton("Treat Disease", skin);
        treatDiseaseButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)  
        	{
            	if( currentPlayer == clientPlayer && actionsRemaining > 0 )
            	{
	        		CityNode city = lookupCity( clientPlayer.getCity() );
	        		boolean[] coloursPresent = city.getDiseaseColours(); 
	        		
	        		Dialog colourSelect = new Dialog( "Select Disease Colour to Remove",skin ){
	                    protected void result(Object object)
	                    {
	                    	if ( object != null )
	                    	{
	                    		CityNode city = lookupCity( clientPlayer.getCity() );
	                    		ClientComm.send( "TreatDisease/" + city.getColour().toLowerCase() );
	                    		city.removeCubeByColour( (DiseaseColour)object );
	                    	}
	                    	Gdx.input.setInputProcessor( buttonStage );
	                    	dialogStage = null;
	                    }
	                };
	                
	        		for( int i = 0; i < 4; i++ )
	        		{
	        			if ( coloursPresent[i] )
	        			{
	        				colourSelect.button( DiseaseColour.getDiseaseName( DiseaseColour.values()[i] ), DiseaseColour.values()[i] );
	        			}
	        		}
	        		colourSelect.button( "Cancel", null );
	        		dialogStage = new Stage( new ScreenViewport() );
	        		Gdx.input.setInputProcessor( dialogStage );
	        		colourSelect.show( dialogStage );
            	}
        	}
        } );
        
        treatDiseaseButton.setBounds( 0, windHeight-actionButtonYSize, actionButtonXSize, actionButtonYSize);
        buttonGroup.addActor( treatDiseaseButton );
        
        TextButton shareKnowledgeButton = new TextButton("Share Knowledge", skin);
        shareKnowledgeButton.addListener( new ChangeListener() {
        	public void changed(ChangeEvent event, Actor actor) 
        	{
        		if ( currentPlayer == clientPlayer && actionsRemaining > 0)
        		{
        			dialogStage = new Stage( new ScreenViewport() );
        			Skin tempSkin = new Skin( Gdx.files.internal( "skin/uiskin.json" ) );
        			final SelectBox<String> selectBox=new SelectBox<String>(tempSkin);
        			
        			Dialog discardPrompt = new Dialog("Choose a card to Discard",skin){
        				protected void result(Object object){
        					String selected = selectBox.getSelected();
        		            Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
        		            dialogStage = null;
        		            ClientComm.send("ShareKnowledge/"+selected);
        		        }
        			};
        			        			
        			int size = 0;
        			for ( int i = 0; i < players.length; i++ )
        			{
        				if ( players[i] != null )
        					size++;
        			}

        			String[] items = new String[size];
        			size  = 0;
        			for ( int i = 0; i < players.length; i++ )
        			{
        				if ( players[i] != null )
        				{
        					items[ size ] = players[i].getName();
        					size++;
        				}
        			}

        			discardPrompt.setSize(250,150);
        			discardPrompt.setPosition( windWidth / 2 - discardPrompt.getWidth() / 2, windHeight / 2 - discardPrompt.getHeight() / 2 );
        			discardPrompt.button( "Select" );
        			selectBox.setItems( items );
        			discardPrompt.getContentTable().add(selectBox);

        			dialogStage.addActor(discardPrompt);
        			Gdx.input.setInputProcessor(dialogStage);
        		}
        	}
        } );
        
        shareKnowledgeButton.setBounds( 0, windHeight-actionButtonYSize*2.125f, actionButtonXSize, actionButtonYSize);
        buttonGroup.addActor( shareKnowledgeButton );
        
        TextButton showInfectionDiscard = new TextButton( "Show Infection Discard", skin );
        showInfectionDiscard.addListener( new ChangeListener() {
        	public void changed(ChangeEvent event, Actor actor) 
        	{
        		
        		Dialog showInfectionDiscard = new Dialog( "Infection Discardpile", skin ){
        	        protected void result(Object object)
        	        {
        	            Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
        	            dialogStage = null;
        	        }
        		};
        		
        		infectionDiscardTable = new Table( skin );
        		
        		for( String cityName : infectionDiscardPile )
        		{
        			//Label label = new Label( cityName );
        			infectionDiscardTable.add( cityName );
        			infectionDiscardTable.row();
        		}
        		
        		showInfectionDiscard.getContentTable().add( infectionDiscardTable ).height( playerDiscardPile.size() * 35f );;
        		showInfectionDiscard.button( "Close" );
        		dialogStage = new Stage( new ScreenViewport() );
        		showInfectionDiscard.show( dialogStage );
	            Gdx.input.setInputProcessor(dialogStage); //Start taking input from the ui
        	}
        } );
        
        showInfectionDiscard.setBounds( 0, windHeight-actionButtonYSize*3.25f, actionButtonXSize, actionButtonYSize);
        buttonGroup.addActor( showInfectionDiscard );
        
        TextButton showPlayerDiscard = new TextButton( "Show Player Discard", skin );
        showPlayerDiscard.addListener( new ChangeListener() {
        	public void changed(ChangeEvent event, Actor actor) 
        	{
        		
        		Dialog showPlayerDiscard = new Dialog( "Player Discardpile", skin ){
        	        protected void result(Object object)
        	        {
        	            Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
        	            dialogStage = null;
        	        }
        		};

        		
        		playerDiscardTable = new Table( altSkin );
        		
        		for( String cityName : playerDiscardPile )
        		{
        			//Label label = new Label( cityName );
        			playerDiscardTable.add( cityName );
        			playerDiscardTable.row();
        		}
        		
        		showPlayerDiscard.getContentTable().add( playerDiscardTable ).height( playerDiscardPile.size() * 25f );
        		showPlayerDiscard.button( "Close" );
        		dialogStage = new Stage( new ScreenViewport() );
        		showPlayerDiscard.show( dialogStage );
	            Gdx.input.setInputProcessor(dialogStage); //Start taking input from the ui
        	}
        } );
        
        showPlayerDiscard.setBounds( 0, windHeight-actionButtonYSize*4.375f, actionButtonXSize, actionButtonYSize);
        buttonGroup.addActor( showPlayerDiscard );
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		if ( ClientComm.messageQueue != null )
		{
			if ( ClientComm.messageQueue.size() > 0  )
			{
				//Eat message
				if ( ClientComm.possibleActions != null )
				{
					for ( Action a: ClientComm.possibleActions )
					{
						String[] message = clientComm.messageQueue.remove(0);
						if ( message.length > 0 )
						{
							String toExecute = message[0];
							if(a.getName().equals(toExecute))
							{
								a.execute(message);
								break;
							}
						}
					}
				}
			}
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
		batch.begin();
			batch.draw(background, 0, 0, windWidth, windHeight);
		batch.end();
		
		Pixmap connections = new Pixmap( (int)windWidth, (int)windHeight, Pixmap.Format.RGBA8888 );
		connections.setColor( Color.WHITE );
				
		for( CityNode curr : cityNodes )
		{
			int x0 = (int)curr.getXInWindowCoords( windWidth );
			int y0 = (int)curr.getYInWindowCoords( windHeight );
			
			for ( CityNode other : curr.getConnectedCities() )
			{
				int x1 = (int)other.getXInWindowCoords( windWidth );
				int y1 = (int)other.getYInWindowCoords( windHeight );

				connections.drawLine( (int)(x0 + nodeSize/2),  (int)(windHeight-y0 - nodeSize/2),  (int)(x1 + nodeSize/2),  (int)(windHeight-y1 - nodeSize/2));
				//connections.drawLine( (int)curr.getX(), (int)curr.getY(), (int)other.getX(), (int)other.getY());
			}
		}

		Texture connectionsTexture = new Texture( connections );
		connections.dispose();
		
		batch.begin();
			batch.draw(connectionsTexture, 0, 0, windWidth, windHeight);
		batch.end();
		connectionsTexture.dispose();
			
		
		cubeOrbitRotation = (float) (( cubeOrbitRotation - delta*cubeOrbitRate ) % (Math.PI * 2));
		updateDiseaseStage();
		diseaseStage.draw();
		
		updateCityTouchability();
		
		updateHandPanelStage();
		
		if ( currentPlayer == clientPlayer && actionsRemaining > 0 )
			buttonStage.act();
		else if ( currentPlayer == clientPlayer && actionsRemaining <= 0 )
		{
			if ( !turnEnded )
			{
				Dialog endTurnDialog = new Dialog( "No Actions Left", skin ){
			        protected void result(Object object)
			        {
			            Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
			            dialogStage = null;
			        }
				};

				endTurnDialog.button("End Turn");
				dialogStage = new Stage( new ScreenViewport() );
				endTurnDialog.show( dialogStage );
				Gdx.input.setInputProcessor(dialogStage);
				turnEnded = true;
				ClientComm.send("EndTurn");
			}
		}
			
		buttonStage.draw();
		
		pawnStage.draw();
		
		if ( dialogStage != null )
		{
			dialogStage.draw();
			dialogStage.act();
		}
		
		batch.begin();
			batch.draw(greyBarOfNumbers, windWidth*0.35f, windHeight*0.917f, 520f, 150f);
			batch.draw(deck, windWidth*0.378f, windHeight*0.9735f, 20f, 25f);
			batch.draw(yellow, windWidth*0.414f, windHeight*0.968f, 50f, 35f);
			batch.draw(red, windWidth*0.464f, windHeight*0.968f, 50f, 35f);
			batch.draw(blue, windWidth*0.514f, windHeight*0.968f, 50f, 35f);
			batch.draw(black, windWidth*0.564f, windHeight*0.968f, 50f, 35f);
		batch.end();
		
		displayCurrPlayer();
		displayInfectionRate( currentInfectionRateIdx );
		displayOutbreakCounter( outbreaks );
		displayNumbers( 23, 23, 23, 23, 23 );
		displayNumberOfActionsLeft( actionsRemaining );
		displayPlayerColours();
	}

	@Override
	public void resize(int width, int height) {
		windWidth = width;// Gdx.graphics.getWidth();
		windHeight= height;//Gdx.graphics.getHeight();
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	void displayCurrPlayer(){
		
		BitmapFont font = new BitmapFont();
		BitmapFont secondPart = new BitmapFont();
		
//		String str = "";
		
		/*float x = curr.getXInWindowCoords( windWidth * 0.85 );
		float y = curr.getYInWindowCoords( windHeight * 0.1 );*/
		
//		for(int i = 0; i < 1.6 * (currentPlayer.getName().length() + 2); i++){	str += " ";	}
		
		Color[] colors = {
				Color.GREEN,
				Color.CYAN,
				Color.PURPLE,
				Color.PINK,
				Color.MAROON,
				Color.BROWN
		};
		
		currentPlayer.setColour(PawnColour.PINK);
		
		batch.begin();
			font.setColor(colors[currentPlayer.getColour().ordinal()]);
			secondPart.draw(batch, currentPlayer.getName() + "'s Turn", windWidth*0.87f, windHeight*0.98f);
			font.draw(batch, currentPlayer.getName() + "'s", windWidth*0.87f, windHeight*0.98f);
		batch.end();
		
		font.dispose();
		secondPart.dispose();
	}
	
	void displayInfectionRate(int position){
		
		BitmapFont font = new BitmapFont();
				
				if(position >= 0 && position <= 2){
					infectRateCircles[position] = new Texture(Gdx.files.internal("infectRate2Green.png"));
				}
				else if(position > 2 && position <= 4){
					infectRateCircles[position] = new Texture(Gdx.files.internal("infectRate3Green.png"));
				}
				else if(position > 4 && position <= 6){
					infectRateCircles[position] = new Texture(Gdx.files.internal("infectRate4Green.png"));
				}
				else{
					System.out.println("Invalid position");
				}
				
				batch.begin();
				
				font.setColor(Color.GREEN);
				
				font.draw(batch, "Infection Rate:", windWidth * 0.765f, windHeight * 0.12f);
				
				for(int i = 0; i < infectRateCircles.length; i++){			
					
					batch.draw(infectRateCircles[i], windWidth * (float)(0.82 + (i * 2.3 * 0.01)), windHeight * 0.1f, 40f, 40f);
					
				}
				
				batch.end();
				
				infectRateCircles[position].dispose();
			}

	void displayOutbreakCounter(int position){
		
		BitmapFont font = new BitmapFont();
		
		for(int i = 0; i <= position; i++){
			if(position >= 0 && position <= 7){
				outbreakRateCircles[i] = new Texture(Gdx.files.internal("outBreakLogo.png"));
			}
			else{
				System.out.println("Invalid position");
				return;
			}
		}
		
		batch.begin();
		
		font.setColor(Color.RED);
		
		font.draw(batch, "Number Of OutBreaks:", windWidth * 0.685f, windHeight * 0.06f);
		
		for(int i = 0; i < outbreakRateCircles.length; i++){			
			
			batch.draw(outbreakRateCircles[i], windWidth * (float)(0.77 + (i * 2.6 * 0.01)), windHeight * 0.035f, 50f, 50f);
			
		}
		
		batch.end();
		
		outbreakRateCircles[position].dispose();
	}
	
	void displayNumbers(int deckNo, int yellowNo, int redNo, int blueNo, int blackNo){
		
		BitmapFont deckFont = new BitmapFont();
		BitmapFont yellowFont = new BitmapFont();
		BitmapFont redFont = new BitmapFont();
		BitmapFont blueFont = new BitmapFont();
		BitmapFont blackFont = new BitmapFont();
		
		
		
		batch.begin();
		
			deckFont.draw(batch, Integer.toString(deckNo)+",", windWidth*0.3925f, windHeight*0.99f);
			yellowFont.draw(batch, Integer.toString(yellowNo)+",", windWidth*0.436f, windHeight*0.99f);
			redFont.draw(batch, Integer.toString(redNo)+",", windWidth*0.485f, windHeight*0.99f);
			blueFont.draw(batch, Integer.toString(blueNo)+",", windWidth*0.538f, windHeight*0.99f);
			blackFont.draw(batch, Integer.toString(blackNo)+",", windWidth*0.588f, windHeight*0.99f);
		
		batch.end();
		
		deckFont.dispose();
		yellowFont.dispose();
		redFont.dispose();
		blueFont.dispose();
		blackFont.dispose();
		
	}
	
	void displayNumberOfActionsLeft(int remActions){
		
		BitmapFont font = new BitmapFont();
		
		batch.begin();
		font.setColor(Color.RED);
		font.draw(batch, "Remaining Actions: " + Integer.toString(remActions), windWidth*0.87f, windHeight*0.95f);
		batch.end();
		
		font.dispose();
	}
	
	void displayPlayerColours(){
		BitmapFont playersColores = new BitmapFont();
		BitmapFont colourToBeDrawn = new BitmapFont();
		
		Color[] colors = {
				Color.GREEN,
				Color.CYAN,
				Color.PURPLE,
				Color.PINK,
				Color.MAROON,
				Color.BROWN
		};
//		String.valueOf(color.getRGB())
		batch.begin();
		for(int i = 0; i < players.length; i++){
			if(players[i] != null){
				colourToBeDrawn.setColor(colors[players[i].getColour().ordinal()]);
				colourToBeDrawn.getData().setScale(1.25f);
				colourToBeDrawn.draw(batch, players[i].getName() + " is " + String.valueOf((players[i].getColour())), windWidth * 0.01f, windHeight - (50f * 1.125f * 4f) - (float)((i+2) * 25f));
				playersColores.getData().setScale(1.25f);
				playersColores.draw( batch, players[i].getName() + " is ", windWidth * 0.01f, windHeight - (50f * 1.125f * 4f) - (float)((i+2) * 25f));
			}
		}
		batch.end();
		
		playersColores.dispose();
		
		colourToBeDrawn.dispose();
	} 

	
	public static CityNode lookupCity( String name )
	{
		for( CityNode curr : cityNodes )
		{
			if ( curr.getName().equals( name ) ) return curr;
		}
		return null;
	}
	
	public static int lookupCityIndex( String name )
	{
		for( int i = 0; i < cityNodes.length; i++ )
		{
			CityNode curr = cityNodes[i];
			if ( curr.getName().equals( name ) ) return i;
		}
		return -1;
	}
	
	public static PlayerInfo lookupPlayer( String name )
	{
		for ( PlayerInfo player : players )
		{
			if ( player == null )
				continue;
			
			if ( player.getName().equals( name ) )
				return player;
		}
		return null;
	}
	
	public static DiseaseColour lookupDisease( String name )
	{
		switch( name )
		{
			case "blue":
				return DiseaseColour.BLUE;
			
			case "red":
				return DiseaseColour.BLUE;

			case "yellow":
				return DiseaseColour.YELLOW;

			case "black":
				return DiseaseColour.BLACK;
				
			default:
				return null;
		}
	}
	
	public static void requestConsent( String message )
	{
		Dialog consentRequest = new Dialog( message, skin ){
	        protected void result(Object object)
	        {
	        	if ( (boolean) object )
	        	{
	        		ClientComm.send("data/true");	        	
	        	}
	        	else
	        	{
	        		ClientComm.send("data/false");	    
	        	}

	            Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
	            dialogStage = null;
	        }
		};
		
		dialogStage = new Stage( new ScreenViewport() );
		consentRequest.button( "Accept", true );
		consentRequest.button( "Refuse", false );
		
        Gdx.input.setInputProcessor(dialogStage); //Start taking input from the ui
		consentRequest.show( dialogStage );
		
	}

	public static void UpdatePlayerLocation( String PlayerName, String CityName)
	{
		PlayerInfo player = lookupPlayer( PlayerName );
		
		if ( player != null )
			player.setCity( CityName );
	}
	
	public static void RemoveCube(String CityName, String DiseaseColor, String Number)
	{
		CityNode city = lookupCity( CityName );
		DiseaseColour disease = lookupDisease( DiseaseColor );
		
		if ( city != null && disease != null )
			city.removeCubeByColour( disease );
	}
	
	public static void AskForConsent()
	{
		requestConsent("Allow share knowledge");
	}
	
	public static void AskForDiscard()
	{
		dialogStage = new Stage( new ScreenViewport() );
		Skin tempSkin = new Skin( Gdx.files.internal( "skin/uiskin.json" ) );
		final SelectBox<String> selectBox=new SelectBox<String>(tempSkin);
		
		Dialog discardPrompt = new Dialog("Choose a card to Discard",skin){
			protected void result(Object object){
				String selected = selectBox.getSelected();
	            Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
	            dialogStage = null;
	            ClientComm.send("data/"+selected);
	        }
		};
		
		String[] items = new String[ currentPlayer.getHandSize() ] ;
		
		for ( int i = 0; i < currentPlayer.getHandSize(); i++ )
		{
			PlayerCardInfo card = currentPlayer.getHand().get( i );
			items[i] = card.getName();
		}

		discardPrompt.setSize(250,150);
		discardPrompt.setPosition( windWidth / 2 - discardPrompt.getWidth() / 2, windHeight / 2 - discardPrompt.getHeight() / 2 );
		discardPrompt.button( "Select" );
		selectBox.setItems( items );
		discardPrompt.getContentTable().add(selectBox);

		dialogStage.addActor(discardPrompt);
		Gdx.input.setInputProcessor(dialogStage);
		
	}
	
	public static void RemoveCardFromHand( String PlayerName, String CardName, String DiscardBool)
	{
		PlayerInfo player = lookupPlayer( PlayerName );
		
		if ( player != null )
		{
			player.removeCardFromHand( CardName );
			if ( Boolean.valueOf( DiscardBool ) )
				playerDiscardPile.add( CardName );
		}
		
	}
	
	public static void AddCardToHand( String PlayerName, String CardName)
	{
		PlayerInfo player = lookupPlayer( PlayerName );
		
		if ( player != null )
		{
			player.addCardToHand( new PlayerCardInfo( CardName ) );
		}
	}
	
	public static void AddDiseaseCubeToCity( String CityName, String DiseaseColor)
	{
		CityNode city = lookupCity( CityName );
		DiseaseColour disease = lookupDisease( DiseaseColor );
		
		if ( city != null && disease != null )
			city.addCube( new DiseaseCubeInfo( disease ) );
	}
	
	public static void AddInfectionCardToDiscard( String InfectionCardName )
	{
		infectionDiscardPile.add( InfectionCardName );
	}
	
	public static void IncOutbreakCounter()
	{
		outbreaks++;
	}
	
	public static void IncInfectionRate()
	{
		Dialog notifyEpidemic = new Dialog( "An Epidemic has Occured", skin ){
	        protected void result(Object object)
	        {
	            Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
	            dialogStage = null;
	        }
		};
		notifyEpidemic.setColor( Color.GREEN );

		notifyEpidemic.button("Okay");
		
		
		dialogStage = new Stage( new ScreenViewport() );
		notifyEpidemic.show( dialogStage );
		Gdx.input.setInputProcessor(dialogStage);
		
		currentInfectionRateIdx++;
	}
	
	public static void ClearInfectionDiscard()
	{
		infectionDiscardPile.clear();
	}
	
	public static void NotifyTurn( String PlayerName, String isTurnClient )
	{
		PlayerInfo player = lookupPlayer( PlayerName );
		
		if ( player != null )
		{
			if ( currentPlayer != null )
			{
				currentPlayer = player;
				if( clientPlayer == currentPlayer )
				{
					Dialog notifyTurnDialog = new Dialog( "It's now your turn", skin ){
				        protected void result(Object object)
				        {
				            Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
				            dialogStage = null;
				        }
					};

					notifyTurnDialog.button("Okay");
					dialogStage = new Stage( new ScreenViewport() );
					notifyTurnDialog.show( dialogStage );
					Gdx.input.setInputProcessor(dialogStage);
					turnEnded = false;
				}
			}
		}
	}
	
	public static void StartGame()
	{
		
	}
	
	public static void DecrementActions()
	{
		actionsRemaining--;
	}
	
	public static void ResetActions()
	{
		actionsRemaining = 4;
	}

}
