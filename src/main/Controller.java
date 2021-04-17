package main;

import models.Matches;
import models.Tournaments;

import java.sql.ResultSet;
import java.sql.SQLException;

import static models.connection.*;
import static models.tournaments.*;

public final class Controller {
    private int tournament_id,match_id;



    private Matches matchDetails;

    private final static Controller INSTANCE = new Controller();

    private Controller(){

    }

    public static Controller getInstance() {
        return INSTANCE;
    }

    public void setTournament_id(int tournament_id) {
        this.tournament_id = tournament_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public int getMatch_id() {
        return match_id;
    }

    public Tournaments getTournament_id() {
        String[] Columns = new String[0];
        StringBuilder query =select(getTableName(),Columns);
        where(query,"id","=",""+this.tournament_id+"");
        try {
            ResultSet rs = getResultsetFromQuery(query.toString());
            Tournaments t = null;
            while(rs.next()){
               t  =new Tournaments(rs.getInt("id"),rs.getString("name"),rs.getString("tts"),rs.getString("tte"),
                        rs.getInt("no_of_teams"),rs.getInt("hasEnded")) ;
            }
            return t;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }


    public Matches getMatchDetails() {
        return matchDetails;
    }

    public void setMatchDetails(Matches matchDetails) {
        this.matchDetails = matchDetails;
    }
}
