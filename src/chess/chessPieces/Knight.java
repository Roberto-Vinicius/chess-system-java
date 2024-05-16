package chess.chessPieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece{

  /**
   * Constructor for a Knight.
   * @param board The board where the knight will be placed.
   * @param color The color of the knight.
   */
  public Knight(Board board, Color color) {
    super(board, color);
  }

  /**
   * Generates a string representation of the Knight.
   * @return The string "N" representing the Knight.
   */
  @Override
  public String toString() {
    return "N";
  }

  /**
   * Checks if a position is valid for the knight to move to.
   * @param position The position to check.
   * @return True if the position is valid for the knight to move to, false otherwise.
   */
  private boolean canMove(Position position) {
    ChessPiece p = (ChessPiece)getBoard().piece(position);
    return p == null || p.getColor() != getColor();
  }

  /**
   * Generates a matrix of possible moves for the knight.
   * @return A boolean matrix indicating the possible moves for the knight.
   */
  @Override
  public boolean[][] possibleMoves() {
    boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

    Position p = new Position(0, 0);

    p.setValues(position.getRow() - 1, position.getColumn() - 2);
    if (getBoard().positionExists(p) && canMove(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }
    
    p.setValues(position.getRow() - 2, position.getColumn() - 1);
    if (getBoard().positionExists(p) && canMove(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }
    
    p.setValues(position.getRow() - 2, position.getColumn() + 1);
    if (getBoard().positionExists(p) && canMove(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }
    
    p.setValues(position.getRow() - 1, position.getColumn() + 2);
    if (getBoard().positionExists(p) && canMove(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }
    
    p.setValues(position.getRow() + 1, position.getColumn() + 2);
    if (getBoard().positionExists(p) && canMove(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }
   
    p.setValues(position.getRow() + 2, position.getColumn() + 1);
    if (getBoard().positionExists(p) && canMove(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }

    p.setValues(position.getRow() + 2, position.getColumn() - 1);
    if (getBoard().positionExists(p) && canMove(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }
    
    p.setValues(position.getRow() + 1, position.getColumn() - 2);
    if (getBoard().positionExists(p) && canMove(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }
    return mat;
  }
}
