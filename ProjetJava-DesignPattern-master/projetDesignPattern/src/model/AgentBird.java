package model;

public class AgentBird extends Agent{
	
	/*---	CONSTRUCTEUR	---*/
	public AgentBird(int x, int y) {
		super(x, y);
		this.setCanFly(true);
	}

	@Override
	public void executeAction() {
		this.move(this.chooseStrategie());
	}

}
