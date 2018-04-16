package Server;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameManager {
	//public static ArrayList<Game> games = new ArrayList<Game>();
	public static Game game = new Game();
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
		game.players.add(t1);
		t1.game = game;
		
		System.out.println("New player connected: " + t1.username);
	
	}
	
	public static void ChangeName(String indexs, String newname) {
		int index = Integer.parseInt(indexs);
		Player t1 = game.getPlayer(index);
		
		System.out.println(t1.username + "changed their name to " + newname);
		
		t1.username = newname;
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
		if(t1.pawn.role.equals(Role.Bioterrorist)) {
			City t2 = game.getCity(city);
			t1.pawn.move(t2, false);
			
			System.out.println(t1.username + " drived to " + city + ".");
			
			String mes = "UpdatePlayerLocation/" + t1.username + "/" + city + "/";
			ServerComm.sendMessage(mes, game.BT);
			
			if(t1.pawn.roleactions == 0) {
				mes = "DecrementActions/";
				for(int i = 0; i < game.players.size(); i++) {
					ServerComm.sendMessage(mes, i);
				}
			}
			else {
				mes = "DecrementActionsBio/";
				for(int i = 0; i < game.players.size(); i++) {
					ServerComm.sendMessage(mes, i);
				}
			}
		}
		else {
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
	}
	
	//directFlight
	public static void DirectFlight(String city) {
		Player t1 = game.getCurrentPlayer();
		if(t1.pawn.role.equals(Role.Bioterrorist)) {
			City t2 = game.getCity(city); 
			t1.pawn.move(t2, false);
			InfectionCard t3 = t1.Bhand.remove(t1.getBCard(city));
			
			game.infectionDiscardPile.add(t3);
			
			System.out.println(t1.username + " directly flew to " + city + ".");
			
			String mes = "UpdatePlayerLocation/" + t1.username + "/" + city + "/";
			ServerComm.sendMessage(mes, game.BT);
			String mes2 = "RemoveCardFromHand/" + t1.username + "/" + city + "/false/";
			ServerComm.sendMessage(mes2, game.BT);
			String mes3 = "AddInfectionCardToDiscard/" + t3.city.name + "/";
			String mes4 = "AirportSighting/" + city + "/";
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes3, i);
				ServerComm.sendMessage(mes4, i);
			}
			
			if(t1.pawn.captured) {
				t1.pawn.captured = false;
			}
			
			mes = "DecrementActions/";
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes, i);
			}
		}
		else {
			City t2 = game.getCity(city); 
			t1.pawn.move(t2, false);
			//Troubleshooter flies for free provided she has the card
			if(t1.pawn.role!=Role.Troubleshooter){
				PlayerCard t3 = t1.hand.remove(t1.getCard(city));
				game.playerDiscardPile.add(t3);
				String mes2 = "RemoveCardFromHand/" + t1.username + "/" + city + "/true/";
				ServerComm.sendToAllClients(mes2);
			}
			String mes1 = "UpdatePlayerLocation/" + t1.username + "/" + city + "/";
			ServerComm.sendToAllClients(mes1);
			ServerComm.sendToAllClients("DecrementActions/");
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
		
		}
	}
	
	//charterFlight
	public static void CharterFlight(String city) {
		Player t1 = game.getCurrentPlayer();
		if(t1.pawn.role.equals(Role.Bioterrorist)) {
			InfectionCard t3 = t1.Bhand.remove(t1.getBCard(t1.pawn.city.name));
			City t2 = game.getCity(city); 
			t1.pawn.move(t2, false);
			
			game.infectionDiscardPile.add(t3);
			
			System.out.println(t1.username + " chartered a flight to " + city + ".");
			
			String mes = "UpdatePlayerLocation/" + t1.username + "/" + city + "/";
			ServerComm.sendMessage(mes, game.BT);
			String mes2 = "RemoveCardFromHand/" + t1.username + "/" + t3.city.name + "/true/";
			ServerComm.sendMessage(mes2, game.BT);
			String mes3 = "AddInfectionCardToDiscard/" + t3.city.name + "/";
			String mes4 = "AirportSighting/" + city + "/";
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes3, i);
				ServerComm.sendMessage(mes4, i);
			}
			
			mes = "DecrementActions/";
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes, i);
			}
		}
		else {
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
	}
	
	public static void BuildResearchStation(String city) {
		Player t1 = game.getCurrentPlayer();
		if(game.research.isEmpty()) {
			City c = game.getCity(city);
			c.removeResearchStation();
			game.getCurrentPlayer().pawn.city.addResearchStation();
			PlayerCard p = t1.hand.remove(t1.getCard(city));
			game.playerDiscardPile.add(p);
			
			String mes = "RemoveResearchStation/" + c.name + "/";
			String mes2 = "AddResearchStation/" + game.getCurrentPlayer().pawn.city.name + "/";
			String mes3 = "RemoveCardFromHand/" + t1.username + "/" + p.city.name + "/true";
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes, i);
				ServerComm.sendMessage(mes2, i);
				ServerComm.sendMessage(mes3, i);
			}
		}
		else {
			game.getCurrentPlayer().pawn.city.addResearchStation();
			PlayerCard p = t1.hand.remove(t1.getCard(city));
			game.playerDiscardPile.add(p);
			
			String mes2 = "AddResearchStation/" + game.getCurrentPlayer().pawn.city.name + "/";
			String mes3 = "RemoveCardFromHand/" + t1.username + "/" + p.city.name + "/true";
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes2, i);
				ServerComm.sendMessage(mes3, i);
			}
		}
	}
	
	public static void BuildQuarantine(String city) {
		Player t1 = game.getCurrentPlayer();
		if(game.quarantines == 0) {
			City t2 = game.getCity(city);
			t2.quarantine = 0;
			game.getCurrentPlayer().pawn.city.quarantine = 2;
			PlayerCard p = t1.hand.remove(t1.getCard(city));
			game.playerDiscardPile.add(p);
			
			String mes = "RemoveQuarantine/" + city + "/";
			String mes2 = "AddQuarantine/" + game.getCurrentPlayer().pawn.city.name + "/";
			String mes3 = "RemoveCardFromHand/" + t1.username + "/" + p.city.name + "/true";
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes, i);
				ServerComm.sendMessage(mes2, i);
				ServerComm.sendMessage(mes3, i);
			}
		}
		else {
			game.getCurrentPlayer().pawn.city.quarantine = 2;
			game.quarantines--;
			PlayerCard p = t1.hand.remove(t1.getCard(city));
			game.playerDiscardPile.add(p);
			
			String mes2 = "AddResearchStation/" + game.getCurrentPlayer().pawn.city.name + "/";
			String mes3 = "RemoveCardFromHand/" + t1.username + "/" + p.city.name + "/true";
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes2, i);
				ServerComm.sendMessage(mes3, i);
			}
		}
	}
	
	//treatDisease
	public static void TreatDisease(String color) {
		int count = 1;
		Player t1 = game.getCurrentPlayer();
		Color c = Color.valueOf(color);
		if(t1.pawn.role.equals(Role.Medic) || game.getDisease(c).cured) {
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
		String ans = ServerComm.getResponse(mes,index);
		/*ServerComm.sendMessage(mes,index);
		
		while(ServerComm.response == null) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String ans = ServerComm.response;
		ServerComm.response = null;*/
		Boolean consent = ans.matches("true");
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
	
	//index/cure/color/city1/city2/city3/city4/city5/city6
	public static void CureDisease(String[] args) {
		Player t1 = game.getCurrentPlayer();
		Color c = Color.valueOf(args[2]);
		game.getDisease(c).cured = true;
		int extra = 0;
		if(game.getDisease(c).virulent) {
			extra = 1;
		}
		if(t1.pawn.role.equals(Role.Scientist)) {
			for(int i = 0; i < 4 + extra; i++) {
				PlayerCard card = t1.hand.remove(t1.getCard(args[i+3]));
				game.playerDiscardPile.add(card);
				String mes = "RemoveCardFromHand/" + t1.username + "/" + card + "/true/";
				for(int j = 0; j < game.players.size(); j++) {
					ServerComm.sendMessage(mes, j);
				}
			}
		}
		else if(t1.pawn.role.equals(Role.FieldOperative)) {
			for(int i = 0; i < 3 + extra; i++) {
				PlayerCard card = t1.hand.remove(t1.getCard(args[i+3]));
				game.playerDiscardPile.add(card);
				String mes = "RemoveCardFromHand/" + t1.username + "/" + card + "/true/";
				for(int j = 0; j < game.players.size(); j++) {
					ServerComm.sendMessage(mes, j);
				}
			}
			t1.pawn.useStash(c);
			//TODO update stash/diseasecubes
		}
		else {
			for(int i = 0; i < 5 + extra; i++) {
				PlayerCard card = t1.hand.remove(t1.getCard(args[i+3]));
				game.playerDiscardPile.add(card);
				String mes = "RemoveCardFromHand/" + t1.username + "/" + card + "/true/";
				for(int j = 0; j < game.players.size(); j++) {
					ServerComm.sendMessage(mes, j);
				}
			}
		}
		
		String mes = "DecrementActions/";
		String mes2 = "CureDisease/" + args[2] + "/";
		for(int i = 0; i < game.players.size(); i++) {
			ServerComm.sendMessage(mes, i);
			ServerComm.sendMessage(mes2, i);
		}
		
		if(game.checkVictory()) {
			mes = "EndGame/true/";
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes, i);
			}
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
			
			String mes = "RemoveCardFromPlayerDiscard/" + p.city.name + "/";
			String mes2 = "AddCardToHand/" + t1.username + "/" + p.city.name + "/false/";
			for(int j = 0; j < game.players.size(); j++) {
				ServerComm.sendMessage(mes, j);
				ServerComm.sendMessage(mes2, j);
			}
			
			
			while(t1.hand.size() > 8) {
				
				String s = ServerComm.getResponse("AskForDiscard/",game.turn);
				PlayerCard t3 = t1.hand.remove(t1.getCard(s));
				game.playerDiscardPile.add(t3);
				
				String mes3 = "RemoveCardFromHand/" + t1.username + "/" + t3.city.name + "/true/";
				for(int j = 0; j < game.players.size(); j++) {
					ServerComm.sendMessage(mes3, j);
				}
			}
		}
		else if(args[2].equals("Colonel")) { //index/RA/colonel/cardtoremove/target/targettorob
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
		/*else if(args[2].equals("ContingencyPlanner")) {//index/RoleAction/ContingencyPlanner/Take-Play/eventname
			if(args[3].equals("Take")) {
				int ind = 0;
				for(int i = 0; i < game.playerDiscardPile.size(); i++) {
					if(game.playerDiscardPile.get(i).name.equalsIgnoreCase(args[4])) {
						ind = i;
					}
				}
				t1.pawn.card = game.playerDiscardPile.remove(ind);
				
				String mes = "RemoveCardFromPlayerDiscard/" + args[4] + "/";
				String mes2 = "AddCardToStash/" + args[4] + "/";
				String mes3 = "DecrementActions/";
				for(int i = 0; i < game.players.size(); i++) {
					ServerComm.sendMessage(mes, i);
					ServerComm.sendMessage(mes2, i);
					ServerComm.sendMessage(mes3, i);
				}
			}
			else {
				PlayerCard t2 = t1.pawn.card;
				t1.pawn.card = null;
				
				int length = args.length;
				int extra = length - 5;
				String[] s = new String[3+extra];
				s[0] = args[0];
				s[1] = "EventAction";
				s[2] = t2.name;
				for(int i = 3; i < 3+extra; i++) {
					s[i] = args[i+2];
				}
				GameManager.EventAction(s);
				
				String mes = "RemoveCardFromStash/" + t2.name + "/";
				String mes2 = "DecrementActions/";
				for(int i = 0; i < game.players.size(); i++) {
					ServerComm.sendMessage(mes, i);
					ServerComm.sendMessage(mes2, i);
				}
			}
		}*/
		else if(args[2].equals("FieldOperative")) {//index/RoleAction/FieldOperative/Add-Cure/Color/Cards,List
			FieldOperativePawn p = (FieldOperativePawn)game.getCurrentPlayer().pawn;
			if(args[3].equals("Add")){
				p.addToStash(Color.valueOf(args[4]));
			}
			else if (args[3].equals("Cure")){
				p.discoverCure(args);
			}
			//AddCubeToStash/color
			//RemoveCubeFromStash/color
		}
		//index/RoleAction/Epidemiologist/targetPlayer/TargetCard
		else if(args[2].equals("Epidemiologist")) {
			Player targetPlayer = game.getPlayer(args[3]);
			String consent = ServerComm.getResponse("AskForConsent/", targetPlayer.ID);
			if(consent.equals("true")){
				for(PlayerCard c: targetPlayer.hand){
					if(c.city.name.equals(args[4])){
						targetPlayer.hand.remove(c);
						game.getCurrentPlayer().hand.add(c);
						ServerComm.sendToAllClients("RemoveCardFromHand/" + targetPlayer.username + "/" + args[4] + "/false/");
						ServerComm.sendToAllClients("AddCardToHand/" + game.getCurrentPlayer().username + "/" + args[4] + "/false/");
						break;
					}
				}
			}
		}
		//index/RoleAction/ContingencyPlanner/Take or Play/targetCard/args for target card
		else if(args[2].equals("ContingencyPlanner")) {
			ContingencyPlannerPawn p = (ContingencyPlannerPawn)game.getCurrentPlayer().getPawn();
			if(args[3].equals("Take")){
				for(PlayerCard c:game.playerDiscardPile){
					if(c.name.equals(args[4])){
						game.playerDiscardPile.remove(c);
						p.takeEvent(c);
						ServerComm.sendToAllClients("RemoveCardFromPlayerDiscard/"+args[4]);
						ServerComm.sendToAllClients("AddCardToStash/" + game.getCurrentPlayer().username + "/" + args[4] + "/false/");
						break;
					}
				}
			}
			else if(args[3].equals("Play")){
				p.playEvent(args);
			}
		}
		//index/RoleAction/OperationsExpert/Build or Move/CityToMoveTo(or CityToTakeRSFrom in Build case)/CardToDiscard
		else if(args[2].equals("OperationsExpert")) {
			Pawn p = game.getCurrentPlayer().getPawn();
			if(args[3].equals("Build")){
				if(args.length<=4){
					//BRS already sends updates
					BuildResearchStation("");
				}
				else{
					BuildResearchStation(args[4]);
				}
			}
			else if (args[3].equals("Move")){
				p.move(game.getCity(args[4]), false);
				ServerComm.sendToAllClients("UpdatePlayerLocation/" + game.getCurrentPlayer().username + "/" + args[4] + "/");
				for(PlayerCard c:p.player.hand){
					if(p.city.name.equals(args[5])){
						p.player.hand.remove(c);
						game.playerDiscardPile.add(c);
						ServerComm.sendToAllClients("RemoveCardFromHand/" + args[5] + "/true/");
						break;
					}
				}
			}
		}
		//index/RoleAction/Dispatcher/Drive-Fly/TargetPlayer/normal args
		//index/EventAction/SpecialOrdersMove/(Drive/Flight etc)/targetPlayerToMove/normal args
		else if(args[2].equals("Dispatcher")){
			Player targetPlayer = game.getPlayer(args[4]);
			Pawn targetPawn = targetPlayer.getPawn();
			Player currentPlayer = game.getCurrentPlayer();
			City destination = game.getCity(args[5]);
			String consent = ServerComm.getResponse("AskForConsent/", targetPlayer.ID);
			if(consent.equals("true")){
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
			else{
				
			}
		}
	}
	
	public static void InfectRemotely(String cardname) {
		Player t1 = game.players.get(game.BT);
		InfectionCard t2 = t1.Bhand.remove(t1.getBCard(cardname));
		
		game.infectCity(t2.city, Color.purple, 1);
		game.infectionDiscardPile.add(t2);
		
		t1.pawn.actions--;
		
		String mes = "RemoveCardFromHand/" + t2.city.name + "/false/";
		ServerComm.sendMessage(mes, game.BT);
		
		mes = "AddInfectionCardToDiscard/" + t2.city.name + "/";	
		String mes2 = "DecrementActions/" + t1.username + "/";
		for(int j = 0; j < game.players.size(); j++) {
			ServerComm.sendMessage(mes, j);
			ServerComm.sendMessage(mes2, j);
		}
	}
	
	public static void BioterroristStart(String cityname) {
		Player t1 = game.players.get(game.BT);
		t1.pawn.city = game.getCity(cityname);
		t1.pawn.city.pawns.add(t1.pawn);
		
		String mes = "UpdatePlayerLocation/" + t1.username + "/" + cityname + "/";
		ServerComm.sendMessage(mes, game.BT);
	}
	
	public static void BioterroristDraw() {
		Player t1 = game.players.get(game.BT);
		InfectionCard t2 = game.infectionDeck.remove(0);
		t1.Bhand.add(t2);
		
		String mes = "AddCardToHand/" + t1.username + "/" + t2.city.name + "/false/";
		ServerComm.sendMessage(mes, game.BT);
		
		String mes2 = "DecrementActions/" + t1.username + "/";
		for(int j = 0; j < game.players.size(); j++) {
			ServerComm.sendMessage(mes2, j);
		}
	}
	
	public static void InfectLocally() {
		Player t1 = game.players.get(game.BT);
		
		game.infectCity(t1.pawn.city, Color.purple, 1);
		
		String mes2 = "DecrementActions/" + t1.username + "/";
		for(int j = 0; j < game.players.size(); j++) {
			ServerComm.sendMessage(mes2, j);
		}
	}
	
	public static void Sabotage(String cityname) {
		Player t1 = game.players.get(game.BT);
		InfectionCard t2 = t1.Bhand.remove(t1.getBCard(cityname));
		t1.pawn.city.removeResearchStation();
		game.infectionDiscardPile.add(t2);
		
		String mes = "RemoveResearchStation/" + t1.pawn.city.name + "/";
		String mes2 = "RemoveCardFromHand/" + t1.username + "/" + cityname + "/false";
		ServerComm.sendMessage(mes2, game.BT);
		String mes3 = "AddInfectionCardToDiscard/" + cityname + "/";
		String mes4 = "DecrementActions/" + t1.username + "/";
		for(int i = 0; i < game.players.size(); i++) {
			ServerComm.sendMessage(mes, i);
			ServerComm.sendMessage(mes3, i);
			ServerComm.sendMessage(mes4, i);
		}
	}
	
	public static void Capture() {
		Player t1 = game.getCurrentPlayer();
		Player t2 = game.players.get(game.BT);
		
		int handsize = t2.Bhand.size();
		for(int i = 0; i < handsize; i++) {
			InfectionCard t3 = t2.Bhand.remove(0);
			game.infectionDiscardPile.add(t3);
			
			String mes2 = "RemoveCardFromHand/" + t2.username + "/" + t3.city.name + "/false";
			ServerComm.sendMessage(mes2, game.BT);
			String mes3 = "AddInfectionCardToDiscard/" + t3.city.name + "/";
			for(int j = 0; j < game.players.size(); j++) {
				ServerComm.sendMessage(mes3, j);
			}
		}
		
		t2.pawn.captured = true;
		
		String mes = "Capture/";
		for(int j = 0; j < game.players.size(); j++) {
			ServerComm.sendMessage(mes, j);
		}
	}
	
	//can be called anytime when game stage attribute is set to Action. There is no Draw stage attribute, because it isn't required.
	public static void EndTurn(){
		Player t1 = game.getCurrentPlayer();
		t1.pawn.actions = 4;
		if(t1.pawn.role.compareTo(Role.Generalist) == 0) {
			t1.pawn.actions = 5;
		}
		
		game.drawCard(t1, 2);
		
		int maxhand = 7;
		if(t1.pawn.role.equals(Role.Archivist)) {
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
					game.infectCity(t2.city, t2.city.disease.color, 1);
					game.infectionDiscardPile.add(t2);
					
					String mes = "AddInfectionCardToDiscard/" + t2.city.name + "/";	
					for(int j = 0; j < game.players.size(); j++) {
						ServerComm.sendMessage(mes, j);
					}
				}
			} 
			else{
				InfectionCard t2 = game.infectionDeck.remove(0);
				game.infectCity(t2.city, t2.city.disease.color, 1);
				game.infectionDiscardPile.add(t2);
				
				String mes = "AddInfectionCardToDiscard/" + t2.city.name + "/";	
				for(int i = 0; i < game.players.size(); i++) {
					ServerComm.sendMessage(mes, i);
				}
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
		if(game.getCurrentPlayer().pawn.role==Role.Troubleshooter){
			String infectionCardsToLookAt = "";
			for(int i=0; i<game.infectionRate[game.infectionCount]; i++){
				InfectionCard c = game.infectionDeck.get(i);
				if(c.city!=null){
					infectionCardsToLookAt = infectionCardsToLookAt + c.city.name + ",";
				}
				else{
					infectionCardsToLookAt = infectionCardsToLookAt + "Mutation" + ",";
				}
			}
			ServerComm.sendMessage("NotifyTurnTroubleShooter/" + infectionCardsToLookAt, game.turn);
		}
		//Client resets flag on NotifyTurn
		String mes = "NotifyTurn/" + game.players.get(game.turn).username + "/";
		ServerComm.sendToAllClients(mes);
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
			ServerComm.sendMessage("MobileHospitalActivated/", game.turn);
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
			ServerComm.sendToAllClients("AddCardToHand/" + targetPlayer.username + "/" + cityCardToAddToHand + "/false/");
			ServerComm.sendToAllClients("RemoveCardFromPlayerDiscard/"+cityCardToAddToHand);
		}
		//params:PlayerIndex/EventAction/RT/CitiesList(max 2, separa 	ted by ,)/ColorsList(max 2 separated by ,)
		else if(args[2].equals("RemoteTreatment")) {
			String[] citiesList = args[3].split(",");
			String[] colorsList = args[4].split(",");
			for(int i = 0; i < citiesList.length; i++){
				City targetCity = game.getCity(citiesList[i]);
				Color targetColor = Color.valueOf(colorsList[i]);
				targetCity.removeDiseaseCube(targetColor);
				ServerComm.sendToAllClients("RemoveCube/" + citiesList[i] + "/" + targetColor.name() + "/" + 1 + "/");
			}
			
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
			//will send discarded card to DP
			ServerComm.sendToAllClients("RemoveCardFromHand/"+player.username+"/"+toRemove.name+"/true/");
		}
	}
	public static void chat(String[] args){
		for(ClientThread t:ServerComm.clientList){
			if(t.getClientNumber()!=Integer.parseInt(args[0])){
				ServerComm.sendMessage("Chat/"+args[2], t.getClientNumber());
			}
		}
	}
}
