package boardgame;

public class Board {

	private int rows;
	
	private int columns;
	
	private Piece[][] pieces;

	public Board(int rows, int columns) {
		checkBoardDimensionIsValid(rows, columns);
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	private void checkBoardDimensionIsValid(int rows, int columns) {
		if (rows < 1 || columns < 1) {
			throw new BoardException("Erro ao criar o tabuleiro: a dimensão mínima deve ser de 1 linha e 1 coluna");
		}
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece getPiece(int row, int column) {
		checkisValidPositionForPiece(row, column);
		return this.pieces[row][column];
	}

	private void checkisValidPositionForPiece(int row, int column) {
		if (!this.positionExists(row, column)) {
			throw new BoardException("A posição especificada não existe no tabuleiro.");
		}
	}
	
	public Piece getPiece(Position position) {
		return this.getPiece(position.getRow(), position.getColumn());
	}
	
	public void placePiece(Piece piece, Position position) {
		if (this.thereIsAPiece(position)) {
			throw new BoardException("Já existe uma peça na posição escolhida.");
		}
		this.pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	public boolean positionExists(Position position) {
		return this.positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean positionExists(int row, int column) {
		return row >= 0 && row < this.rows && column >= 0 && column < this.columns;
	}
	
	public boolean thereIsAPiece(Position position) {
		this.checkisValidPositionForPiece(position.getRow(), position.getColumn());
		return this.getPiece(position) != null;
	}
	
	public Piece removePiece(Position position) {
		this.checkisValidPositionForPiece(position.getRow(), position.getColumn());
		if (this.getPiece(position) == null) {
			return null;
		}
		Piece aux = this.getPiece(position);
		aux.position = null;
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}
	
}
