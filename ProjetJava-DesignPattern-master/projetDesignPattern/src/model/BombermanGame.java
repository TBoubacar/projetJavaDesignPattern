package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import agent.Agent;
import agent.AgentUsine;
import utils.AgentAction;
import utils.InfoAgent;
import utils.InfoBomb;
import utils.InfoItem;
import utils.StateBomb;

public class BombermanGame extends Game {
	/*
	 * 	DANS MA METHODOLOGIE D'IMPLEMENTATION DU CODE, TOUS LES AGENTS BOMBERMANS SONT ALLIÉS
	 */
	
	private boolean finish;						// VARIABLE PERMETTANT DE METTRE FIN AU JEU
	private AgentUsine usineOfAgent;			// CECI CORRESPOND À UNE USINE D'AGENTS PERMETTANT DE CRÉER DIFFÉRENT TYPE D'AGENT (ON NE POSSÈDE QUE 4 TYPES POUR LE MOMENT)
	private ArrayList<Agent> agents;			// CECI CORRESPOND À L'ENSEMBLE DES AGENTS CRÉÉS DANS NOTRE USINE

	private ArrayList<InfoAgent> infoAgents;	// CECI CORRESPOND À L'ENSEMBLE DES INFORMATIONS SUR LES AGENTS SUR NOTRE TERRAIN DE JEU
	private ArrayList<InfoItem> infoItems;		// CECI CORRESPOND À L'ENSEMBLE DES INFORMATIONS SUR LES ITEMS SUR NOTRE TERRAIN DE JEU
	private boolean[][] infoMurs;				// CECI CORRESPOND À L'ENSEMBLE DES INFORMATIONS SUR LES MÛRS BRISABLE SUR NOTRE TERRAIN DE JEU
	private ArrayList<InfoBomb> bombes;			// CECI CORRESPOND À L'ENSEMBLE DES INFORMATIONS SUR LES BOMBES SUR NOTRE TERRAIN DE JEU

