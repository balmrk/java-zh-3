package Program;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class jaror {
    ArrayList<Bejegyzesek> bejegyzesek = new ArrayList<Bejegyzesek>();
    public jaror() throws IOException {
        System.out.println("1. Feladat:");
        Beolvas();
        System.out.println("2. Feladat:");
        ledolgozottOrak();
        System.out.println("3. Feladat:");
        muszakiEllenorzesek();
        System.out.println("4. Feladat:");
        jarmuKategoriak();
        System.out.println("5. Feladat:");
        alacsonyForgalom();
        System.out.println("6. Feladat:");
        keresettRendszam();
        System.out.println("7. Feladat:");
        ellenorzottAutok();
    }

    public void ellenorzottAutok() throws IOException {
        Bejegyzesek ell = new Bejegyzesek(0,0,0,"");
        ArrayList<Bejegyzesek> ellenorzottek = new ArrayList<Bejegyzesek>();
        int ujido = 0, regiido=0;
        for(Bejegyzesek a:bejegyzesek){
            ujido = (a.ora*3600)+(a.perc*60)+a.masodperc;
            if (ujido > regiido){
                ellenorzottek.add(a);
                regiido = ujido+300;
            }
        }
        FileWriter iro = new FileWriter("vizsgalt.txt");
        for (Bejegyzesek a:ellenorzottek){
            //System.out.println(a.ora+":"+a.perc+":"+a.masodperc+" - "+a.rendszam);
            iro.write(a.ora+" "+a.perc+" "+a.masodperc+" "+a.rendszam);
        }
    }
    public void keresettRendszam(){
        Scanner a = new Scanner(System.in);
        System.out.println("Mit látott a rendszámból?");
        String darab = a.nextLine().toUpperCase(Locale.ROOT);
        for (Bejegyzesek b : bejegyzesek){
            if (b.rendszam.contains(darab)){
                System.out.println(b.rendszam);
            }
        }
    }
    public void alacsonyForgalom(){
        int ido=0, eido=0,maxido=0;
        int[] tartomany = new int[6];
        Bejegyzesek elozo = new Bejegyzesek(0,0,0,"");
        for (Bejegyzesek a:bejegyzesek){
            ido = a.masodperc + a.perc*60 + a.ora *3600;
            if(maxido < (ido-eido) && eido!=0){
                maxido = ido-eido;
                tartomany[0]=elozo.ora;
                tartomany[1]=elozo.perc;
                tartomany[2]=elozo.masodperc;
                tartomany[3]=a.ora;
                tartomany[4]=a.perc;
                tartomany[5]=a.masodperc;
            }
            eido = ido;
            elozo = a;
        }
        System.out.println(tartomany[0] + ":" +tartomany[1] + ":" +tartomany[2] + " - " +tartomany[3] + ":" +tartomany[4] + ":" +tartomany[5]);
        System.out.println("max: "+maxido);
    }
    public void jarmuKategoriak(){
        int[] db = new int[4];
        for(Bejegyzesek a: bejegyzesek){
            String[] betuk = a.rendszam.split("");
            if (betuk[0].equals("B")){
                db[0]++;
            } else if (betuk[0].equals("K")) {
                db[1]++;
            }else if (betuk[0].equals("M")){
                db[2]++;
            }else {
                db[3]++;
            }
        }
        System.out.println(db[0] + " autóbusz, " + db[1] + " kamion, " + db[2] + " motor, " + db[3] + " személyautó jelent meg.");
    }
    public void muszakiEllenorzesek(){
        int ora = 0;
        for (Bejegyzesek a : bejegyzesek){
            if (a.ora>ora){
                System.out.println(a.ora+" óra: "+a.rendszam);
                ora = a.ora;
            }
        }
    }
    public void ledolgozottOrak(){
        int min = bejegyzesek.get(0).ora;
        int max = bejegyzesek.get(bejegyzesek.size()-1).ora;
        int munkaorak = (max+1) - min;
        System.out.println("A rend éber őrei ezen a napon " + munkaorak + " órát dolgoztak vért izzadva.");
    }
    public void Beolvas() throws FileNotFoundException {
        Scanner scan = new Scanner(new File("jarmu.txt"));
        while (scan.hasNextLine()) {
            String sor = scan.nextLine();
            String[] oszlopok = sor.split(" ");
            bejegyzesek.add(new Bejegyzesek(Integer.parseInt(oszlopok[0]), Integer.parseInt(oszlopok[1]), Integer.parseInt(oszlopok[2]), oszlopok[3]));
        }
    }
    public static void main(String[] args) throws IOException {
        jaror Jaror = new jaror();
    }
}
