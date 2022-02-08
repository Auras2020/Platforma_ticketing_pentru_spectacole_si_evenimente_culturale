package com.example.platformaticketing;

import com.example.platformaticketing.model.*;
import com.example.platformaticketing.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PlatformaTicketingApplication {

    public static void main(String[] args) {

        SpringApplication.run(PlatformaTicketingApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(AngajatRepository angajatRepository, ClientRepository clientRepository,
                                 EvenimentCulturalRepository evenimentCulturalRepository, FilmRepository filmRepository,
                                 SpectacolRepository spectacolRepository, VanzatorRepository vanzatorRepository){
        return (args -> {
            /*int oraStart = 0;
            for(Vanzator v: vanzatorRepository.findAll()){
                if(v.getInterval_orar().length()==4){
                    oraStart = Integer.parseInt(v.getInterval_orar().substring(0, 1));
                }
                else{
                    oraStart = Integer.parseInt(v.getInterval_orar().substring(0, 2));
                }
                System.out.println(oraStart);
            }*/
            if(angajatRepository.count()==0) {
                insertAngajat(angajatRepository);
                insertClients(clientRepository);
                insertEvenimentCultural(evenimentCulturalRepository);
                insertFilm(filmRepository);
                insertSpectacol(spectacolRepository);
                insertVanzator(vanzatorRepository);
            }
            //System.out.println(clientRepository.findAll());
        });
    }

    public void insertAngajat(AngajatRepository angajatRepository){
        angajatRepository.save(new Angajat("Paul", "12-20", 1));
        angajatRepository.save(new Angajat("Mircea", "8-12", 2));
        angajatRepository.save(new Angajat("Marc", "20-24", 3));
       /* angajatRepository.save(new Angajat("Sandu", "8-12", 4));
        angajatRepository.save(new Angajat("Andrei", "12-20", 5));
        angajatRepository.save(new Angajat("Ana", "12-20", 6));
        angajatRepository.save(new Angajat("Mircea", "20-24", 7));
        angajatRepository.save(new Angajat("Dan", "8-12", 8));
        angajatRepository.save(new Angajat("Gigel", "20-24", 9));*/
    }

    private void insertClients(ClientRepository clientRepository){
        clientRepository.save(new Client("Andrei", 21, "DA"));
        clientRepository.save(new Client("Ana", 17, "NU"));
        clientRepository.save(new Client("Ana", 14, "DA"));
        clientRepository.save(new Client("Matei", 12, "NU"));
        clientRepository.save(new Client("Marius", 8, "DA"));
        clientRepository.save(new Client("Darius", 19, "DA"));
        clientRepository.save(new Client("Raul", 27, "DA"));
        clientRepository.save(new Client("Catalin", 13, "DA"));
        clientRepository.save(new Client("Vasile", 33, "DA"));
        clientRepository.save(new Client("Bogdan", 65, "DA"));
    }

    private void insertEvenimentCultural(EvenimentCulturalRepository evenimentCulturalRepository){
        evenimentCulturalRepository.save(new EvenimentCultural("Expozitia de Arte", "premiera", "11:15 13-05-2020", 40, 15));
        evenimentCulturalRepository.save(new EvenimentCultural("Expozitia de Costume", "premiera", "20:30 12-03-2021", 28, 25));
        evenimentCulturalRepository.save(new EvenimentCultural("Festivalul Culorilor", "avanpremiera", "16:45 12-09-2021", 34, 20));
        evenimentCulturalRepository.save(new EvenimentCultural("Medieval", "premiera", "23:15 21-09-2020", 23, 12));
        evenimentCulturalRepository.save(new EvenimentCultural("Targul de Carte", "simplu", "14:15 03-07-2021", 50, 5));
    }

    private void insertFilm(FilmRepository filmRepository){
        filmRepository.save(new Film("Aftermath", "horror", 15, "simplu", "12:15 06-04-2020 Mi", 34, 18, "Peter Winther",
                "Ashley Greene, Shawn Ashmo, Jason Liles"));
        filmRepository.save(new Film("Amar", "drama", 12, "premiera", "22:30 12-10-2021 V", 56, 25, "Pol Monta",
                "Maria Pedraza, Sonia Almarcha, Greta Fernand"));
        filmRepository.save(new Film("Avengers", "actiune", 12, "avanpremiera", "20:30 12-03-2020 L", 50, 26,
                "Joss Whedon", "Scarlett Johanson, Robert Downey, Chris Evans"));
        filmRepository.save(new Film("Baghul", "horror", 21, "premiera", "14:55 04-08-2021 J", 75, 25, "Scott Derickson",
                "Ethan Hawke, James Ranson, Clare Foley"));
        filmRepository.save(new Film("Infinite", "actiune", 18, "simplu", "11:12 13-03-2021 S", 21, 22, "Antoine Fuqua",
                "Mark Walhberg, Chris Evans, Dylan O'Brien"));
        filmRepository.save(new Film("King", "drama", 15, "avanpremiera", "16:45 21-07-2020 S", 87, 28, "David Michord",
                "Robert Patinson, Joel Edgerton"));
        filmRepository.save(new Film("Liar", "comedie", 12, "premiera", "20:45 14-12-2021 D", 34, 26, "James Strong",
                "Ioan Gruffudd, Shelley Conn"));
        filmRepository.save(new Film("Nun", "horror", 18, "avanpremiera", "23:15 21-09-2021 V", 78, 30, "Corin Hardy",
                "Taisa Framiga, Vera Farmiga, Patrick Wilson"));
        filmRepository.save(new Film("Spiderman", "comedie", 12, "premiera", "10:15 23-12-2020 Ma", 35, 22, "John Wats",
                "Jamie Fox, Benedict Cumdum, Tom Holland"));
        filmRepository.save(new Film("Terminator", "actiune", 15, "simplu", "21:15 23-07-2021 Mi", 67, 20, "Tim Miller",
                "Arnold Schwarzeneger, Linda Hamilton"));
    }

    public void insertSpectacol(SpectacolRepository spectacolRepository){
        spectacolRepository.save(new Spectacol("Aievea", "populara", "09-12-2021", "Bucuresti", "Andra", "20-22"));
        spectacolRepository.save(new Spectacol("Concert Arenele Romane", "electro", "12-05-2021", "Brasov", "Kraftwerk", "19-20"));
        spectacolRepository.save(new Spectacol("Crazy Rock", "rap", "21-03-2021", "Cluj-Napoca", "Confessions", "12-18"));
        spectacolRepository.save(new Spectacol("Emerald Island", "pop", "09-08-2020", "Sibiu", "Caro Emerald", "21-24"));
        spectacolRepository.save(new Spectacol("Foreigner", "rock", "06-12-2021", "Iasi", "Bone Man", "16-19"));
    }

    public void insertVanzator(VanzatorRepository vanzatorRepository){
        vanzatorRepository.save(new Vanzator("George", "8-12", 1));
        vanzatorRepository.save(new Vanzator("Matei", "12-20", 2));
        vanzatorRepository.save(new Vanzator("Nan", "8-12", 3));
        vanzatorRepository.save(new Vanzator("Horia", "20-24", 4));
        vanzatorRepository.save(new Vanzator("Gigel", "12-20", 5));
        vanzatorRepository.save(new Vanzator("Andrei", "8-12", 6));
        vanzatorRepository.save(new Vanzator("Andrei", "20-24", 7));
        vanzatorRepository.save(new Vanzator("Bogdan", "20-24", 8));
        vanzatorRepository.save(new Vanzator("Bogdan", "12-20", 9));
    }

}
