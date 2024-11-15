package projekat.jobeasy.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Pozicije {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nazivPozicije;

    @Lob
    private String opisPozicije;

    @Lob
    private String kvalifikacije;

    @Lob
    private String odgovornosti;

    @ManyToMany(mappedBy = "upisanePozicije")
    private List<Prijava> prijava = new ArrayList<>();


    // Constructors
    public Pozicije() {}
    public Pozicije(Long id, String nazivPozicije, String opisPozicije, String kvalifikacije, String odgovornosti) {
        this.id = id;
        this.nazivPozicije = nazivPozicije;
        this.opisPozicije = opisPozicije;
        this.kvalifikacije = kvalifikacije;
        this.odgovornosti = odgovornosti;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNazivPozicije() { return nazivPozicije; }
    public void setNazivPozicije(String nazivPozicije) { this.nazivPozicije = nazivPozicije; }

    public String getOpisPozicije() { return opisPozicije; }
    public void setOpisPozicije(String opisPozicije) { this.opisPozicije = opisPozicije; }

    public String getKvalifikacije() { return kvalifikacije; }
    public void setKvalifikacije(String kvalifikacije) { this.kvalifikacije = kvalifikacije; }

    public String getOdgovornosti() { return odgovornosti; }
    public void setOdgovornosti(String odgovornosti) { this.odgovornosti = odgovornosti; }

    public List<Prijava> getStudents() { return prijava; }
    public void setStudents(List<Prijava> students) { this.prijava = students; }
}
