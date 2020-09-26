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
    int targetListMaxSize = 1;

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
                if (r == -1){
                    displayShortToast("CLICK NEXT TO START A NEW TURN !");
                } else
                usePower();
            }
        });
    }

    void usePower(){
        dialogBuilder = new AlertDialog.Builder(this);

        popWindow = getLayoutInflater().inflate(R.layout.player_list_popup,null);

        TextView temp = popWindow.findViewById(R.id.target_list);
        temp.setText(R.string.empty);

        popUpInitialize();

        dialogBuilder.setView(popWindow);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    View returnView(int id){
        return popWindow.findViewById(id);
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

    void autoInitialize(ROLES role,LinearLayout card, TextView ownerText, Button button){
        int f = -1;

        for (int i = 0; i < gameList.size(); i++){
            if (gameList.get(i).role == role) {
                f = i;
                break;}
        }

        if (f != -1){
            gameList.get(f).popCard = card;

            gameList.get(f).listText = popWindow.findViewById(R.id.target_list);
            gameList.get(f).temp = getString(R.string.empty);

            gameList.get(f).popOwner = ownerText;
            gameList.get(f).popOwner.setText(gameList.get(f).owner);

            gameList.get(f).listMaxSize = targetListMaxSize;
            gameList.get(f).list = targetList;
            gameList.get(f).popButton = button;
            gameList.get(f).ActionButton();
        } else {
            LinearLayout x = (LinearLayout) popWindow.findViewById(card.getId());
            x.removeAllViewsInLayout();
            x.setVisibility(View.GONE);
        }
    }

    void popUpInitialize(){
        autoInitialize(ROLES.BLUE_WOLF,(LinearLayout) returnView(R.id.blueWCard),
                (TextView) returnView(R.id.blueWOwner),
                (Button) returnView(R.id.blueWAction));

        autoInitialize(ROLES.RED_WOLF,(LinearLayout) returnView(R.id.redWCard),
                (TextView) returnView(R.id.redWOwner),
                (Button) returnView(R.id.redWAction));

        autoInitialize(ROLES.BLACK_WOLF,(LinearLayout) returnView(R.id.blackWCard),
                (TextView) returnView(R.id.blackWOwner),
                (Button) returnView(R.id.blackWAction));

        autoInitialize(ROLES.SERVANT,(LinearLayout) returnView(R.id.servantCard),
                (TextView) returnView(R.id.servantOwner),
                (Button) returnView(R.id.servantAction));

        autoInitialize(ROLES.CUPID,(LinearLayout) returnView(R.id.cupidCard),
                (TextView) returnView(R.id.cupidOwner),
                (Button) returnView(R.id.cupidAction));

        autoInitialize(ROLES.PYROMANIAC,(LinearLayout) returnView(R.id.pCard),
                (TextView) returnView(R.id.pOwner),
                (Button) returnView(R.id.pAction));

        autoInitialize(ROLES.WILD_CHILD,(LinearLayout) returnView(R.id.wildCard),
                (TextView) returnView(R.id.wildOwner),
                (Button) returnView(R.id.wildAction));

        autoInitialize(ROLES.GUARDIAN,(LinearLayout) returnView(R.id.guardCard),
                (TextView) returnView(R.id.guardOwner),
                (Button) returnView(R.id.guardAction));

        autoInitialize(ROLES.WEREWOLF,(LinearLayout) returnView(R.id.wWCard),
                (TextView) returnView(R.id.wWOwner),
                (Button) returnView(R.id.wWAction));

        autoInitialize(ROLES.FATHER_WOLF,(LinearLayout) returnView(R.id.fWCard),
                (TextView) returnView(R.id.fWOwner),
                (Button) returnView(R.id.fWAction));

        autoInitialize(ROLES.SORCERER,(LinearLayout) returnView(R.id.sorcererCard),
                (TextView) returnView(R.id.sorcererOwner),
                (Button) returnView(R.id.sorcererAction));

        autoInitialize(ROLES.SEER,(LinearLayout) returnView(R.id.seerCard),
                (TextView) returnView(R.id.seerOwner),
                (Button) returnView(R.id.seerAction));

        autoInitialize(ROLES.SHEPHERD,(LinearLayout) returnView(R.id.sheepCard),
                (TextView) returnView(R.id.sheepOwner),
                (Button) returnView(R.id.sheepAction));

        autoInitialize(ROLES.BARBER,(LinearLayout) returnView(R.id.barberCard),
                (TextView) returnView(R.id.barberOwner),
                (Button) returnView(R.id.barberAction));

        autoInitialize(ROLES.ALIEN,(LinearLayout) returnView(R.id.aCard),
                (TextView) returnView(R.id.aOwner),
                (Button) returnView(R.id.aAction));

        autoInitialize(ROLES.KNIGHT,(LinearLayout) returnView(R.id.knightCard),
                (TextView) returnView(R.id.knightOwner),
                (Button) returnView(R.id.knightAction));

        autoInitialize(ROLES.CAPTAIN,(LinearLayout) returnView(R.id.cptCard),
                (TextView) returnView(R.id.cptOwner),
                (Button) returnView(R.id.cptAction));

        autoInitialize(ROLES.VILLAGER,(LinearLayout) returnView(R.id.vCard),
                (TextView) returnView(R.id.vOwner),
                (Button) returnView(R.id.vAction));

        autoInitialize(ROLES.BEAR,(LinearLayout) returnView(R.id.bearCard),
                (TextView) returnView(R.id.bearOwner),
                (Button) returnView(R.id.bearAction));
    }

}
