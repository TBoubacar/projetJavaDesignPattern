package strategie;

import java.util.Random;

import utils.AgentAction;

public class PutBombAndRunToBottomOrRightStrategie implements MoveStrategie{

	@Override
	public AgentAction deplace() {
		Random hasard= new Random();
		switch(hasard.nextInt(4)) {
		case 0: return AgentAction.MOVE_DOWN;
		case 1: return AgentAction.MOVE_RIGHT;
		default: return AgentAction.PUT_BOMB;
		}
	}
}
