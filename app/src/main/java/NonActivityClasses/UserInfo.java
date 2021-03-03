package NonActivityClasses;

public class UserInfo {
    private static final UserInfo userInstance = new UserInfo();
    private int age, height, weight, goalStatus;
    private double activeStatus;
    private String name = "", gender = "";

    public static UserInfo getInstance(){
        return userInstance;
    }

    public void setName(String newName){
        name = newName;
    }

    public void setGender(String newGender){
        gender = newGender;
    }

    public void setAge(int newAge){
        age = newAge;
    }

    public void setHeight(int newHeight){
        height = newHeight;
    }

    public void setWeight(int newWeight){
        weight = newWeight;
    }

    public void setActiveStatus(double newActiveStatus){
        activeStatus = newActiveStatus;
    }

    public void setGoalStatus(int newGoalStatus){ goalStatus = (newGoalStatus); }

    public String getName(){
        return name;
    }

    public int getGoalStatus(){
        return (goalStatus);
    }

    public double getBMR(){
        double BMR = 0;
        if (gender.equals("Male")){
            BMR = (66.47 + (13.7 * weight) + (5 * height) - (6.8 * age));
        }
        else if (gender.equals("Female")){
            BMR = (655.1 + (9.6 * weight) + (1.8 * height) - (4.7 * age));
        }
        return BMR;
    }

    public double getTDEE(){
        return (getBMR() * activeStatus);
    }
}
