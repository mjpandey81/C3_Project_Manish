import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {

    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    void initRestaurantTest(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =  new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){

        Restaurant spyRestaurant =  Mockito.spy(restaurant);

        //Check Boundary time at exact opening hours
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(restaurant.openingTime);
        assertTrue(spyRestaurant.isRestaurantOpen());

        //Check Boundary time at exact closing hours
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(restaurant.closingTime);
        assertTrue(spyRestaurant.isRestaurantOpen());

        //Check for between operating hours. say before 3 hours of closing.
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(restaurant.closingTime.minusHours(3));
        assertTrue(spyRestaurant.isRestaurantOpen());

    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){

        Restaurant spyRestaurant =  Mockito.spy(restaurant);

        //Check 1-minute before opening time
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(restaurant.openingTime.minusMinutes(1));
        assertFalse(spyRestaurant.isRestaurantOpen());

        //Check 5 seconds after closing time
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(restaurant.closingTime.plusSeconds(5));
        assertFalse(spyRestaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>SELECTED MENU Part-3 Tests>>>>>>>>>>>>>>>>>>>>
    @Test
    public void given_selected_item_name_item_object_must_be_returned(){

        ArrayList<String> itemNamesList = new ArrayList<String>();
        itemNamesList.add("Sweet corn soup");
        assertEquals(restaurant.getMenu().get(0), restaurant.getSelectedMenuItems(itemNamesList).get(0));
    }

    @Test
    public void given_selected_item_name_list_is_empty_returned_item_list_size_must_be_zero(){

        ArrayList<String> itemNamesList = new ArrayList<String>();
        assertEquals(0, restaurant.getSelectedMenuItems(itemNamesList).size());
    }

    @Test
    public void total_order_value_for_single_selected_item_must_be_same_as_item_price(){

        ArrayList<String> itemNamesList = new ArrayList<String>();
        itemNamesList.add("Sweet corn soup");
        assertEquals(restaurant.getMenu().get(0).getPrice(), restaurant.calculateSelectedMenuCost(restaurant.getSelectedMenuItems(itemNamesList)));
    }

    @Test
    public void total_order_value_for_multiple_selected_item_must_be_sum_of_item_price(){

        ArrayList<String> itemNamesList = new ArrayList<String>();
        itemNamesList.add("Sweet corn soup");
        itemNamesList.add("Vegetable lasagne");
        assertEquals(restaurant.getMenu().get(0).getPrice()+restaurant.getMenu().get(1).getPrice(),
                restaurant.calculateSelectedMenuCost(restaurant.getSelectedMenuItems(itemNamesList)));
    }

    @Test
    public void total_order_value_for_no_item_selected_must_be_zero(){

        ArrayList<String> itemNamesList = new ArrayList<String>();
        assertEquals(0, restaurant.calculateSelectedMenuCost(restaurant.getSelectedMenuItems(itemNamesList)));
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<SELECTED MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

}