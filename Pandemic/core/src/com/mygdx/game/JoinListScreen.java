package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class JoinListScreen implements Screen {
	PandemicGame parent;
	float gameWidth, gameHeight;
	ScrollPane scrollPane;
	SpriteBatch batcher;
	TextureAtlas atlas;
	List<String> list;
	Stage stage;
	Skin skin;
	
	JoinListScreen( PandemicGame _parent )
	{
		parent = _parent;
		
        gameWidth = Gdx.graphics.getWidth();
        gameHeight = Gdx.graphics.getHeight();
        
        atlas = new TextureAtlas(Gdx.files.internal("plain-james-ui/plain-james-ui.atlas"));
        skin = new Skin(Gdx.files.internal("plain-james-ui/plain-james-ui.json"), atlas);
        

        batcher = new SpriteBatch();
		list = new List<String>(skin);
	    String[] strings = new String[40];
	    for ( int i = 0, k = 0; i < 40; i++ ) 
	    {
	    	strings[k++] = "String: " + i;
	    }
	    list.setItems( strings );
		scrollPane = new ScrollPane( list );
        scrollPane.setBounds(0, 0, gameWidth*1.95f, gameHeight*1.5f + gameHeight*0.2f);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setPosition(	gameWidth / 2 	- scrollPane.getWidth() / 4,
        						gameHeight / 2 - 1.4f*gameHeight / 4);
        scrollPane.setTransform(true);
        scrollPane.setScale(0.5f);
        stage = new Stage(parent.screen);
        stage.addActor(scrollPane);
        Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void show() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
 
        stage.act(delta);
        stage.draw();

	}

	@Override
	public void resize(int width, int height) {

        gameWidth = Gdx.graphics.getWidth();
        gameHeight = Gdx.graphics.getHeight();
        scrollPane.setBounds(0, 0, gameWidth*1.95f, gameHeight*1.5f + gameHeight*0.2f);
        scrollPane.setPosition(	gameWidth / 2 	- scrollPane.getWidth() / 4,
        						gameHeight / 2 - 1.4f*gameHeight / 4);
        scrollPane.setScale(0.5f);
        stage.addActor(scrollPane);
		stage.getViewport().update(width, height, true);
        Gdx.input.setInputProcessor(stage);
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
        skin.dispose();
        atlas.dispose();
	}

}
