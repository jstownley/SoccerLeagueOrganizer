package com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Team {
  private String mTeamName;
  private String mCoach;
  private List<Player> mRoster;
 
  public Team(String teamName, String coach) {
    mTeamName = teamName;
    mCoach = coach;
    mRoster = new ArrayList<Player>();
  }
  
  public String getTeamName() {
    return mTeamName;
  }
  
  public String getCoach() {
    return mCoach;
  }
  
  public List<Player> getRoster() {
    return mRoster;
  }
  
  public void setRoster(List<Player> roster) {
    Collections.sort(roster, new Comparator<Player>() {
      
      @Override
      public int compare(Player player1, Player player2) {
        if (player1.equals(player2)) return 0;
        return player1.compareTo(player2);
      }
            
    });
    mRoster = roster;
  }
  
  @Override
  public String toString() {
    return String.format("%s, %s coaching", mTeamName, mCoach);
  }
  
}