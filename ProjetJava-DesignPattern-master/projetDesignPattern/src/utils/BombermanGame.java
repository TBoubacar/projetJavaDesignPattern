package utils;

import java.util.ArrayList;

import model.Agent;
import model.AgentUsine;
import model.InputMap;

public class BombermanGame extends Game {
	private AgentUsine usineOfAgent;	// CECI CORRESPOND À UNE USINE D'AGENTS PERMETTANT DE CRÉER DIFFÉRENT TYPE D'AGENT (ON NE POSSÈDE QUE 4 TYPES POUR LE MOMENT)
	private ArrayList<Agent> agents;	// CECI CORRESPOND À L'ENSEMBLE DES AGENTS CRÉÉS DANS NOTRE USINE

	public BombermanGame(int tourMax, String filename) {
		super(tourMax);
		this.usineOfAgent = AgentUsine.getInstance();
		
		try {
			this.setInputMap(new InputMap(filename));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	public boolean deplacement(Agent agent) {
		if(!this.getInputMap().get_walls()[agent.getX()+1][agent.getY()+1]) {
			agent.setX(agent.getX()+1);
			agent.setY(agent.getY()+1);
			return true;
		} else return false;
	}
	
	//	-----------CODE A IMPLEMENTER----------SEANCE 3.4
	public boolean isLegalMove(Agent agent, AgentAction agentAction) {
		if(this.getInputMap().get_walls()[agent.getY()][agent.getY()])
			return true;
		return false;
	}

	@Override
	public void initializeGame() {
		this.agents = new ArrayList<Agent>();
		for(InfoAgent agent : this.getInputMap().getStart_agents()) {
			this.agents.add(this.usineOfAgent.createAgent(agent.getType(), agent.getColor(), agent.getX(), agent.getY()));
		}
//		System.out.println(this.usineOfAgent);		// VOUS POUVEZ DECOMMENTER CE CODE POUR VOUS ASSURER QUE LA CLASSE HASHTABLE GERE BIEN LE CAS DES DOUBLONS ET EVITE DE LES INSERER
	}

	@Override
	public void takeTurn() {
		String msg = "Tour " + super.getTurn() + " du jeu en cours";
		System.out.println(msg);
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
	public ArrayList<Agent> getAgents() {
		return agents;
	}

	public void setAgents(ArrayList<Agent> agents) {
		this.agents = agents;
	}

	public AgentUsine getUsineOfAgent() {
		return usineOfAgent;
	}
}
