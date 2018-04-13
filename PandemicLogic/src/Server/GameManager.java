package Server;
import java.util.ArrayList;
import java.util.Collections;

public class GameManager {
	public static ArrayList<Game> games = new ArrayList<Game>();
	//public static Game game = new Game();
	public static ArrayList<Player> playerList = new ArrayList<Player>();
	
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
	public static void AddPlayer(String name) {
		Player t1 = new Player(name);
		playerList.add(t1);
		
		//TODO send list of games to player
		
		System.out.println("New player connected: " + t1.username);
	
	}
	
	public static void ChangeName(String indexs, String newname) {
		int index = Integer.parseInt(indexs);
		Player t1 = games.get(0).getPlayer(index);
		
		System.out.println(t1.username + "changed their name to " + newname);
		
		t1.username = newname;
	}
	
	public static void CreateGame(String indexs) {
		int index = Integer.parseInt(indexs);
		Player t1 = playerList.get(index);
		
		games.add(new Game());
		games.get(0).players.add(t1);
		t1.givePawn(games.get(0).getCity("Atlanta"));
		
		//TODO send lobby to this guy
		//TODO send list of games to all players except this guy
	}
	
	public static void JoinGame(String indexs, String indexg) {
		int index = Integer.parseInt(indexs);
		int gindex = Integer.parseInt(indexg);
		Player t1 = playerList.get(index);
		games.get(gindex).players.add(t1);
		t1.givePawn(games.get(0).getCity("Atlanta"));
		
		//TODO send lobby to this guy
	}
	
	//when a player is ready to start the game
	public static void ToggleReady(String indexs) {
		int index = Integer.parseInt(indexs);
		Player t1 = game.getPlayer(index);
		t1.ready = true;
		
		System.out.println(t1.username + " is ready.");
		
		if(game.allReady()) {
			System.out.println("All players ready. Game started.");
			
			games.get(0).start();
			
			game.stage = Stage.Action;
			
			String tmes = "";
			for(Player p: game.players){
				tmes += p.username;
				tmes += ",";
			}
			String mes = "StartGame/" + tmes + "/";
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes, i);
			}
			
			int numcard = 7;
			for(Player p:game.players) {
				game.drawCard(p, numcard);
			}
			Collections.shuffle(game.playerDeck);
			//PlayerCard tempp = game.playerDeck.remove(game.playerDeck.size()-1);
			//PlayerCard tempp2 = game.playerDeck.remove(game.playerDeck.size()-1);
			//game.playerDeck.add(5, tempp);
			//game.playerDeck.add(0, tempp2);
			
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
			
