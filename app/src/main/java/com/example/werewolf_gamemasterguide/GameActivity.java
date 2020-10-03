package com.example.werewolf_gamemasterguide;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

    int fatherPL = 1;
    int barberPL = 1;
    int knightPL = 1;
    int sorcererHeal = 1;
    int sorcererKill = 1;
    int sheepL = 2;

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
                }
                else if (gameList.get(r).isBlocked) {
                    displayShortToast("!!! BLOCKED !!!");
                }
                else if (targetListMaxSize == 0){
                    displayShortToast("!!! NO ABILITY !!!");
                }
                else
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

        targetList.clear();

        final Button confirm = popWindow.findViewById(R.id.confirm_action);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmAction();
            }
        });

        if (gameList.get(r).isBlued) displayShortToast("THIS ONE IS BLUED");

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
            popUpRoleListTargetFilter(f);

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

    void displayCurrentRole(){
        if (r == -1){
            for (Role x : gameList) {
                x.resolveAttribute();
                temporaryCaptain(x);
            }
        }

        r++;
        targetListMaxSize = 0;
        targetList.clear();

        if (r >= ( gameList.size())) {
            autoResolve();
            String turnText = ""+turn;
            turnCount.setText(turnText);
            role.setText("DISCUSSION");
            roleOwner.setText("ALL PLAYERS");
            roleInfo.setText("PLAYERS ARE INVITED TO DISCUSS WHAT HAPPENED THIS NIGHT AND ACCUSE SOMEONE EXPECTED TO BE A WOLF");
            r = -1;
            Log.d("STATUS_EFFECT", "TURN No : "+turn+ " -----------------------------");
            debugging();
            turn ++;
        }



        else{
            String turnText = ""+turn;
            turnCount.setText(turnText);
            role.setText(gameList.get(r).name);
            roleOwner.setText(gameList.get(r).owner);
            roleInfo.setText(gameList.get(r).desc);

            gameList.get(r).refreshAbility();
            targetListMaxSize = gameList.get(r).returnTarget();
            // targetSize();
        }

        if (r != -1) {
            checkSkip();
            }
    }

    void deleteRoleView(int f){
        LinearLayout x = (LinearLayout) gameList.get(f).popCard;
        x.removeAllViewsInLayout();
        x.setVisibility(View.GONE);
    }

    int findRole(ROLES role){
        for (Role x : gameList) {
            if (x.role == role) return gameList.indexOf(x);
        }
        return -1;
    }

    void debugging(){
        for (Role x : gameList) {
            if (x.isBlued) Log.d("STATUS_EFFECT", ""+getString(x.name)+" isBlued");
            if (x.isServed) Log.d("STATUS_EFFECT", ""+getString(x.name)+" isServed");
            if (x.isPreviouslyGuarded) Log.d("STATUS_EFFECT", ""+getString(x.name)+" isGuarded");
            if (x.isBlocked) Log.d("STATUS_EFFECT", ""+getString(x.name)+" isBlocked");
            if (x.isHealed) Log.d("STATUS_EFFECT", ""+getString(x.name)+" isHealed");
            if (x.isKilled) Log.d("STATUS_EFFECT", ""+getString(x.name)+" isKilled");
            if (x.isMuted) Log.d("STATUS_EFFECT", ""+getString(x.name)+" isMuted");
            if (x.isOnFire) Log.d("STATUS_EFFECT", ""+getString(x.name)+" isOnFire");
            if (x.isChilded) Log.d("STATUS_EFFECT", ""+getString(x.name)+" isChilded");
            if (x.isInfected) Log.d("STATUS_EFFECT", ""+getString(x.name)+" isInfected");
            if (x.isSeen) Log.d("STATUS_EFFECT", ""+getString(x.name)+" isSeen");
            if (x.isSheep) Log.d("STATUS_EFFECT", ""+getString(x.name)+" isSheep");
            if (x.isKnighted) Log.d("STATUS_EFFECT", ""+getString(x.name)+" isKnighted");
            if (x.isCaptain) Log.d("STATUS_EFFECT", ""+getString(x.name)+" isCaptain");
            if (x.isTalkingFirst) Log.d("STATUS_EFFECT", ""+getString(x.name)+" isTalkingFirst");
            if (x.isNearBear) Log.d("STATUS_EFFECT", ""+getString(x.name)+" isNearBear");
            if (x.isLover) Log.d("STATUS_EFFECT", ""+getString(x.name)+" isLover");
            if (x.isSorcererEd) Log.d("STATUS_EFFECT", ""+getString(x.name)+" isSorcererEd");
            if (x.isHavingACut) Log.d("STATUS_EFFECT", ""+getString(x.name)+" isHavingACut");
            if (!x.isAlive) Log.d("STATUS_EFFECT", ""+getString(x.name)+" Died");
        }
    }

    void autoResolve(){
        for (Role x : gameList) {
            lovePrice(gameList.indexOf(x));
            if (x.isKilled || x.isHavingACut || x.isKnighted || x.isSorcererEd) x.isAlive = false;
        }
    }

    // --------------------------------------------------------------------------------------------
    // SPECIFICATIONS

    void checkSkip(){
        if ( (gameList.get(r).isSkippable = gameList.get(r).returnSkip(turn)) ) displayCurrentRole();
    }

    void popUpRoleListTargetFilter(int f){
        // Filter Which role could be targeted according to the current player
        // Filters should be added here
        // this function is executed within the autoInitialize() function of each role
        // no order is needed

        switch (gameList.get(r).role){
            case BLUE_WOLF:
            case RED_WOLF: if (gameList.get(f).team == TEAM.WOLVES) deleteRoleView(f); break;
            case SERVANT: if (gameList.get(f).role == ROLES.SERVANT) deleteRoleView(f); break;
            // case CUPID: break;
            // case PYROMANIAC: break;
            case WILD_CHILD: if (gameList.get(f).role == ROLES.WILD_CHILD) deleteRoleView(f); break;
            case GUARDIAN: if (gameList.get(f).isPreviouslyGuarded) deleteRoleView(f); break;
            // case BLACK_WOLF: break;
            // case WEREWOLF: break;
            case FATHER_WOLF: if (!gameList.get(f).isKilled) deleteRoleView(f); break;
            // case SORCERER: break;
            // case SEER:break;
            // case SHEPHERD: break;
            case BARBER: if (gameList.get(r).isSorcererEd || gameList.get(r).isKilled || gameList.get(r).isKnighted) {
                if(gameList.get(f).role == ROLES.BARBER) deleteRoleView(f);
                    } else deleteRoleView(f); break;
            case ALIEN: if (r == -1) {
                if (gameList.get(f).role == gameList.get(r).role) deleteRoleView(f);}
                else deleteRoleView(f); break;
            case KNIGHT: if (gameList.get(f).role == ROLES.KNIGHT) deleteRoleView(f); break;
            // case CAPTAIN: break;
            // case VILLAGER: break;
            // case BEAR: break;
        }

    }

    void targetSize(){
        // Filter Which role could be targeted according to the current player
        // Filters should be added here
        // this function is executed within the autoInitialize() function of each role
        // no order is needed

        switch (gameList.get(r).role){
            case BLUE_WOLF:
            case VILLAGER:
            case RED_WOLF:
            case SERVANT:
            case PYROMANIAC:
            case WILD_CHILD:
            case GUARDIAN:
            case BLACK_WOLF:
            case WEREWOLF:
            case SEER:
            case CAPTAIN: targetListMaxSize = 1; if (gameList.get(r).isBlued) targetListMaxSize ++ ; break;

            case KNIGHT: targetListMaxSize = knightPL; if (gameList.get(r).isBlued) targetListMaxSize ++; break;

            case BARBER: targetListMaxSize = barberPL; if (gameList.get(r).isBlued) targetListMaxSize ++; break;

            case FATHER_WOLF: targetListMaxSize = fatherPL; break;

            case CUPID:
            case BEAR: targetListMaxSize = 2; if (gameList.get(r).isBlued) targetListMaxSize += 2; break;

            case SORCERER: targetListMaxSize = sorcererHeal + sorcererKill; if (gameList.get(r).isBlued) targetListMaxSize += 2 ; break;
            case SHEPHERD: targetListMaxSize = sheepL ; if (gameList.get(r).isBlued) targetListMaxSize += 2; break;

            case ALIEN: targetListMaxSize = gameList.size();
        }

    }

    void confirmAction(){
        if (!targetList.isEmpty()) {
            gameList.get(r).useAbility(gameList);
            if (gameList.get(r).isBlocked && gameList.get(r).isCaptain){
                dialog.dismiss();
                usePower();
                displayShortToast("PICK A NEW CAPTAIN !");
            }
            else {
                dialog.dismiss();
                displayCurrentRole();
            }
        } else{
            displayShortToast("NO TARGET");
            dialog.dismiss();
            displayCurrentRole();
        }
    }

    void temporaryCaptain(Role role){
        if (gameList.get(findRole(ROLES.CAPTAIN)).isAlive && gameList.get(gameList.indexOf(role)).isCaptain){
            gameList.get(gameList.indexOf(role)).isCaptain = false;
        }
    }

    void powerText(){
        TextView text = popWindow.findViewById(R.id.powerText);
        String msg = "";
        switch (gameList.get(r).role){
            case BLUE_WOLF: msg = getString(R.string.blueWolfMsg); break;
            case RED_WOLF: msg = getString(R.string.redWolfMsg);break;
            case VILLAGER: msg = getString(R.string.simpleVillagerMsg);break;
            case BEAR: msg = getString(R.string.bearPower);break;
            case SEER: msg = getString(R.string.seerMsg);break;
            case ALIEN: msg = getString(R.string.alienMsg);break;
            case CUPID: msg = getString(R.string.cupidMsg);break;
            case BARBER: if (gameList.get(r).isKilled || gameList.get(r).isKnighted || gameList.get(r).isSorcererEd) msg = getString(R.string.barberKilledMsg);
                         else msg = getString(R.string.barberMsg);break;
            case KNIGHT: if (gameList.get(r).isKilled || gameList.get(r).isHavingACut || gameList.get(r).isSorcererEd) msg = getString(R.string.knightKillMsg);
                         else msg = getString(R.string.knightPeaceMsg);break;
            case CAPTAIN: if (gameList.get(r).isKilled || gameList.get(r).isKnighted || gameList.get(r).isSorcererEd || gameList.get(r).isHavingACut) msg = getString(R.string.captainDeadMsg);
                          else msg = getString(R.string.captainMsg);break;
            case SERVANT:
        }
    }

    int findOtherLover(int currentLover){
        for (Role x : gameList) {
            if (gameList.indexOf(x) != currentLover && x.isLover) return gameList.indexOf(x);
        }
        return -1;
    }

    void lovePrice(int r){
        if (gameList.get(r).isLover){
            if (gameList.get(r).isKilled) {
                gameList.get(findOtherLover(r)).isKilled = true;
                gameList.get(findOtherLover(r)).isLover = false;
                gameList.get(r).isLover = false; }

            if (gameList.get(r).isSorcererEd) {
                gameList.get(findOtherLover(r)).isKilled = true;
                gameList.get(findOtherLover(r)).isLover = false;
                gameList.get(r).isLover = false; }

            if (gameList.get(r).isKnighted) {
                gameList.get(findOtherLover(r)).isKilled = true;
                gameList.get(findOtherLover(r)).isLover = false;
                gameList.get(r).isLover = false; }

            if (gameList.get(r).isHavingACut) {
                gameList.get(findOtherLover(r)).isKilled = true;
                gameList.get(findOtherLover(r)).isLover = false;
                gameList.get(r).isLover = false; }
        }
    }












    // --------------------------------------------------------------------------------------------
    // FUNCTION GRAVE
    // UNUSED FUNCTIONS
    // SOME HARDCODED MESS HERE
    // YOU GOTTA DIG DOWN FOR A MOMENT ...

    void confirmActionHC(){
        switch (gameList.get(r).role){
            case BLUE_WOLF: bluePower(); break;
            case RED_WOLF: redPower(); break;
            case BLACK_WOLF: blackPower(); break;
            case SERVANT: servantPower(); break;
            case CUPID: cupidPower(); break;
            case PYROMANIAC: pyromaniacPower(); break;
            case WILD_CHILD: wildPower(); break;
            case GUARDIAN: guardianPower(); break;
            case WEREWOLF: wolfPackPower(); break;
            case FATHER_WOLF: fatherPower(); break;
            case SORCERER: sorcererPower(); break;
            case SEER: seerPower(); break;
            case SHEPHERD: sheepPower();break;
            case BARBER: barberKilledPower(); break;
            case ALIEN: break;
            case KNIGHT: knightKilledEffect();
            case CAPTAIN: captainPower(); break;
            case BEAR: bearPower(); break;
        }
    }

    void nextRoleSpecificationCheck(){
        // Include specific rules for certain roles
        // Keep in mind that this specification checks will be called just after switching to a role
        // and displaying his data in the screen.
        // Specification and rules could be added here
        // no order is needed

        // BLUE WOLF
        // Can use his power once every three turns
        if (gameList.get(r).role == ROLES.BLUE_WOLF && turn % 3 != 0){
            displayCurrentRole();
        }

        // RED WOLF
        // Use his power to block an enemy

        // SERVANT
        // In the first turn of the game, the servant choose a target to inherit his power after
        // his death
        if (gameList.get(r).role == ROLES.SERVANT && turn != 1){
            displayCurrentRole();
        }

        // CUPID
        // In the first turn of the game, he bind two with the power of love
        // if one dies, the other dies too
        if (gameList.get(r).role == ROLES.CUPID && turn != 1){
            displayCurrentRole();
        }

        //PYROMANIAC
        // In the first turn of the game, the pyromaniac choose a target,
        // if he is attacked, he do not die,
        // instead, the first wolf on his (the target) right side dies
        // the pyromaniac become a simple villager
        if (gameList.get(r).role == ROLES.PYROMANIAC && turn % 2 == 0){
            displayCurrentRole();
        }

        // WILD CHILD
        // In the first turn of the game, the wild child choose a target,
        // if dead, he join the wolf pack
        if (gameList.get(r).role == ROLES.WILD_CHILD && turn != 1){
            displayCurrentRole();
        }

        // GUARDIAN
        // Every turn, he protect someone from the wolves
        // Can't protect the same person twice in a row

        // FATHER OF WOLF
        // Starting from the second turn, the father of wolf can infect someone
        // He can use this power once
        if (gameList.get(r).role == ROLES.FATHER_WOLF && turn == 1){
            displayCurrentRole();
        }

        // SORCERER
        // Has two potions, to revive or to Kill

        // SEER
        // Has the ability to see the true roles

        // SHEPHERD
        // send his two sheep to two players.
        // if a sheep is sent to a wolf, he dies

        // BARBER
        // Has an active power of killing a player
        // if the target is a wolf, he is dead and the barber became a simple villager
        // if the target is not a wolf, both the target and the barber die.
        if (gameList.get(r).role == ROLES.BARBER && turn != 1 && !gameList.get(r).isKilled && !gameList.get(r).isSorcererEd && !gameList.get(r).isKnighted) {
            displayCurrentRole();
        }

        // ALIEN
        // His objective is to guess the role of each player to win the game
        // if he guess wrong, he dies
        if (gameList.get(r).role == ROLES.ALIEN && turn != 1){
            displayCurrentRole();
        }

        // KNIGHT
        // If he is being targeted by someone, he can use another player as a human shield,
        // the chosen player obviously dies and the knight become a simple villager
        if (gameList.get(r).role == ROLES.KNIGHT && turn != 1 && !gameList.get(r).isKilled && !gameList.get(r).isSorcererEd && !gameList.get(r).isHavingACut) {
            displayCurrentRole();
        }

        // CAPTAIN
        // Chooses who is going to start the discussion after each turn
        // Can decide which player will die if the votes for some players are even.

        // SIMPLE VILLAGER
        // No power
        if (gameList.get(r).role == ROLES.VILLAGER){
            displayCurrentRole();
        }

        // BEAR
        // Make a sound if there is a wolf (not an infected) by his side
        // Make a sound if he is infected

    }

    void blueEffect(){
        if (gameList.get(r).isBlued){
            displayShortToast(getString(R.string.blueWolfAlert)+" : "+getString(gameList.get(r).name));
            targetListMaxSize *= 2;
        }
    }

    void bluePower(){
        if (!targetList.isEmpty()) {
            for (Role x : targetList) {
                gameList.get(gameList.indexOf(x)).isBlued = true;
            }
        }
    }

    void redPower(){
        if (!targetList.isEmpty()) {
            for (Role x : targetList) {
                gameList.get(gameList.indexOf(x)).isBlocked = true;
            }
        }
    }

    void blackPower(){
        if (!targetList.isEmpty()) {
            for (Role x : targetList) {
                if (!gameList.get(gameList.indexOf(x)).isGuarded) gameList.get(gameList.indexOf(x)).isMuted = true;
            }
        }
    }

    void servantPower(){
        if (!targetList.isEmpty() && !gameList.get(r).isBlocked) {
            for (Role x : targetList) {
                gameList.get(gameList.indexOf(x)).isServed = true;
            }
        }
    }

    void cupidPower(){
        if (!targetList.isEmpty() && !gameList.get(r).isBlocked) {
            for (Role x : targetList) {
                gameList.get(gameList.indexOf(x)).isLover = true;
            }
        }
    }

    void pyromaniacPower(){
        if (!targetList.isEmpty() && !gameList.get(r).isBlocked) {
            for (Role x : targetList) {
                gameList.get(gameList.indexOf(x)).isOnFire = true;
            }
        }
    }

    void wildPower(){
        if (!targetList.isEmpty() && !gameList.get(r).isBlocked) {
            for (Role x : targetList) {
                gameList.get(gameList.indexOf(x)).isChilded = true;
            }
        }
    }

    void guardianPower(){
        if (!targetList.isEmpty() && !gameList.get(r).isBlocked) {
            for (Role x : targetList) {
                gameList.get(gameList.indexOf(x)).isGuarded = true;
            }
        }
    }

    void wolfPackPower(){
        if (!targetList.isEmpty()) {
            for (Role x : targetList) {
                if (!gameList.get(gameList.indexOf(x)).isGuarded) gameList.get(gameList.indexOf(x)).isKilled = true;
            }
        }
    }

    void fatherPower(){
        if (!targetList.isEmpty()) {
            for (Role x : targetList) {
                if (!gameList.get(gameList.indexOf(x)).isGuarded) {
                    gameList.get(gameList.indexOf(x)).isInfected = true;
                    gameList.get(gameList.indexOf(x)).isKilled = false;
                    fatherPL = 0;}
            }
        }
    }

    void sorcererPower(){
        if (targetList.size() != 0 && !gameList.get(r).isBlocked) {
            for (Role role1 : targetList) {
                if (!role1.isKilled) {
                    if (sorcererKill == 1){
                        role1.isSorcererEd = true;
                        sorcererKill = 0;
                    }
                } else{
                    if (sorcererHeal == 1){
                        role1.isHealed = true;
                        role1.isKilled = false;
                        sorcererHeal = 0;
                    }
                }
            }
        }
    }

    void seerPower(){
        if (!targetList.isEmpty() && !gameList.get(r).isBlocked) {
            for (Role x : targetList) {
                gameList.get(gameList.indexOf(x)).isSeen = true;
            }
        }
    }

    void sheepPower(){
        if (!targetList.isEmpty() && !gameList.get(r).isBlocked) {
            for (Role x : targetList) {
                gameList.get(gameList.indexOf(x)).isSheep = true;
            }
        }
    }

    void barberKilledPower(){
        if (!targetList.isEmpty() && !gameList.get(r).isBlocked) {
            for (Role x : targetList) {
                if (!gameList.get(r).isBlocked) gameList.get(gameList.indexOf(x)).isHavingACut = true;
            }
        }
    }

    void knightKilledEffect(){
        if (!targetList.isEmpty() && !gameList.get(r).isBlocked) {
            for (Role x : targetList) {
                gameList.get(gameList.indexOf(x)).isKnighted = true;
            }
        }
    }

    void captainPower(){
        if (!targetList.isEmpty() && !gameList.get(r).isBlocked) {
            for (Role x : targetList) {
                gameList.get(gameList.indexOf(x)).isTalkingFirst = true;
            }
        }
    }

    void bearPower(){
        if (!targetList.isEmpty() && !gameList.get(r).isBlocked) {
            for (Role x : targetList) {
                gameList.get(gameList.indexOf(x)).isNearBear = true;
            }
        }
    }


}
