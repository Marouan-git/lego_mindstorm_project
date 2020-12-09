package agent;

import action.Deplacement;
import lejos.utility.Delay;
// Cette classe contient les m�thodes permettant au robot de suivre le circuit d�finit dans la classe Etat
// Elle utilise les classes Etat, Deplacement et DB
public class FollowPath extends Thread {
	
	// Instance Etat pour acc�der au circuit
	private Etat e;
	
	// Instance de D�placement
	private Deplacement d;
	
	// Instance de DB pour modifier les commandes
	private DB db;
	
	// Entier correspondant au nombre de fois que le robot doit r�essayer de toucher le palet,
	// lorsqu'apr�s avoir d�tect� le palet et s'�tre dirig� vers lui il ne l'a pas touch�
	private int countSearch =0;
	
	// Initialise les instances
	public FollowPath(Etat e, Deplacement d, DB db) {
		// TODO Auto-generated constructor stub
		this.e = e;
		this.d =d;
		this.db = db;
		
	}
	
	// Cette m�thode permet de se diriger vers le point suivant du circuit
	private void allerAuPointSvt() {
		System.out.println("POINT ");
		//d.turnRight(e.getAngleFromPointMarquage());
		d.turnLeft(e.getAngleFromPointMarquage());
		while(d.getPilot().isMoving()) {
			//
		}
		if(e.getDistanceToPoint() !=0) {
			d.avancer(e.getDistanceToPoint());
			while(d.getPilot().isMoving()) {
				//
			}
		}
		
		db.setCmd(DB.SEARCHCMD);
		
	}
	