			mes = "NotifyTurn/" + game.players.get(game.turn).username + "/";
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes, i);
			}
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
	//color is sent by Client when the MobileHospital flag is True. "none" otherwise
	public static void Drive(String city, String color) {
		Player t1 = game.getCurrentPlayer();
		City t2 = game.getCity(city);
		t1.pawn.move(t2, false);
		
		
		if(game.mobileHospitalActive){
			Color c = Color.valueOf(color);
			t1.pawn.treat(c, true);
		}
		
		System.out.println(t1.username + " drived to " + city + ".");
		
		String mes = "UpdatePlayerLocation/" + t1.username + "/" + city + "/";
		for(int i = 0; i < game.players.size(); i++) {
			ServerComm.sendMessage(mes, i);
		}
		
		mes = "DecrementActions/";
		for(int i = 0; i < game.players.size(); i++) {
			ServerComm.sendMessage(mes, i);
		}
	}
	
	//directFlight
	public static void DirectFlight(String city, String color) {
		Player t1 = game.getCurrentPlayer();
		City t2 = game.getCity(city); 
		t1.pawn.move(t2, false);
		PlayerCard t3 = t1.hand.remove(t1.getCard(city));
		game.playerDiscardPile.add(t3);
		
		if(game.mobileHospitalActive){
			Color c = Color.valueOf(color);
			t1.pawn.treat(c, true);
		}
		
		System.out.println(t1.username + " directly flew to " + city + ".");
		
		String mes = "UpdatePlayerLocation/" + t1.username + "/" + city + "/";
		String mes2 = "RemoveCardFromHand/" + t1.username + "/" + city + "/true/";
		for(int i = 0; i < game.players.size(); i++) {
			ServerComm.sendMessage(mes, i);
			ServerComm.sendMessage(mes2, i);
		}
		
		mes = "DecrementActions/";
		for(int i = 0; i < game.players.size(); i++) {
			ServerComm.sendMessage(mes, i);
		}
		//ServerComm.sendMessage(mes, game.turn);
	}
	
	//treatDisease
	public static void TreatDisease(String color) {
		int count = 1;
		Player t1 = game.getCurrentPlayer();
		Color c = Color.valueOf(color);
		t1.pawn.treat(c, false);
		
		System.out.println(t1.username + " treated a " + color.toString() + "disease cube in " + t1.pawn.city.name + ".");
		
		String mes = "RemoveCube/" + t1.pawn.city.name + "/" + color + "/" + count + "/";
		for(int i = 0; i < game.players.size(); i++) {
			ServerComm.sendMessage(mes, i);
		}
		
		mes = "DecrementActions/";
		for(int i = 0; i < game.players.size(); i++) {
			ServerComm.sendMessage(mes, i);
		}
	}
	
	//shareKnowledge 
	public static void ShareKnowledge(String target) {
		Player t1 = game.getCurrentPlayer();
		Player t2 = game.getPlayer(target);
		int index = game.players.indexOf(t2);
		
		ServerComm.sendMessage("AskForConsent/",index);
		while(ServerComm.response == null) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String ans = ServerComm.response;
		Boolean consent = ans.matches("true");
		ServerComm.response = null;
		
		if(consent) {
			String t3 = t1.pawn.city.name;
			int a = t1.getCard(t3);
			int b = t2.getCard(t3);
			if(a != -1) {
				t1.share(t2, a, true);
				
				System.out.println(t1.username + " shared their card with " + t2.username + " at " + t3 + ".");
				
				String mes = "RemoveCardFromHand/" + t1.username + "/" + t3 + "/false/";
				String mes2 = "AddCardToHand/" + t2.username + "/" + t3 + "/false/";
				for(int i = 0; i < game.players.size(); i++) {
					ServerComm.sendMessage(mes, i);
				}
				for(int i = 0; i < game.players.size(); i++) {
					ServerComm.sendMessage(mes2,i);
				}
			}
			else if(b != -1) {
				t2.share(t1, b, false);
				
				System.out.println(t2.username + " shared their card with " + t1.username + " at " + t3 + ".");
				
				String mes = "RemoveCardFromHand/" + t2.username + "/" + t3 + "/false/";
				String mes2 = "AddCardToHand/" + t1.username + "/" + t3 + "/false/";
				for(int i = 0; i < game.players.size(); i++) {
					ServerComm.sendMessage(mes, i);
				}
				for(int i = 0; i < game.players.size(); i++) {
					ServerComm.sendMessage(mes2,i);
				}
			}
			
			String mes = "DecrementActions/";
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes, i);
			}
		}
		else {
			
		}
	}
	
	//can be called anytime when game stage attribute is set to Action. There is no Draw stage attribute, because it isn't required.
	public static void EndTurn(){
		Player t1 = game.getCurrentPlayer();
		t1.pawn.actions = 4;
		
		game.drawCard(t1, 2);
		
		while(t1.hand.size() > 7) {
			
			ServerComm.sendMessage("AskForDiscard/",game.turn);
			int count = 0;
			while(ServerComm.response == null) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String s = ServerComm.response;
			System.out.println(s);
			ServerComm.response = null;
			
			PlayerCard t2 = t1.hand.remove(t1.getCard(s));
			game.playerDiscardPile.add(t2);
			
			String mes2 = "RemoveCardFromHand/" + t1.username + "/" + t2.city.name + "/true/";
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes2, i);
			}
		}
		
		game.stage = Stage.Infection;
		//Checks for CommercialTravelBan flag
		if (game.CommercialTravelBanTurnIndex == -1){
			for(int i = 0; i < Game.infectionRate[game.infectionCount]; i++) {
				InfectionCard t2 = game.infectionDeck.remove(0);
				game.infectCity(t2, 1);
			}
		} 
		else{
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
		
		if (game.turn == game.CommercialTravelBanTurnIndex){
			game.CommercialTravelBanTurnIndex = -1;
		}
		
		String mes = "NotifyTurn/" + game.players.get(game.turn).username + "/";
		for(int i = 0; i < game.players.size(); i++) {
			ServerComm.sendMessage(mes, i);
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
	/*public static Player getPlayer(String s) {
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
	}*/
	public static void Event(String[] args){
		switch (args[2]){
		//params PlayerIndex/EventAction/Airlift/targetPlayer/targetCity
		case "Airlift":
			Player target = game.getPlayer(args[3]);
			int index = game.players.indexOf(target);
			
			ServerComm.sendMessage("AskForConsentAirlift/",index);
			while(ServerComm.response == null) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String ans = ServerComm.response;
			Boolean consent = ans.matches("true");
			ServerComm.response = null;
			
			if(consent){
				City c = game.getCity(args[4]);
				target.pawn.move(c,true); 
				Player temp = game.getPlayer(args[0]);
				//TODO remove event card from player hand
				//TODO send messages to client
			}
			else{
				
			}
			break;
			
		case "BorrowedTime":
			Player curPlayer = game.getCurrentPlayer();
			Pawn pawn = curPlayer.getPawn();
			pawn.incrementAction(2);
			//TODO send to client
			break;
		
		//params PlayerIndex/EventAction/CommercialTravelBan
		case "CommercialTravelBan":
			game.CommercialTravelBanTurnIndex = game.turn;
			//TODO send to client
			break;
		
		case "Forecast" :
			break;
			
		//params: PlayerIndex/EventAction/GovernmentGrant/TargetCity/CityToRemoveRSFrom
		case "GovernmentGrant": 
			City targetCity = game.getCity(args[3]);
			if (args[4].equals("none")){
				targetCity.addResearchStation();
			}
			else{
				City cityToRemoveRSFrom = game.getCity(args[4]);
				cityToRemoveRSFrom.removeResearchStation();
				targetCity.addResearchStation();
			}
			//TODO send info to clients
			break;
		
		case "MobileHospital":
			game.changeMobileHospitalFlag(true);
			//TODO send to client, turn on flag in client
			break;
		
		case "NewAssignment":
			break;
		
		case "OneQuietNight":
			break;
		
		case "RapidVaccineDeployment":
			break;
		
		case "ReexaminedResearch":
			break;
		
		case "RemoteTreatment":
			break;
		
		case "ResilientPopulation":
			break;
		
		case "SpecialOrders":
			break;
		default:
			break;
		}
	}
}
