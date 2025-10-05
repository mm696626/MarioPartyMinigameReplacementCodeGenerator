package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MarioPartyMinigameReplacementCodeGeneratorUI extends JFrame implements ActionListener {

    private JComboBox<String> marioPartyGameDropdown;
    private JComboBox<String> oldMinigameDropdown;
    private JComboBox<String> newMinigameDropdown;
    private JButton generateCode;

    public MarioPartyMinigameReplacementCodeGeneratorUI() {
        setTitle("Mario Party Minigame Replacement Code Generator");
        generateUI();
    }

    private void generateUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        panel.add(new JLabel("Select Mario Party Version:"), gbc);
        gbc.gridy = 1;
        marioPartyGameDropdown = new JComboBox<>(new String[] {
                "Mario Party 4", "Mario Party 5", "Mario Party 6", "Mario Party 7", "Mario Party 8"
        });
        panel.add(marioPartyGameDropdown, gbc);

        gbc.gridy = 2;
        panel.add(new JLabel("Select Old Minigame:"), gbc);
        gbc.gridy = 3;
        oldMinigameDropdown = new JComboBox<>(new String[] {
                "Minigame A", "Minigame B", "Minigame C"
        });
        panel.add(oldMinigameDropdown, gbc);

        gbc.gridy = 3;
        panel.add(new JLabel("Select New Minigame:"), gbc);
        gbc.gridy = 4;
        newMinigameDropdown = new JComboBox<>(new String[] {
                "Minigame X", "Minigame Y", "Minigame Z"
        });
        panel.add(newMinigameDropdown, gbc);

        gbc.gridy++;
        generateCode = new JButton("Generate");
        generateCode.addActionListener(this);
        panel.add(generateCode, gbc);

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateCode) {

        }
    }
}
