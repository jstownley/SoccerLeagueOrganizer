import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.model.Teams;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class LeagueManager {
  public static Map<String, String> mMenu = new HashMap<>();
  public static BufferedReader mReader = new BufferedReader(
                                               new InputStreamReader(System.in)
                                             );
  public static Map<String, Team> mTeamMap = new TreeMap<>();

  public static void main(String[] args) {
    Player[] players = Players.load();
    System.out.printf("There are currently %d registered players.%n", players.length);
    
    mMenu.put("new", "Create a new team");
    mMenu.put("remove", "Remove player from team");
    mMenu.put("quit", "Exit the league manager");
    
    String choice = null;
    String teamName = null;
    do {
      try {
        
        choice = PromptForChoice();
      
        switch (choice) {
          
          case "new":
            System.out.print("Enter new team name: ");
            teamName = ReadString();
            System.out.print("Enter coach's name: ");
            String coach = ReadString();
          
            Team newTeam = new Team(teamName, coach);
            mTeamMap.put(teamName,newTeam);
            System.out.printf("Created team: %s %n", newTeam.toString());
            break;
          
          case "remove":
            // Show the team names and prompt for a choice
            System.out.println("Existing teams: ");
            int count = 1;
            String[] teamNameArray = new String[mTeamMap.size()];
            for (Map.Entry<String, Team> thisTeam : mTeamMap.entrySet()) {
              System.out.printf("%d. %s %n", count, thisTeam.getKey());
              teamNameArray[count-1] = thisTeam.getKey();
              count++;
            }
            System.out.print("Choose a team to remove a player: ");
            teamName = teamNameArray[ReadNumber()-1];
            System.out.println("");
          
            // Present the roster to the manager and prompt for player choice
            Team team = mTeamMap.get(teamName);
            List<Player> roster = team.getRoster();
            System.out.printf("Team roster for %s: %n",teamName);
            count = 1;
            for (Player player : roster) {
              String experienceString;
              if (player.isPreviousExperience()) {
                experienceString = "previous experience";
              } else {
                experienceString = "new player";
              }
              System.out.printf("%d. %s, %s, %d\", %s %n", 
                                count,
                                player.getLastName(),
                                player.getFirstName(),
                                player.getHeightInInches(),
                                experienceString);
              count++;
            }
            System.out.println("");
          
            // Get the index of the player to remove and remove that player
            System.out.print("Choose a player to remove: ");
            int index = ReadNumber() - 1;
            roster.remove(index);
            team.setRoster(roster);
            mTeamMap.replace(teamName,team);
            break;
          
          case "quit":
            System.out.println("Exiting...");
            break;
          
          default:
            System.out.printf("Choice '%s' not recognized. ", choice);
        }
      } 
      catch(IOException ioe) {
        System.out.println("Problem processing input");
        ioe.printStackTrace();
      }
      catch(ArrayIndexOutOfBoundsException aioobe) {
        System.out.println("Problem processing input");
        aioobe.printStackTrace();
      }
    } while (0 != choice.compareTo("quit"));
  }
  
  
  // PUBLIC METHODS //
  public static String PromptForChoice() throws IOException {
    System.out.printf("%n%nMenu options: %n");
    for (Map.Entry<String, String> option : mMenu.entrySet()) {
      System.out.printf("%s - %s %n",
                        option.getKey(),
                        option.getValue());
    }
    System.out.print("What do you want to do:  ");
    return ReadString().toLowerCase();
  }
  
  
  // PRIVATE METHODS //
  private static String ReadString() throws IOException {
    return mReader.readLine().trim();
  }
  
  private static int ReadNumber() throws IOException {
    String numberAsString = ReadString();
    return Integer.parseInt(numberAsString);
  }

}
