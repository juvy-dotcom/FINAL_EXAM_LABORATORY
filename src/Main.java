
import controller.TaskManager;
import ui.MainWindow;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        new MainWindow(taskManager);
    }
}
