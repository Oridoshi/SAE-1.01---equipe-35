package exercice1;

import java.io.*;   
import java.util.*;
import iut.algo.*;
import java.nio.charset.StandardCharsets;

/** Exercice 2 : creation des plannings
 *  SAE n°1 Semestre 1
 *  @author  Caron Antoine, Vicente Hugo, Bouloche Eleonore, Bondu Justine, Dunet Tom
 *  @version 2 du 09/01/2023
 */



public class Exercice1
{
	public static void main(String[] args) 
	{
		/*--------------*/
		/*  variables   */
		/*--------------*/
		List<Etudiant>      lstEtu            = new ArrayList<Etudiant>();
		
		int                 nbEtuEquipe;
		List<List<Integer>> lstInfoSalle      = new ArrayList<List<Integer>>();

		boolean             bTriCategori      = false;

		List<Equipe>        lstEquipe         = new ArrayList<Equipe>();   

		/*--------------*/
		/* instructions */
		/*--------------*/

		/* ETUDIANTS */
		// Lecture du fichier qui contient les étudiants
		Exercice1.lectureEtudiant("./donnees/promotion.data", lstEtu);


		// Trie ordre Alphabétique des étudiants
		Collections.sort(lstEtu);
		
		
		// Choix utilisateur pour trie par catégorie des étudiants
		System.out.print("Voulez-vous trier les groupes par catégorie?(O/N) ");
		if (Character.toUpperCase(Clavier.lire_char()) == 'O')
		{
			bTriCategori = true;
			Collections.sort(lstEtu, Etudiant.Comparators.CATEGORIE);
		}
		


		/* SALLE */

		// Lecture du fichier resources et ajout dans informations dans les tableaux
		nbEtuEquipe = Exercice1.lectureSalle("./donnees/ressources.data", lstInfoSalle);		

		
		/* EQUIPE */
		Exercice1.creationEquipe(lstEtu, lstInfoSalle, nbEtuEquipe, bTriCategori, lstEquipe);
	


		/* AFFICHAGE */
		System.out.println(Exercice1.affichage(lstEquipe));
	}

	/**
	 * Permet la création et le remplissage du tableau d'étudiant tabEtu avec les information d'un ficher
	 * @param nomFichier : nom du fichier lu
	 * @param lstEtu : liste des étudiants
	 */
	public static void lectureEtudiant(String nomFichier, List<Etudiant> lstEtu)
	{
		try
			{
				/*--------------*/
				/*  variables   */
				/*--------------*/
				
				Scanner     sc = new Scanner ( new FileReader ( nomFichier, StandardCharsets.UTF_8 ) );
				Decomposeur dec;
				String      ligne;
				String      nom, prenom; 
				char        groupe, categorie;
				int         cpt;

				/*--------------*/
				/* instructions */
				/*--------------*/

				cpt = 0;
				
				if(sc.hasNextLine())
					sc.nextLine();

				while ( sc.hasNextLine() )
				{
					ligne     = sc.nextLine();
					dec       = new Decomposeur(ligne);
					nom       = dec.getString(0);
					prenom    = dec.getString(1);
					groupe    = dec.getChar(2);
					categorie = dec.getChar(3);

					lstEtu.add(cpt, new Etudiant(nom, prenom, groupe, categorie));

					cpt++; 
				}
				sc.close();
			}
			catch (Exception e){ e.printStackTrace(); }
	}


	/**
	 * Lis un fichier contennat les infos des salles et les inscrit dans une liste et retourne le nombre d'étudiant par équipe
	 * @param nomFichier : nom du fichier lu
	 * @param lstInfoSalle : liste de liste des informations de la salle
	 * @return iRet : nombre d'équipes maximale par salle
	 */
	public static int lectureSalle(String nomFichier, List<List<Integer>> lstInfoSalle)
	{
		try
			{
				/*--------------*/
				/*  variables   */
				/*--------------*/
				
				Scanner     sc = new Scanner ( new FileReader ( nomFichier, StandardCharsets.UTF_8 ) );
				Decomposeur dec;
				String      ligne;
				int         cpt;
				int         iRet;

				/*--------------*/
				/* instructions */
				/*--------------*/
				
				iRet  = Integer.valueOf(sc.nextLine());

				cpt   = 0;
				while ( sc.hasNextLine() )
				{
					ligne  = sc.nextLine();
					dec    = new Decomposeur(ligne);
					
					lstInfoSalle.add(new ArrayList<Integer>());
				
					lstInfoSalle.get(cpt).add(0, dec.getInt(0));
					lstInfoSalle.get(cpt).add(1, dec.getInt(1));

					cpt++;
				}
			
				sc.close();

				return iRet;
			}
			catch (Exception e){ e.printStackTrace(); }

		return 0; // cas qui n'arrive jamais
	}


