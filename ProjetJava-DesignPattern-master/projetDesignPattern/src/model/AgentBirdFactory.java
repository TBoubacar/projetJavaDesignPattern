package model;

public class AgentBirdFactory implements AgentFactory {

	@Override
	public Agent createAgent(int x, int y) {
		return new AgentBird(x, y);
	}

}
