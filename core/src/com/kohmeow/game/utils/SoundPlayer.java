package com.kohmeow.game.utils;


import com.badlogic.gdx.audio.Sound;

import com.kohmeow.game.KohMeowGame;

public class SoundPlayer {
    
    private static Sound sound;


    public SoundPlayer(){
        
    }
    public static void Play(String filepath, float volume){
       
        sound = KohMeowGame.manager.get(filepath, Sound.class);
        sound.play(volume);
        

    
    }
}
