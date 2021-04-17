package models;

public class Teams {
    public int id;
    public String name, initials, coach_name, captain_name;
    public Tournaments tournament;

    public Teams(int id, String name, String initials, String coach_name, Tournaments tournament, String captain_name) {
        this.id = id;
        this.name = name;
        this.initials = initials;
        this.coach_name = coach_name;
        this.tournament = tournament;
        this.captain_name = captain_name;
    }

    @Override
    public String toString() {
        return "Teams{" +
                "id=>" + id +
                ", captain_id=>" + captain_name +
                ", name=>'" + name + '\'' +
                ", initials=>'" + initials + '\'' +
                ", coach_name=>'" + coach_name + '\'' +
                ", tournament=>" + tournament +
                '}';
    }
}
