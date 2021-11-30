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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import controller.AbstractController;
import etat.EtatBase;
import etat.EtatButtonCommande;
import etat.EtatFin;
import model.InputMap;
import model.Observer;

public class ViewCommand implements Observer {	
	JFrame jFrame;
	JSlider jSlider;
	JButton jButtonRestart;				//MON BOUTON REDEMARRER
	JButton jButtonStart;				//MON BOUTON RUN (JOUER AUTOMATIQUEMENT)
	JButton jButtonPlay;				//MON BOUTON STEP
	JButton jButtonPause;				//MON BOUTON PAUSE
	JLabel jLabelSlider;
	JLabel jLabel;
	private AbstractController controller;
	private EtatButtonCommande etat;
	
	// PARTIE BONUS
	JButton jButtonChooseInterface;		//MON BOUTON POUR CHOISIR UN PLATEAU DU JEU

	public ViewCommand(AbstractController controllerGame) {
		this.controller = controllerGame;

		this.createFrame();
		
		this.createButton();
		
		this.createSlider();
		
		this.ajoutePanelOnJFrame();
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
		if(this.controller.getGame().getTurn() == this.controller.getGame().getMaxturn() || !this.controller.getGame().gameContinue()) {
			this.setEtat(new EtatFin(this));
		}
	}
	
	/*---		METHODS CONCRETE		---*/
	public void createFrame() {
		/*---		JFRAME		---*/
		jFrame = new JFrame();
		jFrame.setTitle("Commande");
		jFrame.setSize(new Dimension(600, 300));
		Dimension windowSize = jFrame.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - windowSize.width / 2 + 350 ;
		int dy = centerPoint.y - windowSize.height / 2 - 300;
		jFrame.setLocation(dx,dy);
	}
	
	public void createButton() {
		/*---		ICONS & BUTTONS		---*/
		Icon restartIcon= new ImageIcon("icons/icon_restart.png");
		Icon startIcon= new ImageIcon("icons/icon_play.png");
		Icon playIcon= new ImageIcon("icons/icon_step.png");
		Icon pauseIcon= new ImageIcon("icons/icon_pause.png");
		
		jButtonRestart = new JButton(restartIcon);
		jButtonStart= new JButton(startIcon);
		jButtonPlay = new JButton(playIcon);
		jButtonPause = new JButton(pauseIcon);
		// PARTIE BONUS
		jButtonChooseInterface = new JButton("Choose Interface of gaming");

		//DE BASE TOUS LES BOUTONS SONT DESACTIVÉS SAUF LE BOUTON QUI LANCE LA PERMET DE LANCER LA PARTIE
		this.etat = new EtatBase(this);
		
		jButtonRestart.addActionListener(new ActionListener() {	//BUTTON REDEMARRER
			public void actionPerformed(ActionEvent evenement) {
				etat.restart();
			}
		});
		
		jButtonStart.addActionListener(new ActionListener() {	//BUTTON RUN (JOUER AUTOMATIQUEMENT)
			public void actionPerformed(ActionEvent evenement) {
				etat.start();
			}
		});
		
		jButtonPlay.addActionListener(new ActionListener() {	//BUTTON STEP (UNE SEULE OCCURENCE DE PAS) 
			public void actionPerformed(ActionEvent evenement) {
				etat.play();
			}
		});

		jButtonPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				etat.pause();
			}
		});

		jButtonChooseInterface.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evenement) {
				JFileChooser jFileChooser = new JFileChooser("layouts/");
				jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if(jFileChooser.showOpenDialog(jFrame) == JFileChooser.APPROVE_OPTION) {
					try {
						String fileName = jFileChooser.getSelectedFile().getAbsolutePath();
						controller.setInputMap(new InputMap(fileName));
						controller.changeMapOfGaming(fileName);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		});		
	}
	
	public void createSlider() {
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
		String msg = "Turn : " + this.controller.getGame().getMaxturn();
		jLabel = new JLabel(msg, JLabel.CENTER);
		jLabel.setFont(new Font("Serif", Font.BOLD, 14));
	}
	
	public void ajoutePanelOnJFrame() {
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
		
		JPanel jPanelMain1 = new JPanel(new GridLayout(3, 1));
		JPanel jPanelMain2 = new JPanel(new GridLayout(1, 2));
		
		jPanelMain1.add(jPanelBouton);
		
		// BONUS
		JPanel panelChooseInterfaceButton = new JPanel();
		panelChooseInterfaceButton.add(jButtonChooseInterface);
		jPanelMain1.add(panelChooseInterfaceButton);
		
		jPanelMain2.add(jPanel2);
		jPanelMain2.add(jPanel3);
		jPanelMain1.add(jPanelMain2);

		this.addPanel(jPanelMain1);
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

	public JFrame getjFrame() {
		return jFrame;
	}

	public void setjFrame(JFrame jFrame) {
		this.jFrame = jFrame;
	}

	public JSlider getjSlider() {
		return jSlider;
	}

	public void setjSlider(JSlider jSlider) {
		this.jSlider = jSlider;
	}

	public JButton getjButtonRestart() {
		return jButtonRestart;
	}

	public void setjButtonRestart(JButton jButtonRestart) {
		this.jButtonRestart = jButtonRestart;
	}

	public JButton getjButtonStart() {
		return jButtonStart;
	}

	public void setjButtonStart(JButton jButtonStart) {
		this.jButtonStart = jButtonStart;
	}

	public JButton getjButtonPlay() {
		return jButtonPlay;
	}

	public void setjButtonPlay(JButton jButtonPlay) {
		this.jButtonPlay = jButtonPlay;
	}

	public JButton getjButtonPause() {
		return jButtonPause;
	}

	public void setjButtonPause(JButton jButtonPause) {
		this.jButtonPause = jButtonPause;
	}

	public JLabel getjLabelSlider() {
		return jLabelSlider;
	}

	public void setjLabelSlider(JLabel jLabelSlider) {
		this.jLabelSlider = jLabelSlider;
	}

	public JLabel getjLabel() {
		return jLabel;
	}

	public void setjLabel(JLabel jLabel) {
		this.jLabel = jLabel;
	}

	public EtatButtonCommande getEtat() {
		return etat;
	}

	public void setEtat(EtatButtonCommande etat) {
		this.etat = etat;
	}
	
	public JButton getjButtonChooseInterface() {
		return jButtonChooseInterface;
	}

	public void setjButtonChooseInterface(JButton jButtonChooseInterface) {
		this.jButtonChooseInterface = jButtonChooseInterface;
	}
}
