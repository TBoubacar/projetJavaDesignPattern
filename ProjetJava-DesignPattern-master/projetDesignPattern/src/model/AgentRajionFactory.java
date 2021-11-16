package model;

public class AgentRajionFactory implements AgentFactory{

	@Override
	public Agent createAgent(int x, int y) {
		return new AgentRajion(x, y);
	}

}
