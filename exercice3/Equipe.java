package exercice3;

import java.util.List;

/** Exercice 1 : creation des equipes
 *  SAE n°1 Semestre 1
 *  @author  Caron Antoine, Vicente Hugo, Bouloche Eleonore, Bondu Justine, Dunet Tom
 *  @version 1 du 05/01/2023
 */


public class Equipe
{
	private int            numero;
	public List<Etudiant>  lstEtud;
	private int            salle;

	public Equipe (int numeroEquipe, List<Etudiant> lstEtudiant, int salle)
	{
		this.numero      = numeroEquipe;
		this.lstEtud     = List.copyOf(lstEtudiant);
		this.salle       = salle;
	}

	
	public int            getNumero ()  {return this.numero;}
	public List<Etudiant> getLstEtud()  {return this.lstEtud;}
	public int            getSalle  ()  {return this.salle;}

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

		for (Etudiant e : lstEtud)
		{
			s += e.toString() + "\n";
		}
		
		return s;
	}

	public void setGroupe (List<Etudiant> nouveauGroupe)
	{
		this.lstEtud = List.copyOf(nouveauGroupe);
	}

} 