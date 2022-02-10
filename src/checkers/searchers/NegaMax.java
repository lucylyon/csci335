package checkers.searchers;
import checkers.core.Checkerboard;
import checkers.core.CheckersSearcher;
import checkers.core.Move;
import core.Duple;
import java.util.Optional;
import java.util.function.ToIntFunction;

public class NegaMax extends CheckersSearcher {

    private int numNodes = 0;

    public NegaMax(ToIntFunction<Checkerboard> e) {
        super(e);
    }

    @Override
    public int numNodesExpanded() {
        return numNodes;
    }

    @Override
    public Optional<Duple<Integer, Move>> selectMove(Checkerboard board) {
        return selectMoveHelp(board, 0);
    }

    private Optional<Duple<Integer, Move>> selectMoveHelp(Checkerboard board, int depth) {
        Optional<Duple<Integer, Move>> best = Optional.empty();

        //if game over
        if (board.gameOver()){
            //if i win
            if (board.playerWins(board.getCurrentPlayer())){
                return Optional.of(new Duple<>(Integer.MAX_VALUE, board.getLastMove()));
            //if opponent wins
            } else if (board.playerWins(board.getCurrentPlayer().opponent())){
                return Optional.of(new Duple<>(-Integer.MAX_VALUE, board.getLastMove()));
            //if tie
            } else {
                return Optional.of(new Duple<>(0, board.getLastMove()));
            }
        }

        if (getDepthLimit() < depth){
            return Optional.of(new Duple<>(getEvaluator().applyAsInt(board), board.getLastMove()));
        }

        for (Checkerboard alternative: board.getNextBoards()) {
            numNodes += 1;
            int negation = board.getCurrentPlayer() != alternative.getCurrentPlayer() ? -1 : 1;
            int scoreFor = negation * selectMoveHelp(alternative, depth + 1).get().getFirst() ;
            if (best.isEmpty() || best.get().getFirst() < scoreFor) {
                best = Optional.of(new Duple<>(scoreFor, alternative.getLastMove()));
            }
        }
        return best;
    }

}