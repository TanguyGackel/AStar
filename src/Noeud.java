public class Noeud {
    int x;
    int y;
    int id;
    static int nb = 0;
    int g;
    int h;
    int f;
    String statut;
    Noeud parent;

    public Noeud(int x, int y, String statut) {
        this.x = x;
        this.y = y;
        this.id = nb;
        nb++;
        this.statut = statut;
    }
}
