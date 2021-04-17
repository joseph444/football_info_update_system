package models;
import java.sql.*;

public class connection {
    private static Connection conn = null;
    public static void connect() {


        String url = "jdbc:sqlite:database.db";
        try {
            // db parameters
            Class.forName("org.sqlite.JDBC");

            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException | ClassNotFoundException e) {

            System.out.println(e.getMessage());
        }
    }

    public static void disconnect(){
        try{
            if(!conn.isClosed()){
                conn.close();
                System.out.println("Disconnected");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return conn;
    }

    public static PreparedStatement getPreparedStatement(String query) throws SQLException {
        return  conn.prepareStatement(query);
    }

    public static ResultSet getResultsetFromQuery(String query) throws SQLException {
        return conn.createStatement().executeQuery(query);
    }

    public static StringBuilder select(String tableName,String[] Columns){

        StringBuilder Query ;
        if (Columns.length==0){
            Query = new StringBuilder("SELECT * FROM " + tableName);
        }else{
            int i=0;
            Query = new StringBuilder("SELECT ");
            for(String cols: Columns){
                if(i==0){
                    Query.append(cols);
                }else{
                    Query.append(", ").append(cols);
                }
                i++;
            }

            Query.append("FROM ").append(tableName);
        }

        return Query;
    }

    public static void where(StringBuilder query, String Column,String operation,String Operand){
        boolean whereFlag = query.indexOf("WHERE")==-1;
        if(whereFlag){
            query.append(" WHERE ").append(Column).append(" ").append(operation).append(" ").append(Operand);
        }else{
            query.append("AND WHERE ").append(Column).append(" ").append(operation).append(" ").append(Operand);
        }
        System.out.println(query.toString());
    }
    public static void orWhere(StringBuilder query, String Column,String operation,String Operand){
        boolean whereFlag = query.indexOf("where")==-1;
        if(!whereFlag){
            query.append("WHERE ").append(Column).append(" ").append(operation).append(" ").append(Operand);
        }else{
            query.append("OR WHERE ").append(Column).append(" ").append(operation).append(" ").append(Operand);
        }
    }

    public static void orderBy(StringBuilder query, String Column,String order){
        query.append("ORDER BY ").append(Column).append(" ").append(order);
    }

    public static long count(String tableName) {
        String[] Columns = new String[1];
        Columns[0] = "count(*)";
        StringBuilder query = select(tableName, Columns);
        long count = 0;

        try {
            ResultSet rs = getResultsetFromQuery(query.toString());
            while (rs.next()) {
                count = rs.getInt("count(*)");
            }
            return count;
        } catch (SQLException e) {
            System.err.println("Exeception Occured!!");
            System.out.println(e.getMessage());
            disconnect();
            System.exit(1);
            return -1;
        }

    }

    public static void delete(String tableName,int id) throws SQLException {
        String Query = "DELETE FROM "+tableName+" WHERE id =?";

        PreparedStatement stmt = getPreparedStatement(Query);

        stmt.setInt(1,id);

        stmt.executeUpdate();
    }

    public static void test()  {
        String tableName = "test";
        String name = "attack on titan";
        int time = (int) System.currentTimeMillis();

        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "insert into "+tableName+" (name,time) values(?,?)"
            );
            stmt.setString(1,name);
            //sets the value at index 2
            stmt.setInt(2,time);

            //this updates
            stmt.executeUpdate();

            Statement stm = conn.createStatement();
            ResultSet rs =stm.executeQuery("select * from "+tableName);

            while (rs.next()){
                System.out.println(rs.getInt("id")+" "+rs.getString("name"));
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
