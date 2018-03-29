package Server;
import java.util.ArrayList;
import java.util.Collections;

public class Game {
	ArrayList<Player> players;
	ArrayList<City> cities;
	ArrayList<Disease> diseases;
	ArrayList<ResearchStation> research;
	ArrayList<PlayerCard> playerDeck;
	ArrayList<PlayerCard> playerDiscardPile;
	ArrayList<InfectionCard> infectionDeck;
	ArrayList<InfectionCard> infectionDiscardPile;
	int turn;
	Stage stage;
	static int[] infectionRate = new int[] {2,2,2,3,3,4,4};
	int infectionCount;
	int outbreakCount;
	
	//initializes game, initializes alot of things.
	Game(){
		turn = 0;
		stage = Stage.PreGame;
		infectionCount = 0;
		outbreakCount = 0;
		players = new ArrayList<Player>();
		cities = new ArrayList<City>();
		diseases = new ArrayList<Disease>();
		diseases.add(new Disease("black"));
		diseases.add(new Disease("blue"));
		diseases.add(new Disease("red"));
		diseases.add(new Disease("yellow"));
		for(String[] s:Vars.names) {
			if(s[1].matches("black")) {
				cities.add(new City(s[0],diseases.get(0),this));
			}
			else if(s[1].matches("blue")) {
				cities.add(new City(s[0],diseases.get(1),this));
			}
			else if(s[1].matches("red")) {
				cities.add(new City(s[0],diseases.get(2),this));
			}
			else if(s[1].matches("yellow")) {
				cities.add(new City(s[0],diseases.get(3),this));
			}
		}
		for(int i = 0; i < cities.size(); i++) {
			for(int j = 2; j < Vars.names[i].length; j++) {
				cities.get(i).connected.add(getCity(Vars.names[i][j]));
			}
		}
		
		research = new ArrayList<ResearchStation>();
		for(int i = 0; i < 6; i++) {
			research.add(new ResearchStation());
		}
		getCity("Atlanta").addResearchStation();
		
		playerDeck = new ArrayList<PlayerCard>();
		playerDiscardPile = new ArrayList<PlayerCard>();
		infectionDeck = new ArrayList<InfectionCard>();
		infectionDiscardPile = new ArrayList<InfectionCard>();
		for(int i = 0; i < 48; i++) {
			playerDeck.add(new PlayerCard(cities.get(i)));
			infectionDeck.add(new InfectionCard(cities.get(i)));
		}
		Collections.shuffle(playerDeck);
		int epi = 4;
		for(int i = 0; i < epi; i++) {
			playerDeck.add(new PlayerCard(null));
		}
		Collections.shuffle(infectionDeck);
	}
	
	Boolean allReady() {
		Boolean y = true;
		for(Player p: players) {
			if(!p.ready) {
				y = false;
			}
		}
		return y;
	}
	
	//return the current player
	Player getCurrentPlayer() {
		return players.get(turn);
	}
	
	//return a player with the specified name in the game
	Player getPlayer(String s) {
		int i = 0;
		Boolean found = false;
		while(i < players.size() && !found) {
			if(players.get(i).username.matches(s)) {
				found = true;
			}
			else{
				i++;
			}
		}
		if(found) {
			return players.get(i);
		}
		else {
			return null;
		}
	}
	
	Player getPlayer(int i) {
		return players.get(i);
	}
	
	//return a city with the specified name in the game
	City getCity(String s){
		int i = 0;
		Boolean found = false;
		while(i < cities.size() && !found) {
			if(cities.get(i).name.matches(s)) {
				found = true;
			}
			else{
				i++;
			}
		}
		if(found) {
			return cities.get(i);
		}
		else {
			return null;
		}
	}
	
	//return the disease with the specified color
	Disease getDisease(Color color) {
		for(Disease d:diseases) {
			if(d.color.equals(color)) {
				return d;
			}
		}
		return null;
	}
	
