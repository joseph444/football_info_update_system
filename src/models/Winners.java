package models;

public class Winners {
    public int id;
    public Tournaments tournament;
    public Teams winner_team, lost_team;

    public Winners(int id, Tournaments tournament, Teams winner_team, Teams lost_team) {
        this.id = id;
        this.tournament = tournament;
        this.winner_team = winner_team;
        this.lost_team = lost_team;
    }

    @Override
    public String toString() {
        return "Winners{" +
                "id=" + id +
                ", tournament=" + tournament +
                ", winner_team=" + winner_team +
                ", lost_team=" + lost_team +
                '}';
    }
}
