package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> capturedPieces = new ArrayList<>();
		
		while (!chessMatch.isCheckmate()) {
			try {
				UI.clearScreen();
				UI.printMatch(chessMatch, capturedPieces);
				System.out.println();
				System.out.print("Origem: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				
				System.out.println();
				System.out.print("Destino: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				ChessPiece captured = chessMatch.performChessMove(source, target);
				
				if (captured != null) {
					capturedPieces.add(captured);
				}
				
				if (chessMatch.getPromoted() != null) {
					System.out.println("Escolha uma peça para promoção (B, R, N ou Q): ");
					String type = sc.nextLine();
					chessMatch.replacePromotedPiece(type);
				}
				
			} catch (ChessException ce) {
				System.out.println(ce.getMessage());
				sc.nextLine();
			} catch (InputMismatchException ime) {
				System.out.println(ime.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printMatch(chessMatch, capturedPieces);
	}

}
