package com.myproject.model;

import javax.swing.ImageIcon;
import java.util.ArrayList;
// -------------------------------------------------------------------------
/**
 * Represents a Pawn game piece. Unique in that it can move two locations on its
 * first turn and therefore requires a new 'notMoved' variable to keep track of
 * its turns.
 *
 * @author Ben Katz (bakatz)
 * @author Myles David II (davidmm2)
 * @author Danielle Bushrow (dbushrow)
 * @version 2010.11.17
 */
public class Pawn
    extends ChessGamePiece{
    private boolean notMoved;
    // ----------------------------------------------------------
    /**
     * Create a new Pawn object.
     *
     * @param board
     *            the board to create the pawn on
     * @param row
     *            row of the pawn
     * @param col
     *            column of the pawn
     * @param color
     *            either GamePiece.WHITE, BLACK, or UNASSIGNED
     */
    public Pawn( ChessGameBoard board, int row, int col, int color ){
        super( board, row, col, color, true );
        notMoved = true;
        possibleMoves = calculatePossibleMoves( board );
    }
    /**
     * Moves this pawn to a row and col
     *
     * @param board
     *            the board to move on
     * @param row
     *            the row to move to
     * @param col
     *            the col to move to
     * @return boolean true if the move was successful, false otherwise
     */
    @Override
    public boolean move( ChessGameBoard board, int row, int col ){
        if ( super.move( board, row, col ) ){
            notMoved = false;
            possibleMoves = calculatePossibleMoves( board );
            if ( ( getColorOfPiece() == ChessGamePiece.BLACK && row == 7 )
                || ( getColorOfPiece() == ChessGamePiece.WHITE && row == 0 ) ){ // pawn has reached the end of the board, promote it to queen
                board.getCell( row, col ).setPieceOnSquare( new Queen(
                    board,
                    row,
                    col,
                    getColorOfPiece() ) );
            }
            return true;
        }
        return false;
    }
    /**
     * Calculates the possible moves for this piece. These are ALL the possible
     * moves, including illegal (but at the same time valid) moves.
     *
     * @param board
     *            the game board to calculate moves on
     * @return ArrayList<String> the moves
     */
    @Override
    protected ArrayList<String> calculatePossibleMoves(ChessGameBoard board) {
        ArrayList<String> moves = new ArrayList<>();
        if (!isPieceOnScreen()) {
            return moves;
        }

        int direction = getColorOfPiece() == ChessGamePiece.WHITE ? -1 : 1;
        int maxIter = notMoved ? 2 : 1;

        // Check for normal moves
        for (int i = 1; i <= maxIter; i++) {
            int newRow = pieceRow + i * direction;
            if (isValidMove(board, newRow, pieceColumn)) {
                moves.add(newRow + "," + pieceColumn);
            } else {
                break;
            }
        }

        // Check for enemy capture points
        if (isEnemy(board, pieceRow + direction, pieceColumn - 1)) {
            moves.add((pieceRow + direction) + "," + (pieceColumn - 1));
        }
        if (isEnemy(board, pieceRow + direction, pieceColumn + 1)) {
            moves.add((pieceRow + direction) + "," + (pieceColumn + 1));
        }

        return moves;
    }

    private boolean isValidMove(ChessGameBoard board, int row, int col) {
        return isOnScreen(row, col) && board.getCell(row, col).getPieceOnSquare() == null;
    }
    
    @Override
    public boolean isEnemy(ChessGameBoard board, int row, int col) {
        return isOnScreen(row, col) && board.getCell(row, col).getPieceOnSquare() != null
                && board.getCell(row, col).getPieceOnSquare().getColorOfPiece() != getColorOfPiece();
    }
    
    @Override
    public boolean isOnScreen(int row, int col) {
        return row >= 0 && row <= 7 && col >= 0 && col <= 7;
    }

    /**
     * Creates an icon for this piece depending on the piece's color.
     *
     * @return ImageIcon the ImageIcon representation of this piece.
     */
    @Override
    public ImageIcon createImageByPieceType(){
        if ( getColorOfPiece() == ChessGamePiece.WHITE ){
            return new ImageIcon(
                getClass().getResource("/Original/ChessImages/WhitePawn.gif")
            );            
        }
        else if ( getColorOfPiece() == ChessGamePiece.BLACK ){
            return new ImageIcon(
                getClass().getResource("/Original/ChessImages/BlackPawn.gif")
            );            
        }
        else
        {
            return new ImageIcon(
                getClass().getResource("/Original/ChessImages/default-Unassigned.gif")
            );           
        }
    }
}
