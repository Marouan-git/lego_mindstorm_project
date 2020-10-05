package perception;

import lejos.robotics.SampleProvider;
import lejos.robotics.filter.AbstractFilter;
import lejos.utility.Delay;

public class UltrasonicSensor extends AbstractFilter {
    
	//Attribut
	
	float[] sample; // to store samples
	
	//Constructeur
	
	public UltrasonicSensor(SampleProvider source) { // source = sensor mode
		super(source); // initialise la source (choix du sensor) et la taille du sample correspondant
		sample = new float[sampleSize]; 
	}
	
	// M�thodes
	
	public float getDistance() {
		super.fetchSample(sample, 0); // fetch a sample from the US sensor
		return sample[0];
	}
	
	// Permet de d�tecter un mur ou le robot adverse
	// Si la distance est inf�rieur � celle � partir de laquelle un palet n'est plus d�tect�, alors c'est ou bien un mur
	// ou bien le robot adverse qui est en face
	public boolean detectWall() {
		if(getDistance() < 0.326) {
			return true;
		}
		
		return false;
	}
	
	public boolean detectPalet(float currentDistance, float lastDistance) {
	
		
		
		if((currentDistance <= lastDistance)) {
			
			return false;
			
		}
		
		return true;
	}
	
	
	public boolean facingRobot() {
		//need tests
		return false;
	}
}
