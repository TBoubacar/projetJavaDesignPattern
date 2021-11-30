package etat;

import view.ViewCommand;

public class EtatStart implements EtatButtonCommande {
	private ViewCommand viewCommand;

	public EtatStart (ViewCommand viewCommand) {
		this.viewCommand = viewCommand;

		this.viewCommand.getjButtonRestart().setEnabled(true);
		this.viewCommand.getjButtonStart().setEnabled(false);
		this.viewCommand.getjButtonPlay().setEnabled(false);
		this.viewCommand.getjButtonPause().setEnabled(true);
		this.viewCommand.getjButtonChooseInterface().setEnabled(false);
	}
	
	@Override
	public void base() {
		this.viewCommand.setEtat(new EtatBase(this.viewCommand));
		this.viewCommand.getEtat().base();
	}
	
	@Override
	public void fin() {
		this.viewCommand.setEtat(new EtatFin(this.viewCommand));
		this.viewCommand.getEtat().fin();
	}
	
	@Override
	public void restart() {
		this.viewCommand.setEtat(new EtatRestart(this.viewCommand));
		this.viewCommand.getEtat().restart();
	}
	
	@Override
	public void start() {
		this.viewCommand.getController().play();
	}
	
	@Override
	public void play() {
		this.viewCommand.setEtat(new EtatPlay(this.viewCommand));
		this.viewCommand.getEtat().play();
	}
	
	@Override
	public void pause() {
		this.viewCommand.setEtat(new EtatPause(this.viewCommand));
		this.viewCommand.getEtat().pause();
	}
}