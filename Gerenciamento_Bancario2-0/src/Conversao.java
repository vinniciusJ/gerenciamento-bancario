public class Conversao {
	String[] tiposMoedas = {"DOL", "EUR", "BRL"};
	
	public double converterParaDolar(String tipo, double valor) {
		double valorConvertido = valor;
		for(int i = 0; i < tiposMoedas.length; i++) {
			if(tiposMoedas[i].equals(tipo)) {
				tipo = Integer.toString(i);
			}
		}
		switch(Integer.parseInt(tipo)) {
		case 1:
			valorConvertido = valor * 0.90;
			break;
		case 2:
			valorConvertido = valor * 4.86;
		}
		return valorConvertido;
	}
	public double converterParaReal(String tipo, double valor) {
		double valorConvertido = valor;
		for(int i = 0; i < tiposMoedas.length; i++) {
			if(tiposMoedas[i].equals(tipo)) {
				tipo = Integer.toString(i);
			}
		}
		switch(Integer.parseInt(tipo)) {
		case 0:
			valorConvertido = valor * 0.21;
			break;
		case 2:
			valorConvertido = valor * 0.18;
		}
		return valorConvertido;
	}
	public double converterParaEuro(String tipo, double valor) {
		double valorConvertido = valor;
		for(int i = 0; i < tiposMoedas.length; i++) {
			if(tiposMoedas[i].equals(tipo)) {
				tipo = Integer.toString(i);
			}
		}
		switch(Integer.parseInt(tipo)) {
		case 0:
			valorConvertido = valor * 1.11;
			break;
		case 3:
			valorConvertido = valor * 5.42;
		}
		return valorConvertido;
	}
	public String toString() {
		return "Dolar para Euro: €0,90 \n" +
				"Dolar para Real: R$4,86 \n";
	}
}
