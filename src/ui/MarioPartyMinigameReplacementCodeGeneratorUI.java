package ui;

import constants.MinigameCategoryUIConstants;
import constants.MinigameConstants;
import io.CodeGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MarioPartyMinigameReplacementCodeGeneratorUI extends JFrame implements ActionListener {
    private JComboBox<String> marioPartyGameDropdown;
    private JComboBox<String> oldMinigameDropdown;
    private JComboBox<String> newMinigameDropdown;
    private JComboBox<String> categoryFilterDropdown;
    private String selectedGame;

    private JButton generateCode;

    private Map<String, Minigame[]> minigameMap;

    private Minigame[] currentOldMinigames;
    private Minigame[] currentNewMinigames;

    public MarioPartyMinigameReplacementCodeGeneratorUI() {
        setTitle("Mario Party Minigame Replacement Code Generator");
        initializeMinigameMap();
        generateUI();
    }

    private void initializeMinigameMap() {
        minigameMap = new HashMap<>();
        minigameMap.put("Mario Party 4", MinigameConstants.MARIO_PARTY_4_MINIGAMES);
        minigameMap.put("Mario Party 5", MinigameConstants.MARIO_PARTY_5_MINIGAMES);
        minigameMap.put("Mario Party 6", MinigameConstants.MARIO_PARTY_6_MINIGAMES);
        minigameMap.put("Mario Party 7", MinigameConstants.MARIO_PARTY_7_MINIGAMES);
        minigameMap.put("Mario Party 8", MinigameConstants.MARIO_PARTY_8_MINIGAMES);
    }

    private void generateUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Select Mario Party Game:"), gbc);

        gbc.gridy = 1;
        marioPartyGameDropdown = new JComboBox<>(new String[]{
                "Mario Party 4", "Mario Party 5", "Mario Party 6", "Mario Party 7", "Mario Party 8"
        });
        panel.add(marioPartyGameDropdown, gbc);

        marioPartyGameDropdown.addActionListener(e -> {
            updateCategoryFilter();
            updateMinigames();
        });

        gbc.gridy = 2;
        panel.add(new JLabel("Select Old Minigame:"), gbc);

        gbc.gridy = 3;
        oldMinigameDropdown = new JComboBox<>();
        panel.add(oldMinigameDropdown, gbc);

        gbc.gridy = 4;
        panel.add(new JLabel("Select New Minigame:"), gbc);

        gbc.gridy = 5;
        newMinigameDropdown = new JComboBox<>();
        panel.add(newMinigameDropdown, gbc);

        gbc.gridy = 6;
        panel.add(new JLabel("Filter by Category:"), gbc);

        gbc.gridy = 7;
        categoryFilterDropdown = new JComboBox<>(MinigameCategoryUIConstants.CATEGORY_NAMES);
        panel.add(categoryFilterDropdown, gbc);

        marioPartyGameDropdown.addActionListener(e -> updateMinigames());
        categoryFilterDropdown.addActionListener(e -> updateMinigames());

        gbc.gridy = 8;
        generateCode = new JButton("Generate");
        generateCode.addActionListener(this);
        panel.add(generateCode, gbc);

        add(panel);

        updateMinigames();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateMinigames() {
        selectedGame = (String) marioPartyGameDropdown.getSelectedItem();
        String selectedCategory = (String) categoryFilterDropdown.getSelectedItem();

        if (selectedGame != null && minigameMap.containsKey(selectedGame)) {
            if ("All".equals(selectedCategory)) {
                currentOldMinigames = minigameMap.get(selectedGame);
                currentNewMinigames = minigameMap.get(selectedGame);
                oldMinigameDropdown.setModel(new DefaultComboBoxModel<>(MinigameConstants.getNames(currentOldMinigames)));
                newMinigameDropdown.setModel(new DefaultComboBoxModel<>(MinigameConstants.getNames(currentNewMinigames)));
                return;
            }

            int categoryIndex = java.util.Arrays.asList(MinigameCategoryUIConstants.CATEGORY_NAMES).indexOf(selectedCategory) - 1;

            java.util.List<Minigame> filteredList = new java.util.ArrayList<>();
            for (Minigame m : minigameMap.get(selectedGame)) {
                if (m.getCategory() == categoryIndex) {
                    filteredList.add(m);
                }
            }

            currentOldMinigames = filteredList.toArray(new Minigame[0]);
            currentNewMinigames = currentOldMinigames;

            oldMinigameDropdown.setModel(new DefaultComboBoxModel<>(MinigameConstants.getNames(currentOldMinigames)));
            newMinigameDropdown.setModel(new DefaultComboBoxModel<>(MinigameConstants.getNames(currentNewMinigames)));
        }
    }

    private void updateCategoryFilter() {
        selectedGame = (String) marioPartyGameDropdown.getSelectedItem();
        if (selectedGame == null || !minigameMap.containsKey(selectedGame)) return;

        Minigame[] minigames = minigameMap.get(selectedGame);

        Set<Integer> categoriesPresent = new HashSet<>();
        for (Minigame m : minigames) {
            categoriesPresent.add(m.getCategory());
        }

        DefaultComboBoxModel<String> categoryModel = new DefaultComboBoxModel<>();
        categoryModel.addElement(MinigameCategoryUIConstants.MINIGAME_CATEGORY_MAP.get(-1)); // "All"

        for (int i = 0; i <= 12; i++) {
            if (categoriesPresent.contains(i)) {
                categoryModel.addElement(MinigameCategoryUIConstants.MINIGAME_CATEGORY_MAP.get(i));
            }
        }
        categoryFilterDropdown.setModel(categoryModel);

        categoryFilterDropdown.setSelectedIndex(0);
    }

    private Minigame getSelectedOldMinigame() {
        int index = oldMinigameDropdown.getSelectedIndex();
        if (index >= 0 && index < currentOldMinigames.length) {
            return currentOldMinigames[index];
        }
        return null;
    }

    private Minigame getSelectedNewMinigame() {
        int index = newMinigameDropdown.getSelectedIndex();
        if (index >= 0 && index < currentNewMinigames.length) {
            return currentNewMinigames[index];
        }
        return null;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateCode) {
            Minigame oldMinigame = getSelectedOldMinigame();
            Minigame newMinigame = getSelectedNewMinigame();
            if (oldMinigame != null && newMinigame != null) {
                CodeGenerator.generateCode(selectedGame, oldMinigame, newMinigame);
            } else {
                JOptionPane.showMessageDialog(this, "Please select valid minigames.");
            }
        }
    }
}