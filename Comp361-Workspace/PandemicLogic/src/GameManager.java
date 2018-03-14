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
	
	void playerJoin(String name) {
		playerList.add(new Player(name));
	}
	
	void addPlayer(String name) {
		getPlayer(name).givePawn(game.getCity("Atlanta"));
		game.players.add(getPlayer(name));
	}
	
	void toggleReady(String name) {
		game.getPlayer(name).ready = true;
	}
	
	void startGame() {
		game.stage = Stage.Action;
		int numcard = 2;
		for(Player p:game.players) {
			game.drawCard(p, numcard);
		}
		Collections.shuffle(game.playerDeck);
		
		game.infectCity(3);
		game.infectCity(3);
		game.infectCity(3);
		
		game.infectCity(2);
		game.infectCity(2);
		game.infectCity(2);
		
		game.infectCity(1);
		game.infectCity(1);
		game.infectCity(1);
	}
	
	void drive(String name, String city) {
		Player t1 = game.getPlayer(name);
		City t2 = game.getCity(city);
		t1.pawn.move(t2);
		//TODO action class stuff
	}
	
	void directFlight(String name, String city) {
		Player t1 = game.getPlayer(name);
		City t2 = game.getCity(city); 
		t1.pawn.move(t2);
		PlayerCard t3 = t1.hand.remove(t1.getCard(city));
		game.playerDiscardPile.add(t3);
		
		//TODO action class stuff
	}
	
	void treatDisease(String name, Color color) {
		Player t1 = game.getPlayer(name);
		t1.pawn.treat(color);
		
		//TODO action class stuff
	}
	
	void shareKnowledge(String name, String target, String city) {
		
	}
	
	void giveConsent(Boolean b) {
		
	}
	
	void endTurn() {
		
	}
	
	void beginInfection() {
		
	}
	
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
