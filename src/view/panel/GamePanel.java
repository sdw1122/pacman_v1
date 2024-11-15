package view.panel;

import constant.GameConstants;
import model.GameModel;
import model.Position;
import model.ItemType;
import view.util.GameColors;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel {
    private GameModel model;

    public GamePanel() {
        setPreferredSize(new Dimension(GameConstants.WINDOW_SIZE, GameConstants.WINDOW_SIZE));
        setBackground(GameColors.BACKGROUND);
        setFocusable(true);
    }

    public void setModel(GameModel model) {
        this.model = model;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (model == null) return;

        drawGrid(g);
        drawPlayer(g);
        drawEnemies(g);
        drawCoins(g);
        drawItems(g);
        drawStatus(g);

        if (model.isGameOver()) {
            drawGameOver(g);
        } else if (model.isGameClear()) {
            drawGameClear(g);
        }
    }

    private void drawGrid(Graphics g) {
        g.setColor(GameColors.GRID);
        for (int i = 0; i <= GameConstants.BOARD_SIZE; i++) {
            g.drawLine(i * GameConstants.CELL_SIZE, 0,
                    i * GameConstants.CELL_SIZE, GameConstants.WINDOW_SIZE);
            g.drawLine(0, i * GameConstants.CELL_SIZE,
                    GameConstants.WINDOW_SIZE, i * GameConstants.CELL_SIZE);
        }
    }

    private void drawPlayer(Graphics g) {
        Position pos = model.getPlayerPosition();
        // 실드 상태에 따라 플레이어 색상 변경
        g.setColor(model.getShieldCount() > 0 ? GameColors.PLAYER_SHIELD : GameColors.PLAYER);
        g.fillOval(pos.getX() * GameConstants.CELL_SIZE + 5,
                pos.getY() * GameConstants.CELL_SIZE + 5,
                GameConstants.CELL_SIZE - 10,
                GameConstants.CELL_SIZE - 10);
    }

    private void drawEnemies(Graphics g) {
        g.setColor(GameColors.ENEMY);
        for (Position enemy : model.getEnemies()) {
            g.fillOval(enemy.getX() * GameConstants.CELL_SIZE + 5,
                    enemy.getY() * GameConstants.CELL_SIZE + 5,
                    GameConstants.CELL_SIZE - 10,
                    GameConstants.CELL_SIZE - 10);
        }
    }

    private void drawCoins(Graphics g) {
        g.setColor(GameColors.COIN);
        for (Position coin : model.getCoins()) {
            g.fillOval(coin.getX() * GameConstants.CELL_SIZE + 20,
                    coin.getY() * GameConstants.CELL_SIZE + 20,
                    GameConstants.CELL_SIZE - 40,
                    GameConstants.CELL_SIZE - 40);
        }
    }

    private void drawItems(Graphics g) {
        List<Position> items = model.getItems();
        List<ItemType> itemTypes = model.getItemTypes();

        for (int i = 0; i < items.size(); i++) {
            Position item = items.get(i);
            // 아이템 타입에 따라 색상 변경
            g.setColor(itemTypes.get(i) == ItemType.SHIELD ?
                    GameColors.SHIELD_ITEM : GameColors.FREEZE_ITEM);

            // 실드 아이템은 사각형, 프리즈 아이템은 원형으로 그리기
            if (itemTypes.get(i) == ItemType.SHIELD) {
                g.fillRect(item.getX() * GameConstants.CELL_SIZE + 15,
                        item.getY() * GameConstants.CELL_SIZE + 15,
                        GameConstants.CELL_SIZE - 30,
                        GameConstants.CELL_SIZE - 30);
            } else {
                g.fillOval(item.getX() * GameConstants.CELL_SIZE + 15,
                        item.getY() * GameConstants.CELL_SIZE + 15,
                        GameConstants.CELL_SIZE - 30,
                        GameConstants.CELL_SIZE - 30);
            }
        }
    }

    private void drawStatus(Graphics g) {
        g.setColor(GameColors.TEXT);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("Player: " + model.getPlayerName(), 10, 20);
        g.drawString("Score: " + model.getScore(), 10, 40);
        g.drawString("Stage: " + model.getStage(), 10, 60);
        g.drawString("Shield: " + model.getShieldCount(), 10, 80);

        // 프리즈 상태일 때만 시간 표시
        if (model.getTimeCount() > 0) {
            g.setColor(GameColors.FREEZE_ITEM);
            g.drawString("Freeze Time: " + model.getTimeCount(), 10, 100);
        }
    }

    private void drawGameOver(Graphics g) {
        drawCenteredMessage(g, "Game Over!");
        drawScore(g);
    }

    private void drawGameClear(Graphics g) {
        drawCenteredMessage(g, "You Win!");
        drawScore(g);
    }

    private void drawCenteredMessage(Graphics g, String message) {
        g.setColor(GameColors.TEXT);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics metrics = g.getFontMetrics();
        int x = (GameConstants.WINDOW_SIZE - metrics.stringWidth(message)) / 2;
        int y = GameConstants.WINDOW_SIZE / 2;
        g.drawString(message, x, y);
    }

    private void drawScore(Graphics g) {
        String scoreMessage = "Final Score: " + model.getScore();
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics = g.getFontMetrics();
        int x = (GameConstants.WINDOW_SIZE - metrics.stringWidth(scoreMessage)) / 2;
        int y = GameConstants.WINDOW_SIZE / 2 + 40;
        g.drawString(scoreMessage, x, y);
    }
}