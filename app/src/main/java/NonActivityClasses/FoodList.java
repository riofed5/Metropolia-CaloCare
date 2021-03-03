package NonActivityClasses;

import java.util.ArrayList;
import java.util.List;

public class FoodList {
    private List<Food> foodList;
    private static final FoodList listInstance = new FoodList();

    public static FoodList getInstance() {
        return listInstance;
    }

    public void add() {
        foodList = new ArrayList<Food>();
        foodList.add(new Food("Beef",259,20,28,15,0,94,0,"4oz"));
        foodList.add(new Food("Pork",130,1,23,5,0,55,0,"4oz"));
        foodList.add(new Food("Chicken Breast",94,0,20,2,0,49,0,"3oz"));
        foodList.add(new Food("Brocolli",31,6,3,0,3,0,0,"100g"));
        foodList.add(new Food("Salmon",100,2,21,1,1,45,0,"4oz"));
        foodList.add(new Food("Banana",105,27,0,1,3,0,0,"1 banana"));
        foodList.add(new Food("Rice",150,35,3,0,0,0,0,"0.8 cup cooked"));
        foodList.add(new Food("Bread",200,35,4,6,0,0,3,"1 slice"));
        foodList.add(new Food("Eggs",72,1,7,5,0,186,3,"1 large"));
        foodList.add(new Food("Pasta",200,41,7,1,0,0,0,"2 ounces"));
    }

    public List<Food> getFoodList() {
        return this.foodList;
    }

    public Food getFood(int index) {
        return this.foodList.get(index);
    }
}
