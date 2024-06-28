package dao;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		UtilisateurHome uh=new UtilisateurHome();
		
		 Utilisateur u= uh.connexion("sidy", "dione"); if(u==null) {
		 System.out.println("l'utilisateur n'existe pas"); } else {
		 System.out.println("l'utilisateur existe"); }
		 
		/*
		 * Utilisateur user=new Utilisateur("fallou", "ndour", "fallou", "1234", null);
		 * //uh.persist(user); Compte c=new Compte(user, 200000, null, null); CompteHome
		 * ch=new CompteHome(); ch.persist(c); OperationHome oh=new OperationHome();
		 * oh.retrait(c, 20000); System.out.println(c.getSolde());
		 */
		 OperationHome oh=new OperationHome();
		 Compte c=oh.consulter(2);
		 System.out.println(c.getSolde());
	}

}
