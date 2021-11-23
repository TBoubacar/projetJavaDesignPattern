package etat;

import utils.InfoBomb;
import utils.StateBomb;

public class EtatBombeStep2 implements EtatBombe{
	private InfoBomb bombe;
	
	public EtatBombeStep2(InfoBomb bombe) {
		this.bombe = bombe;
	}
	
	@Override
	public void explose() {
		this.bombe.setStateBomb(StateBomb.Step3);
		this.bombe.setEtatBombe(new EtatBombeStep3(this.bombe));
	}

}
