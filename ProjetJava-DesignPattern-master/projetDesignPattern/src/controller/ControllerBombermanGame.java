package controller;

import model.BombermanGame;
import view.ViewBombermanGame;
import view.ViewCommand;

public class ControllerBombermanGame extends AbstractController{
	private ViewBombermanGame viewBombermanGame;
	private ViewCommand viewCommand;
	
	public ControllerBombermanGame(int maxTour) {
		super.setGame(new BombermanGame(maxTour, "layouts/arene.lay"));
		
		this.viewCommand = new ViewCommand(this);
		this.viewBombermanGame = new ViewBombermanGame(this);

		super.getGame().addObserver(this.viewBombermanGame);
		super.getGame().addObserver(this.viewCommand);
		this.showGame();

	}
	
	public void changeMapOfGaming(String fileName) {
		// ON FERME LES ANCIENNES VUES
		this.closeGame();
		
		// ON CRÉE UN NOUVEAU JEU ET SES NOUVELLES VUES
		this.setGame(new BombermanGame(super.getGame().getMaxturn(), fileName));
		super.setGame(this.getGame());
		this.viewBombermanGame = new ViewBombermanGame(this);
		this.viewCommand = new ViewCommand(this);
		
		// ON AFFICHE LES NOUVELLES VUES
		this.showGame();
		
		super.getGame().addObserver(this.viewBombermanGame);
		super.getGame().addObserver(this.viewCommand);
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
	
	public BombermanGame getGame() {
		return (BombermanGame) super.getGame();
	}
	
	public void setGame(BombermanGame game) {
		super.setGame(game);
	}
}
