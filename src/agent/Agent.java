package agent;

import java.io.FileNotFoundException;

import action.*;
import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.utility.Delay;
import perception.*;

// Cette classe permet de lancer le programme complet du robot
// Toutes les m�thodes run() contenues dans les autres classes sont ex�cut�es en parall�le gr�ce � la classe Thread
// Elle utilise toutes les autres classes sauf Point, AgentDroit et AgentGauche
public class Agent {

	// Instance de l'�tat du terrain
	Etat e; 

	// Instance du capteur d'ultrasons
	UltrasonicSensor us; 
	
	// Instance du capteur de couleurs
	ColorSensor cs; 
	
	// Instance du capteur de toucher
	TouchSensor ts; 
	
	// Instance pour la pince
	Pince p; 

	//Instance de deplacement
	Deplacement d;
	
	// Instance de DB pour le contr�le des commandes
	DB db;
	
	// Instance de Followpath pour suivre le circuit d�crit dans Etat
	FollowPath fp;
	
	// Instance de But pour les actions � r�aliser au moment de marquer un but
	But b;
	
	// Instance de PaletFinder pour ex�cuter les phases de recherche
	PaletFinder pf;
	
	// Initialise toutes les instances
	public Agent() throws FileNotFoundException {
		cs = new ColorSensor();
		d = new Deplacement();
		e = new Etat();
		us = new UltrasonicSensor();
		db = new DB();
		p = new Pince(db);
		fp = new FollowPath(e, d, db);
		b= new But(cs, db);
		pf = new PaletFinder(us, d, db);
		ts = new TouchSensor(db);

	}






	// M�thode principale qui instancie Agent et lance le programme d�s que le bouton "Enter" est press�
	// La m�thode start() permet d'appeler les m�thodes run des instances sur lesquelles elle est appel�e
	// Le programme s'arr�te quand le bouton "Escape" est press�
	public static void main(String[] args) {


		try {
			Agent a = new Agent();
			while(!Button.ENTER.isDown()) {

			}

			
			a.fp.start();
			a.b.start();
			a.p.start();
			a.pf.start();
			a.ts.start();

			while(!Button.ESCAPE.isDown()) {

			}
			System.exit(0);
	

			if(Button.ESCAPE.isDown()) {
				//UltrasonicSensor.us.close();
				a.cs.colorSensor.close();
				a.d.stop();

				System.exit(0);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
