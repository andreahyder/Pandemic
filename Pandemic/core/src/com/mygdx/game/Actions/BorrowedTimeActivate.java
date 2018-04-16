package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class BorrowedTimeActivate implements Action{

	@Override
	public void execute(String[] args) {
		GameScreen.BorrowedTimeActivated();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "BorrowedTimeActivate";
	}
	

}
