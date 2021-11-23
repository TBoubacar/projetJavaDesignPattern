package controller;

import model.BombermanGame;
import view.ViewBombermanGame;
import view.ViewCommand;

public class ControllerBombermanGame extends AbstractController{
	private BombermanGame bombermanGame;
	private ViewBombermanGame viewBombermanGame;
	private ViewCommand viewCommand;
	
	public ControllerBombermanGame(BombermanGame game) {
		super.setGame(game);
		this.setBombermanGame(game);
		this.viewBombermanGame = new ViewBombermanGame(this);
		this.viewCommand = new ViewCommand(this);
		
		super.getGame().addObserver(viewBombermanGame);
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