	// Le robot se repositionne en reculant dans le cas o� il n'aurait pas touch� le palet
	// Apr�s avoir recul� il d�marre une nouvelle recherche et retente sa chance 
	private void seRepositonnerIfPaletNotTouched() {
		d.reculer(0.5);
		db.setPaletDetected(false);
		while(d.getPilot().isMoving()) {
			
		}
		d.gotoPosition(e.getAngleFromPointMarquage());
		db.setCmd(DB.SEARCHCMD);
	}
	
	
	// Le robot passe au point suivant dans le cas o� il n'aurait pas trouv� de palet au point actuel
	public void seRepositionnerIfNotFound() {
		if(e.getIdPointActuel() == 1) {
			//d.gotoPosition(180);
			d.gotoPosition(160);
			//d.gotoPosition(360-180);
			while(d.getPilot().isMoving()) {

			}
			
			e.pointNotFound.add(e.getIdPointActuel());
			e.setIdPointActuel(2);
			e.setPointDistanceMAX(0.75f);
			
			//d.avancer(0.4);
			d.avancer(0.7);
			
			while(d.getPilot().isMoving()) {

			}
			db.setDistanceMAX(0.70f);
			db.setCmd(DB.SEARCHCMD);
		}else if(e.getIdPointActuel() == 2) {
			//d.gotoPosition(angle);
			d.gotoPosition(70);
			//d.gotoPosition(360-70);
			while(d.getPilot().isMoving()) {

			}
			e.pointNotFound.add(e.getIdPointActuel());
			e.setIdPointActuel(3);
			e.setPointDistanceMAX(0.6f);
			d.avancer(0.1);
			while(d.getPilot().isMoving()) {

			}
			db.setDistanceMAX(0.70f);
			db.setCmd(DB.SEARCHCMD);
		}
		else if(e.getIdPointActuel() ==3) {
			//d.gotoPosition(150);
			d.gotoPosition(360-160);
			while(d.getPilot().isMoving()) {

			}
			e.pointNotFound.add(e.getIdPointActuel());

			e.setIdPointActuel(4);
			e.setPointDistanceMAX(0.75f);
			d.avancer(0.2);
			while(d.getPilot().isMoving()) {

			}
			db.setDistanceMAX(0.70f);
			db.setCmd(DB.SEARCHCMD);
		}
		else if(e.getIdPointActuel() ==4) {
			if(e.pointNotFound.contains(3)) {
				//d.gotoPosition(70);
				d.gotoPosition(360-70);
				while(d.getPilot().isMoving()) {

				}
				e.pointNotFound.add(e.getIdPointActuel());
				e.setIdPointActuel(5);
				e.setPointDistanceMAX(0.6f);
				d.avancer(0.1);
				while(d.getPilot().isMoving()) {

				}
				db.setDistanceMAX(0.70f);
				db.setCmd(DB.SEARCHCMD);
				
			}else {
				d.gotoPosition(150);
				//d.gotoPosition(360-120);
				while(d.getPilot().isMoving()) {

				}
				
				e.pointNotFound.add(e.getIdPointActuel());
				e.setIdPointActuel(6);
				e.setPointDistanceMAX(0.6f);
				d.avancer(0.2);
				while(d.getPilot().isMoving()) {

				}
				db.setDistanceMAX(0.70f);
				db.setCmd(DB.SEARCHCMD);
			}
			
		}
		
		

	}
	
	
	// M�thode principale contenant toutes les instructions pour suivre le circuit
	// Une fois lanc�e, le robot suit le circuit en respectant toutes les conditions d�crites
	public void run() {
		boolean b =true;
		while(true) {
			if(db.getCmd()== DB.FIRSTPOINTCMD) {
				d.avancer();
				while(db.getCmd() == DB.FIRSTPOINTCMD) {
					
				}
				d.avancer(0.05);
			}
			if(db.getCmd()== DB.FIRSTDIRECTIONCMD) {
				//d.turnLeft(40);
				d.turnRight(40);
				while(d.getPilot().isMoving()) {
					
				}
				d.avancer(0.36);
				while(d.getPilot().isMoving()) {
					
				}
				//d.turnRight(40);
				d.turnLeft(40);
				while(d.getPilot().isMoving()) {
					
				}
				d.avancer();
				while(db.getCmd()==DB.FIRSTDIRECTIONCMD) {
					
				}
				d.stop();
				
			}
			if(db.getCmd() == DB.POINTCMD) {
				allerAuPointSvt();
				b=true;
			}
			if(db.getCmd()==DB.GOTOPALETCMD && b) {
				System.out.println(db.getDistanceToPalet());
				if(db.getDistanceToPalet() > e.getDistanceMAX()) {
					this.seRepositionnerIfNotFound();
				}else {
					
					d.avancer(db.getDistanceToPalet()+0.07);
					while(d.getPilot().isMoving()) {

					}
					
					if(db.isPaletDetected()) {
						b=false;
						System.out.println("PALET DETECTED");
						if(db.getCmd() == DB.GOTOPALETCMD) {
							db.setCmd(DB.AFTEROPENPINCECMD);
						}
						
					} else {
						System.out.println("PALET NOT TOUCHED");
						db.setCmd(DB.PALETNOTTOUCHEDCMD);
					}
							
				}
				
				
			}
			if(db.getCmd()==DB.AFTEROPENPINCECMD) {
				d.avancer(0.10);
				System.out.println("OPEN PINCE");
				while(d.getPilot().isMoving()) {
					
				}
				if(db.getCmd() == DB.AFTEROPENPINCECMD) {
					System.out.println("OPEN PALET NOT TOUCHED");
					 db.setCmd(DB.PALETNOTTOUCHEDCMD);
				}
				
			}
			if(db.getCmd() == DB.PALETNOTTOUCHEDCMD) {
				if(countSearch ==2) {
					db.setCmd(DB.SAISIECMD);
					countSearch =0;
				}else {
					d.stop();
					b = true;
					this.seRepositonnerIfPaletNotTouched();
					countSearch +=1;
				}
				
			}
			
			if(db.getCmd() == DB.DIRECTIONBUTCMD) {
				//System.out.println(d.getPosition());
				db.setPaletDetected(false);
				if(e.getIdPointActuel() ==0) {
					
				}else {
					d.retourPositionInitial();
					//d.retourPositionInitialDroite();
					if(e.getIdPointActuel() == 4) {
						if(!e.pointNotFound.contains(3)) {
							e.setIdPointActuel(6);
							e.getCircuit()[6] =new Point(155, 0.75, 0.8f);
						}else {
							e.setIdPointActuel(e.getIdPointActuel()+1);
						}
					}else {
						e.setIdPointActuel(e.getIdPointActuel()+1);
					}
					
					while(d.getPilot().isMoving()) {
						
					}
					db.setCmd(DB.GOTOBUTCMD);
				}
				
			}
			/**if(db.getCmd() == DB.GOTOBUTCMD) {
				d.avancer();
				Delay.msDelay(50);
				if(db.getCmd()==DB.BUTCMD) {
				d.stop();
				
				
			}
			}**/
			
		}
		
	}

}
