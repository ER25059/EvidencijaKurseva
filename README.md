# Evidencija kurseva

Java Swing aplikacija za evidenciju kurseva (priprema za završni ispit).

## Funkcionalnosti
- **Dodaj kurs** – dodaje kurs iz forme (kurs sa istim nazivom se ne dodaje)
- **Obriši kurs** – briše odabrani kurs uz potvrdu
- **Top 3 vodeća mentora** – mentori sa najviše kurseva

Podaci se čuvaju u fajlu `Kursevi.txt` (format: `naziv;tezina;oblast;trajanje;mentor`).

## Pokretanje
Projekat je pripremljen za Eclipse i zahteva Java 8 ili noviju verziju.

1. U Eclipse-u izaberite **File → Import → General → Existing Projects into Workspace**.
2. Kao root direktorijum izaberite direktorijum ovog repozitorijuma i završite uvoz.
3. Otvorite `EvidencijaKurseva.java` i izaberite **Run As → Java Application**.

Izvorni fajlovi su u podrazumevanom paketu. `Kursevi.txt` se čita i upisuje u
radnom direktorijumu aplikacije; pri pokretanju iz Eclipse-a to je podrazumevano
direktorijum projekta.
