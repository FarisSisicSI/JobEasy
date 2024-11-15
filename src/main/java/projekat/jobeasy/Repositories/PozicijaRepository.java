package projekat.jobeasy.Repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projekat.jobeasy.Models.Pozicije;
@Repository
public interface PozicijaRepository extends JpaRepository<Pozicije, Long> {

//    private List<Pozicije> pozicija = new ArrayList<>();
//
//
//    public PozicijaRepository() {
//
//        pozicija.add(new Pozicije(1L,
//                "Back-End Developer (C#)", "Kao Back-End Developer sa fokusom na C#, radit ćete na razvoju i održavanju serverske strane web aplikacija koristeći .NET framework. Vaša uloga će uključivati pisanje čistog, efikasnog i skalabilnog koda, kao i rad na API-ima, integraciji baza podataka, sigurnosti i performansama aplikacija. Poželjno je iskustvo u radu s tehnologijama kao što su ASP.NET, Entity Framework, i SQL Server.",
//                "Iskustvo u radu s .NET frameworkom, ASP.NET Core, i C#.\n" +
//                        "Razumijevanje principa dizajna softvera i objektno-orijentisanog programiranja (OOP).\n" +
//                        "Iskustvo s relacijskim bazama podataka (npr. SQL Server).\n" +
//                        "Poznavanje alata za kontrolu verzija (npr. Git).",
//                "Dizajniranje i implementacija serverske logike u C#.\n" +
//                        "Razvoj i održavanje RESTful API-a za web aplikacije.\n" +
//                        "Rad s bazama podataka, optimizacija upita i održavanje podataka.\n" +
//                        "Osiguravanje visokih performansi i sigurnosti aplikacija.\n" +
//                        "Povezivanje s front-end timom radi integracije aplikativnih funkcionalnosti."));
//        pozicija.add(new Pozicije(2L,
//                "Back-End Developer (Java)", "Kao Java Back-End Developer, bit ćete odgovorni za razvoj, testiranje i održavanje robustnih aplikacija na serverskoj strani koje služe kao okosnica web aplikacija. Uglavnom ćete koristiti Java i Spring framework za razvoj, fokusirajući se na performanse, sigurnost i skalabilnost aplikacija.",
//                "Iskustvo s Java programskim jezikom i Spring frameworkom.\n" +
//                        "Dobro poznavanje principa dizajna, objektnog programiranja i RESTful arhitekture.\n" +
//                        "Iskustvo rada s bazama podataka, SQL ili NoSQL tehnologijama.\n" +
//                        "Razumijevanje rada s alatima za verzionisanje koda, kao što su Git.",
//                "Razvoj serverske logike koristeći Java i Spring (Spring Boot, Spring MVC).\n" +
//                        "Izrada i održavanje RESTful API servisa i integracija s front-end aplikacijama.\n" +
//                        "Rad s relacijskim (npr. MySQL, PostgreSQL) ili NoSQL bazama podataka.\n" +
//                        "Osiguravanje visokog kvaliteta koda i poštivanje najboljih praksi za pisanje koda."));
//        pozicija.add(new Pozicije(3L,
//                "Front-End Developer (React)", "Kao Front-End Developer sa specijalizacijom za React, radit ćete na kreiranju interaktivnih i responzivnih korisničkih interfejsa koji pružaju visoko korisničko iskustvo. Bit ćete odgovorni za implementaciju dizajna u React-u, optimizaciju performansi aplikacija i sarađivat ćete s back-end timom na povezivanju API-a.",
//                "Dobro poznavanje JavaScript-a, posebno ES6+ i React biblioteke.\n" +
//                        "Iskustvo s alatima za upravljanje stanjem u aplikacijama (npr. Redux).\n" +
//                        "Poznavanje HTML5, CSS3, i preprocesora kao što su Sass ili Less.\n" +
//                        "Iskustvo u radu s alatima za verzionisanje koda, kao što su Git.",
//                "Razvoj i održavanje front-end komponenata koristeći React i Redux.\n" +
//                        "Implementacija dizajna koji su prilagođeni korisnicima i responsivni na svim uređajima.\n" +
//                        "Optimizacija performansi aplikacija za brzinu i efikasnost.\n" +
//                        "Saradnja s UX/UI dizajnerima i back-end timom na implementaciji funkcionalnosti."));
//        pozicija.add(new Pozicije(4L,
//                "Full-Stack Developer (JavaScript / Node.js & React)", "Kao Full-Stack Developer s fokusom na JavaScript, radit ćete na svim aspektima razvoja web aplikacija - od serverske do korisničke strane. Vaša odgovornost će biti osiguravanje da aplikacija bude funkcionalna, skalabilna i korisnicima prilagođena, koristeći Node.js za back-end i React za front-end.",
//                "Dobro poznavanje JavaScript-a, Node.js i React-a.\n" +
//                        "Iskustvo u radu s bazama podataka (npr. MongoDB, MySQL).\n" +
//                        "Razumijevanje principa dizajna i razvojnih alata kao što su Git.\n" +
//                        "Sposobnost rada na svim aspektima razvoja aplikacije.",
//                "Razvoj serverske logike u Node.js i izrada RESTful API-a.\n" +
//                        "Razvoj korisničkog interfejsa koristeći React i prilagođavanje dizajna korisnicima.\n" +
//                        "Upravljanje bazama podataka (npr. MongoDB ili MySQL) i optimizacija performansi.\n" +
//                        "Saradnja s UX/UI timom na izradi responzivnog i prilagodljivog korisničkog interfejsa."));
//        pozicija.add(new Pozicije(5L,
//                "DevOps Engineer (AWS)", "Kao DevOps inženjer fokusiran na AWS, radit ćete na implementaciji, automaciji i optimizaciji CI/CD procesa, konfiguraciji infrastrukture i upravljanju skalabilnošću aplikacija. Vaša uloga će biti ključna u osiguravanju neprekidne isporuke softvera i stabilnog okruženja za razvoj i produkciju.",
//                "Iskustvo s AWS-om i radom na DevOps procesima.\n" +
//                        "Znanje skriptnih jezika kao što su Python, Bash ili PowerShell.\n" +
//                        "Razumijevanje CI/CD alata (npr. Jenkins, GitLab CI/CD).\n" +
//                        "Sposobnost upravljanja i održavanja cloud infrastrukture.",
//                "Implementacija CI/CD procesa i održavanje DevOps praksi.\n" +
//                        "Upravljanje AWS resursima (EC2, S3, RDS, Lambda) i optimizacija infrastrukture.\n" +
//                        "Automatizacija zadataka korištenjem skriptnih jezika kao što su Python ili Bash.\n" +
//                        "Osiguravanje sigurnosti i monitoringa aplikacija u produkciji."));
//
//    }
//
//    public List<Pozicije> findAll() {
//        return pozicija;
//    }
//
//    public Optional<Pozicije> findById(Long id) {
//        return pozicija.stream().filter(course -> course.getId().equals(id)).findFirst();
//    }
//
//    public void save(Pozicije course) {
//        pozicija.add(course);
//    }
//
//    public void deleteById(Long id) {
//        pozicija.removeIf(course -> course.getId().equals(id));
//    }
//


}
