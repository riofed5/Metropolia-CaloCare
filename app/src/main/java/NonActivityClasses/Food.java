package NonActivityClasses;

public class Food {
    private String name;
    private int calories, carbs, protein, fat, fiber, cholesterol, calcium, numOfServ;
    private String servingSize;

    public Food(String name, int calories, int carbs, int protein, int fat, int fiber, int cholesterol, int calcium, String servingSize) {
        this.name = name;
        this.calories = calories;
        this.carbs = carbs;
        this.protein = protein;
        this.fat = fat;
        this.fiber = fiber;
        this.cholesterol = cholesterol;
        this.calcium = calcium;
        this.servingSize= servingSize;
    }

    public int getCalories() { return calories; }

    public int getCarbs() {
        return carbs;
    }

    public int getProtein() {
        return protein;
    }

    public int getFat() {
        return fat;
    }

    public int getFiber() {
        return fiber;
    }

    public int getCholesterol() {
        return cholesterol;
    }

    public int getCalcium() {
        return calcium;
    }

    public int getNumOfServ(){
        return numOfServ;
    }

    public void setNumOfServ(int amount) { numOfServ = amount; }

    public String getServingSize() {
        return this.servingSize;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
