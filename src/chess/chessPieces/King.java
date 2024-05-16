package chess.chessPieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{

  private ChessMatch chessMatch;

  /**
   * Constructor for a King.
   * @param board The board where the king will be placed.
   * @param color The color of the king.
   * @param chessMatch The current chess match.
   */
  public King(Board board, Color color, ChessMatch chessMatch) {
    super(board, color);
    this.chessMatch = chessMatch;
  }

  /**
   * Generates a string representation of the King.
   * @return The string "K" representing the King.
   */
  @Override
  public String toString() {
    return "K";
  }

  /**
   * Tests if a rook is eligible for castling.
   * @param position The position of the rook.
   * @return True if the rook is eligible for castling, false otherwise.
   */
  private boolean testRookCastling(Position position) {
    ChessPiece p = (ChessPiece)getBoard().piece(position);
    return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
  }

  /**
   * Checks if a position is valid for the king to move to.
   * @param position The position to check.
   * @return True if the position is valid for the king to move to, false otherwise.
   */
  private boolean canMove(Position position) {
    ChessPiece p = (ChessPiece)getBoard().piece(position);
    return p == null || p.getColor() != getColor();
  }

  /**
   * Generates a matrix of possible moves for the king.
   * @return A boolean matrix indicating the possible moves for the king.
   */
  @Override
  public boolean[][] possibleMoves() {
    boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

    Position p = new Position(0, 0);

    // Up
    p.setValues(position.getRow() - 1, position.getColumn());
    if (getBoard().positionExists(p) && canMove(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }
    // Down
    p.setValues(position.getRow() + 1, position.getColumn());
    if (getBoard().positionExists(p) && canMove(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }
    // Left
    p.setValues(position.getRow(), position.getColumn() - 1);
    if (getBoard().positionExists(p) && canMove(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }
    // Right
    p.setValues(position.getRow(), position.getColumn() + 1);
    if (getBoard().positionExists(p) && canMove(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }
    // Northwest
    p.setValues(position.getRow() - 1, position.getColumn() - 1);
    if (getBoard().positionExists(p) && canMove(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }
    // Northeast
    p.setValues(position.getRow() - 1, position.getColumn() + 1);
    if (getBoard().positionExists(p) && canMove(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }
    // Southwest
    p.setValues(position.getRow() + 1, position.getColumn() - 1);
    if (getBoard().positionExists(p) && canMove(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }
    // Southeast
    p.setValues(position.getRow() + 1, position.getColumn() + 1);
    if (getBoard().positionExists(p) && canMove(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }
    // Special move castling
    if (getMoveCount() == 0 && !chessMatch.getCheck()) {
      // Castling kingside rook
      Position posT1 = new Position(position.getRow(), position.getColumn() + 3);
      if (testRookCastling(posT1)) {
        Position p1 = new Position(position.getRow(), position.getColumn() + 1);
        Position p2 = new Position(position.getRow(), position.getColumn() + 2);
        if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
          mat[position.getRow()][position.getColumn() + 2] = true;
        }
      }
      // Castling queenside rook
      Position posT2 = new Position(position.getRow(), position.getColumn() - 4);
      if (testRookCastling(posT2)) {
        Position p1 = new Position(position.getRow(), position.getColumn() - 1);
        Position p2 = new Position(position.getRow(), position.getColumn() - 2);
        Position p3 = new Position(position.getRow(), position.getColumn() - 3);
        if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
          mat[position.getRow()][position.getColumn() - 2] = true;
        }
      }
    }
    return mat;
  }
}

