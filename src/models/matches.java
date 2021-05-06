package models;

import java.sql.*;
import java.util.*;

import static models.connection.*;

public class matches {
    private static final String tableName = "matches";
    private static final Map<Integer, Matches> elements = new HashMap<Integer,Matches>();

    public static void insert(int tournamentId,int team1Id,int team2Id){
        String query = "INSERT INTO "+tableName+" (tournament_id,team_1_id,team_2_id,created_at) "+
                "VALUES(?,?,?,?)";

        try {
            PreparedStatement stmt = getPreparedStatement(query);

            long millis=System.currentTimeMillis();
            java.util.Date date=new java.util.Date(millis);

            stmt.setInt(1,tournamentId);
            stmt.setInt(2,team1Id);
            stmt.setInt(3,team2Id);
            stmt.setString(4,date.toString());


            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Exeception Occured!!");
            System.out.println(e.getMessage());
            e.printStackTrace();
            disconnect();
            System.exit(1);
        }
    }

    public static void update(int id,int hasStarted,int hasEnded,int team_1_goals,int team_2_goals){
        String query = "UPDATE "+tableName+" SET hasStarted=?, hasEnded=?, team_1_goals=?,team_2_goals=?"+
                "WHERE id = ?";
        System.out.println(query);
        try {
            PreparedStatement stmt = getPreparedStatement(query);

            long millis=System.currentTimeMillis();
            java.util.Date date=new java.util.Date(millis);

            stmt.setInt(1,hasStarted);
            stmt.setInt(2,hasEnded);
            stmt.setInt(3,team_1_goals);stmt.setInt(4,team_2_goals);
            stmt.setInt(5,id);


            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Exeception Occured!!");
            System.out.println(e.getMessage());
            disconnect();
            System.exit(1);
        }
    }

    public static void all(){
        elements.clear();
        StringBuilder Query;
        String[] columns = new String[0];
        Query = select(tableName,columns);
        try {
            ResultSet rs = getResultsetFromQuery(Query.toString());
            while (rs.next()){
                int team_1_id,team_2_id,won_id,tournament_id;

                tournament_id = rs.getInt("tournament_id");
                team_1_id = rs.getInt("team_1_id");
                team_2_id = rs.getInt("team_2_id");
                Integer Won_id = (Integer)rs.getObject("won_team_id");

                StringBuilder subQuery;

                subQuery = select(tournaments.getTableName(),columns);
                where(subQuery,"id","=",String.format("%d",tournament_id));

                ResultSet subrs = getResultsetFromQuery(subQuery.toString());
                Tournaments t = null;
                while(subrs.next()){
                    t = new Tournaments(subrs.getInt("id"),subrs.getString("name"),subrs.getString("tts"),subrs.getString("tte"),
                            subrs.getInt("no_of_teams"),subrs.getInt("hasEnded"));

                }

                subQuery = select(teams.getTableName(),columns);
                where(subQuery,"id","=",String.format("%d",team_1_id));

                subrs = getResultsetFromQuery(subQuery.toString());
                Teams t1 = null;

                while(subrs.next()){
                    t1 = new Teams(subrs.getInt("id"),subrs.getString("name"),subrs.getString("initials"),
                            subrs.getString("coach_name"),t,subrs.getString("captain_name")) ;
                }

                subQuery = select(teams.getTableName(),columns);
                where(subQuery,"id","=",String.format("%d",team_2_id));

                subrs = getResultsetFromQuery(subQuery.toString());
                Teams t2 = null;

                while(subrs.next()){
                    t2 = new Teams(subrs.getInt("id"),subrs.getString("name"),subrs.getString("initials"),
                            subrs.getString("coach_name"),t,subrs.getString("captain_name")) ;
                }
                Teams wt=null;
                if(Won_id==team_1_id){
                    wt=t1;
                }
                else if (Won_id.equals(team_2_id)){
                    wt=t2;
                }

                Matches tmp = new Matches(rs.getInt("id"),t,t1,t2,rs.getInt("hasStarted"),rs.getInt("hasEnded"),wt,rs.getInt("isDraw"),
                        rs.getInt("team_1_goals"),rs.getInt("team_2_goals"),rs.getString("created_at"));

                elements.put(tmp.id,tmp);

            }
        } catch (SQLException e) {
            System.err.println("Exeception Occured!!");
            System.out.println(e.getMessage());
            disconnect();
            System.exit(1);
        }
    }

    public static void executeQuery(String query){
        elements.clear();
        try {

            String[] columns = new String[0];
            System.out.println(query);
            ResultSet rs = getResultsetFromQuery(query);
            while (rs.next()){
                int team_1_id,team_2_id,won_id,tournament_id;

                tournament_id = rs.getInt("tournament_id");
                team_1_id = rs.getInt("team_1_id");
                team_2_id = rs.getInt("team_2_id");
                Integer Won_id = (Integer)rs.getObject("won_team_id");

                StringBuilder subQuery;

                subQuery = select(tournaments.getTableName(),columns);
                where(subQuery,"id","=",String.format("%d",tournament_id));

                ResultSet subrs = getResultsetFromQuery(subQuery.toString());
                Tournaments t = null;
                while(subrs.next()){
                    t = new Tournaments(subrs.getInt("id"),subrs.getString("name"),subrs.getString("tts"),subrs.getString("tte"),
                            subrs.getInt("no_of_teams"),subrs.getInt("hasEnded"));

                }

                subQuery = select(teams.getTableName(),columns);
                where(subQuery,"id","=",String.format("%d",team_1_id));

                subrs = getResultsetFromQuery(subQuery.toString());
                Teams t1 = null;

                while(subrs.next()){
                    t1 = new Teams(subrs.getInt("id"),subrs.getString("name"),subrs.getString("initials"),
                            subrs.getString("coach_name"),t,subrs.getString("captain_name")) ;
                }

                subQuery = select(teams.getTableName(),columns);
                where(subQuery,"id","=",String.format("%d",team_2_id));

                subrs = getResultsetFromQuery(subQuery.toString());
                Teams t2 = null;

                while(subrs.next()){
                    t2 = new Teams(subrs.getInt("id"),subrs.getString("name"),subrs.getString("initials"),
                            subrs.getString("coach_name"),t,subrs.getString("captain_name")) ;
                }
                Teams wt=null;
               if(Won_id!=null){
                   if(Won_id==team_1_id){
                       wt=t1;
                   }
                   else if (Won_id.equals(team_2_id)){
                       wt=t2;
                   }
               }

                Matches tmp = new Matches(rs.getInt("id"),t,t1,t2,rs.getInt("hasStarted"),rs.getInt("hasEnded"),wt,rs.getInt("isDraw"),
                        rs.getInt("team_1_goals"),rs.getInt("team_2_goals"),rs.getString("created_at"));

                elements.put(tmp.id,tmp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Exeception Occured!!");
            System.out.println(e.getMessage());
        }
    }

    public static Map<Integer, Matches> getElements() {
        return elements;
    }

    public static String getTableName() {
        return tableName;
    }

}
