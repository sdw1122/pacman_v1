// GameModel.java
package model;

import constant.GameConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel {
	
	
    // Game state
    private String playerName;
    private Position playerPosition;
    private List<Position> enemies;
    private List<Position> coins;
    private List<Position> items;
    private List<ItemType> itemTypes;
    private int score;
    private int stage;
    private int shieldCount;
    private int timeCount;
    private boolean isGameOver;
    private boolean isGameClear;
    private final Random random;
    //아이템 사용횟수
    private int totalShield = 0;
    private int totalFrozen = 0;
	

    public GameModel() {
        this.playerName = "Player";
        this.enemies = new ArrayList<>();
        this.coins = new ArrayList<>();
        this.items = new ArrayList<>();
        this.itemTypes = new ArrayList<>();
        this.random = new Random();
        initializeGame();
    }
    
    public void initializeGame() {
        playerPosition = new Position(GameConstants.BOARD_SIZE / 2, GameConstants.BOARD_SIZE / 2);
        enemies.clear();
        coins.clear();
        items.clear();
        itemTypes.clear();
        score = 0;
        stage = 1;
        shieldCount = 0;
        timeCount = 0;
        isGameOver = false;
        isGameClear = false;
        initializeStage();
    }
    
    
    
    
    private void initializeStage() {
        initializeCoins();
        initializeEnemies();
        initializeItems();
    }

    private void initializeCoins() {
        int numCoins = stage * 10;
        for (int i = 0; i < numCoins; i++) {
            Position coin;
            do {
                coin = new Position(
                        random.nextInt(GameConstants.BOARD_SIZE),
                        random.nextInt(GameConstants.BOARD_SIZE)
                );
            } while (coins.contains(coin) || coin.equals(playerPosition));
            coins.add(coin);
        }
    }

    private void initializeEnemies() {
        int numEnemies = stage + 4;
        enemies.clear();  // 이전 스테이지의 적들을 모두 제거

        for (int i = 0; i < numEnemies; i++) {
            Position enemy;
            do {
                enemy = new Position(
                        random.nextInt(GameConstants.BOARD_SIZE),
                        random.nextInt(GameConstants.BOARD_SIZE)
                );
            } while (enemies.contains(enemy) ||
                    enemy.equals(playerPosition) ||
                    isNearPlayer(enemy, 2));
            enemies.add(enemy);
        }
    }

    private void initializeItems() {
        int numItems = stage + 2;
        items.clear();
        itemTypes.clear();

        for (int i = 0; i < numItems; i++) {
            Position item;
            do {
                item = new Position(
                        random.nextInt(GameConstants.BOARD_SIZE),
                        random.nextInt(GameConstants.BOARD_SIZE)
                );
            } while (coins.contains(item) ||
                    enemies.contains(item) ||
                    item.equals(playerPosition));

            items.add(item);
            itemTypes.add(random.nextBoolean() ? ItemType.SHIELD : ItemType.FREEZE);
        }
    }

    private boolean isNearPlayer(Position enemy, int distance) {
        return Math.abs(enemy.getX() - playerPosition.getX()) <= distance &&
                Math.abs(enemy.getY() - playerPosition.getY()) <= distance;
    }

    public void movePlayer(Direction direction) {
        if (isGameOver || isGameClear) {
            if (isGameOver) {
                resetGame();
                return;
            }
            return;
        }

        playerPosition.move(direction, GameConstants.BOARD_SIZE);
        updateGameState();
    }

    private void updateGameState() {
        checkCoinCollection();
        checkItemCollection();
        updateEnemies();
        checkCollisions();
        checkStageCompletion();
    }

    private void checkCoinCollection() {
        coins.removeIf(coin -> {
            if (coin.equals(playerPosition)) {
                score += GameConstants.COIN_SCORE;
                return true;
            }
            return false;
        });
    }

    private void checkItemCollection() {
        for (int i = items.size() - 1; i >= 0; i--) {
            if (items.get(i).equals(playerPosition)) {
                ItemType type = itemTypes.get(i);
                if (type == ItemType.SHIELD) {
                	totalShield++;//실드 총카운트
                    shieldCount++;
                    System.out.println("Shield acquired! Count: " + shieldCount);
                } else {
                	totalFrozen++;//프로즌 총카운트
                    timeCount = GameConstants.FREEZE_TIME;
                    System.out.println("Freeze activated! Duration: " + timeCount);
                }
                items.remove(i);
                itemTypes.remove(i);
            }
        }
    }

    private void checkStageCompletion() {
        if (coins.isEmpty()) {
            if (stage < GameConstants.MAX_STAGES) {
                stage++;
                initializeStage();
            } else {
                isGameClear = true;
            }
        }
    }

    private void updateEnemies() {
        if (timeCount > 0) {
            timeCount--;
            System.out.println("Enemies frozen. Remaining time: " + timeCount);
            return;
        }
        moveEnemies();
    }

    private void moveEnemies() {
        for (Position enemy : enemies) {
            Direction randomDirection = Direction.values()[random.nextInt(Direction.values().length)];
            enemy.move(randomDirection, GameConstants.BOARD_SIZE);
        }
    }

    private void checkCollisions() {
        for (Position enemy : enemies) {
            if (enemy.equals(playerPosition)) {
                handleCollision();
                break;
            }
        }
    }

    private void handleCollision() {
        if (shieldCount > 0) {
            shieldCount--;
            System.out.println("Shield used! Remaining: " + shieldCount);
        } else {
            isGameOver = true;
            System.out.println("Game Over! Score: " + score);
        }
    }

    // Getters and Setters
    public String getPlayerName() { return playerName; }
    public Position getPlayerPosition() { return playerPosition; }
    public List<Position> getEnemies() { return new ArrayList<>(enemies); }
    public List<Position> getCoins() { return new ArrayList<>(coins); }
    public List<Position> getItems() { return new ArrayList<>(items); }
    public List<ItemType> getItemTypes() { return new ArrayList<>(itemTypes); }
    public int getScore() { return score; }
    public int getStage() { return stage; }
    public int getShieldCount() { return shieldCount; }
    public int getTimeCount() { return timeCount; }
    public boolean isGameOver() { return isGameOver; }
    public boolean isGameClear() { return isGameClear; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }
    public ItemType getItemType(int index) { return itemTypes.get(index); }

    public void resetGame() { initializeGame(); }
    
    //아이템 카운트 리턴
    public int getTotalShield() {return totalShield;};
    public int getTotalFrozen() {return totalFrozen;};
    public void initializeItemCount() {
    	totalShield = 0;
    	totalFrozen = 0;
    }
    
}