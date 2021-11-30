package controller;

import model.Game;
import model.InputMap;

public abstract class AbstractController {
	private Game game;
	
	public void restart() {
		getGame().init();
	}
	
	public void step() {
		getGame().step();
	}
	
	public void play() {
		getGame().launch();
	}
	
	public void pause() {
		getGame().pause();
	}
	
	public void setSpeed(double speed) {
		getGame().setTime((long) speed);
	}
	
	public void setInputMap(InputMap newMap) {
		getGame().setInputMap(newMap);
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
}
