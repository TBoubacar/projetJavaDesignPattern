package model;

public class AgentBird extends Agent{
	
	/*---	CONSTRUCTEUR	---*/
	public AgentBird(int x, int y) {
		super(x, y);
	}

	@Override
	public void executeAction() {
		System.out.println("Je suis un Bird !");		
	}

}
