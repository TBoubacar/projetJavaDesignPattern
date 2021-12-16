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

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
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
	ButtonGroup groupBtn;				//MON BOUTON POUR CHANGER LE THEME DE MON INTERFACE GRAPHIQUE
	JRadioButton jRadioButtonMetal;		//LE THÈME METAL
	JRadioButton jRadioButtonMotif;		//LE THÈME MOTIF
	JRadioButton jRadioButtonDefault;	//LE THÈME PAR DEFAULT
	
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
			this.jLabel2.setText("Game mode: NON COOPERATIVE");
		} else {
			this.jLabel2.setText("Game mode: COOPERATIVE");			
		}

		// GESTION DE LA VITESSE DU JEU
		if(this.jSlider.getValue() > 0) {
			this.controller.setSpeed((double) (1000/this.jSlider.getValue()));
		}
		

		String info = "";
		info += "#################################\n"		;
		info += "# \tBOMERMAN GAME \n"			;
		info += "# The maximum number of turns : " + this.controller.getGame().getMaxturn() + "\n";
		info += "# The number of Agent Bomberman : " + this.controller.getGame().nbAgentBombermanSurvivant() + "\n";
		info += "# The number of PNG agents : " +  this.controller.getGame().nbAgentPNGSurvivant() + "\n"		;
		info += "# \t-----------INFO-------------\n"		;
		info += "# \tTHE GAME CAN BEGIN : \n"			;
		
		// GESTION DE LA FIN DU JEU
		if(this.controller.getGame().getTurn() == this.controller.getGame().getMaxturn() || !this.controller.getGame().gameContinue()) {
			this.setEtat(new EtatFin(this));
			info += "# \t ---END OF GAME--- \n";
			
			if(!this.controller.getGame().hasSurvivantAgentBomberman()) {
				if(this.controller.getGame().getMode() == 1 && this.controller.getGame().getMaxturn() != this.controller.getGame().getTurn() && !this.controller.getGame().hasSurvivantAgentPNJ()) {
					info += "# Oooh Oooooohhh ! \n# Your bomberman agents killed each other (Match null).\n";
				} else {
					info += "# Oooh Oooooohhh ! \n# Your bomberman agents have been eaten.\n";
				}
			} 
			else if(!this.controller.getGame().hasSurvivantAgentPNJ() && this.controller.getGame().getMode() == 2) {
				if(this.controller.getGame().getMode() == 1) {
					info += "# Congratulation ! \n# Agent Bomberman " + this.controller.getGame().getSurvivant().getColor() + " won the game.\n";
				} else {
					info += "# Congratulation ! \n# Your bomberman agents won the game.\n";			
				}
			}
			else if(this.controller.getGame().hasOneSurvivant()) {
				if(this.controller.getGame().getMode() == 1) {
					info += "# Congratulation ! \n# Agent Bomberman " + this.controller.getGame().getSurvivant().getColor() + " won the game.\n";
				} else {
					info += "# Congratulation ! \n# Your bomberman agents won the game.\n";			
				}
			} else {
				if(this.controller.getGame().getMode() == 1 && !this.controller.getGame().hasSurvivantAgentPNJ()) {
					info += "# Ouuppss ! \n# There is a null match between the Bomberman agents.\n";
				} else {
					info += "# Ouuppss ! \n# There is a null match between the Bombermans as well as the PNG agents.\n";
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
		jFrame.setTitle("Command");
		jFrame.setSize(new Dimension(800, 680));
		Dimension windowSize = jFrame.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - windowSize.width / 2 + 400 ;
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

		jRadioButtonDefault = new JRadioButton("Default", true);
		jRadioButtonMetal = new JRadioButton("Metal");
		jRadioButtonMotif = new JRadioButton("Motif");
		
		groupBtn = new ButtonGroup();
		groupBtn.add(jRadioButtonDefault);
		groupBtn.add(jRadioButtonMetal);
		groupBtn.add(jRadioButtonMotif);

		jRadioButtonDefault.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					UIManager.setLookAndFeel( new NimbusLookAndFeel() );
				    SwingUtilities.updateComponentTreeUI(jFrame);
				} catch (Exception e) {
					System.out.println("Erreur lors de la définition du LookAndFeel..." + e);
				}
			}
			
		});
		
		jRadioButtonMetal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				String LookAndFeel = "javax.swing.plaf.metal.MetalLookAndFeel";
				try {
					UIManager.setLookAndFeel(LookAndFeel);
				    SwingUtilities.updateComponentTreeUI(jFrame);
				} catch (Exception e) {
					System.out.println("Erreur lors de la définition du LookAndFeel..." + e);
				}
			}
			
		});
		
		jRadioButtonMotif.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				String LookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
				try {
					UIManager.setLookAndFeel(LookAndFeel);
				    SwingUtilities.updateComponentTreeUI(jFrame);
				} catch (Exception e) {
					System.out.println("Erreur lors de la définition du LookAndFeel..." + e);
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
			msg2 = "Game mode: NON COOPERATIVE";
		} else {
			msg2 = "Game mode: COOPERATIVE";			
		}
		jLabel = new JLabel(msg, JLabel.CENTER);
		jLabel2 = new JLabel(msg2, JLabel.CENTER);
		
		jLabel.setFont(new Font("Serif", Font.BOLD, 14));
		jLabel2.setFont(new Font("Serif", Font.BOLD, 14));
		
		jTextArea = new JTextArea(11, 25);
		jTextArea.setPreferredSize( new Dimension(12,30) );
		jTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
		jTextArea.setForeground(Color.WHITE);
		jTextArea.setBackground(Color.BLACK);
		jTextArea.setFont(new Font("Serif", Font.BOLD, 13));		

		String info = "";
		info += "#################################\n"		;
		info += "# \tBOMERMAN GAME \n"			;
		info += "# The maximum number of turns : " + this.controller.getGame().getMaxturn() + "\n";
		info += "# The number of Agent Bomberman : " + this.controller.getGame().nbAgentBombermanSurvivant() + "\n";
		info += "# The number of PNG agents : " +  this.controller.getGame().nbAgentPNGSurvivant() + "\n"		;
		info += "# \t-----------INFO-------------\n"		;
		info += "# \tTHE GAME CAN BEGIN : \n"			;
		info += "# \t Good (^_^) luck \n"				;
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
		
		JPanel jPanelGridOfScrollable = new JPanel(new GridLayout(3, 1));
		JPanel jPanelExit = new JPanel(new GridLayout(1, 1));
		jPanelExit.add(jButtoneExit);
		jPanelGridOfScrollable.add(jPanelExit);

		JPanel jPanelTheme = new JPanel(new GridLayout(2, 1));
		JLabel textChangeInterface = new JLabel("Change theme of interface", JLabel.CENTER);
		textChangeInterface.setFont(new Font("Serif", Font.BOLD, 14));
		
		JPanel jPanelGroupBtn = new JPanel(new GridLayout(1, 1));
		jPanelGroupBtn.add(jRadioButtonDefault);
		jPanelGroupBtn.add(jRadioButtonMetal);
		jPanelGroupBtn.add(jRadioButtonMotif);

		jPanelTheme.add(textChangeInterface);
		jPanelTheme.add(jPanelGroupBtn);

		JPanel jPanelBTNBonus = new JPanel(new GridLayout(1, 2));
		jPanelBTNBonus.add(jButtoneChangeMode);
		jPanelBTNBonus.add(jButtonChooseInterface);
		
		jPanelGridOfScrollable.add(jPanelBTNBonus);
		jPanelGridOfScrollable.add(jPanelTheme);


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
	
	public ButtonGroup getGroupBtn() {
		return groupBtn;
	}

	public void setGroupBtn(ButtonGroup groupBtn) {
		this.groupBtn = groupBtn;
	}

	public JRadioButton getjRadioButtonMetal() {
		return jRadioButtonMetal;
	}

	public void setjRadioButtonMetal(JRadioButton jRadioButtonMetal) {
		this.jRadioButtonMetal = jRadioButtonMetal;
	}

	public JRadioButton getjRadioButtonMotif() {
		return jRadioButtonMotif;
	}

	public void setjRadioButtonMotif(JRadioButton jRadioButtonMotif) {
		this.jRadioButtonMotif = jRadioButtonMotif;
	}
}
