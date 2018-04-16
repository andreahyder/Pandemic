package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class AddCubeToStash implements Action{

	@Override
	public void execute(String[] args) {
		GameScreen.AddCubeToStash( args[1] );
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "AddCubeToStash";
	}
	

}
