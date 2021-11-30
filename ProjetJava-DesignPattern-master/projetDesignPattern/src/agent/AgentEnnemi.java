package agent;

import java.util.ArrayList;

import utils.AgentAction;
import utils.ColorAgent;

public class AgentEnnemi extends Agent{

	public AgentEnnemi(int x, int y) {
		super(x, y);
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
		return 'E';
	}
	
	@Override
	public ColorAgent getColor() {
		return null;
	}
}
