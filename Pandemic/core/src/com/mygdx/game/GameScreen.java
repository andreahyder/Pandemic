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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.Angular;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MenuScreen.JoinGameTextInput;

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
	final static float handPanelXOffset = 25.f;
	final static float handPanelYOffset = 45.f;
	final static float handPanelXSize = 800.f;
	final static float handPanelYSize = 150.f;
	
	
	
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
		new String[] {"San Francisco","blue","Chicago","Los Angeles"},
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
		new String[] {"Los Angeles","yellow","San Francisco","Chicago","Mexico City"},
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
		new String[] {"Tokyo","red","Seoul","Shanghai","Osaka"},
		new String[] {"Osaka","red","Tokyo","Taipei"},
		new String[] {"Taipei","red","Osaka","Shanghai","Hong Kong","Manila"},
		new String[] {"Hong Kong","red","Kolkata","Bangkok","Shanghai","Taipei","Ho Chi Minh City"},
		new String[] {"Bangkok","red","Kolkata","Hong Kong","Chennai","Ho Chi Minh City","Jakarta"},
		new String[] {"Ho Chi Minh City","red","Jakarta","Bangkok","Hong Kong","Manila"},
		new String[] {"Jakarta","red","Chennai","Bangkok","Ho Chi Minh City","Sydney"},
		new String[] {"Manila","red","Sydney","Ho Chi Minh City","Hong Kong","Taipei"},
		new String[] {"Sydney","red","Jakarta","Manila"}
	};
	
	static CityNode[] cityNodes;
	static PlayerInfo[] players;
	static PlayerInfo clientPlayer;
	
	PandemicGame parent;

	Texture[] playerTextures;
	Texture[] diseaseCubeTextures;
	Texture[] cityCardTextures;
	Texture	handPanelTexture;
	float windWidth, windHeight;
	Texture background;
	SpriteBatch batch;
	Stage buttonStage, pawnStage, diseaseStage, dialogStage, handPanelStage;
	Group buttonGroup, handPanelGroup;
	Skin skin;
    Button button;
    
    boolean isMyTurn = true;
    float cubeOrbitRotation = 0;
	PlayerInfo handShownPlayer;
	
	
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
		
		//TESTER CARDS
		clientPlayer.addCardToHand( new PlayerCardInfo( cityNodes[47] ) );
		clientPlayer.addCardToHand( new PlayerCardInfo( cityNodes[23] ) );
		clientPlayer.addCardToHand( new PlayerCardInfo( cityNodes[18] ) );
		clientPlayer.addCardToHand( new PlayerCardInfo( cityNodes[14] ) );
		//////////////
		
		updateDiseaseStage();
		
		initButtonStage();
		
		updatePawnStage();
		
		requestConsent("Allow share knowledge");
	}
	
	void initGeneralTextures()
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
	
	void initGameState()
	{

		for( int i = 0; i < players.length; i++ )
		{
			if( players[i] != null )
			{
				if ( players[i].isClientPlayer() )
					clientPlayer = players[i];
				
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
	
	void updatePawnStage()
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
	
	void updateDiseaseStage()
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
	
	void updateHandPanelStage()
	{
		handPanelStage = new Stage( new ScreenViewport() );
		ArrayList<PlayerCardInfo> playerHand = clientPlayer.getHand();
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
	        

	        button.addListener( new ChangeListener() {
	            @Override
				public void changed(ChangeEvent event, Actor actor) {
				}
	        });
	        
			float x = handPanelXOffset + playerCardGap + (handIdx*(playerCardXSize+ playerCardGap) );
			float y = handPanelYOffset/2;
	        button.setBounds(x, y, playerCardXSize, playerCardYSize);
	        handPanelStage.addActor(button); //Add the button to the stage to perform rendering and take input.
	        handIdx++;
		}
		buttonStage.addActor( handPanelGroup );
	}
	
	void initButtonStage()
	{

		buttonStage = new Stage(new ScreenViewport()); //Set up a stage for the ui
		buttonGroup = new Group(); //Set up a stage for the ui
		
		createCityButtons();
		createActionButtons();
		createPlayerHandButtons();
		
		buttonStage.addActor( buttonGroup );
		
        Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
	}
	
	void createCityButtons()
	{
        for( final CityNode curr : cityNodes )
		{
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
			cityButtonStyle.checkedOver = Draw_O;
			
	        button = new Button( cityButtonStyle );
	        button.addCaptureListener( new InputListener() {
	            @Override
	            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
	                button.setBackground(Draw_O);
	            }

	            @Override
	            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
	                button.setBackground(Draw_UD);
	            }
	        });
	        

	        button.addListener( new ChangeListener() {
	            @Override
				public void changed(ChangeEvent event, Actor actor) {
	            	String cityName = curr.getName();
	            	// RealTODO: Call Drive( CityName ) on Server
				}
	        });
	        
			float x = curr.getXInWindowCoords( windWidth );
			float y = curr.getYInWindowCoords( windHeight );
	        button.setBounds(x, y, nodeSize, nodeSize);
	        buttonGroup.addActor(button); //Add the button to the stage to perform rendering and take input.
		}
	}
	
	void createActionButtons()
	{

        skin.getFont( "font").getData().setScale( actionButtonFontScaleFactor, actionButtonFontScaleFactor);
        
        TextButton treatDiseaseButton = new TextButton("Treat Disease", skin);
        treatDiseaseButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)  
        	{
        		CityNode city = lookupCity( clientPlayer.getCity() );
        		boolean[] coloursPresent = city.getDiseaseColours(); 
        		
        		Dialog colourSelect = new Dialog( "Select Disease Colour to Remove",skin ){
                    protected void result(Object object)
                    {
                    	if ( object != null )
                    	{
                    		CityNode city = lookupCity( clientPlayer.getCity() );
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
        } );
        
        treatDiseaseButton.setBounds( 0, windHeight-actionButtonYSize, actionButtonXSize, actionButtonYSize);
        buttonGroup.addActor( treatDiseaseButton );
        
        TextButton shareKnowledgeButton = new TextButton("Share Knowledge", skin);
        shareKnowledgeButton.addListener( new ChangeListener() {
        	public void changed(ChangeEvent event, Actor actor) 
        	{
        		
        	}
        } );
        
        shareKnowledgeButton.setBounds( 0, windHeight-actionButtonYSize*2.125f, actionButtonXSize, actionButtonYSize);
        buttonGroup.addActor( shareKnowledgeButton );
        
	}
	
	void createPlayerHandButtons()
	{
		//Create clientPlayer Hand
		
		//////////////////////////
	}
	
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
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
		
		if ( isMyTurn )
			buttonStage.act();
		buttonStage.draw();
		
		pawnStage.draw();
		
		if ( dialogStage != null )
		{
			dialogStage.draw();
			dialogStage.act();
		}
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
	
	public void requestConsent( String message )
	{
		Dialog consentRequest = new Dialog( message, skin ){
	        protected void result(Object object)
	        {
	        	if ( (boolean) object )
	        	{
	        		//send consent: true 
	        	}
	        	else
	        	{
	        		//send consent: false
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
}
