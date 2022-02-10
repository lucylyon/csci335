package checkers.searchers;

import checkers.core.Checkerboard;
import checkers.core.CheckersSearcher;
import checkers.core.Move;
import core.Duple;

import java.util.Optional;
import java.util.function.ToIntFunction;

public class AlphaBeta2 extends CheckersSearcher {
    private int numNodes = 0;

    public AlphaBeta2(ToIntFunction<Checkerboard> e) {
        super(e);
    }

    @Override
    public int numNodesExpanded() {
        return numNodes;
    }

    @Override
    public Optional<Duple<Integer, Move>> selectMove(Checkerboard board) {
        return selectMoveHelp(board, 0, -Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    private Optional<Duple<Integer, Move>> selectMoveHelp(Checkerboard board, int depth, int alpha, int beta) {
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

        //search until quiescent - //check if any capture moves left & hit depth limit
        if (getDepthLimit() < depth && board.allCaptureMoves(board.getCurrentPlayer()).isEmpty()) {
            return Optional.of(new Duple<>(getEvaluator().applyAsInt(board), board.getLastMove()));
        }

        for (Checkerboard alternative: board.getNextBoards()) {
            numNodes += 1;
            int negation = board.getCurrentPlayer() != alternative.getCurrentPlayer() ? -1 : 1;
            int scoreFor = negation * selectMoveHelp(alternative, depth + 1, -beta, -alpha).get().getFirst() ;
            if (best.isEmpty() || best.get().getFirst() < scoreFor) {
                best = Optional.of(new Duple<>(scoreFor, alternative.getLastMove()));
                alpha = Math.max(alpha, scoreFor);
            }
            if (alpha >= beta){
                return best;
            }
        }
        return best;
    }
}
//    Search until quiescent (inactive or dormant)
//    if last move you made was a capture and youve hit the depth limit - ignore the depth limit
