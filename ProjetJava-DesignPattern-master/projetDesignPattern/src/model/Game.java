package model;

import java.util.ArrayList;

import agent.Agent;

public abstract class Game implements Runnable, Observable {
	private int turn;
	private int maxturn;
	private boolean isRunning;
	private Thread thread;
	private long time;
	private InputMap inputMap;
	private ArrayList<Observer> observateurs;
	private int mode;			// MODE 1 = LES BOMBERMANS NE SONT PAS ALLIÉS (NON COOPERATIF) | MODE 2 = LES BOMBERMANS SONT ALLIÉS (COOPERATIF)

	/*---	CONSTRUCTEUR	---*/
	public Game (int tourMax) {
		setObservateurs(new ArrayList<Observer>());
		this.setTurn(0);
		this.maxturn = tourMax;
		this.isRunning = false;
		this.time = 1000;		//Temps d'arrêt en millisecondes
		this.mode = 1;
	}
	
	/*--- 		ASTUCE POUR GERER LES PARTIES BONUS POUR LE BOMBERMAN GAME		---*/
	public boolean hasSurvivantAgentBomberman() {
		return false;
	}
	
	public boolean hasSurvivantAgentPNJ() {
		return false;
	}

	public boolean hasOneSurvivant() {
		return false;
	}
	
	public int nbAgentBombermanSurvivant() {
		return 0;
	}
	
	public int nbAgentPNGSurvivant() {
		return 0;
	}
	
	public Agent getSurvivant() {
		return null;
	}
	
		/*---	OBSERVABLE METHOHS		---*/
	@Override
	public void addObserver(Observer o) {
	observateurs.add(o);		
	}
	
	@Override
	public void deleteObserver(Observer o) {
	observateurs.remove(o);		
	}
	
	@Override
	public void notifyObserver() {
		for(Observer o : this.observateurs) {
			o.update(this.turn);
		}
	}
	
	/*---	METHODES CONCRETE	---*/	
	public void changeGameSpace(String filename) {
		try {
			this.inputMap = new InputMap(filename);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void init(){
		this.turn = 0;
		this.isRunning = false;
		this.initializeGame();
		this.notifyObserver();
	}
		
	public void step() {
		if (this.getTurn() < this.maxturn && this.gameContinue()) {
			this.setTurn(this.getTurn()+1);
			this.takeTurn();
		} else {
			this.isRunning = false;
			this.gameOver();
		}
	}

	public void run() {
		while (this.isRunning) {
			try {
				this.step();
				Thread.sleep(this.time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void pause() {
		this.isRunning = false;
		this.notifyObserver();
	}
	
	public void launch() {
		this.isRunning = true;
		this.thread = new Thread(this);
		this.thread.start();
	}
	
	/*---	METHODES ABSTRAITE	---*/
	public abstract void initializeGame();
	public abstract void takeTurn();
	public abstract boolean gameContinue();
	public abstract void gameOver();

		/*---		GETTERS AND SETTERS		---*/
	public ArrayList<Observer> getObservateurs() {
	return observateurs;
	}
	
	public void setObservateurs(ArrayList<Observer> observateurs) {
	this.observateurs = observateurs;
	}
	
	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
		this.notifyObserver();
	}

	public int getMaxturn() {
		return maxturn;
	}

	public void setMaxturn(int maxturn) {
		this.maxturn = maxturn;
		this.notifyObserver();
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
		this.notifyObserver();
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	public InputMap getInputMap() {
		return inputMap;
	}

	public void setInputMap(InputMap inputMap) {
		this.inputMap = inputMap;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
		this.notifyObserver();
	}
}
