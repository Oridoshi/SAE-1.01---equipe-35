package exercice3;

import java.io.*;
import java.util.*;
import iut.algo.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.Desktop;

/** Exercice 1 : creation des equipes
 *  SAE n°1 Semestre 1
 *  @author  Caron Antoine, Vicente Hugo, Bouloche Eleonore, Bondu Justine, Dunet Tom
 *  @version 1 du 05/01/2023
 */


public class Exercice3 
{
	public static void main(String[] a)
	{
		/*--------------*/
		/*  variables   */
		/*--------------*/
		List<Etudiant>      lstEtu = new ArrayList<Etudiant>();
		List<Equipe>        lstEquipe = new ArrayList<Equipe>();
		
		int                 nbEtuEquipe;
		List<List<Integer>> lstInfoSalle = new ArrayList<List<Integer>>();

		boolean             bTriCategori = false;

		List<Jury>          lstJury           = new ArrayList<Jury>();    

		List<List<Integer>> lstHJury          = new ArrayList<List<Integer>>();
		List<Integer>       lstDureeOralPause = new ArrayList<Integer>();

		/*--------------*/
		/* instructions */
		/*--------------*/

		/* ETUDIANTS */
		// Lecture du fichier qui contient les étudiants
		Exercice3.lectureEtudiant("./donnees/promotion.data", lstEtu);


		// Trie ordre Alphabétique des étudiants
		Collections.sort(lstEtu);
		
		// trie ordre alphabétique  des catégorie
		bTriCategori = true;
		Collections.sort(lstEtu, Etudiant.Comparators.CATEGORIE);

		/* SALLE */

		// Lecture du fichier resources et ajout dans informations dans les tableaux
		nbEtuEquipe = Exercice3.lectureSalle("./donnees/ressources.data", lstInfoSalle);		

		
		/* EQUIPE */
		Exercice3.creationEquipe(lstEtu, lstInfoSalle, nbEtuEquipe, bTriCategori, lstEquipe);


		/* JURY */
		Exercice3.creationJurys("./donnees/jury.data", lstJury, lstDureeOralPause);
		
		Collections.sort(lstJury);

		lstHJury = Exercice3.juryHorEqu(lstDureeOralPause.get(0), lstDureeOralPause.get(1), lstJury, lstEquipe);

		/* Création des page HTML */
		Exercice3.creationHTML(lstEtu, lstEquipe, lstJury, lstHJury, lstDureeOralPause);

		/* Ouverture Accueil */
		Exercice3.ouvrirAccueil("./fichier_html/index.html");
	}




