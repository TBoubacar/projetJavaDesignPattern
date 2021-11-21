package model;

public class AgentRajion extends Agent{

	public AgentRajion(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeAction() {
		this.move(this.chooseStrategie());
	}
}
