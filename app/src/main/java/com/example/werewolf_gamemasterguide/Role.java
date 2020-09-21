package com.example.werewolf_gamemasterguide;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class Role implements java.io.Serializable {

    transient ROLES role;
    transient TEAM team;

    String owner;
    int name;
    int desc;

    boolean isChecked = false;

    transient CheckBox checkBox;
    transient TextView nameText;
    transient TextView descText;
    transient TextView teamText;
    transient Button actionButton;


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
    }
}
