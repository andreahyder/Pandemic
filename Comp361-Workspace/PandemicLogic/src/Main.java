
public class Main {

	public static void main(String[] args) {
		GameManager test = new GameManager();
		test.playerJoin("Bob");
		test.addPlayer("Bob");
		test.playerJoin("Roflcopter");
		test.playerJoin("Chris");
		test.addPlayer("Chris");
		test.addPlayer("Roflcopter");
		test.startGame();
		System.out.println(test.game.getPlayer("Bob").hand.get(1).city.name);
		System.out.println(test.game.playerDeck.size());
		Player p = test.game.players.remove(0);
		System.out.println(p.username);
		System.out.println(test.game.players.get(0).username);
	}
}
