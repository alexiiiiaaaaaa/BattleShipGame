/** 
 * @author Chanieva Anna-Oeksandra, 2616332
 * @version 1.0
 * project Battleship game
 * class Main contains the main() method, which is an entry point to the program 
 * **/

public class Main {
    /**
     * This is a standard main function for a Java GUI
     */
    public static void main(String[] args) 
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                BattleGui.createAndShowGUI();
            }
        });
    }
}
