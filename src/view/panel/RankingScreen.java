package view.panel;

import view.util.GameColors;
import constant.GameConstants;
import model.UserData;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class RankingScreen extends JFrame {

    private final JTable table;
    private final DefaultTableModel tableModel;
    private final ArrayList<UserData> userList;

    public RankingScreen(ArrayList<UserData> userList) {
        this.userList = new ArrayList<>(userList); // 원본 데이터 보호를 위해 복사

        // 설정
        setTitle("Ranking");
        setSize(GameConstants.WINDOW_SIZE, GameConstants.WINDOW_SIZE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙에 표시
        setBackground(GameColors.BACKGROUND);

        // 메인 패널
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(GameColors.BACKGROUND);

        // 제목
        JLabel titleLabel = new JLabel("Ranking");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(GameColors.TEXT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // 테이블 데이터 준비
        String[] columnNames = {"Rank", "Nickname", "Score", "Stage", "Frozen", "Shield"};
        tableModel = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 모든 셀 편집 불가능
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        table.getTableHeader().setBackground(GameColors.BACKGROUND);
        table.getTableHeader().setForeground(GameColors.TEXT);
        table.setBackground(GameColors.BACKGROUND);
        table.setForeground(GameColors.TEXT);

        // 가운데 정렬
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // 정렬 기준 선택
        JPanel sortPanel = new JPanel();
        sortPanel.setBackground(GameColors.BACKGROUND);
        JLabel sortLabel = new JLabel("Sort by: ");
        sortLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sortLabel.setForeground(GameColors.TEXT);
        JComboBox<String> sortOptions = new JComboBox<>(new String[]{"Score", "Frozen", "Shield"});
        sortOptions.setFont(new Font("Arial", Font.PLAIN, 16));
        sortOptions.addActionListener(e -> updateTableData(sortOptions.getSelectedItem().toString()));

        sortPanel.add(sortLabel);
        sortPanel.add(sortOptions);

        // 뒤로 가기 버튼
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.addActionListener(e -> dispose()); // 현재 창 닫기

        // 하단 패널 구성
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(GameColors.BACKGROUND);
        bottomPanel.add(sortPanel);
        bottomPanel.add(Box.createVerticalStrut(10)); // 여백 추가
        bottomPanel.add(backButton);

        // 테이블 스크롤 추가
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(GameColors.BACKGROUND);

        // 메인 패널에 컴포넌트 추가
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // 초기 데이터 표시 (기본은 Score)
        updateTableData("Score");
        setVisible(true);
    }

    private void updateTableData(String sortBy) {
        // 정렬 기준 설정
        switch (sortBy) {
            case "Frozen":
                userList.sort(Comparator.comparingInt(UserData::getFrozenCount).reversed());
                break;
            case "Shield":
                userList.sort(Comparator.comparingInt(UserData::getShieldCount).reversed());
                break;
            case "Score":
            default:
                userList.sort(Comparator.comparingInt(UserData::getScoreData).reversed());
                break;
        }

        // 테이블 데이터 업데이트
        tableModel.setRowCount(0); // 기존 데이터 초기화
        for (int i = 0; i < userList.size(); i++) {
            UserData user = userList.get(i);
            tableModel.addRow(new Object[]{
                    i + 1, // Rank
                    user.getNameData(),
                    user.getScoreData(),
                    user.getStageData(),
                    user.getFrozenCount(),
                    user.getShieldCount()
            });
        }
    }
}
