package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

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
	JButton jButtoneExit;				//MON BOUTON EXIT
	JButton jButtoneChangeMode;			//MON BOUTON CHANGE MODE

	JLabel jLabelSlider;
	JLabel jLabel;
	JLabel jLabel2;
	JTextArea jTextArea;
	
	private AbstractController controller;
	private EtatButtonCommande etat;
	
	// PARTIE BONUS
	JButton jButtonChooseInterface;		//MON BOUTON POUR CHOISIR UN PLATEAU DU JEU

	public ViewCommand(AbstractController controllerGame) {
		this.controller = controllerGame;

		this.createFrame();
		
		this.createButton();
		
		this.createSlider();
		
		this.createLabel();
		
		this.ajoutePanelOnJFrame();
	}

	/*---		METHODS OBSERVER		---*/
	@Override
	public void update(int nombreTour) {
		this.jLabel.setText("Turn : " + nombreTour);
		
		if(this.controller.getGame().getMode() == 1) {
			this.jLabel2.setText("Mode du jeu : NON COOPÉRATIF");
		} else {
			this.jLabel2.setText("Mode du jeu : COOPÉRATIF");			
		}

		// GESTION DE LA VITESSE DU JEU
		if(this.jSlider.getValue() > 0) {
			this.controller.setSpeed((double) (1000/this.jSlider.getValue()));
		}
		

		String info = "";
		info += "#################################\n"		;
		info += "# \tBOMERMAN GAME \n"			;
		info += "# Le nombre d'agent Bomberman : " + this.controller.getGame().nbAgentBombermanSurvivant() + "\n";
		info += "# Le nombre d'agent PNJ : " +  this.controller.getGame().nbAgentPNGSurvivant() + "\n"		;
		info += "# \t-----------INFOS-------------\n"		;
		info += "# \tLE JEU PEUT COMMENCER : \n"			;
		
		// GESTION DE LA FIN DU JEU
		if(this.controller.getGame().getTurn() == this.controller.getGame().getMaxturn() || !this.controller.getGame().gameContinue()) {
			this.setEtat(new EtatFin(this));
			info += "# \t ---FIN DE LA PARTIE--- \n";
			
			if(!this.controller.getGame().hasSurvivantAgentBomberman()) {
				if(this.controller.getGame().getMode() == 1 && this.controller.getGame().getMaxturn() != this.controller.getGame().getTurn() && !this.controller.getGame().hasSurvivantAgentPNJ()) {
					info += "# Oooh Oooooohhh ! \n# Vos agents bomberman se sont tués (Match null).\n";
				} else {
					info += "# Oooh Oooooohhh ! \n# Vos agents bomberman ont été mangé.\n";
				}
			} 
			else if(!this.controller.getGame().hasSurvivantAgentPNJ() && this.controller.getGame().getMode() == 2) {
				if(this.controller.getGame().getMode() == 1) {
					info += "# Félicitations ! \n# L'agent bomberman " + this.controller.getGame().getSurvivant().getColor() + " a remporté la partie.\n";
				} else {
					info += "# Félicitations ! \n# Vos agents bomberman ont remporté la partie.\n";			
				}
			}
			else if(this.controller.getGame().hasOneSurvivant()) {
				if(this.controller.getGame().getMode() == 1) {
					info += "# Félicitations ! \n# L'agent bomberman " + this.controller.getGame().getSurvivant().getColor() + " a remporté la partie.\n";
				} else {
					info += "# Félicitations ! \n# Vos agents bomberman ont remporté la partie.\n";			
				}
			} else {
				if(this.controller.getGame().getMode() == 1 && !this.controller.getGame().hasSurvivantAgentPNJ()) {
					info += "# Ouuppss ! \n# Il y a match null entre les agents bomberman.\n";
				} else {
					info += "# Ouuppss ! \n# Il y a match null entre les bomberman ainsi que les agents PNG.\n";
				}
			}
		}

		info += "#################################"			;
		this.jTextArea.setText(info);
	}
	
	/*---		METHODS CONCRETE		---*/
	public void createFrame() {
		/*---		JFRAME		---*/
		jFrame = new JFrame();
		jFrame.setTitle("Commande");
		jFrame.setSize(new Dimension(700, 500));
		Dimension windowSize = jFrame.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - windowSize.width / 2 + 350 ;
		int dy = centerPoint.y - windowSize.height / 2 - 300;
		jFrame.setLocation(dx,dy);
		try {
			UIManager.setLookAndFeel( new NimbusLookAndFeel() );
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
	public void createButton() {
		/*---		ICONS & BUTTONS		---*/
		Icon restartIcon= new ImageIcon("icons/icon_restart.png");
		Icon startIcon= new ImageIcon("icons/icon_play.png");
		Icon playIcon= new ImageIcon("icons/icon_step.png");
		Icon pauseIcon= new ImageIcon("icons/icon_pause.png");
		Icon exitIcon= new ImageIcon("icons/icon_exit.png");
		
		jButtonRestart = new JButton(restartIcon);
		jButtonStart= new JButton(startIcon);
		jButtonPlay = new JButton(playIcon);
		jButtonPause = new JButton(pauseIcon);
		// PARTIE BONUS
		jButtoneExit = new JButton(exitIcon);
		jButtonChooseInterface = new JButton("Change game card");
		jButtoneChangeMode = new JButton("Change mode");
		
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

		jButtoneExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				controller.closeGame();
			}
		});

		jButtoneChangeMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				controller.getGame().setMode((controller.getGame().getMode() % 2) + 1);
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
	}
	
	public void createLabel() {
		/*---		AFFICHAGE NOMBRE TOUR		---*/
		String msg = "Turn : " + this.controller.getGame().getMaxturn();
		String msg2 = "";
		if(this.controller.getGame().getMode() == 1) {
			msg2 = "Mode du jeu : NON COOPÉRATIF";
		} else {
			msg2 = "Mode du jeu : COOPÉRATIF";			
		}
		jLabel = new JLabel(msg, JLabel.CENTER);
		jLabel2 = new JLabel(msg2, JLabel.CENTER);
		
		jLabel.setFont(new Font("Serif", Font.BOLD, 14));
		jLabel2.setFont(new Font("Serif", Font.BOLD, 14));
		
		jTextArea = new JTextArea(10, 40);
		jTextArea.setPreferredSize( new Dimension(12,30) );
		jTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
		jTextArea.setForeground(Color.WHITE);
		jTextArea.setBackground(Color.BLACK);
		
		String info = "";
		info += "#################################\n"		;
		info += "# \tBOMERMAN GAME \n"			;
		info += "# Le nombre d'agent Bomberman : " + this.controller.getGame().nbAgentBombermanSurvivant() + "\n";
		info += "# Le nombre d'agent PNJ : " +  this.controller.getGame().nbAgentPNGSurvivant() + "\n"		;
		info += "# \t-----------INFOS-------------\n"		;
		info += "# \tLE JEU PEUT COMMENCER : \n"			;
		info += "# \t Bonne (^_^) chance \n"				;
		info += "#################################"			;
		this.jTextArea.setText(info);
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
		
		JPanel jPanel3 = new JPanel(new GridLayout(2,1));
		jPanel3.add(jLabel2);
		jPanel3.add(jLabel);
		
				
		// BONUS
		JScrollPane jScrollPane = new JScrollPane(this.jTextArea);
		
		JPanel jPanelGridOfScrollable = new JPanel(new GridLayout(2, 1));
		JPanel jPanelExit = new JPanel(new GridLayout(1, 1));
		jPanelExit.add(jButtoneExit);
		jPanelGridOfScrollable.add(jPanelExit);
		
		JPanel jPanelBTNBonus = new JPanel(new GridLayout(1, 2));
		jPanelBTNBonus.add(jButtoneChangeMode);
		jPanelBTNBonus.add(jButtonChooseInterface);
		
		jPanelGridOfScrollable.add(jPanelBTNBonus);

		JPanel jpanelScrollable = new JPanel(new GridLayout(1, 2));		
		jpanelScrollable.add(jScrollPane);
		jpanelScrollable.add(jPanelGridOfScrollable);

		JPanel jPanelMain2 = new JPanel(new GridLayout(1, 2));
		jPanelMain2.add(jPanel2);
		jPanelMain2.add(jPanel3);
		
		JPanel jPanelMain1 = new JPanel(new GridLayout(3, 1));
		jPanelMain1.add(jPanelBouton);
		jPanelMain1.add(jpanelScrollable);
		jPanelMain1.add(jPanelMain2);

		this.addPanel(jPanelMain1);
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public JButton getjButtoneChangeMode() {
		return jButtoneChangeMode;
	}

	public void setjButtoneChangeMode(JButton jButtoneChangeMode) {
		this.jButtoneChangeMode = jButtoneChangeMode;
	}

	public JLabel getjLabel2() {
		return jLabel2;
	}

	public void setjLabel2(JLabel jLabel2) {
		this.jLabel2 = jLabel2;
	}

	public void addPanel(JPanel panel) {
		this.jFrame.add(panel);
	}
	
	public void show() {
		jFrame.setVisible(true);
	}

	public void close() {
		jFrame.dispose();
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

	public JButton getjButtoneExit() {
		return jButtoneExit;
	}

	public void setjButtoneExit(JButton jButtoneExit) {
		this.jButtoneExit = jButtoneExit;
	}

	public JTextArea getjTextArea() {
		return jTextArea;
	}

	public void setjTextArea(JTextArea jTextArea) {
		this.jTextArea = jTextArea;
	}
}
