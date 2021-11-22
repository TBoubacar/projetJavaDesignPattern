package utils;

import java.util.ArrayList;
import java.util.Random;

import model.Agent;
import model.AgentUsine;
import model.InputMap;

public class BombermanGame extends Game {
	private final int RANGE = 3;				// RANGE DE LA BOMBE
	private AgentUsine usineOfAgent;			// CECI CORRESPOND À UNE USINE D'AGENTS PERMETTANT DE CRÉER DIFFÉRENT TYPE D'AGENT (ON NE POSSÈDE QUE 4 TYPES POUR LE MOMENT)
	private ArrayList<Agent> agents;			// CECI CORRESPOND À L'ENSEMBLE DES AGENTS CRÉÉS DANS NOTRE USINE

	private ArrayList<InfoBomb> bombes;			// CECI CORRESPOND À L'ENSEMBLE DES INFORMATIONS SUR LES BOMBES SUR NOTRE TERRAIN DE JEU
	private ArrayList<InfoAgent> infoAgents;	// CECI CORRESPOND À L'ENSEMBLE DES INFORMATIONS SUR LES AGENTS SUR NOTRE TERRAIN DE JEU
	private ArrayList<InfoItem> infoItems;		// CECI CORRESPOND À L'ENSEMBLE DES INFORMATIONS SUR LES ITEMS SUR NOTRE TERRAIN DE JEU
	private boolean[][] infoMurs;				// CECI CORRESPOND À L'ENSEMBLE DES INFORMATIONS SUR LES MÛRS BRISABLE SUR NOTRE TERRAIN DE JEU
	
	public BombermanGame(int tourMax, String filename) {
		super(tourMax);
		this.usineOfAgent = AgentUsine.getInstance();
		this.agents = new ArrayList<Agent>();
		this.bombes = new ArrayList<InfoBomb>();
		
		try {
			this.setInputMap(new InputMap(filename));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.infoAgents = new ArrayList<InfoAgent>();
		this.infoMurs = new boolean[this.getInputMap().get_walls().length][this.getInputMap().get_walls().length];
		this.setInfoItems(new ArrayList<InfoItem>());

		for(InfoAgent a : this.getInputMap().getStart_agents())
			this.infoAgents.add(a);

		this.initializeGame();
	}
	
	//	-----------CODE A IMPLEMENTER----------SEANCE 3.4
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

	public void putBomb(int xBomberman, int yBomberman) {
		InfoBomb bombe = new InfoBomb(xBomberman, yBomberman, RANGE, StateBomb.Step0);
		this.bombes.add(bombe);
	}
	
	@Override
	public void initializeGame() {
		int i = 0;
		for(boolean[] a : this.getInputMap().getStart_breakable_walls())
			this.infoMurs[i++] = a;
		
		this.agents.clear();
		this.bombes.clear();
		this.infoAgents.clear();
		this.infoItems.clear();
		
		for(InfoAgent agent : this.getInputMap().getStart_agents()) {
			this.agents.add(this.usineOfAgent.createAgent(agent.getType(), agent.getColor(), agent.getX(), agent.getY()));
		}
//		System.out.println(this.usineOfAgent);		// VOUS POUVEZ DECOMMENTER CE CODE POUR VOUS ASSURER QUE LA CLASSE HASHTABLE GERE BIEN LE CAS DES DOUBLONS ET EVITE DE LES INSERER
	}

	@Override
	public void takeTurn() {
		String msg = "Tour " + super.getTurn() + " du jeu en cours";
		System.out.println(msg);
		
		Random hasard= new Random(System.currentTimeMillis());
		this.infoAgents.clear();		// ON VA METTRE À JOUR LES INFORMATIONS SUR LES AGENTS
		
		for(Agent agent : this.agents) {
			AgentAction action = agent.chooseStrategie();
			
			if(hasard.nextInt(5) == 0 && agent.getType() == 'B') {
				this.putBomb(agent.getX(), agent.getY());	
			}
			this.moveAgent(agent, action);
						
			InfoAgent infoAgent = new InfoAgent(agent.getX(), agent.getY(), action, agent.getType(), agent.getColor(), agent.isInvincible(), agent.isSick());
			this.infoAgents.add(infoAgent);				
		}
		
		for(InfoBomb b : this.bombes)
			b.setStateBomb(StateBomb.Step1);
	}

	@Override
	public boolean gameContinue() {
		return true;
	}

	@Override
	public void gameOver() {
		String msg = "Oooh Oooooohhh! Vous avez échoué. \nPartie Terminé (^_^)";
		System.out.println(msg);
		this.notifyObserver();
	}

	/*---		GETTERS AND SETTERS		---*/
	public AgentUsine getUsineOfAgent() {
		return usineOfAgent;
	}

	public ArrayList<InfoBomb> getBombes() {
		return bombes;
	}

	public void setBombes(ArrayList<InfoBomb> bombes) {
		this.bombes = bombes;
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
