package agent;

import java.util.ArrayList;

import model.AleatoireStrategie;
import model.StopStrategie;
import utils.AgentAction;
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

	public void move(AgentAction action, ArrayList<Agent> agents) {
		if(this.isSleep()) {
			if(this.agentBirdRadar(agents)) {
				System.out.println("Agent Bird (" + this.getX() +", " + this.getY() + ") a répéré un agent bomberman a {" + this.getRayonAction() + "} mètres de lui !");
				this.setSleep(false);
				this.setMoveStrategie(new AleatoireStrategie());
				this.birdMove(action);
			}	// L'AGENT BIRD NE BOUGE QUE S'IL REPÈRE UN BOMBERMAN DANS UN RAYON DE bird.getRayonAction() MÈTRES
		} else {
			this.birdMove(action);
		}
	}
	
	public void birdMove (AgentAction action) {
		switch (action) {
		case MOVE_DOWN:
			this.setY(this.getY() + 1);
			break;
		case MOVE_UP:
			this.setY(this.getY() - 1);
			break;
		case MOVE_LEFT:
			this.setX(this.getX() - 1);
			break;
		case MOVE_RIGHT:
			this.setX(this.getX() + 1);
			break;
		default:	//LE CAS OU ON EST STOPÉ
		}
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
