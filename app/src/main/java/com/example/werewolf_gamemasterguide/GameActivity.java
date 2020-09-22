package com.example.werewolf_gamemasterguide;

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

    Turn guardian,wolf,fatherWolf,sorcerer,seer,barber,alien,captain,simpleVillager,servant,
            knight,blackWolf,bear,cupid,wildChild,redWolf,shepherd,pyromaniac,blueWolf;

    ArrayList<Turn> turnList = new ArrayList<Turn>();
    ArrayList<Role> gameList = new ArrayList<Role>();
    Button nextButton;
    Button useButton;
    TextView turnCount;
    TextView role;
    TextView roleOwner;
    TextView roleInfo;
    int r = -1;
    int turn = 1;
    int i = 0;

    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            gameList = (ArrayList<Role>) extras.getSerializable("list");
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

        final View popWindow = getLayoutInflater().inflate(R.layout.player_list_popup,null);

        // LinearLayout root = popWindow.findViewById(R.id.root);

        // View x = getLayoutInflater().inflate(R.layout.element_popup,root);

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

}
