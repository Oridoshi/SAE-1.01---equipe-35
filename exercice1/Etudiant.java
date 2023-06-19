package exercice1;

import java.util.Comparator;

/** classe etudiant
 *  SAE n°1 Semestre 1
 *  @author  Caron Antoine, Vicente Hugo, Bouloche Eleonore, Bondu Justine, Dunet Tom
 *  @version 2 du 09/01/2023
 */


public class Etudiant implements Comparable<Etudiant>
{
	private	String nom;
	private String prenom;
	private char   groupe;
	private char   catego;

	/**
	 * Créée un Etudiant
	 * @param nom : nom de l'étudiant
	 * @param prenom : prénom de l'étudiant
	 * @param groupe : groupe de l'étudiant
	 * @param catego : catégorie de l'étudiant
	 */
	public Etudiant ( String nom, String prenom, char groupe, char catego )
	{
		this.nom    = nom;
		this.prenom = prenom;
		this.groupe = groupe;
		this.catego = catego;
	}
	
	
	public String getNom    (){ return this.nom;    }	
	public String getPrenom (){ return this.prenom; }
	public char   getGroupe (){ return this.groupe; }
	public char   getCatego (){ return this.catego; }
	
    /**
	 * Compare deux Etudiant entre eux en fonction de leurs nom et prénom
	 * @param autreEtud : Etudiant à comparé
	 * @return 0 si le nom et le prenom sont identiques, une valeur > à 0 si le nom et le prenom du premier étudiant est plus grand que celui du second, sinon renvoie une valeur < à 0
	 */
	public int compareTo( Etudiant autreEtud )
	{
		return (( this.nom + this.prenom )).toUpperCase().compareTo(( autreEtud.nom + autreEtud.prenom ).toUpperCase());
	}



	public static class Comparators
	{
		/**
		 * Compare la catégorie de deux étudiants
		 */
		public static Comparator<Etudiant> CATEGORIE = new Comparator<Etudiant>() 
		{
			public int compare(Etudiant e1, Etudiant e2)
			{
				return e1.catego - e2.catego;
			}
		};

		/**
		 * Compare le groupe de deux étudiants
		 */
		public static Comparator<Etudiant> GROUPE = new Comparator<Etudiant>() 
		{
			public int compare(Etudiant e1, Etudiant e2) 
			{
				return e1.groupe - e2.groupe;
			}	
		};
	}

    
	public String toString ()
	{
		return String.format( "%-15s", this.nom ) + String.format( "%-15s", this.prenom ) + this.groupe;
	}
}