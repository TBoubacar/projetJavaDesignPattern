package utils;

import java.util.ArrayList;

import model.Agent;
import model.AgentUsine;
import model.InputMap;

public class BombermanGame extends Game {
	private ArrayList<Agent> agents;
	
	public BombermanGame(int tourMax, String filename) {
		super(tourMax);
		try {
			this.setInputMap(new InputMap(filename));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initializeGame() {
		this.agents = new ArrayList<Agent>();
		for(InfoAgent agent : this.getInputMap().getStart_agents()) {
			AgentUsine agentUsine = new AgentUsine(agent.getType(), agent.getColor());
			this.agents.add(agentUsine.createAgent(agent.getX(), agent.getY()));
		}
		
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

}
