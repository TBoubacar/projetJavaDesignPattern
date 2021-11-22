package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import Controller.AbstractController;
import utils.Observer;

public class ViewCommand implements Observer {	
	JFrame jFrame;
	JSlider jSlider;
	JButton jButtonRestart;		//MON BOUTON REDEMARRER
	JButton jButtonStart;		//MON BOUTON RUN (JOUER AUTOMATIQUEMENT)
	JButton jButtonPlay;		//MON BOUTON STEP
	JButton jButtonPause;		//MON BOUTON PAUSE
	JLabel jLabelSlider;
	JLabel jLabel;
	AbstractController controller;
	
	public ViewCommand(AbstractController controllerGame) {
		this.controller = controllerGame;
		this.controller.getGame().addObserver(this);

		/*---		JFRAME		---*/
		jFrame = new JFrame();
		jFrame.setTitle("Commande");
		jFrame.setSize(new Dimension(700, 400));
		Dimension windowSize = jFrame.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - windowSize.width / 2 + 350 ;
		int dy = centerPoint.y - windowSize.height / 2 - 450;
		jFrame.setLocation(dx,dy);
		

		/*---		ICONS & BUTTONS		---*/
		Icon restartIcon= new ImageIcon("icons/icon_restart.png");
		Icon startIcon= new ImageIcon("icons/icon_play.png");
		Icon playIcon= new ImageIcon("icons/icon_step.png");
		Icon pauseIcon= new ImageIcon("icons/icon_pause.png");
		
		jButtonRestart = new JButton(restartIcon);
		jButtonStart= new JButton(startIcon);
		jButtonPlay = new JButton(playIcon);
		jButtonPause = new JButton(pauseIcon);
				//DE BASE TOUS LES BOUTONS SONT DESACTIVÉS SAUF LE BOUTON QUI LANCE LA PERMET DE LANCER LA PARTIE
		jButtonRestart.setEnabled(false);
		jButtonStart.setEnabled(true);
		jButtonPlay.setEnabled(true);
		jButtonPause.setEnabled(false);
		
		jButtonRestart.addActionListener(new ActionListener() {	//BUTTON REDEMARRER
			public void actionPerformed(ActionEvent evenement) {
				System.out.println("Rédemarrage du jeu...");
				jButtonRestart.setEnabled(false);
				jButtonPause.setEnabled(false);
				jButtonStart.setEnabled(true);
				jButtonPlay.setEnabled(true);
				controller.restart();
			}
		});
		
		jButtonStart.addActionListener(new ActionListener() {	//BUTTON RUN (JOUER AUTOMATIQUEMENT)
			public void actionPerformed(ActionEvent evenement) {
				jButtonRestart.setEnabled(true);
				jButtonPause.setEnabled(true);
				jButtonStart.setEnabled(false);
				jButtonPlay.setEnabled(false);
				controller.play();
			}
		});
		
		jButtonPlay.addActionListener(new ActionListener() {	//BUTTON STEP (UNE SEULE OCCURENCE DE PAS) 
			public void actionPerformed(ActionEvent evenement) {
				jButtonRestart.setEnabled(true);
				jButtonPause.setEnabled(false);
				jButtonStart.setEnabled(true);
				jButtonPlay.setEnabled(true);
				controller.step();
			}
		});

		jButtonPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				jButtonRestart.setEnabled(true);
				jButtonPause.setEnabled(false);
				jButtonStart.setEnabled(true);
				jButtonPlay.setEnabled(true);
				controller.pause();
			}
		});
		
		/*---		JSLIDER & JLABEL SLIDER		---*/
		jSlider = new JSlider();
		jSlider.setValue(1);
		jSlider.setMajorTickSpacing(1);
		jSlider.setMaximum(10);
		jSlider.setPaintTicks(true);
		jSlider.setPaintLabels(true);
		
		jLabelSlider = new JLabel("Number of turns par second", JLabel.CENTER);
		jLabelSlider.setFont(new Font("Serif", Font.BOLD, 14));
		
		/*---		AFFICHAGE NOMBRE TOUR		---*/
		String msg = "Turn : " + controllerGame.getGame().getMaxturn();
		jLabel = new JLabel(msg, JLabel.CENTER);
		jLabel.setFont(new Font("Serif", Font.BOLD, 14));
		

		/*---		JPANEL		---*/
		JPanel jPanelBouton = new JPanel(new GridLayout(1, 4));
		jPanelBouton.add(jButtonRestart);
		jPanelBouton.add(jButtonStart);
		jPanelBouton.add(jButtonPlay);
		jPanelBouton.add(jButtonPause);
		
		JPanel jPanel2 = new JPanel(new GridLayout(2,1));
		jPanel2.add(jLabelSlider);
		jPanel2.add(jSlider);
		
		JPanel jPanel3 = new JPanel(new GridLayout(1,2));
		jPanel3.add(jLabel);
		
		JPanel jPanelMain1 = new JPanel(new GridLayout(2, 1));
		JPanel jPanelMain2 = new JPanel(new GridLayout(1, 2));
		
		jPanelMain1.add(jPanelBouton);
		jPanelMain2.add(jPanel2);
		jPanelMain2.add(jPanel3);
		jPanelMain1.add(jPanelMain2);

		jFrame.add(jPanelMain1);
	}

	public void changePanel(JPanel panel) {
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

	/*---		METHODS OBSERVER		---*/
	@Override
	public void update(int nombreTour) {
		this.jLabel.setText("Turn : " + nombreTour);

		// GESTION DE LA VITESSE DU JEU
		if(this.jSlider.getValue() > 0) {
			this.controller.setSpeed((double) (1000/this.jSlider.getValue()));
		}
		
		// GESTION DE LA FIN DU JEU
		if(this.controller.getGame().getTurn() == this.controller.getGame().getMaxturn()) {
			jButtonStart.setEnabled(false);
			jButtonPlay.setEnabled(false);
			jButtonPause.setEnabled(false);
		}
	}
}
