package projekat.jobeasy.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pozicije {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    private String nazivPozicije;

    @Lob
    @NotNull
    @Size(min = 1)
    private String kratakOpisPozicije;

    @ManyToOne
    @JoinColumn(name = "idFirme", nullable = false)
    private Firma firma;

    @ManyToOne
    @JoinColumn(name = "idZanimanje", nullable = false)
    private Zanimanje zanimanje;

    @NotNull
    private LocalDate datumOd = LocalDate.now();

    @NotNull
    private LocalDate datumDo = LocalDate.now().plusMonths(1);

    @OneToMany(mappedBy = "pozicija", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prijava> prijave;


    // Constructors
    public Pozicije() {}

    public Pozicije(String nazivPozicije, String kratakOpisPozicije, Firma firma, Zanimanje zanimanje, LocalDate datumOd, LocalDate datumDo) {
        this.nazivPozicije = nazivPozicije;
        this.kratakOpisPozicije = kratakOpisPozicije;
        this.firma = firma;
        this.zanimanje = zanimanje;
        this.datumOd = datumOd;
        this.datumDo = datumDo;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNazivPozicije() { return nazivPozicije; }
    public void setNazivPozicije(String nazivPozicije) { this.nazivPozicije = nazivPozicije; }

    public String getKratakOpisPozicije() { return kratakOpisPozicije; }
    public void setKratakOpisPozicije(String kratakOpisPozicije) { this.kratakOpisPozicije = kratakOpisPozicije; }

    public Firma getFirma() { return firma; }
    public void setFirma(Firma firma) { this.firma = firma; }

    public Zanimanje getZanimanje() { return zanimanje; }
    public void setZanimanje(Zanimanje zanimanje) { this.zanimanje = zanimanje; }

    public LocalDate getDatumOd() { return datumOd; }
    public void setDatumOd(LocalDate datumOd) { this.datumOd = datumOd; }

    public LocalDate getDatumDo() { return datumDo; }
    public void setDatumDo(LocalDate datumDo) { this.datumDo = datumDo; }

    public List<Prijava> getPrijave() { return prijave; }
    public void setPrijave(List<Prijava> prijave) { this.prijave = prijave; }
}
