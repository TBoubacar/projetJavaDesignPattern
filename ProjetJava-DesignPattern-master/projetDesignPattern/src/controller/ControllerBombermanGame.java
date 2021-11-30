package controller;

import model.BombermanGame;
import view.PanelBomberman;
import view.ViewBombermanGame;
import view.ViewCommand;

public class ControllerBombermanGame extends AbstractController{
	private BombermanGame bombermanGame;
	private ViewBombermanGame viewBombermanGame;
	private ViewCommand viewCommand;
	
	public ControllerBombermanGame(int maxTour) {
//		this.bombermanGame = new BombermanGame(maxTour, "layouts/arene.lay");
//		this.bombermanGame = new BombermanGame(maxTour, "layouts/alone.lay");
//		this.bombermanGame = new BombermanGame(maxTour, "layouts/exemple.lay");
//		this.bombermanGame = new BombermanGame(maxTour, "layouts/jeu1.lay");
//		this.bombermanGame = new BombermanGame(maxTour, "layouts/jeu_symetrique.lay");
//		this.bombermanGame = new BombermanGame(maxTour, "layouts/niveau1.lay");
		this.bombermanGame = new BombermanGame(maxTour, "layouts/niveau2.lay");
//		this.bombermanGame = new BombermanGame(maxTour, "layouts/niveau3.lay");
		
		super.setGame(this.bombermanGame);
		
		this.viewCommand = new ViewCommand(this);
		this.viewBombermanGame = new ViewBombermanGame(this);
		
		super.getGame().addObserver(viewBombermanGame);
		super.getGame().addObserver(viewCommand);
	}
	
	public void changeMapOfGaming(String fileName) {
		// ON FERME LES ANCIENNES VUES
		this.closeGame();
		
		// ON CRÉE UN NOUVEAU JEU ET SES NOUVELLES VUES
		this.bombermanGame = new BombermanGame(super.getGame().getMaxturn(), fileName);
		super.setGame(this.bombermanGame);
		this.viewBombermanGame = new ViewBombermanGame(this);
		this.viewCommand = new ViewCommand(this);
		
		// ON AFFICHE LES NOUVELLES VUES
		this.showGame();
		
		super.getGame().addObserver(this.viewBombermanGame);
		super.getGame().addObserver(viewCommand);
	}

	public void showGame() {
		this.viewBombermanGame.show();
		this.viewCommand.show();
	}
	
	public void closeGame() {
		this.viewBombermanGame.close();
		this.viewCommand.close();
	}
	
	public ViewBombermanGame getViewBombermanGame() {
		return viewBombermanGame;
	}

	public void setViewBombermanGame(ViewBombermanGame viewBombermanGame) {
		this.viewBombermanGame = viewBombermanGame;
	}

	public ViewCommand getViewCommand() {
		return viewCommand;
	}

	public void setViewCommand(ViewCommand viewCommand) {
		this.viewCommand = viewCommand;
	}

	public BombermanGame getBombermanGame() {
		return bombermanGame;
	}

	public void setBombermanGame(BombermanGame bombermanGame) {
		this.bombermanGame = bombermanGame;
	}
}
