package com.example.werewolf_gamemasterguide;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,Serializable {

    // Roles declaration
    // Add other roles declaration below
    Role guardian,wolf,fatherWolf,sorcerer,seer,barber,alien,captain,simpleVillager,servant,
        knight,blackWolf,bear,cupid,wildChild,redWolf,shepherd,pyromaniac,blueWolf;

    ArrayList<Role> completeList = new ArrayList<Role>(); // Include the complete list of roles
    ArrayList<Role> gameList = new ArrayList<Role>();   // include role chosen by the user
    ArrayList<Role> pickingList = new ArrayList<Role>();    // basically gameList, it will decrease
                                                         // each time a role is given to a player.

    TextView roleNameView;  // role Name to be displayed when picked
    TextView roleDescView;  // role Description to be displayed when picked
    TextView roleTeamView;  // role Team to be displayed when picked

    AlertDialog.Builder dialogBuilder; // Builder for the pop up window
    AlertDialog dialog; // AlertDialog for the pop up window

    EditText popUpName; // Editable text for the game master to fill with the picked role owner
    TextView popUpRole; // A reminder for the game master
    Button popUpButton; // confirm given name
                        // could be skipped, an "unknown" name will be given instead

    int x = -1; // general purpose counter
                // do not remove or override initial value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Include important initialization necessary for the activity to work properly
        // Initializing UI elements
        // Initializing Roles

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_constraint);

        roleNameView = findViewById(R.id.role_name);
        roleDescView = findViewById(R.id.role_desc);
        roleTeamView = findViewById(R.id.role_team);

        Button resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(this);

        Button pickButton = findViewById(R.id.pick_button);
        pickButton.setOnClickListener(this);

        Button startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(this);

        initialize();
        drawLayout();
        resetButton();

    }

    public void createPopUpWindow(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View popWindow = getLayoutInflater().inflate(R.layout.popup,null);
        popUpRole = (TextView) popWindow.findViewById(R.id.popup_role);
        popUpName = (EditText) popWindow.findViewById(R.id.popup_name);
        popUpButton = (Button) popWindow.findViewById(R.id.popup_confirm);

        dialogBuilder.setView(popWindow);
        dialog = dialogBuilder.create();
        dialog.show();

        popUpRole.setText(pickingList.get(x).name);

        popUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!popUpName.getText().toString().isEmpty()){
                    gameList.get(gameList.indexOf(pickingList.get(x))).owner = popUpName.getText().toString();
                    completeList.get(completeList.indexOf(pickingList.get(x))).owner = popUpName.getText().toString();
                }else{
                    gameList.get(gameList.indexOf(pickingList.get(x))).owner = getString(R.string.owner_placeholder);
                    completeList.get(completeList.indexOf(pickingList.get(x))).owner = getString(R.string.owner_placeholder);
                }

                displayShortToast(""+getString(gameList.get(gameList.indexOf(pickingList.get(x))).name)+" is now named: "+gameList.get(gameList.indexOf(pickingList.get(x))).owner);
                pickingList.remove(x);
                dialog.dismiss();
            }
        });
    }

    void initialize(){
        // Executing the constructor of each declared variable here
        // NOTES FOR MODIFICATION
        // ADD YOUR CONSTRUCTOR IN THE SECTION BELOW
        // DO NOT MODIFY ANY CONSTRUCTOR OF ANY EXISTING ROLE
        // YOU MAY NEED TO ADD ROLE NAME, DESCRIPTION AND SOME OTHER INFORMATION TO THE STRING RESOURCE FILE

        // BLUE WOLF
        blueWolf = new Role(ROLES.BLUE_WOLF,TEAM.WOLVES,"empty",R.string.blueWolf,R.string.blueWolfDesc,
                (CheckBox) findViewById(R.id.blue_wolf_checkBox),
                (TextView) findViewById(R.id.blue_wolf_name),
                (TextView) findViewById(R.id.blue_wolf_desc),
                (TextView) findViewById(R.id.blue_wolf_team));
        completeList.add(blueWolf);

        // ------------------------------------------------------------------------------------------------------
        // RED WOLF
        redWolf = new Role(ROLES.RED_WOLF,TEAM.WOLVES,"empty",R.string.redWolf,R.string.redWolfDesc,
                (CheckBox) findViewById(R.id.red_wolf_checkBox),
                (TextView) findViewById(R.id.red_wolf_name),
                (TextView) findViewById(R.id.red_wolf_desc),
                (TextView) findViewById(R.id.red_wolf_team));
        completeList.add(redWolf);

        // ------------------------------------------------------------------------------------------------------
        // BLACK WOLF
        blackWolf = new Role(ROLES.BLACK_WOLF,TEAM.WOLVES,"empty",R.string.blackWolf,R.string.blackWolfDesc,
                (CheckBox) findViewById(R.id.black_wolf_checkBox),
                (TextView) findViewById(R.id.black_wolf_name),
                (TextView) findViewById(R.id.black_wolf_desc),
                (TextView) findViewById(R.id.black_wolf_team));
        completeList.add(blackWolf);

        // ------------------------------------------------------------------------------------------------------
        // SERVANT
        servant = new Role(ROLES.SERVANT,TEAM.VILLAGE,"empty",R.string.servant,R.string.servantDesc,
                (CheckBox) findViewById(R.id.servant_checkBox),
                (TextView) findViewById(R.id.servant_name),
                (TextView) findViewById(R.id.servant_desc),
                (TextView) findViewById(R.id.servant_team));
        completeList.add(servant);

        // ------------------------------------------------------------------------------------------------------
        // CUPID
        cupid = new Role(ROLES.CUPID,TEAM.VILLAGE,"empty",R.string.cupid,R.string.cupidDesc,
                (CheckBox) findViewById(R.id.cupid_checkBox),
                (TextView) findViewById(R.id.cupid_name),
                (TextView) findViewById(R.id.cupid_desc),
                (TextView) findViewById(R.id.cupid_team));
        completeList.add(cupid);

        // ------------------------------------------------------------------------------------------------------
        // PYROMANIAC
        pyromaniac = new Role(ROLES.PYROMANIAC,TEAM.VILLAGE,"empty",R.string.pyromaniac,R.string.pyromaniacDesc,
                (CheckBox) findViewById(R.id.pyromaniac_checkBox),
                (TextView) findViewById(R.id.pyromaniac_name),
                (TextView) findViewById(R.id.pyromaniac_desc),
                (TextView) findViewById(R.id.pyromaniac_team));
        completeList.add(pyromaniac);

        // ------------------------------------------------------------------------------------------------------
        // WILD CHILD
        wildChild = new Role(ROLES.WILD_CHILD,TEAM.VILLAGE,"empty",R.string.wildChild,R.string.wildChildDesc,
                (CheckBox) findViewById(R.id.wild_child_checkBox),
                (TextView) findViewById(R.id.wild_child_name),
                (TextView) findViewById(R.id.wild_child_desc),
                (TextView) findViewById(R.id.wild_child_team));
        completeList.add(wildChild);

        // ------------------------------------------------------------------------------------------------------
        // GUARDIAN
        guardian = new Role(ROLES.GUARDIAN,TEAM.VILLAGE,"empty",R.string.guardian,R.string.guardianDesc,
                (CheckBox) findViewById(R.id.guardian_checkBox),
                (TextView) findViewById(R.id.guardian_name),
                (TextView) findViewById(R.id.guardian_desc),
                (TextView) findViewById(R.id.guardian_team));
        completeList.add(guardian);

        // ------------------------------------------------------------------------------------------------------
        // WEREWOLF
        wolf = new Role(ROLES.WEREWOLF,TEAM.WOLVES,"empty",R.string.wolf,R.string.wolfDesc,
                (CheckBox) findViewById(R.id.wolf_checkBox),
                (TextView) findViewById(R.id.wolf_name),
                (TextView) findViewById(R.id.wolf_desc),
                (TextView) findViewById(R.id.wolf_team));
        completeList.add(wolf);

        // ------------------------------------------------------------------------------------------------------
        // FATHER WOLF
        fatherWolf = new Role(ROLES.FATHER_WOLF,TEAM.WOLVES,"empty",R.string.fatherWolf,R.string.fatherWolfDesc,
                (CheckBox) findViewById(R.id.father_wolf_checkBox),
                (TextView) findViewById(R.id.father_wolf_name),
                (TextView) findViewById(R.id.father_wolf_desc),
                (TextView) findViewById(R.id.father_wolf_team));
        completeList.add(fatherWolf);

        // ------------------------------------------------------------------------------------------------------
        // SORCERER
        sorcerer = new Role(ROLES.SORCERER,TEAM.VILLAGE,"empty",R.string.sorcerer,R.string.sorcererDesc,
                (CheckBox) findViewById(R.id.sorcerer_checkBox),
                (TextView) findViewById(R.id.sorcerer_name),
                (TextView) findViewById(R.id.sorcerer_desc),
                (TextView) findViewById(R.id.sorcerer_team));
        completeList.add(sorcerer);

        // ------------------------------------------------------------------------------------------------------
        // SEER
        seer = new Role(ROLES.SEER,TEAM.VILLAGE,"empty",R.string.seer,R.string.seerDesc,
                (CheckBox) findViewById(R.id.seer_checkBox),
                (TextView) findViewById(R.id.seer_name),
                (TextView) findViewById(R.id.seer_desc),
                (TextView) findViewById(R.id.seer_team));
        completeList.add(seer);

        // ------------------------------------------------------------------------------------------------------
        // SHEPHERD
        shepherd = new Role(ROLES.SHEPHERD,TEAM.VILLAGE,"empty",R.string.shepherd,R.string.shepherdDesc,
                (CheckBox) findViewById(R.id.shepherd_checkBox),
                (TextView) findViewById(R.id.shepherd_name),
                (TextView) findViewById(R.id.shepherd_desc),
                (TextView) findViewById(R.id.shepherd_team));
        completeList.add(shepherd);

        // ------------------------------------------------------------------------------------------------------
        // BARBER
        barber = new Role(ROLES.BARBER,TEAM.VILLAGE,"empty",R.string.barber,R.string.barberDesc,
                (CheckBox) findViewById(R.id.barber_checkBox),
                (TextView) findViewById(R.id.barber_name),
                (TextView) findViewById(R.id.barber_desc),
                (TextView) findViewById(R.id.barber_team));
        completeList.add(barber);

        // ------------------------------------------------------------------------------------------------------
        // ALIEN
        alien = new Role(ROLES.ALIEN,TEAM.SOLO,"empty",R.string.alien,R.string.alienDesc,
                (CheckBox) findViewById(R.id.alien_checkBox),
                (TextView) findViewById(R.id.alien_name),
                (TextView) findViewById(R.id.alien_desc),
                (TextView) findViewById(R.id.alien_team));
        completeList.add(alien);

        // ------------------------------------------------------------------------------------------------------
        // KNIGHT
        knight = new Role(ROLES.KNIGHT,TEAM.VILLAGE,"empty",R.string.knight,R.string.knightDesc,
                (CheckBox) findViewById(R.id.knight_checkBox),
                (TextView) findViewById(R.id.knight_name),
                (TextView) findViewById(R.id.knight_desc),
                (TextView) findViewById(R.id.knight_team));
        completeList.add(knight);

        // ------------------------------------------------------------------------------------------------------
        // CAPTAIN
        captain = new Role(ROLES.CAPTAIN,TEAM.VILLAGE,"empty",R.string.captain,R.string.captainDesc,
                (CheckBox) findViewById(R.id.captain_checkBox),
                (TextView) findViewById(R.id.captain_name),
                (TextView) findViewById(R.id.captain_desc),
                (TextView) findViewById(R.id.captain_team));
        completeList.add(captain);

        // ------------------------------------------------------------------------------------------------------
        // SIMPLE VILLAGER
        simpleVillager = new Role(ROLES.VILLAGER,TEAM.VILLAGE,"empty",R.string.simpleVillager,R.string.simpleVillagerDesc,
                (CheckBox) findViewById(R.id.villager_checkBox),
                (TextView) findViewById(R.id.villager_name),
                (TextView) findViewById(R.id.villager_desc),
                (TextView) findViewById(R.id.villager_team));
        completeList.add(simpleVillager);

        // ------------------------------------------------------------------------------------------------------
        // BEAR
        bear = new Role(ROLES.BEAR,TEAM.VILLAGE,"empty",R.string.bear,R.string.bearDesc,
                (CheckBox) findViewById(R.id.bear_checkBox),
                (TextView) findViewById(R.id.bear_name),
                (TextView) findViewById(R.id.bear_desc),
                (TextView) findViewById(R.id.bear_team));
        completeList.add(bear);

        // ------------------------------------------------------------------------------------------------------
        // ADD NEW ROLES HERE
        // ADD EACH ROLE TO THE ArrayList called"completeList"

        // NEW ROLE
        // completeList.add(newRole)
        // ------------------------------------------------------------------------------------------------------

    }

    void drawLayout(){
        for (int i = 0; i < completeList.size(); i++){
            Role e = completeList.get(i);
            e.nameText.setText(e.name);
            e.descText.setText(e.desc);
            e.teamText.setText(TeamToString(e.team));
        }
    }

    void resetButton(){
        gameList = new ArrayList<Role>();
        pickingList = new ArrayList<Role>();
        x = -1;
        for (int i = 0;i < completeList.size();i++){
            if (completeList.get(i).checkBox.isChecked()){
                completeList.get(i).isChecked = true;
                completeList.get(i).owner = getString(R.string.owner_placeholder);
                gameList.add(completeList.get(i));
                pickingList.add(completeList.get(i));
            }
            else completeList.get(i).isChecked = false;
        }

        Toast toast = Toast.makeText(getApplicationContext(),
                "Reset with "+gameList.size()+" role(s)",
                Toast.LENGTH_SHORT);
        toast.show();
    }

    void startGame(){
        Intent i = new Intent(this,GameActivity.class);
        i.putExtra("gameList", (Serializable) gameList);
        i.putExtra("fullList", (Serializable) completeList);
        startActivity(i);
    }

    public void displayShortToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_SHORT);
        toast.show();
    }

    void pickButton(){
        if (pickingList.size()>0){
            x = (int) (Math.random()*pickingList.size());
            roleNameView.setText(pickingList.get(x).name);
            roleDescView.setText(pickingList.get(x).desc);
            roleTeamView.setText(TeamToString(pickingList.get(x).team));
            createPopUpWindow();

        }else {
            roleNameView.setText(R.string.role_placeholder);
            roleDescView.setText(R.string.description_placeholder);
            roleTeamView.setText(TeamToString(TEAM.SOLO));
            displayShortToast("You picked them all ! Click RESET");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pick_button: pickButton();break;
            case R.id.reset_button: resetButton();break;
            case R.id.start_button: startGame();break;
        }
    }

    String TeamToString(TEAM team){
        switch (team){
            case VILLAGE: return getString(R.string.team_villager);
            case WOLVES: return getString(R.string.team_wolves);
            case SOLO: return getString(R.string.team_solo);
        }
        return null;
    }
}