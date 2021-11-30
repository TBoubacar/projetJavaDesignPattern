package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import agent.Agent;
import agent.AgentBird;
import agent.AgentUsine;
import utils.AgentAction;
import utils.InfoAgent;
import utils.InfoBomb;
import utils.InfoItem;
import utils.ItemType;
import utils.StateBomb;

public class BombermanGame extends Game {
	/*
	 * 	DANS MA METHODOLOGIE D'IMPLEMENTATION DU CODE, TOUS LES AGENTS BOMBERMANS SONT ALLIÉS LORS QUE J'UTILISE LA METHODE : deleteExplosedBombe()
	 * 	ET ILS SONT TOUS ENNEMIS QUAND ON UTILISE LA METHODE : deleteExplosedBombe2() 
	 * 	DANS LA METHODE takeTurn()
	 */
	
	private boolean finish;						// VARIABLE PERMETTANT DE METTRE FIN AU JEU
	private AgentUsine usineOfAgent;			// CECI CORRESPOND À UNE USINE D'AGENTS PERMETTANT DE CRÉER DIFFÉRENT TYPE D'AGENT (ON NE POSSÈDE QUE 4 TYPES POUR LE MOMENT)

	private ArrayList<Agent> agents;			// CECI CORRESPOND À L'ENSEMBLE DES AGENTS CRÉÉS DANS NOTRE USINE
	private ArrayList<Item> items;				// CECI CORRESPOND À L'ENSEMBLE DES AGENTS CRÉÉS DANS NOTRE USINE
	private ArrayList<InfoBomb> bombes;			// CECI CORRESPOND À L'ENSEMBLE DES INFORMATIONS SUR LES BOMBES SUR NOTRE TERRAIN DE JEU

	private ArrayList<InfoAgent> infoAgents;	// CECI CORRESPOND À L'ENSEMBLE DES INFORMATIONS SUR LES AGENTS SUR NOTRE TERRAIN DE JEU
	private ArrayList<InfoItem> infoItems;		// CECI CORRESPOND À L'ENSEMBLE DES INFORMATIONS SUR LES ITEMS SUR NOTRE TERRAIN DE JEU
	private boolean[][] infoMurs;				// CECI CORRESPOND À L'ENSEMBLE DES INFORMATIONS SUR LES MÛRS BRISABLE SUR NOTRE TERRAIN DE JEU

