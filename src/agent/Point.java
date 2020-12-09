package agent;

// Cette classe sert � d�crire les points contenus dans le circuit que le robot doit suivre robot

public class Point {
	
	//angle en degr�s par rapport � la position du robot lorsqu'il est sur la ligne d'en-but adverse face au mur apr�s avoir d�poser le palet
	//Gr�ce � cette valeur, il sait de combien de degr�s il doit se tourner pour �tre face au point suivant (= palet suivant)
	private double angle;
	
	
	
	//la distance � parcourir vers le point avant de faire la recherche de palet
	private double distance;
	
	//la distance max � laquelle le palet est cens� se trouver lors de la recherche
	//Si aucune des valeurs capt�es par l'ultrasonic sensor n'est inf�rieure � cette valeur lors de la recherche
	//alors il consid�re qu'il n'y a plus de palet � ce point
	private float distanceMax = 0.49f;
	
	
	//initialise les valeurs des attributs
	
	public Point(double angle, double distance, float distanceMax) {
		// TODO Auto-generated constructor stub
		this.angle = angle;
		this.distance = distance;
		this.distanceMax = distanceMax;
	}
	

	// Retourne la distance max � laquelle le palet est cens� se trouver lors de la recherche
	public float getDistanceMax() {
		return distanceMax;
	}


	// Modifie la distance max � laquelle le palet est cens� se trouver lors de la recherche
	public void setDistanceMax(float distanceMax) {
		this.distanceMax = distanceMax;
	}


	// Retourne l'angle en degr�s par rapport � la position du robot lorsqu'il est sur la ligne d'en-but adverse face au mur apr�s avoir d�poser le palet
	public double getAngle() {
		return angle;
	}


	// Retourne la distance � parcourir vers le point avant de faire la recherche de palet
	public double getDistance() {
		return distance;
	}


	
	
	
	
	

}
