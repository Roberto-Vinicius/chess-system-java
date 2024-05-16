/**
 * @file Board.java
 * @brief This file contains the implementation of the Board class, representing the game board.
 */

package boardgame;

/**
 * @brief Represents the game board.
 */

public class Board {
  
  private int rows; ///< The number of rows on the board.
  private int columns; ///< The number of columns on the board.
  private Piece[][] pieces; ///< A 2D array representing the pieces on the board.

  /**
   * @brief Constructs a Board object with the specified number of rows and columns.
   * 
   * @param rows The number of rows on the board.
   * @param columns The number of columns on the board.
   * @throws BoardException If the number of rows or columns is less than 1.
   */

  public Board(int rows, int columns) {
    if (rows < 1 || columns < 1) {
      throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
    }
    this.rows = rows;
    this.columns = columns;
    pieces = new Piece[rows][columns];
  }

    /**
   * @brief Gets the number of rows on the board.
   * @return The number of rows on the board.
   */

  public int getRows() {
    return rows;
  }

  /**
   * @brief Gets the number of columns on the board.
   * @return The number of columns on the board.
   */

  public int getColumns() {
    return columns;
  }

  /**
   * @brief Gets the piece at the specified position on the board.
   * 
   * @param row The row of the position.
   * @param column The column of the position.
   * @return The piece at the specified position.
   * @throws BoardException If the position is not on the board.
   */
  
  public Piece piece(int row, int column) {
    if (!positionExists(row, column)) {
      throw new BoardException("Position not on the board");
    }

    return pieces[row][column];
  }

  public Piece piece(Position position) {
    if (!positionExists(position)) {
      throw new BoardException("Position not on the board");
    }

    return pieces[position.getRow()][position.getColumn()];
  }

  //muda uma peça de posição
  public void placePiece(Piece piece, Position position) {
    if (thereIsAPiece(position)) {
      throw new BoardException("There is already a piece on position " + position);
    }

    pieces[position.getRow()][position.getColumn()] = piece;
    piece.position = position;
  }

  //Remove a peça
  public Piece removePiece(Position position) {
    if (!positionExists(position)) {
      throw new BoardException("Position not on the board");
    }
    if (piece(position) == null) {
      return null;
    }
    Piece aux = piece(position);
    aux.position = null;
    pieces[position.getRow()][position.getColumn()] = null;

    return aux;
  }

  private boolean positionExists(int row, int column) {
    return row >= 0 && row < rows && column >= 0 && column < columns;
  }

  //testa se a posição existe
  public boolean positionExists(Position position) {
    return positionExists(position.getRow(), position.getColumn());
  }

  public boolean thereIsAPiece(Position position) {
    if (!positionExists(position)) {
      throw new BoardException("Position not on the board");
    }
    return piece(position) != null;
  }
}