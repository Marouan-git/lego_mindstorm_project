package agent;

import action.*;
import perception.*;

public class agent {
	// Attributs
	
	// Instance de l��tat de l�environnement
		//Etat e; 
	
	// Instance du capteur d'ultrasons
		UltrasonicSensor us; 
	// Instance du capteur de couleurs
		ColorSensor cs; 
	// Instance du capteur de toucher
		TouchSensor ts; 
	// Instance pour la pince
		Pince p; 
		
		Pince p1;

		
		// Constructeur
		public agent() {
			
		}
		
		
		// M�thodes
		
		// M�thode pour marquer le premier point
		private void marquerPremierPoint(){}; 
	// M�thode pour marquer un point (autre que le premier)
		private void marquerUnPoint(){}; 

	// Recherche le palet le plus proche et retourne la distance correspondante
		private float rechercherPaletPlusProche (){}; 

	// se dirige vers et saisit le palet le plus proche, puis retourne true si un palet a bel et bien �t� saisi
	// retourne false sinon
	// distance = distance du palet le plus proche lors de la d�tection
		private boolean saisirPaletPlusProche (float distance){}; 

	// S'oriente, se dirige vers la ligne adverse et d�pose le palet 
		private void deposerPalet (){};

	// �vite les palets sur le chemin lors de la phase de d�pose
		private void eviterPalets (){};

	// se repositionne si face � un mur
		private void eviterMur(){
			
		};

	// �vite le robot adverse
		private void eviterRobotAdverse(){};

	// se positionne au milieu d'une ligne de couleur apr�s avoir marqu� un point
	// (couleur d�pend de la ligne sur laquelle se trouvent les palets les plus proches)
		private void positionApresPoint(){};

	// se repositionne si palet non touch� lors de la tentative de saisie
		private void seRepositionner (){};
		
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
