package etat;

import utils.InfoBomb;

public class EtatBombeFinalStep implements EtatBombe{
	@SuppressWarnings("unused")
	private InfoBomb bombe;
	
	public EtatBombeFinalStep(InfoBomb bombe) {
		this.bombe = bombe;
	}
	
	@Override
	public void explose() {
		//	ON NE FAIT PLUS RIEN, ON SE CHARGE D'EFFACER LA BOMBE SUR LE TERRAIN DE JEU TOUT SIMPLEMENT ET DANS LA LISTE DE BOMBES DE L'AGENT
		//	SI LA BOMBE EST DANS L'ETAT "StateBomb.Boom".
		//	ARRIVÉ À CE NIVEAU, IL EST SURE ET CERTAIN QUE LA BOMBE A DÉJÀ EXPLOSÉ
	}
}
