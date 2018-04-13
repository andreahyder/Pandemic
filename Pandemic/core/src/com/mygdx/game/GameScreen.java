package com.mygdx.game;

import java.security.DigestInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
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
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
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
	final static float researchStationYSize = 35f;
	final static float researchStationXSize = 40f;
	final static float markerXSize = 35f;
	final static float markerYSize = 60f;
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



	static ClientComm clientComm;
	
	static HashMap<String,DiseaseStatus> diseaseStatuses = new HashMap<String, DiseaseStatus>();

	static CityNode[] cityNodes;
	static PlayerInfo[] players;
	static PlayerInfo clientPlayer;
	static PlayerInfo currentPlayer;
	static int outbreaks = 0;
	static int currentInfectionRateIdx = 0;
	static int actionsRemaining = 4;
	static int remCardsDeck = 53;
	static int remYellowCubes = 24;
	static int remRedCubes = 24;
	static int remBlueCubes = 24;
	static int remBlackCubes = 24;
	static int remResearchStations = 6;
	static ArrayList<String> researchStationCityNames = new ArrayList<String>();

	static boolean turnEnded = false;
	
	static boolean showCardSelectStage = false;
	static ArrayList<PlayerCardInfo> possibleSelections;
	static ArrayList<PlayerCardInfo> selections;
	static String showCardSelectAction;
	static int cardsToSelect;
	static ArrayList<String> possibleForecastSelections;
	static ArrayList<String> forecastSelections;

	static boolean useCityButtonStage = false;

	static String charterFlightCard;
	static boolean cityButtonsToAirlift = false;
	static String airliftedPlayer;

	static String[] eventCardNames = new String[] {
		"BorrowedTime",
		"MobileHospital",
		"NewAssignment",
		"RapidVaccine",
		"SpecialOrders",
		"Airlift",
		"OneQuietNight",
		"ResilientPopulation",
		"Forecast",
		"GovernmentGrant",
		"CommercialTravelBan",
		"ReexaminedResearch",
		"RemoteTreatment"
	};
	
	static PandemicGame parent;

	static Texture[] infectRateCircles = new Texture[]
			{
					new Texture(Gdx.files.internal("infectRate2.png")),
					new Texture(Gdx.files.internal("infectRate2.png")),
					new Texture(Gdx.files.internal("infectRate2.png")),
					new Texture(Gdx.files.internal("infectRate3.png")),
					new Texture(Gdx.files.internal("infectRate3.png")),
					new Texture(Gdx.files.internal("infectRate4.png")),
					new Texture(Gdx.files.internal("infectRate4.png"))
			};
	static Texture[] outbreakRateCircles = new Texture[]
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
	static Texture[] cityCardTextures;
	static Texture[] selectedCityCardTextures;
	static Texture[] playerTextures;
	static Texture[] diseaseCubeTextures;
	static Texture[] markerTextures = new Texture[4];
	static Texture[] cureMarkerTextures = new Texture[] {
			new Texture( Gdx.files.internal( "BlueCureMarker.png") ),
			new Texture( Gdx.files.internal( "YellowCureMarker.png") ),
			new Texture( Gdx.files.internal( "BlackCureMarker.png") ),
			new Texture( Gdx.files.internal( "RedCureMarker.png") )
	};
	static Texture[] activeMarkerTextures = new Texture[] {
			new Texture( Gdx.files.internal( "BlueActiveMarker.png") ),
			new Texture( Gdx.files.internal( "YellowActiveMarker.png") ),
			new Texture( Gdx.files.internal( "BlackActiveMarker.png") ),
			new Texture( Gdx.files.internal( "RedActiveMarker.png") )
	};
	static Texture[] eventCardTextures = new Texture[] {
		new Texture( Gdx.files.internal( "eventcards/"+eventCardNames[0]+".png") ),
		new Texture( Gdx.files.internal( "eventcards/"+eventCardNames[1]+".png") ),
		new Texture( Gdx.files.internal( "eventcards/"+eventCardNames[2]+".png") ),
		new Texture( Gdx.files.internal( "eventcards/"+eventCardNames[3]+".png") ),
		new Texture( Gdx.files.internal( "eventcards/"+eventCardNames[4]+".png") ),
		new Texture( Gdx.files.internal( "eventcards/"+eventCardNames[5]+".png") ),
		new Texture( Gdx.files.internal( "eventcards/"+eventCardNames[6]+".png") ),
		new Texture( Gdx.files.internal( "eventcards/"+eventCardNames[7]+".png") ),
		new Texture( Gdx.files.internal( "eventcards/"+eventCardNames[8]+".png") ),
		new Texture( Gdx.files.internal( "eventcards/"+eventCardNames[9]+".png") ),
		new Texture( Gdx.files.internal( "eventcards/"+eventCardNames[10]+".png") ),
		new Texture( Gdx.files.internal( "eventcards/"+eventCardNames[11]+".png") ),
		new Texture( Gdx.files.internal( "eventcards/"+eventCardNames[12]+".png") ),
	};
	
	
	static Texture handPanelTexture;
	static Texture greyBarOfNumbers = new Texture(Gdx.files.internal("numbersBar.png"));
	static Texture deck = new Texture(Gdx.files.internal("faceDownCard.png"));
	static Texture yellow = new Texture(Gdx.files.internal("YellowDiseaseCube.png"));
	static Texture red = new Texture(Gdx.files.internal("RedDiseaseCube.png"));
	static Texture blue = new Texture(Gdx.files.internal("BlueDiseaseCube.png"));
	static Texture black = new Texture(Gdx.files.internal("BlackDiseaseCube.png"));
	static Texture researchStation = new Texture(Gdx.files.internal( "researchStation.png"));
	static Texture background;
	static Texture connectionsTexture;

	static ScreenViewport screen;
	
	static BitmapFont handPlayerFont = new BitmapFont();
	static BitmapFont rvFontFront = new BitmapFont();
	static BitmapFont rvFontBack = new BitmapFont();
	
	static ScrollPane discardPane; 
	static int windWidth, windHeight;

	static SpriteBatch batch;

	static Stage buttonStage, pawnStage, diseaseStage;
	static Stage dialogStage;
	static Stage handPanelStage;
	static Stage cardSelectStage;

	static Table infectionDiscardTable;
	static Table playerDiscardTable;

	static Group buttonGroup, handPanelGroup, cityButtonGroup;

	static Skin skin;
	static Skin altSkin = new Skin( Gdx.files.internal( "plain-james-ui/plain-james-ui.json" ) );
	
	static BitmapFont troubleshooterFont = new BitmapFont();

	static Button button;

	static boolean isMyTurn = true;
	static float cubeOrbitRotation = 0;
	static PlayerInfo handShownPlayer;
    
    static ArrayList<Button> cityButtons 			= new ArrayList<Button>();
    static ArrayList<String> infectionDiscardPile	= new ArrayList<String>();
    static ArrayList<String> playerDiscardPile 		= new ArrayList<String>();
    
	static float nextActionButtonHeight;
	
	static boolean waitForButton = false;
	static boolean govtGrant = false;
	static boolean opExpertFly = false;
	static boolean remoteTreat = false;
	static boolean specialOrders = false;
	static String[] remoteTreatCities;
	static String[] remoteTreatDiseases;
	static int remoteTreated;
	
	static boolean rapidVaccine = false;
	static HashMap<String, Integer> rvCubesToRemove;
	static String rvColour;
	static String rvFirstCity;
	static String opExpertFlyCard;
	static String specialOrderPlayer;
	
	
    GameScreen( PandemicGame _parent ) //Debug Constructor
    {
    	parent = _parent;
    	
    	players = new PlayerInfo[5];
    	players[0] = new PlayerInfo( "Barry", true );
    	players[0].colour = PawnColour.values()[0];
    	players[0].role = "operationsexpert";
    	players[0].setCity( "Tokyo" );
    	
    	players[1] = new PlayerInfo( "Larry", false );
    	players[1].colour = PawnColour.values()[1];
    	players[1].role = "Medic";
    	
    	players[2] = new PlayerInfo( "Carrie", false );
    	players[2].colour = PawnColour.values()[2];
    	players[2].role = "Operations Expert";
    	
    	players[3] = new PlayerInfo( "Jarry", false );
    	players[3].colour = PawnColour.values()[3];
    	players[3].role = "Containment Specialist";
    	
    	players[4] = new PlayerInfo( "Agarry", false );
    	players[4].colour = PawnColour.values()[4];
    	players[4].role = "Generalist";
    	
    	currentPlayer = players[0];
    	clientPlayer = players[0];
    	
    	batch 	= new SpriteBatch();

		skin 	= new Skin( Gdx.files.internal( "plain-james-ui/plain-james-ui.json" ) );

		windWidth 	= Gdx.graphics.getWidth();
		windHeight 	= Gdx.graphics.getHeight();

		initGameState();
		initGeneralTextures();

		diseaseStage = new Stage( parent.screen ); //TEST
		dialogStage = new Stage( parent.screen );
		pawnStage = new Stage( parent.screen );
		handPanelGroup = new Group();

		updateDiseaseStage();

		initButtonStage();
		initHandButtonStage();

		updatePawnStage();
		
		

    	players[0].addCardToHand( new PlayerCardInfo("SpecialOrders" ) );
    	players[0].addCardToHand( new PlayerCardInfo("Atlanta" ) );
    	players[0].addCardToHand( new PlayerCardInfo("Atlanta" ) );
    	players[0].addCardToHand( new PlayerCardInfo("Atlanta" ) );
    	players[0].addCardToHand( new PlayerCardInfo("Atlanta" ) );
    	players[0].addCardToHand( new PlayerCardInfo("Atlanta" ) );
    	//players[0].addCardToHand( new PlayerCardInfo("London" ) );
    	//players[0].addCardToHand( new PlayerCardInfo("Essen" ) );
    	//players[0].addCardToHand( new PlayerCardInfo("Toronto" ) );
    	//players[0].addCardToHand( new PlayerCardInfo("Madrid" ) );
    	

    	players[1].addCardToHand( new PlayerCardInfo("BorrowedTime" ) );
    	//players[1].addCardToHand( new PlayerCardInfo("London" ) );
    	//players[1].addCardToHand( new PlayerCardInfo("Essen" ) );
    	//players[1].addCardToHand( new PlayerCardInfo("Toronto" ) );
    	//players[1].addCardToHand( new PlayerCardInfo("Madrid" ) );
    	
    	players[2].addCardToHand( new PlayerCardInfo("RapidVaccine" ) );
    	//players[2].addCardToHand( new PlayerCardInfo("London" ) );
    	//players[2].addCardToHand( new PlayerCardInfo("Essen" ) );
    	//players[2].addCardToHand( new PlayerCardInfo("Toronto" ) );
    	//players[2].addCardToHand( new PlayerCardInfo("Madrid" ) );
    	
    	infectionDiscardPile.add( "Atalnta" );
    	infectionDiscardPile.add( "Atalnta" );
    	infectionDiscardPile.add( "Atalnta" );
    	infectionDiscardPile.add( "Atalnta" );
    	infectionDiscardPile.add( "Atalnta" );
    	infectionDiscardPile.add( "Atalnta" );
    	infectionDiscardPile.add( "Atalnta" );
    	infectionDiscardPile.add( "Atalnta" );
    	infectionDiscardPile.add( "Atalnta" );
    	infectionDiscardPile.add( "Atalnta" );
    	infectionDiscardPile.add( "Atalnta" );
    	infectionDiscardPile.add( "Atalnta" );
    	infectionDiscardPile.add( "Atalnta" );
    	infectionDiscardPile.add( "Atalnta" );
		handShownPlayer = clientPlayer;

    	playerDiscardPile.add( "Atlanta" );
    	playerDiscardPile.add( "Tokyo" );
    	playerDiscardPile.add( "London" );
		
		researchStationCityNames.add( "Tokyo" );
		lookupCity( "Tokyo" ).putResearchStation();
		remResearchStations--;

		AddDiseaseCubeToCity( "Atlanta" , "blue" );
		AddDiseaseCubeToCity( "Atlanta" , "blue" );
		AddDiseaseCubeToCity( "Atlanta" , "blue" );
		AddDiseaseCubeToCity( "Chicago" , "blue" );
		AddDiseaseCubeToCity( "Washington" , "blue" );
		AddDiseaseCubeToCity( "Miami" , "blue" );
		AddDiseaseCubeToCity( "Bogota" , "blue" );
		AddDiseaseCubeToCity( "Tokyo" , "blue" );

    }
    
	GameScreen( PandemicGame _parent, PlayerInfo[] _players )
	{
		parent 	= _parent;
		players = _players;

		screen 	= parent.screen;
		batch 	= new SpriteBatch();
		skin 	= new Skin( Gdx.files.internal( "plain-james-ui/plain-james-ui.json" ) );

		windWidth 	= Gdx.graphics.getWidth();
		windHeight 	= Gdx.graphics.getHeight();

		initGameState();
		initGeneralTextures();

		diseaseStage = new Stage( screen ); //TEST
		dialogStage = new Stage( screen );
		pawnStage = new Stage( screen );
		cardSelectStage = new Stage( parent.screen );
		handPanelGroup = new Group();

		updateDiseaseStage();

		initButtonStage();
		initHandButtonStage();

		updatePawnStage();
		handShownPlayer = clientPlayer;
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
		selectedCityCardTextures = new Texture[ cityNodes.length ];
		for ( int i = 0; i < cityNodes.length; i++)
		{
			CityNode city = cityNodes[ i ];
			cityCardTextures[ i ] = new Texture( Gdx.files.internal( "citycards/"+ city.getName() +".png" ) );
			//selectedCityCardTextures[ i ] = new Texture( Gdx.files.internal( "citycards/"+ city.getName() +".png" ) );
			TextureData cardTextData = cityCardTextures[i].getTextureData();
			cardTextData.prepare();
			Pixmap cardTextPxMap = cardTextData.consumePixmap();
			for( int y = 0; y < cardTextPxMap.getHeight(); y++ )
			{
				for( int x = 0; x < cardTextPxMap.getWidth(); x++ )
				{
					Color color = new Color();
		            Color.rgba8888ToColor(color, cardTextPxMap.getPixel(x, y));
		            float greyScale = ( color.r + color.g + color.b ) / 3.0f;
		            color.set( greyScale, greyScale, greyScale, color.a );
		            cardTextPxMap.setColor( color );
		            cardTextPxMap.fillRectangle(x, y, 1, 1);
				}
			}
			
			selectedCityCardTextures[ i ] = new Texture( cardTextPxMap );
			cardTextData.disposePixmap();
			cardTextPxMap.dispose();
		}

		handPanelTexture = new Texture( Gdx.files.internal( "handPanel.png") );

		createConnectionsTexture();
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
					//currentPlayer = clientPlayer; // TEST CURRENT PLAYER
				}
				
				if( players[i].role != null )
					if( players[i].role.equalsIgnoreCase("Scientist") )
						players[i].cardsToCure = 4;
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

		researchStationCityNames.add( "Atlanta" );
		lookupCity( "Atlanta" ).putResearchStation();
		
		
		diseaseStatuses.put( "blue", DiseaseStatus.ACTIVE );
		diseaseStatuses.put( "yellow", DiseaseStatus.ACTIVE );
		diseaseStatuses.put( "black", DiseaseStatus.ACTIVE );
		diseaseStatuses.put( "red", DiseaseStatus.ACTIVE );
		
		if ( currentPlayer.role.equalsIgnoreCase( "Generalist" ) )
			actionsRemaining = 5;
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

	static void createWrappingConnection( Pixmap pxMap, CityNode city0, CityNode city1 )
	{
		CityNode temp = city0;
		//Ensure that the line is being drawn with increasing x;
		city0 = ( city0.x < city1.x ) ? city0 : city1;
		city1 = ( city0.x < city1.x ) ? city1 : temp;

		int x0 = (int)city0.getXInWindowCoords( windWidth );
		int y0 = (int)city0.getYInWindowCoords( windHeight );

		int x1 = (int)city1.getXInWindowCoords( windWidth );
		int y1 = (int)city1.getYInWindowCoords( windHeight );

		double grad0 = (double)(y1-y0)/(double)(x1-x0);
		int draw0X0 = 0;
		int draw0Y0 = y0 + (int)(x0*grad0);
		pxMap.drawLine( draw0X0,  (int)(windHeight-draw0Y0 - nodeSize/2),  (int)(x0 + nodeSize/2),  (int)(windHeight-y0 - nodeSize/2));

		int draw1X1 = windWidth;
		int draw1Y1 = y1 - (int)((windWidth-x1)*grad0);
		pxMap.drawLine( (int)(x1 + nodeSize/2),  (int)(windHeight-y1 - nodeSize/2), draw1X1,  (int)(windHeight-draw1Y1 - nodeSize/2));
	}

	static void createConnectionsTexture()
	{
		Pixmap connections = new Pixmap( (int)windWidth, (int)windHeight, Pixmap.Format.RGBA8888 );
		connections.setColor( Color.WHITE );

		for( CityNode curr : cityNodes )
		{
			int x0 = (int)curr.getXInWindowCoords( windWidth );
			int y0 = (int)curr.getYInWindowCoords( windHeight );

			for ( CityNode other : curr.getConnectedCities() )
			{
				if( curr.getName().equals("Tokyo") && other.getName().equals( "San Francisco" ) || other.getName().equals("Tokyo") && curr.getName().equals( "San Francisco" ) )
				{
					createWrappingConnection( connections, curr, other );
				}
				else if ( curr.getName().equals("Manila") && other.getName().equals( "San Francisco" ) || other.getName().equals("Manila") && curr.getName().equals( "San Francisco" ) )
				{
					createWrappingConnection( connections, curr, other );
				}
				else if ( curr.getName().equals("Los Angeles") && other.getName().equals( "Sydney" ) || other.getName().equals("Los Angeles") && curr.getName().equals( "Sydney" ) )
				{
					createWrappingConnection( connections, curr, other );
				}
				else
				{
					int x1 = (int)other.getXInWindowCoords( windWidth );
					int y1 = (int)other.getYInWindowCoords( windHeight );

					connections.drawLine( (int)(x0 + nodeSize/2),  (int)(windHeight-y0 - nodeSize/2),  (int)(x1 + nodeSize/2),  (int)(windHeight-y1 - nodeSize/2));
					//connections.drawLine( (int)curr.getX(), (int)curr.getY(), (int)other.getX(), (int)other.getY());
				}
			}
		}

		connectionsTexture = new Texture( connections );
		connections.dispose();
	}

	static void updatePawnStage()
	{
		pawnStage.clear();

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
		if (diseaseStage != null)
		{
			diseaseStage.clear();
		}
		for ( CityNode curr : cityNodes )
		{
			ArrayList<DiseaseCubeInfo> localCubes = curr.getCubes();
			float arc = (float)(2*Math.PI / localCubes.size());
			for( int i = 0; i < localCubes.size(); i++ )
			{
				int x = (int)(curr.getXInWindowCoords(windWidth) + nodeSize/2 - cubeSize/2 + ( ( nodeSize/2 + cubeOrbitOffset )*Math.cos( arc*i + cubeOrbitRotation * localCubes.size() ) ) );
				int y = (int)(curr.getYInWindowCoords(windHeight) + nodeSize/2 - cubeSize/2 + ( ( nodeSize/2 + cubeOrbitOffset )*Math.sin( arc*i + cubeOrbitRotation * localCubes.size()) ) );

				Image cube = new Image( diseaseCubeTextures[ localCubes.get( i ).getColourIndex() ] );
				cube.setBounds( x ,y, cubeSize, cubeSize);
				diseaseStage.addActor( cube );

			}
		}
	}

	static void updateHandPanelStage()
	{
		handPanelGroup.clear();

		if ( handShownPlayer == null )
			return;

		ArrayList<PlayerCardInfo> playerHand = handShownPlayer.getHand();
		int handIdx = 0;
		for( final PlayerCardInfo card : playerHand )
		{
			if( card != null )
			{
				int index = lookupCityIndex( card.getName() );
				if( index > -1 ) //Player Cards
				{
					Texture cardTexture	 							= cityCardTextures[ index ];
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
		
				            	if( !waitForButton )
				            	{
				            		waitForButton = true;
				            		if (currentPlayer.getCity().equals(card.getName())){
		
										Dialog confirmFlight = new Dialog("Use " + card.getName() + " city card to Charter Flight?", skin) {
											@Override
											protected void result(Object object) {
												if ((boolean) (object)) {
													useCityButtonStage = true;
													//charterFlightCard = card.getName();
													//System.out.println("TEEEEESTING");
												}
												Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
												//dialogStage = null;
											}
										};
		
										//dialogStag/e = new Stage( parent.screen );//
										dialogStage.clear();
		
										confirmFlight.button("Yes", true);
										confirmFlight.button("No", false);
		
										Gdx.input.setInputProcessor(dialogStage); //Start taking input from the ui
										confirmFlight.show(dialogStage);
		
									}
		
									else {
										Dialog confirmFlight = new Dialog("Fly to " + card.getName() + "?", skin) {
											@Override
											protected void result(Object object) {
												if ((boolean) (object)) {
													ClientComm.send("DirectFlight/" + card.getName());
												}
												Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
												//dialogStage = null;
											}
										};
		
										//dialogStag/e = new Stage( parent.screen );//
										dialogStage.clear();
		
										confirmFlight.button("Yes", true);
										confirmFlight.button("No", false);
		
										Gdx.input.setInputProcessor(dialogStage); //Start taking input from the ui
										confirmFlight.show(dialogStage);
									}
					                waitForButton = false;
				            	}
		
							}
						});
					}
		
					float x = playerCardXOffset + playerCardGap + (handIdx*(playerCardXSize+ playerCardGap) );
					float y = playerCardYOffset/2;
					button.setBounds(x, y, playerCardXSize, playerCardYSize);
					handPanelGroup.addActor(button); //Add the button to the stage to perform rendering and take input.
					handIdx++;
				}
				else //Event Cards
				{
					String name = card.getName();
					
					switch( name )
					{
						case "BorrowedTime":
						{
							createBorrowedTime( handIdx );
							handIdx++;
						} break;
						
						case "MobileHospital":
						{
							createMobileHospital( handIdx );
							handIdx++;
						} break;
						
						case "NewAssignment":
						{
							createNewAssignment( handIdx );
							handIdx++;
						} break;
						
						case "RapidVaccine":
						{
							createRapidVaccine( handIdx );
							handIdx++;
						} break;
						
						case "SpecialOrders":
						{
							createSpecialOrders( handIdx );
							handIdx++;
						} break;
						
						case "CommercialTravelBan":
						{
							createCommercialTravelBan( handIdx );
							handIdx++;
						} break;

						case "ReexaminedResearch":
						{
							createReexaminedResearch( handIdx );
							handIdx++;
						} break;

						case "RemoteTreatment":
						{
							createRemoteTreatment( handIdx );
							handIdx++;
						} break;
						
						case "Airlift":
						{
							createAirlift( handIdx );
							handIdx++;
						} break;
						
						case "OneQuietNight":
						{
							createOneQuietNight( handIdx );
							handIdx++;
						} break;
						
						case "ResilientPopulation":
						{
							createResilientPopulation( handIdx );
							handIdx++;
						} break;
						
						case "GovernmentGrant":
						{
							createGovernmentGrant( handIdx );
							handIdx++;
						} break;
						
						case "Forecast":
						{
							createForecast( handIdx );
							handIdx++;
						} break;
						
						default:
						{} break;
					}
				}
			}
		}
		buttonStage.addActor( handPanelGroup );

	}

	static void createBorrowedTime( int idx )
	{
		Texture cardTexture	 							= eventCardTextures[ 0 ];
		TextureRegion TR_cardTexture 					= new TextureRegion( cardTexture );
		final TextureRegionDrawable Draw_cardTexture 	= new TextureRegionDrawable( TR_cardTexture );

		ButtonStyle cityButtonStyle = new ButtonStyle();
		cityButtonStyle.up			= Draw_cardTexture;
		cityButtonStyle.down		= Draw_cardTexture;

		button = new Button( cityButtonStyle );
		button.addListener( new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if( !waitForButton )
				{
					waitForButton = true;
					
					dialogStage.clear();
					Dialog borrowedTimeDiag = new Dialog( "Do you want to play Borrowed Time?", skin )
					{
						@Override
						protected void result(Object object) {
							if( (Boolean)object )
							{
								ClientComm.send("BorrowedTime/");
							}
							Gdx.input.setInputProcessor( buttonStage );
						};
					};
					borrowedTimeDiag.button("Yes", true );
					borrowedTimeDiag.button("No", false);
					borrowedTimeDiag.show( dialogStage );
					Gdx.input.setInputProcessor( dialogStage );
					waitForButton = false;
				}
			}
		} );
		
		float x = playerCardXOffset + playerCardGap + (idx*(playerCardXSize+ playerCardGap) );
		float y = playerCardYOffset/2;
		button.setBounds(x, y, playerCardXSize, playerCardYSize);
		handPanelGroup.addActor(button); //Add the button to the stage to perform rendering and take input.
	}
	
	static void createMobileHospital( int idx )
	{
		Texture cardTexture	 							= eventCardTextures[ 1 ];
		TextureRegion TR_cardTexture 					= new TextureRegion( cardTexture );
		final TextureRegionDrawable Draw_cardTexture 	= new TextureRegionDrawable( TR_cardTexture );

		ButtonStyle cityButtonStyle = new ButtonStyle();
		cityButtonStyle.up			= Draw_cardTexture;
		cityButtonStyle.down		= Draw_cardTexture;

		button = new Button( cityButtonStyle );
		button.addListener( new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if( !waitForButton )
				{
					waitForButton = true;
					
					dialogStage.clear();
					Dialog borrowedTimeDiag = new Dialog( "Do you want to play Mobile Hospital?", skin )
					{
						@Override
						protected void result(Object object) {
							if( (Boolean)object )
							{
								ClientComm.send("MobileHospital/");
							}
							Gdx.input.setInputProcessor( buttonStage );
						};
					};
					borrowedTimeDiag.button("Yes", true );
					borrowedTimeDiag.button("No", false);
					borrowedTimeDiag.show( dialogStage );
					Gdx.input.setInputProcessor( dialogStage );
					waitForButton = false;
				}
			}
		} );
		
		float x = playerCardXOffset + playerCardGap + (idx*(playerCardXSize+ playerCardGap) );
		float y = playerCardYOffset/2;
		button.setBounds(x, y, playerCardXSize, playerCardYSize);
		handPanelGroup.addActor(button); //Add the button to the stage to perform rendering and take input.
	}
	
	static void createNewAssignment( int idx )
	{
		Texture cardTexture	 							= eventCardTextures[ 2 ];
		TextureRegion TR_cardTexture 					= new TextureRegion( cardTexture );
		final TextureRegionDrawable Draw_cardTexture 	= new TextureRegionDrawable( TR_cardTexture );

		ButtonStyle cityButtonStyle = new ButtonStyle();
		cityButtonStyle.up			= Draw_cardTexture;
		cityButtonStyle.down		= Draw_cardTexture;

		button = new Button( cityButtonStyle );
		
		float x = playerCardXOffset + playerCardGap + (idx*(playerCardXSize+ playerCardGap) );
		float y = playerCardYOffset/2;
		button.setBounds(x, y, playerCardXSize, playerCardYSize);
		handPanelGroup.addActor(button); //Add the button to the stage to perform rendering and take input.
	
	}
	
	static void createRapidVaccine( int idx )
	{
		Texture cardTexture	 							= eventCardTextures[ 3 ];
		TextureRegion TR_cardTexture 					= new TextureRegion( cardTexture );
		final TextureRegionDrawable Draw_cardTexture 	= new TextureRegionDrawable( TR_cardTexture );

		ButtonStyle cityButtonStyle = new ButtonStyle();
		cityButtonStyle.up			= Draw_cardTexture;
		cityButtonStyle.down		= Draw_cardTexture;

		button = new Button( cityButtonStyle );
		button.addListener( new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Dialog rvDiag = new Dialog( "Cannot play now", skin ) {
					@Override
					protected void result(Object object) {
						Gdx.input.setInputProcessor( dialogStage );
					}
				};
				rvDiag.button("Okay");
				rvDiag.show( dialogStage );
				Gdx.input.setInputProcessor( dialogStage );
				
			}
		});
		
		float x = playerCardXOffset + playerCardGap + (idx*(playerCardXSize+ playerCardGap) );
		float y = playerCardYOffset/2;
		button.setBounds(x, y, playerCardXSize, playerCardYSize);
		handPanelGroup.addActor(button); //Add the button to the stage to perform rendering and take input.
	
	}
	
	static void createCommercialTravelBan( int idx )
	{
		Texture cardTexture	 							= eventCardTextures[ 4 ];
		TextureRegion TR_cardTexture 					= new TextureRegion( cardTexture );
		final TextureRegionDrawable Draw_cardTexture 	= new TextureRegionDrawable( TR_cardTexture );

		ButtonStyle cityButtonStyle = new ButtonStyle();
		cityButtonStyle.up			= Draw_cardTexture;
		cityButtonStyle.down		= Draw_cardTexture;

		button = new Button( cityButtonStyle );
		button.addListener( new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if( !waitForButton )
				{
					waitForButton = true;
					
					dialogStage.clear();
					Dialog borrowedTimeDiag = new Dialog( "Do you want to play Commercial Travel Ban?", skin )
					{
						@Override
						protected void result(Object object) {
							if( (Boolean)object )
							{
								ClientComm.send("CommercialTravelBan/");
							}
							Gdx.input.setInputProcessor( buttonStage );
						};
					};
					borrowedTimeDiag.button("Yes", true );
					borrowedTimeDiag.button("No", false);
					borrowedTimeDiag.show( dialogStage );
					Gdx.input.setInputProcessor( dialogStage );
					waitForButton = false;
				}
			}
		} );
		
		float x = playerCardXOffset + playerCardGap + (idx*(playerCardXSize+ playerCardGap) );
		float y = playerCardYOffset/2;
		button.setBounds(x, y, playerCardXSize, playerCardYSize);
		handPanelGroup.addActor(button); //Add the button to the stage to perform rendering and take input.
	
	}
	
	static void createSpecialOrders( int idx )
	{
		Texture cardTexture	 							= eventCardTextures[ 5 ];
		TextureRegion TR_cardTexture 					= new TextureRegion( cardTexture );
		final TextureRegionDrawable Draw_cardTexture 	= new TextureRegionDrawable( TR_cardTexture );

		ButtonStyle cityButtonStyle = new ButtonStyle();
		cityButtonStyle.up			= Draw_cardTexture;
		cityButtonStyle.down		= Draw_cardTexture;

		button = new Button( cityButtonStyle );
		button.addListener( new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if( !waitForButton )
				{
					waitForButton = true;
					
					if( currentPlayer == clientPlayer )
					{
						dialogStage.clear();
						Dialog specialOrdersDiag = new Dialog( "Do you want to play Special Orders?", skin )
						{
							@Override
							protected void result(Object object) {
								if( (Boolean)object )
								{
									int numViable = 0;
									for( PlayerInfo curr : players )
										if( curr != null && curr != currentPlayer )
											numViable++;
											
									
									String[] viablePlayers = new String[ numViable ];
									int i = 0;
									for( PlayerInfo curr : players )
									{
										if( curr != null && curr != currentPlayer )
										{
											viablePlayers[i] = curr.getName();
											i++;
										}
									}
									
									final Skin tempSkin = new Skin( Gdx.files.internal( "skin/uiskin.json" ) );
									final SelectBox<String> selector = new SelectBox<String>( tempSkin );
									selector.setItems( viablePlayers );
									
									Dialog soDiag = new Dialog("Select player to request control of", skin) {
										@Override
										protected void result(Object object) {
											if( (boolean)object )
												ClientComm.send( "SpecialOrdersRequest/" + selector.getSelected() + '/' );
											tempSkin.dispose();
											Gdx.input.setInputProcessor( buttonStage );
										}
									};
									soDiag.getContentTable().add( selector );
									soDiag.button("Select", true );
									soDiag.button( "Cancel", false );
									soDiag.show( dialogStage );
									Gdx.input.setInputProcessor( dialogStage );
								}
								else
									Gdx.input.setInputProcessor( buttonStage );
							};
						};
						specialOrdersDiag.button("Yes", true );
						specialOrdersDiag.button("No", false);
						specialOrdersDiag.show( dialogStage );
						Gdx.input.setInputProcessor( dialogStage );
						waitForButton = false;
					}
				}
			}
		} );
		
		
		float x = playerCardXOffset + playerCardGap + (idx*(playerCardXSize+ playerCardGap) );
		float y = playerCardYOffset/2;
		button.setBounds(x, y, playerCardXSize, playerCardYSize);
		handPanelGroup.addActor(button); //Add the button to the stage to perform rendering and take input.
	
	}
	
	static void createReexaminedResearch( int idx )
	{
		Texture cardTexture	 							= eventCardTextures[ 6 ];
		TextureRegion TR_cardTexture 					= new TextureRegion( cardTexture );
		final TextureRegionDrawable Draw_cardTexture 	= new TextureRegionDrawable( TR_cardTexture );

		ButtonStyle cityButtonStyle = new ButtonStyle();
		cityButtonStyle.up			= Draw_cardTexture;
		cityButtonStyle.down		= Draw_cardTexture;

		button = new Button( cityButtonStyle );
		button.addListener( new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if( !waitForButton )
				{
					waitForButton = true;
					if( playerDiscardPile.size() > 0 )
					{
						dialogStage.clear();
						Dialog borrowedTimeDiag = new Dialog( "Do you want to play Re-examined Research?", skin )
						{
							@Override
							protected void result(Object object) {
								if( (Boolean)object )
								{
									int j = 0;
									for ( PlayerInfo curr : players )
										if( curr != null )
											j++;
									
									String[] playerNames = new String[j];
									
									for( int i = 0; i < players.length; i++ )
									{
										if( players[i] != null )
										{
											playerNames[j-1] = players[i].getName();
											j--;
										}
									}
									
									final Skin tempSkin = new Skin( Gdx.files.internal( "skin/uiskin.json" ) );
									final SelectBox<String> selector = new SelectBox<String>( tempSkin );
									selector.setItems( playerNames );
									
									Dialog btDiag = new Dialog( "Select a player", skin ) {
										@Override
										protected void result(Object object) {
											if( (boolean)object )
											{
												ClientComm.send( "ReexaminedReseatch/" + selector.getSelected() + '/' );
												tempSkin.dispose();
											}
											Gdx.input.setInputProcessor( buttonStage );
										};
									};
									btDiag.button( "Select", true );
									btDiag.button( "Cancel", false );
									btDiag.getContentTable().add( selector );
									btDiag.show( dialogStage );
									Gdx.input.setInputProcessor( dialogStage );
								}
								else
									Gdx.input.setInputProcessor( buttonStage );
							};
						};
						borrowedTimeDiag.button("Yes", true );
						borrowedTimeDiag.button("No", false);
						borrowedTimeDiag.show( dialogStage );
						Gdx.input.setInputProcessor( dialogStage );
					}
					else
					{
						dialogStage.clear();
						Dialog btDiag = new Dialog( "No cards in player discardpile", skin ) {
							@Override
							protected void result(Object object) {
								Gdx.input.setInputProcessor( buttonStage );
							};
						};
						btDiag.button("Okay");
						btDiag.show( dialogStage );
						Gdx.input.setInputProcessor( dialogStage );
					}
					waitForButton = false;
				}
			}
		} );
		
		float x = playerCardXOffset + playerCardGap + (idx*(playerCardXSize+ playerCardGap) );
		float y = playerCardYOffset/2;
		button.setBounds(x, y, playerCardXSize, playerCardYSize);
		handPanelGroup.addActor(button); //Add the button to the stage to perform rendering and take input.
	
	}
	
	static void createRemoteTreatment( int idx )
	{
		Texture cardTexture	 							= eventCardTextures[ 7 ];
		TextureRegion TR_cardTexture 					= new TextureRegion( cardTexture );
		final TextureRegionDrawable Draw_cardTexture 	= new TextureRegionDrawable( TR_cardTexture );

		ButtonStyle cityButtonStyle = new ButtonStyle();
		cityButtonStyle.up			= Draw_cardTexture;
		cityButtonStyle.down		= Draw_cardTexture;

		button = new Button( cityButtonStyle );
		button.addListener( new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if( !waitForButton )
				{
					waitForButton = true;
					
					dialogStage.clear();
					Dialog remoteTreatDiag = new Dialog( "Do you want to play Remote Treatment?", skin )
					{
						@Override
						protected void result(Object object) {
							if( (Boolean)object )
							{
								Dialog rtDiag = new Dialog("Pick disease cubes to remove", skin ) {
									@Override
									protected void result(Object object) {
										Gdx.input.setInputProcessor( buttonStage );
										remoteTreat = true;
										useCityButtonStage = true;
										remoteTreated = 0;
										remoteTreatCities = new String[2];
										remoteTreatDiseases = new String[2];
									};
								};

								rtDiag.button("Okay");
								rtDiag.show(dialogStage);
								Gdx.input.setInputProcessor( dialogStage );
							}
							else
								Gdx.input.setInputProcessor( buttonStage );
						};
					};
					remoteTreatDiag.button("Yes", true );
					remoteTreatDiag.button("No", false);
					remoteTreatDiag.show( dialogStage );
					Gdx.input.setInputProcessor( dialogStage );
					waitForButton = false;
				}
			}
		} );
		
		
		float x = playerCardXOffset + playerCardGap + (idx*(playerCardXSize+ playerCardGap) );
		float y = playerCardYOffset/2;
		button.setBounds(x, y, playerCardXSize, playerCardYSize);
		handPanelGroup.addActor(button); //Add the button to the stage to perform rendering and take input.
	
	}
	
	static void createAirlift( int idx )
	{
		Texture cardTexture	 							= eventCardTextures[ 8 ];
		TextureRegion TR_cardTexture 					= new TextureRegion( cardTexture );
		final TextureRegionDrawable Draw_cardTexture 	= new TextureRegionDrawable( TR_cardTexture );

		ButtonStyle cityButtonStyle = new ButtonStyle();
		cityButtonStyle.up			= Draw_cardTexture;
		cityButtonStyle.down		= Draw_cardTexture;

		button = new Button( cityButtonStyle );
		button.addListener( new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if( !waitForButton )
				{
					waitForButton = true;

					dialogStage.clear();
					final Skin tempSkin = new Skin( Gdx.files.internal( "skin/uiskin.json" ) );
					final SelectBox<String> selectBox = new SelectBox<String>( tempSkin );
					Dialog airliftDiag = new Dialog( "Select a player to airlift.", skin )
					{
						@Override
						protected void result(Object object) {
							if( (boolean)object )
							{
								String selected = selectBox.getSelected();
								useCityButtonStage = true;
								cityButtonsToAirlift = true;
								airliftedPlayer = selected;
								//charterFlightCard = card.getName();
							}
							Gdx.input.setInputProcessor( buttonStage );
						};
					};
					String[] names = new String[getNumPlayers()];
					int j = 0;
					for( int i = 0; i < players.length; i++)
					{
						if( players[i] != null )
						{
							names[j] = players[i].getName();
							j++;
						}
					}
					selectBox.setItems( names );
					
					airliftDiag.getContentTable().add( selectBox );
					airliftDiag.button("Select", true );
					airliftDiag.button("Cancel", false);
					airliftDiag.show( dialogStage );
					Gdx.input.setInputProcessor( dialogStage );
					waitForButton = false;
				}
			}
		} );
		
		float x = playerCardXOffset + playerCardGap + (idx*(playerCardXSize+ playerCardGap) );
		float y = playerCardYOffset/2;
		button.setBounds(x, y, playerCardXSize, playerCardYSize);
		handPanelGroup.addActor(button); //Add the button to the stage to perform rendering and take input.
	
	}
	
	static void createOneQuietNight( int idx )
	{
		Texture cardTexture	 							= eventCardTextures[ 9 ];
		TextureRegion TR_cardTexture 					= new TextureRegion( cardTexture );
		final TextureRegionDrawable Draw_cardTexture 	= new TextureRegionDrawable( TR_cardTexture );

		ButtonStyle cityButtonStyle = new ButtonStyle();
		cityButtonStyle.up			= Draw_cardTexture;
		cityButtonStyle.down		= Draw_cardTexture;

		button = new Button( cityButtonStyle );
		button.addListener( new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if( !waitForButton )
				{
					waitForButton = true;
					
					dialogStage.clear();
					Dialog borrowedTimeDiag = new Dialog( "Do you want to play One Quiet Night?", skin )
					{
						@Override
						protected void result(Object object) {
							if( (Boolean)object )
							{
								ClientComm.send("OneQuietNight/");
							}
							Gdx.input.setInputProcessor( buttonStage );
						};
					};
					borrowedTimeDiag.button("Yes", true );
					borrowedTimeDiag.button("No", false);
					borrowedTimeDiag.show( dialogStage );
					Gdx.input.setInputProcessor( dialogStage );
					waitForButton = false;
				}
			}
		} );
		
		float x = playerCardXOffset + playerCardGap + (idx*(playerCardXSize+ playerCardGap) );
		float y = playerCardYOffset/2;
		button.setBounds(x, y, playerCardXSize, playerCardYSize);
		handPanelGroup.addActor(button); //Add the button to the stage to perform rendering and take input.
	
	}
	
	static void createResilientPopulation( int idx )
	{
		Texture cardTexture	 							= eventCardTextures[ 10 ];
		TextureRegion TR_cardTexture 					= new TextureRegion( cardTexture );
		final TextureRegionDrawable Draw_cardTexture 	= new TextureRegionDrawable( TR_cardTexture );

		ButtonStyle cityButtonStyle = new ButtonStyle();
		cityButtonStyle.up			= Draw_cardTexture;
		cityButtonStyle.down		= Draw_cardTexture;

		button = new Button( cityButtonStyle );
		button.addListener( new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if( infectionDiscardPile.size() > 0 )
				{
					String[] data = new String[ infectionDiscardPile.size() ];
					for( int i = 0; i < data.length; i++ )
						data[i] = infectionDiscardPile.get( i );
					
					final Skin tempSkin = new Skin( Gdx.files.internal( "skin/uiskin.json") );
					final SelectBox<String> selector = new SelectBox<String>( tempSkin );
					selector.setItems( data );
					
					Dialog rpDiag = new Dialog( "Pick a card to remove from infection Discard", skin ) {
						@Override
						protected void result(Object object) {
							if( (boolean)object )
							{
								ClientComm.send("ResilientPopulation/" + selector.getSelected() +'/');
							}
							Gdx.input.setInputProcessor( buttonStage );
						};
					};
					rpDiag.getContentTable().add( selector );
					rpDiag.button("Select", true );
					rpDiag.button("Cancel", false );
					rpDiag.show( dialogStage );
					Gdx.input.setInputProcessor( dialogStage );
				}
				else
				{
					Dialog rpDiag = new Dialog( "No cards in infection discard", skin ) {
						@Override
						protected void result(Object object) {
							Gdx.input.setInputProcessor( buttonStage );
						};
					};
					rpDiag.button("Okay");
					rpDiag.show( dialogStage );
					Gdx.input.setInputProcessor( dialogStage );
				}
			}
		});
		
		float x = playerCardXOffset + playerCardGap + (idx*(playerCardXSize+ playerCardGap) );
		float y = playerCardYOffset/2;
		button.setBounds(x, y, playerCardXSize, playerCardYSize);
		handPanelGroup.addActor(button); //Add the button to the stage to perform rendering and take input.
	
	}
	
	static void createGovernmentGrant( int idx )
	{
		Texture cardTexture	 							= eventCardTextures[ 11 ];
		TextureRegion TR_cardTexture 					= new TextureRegion( cardTexture );
		final TextureRegionDrawable Draw_cardTexture 	= new TextureRegionDrawable( TR_cardTexture );

		ButtonStyle cityButtonStyle = new ButtonStyle();
		cityButtonStyle.up			= Draw_cardTexture;
		cityButtonStyle.down		= Draw_cardTexture;

		button = new Button( cityButtonStyle );
		button.addListener( new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if( !waitForButton )
				{
					waitForButton = true;
					
					dialogStage.clear();
					Dialog borrowedTimeDiag = new Dialog( "Do you want to play Government Grant?", skin )
					{
						@Override
						protected void result(Object object) {
							if( (Boolean)object )
							{
								Dialog ggInfo = new Dialog( "Please select a city to build a research station in", skin ) {
									@Override
									protected void result(Object object) {
										useCityButtonStage = true;
										govtGrant = true;
										Gdx.input.setInputProcessor( buttonStage );
									};
								};
								ggInfo.button("Okay");
								ggInfo.show( dialogStage );
								
							}
							else
								Gdx.input.setInputProcessor( buttonStage );
						};
					};
					borrowedTimeDiag.button("Yes", true );
					borrowedTimeDiag.button("No", false);
					borrowedTimeDiag.show( dialogStage );
					Gdx.input.setInputProcessor( dialogStage );
					waitForButton = false;
				}
			}
		} );
		
		float x = playerCardXOffset + playerCardGap + (idx*(playerCardXSize+ playerCardGap) );
		float y = playerCardYOffset/2;
		button.setBounds(x, y, playerCardXSize, playerCardYSize);
		handPanelGroup.addActor(button); //Add the button to the stage to perform rendering and take input.
	
	}
	
	static void createForecast( int idx )
	{
		Texture cardTexture	 							= eventCardTextures[ 12 ];
		TextureRegion TR_cardTexture 					= new TextureRegion( cardTexture );
		final TextureRegionDrawable Draw_cardTexture 	= new TextureRegionDrawable( TR_cardTexture );

		ButtonStyle cityButtonStyle = new ButtonStyle();
		cityButtonStyle.up			= Draw_cardTexture;
		cityButtonStyle.down		= Draw_cardTexture;

		button = new Button( cityButtonStyle );
		button.addListener( new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if( !waitForButton )
				{
					waitForButton = true;
					
					dialogStage.clear();
					Dialog borrowedTimeDiag = new Dialog( "Do you want to play Forecast?", skin )
					{
						@Override
						protected void result(Object object) {
							if( (Boolean)object )
							{
								Dialog info = new Dialog( "Select cards in order to be drawn", skin ) {
									@Override
									protected void result(Object object) {
										//ClientComm.send("ForecastRequest/"); TODO: Remove
										Forecast( new String[] { "Atlanta", "Tokyo", "Manila", "Toronto", "London", "Johannesburg" } );
									};
								};
								info.button("Okay");
								info.show( dialogStage );
							}
							else
								Gdx.input.setInputProcessor( buttonStage );
						};
					};
					borrowedTimeDiag.button("Yes", true );
					borrowedTimeDiag.button("No", false);
					borrowedTimeDiag.show( dialogStage );
					Gdx.input.setInputProcessor( dialogStage );
					waitForButton = false;
				}
			}
		} );
		
		
		float x = playerCardXOffset + playerCardGap + (idx*(playerCardXSize+ playerCardGap) );
		float y = playerCardYOffset/2;
		button.setBounds(x, y, playerCardXSize, playerCardYSize);
		handPanelGroup.addActor(button); //Add the button to the stage to perform rendering and take input.
	}

	
	static void updateCityTouchability()
	{
		if( useCityButtonStage )
		{
			buttonGroup.setTouchable( Touchable.disabled );
		}
		else
		{
			buttonGroup.setTouchable( Touchable.enabled );
		}
		
		for ( Button currButton : cityButtons ){
			currButton.setTouchable(Touchable.disabled);
		}

		if(useCityButtonStage && !rapidVaccine && !remoteTreat ){	// if boolean is true then make all cities touchable (except for currentPlayer's currentCity
			ArrayList<CityNode> allTheCities = new ArrayList<CityNode>();

			for( int i = 0; i < cityNodes.length; i++){		// copy all cities from cityNodes (except for currentPlayer's currentCity) to allTheCities ArrayList
				if( !(currentPlayer.getCity().equals(cityNodes[i].getName())) ){
					//System.out.println(i + "before works");
					allTheCities.add(cityNodes[i]);
					//System.out.println(i + "after works");
				}
			}
			for ( CityNode curr : allTheCities){
				cityButtons.get(lookupCityIndex(curr.getName())).setTouchable( Touchable.enabled );
			}
		}
		else if( rapidVaccine )
		{
			if( rvFirstCity == null || rvFirstCity.equals("") )
			{
				for ( CityNode curr : cityNodes){
					cityButtons.get(lookupCityIndex(curr.getName())).setTouchable( Touchable.enabled );
				}
			}
			else
			{
				for( String coreCity : rvCubesToRemove.keySet() )
				{
					cityButtons.get(lookupCityIndex( coreCity )).setTouchable( Touchable.enabled );
					for( CityNode city : lookupCity( coreCity ).connectedCities )
					{
						cityButtons.get(lookupCityIndex(city.getName())).setTouchable( Touchable.enabled );
					}
				}
			}
		}
		else if ( remoteTreat )
		{
			for ( CityNode curr : cityNodes)
			{
				if( ( curr.cubes.size() > 0 ) )
					cityButtons.get(lookupCityIndex(curr.getName())).setTouchable( Touchable.enabled );
				
				if( remoteTreatCities[0] == null )
					continue;
				
				if( curr.getName().equalsIgnoreCase( remoteTreatCities[0] ) && curr.cubes.size() <= 1 )
					cityButtons.get(lookupCityIndex(curr.getName())).setTouchable( Touchable.disabled );
			}
		}
		else {
			if( !specialOrders )
			{
				ArrayList<CityNode> adjCities = lookupCity(currentPlayer.getCity()).getConnectedCities();
				for (CityNode curr : adjCities) {
					cityButtons.get(lookupCityIndex(curr.getName())).setTouchable(Touchable.enabled);
				}
			}
			else
			{
				ArrayList<CityNode> adjCities = lookupCity(currentPlayer.getCity()).getConnectedCities();
				for (CityNode curr : adjCities) {
					cityButtons.get(lookupCityIndex(curr.getName())).setTouchable(Touchable.enabled);
				}
				
				adjCities = lookupCity(lookupPlayer(specialOrderPlayer).getCity()).getConnectedCities();
				for (CityNode curr : adjCities) {
					cityButtons.get(lookupCityIndex(curr.getName())).setTouchable(Touchable.enabled);
				}
			}
		}
	}

	static void initButtonStage()
	{

		buttonStage = new Stage(parent.screen); //Set up a stage for the ui
		buttonGroup = new Group(); //Set up a stage for the ui

		createCityButtons();
		createActionButtons();

		buttonStage.addActor( buttonGroup );
		buttonStage.addActor( cityButtonGroup );

		updateHandPanelStage();

		Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
	}

	static void createCityButtons()
	{
		cityButtonGroup = new Group();
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
	            	
					if( !waitForButton )
					{
						waitForButton = true;
						if ( actionsRemaining > 0 && clientPlayer == currentPlayer ) {
							
							if( useCityButtonStage ){
								if( opExpertFly )
								{
									String cityName = curr.getName();
									ClientComm.send("OperationsExpertAction/Move/" + cityName + '/' + opExpertFlyCard );
									clientPlayer.roleActionUsed = true;
									useCityButtonStage = false;
									opExpertFly = false;
									opExpertFlyCard = null;
									Gdx.input.setInputProcessor( buttonStage );
								}
								else if( cityButtonsToAirlift )
								{
									String cityName = curr.getName();
									ClientComm.send("Airlift/" + airliftedPlayer + '/' + cityName + '/');
									useCityButtonStage = false;
									cityButtonsToAirlift = false;
									airliftedPlayer = null;
									Gdx.input.setInputProcessor( buttonStage );
								}
								else if( govtGrant )
								{
									final String cityName = curr.getName();
									if( lookupCity(cityName).hasResearchStation )
									{
										Dialog notValid = new Dialog("City already has research station", skin) {
											@Override
											protected void result(Object object) {
												Gdx.input.setInputProcessor( buttonStage);
											};
										
										};
										notValid.button("Okay");
										notValid.show( dialogStage );
										Gdx.input.setInputProcessor( dialogStage );
									}
									else
									{
										if( remResearchStations > 0 )
										{
											ClientComm.send("GovernmentGrant/"+cityName);	
										}
										else
										{
											final Skin tempSkin = new Skin( Gdx.files.internal( "skin/uiskin.json" ) );
											final SelectBox<String> selectbox = new SelectBox<String>( tempSkin );
											
											String[] data = new String[ researchStationCityNames.size() ];
											for( int i = 0; i < data.length; i++ )
											{
												data[i] = researchStationCityNames.get( i );
											}
											selectbox.setItems( data );
											
											Dialog rmRS = new Dialog( "Select Research Station to remove", skin ) {
												@Override
												protected void result(Object object) {
													if( (boolean) true )
													{
														ClientComm.send("GovernmentGrant/" + cityName + '/' + selectbox.getSelected() + '/');
													}
													Gdx.input.setInputProcessor( buttonStage );
												};
											};
											rmRS.getContentTable().add( selectbox );
											rmRS.button( "Select", true );
											rmRS.button( "Cancel", false );
											rmRS.show( dialogStage );
											Gdx.input.setInputProcessor( dialogStage );
										}
									}
									useCityButtonStage = false;
									govtGrant = false;
								}
								else if( rapidVaccine )
								{
									String currName = curr.getName();
									ArrayList<DiseaseCubeInfo> cubes = lookupCity( currName ).getCubesByColor( rvColour );
									if( cubes.size() > 0 )
									{
										if(  ( rvFirstCity == null || rvFirstCity.equals( "" ) ) )
										{
											rvFirstCity = currName;
										}
										Integer currVal = rvCubesToRemove.get( currName );
										if( currVal != null && currVal < cubes.size() )
										{
											rvCubesToRemove.put( currName, rvCubesToRemove.get( currName)+1 );
										}
										else if ( currVal == null )
										{
											rvCubesToRemove.put( currName, 1 );
										}
											
										
										int sum = 0;
										for( Integer numRem : rvCubesToRemove.values() )
											sum += numRem;
										
										String[] reachableCities = new String[ rvCubesToRemove.size() ];
										int j = 0;
										for( String city : rvCubesToRemove.keySet() )
										{
											reachableCities[j] = city;
											j++;
										}
											
										int reachableCubes = getNumCubesOfColourInAdjCities( reachableCities, rvColour );
										
										if( sum > 4  || sum >= reachableCubes )
										{
											rapidVaccine = false;
											useCityButtonStage = true;
											for( String key : rvCubesToRemove.keySet() )
											{
												String message = "RapidVaccine/";
												for( int i = 0; i < rvCubesToRemove.get( key ); i++ )
													message += key + '/';
												ClientComm.send( message ); 
											}
										}
									}	
								}
								else if ( remoteTreat )
								{
									if( remoteTreated < 2 )
									{
										boolean[] diseasesPresent = new boolean[5];
										
										int numDiseasesPresent = 0;
										for( DiseaseCubeInfo cube : curr.cubes )
										{
											if( !diseasesPresent[cube.getColourIndex() ] )
											{
												numDiseasesPresent++;
												diseasesPresent[ cube.getColourIndex() ] = true;
											}
										}
										
										String[] diseases = new String[numDiseasesPresent];
										
										int j = 0;
										for( int i = 0; i < diseasesPresent.length; i++ )
										{
											if( diseasesPresent[i] )
											{
												diseases[j] = DiseaseColour.getDiseaseName( DiseaseColour.values()[i] );
												j++;
											}
										}
										
										final Skin tempSkin = new Skin( Gdx.files.internal( "skin/uiskin.json" ) );
										final SelectBox<String> selector = new SelectBox<String>( tempSkin );
										
										Dialog rtDiag = new Dialog("Pick Disease Colour of cube to remove", skin ) {
											@Override
											protected void result(Object object) {
												if( (boolean)object )
												{
													tempSkin.dispose();
													remoteTreatCities[remoteTreated] = curr.getName();
													remoteTreatDiseases[remoteTreated] = selector.getSelected();
													remoteTreated++;
													Gdx.input.setInputProcessor( buttonStage );
													if( remoteTreated > 1 )
													{
														ClientComm.send("RemoteTreatment/" + remoteTreatCities[0] + '/' + remoteTreatDiseases[0] + '/' + remoteTreatCities[1] + '/' + remoteTreatDiseases[1] + '/' );
														remoteTreated = 0;
														remoteTreatCities = null;
														remoteTreatDiseases = null;
														remoteTreat = false;
														useCityButtonStage = false;
													}
												}
												Gdx.input.setInputProcessor( buttonStage );
											}
										};
										selector.setItems( diseases );
										rtDiag.getContentTable().add( selector );
										rtDiag.button( "Select", true );
										rtDiag.button( "Cancel", false );
										rtDiag.show( dialogStage );
										Gdx.input.setInputProcessor( dialogStage );
									}
								}
								else
								{
									// TODO: Implement highlight all connections between cities
									String cityName = curr.getName();
									String cardName = currentPlayer.getCity();
									ClientComm.send("CharterFlight/" + cityName);
									useCityButtonStage = false;
									Gdx.input.setInputProcessor( buttonStage );
								}
							}
							else {
								if( !specialOrders )
								{
									String cityName = curr.getName();
									ClientComm.send("Drive/" + cityName);
									// IMPLEMENT Call Drive( CityName ) on Server
								}
								else
								{
									int numViable = 0;
									for( CityNode adj : curr.connectedCities )
									{
										if( adj.getName().equals( currentPlayer.getCity() ) )
										{
											numViable++;
										}
										
										if( adj.getName().equals( lookupPlayer( specialOrderPlayer ).getCity() ) )
										{
											numViable++;
										}
									}
									
									String[] viablePlayers = new String[ numViable ];
									int j = 0;
									for( CityNode adj : curr.connectedCities )
									{
										if( adj.getName().equals( currentPlayer.getCity() ) )
										{
											viablePlayers[j] = currentPlayer.getName();
											j++;
										}
										
										if( adj.getName().equals( lookupPlayer( specialOrderPlayer ).getCity() ) )
										{
											viablePlayers[j] = specialOrderPlayer;
											j++;
										}
									}
									
									final Skin tempSkin = new Skin( Gdx.files.internal( "skin/uiskin.json" ) );
									final SelectBox<String> selector = new SelectBox<String>( tempSkin );
									selector.setItems( viablePlayers );
									
									Dialog soDiag = new Dialog( "Pick player to move to " + curr.getName(), skin ) {
										@Override
										protected void result(Object object) {
											if( (boolean)object )
											{
												String selected = selector.getSelected();
												tempSkin.dispose();
												
												if( currentPlayer.getName().equals( selected ) )
												{
													String cityName = curr.getName();
													ClientComm.send("Drive/" + cityName);
												}
												else
												{
													String cityName = curr.getName();
													ClientComm.send("SpecialOrdersMove/" + selected + '/' + cityName + '/' );
												}
											}
											Gdx.input.setInputProcessor( buttonStage );
										};
									};
									soDiag.getContentTable().add( selector );
									soDiag.button( "Select", true );
									soDiag.button( "Cancel", false );
									soDiag.show( dialogStage );
									Gdx.input.setInputProcessor( dialogStage );
								}
							}
						}
						waitForButton = false;
					}
				}
			});

			float x = curr.getXInWindowCoords( windWidth );
			float y = curr.getYInWindowCoords( windHeight );
			cityButton.setBounds(x, y, nodeSize, nodeSize);
			cityButton.setTouchable( Touchable.disabled );


			cityLabel.setFontScale(0.75f);
			cityLabel.setBounds( x - cityLabel.getWidth()/4, y - nodeSize*1.5f, cityLabel.getWidth(), cityLabel.getHeight() );
			cityButtonGroup.addActor( cityLabel);


			cityButtons.add( cityButton );
			cityButtonGroup.addActor(cityButton); //Add the button to the stage to perform rendering and take input.

			//cityButtonStage.addActor( cityButton );	// FIX

			cityLabel.getStyle().fontColor = Color.WHITE;
		}
	}
	

	static boolean playerHasCityCard( PlayerInfo player, String CardName )
	{
		boolean hasCard = false;
		for ( PlayerCardInfo card : currentPlayer.getHand() )
		{
			hasCard |= ( CardName.equals(card.getName() ) );
		}

		return hasCard;
	}

	static void drawResearchStations()
	{
		for( String station : researchStationCityNames )
		{
			CityNode city = lookupCity( station );
			if( city.hasResearchStation )
			{
				float x = city.getXInWindowCoords( windWidth ) + nodeSize/2 - researchStationXSize / 2;
				float y = city.getYInWindowCoords( windHeight ) + nodeSize/2 - researchStationYSize / 2 - researchStationYSize/4.f;
				batch.begin();
				batch.draw( researchStation, x, y, researchStationXSize, researchStationYSize );
				batch.end();
			}
		}
	}

	static void createActionButtons()
	{
        skin.getFont( "font").getData().setScale( actionButtonFontScaleFactor, actionButtonFontScaleFactor);
        
        nextActionButtonHeight = windHeight- actionButtonYSize;
        
        TextButton treatDiseaseButton = new TextButton("Treat Disease", skin);
        treatDiseaseButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)  
        	{
            	if( !waitForButton )
            	{
            		waitForButton = true;
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
		                    		//city.removeCubeByColour( (DiseaseColour)object );
		                    	}
		                    	Gdx.input.setInputProcessor( buttonStage );
		                    	//dialogStage = null;
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
		        		dialogStage.clear();;
		        		Gdx.input.setInputProcessor( dialogStage );
		        		colourSelect.show( dialogStage );
	            	}
	            	waitForButton = false;
            	}
        	}
} );
        treatDiseaseButton.setBounds( 0, nextActionButtonHeight, actionButtonXSize, actionButtonYSize);
        nextActionButtonHeight -= actionButtonYSize*1.125f;
        buttonGroup.addActor( treatDiseaseButton );
        
        
        TextButton shareKnowledgeButton = new TextButton("Share Knowledge", skin);
        shareKnowledgeButton.addListener( new ChangeListener() {
        	public void changed(ChangeEvent event, Actor actor) 
        	{
        		if( !waitForButton )
            	{
            		waitForButton = true;
	        		if ( currentPlayer == clientPlayer && actionsRemaining > 0)
	        		{
	        			dialogStage.clear();
	        			String cityName = currentPlayer.getCity();
	        			boolean hasCard = playerHasCityCard( currentPlayer, cityName );
	        			if( hasCard )
	        			{
	        				ArrayList<PlayerInfo> playerList = new ArrayList<PlayerInfo>();
	        				for ( PlayerInfo player : players )
	        				{
	        					if( player != null && player != currentPlayer )
	        					{
	        						if ( player.getCity().equals(cityName) )
	        						{
	        							playerList.add( player );
	        						}
	        					}
	        				}
	        				
	        				String[] list = new String[ playerList.size() ];
	        				if ( playerList.size() != 0 )
	        				{
	            				for ( int i = 0; i < playerList.size(); i++ )
	            				{
	            					list[ i ] = playerList.get( i ).getName();
	            				}
	            				
	            				final Skin tempSkin = new Skin( Gdx.files.internal( "skin/uiskin.json" ) );
	                			final SelectBox<String> selectBox=new SelectBox<String>(tempSkin);
	                			
	                			Dialog skPrompt = new Dialog("Choose a Player to give card to",skin){
	                				protected void result(Object object){
	                					String selected = selectBox.getSelected();
	                					tempSkin.dispose();
	                		            Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
	                		            //dialogStage = null;
	                		            ClientComm.send("ShareKnowledge/"+selected);
	                		        }
	                			};
	
	                			skPrompt.setSize(250,150);
	                			skPrompt.setPosition( windWidth / 2 - skPrompt.getWidth() / 2, windHeight / 2 - skPrompt.getHeight() / 2 );
	                			skPrompt.button( "Select" );
	                			selectBox.setItems( list );
	                			skPrompt.getContentTable().add(selectBox);
	
	                			dialogStage.addActor(skPrompt);
	                			Gdx.input.setInputProcessor(dialogStage);
	        				}
	        				else
	        				{
	        					Dialog skPrompt = new Dialog( "No Valid Targets", skin ){
	        				        protected void result(Object object)
	        				        {
	        				            Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
	        				            //dialogStage = null;
	        				        }
	        					};
	
	        					skPrompt.button("Okay");
	        					dialogStage.clear();;
	        					skPrompt.show( dialogStage );
	        					Gdx.input.setInputProcessor(dialogStage);
	        				}
	        			}
	        			else
	        			{
	        				ArrayList<PlayerInfo> playerList = new ArrayList<PlayerInfo>();
	        				for ( PlayerInfo player : players )
	        				{
	        					if( player != null && player != currentPlayer )
	        					{
	        						if ( player.getCity().equals(cityName) )
	        						{
	        							playerList.add( player );
	        						}
	        					}
	        				}
	        				
	        				PlayerInfo holdingPlayer = null;
	        				for ( PlayerInfo player : players )
	        				{
	        					if( player == null )
	        						continue;
	        					
	        					for( PlayerCardInfo card : player.getHand() )
	        					{
	        						if ( card.getName().equals( cityName ) )
	        						{
	        							holdingPlayer = player;
	        							break;
	        						}
	        					}
	        					if ( holdingPlayer == player && player != null )
	        						break;
	        				}
	        				if ( holdingPlayer != null )
	        				{
	        					Dialog skPrompt = new Dialog( "Take " + cityName + " from " + holdingPlayer.getName() , skin ){
	        				        protected void result(Object object)
	        				        {
	        				            Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
	        				            //dialogStage = null;
	        				        }
	        					};
	
	        					skPrompt.button("Okay", true);
	        					skPrompt.button("No", false);
	        					dialogStage.clear();;
	        					skPrompt.show( dialogStage );
	        					Gdx.input.setInputProcessor(dialogStage);
	        				}
	        				else
	        				{
	        					Dialog skPrompt = new Dialog( "No Valid Targets", skin ){
	        				        protected void result(Object object)
	        				        {
	        				            Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
	        				            //dialogStage = null;
	        				        }
	        					};
	
	        					skPrompt.button("Okay");
	        					dialogStage.clear();;
	        					skPrompt.show( dialogStage );
	        					Gdx.input.setInputProcessor(dialogStage);
	        				}
	        			}
	        		}
	        		waitForButton = false;
            	}
        	}
        } );
        shareKnowledgeButton.setBounds( 0, nextActionButtonHeight, actionButtonXSize, actionButtonYSize);
        nextActionButtonHeight -= actionButtonYSize*1.125f;
        buttonGroup.addActor( shareKnowledgeButton );
        
        TextButton showInfectionDiscard = new TextButton( "Show Infection Discard", skin );
        showInfectionDiscard.addListener( new ChangeListener() {
        	public void changed(ChangeEvent event, Actor actor) 
        	{
        		if( !waitForButton )
            	{
            		waitForButton = true;
	        		Dialog showInfectionDiscard = new Dialog( "Infection Discardpile", skin ){
	        	        protected void result(Object object)
	        	        {
	        	            Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
	        	            //dialogStage = null;
	        	        }
	        		};
	        		        		
	        		String[] names = new String[infectionDiscardPile.size()];
	        		List<String> list = new List<>(skin);
	        		for( int i = 0; i < names.length; i++ )
	        		{
	        			//Label label = new Label( cityName );
	        			//infectionDiscardTable.add( cityName );
	        			//infectionDiscardTable.row();
	        			names[i] = infectionDiscardPile.get( i );
	        		}
	        		list.setItems( names );
	        		discardPane = new ScrollPane( list );
	        		discardPane.setFillParent(false);
	        		
	        		showInfectionDiscard.getContentTable().add( discardPane ).width(100).height(100);
	        		showInfectionDiscard.button( "Close" );
	        		dialogStage.clear();;
	        		showInfectionDiscard.show( dialogStage );
		            Gdx.input.setInputProcessor(dialogStage); //Start taking input from the ui
		            waitForButton = false;
            	}
        	}
        } );
        showInfectionDiscard.setBounds( 0, nextActionButtonHeight, actionButtonXSize, actionButtonYSize);
        nextActionButtonHeight -= actionButtonYSize*1.125f;
        buttonGroup.addActor( showInfectionDiscard );
        
        TextButton showPlayerDiscard = new TextButton( "Show Player Discard", skin );
        showPlayerDiscard.addListener( new ChangeListener() {
        	public void changed(ChangeEvent event, Actor actor) 
        	{
        		if( !waitForButton )
            	{
            		waitForButton = true;
	        		Dialog showPlayerDiscard = new Dialog( "Player Discardpile", skin ){
	        	        protected void result(Object object)
	        	        {
	        	            Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
	        	            //dialogStage = null;
	        	        }
	        		};
	
	        		
	        		String[] names = new String[playerDiscardPile.size()];
	        		List<String> list = new List<>(skin);
	        		for( int i = 0; i < names.length; i++ )
	        		{
	        			//Label label = new Label( cityName );
	        			//infectionDiscardTable.add( cityName );
	        			//infectionDiscardTable.row();
	        			names[i] = playerDiscardPile.get( i );
	        		}
	        		list.setItems( names );
	        		discardPane = new ScrollPane( list );
	        		discardPane.setFillParent(false);
	        		
	        		showPlayerDiscard.getContentTable().add( discardPane ).width(100).height(100);
	        		showPlayerDiscard.button( "Close" );
	        		dialogStage.clear();;
	        		showPlayerDiscard.show( dialogStage );
		            Gdx.input.setInputProcessor(dialogStage); //Start taking input from the ui
		            waitForButton = false;
            	}
        	}
        } );
        showPlayerDiscard.setBounds( 0, nextActionButtonHeight, actionButtonXSize, actionButtonYSize);
        nextActionButtonHeight -= actionButtonYSize*1.125f;
        buttonGroup.addActor( showPlayerDiscard );
        
        TextButton createResearchStation = new TextButton( "Build Research Station", skin );
        createResearchStation.addListener( new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if( !waitForButton )
				{
					waitForButton = true;
					if( clientPlayer == currentPlayer )
					{
						if( playerHasCityCard( currentPlayer, currentPlayer.getCity() ) )
						{
							if( !lookupCity( currentPlayer.getCity() ).hasResearchStation )
							{
								if( remResearchStations > 0 )
								{	
									ClientComm.send( "BuildResearchStation/" );
								}
								else
								{
									String[] list = new String[researchStationCityNames.size()];
									for ( int i = 0; i < researchStationCityNames.size(); i++ )
				    				{
				    					list[ i ] = researchStationCityNames.get(i);
				    				}
				    				
				    				Skin tempSkin = new Skin( Gdx.files.internal( "skin/uiskin.json" ) );
				        			final SelectBox<String> selectBox=new SelectBox<String>(tempSkin);
				        			
				        			Dialog rsPrompt = new Dialog("Choose a Research Station to remove",skin){
				        				protected void result(Object object){
				        					if( (Boolean)(object) )
				        					{
				            					String selected = selectBox.getSelected();
				            					ClientComm.send( "BuildResearchStation/"+selected );
				            		            Gdx.input.setInputProcessor(buttonStage);
				        					}
				        					else
				        					{
				            		            Gdx.input.setInputProcessor(buttonStage);
				        					}
				        		        }
				        			};
				
				        			rsPrompt.setSize(250,150);
				        			rsPrompt.setPosition( windWidth / 2 - rsPrompt.getWidth() / 2, windHeight / 2 - rsPrompt.getHeight() / 2 );
									rsPrompt.button("Okay", true);
									rsPrompt.button("Cancel", false);
				
									selectBox.setItems( list );
				        			rsPrompt.getContentTable().add(selectBox);
				
				        			dialogStage.addActor(rsPrompt);
				        			Gdx.input.setInputProcessor(dialogStage);
								}
							}
							else
							{
								Dialog rsPrompt = new Dialog("    Don't have the necessary card to build! ",skin){
			        				protected void result(Object object){
			        		            Gdx.input.setInputProcessor(buttonStage);
			        		        }
			        			};
			
			        			rsPrompt.setSize(375,90);
			        			rsPrompt.setPosition( windWidth / 2 - rsPrompt.getWidth() / 2, windHeight / 2 - rsPrompt.getHeight() / 2 );
								rsPrompt.button("Okay", true);
			        			dialogStage.addActor(rsPrompt);
			        			Gdx.input.setInputProcessor(dialogStage);
							}
						}
						else
						{
							
						}
					}
					waitForButton = false;
				}
        	}
        });
        createResearchStation.setBounds( 0, nextActionButtonHeight, actionButtonXSize, actionButtonYSize);
        nextActionButtonHeight -= actionButtonYSize*1.125f;
        buttonGroup.addActor( createResearchStation );
        
        TextButton cureDisease = new TextButton( "Cure Disease", skin );
        cureDisease.addListener( new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if( !waitForButton )
				{
					waitForButton = true;
					if( clientPlayer == currentPlayer )
					{
						if(  lookupCity( currentPlayer.getCity() ).hasResearchStation  ) 
						{	
							ArrayList<PlayerCardInfo> hand = currentPlayer.getHand();
							int[] cardNumByColor = new int[5];
							for( int i = 0; i < 5; i++ )
								cardNumByColor[i] = 0;
							
							for( PlayerCardInfo card : hand )
							{
								CityNode city = lookupCity(card.getName());
								if( city == null)
									continue;
								
								String colourName = city.getColour();
								if( colourName != null && colourName.equals("") )
									cardNumByColor[ DiseaseColour.lookupColourByName( colourName  ).ordinal() ]++;
							}
							
							String diseaseName = "";
							ArrayList<String> diseases;
							for( int i = 0; i < 5; i++ )
							{
								if( cardNumByColor[i] >= currentPlayer.cardsToCure )
									diseaseName = DiseaseColour.getDiseaseName( DiseaseColour.values()[i] );
		 					}
							
							if( diseaseName.equals("") )
							{
								Dialog cdPrompt = new Dialog( "You don't have the necessary cards to cure anything", skin ){
		    				        protected void result(Object object)
		    				        {
		    				            Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
		    				            //dialogStage = null;
		    				        }
		    					};
		
		    					cdPrompt.button("Okay");
		    					dialogStage.clear();;
		    					cdPrompt.show( dialogStage );
		    					Gdx.input.setInputProcessor(dialogStage);
							}
							else
							{
								possibleSelections = new ArrayList<PlayerCardInfo>();
								selections = new ArrayList<PlayerCardInfo>();
								int handIdx = 0;
								for( final PlayerCardInfo card : hand )
								{
									if ( lookupCity(card.getName()) == null )
										continue;
									
									if( lookupCity(card.getName()).getColour().equalsIgnoreCase( diseaseName ) )
									{
										possibleSelections.add( card );
									}
								}
								
								float cardSizeX = playerCardXSize*1.5f;
								float cardSizeY = playerCardYSize*1.5f;
								float cardOffset = playerCardXOffset*1.5f;
								float cardPosX = windWidth / 2 - possibleSelections.size()*( cardSizeX + cardOffset ) / 2; 
								float cardPosY = windHeight / 2 - cardSizeY / 2;
								for( final PlayerCardInfo card : possibleSelections )
								{
									Texture cardTexture	 							= cityCardTextures[ lookupCityIndex( card.getName() ) ];
									TextureRegion TR_cardTexture 					= new TextureRegion( cardTexture );
									final TextureRegionDrawable Draw_cardTexture 	= new TextureRegionDrawable( TR_cardTexture );
									
									Texture selectedCardTexture	 							= selectedCityCardTextures[ lookupCityIndex( card.getName() ) ];
									TextureRegion TR_selectedCardTexture 					= new TextureRegion( selectedCardTexture );
									final TextureRegionDrawable Draw_selectedCardTexture 	= new TextureRegionDrawable( TR_selectedCardTexture );
									
									ButtonStyle cityButtonStyle = new ButtonStyle();
									cityButtonStyle.up			= Draw_cardTexture;
									cityButtonStyle.down		= Draw_cardTexture;
									cityButtonStyle.checked		= Draw_selectedCardTexture;
									
							        button = new Button( cityButtonStyle );
							        button.setBounds( cardPosX, cardPosY, cardSizeX, cardSizeY );
							        
							        cardSelectStage.addActor( button );
							        
							        button.addListener( new ChangeListener() {
										@Override
										public void changed(ChangeEvent event, Actor actor) {
											if( !waitForButton )
											{	
												waitForButton = true;
												if( !((Button)actor).isChecked() )
												{
													for( int i = 0; i < selections.size(); i++ )
													{
														if( selections.get(i).getName().equals( card.getName() ) )
														{
															selections.remove( i );
															break;
														}
													}
													cardsToSelect++;
												}
												else
												{
													selections.add(card);
													cardsToSelect--;
												}
												waitForButton = false;
											}
										}
									});
							        cardPosX += cardSizeX + cardOffset;
								}
								showCardSelectStage = true;
								Gdx.input.setInputProcessor( cardSelectStage );
								cardsToSelect = 5;
								showCardSelectAction = "DiscardForCure";
							}
						}
						else
						{
							Dialog cdPrompt = new Dialog( "You need to be in a city with a research station", skin ){
	    				        protected void result(Object object)
	    				        {
	    				            Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
	    				            //dialogStage = null;
	    				        }
	    					};
	
	    					cdPrompt.button("Okay");
	    					dialogStage.clear();;
	    					cdPrompt.show( dialogStage );
	    					Gdx.input.setInputProcessor(dialogStage);
						}
					}
					waitForButton = false;
				}
			}
        });
        cureDisease.setBounds( 0, nextActionButtonHeight, actionButtonXSize, actionButtonYSize);
        nextActionButtonHeight -= actionButtonYSize*1.125f;
        buttonGroup.addActor( cureDisease );
        
        TextButton endTurn = new TextButton( "End Turn", skin );
        endTurn.addListener( new ChangeListener() {
            @Override
			public void changed(ChangeEvent event, Actor actor) {
            	if( !waitForButton )
            	{
            		waitForButton = true;
            		if( clientPlayer == currentPlayer )
            		{
		            	Dialog endTurnDiag = new Dialog("End Turn?" , skin ){
		            		@Override
		            		protected void result(Object object) {
		            			if ( (boolean) ( object ) )
		            			{
		            				ClientComm.send("EndTurn/");
		            				turnEnded = true;
		            			}
		        				Gdx.input.setInputProcessor( buttonStage );
		            		}
		            	};
		
		        		//dialogStag/e = new Stage( parent.screen );//
		        		dialogStage.clear();
		        		
		        		endTurnDiag.button( "Yes", true );
		        		endTurnDiag.button( "No", false );
		        		
		                Gdx.input.setInputProcessor(dialogStage); //Start taking input from the ui
		                endTurnDiag.show( dialogStage );
            		}
	                waitForButton = false;
            	}
			}
        });
        endTurn.setBounds( 0, nextActionButtonHeight, actionButtonXSize, actionButtonYSize);
        nextActionButtonHeight -= actionButtonYSize*1.125f;
        buttonGroup.addActor( endTurn );
        
        createOperationExpertButton();
        
        createArchivistButton();
        
        createEpidemiologistButton();
	}
	
	static void createOperationExpertButton()
	{
		if( clientPlayer.role.equalsIgnoreCase("operationsexpert") )
		{
			TextButton endTurn = new TextButton( "Role Action", skin );
	        endTurn.addListener( new ChangeListener() {
	            @Override
				public void changed(ChangeEvent event, Actor actor) {
	            	if( !waitForButton && !clientPlayer.roleActionUsed )
	            	{
	            		waitForButton = true;
	            		if( currentPlayer == clientPlayer )
	            		{	
			            	Dialog opExpDiag = new Dialog("Which Role Action do you want to use?" , skin ){
			            		@Override
			            		protected void result(Object object) {
			            			waitForButton = true;
			            			if ( ( (String)  object ).equals( "build") )
			            			{
			            				if( lookupCity( currentPlayer.getCity() ).hasResearchStation )
			            				{
			            	            	Dialog confirmFlight = new Dialog("City already has Research Station?" , skin ){
			            	            		@Override
			            	            		protected void result(Object object) {

			            	        				Gdx.input.setInputProcessor( buttonStage );
			            	            		}
			            	            	};
			            	
			            	        		//dialogStag/e = new Stage( parent.screen );//
			            	        		dialogStage.clear();
			            	        		
			            	        		confirmFlight.button("OK");
			            	        		
			            	                Gdx.input.setInputProcessor(dialogStage); //Start taking input from the ui
			            	                confirmFlight.show( dialogStage );
			            				}
			            				else if( remResearchStations > 0 )
			            				{
											ClientComm.send( "OperationsExpertAction/Build/" );
											clientPlayer.roleActionUsed = true;
			            				}
			            				else
			            				{
			            					String[] list = new String[researchStationCityNames.size()];
											for ( int i = 0; i < researchStationCityNames.size(); i++ )
						    				{
						    					list[ i ] = researchStationCityNames.get(i);
						    				}
						    				
						    				Skin tempSkin = new Skin( Gdx.files.internal( "skin/uiskin.json" ) );
						        			final SelectBox<String> selectBox=new SelectBox<String>(tempSkin);
						        			
						        			Dialog rsPrompt = new Dialog("Choose a Research Station to remove",skin){
						        				protected void result(Object object){
						        					if( (Boolean)(object) )
						        					{
						            					String selected = selectBox.getSelected();
						            					ClientComm.send( "BuildResearchStation/"+selected );
						            		            Gdx.input.setInputProcessor(buttonStage);
						        					}
						        					else
						        					{
						            		            Gdx.input.setInputProcessor(buttonStage);
						        					}
						        		        }
						        			};
						
						        			rsPrompt.setSize(250,150);
						        			rsPrompt.setPosition( windWidth / 2 - rsPrompt.getWidth() / 2, windHeight / 2 - rsPrompt.getHeight() / 2 );
											rsPrompt.button("Okay", true);
											rsPrompt.button("Cancel", false);
						
											selectBox.setItems( list );
						        			rsPrompt.getContentTable().add(selectBox);
						
						        			dialogStage.addActor(rsPrompt);
						        			Gdx.input.setInputProcessor(dialogStage);
			            				}
			            			}
			            			else if ( ( (String)  object ).equals( "move") )
			            			{
			            				dialogStage.clear();;
			            				Skin tempSkin = new Skin( Gdx.files.internal( "skin/uiskin.json" ) );
			            				final SelectBox<String> selectBox=new SelectBox<String>(tempSkin);

			            				Dialog discardPrompt = new Dialog("Choose a card to Discard",skin){
			            					protected void result(Object object){
			            						final String selected = selectBox.getSelected();
			            						
		        								Dialog confirmFlight = new Dialog("Use " + selected + " city card to fly anywhere?", skin) {
		        									@Override
		        									protected void result(Object object) {
		        										if ((boolean) (object)) {
		        											useCityButtonStage = true;
		        											opExpertFly = true;
		        											opExpertFlyCard = selected;
		        										}
		        										Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
		        										//dialogStage = null;
		        									}
		        								};

		        								//dialogStag/e = new Stage( parent.screen );//
		        								dialogStage.clear();

		        								confirmFlight.button("Yes", true);
		        								confirmFlight.button("No", false);

		        								Gdx.input.setInputProcessor(dialogStage); //Start taking input from the ui
		        								confirmFlight.show(dialogStage);

			        							
			            						//dialogStage = null;
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
			            			else
			            			{
				        				Gdx.input.setInputProcessor( buttonStage );
			            			}
			            			waitForButton = false;
			            		}
			            	};
			            	//dialogStag/e = new Stage( parent.screen );//
			        		dialogStage.clear();
			        		
			        		opExpDiag.button( "Build", "build" );
			        		opExpDiag.button( "Move", "move" );
			        		opExpDiag.button( "Cancel", "cancel" );
			        		
			                Gdx.input.setInputProcessor(dialogStage); //Start taking input from the ui
			                opExpDiag.show( dialogStage );
	            		}
		
		        		
		                waitForButton = false;
	            	}
				}
	        });
	        endTurn.setBounds( 0, nextActionButtonHeight, actionButtonXSize, actionButtonYSize);
	        nextActionButtonHeight -= actionButtonYSize*1.125f;
	        buttonGroup.addActor( endTurn );
		}
	}

	static void createArchivistButton()
	{
		if( clientPlayer.role != null )
		{
			if( clientPlayer.role.equalsIgnoreCase( "Archivist" ) )
			{
				TextButton archivistBtn = new TextButton( "Archivist Role Action", skin );
		        archivistBtn.addListener( new ChangeListener() {
		            @Override
					public void changed(ChangeEvent event, Actor actor) {
		            	if( !waitForButton && !clientPlayer.roleActionUsed )
		            	{
		            		waitForButton = true;
		            		boolean cardInDiscard = false;
		    				for( String card : playerDiscardPile )
		    				{
		    					if( card.equalsIgnoreCase( clientPlayer.getCity() ) )
		    						cardInDiscard = true;
		    				}
		    				
		    				if( cardInDiscard )
		    				{
		    					dialogStage.clear();
		    					Dialog archivistDialog = new Dialog( "Draw Current CityCard from Discard", skin ) {
		    						@Override
		    						protected void result(Object object) {
		    							if( (Boolean) object )
		    							{
		    								ClientComm.send("ArchivistAction/");
		    								clientPlayer.roleActionUsed = true;
		    							}
		    							Gdx.input.setInputProcessor( buttonStage );
		    						}
		    					};
		    					archivistDialog.button( "Okay", true );
		    					archivistDialog.button( "Cancel", false );
		    					archivistDialog.show( dialogStage );
		    					Gdx.input.setInputProcessor( dialogStage );
		    				}
		    				else
		    				{
		    					dialogStage.clear();
		    					Dialog archivistDialog = new Dialog( "Current City Card not in Discard", skin ) {
		    						@Override
		    						protected void result(Object object) {
		    							Gdx.input.setInputProcessor( buttonStage );
		    						}
		    					};
		    					archivistDialog.button( "Okay" );
		    					archivistDialog.show( dialogStage );
		    					Gdx.input.setInputProcessor( dialogStage );
		    				}

		            		waitForButton = false;
		            	}
					}
		        });
		        archivistBtn.setBounds( 0, nextActionButtonHeight, actionButtonXSize, actionButtonYSize);
		        nextActionButtonHeight -= actionButtonYSize*1.125f;
		        buttonGroup.addActor( archivistBtn );
			}
		}
	}
	
	static void createEpidemiologistButton()
	{
		if ( clientPlayer.role != null )
		{
			if( clientPlayer.role.equalsIgnoreCase( "Epidemiologist" ) )
			{
				TextButton epidemiologistBtn = new TextButton( "Epidemiologist Action", skin );
		        epidemiologistBtn.addListener( new ChangeListener() {
		            @Override
					public void changed(ChangeEvent event, Actor actor) {
		            	if( !waitForButton && !clientPlayer.roleActionUsed )
		            	{
		            		waitForButton = true;
		            		if( clientPlayer == currentPlayer )
		            		{
		            			ArrayList<String> list = new ArrayList<String>();
			            		for( int i = 0; i < players.length; i++ )
			            		{
			            			if( currentPlayer != players[i] )
			            			{
			            				if( players[i] != null )
			            				{
			            					if( players[i].getCity().equalsIgnoreCase(currentPlayer.getCity()))
			            						if( players[i].hand.size() > 0 )
			            							list.add( players[i].getName() );
			            				}
			            			}
			            		}
			            		
			            		if( list.size() > 0 )
			            		{
			            			String[] data = new String[list.size()];
			            			for( int i = 0; i < list.size(); i++ )
			            			{
			            				data[i] = list.get(i);
			            			}
			            			

			            			final Skin tempSkin = new Skin( Gdx.files.internal( "skin/uiskin.json" ) );
			            			final SelectBox<String> selectBox=new SelectBox<String>(tempSkin);
			            			selectBox.setItems( data );
			            			dialogStage.clear();
			    					Dialog epiDialog = new Dialog( "Select player to card take from", skin ) {
			    						@Override
			    						protected void result(Object object) {
			    							if( (Boolean)object )
			    							{
				    							dialogStage.clear();
				    							final String selection = selectBox.getSelected();
				    							PlayerInfo selected = null;
				    							for( PlayerInfo curr : players )
				    								if( selection.equalsIgnoreCase( curr.getName() ) )
				    									selected = curr;
				    							
				    							
				    							String[] handData = new String[ selected.getHandSize() ];
				    							for( int i = 0; i < handData.length; i++ )
				    								handData[i] = selected.getHand().get(i).name;
				    							
				    							final SelectBox<String> handSelectBox=new SelectBox<String>(tempSkin);
				    							handSelectBox.setItems( handData );
						    					Dialog epiDialog = new Dialog( "Select card to take", skin ) {
						    						@Override
						    						protected void result(Object object) {
						    							if( (Boolean)object )
						    							{
							    							String cardSelected = handSelectBox.getSelected();
							    							ClientComm.send("EpidemiologistAction/" + selection + '/' + cardSelected );
							    							currentPlayer.roleActionUsed = true;
						    							}
						    							Gdx.input.setInputProcessor( buttonStage );
						    						}
						    					};
						    					epiDialog.getContentTable().add( handSelectBox );
						    					epiDialog.button( "Select", true );
						    					epiDialog.button( "Cancel", false );
						    					epiDialog.show( dialogStage );
			    							}
					    					Gdx.input.setInputProcessor( dialogStage );
			    						}
			    					};
			    					epiDialog.getContentTable().add( selectBox );
			    					epiDialog.button( "Select", true );
			    					epiDialog.button( "Cancel", false );
			    					epiDialog.show( dialogStage );
			    					Gdx.input.setInputProcessor( dialogStage );
			            			
			            		}
			            		else
			            		{
			            			dialogStage.clear();
			    					Dialog epiDialog = new Dialog( "No Other Players in City with cards", skin ) {
			    						@Override
			    						protected void result(Object object) {
			    							Gdx.input.setInputProcessor( buttonStage );
			    						}
			    					};
			    					epiDialog.button( "Okay" );
			    					epiDialog.show( dialogStage );
			    					Gdx.input.setInputProcessor( dialogStage );
			            		}
		            		
		            		}
		            		waitForButton = false;
		            	}
					}
		        });
		        epidemiologistBtn.setBounds( 0, nextActionButtonHeight, actionButtonXSize, actionButtonYSize);
		        nextActionButtonHeight -= actionButtonYSize*1.125f;
		        buttonGroup.addActor( epidemiologistBtn );
			}
		}
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	public void updateCardShowPanelStage()
	{
		if( showCardSelectAction == null )
			return;
		
		switch( showCardSelectAction )
		{
			case( "DiscardForCure"):
			{
				if( cardsToSelect < 1 )
				{
					showCardSelectStage = false;
					Gdx.input.setInputProcessor( buttonStage );
					String message = "CureDisease/";
					for( PlayerCardInfo card : selections )
						message += card.getName();
					
					selections.clear();
					possibleSelections.clear();
					ClientComm.send( message );
					showCardSelectAction = "";
				}
			} break;
			
			case( "Forecast" ):
			{
				if( cardsToSelect < 1 )
				{
					showCardSelectStage = false;
					Gdx.input.setInputProcessor( buttonStage );
					String message = "ForecastResponse/";
					for( String card : forecastSelections )
						message += card;
					
					forecastSelections.clear();
					possibleForecastSelections.clear();
					ClientComm.send( message );
					showCardSelectAction = "";
					}
			} break;
		}
	}
	
	public void drawConnections()
	{
		batch.begin();
		batch.draw(connectionsTexture, 0, 0, windWidth, windHeight);
		batch.end();
	}

	public void drawMarkers()
	{
		for( int i = 0; i < diseaseStatuses.size(); i++ )
		{
			if( (DiseaseStatus)diseaseStatuses.values().toArray()[i] == DiseaseStatus.ACTIVE )
			{
				markerTextures[i] = activeMarkerTextures[i];
			} 
			else
			{
				markerTextures[i] = cureMarkerTextures[i];
			}
		}
		
		batch.begin();
		batch.draw(greyBarOfNumbers, windWidth*0.3425f-15f-4*(markerXSize+15f)+50f, windHeight-markerYSize*3.75f, 4*(markerXSize+15f)+39f, markerYSize*6.75f);
		for( int i = 0; i < markerTextures.length; i++ )
		{
			batch.draw( markerTextures[i],windWidth*0.3575f-15f-i*(markerXSize+15f), windHeight - markerYSize*1.075f, markerXSize, markerYSize );
		}
		batch.end();
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
						String[] message = ClientComm.messageQueue.get(0);
						if ( message != null )
						{
							if ( message.length > 0 )
							{
								System.out.println( message[0] );
								String toExecute = message[0];
								if(a.getName().equals(toExecute))
								{
									ClientComm.messageQueue.remove( 0 );
									a.execute(message);
									break;
								}
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


		drawConnections();


		///////////////////////////////////////////////////////////////////////////////////////////////////
		cubeOrbitRotation = (float) (( cubeOrbitRotation - delta*cubeOrbitRate ) % (Math.PI * 2));
		updateDiseaseStage();
		diseaseStage.draw();

		updateCityTouchability();

		updateHandPanelStage();

		
		if ( currentPlayer == clientPlayer && actionsRemaining <= 0 )
		{
			if ( !turnEnded )
			{
				ClientComm.send("EndTurn");
				turnEnded = true;
				/*
				Dialog endTurnDialog = new Dialog( "No Actions Left", skin ){
			        protected void result(Object object)
			        {
			            Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
						ClientComm.send("EndTurn");
			            //dialogStage = null;
			        }
				};
				endTurnDialog.button("End Turn");
				dialogStage.clear();;
				endTurnDialog.show( dialogStage );
				Gdx.input.setInputProcessor(dialogStage);
				turnEnded = true;
				endTurnDialog.clear();
				*/
			}
		}
		
		buttonStage.act();
		buttonStage.draw();

		updatePawnStage();
		pawnStage.draw();

		if ( dialogStage != null )
		{
			dialogStage.draw();
			dialogStage.act();
		}

		batch.begin();
		batch.draw(greyBarOfNumbers, windWidth*0.35f, windHeight*0.917f, 520f, 150f);
		batch.draw(deck, windWidth*0.378f, windHeight*0.9735f, 20f, 25f);
		batch.draw(yellow, windWidth*0.404f, windHeight*0.968f, 50f, 35f);
		batch.draw(red, windWidth*0.444f, windHeight*0.968f, 50f, 35f);
		batch.draw(blue, windWidth*0.484f, windHeight*0.968f, 50f, 35f);
		batch.draw(black, windWidth*0.524f, windHeight*0.968f, 50f, 35f);
		batch.draw(researchStation, windWidth*0.564f, windHeight*0.968f, 40f, 35f);
		batch.end();

		displayCurrPlayer();
		displayInfectionRate( currentInfectionRateIdx );
		displayOutbreakCounter( outbreaks );
		displayNumbers( remCardsDeck, remYellowCubes, remRedCubes, remBlueCubes, remBlackCubes, remResearchStations);	// ADDED
		//displayNumbers( 23, 23, 23, 23, 23 );
		displayNumberOfActionsLeft( actionsRemaining );
		displayPlayerColours();
		drawResearchStations();
		drawMarkers();
		
		updateCardShowPanelStage();
		
		if( showCardSelectStage )
		{
			cardSelectStage.draw();
			cardSelectStage.act();
		}
		
		displayRapidVaccineNumbers();
	}

	@Override
	public void resize(int width, int height) {
		parent.screen.update( windWidth, windHeight );
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

		batch.begin();
			font.setColor(colors[currentPlayer.getColour().ordinal()]);
			handPlayerFont.setColor(colors[handShownPlayer.getColour().ordinal()]);
			secondPart.draw(batch, currentPlayer.getName() + "'s Turn", windWidth*0.87f, windHeight*0.98f);
			font.draw(batch, currentPlayer.getName() + "'s", windWidth*0.87f, windHeight*0.98f);
			if( handShownPlayer == clientPlayer )
			{
				secondPart.draw(batch, "Your Hand:",  15f, handPanelYSize*1.3f );
			}
			else
			{
				handPlayerFont.draw(batch, handShownPlayer.getName() + "'s Hand:", 15f, handPanelYSize*1.3f);
			}
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

	void displayOutbreakCounter(int amountOfOutbreaks){

		BitmapFont font = new BitmapFont();

		for(int i = 0; i < amountOfOutbreaks; i++){
			if(amountOfOutbreaks >= 0 && amountOfOutbreaks <= 8){
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
		if( amountOfOutbreaks > 0){
			outbreakRateCircles[amountOfOutbreaks-1].dispose();
		}
	}

	void displayNumbers(int deckNo, int yellowNo, int redNo, int blueNo, int blackNo, int researchNo){

		BitmapFont deckFont = new BitmapFont();
		BitmapFont yellowFont = new BitmapFont();
		BitmapFont redFont = new BitmapFont();
		BitmapFont blueFont = new BitmapFont();
		BitmapFont blackFont = new BitmapFont();
		BitmapFont researchFont = new BitmapFont();



		batch.begin();

		deckFont.draw(batch, Integer.toString(deckNo)+",", windWidth*0.3925f, windHeight*0.99f);
		yellowFont.draw(batch, Integer.toString(yellowNo)+",", windWidth*0.426f, windHeight*0.99f);
		redFont.draw(batch, Integer.toString(redNo)+",", windWidth*0.466f, windHeight*0.99f);
		blueFont.draw(batch, Integer.toString(blueNo)+",", windWidth*0.506f, windHeight*0.99f);
		blackFont.draw(batch, Integer.toString(blackNo)+",", windWidth*0.546f, windHeight*0.99f);
		researchFont.draw(batch, Integer.toString(researchNo)+",", windWidth*0.582f, windHeight*0.99f);

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
		PlayerInfo[] playersInOrder = new PlayerInfo[ players.length ];
		int currPlayerIdx = 0;
		for( int i = 0; i < players.length; i++ )
		{
			if( currentPlayer == players[i] )
				currPlayerIdx = i;
		}
		
		for( int i = 0; i < players.length; i++ )
		{
			playersInOrder[i] = players[(currPlayerIdx + i) % players.length];
		}
		
		
		Pixmap panel = new Pixmap( 150, 25*(players.length+2), Format.RGB888 );
		panel.setColor( Color.GRAY );
		panel.fillRectangle(0, 0, 150, 25*(players.length+2) );
		Texture panelText = new Texture( panel );
		
		float localheight = nextActionButtonHeight;
		localheight += actionButtonYSize*1.125 - 15f;
		batch.begin();
		batch.draw( panelText, 0, localheight - 25*(players.length+2), 150, 25*(players.length+2) );
		localheight -= 15f;
		playersColores.setColor( Color.WHITE );
		playersColores.getData().setScale( 1.25f );
		playersColores.draw( batch, "Turn Order:", 15f, localheight );
		localheight -= 25f;
		for(int i = 0; i < players.length; i++){
			if(playersInOrder[i] != null){
				colourToBeDrawn.setColor(colors[playersInOrder[i].getColour().ordinal()]);
				colourToBeDrawn.getData().setScale(1.25f);
				colourToBeDrawn.draw(batch, playersInOrder[i].getName(), windWidth * 0.025f, localheight );
				localheight -= 25f;
				//playersColores.getData().setScale(1.25f);
				//playersColores.draw( batch, players[i].getName() + " is ", windWidth * 0.01f, windHeight - (50f * 1.125f * 4f) - (float)((i+2) * 25f));
			}
		}
		batch.end();

		playersColores.dispose();

		colourToBeDrawn.dispose();
	}

	
	void displayRapidVaccineNumbers()
	{
		if( !rapidVaccine )
			return;
		
		if( rvCubesToRemove == null )
			return;
		
		if( rvCubesToRemove.size() == 0 )
			return;

		rvFontBack.setColor( Color.WHITE );
		rvFontBack.getData().setScale( 2.3f );
		
		rvFontFront.setColor( Color.BLACK );
		rvFontFront.getData().setScale( 2.0f );
		for( String key : rvCubesToRemove.keySet() )
		{
			CityNode city = lookupCity( key );
			float x = city.getXInWindowCoords( windWidth ) + nodeSize / 2 -10f;
			float y = city.getYInWindowCoords( windHeight ) + nodeSize / 2 + 15f;
			
			batch.begin();
			rvFontBack.draw( batch, String.valueOf( rvCubesToRemove.get( key ) ), x, y );
			rvFontFront.draw( batch, String.valueOf( rvCubesToRemove.get( key ) ), x, y );
			batch.end();
		}
	}
	
	public static int getNumPlayers()
	{
		int num = 0;
		for( int i = 0; i < players.length; i++ )
			if( players[i] != null )
				num++;
		
		return num;
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

	public static int getNumCubesOfColourInAdjCities( String[] cities, String colour )
	{
		int numCubes = 0;
		
		HashSet<String> reachableCities = new HashSet<String>();
		for( String cityName : cities )
		{
			if( cityName != null )
			{
				reachableCities.add( cityName );
				for( CityNode adjCity : lookupCity( cityName ).connectedCities )
					reachableCities.add( adjCity.getName() );
			}
		}
		
		for( String city : reachableCities )
			numCubes += lookupCity( city ).getCubesByColor( colour ).size();
		
		return numCubes;
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

	public static int getRemCubesByName( String name )
	{
		int remCubes = 0;
		
		if( name.equalsIgnoreCase( "blue" ) )
			remCubes = remBlueCubes;
		else if( name.equalsIgnoreCase( "black" ) )
			remCubes = remBlackCubes;
		else if( name.equalsIgnoreCase( "yellow" ) )
			remCubes = remYellowCubes;
		else if( name.equalsIgnoreCase( "red" ) )
			remCubes = remRedCubes;
		
		return remCubes;
	}
	
	public static int getMaxCubesByColour( String name )
	{
		int maxCubes = 0;
		
		if( name.equalsIgnoreCase( "blue" ) )
			maxCubes = 24;
		else if( name.equalsIgnoreCase( "black" ) )
			maxCubes = 24;
		else if( name.equalsIgnoreCase( "yellow" ) )
			maxCubes = 24;
		else if( name.equalsIgnoreCase( "red" ) )
			maxCubes = 24;
		else if( name.equalsIgnoreCase( "purple" ) )
			maxCubes = 12;
		
		return maxCubes;	
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
				//dialogStage = null;
			}
		};

		dialogStage.clear();;
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

		if (DiseaseColor.equals("yellow") && Integer.parseInt(Number) >= remYellowCubes){
			remYellowCubes++;
		}
		else if (DiseaseColor.equals("yellow") && Integer.parseInt(Number) < remYellowCubes){
			remYellowCubes += Integer.parseInt(Number);
		}
		else if (DiseaseColor.equals("blue") && Integer.parseInt(Number) >= remBlueCubes){
			remBlueCubes += Integer.parseInt(Number);
		}
		else if (DiseaseColor.equals("blue") && Integer.parseInt(Number) < remBlueCubes){
			remBlueCubes += Integer.parseInt(Number);
		}
		else if (DiseaseColor.equals("red") && Integer.parseInt(Number) >= remRedCubes){
			remRedCubes += Integer.parseInt(Number);
		}
		else if (DiseaseColor == "red" && Integer.parseInt(Number) < remRedCubes){
			remRedCubes += Integer.parseInt(Number);
		}
		else if (DiseaseColor.equals("black") && Integer.parseInt(Number) >= remBlackCubes){
			remBlackCubes += Integer.parseInt(Number);;
		}
		else if (DiseaseColor.equals("black") && Integer.parseInt(Number) < remBlackCubes){
			remBlackCubes += Integer.parseInt(Number);
		}
		else{
			System.out.println("Invalid Color");
		}
	}

	public static void AskForConsent()
	{
		requestConsent("Allow share knowledge");
	}

	public static void AskForDiscard()
	{
		dialogStage.clear();;
		Skin tempSkin = new Skin( Gdx.files.internal( "skin/uiskin.json" ) );
		final SelectBox<String> selectBox=new SelectBox<String>(tempSkin);

		Dialog discardPrompt = new Dialog("Choose a card to Discard",skin){
			protected void result(Object object){
				String selected = selectBox.getSelected();
				Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
				//dialogStage = null;
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

	public static void AddCardToHand( String PlayerName, String CardName, String Boolean)
	{
		PlayerInfo player = lookupPlayer( PlayerName );

		if ( player != null )
		{
			player.addCardToHand( new PlayerCardInfo( CardName ) );
		}
		if(Boolean.equals("true"))
		{
			remCardsDeck--;
		}
	}

	public static void AddDiseaseCubeToCity( String CityName, String DiseaseColor)
	{
		CityNode city = lookupCity( CityName );
		DiseaseColour disease = lookupDisease( DiseaseColor );

		if ( city != null && disease != null )
			city.addCube( new DiseaseCubeInfo( disease ) );

		if (DiseaseColor.equals("yellow") && 1 >= remYellowCubes){
			remYellowCubes = 0;
		}
		else if (DiseaseColor.equals("yellow") && 1 < remYellowCubes){
			remYellowCubes -= 1;
		}
		else if (DiseaseColor.equals("blue") && 1 >= remBlueCubes){
			remBlueCubes = 0;
		}
		else if (DiseaseColor.equals("blue") && 1 < remBlueCubes){
			remBlueCubes -= 1;
		}
		else if (DiseaseColor.equals("red") && 1 >= remRedCubes){
			remRedCubes = 0;
		}
		else if (DiseaseColor == "red" && 1 < remRedCubes){
			remRedCubes -= 1;
		}
		else if (DiseaseColor.equals("black") && 1 >= remBlackCubes){
			remBlackCubes = 0;
		}
		else if (DiseaseColor.equals("black") && 1 < remBlackCubes){
			remBlackCubes -= 1;
		}
		else{
			System.out.println("Invalid Color");
		}
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
				//dialogStage = null;
			}
		};
		notifyEpidemic.setColor( Color.GREEN );

		notifyEpidemic.button("Okay");


		dialogStage.clear();;
		notifyEpidemic.show( dialogStage );
		Gdx.input.setInputProcessor(dialogStage);

		currentInfectionRateIdx++;
	}

	public static void IncActionsRememaining()
	{
		actionsRemaining++;
	}
	
	public static void ClearInfectionDiscard()
	{
		infectionDiscardPile.clear();
	}

	public static void NotifyTurn( String PlayerName )
	{
		PlayerInfo player = lookupPlayer( PlayerName );

		if ( player != null )
		{
			if ( currentPlayer != null )
			{

				actionsRemaining = 4;
				currentPlayer = player;
				if( clientPlayer == currentPlayer )
				{
					clientPlayer.roleActionUsed = false;
					Dialog notifyTurnDialog = new Dialog( "It's now your turn", skin ){
						protected void result(Object object)
						{
							Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
							//dialogStage = null;
						}
					};

					notifyTurnDialog.button("Okay");
					dialogStage.clear();;
					notifyTurnDialog.show( dialogStage );
					Gdx.input.setInputProcessor(dialogStage);
					turnEnded = false;
				}
			}
		}
		
		specialOrders = false;
		specialOrderPlayer = null;
	}
	
	public static void NotifyTurnTroubleshoorter( String PlayerName, final String[] InfectionCards )
	{
		PlayerInfo player = lookupPlayer( PlayerName );

		if ( player != null )
		{
			if ( currentPlayer != null )
			{

				actionsRemaining = 4;
				currentPlayer = player;
				if( clientPlayer == currentPlayer )
				{
					clientPlayer.roleActionUsed = false;
					Dialog notifyTurnDialog = new Dialog( "It's now your turn", skin ){
						protected void result(Object object)
						{
							Dialog showInfectDialog = new Dialog( "The next infect cards are", skin ){
								protected void result(Object object)
								{
									
									
									Gdx.input.setInputProcessor(buttonStage); //Start taking input from the ui
									//dialogStage = null;
								}
							};
							
							showInfectDialog.setColor( Color.GRAY );
							List<String> items = new List<String>(skin);
							items.setItems( InfectionCards );
							ScrollPane panel = new ScrollPane( items );
							showInfectDialog.getContentTable().add(panel);
							
							showInfectDialog.button("Okay");
							dialogStage.clear();;
							showInfectDialog.show( dialogStage );
							Gdx.input.setInputProcessor(dialogStage);
							turnEnded = false;
							//dialogStage = null;
						}
					};

					notifyTurnDialog.button("Okay");
					dialogStage.clear();;
					notifyTurnDialog.show( dialogStage );
					Gdx.input.setInputProcessor(dialogStage);
					turnEnded = false;
				}
			}
		}

		specialOrders = false;
		specialOrderPlayer = null;
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
		if( currentPlayer.role.equalsIgnoreCase( "Generalist ") )
			actionsRemaining = 5;
		else
			actionsRemaining = 4;
	}

	public static void RemoveResearchStation( String CityName )
	{
		remResearchStations++;
		for( int i = 0; i < researchStationCityNames.size(); i++ )
		{
			if( researchStationCityNames.get( i ).equals( CityName ) )
			{
				researchStationCityNames.remove( i );
				break;
			}
		}
		lookupCity( CityName ).removeResearchStation();
	}

	public static void AddResearchStation( String CityName )
	{
		remResearchStations--;
		researchStationCityNames.add(CityName);
		lookupCity( CityName ).putResearchStation();
	}
	
	public static void CureDisease( final String DiseaseColor )
	{
		diseaseStatuses.remove( DiseaseColor );
		
		diseaseStatuses.put( DiseaseColor, DiseaseStatus.CURED );
		
		for( PlayerCardInfo card : clientPlayer.hand )
		{
			if( card.getName().equalsIgnoreCase( "RapidVaccine") && getRemCubesByName( DiseaseColor) < getMaxCubesByColour( DiseaseColor ) )
			{
				Dialog rvPrompt = new Dialog("Do you want to play Rapid Vaccine?", skin ) {
					@Override
					protected void result(Object object) {
						if( (boolean)object )
						{
							rapidVaccine = true;
							rvColour = DiseaseColor;
							useCityButtonStage = true;
							rvCubesToRemove = new HashMap<String,Integer>();
						}
						
						
						Gdx.input.setInputProcessor( buttonStage );
					};
				};
				rvPrompt.button( "Okay", true );
				rvPrompt.button( "No", false );
				rvPrompt.show( dialogStage );
				Gdx.input.setInputProcessor( dialogStage );
				break;
			}
		}
	}
	
	public static void EradicateDisease( String DiseaseColor )
	{
		diseaseStatuses.remove( DiseaseColor );
		diseaseStatuses.put( DiseaseColor, DiseaseStatus.ERADICATED );
	}

	public static void EndGame( String GameWon )
	{
		boolean gameWon = Boolean.valueOf( GameWon );
		String message;
		if( gameWon )
		{
			message = "You Won!";
		}
		else
		{
			message = "You Lost!";
		}
		
		Dialog gameEnd = new Dialog( message , skin ){
    		@Override
    		protected void result(Object object) {
    			System.exit( 0 );
    		}
    	};

		//dialogStag/e = new Stage( parent.screen );//
		dialogStage.clear();
		
		gameEnd.button( "Okay" );
		
        Gdx.input.setInputProcessor(dialogStage); //Start taking input from the ui
        gameEnd.show( dialogStage );
	}

	public static void Forecast( String[] InfectionCards )
	{
		possibleForecastSelections = new ArrayList<String>();
		forecastSelections = new ArrayList<String>();
		for( final String infectionCard : InfectionCards )
			possibleForecastSelections.add( infectionCard );
		
		float cardSizeX = playerCardXSize*1.5f;
		float cardSizeY = playerCardYSize*1.5f;
		float cardOffset = playerCardXOffset*1.5f;
		float cardPosX = windWidth / 2 - possibleForecastSelections.size()*( cardSizeX + cardOffset ) / 2; 
		float cardPosY = windHeight / 2 - cardSizeY / 2;
		for( final String card : possibleForecastSelections )
		{
			Texture cardTexture	 							= cityCardTextures[ lookupCityIndex( card ) ];
			TextureRegion TR_cardTexture 					= new TextureRegion( cardTexture );
			final TextureRegionDrawable Draw_cardTexture 	= new TextureRegionDrawable( TR_cardTexture );
			
			Texture selectedCardTexture	 							= selectedCityCardTextures[ lookupCityIndex( card ) ];
			TextureRegion TR_selectedCardTexture 					= new TextureRegion( selectedCardTexture );
			final TextureRegionDrawable Draw_selectedCardTexture 	= new TextureRegionDrawable( TR_selectedCardTexture );
			
			ButtonStyle cityButtonStyle = new ButtonStyle();
			cityButtonStyle.up			= Draw_cardTexture;
			cityButtonStyle.down		= Draw_cardTexture;
			cityButtonStyle.checked		= Draw_selectedCardTexture;
			
	        button = new Button( cityButtonStyle );
	        button.setBounds( cardPosX, cardPosY, cardSizeX, cardSizeY );
	        
	        cardSelectStage.addActor( button );
	        
	        button.addListener( new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if( !waitForButton )
					{	
						waitForButton = true;
						if( !((Button)actor).isChecked() )
						{
							for( int i = 0; i < selections.size(); i++ )
							{
								if( selections.get(i).getName().equals( card ) )
								{
									selections.remove( i );
									break;
								}
							}
							cardsToSelect++;
						}
						else
						{
							forecastSelections.add(card);
							cardsToSelect--;
						}
						waitForButton = false;
					}
				}
			});
	        cardPosX += cardSizeX + cardOffset;
		}
		showCardSelectStage = true;
		Gdx.input.setInputProcessor( cardSelectStage );
		cardsToSelect = InfectionCards.length;
		showCardSelectAction = "Forecast";
	}

	public static void ReexaminedResearch()
	{
		String[] cards = new String[ playerDiscardPile.size() ];
		for( int i = 0; i < cards.length; i++ )
			cards[i] = playerDiscardPile.get( i );
		
		final Skin tempSkin = new Skin( Gdx.files.internal( "skin/uiskin.json") );
		final SelectBox<String> selector = new SelectBox<String>( tempSkin );
		selector.setItems( cards );
		
		Dialog rrDiag = new Dialog("Select card to draw from discard", skin ){
			@Override
			protected void result(Object object) {
				ClientComm.send( "ReexaminedResearchResponse/" + selector.getSelected() );
				Gdx.input.setInputProcessor( buttonStage );
				tempSkin.dispose();
			}
		};
		rrDiag.getContentTable().add( selector );
		rrDiag.button( "Select" );
		rrDiag.show( dialogStage );
		Gdx.input.setInputProcessor( dialogStage );
	}

	public static void SpecialOrders( String PlayerName )
	{
		if( PlayerName != null )
		{
			dialogStage.clear();
			specialOrderPlayer = PlayerName;
			specialOrders = true;
			
			Dialog soDiag = new Dialog("You now have control of " + PlayerName, skin ){
				@Override
				protected void result(Object object) {
					Gdx.input.setInputProcessor( buttonStage );
				}
			};
			
			soDiag.button("Okay");
			soDiag.show( dialogStage );
			Gdx.input.setInputProcessor( dialogStage );
		}
		else
		{
			dialogStage.clear();
			specialOrderPlayer = null;
			specialOrders = false;
			
			Dialog soDiag = new Dialog("Consent not given", skin ){
				@Override
				protected void result(Object object) {
					Gdx.input.setInputProcessor( buttonStage );
				}
			};
			
			soDiag.button("Okay");
			soDiag.show( dialogStage );
			Gdx.input.setInputProcessor( dialogStage );
		}
	}
}






















