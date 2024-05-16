/**
 * @file Program.java
 * @brief This file contains the main class and entry point of the chess game application.
 */

package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import boardgame.BoardException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * @brief The main class of the chess game application.
 */

public class Program {

  /**
   * @brief The entry point of the application.
   * 
   * This method starts the chess game and handles the game loop until checkmate is reached.
   * It prompts players for moves, validates input, performs moves, and checks for special moves like promotions.
   * It also handles exceptions and prints messages accordingly.
   */

  public static void main(String[] args) {
    
    Scanner sc = new Scanner(System.in);
    ChessMatch chessMatch = new ChessMatch();
    List<ChessPiece> captured = new ArrayList<>();

    while (!chessMatch.getCheckMate()) {
      try {
        // Clear the console screen
        UI.clearScreen();

        // Print the current state of the chess match
        UI.printMatch(chessMatch, captured);
        System.out.println();
        System.out.print("Source: ");
        ChessPosition source = UI.readChessPosition(sc);

        System.out.println();
        boolean[][] possibleMoves = chessMatch.possibleMoves(source);
        UI.clearScreen();
        UI.printBoard(chessMatch.getPieces(), possibleMoves);
        System.out.print("Target: ");
        ChessPosition target = UI.readChessPosition(sc);

        ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

        if (capturedPiece != null) {
          captured.add(capturedPiece);
        }

        if (chessMatch.getPromoted() != null) {
          // Prompt the player for piece promotion
					System.out.print("Enter piece for promotion (B/N/R/Q): ");
					String type = sc.nextLine().toUpperCase();
          while (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
            System.out.print("Invalid value! Enter piece for promotion (B/N/R/Q): ");
					  type = sc.nextLine().toUpperCase();
          }
					chessMatch.replacePromotedPiece(type);
				} 
      }
      catch (BoardException e) {
        System.out.println(e.getLocalizedMessage());
        sc.nextLine();
      } 
      catch (InputMismatchException e) {
        System.out.println(e.getLocalizedMessage());
        sc.nextLine();
      }
    } 
    // Clear the console screen
    UI.clearScreen();
    // Print the final state of the chess match
    UI.printMatch(chessMatch, captured);
  }
}
