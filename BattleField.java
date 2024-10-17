
import java.util.Random;

/** 
 * @author Chanieva Anna-Oeksandra, 2616332
 * @version 1.0
 * project Battleship game
 * BattleField class contains fields and methods to create and update the battlefield and add ships
 * **/

public class BattleField {
    //TODO: possible feature - ability to change ships layout on a battle field,
    //note that method isPlaceAvailable(int place, int shipSize) need to be ajusted for that    
    //private final static int HORIZONTALLY = 1;
    //private final static int VERTICALLY = 0;

    // EMPTY - const for an empty (available to place a ship) cell in a battlefield 
    public static final int EMPTY = 0;
    // BUSY = const for an busy (where is not possible to place ship there) cell in a battlefield
    public static final int BUSY = 1;

    // Grid size is 10, so BATTLE_FIELD_SIZE = 10*10 
    private int GRID_SIZE = 10;
    private int BATTLE_FIELD_SIZE = GRID_SIZE*GRID_SIZE;
    private int[] battleField;
    
    private Ship submarine;
    private Ship destroyer;
    private Ship cruiser;
    private Ship battleship;

    /** 
     * constuctor which initialies battleField field
     * **/
    public BattleField()
    {
        battleField = new int[BATTLE_FIELD_SIZE];
        System.out.println("Battlefield created");
    }

    /** 
     * int[] getBattleField() - method returns battleField array 
     * @return int[]
     * **/
    public int[] getBattleField()
    {
        return this.battleField;
    }

    /** 
     * void setBattleFieldByIndex(int index, int value) - method set battle field value by index 
     * @return void
     *  **/
    public void setBattleFieldByIndex(int index, int value)
    {
        this.battleField[index] = value;
        System.out.println("Set battleField[" + index + "] = " + value); 
    }

    /** 
     * void cleanBattleField() - method which cleans battle field, set all values to empty
     * @return void 
     * **/
    public void cleanBattleField()
    {
        for (int i=0; i<battleField.length;i++) {
            battleField[i] = EMPTY;
        }
        System.out.println("Battlefield cleared, all values set to 0");
    }

    /** 
     * boolean addShip(Ship ship, int place) - add a ship to battle field, returns true if successful 
     * @return boolean 
     * **/
    public boolean addShip(Ship ship, int place)
    {
        // isPlaced = true if ship was added to the battlefield succesfully
        // by default set to false  
        boolean isPlaced = false;
        if ( ! isPlaceAvailable(place, ship.getShipSize())) {
            return isPlaced;
        }
        
        for (int i=0; i < ship.getShipSize(); i++)  
        {
            // to place ships horizontally use battleField[] = [place + i]
            battleField[place + i] = BUSY;
            
            //to place ships vertically use  battleField index = [place + i*GRID_SIZE]
            //but also note, that method isPlaceAvailable(int place, int shipSize) need to be changed as well to support that 
            //battleField[place + i*GRID_SIZE] = 1;
        }
        System.out.println("Info: The " + ship.getShipType() + " ship, size " + ship.getShipSize() + "  added at " + place);
        isPlaced = true;

        return isPlaced;
    }

