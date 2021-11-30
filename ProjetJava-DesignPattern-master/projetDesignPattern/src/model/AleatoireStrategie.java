package model;

import java.util.Random;

import utils.AgentAction;

public class AleatoireStrategie implements MoveStrategie{

	@Override
	public AgentAction deplace() {
		Random hasard= new Random();
		switch(hasard.nextInt(5)) {
		case 0: return AgentAction.MOVE_UP;
		case 1: return AgentAction.MOVE_DOWN;
		case 2: return AgentAction.MOVE_LEFT;
		case 3: return AgentAction.MOVE_RIGHT;
		case 4: return AgentAction.STOP;
		default: return AgentAction.PUT_BOMB;
		}		
	}

}
