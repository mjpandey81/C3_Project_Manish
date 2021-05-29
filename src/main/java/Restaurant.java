import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<Item>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen() {
        return ((getCurrentTime().compareTo(this.openingTime)>=0)&&(getCurrentTime().compareTo(this.closingTime)<=0));
    }

    public LocalTime getCurrentTime(){ return  LocalTime.now(); }

    public List<Item> getMenu() {
        return menu;
    }

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }
    
    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
/*    public void displayDetails(){
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());

    }*/

    public String getName() {
        return name;
    }

    //Part-3: SelectedMenu Methods for returning Item object when names are given and
    //calculating total price for given Item List

    public List<Item> getSelectedMenuItems(ArrayList<String> selectedItemNameList) {
        List<Item> selectedMenuItems = new ArrayList<Item>();

        if(selectedItemNameList != null) {
            for (String itemName : selectedItemNameList) {
                selectedMenuItems.add(findItemByName(itemName));
            }
        }

        return selectedMenuItems;
    }

    public int calculateSelectedMenuCost(List<Item> selectedMenuItems){
        int sum = 0;
        if(selectedMenuItems != null) {
            for (Item item : selectedMenuItems) {
                sum += item.getPrice();
            }
        }

        return sum;

    }



}
