package bean;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import dao.Compte;
import dao.CompteHome;
import dao.CompteUtilisateur;
import dao.Operation;
import dao.OperationHome;
import dao.Utilisateur;
import dao.UtilisateurHome;
import securite.SecurityPassword;
import Util.EmailUtil;

@ManagedBean
public class UserBean {
    ApplicationContext spring = new AnnotationConfigApplicationContext("dao");

    FacesContext facesContext = FacesContext.getCurrentInstance();
    ExternalContext externalContext = facesContext.getExternalContext();
    HttpSession session = (HttpSession) externalContext.getSession(true);

    private CompteUtilisateur compteUtilisateurs = spring.getBean(CompteUtilisateur.class);
    dao.SecurityPassword securityPassword = spring.getBean(dao.SecurityPassword.class);

    private Utilisateur user = spring.getBean(Utilisateur.class);
    private Compte compte = spring.getBean(Compte.class);
    int somme;

    Operation ope = new Operation();
    public Operation getOpe() {
        return ope;
    }

    public void setOpe(Operation ope) {
        this.ope = ope;
    }

    public int getSomme() {
        return somme;
    }

    public void setSomme(int somme) {
        this.somme = somme;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public CompteUtilisateur getCompteUtilisateurs() {
        return compteUtilisateurs;
    }

    public void setCompteUtilisateurs(CompteUtilisateur compteUtilisateurs) {
        this.compteUtilisateurs = compteUtilisateurs;
    }

    UtilisateurHome uh = spring.getBean(UtilisateurHome.class);
    CompteHome ch = spring.getBean(CompteHome.class);
    OperationHome oh = spring.getBean(OperationHome.class);

    public String verif() {
        if (uh.connexion(user.getLogin(), user.getPassword()) == null) {
            return "echec";
        } else {
            session.setAttribute("userC", uh.connexion(user.getLogin(), user.getPassword()));
            user = uh.connexion(user.getLogin(), user.getPassword());
            compte = ch.rechercheCompte(user);
            session.setAttribute("compteC", compte);
            compteUtilisateurs.setCompte(compte);
            compteUtilisateurs.setUtilisateur(user);
            return "reussi";
        }
    }

    public String inscrire() {
        compte.setUtilisateur(user);
        compte.setSolde(0);
        compte.setDatecreation(new Date());
        user.getComptes().add(compte);

        String rawPassword = user.getPassword();
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("Mot de passe ne peut pas être null ou vide");
        }
        String hashedPassword = securityPassword.CryptPwd(rawPassword);
        user.setPassword(hashedPassword);

        compteUtilisateurs.setCompte(compte);
        compteUtilisateurs.setUtilisateur(user);
        session.setAttribute("userA", user);
        session.setAttribute("compteA", compte);
        uh.persist(user);
        ch.persist(compte);

        // Générer le fichier avec les informations de l'utilisateur
        String filePath = "C:\\Users\\HP\\Documents\\kya.txt"; // Chemin du fichier
        try {
            generateUserInfoFile(user, filePath);

            // Envoyer l'email avec le fichier en pièce jointe
            String to = user.getLogin(); // Adresse email de l'utilisateur
            String subject = "Bienvenue, votre compte a été créé";
            String body = "Bonjour " + user.getPrenom() + ",\n\nVotre compte a été créé avec succès.";
            EmailUtil.sendEmailWithAttachment(to, subject, body, filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "connexion";
    }

    public String depot() {
        compte = (Compte) session.getAttribute("compteC");
        compte = oh.depot(compte, somme);
        ch.merge(compte);
        return "reussi";
    }

    public String retrait() {
        compte = (Compte) session.getAttribute("compteC");
        compte = oh.retrait(compte, somme);
        ch.merge(compte);
        return "reussi";
    }

    public String consulter() {
        compte = (Compte) session.getAttribute("compteC");
        compte = oh.consulter(somme);
        // ch.merge(compte);
        return "affichage";
    }

    public String goToReussi() {
        return "reussi"; // Retourne le nom de la page sans l'extension .xhtml
    }

    private void generateUserInfoFile(Utilisateur user, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Informations de l'utilisateur :\n");
            writer.write("Nom : " + user.getNom() + "\n");
            writer.write("Prénom : " + user.getPrenom() + "\n");
            writer.write("Login : " + user.getLogin() + "\n");
            // Ajoutez d'autres informations si nécessaire
        }
    }
}
