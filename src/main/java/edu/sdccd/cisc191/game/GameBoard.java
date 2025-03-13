package edu.sdccd.cisc191.game;

public class GameBoard {
    private int[][] board;
    private final int rows = 5;
    private final int cols = 5;

    public GameBoard() {
        board = new int[rows][cols];
    }

    /**
     * Initializes the game board with default values.
     */
    public void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = 0; // Assume 0 means empty space
            }
        }
    }

    public int getCellValue(int row, int col) {
        int value = board[row][col];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = 1;
                    value -= 1;
                    return value;
                }
            }
        }
        return board[row][col];
    }

    public void setCellValue(int row, int col, int value) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = 1;
                    value += 1;
                }
            }
        }
        board[row][col] = value;
    }

    public int[][] getBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = 1;
                }
            }
        }
        return board;
    }
}