package strategie;

import java.util.Random;

import utils.AgentAction;

public class RunToTopOrLeftStrategie implements MoveStrategie{

	@Override
	public AgentAction deplace() {
		Random hasard= new Random();
		switch(hasard.nextInt(2)) {
		case 0: return AgentAction.MOVE_UP;
		default: return AgentAction.MOVE_LEFT;
		}
	}
}
