import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;

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
    List<Player> players = Players.load();
    System.out.printf("There are currently %d registered players.%n", players.size());
    
    mMenu.put("new", "Create a new team");
    mMenu.put("add", "Add a player to a team");
    mMenu.put("remove", "Remove a player from a team");
    mMenu.put("print", "Print the player roster for a team");
    mMenu.put("team report", "Show the Balance Report for a team");
    mMenu.put("league report", "Show a League Balance Report for the whole league");
    mMenu.put("quit", "Exit the league manager");
    
    String choice = null;
    String teamName = null;
    do {
      try {
        
        choice = PromptForChoice();
      
        switch (choice) {
          
          case "new":
            // First, check to see that we only have enough teams for 11 players per team
            if (mTeamMap.size() == players.size()/11) {
              System.out.println("Maximum number of teams already exists");
              mMenu.remove("new");
              break;
            }
            
            System.out.print("Enter new team name: ");
            teamName = ReadString();
            System.out.print("Enter coach's name: ");
            String coach = ReadString();
          
            Team newTeam = new Team(teamName, coach);
            mTeamMap.put(teamName,newTeam);
            System.out.printf("Created team: %s %n", newTeam.toString());
            break;
          
          case "add":
            // Show the existings and choose one whose roster we'll modify
            Team team = ChooseTeam();
            if (null == team) {
              System.out.println("Cannot add players to teams until teams are created");
              break;
            }
            List<Player> roster = team.getRoster();
          
            // Show the players on the master list and choose one to add to the roster
            ShowPlayerList(players);
            System.out.print("Choose a player to add: ");
            int index = ReadNumber() - 1;
          
            // Add the player to the team roster and remove him from the master list
            roster.add(players.get(index));
            players.remove(index);
            team.setRoster(roster);
            mTeamMap.replace(teamName,team);
          
            // Show the modified roster
            roster = ShowPlayersForTeam(team);
            break;
          
          case "remove":
            // Show the existing teams and choose one whose roster we'll modify.  Then,
            // show the player roster for that team.
            team = ChooseTeam();
            if (null == team) {
              System.out.println("Cannot remove players from teams until teams are created");
              break;
            }
            roster = ShowPlayersForTeam(team);
          
            // Get the index of the player to remove and remove that player from the team list 
            // while adding the player to the master list of players
            System.out.print("Choose a player to remove: ");
            index = ReadNumber() - 1;
            players.add(roster.get(index));
            roster.remove(index);
            team.setRoster(roster);
            mTeamMap.replace(teamName,team);
          
            // Finally, show the modified roster
            roster = ShowPlayersForTeam(team);
            break;
          
          case "print":
            // Show a list of teams, choose one, and then show the roster for that team
            team = ChooseTeam();
            if (null == team) {
              System.out.println("Cannot print team roster until teams are created");
              break;
            }
            roster = ShowPlayersForTeam(team);
            break;
          
          case "team report":
            // Show a list of teams, choose one, and then show the report for that team
            team = ChooseTeam();
            if (null == team) {
              System.out.println("Cannot show team Balance Report until teams are created");
              break;
            }
            ShowBalanceReportForTeam(team);
            break;
          
          case "league report":
            System.out.printf("%n%n *** League Balance Report *** %n%n");
            // For each team, get the balance report and show the stats
            for (Map.Entry<String, Team> thisTeam : mTeamMap.entrySet()) {
              balanceReport = GetBalanceReportForTeam(thisTeam.getValue());
              System.out.printf("Showing stats for %s: %n",thisTeam.getValue().toString());
              
              // Show the size of each group in the balance report
              for (Map.Entry<String, List<Player>> thisEntry : balanceReport.entrySet()) {
                System.out.printf("Number of players in group '%s': %d %n",
                                  thisEntry.getKey(), thisEntry.getValue().size());
              }
              System.out.println("");
            }
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
      catch(NumberFormatException nfe) {
        System.out.println("Problem processing input");
        nfe.printStackTrace();
      }
    } while (0 != choice.compareTo("quit"));
  }
  
  
  // PUBLIC METHODS //
  public static String PromptForChoice() throws IOException {
    System.out.printf("%nMenu options: %n");
    for (Map.Entry<String, String> option : mMenu.entrySet()) {
      System.out.printf("%s - %s %n",
                        option.getKey(),
                        option.getValue());
    }
    System.out.print("What do you want to do:  ");
    String choice = ReadString().toLowerCase();
    System.out.println("");
    return choice;
  }
  
  
  
  // PRIVATE METHODS //
  private static Team ChooseTeam() throws IOException {
    // Show the team names and prompt for a choice
    String[] teamNameArray = ShowExistingTeams();
    if (0 == teamNameArray.length) return null;
    System.out.print("Choose a team: ");
    String teamName = teamNameArray[ReadNumber()-1];
    System.out.println("");
    return mTeamMap.get(teamName);
  }
  
  
  private static String[] ShowExistingTeams() {
    if (0 == mTeamMap.size()) return new String[0];
    System.out.println("Existing teams: ");
    int count = 1;
    String[] teamNameArray = new String[mTeamMap.size()];
    for (Map.Entry<String, Team> thisTeam : mTeamMap.entrySet()) {
      System.out.printf("%d. %s %n", count, thisTeam.getKey());
      teamNameArray[count-1] = thisTeam.getKey();
      count++;
    }
    return teamNameArray;
  }
  
  
  private static List<Player> ShowPlayersForTeam(Team team) {
    List<Player> roster = team.getRoster();
    System.out.printf("Team roster for the %s: %n",team.toString());
    ShowPlayerList(roster);
    return roster;
  }
  
  
  private static Map<String, List<Player>> GetBalanceReportForTeam(Team team) {
    Map<String, List<Player>> reportMap = new TreeMap<>();
    List<Player> heightList = new ArrayList<>();
    List<Player> expList = new ArrayList<>();
    List<Player> newList = new ArrayList<>();
    List<Player> roster = team.getRoster();
    
    // Populate the Balance Report map...
    for ( Player player : roster) {
      // ...using player height as a key...
      int playerHeight = player.getHeightInInches();
      String heightAsString = Integer.toString(playerHeight) + " inches";
      if (reportMap.containsKey(heightAsString)) {
        heightList = reportMap.get(heightAsString);
        heightList.add(player);
        reportMap.put(heightAsString, heightList);
      }
      else {
        List<Player> newHeightList = new ArrayList<>();
        newHeightList.add(player);
        reportMap.put(heightAsString, newHeightList);
      }
      
      // ...as well as previous experience
      if (player.isPreviousExperience()) {
        if (reportMap.containsKey("experienced")) {
          expList = reportMap.get("experienced");
          expList.add(player);
          reportMap.put("experienced", expList);
        }
        else {
          List<Player> newExpList = new ArrayList<>();
          newExpList.add(player);
          reportMap.put("experienced", newExpList);
        }
      }
      else {
        if (reportMap.containsKey("new")) {
          newList = reportMap.get("new");
          newList.add(player);
          reportMap.put("new", newList);
        }
        else {
          List<Player> newNewList = new ArrayList<>();
          newNewList.add(player);
          reportMap.put("new", newNewList);
        }
      }
    }
    return reportMap;
  }
  
  
  private static void ShowBalanceReportForTeam(Team team) {
    // Get the balance report...
    Map<String, List<Player>> report = GetBalanceReportForTeam(team);
    
    // ...and show the Balance Report
    System.out.printf("Balance Report roster for the %s: %n%n",team.toString());
    for (Map.Entry<String,List<Player>> thisEntry : report.entrySet()) {
      System.out.printf("Showing players for group: %s %n",thisEntry.getKey());
      ShowPlayerList(thisEntry.getValue());
    }
  }
  
  
  private static void ShowPlayerList(List<Player> playerList) {
    int count = 1;
    for (Player player : playerList) {
      System.out.printf("%d. %s %n", count, player.toString());
      count++;
    }
    System.out.println("");
  }
  
  
  private static String ReadString() throws IOException {
    return mReader.readLine().trim();
  }
  
  
  private static int ReadNumber() throws IOException {
    String numberAsString = ReadString();
    return Integer.parseInt(numberAsString);
  }

}
