package edu.sdccd.cisc191.utilities;

import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;

public class GameUI extends VBox {
    private final TextArea outputArea;

    public GameUI() {
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setFont(Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 13));
        outputArea.setWrapText(true);
        outputArea.setPrefHeight(300); // Adjust as needed
        this.getChildren().add(outputArea);
    }

    /**
     * Appends a message to the output area.
     * @param message The message to display.
     */
    public void appendMessage(String message) {
        outputArea.appendText(message + "\n");
    }

    /**
     * Clears all messages from the output area.
     */
    public void clearMessages() {
        outputArea.clear();
    }
}
