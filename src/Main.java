import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        String[][] tab = new String[10][5];
        Noeud[][] noeuds = new Noeud[10][5];

        tab[0] = new String[]{"1", "1", "1", "1", "1"};
        tab[1] = new String[]{"1", "3", "0", "0", "1"};
        tab[2] = new String[]{"1", "0", "1", "0", "1"};
        tab[3] = new String[]{"1", "2", "1", "0", "1"};
        tab[4] = new String[]{"1", "0", "1", "0", "1"};
        tab[5] = new String[]{"1", "0", "1", "0", "1"};
        tab[6] = new String[]{"1", "0", "1", "0", "1"};
        tab[7] = new String[]{"1", "0", "1", "0", "1"};
        tab[8] = new String[]{"1", "0", "0", "0", "1"};
        tab[9] = new String[]{"1", "1", "1", "1", "1"};

        String status;


        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                if (Integer.parseInt(tab[i][j]) == 2)
                    status = "d";
                else if (Integer.parseInt(tab[i][j]) == 3)
                    status = "f";
                else if (Integer.parseInt(tab[i][j]) == 0)
                    status = "o";
                else
                    status = "n";

                noeuds[i][j] = new Noeud(i, j, status);
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(tab[i][j]);
            }
            System.out.println();
        }
        System.out.println();

        Noeud depart = null;
        Noeud fin = null;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                if (noeuds[i][j].statut.equals("d")) {
                    System.out.println("depart a bien ete ajoute");
                    depart = noeuds[i][j];
                    depart.f = 0;
                } else if (noeuds[i][j].statut.equals("f")) {
                    System.out.println("fin a bien ete ajoute");
                    fin = noeuds[i][j];
                }
            }
        }

        ArrayList<Noeud> open = new ArrayList<>();
        ArrayList<Noeud> closed = new ArrayList<>();
        ArrayList<Noeud> neighbors;
        ArrayList<Noeud> chemin = new ArrayList<>();

        boolean verif;

        open.add(depart);

//        depart.parent = new Noeud(-5, -5, "m");
//        fin.parent = new Noeud(-10, -10, "p");

        int n = 0;
        boolean cheminEstTrouve = false;

        while (!cheminEstTrouve) {

            if(open.isEmpty()) {
                System.out.println("Pas de chemin trouve ... desole");
                break;
            }
            System.out.println("Il y a " + open.size() + " noeuds dans open, on est au tour " + n);
            n++;

            Noeud current = lowestF(open);
            verif = open.remove(current);
            System.out.println("current a bien ete enleve ?" + verif);

            System.out.println("current x/y " + current.x + "/" + current.y);
            neighbors = addNeighbors(current, noeuds);

            for(Noeud noeud : neighbors){
                System.out.println("neighbor x/y " + noeud.x + "/" + noeud.y + "  //  " + noeud.statut);
                if(noeud == fin){
                    System.out.println("ajout du neighbors : " + noeud.x + "/" + noeud.y);
                    noeud.parent = current;
                    noeud.statut = "f";
//                    closed.add(noeud);
                    chemin.add(noeud);

                    System.out.println("FIN");
                    cheminEstTrouve = true;
                    System.out.println("cheminEstTrouve" + cheminEstTrouve);
                    break;
                }
                if(!closed.contains(noeud)){
                    noeud.parent = current;
                    noeud.g = current.g + 1;
                    noeud.h = manhattan(noeud.x, fin.x, noeud.y, fin.y);
                    noeud.f = noeud.g + noeud.h;
                    System.out.println("Noeud " + noeud.id + " g="+noeud.g+", h="+noeud.h+", f="+noeud.f+", x="+noeud.x+", y="+noeud.y);
                    open.add(noeud);
                }

//                if(open.contains(noeud)){
//
//                }
//                if(closed.contains(noeud)){
//
//                }

            }
            closed.add(current);
        }

        for(int i = 0; i < closed.size(); i++)
        {
//            System.out.println(closed.get(i).x + "/" + closed.get(i).y + " parent : " + closed.get(i).parent.x + "/" + closed.get(i).parent.y);
        }


        for(int i = 0; i < closed.size(); i++){
            System.out.println("linear search " + i);
            chemin.add(linearSearch(closed, chemin.get(i)));
            if(chemin.get(i+1).statut.equals("d"))
            {
                System.out.println("Chemin trouve");
                break;
            }
            if(chemin.get(i+1) == null) {
                System.out.println("Erreur pas de parent trouve");
                break;
            }
        }


        for(int i = 0; i < chemin.size(); i++){
            System.out.println(chemin.get(i).x + " / " + chemin.get(i).y);
        }



    }

    public static Noeud linearSearch(ArrayList<Noeud> tab, Noeud noeud){
        for(int i = 0; i < tab.size(); i++){
            if(tab.get(i).equals(noeud.parent))
            {
                tab.remove(noeud.parent);
                return noeud.parent;
            }
        }
        return null;
    }

    public static int manhattan(int a, int b, int c, int d){
        a -= b;
        if(a < 0)
            a *= -1;
        c -= d;
        if(c < 0)
            c *= -1;
        return a+c;
    }


    public static Noeud lowestF(ArrayList<Noeud> list){
        Noeud toReturn = null;
        for (Noeud noeud : list) {
            if (toReturn == null || toReturn.f > noeud.f) {
                toReturn = noeud;
            }
        }
        return toReturn;
    }

    public static ArrayList<Noeud> addNeighbors(Noeud noeud, Noeud[][] tab){
        ArrayList<Noeud> toReturn = new ArrayList<>();
        if(tab[noeud.x+1][noeud.y].statut.equals("o") || tab[noeud.x+1][noeud.y].statut.equals("d") || tab[noeud.x+1][noeud.y].statut.equals("f")){
            toReturn.add(tab[noeud.x+1][noeud.y]);
        }
        if(tab[noeud.x][noeud.y+1].statut.equals("o") || tab[noeud.x][noeud.y+1].statut.equals("d") || tab[noeud.x][noeud.y+1].statut.equals("f")){
            toReturn.add(tab[noeud.x][noeud.y+1]);
        }
        if(tab[noeud.x-1][noeud.y].statut.equals("o") || tab[noeud.x-1][noeud.y].statut.equals("d") || tab[noeud.x-1][noeud.y].statut.equals("f")){
            toReturn.add(tab[noeud.x-1][noeud.y]);
        }
        if(tab[noeud.x][noeud.y-1].statut.equals("o") || tab[noeud.x][noeud.y-1].statut.equals("d") || tab[noeud.x][noeud.y-1].statut.equals("f")){
            toReturn.add(tab[noeud.x][noeud.y-1]);
        }
        System.out.println("Nombre de voisins : " + toReturn.size());
        return toReturn;
    }


