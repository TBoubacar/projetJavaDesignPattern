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
	
	public void changeMapOfGaming(String fileName) {
		// ASTUCE : METHODE A REDEFINIR POUR LA GESTION DU CHARGEMENT D'UN NOUVEAU TERRAIN DE JEU (PARTIE 5.2)
		System.out.println("Ouverture du terrain de jeu : " + fileName);
	}

	public void showGame() {
		// ASTUCE : ON NE FAIT RIEN
	}
	
	public void closeGame() {
		// ASTUCE : ON NE FAIT RIEN
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
}
