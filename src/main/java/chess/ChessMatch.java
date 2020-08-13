package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
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
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	private void initialSetup() {
		this.placeNewPiece('c', 1, new Rook(board, Color.WHITE));
		this.placeNewPiece('c', 2, new Rook(board, Color.WHITE));
		this.placeNewPiece('d', 2, new Rook(board, Color.WHITE));
		this.placeNewPiece('e', 2, new Rook(board, Color.WHITE));
		this.placeNewPiece('e', 1, new Rook(board, Color.WHITE));
		this.placeNewPiece('d', 1, new King(board, Color.WHITE));

		this.placeNewPiece('c', 7, new Rook(board, Color.BLACK));
		this.placeNewPiece('c', 8, new Rook(board, Color.BLACK));
		this.placeNewPiece('d', 7, new Rook(board, Color.BLACK));
		this.placeNewPiece('e', 7, new Rook(board, Color.BLACK));
		this.placeNewPiece('e', 8, new Rook(board, Color.BLACK));
		this.placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePos, ChessPosition targetPos) {
		Position source = sourcePos.toPosition();
		Position target = targetPos.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		return (ChessPiece) capturedPiece;
	}
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("Não existe peça na posição de origem.");
		}
		if(!board.getPiece(position).isThereAnyPossibleMove()) {
			throw new ChessException("Não há movimentos possíveis pra peça escolhida.");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if (!this.board.getPiece(source).possibleMove(target)) {
			throw new ChessException("A peça escolhida não pode se mover para o destino selecionado.");
		}
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		return capturedPiece;
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		this.validateSourcePosition(position);
		return this.board.getPiece(position).possibleMoves();
	}
}
