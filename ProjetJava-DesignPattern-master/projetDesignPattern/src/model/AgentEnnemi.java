package model;

public class AgentEnnemi extends Agent{

	public AgentEnnemi(int x, int y) {
		super(x, y);
	}

	@Override
	public void executeAction() {
		System.out.println("Je suis un Ennemi");		
	}

}
