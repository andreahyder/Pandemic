package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameScreen implements Screen {
	final static float nodeSize = 25.0f;
	
	static float[][] positions = new float[][]
			{
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f },
				new float[]{ 0.0f, 0.0f }
			};
	static String[][] names = new String[][]
	{
		new String[] {"Atlanta","blue","Chicago","Washington","Miami"},	
		new String[] {"Chicago","blue","San Francisco","Los Angeles","Atlanta","Toronto"},
		new String[] {"Essen","blue","London","St. Petersburg","Milan","Paris"},
		new String[] {"London","blue","New York","Essen","Madrid","Paris"},
		new String[] {"Madrid","blue","New York","Sao Paulo","Algiers","London","Paris"},
		new String[] {"Milan","blue","Essen","St. Petersburg","Istanbul","Paris"},
		new String[] {"New York","blue","Toronto","Washington","Madrid","London"},
		new String[] {"Paris","blue","London","Essen","Milan","Algiers","Madrid"},
		new String[] {"San Francisco","blue","Chicago","Los Angeles"},
		new String[] {"St. Petersburg","blue","Essen","Moscow","Istanbul","Milan"},
		new String[] {"Toronto","blue","Chicago","New York","Washington"},
		new String[] {"Washington","blue","New York","Toronto","Atlanta","Miami"},
		new String[] {"Bogota","yellow","Miami","Mexico City","Lima","Buenos Aires","Sao Paulo"},
		new String[] {"Buenos Aires","yellow","Sao Paulo","Bogota"},
		new String[] {"Johannesburg","yellow","Kinshasa","Khartoum"},
		new String[] {"Khartoum","yellow","Johannesburg","Kinshasa","Lagos","Cairo"},
		new String[] {"Kinshasa","yellow","Lagos","Khartoum","Johannesburg"},
		new String[] {"Lagos","yellow","Sao Paulo","Kinshasa","Khartoum"},
		new String[] {"Lima","yellow","Mexico City","Bogota","Santiago"},
		new String[] {"Los Angeles","yellow","San Francisco","Chicago","Mexico City"},
		new String[] {"Mexico City","yellow","Los Angeles","Chicago","Miami","Bogota","Lima"},
		new String[] {"Miami","yellow","Bogota","Mexico City","Atlanta","Washington"},
		new String[] {"Santiago","yellow","Lima"},
		new String[] {"Sao Paulo","yellow","Buenos Aires","Bogota","Madrid","Lagos"},
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
	
	CityNode[] cityNodes;
	PandemicGame parent;
	
	float windWidth, windHeight;
	Texture background;
	SpriteBatch batch;
	Stage stage;
	Skin skin;
	
	GameScreen( PandemicGame _parent )
	{
		parent 	= _parent;
		batch 	= new SpriteBatch();
		stage 	= new Stage();
		skin 	= new Skin( Gdx.files.internal( "plain-james-ui/plain-james-ui.json" ) );
		
		windWidth 	= Gdx.graphics.getWidth();
		windHeight 	= Gdx.graphics.getHeight();
		
		cityNodes = new CityNode[ names.length ];
		for ( int i = 0; i < names.length; i++ )
		{
			cityNodes[ i ] = new CityNode( names[i], positions[i] ); 
		}
		
		background 	= new Texture(Gdx.files.internal( "board.png" ) );
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		batch.begin();
			batch.draw(background, 0, 0, windWidth, windHeight);
		batch.end();
		
		batch.begin();
			for( CityNode curr : cityNodes )
			{
				float x = curr.getXInWindowCoords( windWidth );
				float y = curr.getXInWindowCoords( windHeight );
				
				Texture cityCircle = new Texture( Gdx.files.internal( curr.getColour() + "City.png" ) );
				batch.draw( cityCircle, x, y, nodeSize, nodeSize );
			}
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
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

	public CityNode lookupCity( String name )
	{
		for( CityNode curr : cityNodes )
		{
			if ( curr.getName().equals( name ) ) return curr;
		}
		return null;
	}
}
