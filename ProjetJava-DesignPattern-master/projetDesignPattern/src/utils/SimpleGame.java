package utils;

public class SimpleGame extends Game implements Observable {

	public SimpleGame(int tourMax) {
		super(tourMax);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeGame() {
		// TODO Auto-generated method stub
	}

	@Override
	public void takeTurn() {
		String msg = "Tour " + super.getTurn() + " du jeu en cours";
		System.out.println(msg);
//		super.notifyObserver();		//ON NOTIFIE QU'IL Y A EU UN CHANGEMENT
	}

	@Override
	public boolean gameContinue() {
		return true;
	}

	@Override
	public void gameOver() {
		String msg = "Oooh Oooooohhh! Vous avez échoué. \nPartie Terminé (^_^)";
		System.out.println(msg);
	}

}
