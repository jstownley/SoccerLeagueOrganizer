package com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
  private String mTeamName;
  private String mCoach;
  private List<Player> mRoster;
 
  public Team(String teamName, String coach) {
    mTeamName = teamName;
    mCoach = coach;
    mRoster = new ArrayList<Player>();
    
    // TEST CODE! REMOVE THIS FOR FINAL VERSION
    Player[] players = Players.load();
    for (Player player : players) {
      mRoster.add(player);
    }
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
    mRoster = roster;
  }
  
  @Override
  public String toString() {
    return String.format("%s, %s coaching", mTeamName, mCoach);
  }
  
}