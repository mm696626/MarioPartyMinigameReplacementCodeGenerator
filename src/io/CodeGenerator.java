package io;

import ui.Minigame;

import javax.swing.*;
import java.io.File;
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

        code += "$Minigame Replacement - " + oldMinigame.getName() + " ➜ " + newMinigame.getName() + "\n";
        code += writeRawCode(selectedGame, oldMinigame, newMinigame);
        return code;
    }

    public static String writeRawCode(String selectedGame, Minigame oldMinigame, Minigame newMinigame) {
        StringBuilder code = new StringBuilder();

        if (selectedGame.equals("Mario Party")) {
            code.append("D10ED5DE 00").append(String.format("%02X", oldMinigame.getId())).append("\n");
            code.append("810ED5DE 00").append(String.format("%02X", newMinigame.getId())).append("\n");
        } else if (selectedGame.equals("Mario Party 4")) {
            code.append("2818FD2C 000000").append(String.format("%02X", oldMinigame.getId() - 1)).append("\n");
            code.append("0218FD2C 000000").append(String.format("%02X", newMinigame.getId() - 1)).append("\n");
            code.append("E2000001 80008000\n");
        } else if (selectedGame.equals("Mario Party 5")) {
            code.append("2822A4C4 000000").append(String.format("%02X", oldMinigame.getId() - 1)).append("\n");
            code.append("0222A4C4 000000").append(String.format("%02X", newMinigame.getId() - 1)).append("\n");
            code.append("E2000001 80008000\n");
        } else if (selectedGame.equals("Mario Party 6")) {
            code.append("28265BA8 000000").append(String.format("%02X", oldMinigame.getId() - 1)).append("\n");
            code.append("02265BA8 000000").append(String.format("%02X", newMinigame.getId() - 1)).append("\n");
            code.append("E2000001 80008000\n");
        } else if (selectedGame.equals("Mario Party 7")) {
            code.append("28291558 000000").append(String.format("%02X", oldMinigame.getId() - 1)).append("\n");
            code.append("02291558 000000").append(String.format("%02X", newMinigame.getId() - 1)).append("\n");
            code.append("E2000001 80008000\n");
        } else {
            code.append("282287CC 000000").append(String.format("%02X", oldMinigame.getId() - 1)).append("\n");
            code.append("022287CC 000000").append(String.format("%02X", newMinigame.getId() - 1)).append("\n");
            code.append("E2000001 80008000\n");
        }

        return code.toString();
    }


    public static String getFileName(String selectedGame) {
        File generatedCodesFolder = new File("generated_codes");
        if (!generatedCodesFolder.exists()) {
            generatedCodesFolder.mkdirs();
        }

        String fileName = switch (selectedGame) {
            case "Mario Party" -> "CLBE01.txt";
            case "Mario Party 4" -> "GMPE01.txt";
            case "Mario Party 5" -> "GP5E01.txt";
            case "Mario Party 6" -> "GP6E01.txt";
            case "Mario Party 7" -> "GP7E01.txt";
            default -> "RM8E01.txt";
        };

        return "generated_codes/" + fileName;
    }
}
