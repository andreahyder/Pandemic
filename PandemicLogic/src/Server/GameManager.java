package Server;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

public class GameManager {
	//public static ArrayList<Game> games = new ArrayList<Game>();
	static File saveFile;
	static FileOutputStream fileOut;
	static File[] allSavedGameFiles = new File[20];
	static int numSavedGames = 0;
	
	
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
		
		String plist = "";
		String mes = "UpdateSetting/players/" + plist + "/";
		for(int i = 0; i< game.players.size(); i++){
			ServerComm.sendMessage(mes, i);
		}
		
		System.out.println("New player connected: " + t1.username);
	
	}
	
	public static void SaveGame() {
		String name = "Game" + numSavedGames;
		String mes = "SaveGame/" + name + "/";
		for(int i = 0; i< game.players.size(); i++){
			ServerComm.sendMessage(mes, i);
		}
		
		if (game.beenSaved == false) {
        	try {   
        		String saveName = "game" + (numSavedGames+1);
        		saveFile = new File(saveName);
        		fileOut = new FileOutputStream(saveFile);
        		ObjectOutputStream objectStream = new ObjectOutputStream(fileOut);   

                objectStream.writeObject(game); //Game state NOT window (CHANGE)
                
                objectStream.close();   
                fileOut.close();   
            }
        	catch (Exception e) {  
            } 
        	game.beenSaved = true; 	//Now the Game has been saved once, so change the boolean
        	// Now add the file to the list of savedGameFiles:
    		allSavedGameFiles[numSavedGames] = Game.saveFile;
    		game.posInSavedArray = numSavedGames;
    		numSavedGames ++; 	//add 1 to number of saved games
    		System.out.println(Game.posInSavedArray);
    	}
    	else {  // so if it's already been saved before:
    		// Erase contents of saveFile before rewritting to it:
    		String saveName = "game" + (Game.posInSavedArray+1);
    		saveFile = new File(saveName);
    		try {  
    			fileOut = new FileOutputStream(saveFile);
    			// file saved already exists then, so don't need to recreate it. Everything else is the same.
        		ObjectOutputStream objectStream = new ObjectOutputStream(fileOut);   

                objectStream.writeObject(game); //Game state NOT window (CHANGE)
                
                objectStream.close();   
                fileOut.close();   
            } 
    		catch (Exception e) { 
    			
            } 
    		// Now override the file in the list of savedGameFiles:
    		allSavedGameFiles[Game.posInSavedArray] = saveFile;
    		System.out.println("Overidden!");
    	}
	}
	
	public static void ChangeName(String indexs, String newname) {
		int index = Integer.parseInt(indexs);
		Player t1 = game.getPlayer(index);
		
		System.out.println(t1.username + "changed their name to " + newname);
		
		t1.username = newname;
	}
	
	public static void ToggleSetting(String[] args) {
		String mes = "";
		switch(args[2]) { 
		case "Diff":
			int a = Integer.parseInt(args[3]);
			game.Diff = a;
			
			mes = "UpdateSetting/DiffOption/" + args[3] + "/"; 
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes, i);
			}
			break;
		case "Num":
			int b = Integer.parseInt(args[3]);
			game.Num = b;
			
			mes = "UpdateSetting/NumOption/" + args[3] + "/"; 
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes, i);
			}
			break;
		case "Otb":
			if(args[3].equals("true")) {
				game.Otb = true;
				mes = "UpdateSetting/OtbOption/" + args[3] + "/"; 
				for(int i = 0; i < game.players.size(); i++) {
					ServerComm.sendMessage(mes, i);
				}
			}
			else {
				game.Otb = false;
				mes = "UpdateSetting/OtbOption/" + args[3] + "/"; 
				for(int i = 0; i < game.players.size(); i++) {
					ServerComm.sendMessage(mes, i);
				}
			}
			break;
		case "Vir":
			if(args[3].equals("true")) {
				game.Vir = true;
				mes = "UpdateSetting/VirOption/" + args[3] + "/"; 
				for(int i = 0; i < game.players.size(); i++) {
					ServerComm.sendMessage(mes, i);
				}
			}
			else {
				game.Vir = false;
				mes = "UpdateSetting/VirOption/" + args[3] + "/"; 
				for(int i = 0; i < game.players.size(); i++) {
					ServerComm.sendMessage(mes, i);
				}
			}
			break;
		case "Mut":
			if(args[3].equals("true")) {
				game.Mut = true;
				mes = "UpdateSetting/MutOption/" + args[3] + "/"; 
				for(int i = 0; i < game.players.size(); i++) {
					ServerComm.sendMessage(mes, i);
				}
			}
			else {
				game.Mut = false;
				mes = "UpdateSetting/MutOption/" + args[3] + "/"; 
				for(int i = 0; i < game.players.size(); i++) {
					ServerComm.sendMessage(mes, i);
				}
			}
			break;
		case "Bio":
			if(args[3].equals("true")) {
				game.Bio = true;
				mes = "UpdateSetting/BioOption/" + args[3] + "/"; 
				for(int i = 0; i < game.players.size(); i++) {
					ServerComm.sendMessage(mes, i);
				}
			}
			else {
				game.Bio = false;
				mes = "UpdateSetting/BioOption/" + args[3] + "/"; 
				for(int i = 0; i < game.players.size(); i++) {
					ServerComm.sendMessage(mes, i);
				}
			}
			break;
		case "BT":
			int ind = Integer.parseInt(args[3]);
			game.BT = ind;
			mes = "UpdateSetting/BTOption/" + args[3] + "/"; 
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes, i);
			}
			break;
		case "ChoseSavedGame":
			if(args[3] != "null"){
				String mes2 = "LoadGame/" + args[3] + "/";
				for(int i = 0; i< game.players.size(); i++){
					ServerComm.sendMessage(mes2, i);
				}
				
				FileInputStream fileIn;
		        Game GameFromSaved = null;
				try {
					int index = Integer.parseInt(args[3]);
					fileIn = new FileInputStream(allSavedGameFiles[index]);
		            ObjectInputStream objectStream = new ObjectInputStream(fileIn);   

		            GameFromSaved = (Game) objectStream.readObject();
				} 
				catch (Exception e) {
					
				}   
				//System.out.println(GameFromSaved.toString());
				game = GameFromSaved;
				game.players = new ArrayList<Player>();
				for(Player p: playerList) {
					game.players.add(p);
				}
			}
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
				for(int i = 0; i< game.players.size(); i++){
					ServerComm.sendMessage(mes2, i);
				}
			}
			String mes1 = "UpdatePlayerLocation/" + t1.username + "/" + city + "/";
			for(int i = 0; i< game.players.size(); i++){
				ServerComm.sendMessage(mes1, i);
			}
			for(int i = 0; i< game.players.size(); i++){
				ServerComm.sendMessage("DecrementActions/", i);
			}
			if(game.EvMobile){
				ServerComm.sendMessage("AskForTreat/",game.turn);
				while(ServerComm.response == null) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
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
			String mes2 = "RemoveCardFromHand/" + t1.username + "/" + t3.city.name + "/true/";
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
			PlayerCard p = t1.hand.remove(t1.getCard(t1.pawn.city.name));
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
			/*PlayerCard p = t1.hand.remove(t1.getCard(city));
			game.playerDiscardPile.add(p);*/
			
			String mes = "RemoveQuarantine/" + city + "/";
			String mes2 = "AddQuarantine/" + game.getCurrentPlayer().pawn.city.name + "/";
			/*String mes3 = "RemoveCardFromHand/" + t1.username + "/" + p.city.name + "/true";*/
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes, i);
				ServerComm.sendMessage(mes2, i);
				/*ServerComm.sendMessage(mes3, i);*/
			}
		}
		else {
			game.getCurrentPlayer().pawn.city.quarantine = 2;
			game.quarantines--;
			/*PlayerCard p = t1.hand.remove(t1.getCard(t1.pawn.city.name));
			game.playerDiscardPile.add(p);*/
			
			String mes2 = "AddQuarantine/" + game.getCurrentPlayer().pawn.city.name + "/";
			/*String mes3 = "RemoveCardFromHand/" + t1.username + "/" + p.city.name + "/true";*/
			for(int i = 0; i < game.players.size(); i++) {
				ServerComm.sendMessage(mes2, i);
				/*ServerComm.sendMessage(mes3, i);*/
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
		}/*
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
		}*/
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
		if(args[2].equals("Archivist")) {
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
			
			if(args.length<6) {
				City t3 = game.getCity(args[5]);
				t3.quarantine = 0;
			}
			
			City t3 = game.getCity(args[4]);
			t3.quarantine = 2;
			game.quarantines--;
			for(int i =0; i<game.players.size(); i++){
				ServerComm.sendMessage("UpdateQuarantine/"+t3.name+"/2/", i);
			}
			
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
						for(int i =0; i<game.players.size(); i++){
							ServerComm.sendMessage("RemoveCardFromHand/" + targetPlayer.username + "/" + args[4] + "/false/", i);
						}for(int i = 0; i< game.players.size(); i++){
							ServerComm.sendMessage("AddCardToHand/" + game.getCurrentPlayer().username + "/" + args[4] + "/false/", i);
						}
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
						for(int i = 0; i< game.players.size(); i++){
							ServerComm.sendMessage("RemoveCardFromPlayerDiscard/"+args[4], i);
						}
						for(int i = 0; i< game.players.size(); i++){
							ServerComm.sendMessage("AddCardToStash/" + args[4], i);
						}
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
				for(int i = 0; i< game.players.size(); i++){
					ServerComm.sendMessage("UpdatePlayerLocation/" + game.getCurrentPlayer().username + "/" + args[4] + "/", i);
				}
				for(PlayerCard c:p.player.hand){
					if(p.city.name.equals(args[5])){
						p.player.hand.remove(c);
						game.playerDiscardPile.add(c);
						for(int i = 0; i< game.players.size(); i++){
							ServerComm.sendMessage("RemoveCardFromHand/" + args[5] + "/true/", i);
						}
						break;
					}
				}
			}
		}
		//index/RoleAction/Dispatcher/Drive-Fly/TargetPlayer/normal args
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
							for(int i = 0; i< game.players.size(); i++){
								ServerComm.sendMessage("RemoveCardFromHand"+currentPlayer.username+"/"+c.city.name+"/true/", i);
							}
							break;
						}
					}
				}
				else if(args[3].equals("CharterFlight")){
					for (PlayerCard c: currentPlayer.hand){
						if(c.city==targetPawn.city){
							currentPlayer.hand.remove(c);
							game.playerDiscardPile.add(c);
							for(int i = 0; i< game.players.size(); i++){
								ServerComm.sendMessage("RemoveCardFromHand"+currentPlayer.username+"/"+c.city.name+"/true/", i);
							}
							break;
						}
					}
					targetPawn.move(destination, true);
				}
				for(int i = 0; i< game.players.size(); i++){
					ServerComm.sendMessage("UpdatePlayerLocation/" + targetPlayer.username + "/" + destination.name + "/", i);
				}
				currentPlayer.pawn.actions--;
				for(int i = 0; i< game.players.size(); i++){
					ServerComm.sendMessage("DecrementActions/", i);
				}
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
		t1.pawn.roleactions = 1;
		if(t1.pawn.role.compareTo(Role.Generalist) == 0) {
			t1.pawn.actions = 5;
		}
		if(t1.pawn.role.compareTo(Role.Bioterrorist) == 0) {
			t1.pawn.actions = 2;
		}
		
		if(game.Bturn == 0) {
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
						if(t2.type.equals(Type.City)) {
							if(game.Mut && t2.city.countDiseaseCube(Color.purple) > 0) {
								game.infectCity(t2.city, t2.city.disease.color, 1);
								game.infectCity(t2.city, Color.purple, 1);
								game.infectionDiscardPile.add(t2);
								
								String mes = "AddInfectionCardToDiscard/" + t2.city.name + "/";	
								for(int j = 0; j < game.players.size(); j++) {
									ServerComm.sendMessage(mes, j);
								}
							}
							else {
								game.infectCity(t2.city, t2.city.disease.color, 1);
								game.infectionDiscardPile.add(t2);
								
								String mes = "AddInfectionCardToDiscard/" + t2.city.name + "/";	
								for(int j = 0; j < game.players.size(); j++) {
									ServerComm.sendMessage(mes, j);
								}
							}
						}
						else {
							InfectionCard t3 = null;
							while(t3 == null) {
								t3 = game.infectionDeck.remove(game.infectionDeck.size()-1);
								if(t3.type.equals(Type.Mutation)) {
									game.infectionDeck.add(0,t3);
									t3 = null;
								}
							}
							game.infectCity(t3.city, Color.purple, 1);
							game.infectionDiscardPile.add(t2);
							game.infectionDiscardPile.add(t3);
							
							String mes = "AddInfectionCardToDiscard/" + t3.city.name + "/";
							String mes2 = "AddInfectionCardToDiscard/MutationCard/";
							for(int j = 0; j< game.players.size(); i++){
								ServerComm.sendMessage(mes, i);
							}
							for(int j = 0; j< game.players.size(); i++){
								ServerComm.sendMessage(mes2, i);
							}
						}
					}
				} 
				else{
					InfectionCard t2 = game.infectionDeck.remove(0);
					if(t2.type.equals(Type.City)) {
						if(game.Mut && t2.city.countDiseaseCube(Color.purple) > 0) {
							game.infectCity(t2.city, t2.city.disease.color, 1);
							game.infectCity(t2.city, Color.purple, 1);
							game.infectionDiscardPile.add(t2);
							
							String mes = "AddInfectionCardToDiscard/" + t2.city.name + "/";	
							for(int j = 0; j < game.players.size(); j++) {
								ServerComm.sendMessage(mes, j);
							}
						}
						else {
							game.infectCity(t2.city, t2.city.disease.color, 1);
							game.infectionDiscardPile.add(t2);
							
							String mes = "AddInfectionCardToDiscard/" + t2.city.name + "/";	
							for(int j = 0; j < game.players.size(); j++) {
								ServerComm.sendMessage(mes, j);
							}
						}
					}
					else {
						InfectionCard t3 = null;
						while(t3 == null) {
							t3 = game.infectionDeck.remove(game.infectionDeck.size()-1);
							if(t3.type.equals(Type.Mutation)) {
								game.infectionDeck.add(0,t3);
								t3 = null;
							}
						}
						game.infectCity(t3.city, Color.purple, 1);
						game.infectionDiscardPile.add(t2);
						game.infectionDiscardPile.add(t3);
						
						String mes = "AddInfectionCardToDiscard/" + t3.city.name + "/";
						String mes2 = "AddInfectionCardToDiscard/MutationCard/";
						for(int j = 0; j< game.players.size(); j++){
							ServerComm.sendMessage(mes, j);
						}
						for(int j = 0; j< game.players.size(); j++){
							ServerComm.sendMessage(mes2, j);
						}
					}
				}
			}
		}
		
		//Checks for MobileHospital flag
		game.EvMobile = false;
		
		game.stage = Stage.Action;
		
		//set turn, if turn = bt turn index, keep going. If bt just finished turn, Bturn set to 0
		if(game.Bturn == 0 && game.Bio) {
			game.Bturn = 1;
		}
		else {
			game.Bturn = 0;
			if(game.turn == game.players.size()-1) {
				game.turn = 0;
			}
			else {
				game.turn++;
			}
			if(game.turn == game.BT) {
				if(game.turn == game.players.size()-1) {
					game.turn = 0;
				}
				else {
					game.turn++;
				}
			}
		}
		
		if (game.turn == game.EvCommercial){
			game.EvCommercial = -1;
			
			//update flag for everyone (client does this)
		}
		if(game.Bturn == 0 && game.getCurrentPlayer().pawn.role==Role.Troubleshooter){
			String infectionCardsToLookAt = "";
			for(int i=0; i<Game.infectionRate[game.infectionCount]; i++){
				InfectionCard c = game.infectionDeck.get(i);
				if(c.city!=null){
					infectionCardsToLookAt = infectionCardsToLookAt + c.city.name + ",";
				}
				else{
					infectionCardsToLookAt = infectionCardsToLookAt + "MutationCard" + ",";
				}
			}
			ServerComm.sendMessage("NotifyTurnTroubleShooter/" + infectionCardsToLookAt, game.turn);
		}
		//Client resets flag on NotifyTurn, bioterrorist turn if Bturn = 1
		String mes = "";
		if(game.Bturn == 1) {
			mes = "NotifyTurn/" + game.players.get(game.BT).username + "/";
		}
		else {
			mes = "NotifyTurn/" + game.players.get(game.turn).username + "/";
		}
		
		for(int j = 0; j< game.players.size(); j++){
			ServerComm.sendMessage(mes, j);
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
				for(int j = 0; j< game.players.size(); j++){
					ServerComm.sendMessage("UpdatePlayerLocation/" + target.username + "/" + c.name + "/", j);
				}
			}
			else{
				removeEventFromHand = false;
			}
		}
		else if(args[2].equals("BorrowedTime")) {
			Player curPlayer = game.getCurrentPlayer();
			Pawn pawn = curPlayer.getPawn();
			pawn.actions += 2;
			//send to client
			for(int j = 0; j< game.players.size(); j++){
				ServerComm.sendMessage("BorrowedTimeActivated/", j);
			}
		}
		else if(args[2].equals("CommercialTravelBan")) {
			game.EvCommercial = game.turn;
			
		}
		//params: PlayerIndex/EventAction/Forecast
		else if(args[2].equals("Forecast")) {
			ArrayList<InfectionCard> forecastCards = new ArrayList<InfectionCard>();//game.infectionDeck.subList(0, 5);
			int count = 5;
			if(count > game.infectionDeck.size()){
				count = game.infectionDeck.size();
			}
			for(int i = 0; i < count; i++){
				forecastCards.add(game.infectionDeck.get(i));
				game.infectionDeck.remove(0);
			}
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
				if(s.equals("MutationCard")){
					for (InfectionCard c:forecastCards){
						if(c.city==null){
							//forecastCards.remove(c);
							newInfectionDiscardPile.add(c);
						}
					}
				}
				else{
					for (InfectionCard c:forecastCards){
						if(c.city.name.equals(s)){
							//forecastCards.remove(c);
							newInfectionDiscardPile.add(c);
						}
					}
				}
			}
			for(int i = 0; i < count; i++){
				game.infectionDiscardPile.add(i, newInfectionDiscardPile.get(i));
			}
			removeEventFromHand = true;
			
			/*newInfectionDiscardPile.addAll(game.infectionDiscardPile);
			game.infectionDiscardPile = newInfectionDiscardPile;*/
		}
		//params: PlayerIndex/EventAction/GG/targetCity/CityToRemoveRS
		else if(args[2].equals("GovernmentGrant")) {
			City targetCity = game.getCity(args[3]);
			if (!game.research.isEmpty()){
				targetCity.addResearchStation();
				for(int j = 0; j< game.players.size(); j++){
					ServerComm.sendMessage("AddResearchStation/"+targetCity.name+"/", j);
				}
			}
			else{
				City cityToRemoveRSFrom = game.getCity(args[4]);
				cityToRemoveRSFrom.removeResearchStation();
				targetCity.addResearchStation();
				for(int j = 0; j< game.players.size(); j++){
					ServerComm.sendMessage("RemoveResearchStation/"+cityToRemoveRSFrom.name+"/", j);
				}
				for(int j = 0; j< game.players.size(); j++){
					ServerComm.sendMessage("AddResearchStation/"+targetCity.name+"/", j);
				}
			}
		}
		//index/EventAction/LocalInit/CityName/CityToMoveQFrom
		else if(args[2].equals("LocalInitiative")) {
			City t1 = game.getCity(args[3]);
			if(args.length<5) {
				t1.quarantine = 2;
				game.quarantines--;
				
				//update clients
				for(int j = 0; j< game.players.size(); j++){
					ServerComm.sendMessage("AddQuarantine/"+t1.name, j);
				}
			}
			else {
				City t2 = game.getCity(args[4]);
				t2.quarantine = 0;
				t1.quarantine = 2;
				
				//update clients
				for(int j = 0; j< game.players.size(); j++){
					ServerComm.sendMessage("AddQuarantine/"+t1.name, j);
				}
				for(int j = 0; j< game.players.size(); j++){
					ServerComm.sendMessage("RemoveQuarantine/"+t2.name, j);
				}
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
			for(int j = 0; j< game.players.size(); j++){
					ServerComm.sendMessage(update, j);
				}
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
				for(int j = 0; j< game.players.size(); j++){
					ServerComm.sendMessage("RemoveCube/" + c + "/" + z + "/" + 1 + "/", j);
				}
			}
			
		}
		//params: PlayerIndex/EventAction/ReexaminedResearch/TargetPlayer
		else if(args[2].equals("ReexaminedResearch")) {
			Player targetPlayer = game.getPlayer(args[3]);
			ArrayList<PlayerCard> discardPile = (ArrayList<PlayerCard>) game.playerDiscardPile.clone();
			String q = "ChooseCard/PlayerCard/";
			for(PlayerCard c: discardPile){
				if(c.type == Type.City){
					q = q + c.city.name + ",";
				}
				else{
					//q = q + c.name + ",";
				}
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
				
				for(int j = 0; j< game.players.size(); j++){
					ServerComm.sendMessage("AddCardToHand/" + targetPlayer.username + "/" + cityCardToAddToHand + "/false/", j);
				}
				for(int j = 0; j< game.players.size(); j++){
					ServerComm.sendMessage("RemoveCardFromPlayerDiscard/"+cityCardToAddToHand, j);
				}
			}
			
		}
		//params:PlayerIndex/EventAction/RT/CitiesList(max 2, separa 	ted by ,)/ColorsList(max 2 separated by ,)
		else if(args[2].equals("RemoteTreatment")) {
			String[] citiesList = args[3].split(",");
			String[] colorsList = args[4].split(",");
			for(int i = 0; i < citiesList.length; i++){
				City targetCity = game.getCity(citiesList[i]);
				Color targetColor = Color.valueOf(colorsList[i]);
				targetCity.removeDiseaseCube(targetColor);
				for(int j = 0; j< game.players.size(); j++){
					ServerComm.sendMessage("RemoveCube/" + citiesList[i] + "/" + targetColor.name() + "/" + 1 + "/", j);
				}
			}
			
		}
		//params:PlayerIndex/EventAction/RP/CardToRemove
		else if(args[2].equals("ResilientPopulation")) {
			if(args[3].equals("MutationCard")){
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
			for(int j = 0; j< game.players.size(); j++){
					ServerComm.sendMessage("RemoveInfectionCardFromDiscard/" + args[3], j);
				}
		}
		//params:PlayerIndex/EventAction/SO/AffectedPlayer
		else if(args[2].equals("SpecialOrders")) {
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
						for(int j = 0; j< game.players.size(); j++){
					ServerComm.sendMessage("RemoveCardFromHand"+currentPlayer.username+"/"+c.city.name+"/true/", j);
				}
						break;
					}
				}
			}
			else if(args[3].equals("CharterFlight")){
				for (PlayerCard c: currentPlayer.hand){
					if(c.city==targetPawn.city){
						currentPlayer.hand.remove(c);
						game.playerDiscardPile.add(c);
						for(int j = 0; j< game.players.size(); j++){
					ServerComm.sendMessage("RemoveCardFromHand"+currentPlayer.username+"/"+c.city.name+"/true/", j);
				}
						break;
					}
				}
				targetPawn.move(destination, true);
			}
			for(int j = 0; j< game.players.size(); j++){
					ServerComm.sendMessage("UpdatePlayerLocation/" + targetPlayer.username + "/" + destination.name + "/", j);
				}
			currentPlayer.pawn.actions--;
			for(int j = 0; j< game.players.size(); j++){
					ServerComm.sendMessage("DecrementActions/", j);
				}
		}
		if(removeEventFromHand){
			Player player = game.getPlayer(Integer.parseInt(args[0]));
			PlayerCard toRemove = null;
			for(PlayerCard c:player.hand){
				if(c.type==Type.Event){
					if(c.name.equals(args[2])){
						toRemove = c;
						break;
					}
				}
			}
			if(toRemove != null){
				game.playerDiscardPile.add(toRemove);
				player.hand.remove(toRemove);
				//will send discarded card to DP
				for(int j = 0; j< game.players.size(); j++){
					ServerComm.sendMessage("RemoveCardFromHand/"+player.username+"/"+toRemove.name+"/true/", j);
				}
			}
			else if(player.pawn.role==Role.ContingencyPlanner){
					ContingencyPlannerPawn playerPawn = (ContingencyPlannerPawn)player.pawn;
					if (playerPawn.heldEvent.name.equals(args[2])){
						playerPawn.heldEvent = null;
						for(int j = 0; j< game.players.size(); j++){
					ServerComm.sendMessage("RemoveCardFromStash/", j);
				}
					}
				}
			
			
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
