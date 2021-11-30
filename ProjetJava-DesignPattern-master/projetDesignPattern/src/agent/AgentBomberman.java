package agent;

import java.util.ArrayList;

import utils.AgentAction;
import utils.ColorAgent;

public class AgentBomberman extends Agent{
	private ColorAgent color;
	
	/*---	CONSTRUCTEUR	---*/
	public AgentBomberman(int x, int y, ColorAgent colorAgent) {
		super(x, y);
		this.setColor(colorAgent);
	}

	public void move(AgentAction action, ArrayList<Agent> agents) {
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

}
