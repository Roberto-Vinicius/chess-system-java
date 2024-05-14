package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.ChessException;
import application.UI;
import boardgame.BoardException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {
  public static void main(String[] args) {
    
    Scanner sc = new Scanner(System.in);
    ChessMatch chessMatch = new ChessMatch();
    List<ChessPiece> captured = new ArrayList<>();

    while (!chessMatch.getCheckMate()) {
      try {
        UI.clearScreen();

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
    UI.clearScreen();
    UI.printMatch(chessMatch, captured);
  }
}
