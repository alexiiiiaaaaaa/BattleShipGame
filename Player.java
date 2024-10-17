/** 
 * @author Chanieva Anna-Oeksandra, 2616332
 * @version 1.0
 * project Battleship game
 * class Player contains hits statistic, hitMapArray fields;
 * get, set, update methods for the fields.
 * and isGameWon() method 
 * **/

public class Player {
    private int GRID_SIZE = 10;
    private int totalHits;
    private int successfulHits;
    public static int HIT = 1;
    public static int MISS = 0;
    
    // hitMapArray: 0 - default, no hit; 1 - hit happened to the cell;       
    private int[] hitMapArray;

    /**  
     * construcor Player()
     **/
    public Player()
    {
        this.totalHits = 0;
        this.successfulHits = 0;
        this.hitMapArray = new int[GRID_SIZE*GRID_SIZE];
        System.out.println("New player created");
    }
    
    // total hit methods 
    /**
     * int getTotalHits() - get total hits
     * @return int
     **/
    public int getTotalHits()
    {
        return this.totalHits;
    }

    /**
     * int updateTotalHitsBy(int num) - update total hits
     * @return int
     **/
    public int updateTotalHitsBy(int num)
    {
        this.totalHits += num;
        System.out.println("Total hits updated, new value: " + totalHits);
        return this.totalHits;
    }

     /**
     * void setTotalHits(int num) - set total hits
     * @return void
     **/
    public void setTotalHits(int num)
    {
        this.totalHits = num;
        System.out.println("Total hits set new value: " + totalHits);
    }

    // Succesfull hits methods 

    
     /**
     * int getSuccessfulHits() - gets successuful hits
     * @return int
     **/
    public int getSuccessfulHits()
    {
        return this.successfulHits;
    }

     /**
     * int updateSuccesfulHitsBy(int num) - update successful hits
     * @return int
     **/
    public int updateSuccesfulHitsBy(int num)
    {
        this.successfulHits += num;
        System.out.println("Successful hits updated, new value: " + successfulHits);
        return this.successfulHits;
    }

     /**
     * void setSuccessfulHits(int num)) - set successful hits
     * @return void
     **/
    public void setSuccessfulHits(int num)
    {
        this.successfulHits = num;
        System.out.println("Successful hits set new value: " + successfulHits);
    }

    /**
     * boolean isGameWon() - check if player won the game
     * @return bollean
     **/
    public boolean isGameWon()
    {
        if (successfulHits >= Ship.SHIPS_TOTAL_SIZE)
            return true;
        else 
            return false; 
    }

    // hitMapArray methods
     /**
     * int[] getHitMapArray() - gets map array
     * @return int
     **/
    public int[] getHitMapArray()
    {
        return this.hitMapArray;
    }
    
     /**
     * int getHitMapArrayValueByIndex(int index) - gets hit map value by index
     * @return int
     **/
    public int getHitMapArrayValueByIndex(int index)
    {
        return this.hitMapArray[index];
    }

     /**
     * void setHitMapArrayValueByIndex(int index, int value) - sets hit map value by index
     * @return void
     **/
    public void setHitMapArrayValueByIndex(int index, int value)
    {
        this.hitMapArray[index] = value;
        System.out.println("Set hitMapArray[" + index + "] = " +  value);        
    }

     /**
     * void clearHitMapArray() -  clear hit map array, sets all values to 0
     * @return void
     **/
    public void clearHitMapArray()
    {
        for (int i=0; i<hitMapArray.length; i++){
            hitMapArray[i] = 0;
        }
        System.out.println("Clear hitMapArray, set all values to 0");
    } 
}
