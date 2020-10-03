package com.example.werewolf_gamemasterguide;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Role implements java.io.Serializable {

    ROLES role;
    TEAM team;

    String owner;
    int name;
    int desc;

    boolean isChecked = false;

    int abilityOne = 0;
    int abilityTwo = 0;

    transient CheckBox checkBox;
    transient TextView nameText;
    transient TextView descText;
    transient TextView teamText;

    transient LinearLayout popCard;
    transient TextView popOwner;
    transient Button popButton;

    int listMaxSize = 0;
    String temp = "empty";
    transient ArrayList<Role> list = new ArrayList<Role>();
    transient ArrayList<Role> gameList = new ArrayList<Role>();
    transient TextView listText;

    boolean isAlive = true,
            isBlued = false,
            isServed  = false,
            isGuarded = false,
            isPreviouslyGuarded = false,
            isBlocked = false,
            isHealed = false,
            isKilled = false,
            isMuted = false,
            isOnFire = false,
            isChilded = false,
            isInfected = false,
            isSeen = false,
            isSheep = false,
            isKnighted = false,
            isCaptain = false,
            isTalkingFirst = false,
            isNearBear = false,
            isLover = false,
            isSorcererEd = false,
            isHavingACut = false,
            isSkippable = false;

    public Role(ROLES role, TEAM team, String owner, int name, int desc, CheckBox checkBox, TextView nameText, TextView descText, TextView teamText) {
        this.role = role;
        this.team = team;
        this.owner = owner;
        this.name = name;
        this.desc = desc;
        this.checkBox = checkBox;
        this.nameText = nameText;
        this.descText = descText;
        this.teamText = teamText;

        targetInit();
        if (this.role == ROLES.CAPTAIN) isCaptain = true;
    }

    public void ActionButton(){
        popButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listMaxSize == list.size()) list.remove(0);;
                {list.add(Role.this);}

                temp = "";
                for (Role role1 : list) {
                    temp += " "+ role1.owner;
                }

                listText.setText(temp);
            }
        });
    }

    public void useAbility(ArrayList<Role> gameList){
        if (!list.isEmpty()){

            switch (this.role){

                case BLUE_WOLF:
                        for (Role x : list) {
                            gameList.get(gameList.indexOf(x)).isBlued = true;
                        } break;

                case RED_WOLF:
                        for (Role x : list){
                            gameList.get(gameList.indexOf(x)).isBlocked = true;
                        } break;

                case BLACK_WOLF:
                        for (Role x : list){
                            if(!gameList.get(gameList.indexOf(x)).isGuarded) gameList.get(gameList.indexOf(x)).isMuted = true;
                        } break;

                case WEREWOLF:
                        for (Role x : list){
                            if(!gameList.get(gameList.indexOf(x)).isGuarded) gameList.get(gameList.indexOf(x)).isKilled = true;
                        } break;

                case FATHER_WOLF:
                        for (Role x : list){
                            if(!gameList.get(gameList.indexOf(x)).isGuarded && abilityOne == 1) {
                                gameList.get(gameList.indexOf(x)).isInfected = true;
                                gameList.get(gameList.indexOf(x)).isKilled = false;
                                abilityOne = 0;
                            }
                        } break;

                case WILD_CHILD:
                        for (Role x : list){
                            gameList.get(gameList.indexOf(x)).isChilded = true;
                        } break;

                case SERVANT:
                        for (Role x : list){
                            gameList.get(gameList.indexOf(x)).isServed = true;
                        } break;

                case CUPID:
                        for (Role x : list){
                            gameList.get(gameList.indexOf(x)).isLover = true;
                        } break;

                case PYROMANIAC:
                        for (Role x : list){
                            gameList.get(gameList.indexOf(x)).isOnFire = true;
                        }break;

                case GUARDIAN:
                        for (Role x : list){
                            gameList.get(gameList.indexOf(x)).isGuarded = true;
                        }break;

                case SORCERER:
                        for (Role x : list){
                            if (!gameList.get(gameList.indexOf(x)).isKilled) {
                                if (abilityOne == 1) {
                                    gameList.get(gameList.indexOf(x)).isSorcererEd = true;
                                    abilityOne = 0;
                                }
                            }
                            else  {
                                if (abilityTwo == 1){
                                    gameList.get(gameList.indexOf(x)).isHealed = true;
                                    gameList.get(gameList.indexOf(x)).isKilled = false;
                                    abilityTwo = 0;
                                }
                            }
                        }break;


                case SEER:
                        for (Role x : list){
                            gameList.get(gameList.indexOf(x)).isSeen = true;
                        }break;


                case SHEPHERD:
                        for (Role x : list){
                            gameList.get(gameList.indexOf(x)).isSheep = true;
                        }break;


                case KNIGHT:
                        for (Role x : list){
                            gameList.get(gameList.indexOf(x)).isKnighted = true;
                        }break;


                case BARBER:
                        for (Role x : list){
                            gameList.get(gameList.indexOf(x)).isHavingACut = true;
                        }break;


                case CAPTAIN:
                        for (Role x : list){
                            if (isBlocked){
                                isCaptain = false;
                                gameList.get(gameList.indexOf(x)).isCaptain = true;
                            } else
                            gameList.get(gameList.indexOf(x)).isTalkingFirst = true;
                        }break;


                case BEAR:
                    for (Role x : list){
                        gameList.get(gameList.indexOf(x)).isNearBear = true;
                    }break;


                case VILLAGER:
                case ALIEN:
                        break;

            }

        }
    }

    public boolean returnSkip(int turn){
        switch (this.role){

            case BLACK_WOLF:
            case WEREWOLF:
            case SORCERER:
            case SHEPHERD:
            case RED_WOLF:
            case GUARDIAN:
            case BEAR:
            case SEER:
            case CAPTAIN:
            case KNIGHT: return false;

            case BLUE_WOLF: if (turn % 3 != 0) return true; else return false;

            case FATHER_WOLF: if (turn == 1) return true; else return false;

            case VILLAGER: return true;

            case BARBER: if (turn != 1 && !isKilled && !isKnighted && !isSorcererEd) return true; else return false;

            case CUPID:
            case SERVANT:
            case ALIEN:
            case WILD_CHILD: if (turn != 1) return true; else return false;

            case PYROMANIAC: if (turn+1 % 2 != 0) return true; else return false;

        }
        return true;
    }

    public int returnTarget(){
        return abilityOne + abilityTwo;
    }

    public void targetInit(){
        switch (this.role){
            case FATHER_WOLF:
            case BLUE_WOLF:
            case PYROMANIAC:
            case WILD_CHILD:
            case CAPTAIN:
            case SERVANT:
            case KNIGHT:
            case BARBER:
            case SEER:
            case GUARDIAN:
            case RED_WOLF:
            case WEREWOLF:
            case BLACK_WOLF: abilityOne = 1; abilityTwo = 0; break;

            case VILLAGER: abilityOne = 0; abilityTwo = 0; break;

            case SORCERER:
            case SHEPHERD:
            case CUPID:
            case BEAR: abilityOne = 1; abilityTwo = 1; break;

            case ALIEN: break;

        }
    }

    public void refreshAbility(){
        switch (this.role){
            case BLUE_WOLF:
            case PYROMANIAC:
            case CAPTAIN:
            case SEER:
            case GUARDIAN:
            case RED_WOLF:
            case WEREWOLF:
            case BLACK_WOLF: abilityOne = 1; abilityTwo = 0; break;

            case BEAR: abilityOne = 1; abilityTwo = 1; break;

            case ALIEN: break;

        }
    }

    public void resolveAttribute(){
        if (isAlive){
            isBlued = false;
            if (isGuarded) isPreviouslyGuarded = true;
            else isPreviouslyGuarded = false;
            isGuarded = false;
            isBlocked = false;
            if (role == ROLES.CAPTAIN && isAlive && !isCaptain) isCaptain = true;
            isMuted = false;
            isSeen = false;
            isSheep = false;
            isTalkingFirst = false;
        }

    }

}
