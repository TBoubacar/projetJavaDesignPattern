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
		
//		BombermanGame bombermanGame = new BombermanGame(500, "layouts/alone.lay");
		BombermanGame bombermanGame = new BombermanGame(500, "layouts/niveau2.lay");
//		BombermanGame bombermanGame = new BombermanGame(500, "layouts/niveau3.lay");
//		BombermanGame bombermanGame = new BombermanGame(500, "layouts/arene.lay");
//		BombermanGame bombermanGame = new BombermanGame(500, "layouts/jeu1.lay");
		ControllerBombermanGame controllerBombermanGame = new ControllerBombermanGame(bombermanGame);
		controllerBombermanGame.showGame();
	}

}
