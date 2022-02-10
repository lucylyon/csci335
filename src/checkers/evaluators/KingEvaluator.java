package checkers.evaluators;

import checkers.core.Checkerboard;

import java.util.function.ToIntFunction;

public class KingEvaluator implements ToIntFunction<Checkerboard> {
    public int applyAsInt(Checkerboard c) {
        int myKings = c.numKingsOf(c.getCurrentPlayer());
        int myRegPieces = c.numPiecesOf(c.getCurrentPlayer()) - c.numKingsOf(c.getCurrentPlayer());
        int myScore = (myKings * 2) + myRegPieces;

        int opponentKings = c.numKingsOf(c.getCurrentPlayer().opponent());
        int opponentRegPieces = c.numPiecesOf(c.getCurrentPlayer().opponent()) - c.numKingsOf(c.getCurrentPlayer().opponent());
        int opponentScore = (opponentKings * 2) + opponentRegPieces;

        return myScore - opponentScore;
    }
}