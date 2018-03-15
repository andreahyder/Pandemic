import java.util.ArrayList;
import java.util.Collections;

public class GameManager {
	//ArrayList<Game> games;
	Game game;
	ArrayList<Player> playerList;
	
	GameManager(){
		playerList = new ArrayList<Player>();
		game = new Game();
	}
	
	//when a player connects, but haven't joined a game yet
	//retired method
	/*void playerJoin(String name) {
		playerList.add(new Player(name));
	}*/
	
	//when a player joins the game
	void addPlayer(String name) {
		playerList.add(new Player(name));
		getPlayer(name).givePawn(game.getCity("Atlanta"));
		game.players.add(getPlayer(name));
	}
	
	//when a player is ready to start the game
	void toggleReady(String name) {
		game.getPlayer(name).ready = true;
	}
	
	//start the game, distribute cards, infect initial cities
	void startGame() {
		game.stage = Stage.Action;
		int numcard = 2;
		for(Player p:game.players) {
			game.drawCard(p, numcard);
		}
		Collections.shuffle(game.playerDeck);
		
		for(int i = 0; i < 3; i++) {
			InfectionCard t1 = game.infectionDeck.remove(0);
			game.infectCity(t1, 3);
		}
		for(int i = 0; i < 3; i++) {
			InfectionCard t1 = game.infectionDeck.remove(0);
			game.infectCity(t1, 2);
		}
		for(int i = 0; i < 3; i++) {
			InfectionCard t1 = game.infectionDeck.remove(0);
			game.infectCity(t1, 1);
		}
	}
	
	//drive
	void drive(String name, String city) {
		Player t1 = game.getPlayer(name);
		City t2 = game.getCity(city);
		t1.pawn.move(t2);
		
		
	}
	
	//directFlight
	void directFlight(String name, String city) {
		Player t1 = game.getPlayer(name);
		City t2 = game.getCity(city); 
		t1.pawn.move(t2);
		PlayerCard t3 = t1.hand.remove(t1.getCard(city));
		game.playerDiscardPile.add(t3);
	}
	
	//treatDisease
	void treatDisease(String name, String color) {
		Color c = Color.valueOf(color);
		Player t1 = game.getPlayer(name);
		t1.pawn.treat(c);
	}
	
	//shareKnowledge (this happens after consent is given.) (when shareknowledge request is received by server, server doesnt go through gamemanager class, instead a
	//request is directly sent to the targeted client. because consent does not affect internal game logic)
	void shareKnowledge(String name, String target) {
		Boolean consent = true;
		
		if(consent) {
			Player t1 = game.getPlayer(name);
			Player t2 = game.getPlayer(target);
			String t3 = t1.pawn.city.name;
			int a = t1.getCard(t3);
			int b = t2.getCard(t3);
			if(a != -1) {
				t1.share(t2, a, true);
			}
			else if(b != -1) {
				t2.share(t1, b, false);
			}
		}
		else {
			
		}
	}
	
	//can be called anytime when game stage attribute is set to Action. There is no Draw stage attribute, because it isn't required.
	void endTurn(String name) {
		Player t1 = game.getPlayer(name);
		t1.pawn.actions = 4;
		game.drawCard(t1, 2);
		
		game.stage = Stage.Infection;
	}
	
	//can be called when game stage is Infection. Changes stage to Action at the end, and changes turn to the next player.
	void beginInfection() {
		for(int i = 0; i < game.infectionRate[game.infectionCount]; i++) {
			InfectionCard t1 = game.infectionDeck.remove(0);
			game.infectCity(t1, 1);
		}
		
		game.stage = Stage.Action;
		if(game.turn == game.players.size()-1) {
			game.turn = 0;
		}
		else {
			game.turn++;
		}
	}
	
	
	//helper function to return a player from a name string
	Player getPlayer(String s) {
		int i = 0;
		Boolean found = false;
		while(i < playerList.size() && !found) {
			if(playerList.get(i).username.matches(s)) {
				found = true;
			}
			else{
				i++;
			}
		}
		if(found) {
			return playerList.get(i);
		}
		else {
			return null;
		}
	}
}