    /**
     * isPlaceAvailable(int place, int shipSize) checks if there is a place for the selected shipSize in a battlefield 
     * this method was the most challenging to write and maybe it has some undiscovered defects. 
     * Probably there are better ways to do it or I need to find out a better model for the battlefield so checks will be simpler.
     * It has multiple if's which are checking if places for the shipSize are busy in both horizontal and vertical directions. 
     * Note: it is written to place ships horizontally. For the vertical ships placement - it needs to be changed/updated. 
     * @return boolean    
     **/
    public boolean isPlaceAvailable(int place, int shipSize)
    {
        boolean available = true;

        // check if place is within range [0 .. BATTLE_FIELD_SIZE]
        if ( ( place < 0 ) || ( place > BATTLE_FIELD_SIZE ) ) {
            System.out.println("Error: The place "+ place +" is out of battleField boundaries: " + 
                                        "0 ... " + BATTLE_FIELD_SIZE);
            available = false;
            return available;
        } 
        
        //calc row, col based on a place for the ship
        int row = place / GRID_SIZE;
        int col = place % GRID_SIZE;
        
        //checks if there is a place for the ship size to the right 
        if ((col + shipSize) > (GRID_SIZE-1)) {
            System.out.println("Error: the Ship size "  + shipSize + " can't be located at " 
                                        + place + " due to a GRID boundaries horizontally");
            available = false;
            return available;
        }

        // to restore array index from the coordinates: row * GRID_SIZE + col;
        // checks if there is another ship in the col + shipSize + 1 range horizontally  
        if ( col == 0 ) {
            if (battleField[row * GRID_SIZE + col + shipSize + 1] == BUSY) {
                System.out.println("Error: the Ship size "  + shipSize + " can't be located at " 
                                            + place + " due to another ship nearby horizontally ");
                available = false;
                return available;
            }
        }

        // ****checks if there is another ship in the col - 1 and col + shipSize + 1 range horizontally  
        if ((col > 0) && (col < (GRID_SIZE-shipSize-1))) {
            if ((battleField[row * GRID_SIZE + col - 1] == BUSY) || (battleField[row * GRID_SIZE + col + shipSize + 1] == BUSY)) {
                System.out.println("Error: the Ship size "  + shipSize + " can't be located at " 
                                            + place + " due to another ship nearby horizontally ");
                available = false;
                return available;
            }
        }

        // ****checks if there is another ship in the col - 1 range horizontally  
        if ( col == (GRID_SIZE-shipSize-1) ) {
            if ( battleField[row * GRID_SIZE + col - 1] == BUSY ) {
                System.out.println("Error: the Ship size "  + shipSize + " can't be located at " 
                                            + place + " due to another ship nearby horizontally ");
                available = false;
                return available;
            }
        }

        // if place is in a zero row, checks if there is another ship in a row + 1 (i.e. vertically one row below) 
        if ( row == 0 ) {
            for ( int i=0; i < shipSize; i++ ) {
                if (battleField[(row + 1) * GRID_SIZE + col + i] == BUSY) {
                    System.out.println("Error: the Ship size "  + shipSize + " can't be located at " 
                                                + place + " due to another ship nearby vertically");
                    available = false;
                    return available;
                }
            }
        }

        // if place is between zero and last Grid rows, checks for other ships in row - 1, row + 1 (i.e. vertically one row above and one row below) 
        if ((row > 0) && (row < (GRID_SIZE-1) )) {
            for ( int i=0; i < shipSize; i++ ) {
                if ((battleField[(row - 1) * GRID_SIZE + col + i] == BUSY) || (battleField[(row + 1) * GRID_SIZE + col + i] == BUSY)) {
                    System.out.println("Error: the Ship size "  + shipSize + " can't be located at " 
                                                + place + " due to another ship nearby vertically");
                    available = false;
                    return available;
                }
            }
        }

        // if place is in a last Grid row, checks if there is another ship in a row - 1 (i.e. vertically one row above) 
        if ((row == (GRID_SIZE-1) )) {
            for ( int i=0; i < shipSize; i++ ) {
                if ((battleField[(row - 1) * GRID_SIZE + col + i] == BUSY)) {
                    System.out.println("Error: the Ship size "  + shipSize + " can't be located at " 
                                                + place + " due to another ship nearby vertically");
                    available = false;
                    return available;
                }
            }
        }

        // checks if there is a free space for entire ship size on the row (horizontally)  
        for ( int i=0; i < shipSize; i++ ) {
            if ( battleField[place + i] == BUSY ) {
                System.out.println("Error: the Ship size "  + shipSize + " can't be located at " + place);
                available = false;
                break;
            }
        }

        return available;
    }


    /** 
     * void addShipsToBattlefield() - method add randomly all ships to a battle field
     * @return void
     * **/
    public void addShipsToBattlefield()
    {
        Random randomGenerator = new Random();
        int randPlace;
        
        battleship = new Ship(Ship.BATTLESHIP_SIZE);
        do {
            randPlace = randomGenerator.nextInt(GRID_SIZE*GRID_SIZE);
            System.out.println("Random place = " + randPlace);
        } while (! addShip(battleship, randPlace));

        cruiser = new Ship(Ship.CRUISER_SIZE);
        for (int i = 0; i < cruiser.getShipsQty(); i++){
            do {
                randPlace = randomGenerator.nextInt(GRID_SIZE*GRID_SIZE);
                System.out.println("Random place = " + randPlace);
            } while (! addShip(cruiser,randPlace));
        }

        destroyer = new Ship(Ship.DESTROYER_SIZE);
        for (int i = 0; i < destroyer.getShipsQty(); i++){
            do {
                randPlace = randomGenerator.nextInt(GRID_SIZE*GRID_SIZE);
                System.out.println("Random place = " + randPlace);
            } while (! addShip(destroyer,randPlace));
        } 

        submarine = new Ship(Ship.SUBMARINE_SIZE);
        for (int i = 0; i < submarine.getShipsQty(); i++){
            do {
                randPlace = randomGenerator.nextInt(GRID_SIZE*GRID_SIZE);
                System.out.println("Random place = " + randPlace);
            } while (! addShip(submarine,randPlace));
        }
        
        printBattleField();
        /*few boundary tests:         
        battleField.addShip(battleship, -1);
        battleField.addShip(battleship, 99);
        battleField.addShip(battleship, 101);
        */
    }
    
    /** 
     * void printBattleField() - print a battle field state (needed for debugging)
     * @return void
     * **/
    public void printBattleField()
    {
        System.out.println("Printing battleField:");
        for (int i = 0; i < getBattleField().length; i++)
        {
            //int row = i / GRID_SIZE;
            int col = i % GRID_SIZE;
            System.out.print(battleField[i] + "  ");
            if (col == GRID_SIZE - 1)
                System.out.println("");

        }
    }
}
