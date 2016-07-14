package com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Players {

  public static List<Player> load() {
    List<Player> players = new ArrayList<>();
    
    players.add(new Player("Joe", "Smith", 42, true));
    players.add(new Player("Jill", "Tanner", 36, true));
    players.add(new Player("Bill", "Bon", 43, true));
    players.add(new Player("Eva", "Gordon", 45, false));
    players.add(new Player("Matt", "Gill", 40, false));
    players.add(new Player("Kimmy", "Stein", 41, false));
    players.add(new Player("Sammy", "Adams", 45, false));
    players.add(new Player("Karl", "Saygan", 42, true));
    players.add(new Player("Suzane", "Greenberg", 44, true));
    players.add(new Player("Sal", "Dali", 41, false));
    players.add(new Player("Joe", "Kavalier", 39, false));
    players.add(new Player("Ben", "Finkelstein", 44, false));
    players.add(new Player("Diego", "Soto", 41, true));
    players.add(new Player("Chloe", "Alaska", 47, false));
    players.add(new Player("Arfalseld", "Willis", 43, false));
    players.add(new Player("Phillip", "Helm", 44, true));
    players.add(new Player("Les", "Clay", 42, true));
    players.add(new Player("Herschel", "Krustofski", 45, true));
    players.add(new Player("Andrew", "Chalklerz", 42, true));
    players.add(new Player("Pasan", "Membrane", 36, true));
    players.add(new Player("Kenny", "Lovins", 35, true));
    players.add(new Player("Alena", "Sketchings", 45, false));
    players.add(new Player("Carling", "Seacharpet", 40, false));
    players.add(new Player("Joseph", "Freely", 41, false));
    players.add(new Player("Gabe", "Listmaker", 45, false));
    players.add(new Player("Jeremy", "Smith", 42, true));
    players.add(new Player("Ben", "Droid", 44, true));
    players.add(new Player("James", "Dothnette", 41, false));
    players.add(new Player("Nick", "Grande", 39, false));
    players.add(new Player("Will", "Guyam", 44, false));
    players.add(new Player("Jason", "Seaver", 41, true));
    players.add(new Player("Johnny", "Thunder", 47, false));
    players.add(new Player("Ryan", "Creedson", 43, false));
    Collections.sort(players, new Comparator<Player>() {
      
      @Override
      public int compare(Player player1, Player player2) {
        if (player1.equals(player2)) return 0;
        return player1.compareTo(player2);
      }
            
    });
    return players;
  }
  
  /*@Override
  public static void sort(List<Player> playerList) {
    
  }*/

}