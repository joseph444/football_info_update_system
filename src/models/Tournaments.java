package models;

public class Tournaments {
    public int id, no_of_teams;

    public int getId() {
        return id;
    }

    public int getNo_of_teams() {
        return no_of_teams;
    }

    public String getName() {
        return name;
    }

    public String getTts() {
        return tts;
    }

    public String getTte() {
        return tte;
    }

    public boolean isHasEnded() {
        return hasEnded;
    }

    public String name, tts, tte;
    public boolean hasEnded;

    public Tournaments(int id, String name, String tts, String tte, int no_of_teams, int hasEnded) {
        this.id = id;
        this.name = name;
        this.tts = tts;
        this.tte = tte;
        this.no_of_teams = no_of_teams;
        this.hasEnded = hasEnded != 0;
    }


    @Override
    public String toString() {
        return
                "{id=" + id +
                        ", no_of_teams=" + no_of_teams +
                        ", name='" + name + '\'' +
                        ", tts='" + tts + '\'' +
                        ", tte='" + tte + '\'' +
                        ", hasEnded=" + hasEnded + "}"
                ;
    }


}
