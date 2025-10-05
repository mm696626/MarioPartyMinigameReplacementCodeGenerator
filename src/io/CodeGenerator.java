package io;

import ui.Minigame;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class CodeGenerator {

    public static void generateCode(String selectedGame, Minigame oldMinigame, Minigame newMinigame) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream("generatedCodes.txt", true))) {
            writer.println(writeGeneratedCode(selectedGame, oldMinigame, newMinigame));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to write code to file: " + e.getMessage());
        }
    }

    private static String writeGeneratedCode(String selectedGame, Minigame oldMinigame, Minigame newMinigame) {
        String code = "";

        //write header
        code += "$Minigame Replacement - " + oldMinigame.getName() + " ➜ " + newMinigame.getName() + "\n";


        if (selectedGame.equals("Mario Party 4")) {
            code += "2818fd2c 000000" + String.format("%02X", oldMinigame.getId() - 1) + "\n";
            code += "0218fd2c 000000" + String.format("%02X", newMinigame.getId() - 1) + "\n";
            code += "e2000001 80008000" + "\n";
        }
        else if (selectedGame.equals("Mario Party 5")) {
            code += "2822a4c4 000000" + String.format("%02X", oldMinigame.getId() - 1) + "\n";
            code += "0222a4c4 000000" + String.format("%02X", newMinigame.getId() - 1) + "\n";
            code += "e2000001 80008000" + "\n";
        }
        else if (selectedGame.equals("Mario Party 6")) {
            code += "28265ba8 000000" + String.format("%02X", oldMinigame.getId() - 1) + "\n";
            code += "02265ba8 000000" + String.format("%02X", newMinigame.getId() - 1) + "\n";
            code += "e2000001 80008000" + "\n";
        }
        else if (selectedGame.equals("Mario Party 7")) {
            code += "28291558 000000" + String.format("%02X", oldMinigame.getId() - 1) + "\n";
            code += "02291558 000000" + String.format("%02X", newMinigame.getId() - 1) + "\n";
            code += "e2000001 80008000" + "\n";
        }
        else {
            code += "282287cc 000000" + String.format("%02X", oldMinigame.getId() - 1) + "\n";
            code += "022287cc 000000" + String.format("%02X", newMinigame.getId() - 1) + "\n";
            code += "e2000001 80008000" + "\n";
        }

        return code;
    }
}