	public BombermanGame(int tourMax, String filename) {
		super(tourMax);
		this.finish = false;	
		this.usineOfAgent = AgentUsine.getInstance();
		this.agents = new ArrayList<Agent>();
		this.bombes = new ArrayList<InfoBomb>();

		try {
			this.setInputMap(new InputMap(filename));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.infoAgents = new ArrayList<InfoAgent>();
		this.infoMurs = new boolean[this.getInputMap().get_walls().length][this.getInputMap().get_walls()[0].length];
		this.setInfoItems(new ArrayList<InfoItem>());

		for(InfoAgent a : this.getInputMap().getStart_agents())
			this.infoAgents.add(a);

		this.initializeGame();
	}

	public ArrayList<InfoBomb> getBombes() {
		return bombes;
	}

	public void setBombes(ArrayList<InfoBomb> bombes) {
		this.bombes = bombes;
	}

	public boolean isLegalMove(Agent agent, AgentAction agentAction) {
		// LE DEPLACEMENT N'EST POSSIBLE QUE SI ON NE SE TROUVERA PAS À LA MÊME POSITION QU'UN MÛR QUELQUE SOIT SON TYPE (BRISABLE OU PAS)
		if((agent.getX() >= 1 && agent.getY() >= 1) && (agent.getX() <= this.getInputMap().get_walls().length && agent.getY() <= this.getInputMap().get_walls().length)) {
			switch(agentAction) {
			case MOVE_DOWN:
				if(agent.isCanFly()) return !this.getInputMap().get_walls()[agent.getX()][agent.getY()+1];
				else
					return !this.getInputMap().get_walls()[agent.getX()][agent.getY()+1] && !this.getInputMap().getStart_breakable_walls()[agent.getX()][agent.getY()+1];
			case MOVE_UP:
				if(agent.isCanFly()) return !this.getInputMap().get_walls()[agent.getX()][agent.getY()-1];
				else
					return !this.getInputMap().get_walls()[agent.getX()][agent.getY()-1] && !this.getInputMap().getStart_breakable_walls()[agent.getX()][agent.getY()-1];
			case MOVE_LEFT:
				if(agent.isCanFly()) return !this.getInputMap().get_walls()[agent.getX()-1][agent.getY()];
				else
					return !this.getInputMap().get_walls()[agent.getX()-1][agent.getY()] && !this.getInputMap().getStart_breakable_walls()[agent.getX()-1][agent.getY()];
			case MOVE_RIGHT:
				if(agent.isCanFly()) return !this.getInputMap().get_walls()[agent.getX()+1][agent.getY()];
				else
					return !this.getInputMap().get_walls()[agent.getX()+1][agent.getY()] && !this.getInputMap().getStart_breakable_walls()[agent.getX()+1][agent.getY()];
			case PUT_BOMB:
				if(agent.isCanFly()) return !this.getInputMap().get_walls()[agent.getX()][agent.getY()];
				else 
					return !this.getInputMap().get_walls()[agent.getX()][agent.getY()] && !this.getInputMap().getStart_breakable_walls()[agent.getX()][agent.getY()];
			default:
				if(agent.isCanFly()) return !this.getInputMap().get_walls()[agent.getX()][agent.getY()];
				else
					return !this.getInputMap().get_walls()[agent.getX()][agent.getY()] && !this.getInputMap().getStart_breakable_walls()[agent.getX()][agent.getY()];
			}			
		} else
			return false;
	}

	public void moveAgent(Agent agent, AgentAction action) {
		if(isLegalMove(agent, action))
			agent.move(action);
	}

	public void putBomb(Agent bomberman) {
		if(bomberman.getType() == 'B') {
			InfoBomb bombe = new InfoBomb(bomberman.getX(), bomberman.getY(), bomberman.getBOMBE_RANGE(), StateBomb.Step0);
			bomberman.getBombes().add(bombe);	//	UTILE POUR DIFFÉRENTIER LES BOMBES APPARTENANT A CHAQUE BOMBERMAN. DE CETTE MANIÈRE LES BOMBERMANS NE SERONT PAS TUÉS PAR LEURS PROPRES BOMBES
			this.bombes.add(bombe);			
		} else {
			System.out.println("SEUL LES BOMBERMANS ONT LA POSSIBILITÉ DE POSER DES BOMBES, DÉSOLÉ !");
		}
	}

	@Override
	public void initializeGame() {
		int i = 0;
		for(boolean[] m : this.getInputMap().getStart_breakable_walls())
			this.infoMurs[i++] = m.clone();
		this.finish = false;
		this.agents.clear();
		this.bombes.clear();
		this.infoItems.clear();
		this.infoAgents.clear();

		for(InfoAgent agent : this.getInputMap().getStart_agents()) {
			this.agents.add(this.usineOfAgent.createAgent(agent.getType(), agent.getColor(), agent.getX(), agent.getY()));
		}
		//		System.out.println(this.usineOfAgent);		// VOUS POUVEZ DECOMMENTER CE CODE POUR VOUS ASSURER QUE LA CLASSE HASHTABLE GERE BIEN LE CAS DES DOUBLONS ET EVITE DE LES INSERER
	}

	public ArrayList<InfoAgent> agentToucher(int xExplosion, int yExplosion, int rangeExplosion) {
		ArrayList<InfoAgent> agentsToucher = new ArrayList<InfoAgent>();
		for(InfoAgent a: this.infoAgents) {
			if((a.getX() == xExplosion && a.getY() >= (yExplosion-rangeExplosion) && a.getY() <= (yExplosion+rangeExplosion)) || (a.getY() == yExplosion && a.getX() >= (xExplosion-rangeExplosion) && a.getX() <= (xExplosion+rangeExplosion)))
				agentsToucher.add(a);
		}
		return agentsToucher;
	}	//CETTE FONCTION ME PERMET DE RECUPERER LA LISTE DES INFORMATIONS SUR LES AGENTS TOUCHER PAR L'EXPLOSION DE LA BOMBE A PARTIR DE LA POSITION D'EXPLOSION DE LA BOMBE
	
	public void deleteDiedAgent(InfoAgent cetAgent) {
		if(cetAgent != null) {
			Iterator<Agent> iter = this.agents.iterator();
			Iterator<InfoAgent> iter2 = this.infoAgents.iterator();
			
			while(iter.hasNext()) {		// ON SUPPRIME L'AGENT 
				Agent agent = iter.next();
				if(agent.getType() == cetAgent.getType() && agent.getX() == cetAgent.getX() && agent.getY() == cetAgent.getY()) {
					iter.remove();				
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

	public void deleteExplosedMur(int xExplosion, int yExplosion, int rangeExplosion) {
		this.infoMurs[xExplosion][yExplosion] = false;
		for(int i = 1; i <= rangeExplosion; ++i) {
			if((xExplosion-i) >= 1) this.infoMurs[xExplosion-i][yExplosion] = false;
			if((xExplosion+i) < this.infoMurs.length) this.infoMurs[xExplosion+i][yExplosion] = false;
			if((yExplosion-i) >= 1) this.infoMurs[xExplosion][yExplosion-i] = false;
			if((yExplosion+i) < this.infoMurs[0].length) this.infoMurs[xExplosion][yExplosion+i] = false;
		}
	}	// CETTE FONCTION ME PERMET DE SUPPRIMER UN MUR SUPPOSÉ ETRE TOUCHÉ PAR UNE BOMBE SUR NOTRE CARTE
	
	public void deleteExplosedBombe() {
		Iterator<InfoBomb> iter = this.bombes.iterator();
		
		while(iter.hasNext()) {
			InfoBomb bombe = iter.next();
			if(bombe.getStateBomb() == StateBomb.Boom) {
				for(InfoAgent agent :this.agentToucher(bombe.getX(), bombe.getY(), bombe.getRange())) {
					this.deleteDiedAgent(agent);
				}
				this.deleteExplosedMur(bombe.getX(), bombe.getY(), bombe.getRange());
				iter.remove();
			}
		}
	}
	
	public boolean hasSurvivantAgentBomberman() {
		for(Agent a: this.agents) {
			if(a.getType() == 'B') return true;
		} return false;
	}
	
	public InfoAgent eatBomberman() {		
		for(InfoAgent agent : this.infoAgents) {		// ON SUPPRIME LES INFORMATIONS SUR LES AGENTS BOMBERMAN QUI DOIVENT ETRE MANGER
			for(InfoAgent cetAgent : this.infoAgents) {
				if(agent.getType() == 'B' && agent.getType() != cetAgent.getType() && agent.getX() == cetAgent.getX() && agent.getY() == cetAgent.getY()) {
					return agent;				
				}
			}
		} return null;
	}
	
	@Override
	public void takeTurn() {
		String msg = "Tour " + super.getTurn() + " du jeu en cours";
		System.out.println(msg);
		
		this.deleteExplosedBombe();
		this.deleteDiedAgent(this.eatBomberman());
		
		if(!this.hasSurvivantAgentBomberman()) {
			// S'IL N'Y A PLUS DE SURVIVANT, ALORS LE JEU EST TERMINÉ
			this.gameOver();
		}
		
		Random hasard= new Random(System.currentTimeMillis());
		this.infoAgents.clear();		// ON VA METTRE À JOUR LES INFORMATIONS SUR LES AGENTS

		for(Agent agent : this.agents) {
			AgentAction action = agent.chooseStrategie();

			if(hasard.nextInt(40) == 0 && agent.getType() == 'B') {
				this.putBomb(agent);	
			}
			this.moveAgent(agent, action);

			InfoAgent infoAgent = new InfoAgent(agent.getX(), agent.getY(), action, agent.getType(), agent.getColor(), agent.isInvincible(), agent.isSick());
			this.infoAgents.add(infoAgent);
			agent.deleteExplosedBombe();
		}
		
		for(InfoBomb bombe: this.bombes) {
			bombe.declencheMinuteurOfBombe();
		}
	}

	@Override
	public boolean gameContinue() {
		return !this.finish;
	}

	@Override
	public void gameOver() {
		this.bombes.clear(); this.agents.clear();
		String msg = "Oooh Oooooohhh! Vos agents bomberman ont été mangé. \nPartie Terminé (^_^)";
		System.out.println(msg);
		this.finish = true;
		this.setRunning(false);
		this.notifyObserver();
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
}
