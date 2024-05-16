/**
 * @file ChessMatch.java
 * @brief This file contains the implementation of the ChessMatch class.
 */

package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Position;
import boardgame.Piece;
import chess.chessPieces.Bishop;
import chess.chessPieces.King;
import chess.chessPieces.Knight;
import chess.chessPieces.Pawn;
import chess.chessPieces.Queen;
import chess.chessPieces.Rook;

/**
 * @brief Represents a game of chess.
 */

public class ChessMatch {

  /**
   * @brief Constructs a new ChessMatch object.
   * 
   * Initializes the chess game with an 8x8 board and sets up the initial board configuration.
   */
  
  private int turn;
  private Color currentPlayer;
  private Board board;
  private boolean check;
  private boolean checkMate;
  private ChessPiece enPassantVulnerable;
  private ChessPiece promoted;

  private List<Piece> piecesOnTheBoard = new ArrayList<>();
  private List<Piece> capturedPieces = new ArrayList<>();

  //Dimensão do tabuleiro
  public ChessMatch() {
   board = new Board(8,8 );
   turn = 1;
   currentPlayer = Color.WHITE;
   initialSetup();
  }

  /**
   * @brief Gets the current turn number.
   * @return The current turn number.
   */

  public int getTurn() {
    return turn;
  }

  /**
   * @brief Gets the color of the current player.
   * @return The color of the current player.
   */
  
  public Color getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Retorna se o rei do jogador atual está em xeque.
   * @return true se o rei do jogador atual estiver em xeque, false caso contrário.
   */
  /**
 * Retrieves whether the current player's king is in check.
 * @return true if the current player's king is in check, false otherwise.
 */
  public boolean getCheck() {
    return check;
  }

  /**
  * Retrieves whether the current player's king is in checkmate.
  * @return true if the current player's king is in checkmate, false otherwise.
  */
  public boolean getCheckMate() {
    return checkMate;
  }

  /**
  * Retrieves the piece vulnerable to "en passant" move.
  * @return the piece vulnerable to "en passant" move, or null if there is none.
  */
  public ChessPiece getEnPassantVulnerable() {
    return enPassantVulnerable;
  }

  /**
  * Retrieves the piece promoted during the game.
  * @return the piece promoted during the game, or null if no piece was promoted.
  */
  public ChessPiece getPromoted() {
    return promoted;
  }

  /**
  * Retrieves a matrix representing the current state of the chessboard.
  * @return a matrix of game pieces.
  */
  public ChessPiece[][] getPieces() {
    ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];

    // Iterate over the matrix
    for (int i = 0; i < board.getRows(); i++) {
      for (int j = 0; j < board.getColumns(); j++) {
        mat[i][j] = (ChessPiece) board.piece(i, j);
      }
    }

