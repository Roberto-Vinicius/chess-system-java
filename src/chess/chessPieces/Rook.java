package chess.chessPieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {
  
  /**
   * Constructor for a Rook.
   * @param board The board where the rook will be placed.
   * @param color The color of the rook.
   */
  public Rook(Board board, Color color) {
    super(board, color);
  }

  /**
   * Generates a string representation of the Rook.
   * @return The string "R" representing the Rook.
   */
  @Override
  public String toString() {
    return "R";
  }

  /**
   * Generates a matrix of possible moves for the rook.
   * @return A boolean matrix indicating the possible moves for the rook.
   */
  @Override
  public boolean[][] possibleMoves() {
    boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
    Position p = new Position(0, 0);

    // Up
    p.setValues(position.getRow() - 1, position.getColumn());
    // Move upwards until there is a piece
    while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
      mat[p.getRow()][p.getColumn()] = true;
      p.setRow(p.getRow() - 1);
    }
    if (getBoard().positionExists(p) && isTheOppenentPiece(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }

    // Left
    p.setValues(position.getRow(), position.getColumn() - 1);
    while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
      mat[p.getRow()][p.getColumn()] = true;
      p.setColumn(p.getColumn() - 1);
    }
    if (getBoard().positionExists(p) && isTheOppenentPiece(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }

    // Right
    p.setValues(position.getRow(), position.getColumn() + 1);
    while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
      mat[p.getRow()][p.getColumn()] = true;
      p.setColumn(p.getColumn() + 1);
    }
    if (getBoard().positionExists(p) && isTheOppenentPiece(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }

    // Down
    p.setValues(position.getRow() + 1, position.getColumn());
    while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
      mat[p.getRow()][p.getColumn()] = true;
      p.setRow(p.getRow() + 1);
    }
    if (getBoard().positionExists(p) && isTheOppenentPiece(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }
    
    return mat;
  }
}
