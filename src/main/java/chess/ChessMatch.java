package chess;

import static chess.Color.BLACK;
import static chess.Color.WHITE;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private Board board;
	
	public ChessMatch() {
		this.board = new Board(8, 8);
		this.initialSetup();
	}
	
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i=0; i<this.board.getRows(); i++) {
			for (int j=0; j<this.board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) this.board.getPiece(i, j);
			}
		}
		return mat;
	}
	
	private void placingNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	private void initialSetup() {
		this.placingNewPiece('b', 6, new Rook(this.board, WHITE));
		this.placingNewPiece('e', 8, new King(this.board, BLACK));
		this.placingNewPiece('e', 1, new King(this.board, WHITE));
	}
}
