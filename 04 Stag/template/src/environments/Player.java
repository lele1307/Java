package environments;
import entities.Inventory;
import java.util.ArrayList;
/**
 * @author dukehan
 */
public class Player {
    private String name;
    private int level;
    private int locationInd;
    private ArrayList<Inventory> inventories = new ArrayList<Inventory>();

    public Player(String name){
        this.name = name;
        level = 3;
        locationInd = 0;
    }
    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void addLevel(){
        level++;
    }

    public void decLevel(){
        if (level >= 1) {
            level--;
        }
    }

    public void initLevel(){
        level = 3;
        locationInd = 0;
    }

    public int getLocationInd() {
        return locationInd;
    }

    public void setLocationInd(int currLocationInd){
        locationInd = currLocationInd;
    }

    public ArrayList<Inventory> getInventories() {
        return inventories;
    }

    public void addInventories(Inventory inv) {
        inventories.add(inv);
    }

    public void removeInventory(int index){
        inventories.remove(index);
    }

}