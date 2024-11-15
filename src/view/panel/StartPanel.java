package view.panel;

import constant.GameConstants;
import view.util.GameColors;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel {
    private final JTextField nameField;
    private final JButton startButton;

    public StartPanel() {
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

        // Add components
        add(Box.createVerticalGlue());
        add(titleLabel);
        add(Box.createVerticalStrut(50));
        add(inputPanel);
        add(Box.createVerticalStrut(30));
        add(startButton);
        add(Box.createVerticalGlue());
    }

    public String getPlayerName() {
        return nameField.getText().trim();
    }

    public void setStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }
}