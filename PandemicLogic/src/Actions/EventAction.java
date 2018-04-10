package Actions;
import Events.*;
import Server.GameManager;

import java.util.ArrayList;

public class EventAction implements Action{
	//private static ArrayList<Event> EventQueue = initializeEventQueue();
	@Override
	public void execute(String[] args) {
		/*for (Event e: EventQueue){
			if (e.getName().equals(args[2])){
				e.executeEvent(args);
			}
		}*/
		GameManager.Event(args);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "EventAction";
	}	
	/*private static ArrayList<Event> initializeEventQueue(){
		ArrayList<Event> toReturn = new ArrayList<Event>();
		toReturn.add(new Airlift());
		toReturn.add(new BorrowedTime());
		toReturn.add(new CommercialTravelBan());
		toReturn.add(new Forecast());
		toReturn.add(new GovernmentGrant());
		toReturn.add(new MobileHospital());
		toReturn.add(new NewAssignment());
		toReturn.add(new OneQuietNight());
		toReturn.add(new RapidVaccineDeployment());
		toReturn.add(new ReexaminedResearch());
		toReturn.add(new RemoteTreatment());
		toReturn.add(new ResilientPopulation());
		toReturn.add(new SpecialOrders());
		return toReturn;
		
	}*/
}
