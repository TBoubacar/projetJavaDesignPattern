package etat;

import utils.InfoBomb;
import utils.StateBomb;

public class EtatBombeStep1 implements EtatBombe{
	private InfoBomb bombe;
	
	public EtatBombeStep1(InfoBomb bombe) {
		this.bombe = bombe;
	}
	
	@Override
	public void explose() {
		this.bombe.setStateBomb(StateBomb.Step2);
		this.bombe.setEtatBombe(new EtatBombeStep2(this.bombe));
	}

}
