package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class VirulentStrainEpidemic implements Action{

	@Override
	public void execute(String[] args) {
		GameScreen.VirulentStrainChosen( args[1] );			
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "VirulentStrainChosen";
	}

}
