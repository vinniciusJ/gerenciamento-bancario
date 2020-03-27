import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Cofre {
	String nomeCofre, id;
	private int pin;
	private int capacidadeAtual = 0;
	final int capacidade = 19;
	
	private Pertence[] pertences = new Pertence[capacidade];
	
	/*
	 *	REGRAS DE STATUS
	 * 0 -> OK
	 * 1 -> ERRO DE SENHA
	 * 2 -> Sem espaço
	 * 3 -> Sem dinheiro suficiente
	 */
	
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
	//-------------------------------- Adicionar Pertences ------------------------------//
		
	// Return 1 -> Não tem espaço ---- Return 2 -> A quantidade desejada não cabe no banco
	public int adicionarDinheiro(String tipoMoeda, double value) {
		if(this.verificarCapacidade()) {
			return 2;
		}
		
		int id = this.capacidadeAtual;		
		
		if(this.pertences[id] == null) {
			this.pertences[id].valor = value;
			this.pertences[id].moeda = tipoMoeda;
		}
		else if(this.pertences[id].valor < 10000 && (this.pertences[id].valor + value) < 10000) {
			this.pertences[id].valor += value;
			this.pertences[id ].moeda = tipoMoeda;
		}
		else if(id != this.capacidade){
			this.pertences[id + 1].valor = value;
			this.pertences[id + 1].moeda = tipoMoeda;
		}
		else {
			return 2;
		}
		return 0;
	}
		
	private void contarCapacidade() {
		for(int i = 0; i < this.pertences.length; i++) {
			if(this.pertences[i] != null) {
				if(this.pertences[i].tipoPertence == "Dinheiro") {
					if(this.pertences[i].valor > 10000) {
						this.capacidadeAtual++;
					}
				}
				else {
					this.capacidadeAtual++;
				}
			}
		}
		this.capacidadeAtual--;
	}
	private boolean verificarCapacidade() {
		contarCapacidade();
		return this.capacidadeAtual == this.capacidade ? true : false;
	}

	public int adicionarObjeto(String nome, String tipoMoeda, double value) {
		if(this.verificarCapacidade()) {
			return 2;
		}
		int id = this.capacidadeAtual;
		
		if(this.pertences[id] == null) {
			this.pertences[id].nomePertence = nome;
			this.pertences[id].moeda = tipoMoeda;
			this.pertences[id].valor = value;
		}
		else if(id != this.capacidade){
			this.pertences[id + 1] = this.pertences[id];
			Pertence p = new Pertence();
			p.moeda = tipoMoeda;
			p.nomePertence = nome;
			p.valor = value;
			this.pertences[id] = p;
		}
		else {
			return 2;
		}
		return 0;
	}
	//----------------------------------Retirar Pertence ------------------------------------------//
	public int retirarDinheiro(int pin, String tipoMoeda, double valor) {
		if(this.pin != pin) {
			return 1;
		}
		double valorAtual = 0;
		for(int i = 0; i < this.pertences.length; i++) {
			if(this.pertences[i].tipoPertence.equals("Dinheiro")) {
				if(this.pertences[i].moeda.equals(tipoMoeda)) {
					if(valorAtual <= valor) {
						valorAtual += this.pertences[i].valor;
						this.pertences[i].valor = 0;
					}
				}
			}
		}
		if(valorAtual < valor) {
			this.adicionarDinheiro(tipoMoeda, valorAtual);
			return 3;
		}
		return 0;
	}
	public int retirarObjeto(int pin, String nome) {
		if(this.pin == pin) {
			return 1;
		}
		for(int i = 0; i < this.pertences.length; i++) {
			if(this.pertences[i].tipoPertence.equals("Objeto")) {
				if(this.pertences[i].nomePertence.equals(nome)) {
					this.pertences[i] = null;
				}
			}
		}
		
		return 0;
	}
	//------------------------------------------------------------------------------------//
	public int getPIN() {
		return this.pin;
	}
	public Pertence[] getPertence(){
		return this.pertences;
	}
	public int getCapacidadeAtual() {
		return this.capacidadeAtual;
	}
	public double getSaldo() {
		Conversao conversor = new Conversao();
		double valorAtual = 0;
		for(int i = 0; i < this.pertences.length; i++) {
			valorAtual += conversor.converterParaDolar(this.pertences[i].moeda, this.pertences[i].valor);
		}
		return valorAtual;
	}
	//---------------------------------------------------------------------------------------//
	public String toString(int i) {
		String conteudo = "<html><ul>";
		if(this.pertences[i].tipoPertence == "Dinheiro") {
			conteudo += "<li> Pertence: " + this.pertences[i].tipoPertence + "</li>" + 
					"<li> Moeda: " + this.pertences[i].moeda + " </li>" + 
					"<li> Valor: " + (this.pertences[i].moeda == "DOL" ? "$" : (this.pertences[i].moeda == "EUR" ? "€" : "R$")) +this.pertences[i].valor + "</li>" + 
					"<hr/><br/>";
		}
		else {
			conteudo += "<li> Pertence: " + this.pertences[i].tipoPertence + "</li>" + 
					"<li> Nome do pertence: " + this.pertences[i].nomePertence + "</li>" +
					"<li> Moeda: " + this.pertences[i].moeda + " </li>" + 
					"<li> Valor: " + (this.pertences[i].moeda == "DOL" ? "$" : (this.pertences[i].moeda == "EUR" ? "€" : "R$")) +this.pertences[i].valor + "</li>" + 
					"<hr/><br/>";
		}
		conteudo += "</ul></html>";
		return conteudo;
	}
}
