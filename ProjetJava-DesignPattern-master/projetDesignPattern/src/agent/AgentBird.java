package agent;

import java.util.ArrayList;

import model.StopStrategie;
import utils.ColorAgent;

public class AgentBird extends Agent{
	private int rayonAction;
	
	/*---	CONSTRUCTEUR	---*/
	public AgentBird(int x, int y) {
		super(x, y);
		this.setCanFly(true);
		this.setMoveStrategie(new StopStrategie());
		this.rayonAction = 5;	// RAYON D'ACTION DE L'AGENT BIRD 5 MÈTRES
		this.setSleep(true);
	}


	public boolean agentBirdRadar(ArrayList<Agent> agents) {
		for(Agent agent: agents) {
			if(agent.getType() == 'B' && agent.getX() >= this.getX() - this.rayonAction && agent.getX() <= this.getX() + this.rayonAction && agent.getY() <= this.getY() + this.rayonAction && agent.getY() >= this.getY() - this.rayonAction) {
				return true;
			}
		}
		return false;
	}	// PERMET DE REPERER UN AGENT BOMBERMAN DANS UN RAYON DE rayonAction MÈTRE DE LUI EN FONCTION DE LA LISTE DES AGENTS SUR LE TERRAIN DE JEU
	
	@Override
	public char getType() {
		return 'V';
	}
	
	@Override
	public ColorAgent getColor() {
		return null;
	}

	public int getRayonAction() {
		return rayonAction;
	}

	public void setRayonAction(int rayonAction) {
		this.rayonAction = rayonAction;
	}
}
