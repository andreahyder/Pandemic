package Server;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		t1.game = game;
		
		//TODO send playerlist to this guy
		//TODO send lobby to this guy
	}
	
	public static void JoinGame(String indexs) {
		int index = Integer.parseInt(indexs);
		Player t1 = playerList.get(index);
		game.players.add(t1);
		t1.game = game;
		
		//TODO send playerlist to everyone in game
		//TODO send lobby to this guy
	}
	
	public static void ToggleSetting(String[] args) {
		switch(args[2]) {
		case "Diff":
			int a = Integer.parseInt(args[3]);
			game.Diff = a;
			
			//TODO update setting for all players
			break;
		case "Num":
			int b = Integer.parseInt(args[3]);
			game.Num = b;
			
			//TODO update
			break;
		case "Otb":
			if(args[3].equals("true")) {
				game.Otb = true;
				
				//TODO update
			}
			else {
				game.Otb = false;
				
				//TODO update
			}
			break;
		case "Vir":
			if(args[3].equals("true")) {
				game.Vir = true;
				
				//TODO update
			}
			else {
				game.Vir = false;
				
				//TODO update
			}
			break;
		case "Mut":
			if(args[3].equals("true")) {
				game.Mut = true;
				
				//TODO update
			}
			else {
				game.Mut = false;
				
				//TODO update
			}
			break;
		case "Bio":
			if(args[3].equals("true")) {
				game.Bio = true;
				
				//TODO update
			}
			else {
				game.Bio = false;
				
				//TODO update
			}
			break;
		case "BT":
			int i = Integer.parseInt(args[3]);
			game.BT = i;
			
			//TODO update
			break;
		default:
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
	public static void Drive(String city) {
		Player t1 = game.getCurrentPlayer();
		City t2 = game.getCity(city);
		t1.pawn.move(t2, false);
		
		
		if(game.EvMobile){
			ServerComm.sendMessage("AskForTreat/",game.turn);
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
			
			Color c = Color.valueOf(s);
			t1.pawn.treat(c, 1, true);
			
			String mes = "RemoveCube/" + t1.pawn.city.name + "/" + s + "/" + 1 + "/";
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes, i);
			}
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
	public static void DirectFlight(String city) {
		Player t1 = game.getCurrentPlayer();
		City t2 = game.getCity(city); 
		t1.pawn.move(t2, false);
		PlayerCard t3 = t1.hand.remove(t1.getCard(city));
		
		game.playerDiscardPile.add(t3);
		
		if(game.EvMobile){
			ServerComm.sendMessage("AskForTreat/",game.turn);
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
			
			Color c = Color.valueOf(s);
			t1.pawn.treat(c, 1, true);
			
			String mes = "RemoveCube/" + t1.pawn.city.name + "/" + s + "/" + 1 + "/";
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes, i);
			}
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
	public static void CharterFlight(String city) {
		Player t1 = game.getCurrentPlayer();
		PlayerCard t3 = t1.hand.remove(t1.getCard(t1.pawn.city.name));
		City t2 = game.getCity(city); 
		t1.pawn.move(t2, false);
		
		game.playerDiscardPile.add(t3);
		
		if(game.EvMobile){
			ServerComm.sendMessage("AskForTreat/",game.turn);
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
			
			Color c = Color.valueOf(s);
			t1.pawn.treat(c, 1, true);
			
			String mes = "RemoveCube/" + t1.pawn.city.name + "/" + s + "/" + 1 + "/";
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes, i);
			}
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
	
	public static void BuildResearchStation(String city) {
		if(game.research.isEmpty()) {
			City c = game.getCity(city);
			c.removeResearchStation();
			game.getCurrentPlayer().pawn.city.addResearchStation();
			
			//TODO update new RS and old RS
		}
		else {
			game.getCurrentPlayer().pawn.city.addResearchStation();
			
			//TODO update new RS
		}
	}
	//treatDisease
	public static void TreatDisease(String color) {
		int count = 1;
		Player t1 = game.getCurrentPlayer();
		Color c = Color.valueOf(color);
		if(t1.pawn.role.equals(Role.Med) || game.getDisease(c).cured) {
			count = t1.pawn.city.countDiseaseCube(c);
		}
		t1.pawn.treat(c, count, false);
		
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
		//TODO make sure it woks
		Player t1 = game.getCurrentPlayer();
		Player t2 = game.getPlayer(target);
		int index = game.players.indexOf(t2);
		
		String mes = "AskForConsent/" + city + "/";
		ServerComm.sendMessage(mes,index);
		
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
			int a = t1.getCard(city);
			int b = t2.getCard(city);
			if(a != -1) {
				t1.share(t2, a, true);
				
				System.out.println(t1.username + " shared their " + city + " with " + t2.username + ".");
				
				mes = "RemoveCardFromHand/" + t1.username + "/" + city + "/false/";
				String mes2 = "AddCardToHand/" + t2.username + "/" + city + "/false/";
				for(int i = 0; i < game.players.size(); i++) {
					ServerComm.sendMessage(mes, i);
				}
				for(int i = 0; i < game.players.size(); i++) {
					ServerComm.sendMessage(mes2,i);
				}
			}
			else if(b != -1) {
				t2.share(t1, b, false);
				
				System.out.println(t2.username + " shared their " + city + " with " + t1.username + ".");
				
				mes = "RemoveCardFromHand/" + t2.username + "/" + city + "/false/";
				String mes2 = "AddCardToHand/" + t1.username + "/" + city + "/false/";
				for(int i = 0; i < game.players.size(); i++) {
					ServerComm.sendMessage(mes, i);
				}
				for(int i = 0; i < game.players.size(); i++) {
					ServerComm.sendMessage(mes2,i);
				}
			}
			
			mes = "DecrementActions/";
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes, i);
			}
		}
		else {
			//TODO send message to guy saying he got denied
		}
	}
	
	//color/city1/city2/city3/city4/city5
	public static void DiscoverCure(String color, String c1, String c2, String c3, String c4, String c5) {
		Player t1 = game.getCurrentPlayer();
		Color c = Color.valueOf(color);
		if(t1.pawn.role.equals(Role.Sci)) {
			game.getDisease(c).cured = true;
			PlayerCard card1 = t1.hand.remove(t1.getCard(c1));
			PlayerCard card2 = t1.hand.remove(t1.getCard(c2));
			PlayerCard card3 = t1.hand.remove(t1.getCard(c3));
			PlayerCard card4 = t1.hand.remove(t1.getCard(c4));
			game.playerDiscardPile.add(card1);
			game.playerDiscardPile.add(card2);
			game.playerDiscardPile.add(card3);
			game.playerDiscardPile.add(card4);
			
			//TODO update disease cured
			String mes1 = "RemoveCardFromHand/" + t1.username + "/" + c1 + "/true/";
			String mes2 = "RemoveCardFromHand/" + t1.username + "/" + c2 + "/true/";
			String mes3 = "RemoveCardFromHand/" + t1.username + "/" + c3 + "/true/";
			String mes4 = "RemoveCardFromHand/" + t1.username + "/" + c4 + "/true/";
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes1, i);
				ServerComm.sendMessage(mes2, i);
				ServerComm.sendMessage(mes3, i);
				ServerComm.sendMessage(mes4, i);
			}
		}
		else if(t1.pawn.role.equals(Role.Field)) {
			game.getDisease(c).cured = true;
			PlayerCard card1 = t1.hand.remove(t1.getCard(c1));
			PlayerCard card2 = t1.hand.remove(t1.getCard(c2));
			PlayerCard card3 = t1.hand.remove(t1.getCard(c3));
			game.playerDiscardPile.add(card1);
			game.playerDiscardPile.add(card2);
			game.playerDiscardPile.add(card3);
			
			t1.pawn.useStash(c);
			
			//TODO update disease cured
			//TODO update stash/diseasecubes
			String mes1 = "RemoveCardFromHand/" + t1.username + "/" + c1 + "/true/";
			String mes2 = "RemoveCardFromHand/" + t1.username + "/" + c2 + "/true/";
			String mes3 = "RemoveCardFromHand/" + t1.username + "/" + c3 + "/true/";
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes1, i);
				ServerComm.sendMessage(mes2, i);
				ServerComm.sendMessage(mes3, i);
			}
		}
		else {
			game.getDisease(c).cured = true;
			PlayerCard card1 = t1.hand.remove(t1.getCard(c1));
			PlayerCard card2 = t1.hand.remove(t1.getCard(c2));
			PlayerCard card3 = t1.hand.remove(t1.getCard(c3));
			PlayerCard card4 = t1.hand.remove(t1.getCard(c4));
			PlayerCard card5 = t1.hand.remove(t1.getCard(c5));
			game.playerDiscardPile.add(card1);
			game.playerDiscardPile.add(card2);
			game.playerDiscardPile.add(card3);
			game.playerDiscardPile.add(card4);
			game.playerDiscardPile.add(card5);
			
			//TODO update disease cured
			String mes1 = "RemoveCardFromHand/" + t1.username + "/" + c1 + "/true/";
			String mes2 = "RemoveCardFromHand/" + t1.username + "/" + c2 + "/true/";
			String mes3 = "RemoveCardFromHand/" + t1.username + "/" + c3 + "/true/";
			String mes4 = "RemoveCardFromHand/" + t1.username + "/" + c4 + "/true/";
			String mes5 = "RemoveCardFromHand/" + t1.username + "/" + c5 + "/true/";
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes1, i);
				ServerComm.sendMessage(mes2, i);
				ServerComm.sendMessage(mes3, i);
				ServerComm.sendMessage(mes4, i);
				ServerComm.sendMessage(mes5, i);
			}
		}
		
		String mes = "DecrementActions/";
		for(int i = 0; i < game.players.size(); i++) {
			ServerComm.sendMessage(mes, i);
		}
	}
	
	public static void RoleAction(String[] args) {
		int index = Integer.parseInt(args[0]);
		Player t1 = game.getPlayer(index);
		if(args[2].equals("archivist")) {
			City t2 = t1.pawn.city;
			
			PlayerCard p = game.playerDiscardPile.remove(game.getDiscardCard(t2));
			t1.hand.add(p);
			
			t1.pawn.roleactions--;
			//TODO update role action count for this player only
			//TODO update discard pile for everyone
			String mes = "AddCardToHand/" + t1.username + "/" + p.city.name + "/false/";
			for(int j = 0; j < game.players.size(); j++) {
				ServerComm.sendMessage(mes, j);
			}
			
			
			while(t1.hand.size() > 8) {
				
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
				
				PlayerCard t3 = t1.hand.remove(t1.getCard(s));
				game.playerDiscardPile.add(t3);
				
				String mes2 = "RemoveCardFromHand/" + t1.username + "/" + t3.city.name + "/true/";
				for(int j = 0; j < game.players.size(); j++) {
					ServerComm.sendMessage(mes2, j);
				}
			}
		}
		else if(args[2].equals("bioterrorist")) {
			
		}
		else if(args[2].equals("colonel")) { //index/RA/colonel/cardtoremove/target/targettorob
			PlayerCard t2 = t1.hand.remove(t1.getCard(args[3]));
			game.playerDiscardPile.add(t2);
			
			if(!args[5].equals("none")) {
				City t3 = game.getCity(args[5]);
				t3.quarantine = 0;
			}
			
			City t3 = game.getCity(args[4]);
			t3.quarantine = 2;
			game.quarantines--;
			
			String mes2 = "RemoveCardFromHand/" + t1.username + "/" + t3.name + "/true/";
			for(int j = 0; j < game.players.size(); j++) {
				ServerComm.sendMessage(mes2, j);
			}
		}
		else if(args[2].equals("bioterrorist")) {
			
		}
		else if(args[2].equals("bioterrorist")) {
			
		}
		else if(args[2].equals("bioterrorist")) {
			
		}
		else if(args[2].equals("bioterrorist")) {
			
		}
		else if(args[2].equals("bioterrorist")) {
			
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
		
		int maxhand = 7;
		if(t1.pawn.role.equals(Role.Arch)) {
			maxhand++;
		}
		
		while(t1.hand.size() > maxhand) {
			
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
		//Check for OneQuietNightFlag
		if(game.oneQuietNightFlag){
			game.oneQuietNightFlag = false;
		}
		else{
			//Checks for CommercialTravelBan flag
			if (game.EvCommercial == -1){
				for(int i = 0; i < Game.infectionRate[game.infectionCount]; i++) {
					InfectionCard t2 = game.infectionDeck.remove(0);
					game.infectCity(t2, 1);
				}
			} 
			else{
				InfectionCard t2 = game.infectionDeck.remove(0);
				game.infectCity(t2, 1);
			}
		}
		//Checks for MobileHospital flag
		game.EvMobile = false;
		
		game.stage = Stage.Action;
		if(game.turn == game.players.size()-1) {
			game.turn = 0;
		}
		else {
			game.turn++;
		}
		
		if (game.turn == game.EvCommercial){
			game.EvCommercial = -1;
			
			//TODO update flag for everyone
		}
		
		String mes = "NotifyTurn/" + game.players.get(game.turn).username + "/";
		for(int i = 0; i < game.players.size(); i++) {
			ServerComm.sendMessage(mes, i);
		}
	}
	
	public static void EventAction(String[] args){
		boolean removeEventFromHand = true;
		if(args[2].equals("Airlift")) {
			Player target = game.getPlayer(args[3]);
			int index = game.players.indexOf(target);
			
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
			
			if(consent){
				City c = game.getCity(args[4]);
				target.pawn.move(c,true); 
				//send messages to client
				ServerComm.sendToAllClients("UpdatePlayerLocation/" + target.username + "/" + c.name + "/");
			}
			else{
				removeEventFromHand = false;
			}
		}
		else if(args[2].equals("BorrowedTime")) {
			Player curPlayer = game.getCurrentPlayer();
			Pawn pawn = curPlayer.getPawn();
			pawn.actions += 2;
			//TODO send to client
			ServerComm.sendToAllClients("BorrowedTimeActivated/");
		}
		else if(args[2].equals("CommercialTravelBan")) {
			game.EvCommercial = game.turn;
			
		}
		//params: PlayerIndex/EventAction/Forecast
		else if(args[2].equals("Forecast")) {
			List<InfectionCard> forecastCards = game.infectionDeck.subList(0, 5);
			String forecastPrompt = "ForecastPrompt/";
			for(int i = 0; i<5; i++){
				InfectionCard c = forecastCards.get(i);
				if(c.city!=null){
					forecastPrompt = forecastPrompt + c.city.name + ",";
				}
				else{
					forecastPrompt = forecastPrompt + "Mutation,";
				}
			}
			String[] response = ServerComm.getResponse(forecastPrompt, Integer.parseInt(args[0])).split(",");
			//Create new infection pile, add first cards in order, then the rest.
			ArrayList<InfectionCard> newInfectionDiscardPile = new ArrayList<InfectionCard>();
			for(String s:response){
				if(s.equals("Mutation")){
					for (InfectionCard c:forecastCards){
						if(c.city==null){
							forecastCards.remove(c);
							newInfectionDiscardPile.add(c);
						}
					}
				}
				else{
					for (InfectionCard c:forecastCards){
						if(c.city.name.equals(s)){
							forecastCards.remove(c);
							newInfectionDiscardPile.add(c);
						}
					}
				}
			}
			newInfectionDiscardPile.addAll(game.infectionDiscardPile);
			game.infectionDiscardPile = newInfectionDiscardPile;
		}
		//params: PlayerIndex/EventAction/GG/targetCity/CityToRemoveRS
		else if(args[2].equals("GovernmentGrant")) {
			City targetCity = game.getCity(args[3]);
			if (args[4].equals("none")){
				targetCity.addResearchStation();
				ServerComm.sendToAllClients("AddResearchStation/"+targetCity.name+"/");
			}
			else{
				City cityToRemoveRSFrom = game.getCity(args[4]);
				cityToRemoveRSFrom.removeResearchStation();
				targetCity.addResearchStation();
				ServerComm.sendToAllClients("RemoveResearchStation/"+cityToRemoveRSFrom.name+"/");
				ServerComm.sendToAllClients("AddResearchStation/"+targetCity.name+"/");
				//TODO send info to clients
			}
		}
		else if(args[2].equals("LocalInitiative")) {
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
		}
		else if(args[2].equals("MobileHospital")) {
			game.EvMobile = true;
			//TODO send to client, turn on flag in client
		}
		//params: PlayerIndex/EventAction/NA/targetPlayer
		else if(args[2].equals("NewAssignmentRequest")) {
			Player targetPlayer = game.getPlayer(args[3]);
			String q = "PromptNewAssignment/";
			for (Pawn p: game.pawns){
				q = q + p.role.name();
			}
			String response = ServerComm.getResponse(q, targetPlayer.ID);
			//Assign new role to that player
			Pawn toGive = null;
			for(Pawn p: game.pawns){
				if(p.role.name().equals(response)){
					toGive = p;
					game.pawns.remove(p);
					break;
				}
			}
			if(toGive != null){
				City c = targetPlayer.pawn.city;
				targetPlayer.givePawn(toGive, c);
			}
			//Updates clients: UpdateRole/targetPlayer/targetRole
			String update = "UpdateRole/"+ targetPlayer.username + "/" + response;
			ServerComm.sendToAllClients(update);
		}
		else if(args[2].equals("OneQuietNight")) {
			game.oneQuietNightFlag = true;
		}
		//params:PlayerIndex/EventAction/RVD/Color/ListOfCities <- separated by ,
		else if(args[2].equals("RapidVaccine")) {
			//prompt client to play RVD after curing sth (done in client)
			String[] citiesList = args[4].split(",");
			Color z = Color.valueOf(args[3]);
			for(String s:citiesList){
				City c = game.getCity(s);
				c.removeDiseaseCube(z);
				ServerComm.sendToAllClients("RemoveCube/" + c + "/" + z + "/" + 1 + "/");
			}
			
		}
		//params: PlayerIndex/EventAction/ReexaminedResearch/TargetPlayer
		else if(args[2].equals("ReexaminedResearch")) {
			Player targetPlayer = game.getPlayer(args[3]);
			ArrayList<PlayerCard> discardPile = (ArrayList<PlayerCard>) game.playerDiscardPile.clone();
			String q = "ChooseCard/PlayerCard/";
			for(PlayerCard c: discardPile){
				q = q + c.name + ",";
			}
			String cityCardToAddToHand = ServerComm.getResponse(q, targetPlayer.ID);
			PlayerCard targetCard = null;
			for(PlayerCard c: game.playerDiscardPile){
				if(c.name.equals(cityCardToAddToHand)){
					targetCard = c;
					break;
				}
			}
			if(targetCard != null){
				targetPlayer.hand.add(targetCard);
				game.playerDiscardPile.remove(targetCard);
			}
			//TODO update clients
		}
		//params:PlayerIndex/EventAction/RT/CitiesList(max 2, separa 	ted by ,)/ColorsList(max 2 separated by ,)
		else if(args[2].equals("RemoteTreatment")) {
			String[] citiesList = args[3].split(",");
			String[] colorsList = args[4].split(",");
			for(int i = 0; i < citiesList.length; i++){
				City targetCity = game.getCity(citiesList[i]);
				Color targetColor = Color.valueOf(colorsList[i]);
				targetCity.removeDiseaseCube(targetColor);
			}
			//TODO update clients
		}
		//params:PlayerIndex/EventAction/RP/CardToRemove
		else if(args[2].equals("ResilientPopulation")) {
			if(args[3].equals("Mutation")){
				for(InfectionCard c: game.infectionDiscardPile){
					if(c.type == Type.Mutation){
						game.infectionDiscardPile.remove(c);
						break;
					}
				}
			}
			else{
				for(InfectionCard c: game.infectionDiscardPile){
					if(c.city.name.equals(args[3])){
						game.infectionDiscardPile.remove(c);
						break;
					}
				}
			}
			ServerComm.sendToAllClients("RemoveFromInfectionPile/" + args[3]);
		}
		//params:PlayerIndex/EventAction/SO/AffectedPlayer
		else if(args[2].equals("SpecialOrdersRequest")) {
			int targetPlayerIndex = game.getPlayer(args[3]).ID;
			String consent = ServerComm.getResponse("AskForConsent", targetPlayerIndex);
			//Sends name of affected player to current turn client
			if(consent.equals("true")){
				ServerComm.sendMessage("SpecialOrdersActivated/"+args[3]+"/", game.turn);
			}
			else{
				removeEventFromHand = false;
			}
			
		}
		//PlayerIndex/EventAction/SpecialOrdersMove/(Drive/Flight etc)/targetPlayerToMove/normal args
		else if(args[2].equals("SpecialOrdersMove")){
			Player targetPlayer = game.getPlayer(args[4]);
			Pawn targetPawn = targetPlayer.getPawn();
			Player currentPlayer = game.getCurrentPlayer();
			City destination = game.getCity(args[5]);
			if(args[3].equals("Drive")){
				targetPawn.move(destination, true);
			}
			else if(args[3].equals("DirectFlight")){
				targetPawn.move(destination, true);
				for (PlayerCard c: currentPlayer.hand){
					if(c.city==destination){
						currentPlayer.hand.remove(c);
						game.playerDiscardPile.add(c);
						ServerComm.sendToAllClients("RemoveCardFromHand"+currentPlayer.username+"/"+c.city.name+"/true/");
						break;
					}
				}
			}
			else if(args[3].equals("CharterFlight")){
				for (PlayerCard c: currentPlayer.hand){
					if(c.city==targetPawn.city){
						currentPlayer.hand.remove(c);
						game.playerDiscardPile.add(c);
						ServerComm.sendToAllClients("RemoveCardFromHand"+currentPlayer.username+"/"+c.city.name+"/true/");
						break;
					}
				}
				targetPawn.move(destination, true);
			}
			ServerComm.sendToAllClients("UpdatePlayerLocation/" + targetPlayer.username + "/" + destination.name + "/");
			currentPlayer.pawn.actions--;
			ServerComm.sendToAllClients("DecrementActions/");
		}
		if(removeEventFromHand){
			Player player = game.getPlayer(Integer.parseInt(args[0]));
			PlayerCard toRemove = null;
			for(PlayerCard c:player.hand){
				if(c.name.equals(args[2])){
					toRemove = c;
					break;
				}
			}
			if(toRemove != null){
				game.playerDiscardPile.add(toRemove);
				player.hand.remove(toRemove);
				
			}
			ServerComm.sendToAllClients("RemoveCardFromHand/"+player.username+"/"+toRemove.name+"/true/");
			
		}
	}
}
