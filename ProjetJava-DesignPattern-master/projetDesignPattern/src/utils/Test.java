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
		
		BombermanGame bombermanGame = new BombermanGame(500, "layouts/niveau2.lay");
		ControllerBombermanGame controllerBombermanGame = new ControllerBombermanGame(bombermanGame);
		controllerBombermanGame.showGame();
	}

}
