package exercice2;

import java.util.*;

/** classe jury
 *  SAE n°1 Semestre 1
 *  @author  Caron Antoine, Vicente Hugo, Bouloche Eleonore, Bondu Justine, Dunet Tom
 *  @version 1 du 06/01/2023
 */


public class Jury implements Comparable<Jury>
{
	private int          numJury;    
	private String       date;  
	private int          heureD;//en minutes
	private int          heureF;//en minutes
	private List<String> lstProf;
	private int          salle;

	/**
	 * Créée un Jury
	 * @param numJury : numéro du Jury
	 * @param date : date d'activité du Jury
	 * @param heureD : heure du début de l'activité du Jury
	 * @param heureF : heure de fin de l'activité du Jury
	 * @param lstProf : liste des Professeurs du Jury
	 * @param salle : salle du Jury
	 */
	public Jury(int numJury, String date, int heureD, int heureF, List<String> lstProf, int salle)
	{

		this.numJury = numJury;
		this.date    = date;
		this.heureD  = heureD;
		this.heureF  = heureF;
		this.lstProf = List.copyOf(lstProf);
		this.salle   = salle;

	}

	
	
	public int          getnumJury  (){ return this.numJury;}
	public String       getDate     (){ return this.date;   }	
	public int          getHeureDeb (){ return this.heureD; }
	public int          getHeureFin (){ return this.heureF; }
	public List<String> getLstProf  (){ return this.lstProf;}
	public int          getSalle    (){ return this.salle;  }


	/**
	 * permet de comparer deux jurys entre eux en fonction de la date, de l'heure puis de l'heure numéro de jury
	 * @param j : jury à comparer
	 * @return i : valeur retourner par la comparaison
	 */
	public int compareTo(Jury j) 
	{
		int i = this.date.compareTo(j.date);
		if (i == 0)
			i = this.heureD - j.heureD;
				if(i == 0)
					i = this.numJury - j.numJury;

		return i;
	}


	public String toString ()
	{
		String sRet = "";

		sRet += "Jury " + this.numJury + '\n';

		for( String p : lstProf)
		{
			sRet += p + "\n";
		}


		return sRet;
	}
	
	

}
