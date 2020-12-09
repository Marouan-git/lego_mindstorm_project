package agent;

import lejos.utility.Delay;

// Cette classe ex�cute les actions � r�aliser lorsque le robot franchit la ligne blanche adverse
// Elle utilise les classes ColorSensor et DB
import perception.ColorSensor;

public class But extends Thread{
	
	// instance du colorSensor pour d�tecter la ligne blanche
	ColorSensor cs;
	
	// instance DB pour passer la commande actuelle � BUTCMD
	DB db;
	
	// initialise les instances ColorSensor et DB
	public But(ColorSensor cs, DB db) {
		// TODO Auto-generated constructor stub
		this.cs = cs;
		this.db = db;
	}
	
	
	public void run() {
		while(true) {
			if(db.getCmd() == DB.FIRSTDIRECTIONCMD) {
				while(!cs.changeColor().equals(ColorSensor.WHITE)) {
					Delay.msDelay(5);
					
				}
				db.setCmd(DB.BUTCMD);
			}
			if(db.getCmd()==DB.GOTOBUTCMD) {
				while(!cs.changeColor().equals(ColorSensor.WHITE)) {
					Delay.msDelay(5);
					
				}
				db.setCmd(DB.BUTCMD);
			}
			
		}
	}

}
