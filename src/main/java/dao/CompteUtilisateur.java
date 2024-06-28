package dao;

import org.springframework.stereotype.Component;

@Component

public class CompteUtilisateur {
    private Compte compte;
    private Utilisateur utilisateur;

    public CompteUtilisateur(Compte compte, Utilisateur utilisateur) {
        this.compte = compte;
        this.utilisateur = utilisateur;
    }
    public CompteUtilisateur() {
        
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
