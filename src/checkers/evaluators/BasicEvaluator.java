package checkers.evaluators;

import checkers.core.Checkerboard;

import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;

public class BasicEvaluator implements ToIntFunction<Checkerboard> {
    @Override
    public int applyAsInt(Checkerboard c) {
        return c.numPiecesOf(c.getCurrentPlayer()) - c.numPiecesOf(c.getCurrentPlayer().opponent());
    }
}


