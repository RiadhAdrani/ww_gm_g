package com.example.werewolf_gamemasterguide;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
    int j = 0;

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

        // Log.i("DEBUGGING",""+findTarget(role));
        if (findTarget(role) != -1){
            if (targetSize == targets.size()){
                targets.remove(0);
            }
            // Log.i("DEBUGGING","IT WORKS");
            targets.add(gameList.get(findTarget(role)));
        } else{
            displayShortToast(getString(R.string.not_available));
        }
    }

    String setText(int index){
        if (index != -1){
            return gameList.get(index).owner;
        }
        return getString(R.string.owner_placeholder);
    }

    int findTarget(ROLES role){

        // return the element in __gameList__ with the __role__ role

        j = 0;
        if (gameList.get(j).role == role) return j;
        j++;
        Boolean found = false;

//        for (j = 0; j < gameList.size();j++){
//            if (gameList.get(j).role == role) {
//                found = true;
//                Log.i("DEBUGGING",""+getString(gameList.get(j).name));
//                break;
//            }
//        }

        while (j<gameList.size() && !found){
            if (gameList.get(j).role == role) found = true;
            else j++;
        }

        Log.i("DEBUGGING",""+j);
        if (found) return j;
        else return -1;
    }

    void usePower(){

        // display action screen

        dialogBuilder = new AlertDialog.Builder(this);

        final View popWindow = getLayoutInflater().inflate(R.layout.player_list_popup,null);

        // DISPLAY CURRENT ROLE
        TextView currentRole = popWindow.findViewById(R.id.RoleText);
        currentRole.setText(gameList.get(r).name);

        // DISPLAY CURRENT ROLE INFO
        TextView currentInfo = popWindow.findViewById(R.id.powerText);
        currentInfo.setText(R.string.info2_placeholder);

        // DISPLAY TARGETED PLAYERS
        TextView currentTargets = popWindow.findViewById(R.id.targetsText);
        currentTargets.setText(R.string.no_target);

        // TARGET NAME & BUTTON CONFIGURATION
        // ADD BELOW

        // ---------------------------------------------------------------------------------------
        // BLACK WEREWOLF
        TextView blackOwner = popWindow.findViewById(R.id.blackWOwner);
        blackOwner.setText(setText(findTarget(ROLES.BLACK_WOLF)));
        Button blackButton = popWindow.findViewById(R.id.blackWAction);
        blackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarget(ROLES.BLACK_WOLF);
            }
        });

        // ---------------------------------------------------------------------------------------
        // RED WEREWOLF
        TextView redOwner = popWindow.findViewById(R.id.redWOwner);
        redOwner.setText(setText(findTarget(ROLES.RED_WOLF)));
        Button redButton = popWindow.findViewById(R.id.redWAction);
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarget(ROLES.RED_WOLF);
            }
        });

        // ---------------------------------------------------------------------------------------
        // BLUE WEREWOLF
        TextView blueOwner = popWindow.findViewById(R.id.blueWOwner);
        blueOwner.setText(setText(findTarget(ROLES.BLUE_WOLF)));
        Button blueButton = popWindow.findViewById(R.id.blueWAction);
        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarget(ROLES.BLUE_WOLF);
            }
        });

        // ---------------------------------------------------------------------------------------
        // WEREWOLF
        TextView wolfOwner = popWindow.findViewById(R.id.wWOwner);
        wolfOwner.setText(setText(findTarget(ROLES.WEREWOLF)));
        Button wolfButton = popWindow.findViewById(R.id.wWAction);
        wolfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarget(ROLES.WEREWOLF);
            }
        });

        // ---------------------------------------------------------------------------------------
        // FATHER WOLF
        TextView fatherOwner = popWindow.findViewById(R.id.fWOwner);
        fatherOwner.setText(setText(findTarget(ROLES.FATHER_WOLF)));
        Button fatherButton = popWindow.findViewById(R.id.fWAction);
        fatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { addTarget(ROLES.FATHER_WOLF);
            }
        });

        // ---------------------------------------------------------------------------------------
        // FATHER WOLF
        TextView servantOwner = popWindow.findViewById(R.id.servantOwner);
        servantOwner.setText(setText(findTarget(ROLES.SERVANT)));
        Button servantButton = popWindow.findViewById(R.id.servantAction);
        servantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarget(ROLES.SERVANT);
            }
        });

        // ---------------------------------------------------------------------------------------
        // WILD CHILD
        TextView wildOwner = popWindow.findViewById(R.id.wildOwner);
        wildOwner.setText(setText(findTarget(ROLES.WILD_CHILD)));
        Button wildButton = popWindow.findViewById(R.id.wildAction);
        wildButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarget(ROLES.WILD_CHILD);
            }
        });

        // ---------------------------------------------------------------------------------------
        // CUPID
        TextView cupidOwner = popWindow.findViewById(R.id.cupidOwner);
        cupidOwner.setText(setText(findTarget(ROLES.CUPID)));
        Button cupidButton = popWindow.findViewById(R.id.cupidAction);
        cupidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarget(ROLES.CUPID);
            }
        });

        // ---------------------------------------------------------------------------------------
        // GUARDIAN
        TextView guardOwner = popWindow.findViewById(R.id.guardOwner);
        guardOwner.setText(setText(findTarget(ROLES.GUARDIAN)));
        Button guardButton = popWindow.findViewById(R.id.guardAction);
        guardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarget(ROLES.GUARDIAN);
            }
        });

        // ---------------------------------------------------------------------------------------
        // SORCERER
        TextView sorcererOwner = popWindow.findViewById(R.id.sorcererOwner);
        sorcererOwner.setText(setText(findTarget(ROLES.SORCERER)));
        Button sorcererButton = popWindow.findViewById(R.id.sorcererAction);
        sorcererButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarget(ROLES.SORCERER);
            }
        });

        // ---------------------------------------------------------------------------------------
        // SEER
        TextView seerOwner = popWindow.findViewById(R.id.seerOwner);
        seerOwner.setText(setText(findTarget(ROLES.SEER)));
        Button seerButton = popWindow.findViewById(R.id.seerAction);
        seerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarget(ROLES.SEER);
            }
        });

        // ---------------------------------------------------------------------------------------
        // SHEPHERD
        TextView sheepOwner = popWindow.findViewById(R.id.sheepOwner);
        sheepOwner.setText(setText(findTarget(ROLES.SHEPHERD)));
        Button sheepButton = popWindow.findViewById(R.id.sheepAction);
        sheepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarget(ROLES.SHEPHERD);
            }
        });

        // ---------------------------------------------------------------------------------------
        // KNIGHT
        TextView knightOwner = popWindow.findViewById(R.id.knightOwner);
        knightOwner.setText(setText(findTarget(ROLES.KNIGHT)));
        Button knightButton = popWindow.findViewById(R.id.knightAction);
        knightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarget(ROLES.KNIGHT);
            }
        });

        // ---------------------------------------------------------------------------------------
        // BARBER
        TextView barberOwner = popWindow.findViewById(R.id.barberOwner);
        barberOwner.setText(setText(findTarget(ROLES.BARBER)));
        Button barberButton = popWindow.findViewById(R.id.barberAction);
        barberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarget(ROLES.BARBER);
            }
        });

        // ---------------------------------------------------------------------------------------
        // CAPTAIN
        TextView cptOwner = popWindow.findViewById(R.id.cptOwner);
        cptOwner.setText(setText(findTarget(ROLES.CAPTAIN)));
        Button cptButton = popWindow.findViewById(R.id.cptAction);
        cptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarget(ROLES.CAPTAIN);
            }
        });

        // ---------------------------------------------------------------------------------------
        // BEAR
        TextView bearOwner = popWindow.findViewById(R.id.bearOwner);
        bearOwner.setText(setText(findTarget(ROLES.BEAR)));
        Button bearButton = popWindow.findViewById(R.id.bearAction);
        bearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarget(ROLES.BEAR);
            }
        });

        // ---------------------------------------------------------------------------------------
        // BEAR
        TextView villagerOwner = popWindow.findViewById(R.id.vOwner);
        villagerOwner.setText(setText(findTarget(ROLES.VILLAGER)));
        Button villagerButton = popWindow.findViewById(R.id.vAction);
        villagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarget(ROLES.VILLAGER);
            }
        });

        // ---------------------------------------------------------------------------------------
        // BEAR
        TextView pyroOwner = popWindow.findViewById(R.id.pOwner);
        pyroOwner.setText(setText(findTarget(ROLES.PYROMANIAC)));
        Button pyroButton = popWindow.findViewById(R.id.pAction);
        pyroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarget(ROLES.PYROMANIAC);
            }
        });

        // ---------------------------------------------------------------------------------------
        // ALIEN
        TextView alienOwner = popWindow.findViewById(R.id.aOwner);
        alienOwner.setText(setText(findTarget(ROLES.ALIEN)));
        Button alienButton = popWindow.findViewById(R.id.aAction);
        alienButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarget(ROLES.ALIEN);
            }
        });

        // ---------------------------------------------------------------------------------------
        // ADD NEW ROLE HERE RESPECTING THE SAME TEMPLATE
        // TextView xOwner = popWindow.findViewById(R.id.xOwnerId);
        // xOwner.setText(setText(findTarget(ROLES.NEW_ROLE)));
        // Button xButton = popWindow.findViewById(R.id.xActionButton);
        // xButton.setOnClickListener(new View.setOnClickListener ...);

        // END
        // ---------------------------------------------------------------------------------------

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
