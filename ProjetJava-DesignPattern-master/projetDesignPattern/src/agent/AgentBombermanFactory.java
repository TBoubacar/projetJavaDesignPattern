package agent;

import utils.ColorAgent;

public class AgentBombermanFactory implements AgentFactory{
	private ColorAgent colorAgent;
	
	public AgentBombermanFactory(ColorAgent color) {
		this.setColorAgent(color);
	}

	@Override
	public Agent createAgent(int x, int y) {
		return new AgentBomberman(x, y, this.colorAgent);
	}

	/*---		GETTERS AND SETTERS		---*/
	public ColorAgent getColorAgent() {
		return colorAgent;
	}

	public void setColorAgent(ColorAgent colorAgent) {
		this.colorAgent = colorAgent;
	}

}
