import java.util.*;
// import java.util.ArrayList;

/**
 * Represents a board.
 */
public class Board implements Ilayout,Cloneable{


    private ID[][] board;
    private ID playersTurn;
    private ID winner;
    private HashSet<Integer> movesAvailable;

    private int moveCount;
    private boolean gameOver;


    Board() {
        board = new ID[N][M];
        movesAvailable = new HashSet<>();
        reset();
    }

    /**
     * Set the cells to be blank and load the available moves (all the moves are
     * available at the start of the game).
     */
    private void initialize () {
        for (int row = 0; row < N; row++)
            for (int col = 0; col < M; col++) {
                board[row][col] = ID.Blank;
            }
        movesAvailable.clear();

        for (int i = 0; i < N*M; i++) {
            movesAvailable.add(i);
        }
    }

    /**
     * Restart the game with a new blank board.
     */
    public void reset() {
        moveCount = 0;
        gameOver = false;
        playersTurn = ID.X;
        winner = ID.Blank;
        initialize();
    }

    /**
     * Places an X or an O on the specified index depending on whose turn it is.
     * @param index     position starts in 0 and increases from left to right and from top to bottom
     * @return          true if the move has not already been played
     */
    public boolean move (int index) {
        return move(index% M, index/M);
    }

    /**
     * Places an X or an O on the specified location depending on who turn it is.
     * @param x         the x coordinate of the location
     * @param y         the y coordinate of the location
     * @return          true if the move has not already been played
     */
    public boolean move (int x, int y) {

        if (gameOver) {
            throw new IllegalStateException("Game over. No more moves can be played.");
        }

        if (board[y][x] == ID.Blank) {
            board[y][x] = playersTurn;
        } else {
            return false;
        }

        moveCount++;
        movesAvailable.remove(y * M + x);

        // The game is a draw.
        if (moveCount == N * M) {
            winner = ID.Blank;
            gameOver = true;
        }

        // Check for a winner.

        if (checkForWin(x, y, playersTurn)) {
            winner = playersTurn;
            gameOver = true;
        }


        playersTurn = (playersTurn == ID.X) ? ID.O : ID.X;
        return true;
    }

    /**
     * Checks if there is a winner.
     * @param x         the x coordinate of the location
     * @param y         the y coordinate of the location
     * @return          true if there is a winner, false otherwise
     */
    private boolean checkForWin(int x, int y, ID player) {
        // Check the row
        int index = 0;
        for (int i = 0; i < M; i++) {
            if (board[y][i] != player) {
                index = 0;
                continue;
            }
            if (index == K-1) {
                return true; // Player has won the row
            }
            index++;
        }

        // Check the column
        index = 0;
        for (int i = 0; i < N; i++) {
            if (board[i][x] != player) {
                index = 0;
                continue;
            }
            if (index == K-1) {
                return true; // Player has won the column
            }
            index++;
        }

        // Verificar todas as diagonais do tipo superior esquerdo para inferior direito
        for (int startX = 0; startX < M; startX++) {
            for (int startY = 0; startY < N; startY++) {
                 index = 0;
                for (int i = 0; i < K; i++) {
                    if (startX + i < M && startY + i < N && board[startY + i][startX + i] == player) {
                        index++;
                        if (index == K) {
                            return true; // O jogador ganhou na diagonal
                        }
                    } else {
                        break;
                    }
                }
            }
        }

        // Verificar todas as diagonais do tipo superior direito para inferior esquerdo
        for (int startX = 0; startX < M; startX++) {
            for (int startY = 0; startY < N; startY++) {
                 index = 0;
                for (int i = 0; i < K; i++) {
                    if (startX - i >= 0 && startY + i < N && board[startY + i][startX - i] == player) {
                        index++;
                        if (index == K) {
                            return true; // O jogador ganhou na diagonal
                        }
                    } else {
                        break;
                    }
                }
            }
        }

        return false; // Nenhuma vitória
    }

    /**
     * Check to see if the game is over (if there is a winner or a draw).
     * @return          true if the game is over
     */
    public boolean isGameOver () {
        return gameOver;
    }

    /**
     * Check to see who's turn it is.
     * @return          the player who's turn it is
     */
    public ID getTurn () {
        return playersTurn;
    }

    /**
     * Check to see who won the game.
     * @return          the player who won (or Blank if the game is a draw)
     */
    public ID getWinner () {
        if (!gameOver) {
            throw new IllegalStateException("Not over yet!");
        }
        return winner;
    }

    /**
     * Get the indexes of all the positions on the board that are empty.
     * @return          the empty cells
     */
    public HashSet<Integer> getAvailableMoves () {
        return movesAvailable;
    }

    /**
     * Returns a deep copy of the board.
     * @return  an deep copy of the board
     */
    public Object clone () {
        try {
            Board b = (Board) super.clone();

            b.board = new ID[N][M];
            for (int i = 0; i < N; i++)
                for (int j=0; j<M; j++)
                    b.board[i][j] = this.board[i][j];

            b.playersTurn       = this.playersTurn;
            b.winner            = this.winner;
            b.movesAvailable    = new HashSet<Integer>();
            b.movesAvailable.addAll(this.movesAvailable);
            b.moveCount         = this.moveCount;
            b.gameOver          = this.gameOver;
            return b;
        }
        catch (Exception e) {
            throw new InternalError();
        }
    }

    /**
     * ToString method.
     *
     *
     * @return          String representation of the board
     */
    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < N; y++) {
            for (int x = 0; x < M; x++) {

                if (board[y][x] == ID.Blank) {
                    sb.append("-");
                } else {
                    sb.append(board[y][x].name());
                }
                sb.append(" ");

            }
            if (y != N -1) {
                sb.append("\n");
            }
        }
        return new String(sb);
    }

    /**
     * Generates all the children of the receiver.
     * @return the children of the receiver.
     */
    public List<Ilayout> children() {
        List<Ilayout> childStates = new ArrayList<>();

        if (gameOver) {
            return childStates; // No children when the game is over
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] == ID.Blank) {
                    // Create a copy of the current game state
                    Board childState = (Board) this.clone();
                    // Make the move for the current player
                    childState.move(j, i);
                    childStates.add(childState);
                }
            }
        }

        return childStates;
    }

    /**
     * Given the next layout, return the move made from the receiver to the next layout.
     * @param nextLayout         the next layout
     * @return          int representing the move made from the receiver to the next layout
     */
    @Override
    public int getLastMove(Ilayout nextLayout) {
        if (!(nextLayout instanceof Board)) {
            throw new IllegalArgumentException("Invalid argument type. Expecting Board instance.");
        }

        Board nextBoard = (Board) nextLayout;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] != nextBoard.board[i][j]) {
                    return i * M + j;
                }
            }
        }

        // No move made or boards are identical
        return -1;
    }

    /**
     * Compares the receiver to the argument.
     * @param other         the object to compare to the receiver
     * @return          true if the argument is equal to the receiver
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false;
        Board that = (Board) other;

        for (int y = 0; y < N; y++)
            for (int x = 0; x < M; x++)
                if (board[x][y] != that.board[x][y]) return false;
        return true;
    }

    /**
     * Returns a hash code value for the object.
     * @return          a hash code value for this object
     */
    @Override
    public int hashCode() {
        return board.hashCode();
    }

    /**
     * Check if the specified location is blank.
     * @param index        the position on the board
     * @return          true if the location is blank
     */
    public boolean isBlank (int index) {
        int x=index/M;
        int y=index%M;
        return (board[x][y] == ID.Blank);
    }
}
