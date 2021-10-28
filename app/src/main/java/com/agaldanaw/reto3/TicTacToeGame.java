package com.agaldanaw.reto3;

import android.graphics.Color;
import android.widget.Button;

import java.util.Arrays;
import java.util.Random;

public class TicTacToeGame {

    public static final char HUMAN_PLAYER = 'X';
    public static final char COMPUTER_PLAYER = 'O';

    private int computerWins = 0;
    private int humanWins = 0;
    private int ties = 0;

    private char mBoard[] = {'1','2','3','4','5','6','7','8','9'};
    private Button mBoardButtons[];
    public final int BOARD_SIZE = 9;

    private Random mRand;

    public TicTacToeGame() {
        mRand = new Random();
    }

    /*
        SET BUTTONS
    */
    public void SetButtons(Button[] buttons)
    {
        mBoardButtons = buttons;
    }

    /**
     * Return computerWins
     * */

    public String GetComputerWins()
    {
        return Integer.toString(computerWins);
    }

    /**
     * set computerWins
     * */

    public void updateComputerWins()
    {
        ++computerWins;
    }

    /**
     * Return humanWins
     * */

    public String GetHumanWins()
    {
        return Integer.toString(humanWins);
    }

    /**
     * set humanWins
     * */

    public void UpdateHumanWins()
    {
        ++humanWins;
    }

    /**
     * Return ties
     * */

    public String GetTies()
    {
        return Integer.toString(ties);
    }

    /**
     * set ties
     * */

    public void UpdateTies()
    {
        ++ties;
    }

    /**
     * Check for a winner and return a status value indicating who has won.
     * @return Return 0 if no winner or tie yet, 1 if it's a tie, 2 if X won,
     * or 3 if O won.
     */
    public int checkForWinner() {

        // Check horizontal wins
        for (int i = 0; i <= 6; i += 3)	{
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i+1] == HUMAN_PLAYER &&
                    mBoard[i+2]== HUMAN_PLAYER)
                return 2;
            if (mBoard[i] == COMPUTER_PLAYER &&
                    mBoard[i+1]== COMPUTER_PLAYER &&
                    mBoard[i+2] == COMPUTER_PLAYER)
                return 3;
        }

        // Check vertical wins
        for (int i = 0; i <= 2; i++) {
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i+3] == HUMAN_PLAYER &&
                    mBoard[i+6]== HUMAN_PLAYER)
                return 2;
            if (mBoard[i] == COMPUTER_PLAYER &&
                    mBoard[i+3] == COMPUTER_PLAYER &&
                    mBoard[i+6]== COMPUTER_PLAYER)
                return 3;
        }

        // Check for diagonal wins
        if ((mBoard[0] == HUMAN_PLAYER &&
                mBoard[4] == HUMAN_PLAYER &&
                mBoard[8] == HUMAN_PLAYER) ||
                (mBoard[2] == HUMAN_PLAYER &&
                        mBoard[4] == HUMAN_PLAYER &&
                        mBoard[6] == HUMAN_PLAYER))
            return 2;
        if ((mBoard[0] == COMPUTER_PLAYER &&
                mBoard[4] == COMPUTER_PLAYER &&
                mBoard[8] == COMPUTER_PLAYER) ||
                (mBoard[2] == COMPUTER_PLAYER &&
                        mBoard[4] == COMPUTER_PLAYER &&
                        mBoard[6] == COMPUTER_PLAYER))
            return 3;

        // Check for tie
        for (int i = 0; i < BOARD_SIZE; i++) {
            // If we find a number, then no one has won yet
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER)
                return 0;
        }

        // If we make it through the previous loop, all places are taken, so it's a tie
        return 1;
    }

    /** Return the best move for the computer to make. You must call setMove()
     * to actually make the computer move to that location.
     * @return The best move for the computer to make (0-8).
     */
    public int getComputerMove()
    {
        int move;

        // First see if there's a move O can make to win
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                char curr = mBoard[i];
                mBoard[i] = COMPUTER_PLAYER;
                if (checkForWinner() == 3) {
                    mBoard[i] = curr;
                    return i;
                }
                else
                    mBoard[i] = curr;
            }
        }

        // See if there's a move O can make to block X from winning
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                char curr = mBoard[i];   // Save the current number
                mBoard[i] = HUMAN_PLAYER;
                if (checkForWinner() == 2) {
                    //mBoard[i] = COMPUTER_PLAYER;
                    mBoard[i] = curr;
                    return i;
                }
                else
                    mBoard[i] = curr;
            }
        }

        // Generate random move
        do
        {
            move = mRand.nextInt(BOARD_SIZE);
        } while (mBoard[move] == HUMAN_PLAYER || mBoard[move] == COMPUTER_PLAYER);

        return move;
    }

    public char initPlayer()
    {
        int player = mRand.nextInt(2);
        if(player < 1)
        {
            //computer turn
            int move = getComputerMove();
            setMove(COMPUTER_PLAYER, move);

            return COMPUTER_PLAYER;
        }
        return HUMAN_PLAYER;
    }

    /** Clear the board of all X's and O's by setting all spots to OPEN_SPOT. */
    public void clearBoard(boolean eraseWins)
    {
        if(eraseWins)
        {
            computerWins = 0;
            humanWins = 0;
            ties = 0;
        }
        mBoard = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        for(int i = 0; i < BOARD_SIZE; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);

        }
    }

    public void DisableAllbuttons()
    {
        for(int i = 0; i < BOARD_SIZE; i++) {
            mBoardButtons[i].setEnabled(false);
        }
    }


    /** Set the given player at the given location on the game board.
     * The location must be available, or the board will not be changed.
     *
     * @param player - The HUMAN_PLAYER or COMPUTER_PLAYER
     * @param location - The location (0-8) to place the move
     */
    public void setMove(char player, int location)
    {
        if(mBoard[location] != HUMAN_PLAYER && mBoard[location] != COMPUTER_PLAYER )
        {
            mBoardButtons[location].setEnabled(false);
            mBoardButtons[location].setText(String.valueOf(player));

            if (player == TicTacToeGame.HUMAN_PLAYER)
                mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
            else
                mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));

            mBoard[location] = player;
        }

    }

}
