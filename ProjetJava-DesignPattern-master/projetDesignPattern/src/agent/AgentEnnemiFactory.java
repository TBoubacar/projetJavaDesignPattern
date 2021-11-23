package agent;

public class AgentEnnemiFactory implements AgentFactory {

	@Override
	public Agent createAgent(int x, int y) {
		return new AgentEnnemi(x, y);
	}

}
