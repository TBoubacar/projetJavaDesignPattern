package model;

import utils.AgentAction;
import utils.ColorAgent;

public abstract class Agent {
	private int x;
	private int y;
	private boolean canFly;
	private MoveStrategie moveStrategie;
	private boolean isInvincible;
	private boolean isSick;

	/*---	CONSTRUCTEUR	---*/
	public Agent(int x, int y) {
		this.moveStrategie = new AleatoireStrategie();
		this.setX(x);
		this.setY(y);
		this.canFly = false;
		this.isInvincible = false;
		this.isSick = false;
	}
	
	/*-----------	METHODES	----------*/
	public void move(AgentAction action) {
		switch (action) {
		case MOVE_DOWN:
			this.setY(this.getY() + 1);
			break;
		case MOVE_UP:
			this.setY(this.getY() - 1);
			break;
		case MOVE_LEFT:
			this.setX(this.getX() - 1);
			break;
		case MOVE_RIGHT:
			this.setX(this.getX() + 1);
			break;
		default:	//LE CAS OU ON EST STOPÉ
		}
	}

	public AgentAction chooseStrategie() {
		return this.moveStrategie.deplace();
	}
	
	public abstract char getType();
	public abstract ColorAgent getColor();

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

	public MoveStrategie getMoveStrategie() {
		return moveStrategie;
	}

	public void setMoveStrategie(MoveStrategie moveStrategie) {
		this.moveStrategie = moveStrategie;
	}

	public boolean isCanFly() {
		return canFly;
	}

	public void setCanFly(boolean canFly) {
		this.canFly = canFly;
	}

	public boolean isInvincible() {
		return isInvincible;
	}

	public void setInvincible(boolean isInvincible) {
		this.isInvincible = isInvincible;
	}

	public boolean isSick() {
		return isSick;
	}

	public void setSick(boolean isSick) {
		this.isSick = isSick;
	}
}
