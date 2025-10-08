package ui;

import constants.MinigameCategoryConstants;
import constants.MinigameConstants;
import io.CodeGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

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

    private JCheckBox allowAllMinigamesCheckbox;

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
        categoryFilterDropdown = new JComboBox<>(MinigameCategoryConstants.MINIGAME_CATEGORY_NAMES);
        panel.add(categoryFilterDropdown, gbc);

        marioPartyGameDropdown.addActionListener(e -> updateMinigames());
        categoryFilterDropdown.addActionListener(e -> updateMinigames());

        gbc.gridy = 8;
        generateCode = new JButton("Generate");
        generateCode.addActionListener(this);
        panel.add(generateCode, gbc);

        gbc.gridy = 9;
        allowAllMinigamesCheckbox = new JCheckBox("All minigames selectable for replacement in packs");
        panel.add(allowAllMinigamesCheckbox, gbc);

        gbc.gridy = 10;
        JButton generatePackButton = new JButton("Open Minigame Pack Editor");
        generatePackButton.addActionListener(e -> openMinigamePackEditor());
        panel.add(generatePackButton, gbc);

        add(panel);

        updateMinigames();
        updateCategoryFilter();

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

            int categoryIndex = Arrays.asList(MinigameCategoryConstants.MINIGAME_CATEGORY_NAMES).indexOf(selectedCategory) - 1;

            List<Minigame> filteredList = new ArrayList<>();
            for (Minigame m : minigameMap.get(selectedGame)) {
                if (m.getCategory() == categoryIndex) {
                    filteredList.add(m);
                }
            }

            currentOldMinigames = filteredList.toArray(new Minigame[0]);

            if (selectedGame.equals("Mario Party 8")) {
                if ("4 Player".equals(selectedCategory)) {
                    for (Minigame m : minigameMap.get(selectedGame)) {
                        if (m.getCategory() == MinigameCategoryConstants.FOUR_PLAYER_DUEL_MINIGAME) {
                            filteredList.add(m);
                        }
                    }
                }
                if ("2v2".equals(selectedCategory)) {
                    for (Minigame m : minigameMap.get(selectedGame)) {
                        if (m.getCategory() == MinigameCategoryConstants.TWO_V_TWO_DUEL_MINIGAME) {
                            filteredList.add(m);
                        }
                    }
                }
                if ("Battle".equals(selectedCategory)) {
                    for (Minigame m : minigameMap.get(selectedGame)) {
                        if (m.getCategory() == MinigameCategoryConstants.BATTLE_DUEL_MINIGAME) {
                            filteredList.add(m);
                        }
                    }
                }
            }

            currentNewMinigames = filteredList.toArray(new Minigame[0]);

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
        categoryModel.addElement(MinigameCategoryConstants.MINIGAME_CATEGORY_MAP.get(-1));

        for (int i = 0; i <= MinigameCategoryConstants.MINIGAME_CATEGORY_MAP.size() - 2; i++) {
            if (categoriesPresent.contains(i)) {
                categoryModel.addElement(MinigameCategoryConstants.MINIGAME_CATEGORY_MAP.get(i));
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

    private void openMinigamePackEditor() {
        selectedGame = (String) marioPartyGameDropdown.getSelectedItem();
        if (selectedGame == null || !minigameMap.containsKey(selectedGame)) {
            JOptionPane.showMessageDialog(this, "Please select a valid Mario Party game.");
            return;
        }
        Minigame[] minigames = minigameMap.get(selectedGame);
        boolean allowAll = allowAllMinigamesCheckbox.isSelected();
        Map<Integer, List<Minigame>> categoryMap = new HashMap<>();
        for (Minigame m : minigames) {
            categoryMap.computeIfAbsent(m.getCategory(), k -> new ArrayList<>()).add(m);
        }

        JFrame packEditorFrame = new JFrame("Minigame Pack Editor - " + selectedGame);
        packEditorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel loadPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        loadPanel.add(new JLabel("Load Saved Pack:"));
        JComboBox<String> savedPacksDropdown = new JComboBox<>();
        loadPanel.add(savedPacksDropdown);
        JButton loadSelectedButton = new JButton("Load Selected Pack");
        loadPanel.add(loadSelectedButton);
        mainPanel.add(loadPanel);

        File packDir = new File("saved_packs" + File.separator + selectedGame);
        if (packDir.exists() && packDir.isDirectory()) {
            String[] packFiles = packDir.list((dir, name) -> name.toLowerCase().endsWith(".json"));
            if (packFiles != null) {
                Arrays.sort(packFiles);
                for (String f : packFiles) {
                    savedPacksDropdown.addItem(f);
                }
            }
        }

        Map<Minigame, JComboBox<String>> replacementSelectors = new HashMap<>();

        JPanel bulkReplacePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bulkReplacePanel.setBorder(BorderFactory.createTitledBorder("Bulk Replace Category Minigames"));

        bulkReplacePanel.add(new JLabel("Select Category:"));
        JComboBox<String> bulkCategoryDropdown = new JComboBox<>();

        List<Integer> categoryKeys = new ArrayList<>(categoryMap.keySet());
        categoryKeys.sort(Integer::compareTo);
        for (Integer cat : categoryKeys) {
            String catName = MinigameCategoryConstants.MINIGAME_CATEGORY_MAP.get(cat);
            bulkCategoryDropdown.addItem(catName);
        }

        bulkReplacePanel.add(bulkCategoryDropdown);

        bulkReplacePanel.add(new JLabel("Replacement Minigame:"));
        JComboBox<String> bulkReplacementDropdown = new JComboBox<>();
        bulkReplacePanel.add(bulkReplacementDropdown);

        bulkCategoryDropdown.addActionListener(ev -> {
            int selectedIndex = bulkCategoryDropdown.getSelectedIndex();
            if (selectedIndex >= 0) {
                int catId = categoryKeys.get(selectedIndex);
                List<Minigame> replacements = categoryMap.get(catId);
                if (allowAll) {
                    replacements = new ArrayList<>(Arrays.asList(minigames));
                }
                String[] names = MinigameConstants.getNames(replacements.toArray(new Minigame[0]));
                bulkReplacementDropdown.setModel(new DefaultComboBoxModel<>(names));
                if (names.length > 0) {
                    bulkReplacementDropdown.setSelectedIndex(0);
                }
            }
        });
        bulkCategoryDropdown.setSelectedIndex(0);

        JButton setAllButton = new JButton("Set All In Category");
        bulkReplacePanel.add(setAllButton);

        setAllButton.addActionListener(ev -> {
            int catIndex = bulkCategoryDropdown.getSelectedIndex();
            if (catIndex < 0) {
                JOptionPane.showMessageDialog(packEditorFrame, "Please select a category.");
                return;
            }
            int catId = categoryKeys.get(catIndex);
            String replacementName = (String) bulkReplacementDropdown.getSelectedItem();
            if (replacementName == null) return;

            List<Minigame> minigamesInCat = categoryMap.get(catId);
            for (Minigame mg : minigamesInCat) {
                JComboBox<String> combo = replacementSelectors.get(mg);
                if (combo != null) {
                    combo.setSelectedItem(replacementName);
                }
            }
        });
        mainPanel.add(bulkReplacePanel, 0);

        JCheckBox allowDuplicatesCheckbox = new JCheckBox("Allow duplicates when randomizing");
        allowDuplicatesCheckbox.setSelected(true);
        mainPanel.add(allowDuplicatesCheckbox, 1);

        for (Map.Entry<Integer, List<Minigame>> entry : categoryMap.entrySet()) {
            int category = entry.getKey();
            List<Minigame> categoryMinigames = entry.getValue();
            JPanel categoryPanel = new JPanel(new BorderLayout());
            categoryPanel.setBorder(BorderFactory.createTitledBorder(MinigameCategoryConstants.MINIGAME_CATEGORY_MAP.get(category)));
            JPanel grid = new JPanel(new GridLayout(categoryMinigames.size(), 2, 10, 5));

            for (Minigame originalMinigame : categoryMinigames) {
                JLabel originalLabel = new JLabel(originalMinigame.getName());
                Minigame[] replacementPool;
                if (allowAll) {
                    replacementPool = minigames;
                } else {
                    List<Minigame> replacements = new ArrayList<>(categoryMinigames);
                    if ("Mario Party 8".equals(selectedGame)) {
                        int categoryId = originalMinigame.getCategory();
                        for (Minigame m : minigames) {
                            int duelCat = m.getCategory();
                            if (categoryId == MinigameCategoryConstants.FOUR_PLAYER_MINIGAME && duelCat == MinigameCategoryConstants.FOUR_PLAYER_DUEL_MINIGAME) {
                                replacements.add(m);
                            }
                            if (categoryId == MinigameCategoryConstants.TWO_V_TWO_MINIGAME && duelCat == MinigameCategoryConstants.TWO_V_TWO_DUEL_MINIGAME) {
                                replacements.add(m);
                            }
                            if (categoryId == MinigameCategoryConstants.BATTLE_MINIGAME && duelCat == MinigameCategoryConstants.BATTLE_DUEL_MINIGAME) {
                                replacements.add(m);
                            }
                        }
                    }
                    replacementPool = replacements.toArray(new Minigame[0]);
                }
                String[] replacementOptions = MinigameConstants.getNames(replacementPool);
                JComboBox<String> replacementDropdown = new JComboBox<>(replacementOptions);
                replacementDropdown.setSelectedItem(originalMinigame.getName());
                replacementSelectors.put(originalMinigame, replacementDropdown);
                grid.add(originalLabel);
                grid.add(replacementDropdown);
            }

            categoryPanel.add(grid, BorderLayout.CENTER);

            JButton randomizeButton = new JButton("Randomize Category");
            randomizeButton.addActionListener(ev -> {
                List<Minigame> minigamesInCategory = categoryMap.get(category);

                List<Minigame> replacementPoolList;
                if (allowAll) {
                    replacementPoolList = new ArrayList<>(Arrays.asList(minigames));
                } else {
                    replacementPoolList = new ArrayList<>(minigamesInCategory);
                    if ("Mario Party 8".equals(selectedGame)) {
                        int categoryId = category;
                        for (Minigame m : minigames) {
                            int duelCat = m.getCategory();
                            if (categoryId == MinigameCategoryConstants.FOUR_PLAYER_MINIGAME && duelCat == MinigameCategoryConstants.FOUR_PLAYER_DUEL_MINIGAME) {
                                replacementPoolList.add(m);
                            }
                            if (categoryId == MinigameCategoryConstants.TWO_V_TWO_MINIGAME && duelCat == MinigameCategoryConstants.TWO_V_TWO_DUEL_MINIGAME) {
                                replacementPoolList.add(m);
                            }
                            if (categoryId == MinigameCategoryConstants.BATTLE_MINIGAME && duelCat == MinigameCategoryConstants.BATTLE_DUEL_MINIGAME) {
                                replacementPoolList.add(m);
                            }
                        }
                    }
                }

                Random rand = new Random();
                boolean allowDuplicates = allowDuplicatesCheckbox.isSelected();

                if (allowDuplicates) {
                    for (Minigame original : minigamesInCategory) {
                        JComboBox<String> combo = replacementSelectors.get(original);
                        if (combo != null && !replacementPoolList.isEmpty()) {
                            Minigame replacement = replacementPoolList.get(rand.nextInt(replacementPoolList.size()));
                            combo.setSelectedItem(replacement.getName());
                        }
                    }
                } else {
                    if (replacementPoolList.size() < minigamesInCategory.size()) {
                        JOptionPane.showMessageDialog(packEditorFrame, "Not enough unique minigames in category to randomize without duplicates.");
                        return;
                    }
                    Collections.shuffle(replacementPoolList);
                    for (int i = 0; i < minigamesInCategory.size(); i++) {
                        Minigame original = minigamesInCategory.get(i);
                        JComboBox<String> combo = replacementSelectors.get(original);
                        if (combo != null) {
                            Minigame replacement = replacementPoolList.get(i);
                            combo.setSelectedItem(replacement.getName());
                        }
                    }
                }
            });
            categoryPanel.add(randomizeButton, BorderLayout.SOUTH);

            mainPanel.add(categoryPanel);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton generatePackButton = new JButton("Generate Pack Code");
        generatePackButton.addActionListener(e -> {
            generatePackCodesFromSelections(selectedGame, replacementSelectors);
        });

        JButton exportButton = new JButton("Export Pack to JSON");
        exportButton.addActionListener(e -> exportPackToJson(replacementSelectors, selectedGame));

        buttonPanel.add(generatePackButton);
        buttonPanel.add(exportButton);

        mainPanel.add(buttonPanel);

        loadSelectedButton.addActionListener(e -> {
            String selectedPack = (String) savedPacksDropdown.getSelectedItem();
            if (selectedPack == null) {
                JOptionPane.showMessageDialog(packEditorFrame, "Please select a pack to load.");
                return;
            }
            File jsonFile = new File(packDir, selectedPack);
            importPackFromJsonFile(jsonFile, replacementSelectors, selectedGame);
        });

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setPreferredSize(new Dimension(700, 500));
        packEditorFrame.add(scrollPane);
        packEditorFrame.pack();
        packEditorFrame.setLocationRelativeTo(this);
        packEditorFrame.setVisible(true);
    }

    private void exportPackToJson(Map<Minigame, JComboBox<String>> selectors, String game) {
        try {
            File packDir = new File("saved_packs" + File.separator + game);
            if (!packDir.exists()) {
                packDir.mkdirs();
            }

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File outFile = new File(packDir, "pack_" + timeStamp + ".json");

            StringBuilder sb = new StringBuilder();
            sb.append("{\n");
            sb.append("  \"game\": \"").append(game).append("\",\n");
            sb.append("  \"replacements\": {\n");

            int count = 0;
            int total = 0;

            for (Map.Entry<Minigame, JComboBox<String>> entry : selectors.entrySet()) {
                Minigame oldM = entry.getKey();
                String newName = (String) entry.getValue().getSelectedItem();
                if (!oldM.getName().equals(newName)) {
                    total++;
                }
            }

            for (Map.Entry<Minigame, JComboBox<String>> entry : selectors.entrySet()) {
                Minigame oldM = entry.getKey();
                String newName = (String) entry.getValue().getSelectedItem();
                if (!oldM.getName().equals(newName)) {
                    sb.append("    \"").append(escapeJson(oldM.getName())).append("\": \"")
                            .append(escapeJson(newName)).append("\"");
                    count++;
                    if (count < total) sb.append(",");
                    sb.append("\n");
                }
            }

            sb.append("  }\n");
            sb.append("}\n");

            try (PrintWriter pw = new PrintWriter(outFile)) {
                pw.write(sb.toString());
            }

            if (count == 0) {
                JOptionPane.showMessageDialog(this, "No replacements were made — nothing to export.");
                outFile.delete();
            } else {
                JOptionPane.showMessageDialog(this, "Pack exported successfully as:\n" + outFile.getName());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error exporting JSON: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void importPackFromJsonFile(File jsonFile, Map<Minigame, JComboBox<String>> selectors, String game) {
        try {
            Scanner sc = new Scanner(jsonFile);
            StringBuilder json = new StringBuilder();
            while (sc.hasNextLine()) json.append(sc.nextLine());
            sc.close();

            String content = json.toString();
            int start = content.indexOf("\"replacements\"");
            if (start == -1) throw new IllegalArgumentException("Invalid JSON format");
            String replacements = content.substring(content.indexOf("{", start) + 1, content.indexOf("}", start));

            for (Map.Entry<Minigame, JComboBox<String>> entry : selectors.entrySet()) {
                entry.getValue().setSelectedItem(entry.getKey().getName());
            }

            for (String line : replacements.split(",")) {
                line = line.trim();
                if (line.isEmpty() || !line.contains(":")) continue;
                String[] parts = line.split(":", 2);
                String oldName = unquote(parts[0].trim());
                String newName = unquote(parts[1].trim());

                for (Map.Entry<Minigame, JComboBox<String>> entry : selectors.entrySet()) {
                    Minigame m = entry.getKey();
                    if (m.getName().equals(oldName)) {
                        entry.getValue().setSelectedItem(newName);
                        break;
                    }
                }
            }

            JOptionPane.showMessageDialog(this, "Pack imported successfully from:\n" + jsonFile.getName());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error importing JSON: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private String unquote(String s) {
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.substring(1, s.length() - 1);
        }
        return s.replace("\\\"", "\"").replace("\\\\", "\\");
    }

    private void generatePackCodesFromSelections(String game, Map<Minigame, JComboBox<String>> replacementSelectors) {
        try {
            ArrayList<String> codeBlocks = new ArrayList<>();

            for (Map.Entry<Minigame, JComboBox<String>> entry : replacementSelectors.entrySet()) {
                Minigame oldMinigame = entry.getKey();
                String newMinigameName = (String) entry.getValue().getSelectedItem();

                Minigame newMinigame = null;
                for (Minigame m : minigameMap.get(game)) {
                    if (m.getName().equals(newMinigameName)) {
                        newMinigame = m;
                        break;
                    }
                }

                if (newMinigame != null && isDifferentMinigame(oldMinigame, newMinigame)) {
                    String code = CodeGenerator.writeRawCode(game, oldMinigame, newMinigame);
                    codeBlocks.add(code);
                }
            }

            if (!codeBlocks.isEmpty()) {
                try (PrintWriter writer = new PrintWriter(new FileOutputStream(CodeGenerator.getFileName(game), true))) {
                    writer.println("$" + selectedGame + " Custom Minigame Pack");

                    for (String code : codeBlocks) {
                        writer.print(code);
                    }
                }
            }

            JOptionPane.showMessageDialog(this, "Minigame pack code generated successfully!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error generating minigame pack code: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private boolean isDifferentMinigame(Minigame oldMinigame, Minigame newMinigame) {
        return oldMinigame.getId() != newMinigame.getId();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateCode) {
            Minigame oldMinigame = getSelectedOldMinigame();
            Minigame newMinigame = getSelectedNewMinigame();
            if (oldMinigame != null && newMinigame != null && isDifferentMinigame(oldMinigame, newMinigame)) {
                CodeGenerator.generateCode(selectedGame, oldMinigame, newMinigame);
                JOptionPane.showMessageDialog(this, "Minigame replacement code generated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please select valid minigames.");
            }
        }
    }
}