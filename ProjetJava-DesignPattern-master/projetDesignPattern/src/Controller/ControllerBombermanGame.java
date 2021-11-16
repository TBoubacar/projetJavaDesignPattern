package Controller;

import utils.Game;
import view.PanelBomberman;
import view.ViewBombermanGame;
import view.ViewCommand;

public class ControllerBombermanGame extends AbstractController{

	private ViewBombermanGame viewBombermanGame;
	private ViewCommand viewCommand;
	private PanelBomberman panelBomberman;
	
	public ControllerBombermanGame(Game game) {
		super.setGame(game);
		this.viewBombermanGame = new ViewBombermanGame(this);
		this.viewCommand = new ViewCommand(this);
		
		super.getGame().addObserver(viewBombermanGame);
		super.getGame().addObserver(viewCommand);
		
		this.panelBomberman = new PanelBomberman(this.getGame().getInputMap().getSizeX(), this.getGame().getInputMap().getSizeY(), this.getGame().getInputMap().get_walls(), this.getGame().getInputMap().getStart_breakable_walls(), this.getGame().getInputMap().getStart_agents());
		this.viewBombermanGame.addPanel(panelBomberman);

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

	public PanelBomberman getPanelBomberman() {
		return panelBomberman;
	}

	public void setPanelBomberman(PanelBomberman panelBomberman) {
		this.panelBomberman = panelBomberman;
	}

	public ViewCommand getViewCommand() {
		return viewCommand;
	}

	public void setViewCommand(ViewCommand viewCommand) {
		this.viewCommand = viewCommand;
	}
}
