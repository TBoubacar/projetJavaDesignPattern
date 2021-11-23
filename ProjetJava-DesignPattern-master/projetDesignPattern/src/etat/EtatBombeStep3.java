package etat;

import utils.InfoBomb;
import utils.StateBomb;

public class EtatBombeStep3 implements EtatBombe{
	private InfoBomb bombe;

	public EtatBombeStep3(InfoBomb bombe) {
		this.bombe = bombe;
	}
	
	@Override
	public void explose() {
		this.bombe.setStateBomb(StateBomb.Boom);
		this.bombe.setEtatBombe(new EtatBombeFinalStep(this.bombe));
	}

}
