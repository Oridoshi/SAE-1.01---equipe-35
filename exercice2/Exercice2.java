package exercice2;

import java.io.*;   
import java.util.*;
import iut.algo.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/** Exercice 1 : creation des plannings
 *  SAE n°1 Semestre 1
 *  @author  Caron Antoine, Vicente Hugo, Bouloche Eleonore, Bondu Justine, Dunet Tom
 *  @version 1 du 09/01/2023
 */



public class Exercice2
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
		List<Jury>		    lstJury           = new ArrayList<Jury>();    

		List<List<Integer>> lstHJury          = new ArrayList<List<Integer>>();
		List<Integer>       lstDureeOralPause = new ArrayList<Integer>();

		/*--------------*/
		/* instructions */
		/*--------------*/

		/* ETUDIANTS */
		// Lecture du fichier qui contient les étudiants
		Exercice2.lectureEtudiant("./donnees/promotion.data", lstEtu);


		// Trie ordre Alphabétique des étudiants
		Collections.sort(lstEtu);
		
		
		// Choix utilisateur pour trie par catégorie des étudiants
		System.out.print("Voulez-vous des groupes par catégories ?(O/N) ");
		if (Character.toUpperCase(Clavier.lire_char()) == 'O')
		{
			bTriCategori = true;
			Collections.sort(lstEtu, Etudiant.Comparators.CATEGORIE);
		}
		


		/* SALLE */

		// Lecture du fichier resources et ajout dans informations dans les tableaux
		nbEtuEquipe = Exercice2.lectureSalle("./donnees/ressources.data", lstInfoSalle);		

		
		/* EQUIPE */
		Exercice2.creationEquipe(lstEtu, lstInfoSalle, nbEtuEquipe, bTriCategori, lstEquipe);
	

		/* JURY */
		Exercice2.creationJurys("./donnees/jury.data", lstJury, lstDureeOralPause);
		
		Collections.sort(lstJury);

		lstHJury = Exercice2.juryHorEqu(lstDureeOralPause.get(0), lstDureeOralPause.get(1), lstJury, lstEquipe);
		

		/* AFFICHAGE */
		System.out.println(Exercice2.affichage(lstJury, lstEquipe, lstHJury, lstDureeOralPause.get(0)));
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



