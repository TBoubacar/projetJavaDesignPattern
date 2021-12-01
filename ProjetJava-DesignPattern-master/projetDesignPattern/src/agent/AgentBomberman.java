package agent;

import java.util.ArrayList;

import strategie.AleatoireStrategie;
import strategie.PutBombAndRunToBottomOrLeftStrategie;
import strategie.PutBombAndRunToBottomOrRightStrategie;
import strategie.PutBombAndRunToTopOrLeftStrategie;
import strategie.PutBombAndRunToTopOrRightStrategie;
import utils.AgentAction;
import utils.ColorAgent;

public class AgentBomberman extends Agent{
	private ColorAgent color;
	private int rayonAction;
	
	/*---	CONSTRUCTEUR	---*/
	public AgentBomberman(int x, int y, ColorAgent colorAgent) {
		super(x, y);
		this.setColor(colorAgent);
		this.setRayonAction(2);
	}

	public void move(AgentAction action, ArrayList<Agent> agents) {
		if (this.isInvincible()) {
			this.rayonAction = 5;	// ON REND LE BOMBERMAN HYPER AGRESSIF
		} 
		if (!this.isInvincible() && this.rayonAction != 2) {
			this.rayonAction = 2;
		}
		
		if(this.bombermanRadarRepere(agents)) {
			if(this.bombermanRadarRepereOnTopRight(agents)) {
				if(!this.isInvincible()) {
					this.setMoveStrategie(new PutBombAndRunToBottomOrLeftStrategie());
				} else {
					this.setMoveStrategie(new PutBombAndRunToTopOrRightStrategie());
				}
			} 
			else if (this.bombermanRadarRepereOnBottomLeft(agents)) {
				if(!this.isInvincible()) {
					this.setMoveStrategie(new PutBombAndRunToTopOrRightStrategie());
				} else {
					this.setMoveStrategie(new PutBombAndRunToBottomOrLeftStrategie());					
				}
			} 
			else if (this.bombermanRadarRepereOnBottomRight(agents)) {
				if(!this.isInvincible()) {
					this.setMoveStrategie(new PutBombAndRunToTopOrLeftStrategie());
				} else {
					this.setMoveStrategie(new PutBombAndRunToBottomOrRightStrategie());					
				}
			} 
			else {
				if(!this.isInvincible()) {
					this.setMoveStrategie(new PutBombAndRunToBottomOrRightStrategie());
				} else {
					this.setMoveStrategie(new PutBombAndRunToTopOrLeftStrategie());
				}
			}
		} else {
			this.setMoveStrategie(new AleatoireStrategie());			
		}
		this.BombermanMove(action);
	}
	
	public boolean bombermanRadarRepere(ArrayList<Agent> agents) {
		for(Agent agent : agents) {
			if(agent.getType() != 'B' && agent.getX() >= this.getX() - this.rayonAction && agent.getX() <= this.getX() + this.rayonAction && agent.getY() <= this.getY() + this.rayonAction && agent.getY() >= this.getY() - this.rayonAction)
				return true;
		} return false;
	}
	
	public boolean bombermanRadarRepereOnTopLeft(ArrayList<Agent> agents) {
		for(Agent agent : agents) {
			if(agent.getType() != 'B' && agent.getY() >= this.getY() - this.rayonAction && agent.getY() <= this.getY() && agent.getX() <= this.getX())
				return true;
		} return false;
	}
	
	public boolean bombermanRadarRepereOnTopRight(ArrayList<Agent> agents) {
		for(Agent agent : agents) {
			if(agent.getType() != 'B' && agent.getY() >= this.getY() - this.rayonAction && agent.getY() <= this.getY() && agent.getX() >= this.getX())
				return true;
		} return false;
	}
	
	public boolean bombermanRadarRepereOnBottomLeft(ArrayList<Agent> agents) {
		for(Agent agent : agents) {
			if(agent.getType() != 'B' && agent.getY() <= this.getY() + this.rayonAction && agent.getY() >= this.getY() && agent.getX() <= this.getX())
				return true;
		} return false;
	}
	
	public boolean bombermanRadarRepereOnBottomRight(ArrayList<Agent> agents) {
		for(Agent agent : agents) {
			if(agent.getType() != 'B' && agent.getY() <= this.getY() + this.rayonAction && agent.getY() >= this.getY() && agent.getX() >= this.getX())
				return true;
		} return false;
	}
	
	public void BombermanMove(AgentAction action) {
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
	
	@Override
	public char getType() {
		return 'B';
	}
	
	public void setColor(ColorAgent colorAgent) {
		this.color = colorAgent;
	}

	@Override
	public ColorAgent getColor() {
		return this.color;
	}

	public int getRayonAction() {
		return rayonAction;
	}

	public void setRayonAction(int rayonAction) {
		this.rayonAction = rayonAction;
	}

}
