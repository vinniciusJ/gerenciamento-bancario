import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Banco{
	String nome, nomeAgencia, codBanco;
	private int pin;
	private ArrayList<Cofre> cofres = new ArrayList<>();
	
	private void imprimirMessagemErro(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}
	
	private boolean verificarPIN(int pinRequest) {
		String pin = Integer.toString(pinRequest);
		if(pin.length() < 4 || pin.length() > 6) {
			return false;
		}
		else {
			return true;
		}
	}
	public int setPIN(int pinRequest) {
		if(verificarPIN(pinRequest)) {
			this.pin = pinRequest;		
			return 0;
		}
		else {
			return 1;
		}
	}
	public int setCofres(int pin, ArrayList<Cofre> cofres) {
		if(this.pin == pin) {
			this.cofres = cofres;
			return 0;
		}
		else {
			return 1;
		}
	}
	public ArrayList<Cofre> getCofre(){
		return this.cofres;
	}
}
