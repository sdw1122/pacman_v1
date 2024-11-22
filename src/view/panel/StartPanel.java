package view.panel;

import constant.GameConstants;
import model.UserData;
import view.util.GameColors;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StartPanel extends JPanel {
    private final JTextField nameField;
    private final JButton startButton;
    private final JButton rankingButton;
    private final ArrayList<UserData> userList;

    public StartPanel(ArrayList<UserData> userList) {
    	this.userList = userList;//유저리스트
    	
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(GameColors.BACKGROUND);
        setPreferredSize(new Dimension(GameConstants.WINDOW_SIZE, GameConstants.WINDOW_SIZE));

        // Title
        JLabel titleLabel = new JLabel("PacMan Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(GameColors.TEXT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Name input
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(GameColors.BACKGROUND);
        JLabel nameLabel = new JLabel("Enter Nickname: ");
        nameLabel.setForeground(GameColors.TEXT);
        nameField = new JTextField(15);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);

        // Start button
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        rankingButton = new JButton("Ranking");
        rankingButton.setFont(new Font("Arial", Font.BOLD, 20));
        rankingButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components
        add(Box.createVerticalGlue());
        add(titleLabel);
        add(Box.createVerticalStrut(50));
        add(inputPanel);
        add(Box.createVerticalStrut(30));
        add(startButton);
        add(Box.createRigidArea(new Dimension(0, 10))); // 버튼 사이 간격
		add(rankingButton);
        add(Box.createVerticalGlue());
        
        setRankingButtonListener(e -> {
        	new RankingScreen(userList);
        });

        setVisible(true);
    }

    public String getPlayerName() {
        return nameField.getText().trim();
    }
    
    public void setRankingButtonListener(ActionListener listener) {
    	rankingButton.addActionListener(listener);
    }

    public void setStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }
}