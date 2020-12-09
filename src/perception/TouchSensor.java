package perception;

import agent.DB;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;

import lejos.utility.Delay;

import lejos.robotics.*;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

// Classe qui g�re le capteur de toucher du robot
// Elle utilise la classe DB
public class TouchSensor extends Thread {

	// Instance du capteur de toucher
	private EV3TouchSensor ts;
	
	// Tableau contenant les valeurs renvoy�es par le capteur de toucher
	private float [] sample;
	
	// Fournit le bon nombre de valeurs � mettre dans le tableau sample en fonction du mode du capteur choisi
	private SampleProvider source;
	
	// Instance de la Brick
	Brick b;
	
	// Instance du port du capteur de toucher
	Port s3;
	
	// Etat du capteur de toucher : true = le capteur a touch� quelque chose, false sinon.
	private boolean etat;

	
	// Instance de DB permettant de modifier les commandes
	private DB db;
	
	
	
	// Initialise les attributs
	// Le mode du capteur est le TouchMode
	// L'�tat est initialis� � false : au d�but de la partie, le robot ne touche rien
	public TouchSensor(DB db)
    {
		b = BrickFinder.getDefault();
		s3 = b.getPort("S3");
		ts = new EV3TouchSensor(s3);
		source = ts.getTouchMode();
		sample = new float[source.sampleSize()];
		etat=false;
		this.db = db;
    }
	
	
	// Retourne true au moment o� le capteur est touch�
	public boolean isPressed()
	{
		float[] sample = new float[1];
		source.fetchSample(sample, 0);
		return sample[0] != 0;

	}
		
	// Retourne l'�tat du capteur
	public boolean isEtat() {
		return etat;
	}

	// Modifie l'�tat du capteur
	public void setEtat(boolean etat) {
		this.etat = etat;
	}
	
	

	// Retourne true si le capteur a �t� touch� � un moment donn�
	public boolean aEteTouche() {
		while(etat == false) {
			etat = isPressed();
		}
		return etat;
	}


	
	
	// M�thode principale contenant les instructions � r�aliser suivant l'�tat du capteur et la commande en cours
	public void run() {
		while(true) {
			if(db.getCmd()==DB.FIRSTPOINTCMD) {
				if(aEteTouche()) {
					System.out.print("CA MARCHE AUSSI");
					db.setPaletDetected(false);
					db.setCmd(DB.FIRSTSAISIECMD);
					this.etat=false;
				}

			}
			if(db.isPaletDetected() && db.getCmd()==DB.GOTOPALETCMD ) {
				//System.out.print("CA MARCHE");
				if(aEteTouche()) {
					System.out.print("CA MARCHE AUSSI");
					db.setCmd(DB.SAISIECMD);
					//this.etat=false;
				}
			}
			if(db.isPaletDetected() && db.getCmd()==DB.AFTEROPENPINCECMD ) {
				//System.out.print("CA MARCHE");
				if(aEteTouche()) {
					//System.out.print("CA MARCHE AUSSI");
					db.setCmd(DB.SAISIECMD);
					//this.etat=false;
				}
			}
			if(db.getCmd() == DB.DIRECTIONBUTCMD){
				this.etat=false;
			}
			
			
			
			
		}
	}

  
  	
	
    
    
    // Tests
	public static void main(String[] args) {
		
		TouchSensor ts = new TouchSensor(new DB());
		MovePilot pilot;
		Wheel leftWheel = WheeledChassis.modelWheel(Motor.B,0.056).offset(-0.06075); // 0.056 = diam�tre des roues, offset = d�calage des roues ??
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.C, 0.056).offset(0.06075);
		Chassis chassis = new WheeledChassis(new Wheel[] {leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
		pilot.forward();
		while(true) {
		
			if(ts.aEteTouche())
				pilot.stop();
			System.out.println(ts.aEteTouche());
		if(Button.ESCAPE.isDown()) {
			System.exit(0);
		}
		}
	}

}
