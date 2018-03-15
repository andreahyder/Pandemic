
public class Main {

	public static void main(String[] args) {
		//test
		GameManager test = new GameManager();
		
		//3 players join the game
		test.addPlayer("Bob");
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
		
		for(Disease d: test.game.diseases) {
			System.out.println(d.cubes.size() + " " + d.color + " cubes left.");
		}
		
		//(illegal) share knowledge test. Bob steals a card from Roflcopter
		test.shareKnowledge("Bob", "Roflcopter");
		
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
