package chess.chessPieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {
  
  /**
   * Constructor for a Queen.
   * @param board The board where the queen will be placed.
   * @param color The color of the queen.
   */
  public Queen(Board board, Color color) {
    super(board, color);
  }

  /**
   * Generates a string representation of the Queen.
   * @return The string "Q" representing the Queen.
   */
  @Override
  public String toString() {
    return "Q";
  }

  /**
   * Generates a matrix of possible moves for the queen.
   * @return A boolean matrix indicating the possible moves for the queen.
   */
  @Override
  public boolean[][] possibleMoves() {
    boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
    Position p = new Position(0, 0);

    // Up
    p.setValues(position.getRow() - 1, position.getColumn());
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

    // nw
    p.setValues(position.getRow() - 1, position.getColumn() - 1);
    while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
      mat[p.getRow()][p.getColumn()] = true;
      p.setValues(p.getRow() - 1, p.getColumn() - 1);
    }
    if (getBoard().positionExists(p) && isTheOppenentPiece(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }

    // ne
    p.setValues(position.getRow() - 1, position.getColumn() + 1);
    while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
      mat[p.getRow()][p.getColumn()] = true;
      p.setValues(p.getRow() - 1, p.getColumn() + 1);
    }
    if (getBoard().positionExists(p) && isTheOppenentPiece(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }

    // se
    p.setValues(position.getRow() + 1, position.getColumn() + 1);
    while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
      mat[p.getRow()][p.getColumn()] = true;
      p.setValues(p.getRow() + 1, p.getColumn() + 1);
    }
    if (getBoard().positionExists(p) && isTheOppenentPiece(p)) {
      mat[p.getRow()][p.getColumn()] = true;
    }

    // sw
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
