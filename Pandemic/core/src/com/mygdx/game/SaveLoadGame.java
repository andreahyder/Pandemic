package com.mygdx.game;

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
    private static File[] allSavedGameFiles = new File[20]; //We can have 20 saved Games.
    private static int numSavedGames = 0;
	
	public static GameScreen loadGameFromFile(File file) {   

        FileInputStream fileIn;
        GameScreen GameFromSaved = null;
		try {
			fileIn = new FileInputStream(file);
            ObjectInputStream objectStream = new ObjectInputStream(fileIn);   

            GameFromSaved = (GameScreen) objectStream.readObject();
            
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
	
	public static File saveGame(GameScreen Game) {
    	if (Game.beenSaved == false) {
        	try {   
        		String saveName = "Game" + (numSavedGames);
        		saveFile = new File(saveName);
        		fileOut = new FileOutputStream(saveFile);
        		ObjectOutputStream objectStream = new ObjectOutputStream(fileOut);   

                objectStream.writeObject(Game); //Game state NOT window (CHANGE)
                
                objectStream.close();   
                fileOut.close();   

            } catch (Exception e) {   
               e.printStackTrace(); 
            } 
        	Game.beenSaved = true; 	//Now the Game has been saved once, so change the boolean
        	// Now add the file to the list of savedGameFiles:
    		allSavedGameFiles[numSavedGames] = saveFile;
    		Game.numSaves = numSavedGames;
    		numSavedGames ++; 	//add 1 to number of saved Games
    		System.out.println(Game.numSaves);
    	}
    	else {  // so if it's already been saved before:
    		// Erase contents of saveFile before rewritting to it:
    		String saveName = "Game" + (Game.numSaves+1);
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
    		allSavedGameFiles[Game.numSaves] = saveFile;
    		System.out.println("Overidden!");
    	}
    	return saveFile;
    }

}
