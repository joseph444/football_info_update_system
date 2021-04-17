package models;

import java.sql.*;
import java.util.*;

import static models.connection.*;

public class teams {
    private static final String tableName = "teams";
    private static final Map<Integer, Teams> elements = new HashMap<Integer,Teams>();


    public static void insert(String name,String initials,String coach_name,int t_id,String captain_name){
        String query = "INSERT INTO "+tableName+" (name,initials,tournament_id,coach_name,captain_name) "+
                "VALUES(?,?,?,?,?)";

        try {
            PreparedStatement stmt = getPreparedStatement(query);

            stmt.setString(1,name);
            stmt.setString(2,initials);
            stmt.setInt(3,t_id);
            stmt.setString(4,coach_name);
            stmt.setString(5,captain_name);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Exeception Occured!!");
            System.out.println(e.getMessage());
            disconnect();
            System.exit(1);
        }
    }

    public static void update(int id,String name,String initials,String coach_name,int t_id,String captain_name){
        String query = "UPDATE"+tableName+" SET name=?,initals=?,tournament_id=?,coach_name=?,captain_name=? WHERE id = ?";

        try {
            PreparedStatement stmt = getPreparedStatement(query);

            stmt.setString(1,name);
            stmt.setString(2,initials);
            stmt.setInt(3,t_id);
            stmt.setString(4,coach_name);
            stmt.setString(4,captain_name);
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
        StringBuilder Query;
        String[] columns = new String[0];
        Query = select(tableName,columns);
        try {
            ResultSet rs = getResultsetFromQuery(Query.toString());
            while (rs.next()){
                int t_id = rs.getInt("tournament_id");
                String relationalName=tournaments.getTableName();
                StringBuilder q = select(relationalName,columns);
                where(q,"id","=",String.format("%d",t_id));
                ResultSet rst = getResultsetFromQuery(q.toString());
                Tournaments t = null;
                while(rst.next()){
                    t =new Tournaments(rst.getInt("id"),rst.getString("name"),rst.getString("tts"),rst.getString("tte"),
                            rst.getInt("no_of_teams"),rst.getInt("hasEnded")) ;

                }
                Teams  tmp =new Teams(rs.getInt("id"),rs.getString("name"),rs.getString("initials"),
                        rs.getString("coach_name"),t,rs.getString("captain_name")) ;

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
        try {

            String[] columns = new String[0];
            ResultSet rs = getResultsetFromQuery(query);
            while (rs.next()){
                int t_id = rs.getInt("tournament_id");
                String relationalName=tournaments.getTableName();
                StringBuilder q = select(relationalName,columns);
                where(q,"id","=",String.format("%d",t_id));
                ResultSet rst = getResultsetFromQuery(q.toString());
                Tournaments t = null;
                while(rst.next()){
                    t =new Tournaments(rst.getInt("id"),rst.getString("name"),rst.getString("tts"),rst.getString("tte"),
                            rst.getInt("no_of_teams"),rst.getInt("hasEnded")) ;

                }
                Teams  tmp =new Teams(rs.getInt("id"),rs.getString("name"),rs.getString("initals"),
                        rs.getString("couch_name"),t,rs.getString("captain_name")) ;

                elements.put(tmp.id,tmp);
            }
        } catch (SQLException e) {
            System.err.println("Exeception Occured!!");
            System.out.println(e.getMessage());
            disconnect();
            System.exit(1);
        }
    }


    public static Map<Integer, Teams> getElements() {
        return elements;
    }

    public static String getTableName() {
        return tableName;
    }

}