		/**
		 * créée les différentes pages html et fichiers css
		 * @param lstEtu : liste des étudiants
		 * @param lstEquipe : liste des équipes
		 * @param lstJury : liste des jurys
		 * @param lstHJury : liste de liste des horaires de jurys
		 * @param lstDureeOralPause : liste des durées des oraux, et des pauses
		 */
		public static void creationHTML(List<Etudiant> lstEtu, List<Equipe> lstEquipe, List<Jury> lstJury, List<List<Integer>> lstHJury, List<Integer> lstDureeOralPause)
		{
			int                 EquipeActu;
			int                 nbEquipeParColonne;
			int                 nbEquipeActu;

			/* Création de la Page 1 en HTML */

			try
			{
				Collections.sort(lstEtu);
				PrintWriter pw = new PrintWriter("./fichier_html/page1.html", "UTF-8");
				pw.println(
					"<!DOCTYPE html>\n" +
					"<html lang=\"fr\">\n" +
					"	<head>\n" +
					"		<meta charset=\"UTF-8\">\n" +
					"		<meta name=\"author\" content=\"Justine BONDU, Eleonore BOULOCHE, Antoina CARON, Tom DUNET, Hugo VICENTE\">\n" +
					"		<link rel=\"stylesheet\" href=\"./Styles/header.css\" type=\"text/css\">\n" +
					"		<link rel=\"stylesheet\" href=\"./Styles/page1.css\" type=\"text/css\">\n" +
					"		<link rel=\"icon\" href=\"./Images/PlanIutLogoComplet.png\" type=\"text/css\">\n" +
					"		<title>Exercice 3 : Page 1</title>\n" +
					"	</head>\n" +
					"	<body >\n" +
					"		<header>\n" +
					"			<a href=\"https://validator.w3.org/#validate_by_upload\"> <img src=\"./Images/PlanIutLogoComplet.png\" title=\"Logo\" alt=\"Logo de l'app\"></a>\n" +
					"			<a href=\"index.html\" title=\"Plan IUT\"> <h1>Plan IUT</h1></a>\n" +
					"			<nav>\n" +
					"				<a href=\"page1.html\" title=\"Liste des étudiants\"> Liste des étudiants</a>\n" +
					"				<a href=\"page2.html\" title=\"Liste des équipe\"> Liste des équipes</a>\n" +
					"				<a href=\"page3.html\" title=\"Planning\"> Planning</a>\n" +
					"			</nav>\n" +
					"		</header>\n" +
					"		<p ID=\"titre\"> Liste des étudiants </p>\n" +
					"		<div class=\"scrollable-div\">\n" +
					"			<table>\n" +
					"				<tr >\n" +
					"					<th > Nom </th >\n" +
					"					<th > Prénom </th >\n" +
					"					<th > Groupe </th >\n" +
					"					<th class=\"equipe\"> N° équipe </th >\n" +
					"				</tr >\n");

					for(int cpt = 0; cpt < lstEtu.size(); cpt++)
						pw.println(
							"				<tr>\n"+
							"					<td>" + lstEtu.get(cpt).getNom() + "</td>\n" +
							"					<td>" + lstEtu.get(cpt).getPrenom() + "</td>\n" +
							"					<td>" + lstEtu.get(cpt).getGroupe() + "</td>\n" +
							"					<td class=\"equipe\">" + lstEtu.get(cpt).getEquipe() + "</td>\n" +
							"				</tr>\n");

					pw.println(
						"				</table >\n" +
						"		</div>\n" +
						"		<footer>\n" +
						"			<br> \n" +
						"			<p>© 2023 par BONDU Justine, BOULOCHE Eleonore, CARON Antoine, DUNET Tom, VICENTE Hugo</p>\n" +
						"		</footer>\n" +
						"	</body>\n" +
						"</html>");
				pw.close();
			}
			catch (Exception e){ e.printStackTrace(); }

			/* Génération de la page 2 en HTML */
			try
			{
				PrintWriter pw = new PrintWriter("./fichier_html/page2.html", "UTF-8");
				pw.println(
					"<!DOCTYPE html>\n" +
					"<html lang=\"fr\">\n" +
					"	<head>\n" +
					"		<meta charset=\"UTF-8\">\n" +
					"		<meta name=\"author\" content=\"Justine BONDU, Eleonore BOULOCHE, Antoina CARON, Tom DUNET, Hugo VICENTE\">\n" +
					"		<link rel=\"stylesheet\" href=\"./Styles/header.css\" type=\"text/css\">\n" +
					"		<link rel=\"stylesheet\" href=\"./Styles/page2.css\" type=\"text/css\">\n" +
					"		<link rel=\"icon\" href=\"./Images/PlanIutLogoComplet.png\" type=\"text/css\">\n" +
					"		<title>Exercice 3 : Page 2</title>\n" +
					"	</head>\n" +
					"	<body >\n" +
					"		<header>\n" +
					"			<a href=\"https://validator.w3.org/#validate_by_upload\"> <img src=\"./Images/PlanIutLogoComplet.png\" title=\"Logo\" alt=\"Logo de l'app\"></a>\n" +
					"			<a href=\"index.html\" title=\"Plan IUT\"> <h1>Plan IUT</h1></a>\n" +
					"			<nav>\n" +
					"				<a href=\"page1.html\" title=\"Liste des étudiants\"> Liste des étudiants</a>\n" +
					"				<a href=\"page2.html\" title=\"Liste des équipe\"> Liste des équipes</a>\n" +
					"				<a href=\"page3.html\" title=\"Planning\"> Planning</a>\n" +
					"			</nav>\n" +
					"		</header>\n" +
					"		<p ID=\"titre\">Liste des équipes</p>\n" +
					"		<div>");

					nbEquipeParColonne = lstEquipe.size()/ 3;
					nbEquipeActu       = 0;

					for(int cpt1 = 0; cpt1 < 3; cpt1++)
					{
						pw.println("			<div>");
						for(int cpt2 = 0; cpt2 < nbEquipeParColonne; cpt2++, nbEquipeActu++)
						{
						pw.println(
							"				<table class=\"groupcompl\" ID=\"equipe" + (nbEquipeActu + 1) +"\">\n" +
							"					<tr >\n" +
							"						<td >" + lstEquipe.get(nbEquipeActu).getNumero() + "</td>\n" +
							"						<td >\n" +
							"							<table class=\"groupe\">");

							for(int cpt3 = 0; cpt3 < lstEquipe.get(nbEquipeActu).getLstEtud().size(); cpt3++)
							{
								pw.println(
								"								<tr >\n" +
								"									<td >" + lstEquipe.get(nbEquipeActu).getLstEtud().get(cpt3).getNom() + "</td >\n" +
								"									<td >" + lstEquipe.get(nbEquipeActu).getLstEtud().get(cpt3).getPrenom() + "</td >\n" +
								"									<td >" + lstEquipe.get(nbEquipeActu).getLstEtud().get(cpt3).getGroupe() + "</td >\n" +
								"								</tr >");
							}

						pw.println(
							"							</table>\n" +
							"						</td>\n" +
							"						<td >" + lstEquipe.get(nbEquipeActu).getSalle() + "</td>\n" +
							"					</tr>\n" +
							"				</table>\n");

						if (((lstEquipe.size()/ 3.0 > nbEquipeParColonne &&  // permet de regarder si il y a besoin de mettre une equipe dans la deuxième ou troisième colonne si il n'y a pas assez d'équipe pour faire une dernière ligne
							  lstEquipe.size()/ 3.0 >= nbEquipeParColonne + 0.5) && cpt1 == 1) ||
							((lstEquipe.size()/ 3.0 > nbEquipeParColonne &&
							((lstEquipe.size()/ 3.0 < nbEquipeParColonne + 0.5) ||
							  lstEquipe.size()/ 3.0 > nbEquipeParColonne)) && cpt1 == 2 ))
								if ( cpt2 == nbEquipeParColonne - 1 )
								{
									nbEquipeActu++;

									pw.println(
										"				<table class=\"groupcompl\" ID=\"equipe" + (nbEquipeActu + 1) + "\">\n" +
										"					<tr >\n" +
										"						<td >" + lstEquipe.get(nbEquipeActu).getNumero() + "</td>\n" +
										"						<td >\n" +
										"							<table class=\"groupe\">");
									
									for(int cpt3 = 0; cpt3 < lstEquipe.get(nbEquipeActu).getLstEtud().size(); cpt3++)
										pw.println(
											"								<tr >\n" +
											"									<td >" + lstEquipe.get(nbEquipeActu).getLstEtud().get(cpt3).getNom() + "</td >\n" +
											"									<td >" + lstEquipe.get(nbEquipeActu).getLstEtud().get(cpt3).getPrenom() + "</td >\n" +
											"									<td >" + lstEquipe.get(nbEquipeActu).getLstEtud().get(cpt3).getGroupe() + "</td >\n" +
											"								</tr >");

									pw.println(
										"						</table>\n" +
										"						</td>\n" +
										"						<td >" + lstEquipe.get(nbEquipeActu).getSalle() + "</td>\n" +
										"					</tr>\n" +
										"				</table>\n");
								}
						}

					pw.println("			</div>");
					}
					
					pw.println(
						"		</div>\n" +
						"		<footer>\n" +
						"			<br>\n" +
						"			<p>© 2023 par BONDU Justine, BOULOCHE Eleonore, CARON Antoine, DUNET Tom, VICENTE Hugo</p>\n" +
						"		</footer>\n" +
						"	</body>\n" +
						"</html>");
				pw.close();
			}
			catch (Exception e){ e.printStackTrace(); }

			/* Génération de la page 3 en HTML */
			try
			{
				PrintWriter pw = new PrintWriter("./fichier_html/page3.html", "UTF-8");
				pw.println(
					"<!DOCTYPE html>\n" +
					"<html lang=\"fr\">\n" +
					"	<head>\n" +
					"		<meta charset=\"UTF-8\">\n" +
					"		<meta name=\"author\" content=\"Justine BONDU, Eleonore BOULOCHE, Antoina CARON, Tom DUNET, Hugo VICENTE\">\n" +
					"		<link rel=\"stylesheet\" href=\"./Styles/header.css\" type=\"text/css\">\n" +
					"		<link rel=\"stylesheet\" href=\"./Styles/page3.css\" type=\"text/css\">\n" +
					"		<link rel=\"icon\" href=\"./Images/PlanIutLogoComplet.png\" type=\"text/css\">\n" +
					"		<title>Exercice 3 : Page 3</title>\n" +
					"	</head>\n" +
					"	<body >\n" +
					"		<header>\n" +
					"			<a href=\"https://validator.w3.org/#validate_by_upload\"> <img src=\"./Images/PlanIutLogoComplet.png\" title=\"Logo\" alt=\"Logo de l'app\"></a>\n" +
					"			<a href=\"index.html\" title=\"Plan IUT\"> <h1>Plan IUT</h1></a>\n" +
					"			<nav>\n" +
					"				<a href=\"page1.html\" title=\"Liste des étudiants\"> Liste des étudiants</a>\n" +
					"				<a href=\"page2.html\" title=\"Liste des équipe\"> Liste des équipes</a>\n" +
					"				<a href=\"page3.html\" title=\"Planning\">Planning</a>\n" +
					"			</nav>\n" +
					"		</header>\n" +
					"		<p ID=\"titre\">Planning</p>");

					EquipeActu = 1;
					for(int cpt1 = 0; cpt1 < lstJury.size(); cpt1++)
					{
						if (cpt1 == 0 || !(lstJury.get(cpt1-1).getDate().equals(lstJury.get(cpt1).getDate())))
						{
							if( cpt1 != 0)
								pw.println("</div>");

							pw.println(
								"		<div>\n" +
								"			<h2 class = 'titre1'>" + Exercice3.dateToString(lstJury.get(cpt1).getDate()) + "</h2>");
						}
					pw.println(
						"			<section>\n" +
						"				<h2> Jury " + lstJury.get(cpt1).getnumJury() + "</h2>\n" +
						"				<div>\n" +
						"					<ul>");

					for(int cpt2 = 0; cpt2 < lstJury.get(cpt1).getLstProf().size(); cpt2++)
						pw.println(
						"						<li>" + lstJury.get(cpt1).getLstProf().get(cpt2) + "</li>");

					pw.println(
						"					</ul>\n" +
						"				</div>\n" +
						"				<table>\n" +
						"					<thead>\n" +
						"						<tr>\n" +
						"							<th colspan = '2' class = \"horaire\"> Horaires de passage : </th>\n" +
						"						</tr>\n" +
						"					</thead>\n" +
						"					<tbody>");

					for(int cpt2 = 0; cpt2 < lstHJury.get(cpt1).size(); cpt2++ , EquipeActu++)
						pw.println(
							"						<tr>\n" +
							"							<td class = \"heureDebut\"  >" + Exercice3.minutesToHeure(lstHJury.get(cpt1).get(cpt2)) + "</td>\n" +
							"							<td class = \"passage\"    > Groupe " + EquipeActu + " </td>\n" +
							"						</tr>\n" +
							"\n" +
							"						<tr>\n" +
							"							<td class = \"heureFin\"    >" + Exercice3.minutesToHeure( lstHJury.get(cpt1).get(cpt2) + lstDureeOralPause.get(0) )+ "</td>\n" +
							"							<td class = \"passage\"     >" + lstJury.get(cpt1).getSalle() + "</td>\n" +
							"						</tr>\n");

					pw.println(
						"					</tbody>\n" +
						"				</table>\n" +
						"			</section>");
					}

					pw.println(
						"		</div>\n" +
						"		<footer>\n" +
						"			<br>\n" +
						"			<p>© 2023 par BONDU Justine, BOULOCHE Eleonore, CARON Antoine, DUNET Tom, VICENTE Hugo</p>\n" +
						"		</footer>\n" +
						"	</body>\n" +
						"</html>");
				pw.close();
			}
			catch (Exception e){ e.printStackTrace(); }

			/* Génération de la page d'acceuil en HTML */
			try
			{
				PrintWriter pw = new PrintWriter("./fichier_html/index.html", "UTF-8");
				pw.println(
					"<!DOCTYPE html>\n" +
					"<html lang=\"fr\">\n" +
					"	<head>\n" +
					"		<meta charset=\"UTF-8\">\n" +
					"		<meta name=\"author\" content=\"Justine BONDU, Eleonore BOULOCHE, Antoina CARON, Tom DUNET, Hugo VICENTE\">\n" + 
					"		<link rel=\"stylesheet\" href=\"./Styles/header.css\" type=\"text/css\">\n" +
					"		<link rel=\"stylesheet\" href=\"./Styles/Page_acc.css\" type=\"text/css\">\n" +
					"		<link rel=\"icon\" href=\"Images/PlanIutLogoComplet.png\" type=\"text/css\">\n" +
					"		<title>Exercice 3 : Accueil</title>\n" +
					"	</head>\n" +
					"	<body >\n" +
					"		<header>\n" +
					"			<a href=\"https://validator.w3.org/#validate_by_upload\"> <img src=\"Images/PlanIutLogoComplet.png\" title=\"Logo\" alt=\"Logo de l'app\"></a>\n" +
					"			<h1>Plan IUT</h1>\n" +
					"			<nav>\n" +
					"				<a href=\"Page1.html\" title=\"Liste des étudiants\"> Liste des étudiants</a>\n" +
					"				<a href=\"Page2.html\" title=\"Liste des équipe\">Liste des équipes</a>\n" +
					"				<a href=\"Page3.html\" title=\"Planning\"> Planning</a>\n" +
					"			</nav>\n" +
					"		</header>\n" +
					"		<p ID=\"titre\">Page d'Accueil</p>\n" +
					"\n" +
					"		<div>\n" +
					"			<button class=\"button\" onclick=\"window.location.href='page1.html'\">Liste des étudiants</button>\n" +
					"		</div>\n" +
					"\n" +
					"		<div>\n" +
					"			<button class=\"button\" onclick=\"window.location.href='page2.html'\">Liste des équipes</button>\n" +
					"		</div>\n" +
					"\n" +
					"		<div>\n" +
					"			<button class=\"button\" onclick=\"window.location.href='page3.html'\">Planning</button>\n" +
					"		</div>\n" +
					"\n" +
					"		<footer>\n" +
					"			<br>\n" +
					"			<p>© 2023 par BONDU Justine, BOULOCHE Eleonore, CARON Antoine, DUNET Tom, VICENTE Hugo</p>\n" +
					"		</footer>\n" +
					"\n" +
						"</body>\n" +
					"</html>");
				pw.close();
			}
			catch (Exception e){ e.printStackTrace(); }

			/*-------------------*/
			/* Génération du CSS */
			/*-------------------*/

			/* Génération de Header.css */
			try
			{
				PrintWriter pw = new PrintWriter("./fichier_html/Styles/header.css", "UTF-8");
				pw.println(
					"#titre{\n" +
					"	margin-top: 35px;\n" +
					"	background-color: white;\n" +
					"	margin-bottom: 30px;\n" +
					"	border: 1px solid black;\n" +
					"	text-align: center;\n" +
					"	font-weight: bold;\n" +
					"	margin-top: 100px;\n" +
					"}\n" +
					"\n" +
					"header{\n" +
					"	font-size: 20px;\n" +
					"	background-color: #353E4F;\n" +
					"	box-shadow: 6px 6px 0px #242b36;\n" +
					"	width: 100%;\n" +
					"	color: white;\n" +
					"	height: 80px;\n" +
					"	top: 0;\n" +
					"	left: 0;\n" +
					"	position : fixed;\n" +
					"}\n" +
					"\n" +
					"header img{\n" +
					"	float: left;\n" +
					"	width: 4cm;\n" +
					"	margin-left: 5%;\n" +
					"}\n" +
					"\n" +
					"header h1{\n" +
					"	font-size: 30px;\n" +
					"	float: right;\n" +
					"	color: white;\n" +
					"	margin-right: 10px;\n" +
					"}\n" +
					"\n" +
					"header a{\n" +
					"	color: white;\n" +
					"	margin-left: 10%;\n" +
					"	margin-top: 5px;\n" +
					"}\n" +
					"\n" +
					"body{\n" +
					"	background-color: #d4d8df;\n" +
					"}\n" +
					"\n" +
					"footer{\n" +
					"	background-color: #353E4F;\n" +
					"	width: 100%;\n" +
					"	color: #9d9999;\n" +
					"	height: 40px;\n" +
					"	font-size: 10px;\n" +
					"	text-align: center;\n" +
					"	position: absolute;\n" +
					"	bottom: 0;\n" +
					"	left: 0;\n" +
					"	position : fixed;\n" +
					"}");
				pw.close();
			}
			catch (Exception e){ e.printStackTrace(); }
			
			/* Génération de Page1.css */
			try
			{
				Collections.sort(lstEtu);
				PrintWriter pw = new PrintWriter("./fichier_html/Styles/page1.css", "UTF-8");
				pw.println(
					"th{\n" +
					"	color: white;\n" +
					"	background-color: #353E4F;\n" +
					"	padding-right: 15px;\n" +
					"	text-align: left;\n" +
					"}\n" +
					"\n" +
					"table{\n" +
					"	border: 0px;\n" +
					"	border-collapse: collapse;\n" +
					"	border-color: white;\n" +
					"	background-color: white;\n" +
					"	width: fit-content;\n" +
					"}\n" +
					"\n" +
					".equipe{\n" +
					"	text-align: right;\n" +
					"	padding-left: 15px;\n" +
					"}\n" +
					"\n" +
					"tr:nth-child(odd) {\n" +
					"	background-color: #d6d6d6;\n" +
					"}\n" +
					"\n" +
					".scrollable-div {\n" +
					"	height: 480px;\n" +
					"	overflow-y: auto;\n" +
					"	scrollbar-color : black;\n" +
					"}\n" +
					"\n" +
					"div{\n" +
					"	width: fit-content;\n" +
					"	margin-left: auto;\n" +
					"	margin-right: auto;\n" +
					"}\n");
				pw.close();
			}
			catch (Exception e){ e.printStackTrace(); }

		/* Génération de Page2.css */
		try
		{
			PrintWriter pw = new PrintWriter("./fichier_html/Styles/page2.css", "UTF-8");
			pw.println(
				".groupcompl{\n" +
				"	border: 1px solid;\n" +
				"	width: 300px;\n" +
				"	height: 120px;\n" +
				"	border-collapse: collapse;\n" +
				"	border-color: black;\n" +
				"	margin-bottom: 20px;\n" +
				"}\n" +
				"\n" +
				".groupe{" +
				"	border-left : 1px solid black ;\n" +
				"	border-right: 1px solid black ;\n" +
				"\n" +
				"	background-color: white;\n" +
				"	height: 120px;\n" +
				"	width: 190px;\n" +
				"}\n" +
				"\n" +
				"td\n" +
				"{\n" +
				"	padding-left: 10px;\n" +
				"	padding-right: 10px;\n" +
				"}\n" +
				"\n" +
				"table{\n" +
				"	border: 0px;\n" +
				"	background-color: white;\n" +
				"	border-color: black;\n" +
				"}\n" +
				"\n" +
				"div div{\n" +
				"	float: left;\n" +
				"	margin-left: 1.5%;\n" +
				"	margin-right: 1.5%;\n" +
				"	width: 30%;\n" +
				"	height: fit-content;\n" +
				"}\n" +
				"\n" +
				"div{\n" +
				"	margin-bottom: 45px;\n" +
				"}\n");

				nbEquipeParColonne = lstEquipe.size()/ 3;
				for(nbEquipeActu = 1; nbEquipeActu <= lstEquipe.size() ; nbEquipeActu++ )
				{
					if(lstEquipe.size()%3 == 0)
					{
						if ((nbEquipeActu >= (lstEquipe.size() - nbEquipeParColonne * 3)) && (nbEquipeActu < (lstEquipe.size() - nbEquipeParColonne * 2 + 1)))
								pw.println(
									"\n#equipe" + nbEquipeActu + "{\n" +
									"	background-color : rgb(" + 255 + ", " + 255 + ", " + (nbEquipeActu * 32) + " )\n" +
									"}");
						if ((nbEquipeActu >= (lstEquipe.size() - nbEquipeParColonne * 2 + 1) && (nbEquipeActu < (lstEquipe.size() - nbEquipeParColonne + 1))))
								pw.println(
									"\n#equipe" + nbEquipeActu + "{\n" +
									"	background-color : rgb(" + (nbEquipeActu * 13) + ", " + (nbEquipeActu * 13) + ", " + 255 + " )\n" +
									"}");
						if (nbEquipeActu >= (lstEquipe.size() - nbEquipeParColonne + 1))
							pw.println(
								"\n#equipe" + nbEquipeActu + "{\n" +
								"	background-color : rgb(" + 255 + ", " + (0 + nbEquipeActu * 8) + ", " + (0 + nbEquipeActu * 8 ) + " )\n" +
								"}");
					}
					else
					{
						if ((nbEquipeActu >= (lstEquipe.size() - nbEquipeParColonne * 3)) && (nbEquipeActu < (lstEquipe.size() - nbEquipeParColonne * 2)))
							pw.println(
								"\n#equipe" + nbEquipeActu + "{\n" +
								"	background-color : rgb(" + 255 + ", " + 255 + ", " + (nbEquipeActu * 32) + " )\n" +
								"}");

						if ((lstEquipe.size()/ 3.0 > nbEquipeParColonne) && 
							(lstEquipe.size()/ 3.0 >= nbEquipeParColonne + 0.5))
						{
							if ((nbEquipeActu >= (lstEquipe.size() - nbEquipeParColonne * 2 - 1) && (nbEquipeActu < (lstEquipe.size() - nbEquipeParColonne))))
								pw.println(
									"\n#equipe" + nbEquipeActu + "{\n" +
									"	background-color : rgb(" + (nbEquipeActu * 13) + ", " + (nbEquipeActu * 13) + ", " + 255 + " )\n" +
									"}");
						}
						else
							if ((nbEquipeActu >= (lstEquipe.size() - nbEquipeParColonne * 2) && (nbEquipeActu < (lstEquipe.size() - nbEquipeParColonne))))
								pw.println(
									"\n#equipe" + nbEquipeActu + "{\n" +
									"	background-color : rgb(" + (nbEquipeActu * 13) + ", " + (nbEquipeActu * 13) + ", " + 255 + " )\n" +
									"}");
						
						if (nbEquipeActu >= (lstEquipe.size() - nbEquipeParColonne))
							pw.println(
								"\n#equipe" + nbEquipeActu + "{\n" +
								"	background-color : rgb(" + 255 + ", " + (0 + nbEquipeActu * 8) + ", " + (0 + nbEquipeActu * 8 ) + " )\n" +
								"}");
					}
				}	
						
				pw.close();
		}
			catch (Exception e){ e.printStackTrace();} 

		/* Génération de Page3.css */
		try
		{
			PrintWriter pw = new PrintWriter("./fichier_html/Styles/page3.css", "UTF-8");
			pw.println(
				"section {" +
				"	float : left;\n" +
				"	width : 30%;\n" +
				"	margin-left: 1.5%;\n" +
				"	margin-right: 1.5%;\n" +
				"	margin-bottom : 5%;\n" +
				"	background-color: white;" +
				"	border: 1px solid #353E4F;\n" +
				"}\n" +
				"\n" +
				"table {\n" +
				"	margin-left   : 5%;\n" +
				"	margin-bottom : 5%;\n" +
				"	border-collapse : collapse;\n" +
				"	width: 90%;\n" +
				"}\n" +
				"\n" +
				"tr:nth-child(even) {\n" +
				"	border-bottom: 1px white solid;\n" +
				"}\n" +
				"\n" +
				".horaire{\n" +
				"	font-size: 21.6px;\n" +
				"	padding-bottom: 30px;\n" +
				"	padding-top: 10px;\n" +
				"}\n" +
				"\n" +
				"div{\n" +
				"	width: 100%;\n" +
				"}\n" +
				"\n" +
				"h2 {\n" +
				"	padding    : 1%;\n" +
				"	margin-top : 0px;\n" +
				"	text-align : center;\n" +
				"	text-decoration : #353E4F underline;\n" +
				"	font-size  : 2em;\n" +
				"}\n" +
				"\n" +
				".marge {\n" +
				"	margin-top : 50%;\n" +
				"}\n" +
				"\n" +
				".heureDebut {\n" +
				"	padding-top    : 0%;\n" +
				"	padding-bottom : 7%;\n" +
				"	padding-right   : 5%;\n" +
				"	text-align     : right;\n" +
				"}\n" +
				"\n" +
				".heureFin {\n" +
				"	padding-bottom : 0%;\n" +
				"	padding-top    : 7%;\n" +
				"	padding-right   : 5%;\n" +
				"	text-align     : right;\n" +
				"}\n" +
				"\n" +
				".passage {\n" +
				"	padding-bottom : 7%;\n" +
				"	background  : #353E4F;\n" +
				"	width       : 40%;\n" +
				"	text-align  : center;\n" +
				"	color       : white;\n" +
				"}");
			pw.close();
		}
		catch (Exception e){ e.printStackTrace();}



			try
			{
				PrintWriter pw = new PrintWriter("./fichier_html/Styles/page_acc.css", "UTF-8");
				pw.println(
					".button{\n" +
					"	background-color: #353E4F;\n" +
					"	box-shadow: 6px 6px 10px #242b36;\n" +
					"	width: 75%;\n" +
					"	color: white;\n" +
					"	height: 120px;\n" +
					"	font-size: 20px;\n" +
					"	margin-left: 12.5%;\n" +
					"	margin-bottom: 25px;\n" +
					"}\n" +
					"\n" +
					".button:hover {\n" +
					"	background-color: white;\n" +
					"	color: #353E4F;\n" +
					"	transition: 500ms;\n" +
					"}\n");
				pw.close();
			}
			catch (Exception e){ e.printStackTrace();}
		}


