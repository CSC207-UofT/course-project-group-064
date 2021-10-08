public class User{
    private String name;
    private int elo;
    public User(String username, int elo){
        this.name = username;
        this.elo = elo;
        }
    public String getName(){
        return this.name;
        }
    public int getElo(){
        return this.elo;
        }

    public void updateElo(int new_elo){
        this.elo = new_elo;
    }
}