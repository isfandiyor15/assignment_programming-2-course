import java.util.ArrayList;
import java.util.List;

class Menu {
    private List<MenuItem> fixedMenu;

    public Menu() {
        fixedMenu = new ArrayList<>();
        fixedMenu.add(new MenuItem("Palov", 2.7));
        fixedMenu.add(new MenuItem("Manti", 4.8));
        fixedMenu.add(new MenuItem("Xalim", 12));
        fixedMenu.add(new MenuItem("Dimlama", 15));
        fixedMenu.add(new MenuItem("Norin", 2.5));
        fixedMenu.add(new MenuItem("Sumalak", 2.4));
        fixedMenu.add(new MenuItem("Chuchvara", 2.3));
        fixedMenu.add(new MenuItem("Shorva", 3));
    }

    public void displayMenu() {
        System.out.println("\nMenu:");
        for (MenuItem item : fixedMenu) {
            System.out.println(item.getName() + " = $" + item.getPrice());
        }
    }

    public List<MenuItem> getFixedMenu() {
        return fixedMenu;
    }
}