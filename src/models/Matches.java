package models;

public class Matches {
    public int id;
    public Tournaments tournament;
    public Teams t1, t2, t3_id;
    public boolean hasStarted, hasEnded, isDraw;
    public int t1_goals, t2_goals;
    public Teams won_team;
    public String created_at;

    public Matches(int id,
                   Tournaments tournament,
                   Teams team_1, Teams team_2,
                   int hasStarted, int hasEnded,
                   Teams won_team,
                   int isDraw,
                   int team_1_goals, int team_2_goals,
                   String created_at) {

        this.id = id;
        this.tournament = tournament;
        this.t1 = team_1;
        this.t2 = team_2;
        this.hasStarted = hasStarted != 0;
        this.hasEnded = hasEnded != 0;
        this.isDraw = isDraw != 0;
        this.won_team = won_team;
        this.t1_goals = team_1_goals;
        this.t2_goals = team_2_goals;
        this.created_at = created_at;

    }

    @Override
    public String toString() {
        return "Matches{" +
                "id=" + id +
                ", tournament=" + tournament +
                ", t1=" + t1 +
                ", t2=" + t2 +
                ", t3_id=" + t3_id +
                ", hasStarted=" + hasStarted +
                ", hasEnded=" + hasEnded +
                ", isDraw=" + isDraw +
                ", t1_goals=" + t1_goals +
                ", t2_goals=" + t2_goals +
                ", won_team=" + won_team +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
