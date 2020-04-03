import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Banco{
	String nome, nomeAgencia, codBanco;
	private int pin;
	int qtdCofresAtual;
	final String[] bancosCredenciados = {"Banco do Brasil", "Itaú S.A.", "Santander S.A.", "Bradesco S.A.", "Caixa Econômica"};
	final String[] codigoBancosCredenciados = {"001", "341", "033", "237", "104"};
	
	private ArrayList<Cofre> cofres = new ArrayList<>();
	
	private void imprimirMessagemErro(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}
	
	private boolean verificarPIN(int pinRequest) {
		String pin = Integer.toString(pinRequest);
		
		if(pin.length() < 4 || pin.length() > 6) {
			System.out.println(pin);
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
	private void contarCapacidade() {
		for(int i = 0; i < this.cofres.size(); i++) {
			if(this.cofres.get(i) != null) {
				this.qtdCofresAtual++;
			}
		}
	}
	public int adicionarCofre(String nomeCofre, String id, int PIN) {
		Cofre c = new Cofre();
		c.nomeCofre = nomeCofre;
		c.id = id;
		
		if(c.setPIN(PIN) == 1) {
			return 1;
		}
		this.cofres.add(c);
		return 0;
	}
	public int excluirCofre(int pinRequest, int id) {
		if(this.pin != pinRequest) {
			return 1;
		}
		this.cofres.remove(id);
		
		return 0;
	}
	public ArrayList<Cofre> getCofres() {
		return this.cofres;
	}
	public int getPIN() {
		return this.pin;
	}
	public String toString() {
		if(this.cofres.size() == 0) {
			return "3";
		}
		
		String conteudo = "<html><h3>Cofres existentes em " + this.nome + " " + this.codBanco + " - " + this.nomeAgencia + "</h3>";
		for(int i = 0; i < cofres.size(); i++) {
			conteudo += "<p style='font-size:16px;'> COFRE " + this.cofres.get(i).nomeCofre + " " +  this.cofres.get(i).id + "</p>";
		}
		conteudo += "</html>";
		return conteudo;
	}
	//---------------------------------------------------------------------//
	public int setCofre(int idCofre, String tipoMoeda, double valor ) {;
		int response = this.cofres.get(idCofre).adicionarDinheiro(tipoMoeda, valor);
		
		return response;
	}
	public int setCofre(int idCofre, String nome, String tipoMoeda, double valor ) {
		int response = this.cofres.get(idCofre).adicionarObjeto(nome, tipoMoeda, valor);
		
		return response;
	}
	public int setCofre(int idCofre, int pin, String tipoMoeda, double valor) {
		int response = this.cofres.get(idCofre).retirarDinheiro(pin, tipoMoeda, valor);
		return response;
	}
	public int setCofre(int idCofre, int pin, int idObjeto) {
		int response = this.cofres.get(idCofre).retirarObjeto(pin, idObjeto);
		return response;
	}
	public int setCofre(int idCofre, int pin) {
		int response = this.cofres.get(idCofre).setPIN(pin);
		return response;
	}
}
