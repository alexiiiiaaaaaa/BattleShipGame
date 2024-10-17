/** 
 * @author Chanieva Anna-Oeksandra, 2616332
 * @version 1.0
 * project Battleship game
 * class Ship containes ship definitions
 * and methods to access ShipSize, ShipsQty, ShipType
 * **/

public class Ship {
    //Declare ship sizes - the number of cells it's required  
    public static final int SUBMARINE_SIZE     = 1;
    public static final int DESTROYER_SIZE     = 2;
    public static final int CRUISER_SIZE       = 3;
    public static final int BATTLESHIP_SIZE    = 4;
    
    //Declare ships quantity (QTY)
    private static final int SUBMARINE_QTY      = 3;
    private static final int DESTROYER_QTY      = 3;
    private static final int CRUISER_QTY        = 2;
    private static final int BATTLESHIP_QTY     = 1;
    
    //Total size of all ships, we need it to find out when player will destroy all of them 
    public static final int SHIPS_TOTAL_SIZE   =    SUBMARINE_SIZE * SUBMARINE_QTY + 
                                                    DESTROYER_SIZE * DESTROYER_QTY +
                                                    CRUISER_SIZE * CRUISER_QTY +
                                                    BATTLESHIP_SIZE * BATTLESHIP_QTY;

    private int shipSize;
    private int shipsQty; 
    
    private String[] shipTypes = {"","Submarine","Destroyer","Cruiser","Battleship"};  
    private String shipType;

    /**
     * constructor Ship(int size) - creates a ship based on it size 
     **/
    public Ship(int size)
    {
        this.shipSize = size;
        switch (size) {
            case SUBMARINE_SIZE: 
                shipType = shipTypes[SUBMARINE_SIZE];
                shipsQty = SUBMARINE_QTY;
                break;
            case DESTROYER_SIZE: 
                shipType = shipTypes[DESTROYER_SIZE];
                shipsQty = DESTROYER_QTY;
                break;
            case CRUISER_SIZE: 
                shipType = shipTypes[CRUISER_SIZE];
                shipsQty = CRUISER_QTY;
                break;
            case BATTLESHIP_SIZE: 
                shipType = shipTypes[BATTLESHIP_SIZE];
                shipsQty = BATTLESHIP_QTY;
                break;                           
            default:
                this.shipSize = 0;
                System.out.println("Error: The Ship size must be within range [1-4]");
                break;
        }
        System.out.println("New " + this.shipType + " ship created, the ship size is " + this.shipSize);
    }

    /** 
     * int getShipSize() - gets ships size
     * @return int
     **/
    public int getShipSize()
    {
        return this.shipSize;
    }

    /** 
     * int getShipsQty() - gets ships quantity
     * @return int
     **/
    public int getShipsQty()
    {
        return this.shipsQty;
    }

     /** 
     * String getShipType() - gets ships type
     * @return String
     **/
    public String getShipType()
    {
        return this.shipType;
    }
}
