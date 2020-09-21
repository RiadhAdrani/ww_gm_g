package com.example.werewolf_gamemasterguide;

import java.util.ArrayList;

public class Turn {
    Role current;
    Turn previous;
    Turn next;
    String info;
    Boolean isPlayable;
    Boolean isPlaying;

    public Turn(Role current, Turn previous, Turn next, String info) {
        this.current = current;
        this.previous = previous;
        this.next = next;
        this.info = info;
    }

    public void associateRole(ArrayList<Role> list){
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).role == this.current.role) {
                this.current = list.get(i);
                isPlayable = true;
                break;
            } else {
                isPlayable = false;
            }
        }
    }
    
}