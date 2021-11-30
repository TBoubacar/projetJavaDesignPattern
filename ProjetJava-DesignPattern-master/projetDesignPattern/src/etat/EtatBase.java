package etat;

import view.ViewCommand;

public class EtatBase implements EtatButtonCommande{
	private ViewCommand viewCommand;
	
	public EtatBase(ViewCommand viewCommand) {
		this.viewCommand = viewCommand;
		
		this.viewCommand.getjButtonRestart().setEnabled(false);
		this.viewCommand.getjButtonStart().setEnabled(true);
		this.viewCommand.getjButtonPlay().setEnabled(true);
		this.viewCommand.getjButtonPause().setEnabled(false);
		this.viewCommand.getjButtonChooseInterface().setEnabled(true);
		
	}

	@Override
	public void base() {
//		this.viewCommand.setEtat(new EtatBase(this.viewCommand));
//		this.viewCommand.getEtat().base();
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
		this.viewCommand.setEtat(new EtatStart(this.viewCommand));
		this.viewCommand.getEtat().start();
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
