package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final int BOARD_SIZE = 3;

    boolean didWin = false;
    boolean XTurn = true;
    ImageView[][] board = new ImageView[BOARD_SIZE][BOARD_SIZE];
    int movesCounter = 0;

    // Return true if current player won/ false if not
    protected boolean checkWinInCurrentPosition(int positionI, int positionJ, Drawable.ConstantState player){
        int col, row, diag, rdiag;
        col = row = diag = rdiag = 0;

        // Accumulates each of the player's possibilities to win for the chosen cell
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[positionI][i].getDrawable().getConstantState() == player) col++;
            if (board[i][positionJ].getDrawable().getConstantState() == player) row++;
            if (board[i][i].getDrawable().getConstantState() == player) diag++;
            if (board[i][(BOARD_SIZE-1)-i].getDrawable().getConstantState() == player) rdiag++;
        }

        return (col == BOARD_SIZE || row == BOARD_SIZE || diag == BOARD_SIZE || rdiag == BOARD_SIZE);
    }

    private void changePlayerTurn(boolean isX){
        XTurn = isX;

            ImageView playerTurn = (ImageView) findViewById(R.id.tiktaktoe_text);
        if(isX){
            playerTurn.setImageResource(R.drawable.xplay);
        }else{
            playerTurn.setImageResource(R.drawable.oplay);
        }

    }

    private void restartGame(){
        // Update variables
        movesCounter = 0;
        didWin = false;

        // Start new game with X
        changePlayerTurn(true);

        findViewById(R.id.tictactoe_replay).setVisibility(View.INVISIBLE);

        // Restart board
        for(int i=0; i<BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++)
            {
                board[i][j].setImageResource(R.drawable.empty);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up x/o places on board
        for(int i=0; i<BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                String buttonID = "tiktaktoe_btn" + (BOARD_SIZE*i + j + 1);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                board[i][j] = ((ImageView) findViewById(resID));
                setOnClick(board[i][j], i, j);
            }
        }

        // Set play again button onClick
        ImageButton replayBtn =(ImageButton)findViewById(R.id.tictactoe_replay);
        replayBtn.setVisibility(View.INVISIBLE);
        replayBtn.setOnClickListener(v -> restartGame());
    }

    private void setOnClick(final ImageView btn, int i, int j){
        btn.setOnClickListener(v -> {
            ImageView playerTurn = (ImageView) findViewById(R.id.tiktaktoe_text);

            // If not already pressed
            final Drawable.ConstantState X = getDrawable(R.drawable.x).getConstantState();
            final Drawable.ConstantState O = getDrawable(R.drawable.o).getConstantState();

            if((btn.getDrawable().getConstantState() !=  O)
               &&
               (btn.getDrawable().getConstantState() !=  X)
               && !didWin) {

                if (XTurn) {
                    btn.setImageResource(R.drawable.x);
                } else {
                    btn.setImageResource(R.drawable.o);
                }
                movesCounter++;

                // Check if there is a winner
                didWin = checkWinInCurrentPosition(i,j,btn.getDrawable().getConstantState());

                if (didWin){
                    if(XTurn) {
                        playerTurn.setImageResource(R.drawable.xwin);
                    } else {
                        playerTurn.setImageResource(R.drawable.owin);
                    }
                    findViewById(R.id.tictactoe_replay).setVisibility(View.VISIBLE);
                } else if (movesCounter == 9 ){ // If no winner check if board is full
                    playerTurn.setImageResource(R.drawable.nowin);
                    findViewById(R.id.tictactoe_replay).setVisibility(View.VISIBLE);
                }else{ // If no player won and there is still empty places on the board
                    changePlayerTurn(!XTurn);
                }
            }
        });
    }

}

