package chess;

import boardgame.Position;

/**
 * Represents a position on a chess board.
 */
public class ChessPosition {
  
  private char column; // Column of the position
  private int row; // Row of the position

  /**
   * Constructor for a ChessPosition.
   * @param column The column of the position.
   * @param row The row of the position.
   * @throws ChessException if the provided column or row are not within valid bounds.
   */
  public ChessPosition(char column, int row) {
    if (column < 'a' || column > 'h' || row > 8) {
      throw new ChessException("Error instantiating ChessPosition. Valid values are from a1 to h8");
    }
    this.column = column;
    this.row = row;
  }

  /**
   * Gets the column of the position.
   * @return The column of the position.
   */
  public char getColumn() {
    return column;
  }

  /**
   * Gets the row of the position.
   * @return The row of the position.
   */
  public int getRow() {
    return row;
  }

  /**
   * Converts the ChessPosition to a Position object.
   * @return The Position object equivalent to this ChessPosition.
   */
  protected Position toPosition() {
    return new Position(8 - row, column - 'a');
  }

  /**
   * Converts a Position object to a ChessPosition.
   * @param position The Position object to convert.
   * @return The ChessPosition equivalent to the provided Position.
   */
  protected static ChessPosition fromPosition(Position position) {
    return new ChessPosition((char)('a' + position.getColumn()),8 - position.getRow());
  }

  /**
   * Generates a string representation of the ChessPosition.
   * @return A string representing the ChessPosition in the format "column + row".
   */
  @Override
  public String toString() {
    return "" + column + row;
  }
}
