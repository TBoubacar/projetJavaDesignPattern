package utils;

import java.util.ArrayList;

import model.Agent;
import model.AgentUsine;
import model.InputMap;

public class BombermanGame extends Game {
	private AgentUsine usineOfAgent;			// CECI CORRESPOND À UNE USINE D'AGENTS PERMETTANT DE CRÉER DIFFÉRENT TYPE D'AGENT (ON NE POSSÈDE QUE 4 TYPES POUR LE MOMENT)
	private ArrayList<Agent> agentEnnemis;		// CECI CORRESPOND À L'ENSEMBLE DES AGENTS CRÉÉS DANS NOTRE USINE
	private ArrayList<Agent> agentBombermans;	// CECI CORRESPOND À L'ENSEMBLE DES AGENTS CRÉÉS DANS NOTRE USINE
	
	private ArrayList<InfoBomb> bombes;			// CECI CORRESPOND À L'ENSEMBLE DES INFORMATIONS SUR LES BOMBES SUR NOTRE TERRAIN DE JEU
	private ArrayList<InfoAgent> infoAgents;	// CECI CORRESPOND À L'ENSEMBLE DES INFORMATIONS SUR LES AGENTS SUR NOTRE TERRAIN DE JEU
	private boolean[][] infoMurs;				// CECI CORRESPOND À L'ENSEMBLE DES INFORMATIONS SUR LES MÛRS BRISABLE SUR NOTRE TERRAIN DE JEU
	
	public BombermanGame(int tourMax, String filename) {
		super(tourMax);
		this.usineOfAgent = AgentUsine.getInstance();
		this.agentEnnemis = new ArrayList<Agent>();
		this.agentBombermans = new ArrayList<Agent>();
		this.bombes = new ArrayList<InfoBomb>();

		try {
			this.setInputMap(new InputMap(filename));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.infoAgents = this.getInputMap().getStart_agents();
		this.infoMurs = this.getInputMap().getStart_breakable_walls();
		
		this.initializeGame();
	}
	
	//	-----------CODE A IMPLEMENTER----------SEANCE 3.4
	public boolean isLegalMove(Agent agent, AgentAction agentAction) {
		// LE DEPLACEMENT N'EST POSSIBLE QUE SI ON NE SE TROUVERA PAS À LA MÊME POSITION QU'UN MÛR QUELQUE SOIT SON TYPE (BRISABLE OU PAS)
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
	}
	
	public void moveAgent(Agent agent, AgentAction action) {
		if(isLegalMove(agent, action))
			agent.move(action);
	}

	public void putBomb(int xBomberman, int yBomberman) {
		InfoBomb bombe = new InfoBomb(xBomberman, yBomberman, 3, StateBomb.Step0);
		this.bombes.add(bombe);
	}
	
	@Override
	public void initializeGame() {
		for(InfoAgent agent : this.getInputMap().getStart_agents()) {
			if(agent.getType() == 'B') {
				this.agentBombermans.add(this.usineOfAgent.createAgent(agent.getType(), agent.getColor(), agent.getX(), agent.getY()));
			}
			else {
				this.agentEnnemis.add(this.usineOfAgent.createAgent(agent.getType(), agent.getColor(), agent.getX(), agent.getY()));		
			}
		}
//		System.out.println(this.usineOfAgent);		// VOUS POUVEZ DECOMMENTER CE CODE POUR VOUS ASSURER QUE LA CLASSE HASHTABLE GERE BIEN LE CAS DES DOUBLONS ET EVITE DE LES INSERER
	}

	@Override
	public void takeTurn() {
		String msg = "Tour " + super.getTurn() + " du jeu en cours";
		System.out.println(msg);
//		//---- DEBUTE ICI
//		for(Agent agent : this.agentBombermans) {
//			agent.executeAction();
//		}
//		for(Agent agent : this.agentEnnemis) {
//			agent.executeAction();
//		}
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

	public ArrayList<Agent> getAgentEnnemis() {
		return agentEnnemis;
	}

	public void setAgentEnnemis(ArrayList<Agent> agentEnnemis) {
		this.agentEnnemis = agentEnnemis;
	}

	public ArrayList<Agent> getAgentBombermans() {
		return agentBombermans;
	}

	public void setAgentBombermans(ArrayList<Agent> agentBombermans) {
		this.agentBombermans = agentBombermans;
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
}
