package agent;

import action.Deplacement;
import lejos.utility.Delay;
import perception.UltrasonicSensor;

// Cette classe g�re la phase de recherche de palet et la calibration de la direction du robot
// Elle utilise les classes UltrasonicSensor, D�placement et DB
public class PaletFinder extends Thread {
	
	// Instance du capteur ultrasons pour calculer les distances
	private UltrasonicSensor us;
	
	// Instance de D�placement
	private Deplacement d;
	
	// Instance de DB pour modifier les commandes
	private DB db;

	// Initialise les instances
	public PaletFinder(UltrasonicSensor us, Deplacement d, DB db) {
		// TODO Auto-generated constructor stub
		this.us = us;
		this.d = d;
		this.db = db;
	}
	
	
	// D�marre une recherche de palet en effectuant un balayage d'un angle donn� en param�tre
	// Le robot se met en direction de l'objet d�tect� le plus proche s'il en a trouv� un
	// Retourne la distance de l'objet d�tect� le plus proche
	private float rechercherPaletPlusProche (double angleDeBalayage){
		float closestAngle = -1.0f;
		d.getPilot().setAngularSpeed(45);
		d.getPilot().rotate(angleDeBalayage / 2);
		d.getPilot().rotate(-angleDeBalayage,true);
		
		float lastDistance = 3.0f;
		float currentDistance = us.getDistance();
		
		while(d.getPilot().isMoving()) {
			Delay.msDelay(20); // 50 mesures par sec
		    currentDistance = us.getDistance();
			if (currentDistance < lastDistance ) {
				lastDistance = currentDistance;
				closestAngle = d.getPilot().getMovement().getAngleTurned(); // retourne l'angle parcouru depuis que la rotation a commenc�
			}
		}
		
		
		d.getPilot().rotate(angleDeBalayage+closestAngle);
		d.modifierPosition(angleDeBalayage/2+closestAngle);
		us.setLastDistance(lastDistance);
		
		return lastDistance;
	
	}
	
	
	// Permet de calibrer la direction du robot � l'aide d'un mur
	// Le robot effectue un balayage et se remet en direction du point du mur le plus proche correspondant � la position 0
	// Cette m�thode est ex�cut�e � chaque fois que le robot marque un point
	private float calibratePosition (){
		float closestAngle = -1.0f;
		d.getPilot().setAngularSpeed(45);
		d.getPilot().rotate(25);
		d.getPilot().rotate(-50,true);
		float lastDistance = 3.0f;
		float currentDistance = us.getDistance();
		
		while(d.getPilot().isMoving()) {
			Delay.msDelay(20); // 50 mesures par sec
			currentDistance = us.getDistance();
			if (currentDistance < lastDistance) {
				lastDistance = currentDistance;
				closestAngle = d.getPilot().getMovement().getAngleTurned(); // retourne l'angle parcouru depuis que la rotation a commenc�
			}
		}
		
		
		d.getPilot().rotate(45+closestAngle);
		d.setPosition(0);
		
		return lastDistance;
	
	}
	
	
	// M�thode principale contenant les actions � r�aliser pour la phase de recherche, en respectant toutes les conditions d�crites
	public void run() {
		while(true) {
			if(db.getCmd() == DB.SEARCHCMD) {
				float res = rechercherPaletPlusProche(50);
				//System.out.println(res);
				while(d.getPilot().isMoving()) {
					
				}
				
				db.setDistanceToPalet(res);
				db.setCmd(DB.GOTOPALETCMD);
				//us.setCurrentDistance(us.getDistance());
			}
			if(db.getCmd()== DB.GOTOPALETCMD) {
				us.setCurrentDistance(us.getDistance());
				if(us.detectPalet()) {
					db.setPaletDetected(true);
					us.setLastDistance(3.0f);
					//d.stop();
				}else {
					us.setLastDistance(us.getCurrentDistance()); 
					Delay.msDelay(30);
				}
				
				
				
			}
			if(db.getCmd()==DB.SAISIECMD) {
				d.avancer(0.05);
				while(db.getCmd() ==DB.SAISIECMD) {
					//b
				}
				
			}
			
			if(db.getCmd()==DB.GOTOBUTCMD) {
				d.avancer();
				while(db.getCmd()==DB.GOTOBUTCMD) {
					
				}
				d.getPilot().stop();
				//db.setCmd(DB.BUTCMD);
				//d.stop();
				
			}
			if(db.getCmd()==DB.CALIBRATECMD) {
				float res = calibratePosition();
				while(d.getPilot().isMoving()) {
					
				}
				db.setCmd(DB.POINTCMD);
				//db.setCmd(DB.BUTCMD);
				//d.stop();
				
			}
			/*
			 * if(db.getCmd() == DB.RESETPOSITIONCMD) { calibratePosition();
			 * while(d.getPosition() !=0) {
			 * 
			 * } db.setCmd(DB.POINTCMD); }
			 */
		}
		
	}

}
