package chess;

import boardgame.Board;

public class ChessMatch {
  
  private Board board;

  //Dimensão do tabuleiro
  public ChessMatch() {
   board = new Board(8,8 );
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
}
