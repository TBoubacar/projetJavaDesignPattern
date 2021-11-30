package agent;

import java.util.ArrayList;
import java.util.Iterator;

import model.AleatoireStrategie;
import model.Item;
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
	private int nbTourOfInvincible;
	private boolean isSick;
	private int nbTourOfSick;
	private ArrayList<InfoBomb> bombes;					// CECI CORRESPOND À L'ENSEMBLE DES INFORMATIONS DES BOMBES SUR NOTRE TERRAIN DE JEU POUR UN BOMBERMAN EN PARTICULIER
	private int BOMBE_RANGE = 2;						// RANGE DE LA BOMBE SPECIFIQUE À CHAQUE AGENT BOMBERMAN
	private final int NB_TOURS_OF_ITEM_EFFET = 20;		// LE NOMBRE DE TOUR AVANT QUE L'EFFET DE L'ITEM SE DESACTIVE (DANS LE CAS DES FIRE_UP ET FIRE_DOWN) CELA NE CHANGE RIEN
	private boolean sleep;

	/*---	CONSTRUCTEUR	---*/
	public Agent(int x, int y) {
		this.moveStrategie = new AleatoireStrategie();
		this.setX(x);
		this.setY(y);
		this.canFly = false;
		this.isInvincible = false;
		this.nbTourOfInvincible = 0;
		this.isSick = false;
		this.nbTourOfSick = 0;
		this.bombes = new ArrayList<InfoBomb>();
		this.sleep = false;
	}
	
	/*-----------	METHODES	----------*/
	public final void moving(AgentAction action, ArrayList<Agent> agents) {
		this.move(action, agents);
	}
	
	public abstract void move(AgentAction action, ArrayList<Agent> agents);
	
	public void deleteExplosedBombe() {
		Iterator<InfoBomb> iter = this.bombes.iterator();
		
		while(iter.hasNext()) {
			InfoBomb bombe = iter.next();
			if(bombe.getStateBomb() == StateBomb.Boom)
				iter.remove();
		}
	}

	public void applyItemEffet(Item item) {
		switch(item.getItemType()) {
		case FIRE_DOWN:
			if(this.getBOMBE_RANGE() > 1) {
				this.setBOMBE_RANGE(this.getBOMBE_RANGE()-1);
			}
			break;
		case FIRE_UP:
			this.setBOMBE_RANGE(this.getBOMBE_RANGE()+1);
			break;
		case FIRE_SUIT:
			this.setInvincible(true);
			break;
		default:
			this.setSick(true);
		}
	}
	
	public void applyEffetOfInvincibilityAndSick() {
		if (this.nbTourOfInvincible > 0) {
			this.nbTourOfInvincible--;
		}
		if(this.nbTourOfInvincible == 0) {
			this.setInvincible(false);
		} 
		if (this.nbTourOfSick > 0) {
			this.nbTourOfSick--;
		}
		if(this.nbTourOfSick == 0) {
			this.setSick(false);
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
		if(isInvincible) {
			this.nbTourOfInvincible += NB_TOURS_OF_ITEM_EFFET;
		}
		this.isInvincible = isInvincible;
	}

	public void setSick(boolean isSick) {
		if(isSick) {
			this.nbTourOfSick += NB_TOURS_OF_ITEM_EFFET;
		}
		this.isSick = isSick;
	}

	public int getNbTourOfInvincible() {
		return nbTourOfInvincible;
	}

	public void setNbTourOfInvincible(int nbTourOfInvincible) {
		this.nbTourOfInvincible = nbTourOfInvincible;
	}

	public int getNbTourOfSick() {
		return nbTourOfSick;
	}

	public void setNbTourOfSick(int nbTourOfSick) {
		this.nbTourOfSick = nbTourOfSick;
	}

	public boolean isSick() {
		return isSick;
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

	public void setBOMBE_RANGE(int bOMBE_RANGE) {
		BOMBE_RANGE = bOMBE_RANGE;
	}
	
	public boolean isSleep() {
		return sleep;
	}

	public void setSleep(boolean sleep) {
		this.sleep = sleep;
	}
}
