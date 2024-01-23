import java.util.ArrayList;
import java.util.List;

class Reservation {
    private String customerName;
    private String date;
    private String time;
    private List<MenuItem> selectedMeals;

    public Reservation(String customerName, String date, String time) {
        this.customerName = customerName;
        this.date = date;
        this.time = time;
        this.selectedMeals = new ArrayList<>();
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public List<MenuItem> getSelectedMeals() {
        return selectedMeals;
    }

    public void addMeal(MenuItem meal) {
        selectedMeals.add(meal);
    }

    public void removeMeal(MenuItem meal) {
        selectedMeals.remove(meal);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public void setTime(String time) {
        this.time = time;
    }
}