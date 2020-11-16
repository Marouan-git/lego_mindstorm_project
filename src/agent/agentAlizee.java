package agent;

import perception.*;

import java.io.FileNotFoundException;

import action.*;
import lejos.hardware.port.Port;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class agentAlizee {
	

		Deplacement d;
		UltrasonicSensor us;
		Pince p; 
		ColorSensor cs;
		Ligne l;
		
		public agentAlizee() {
			d= new Deplacement();
			us= new UltrasonicSensor();
			p= new Pince();
			try {
				cs= new ColorSensor();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
private void eviterPalets(){
		
		// si on d�tecte un palet sachant qu'on a d�j� un palet
		if(us.detectPalet() == true && p.isaPalet() == true) {
			d.stop(); // on s'arrete
			d.turnLeft(90); // on tourne de 90�
			
			//d.avancer();
			//Delay.msDelay(50);// il faut voir combien de rotations il faut pour avancer de environ 23 cm
			//d.stop(); // on s'arr�te
			// ou
			d.getPilot().travel(0.23);
			
			
			d.turnRight(90); // on se remet dans la position de la direction intiale
			d.avancer(); // on avance
		}
		// sinon
		else {
			p.saisiePalet(); // on saisit le palet
			deposerPalet(); // et on va le d�poser dans l'en-but adverse
			// ou marquerUnPoint()
		}
		
		
		
	}
	
	// le palet doit �tre saisit
	// il faut avancer jusqu'� la ligne adverse et ouvrir les pinces 
	// pour d�poser le palet avant de reculer, de fermer les pinces
	// et de se retourner 
	
	private void deposerPalet() {
		
		// si on a le palet
		if (p.isaPalet() == true) {
			
			// se replacer face � la ligne adverse
			int po = d.getPosition();
			if (po>=0 && po<=180) {
				d.turnLeft(po); // en tournant � gauche si le robot est dans les 180 premieres degr�s
			}
			else if(po>=180) { // ou � droite s'il est dans l'autre sens
				d.turnRight(360-po);
			}
			
			// avancer tant qu'on n'a pas franchit la ligne adverse
			

			while(l.getCouleur()!="WHITE") {
				d.avancer();
				eviterPalets();
			}
			
			
			// puis avancer encore un petit peu avant de s'arr�ter, de lacher le palet, de reculer et de fermer les pinces
			//d.avancer();
			//Delay.msDelay(20);
			//d.stop();
			
			d.getPilot().travel(0.20);
			p.lachePalet();
			d.getPilot().travel(-0.20);
			
			//d.reculer();
			//Delay.msDelay(-20);;
			
			p.serrer();
			
		}
		else{
			System.out.println("pas de palet saisi"); // sinon, il n'y a pas de palet saisi
			recherchePaletPlusProche();
			saisirPaletPlusProche();
			deposerPalet();
			// ou marquerUnPoint();
		}
	}
}
