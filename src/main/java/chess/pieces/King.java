package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	public King(Board board, Color color) {
		super(board, color);
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
				
		return mat;
	}
	
}
