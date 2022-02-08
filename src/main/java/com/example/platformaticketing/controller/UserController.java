package com.example.platformaticketing.controller;

import com.example.platformaticketing.model.*;
import com.example.platformaticketing.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class UserController {

    private String nume;
    private String id;
    private String nume1;
    private String id1;

    private boolean okNume = false;
    private boolean okId = false;
    //private int ok = 0;
    private Client client;
    private Film film;
    private EvenimentCultural evenimentCultural;
    private Spectacol spectacol;
    private int f1 = 0;
    private int e1 = 0;
    private int s1 = 0;

    private int ocupateFilm = 0;
    private int ocupateEveniment = 0;

    private List<Integer> incasariFilme = new ArrayList<>(Collections.nCopies(9, 0));
    private List<Integer> incasariEvenimente = new ArrayList<>(Collections.nCopies(9, 0));
    private List<Integer> incasariSpectacole = new ArrayList<>(Collections.nCopies(9, 0));

    private List<Integer> preturi = new ArrayList<>(Collections.nCopies(3, 0));
    private List<List<Client>> clienti = new ArrayList<List<Client>>();
    private List<Client> clienti1 = new ArrayList<>();
    private List<Client> clienti2 = new ArrayList<>();
    private List<Client> clienti3 = new ArrayList<>();

    private List<Integer> vanzatoriDimineata = new ArrayList<>();
    private List<Integer> vanzatoriDupaAmiaza = new ArrayList<>();
    private List<Integer> vanzatoriSeara = new ArrayList<>();

    private ClientRepository clientRepository;
    private VanzatorRepository vanzatorRepository;
    private AngajatRepository angajatRepository;
    private EvenimentCulturalRepository evenimentCulturalRepository;
    private FilmRepository filmRepository;
    private SpectacolRepository spectacolRepository;

    //@Autowired
    //private JavaMailSender mailSender;

    @Autowired
    public UserController(ClientRepository clientRepository, VanzatorRepository vanzatorRepository,
                          AngajatRepository angajatRepository, EvenimentCulturalRepository evenimentCulturalRepository,
                          FilmRepository filmRepository, SpectacolRepository spectacolRepository){
        this.clientRepository = clientRepository;
        this.vanzatorRepository = vanzatorRepository;
        this.angajatRepository = angajatRepository;
        this.evenimentCulturalRepository = evenimentCulturalRepository;
        this.filmRepository = filmRepository;
        this.spectacolRepository = spectacolRepository;
    }

    @GetMapping("/client/nume")
    public String numeClient(HttpServletRequest request, HttpServletResponse response, Model model){
        nume = request.getParameter("nume");
        id = request.getParameter("id");

        String s = "";

        if(nume!=null && id!=null) {
            for (Client c : clientRepository.findAll()) {
                if (c.getNume().equals(nume) && !(c.getId() + "").equals(id)) {
                    okNume = true;
                    //ok = 1;
                }
                if ((c.getId() + "").equals(id) && !c.getNume().equals(nume)) {
                    okId = true;
                    //ok = 2;
                }
                if (c.getNume().equals(nume) && (c.getId() + "").equals(id)) {
                    //okNume = true;
                    //okId = true;
                    //ok = 1;
                    client = c;
                    nume1 = nume;
                    id1 = id;
                    break;
                }
            }
            if (nume.equals(nume1) && id.equals(id1)) {
                s = "redirect:/client";
            }
            else {
                if (!okId && !okNume) {
                    model.addAttribute("errc", "Name and id given don't exist");
                    s = "numeClient";
                }
                else{
                    if (!okNume) {
                        model.addAttribute("errc", "Name given doesn't exist");
                        s = "numeClient";
                    }
                    if (!okId) {
                        model.addAttribute("errc", "Id given doesn't exist");
                        s = "numeClient";
                    }
                    if(okId && okNume){
                        model.addAttribute("errc", "Client with this id doesn't exist");
                        s = "numeClient";
                    }
                }
            }
        }
        else{
            model.addAttribute("errc", "Please enter your name and id");
            s =  "numeClient";
        }

        return s;
    }

    @GetMapping("client")
    public String showEvents(){
        return "events";
    }

    @GetMapping("client/clienti")
    public String showClienti(Model model){
        model.addAttribute("clienti", clientRepository.findAll());
        return "clienti";
    }

    @GetMapping("/client/filme")
    public String showFilmeMeniu(){
        return "filmeMenu";
    }

    @GetMapping("/client/filme/vizualizare")
    public String showFilme(Model model){
        model.addAttribute("filme", filmRepository.findAll());
        return "filme";
    }

    @GetMapping("/client/filme/cumparare")
    public String showFilmeCumparate(HttpServletRequest request, HttpServletResponse response, Model model) {
        String nume2 = request.getParameter("nume");
        boolean find = false;
        f1 = 0;

        if(nume2 != null){
            for(Film f: filmRepository.findAll()){
                if(f.getNume().equals(nume2)){
                    ocupateFilm++;
                    film = f;
                    find = true;
                    break;
                }
            }
            if(client != null){
                if(find){
                    if(client.getVarsta()>=film.getVarsta() && client.getCertificatVerde().equals("DA")
                            && film.getCapacitate() >= ocupateFilm){
                        if(Integer.parseInt(film.getData().substring(0,2)) < 12){
                            clienti1.add(client);
                        }
                        else if(Integer.parseInt(film.getData().substring(0,2)) < 20){
                            clienti2.add(client);
                        }
                        else{
                            clienti3.add(client);
                        }
                        model.addAttribute("error", "");
                        model.addAttribute("film", "Clientul " + nume1 + " cu id-ul " + id1  +
                                " a cumparat un bilet la filmul " + nume2);
                        model.addAttribute("nume", nume2 + " " + "($" + film.getPret() + ")");
                        model.addAttribute("price", film.getPret());
                        f1 = 1;
                        e1 = 0;
                        s1 = 0;
                        return "cinema";
                }
                    else{
                        if(film.getCapacitate() < ocupateFilm){
                            ocupateFilm = film.getCapacitate();
                        }
                        model.addAttribute("error", "Clientul " + nume1 + " cu id-ul " + id1
                                + " nu poate intra la filmul " + film.getNume());
                    }
                }
                else{
                    model.addAttribute("error", "This film doesn't exist");
                }
            }
            else{
                model.addAttribute("error", "Please write a valid client first");
            }
        }
        else{
            model.addAttribute("error", "Write the name of the film");
        }

        return "filmeCumparat";
    }

    @GetMapping("/client/filme/rezervare")
    public String showFilmeRezervate(HttpServletRequest request, HttpServletResponse response, Model model){
        String nume2 = request.getParameter("nume");
        boolean find = false;
        f1 = 0;

        if(nume2 != null){
            for(Film f: filmRepository.findAll()){
                if(f.getNume().equals(nume2)){
                    ocupateFilm++;
                    film = f;
                    find = true;
                    break;
                }
            }
            if(client !=null){
                if(find){
                    if(client.getVarsta()>=film.getVarsta() && client.getCertificatVerde().equals("DA")){
                        if(Integer.parseInt(film.getData().substring(0,2)) < 12){
                            clienti1.add(client);
                        }
                        else if(Integer.parseInt(film.getData().substring(0,2)) < 20){
                            clienti2.add(client);
                        }
                        else{
                            clienti3.add(client);
                        }
                        model.addAttribute("error", "");
                        model.addAttribute("film", "Clientul " + nume1 + " cu id-ul " + id1  +
                                " a rezervat un loc la filmul " + nume2);
                        f1 = 1;
                        e1 = 0;
                        s1 = 0;
                        model.addAttribute("nume", nume2 + " " + "($" + film.getPret() + ")");
                        model.addAttribute("price", film.getPret());
                        f1 = 1;
                        e1 = 0;
                        s1 = 0;
                        return "cinema";
                    }
                    else{
                        model.addAttribute("error", "Clientul " + nume1 + " cu id-ul " + id1
                                + " nu poate intra la filmul " + film.getNume());
                    }
                }
                else{
                    model.addAttribute("error", "This film doesn't exist");
                }
            }
            else{
                model.addAttribute("error", "Please write a valid client first");
            }

        }
        else{
            model.addAttribute("error", "Write the name of the film");
        }

        return "filmeRezervat";
    }

    @GetMapping("/client/evenimente culturale")
    public String showEvenimenteCulturaleMeniu(Model model){
        return "evenimenteMenu";
    }

    /*@GetMapping("/client/email")
    public String sendEmailToClient(){

    }*/

    @GetMapping("/client/evenimente culturale/vizualizare")
    public String showEvenimente(Model model){
        model.addAttribute("evenimente", evenimentCulturalRepository.findAll());
        return "evenimente";
    }

    /*public void sendFromGMail(String from,String password,String to,String sub,String msg){
        //Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from,password);
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg);
            //send message
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {throw new RuntimeException(e);}

    }*/

    @GetMapping("/client/email")
    public String sendEmail(Model model){

        if(client!=null){
            String num = client.getNume();
            String i = client.getId() + "";
            String to = "To: " + num + i + "@gmail.com";
            model.addAttribute("to", to);

            String from = "From: aurasovreiu2017@gmail.com";
            String subj = "Subject: Confirmare rezervare loc";
            String msg = "Message: ";
            if(nume1 != null){
                if(f1 == 1){
                    msg += nume1 + ", " + "rezervarea dumneavoastra la filmul " + film.getNume() + " din data de "
                            + film.getData() + " a fost confirmata";
                }
                else if(e1 == 1){
                    msg += nume1 + ", " + "rezervarea dumneavoastra la evenimentul cultural " + evenimentCultural.getNume() + " din data de "
                            + evenimentCultural.getData() + " a fost confirmata";
                }
                else if(s1 == 1){
                    msg += nume1 + ", " + "rezervarea dumneavoastra la spectacolul " + spectacol.getNume() + " din data de "
                            + spectacol.getData() + " a fost confirmata";
                }
            }

            model.addAttribute("from", from);
            model.addAttribute("subj", subj);
            model.addAttribute("msg", msg);
        }

        return "email";

        /*String to = "aurasovreiu2017@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "aurasovreiu2017@gmail.com";

        // Assuming you are sending email from localhost
        String host = "localhost:8080";

        // Get system properties
        Properties properties = System.getProperties();

        //properties.setProperty("mail.smtp.auth", "true");
        //properties.setProperty("mail.smtp.starttls.enable", "true");
        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Now set the actual message
            message.setText("This is actual message");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }*/

        /*String from = "aurasovreiu2017@gmail.com";
        String to = "aurasovreiu2017@gmail.com";

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject("This is a plain text email");
        message.setText("Hello guys! This is a plain text email.");

        mailSender.send(message);*/
    }

    @GetMapping("/client/evenimente culturale/cumparare")
    public String showEvenimenteCumparate(HttpServletRequest request, HttpServletResponse response, Model model){
        String nume2 = request.getParameter("nume");
        boolean find = false;
        e1 = 0;

        if(nume2 != null){
            for(EvenimentCultural e: evenimentCulturalRepository.findAll()){
                if(e.getNume().equals(nume2)){
                    ocupateEveniment++;
                    evenimentCultural = e;
                    find = true;
                    break;
                }
            }
            if(evenimentCultural != null && client !=null){
                if(find){
                    if(evenimentCultural.getCapacitate() >= ocupateEveniment && client.getCertificatVerde().equals("DA")){
                        if(Integer.parseInt(evenimentCultural.getData().substring(0,2)) < 12){
                            clienti1.add(client);
                        }
                        else if(Integer.parseInt(evenimentCultural.getData().substring(0,2)) < 20){
                            clienti2.add(client);
                        }
                        else{
                            clienti3.add(client);
                        }
                        model.addAttribute("error", "");
                        model.addAttribute("eveniment", "Clientul " + nume1 + " cu id-ul " + id1  +
                                " a cumparat un bilet la evenimentul cultural " + nume2);
                        e1 = 1;
                        f1 = 0;
                        s1 = 0;
                    }
                    else{
                        if(evenimentCultural.getCapacitate() < ocupateEveniment){
                            ocupateEveniment = evenimentCultural.getCapacitate();
                        }
                        model.addAttribute("error", "Clientul " + nume1 + " cu id-ul " + id1
                                + " nu poate intra la  evenimentul cultural " + evenimentCultural.getNume());
                    }
                }
                else{
                    model.addAttribute("error", "This cultural event doesn't exist");
                }
            }
            else if(client == null){
                model.addAttribute("error", "Please write a valid client first");
            }
            else{
                model.addAttribute("error", "Wrong cultural event");
            }
        }
        else{
            model.addAttribute("error", "Write the name of the cultural event");
        }

        return "evenimenteCumparat";
    }

    @GetMapping("/client/evenimente culturale/rezervare")
    public String showEvenimenteRezervate(HttpServletRequest request, HttpServletResponse response, Model model){
        String nume2 = request.getParameter("nume");
        boolean find = false;
        e1 = 0;

        if(nume2 != null){
            for(EvenimentCultural e: evenimentCulturalRepository.findAll()){
                if(e.getNume().equals(nume2)){
                    ocupateEveniment++;
                    evenimentCultural = e;
                    find = true;
                    break;
                }
            }
            if(evenimentCultural != null && client != null){
                if(find){
                    if(evenimentCultural.getCapacitate() >= ocupateEveniment && client.getCertificatVerde().equals("DA")){
                        if(Integer.parseInt(evenimentCultural.getData().substring(0,2)) < 12){
                            clienti1.add(client);
                        }
                        else if(Integer.parseInt(evenimentCultural.getData().substring(0,2)) < 20){
                            clienti2.add(client);
                        }
                        else{
                            clienti3.add(client);
                        }
                        model.addAttribute("error", "");
                        model.addAttribute("eveniment", "Clientul " + nume1 + " cu id-ul " + id1  +
                                " a cumparat un bilet la evenimentul cultural " + nume2);
                        e1 = 1;
                        f1 = 0;
                        s1 = 0;
                    }
                    else{
                        if(evenimentCultural.getCapacitate() < ocupateEveniment){
                            ocupateEveniment = evenimentCultural.getCapacitate();
                        }
                        model.addAttribute("error", "Clientul " + nume1 + " cu id-ul " + id1
                                + " nu poate intra la  evenimentul cultural " + evenimentCultural.getNume());
                    }
                }
                else{
                    model.addAttribute("error", "This cultural event doesn't exist");
                }
            }
            else if(client == null){
                model.addAttribute("error", "Please write a valid client first");
            }
            else{
                model.addAttribute("error", "Wrong cultural event");
            }

        }
        else{
            model.addAttribute("error", "Write the name of the cultural event");
        }

        return "evenimenteRezervat";
    }

    @GetMapping("/client/spectacole")
    public String showSpectacoleMeniu(){
        return "spectacoleMenu";
    }

    @GetMapping("/client/spectacole/vizualizare")
    public String showSpectacole(Model model){
        model.addAttribute("spectacole", spectacolRepository.findAll());
        return "spectacole";
    }

    @GetMapping("/client/spectacole/rezervare")
    public String showSpectacoleRezervate(HttpServletRequest request, HttpServletResponse response, Model model){
        String nume2 = request.getParameter("nume");
        boolean find = false;
        s1 = 0;

        if(nume2 != null && client != null){
            for(Spectacol s: spectacolRepository.findAll()){
                if(s.getNume().equals(nume2)){
                    spectacol = s;
                    find = true;
                    break;
                }
            }
            if(find){
                if(Integer.parseInt(spectacol.getInterval_orar().substring(0,2)) < 12){
                    clienti1.add(client);
                }
                else if(Integer.parseInt(spectacol.getInterval_orar().substring(0,2)) < 20){
                    clienti2.add(client);
                }
                else{
                    clienti3.add(client);
                }
                model.addAttribute("error", "");
                model.addAttribute("spectacol", "Clientul " + nume1 + " cu id-ul " + id1  +
                        " a rezervat un loc la spectacolul " + nume2);
                s1 = 1;
                f1 = 0;
                e1 = 0;
            }
            else{
                model.addAttribute("error", "This spectacle doesn't exist");
            }
        }
        else if(nume2 == null){
            model.addAttribute("error", "Write the name of the spectacle");
        }
        else{
            model.addAttribute("error", "Please write a valid client first");
        }

        return "spectacoleRezervat";
    }

    @GetMapping("vanzator")
    public String showVanzatori(Model model){
        int i = 0;
        int oraStart = 0;
        int oraEnd = 0;
        Random rand = new Random();

        for(Vanzator v: vanzatorRepository.findAll()){
            if(v.getInterval_orar().equals("8-12")){
                vanzatoriDimineata.add(i);
            }
            else if(v.getInterval_orar().equals("12-20")){
                vanzatoriDupaAmiaza.add(i);
            }
            else if(v.getInterval_orar().equals("20-24")){
                vanzatoriSeara.add(i);
            }
            i++;
        }

        i=0;
        int r = rand.nextInt(3);
        for(Vanzator v: vanzatorRepository.findAll()){
            if(v.getInterval_orar().length()==4){
                oraStart = Integer.parseInt(v.getInterval_orar().substring(0, 1));
                oraEnd = Integer.parseInt(v.getInterval_orar().substring(2, 4));
            }
            else{
                oraStart = Integer.parseInt(v.getInterval_orar().substring(0, 2));
                oraEnd = Integer.parseInt(v.getInterval_orar().substring(3, 5));
            }
            if(film != null){
                if(oraStart <= Integer.parseInt(film.getData().substring(0, 2)) &&
                        oraEnd > Integer.parseInt(film.getData().substring(0, 2))){
                    if(oraEnd == 12){
                        if(i == vanzatoriDimineata.get(r)){
                            incasariFilme.set(i, incasariFilme.get(i) + film.getPret());
                            break;
                        }
                    }
                    else if(oraEnd == 20){
                        if(i == vanzatoriDupaAmiaza.get(r)){
                            incasariFilme.set(i, incasariFilme.get(i) + film.getPret());
                            break;
                        }
                    }
                    else{
                        if(i == vanzatoriSeara.get(r)){
                            incasariFilme.set(i, incasariFilme.get(i) + film.getPret());
                            break;
                        }
                    }
                }
            }

            if(evenimentCultural != null){
                if(oraStart <= Integer.parseInt(evenimentCultural.getData().substring(0, 2)) &&
                        oraEnd > Integer.parseInt(evenimentCultural.getData().substring(0, 2))){
                    if(oraEnd == 12){
                        if(i == vanzatoriDimineata.get(r)){
                            incasariEvenimente.set(i, incasariEvenimente.get(i) + evenimentCultural.getPret());
                            break;
                        }
                    }
                    else if(oraEnd == 20){
                        if(i == vanzatoriDupaAmiaza.get(r)){
                            incasariEvenimente.set(i, incasariEvenimente.get(i) + evenimentCultural.getPret());
                            break;
                        }
                    }
                    else{
                        if(i == vanzatoriSeara.get(r)){
                            incasariEvenimente.set(i, incasariEvenimente.get(i) + evenimentCultural.getPret());
                            break;
                        }
                    }
                }
            }

            if(spectacol != null){
                if(oraStart <= Integer.parseInt(spectacol.getInterval_orar().substring(0, 2)) &&
                        oraEnd > Integer.parseInt(spectacol.getInterval_orar().substring(0, 2))){
                    if(oraEnd == 12){
                        if(i == vanzatoriDimineata.get(r)){
                            incasariSpectacole.set(i, incasariSpectacole.get(i) + 1);
                            break;
                        }
                    }
                    else if(oraEnd == 20){
                        if(i == vanzatoriDupaAmiaza.get(r)){
                            incasariSpectacole.set(i, incasariSpectacole.get(i) + 1);
                            break;
                        }
                    }
                    else{
                        if(i == vanzatoriSeara.get(r)){
                            incasariSpectacole.set(i, incasariSpectacole.get(i) + 1);
                            break;
                        }
                    }
                }
            }

            i++;
        }

        film = null;
        evenimentCultural = null;
        spectacol = null;

        model.addAttribute("vanzatori", vanzatorRepository.findAll());
        model.addAttribute("incasari1", incasariFilme);
        model.addAttribute("incasari2", incasariEvenimente);
        model.addAttribute("spectatori", incasariSpectacole);
        return "vanzatori";
    }

    @GetMapping("angajat")
    public String showAngajati(Model model){

        int i = 0;
        int oraStart = 0;
        int oraEnd = 0;

        clienti = new ArrayList<List<Client>>();

        for(Angajat a: angajatRepository.findAll()) {
            if (a.getInterval_orar().length() == 4) {
                oraStart = Integer.parseInt(a.getInterval_orar().substring(0, 1));
                oraEnd = Integer.parseInt(a.getInterval_orar().substring(2, 4));
            } else {
                oraStart = Integer.parseInt(a.getInterval_orar().substring(0, 2));
                oraEnd = Integer.parseInt(a.getInterval_orar().substring(3, 5));
            }

            if(oraStart == 8){
                clienti.add(i, clienti1);
            }
            else if(oraStart == 12){
                clienti.add(i, clienti2);
            }
            else{
                clienti.add(i, clienti3);
            }

            int j = 0, s = 0;
            for(Vanzator v: vanzatorRepository.findAll()){
                int oraStart1 = 0;
                int oraEnd1 = 0;

                if(v.getInterval_orar().length()==4){
                    oraStart1 = Integer.parseInt(v.getInterval_orar().substring(0, 1));
                    oraEnd1 = Integer.parseInt(v.getInterval_orar().substring(2, 4));
                }
                else{
                    oraStart1 = Integer.parseInt(v.getInterval_orar().substring(0, 2));
                    oraEnd1 = Integer.parseInt(v.getInterval_orar().substring(3, 5));
                }
                if(oraStart1 == oraStart){
                    s += incasariFilme.get(j) + incasariEvenimente.get(j);
                }
                j++;
            }
            preturi.set(i, s);
            i++;
        }


        List<Integer> nr = new ArrayList<>(Collections.nCopies(3, 0));
        if(clienti != null){
            for(int k = 0; k<3; k++){
                nr.set(k, (clienti.get(k).size()));
            }
        }

        List<List<Client>> cl;
        if(clienti != null){
            cl = clienti;
        }
        else{
            cl = new ArrayList<List<Client>>();
        }

        model.addAttribute("angajati", angajatRepository.findAll());
        model.addAttribute("preturi", preturi);
        model.addAttribute("nr", nr);
        model.addAttribute("clienti", cl);
        return "angajati";
    }

    @GetMapping("manager")
    public String showManagerMenu(){
        return "manager";
    }

    @GetMapping("manager/vizualizare/filme")
    public String showManagerFilme(Model model){
        model.addAttribute("filme", filmRepository.findAll());
        return "filme";
    }

    @GetMapping("manager/vizualizare/evenimente culturale")
    public String showManagerEvenimenteCulturale(Model model){
        model.addAttribute("evenimente", evenimentCulturalRepository.findAll());
        return "evenimente";
    }

    @GetMapping("manager/vizualizare/spectacole")
    public String showManagerSpectacole(Model model){
        model.addAttribute("spectacole", spectacolRepository.findAll());
        return "spectacole";
    }

    @GetMapping("manager/adaugare")
    public String showManagerEvents(){
        return "events1";
    }

    @GetMapping("manager/adaugare/film")
    public String adaugareFilm(HttpServletRequest request, HttpServletResponse response, Model model)
    throws NumberFormatException{
        String num = request.getParameter("nume");
        String gen = request.getParameter("gen");
        String varsta = request.getParameter("varsta");
        String tip = request.getParameter("tip");
        String data = request.getParameter("data");
        String capacitate = request.getParameter("capacitate");
        String pret = request.getParameter("pret");
        String regizor = request.getParameter("regizor");
        String actori = request.getParameter("actori");

        int ok1 =0, ok2 = 0, ok3 = 0;

        if(!Objects.equals(num, "") && !Objects.equals(gen, "") && !Objects.equals(varsta, "")
                && !Objects.equals(tip, "") && !Objects.equals(data, "")
                && !Objects.equals(capacitate, "") && !Objects.equals(pret, "")){
            int v = 0;
            try{
                v = Integer.parseInt(varsta);
            }
            catch (NumberFormatException n1){
                ok1 = 1;
                model.addAttribute("error1", "Film age must be an integer");
            }
            int c = 0;
            try{
                c = Integer.parseInt(capacitate);
            }
            catch (NumberFormatException n2){
                ok2 = 1;
                model.addAttribute("error2", "Film capacity must be an integer");
            }
            int p = 0;
            try{
                p = Integer.parseInt(pret);
            }
            catch (NumberFormatException n3){
                ok3 = 1;
                model.addAttribute("error3", "Film price must be an integer");
            }

            if(ok1 == 0 && ok2 == 0 && ok3 == 0){
                filmRepository.save(new Film(num, gen, v, tip, data, c, p, regizor, actori));
                model.addAttribute("error1", "");
                model.addAttribute("error2", "");
                model.addAttribute("error3", "");
                model.addAttribute("succ", "Film " + num + " was added succesfully");
            }
        }
        else{
            model.addAttribute("error4", "Complete all fields");
        }
        return "adaugareFilm";
    }

    @GetMapping("manager/adaugare/eveniment cultural")
    public String adaugareEvenimentCultural(HttpServletRequest request, HttpServletResponse response, Model model)
            throws NumberFormatException{
        String num = request.getParameter("nume");
        String tip = request.getParameter("tip");
        String data = request.getParameter("data");
        String capacitate = request.getParameter("capacitate");
        String pret = request.getParameter("pret");

        int ok1 =0, ok2 = 0;

        if(!Objects.equals(num, "") && !Objects.equals(tip, "") && !Objects.equals(data, "")
                && !Objects.equals(capacitate, "") && !Objects.equals(pret, "")){
            int c = 0;
            try{
                c = Integer.parseInt(capacitate);
            }
            catch (NumberFormatException n2){
                ok1 = 1;
                model.addAttribute("error1", "Cultural event capacity must be an integer");
            }
            int p = 0;
            try{
                p = Integer.parseInt(pret);
            }
            catch (NumberFormatException n3){
                ok2 = 1;
                model.addAttribute("error2", "Cultural event price must be an integer");
            }

            if(ok1 == 0 && ok2 == 0){
                evenimentCulturalRepository.save(new EvenimentCultural(num, tip, data, c, p));
                model.addAttribute("error1", "");
                model.addAttribute("error2", "");
                model.addAttribute("succ", "Cultural event " + num + " was added succesfully");
            }
        }
        else{
            model.addAttribute("error3", "Complete all fields");
        }
        return "adaugareEveniment";
    }

    @GetMapping("manager/adaugare/spectacol")
    public String adaugareSpectacol(HttpServletRequest request, HttpServletResponse response, Model model)
            throws NumberFormatException{
        String num = request.getParameter("nume");
        String muzica = request.getParameter("muzica");
        String data = request.getParameter("data");
        String locatie = request.getParameter("locatie");
        String artisti = request.getParameter("artisti");
        String interval = request.getParameter("interval");

        if(num != null && muzica != null && data != null && locatie != null && artisti != null && interval != null){
            if(!Objects.equals(num, "") && !Objects.equals(muzica, "") && !Objects.equals(data, "")
                    && !Objects.equals(locatie, "") && !Objects.equals(artisti, "")
                    && !Objects.equals(interval, "")){

                spectacolRepository.save(new Spectacol(num, muzica, data, locatie, artisti, interval));
                model.addAttribute("succ", "Spectacle " + num + " was added succesfully");
            }
            else{
                model.addAttribute("error", "Complete all fields");
            }
        }
        else{
            model.addAttribute("error", "Complete all fields");
        }

        return "adaugareSpectacol";
    }

    @GetMapping("manager/stergere")
    public String showManagerEvents1(){
        return "events2";
    }

    @GetMapping("manager/stergere/film")
    public String stergereFilm(HttpServletRequest request, HttpServletResponse response, Model model){
        String num = request.getParameter("nume");
        boolean find = false;
        Film f1 = null;

        if(num != null) {
            for (Film f : filmRepository.findAll()) {
                if (f.getNume().equals(num)) {
                    f1 = f;
                    find = true;
                    break;
                }
            }

            if(!num.equals("")){
                if(find){
                    filmRepository.delete(f1);
                    model.addAttribute("succ", "Filmul " + num + " a fost sters");
                }
                else{
                    model.addAttribute("error", "nu exista filme cu numele " + num);
                }
            }
            else{
                model.addAttribute("error", "Write the name of the film to be deleted");
            }
        }
        else{
            model.addAttribute("error", "Write the name of the film to be deleted");
        }

        return "stergereFilm";
    }

    @GetMapping("manager/stergere/eveniment cultural")
    public String stergereEvenimentCultural(HttpServletRequest request, HttpServletResponse response, Model model){
        String num = request.getParameter("nume");
        boolean find = false;
        EvenimentCultural e1 = null;

        if(num != null) {
            for (EvenimentCultural e: evenimentCulturalRepository.findAll()) {
                if (e.getNume().equals(num)) {
                    e1 = e;
                    find = true;
                    break;
                }
            }

            if(!num.equals("")){
                if(find){
                    evenimentCulturalRepository.delete(e1);
                    model.addAttribute("succ", "Evenimentul cultural " + num + " a fost sters");
                }
                else{
                    model.addAttribute("error", "nu exista evenimente culturale cu numele " + num);
                }
            }
            else{
                model.addAttribute("error", "Write the name of the cultural event to be deleted");
            }
        }
        else{
            model.addAttribute("error", "Write the name of the cultural event to be deleted");
        }

        return "stergereEveniment";
    }

    @GetMapping("manager/stergere/spectacol")
    public String stergereSpecatcol(HttpServletRequest request, HttpServletResponse response, Model model){
        String num = request.getParameter("nume");
        boolean find = false;
        Spectacol s1 = null;

        if(num != null) {
            for (Spectacol s: spectacolRepository.findAll()) {
                if(s.getNume() == null){
                    break;
                }
                if (s.getNume().equals(num)) {
                    s1 = s;
                    find = true;
                    break;
                }
            }

            if(!num.equals("")){
                if(find){
                    spectacolRepository.delete(s1);
                    model.addAttribute("succ", "Spectacolul " + num + " a fost sters");
                }
                else{
                    model.addAttribute("error", "nu exista spectacole cu numele " + num);
                }
            }
            else{
                model.addAttribute("error", "Write the name of the spectacle to be deleted");
            }
        }
        else{
            model.addAttribute("error", "Write the name of the spectacle to be deleted");
        }

        return "stergereSpectacol";
    }

    @GetMapping("manager/modificare pret")
    public String showManagerEvents2(){
        return "events3";
    }

    @GetMapping("manager/modificare pret/film")
    public String modificarePretFilm(HttpServletRequest request, HttpServletResponse response, Model model)
    throws NumberFormatException{
        String num = request.getParameter("nume");
        String pret = request.getParameter("pret");
        boolean find = false;
        Film f1 = null;

        if(num != null) {
            for (Film f : filmRepository.findAll()) {
                if (f.getNume().equals(num)) {
                    f1 = f;
                    find = true;
                    break;
                }
            }

            int p2 = 0;
            if(f1 != null){
                p2 = f1.getPret();
            }

            if(!num.equals("")){
                if(find){
                    int p = 0, ok = 0;
                    try{
                        p = Integer.parseInt(pret);
                    }catch (NumberFormatException p1){
                        ok = 1;
                        model.addAttribute("error1", "Price must be an integer");
                    }
                    if(ok == 0){
                        f1.setPret(p);
                        filmRepository.save(f1);
                        model.addAttribute("succ", "Pretul filmului " + num + " a fost setat" +
                                " de la " + p2 + " la " + p);
                    }
                }
                else{
                    model.addAttribute("error", "nu exista filme cu numele " + num);
                }
            }
            else{
                model.addAttribute("error", "Write the name of the film to be deleted");
            }
        }
        else{
            model.addAttribute("error", "Write the name of the film to be deleted");
        }

        return "modificarePretFilm";
    }

    @GetMapping("manager/modificare pret/eveniment cultural")
    public String modificarePretEvenimentCultural(HttpServletRequest request, HttpServletResponse response, Model model)
            throws NumberFormatException{
        String num = request.getParameter("nume");
        String pret = request.getParameter("pret");
        boolean find = false;
        EvenimentCultural e1 = null;

        if(num != null) {
            for (EvenimentCultural e : evenimentCulturalRepository.findAll()) {
                if (e.getNume().equals(num)) {
                    e1 = e;
                    find = true;
                    break;
                }
            }

            int p2 = 0;
            if(e1 != null){
                p2 = e1.getPret();
            }

            if(!num.equals("")){
                if(find){
                    int p = 0, ok = 0;
                    try{
                        p = Integer.parseInt(pret);
                    }catch (NumberFormatException p1){
                        ok = 1;
                        model.addAttribute("error1", "Price must be an integer");
                    }
                    if(ok == 0){
                        e1.setPret(p);
                        evenimentCulturalRepository.save(e1);
                        model.addAttribute("succ", "Pretul evenimentului cultural " +
                                num + " a fost setat" + " de la " + p2 + " la " + p);
                    }
                }
                else{
                    model.addAttribute("error", "nu exista evenimente culturale cu" +
                            " numele " + num);
                }
            }
            else{
                model.addAttribute("error", "Write the name of the cultural event to be deleted");
            }
        }
        else{
            model.addAttribute("error", "Write the name of the cultural event to be deleted");
        }

        return "modificarePretEveniment";
    }

    @GetMapping("manager/modificare capacitate")
    public String showManagerEvents3(){
        return "events4";
    }

    @GetMapping("manager/modificare capacitate/film")
    public String modificareCapacitateFilm(HttpServletRequest request, HttpServletResponse response, Model model)
            throws NumberFormatException{
        String num = request.getParameter("nume");
        String capacitate = request.getParameter("capacitate");
        boolean find = false;
        Film f1 = null;

        if(num != null) {
            for (Film f : filmRepository.findAll()) {
                if (f.getNume().equals(num)) {
                    f1 = f;
                    find = true;
                    break;
                }
            }

            int p2 = 0;
            if(f1 != null){
                p2 = f1.getCapacitate();
            }

            if(!num.equals("")){
                if(find){
                    int p = 0, ok = 0;
                    try{
                        p = Integer.parseInt(capacitate);
                    }catch (NumberFormatException p1){
                        ok = 1;
                        model.addAttribute("error1", "Capacity must be an integer");
                    }
                    if(ok == 0){
                        f1.setCapacitate(p);
                        filmRepository.save(f1);
                        model.addAttribute("succ", "Capacitatea filmului " + num + " a fost setata" +
                                " de la " + p2 + " la " + p);
                    }
                }
                else{
                    model.addAttribute("error", "nu exista filme cu numele " + num);
                }
            }
            else{
                model.addAttribute("error", "Write the name of the film to be deleted");
            }
        }
        else{
            model.addAttribute("error", "Write the name of the film to be deleted");
        }

        return "modificareCapacitateFilm";
    }

    @GetMapping("manager/modificare capacitate/eveniment cultural")
    public String modificareCapacitateEvenimentCultural(HttpServletRequest request, HttpServletResponse response, Model model)
            throws NumberFormatException{
        String num = request.getParameter("nume");
        String capacitate = request.getParameter("capacitate");
        boolean find = false;
        EvenimentCultural e1 = null;

        if(num != null) {
            for (EvenimentCultural e : evenimentCulturalRepository.findAll()) {
                if (e.getNume().equals(num)) {
                    e1 = e;
                    find = true;
                    break;
                }
            }

            int p2 = 0;
            if(e1 != null){
                p2 = e1.getCapacitate();
            }

            if(!num.equals("")){
                if(find){
                    int p = 0, ok = 0;
                    try{
                        p = Integer.parseInt(capacitate);
                    }catch (NumberFormatException p1){
                        ok = 1;
                        model.addAttribute("error1", "Capacity must be an integer");
                    }
                    if(ok == 0){
                        e1.setCapacitate(p);
                        evenimentCulturalRepository.save(e1);
                        model.addAttribute("succ", "Capacitatea evenimentului cultural "
                                + num + " a fost setata" + " de la " + p2 + " la " + p);
                    }
                }
                else{
                    model.addAttribute("error", "nu exista evenimente culturale cu" +
                            " numele " + num);
                }
            }
            else{
                model.addAttribute("error", "Write the name of the cultural event to be deleted");
            }
        }
        else{
            model.addAttribute("error", "Write the name of the cultural event to be deleted");
        }

        return "modificareCapacitateEveniment";
    }
}
