package model;

import utils.ColorAgent;

public class AgentBird extends Agent{
	
	/*---	CONSTRUCTEUR	---*/
	public AgentBird(int x, int y) {
		super(x, y);
		this.setCanFly(true);
	}

	@Override
	public char getType() {
		return 'V';
	}
	
	@Override
	public ColorAgent getColor() {
		return null;
	}
}
