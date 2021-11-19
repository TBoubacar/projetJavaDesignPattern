package view;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import javax.swing.JFrame;

import Controller.AbstractController;
import utils.Observer;

public class ViewBombermanGame implements Observer {

	JFrame jFrame;
	AbstractController controller;
	
	public ViewBombermanGame(AbstractController controllerGame) {
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

	public AbstractController getController() {
		return controller;
	}

	public void setController(AbstractController controller) {
		this.controller = controller;
	}

	@Override
	public void update(int nombreTour) {
		// TODO Auto-generated method stub
		
	}
}
