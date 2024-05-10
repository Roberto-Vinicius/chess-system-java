package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.ChessException;
import application.UI;
import boardgame.BoardException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {
  public static void main(String[] args) {
    
    Scanner sc = new Scanner(System.in);
    ChessMatch chessMatch = new ChessMatch();

    while (true) {
      try {
        UI.clearScreen();

        UI.printBoard(chessMatch.getPieces());
        System.out.println();
        System.out.print("Source: ");
        ChessPosition source = UI.readChessPosition(sc);

        System.out.println();
        System.out.print("Target: ");
        ChessPosition target = UI.readChessPosition(sc);

        ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
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
  }
}
