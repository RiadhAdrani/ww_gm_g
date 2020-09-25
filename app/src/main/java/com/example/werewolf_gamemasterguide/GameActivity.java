package com.example.werewolf_gamemasterguide;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements java.io.Serializable {

    ArrayList<Role> fullList = new ArrayList<Role>();
    ArrayList<Role> gameList = new ArrayList<Role>();
    ArrayList<Role> targetList = new ArrayList<Role>();
    Button nextButton;
    Button useButton;
    TextView turnCount;
    TextView role;
    TextView roleOwner;
    TextView roleInfo;
    int r = -1;
    int turn = 1;
    int i ;

    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;

    View popWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        Bundle extras = getIntent().getExtras();

        gameList = (ArrayList<Role>) extras.getSerializable("gameList");
        fullList = (ArrayList<Role>) extras.getSerializable("fullList");


        // gameList.clear();
        popWindow = getLayoutInflater().inflate(R.layout.player_list_popup,null);

        for (Role x : fullList) {
            switch(x.role){
                case BLUE_WOLF: x.popCard = popWindow.findViewById(R.id.blueWCard);
                                x.popOwner = popWindow.findViewById(R.id.blueWOwner);
                                x.popButton = popWindow.findViewById(R.id.blueWAction);
                                break;
                case GUARDIAN:  x.popCard = popWindow.findViewById(R.id.guardCard);
                                x.popOwner = popWindow.findViewById(R.id.guardOwner);
                                x.popButton = popWindow.findViewById(R.id.guardAction);
                                break;
            }

            if (!x.isChecked){
                // x.popCard.removeAllViews();
            }
            else gameList.add(x);
        }

        turnCount = findViewById(R.id.turn_count);
        role = findViewById(R.id.role_name);
        roleOwner = findViewById(R.id.role_owner);
        roleInfo = findViewById(R.id.role_info);

        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayCurrentRole();
            }
        });

        useButton = (Button) findViewById(R.id.use_button);
        useButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usePower();
            }
        });
    }

    void usePower(){
        dialogBuilder = new AlertDialog.Builder(this);

        // hardcodedPopUpWindowInitialization(popWindow);

        dialogBuilder.setView(popWindow);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    public void displayShortToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_SHORT);
        toast.show();
    }

    void displayCurrentRole(){
        r++;
        if (r > ( gameList.size() - 1) ) {
            String turnText = ""+turn;
            turnCount.setText(turnText);
            role.setText("DISCUSSION");
            roleOwner.setText("ALL PLAYERS");
            roleInfo.setText("PLAYERS ARE INVITED TO DISCUSS WHAT HAPPENED THIS NIGHT AND ACCUSE SOMEONE EXPECTED TO BE A WOLF");
            r = -1;
            turn ++;
        }

        else{
            String turnText = ""+turn;
            turnCount.setText(turnText);
            role.setText(gameList.get(r).name);
            roleOwner.setText(gameList.get(r).owner);
            roleInfo.setText(gameList.get(r).desc);
        }
    }

    void hardcodedPopUpWindowInitialization(View popWindow){
        // Hardcoding every single role into the pop up window
        // displaying owner name
        // overriding every button

        // ---------------------------------------------------------------------------------------
        // Blue WOLF
        for (int i = 0; i < fullList.size(); i++){

            if (fullList.get(i).role == ROLES.BLUE_WOLF){
                gameList.get(gameList.indexOf(fullList.get(i))).popCard = popWindow.findViewById(R.id.blueWCard);
            }

            if (fullList.get(i).isChecked){
                gameList.get(gameList.indexOf(fullList.get(i))).list = targetList;
                gameList.get(gameList.indexOf(fullList.get(i))).popOwner = popWindow.findViewById(R.id.blueWOwner);
                gameList.get(gameList.indexOf(fullList.get(i))).popOwner.setText(gameList.get(gameList.indexOf(fullList.get(i))).owner);
                gameList.get(gameList.indexOf(fullList.get(i))).popButton = popWindow.findViewById(R.id.blueWAction);

            }else gameList.get(gameList.indexOf(fullList.get(i))).popCard.removeAllViews();
        }
    }

}
