import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Cofre {
	String nomeCofre = "", id = "";
	private int pin = 0;
	private int capacidadeAtual = 0;
	final int capacidade = 19;
	
	private ArrayList<Pertence> pertences = new ArrayList<>();
	
	/*
	 *	REGRAS DE STATUS
	 * 0 -> OK
	 * 1 -> ERRO DE SENHA
	 * 2 -> Sem espaço
	 * 3 -> Sem dinheiro suficiente
	 * 4 -> Dinheiro a mais
	 */

	// BRL EUR E DOL
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
		Pertence p = new Pertence();
		p.moeda = tipoMoeda;
		p.valor = value;
		p.tipoPertence = "Dinheiro";
		this.pertences.add(p);
		
		return 0;
	}

	private boolean verificarCapacidade() {
		this.capacidadeAtual = this.pertences.size();
		return this.capacidadeAtual == this.capacidade ? true : false;
	}

	public int adicionarObjeto(String nome, String tipoMoeda, double value) {
		if(this.verificarCapacidade()) {
			return 2;
		}
		
		Pertence p = new Pertence();
		p.nomePertence = nome;
		p.moeda = tipoMoeda;
		p.valor = value;
		p.tipoPertence = "Objeto";
		
		this.pertences.add(p);
		
		return 0;
	}
	//----------------------------------Retirar Pertence ------------------------------------------//
	public int retirarDinheiro(int pin, String tipoMoeda, double valor) {
		if(this.pin != pin) {
			return 1;
		}
		double valorAtual = 0;
		for(int i = 0; i < this.pertences.size(); i++) {
			if(this.pertences.get(i).tipoPertence.equals("Dinheiro")) {
				if(this.pertences.get(i).moeda.equals(tipoMoeda)) {
					while(valorAtual <= valor) {
						valorAtual++;
						this.pertences.get(i).valor--;
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
	public int retirarObjeto(int pin, int id) {
		if(this.pin == pin) {
			return 1;
		}
		this.pertences.remove(id);
		
		return 0;
	}
	//------------------------------------------------------------------------------------//
	public int getPIN() {
		return this.pin;
	}
	public ArrayList<Pertence> getPertence(){
		return this.pertences;
	}
	public int getCapacidadeAtual() {
		return this.capacidadeAtual;
	}
	public double getSaldo() {
		Conversao conversor = new Conversao();
		double valorAtual = 0;
		for(int i = 0; i < this.pertences.size(); i++) {
			valorAtual += conversor.converterParaDolar(this.pertences.get(i).moeda,this.pertences.get(i).valor);
		}
		return valorAtual;
	}
	
	//---------------------------------------------------------------------------------------//
	public String toString(int i) {
		String conteudo = "<html><ul>";
		if(this.pertences.get(i).tipoPertence == "Dinheiro") {
			conteudo += "<li> Pertence: " + this.pertences.get(i).tipoPertence + "</li>" + 
					"<li> Moeda: " + this.pertences.get(i).moeda + " </li>" + 
					"<li> Valor: " + (this.pertences.get(i).moeda == "DOL" ? "$" : (this.pertences.get(i).moeda == "EUR" ? "€" : "R$")) + this.pertences.get(i).valor + "</li>" + 
					"<hr/><br/>";
		}
		else {
			conteudo += "<li> Pertence: " + this.pertences.get(i).tipoPertence + "</li>" + 
					"<li> Nome do pertence: " + this.pertences.get(i).nomePertence + "</li>" +
					"<li> Moeda: " + this.pertences.get(i).moeda + " </li>" + 
					"<li> Valor: " + (this.pertences.get(i).moeda == "DOL" ? "$" : (this.pertences.get(i).moeda == "EUR" ? "€" : "R$")) +this.pertences.get(i).valor + "</li>" + 
					"<hr/><br/>";
		}
		conteudo += "</ul></html>";
		return conteudo;
	}
}
