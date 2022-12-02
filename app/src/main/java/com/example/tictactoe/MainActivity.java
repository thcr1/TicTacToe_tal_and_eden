package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean XTurn = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView[] buttons = new ImageView[9];

        for(int i=0; i<9; i++) {
            String buttonID = "tiktaktoe_btn" + (i + 1);
            System.out.println(buttonID);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = ((ImageView) findViewById(resID));
            setOnClick(buttons[i], i);
        }
    }
    private void setOnClick(final ImageView btn, int cellInx){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView playerTurn = (ImageView) findViewById(R.id.tiktaktoe_text);
                System.out.println("pressed button: " + cellInx);
                if(true) {
                    if (XTurn) {
                        btn.setImageResource(R.drawable.x);
                        playerTurn.setImageResource(R.drawable.oplay);
                    } else {
                        btn.setImageResource(R.drawable.o);
                        playerTurn.setImageResource(R.drawable.xplay);
                    }
                }
                XTurn = !XTurn;



                // Do whatever you want(str can be used here)

            }
        });
    }
}
