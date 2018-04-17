package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;
import com.mygdx.game.PandemicGame;

public class SaveGame implements Action{

	@Override
	public void execute(String[] args) {
		PandemicGame.gameScreen.SaveGame( );
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "SaveGame";
	}
	

}
