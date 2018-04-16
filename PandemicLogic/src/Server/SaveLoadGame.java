package C361serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class SaveLoadGame {
	
    private static File saveFile;
    private static FileOutputStream fileOut;
    private static File[] allSavedGameFiles = new File[20]; //We can have 20 saved games.
    private static int numSavedGames = 0;
    
    public static void main(String[] args) {
    	
    	Game tester = new Game("Name", new Cool303SummerTheme());
    	File savedIn = saveGame(tester);
    	//Game created = loadGameFromFile(savedIn);
    	for (int i=0; i<numSavedGames; i++) {
    		System.out.println(i + " : " + allSavedGameFiles[i].toString());
    	}
    	
    	Game tester2 = new Game("Name", new Cool303SummerTheme());
    	File savedIn2 = saveGame(tester2);
    	//Game created = loadGameFromFile(savedIn);
    	for (int i=0; i<numSavedGames; i++) {
    		System.out.println(i + " : " + allSavedGameFiles[i].toString());
    	}
    	
    	Game testerOld = loadGameFromFile(allSavedGameFiles[1]);
    	saveGame(testerOld);
    	for (int i=0; i<numSavedGames; i++) {
    		System.out.println(i + " : " + allSavedGameFiles[i].toString());
    	}
    	
    	Game tester3 = new Game("Name", new Cool303SummerTheme());
    	File savedIn3 = saveGame(tester3);
    	//Game created = loadGameFromFile(savedIn);
    	for (int i=0; i<numSavedGames; i++) {
    		System.out.println(i + " : " + allSavedGameFiles[i].toString());
    	}
    	
    }
	
	
	public static Game loadGameFromFile(File file) {   

        FileInputStream fileIn;
        Game GameFromSaved = null;
		try {
			fileIn = new FileInputStream(file);
            ObjectInputStream objectStream = new ObjectInputStream(fileIn);   

            GameFromSaved = (Game) objectStream.readObject();
            
            JOptionPane.showConfirmDialog(null, 
                    "Load Game successful.", 
                    "Pandemic - Load Game",   
                    JOptionPane.DEFAULT_OPTION); 
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, 
                    e.toString() + "\nFail to load Game.",   
                    "Pandemic - Load Game", 
                    JOptionPane.DEFAULT_OPTION);   
		}   
		//System.out.println(GameFromSaved.toString());
		return GameFromSaved;
    }
	
	public static File saveGame(Game Game) {
    	if (Game.beenSaved == false) {
        	try {   
        		String saveName = "game" + (numSavedGames+1);
        		saveFile = new File(saveName);
        		fileOut = new FileOutputStream(saveFile);
        		ObjectOutputStream objectStream = new ObjectOutputStream(fileOut);   

                objectStream.writeObject(Game); //Game state NOT window (CHANGE)
                
                objectStream.close();   
                fileOut.close();   

                JOptionPane.showConfirmDialog(Game, 
                    "First save Game state successfully.", 
                    "Pandemic - Save Game",   
                    JOptionPane.DEFAULT_OPTION); 
            } catch (Exception e) {   
                JOptionPane.showConfirmDialog(Game, 
                    e.toString() + "\nFail to save Game state.",   
                    "Pandemic - Save Game", 
                    JOptionPane.DEFAULT_OPTION);   
            } 
        	Game.beenSaved = true; 	//Now the Game has been saved once, so change the boolean
        	// Now add the file to the list of savedGameFiles:
    		allSavedGameFiles[numSavedGames] = saveFile;
    		Game.posInSavedArray = numSavedGames;
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

                objectStream.writeObject(Game); //Game state NOT window (CHANGE)
                
                objectStream.close();   
                fileOut.close();   

                JOptionPane.showConfirmDialog(null, 
                    "Not first save Game state successfully.", 
                    "Pandemic - Save Game",   
                    JOptionPane.DEFAULT_OPTION); 
            } catch (Exception e) {   
                JOptionPane.showConfirmDialog(null, 
                    e.toString() + "\nFail to save Game state.",   
                    "Pandemic - Save Game", 
                    JOptionPane.DEFAULT_OPTION);   
            } 
    		// Now override the file in the list of savedGameFiles:
    		allSavedGameFiles[Game.posInSavedArray] = saveFile;
    		System.out.println("Overidden!");
    	}
    	return saveFile;
    }

}