/*-------------------------------------------------------------------------------------*/
/*-------------------------------------------------------------------------------------*/
/*-------------------------------------------------------------------------------------*/
/*-------------------------------------------------------------------------------------*/
/*----------------------------   Partie Exercice 2    ---------------------------------*/
/*-------------------------------------------------------------------------------------*/
/*-------------------------------------------------------------------------------------*/
/*-------------------------------------------------------------------------------------*/
/*-------------------------------------------------------------------------------------*/



	/**
	 * lis le fichier contenant les jurys et créer des objets "Jury" qui sont stockées dans une liste "lstJury"
	 * @param nomFichier : nom du fichier lu
	 * @param lstJury : liste des jurys
	 * @param lstDureeOralPause : liste des durées des oraux et des pauses
	 */
	public static void creationJurys(String nomFichier, List<Jury> lstJury, List<Integer> lstDureeOralPause)
	{
		try
		{
			/*--------------*/
			/*  Variables   */
			/*--------------*/
			Scanner      sc      = new Scanner ( new FileReader ( nomFichier, StandardCharsets.UTF_8 ) );
			String       ligne;
			

			Decomposeur  dec;

			int          numJury;
			String       temp;
			String       date;
			String       heureDTemp;
			String       heureFTemp;
			int          heureD;
			int          heureF;
			int          salle;
			List<String> lstProf = new ArrayList<String>();

			List<String> lstTemp;
				

			/*--------------*/
			/* Instructions */
			/*--------------*/
			if (sc.hasNextLine())
			{
				ligne = sc.nextLine();
				dec   = new Decomposeur(ligne);

				lstDureeOralPause.add(dec.getInt(0));
				lstDureeOralPause.add(dec.getInt(1));
			}


			while(sc.hasNextLine())
			{
				ligne      = sc.nextLine();
				dec        = new Decomposeur(ligne);
		
				temp       = dec.getString(0);
				numJury    = Integer.valueOf(temp.charAt(temp.length() - 1) + "");

				salle      = dec.getInt(1);

				date       = dec.getString(2);

				heureDTemp = dec.getString(3);
				heureD     = Exercice2.timeToInt(heureDTemp);
				
				heureFTemp = dec.getString(4);
				heureF     = Exercice2.timeToInt(heureFTemp);
				

				lstTemp    = Arrays.asList(ligne.split("\t"));
				
				for (int cpt = 5; cpt < lstTemp.size(); cpt++) 
					lstProf.add(lstTemp.get(cpt));	

				lstJury.add(new Jury(numJury, date, heureD, heureF, lstProf, salle));

				lstProf.clear();
			}
			sc.close();
		}
		catch (Exception e){ e.printStackTrace(); }

	}

	/**
	 * transforme une date sous la forme "25/12/2022" sous la forme "Dimanche 25 Décembre 2022"
	 * @param sDate : date sous forme de châine de caractère "25/12/2022"
	 * @return sRet : date sous forme de châine de caractère "Dimanche 25 Décembre 2022"
	 */
	public static String dateToString(String sDate)
	{
		final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
		SimpleDateFormat formater;

		String   sRet = "";
		String[] tabTemp;
		
		Date     date = null;
		
		try 
		{
			date       = dateFormat.parse(sDate);
			
			formater   = new SimpleDateFormat("EEEE d MMMM yyyy");
			
			sRet       = formater.format(date);

			tabTemp    = sRet.split(" ");

			tabTemp[0] = Character.toUpperCase(tabTemp[0].charAt(0)) + tabTemp[0].substring(1);
			
			tabTemp[2] = Character.toUpperCase(tabTemp[2].charAt(0)) + tabTemp[2].substring(1);

			sRet = "";
			
			for (String string : tabTemp) 
				sRet += string + " ";		
		} 
		catch (Exception e)
		{ 
			e.printStackTrace();
			System.out.println("La date n'est pas au bon format."); 
		}

		return sRet;
	}


	/**
	 * transforme l'heure sous la forme "09:11" et retourne le resultat sous forme de minutes "551"
	 * @param temps : temps sous forme de chaîne de caractère "09:11"
	 * @return minutes : sous forme d'entier "551"
	 */
	public static int timeToInt (String temps)
	{
		return Integer.valueOf(temps.split(":")[0]) * 60 + Integer.valueOf(temps.split(":")[1]);
	}




	/**
	 * créer la liste des horaires de chaque Jurys
	 * @param orauxDur : durée d'un oral
	 * @param pauseDur : durée de la pause entre chaque oral
	 * @param jurys : liste des jurys
	 * @param equipes : liste des équipes
	 * @return listHJurys : liste de liste des horaires des jurys
	 */
	public static List<List<Integer>> juryHorEqu (int orauxDur, int pauseDur, List<Jury> jurys, List<Equipe> equipes)
	{
		int nbEquipesRestant;

		int heureD = 0;
		int heureF = 0;

		List<List<Integer>> listHJurys = new ArrayList<List<Integer>>();

		
		nbEquipesRestant = equipes.size();
		
		
		for(int cpt = 0; cpt < jurys.size(); cpt++)
		{
			heureD = jurys.get(cpt).getHeureDeb();
			heureF = jurys.get(cpt).getHeureFin();

			listHJurys.add(new ArrayList<Integer>());
			nbEquipesRestant -= horOraux(heureD, heureF, orauxDur, pauseDur, nbEquipesRestant, listHJurys.get(cpt), cpt);
		}

		return listHJurys;

	}

	/**
	 * Ajoutes les horaires valides aux différents Jurys
	 * @param heureD : heure de début de l'oral
	 * @param heureF : heure de fin de l'oral
	 * @param orauxDur : durée d'un oral
	 * @param pauseDur : durée de pause entre les oraux
	 * @param nbEquipesRestant : nombre d'équipes restantes
	 * @param listHJurys : liste de liste des horaires des jurys
	 * @param numJury : numéro du jury
	 * @return cpt : nombre d'équipes passé à l'oral
	 */
	public static int horOraux (int heureD, int heureF, int orauxDur, int pauseDur, int nbEquipesRestant, List<Integer> listHJurys, int numJury)
	{
		int nbEquipe;

		for (nbEquipe = 0; heureF >= (heureD + orauxDur) && nbEquipe < nbEquipesRestant; nbEquipe++)
		{
			listHJurys.add(heureD); 
			heureD += orauxDur + pauseDur;
		}

		return nbEquipe;
	}
	
	/**
	 * transforme des minutes sous forme de chaîne de caractère "9h11"
	 * @param minutes : temps en minutes
	 * @return sRet : heure sous la forme de chaîne de caractère "9h11"
	 */ 
	public static String minutesToHeure(int minutes)
	{
		String sRet   = " ";

		int minuteAff = 0;
		int heureAff  = 0;

		minuteAff = minutes % 60;
		heureAff  = minutes / 60;

		sRet = String.format("%2d", heureAff) + "h" + String.format("%02d",minuteAff);	

		return sRet;
	}


	/**
	 * affichage final
	 * @param lstJury : liste des jurys
	 * @param lstEquipe : liste des équipes
	 * @param lstHJury : liste de liste des horaires des jurys
	 * @param tempsOral : durée d'un oral
	 * @return sRet : chaîne de caractère contennant les données à afficher
	 */
	public static String affichage(List<Jury> lstJury, List<Equipe> lstEquipe, List<List<Integer>> lstHJury, int tempsOral)
	{
		String sRet = "";
		int    numEquipe = 1;

		for (int cptJury = 0; cptJury < lstJury.size(); cptJury++) 
		{
			if (cptJury == 0 || !(lstJury.get(cptJury-1).getDate().equals(lstJury.get(cptJury).getDate()))) 
			{
				String date = Exercice2.dateToString(lstJury.get(cptJury).getDate());

				sRet += String.format("%" + (date.length() - 1) + "s", "-").replace(" ", "-") + '\n' + date + '\n' + 
				        String.format("%" + (date.length() - 1) + "s", "-").replace(" ", "-") + '\n';
			}

			sRet += '\n' + lstJury.get(cptJury).toString() + '\n';


			/* affiche les horaires et les groupes */
			for (int cptH = 0; cptH < lstHJury.get(cptJury).size(); cptH++, numEquipe++) 
				sRet += Exercice2.minutesToHeure(lstHJury.get(cptJury).get(cptH)) + " à "      +
				        Exercice2.minutesToHeure(lstHJury.get(cptJury).get(cptH)  + tempsOral) + 
				        " équipe " + String.format("%2d", numEquipe) + " salle "     + lstJury.get(cptJury).getSalle() + '\n';

			sRet += '\n';
		}

		return sRet;
	}
}