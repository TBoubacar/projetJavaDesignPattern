package etat;

import utils.InfoBomb;
import utils.StateBomb;

public class EtatBombeStep0 implements EtatBombe{
	private InfoBomb bombe;

	public EtatBombeStep0(InfoBomb bombe) {
		this.bombe = bombe;
	}
	
	@Override
	public void explose() {
		this.bombe.setStateBomb(StateBomb.Step1);
		this.bombe.setEtatBombe(new EtatBombeStep1(this.bombe));
	}

}