//    public void aStar(int x1, int y1, int x2, int y2){
//        System.out.println("x1:" +x1+"x2:"+x2+"y1:"+y1+"y2:"+y2);
//        Noeud start = new Noeud(x1, y1);
//        start.setG(0);
//        start.setF(0);
//        Noeud goal = new Noeud(x2, y2);
//        ArrayList<Noeud> open = new ArrayList<>();
//        ArrayList<Noeud> closed = new ArrayList<>();
//        ArrayList<Noeud> neighbors;
//        open.add(start);
//
//        int n = 0;
//        while(!open.isEmpty()){
//            System.out.println("nombre d'itération:"+n);
//            n++;
//            Noeud current = lowestF(open);
//            System.out.println("noeud courant :" + current.getX() + "/" + current.getY());
//            open.remove(current);
//            closed.add(current);
//            if(current.getX() == goal.getX() && current.getY() == goal.getY()){
//                break;
//            }
//            neighbors = addNeighbors(current);
//            for(Noeud noeud : neighbors){
//
//                int h = manhattan(noeud.getX(), goal.getX(), noeud.getY(), goal.getY());
//                System.out.println("h : " + h);
//                int g_tmp = current.getG() + 1;
//                System.out.println("g_tmp : " + g_tmp);
//                int f_tmp = g_tmp + h;
//                System.out.println("f_tmp : " + f_tmp);
//                if(open.contains(noeud) && noeud.getF() < f_tmp){
//                    System.out.println("remove open");
//                    open.remove(noeud);
//                }
//                if(closed.contains(noeud) && noeud.getF() < f_tmp){
//                    System.out.println("remove closed");
//                    closed.remove(noeud);
//                }
//                if ((!open.contains(noeud) && !closed.contains(noeud)) || noeud.getF() >= f_tmp) {
//                    System.out.println("add");
//                    noeud.setParent(current);
//                    noeud.setG(g_tmp);
//                    noeud.setF(f_tmp);
//                    open.add(noeud);
//                }
//            }
//        }
//        for(int i =0 ; i < closed.size(); i++){
//            System.out.println(closed.size());
//            System.out.println("num" + i + ":" + closed.get(i).getX() + "/" + closed.get(i).getY());
//        }
//        return convertNoeudDir(closed);
//    }







}