	public BombermanGame(int tourMax, String filename) {
		super(tourMax);
		this.finish = false;
		this.usineOfAgent = AgentUsine.getInstance();
		
		this.agents = new ArrayList<Agent>();
		this.bombes = new ArrayList<InfoBomb>();
		this.items = new ArrayList<Item>();

		try {
			this.setInputMap(new InputMap(filename));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.infoAgents = new ArrayList<InfoAgent>();
		this.setInfoItems(new ArrayList<InfoItem>());

		this.initializeGame();
	}


	@Override
	public void initializeGame() {
		int i = 0;
		this.infoMurs = new boolean[this.getInputMap().get_walls().length][this.getInputMap().get_walls()[0].length];
		for(boolean[] m : this.getInputMap().getStart_breakable_walls())
			this.infoMurs[i++] = m.clone();

		this.finish = false;
		this.agents.clear();
		this.bombes.clear();
		this.items.clear();
		this.infoItems.clear();
		this.infoAgents.clear();

		for(InfoAgent a : this.getInputMap().getStart_agents())
			this.infoAgents.add(a);

		for(InfoAgent agent : this.getInputMap().getStart_agents()) {
			this.agents.add(this.usineOfAgent.createAgent(agent.getType(), agent.getColor(), agent.getX(), agent.getY()));
		}
		//		System.out.println(this.usineOfAgent);		// VOUS POUVEZ DECOMMENTER CE CODE POUR VOUS ASSURER QUE LA CLASSE HASHTABLE GERE BIEN LE CAS DES DOUBLONS ET EVITE DE LES INSERER
	}

	public boolean isLegalMove(Agent agent, AgentAction agentAction) {
		// LE DEPLACEMENT N'EST POSSIBLE QUE SI ON NE SE TROUVERA PAS À LA MÊME POSITION QU'UN MÛR QUELQUE SOIT SON TYPE (BRISABLE OU PAS)
		if((agent.getX() >= 1 && agent.getY() >= 1) && (agent.getX() < this.getInputMap().get_walls().length && agent.getY() < this.getInputMap().get_walls()[0].length)) {
			switch(agentAction) {
			case MOVE_DOWN:
				if(agent.isCanFly()) return !this.getInputMap().get_walls()[agent.getX()][agent.getY()+1];
				else
					return !this.getInputMap().get_walls()[agent.getX()][agent.getY()+1] && !this.infoMurs[agent.getX()][agent.getY()+1];
			case MOVE_UP:
				if(agent.isCanFly()) return !this.getInputMap().get_walls()[agent.getX()][agent.getY()-1];
				else
					return !this.getInputMap().get_walls()[agent.getX()][agent.getY()-1] && !this.infoMurs[agent.getX()][agent.getY()-1];
			case MOVE_LEFT:
				if(agent.isCanFly()) return !this.getInputMap().get_walls()[agent.getX()-1][agent.getY()];
				else
					return !this.getInputMap().get_walls()[agent.getX()-1][agent.getY()] && !this.infoMurs[agent.getX()-1][agent.getY()];
			case MOVE_RIGHT:
				if(agent.isCanFly()) return !this.getInputMap().get_walls()[agent.getX()+1][agent.getY()];
				else
					return !this.getInputMap().get_walls()[agent.getX()+1][agent.getY()] && !this.infoMurs[agent.getX()+1][agent.getY()];
			case PUT_BOMB:
				if(agent.isCanFly()) return !this.getInputMap().get_walls()[agent.getX()][agent.getY()];
				else 
					return !this.getInputMap().get_walls()[agent.getX()][agent.getY()] && !this.infoMurs[agent.getX()][agent.getY()];
			default:
				if(agent.isCanFly()) return !this.getInputMap().get_walls()[agent.getX()][agent.getY()];
				else
					return !this.getInputMap().get_walls()[agent.getX()][agent.getY()] && !this.infoMurs[agent.getX()][agent.getY()];
			}			
		} else
			return false;
	}

	public void moveAgent(Agent agent, AgentAction action) {
		if(isLegalMove(agent, action)) {
			if(agent.getType() == 'V' && agent.isSleep()) {
				AgentBird bird = (AgentBird) agent;
				if(bird.agentBirdRadar(this.agents)) {
					System.out.println("Agent Bird (" + agent.getX() +", " + agent.getY() + ") a répéré un agent bomberman a {" + bird.getRayonAction() + "} mètres de lui !");
					agent.setSleep(false);
					agent.setMoveStrategie(new AleatoireStrategie());
					agent.move(action);
				}	// L'AGENT BIRD NE BOUGE QUE S'IL REPÈRE UN BOMBERMAN DANS UN RAYON DE bird.getRayonAction() MÈTRES
			}
			else {
				agent.move(action);
			}
		}
	}

	public void putBomb(Agent bomberman) {
		if(bomberman.getType() == 'B' && !bomberman.isSick()) {
			InfoBomb bombe = new InfoBomb(bomberman.getX(), bomberman.getY(), bomberman.getBOMBE_RANGE(), StateBomb.Step0);
			bomberman.getBombes().add(bombe);	//	UTILE POUR DIFFÉRENTIER LES BOMBES APPARTENANT A CHAQUE BOMBERMAN. DE CETTE MANIÈRE LES BOMBERMANS NE SERONT PAS TUÉS PAR LEURS PROPRES BOMBES
			this.bombes.add(bombe);			
		} else {
			System.out.println("LE BOMBERMAN ["+ bomberman.getColor() + "] EN POSITION (" + bomberman.getX() + ", " + bomberman.getY()  + ") EST MALADE POUR POSER DES BOMBES, DÉSOLÉ !");
		}
	}
	
	public ArrayList<InfoAgent> agentToucher(InfoBomb bombe) {
		ArrayList<InfoAgent> agentsToucher = new ArrayList<InfoAgent>();
		for(InfoAgent a: this.infoAgents) {
			if((a.getType() != 'B' && a.getX() == bombe.getX() && a.getY() >= (bombe.getY()-bombe.getRange()) && a.getY() <= (bombe.getY()+bombe.getRange())) || (a.getType() != 'B' && a.getY() == bombe.getY() && a.getX() >= (bombe.getX()-bombe.getRange()) && a.getX() <= (bombe.getX()+bombe.getRange()))) {
				agentsToucher.add(a);				
			}
		}
		return agentsToucher;
	}	//CETTE FONCTION ME PERMET DE RECUPERER LA LISTE DES INFORMATIONS SUR LES AGENTS TOUCHER PAR L'EXPLOSION DE LA BOMBE A PARTIR DE LA POSITION D'EXPLOSION DE LA BOMBE

	public ArrayList<InfoAgent> agentToucher2(InfoBomb bombe) {
		ArrayList<InfoAgent> agentsToucher = new ArrayList<InfoAgent>();
		for(Agent a: this.agents) {
			if((!a.getBombes().contains(bombe) && a.getX() == bombe.getX() && a.getY() >= (bombe.getY()-bombe.getRange()) && a.getY() <= (bombe.getY()+bombe.getRange())) || (!a.getBombes().contains(bombe) && a.getY() == bombe.getY() && a.getX() >= (bombe.getX()-bombe.getRange()) && a.getX() <= (bombe.getX()+bombe.getRange()))) {
				InfoAgent infoAgent = new InfoAgent(a.getX(), a.getY(), null, a.getType(), a.getColor(), a.isInvincible(), a.isSick());
				agentsToucher.add(infoAgent);
			}
		}
		return agentsToucher;
	}	//CETTE FONCTION ME PERMET DE RECUPERER LA LISTE DES INFORMATIONS SUR LES AGENTS TOUCHER PAR L'EXPLOSION DE LA BOMBE (SANS COMPTER LE POSEUR DE BOMBE) A PARTIR DE LA POSITION D'EXPLOSION DE LA BOMBE
	
	public void deleteDiedAgent(InfoAgent cetAgent) {
		if(cetAgent != null) {
			Iterator<Agent> iter = this.agents.iterator();
			Iterator<InfoAgent> iter2 = this.infoAgents.iterator();
			
			while(iter.hasNext()) {		// ON SUPPRIME L'AGENT 
				Agent agent = iter.next();
				if(!agent.isInvincible() && agent.getType() == cetAgent.getType() && agent.getX() == cetAgent.getX() && agent.getY() == cetAgent.getY()) {
					iter.remove();
					System.out.println("Agent [" + agent.getType() + "] est éliminé à la position (" + agent.getX() + ", " + agent.getY() + ")");
				}
			}
			while(iter2.hasNext()) {	// PUIS ON SUPPRIME LES INFORMATIONS SUR L'AGENT
				InfoAgent agent = iter2.next();
				if(agent == cetAgent) {
					iter2.remove();				
				}
			}
			
		}
	}	// CETTE FONCTION ME PERMET DE SUPPRIMER UN AGENT SUPPOSÉ ETRE TOUCHÉ PAR UNE BOMBE

	public void deleteExplosedMur(InfoBomb bombe) {
		this.infoMurs[bombe.getX()][bombe.getY()] = false;
		
		for(int i = 1; i <= bombe.getRange(); ++i) {
			if((bombe.getX()-i) >= 1) {
				if(this.infoMurs[bombe.getX()-i][bombe.getY()]) {
					Item item = new Item(bombe.getX()-i, bombe.getY());
					ItemType itemType = item.chooseAleatoireItem();
					if(itemType != null) {
						item.setItemType(itemType);
						this.items.add(item);
						this.infoItems.add(new InfoItem(item.getX(), item.getY(), itemType));
					}
				}
				this.infoMurs[bombe.getX()-i][bombe.getY()] = false;
			}
			if((bombe.getX()+i) < this.infoMurs.length) {
				if(this.infoMurs[bombe.getX()+i][bombe.getY()]) {
					Item item = new Item(bombe.getX()+i, bombe.getY());
					ItemType itemType = item.chooseAleatoireItem();
					if(itemType != null) {
						item.setItemType(itemType);
						this.items.add(item);
						this.infoItems.add(new InfoItem(item.getX(), item.getY(), itemType));
					}
				}
				this.infoMurs[bombe.getX()+i][bombe.getY()] = false;
			}
			if((bombe.getY()-i) >= 1) {
				if(this.infoMurs[bombe.getX()][bombe.getY()-i]) {
					Item item = new Item(bombe.getX(), bombe.getY()-i);
					ItemType itemType = item.chooseAleatoireItem();
					if(itemType != null) {
						item.setItemType(itemType);
						this.items.add(item);
						this.infoItems.add(new InfoItem(item.getX(), item.getY(), itemType));
					}				}
				this.infoMurs[bombe.getX()][bombe.getY()-i] = false;
			}
			if((bombe.getY()+i) < this.infoMurs[0].length) {
				if (this.infoMurs[bombe.getX()][bombe.getY()+i]) {
					Item item = new Item(bombe.getX(), bombe.getY()+i);
					ItemType itemType = item.chooseAleatoireItem();
					if(itemType != null) {
						item.setItemType(itemType);
						this.items.add(item);
						this.infoItems.add(new InfoItem(item.getX(), item.getY(), itemType));
					}
				}
				this.infoMurs[bombe.getX()][bombe.getY()+i] = false;
			}
		}
	}	// CETTE FONCTION ME PERMET DE SUPPRIMER UN MUR SUPPOSÉ ETRE TOUCHÉ PAR UNE BOMBE SUR NOTRE CARTE
	
	public void deleteExplosedBombe() {		// LORS QUE NOS BOMBERMANS SONT ALLIÉS
		Iterator<InfoBomb> iter = this.bombes.iterator();
		
		while(iter.hasNext()) {
			InfoBomb bombe = iter.next();
			if(bombe.getStateBomb() == StateBomb.Boom) {
				for(InfoAgent agent :this.agentToucher(bombe)) {
					this.deleteDiedAgent(agent);
				}
				this.deleteExplosedMur(bombe);
				iter.remove();
			}
		}
	}

	public void deleteExplosedBombe2() {		// LORS QUE NOS BOMBERMANS NE SONT PAS ALLIÉS
		Iterator<InfoBomb> iter = this.bombes.iterator();
		
		while(iter.hasNext()) {
			InfoBomb bombe = iter.next();
			if(bombe.getStateBomb() == StateBomb.Boom) {
				for(InfoAgent agent :this.agentToucher2(bombe)) {
					this.deleteDiedAgent(agent);
				}
				this.deleteExplosedMur(bombe);
				iter.remove();
			}
		}
	}
	
	public InfoAgent eatBomberman() {		
		for(InfoAgent agent : this.infoAgents) {		// ON SUPPRIME LES INFORMATIONS SUR LES AGENTS BOMBERMAN QUI DOIVENT ETRE MANGER
			for(InfoAgent cetAgent : this.infoAgents) {
				if (agent.isInvincible() && agent.getType() == 'B' && agent.getType() != cetAgent.getType() && agent.getX() == cetAgent.getX() && agent.getY() == cetAgent.getY()) {
					System.out.println("LE BOMBERMAN["+ agent.getColor() + "] EN POSITION (" + agent.getX() + ", " + agent.getY()  + ") EST INVINCIBLE, DONC NE PEUT PAS ETRE TUÉ !");
				}
				if(!agent.isInvincible() && agent.getType() == 'B' && agent.getType() != cetAgent.getType() && agent.getX() == cetAgent.getX() && agent.getY() == cetAgent.getY()) {
					return agent;				
				}
			}
		} return null;
	}
	
	public void deleteItem(Item cetItem) {		// PERMET DE SUPPRIMER UN ITEM DU TERRAIN
		if(cetItem != null) {
			Iterator<Item> iter = this.items.iterator();
			Iterator<InfoItem> iter2 = this.infoItems.iterator();
			
			while(iter.hasNext()) {		// ON SUPPRIME L'ITEM 
				Item item = iter.next();
				if(item == cetItem) {
					iter.remove();
				}
			}
			while(iter2.hasNext()) {			// PUIS ON SUPPRIME LES INFORMATIONS SUR L'ITEM
				InfoItem itemInfo = iter2.next();
				if(itemInfo.getType() == cetItem.getItemType() && itemInfo.getX() == cetItem.getX() && itemInfo.getY() == cetItem.getY()) {
					iter2.remove();
				}
			}
		}
	}

	public Item eatItem() {					// PERMET A UN BOMBERMAN D'ACQUERIR LES EFFETS POSITIFS OU NEGATIFS D'UN ITEM ET RETOURNE CET ITEM AFIN QUE LA FONCTION deleteItem() SE CHARGE DE LE SUPPRIMER
		if(this.items.size() > 0) {
			for(Agent agent : this.agents) {
				for(Item item: this.items) {
					if(item.getItemType() != null && agent.getType() == 'B' && agent.getX() == item.getX() && agent.getY() == item.getY()) {
						System.out.println("L'agent ["+ agent.getColor() + "] en position ("  + agent.getX() + ", " + agent.getY()  + ") a pris l'item : <<" + item.getItemType() + ">>");
						agent.applyItemEffet(item);
						return item;
					}
				}
			}	
		}
		return null;
	}
	
	public boolean hasSurvivantAgentBomberman() {		// CETTE METHODE ME PERMET DE SAVOIR S'IL RESTE UN AGENT BOMBERMAN SUR LE TERRAIN
		for(Agent a: this.agents) {
			if(a.getType() == 'B') return true;
		} return false;
	}

	public boolean hasSurvivantAgentPNJ() {				// CETTE METHODE ME PERMET DE SAVOIR S'IL RESTE UN AGENT PNJ SUR LE TERRAIN
		for(Agent a: this.agents) {
			if(a.getType() != 'B') return true;
		} return false;
	}

	public boolean hasOneSurvivant() {					// CETTE METHODE ME PERMET DE SAVOIR S'IL RESTE UN ET UN SEUL AGENT BOMBERMAN SUR LE TERRAIN (DANS LE CAS OÙ TOUS LES AGENTS SONT ENEMIS)
		return this.agents.size() <= 1 && this.agents.get(this.agents.size()-1).getType() == 'B';
	}

	@Override
	public void takeTurn() {
		String msg = "Tour " + super.getTurn() + " du jeu en cours";
		System.out.println(msg);
		
//		this.deleteExplosedBombe();		// AVEC CETTE METHODE LES BOMBERMAN SONT TOUS ALLIÉS
		this.deleteExplosedBombe2();	// AVEC CETTE METHODE LES BOMBERMAN SONT TOUS ENNEMIS
		this.deleteDiedAgent(this.eatBomberman());
		
		this.verifyEtatOfGame();
		
		Random hasard= new Random(System.currentTimeMillis());
		this.infoAgents.clear();		// ON VA METTRE À JOUR LES INFORMATIONS SUR LES AGENTS

		for(Agent agent : this.agents) {
			AgentAction action = agent.chooseStrategie();

			if(hasard.nextInt(20) == 0 && agent.getType() == 'B') {
				this.putBomb(agent);	
			}
			this.moveAgent(agent, action);

			InfoAgent infoAgent = new InfoAgent(agent.getX(), agent.getY(), action, agent.getType(), agent.getColor(), agent.isInvincible(), agent.isSick());
			this.infoAgents.add(infoAgent);
			agent.deleteExplosedBombe();
			
			if(agent.isInvincible() || agent.isSick()) {
				agent.applyEffetOfInvincibilityAndSick();
			}
		}
		
		for(InfoBomb bombe: this.bombes) {
			bombe.declencheMinuteurOfBombe();
		}

		this.deleteItem(this.eatItem());
	}

	@Override
	public boolean gameContinue() {
		return !this.finish;
	}

	@Override
	public void gameOver() {
		this.bombes.clear();
		String msg = "Oooh Oooooohhh ! Vos agents bomberman ont été mangé. \nPartie Terminé (^_^)";
		System.out.println(msg);
		this.finish = true;
		this.setRunning(false);
		this.notifyObserver();
	}

	public void gameWin() {
		this.bombes.clear();
		String msg = "Félicitations ! l'agent bomberman " + this.agents.get(0).getColor() + " a remporté la partie. \nPartie Terminé (^_^)";
		System.out.println(msg);
		this.finish = true;
		this.setRunning(false);
		this.notifyObserver();
	}
	
	public void egalityGame() {
		this.bombes.clear();
		String msg = "Ouuppss ! Il y a match null. \nPartie Terminé (^_^)";
		System.out.println(msg);
		this.finish = true;
		this.setRunning(false);
		this.notifyObserver();
	}

	public void verifyEtatOfGame() {
		// S'IL N'Y A PLUS DE SURVIVANT, ALORS LE JEU EST TERMINÉ
		if(!this.hasSurvivantAgentBomberman()) {
			this.gameOver();
		} 
//		else if(!this.hasSurvivantAgentPNJ()) {				// DECOMMENTE CETTE FONCTION SI TU VEUX QUE LES AGENTS BOMBERMAN SOIENT DES ALLIÉS.
//			this.gameWin();
//		}
		else if(this.hasOneSurvivant()) {					// DECOMMENTE CETTE FONCTION SI TU VEUX QUE LES AGENTS BOMBERMAN SOIENT DES ENNEMIES.
			this.gameWin();
		} else if (this.getTurn() == this.getMaxturn()){
			this.egalityGame();
		} else {
			// Je ne fais rien
		}
	}
	
	/*---		GETTERS AND SETTERS		---*/
	public AgentUsine getUsineOfAgent() {
		return usineOfAgent;
	}

	public ArrayList<InfoAgent> getInfoAgents() {
		return infoAgents;
	}

	public void setInfoAgents(ArrayList<InfoAgent> infoAgents) {
		this.infoAgents = infoAgents;
	}

	public boolean[][] getInfoMurs() {
		return infoMurs;
	}

	public void setInfoMurs(boolean[][] infoMurs) {
		this.infoMurs = infoMurs;
	}

	public ArrayList<InfoItem> getInfoItems() {
		return infoItems;
	}

	public void setInfoItems(ArrayList<InfoItem> infoItems) {
		this.infoItems = infoItems;
	}

	public ArrayList<Agent> getAgents() {
		return agents;
	}

	public void setAgents(ArrayList<Agent> agents) {
		this.agents = agents;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public ArrayList<InfoBomb> getBombes() {
		return bombes;
	}

	public void setBombes(ArrayList<InfoBomb> bombes) {
		this.bombes = bombes;
	}

}
