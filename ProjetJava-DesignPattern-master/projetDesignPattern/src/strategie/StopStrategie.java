package strategie;

import utils.AgentAction;

public class StopStrategie implements MoveStrategie{

	@Override
	public AgentAction deplace() {
		return AgentAction.STOP;
	}

}
