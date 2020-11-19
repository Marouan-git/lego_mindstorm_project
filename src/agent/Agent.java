package agent;

import java.io.FileNotFoundException;

import action.*;
import lejos.hardware.Button;
import lejos.utility.Delay;
import perception.*;

public class Agent {
	// Attributs
	
	// Instance de l'etat de l'environnement

	// Instance de l��tat de l�environnement

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
		DB db;
		FollowPath fp;
		But b;
		PaletFinder pf;
		// Constructeur
		public Agent() throws FileNotFoundException {
			cs = new ColorSensor();
			d = new Deplacement();
			e = new Etat(ColorSensor.BLUE);
			us = new UltrasonicSensor();
			db = new DB();
			p = new Pince(db);
			fp = new FollowPath(e, d, db);
			b= new But(cs, db);
			pf = new PaletFinder(us, d, db);
			ts = new TouchSensor(db);
			
		}
		
		
		// M�thodes
		
		// M�thode pour marquer le premier point
		//private void marquerPremierPoint(){}; 
	// M�thode pour marquer un point (autre que le premier)
		//private void marquerUnPoint(){}; 

	// Recherche le palet le plus proche et retourne la distance correspondante
		//private float rechercherPaletPlusProche (){}; 

	// se dirige vers et saisit le palet le plus proche, puis retourne true si un palet a bel et bien �t� saisi
	// retourne false sinon
	// distance = distance du palet le plus proche lors de la d�tection
		//private boolean saisirPaletPlusProche (float distance){}; 

	// S'oriente, se dirige vers la ligne adverse et d�pose le palet 
		private void allerAuProchainPoin() {
			
		}
		private void deposerPalet (){};

	// �vite les palets sur le chemin lors de la phase de d�pose
		private void eviterPalets (){};

	


	// se positionne au milieu d'une ligne de couleur apr�s avoir marqu� un point
	// (couleur d�pend de la ligne sur laquelle se trouvent les palets les plus proches)
		private void positionApresPoint(){
			e.setIdligneActuel();
			d.turnRight(90);
			float distance = us.getDistance();
			if(distance > 1.01f) {
				d.avancer();
				while(distance > 1.02f || distance < 0.98f) {
					Delay.msDelay(50);
					distance = us.getDistance();
				}
			}
			if(distance < 0.98f) {
			    d.reculer();
			    while(distance > 1.02f || distance < 0.98f) {
					Delay.msDelay(50);
					distance = us.getDistance();
				}
			    
			}
			
			
			d.stop();
			e.setIdligneActuel(Etat.idLigneMilieu);
			if(e.getIdligneActuel() == Etat.idLigneProche) {
				d.turnRight(90);
				
				while(!cs.changeColor().equals(ColorSensor.WHITE)) {
					d.reculer();
					Delay.msDelay(10);
					
				}
				d.stop();
			}
			
			if(e.getIdligneActuel() == Etat.idLigneMilieu) {
				d.turnRight(90);
				while(!cs.changeColor().equals(e.ligneLoin.getCouleur())) {
					d.avancer();
					Delay.msDelay(30);
				}
				d.stop();
			}
			
		};

		private void testEtaT() {
			//vert turnRight 154 point 1 avance de 10- 20cm
			//bleu turnRight 160 point 1  10 20cm
			//bleu turnRight 126 point 2 avance 10 - 12 cm rech -> 44 Point2  -> 85 Point3
			//turnLeft  142 - 160 point 2 avance 20 cm
			
			//POINT 4 TURNlEFT 180 POINT 50
			//
			//System.out.println(this.e.getAngleFromPointMarquage());
			d.turnRight(126); 
			this.e.setIdPointActuel(1);
			//d.turnLeft(this.e.getAngleFromPointMarquage()); 
		}
		
		private void allerChercherLePointSuivant() {
			
		}
		
		// se repositionne si palet non touch� lors de la tentative de saisie

		//private void seRepositionner (){};
		
		private void seRepositionner (){};

	    
	
		
	public static void main(String[] args) {
		//20 45 cm
		//30 48 cm
		//33 54
		

		try {
			Agent a = new Agent();
			a.fp.start();
			//a.b.start();
			a.p.start();
			a.pf.start();
			a.ts.start();
			while(!Button.ESCAPE.isDown()) {
				
			}
			System.exit(0);
			/**boolean t= true;
			while(t) {
				a.testEtaT();
				t=false;
				if(Button.ESCAPE.isDown()) {
					//UltrasonicSensor.us.close();
					a.cs.colorSensor.close();
					a.d.stop();
					
					
				}
			}**/
			
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
