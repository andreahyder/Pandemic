package Server;
import java.util.ArrayList;
import java.util.Collections;

public class GameManager {
	//public static ArrayList<Game> games = new ArrayList<Game>();
	public static Game game;
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
		Player t1 = game.getPlayer(index);
		
		System.out.println(t1.username + "changed their name to " + newname);
		
		t1.username = newname;
	}
	
	public static void CreateGame(String indexs) {
		int index = Integer.parseInt(indexs);
		Player t1 = playerList.get(index);
		
		game = new Game();
		game.players.add(t1);
		
		//TODO send playerlist to this guy
		//TODO send lobby to this guy
	}
	
	public static void JoinGame(String indexs) {
		int index = Integer.parseInt(indexs);
		Player t1 = playerList.get(index);
		game.players.add(t1);
		
		//TODO send playerlist to everyone in game
		//TODO send lobby to this guy
	}
	
	public static void ToggleSetting(String[] args) {
		switch(args[2]) {
		case "difficulty":
			int a = Integer.parseInt(args[3]);
			game.difficulty = a;
			
			//TODO update setting for all players
			break;
		case "playerNumber":
			int b = Integer.parseInt(args[3]);
			game.maxPlayer = b;
			
			//TODO update
			break;
		case "otb":
			if(args[3].equals("true")) {
				game.OTB = true;
				
				//TODO update
			}
			else {
				game.OTB = false;
				
				//TODO update
			}
			break;
		case "virulentStrain":
			if(args[3].equals("true")) {
				game.Vir = true;
				
				//TODO update
			}
			else {
				game.Vir = false;
				
				//TODO update
			}
			break;
		case "mutation":
			if(args[3].equals("true")) {
				game.Mut = true;
				
				//TODO update
			}
			else {
				game.Mut = false;
				
				//TODO update
			}
			break;
		case "bio":
			if(args[3].equals("true")) {
				game.Bio = true;
				
				//TODO update
			}
			else {
				game.Bio = false;
				
				//TODO update
			}
			break;
		case "bioPlayer":
			int i = Integer.parseInt(args[3]);
			game.BT = i;
			
			//TODO update
			break;
		}
	}
	
	//when a player is ready to start the game
	public static void ToggleReady(String indexs) {
		int index = Integer.parseInt(indexs);
		Player t1 = game.getPlayer(index);
		t1.ready = true;
		
		System.out.println(t1.username + " is ready.");
		
		if(game.allReady()) {
			System.out.println("All players ready. Game started.");
			game.start();
		}
	}
	
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
	
	//charterFlight
	public static void CharterFlight(String city, String color) {
		Player t1 = game.getCurrentPlayer();
		PlayerCard t3 = t1.hand.remove(t1.getCard(t1.pawn.city.name));
		City t2 = game.getCity(city); 
		t1.pawn.move(t2, false);
		
		game.playerDiscardPile.add(t3);
		
		if(game.mobileHospitalActive){
			Color c = Color.valueOf(color);
			t1.pawn.treat(c, true);
		}
		
		System.out.println(t1.username + " chartered a flight to " + city + ".");
		
		String mes = "UpdatePlayerLocation/" + t1.username + "/" + city + "/";
		String mes2 = "RemoveCardFromHand/" + t1.username + "/" + t3.name + "/true/";
		for(int i = 0; i < game.players.size(); i++) {
			ServerComm.sendMessage(mes, i);
			ServerComm.sendMessage(mes2, i);
		}
		
		mes = "DecrementActions/";
		for(int i = 0; i < game.players.size(); i++) {
			ServerComm.sendMessage(mes, i);
		}
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
	public static void ShareKnowledge(String target, String city) {
		Boolean researcher = !city.equals("none");
		
		Player t1 = game.getCurrentPlayer();
		Player t2 = game.getPlayer(target);
		int index = game.players.indexOf(t2);
		
		if(researcher) {
			ServerComm.sendMessage("AskForConsent/none/",index);
		}
		else {
			String mes = "AskForConsent/" + city + "/";
			ServerComm.sendMessage(mes,index);
		}
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
			if(researcher) {
				t3 = city;
			}
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
			//TODO send message to guy saying he got denied
		}
	}
	
	//can be called anytime when game stage attribute is set to Action. There is no Draw stage attribute, because it isn't required.
	public static void EndTurn(){
		Player t1 = game.getCurrentPlayer();
		t1.pawn.actions = 4;
		if(t1.pawn.role.compareTo(Role.Gen) == 0) {
			t1.pawn.actions = 5;
		}
		
		game.drawCard(t1, 2);
		
		while(t1.hand.size() > 7) {
			
			ServerComm.sendMessage("AskForDiscard/",game.turn);
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
				
				//TODO send info to clients
			}
			else{
				City cityToRemoveRSFrom = game.getCity(args[4]);
				cityToRemoveRSFrom.removeResearchStation();
				targetCity.addResearchStation();
				
				//TODO send info to clients
			}
			break;
		
		//params: PlayerIndex/EventAction/LocalInitiative/TargetCity/RemoveCity
		case "LocalInitiative":
			City t1 = game.getCity(args[3]);
			if(args[4].equals("none")) {
				t1.quarantine = 2;
				game.quarantines--;
				
				//TODO update clients
			}
			else {
				City t2 = game.getCity(args[4]);
				t2.quarantine = 0;
				t1.quarantine = 2;
				
				//TODO update clients
			}
			
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
