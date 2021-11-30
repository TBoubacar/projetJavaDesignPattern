package view;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import javax.swing.JFrame;

import controller.ControllerBombermanGame;
import model.Observer;

public class ViewBombermanGame implements Observer {

	JFrame jFrame;
	private PanelBomberman panelBomberman;
	ControllerBombermanGame controller;
	
	public ViewBombermanGame(ControllerBombermanGame controllerGame) {
		this.controller = controllerGame;

		/*---		JFRAME		---*/
		jFrame = new JFrame();
		jFrame.setTitle("Game");
		jFrame.setSize(new Dimension(950, 700));
		Dimension windowSize = jFrame.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - windowSize.width / 2 - 500;
		int dy = centerPoint.y - windowSize.height / 2 - 100;
		jFrame.setLocation(dx,dy);

		this.panelBomberman = new PanelBomberman(this.getController().getGame().getInputMap().getSizeX(), this.getController().getGame().getInputMap().getSizeY(), this.getController().getGame().getInputMap().get_walls(), this.controller.getBombermanGame().getInfoMurs(), this.controller.getBombermanGame().getInfoAgents());
		this.addPanel(panelBomberman);
	}

	@Override
	public void update(int nombreTour) {
		this.panelBomberman.updateInfoGame(this.controller.getBombermanGame().getInfoMurs(), this.controller.getBombermanGame().getInfoAgents(), this.controller.getBombermanGame().getInfoItems(), this.controller.getBombermanGame().getBombes());
		this.panelBomberman.repaint();
	}
	
	public void addPanel(PanelBomberman panelBomberman) {
		this.jFrame.add(panelBomberman);
	}

	public void show() {
		jFrame.setVisible(true);
	}
	
	public void close() {
		jFrame.setVisible(false);
	}
	
	public JFrame getjFrame() {
		return jFrame;
	}

	public void setjFrame(JFrame jFrame) {
		this.jFrame = jFrame;
	}

	public ControllerBombermanGame getController() {
		return controller;
	}

	public void setController(ControllerBombermanGame controller) {
		this.controller = controller;
	}

	public PanelBomberman getPanelBomberman() {
		return panelBomberman;
	}

	public void setPanelBomberman(PanelBomberman panelBomberman) {
		this.panelBomberman = panelBomberman;
	}
}
