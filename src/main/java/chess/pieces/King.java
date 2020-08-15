package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
	
	private ChessMatch chessMatch; 

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}
	
	private boolean testRookCastling(Position position) {
		ChessPiece p = ((ChessPiece) this.getBoard().getPiece(position));
		return p != null && p instanceof Rook && p.getColor() == this.getColor() && p.getMoveCount() == 0;
	}
	
	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) this.getBoard().getPiece(position);
		return p == null || p.getColor() != this.getColor();
	}

	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[this.getBoard().getRows()][this.getBoard().getColumns()];
		Position p = new Position(0, 0);
		
		// Above
		p.setValues(position.getRow() - 1, position.getColumn());
		if (this.getBoard().positionExists(p) && this.canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Below
		p.setValues(position.getRow() + 1, position.getColumn());
		if (this.getBoard().positionExists(p) && this.canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Left
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (this.getBoard().positionExists(p) && this.canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Right
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (this.getBoard().positionExists(p) && this.canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// NW
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (this.getBoard().positionExists(p) && this.canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// SE
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (this.getBoard().positionExists(p) && this.canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// NE
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (this.getBoard().positionExists(p) && this.canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// SW
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (this.getBoard().positionExists(p) && this.canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Castling
		if (!this.chessMatch.isCheck() && this.getMoveCount() == 0) {
			// Roque pequeno (com a Torre do lado do Rei)
			Position posRook1 = new Position(this.position.getRow(), this.position.getColumn() + 3);
			if (this.testRookCastling(posRook1)) {
				// Casas entre o Rei e a Torre da direita
				Position p1 = new Position(this.position.getRow(), this.position.getColumn() + 1);
				Position p2 = new Position(this.position.getRow(), this.position.getColumn() + 2);
				if (this.getBoard().getPiece(p1) == null && this.getBoard().getPiece(p2) == null) {
					mat[position.getRow()][this.position.getColumn() + 2] = true;
				}
			}
		}
		
		if (!this.chessMatch.isCheck() && this.getMoveCount() == 0) {
			// Roque grande (com a Torre do lado da Rainha)
			Position posRook2 = new Position(this.position.getRow(), this.position.getColumn() - 4);
			if (this.testRookCastling(posRook2)) {
				// Casas entre o Rei e a Torre da esquerda
				Position p1 = new Position(this.position.getRow(), this.position.getColumn() - 1);
				Position p2 = new Position(this.position.getRow(), this.position.getColumn() - 2);
				Position p3 = new Position(this.position.getRow(), this.position.getColumn() - 3);
				if (this.getBoard().getPiece(p1) == null && this.getBoard().getPiece(p2) == null && this.getBoard().getPiece(p3) == null ) {
					mat[position.getRow()][this.position.getColumn() - 2] = true;
				}
			}
		}
				
		return mat;
	}
	
}
