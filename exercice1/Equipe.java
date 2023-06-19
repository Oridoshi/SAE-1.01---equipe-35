package exercice1;

import java.util.List;

/** classe equipe
 *  SAE n°1 Semestre 1
 *  @author  Caron Antoine, Vicente Hugo, Bouloche Eleonore, Bondu Justine, Dunet Tom
 *  @version 1 du 06/01/2023
 */


public class Equipe
{
	private int            numero;
	private List<Etudiant> lstEtud;
	private int            salle;

	/**
	 * Créée une équipe
	 * @param numeroEquipe : numéro de l'équipe
	 * @param lstEtudiant : liste des étudiants
	 * @param salle : numéro de la salle
	 */
	public Equipe (int numeroEquipe, List<Etudiant> lstEtudiant, int salle)
	{
		this.numero      = numeroEquipe;
		this.lstEtud     = List.copyOf(lstEtudiant);
		this.salle       = salle;
	}

	
	public int            getNumero ()  {return this.numero;}
	public List<Etudiant> getLstEtud()  {return this.lstEtud;}
	public int            getSalle  ()  {return this.salle;}
	
	/**
	 * Permet de changer le groupe d'une équipe
	 * @param nouveauGroupe : nouveau groupe de l'équipe
	 */
	public void setGroupe (List<Etudiant> nouveauGroupe)
	{
		this.lstEtud = List.copyOf(nouveauGroupe);
	}

	
	public String toString()
	{
		/*--------------*/
		/*  variables   */
		/*--------------*/

		String  s;

		/*--------------*/
		/* instructions */
		/*--------------*/

		s = "Equipe " + this.numero + '\n';
		
		return s;
	}


} 