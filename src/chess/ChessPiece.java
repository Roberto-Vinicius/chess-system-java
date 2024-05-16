package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

/**
 * Abstract class representing a chess piece.
 */
public abstract class ChessPiece extends Piece {

  private Color color; // Piece color
  private int moveCount; // Number of moves made by the piece

  /**
   * Constructor for a ChessPiece.
   * @param board The board where the piece will be placed.
   * @param color The color of the piece.
   */
  public ChessPiece(Board board, Color color) {
    super(board); // Calls the constructor of the superclass (Piece)
    this.color = color;
  }

  /**
   * Gets the color of the piece.
   * @return The color of the piece.
   */
  public Color getColor() {
    return color;
  }

  /**
   * Gets the number of moves made by the piece.
   * @return The number of moves made by the piece.
   */
  public int getMoveCount() {
    return moveCount;
  }
  
  /**
   * Increases the move count of the piece by 1.
   */
  protected void increaseMoveCount() {
    moveCount++;
  }

  /**
   * Decreases the move count of the piece by 1.
   */
  protected void decreaseMoveCount() {
    moveCount--;
  }

  /**
   * Gets the chess position of the piece.
   * @return The chess position of the piece.
   */
  public ChessPosition getChessPosition() {
    return ChessPosition.fromPosition(position);
  }

  /**
   * Checks if the piece at a given position is an opponent's piece.
   * @param position The position to check.
   * @return True if the piece at the given position is an opponent's piece, false otherwise.
   */
  protected boolean isTheOppenentPiece(Position position) {
    ChessPiece p = (ChessPiece)getBoard().piece(position);
    return p != null && p.getColor() != color;
  }
}