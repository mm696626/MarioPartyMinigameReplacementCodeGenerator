package ui;

import constants.MinigameConstants;

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

    private static final String[] CATEGORY_NAMES = {
            "All",
            "Four Player",
            "1v3",
            "2v2",
            "Battle",
            "Duel",
            "Bowser",
            "DK",
            "Story",
            "Extra",
            "8 Player",
            "1 Player",
            "1 Player Bowser",
            "1 Player DK"
    };

    private static final Map<Integer, String> MINIGAME_CATEGORY_MAP = new HashMap<>() {{
        put(-1, "All");
        put(0, "Four Player");
        put(1, "1v3");
        put(2, "2v2");
        put(3, "Battle");
        put(4, "Duel");
        put(5, "Bowser");
        put(6, "DK");
        put(7, "Story");
        put(8, "Extra");
        put(9, "8 Player");
        put(10, "1 Player");
        put(11, "1 Player Bowser");
        put(12, "1 Player DK");
    }};


    private JButton generateCode;

    private Map<String, Minigame[]> minigameMap;

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
        categoryFilterDropdown = new JComboBox<>(CATEGORY_NAMES);
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
        String selectedGame = (String) marioPartyGameDropdown.getSelectedItem();
        String selectedCategory = (String) categoryFilterDropdown.getSelectedItem();

        if (selectedGame != null && minigameMap.containsKey(selectedGame)) {
            Minigame[] allMinigames = minigameMap.get(selectedGame);

            if ("All".equals(selectedCategory)) {
                oldMinigameDropdown.setModel(new DefaultComboBoxModel<>(MinigameConstants.getNames(allMinigames)));
                newMinigameDropdown.setModel(new DefaultComboBoxModel<>(MinigameConstants.getNames(allMinigames)));
                return;
            }

            int categoryIndex = java.util.Arrays.asList(CATEGORY_NAMES).indexOf(selectedCategory) - 1;

            java.util.List<Minigame> filteredList = new java.util.ArrayList<>();
            for (Minigame m : allMinigames) {
                if (m.getCategory() == categoryIndex) {
                    filteredList.add(m);
                }
            }

            Minigame[] filteredMinigames = filteredList.toArray(new Minigame[0]);
            oldMinigameDropdown.setModel(new DefaultComboBoxModel<>(MinigameConstants.getNames(filteredMinigames)));
            newMinigameDropdown.setModel(new DefaultComboBoxModel<>(MinigameConstants.getNames(filteredMinigames)));
        }
    }

    private void updateCategoryFilter() {
        String selectedGame = (String) marioPartyGameDropdown.getSelectedItem();
        if (selectedGame == null || !minigameMap.containsKey(selectedGame)) return;

        Minigame[] minigames = minigameMap.get(selectedGame);

        Set<Integer> categoriesPresent = new HashSet<>();
        for (Minigame m : minigames) {
            categoriesPresent.add(m.getCategory());
        }

        DefaultComboBoxModel<String> categoryModel = new DefaultComboBoxModel<>();
        categoryModel.addElement(MINIGAME_CATEGORY_MAP.get(-1)); // "All"

        for (int i = 0; i <= 12; i++) {
            if (categoriesPresent.contains(i)) {
                categoryModel.addElement(MINIGAME_CATEGORY_MAP.get(i));
            }
        }
        categoryFilterDropdown.setModel(categoryModel);

        categoryFilterDropdown.setSelectedIndex(0);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}