package agent;

// Cette classe sert � d�crire les diff�rents �tats dans lesquels le robot se trouve (DB = Data Base)

public class DB {
	
	// Renseigne si le robot a d�tect� un palet dans son �tat actuel ou non 
	private boolean paletDetected = false;
	
	// Distance s�parant le robot du palet rep�r� lors de la recherche
	private float distanceToPalet = 0f;
	
	// Distance max � laquelle le palet est cens� se trouver lors de la recherche
	private float distanceMAX = 0.45f;
	
	
	// Les diff�rentes commandes � r�aliser (= diff�rents �tats du robot) 
	
	
	// Dans cet �tat, le robot se dirige vers le point suivant
	public static int POINTCMD = 0;
	
	// Dans cet �tat, le robot d�marre une recherche (= balayage) du palet
	public static int SEARCHCMD =1;
	
	// Dans cet �tat, le robot, apr�s avoir effectuer la recherche, ouvre les pinces et se dirige vers le palet
	public static int GOTOPALETCMD = 2;
	
	// ????????????
	public static int AFTEROPENPINCECMD = 7;
	
	// Dans cet �tat, le robot enclenche la saisie du palet
	public static int SAISIECMD = 3;
	
	// Dans cet �tat, apr�s avoir saisi le palet, le robot se remet en direction de la ligne d'en-but adverse
	public static int DIRECTIONBUTCMD = 4;
	
	// Dans cet �tat, apr�s avoir s'�tre mis en direction du but adverse, 
	// le robot se dirige vers le but adverse et ne s'arr�te pas tant qu'il n'a pas d�tect� la ligne blanche
	public static int GOTOBUTCMD = 5;
	
	// Dans cet �tat, apr�s avoir atteint la ligne d'en-but adverse, le robot lache le palet
	public static int BUTCMD = 6;
	
	// Dans cet �tat, apr�s avoir d�pos� le palet, le robot calibre sa position en s'aidant du mur se trouvant en face
	public static int CALIBRATECMD = 9;
	
	// Dans cet �tat, apr�s s'�tre dirig� vers le palet d�tect� et parcouru la distance correspondant � sa position sans l'avoir touch�,
	// le robot recule, refait une recherche et retente sa chance 
	public static int PALETNOTTOUCHEDCMD = 10;
	
	// Cet �tat correspond au d�marrage du robot (en d�but de partie ou apr�s un temps mort), 
	// il se dirige vers le premier point se situant face � lui 
	public static int FIRSTPOINTCMD = 11;
	
	// Dans cet �tat, le robot saisit le premier palet 
	public static int FIRSTSAISIECMD = 12;
	
	// Dans cet �tat, apr�s avoir saisi le premier palet, le robot se d�cale de la ligne sur laquelle il se trouve pour �viter d'�ventuels palets en chemin
	// et se remet en direction de la ligne adverse pour aller marquer le point
	public static int FIRSTDIRECTIONCMD = 13;
	
	
	
	// Valeur de la commande correspondant � un des �tats d�crits ci-dessus
	private int cmd;
	
	
	
	// Initialise la commande au premier point � r�cup�rer (en d�but de partie ou apr�s temps mort)
	public DB() {
		cmd = FIRSTPOINTCMD;
		// TODO Auto-generated constructor stub
	}
	
	// Renvoie true si le robot est dans l'�tat o� il a d�tect� un palet 
	public boolean isPaletDetected() {
		return paletDetected;
	}
	
	// Modifie l'�tat "palet d�tect�"
	public void setPaletDetected(boolean paletDetected) {
		this.paletDetected = paletDetected;
	}
	
	// Retourne l'entier correspondant � la commande actuelle
	public int getCmd() {
		return cmd;
	}
	
	
	// Modifie la commande actuelle
	public void setCmd(int cmd) {
		this.cmd = cmd;
	}
	
	
	// Retourne la distance s�parant le robot du palet rep�r� lors de la recherche
	public float getDistanceToPalet() {
		return distanceToPalet;
	}
	
	
	// Modifie la distance s�parant le robot du palet rep�r� lors de la recherche
	public void setDistanceToPalet(float distanceToPalet) {
		this.distanceToPalet = distanceToPalet;
	}

	// Retourne la distance max � laquelle le palet est cens� se trouver lors de la recherche
	public float getDistanceMAX() {
		return distanceMAX;
	}

	// Modifie la distance max � laquelle le palet est cens� se trouver lors de la recherche
	public void setDistanceMAX(float distanceMAX) {
		this.distanceMAX = distanceMAX;
	}

}
