package model;

import java.util.Hashtable;

import utils.ColorAgent;

public class AgentUsine {
	private Hashtable<Character, AgentFactory> factories;
	private char type;
	private ColorAgent color;	// SEUL LES AGENTS BOMBERMAN SERONT EN COULEUR MALGRÉ TOUT
	
	public AgentUsine(char type, ColorAgent color) {
		this.factories = new Hashtable<Character, AgentFactory>();
		this.setColor(color);
		this.setType(type);
		
		switch (type) {
		case 'B':
			this.factories.put(type, new AgentBombermanFactory(this.color));
			break;
		case 'R':
			this.factories.put(type, new AgentRajionFactory());
			break;
		case 'V':
			this.factories.put(type, new AgentBirdFactory());
			break;
		default:
			this.factories.put(type, new AgentEnnemiFactory());
			break;
		}	
	}
	
	public Agent createAgent(int x, int y) {
		return this.factories.get(this.type).createAgent(x, y);
	}

	/*---		GETTERS AND SETTERS		---*/
	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public ColorAgent getColor() {
		return color;
	}

	public void setColor(ColorAgent color) {
		this.color = color;
	}

}