	//helper function that is used to draw count number of cards for player p. Epidemic included
	void drawCard(Player p, int count) {
		for(int i = 0; i < count; i++) {
			PlayerCard t1 = playerDeck.remove(0);
			if(t1.type.equals(Type.Epidemic)) {
				System.out.println("Player " + p.username + " drew an EPIDEMIC card!");
				
				infectionCount++;
				
				for(int j = 0; j < players.size(); j++) {
					ServerComm.sendMessage("IncInfectionRate/", j);
				}
				
				
				InfectionCard t2 = infectionDeck.remove(infectionDeck.size()-1);
				int c = t2.city.countDiseaseCube(t2.city.disease.color);
				if(c == 0) {
					infectCity(t2, 3);
				}
				else {
					infectCity(t2, 4-c);
				}
				Collections.shuffle(infectionDiscardPile);
				for(int j = 0; j < infectionDiscardPile.size(); j++) {
					InfectionCard d = infectionDiscardPile.remove(0);
					infectionDeck.add(0,d);
				}
				
				for(int j = 0; j < players.size(); j++) {
					ServerComm.sendMessage("ClearInfectionDiscard/", j);
				}
			}
			else {
				System.out.println("Player " + p.username + " drew " + t1.city.name + ".");
				
				p.hand.add(t1);
				
				String mes = "AddCardToHand/" + p.username + "/" + t1.city.name + "/true/";
				for(int j = 0; j < players.size(); j++) {
					ServerComm.sendMessage(mes, j);
				}
			}
		}
	}
	
	//helper function that is used to infect city of c with count number of disease cubes. Outbreak included
	void infectCity(InfectionCard c, int count) {
		Color color = c.city.disease.color;
		for(int i = 0; i < count; i++) {
			if(c.city.countDiseaseCube(color) != 3) {
				c.city.addDiseaseCube(color);
				
				System.out.println("Infected " + c.city.name + " with a " + color.toString() + " disease cube!");
				
				String mes = "AddDiseaseCubeToCity/" + c.city.name + "/" + color.toString() + "/";	
				for(int j = 0; j < players.size(); j++) {
					ServerComm.sendMessage(mes, j);
				}
			}
			else {
				System.out.println("OUTBREAK IN " + c + "!");
				
				String mes2 = "IncOutbreakCounter/";	
				for(int j = 0; j < players.size(); j++) {
					ServerComm.sendMessage(mes2, j);
				}
				
				ArrayList<City> outbreakList = new ArrayList<City>();
				outbreakList.add(c.city);
				c.city.outbroken = true;
				while(!outbreakList.isEmpty()) {
					outbreakCount++;
					for(City link: outbreakList.get(0).connected) {
						if(!link.outbroken) {
							if(link.countDiseaseCube(color) != 3) {
								System.out.println("Infected " + link.name + " with a " + color.toString() + " disease cube!");
								
								link.addDiseaseCube(color);
								
								String mes = "AddDiseaseCubeToCity/" + link.name + "/" + color.toString() + "/";	
								for(int j = 0; j < players.size(); j++) {
									ServerComm.sendMessage(mes, j);
								}
							}
							else {
								System.out.println("CHAIN OUTBREAK IN " + link + "!");
								
								String mes = "IncOutbreakCounter/";	
								for(int j = 0; j < players.size(); j++) {
									ServerComm.sendMessage(mes, j);
								}
								
								outbreakList.add(link);
								link.outbroken = true;
							}
						}
					}
					outbreakList.remove(0);
				}
				for(City cit: cities) {
					cit.outbroken = false;
				}
			}
		}
		infectionDiscardPile.add(c);
		
		String mes = "AddInfectionCardToDiscard/" + c.city.name + "/";	
		for(int i = 0; i < players.size(); i++) {
			ServerComm.sendMessage(mes, i);
		}
	}
}

enum Stage{
	PreGame,Action,Infection,BTAction,BTDraw,PostGame
}

