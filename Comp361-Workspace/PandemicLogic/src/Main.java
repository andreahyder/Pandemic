
public class Main {

	public static void main(String[] args) {
		//test
		GameManager test = new GameManager();
		
		//3 players open the game, then join the lobby
		test.playerJoin("Bob");
		test.addPlayer("Bob");
		test.playerJoin("Roflcopter");
		test.playerJoin("Chris");
		test.addPlayer("Chris");
		test.addPlayer("Roflcopter");
		
		//3 players ready
		test.toggleReady("Bob");
		test.toggleReady("Roflcopter");
		test.toggleReady("Chris");
		
		//start the game
		test.startGame();
		
		//print out the starting player hands
		System.out.print("Bob: ");
		for(PlayerCard c: test.game.getPlayer("Bob").hand) {	
			System.out.print("["+c.city.name + "] ");
		}
		System.out.println("");
		System.out.print("Chris: ");
		for(PlayerCard c: test.game.getPlayer("Chris").hand) {	
			System.out.print("["+c.city.name + "] ");
		}
		System.out.println("");
		System.out.print("Roflcopter: ");
		for(PlayerCard c: test.game.getPlayer("Roflcopter").hand) {	
			System.out.print("["+c.city.name + "] ");
		}
		System.out.println("");
		System.out.println("");
		
		//print out infection discard pile, and number of disease cubes in corresponding cities
		for(InfectionCard c: test.game.infectionDiscardPile) {
			System.out.println(c.city.name + ": " + c.city.countDiseaseCube(c.city.disease.color) + " cubes");
		}
		System.out.println("");
		
		//(illegal) share knowledge test. Bob steals a card from Roflcopter
		test.shareKnowledge("Bob", "Roflcopter", test.game.getPlayer("Roflcopter").hand.get(0).city.name);
		
		System.out.print("Bob: ");
		for(PlayerCard c: test.game.getPlayer("Bob").hand) {	
			System.out.print("["+c.city.name + "] ");
		}
		System.out.print(" actions left: "+ test.game.getPlayer("Bob").pawn.actions);
		System.out.println("");
		System.out.print("Chris: ");
		for(PlayerCard c: test.game.getPlayer("Chris").hand) {	
			System.out.print("["+c.city.name + "] ");
		}
		System.out.print(" actions left: "+ test.game.getPlayer("Chris").pawn.actions);
		System.out.println("");
		System.out.print("Roflcopter: ");
		for(PlayerCard c: test.game.getPlayer("Roflcopter").hand) {	
			System.out.print("["+c.city.name + "] ");
		}
		System.out.print(" actions left: "+ test.game.getPlayer("Roflcopter").pawn.actions);
		System.out.println("");
		System.out.println("");
	}
}
