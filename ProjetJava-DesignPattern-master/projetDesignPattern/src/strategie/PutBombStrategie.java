package strategie;

import utils.AgentAction;

public class PutBombStrategie implements MoveStrategie {

	@Override
	public AgentAction deplace() {
		return AgentAction.PUT_BOMB;
	}

}
