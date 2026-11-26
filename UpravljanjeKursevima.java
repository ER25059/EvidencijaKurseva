import java.io.*;
import java.util.*;

public class UpravljanjeKursevima {

    private ArrayList<Kurs> kursevi = new ArrayList<>();
    private String nazivFajla;

    public UpravljanjeKursevima(String nazivFajla) {
        this.nazivFajla = nazivFajla;
        ucitaj();
    }

    public ArrayList<Kurs> getKursevi() {
        return kursevi;
    }

    // Ucitava kurseve iz fajla
    public void ucitaj() {
        kursevi.clear();
        File f = new File(nazivFajla);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String red;
            while ((red = br.readLine()) != null) {
                Kurs k = Kurs.izReda(red);
                if (k != null) kursevi.add(k);
            }
        } catch (IOException e) {
            System.out.println("Greska pri citanju: " + e.getMessage());
        }
    }

    // Upisuje sve kurseve u fajl
    public void sacuvaj() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nazivFajla))) {
            for (Kurs k : kursevi) {
                pw.println(k.zaFajl());
            }
        } catch (IOException e) {
            System.out.println("Greska pri upisu: " + e.getMessage());
        }
    }

    public boolean postoji(String naziv) {
        for (Kurs k : kursevi) {
            if (k.getNaziv().equalsIgnoreCase(naziv)) return true;
        }
        return false;
    }

    // Vraca false ako kurs sa istim nazivom vec postoji
    public boolean dodaj(Kurs k) {
        if (postoji(k.getNaziv())) return false;
        kursevi.add(k);
        sacuvaj();
        return true;
    }

    public void obrisi(Kurs k) {
        kursevi.remove(k);
        sacuvaj();
    }

    // Tri mentora sa najvise kurseva
    public String top3Mentora() {
        HashMap<String, Integer> brojac = new HashMap<>();
        for (Kurs k : kursevi) {
            String m = k.getMentor();
            if (brojac.containsKey(m)) {
                brojac.put(m, brojac.get(m) + 1);
            } else {
                brojac.put(m, 1);
            }
        }

        ArrayList<String> mentori = new ArrayList<>(brojac.keySet());
        mentori.sort((a, b) -> brojac.get(b) - brojac.get(a));

        String rezultat = "";
        for (int i = 0; i < mentori.size() && i < 3; i++) {
            String m = mentori.get(i);
            rezultat += (i + 1) + ". " + m + " - " + brojac.get(m) + " kurs(a)\n";
        }
        if (rezultat.isEmpty()) rezultat = "Nema unesenih kurseva.";
        return rezultat;
    }
}
