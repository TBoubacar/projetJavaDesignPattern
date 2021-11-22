package model;

import utils.ColorAgent;

public class AgentBomberman extends Agent{
	private ColorAgent color;
	
	/*---	CONSTRUCTEUR	---*/
	public AgentBomberman(int x, int y, ColorAgent colorAgent) {
		super(x, y);
		this.setColor(colorAgent);
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
