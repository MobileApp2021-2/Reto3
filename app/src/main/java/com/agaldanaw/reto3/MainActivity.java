package com.agaldanaw.reto3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public TicTacToeGame mGame;
    private Button mBoardButtons[];
    private TextView mInfoTextView;
    private TextView infoHumanWins;
    private TextView infoComputerWins;
    private TextView infoTies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGame = new TicTacToeGame();
        mBoardButtons = new Button[mGame.BOARD_SIZE];

        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);
        mBoardButtons[3] = (Button) findViewById(R.id.four);
        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);
        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);
        mBoardButtons[8] = (Button) findViewById(R.id.nine);

        mGame.SetButtons(mBoardButtons);

        mInfoTextView = (TextView) findViewById(R.id.information);
        infoHumanWins = (TextView) findViewById(R.id.humanWins);
        infoComputerWins = (TextView) findViewById(R.id.computerWins);
        infoTies = (TextView) findViewById(R.id.ties);

        findViewById(R.id.rebootGame).setOnClickListener(new ButtonRebootClickListener());
        findViewById(R.id.playAgain).setOnClickListener(new ButtonPlayAgainClickListener(mGame));

        startGame();
    }

    public void SetTextWins()
    {
        String human = getResources().getString(R.string.human_wins);
        String computer = getResources().getString(R.string.computer_wins);
        String ties = getResources().getString(R.string.ties);

        infoHumanWins.setText(human + " " +  mGame.GetHumanWins());
        infoComputerWins.setText(computer + " " + mGame.GetComputerWins());
        infoTies.setText(ties + " " + mGame.GetTies());
    }

    private void startGame()
    {
        mGame.clearBoard(true);

        // Reset all buttons
        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i, mGame));
        }
        SetFirstTurn();
        SetTextWins();
    }

    public void SetFirstTurn()
    {
        char player = mGame.initPlayer();
        if(player == TicTacToeGame.HUMAN_PLAYER)
        {
            mInfoTextView.setText(R.string.first_human);
        }
        else
        {
            mInfoTextView.setText(R.string.first_computer);
            int winner = mGame.checkForWinner();
            setTextInfo(winner);
        }
    }

    public void setTextInfo(int winner)
    {
        if (winner == 0) {
            mInfoTextView.setText(R.string.turn_human);
        }
        else
        {
            if (winner == 1) {
                mGame.UpdateTies();
                mInfoTextView.setText(R.string.result_tie);
            }
            else if (winner == 2){
                mGame.UpdateHumanWins();
                mInfoTextView.setText(R.string.result_human_wins);
            }
            else {
                mGame.updateComputerWins();
                mInfoTextView.setText(R.string.result_computer_wins);
            }
            mGame.DisableAllbuttons();
        }
    }


    private class ButtonClickListener implements OnClickListener {
        private int location;
        private TicTacToeGame mGame;

        public ButtonClickListener(int location, TicTacToeGame mGame) {
            this.location = location;
            this.mGame = mGame;
        }

        public void onClick(View view) {
            if (mBoardButtons[location].isEnabled()) {
                mGame.setMove(TicTacToeGame.HUMAN_PLAYER, location);
                // If no winner yet, let the computer make a move
                int winner = mGame.checkForWinner();

                if (winner == 0) {
                    mInfoTextView.setText(R.string.turn_computer);
                    int move = mGame.getComputerMove();
                    mGame.setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    winner = mGame.checkForWinner();
                }

                setTextInfo(winner);
                SetTextWins();
            }
        }
    }

    private class ButtonRebootClickListener implements OnClickListener {
        public ButtonRebootClickListener() {
        }

        public void onClick(View view) {
            startGame();
        }
    }

    private class ButtonPlayAgainClickListener implements OnClickListener {

        private TicTacToeGame mGame;

        public ButtonPlayAgainClickListener(TicTacToeGame mGame) {
            this.mGame = mGame;
        }

        public void onClick(View view) {
            mGame.clearBoard(false);
            SetFirstTurn();
        }
    }
}