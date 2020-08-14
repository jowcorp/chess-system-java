package chess;

import static chess.Color.BLACK;
import static chess.Color.WHITE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private Board board;
	
	private int turn;
	
	private Color currentPlayer;
	
	private List<Piece> piecesOnBoard = new ArrayList<>();
	
	private List<Piece> capturedPieces = new ArrayList<>();
	
	private boolean check;
	
	public ChessMatch() {
		this.board = new Board(8, 8);
		this.turn = 1;
		this.currentPlayer = WHITE;
		this.initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean isCheck() {
		return this.check;
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
	
	private Color getOpponentColor(Color color) {
		return WHITE.equals(color) ? BLACK : WHITE;
	}
	
	private ChessPiece getKing(Color color) {
		List<Piece> list = this.piecesOnBoard.stream().filter(x ->((ChessPiece) x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("Não existe Rei com a cor " + color + " no tabuleiro");
	}
	
	private boolean testCheck(Color color) {
		Position kingPosition = this.getKing(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = this.piecesOnBoard.stream().filter(x ->((ChessPiece) x).getColor() == getOpponentColor(color)).collect(Collectors.toList());
		int i = 0;
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
			i++;
		}
		return false;
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		this.piecesOnBoard.add(piece);
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
		
		if (this.testCheck(currentPlayer)) {
			this.undoMove(source, target, capturedPiece);
			throw new ChessException("Você não pode se colocar em xeque.");
		}
		
		this.check = this.testCheck(getOpponentColor(currentPlayer));
		
		this.nextTurn();
		return (ChessPiece) capturedPiece;
	}
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("Não existe peça na posição de origem.");
		}
		if (this.currentPlayer != ((ChessPiece) this.board.getPiece(position)).getColor()) {
			throw new ChessException("A peça escolhida pertence ao adversário.");
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
		if (capturedPiece != null) {
			this.piecesOnBoard.remove(capturedPiece);
			this.capturedPieces.add(capturedPiece);
		}
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		Piece p = board.removePiece(target);
		board.placePiece(p, source);
		
		if (capturedPiece != null) {
			this.board.placePiece(capturedPiece, target);
			this.capturedPieces.remove(capturedPiece);
			this.piecesOnBoard.add(capturedPiece);
		}
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		this.validateSourcePosition(position);
		return this.board.getPiece(position).possibleMoves();
	}
	
	public void nextTurn() {
		this.turn++;
		this.currentPlayer = (WHITE.equals(this.currentPlayer) ? BLACK : WHITE);
	}
}