class Vars{
	static String[][] names = new String[][]
	{
		new String[] {"Atlanta","blue","Chicago","Washington","Miami"},	
		new String[] {"Chicago","blue","San Francisco","Los Angeles","Atlanta","Toronto"},
		new String[] {"Essen","blue","London","St. Petersburg","Milan","Paris"},
		new String[] {"London","blue","New York","Essen","Madrid","Paris"},
		new String[] {"Madrid","blue","New York","Sao Paolo","Algiers","London","Paris"},
		new String[] {"Milan","blue","Essen","St. Petersburg","Istanbul","Paris"},
		new String[] {"New York","blue","Toronto","Washington","Madrid","London"},
		new String[] {"Paris","blue","London","Essen","Milan","Algiers","Madrid"},
		new String[] {"San Francisco","blue","Chicago","Los Angeles", "Manila", "Tokyo"},
		new String[] {"St. Petersburg","blue","Essen","Moscow","Istanbul","Milan"},
		new String[] {"Toronto","blue","Chicago","New York","Washington"},
		new String[] {"Washington","blue","New York","Toronto","Atlanta","Miami"},
		new String[] {"Bogota","yellow","Miami","Mexico City","Lima","Buenos Aires","Sao Paolo"},
		new String[] {"Buenos Aires","yellow","Sao Paolo","Bogota"},
		new String[] {"Johannesburg","yellow","Kinshasa","Khartoum"},
		new String[] {"Khartoum","yellow","Johannesburg","Kinshasa","Lagos","Cairo"},
		new String[] {"Kinshasa","yellow","Lagos","Khartoum","Johannesburg"},
		new String[] {"Lagos","yellow","Sao Paolo","Kinshasa","Khartoum"},
		new String[] {"Lima","yellow","Mexico City","Bogota","Santiago"},
		new String[] {"Los Angeles","yellow","San Francisco","Chicago","Mexico City", "Sydney"},
		new String[] {"Mexico City","yellow","Los Angeles","Chicago","Miami","Bogota","Lima"},
		new String[] {"Miami","yellow","Bogota","Mexico City","Atlanta","Washington"},
		new String[] {"Santiago","yellow","Lima"},
		new String[] {"Sao Paolo","yellow","Buenos Aires","Bogota","Madrid","Lagos"},
		new String[] {"Algiers","black","Madrid","Paris","Istanbul","Cairo"},
		new String[] {"Istanbul","black","Milan","Moscow","Baghdad","Cairo","Algiers","St. Petersburg"},
		new String[] {"Cairo","black","Algiers","Istanbul","Baghdad","Riyadh","Khartoum"},
		new String[] {"Moscow","black","St. Petersburg","Tehran","Istanbul"},
		new String[] {"Tehran","black","Moscow","Delhi","Baghdad","Karachi"},
		new String[] {"Baghdad","black","Istanbul","Tehran","Karachi","Riyadh","Cairo"},
		new String[] {"Riyadh","black","Karachi","Baghdad","Cairo"},
		new String[] {"Karachi","black","Mumbai","Riyadh","Baghdad","Tehran","Delhi"},
		new String[] {"Mumbai","black","Karachi","Delhi","Chennai"},
		new String[] {"Delhi","black","Tehran","Karachi","Mumbai","Kolkata","Chennai"},
		new String[] {"Kolkata","black","Delhi","Chennai","Bangkok","Hong Kong"},
		new String[] {"Chennai","black","Mumbai","Delhi","Kolkata","Bangkok","Jakarta"},
		new String[] {"Beijing","red","Shanghai","Seoul"},
		new String[] {"Seoul","red","Beijing","Shanghai","Tokyo"},
		new String[] {"Shanghai","red","Beijing","Seoul","Tokyo","Taipei","Hong Kong"},
		new String[] {"Tokyo","red","Seoul","Shanghai","Osaka", "San Francisco"},
		new String[] {"Osaka","red","Tokyo","Taipei"},
		new String[] {"Taipei","red","Osaka","Shanghai","Hong Kong","Manila"},
		new String[] {"Hong Kong","red","Kolkata","Bangkok","Shanghai","Taipei","Ho Chi Minh City"},
		new String[] {"Bangkok","red","Kolkata","Hong Kong","Chennai","Ho Chi Minh City","Jakarta"},
		new String[] {"Ho Chi Minh City","red","Jakarta","Bangkok","Hong Kong","Manila"},
		new String[] {"Jakarta","red","Chennai","Bangkok","Ho Chi Minh City","Sydney"},
		new String[] {"Manila","red","Sydney","Ho Chi Minh City","Hong Kong","Taipei", "San Francisco"},
		new String[] {"Sydney","red","Jakarta","Manila", "Los Angeles" }
	};
}


