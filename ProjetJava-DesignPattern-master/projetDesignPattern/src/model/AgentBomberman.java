package model;

import utils.ColorAgent;

public class AgentBomberman extends Agent{
	private ColorAgent colorAgent;
	
	/*---	CONSTRUCTEUR	---*/
	public AgentBomberman(int x, int y, ColorAgent colorAgent) {
		super(x, y);
		this.setColorAgent(colorAgent);
	}

	public ColorAgent getColorAgent() {
		return colorAgent;
	}

	public void setColorAgent(ColorAgent colorAgent) {
		this.colorAgent = colorAgent;
	}

	@Override
	public void executeAction() {
		System.out.println("Je suis un Bomberman !");		
	}

}
