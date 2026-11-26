/**
 * Klasa koja predstavlja jedan kurs.
 * Polja su privatna (enkapsulacija), a pristupa im se preko getter metoda.
 */
public class Kurs {

    private String naziv;
    private String tezina;   // osnovni / srednji / napredni
    private String oblast;   // informatika / matematika / elektrotehnika
    private int trajanje;    // u satima
    private String mentor;

    public Kurs(String naziv, String tezina, String oblast, int trajanje, String mentor) {
        this.naziv = naziv;
        this.tezina = tezina;
        this.oblast = oblast;
        this.trajanje = trajanje;
        this.mentor = mentor;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getTezina() {
        return tezina;
    }

    public String getOblast() {
        return oblast;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public String getMentor() {
        return mentor;
    }

    /**
     * Pretvara kurs u jedan red za upis u fajl.
     * Primjer: Web programiranje;srednji;informatika;40;Ivan Ilic
     */
    public String zaFajl() {
        return naziv + ";" + tezina + ";" + oblast + ";" + trajanje + ";" + mentor;
    }

    /**
     * Pravi objekat Kurs od jednog reda iz fajla.
     */
    public static Kurs izReda(String red) {
        String[] dijelovi = red.split(";");
        if (dijelovi.length != 5) {
            return null; // neispravan red se preskace
        }
        try {
            int trajanje = Integer.parseInt(dijelovi[3].trim());
            return new Kurs(dijelovi[0].trim(), dijelovi[1].trim(), dijelovi[2].trim(),
                    trajanje, dijelovi[4].trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Ovako se kurs prikazuje u JList-i.
     * Primjer: Web programiranje (srednji, informatika, 40h, Ivan Ilic)
     */
    @Override
    public String toString() {
        return naziv + " (" + tezina + ", " + oblast + ", " + trajanje + "h, " + mentor + ")";
    }
}
