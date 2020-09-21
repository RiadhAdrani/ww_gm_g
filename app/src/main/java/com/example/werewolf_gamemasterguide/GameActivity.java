package com.example.werewolf_gamemasterguide;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements java.io.Serializable,View.OnClickListener {

    //    Turn guardian,wolf,fatherWolf,sorcerer,seer,barber,alien,captain,simpleVillager,servant,
    //            knight,blackWolf,bear,cupid,wildChild,redWolf,shepherd,pyromaniac,blueWolf;

    ArrayList<Turn> turnList = new ArrayList<Turn>();

    ArrayList<Role> gameList = new ArrayList<Role>();

    Button nextButton;  // allow user to switch to the next player

    Button useButton;   // allow user to use the power of the current player

    TextView turnCount; // text displaying the current turn count

    TextView role;      // text displaying the current role name

    TextView roleOwner; // text displaying the current role owner (real name or nickname ...)

    TextView roleInfo;  // text displaying the ability of the current role, or the info needed to execute his power;
                        // example : sorcerer need to know who died so it can revive
                        // example : werewolf does not need any info to make his decision.

    int r = -1;         // the INDEX of the current role

    int turn = 1;       // current role number

    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;

    ArrayList<Role> targets = new ArrayList<Role>();    // target of the current player

    int targetSize;     // number of target for the current player
                        // example: guardian have one target
                        //          sorcerer can have one or two targets
                        //          cupid have two targets

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
                if (r != -1) usePower();
                else displayShortToast(getString(R.string.start_new_turn_alert));
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.blackWAction: addTarget(ROLES.BLACK_WOLF); break;
            case R.id.redWAction: addTarget(ROLES.RED_WOLF);break;
            case R.id.blueWAction: addTarget(ROLES.BLUE_WOLF);break;
            case R.id.wWAction: addTarget(ROLES.WEREWOLF);break;
            case R.id.fWAction: addTarget(ROLES.FATHER_WOLF);break;

            case R.id.servantAction: addTarget(ROLES.SERVANT);break;
            case R.id.wildAction: addTarget(ROLES.WILD_CHILD);break;
            case R.id.cupidAction: addTarget(ROLES.CUPID);break;
            case R.id.guardAction: addTarget(ROLES.GUARDIAN);break;
            case R.id.sorcererAction: addTarget(ROLES.SORCERER);break;
            case R.id.seerAction: addTarget(ROLES.SEER);break;
            case R.id.sheepAction: addTarget(ROLES.SHEPHERD);break;
            case R.id.knightAction: addTarget(ROLES.KNIGHT);break;
            case R.id.barberAction: addTarget(ROLES.BARBER);break;
            case R.id.cptAction: addTarget(ROLES.CAPTAIN);break;
            case R.id.bearAction: addTarget(ROLES.BEAR);break;
            case R.id.vAction: addTarget(ROLES.VILLAGER);break;
            case R.id.pAction: addTarget(ROLES.PYROMANIAC);break;

            case R.id.aAction: addTarget(ROLES.ALIEN);break;
        }
    }

    void addTarget(ROLES role){

        // add the selected role to the target array

        Log.i("add","IT WORKS");
        if (findTarget(role) != -1){
            if (targetSize == targets.size()){
                targets.remove(0);
            }
            targets.add(gameList.get(findTarget(role)));
        } else{
            displayShortToast(getString(R.string.not_available));
        }
    }

    int findTarget(ROLES role){

        // return the element in __gameList__ with the __role__ role

        for (int j = 0; j < gameList.size();j++){
            if (gameList.get(j).role == role) return j;
        }
        return -1;
    }

    void usePower(){

        // display action screen

        dialogBuilder = new AlertDialog.Builder(this);

        final View popWindow = getLayoutInflater().inflate(R.layout.player_list_popup,null);

        TextView currentRole = popWindow.findViewById(R.id.RoleText);
        currentRole.setText(gameList.get(r).name);

        TextView currentInfo = popWindow.findViewById(R.id.powerText);
        currentInfo.setText(R.string.info2_placeholder);

        TextView currentTargets = popWindow.findViewById(R.id.targetsText);
        currentTargets.setText(R.string.no_target);

        Button blackButton = popWindow.findViewById(R.id.blackWAction);
        blackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarget(ROLES.BLACK_WOLF);
            }
        });

        dialogBuilder.setView(popWindow);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    public void displayShortToast(String message){

        // display short toast message

        Toast toast = Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_SHORT);
        toast.show();
    }

    void displayCurrentRole(){

        // display the current player information: role_name
        //                                         role_info

        r++;
        if (r > ( gameList.size() - 1) ) {
            String turnText = ""+turn;
            turnCount.setText(turnText);
            role.setText(R.string.discuss);
            roleOwner.setText(R.string.all_players);
            roleInfo.setText(R.string.all_players_discuss);
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
