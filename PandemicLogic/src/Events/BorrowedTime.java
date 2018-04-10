package Events;
import Server.*;
public class BorrowedTime implements Event {

	@Override
	public void executeEvent(String[] args) {
		Player curPlayer = GameManager.game.getCurrentPlayer();
		Pawn pawn = curPlayer.getPawn();
		pawn.incrementAction(2);
		//TODO send incrementAction to clients

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "BorrowedTime";
	}

}
