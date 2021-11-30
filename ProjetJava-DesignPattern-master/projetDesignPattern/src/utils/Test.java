package utils;

import controller.ControllerBombermanGame;
import model.BombermanGame;

public class Test {

	public static void main(String[] args) {
		/*
		SimpleGame simpleGame = new SimpleGame(5);
		ControllerSimpleGame controllerSimpleGame = new ControllerSimpleGame(simpleGame);
		controllerSimpleGame.showGame();
		*/
		
		ControllerBombermanGame controllerBombermanGame = new ControllerBombermanGame(200);
		controllerBombermanGame.showGame();
	}

}
