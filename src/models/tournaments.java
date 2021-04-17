package models;
import java.sql.*;
import java.util.*;

import static models.connection.*;

public class tournaments {
    private static final String tableName = "tournament";
    private static final Map<Integer, Tournaments> elements = new HashMap<Integer,Tournaments>();

    public static void insert(String name,String tts,String tte,int no_of_teams){

        String query = "INSERT INTO "+tableName+" (name,tts,tte,no_of_teams) "+
                "VALUES(?,?,?,?)";


        try{
            PreparedStatement stmt = getPreparedStatement(query);

            stmt.setString(1,name);
            stmt.setString(2,tts);
            stmt.setString(3,tte);
            stmt.setInt(4,no_of_teams);

            stmt.executeUpdate();
        }catch(SQLException e){
            System.err.println("Exeception Occured!!");
            System.out.println(e.getMessage());
            disconnect();
            System.exit(1);
        }

    }

    public static void update(int id,String name,String tts,String tte,int no_of_teams,int hasEnded){

        String query = "UPDATE "+tableName+" SET name=?,tts=?,tte=?,no_of_teams=?,hasEnded=? WHERE id=?";
        System.out.println(query);
        try{
            PreparedStatement stmt = getPreparedStatement(query);

            stmt.setString(1,name);
            stmt.setString(2,tts);
            stmt.setString(3,tte);
            stmt.setInt(4,no_of_teams);
            stmt.setInt(5,hasEnded);
            stmt.setInt(6,id);

            stmt.executeUpdate();
        }catch(SQLException e){
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
                Tournaments  tmp =new Tournaments(rs.getInt("id"),rs.getString("name"),rs.getString("tts"),rs.getString("tte"),
                        rs.getInt("no_of_teams"),rs.getInt("hasEnded")) ;

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
            ResultSet rs = getResultsetFromQuery(query);
            while (rs.next()){
                Tournaments  tmp =new Tournaments(rs.getInt("id"),rs.getString("name"),rs.getString("tts"),rs.getString("tte"),
                        rs.getInt("no_of_teams"),rs.getInt("hasEnded")) ;

                elements.put(tmp.id,tmp);
            }
        } catch (SQLException e) {
            System.err.println("Exeception Occured!!");
            System.out.println(e.getMessage());
            disconnect();
            System.exit(1);
        }
    }


    public static Map<Integer,Tournaments> getElements(){
        return elements;
    }

    public static String getTableName() {
        return tableName;
    }
}
