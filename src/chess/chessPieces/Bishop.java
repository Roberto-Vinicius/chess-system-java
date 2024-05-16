package chess.chessPieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

/**
 * Represents a bishop chess piece.
 */
public class Bishop extends ChessPiece {

  /**
   * Constructor for a Bishop.
   * @param board The board where the bishop will be placed.
   * @param color The color of the bishop.
   */
  public Bishop(Board board, Color color) {
    super(board, color);
  }

  /**
   * Generates a string representation of the Bishop.
   * @return The string "B" representing the Bishop.
   */
  @Override
  public String toString() {
    return "B";
  }

  /**
   * Generates a matrix of possible moves for the bishop.
   * @return A boolean matrix indicating the possible moves for the bishop.
   */
  @Override
  public boolean[][] possibleMoves() {
    boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

    Position p = new Position(0, 0);

    // Northwest
    p.setValues(position.getRow() - 1, position.getColumn() - 1);
    while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
      mat[p.getRow()][p.getColumn()] = true;
      p.setValues(p.getRow() - 1, p.getColumn() - 1);
    }
    if (getBoard().positionExists(p) && isTheOppenentPiece(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }
    
    // Northeast
    p.setValues(position.getRow() - 1, position.getColumn() + 1);
    while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
      mat[p.getRow()][p.getColumn()] = true;
      p.setValues(p.getRow() - 1, p.getColumn() + 1);
    }
    if (getBoard().positionExists(p) && isTheOppenentPiece(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }

    // Southeast
    p.setValues(position.getRow() + 1, position.getColumn() + 1);
    while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
      mat[p.getRow()][p.getColumn()] = true;
      p.setValues(p.getRow() + 1, p.getColumn() + 1);
    }
    if (getBoard().positionExists(p) && isTheOppenentPiece(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }
    
    // Southwest
    p.setValues(position.getRow() + 1, position.getColumn() - 1);
    while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
      mat[p.getRow()][p.getColumn()] = true;
      p.setValues(p.getRow() + 1, p.getColumn() - 1);
    }
    if (getBoard().positionExists(p) && isTheOppenentPiece(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }
    
    return mat;
  }
}