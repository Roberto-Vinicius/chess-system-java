package chess;

import boardgame.Board;
import chess.chessPieces.King;
import chess.chessPieces.Rook;
import boardgame.Position;

public class ChessMatch {
  
  private Board board;

  //Dimensão do tabuleiro
  public ChessMatch() {
   board = new Board(8,8 );
   initialSetup();
  }

  public ChessPiece[][] getPieces() {
    ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];

    //percorre a matrix 
    for (int i = 0; i < board.getRows(); i++) {
      for (int j = 0; j < board.getColumns(); j++) {
        mat[i][j] = (ChessPiece) board.piece(i, j);
      }
    }

    return mat; //retorna a matriz de peças da partida
  }

  //iniciação da partida; Colocando as peças no tabuleiro
  private void initialSetup() {
    board.placePiece(new Rook(board, Color.WHITE), new Position(2, 1));
    board.placePiece(new King(board, Color.BLACK), new Position(2, 1));
  }
}
