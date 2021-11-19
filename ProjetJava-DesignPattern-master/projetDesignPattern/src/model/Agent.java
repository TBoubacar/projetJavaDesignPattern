package model;

import utils.AgentAction;

public abstract class Agent {
	private int x;
	private int y;
	
	/*---	CONSTRUCTEUR	---*/
	public Agent(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	
	//	-----------CODE A IMPLEMENTER----------SEANCE 3.4
	public void deplaceTo(AgentAction action) {
		//	CODE A IMPLEMENTER
		switch (action) {
		case MOVE_UP:

			break;
		case MOVE_DOWN:

			break;
		case MOVE_LEFT:

			break;
		case MOVE_RIGHT:

			break;
		case PUT_BOMB:

			break;
		default:
			break;
		}
	}
	//	-----------CODE A IMPLEMENTER----------SEANCE 3.4
	public abstract void executeAction();

	/*---	GETTER AND SETTER	---*/
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
