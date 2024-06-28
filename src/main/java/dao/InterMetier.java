package dao;

public interface InterMetier {
	public Compte depot(Compte c,int montant);
	public Compte retrait(Compte c,int montant);
	public Compte consulter(int numero);

}
