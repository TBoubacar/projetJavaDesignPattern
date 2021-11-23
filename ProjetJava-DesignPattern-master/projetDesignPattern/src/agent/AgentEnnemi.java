package agent;

import utils.ColorAgent;

public class AgentEnnemi extends Agent{

	public AgentEnnemi(int x, int y) {
		super(x, y);
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
