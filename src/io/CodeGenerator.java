package io;

import ui.Minigame;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class CodeGenerator {

    public static void generateCode(String selectedGame, Minigame oldMinigame, Minigame newMinigame) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(getFileName(selectedGame), true))) {
            writer.print(writeGeneratedCode(selectedGame, oldMinigame, newMinigame));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to write code to file: " + e.getMessage());
        }
    }

    private static String writeGeneratedCode(String selectedGame, Minigame oldMinigame, Minigame newMinigame) {
        String code = "";

        //write header
        code += "$Minigame Replacement - " + oldMinigame.getName() + " ➜ " + newMinigame.getName() + "\n";


        if (selectedGame.equals("Mario Party 4")) {
            code += "2818FD2C 000000" + String.format("%02X", oldMinigame.getId() - 1) + "\n";
            code += "0218FD2C 000000" + String.format("%02X", newMinigame.getId() - 1) + "\n";
            code += "E2000001 80008000" + "\n";
        }
        else if (selectedGame.equals("Mario Party 5")) {
            code += "2822A4C4 000000" + String.format("%02X", oldMinigame.getId() - 1) + "\n";
            code += "0222A4C4 000000" + String.format("%02X", newMinigame.getId() - 1) + "\n";
            code += "E2000001 80008000" + "\n";
        }
        else if (selectedGame.equals("Mario Party 6")) {
            code += "28265BA8 000000" + String.format("%02X", oldMinigame.getId() - 1) + "\n";
            code += "02265BA8 000000" + String.format("%02X", newMinigame.getId() - 1) + "\n";
            code += "E2000001 80008000" + "\n";
        }
        else if (selectedGame.equals("Mario Party 7")) {
            code += "28291558 000000" + String.format("%02X", oldMinigame.getId() - 1) + "\n";
            code += "02291558 000000" + String.format("%02X", newMinigame.getId() - 1) + "\n";
            code += "E2000001 80008000" + "\n";
        }
        else {
            code += "282287CC 000000" + String.format("%02X", oldMinigame.getId() - 1) + "\n";
            code += "022287CC 000000" + String.format("%02X", newMinigame.getId() - 1) + "\n";
            code += "E2000001 80008000" + "\n";
        }

        return code;
    }

    private static String getFileName(String selectedGame) {
        return switch (selectedGame) {
            case "Mario Party 4" -> "GMPE01.txt";
            case "Mario Party 5" -> "GP5E01.txt";
            case "Mario Party 6" -> "GP6E01.txt";
            case "Mario Party 7" -> "GP7E01.txt";
            default -> "RM8E01.txt";
        };
    }
}
