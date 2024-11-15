package view;

import model.GameModel;
import view.panel.GamePanel;
import view.panel.StartPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class GameView extends JFrame {
    private final GamePanel gamePanel;
    private final StartPanel startPanel;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    public GameView() {
        setTitle("PacMan Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        gamePanel = new GamePanel();
        startPanel = new StartPanel();

        mainPanel.add(startPanel, "Start");
        mainPanel.add(gamePanel, "Game");

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    public void setModel(GameModel model) {
        gamePanel.setModel(model);
    }

    public void addKeyListener(KeyListener listener) {
        gamePanel.addKeyListener(listener);
    }

    public void setStartButtonListener(ActionListener listener) {
        startPanel.setStartButtonListener(listener);
    }

    public String getPlayerName() {
        return startPanel.getPlayerName();
    }

    public void showStartScreen() {
        cardLayout.show(mainPanel, "Start");
    }

    public void showGameScreen() {
        cardLayout.show(mainPanel, "Game");
        gamePanel.requestFocus();
    }

    public void refresh() {
        gamePanel.repaint();
    }
}