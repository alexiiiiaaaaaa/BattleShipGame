
/**
 * BattleGui: 
 * 
 * @author Iain Martin , Chanieva Anna-Oleksandra, 2616332
 * @version 1.0
 * 
 * 
 * Notes to use BattleGui
 *  BattleGuu is intended as a replacement for a Menu class for Battleships.
 *  Comments that start with BATTLEGUI mark where you might 
 *  add your own code. Please do not attempt to use this GUI until
 *  you have already met the minimum requirements of the project.
 * 
 * Notes:
 *  Event handlers have been set up for Menu Options
 *  NewGame, LoadGame and Save Game.
 *  
 *  An Event handler has also been set up for a Mouse Click on
 *  the grid which calls fireShot(row, col).
 *  
 *  To add functionality to this GUI add you code to these functions
 *  which are at the end of this file. 
 *  
 *  Potential additions: FileChoosers could be implemented and the grid characters
 *  could be replaced with graphics by loading gifs or jpgs into the grid which is
 *  created from JButtons.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class BattleGui implements ActionListener 
{
    // Default filename to use for saving and loading files
    // Possible improvement: replace with a FileChooser
    private final static String DEFAULT_FILENAME = "battlegui.txt";
    private final static String BOOM_GIF = "images/Explode.png";
    private final static String WATTER_GIF = "images/water.png";
    private final static String MISSED_SHOT_GIF = "images/missed_shot.png"; 
    
    // defines size of the battleField grid
    private int GRID_SIZE = 10;
    // array of buttons, each button represents a cell on a battlefield
    private JButton [] buttonArray;
    
    // labels to show game info, total and successful hits 
    private JLabel labelInfo;
    private JLabel labelTotalHits;
    private JLabel labelSuccessfulHits;

    // changes the sound setting: on/off
    private JCheckBox changeSoundSetting;

    // row and col arrays for grid marks: 'A B C D E F G H I J' , '1 2 3 4 5 6 7 8 9 10'
    private JLabel [] labelsRowArray;
    private JLabel [] labelsColArray;

    // battle field object
    private BattleField battleField;

    private Player player;
    private Sound sound;

    /** 
     * constructor BattleGui() - creates new player, battle field, new sound objects
    **/
    public BattleGui()
    {
        player = new Player();
        battleField = new BattleField();
        sound = new Sound();
        System.out.println("BattleGui constructor invoked");
    }

    public JMenuBar createMenu() 
    {
        JMenuBar menuBar  = new JMenuBar();;
        JMenu menu = new JMenu("Battle Menu");
        JMenuItem menuItem;
       
        menuBar.add(menu);

        // A group of JMenuItems. You can create other menu items here if desired
        menuItem = new JMenuItem("New Game");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Load Game");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Save Game");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //a submenu
        menu.addSeparator();

        menuItem = new JMenuItem("About");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        return menuBar;
    }

    /** layout has: 1) 1st row: labels required for a game statistics output and Sound enabled checkbox, 
     *             2) 2nd row: labelsRowArray to dispaly the row coordinates 'A,B,C,D,E,F,G,H,I,J'
     *             3) Zero collumn: labelsColArray to display the col coordinates '1,2,3,4,5,6,7,8,9,10'
     *             4) square grid of Jbuttons to display a battleField cells
     * We come up to a grid layout size: (GRID_SIZE + 2, GRID_SIZE + 1), where GRID_SIZE is a battlefield grid size, usually equals 10   
     * Decided to use GridBagLayout because first row contains only 4 elements, while other rows contains GRID + 1 elements
     * @return Container
     **/
    public Container createContentPane() 
    {
        JPanel grid = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        labelInfo = new JLabel("Select New game to start!");
        labelInfo.setHorizontalAlignment(JLabel.CENTER);
        labelInfo.setHorizontalTextPosition(JLabel.CENTER);        
        labelInfo.setForeground(Color.BLUE);
        //c.fill = GridBagConstraints.BOTH;
        c.ipady = 20;
        c.ipadx = 100; 
        //c.weightx = 0.5;
        c.gridwidth = 3;    //3 collumn wide  
        c.gridx = 0;        // 0th position in the grid
        c.gridy = 0;
        grid.add(labelInfo,c);

        labelTotalHits = new JLabel("Total hits: " + player.getTotalHits());
        labelTotalHits.setHorizontalAlignment(JLabel.CENTER);
        labelTotalHits.setHorizontalTextPosition(JLabel.CENTER);
        //c.fill = GridBagConstraints.BOTH;
        c.ipady = 20;
        //c.ipadx = 0;
        //c.weightx = 0.5;
        c.gridwidth = 3;    //3 columns wide
        c.gridx = 3;        //3rd position in the grid
        c.gridy = 0;
        grid.add(labelTotalHits,c);

        labelSuccessfulHits = new JLabel("Success hits: " + player.getSuccessfulHits());
        labelSuccessfulHits.setHorizontalAlignment(JLabel.CENTER);
        labelSuccessfulHits.setHorizontalTextPosition(JLabel.CENTER);
        //c.fill = GridBagConstraints.BOTH;
        c.ipady = 20;
        //c.ipadx = 0;
        //c.weightx = 0.5;
        c.gridwidth = 3;    //3 columns wide
        c.gridx = 6;        //6th position in the grid 
        c.gridy = 0;
        grid.add(labelSuccessfulHits,c);

        // Add a Sound on/off checkbox 
        changeSoundSetting = new JCheckBox("Sound");
        changeSoundSetting.setSelected(true);
        changeSoundSetting.setHorizontalAlignment(JCheckBox.CENTER);
        changeSoundSetting.setHorizontalTextPosition(JCheckBox.RIGHT);
        changeSoundSetting.setActionCommand("Sound");
        changeSoundSetting.addActionListener(this);
        //c.fill = GridBagConstraints.BOTH;
        c.ipady = 20;
        //c.ipadx = 0;
        //c.weightx = 0.5;
        c.gridwidth = 2;    //2 columns wide
        c.gridx = 9;        //9th - position in the grid 
        c.gridy = 0;
        grid.add(changeSoundSetting,c);

        int numLayoutComponents = (GRID_SIZE + 1) * (GRID_SIZE + 1);

        //one row of labels to dispaly the row coordinates 'A,B,C,D,E,F,G,H,I,J'
        labelsRowArray = new JLabel[GRID_SIZE + 1];
        //one col of labels to display the col coordinates '1,2,3,4,5,6,7,8,9,10' 
        labelsColArray = new JLabel[GRID_SIZE + 1]; 
        
        // buttons array for the batlefield cells 
        int numButtons = GRID_SIZE * GRID_SIZE;
        buttonArray = new JButton[numButtons];
        ImageIcon buttonIcon = createImageIcon(WATTER_GIF);

        char[] gridTitleLine = "0ABCDEFGHIJ".toCharArray();
        
        // index for the buttons array, required because grid for the square part of the layout is GRID_SIZE+1, 
        // so we can't use index from the 'for' loop below
        int btnIndex = 0;
        
        int row, col;
        for (int i=0; i < numLayoutComponents; i++)
        {
            row = i / (GRID_SIZE +1);
            col = i % (GRID_SIZE +1);
            if ( (row == 0) && (col == 0)) {
                labelsRowArray[col] = new JLabel(String.valueOf(""));
                labelsRowArray[col].setHorizontalAlignment(JLabel.CENTER);
                labelsRowArray[col].setHorizontalTextPosition(JLabel.CENTER);
                //c.fill = GridBagConstraints.BOTH;
                c.ipady = 20;
                c.ipadx = 100;
                c.gridwidth = 1;   //1 columns wide
                //c.weightx = 0.5;
                c.gridx = col;
                c.gridy = row + 1;  // row+1 because 0th row is filled by the statistic labels and sound checkbox  
                grid.add(labelsRowArray[col], c); 
                continue;
            }
            else if (row == 0) {
                labelsRowArray[col] = new JLabel(String.valueOf(gridTitleLine[col]));
                labelsRowArray[col].setHorizontalAlignment(JLabel.CENTER);
                labelsRowArray[col].setHorizontalTextPosition(JLabel.CENTER);
                //c.fill = GridBagConstraints.BOTH;
                c.ipady = 20;
                c.ipadx = 30;
                c.gridwidth = 1;   //1 columns wide
                //c.weightx = 0.5;
                c.gridx = col;
                c.gridy = row + 1;                
                grid.add(labelsRowArray[col], c);
                continue;
            } 
            else if (col == 0) {
                labelsColArray[row] = new JLabel(String.valueOf(row) );
                labelsColArray[row].setHorizontalAlignment(JLabel.CENTER);
                labelsColArray[row].setHorizontalTextPosition(JLabel.CENTER);
                //c.fill = GridBagConstraints.BOTH;
                c.ipady = 20;
                c.ipadx = 100;
                c.gridwidth = 1;   //1 columns wide
                //c.weightx = 0.5;
                //c.weighty = 1;
                c.gridx = col;
                c.gridy = row + 1;  
                grid.add(labelsColArray[row], c);
                continue; 
            }

            buttonArray[btnIndex] = new JButton("", buttonIcon);
            //buttonArray[btnIndex] = new JButton("" + btnIndex);
            //buttonArray[btnIndex].setMargin(new Insets(0, 0, 0, 0));

			// This label is used to identify which button was clicked in the action listener
            buttonArray[btnIndex].setActionCommand("" + btnIndex); // String "0", "1" etc.
            buttonArray[btnIndex].addActionListener(this);
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets(0,0,0,0);  //top padding
            c.ipady = 20;
            c.ipadx = 20;
            c.gridwidth = 1;   //1 columns wide
            c.weightx = 0.5;
            c.weighty = 0.5;
            c.gridx = col;
            c.gridy = row + 1;
            grid.add(buttonArray[btnIndex], c);
            
            btnIndex++; 
        }
        return grid;
    }

    /**
     * This method handles events from the Menu and the board.
     *@return void
     **/
    public void actionPerformed(ActionEvent e) 
    {
        String classname = getClassName(e.getSource());
        //JComponent component = (JComponent)(e.getSource());
    
        if (classname.equals("JMenuItem"))
        {
            JMenuItem menusource = (JMenuItem)(e.getSource());
            String menutext  = menusource.getText();
            
            // Determine which menu option was chosen
            if (menutext.equals("Load Game"))
            {
                /* BATTLEGUI    Add your code here to handle Load Game **********/
                LoadGame();
            }
            else if (menutext.equals("Save Game"))
            {
                /* BATTLEGUI    Add your code here to handle Save Game **********/
                SaveGame();
            }
            else if (menutext.equals("New Game"))
            {
                /* BATTLEGUI    Add your code here to handle Save Game **********/
                NewGame();
            }
            else if (menutext.equals("About"))
            {
                /* BATTLEGUI    Add your code here to handle Save Game **********/
                showAboutDialog();
            }
        }
        // Handle the event from the user clicking on a command button
        else if (classname.equals("JButton")) {
            JButton button = (JButton)(e.getSource());

            int bnum = Integer.parseInt(button.getActionCommand());
            int row = bnum / GRID_SIZE;
            int col = bnum % GRID_SIZE;
                    
            /* BATTLEGUI    Add your code here to handle user clicking on the grid ***********/
            fireShot(bnum, row, col);
        }  else if (classname.equals("JCheckBox")) {    
            JCheckBox checkBox = (JCheckBox)(e.getSource());
            
            if (checkBox.getActionCommand() == "Sound") {
                if (checkBox.isSelected()) {
                    Sound.setSoundOn(true);
                } else {
                    Sound.setSoundOn(false);
                }
            }
        } 
    }
    
    /**
     *  Returns the class name
     * @return String
     **/
    protected String getClassName(Object o) 
    {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex+1);
    }

    /**
     * Create the GUI and show it.
     * For thread safety, this method should be invoked from the event-dispatching thread.
     * @return void
     **/
    public static void createAndShowGUI() 
    {
        // Create and set up the window.
        JFrame frame = new JFrame("Battleships");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane.
        BattleGui battlegui = new BattleGui();
        frame.setJMenuBar(battlegui.createMenu());
        frame.setContentPane(battlegui.createContentPane());
        
        // Display the window, setting the size
        frame.setSize(750, 800);
        //frame.setResizable(false);
        frame.setVisible(true);
    }
    
    /**
     * Sets a Gui grid square at row, col to display a character
     * @return boolean
     **/
    public boolean setGuiSquare(int row, int col, char c)
    {
        int bnum = row * GRID_SIZE + col;
        if (bnum >= (GRID_SIZE*GRID_SIZE))
        {
            return false;
        }
        else
        {
            buttonArray[bnum].setText(Character.toString(c));
        }
        return true;
    }
    

    //************************************************************************
    //*** BATTLEGUI: Modify the methods below to respond to Menu and Mouse click events   
    /**
     * This method is called from the Menu event: New Game.
     * BATTLEGUI
     * @return void
     **/
    public void NewGame()
    {
        System.out.println("");
        System.out.println("New Game selected");

        this.setDefaultGridButtonIcons();
        
        player.clearHitMapArray();
        
        // reset value of succesful hits 
        player.setSuccessfulHits(0);
        labelSuccessfulHits.setText("Success hits: " + player.getSuccessfulHits());

        //reset value of total hits
        player.setTotalHits(0);
        labelTotalHits.setText("Total hits: " + player.getTotalHits());

        battleField.cleanBattleField();        
        battleField.addShipsToBattlefield();
        labelInfo.setText("New Game started!");
    }
    
    /**
     * This method is called from the Menu event: Load Game.
     * BATTLEGUI
     * @return void
     **/
    public void LoadGame()
    {
        System.out.println("");
        System.out.println("Load game selected from the file " + DEFAULT_FILENAME);
        
        String line;
        try 
        {
            FileReader fr = new FileReader(DEFAULT_FILENAME);        
            BufferedReader buffReader  = new BufferedReader(fr);
            
            line = buffReader.readLine();
            System.out.println("Reading " + line);
            for (int i=0; i<battleField.getBattleField().length; i++){
                line = buffReader.readLine();
                System.out.print("Reading " + line + " and ");
                battleField.setBattleFieldByIndex(i, Integer.parseInt(line));
            }
            System.out.println("");
            
            line = buffReader.readLine(); // Text: Player Save:
            System.out.println("Reading " + line);

            line = buffReader.readLine(); // Text: Player's TotalHits:
            System.out.println("Reading " + line);

            line = buffReader.readLine(); // Value: Player's TotalHits
            player.setTotalHits(Integer.parseInt(line));
            System.out.println("Player's TotalHits = " + line);

            line = buffReader.readLine(); // Text: Player's SuccesfulHits:
            System.out.println("Reading " + line);
            
            line = buffReader.readLine(); // Value: Player's SuccesfulHits
            player.setSuccessfulHits(Integer.parseInt(line));
            System.out.println("Player's SuccesfulHits: = " + line);
            
            line = buffReader.readLine(); // Text: Player's hitMapArray:
            System.out.println("Reading " + line);

            for (int i=0; i < player.getHitMapArray().length; i++)
            {
                line = buffReader.readLine();
                System.out.print("Reading " + line + " and ");
                player.setHitMapArrayValueByIndex(i, Integer.parseInt(line));
            }
            System.out.println("");

            buffReader.close();
            System.out.println("Game loaded succesfully from the file: " + DEFAULT_FILENAME);
        }
        catch(Exception fe)
        {
            System.out.print(fe);
        }

        updateLabels();
        updateGridButtonIcons();
        labelInfo.setText("Game loaded!");
    }
    
    
    /**
     * This method is called from the Menu event: Save Game.
     * BATTLEGUI
     * @return void
     **/
    public void SaveGame()
    {
        System.out.println("");
        System.out.println("Save game selected");

        try
        {
            FileOutputStream fos = new FileOutputStream(DEFAULT_FILENAME, false);
            PrintWriter pw = new PrintWriter(fos);
            
            //Writing
            pw.println("BattleField Save:");
            for (int i=0; i<battleField.getBattleField().length; i++){
                pw.println(battleField.getBattleField()[i]);
            }

            pw.println("Player Save:");
            
            pw.println("Player's TotalHits:");
            pw.println(player.getTotalHits());
            
            pw.println("Player's SuccesfulHits:");
            pw.println(player.getSuccessfulHits());

            pw.println("Player's hitMapArray:");
            for (int i=0; i<player.getHitMapArray().length; i++){
                pw.println(player.getHitMapArrayValueByIndex(i));
            }

            pw.close();
            System.out.println("File "+ DEFAULT_FILENAME + " written successfully");
        }
        catch(FileNotFoundException e)
        {
            
            System.out.println(e.getMessage());
        }

        labelInfo.setText("Game saved!");
    }
    
    /**
     * This method is called from the Mouse Click event.
     * Updated the method to get one more int param, so can get the buttonArray index and change button's image correspondingly 
     * @return void  
     **/
    public void fireShot(int num, int row, int col)
    {
        System.out.println("Fire shot selected: at (" + row + ", " + col + ")");
        
        ImageIcon buttonIcon;       
        if (battleField.getBattleField()[num] == battleField.BUSY)
        {
            // on a first succesful hit to the cell: update hitMapArray, totalHits, succesfulHits and buttonImage 
            if (player.getHitMapArrayValueByIndex(num) == Player.MISS)
            {
                player.setHitMapArrayValueByIndex(num, Player.HIT);
                player.updateSuccesfulHitsBy(1);
                labelSuccessfulHits.setText("Success hits: " + player.getSuccessfulHits());
                player.updateTotalHitsBy(1);
                labelTotalHits.setText("Total hits: " + player.getTotalHits());
                
                // changes the image for the clicked button to the BOOM!
                buttonIcon = createImageIcon(BOOM_GIF);
                buttonArray[num].setIcon(buttonIcon);
                labelInfo.setText("Successful hit to the ship!");

                System.out.println("Successful hit to the ship at (" + row + ", " + col + ")!");
                
                // check if player won the game and display a congratulations dialog!   
                if (player.isGameWon()) {
                    String msg =    "Congratulations you've won the BattleShip game! \n" + 
                                    "Total hits: " + player.getTotalHits() + ", " +
                                    "Succesfful hits: " + player.getSuccessfulHits() + "\n" + 
                                    "Please start a new game or load previously saved game!";
                    sound.play(Sound.SOUND_WIN);
                    showInfoDialog(msg);
                } else {
                    sound.play(Sound.SOUND_EXPLODE);
                }
            }

            // on unsuccessful hit to the cell: update hitMapArray, totalHits, buttonImage
        } else if (player.getHitMapArrayValueByIndex(num) == Player.MISS){
                player.setHitMapArrayValueByIndex(num, Player.HIT);
                player.updateTotalHitsBy(1);
                labelTotalHits.setText("Total hits: " + player.getTotalHits());

                // changes the image for the clicked button to the MISSED_SHOT!
                buttonIcon = createImageIcon(MISSED_SHOT_GIF); 
                buttonArray[num].setIcon(buttonIcon);
                sound.play(Sound.SOUND_MISS);
                labelInfo.setText("Missed!");

                System.out.println("Missed hit at (" + row + ", " + col + "), try again!");
            }
    }

    /** 
     * Returns an ImageIcon, or null if the path was invalid. 
     * Example is taken from the Java(TM) Tutorials: https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/components/button.html 
     * @return ImageIcon
    **/
    protected ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = BattleGui.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /** 
     * void setDefaultGridButtonIcons() - set default button icons to WATTER_GIF
     * @return void
     **/
    public void setDefaultGridButtonIcons()
    {
        ImageIcon buttonIcon = createImageIcon(WATTER_GIF);
        for (int i=0; i< buttonArray.length; i++){
            buttonArray[i].setIcon(buttonIcon);
        }
        System.out.println("All Button images are set to default WATTER_GIF");
    }

     /** 
     * void updateLabels() - uptade hits labels in the UI
     * @return void
     **/
    public void updateLabels()
    {
        labelTotalHits.setText("Total hits: " + player.getTotalHits());
        labelSuccessfulHits.setText("Success hits: " + player.getSuccessfulHits());
    }
    
     /** 
     *  void updateGridButtonIcons() - uptade button icons in the UI
     * @return void
     **/
    public void updateGridButtonIcons()
    {
        ImageIcon buttonIconWatter = createImageIcon(WATTER_GIF);
        ImageIcon buttonIconMissedShot = createImageIcon(MISSED_SHOT_GIF);
        ImageIcon buttonIconBoom = createImageIcon(BOOM_GIF);

        for (int i=0; i < buttonArray.length; i++){
            if ( (battleField.getBattleField()[i] == 1) && (player.getHitMapArrayValueByIndex(i) == 1)){
                buttonArray[i].setIcon(buttonIconBoom);
            } 
            else if ( (battleField.getBattleField()[i] == 0) && (player.getHitMapArrayValueByIndex(i) == 1)){
                buttonArray[i].setIcon(buttonIconMissedShot);
            } 
            else {
                buttonArray[i].setIcon(buttonIconWatter);
            }
        }
        System.out.println("All Grid Button images are updated based on the loaded data");
    }

     /** 
     *  void showAboutDialog() - show about dialog
     * @return void
     **/
    public void showAboutDialog() {
        final String msg = "Battleship Game v1.0\n" + 
        "Created by Chanieva Anna-Oleksandra\n" +
        "Student: 2616332\n" +
        "Introduction to Software Development\n" +
        "University of Dundee";
        showInfoDialog(msg);
    }

     /** 
     *  void showInfoDialog(String msg) - show info dialog
     * @return void
     **/
    public static void showInfoDialog(String msg) {
        new Thread(new Runnable() {
            public void run() {
                JOptionPane.showMessageDialog(null, msg, "BatleShips", JOptionPane.INFORMATION_MESSAGE);
            }
        }).start();
    }
}
