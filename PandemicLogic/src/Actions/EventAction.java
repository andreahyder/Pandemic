package Actions;
import Events.*;
import java.util.ArrayList;

public class EventAction implements Action{
	private static ArrayList<Event> EventQueue = initializeEventQueue();
	@Override
	public void execute(String[] args) {
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Event";
	}	
	private static ArrayList<Event> initializeEventQueue(){
		ArrayList<Event> toReturn = new ArrayList<Event>();
		Event aaa = new BorrowedTime();
		toReturn.add(aaa);
		return toReturn;
		
	}
}
