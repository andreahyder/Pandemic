import java.util.ArrayList;
import java.util.Collections;

public class GameManager {
	//ArrayList<Game> games;
	static Game game = new Game();
	static ArrayList<Player> playerList = new ArrayList<Player>();
	
	GameManager(){
		//playerList = new ArrayList<Player>();
		//game = new Game();
	}
	
	//when a player connects, but haven't joined a game yet
	//retired method
	/*void playerJoin(String name) {
		playerList.add(new Player(name));
	}*/
	
	//when a player joins the game
	static void AddPlayer(String name) {
		Player t1 = new Player(name);
		playerList.add(t1);
		game.players.add(t1);
		t1.givePawn(game.getCity("Atlanta"));
	}
	
	static void ChangeName(String newname) {
		Player t1 = game.getCurrentPlayer();
		t1.username = newname;
	}
	
	//when a player is ready to start the game
	static void ToggleReady(int index) {
		Player t1 = game.getPlayer(index);
		t1.ready = true;
		
		if(game.allReady()) {
			game.stage = Stage.Action;
			int numcard = 2;
			for(Player p:game.players) {
				game.drawCard(p, numcard);
			}
			Collections.shuffle(game.playerDeck);
			
			for(int i = 0; i < 3; i++) {
				InfectionCard t2 = game.infectionDeck.remove(0);
				game.infectCity(t2, 3);
			}
			for(int i = 0; i < 3; i++) {
				InfectionCard t2 = game.infectionDeck.remove(0);
				game.infectCity(t2, 2);
			}
			for(int i = 0; i < 3; i++) {
				InfectionCard t2 = game.infectionDeck.remove(0);
				game.infectCity(t2, 1);
			}
			
			/*for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage("StartGame/", i);
			}*/
		}
	}
	
	//start the game, distribute cards, infect initial cities
	/*void StartGame() {
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
	}*/
	
	//drive
	static void Drive(String name, String city) {
		Player t1 = game.getCurrentPlayer();
		City t2 = game.getCity(city);
		t1.pawn.move(t2);
		
		
	}
	
	//directFlight
	static void DirectFlight(String name, String city) {
		Player t1 = game.getCurrentPlayer();
		City t2 = game.getCity(city); 
		t1.pawn.move(t2);
		PlayerCard t3 = t1.hand.remove(t1.getCard(city));
		game.playerDiscardPile.add(t3);
	}
	
	//treatDisease
	static void TreatDisease(String name, String color) {
		Player t1 = game.getCurrentPlayer();
		Color c = Color.valueOf(color);
		t1.pawn.treat(c);
	}
	
	//shareKnowledge (this happens after consent is given.) (when shareknowledge request is received by server, server doesnt go through gamemanager class, instead a
	//request is directly sent to the targeted client. because consent does not affect internal game logic)
	static void ShareKnowledge(String name, String target) {
		Player t1 = game.getCurrentPlayer();
		String ans = "";
		Boolean consent = ans.matches("true");
		
		if(consent) {
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
	static void EndTurn(String name) {
		Player t1 = game.getCurrentPlayer();
		t1.pawn.actions = 4;
		game.drawCard(t1, 2);
		
		/*while(t1.hand.size() > 7) {
			ServerComm.sendMessage("AskForDiscard/",game.turn);
			while(ServerComm.response == null) {}
			String s = ServerComm.response;
			ServerComm.response = null;
			PlayerCard t2 = t1.hand.remove(t1.getCard(s));
			game.playerDiscardPile.add(t2);
		}*/
		
		game.stage = Stage.Infection;
		
		for(int i = 0; i < game.infectionRate[game.infectionCount]; i++) {
			InfectionCard t2 = game.infectionDeck.remove(0);
			game.infectCity(t2, 1);
		}
		
		game.stage = Stage.Action;
		if(game.turn == game.players.size()-1) {
			game.turn = 0;
		}
		else {
			game.turn++;
		}
	}
	
	//can be called when game stage is Infection. Changes stage to Action at the end, and changes turn to the next player.
	/*void BeginInfection() {
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
	}*/
	
	
	//helper function to return a player from a name string
	static Player getPlayer(String s) {
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
