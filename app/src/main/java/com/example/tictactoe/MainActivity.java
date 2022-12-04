package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int BOARD_SIZE = 3;

    int winLineImageNumber = 0;
    boolean XTurn = true;
    ImageView[][] board = new ImageView[BOARD_SIZE][BOARD_SIZE];
    int movesCounter = 0;

    // Return true if current player won/ false if not
    private WinDirection checkWinInCurrentPosition(int positionI, int positionJ, Drawable.ConstantState player){
        HashMap<WinDirection, Integer> winCounterDict = new HashMap<WinDirection, Integer>() {{
            put(WinDirection.WinCol, 0);
            put(WinDirection.WinRow, 0);
            put(WinDirection.WinDiag, 0);
            put(WinDirection.WinRdiag, 0);}};

        // Accumulates each of the player's possibilities to win for the chosen cell
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[positionI][i].getDrawable().getConstantState() == player)
                winCounterDict.put(WinDirection.WinRow, winCounterDict.get(WinDirection.WinRow) + 1);
            if (board[i][positionJ].getDrawable().getConstantState() == player)
                winCounterDict.put(WinDirection.WinCol, winCounterDict.get(WinDirection.WinCol) + 1);
            if (board[i][i].getDrawable().getConstantState() == player)
                winCounterDict.put(WinDirection.WinDiag, winCounterDict.get(WinDirection.WinDiag) + 1);
            if (board[i][(BOARD_SIZE-1)-i].getDrawable().getConstantState() == player)
                winCounterDict.put(WinDirection.WinRdiag, winCounterDict.get(WinDirection.WinRdiag) + 1);
        }
        
        WinDirection winDirection = Collections.max(winCounterDict.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
        if (winCounterDict.get(winDirection) == 3) return winDirection;
        return WinDirection.NoWin;
    }

    protected int getWinLineEnum(int positionI, int positionJ, Drawable.ConstantState player) {
        WinDirection winDirection = checkWinInCurrentPosition(positionI, positionJ, player);

        if (winDirection == WinDirection.NoWin) return 0;

        if (winDirection == WinDirection.WinCol) {
            if (positionJ == 0) return R.id.vertical_left_line;
            if (positionJ == 1) return R.id.vertical_middle_line;
            if (positionJ == 2) return R.id.vertical_right_line;
        } else if(winDirection == WinDirection.WinRow) {
            if (positionI == 0) return R.id.horizontal_top_line;
            if (positionI == 1) return R.id.horizontal_middle_line;
            if (positionI == 2) return R.id.horizontal_bottom_line;
        } else if (winDirection == WinDirection.WinDiag) {
            return R.id.diagnole_line;
        }
        return R.id.rdiagonal_line;
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

        findViewById(winLineImageNumber).setVisibility(View.INVISIBLE);
        winLineImageNumber = 0;

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
               && winLineImageNumber == 0) {

                if (XTurn) {
                    btn.setImageResource(R.drawable.x);
                } else {
                    btn.setImageResource(R.drawable.o);
                }
                movesCounter++;

                // Check if there is a winner
                winLineImageNumber = getWinLineEnum(i,j,btn.getDrawable().getConstantState());

                if (winLineImageNumber != 0){
                    findViewById(winLineImageNumber).setVisibility(View.VISIBLE);
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

