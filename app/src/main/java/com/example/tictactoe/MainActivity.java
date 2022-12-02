package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final int BOARD_SIZE = 3;

    boolean didWin = false;
    boolean XTurn = true;
    ImageView[][] board = new ImageView[BOARD_SIZE][BOARD_SIZE];
    int movesCounter = 0;

    // Return true if current player won/ false if not
    protected boolean checkWinInCurrentPosition(int positionI, int positionJ, Drawable.ConstantState player){
        //check col
        for(int j = 0; j < BOARD_SIZE; j++){
            if(board[positionI][j].getDrawable().getConstantState() != player)
                break;
            if(j == BOARD_SIZE-1){
                return true;
            }
        }

        //check row
        for(int i = 0; i < BOARD_SIZE; i++){
            if(board[i][positionJ].getDrawable().getConstantState() != player)
                break;
            if(i == BOARD_SIZE-1){
                return true;
            }
        }

        //check diag
        if(positionI == positionJ){
            for(int i = 0; i < BOARD_SIZE; i++){
                if(board[i][i].getDrawable().getConstantState() != player)
                    break;
                if(i == BOARD_SIZE-1){
                    return true;
                }
            }
        }

        //check anti diag
        if(positionI+positionJ == BOARD_SIZE - 1){
            for(int i = 0; i < BOARD_SIZE; i++){
                if(board[i][(BOARD_SIZE-1)-i].getDrawable().getConstantState() != player)
                    break;
                if(i == BOARD_SIZE-1){
                    return true;
                }
            }
        }

        return false;
    }

    protected void restartGame(){
        movesCounter = 0;
        didWin = false;
        for(int i=0; i<BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
               // remove img
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
    }

    private void setOnClick(final ImageView btn, int i, int j){
        btn.setOnClickListener(v -> {
            ImageView playerTurn = (ImageView) findViewById(R.id.tiktaktoe_text);

            // If not already pressed
            final Drawable.ConstantState X = getDrawable( R.drawable.x).getConstantState();
            final Drawable.ConstantState O = getDrawable( R.drawable.o).getConstantState();

            if((btn.getDrawable().getConstantState() !=  O)
               &&
               (btn.getDrawable().getConstantState() !=  X)
               && !didWin) {

                if (XTurn) {
                    btn.setImageResource(R.drawable.x);
                    playerTurn.setImageResource(R.drawable.oplay);
                } else {
                    btn.setImageResource(R.drawable.o);
                    playerTurn.setImageResource(R.drawable.xplay);
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
                    findViewById(R.id.tiktaktoe_text).setVisibility(View.VISIBLE);
                } else if (movesCounter == 9 ){ // If no winner check if board is full
                    playerTurn.setImageResource(R.drawable.nowin);
                }

                    findViewById(R.id.tiktaktoe_text).setVisibility(View.VISIBLE);
                // If game not over change turns
                XTurn = !XTurn;
            }
        });
    }
}

