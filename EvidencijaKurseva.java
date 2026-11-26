import java.awt.*;
import javax.swing.*;

public class EvidencijaKurseva extends JFrame {

    private UpravljanjeKursevima upravljanje = new UpravljanjeKursevima("Kursevi.txt");

    private JTextField tfNaziv = new JTextField(15);
    private JComboBox<String> cbTezina = new JComboBox<>(new String[]{"osnovni", "srednji", "napredni"});
    private JComboBox<String> cbOblast = new JComboBox<>(new String[]{"informatika", "matematika", "elektrotehnika"});
    private JTextField tfTrajanje = new JTextField(15);
    private JTextField tfMentor = new JTextField(15);

    private DefaultListModel<Kurs> model = new DefaultListModel<>();
    private JList<Kurs> lista = new JList<>(model);

    public EvidencijaKurseva() {
        setTitle("Evidencija kurseva");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2, 10, 10));

        // ----- LIJEVA STRANA -----
        JPanel lijevo = new JPanel(new BorderLayout(5, 10));

        JPanel forma = new JPanel(new GridLayout(5, 2, 5, 5));
        forma.setBorder(BorderFactory.createTitledBorder("Unos kursa"));
        forma.add(new JLabel("Naziv:"));       forma.add(tfNaziv);
        forma.add(new JLabel("Tezina:"));      forma.add(cbTezina);
        forma.add(new JLabel("Oblast:"));      forma.add(cbOblast);
        forma.add(new JLabel("Trajanje (h):")); forma.add(tfTrajanje);
        forma.add(new JLabel("Mentor:"));      forma.add(tfMentor);

        JPanel dugmad = new JPanel(new GridLayout(3, 1, 5, 5));
        JButton btnDodaj = new JButton("Dodaj kurs");
        JButton btnObrisi = new JButton("Obrisi kurs");
        JButton btnTop3 = new JButton("Top 3 vodeca mentora");
        dugmad.add(btnDodaj);
        dugmad.add(btnObrisi);
        dugmad.add(btnTop3);

        lijevo.add(forma, BorderLayout.CENTER);
        lijevo.add(dugmad, BorderLayout.SOUTH);

        // ----- DESNA STRANA -----
        JPanel desno = new JPanel(new BorderLayout());
        desno.setBorder(BorderFactory.createTitledBorder("Lista kurseva"));
        desno.add(new JScrollPane(lista), BorderLayout.CENTER);

        add(lijevo);
        add(desno);

        // ----- AKCIJE -----
        btnDodaj.addActionListener(e -> dodajKurs());
        btnObrisi.addActionListener(e -> obrisiKurs());
        btnTop3.addActionListener(e ->
                JOptionPane.showMessageDialog(this, upravljanje.top3Mentora(), "Top 3 mentora",
                        JOptionPane.INFORMATION_MESSAGE));

        osvjeziListu();
        setSize(750, 350);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void osvjeziListu() {
        model.clear();
        for (Kurs k : upravljanje.getKursevi()) {
            model.addElement(k);
        }
    }

    private void dodajKurs() {
        String naziv = tfNaziv.getText().trim();
        String mentor = tfMentor.getText().trim();

        if (naziv.isEmpty() || mentor.isEmpty() || tfTrajanje.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Popunite sva polja!", "Greska", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int trajanje;
        try {
            trajanje = Integer.parseInt(tfTrajanje.getText().trim());
            if (trajanje <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Trajanje mora biti pozitivan cijeli broj!", "Greska",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Kurs k = new Kurs(naziv, (String) cbTezina.getSelectedItem(),
                (String) cbOblast.getSelectedItem(), trajanje, mentor);

        if (upravljanje.dodaj(k)) {
            osvjeziListu();
            tfNaziv.setText("");
            tfTrajanje.setText("");
            tfMentor.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Kurs sa nazivom \"" + naziv + "\" vec postoji!",
                    "Obavjestenje", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void obrisiKurs() {
        Kurs odabran = lista.getSelectedValue();
        if (odabran == null) {
            JOptionPane.showMessageDialog(this, "Odaberite kurs iz liste!", "Obavjestenje",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int odgovor = JOptionPane.showConfirmDialog(this,
                "Da li ste sigurni da zelite obrisati kurs \"" + odabran.getNaziv() + "\"?",
                "Potvrda brisanja", JOptionPane.YES_NO_OPTION);
        if (odgovor == JOptionPane.YES_OPTION) {
            upravljanje.obrisi(odabran);
            osvjeziListu();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EvidencijaKurseva());
    }
}
