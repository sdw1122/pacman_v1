package controller;

import model.GameModel;
import model.Direction;
import view.GameView;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameController implements KeyListener {
    private final GameModel model;
    private final GameView view;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;

        view.setModel(model);
        view.addKeyListener(this);
        view.setStartButtonListener(e -> startGame());
    }

    private void startGame() {
        String playerName = view.getPlayerName();
        if (playerName.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter a nickname!");
            return;
        }

        model.setPlayerName(playerName);
        model.initializeGame();
        view.showGameScreen();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Direction direction = switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> Direction.UP;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> Direction.DOWN;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> Direction.LEFT;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> Direction.RIGHT;
            default -> null;
        };

        if (direction != null) {
            model.movePlayer(direction);
            if (model.isGameOver()) {
                int option = JOptionPane.showConfirmDialog(
                        view,
                        "Game Over! Your score: " + model.getScore() + "\nDo you want to try again?",
                        "Game Over",
                        JOptionPane.YES_NO_OPTION
                );
                if (option == JOptionPane.YES_OPTION) {
                    view.showStartScreen();
                } else {
                    System.exit(0);
                }
            }
            view.refresh();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}