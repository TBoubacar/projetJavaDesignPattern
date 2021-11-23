package agent;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import utils.ColorAgent;

public class AgentUsine {
	//	ON UTILISE LE PRINCIPE DU DESIGN PATTERN SINGLETON ICI POUR S'ASSURER QU'IL N'EXISTE QU'UNE SEULE USINE DE CREATION D'AGENTS DANS NOTRE JEU
	private static AgentUsine uniqueInstance;
	private Hashtable<Character, AgentFactory> factories;
	
	/*---		CONSTRUCTEUR EN PRIVATE POUR LA GESTION DU DESIGN PATTERN SINGLETON		---*/
	private AgentUsine() {
		this.factories = new Hashtable<Character, AgentFactory>();
	}
	
	/*---	CONSTRUCTEUR EN PUBLIC POUR LA CREATION D'UNE UNIQUE INSTANCE DE LA CLASSE	---*/
	public static synchronized AgentUsine getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new AgentUsine();
		}
		return uniqueInstance;
	}
	
	public String toString() {
		String tmp ="";
		for(Map.Entry<Character, AgentFactory> agent : factories.entrySet()) {
			tmp += agent.getValue().getClass().getName().toString() + "\n";
		}
		return tmp;
	}
	
	public ArrayList<Character> getTypes() {
		ArrayList<Character> cles = new ArrayList<Character>();
		for(Map.Entry<Character, AgentFactory> agent: factories.entrySet())
			cles.add(agent.getKey());
		return cles;
	}
	
	public Agent createAgent(char type, ColorAgent color, int x, int y) {
		// IL N'Y A PAS D'INQUIÉTUDE À CE FAIRE POUR LES DOUBLONS, CAR UN HASTABLE PERMET DE 
		// REPÉRER LES DOUBLONS ET DONC NE L'INSERT PAS EN TANT QUE NOUVELLE DONNÉE.
		if(!this.getTypes().contains(type)) {
			switch (type) {
			case 'B':	// SEUL LES AGENTS BOMBERMAN SERONT EN COULEUR MALGRÉ TOUT
				this.factories.put(type, new AgentBombermanFactory(color));
				break;
			case 'R':
				this.factories.put(type, new AgentRajionFactory());
				break;
			case 'V':
				this.factories.put(type, new AgentBirdFactory());
				break;
			default:
				this.factories.put(type, new AgentEnnemiFactory());
				break;
			}	
		}
		return this.factories.get(type).createAgent(x, y);
	}

	/*---		GETTERS AND SETTERS		---*/
	public Hashtable<Character, AgentFactory> getFactories() {
		return factories;
	}

	public void setFactories(Hashtable<Character, AgentFactory> factories) {
		this.factories = factories;
	}
}
