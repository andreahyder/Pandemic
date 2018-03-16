
public class Main {

	public static void main(String[] args) {
		//GameManager
		
		//3 players join the game
		GameManager.AddPlayer("Bob");
		GameManager.AddPlayer("Chris");
		GameManager.AddPlayer("Roflcopter");
		
		//3 players ready
		GameManager.ToggleReady(2);
		GameManager.ToggleReady(0);
		GameManager.ToggleReady(1);
		
		//print out the starting player hands
		System.out.print("Bob: ");
		for(PlayerCard c: GameManager.game.getPlayer("Bob").hand) {	
			System.out.print("["+c.city.name + "] ");
		}
		System.out.println("");
		System.out.print("Chris: ");
		for(PlayerCard c: GameManager.game.getPlayer("Chris").hand) {	
			System.out.print("["+c.city.name + "] ");
		}
		System.out.println("");
		System.out.print("Roflcopter: ");
		for(PlayerCard c: GameManager.game.getPlayer("Roflcopter").hand) {	
			System.out.print("["+c.city.name + "] ");
		}
		System.out.println("");
		System.out.println("");
		
		//print out infection discard pile, and number of disease cubes in corresponding cities
		for(InfectionCard c: GameManager.game.infectionDiscardPile) {
			System.out.println(c.city.name + ": " + c.city.countDiseaseCube(c.city.disease.color) + " cubes");
		}
		System.out.println("");
		
		for(Disease d: GameManager.game.diseases) {
			System.out.println(d.cubes.size() + " " + d.color + " cubes left.");
		}
		
		//(illegal) share knowledge GameManager. Bob steals a card from Roflcopter
		GameManager.ShareKnowledge("Bob", "Roflcopter");
		
		System.out.print("Bob: ");
		for(PlayerCard c: GameManager.game.getPlayer("Bob").hand) {	
			System.out.print("["+c.city.name + "] ");
		}
		System.out.print(" actions left: "+ GameManager.game.getPlayer("Bob").pawn.actions);
		System.out.println("");
		System.out.print("Chris: ");
		for(PlayerCard c: GameManager.game.getPlayer("Chris").hand) {	
			System.out.print("["+c.city.name + "] ");
		}
		System.out.print(" actions left: "+ GameManager.game.getPlayer("Chris").pawn.actions);
		System.out.println("");
		System.out.print("Roflcopter: ");
		for(PlayerCard c: GameManager.game.getPlayer("Roflcopter").hand) {	
			System.out.print("["+c.city.name + "] ");
		}
		System.out.print(" actions left: "+ GameManager.game.getPlayer("Roflcopter").pawn.actions);
		System.out.println("");
		System.out.println("");
		
		String s = null;
		if(s == null) {
			System.out.println("ll");
		}
	}
}
