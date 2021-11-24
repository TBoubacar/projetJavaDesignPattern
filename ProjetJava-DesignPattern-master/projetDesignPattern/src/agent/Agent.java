package agent;

import java.util.ArrayList;
import java.util.Iterator;

import model.AleatoireStrategie;
import model.MoveStrategie;
import utils.AgentAction;
import utils.ColorAgent;
import utils.InfoBomb;
import utils.StateBomb;

public abstract class Agent {
	private int x;
	private int y;
	private boolean canFly;
	private MoveStrategie moveStrategie;
	private boolean isInvincible;
	private boolean isSick;
	private ArrayList<InfoBomb> bombes;			// CECI CORRESPOND À L'ENSEMBLE DES INFORMATIONS DES BOMBES SUR NOTRE TERRAIN DE JEU POUR UN BOMBERMAN EN PARTICULIER
	private final int BOMBE_RANGE = 2;			// RANGE DE LA BOMBE SPECIFIQUE À CHAQUE AGENT BOMBERMAN
	
	/*---	CONSTRUCTEUR	---*/
	public Agent(int x, int y) {
		this.moveStrategie = new AleatoireStrategie();
		this.setX(x);
		this.setY(y);
		this.canFly = false;
		this.isInvincible = false;
		this.isSick = false;
		this.bombes = new ArrayList<InfoBomb>();
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
	
	public void deleteExplosedBombe() {
		Iterator<InfoBomb> iter = this.bombes.iterator();
		
		while(iter.hasNext()) {
			InfoBomb bombe = iter.next();
			if(bombe.getStateBomb() == StateBomb.Boom)
				iter.remove();
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

	public ArrayList<InfoBomb> getBombes() {
		return bombes;
	}

	public void setBombes(ArrayList<InfoBomb> bombes) {
		this.bombes = bombes;
	}

	public int getBOMBE_RANGE() {
		return BOMBE_RANGE;
	}
}