	/**
	 * créer une liste d'équipe en fonction des étudiant, des salle et du nombre d'étudiant par équipe
	 * @param lstEtu : liste des étudiants
	 * @param lstInfoSalle : liste de liste des informations de la salle
	 * @param nbEtuEquipe : nombre d'étudiants par équipe
	 * @param bTriCategori : booléen qui permet ou non le tri par catégorie
	 * @param lstEquipe : liste des équipes
	 */
	public static void creationEquipe(List<Etudiant> lstEtu, List<List<Integer>> lstInfoSalle, int nbEtuEquipe, boolean bTriCategori, List<Equipe> lstEquipe)
	{
		List<Etudiant> lstGroupe = new ArrayList<Etudiant>();
		int            nEtu;

		int            salle = 0;

		List<Etudiant> lstGroupeTemp;
		List<Etudiant> lstGroupeTemp2;
		int            nbGroupe = lstEtu.size() / nbEtuEquipe + 1;

		int            cptSalle = 0;

		lstGroupeTemp2 = new ArrayList<Etudiant>(); 
		nEtu = 0;

		for (int nEquipe = 1; nEquipe <= nbGroupe; nEquipe++) 
		{
			if ( lstInfoSalle.get(cptSalle).get(1) > 0 )
			{
				salle = lstInfoSalle.get(cptSalle).get(0);
				lstInfoSalle.get(cptSalle).set(1, lstInfoSalle.get(cptSalle).get(1) - 1) ;
			}

			else
			{
				if ( cptSalle < lstInfoSalle.size() - 1 )
				{
					cptSalle++;

					salle = lstInfoSalle.get(cptSalle).get(0);
					lstInfoSalle.get(cptSalle).set(1, lstInfoSalle.get(cptSalle).get(1) - 1) ;
				}
			}

			if ( !bTriCategori )
			{
				for (int cpt = 0; cpt < nbEtuEquipe && nEtu < lstEtu.size(); nEtu++, cpt++)
				{
					lstGroupe.add(lstEtu.get(nEtu));
				}
			}
			else
			{
				if(nEtu < lstEtu.size())
				{
					lstGroupe.add(lstEtu.get(nEtu));
					nEtu ++;
					
					for (int cpt = 1; cpt < nbEtuEquipe && nEtu < lstEtu.size() && lstEtu.get(nEtu).getCatego() == lstGroupe.get(cpt-1).getCatego() ; nEtu++, cpt++)
					{
						lstGroupe.add(lstEtu.get(nEtu));
					}
				}
			}

			if (lstGroupe.size() == nbEtuEquipe)
			{
				lstEquipe.add(new Equipe( nEquipe, lstGroupe, salle));
			}
			else
			{
				Collections.reverse(lstGroupe);
				for(int cpt = 1; cpt <= lstGroupe.size(); cpt++)
				{
					if(cpt == 1)
						nEquipe--;
						
					lstGroupeTemp = (List.copyOf(lstEquipe.get(nEquipe - cpt).getLstEtud()));
					
					for (Etudiant e : lstGroupeTemp)
					{
						lstGroupeTemp2.add(e);
					}
					
					lstGroupeTemp2.add(lstGroupe.get(cpt - 1)); 
					lstEquipe.get(nEquipe - cpt).setGroupe(lstGroupeTemp2);

					lstGroupeTemp2.clear();
				}
			}
		
			lstGroupe.clear();
		} 
	}

	public static String affichage(List<Equipe> lstEquipe)
	{
		String sRet = "";
		for (Equipe equipe : lstEquipe) 
		{
			sRet += equipe.toString();
			for (int cpt = 0; cpt < equipe.getLstEtud().size(); cpt++) 
				sRet += equipe.getLstEtud().get(cpt).toString() + '\n';

			sRet += '\n';
		}

		return sRet;
	}
}
