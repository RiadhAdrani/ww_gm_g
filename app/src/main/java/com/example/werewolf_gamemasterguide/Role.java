package com.example.werewolf_gamemasterguide;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    transient ArrayList<Role> list = new ArrayList<Role>();

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

    public void ActionButton(){
        popButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(Role.this);
            }
        });

    }

}