    return mat; // Returns the matrix of game pieces
  }

  /**
  * Retrieves a boolean matrix representing the possible moves of a piece.
  * @param sourcePosition the position of the piece to check possible moves for.
  * @return a boolean matrix representing the possible moves.
  */
  public boolean[][] possibleMoves(ChessPosition sourcePosition) {
    Position position = sourcePosition.toPosition();
    validateSourcePosition(position);
    return board.piece(position).possibleMoves();
  }

  /**
  * Performs a chess move, moving a piece from source position to target position.
  * Also handles special situations like pawn promotion and "en passant" moves.
  * @param sourcePosition the source position of the piece to be moved.
  * @param targetPosition the target position where the piece will be moved to.
  * @return the captured piece during the move, if any, or null otherwise.
  * @throws ChessException if the move results in check for the current player.
  */

  public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
    Position source = sourcePosition.toPosition();
    Position target = targetPosition.toPosition();
    validateSourcePosition(source);
    validateTargetPosition(source, target);
    Piece capturePiece = makeMove(source, target);

    // Checks if the move leaves own king in check
    if (testCheck(currentPlayer)) {
      undoMove(source, target, capturePiece);
      throw new ChessException("Kamikazes not here");
    }

    // Gets the moved piece
    ChessPiece movedPiece = (ChessPiece)board.piece(target);

    //#SpecialMove Promotion
    promoted = null;
    if (movedPiece instanceof Pawn) {
      if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
        promoted = (ChessPiece)board.piece(target);
        promoted = replacePromotedPiece("Q");
      }
    }

    // Checks if the opponent is in check after the move
    check = (testCheck(opponent(currentPlayer))) ? true : false;

    // Checks if the opponent is in checkmate
    if (testCheckMate(opponent(currentPlayer))) {
      checkMate = true;
    }
    else {
      nextTurn();
    }

    // #specialmove en passant
    if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
      enPassantVulnerable = movedPiece;
    }
    else {
      enPassantVulnerable = null;
    }

    return (ChessPiece)capturePiece;
  }

  /**
  * Replaces a promoted pawn with a piece chosen by the player.
  * @param type the type of piece the pawn was promoted to ("B" for bishop, "N" for knight, "R" for rook, "Q" for queen).
  * @return the new promoted piece.
  * @throws IllegalStateException if there is no piece to be promoted.
  */

  public ChessPiece replacePromotedPiece(String type) {
    if (promoted == null) {
      throw new IllegalStateException("There is no piece to be promoted");
    }
    if (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
      return promoted;
    }

    Position pos = promoted.getChessPosition().toPosition();
    Piece p = board.removePiece(pos);
    piecesOnTheBoard.remove(p);

    // Creates and places the new promoted piece on the board
    ChessPiece newPiece = newPiece(type, promoted.getColor());
    board.placePiece(newPiece, pos);
    piecesOnTheBoard.add(newPiece);

    return newPiece;
  }

  /**
  * Creates a new piece based on the provided type and color.
  * @param type the type of piece to be created ("B" for bishop, "N" for knight, "Q" for queen, "R" for rook).
  * @param color the color of the piece to be created.
  * @return a new instance of the specified piece.
  */


  private ChessPiece newPiece(String type, Color color) {
    if (type.equals("B")) return new Bishop(board, color);
    if (type.equals("N")) return new Knight(board, color);
    if (type.equals("Q")) return new Queen(board, color);
    return new Rook(board, color);
  }
  
  /**
  * Performs a chess piece movement on the board.
  * @param source the source position of the piece to be moved.
  * @param target the target position where the piece will be moved to.
  * @return the piece captured during the movement, if any, or null otherwise.
  */
  private Piece makeMove(Position source, Position target) {
    ChessPiece p = (ChessPiece)board.removePiece(source);
    p.increaseMoveCount();
    Piece capturedPiece = board.removePiece(target);
    board.placePiece(p, target);
  
    // Capturing a piece if it exists
    if (capturedPiece != null) {
        piecesOnTheBoard.remove(capturedPiece);
        capturedPieces.add(capturedPiece);
    }
  
    // Special move: castling kingside
    if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
        Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
        Position targetT = new Position(source.getRow(), source.getColumn() + 1);
        ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
        board.placePiece(rook, targetT);
        rook.increaseMoveCount();
    }
  
    // Special move: castling queenside
    if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
        Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
        Position targetT = new Position(source.getRow(), source.getColumn() - 1);
        ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
        board.placePiece(rook, targetT);
        rook.increaseMoveCount();
    }
  
    // Special move: "en passant"
    if (p instanceof Pawn) {
        if (source.getColumn() != target.getColumn() && capturedPiece == null) {
            Position pawnPosition;
            if (p.getColor() == Color.WHITE) {
                pawnPosition = new Position(target.getRow() + 1, target.getColumn());
            } else {
                pawnPosition = new Position(target.getRow() - 1, target.getColumn());
            }
            capturedPiece = board.removePiece(pawnPosition);
            capturedPieces.add(capturedPiece);
            piecesOnTheBoard.remove(capturedPiece);
        }
    }
  
    return capturedPiece;
  }
  
  /**
  * Undoes a movement made on the board.
  * @param source the source position of the movement.
  * @param target the target position of the movement.
  * @param capturedPiece the piece captured during the movement.
  */
  private void undoMove(Position source, Position target, Piece capturedPiece) {
    ChessPiece p = (ChessPiece)board.removePiece(target);
    p.decreaseMoveCount();
    
    board.placePiece(p, source);
  
    // Puts the captured piece back on the board, if any
    if (capturedPiece != null) {
        board.placePiece(capturedPiece, target);
        capturedPieces.remove(capturedPiece);
        piecesOnTheBoard.add(capturedPiece);
    }
  
    // Undoes the special move of castling kingside
    if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
        Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
        Position targetT = new Position(source.getRow(), source.getColumn() + 1);
        ChessPiece rook = (ChessPiece)board.removePiece(targetT);
        board.placePiece(rook, sourceT);
        rook.decreaseMoveCount();
    }
  
    // Undoes the special move of castling queenside
    if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
        Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
        Position targetT = new Position(source.getRow(), source.getColumn() - 1);
        ChessPiece rook = (ChessPiece)board.removePiece(targetT);
        board.placePiece(rook, sourceT);
        rook.decreaseMoveCount();
    }
  
    // Undoes the special move of "en passant"
    if (p instanceof Pawn) {
        if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
            ChessPiece pawn = (ChessPiece)board.removePiece(target);
            Position pawnPosition;
            if (p.getColor() == Color.WHITE) {
                pawnPosition = new Position(3, target.getColumn());
            } else {
                pawnPosition = new Position(4, target.getColumn());
            }
            board.placePiece(pawn, pawnPosition);
        }
    }
  }
  
    /**
  * Validates if the source position of a movement is valid.
  * @param position the source position of the movement.
  * @throws ChessException if the source position does not contain a piece of the current player or if there are no possible moves for the piece.
  */
  private void validateSourcePosition(Position position) {
    if (!board.thereIsAPiece(position)) {
        throw new ChessException("There is no piece on source position");
    }
    if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
        throw new ChessException("The chosen piece is not yours.");
    }
    if (!board.piece(position).isThereAnyPossibleMove()) {
        throw new ChessException("There is no possible moves for the chosen piece");
    }
  }

  /**
  * Validates if the target position of a movement is valid for the source piece.
  * @param source the source position of the movement.
  * @param target the target position of the movement.
  * @throws ChessException if the source piece cannot move to the target position.
  */
  private void validateTargetPosition(Position source, Position target) {
    if (!board.piece(source).possibleMove(target)) {
        throw new ChessException("The chosen piece can't move to target position");
    }
  }

  /**
  * Advances to the next turn by changing the current player.
  */
  private void nextTurn() {
    turn++;
    currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
  }

  /**
  * Gets the opponent's color of the current player.
  * @param color the color of the current player.
  * @return the opponent's color.
  */
  private Color opponent(Color color) {
    return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
  }

  /**
  * Finds the king of a specific color on the board.
  * @param color the color of the king to be found.
  * @return the instance of the specified color's king.
  * @throws IllegalStateException if there is no king of the specified color on the board.
  */
  private ChessPiece king(Color color) {
    List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
    for (Piece p : list) {
        if (p instanceof King) {
            return(ChessPiece)p;
        }
    }
    throw new IllegalStateException("There is no " + color + " King on the board");
  }

  /**
  * Checks if the king of a specific color is in check.
  * @param color the color of the king to be checked.
  * @return true if the king is in check, false otherwise.
  */
  private boolean testCheck(Color color) {
    Position kingPosition = king(color).getChessPosition().toPosition();
    List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
    for (Piece p : opponentPieces) {
        boolean[][] mat = p.possibleMoves();
        if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
            return true;
        }
    }
    return false;
  }

  /**
  * Checks if the king of a specific color is in checkmate.
  * @param color the color of the king to be checked.
  * @return true if the king is in checkmate, false otherwise.
  */
  private boolean testCheckMate(Color color) {
    if (!testCheck(color)) {
        return false;
    }

    List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
    for (Piece p : list) {
        boolean[][] mat = p.possibleMoves();
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j ++) {
                if (mat[i][j]) {
                    Position source = ((ChessPiece)p).getChessPosition().toPosition();
                    Position target = new Position(i, j);

                    Piece capturedPiece = makeMove(source, target);
                    boolean testCheck = testCheck(color);
                    undoMove(source, target, capturedPiece);

                    if (!testCheck) {
                        return false;
                    }
                }
            }
        }
    }
    return true;
  }
      /**
  * Places a new piece on the board.
  * @param column the column where the piece will be placed.
  * @param row the row where the piece will be placed.
  * @param piece the piece to be placed on the board.
  */
  private void placeNewPiece(char column, int row, ChessPiece piece) {
    board.placePiece(piece, new ChessPosition(column, row).toPosition());
    piecesOnTheBoard.add(piece);
  }
    /**
  * Sets up the initial arrangement of pieces on the chessboard.
  */
  private void initialSetup() {
    //White pieces
    placeNewPiece('a', 1, new Rook(board, Color.WHITE));
    placeNewPiece('b', 1, new Knight(board, Color.WHITE));
    placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
    placeNewPiece('d', 1, new Queen(board, Color.WHITE));
    placeNewPiece('e', 1, new King(board, Color.WHITE, this));
    placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
    placeNewPiece('g', 1, new Knight(board, Color.WHITE));
    placeNewPiece('h', 1, new Rook(board, Color.WHITE));
    placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
    placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
    placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
    placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
    placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
    placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
    placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
    placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));
    //Black pieces
    placeNewPiece('a', 8, new Rook(board, Color.BLACK));
    placeNewPiece('b', 8, new Knight(board, Color.BLACK));
    placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
    placeNewPiece('d', 8, new Queen(board, Color.BLACK));
    placeNewPiece('e', 8, new King(board, Color.BLACK, this));
    placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
    placeNewPiece('g', 8, new Knight(board, Color.BLACK));
    placeNewPiece('h', 8, new Rook(board, Color.BLACK));
    placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
    placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
    placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
    placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
    placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
    placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
    placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
    placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
  }
}