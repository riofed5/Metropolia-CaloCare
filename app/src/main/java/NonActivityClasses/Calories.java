package NonActivityClasses;

public class Calories {
    private static final Calories caloriesInstance = new Calories();

    private int goalStatus;
    private double calcServingCalo;
    private int caloOfGoal;
    private int addedCalo = 0;
    private int maxCalo = 0;

    public static Calories getInstance() {
        return caloriesInstance;
    }

    public int maxCalo() {
        goalStatus = UserInfo.getInstance().getGoalStatus();

        if (goalStatus == 1){ caloOfGoal= -500; }
        else if (goalStatus == 2){ caloOfGoal=0; }
        else if (goalStatus == 3){ caloOfGoal=500; }
        maxCalo = (int) (Math.round(UserInfo.getInstance().getTDEE()) + caloOfGoal);
        return maxCalo;
    }

    public void calcServingCalo(Food input){
        calcServingCalo = input.getCalories() * input.getNumOfServ();
        addedCalo += calcServingCalo;
    }
    public int calcRemain(){
        return maxCalo - addedCalo;
    }

    public int getAddedCalo() {
        return addedCalo;
    }

    public void setAddedCalo(int newAddedCalo) {
        addedCalo = newAddedCalo;
    }
}