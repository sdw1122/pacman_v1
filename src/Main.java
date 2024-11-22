import model.GameModel;
import model.UserData;
import view.GameView;
import controller.GameController;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameModel model = new GameModel();
            
            //userList추가
            ArrayList<UserData> userList = new ArrayList<>();
            GameView view = new GameView(userList);
            new GameController(model, view, userList);
            
            view.setVisible(true);
        });
    }
}