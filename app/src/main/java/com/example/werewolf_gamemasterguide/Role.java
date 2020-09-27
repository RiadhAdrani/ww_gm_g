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
    transient TextView listText;

    boolean isAlive = true,
            isBlued = false,
            isServed  = false,
            isGuarded = false,
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
            isHavingACut = false;


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
}
