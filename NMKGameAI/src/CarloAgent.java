import java.util.*;

/**
 * An agent that plays using Monte Carlo Tree Search.
 */
public class CarloAgent {
    State root;

    /**
     * Represents a state in the game.
     */
    public class State {
        State parent;
        Ilayout layout;
        int visitCount;
        double winScore;
        List<State> childArray;

        private int profundidade;

        /**
         * Constructor.
         * @param parent       the parent of the state
         * @param layout      the layout of the state
         */
        public State(State parent, Ilayout layout, int profundidade) {
            this.parent = parent;
            this.layout = layout;
            this.visitCount = 0;
            this.winScore = 0.0;
            this.childArray = new ArrayList<>();
            this.profundidade = profundidade;
        }
    }

    /**
     * return a valid random move.
     * @param board         the board to play on
     */
    static int playRadom(Ilayout board) {
        Set<Integer> availableMoves = board.getAvailableMoves();
        if (availableMoves.isEmpty()) {
            throw new IllegalStateException("No available moves to play");
        }

        int[] moves = new int[availableMoves.size()];
        int index = 0;
        for (Integer move : availableMoves) {
            moves[index++] = move;
        }
        int randomMove = moves[new java.util.Random().nextInt(moves.length)];
        return randomMove;
    }



    /**
     * return a valid move using MCTS.
     * @param board        the board to play on
     * @return            the move to play
     */
    static int playMCTS(Ilayout board) {
        if (board.getAvailableMoves().isEmpty()) {
            throw new IllegalStateException("No available moves to play in playMCTS");
        }

        CarloAgent agent = new CarloAgent();
        State bestChild = agent.mcts(board, 1250);
        return board.getLastMove(bestChild.layout);
    }


    /**
     * return a state using MCTS.
     * @param initialLayout the layout to start from
     * @param timeLimitInMillis the time limit in milliseconds
     * @return           the state to play
     */
    public State mcts(Ilayout initialLayout, long timeLimitInMillis) {
        root = new State(null, initialLayout, 0);
        long endTime = System.currentTimeMillis() + timeLimitInMillis;

        while (System.currentTimeMillis() < endTime) {
            State leaf = select(root);
            if (leaf.visitCount != 0 && !leaf.layout.isGameOver()) {
                if (leaf.profundidade < 2) {
                    expand(leaf);
                }

                if (!leaf.childArray.isEmpty()) {
                    leaf = leaf.childArray.get(new java.util.Random().nextInt(leaf.childArray.size()));
                }
            }

            double result = simulate(leaf);
            backpropagate(leaf, result);
        }

        return findBestChildUsingUCT(root);
    }


    /*
    /**
     * return a state using MCTS.
     * @param initialLayout the layout to start from
     * @param iterations the limit number of iterations
     * @return           the state to play
     */

    /*public State mcts(Ilayout initialLayout, int iterations) {
        root = new State(null, initialLayout);
        int i = 0;

        while (i < iterations) {
            State leaf = select(root);
            if(leaf.visitCount != 0) {
                if(!leaf.layout.isGameOver()) {
                    expand(leaf);
                    leaf = leaf.childArray.get(new java.util.Random().nextInt(leaf.childArray.size()));
                }
            }

            double result = simulate(leaf);
            backpropagate(leaf, result);
            i++;
        }
        State bestChild = findBestChildUsingUCT(root);

        return bestChild;
    }*/

    /**
     * Select the leaf node with the highest UCT value.
     * @param rootNode     the root node
     * @return          the leaf node with the highest UCT value
     */
    State select(State rootNode) {
        while (rootNode.childArray.size() != 0) {
            rootNode = findBestChildUsingUCT(rootNode);
        }
        return rootNode;
    }


    /**
     * Expand the leaf node by adding all its children to the tree.
     * @param node    the node to expand
     */
    void expand(State node) {
        if(node.parent == null || node.parent.parent == null) {
            for (Ilayout suc : node.layout.children()) {
                State a = new State(node, suc, node.profundidade + 1);
                node.childArray.add(a);
            }
        }
    }

    /**
     * Simulate a random game from the given node.
     * @param node     the node to simulate from
     * @return         1 if the final state is a win, 0 if it's a draw, -1 if it's a loss
     */
    double simulate(State node) {
        Ilayout clonedLayout = (Ilayout) node.layout.clone();
        Ilayout.ID turn = root.layout.getTurn();

        while(true) {
            if(clonedLayout.isGameOver()){
                if(clonedLayout.getWinner() == turn){
                    return 1;
                }else if(clonedLayout.getWinner() == Ilayout.ID.Blank){
                    return 0;
                }else{
                    return -1.75;
                }
            }else{
                int move = playRadom(clonedLayout);
                clonedLayout.move(move);
            }
        }
    }


    /**
     * Update the node statistics back up to the root node.
     * @param leaf     the leaf node
     * @param result  the result of the playout
     */
    void backpropagate(State leaf, double result) {
        State currentNode = leaf;

        while (currentNode != null) {
            currentNode.visitCount++;
            currentNode.winScore += result;
            currentNode = currentNode.parent;
        }
    }

    /**
     * Calculate the UCT value of a node.
     * @param totalVisit    the total number of visits
     * @param nodeWinScore the number of wins
     * @param nodeVisit   the number of visits
     * @return         the UCT value
     */
    public double uctValue(int totalVisit, double nodeWinScore, int nodeVisit) {
        if (nodeVisit == 0) {
            return Integer.MAX_VALUE;
        }
        return (nodeWinScore / (double) nodeVisit) + Math.sqrt(2) * Math.sqrt(Math.log(totalVisit) / (double) nodeVisit);
    }

    /**
     * Find the best child of a node using the UCT value.
     * @param node    the node to find the best child of
     * @return       the best child of the node
     */
    State findBestChildUsingUCT(State node) {
        int totalVisit = root.visitCount;
        State bestChild = null;
        double bestUctValue = -30.0;

        for (State child : node.childArray) {
            double uctValue = uctValue(totalVisit, child.winScore, child.visitCount);
            if (uctValue > bestUctValue) {
                bestUctValue = uctValue;
                bestChild = child;
            }
        }
        return bestChild;
    }
}