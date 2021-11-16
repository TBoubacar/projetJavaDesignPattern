package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.AbstractController;
import utils.Observer;

public class ViewSimpleGame implements Observer {

	JFrame jFrame;
	JLabel jLabel;
	AbstractController controller;
	
	public ViewSimpleGame(AbstractController controllerGame) {
		this.controller = controllerGame;
		this.controller.getGame().addObserver(this);

		/*---		JFRAME		---*/
		jFrame = new JFrame();
		jFrame.setTitle("Game");
		jFrame.setSize(new Dimension(700, 700));
		Dimension windowSize = jFrame.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - windowSize.width / 2 - 350;
		int dy = centerPoint.y - windowSize.height / 2 - 100;

		/*---		AFFICHAGE NOMBRE TOUR		---*/
		String msg = "Turn : " + this.controller.getGame().getMaxturn();
		jLabel = new JLabel(msg, JLabel.CENTER);
		jLabel.setFont(new Font("Serif", Font.BOLD, 14));
		jFrame.add(jLabel);
		
		jFrame.setLocation(dx,dy);
	}
	
	public void addPanel(JPanel panel) {
		this.jFrame.add(panel);
	}
	
	public void show() {
		jFrame.setVisible(true);
	}
	
	public void close() {
		jFrame.setVisible(false);
	}

	public AbstractController getController() {
		return controller;
	}

	public void setController(AbstractController controller) {
		this.controller = controller;
	}

	/*---	METHODS OBSERVER		---*/
	@Override
	public void update(int nombreTour) {
		this.jLabel.setText("Turn : " + nombreTour);		
	}
}