	 /**
	  * Permet la création et le remplissage du tableau d'étudiant tabEtu avec les information d'un ficher
	  * @param nomFichier,lstEtu
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

			cpt = 0;
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
					 lstEtu.get(nEtu).setEquipe(nEquipe);
				 }
			 }
			 else
			 {
				 if(nEtu < lstEtu.size())
				 {
					 lstGroupe.add(lstEtu.get(nEtu));
					 lstEtu.get(nEtu).setEquipe(nEquipe);
					 nEtu ++;
					 
					 for (int cpt = 1; cpt < nbEtuEquipe && nEtu < lstEtu.size() && lstEtu.get(nEtu).getCatego() == lstGroupe.get(cpt-1).getCatego() ; nEtu++, cpt++)
					 {
						 lstGroupe.add(lstEtu.get(nEtu));
						 lstEtu.get(nEtu).setEquipe(nEquipe);
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
					 lstGroupe.get(cpt - 1).setEquipe(nEquipe - cpt + 1);
					 lstEquipe.get(nEquipe - cpt).setGroupe(lstGroupeTemp2);
					 
					 
					 lstGroupeTemp2.clear();
				 }
			 }
		 
			 lstGroupe.clear();
		 } 
	 }
 
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
				heureD     = Exercice3.timeToInt(heureDTemp);
				
				heureFTemp = dec.getString(4);
				heureF     = Exercice3.timeToInt(heureFTemp);
				

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
	 * ouvre la page d'accueil
	 * @param page : chemin de la page d'accueil
	 */
	public static void ouvrirAccueil(String page) 
    {
        File file = new File(page);
        
        //Vérifier si le système prend en charge la classe Desktop ou non
        if(!Desktop.isDesktopSupported())
        {
            System.out.println("Desktop n'est pas prise en charge");
            return;
        }
        
        Desktop d = Desktop.getDesktop();
        if(file.exists()) 
            try 
            {
                d.open(file);
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
    }
}