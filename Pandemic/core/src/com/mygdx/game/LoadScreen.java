package com.mygdx.game;

import com.badlogic.gdx.Screen;

public class LoadScreen implements Screen {
	
	private PandemicGame parent;
	
	LoadScreen( PandemicGame _parent )
	{
		parent = _parent;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) { 	
		parent.changeScreen( PandemicGame.Screens.MENU );

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
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
