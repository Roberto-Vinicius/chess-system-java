package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {

  // Text colors
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  // Background colors
  public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
  public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
  public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
  public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
  public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
  public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
  public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
  public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

  // Clear the screen
  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  /**
   * Reads a chess position from the user.
   * @param sc The Scanner object to read input from.
   * @return The ChessPosition object read from the input.
   * @throws InputMismatchException if the input is not valid.
   */
  public static ChessPosition readChessPosition(Scanner sc) {
    try {
      String s = sc.nextLine();
      char column = s.charAt(0); 
      int row = Integer.parseInt(s.substring(1));
      return new ChessPosition(column, row);
    } 
    catch (RuntimeException e) {
      throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8.");
    }
  }

  /**
   * Prints the current state of the chess match.
   * @param chessMatch The ChessMatch object representing the current match.
   * @param captured The list of captured pieces.
   */
  public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
    printBoard(chessMatch.getPieces());
    System.out.println();
    printCapturePieces(captured);
    System.out.println();
    System.out.println("Turn: " + chessMatch.getTurn());
    if (!chessMatch.getCheckMate()) {
      System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
      if (chessMatch.getCheck()) {
        System.out.println("CHECK!!");
      }
    }
    else {
      System.out.println("CHECKMATE!!");
      System.out.println("Winner: " + chessMatch.getCurrentPlayer());
    }
  }

  /**
   * Prints the chess board.
   * @param pieces The matrix representing the pieces on the board.
   */
  public static void printBoard(ChessPiece[][] pieces) {
    for (int i = 0; i < pieces.length; i++) {
      System.out.print((8 - i) + " ");
      for (int j = 0; j < pieces.length; j++) {
        printPiece(pieces[i][j], false);
      }
      System.out.println();
    }
    System.out.println("  a b c d e f g h");
  }

  /**
   * Prints the chess board with possible moves highlighted.
   * @param pieces The matrix representing the pieces on the board.
   * @param possibleMoves The matrix representing the possible moves for each piece.
   */
  public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
    for (int i = 0; i < pieces.length; i++) {
      System.out.print((8 - i) + " ");
      for (int j = 0; j < pieces.length; j++) {
        printPiece(pieces[i][j], possibleMoves[i][j]);
      }
      System.out.println();
    }
    System.out.println("  a b c d e f g h");
  }

  private static void printPiece(ChessPiece piece, boolean background) {
    if (background) {
      System.out.print(ANSI_GREEN_BACKGROUND);
    }
    if (piece == null) {
      System.out.print("-" + ANSI_RESET);
    }
    else {
        if (piece.getColor() == Color.WHITE) {
            System.out.print(ANSI_WHITE + piece + ANSI_RESET);
        }
        else {
            System.out.print(ANSI_RED + piece + ANSI_RESET);
        }
    }
    System.out.print(" ");
  }

  // Print captured pieces
  private static void printCapturePieces(List<ChessPiece> capture) {
    List<ChessPiece> white = capture.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
    List<ChessPiece> black = capture.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
    
    System.out.println("Capture pieces: ");
    System.out.print("White: ");
    System.out.print(ANSI_WHITE);
    System.out.print(Arrays.toString(white.toArray()));
    System.out.print(ANSI_RESET);
    System.out.println();

    System.out.print("Black: ");
    System.out.print(ANSI_RED);
    System.out.print(Arrays.toString(black.toArray()));
    System.out.print(ANSI_RESET);
    System.out.println();
  } 
}