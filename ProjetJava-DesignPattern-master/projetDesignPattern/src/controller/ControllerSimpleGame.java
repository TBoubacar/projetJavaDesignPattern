package controller;

import model.SimpleGame;
import view.ViewCommand;
import view.ViewSimpleGame;

public class ControllerSimpleGame extends AbstractController{
	private ViewSimpleGame viewSimpleGame;
	private ViewCommand viewCommand;
	
	public ControllerSimpleGame(int tourMax) {
		super.setGame(new SimpleGame(tourMax));
		this.viewSimpleGame = new ViewSimpleGame(this);
		this.viewCommand = new ViewCommand(this);

		super.getGame().addObserver(this.viewSimpleGame);
		super.getGame().addObserver(this.viewCommand);
		this.showGame();
	}

	public void showGame() {
		this.viewSimpleGame.show();
		this.viewCommand.show();
	}
	
	public void closeGame() {
		this.viewSimpleGame.close();
		this.viewCommand.close();
	}
	
	public ViewSimpleGame getViewSimpleGame() {
		return viewSimpleGame;
	}

	public void setViewSimpleGame(ViewSimpleGame viewSimpleGame) {
		this.viewSimpleGame = viewSimpleGame;
	}

	public ViewCommand getViewCommand() {
		return viewCommand;
	}

	public void setViewCommand(ViewCommand viewCommand) {
		this.viewCommand = viewCommand;
	}
	
	public SimpleGame getGame() {
		return (SimpleGame) super.getGame();
	}
	
	public void setGame(SimpleGame game) {
		super.setGame(game);
	}
}
