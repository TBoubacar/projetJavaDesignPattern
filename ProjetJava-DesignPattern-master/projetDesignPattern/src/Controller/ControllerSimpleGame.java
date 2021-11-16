package Controller;

import utils.Game;
import utils.SimpleGame;
import view.ViewCommand;
import view.ViewSimpleGame;

public class ControllerSimpleGame extends AbstractController{
	private ViewSimpleGame viewSimpleGame;
	private ViewCommand viewCommand;
	//		POUR CE TYPE D'IMPLEMENTATION, ON N'EST PAS FERMÉ À LA MODIFICATION AVEC L'AJOUT D'UNE NOUVELLE VUE.
	//		POUR UNE MEILLEURE IMPLEMENTATION, IL FAUDRAIT CRÉER UNE CLASSE MERE VIEW DONT HERITERAIT TOUTES LES CLASSES VIEWS
	//		ET AINSI CRÉER UN ARRAY LIST PERMETTANT DE STOCKER L'ENSEMBLE DES VUES DE NOTRE JEU DANS LE CONTROLEUR ET AINSI CREER
	//		UNE METHODE ADD_NEW_VIEW PERMETTANT D'AJOUTER AUTOMATIQUEMENT UNE NOUVELLE VUE A LA DEMANDE SANS AVOIR A MODIFIER LA CLASSE
	//		ET APPLIQUER L'AFFICHAGE ET LA FERMETURE DES VUES A L'AIDE D'UNE BOUCLE FOR SUR NOTRE ARRAY LIST DE VUE
	//		CELA PERMETTRAIT D'APPLIQUER UN ENSEMBLE DE FONCTIONNALITÉ COMMUN A TOUTES LES VUES DE MANIÈRE AUTOMATIQUE.
	//		PAR CONTRE POUR CE CAS, JE PRÉFÈRE AVANCER COMME ÇA AFIN DE NE PAS AVOIR À FAIRE TROP DE MODIFICATION DANS MON CODE
	//		ET GAGNER PLUS DE TEMPS POUR LA FINITION DU RESTE DE MON PROJET (^_^)
	//______N.B.	ON SUPPOSERA QUE NOTRE JEU N'AURA PLUS D'AUTRES VUES À IMPLEMENTER.
	
	public ControllerSimpleGame(Game game) {
		super.setGame(new SimpleGame(game.getMaxturn()));
		this.viewSimpleGame = new ViewSimpleGame(this);
		this.viewCommand = new ViewCommand(this);
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
}
