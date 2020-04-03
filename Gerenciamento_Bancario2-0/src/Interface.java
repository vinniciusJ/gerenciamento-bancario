import java.util.ArrayList;
import javax.swing.JOptionPane;

// REGRA DE ERROR
// -1 -> OPCAO INVALIDA

public class Interface {
	private static ArrayList<Banco> bancos = new ArrayList<>();
	private static final String[] OPCOES_INICIAIS = {"Criar Banco", "Mostrar Bancos Criados", "Escolher Banco", "Excluir Banco", "Sair"};
	private static final String[] OPCOES_BANCO = {"Criar Cofre", "Mostrar Cofres Criados", "Escolher Cofre", "Excluir Cofre", "Voltar"};
	private static final String[] OPCOES_COFRE = {"Adicionar", "Retirar", "Voltar"};
	private static final String[] TIPOS_MOEDA = {"Dolar", "Euro", "Real" };
	
	//---------------------------- M�todos de interface --------------------------------------//
	
	public static int lerOpcao(String idMenu, String message, Object[] opcoes) {
		return JOptionPane.showOptionDialog(null, message, idMenu, 0, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
	}
	public static int lerOpcao(String idMenu, String message) {
		return JOptionPane.showConfirmDialog(null, message, idMenu,JOptionPane.YES_NO_OPTION);
	}
	public static String lerOpcao(String message) {
		return JOptionPane.showInputDialog(message);
	}
	public static boolean confirmarSaida() {
		return lerOpcao("Sair", "Voc� realmente deseja sair?") == 0 ? true : false;
	}
	public static void selecionarOpcao(Object iniciar, int opcao) {
		switch(opcao) {
		case 0:
			criarBanco();
			break;
		case 1:
			mostrarBancosCriados();
			break;
		case 2:
			selecionarBanco();
			break;
		case 3:
			excluirBanco();
			break;
		case 4:
			if(confirmarSaida()) {
				System.exit(0);
			}
			else {
				abrirMenu();
			}
			break;
		}
		voltar("/");
	}
	public static void selecionarOpcao(int idBanco, int pin, int opcao) {
		switch(opcao) {
		case 0:
			criarCofre(idBanco);
			break;
		case 1:
			mostrarCofresExistentes(idBanco);
			break;
		case 2:
			selecionarCofre(idBanco);
			break;
		case 3:
			excluirCofre(idBanco);
			break;
		case 4:
			voltar("/");
			break;
		}
		voltar("/banco", idBanco, bancos.get(idBanco).getPIN());
	}
	public static void selecionarOpcao(int idBanco, int idCofre, int pin, int opcao) {
		String[] escolha = {"Dinheiro", "Objeto"};
		
		switch(opcao) {
		case 0:
			int op = lerOpcao("Adicionar", "Escolha o que deseja adicionar", escolha);
			if(op == 0) {
				String moeda = TIPOS_MOEDA[lerOpcao("Escolher qual moeda", "Escolha a moeda que deseja adicionar", TIPOS_MOEDA)];
				double valor = Double.parseDouble(lerOpcao("Insira o valor que deseja adicionar"));
				adicionarDinheiro(idBanco, idCofre, moeda, valor);
			}
			else {
				String nomePertence = lerOpcao("Informe o nome do pertence: ");
				String moeda = TIPOS_MOEDA[lerOpcao("Escolher qual moeda", "Escolha a moeda que deseja adicionar", TIPOS_MOEDA)];
				double valor = Double.parseDouble(lerOpcao("Insira o valor estimado ao pertence: "));
				adicionarObjeto(idBanco, idCofre, nomePertence, moeda, valor);
			}
			break;
		case 1:
			int option = lerOpcao("Retirar", "O que deseja retirar: ", escolha);
			if(option == 0) {
				String moeda = TIPOS_MOEDA[lerOpcao("Escolha qual moeda", "Escolha qual moeda deseja sacara", TIPOS_MOEDA)];
				double valor = Double.parseDouble(lerOpcao("Insira o valor que deseja adicionar"));
				retirarDinheiro(idBanco, idCofre, pin, moeda, valor);
			}
			else {
				Object[] gavetas = quantidadeDeGavetas(idBanco, idCofre).toArray();
				int idPertence = lerOpcao("Escolher pertence", "Escolha qual pertence deseja retirar: ", gavetas);
				String nomeObjeto = bancos.get(idBanco).getCofres().get(idCofre).getPertence().get(idPertence).nomePertence;
				int response = lerOpcao("Confirmar retirada", ("Voc� tem certeza que deseja retirar " + nomeObjeto));
				
				if(response == 0) {
					retirarObjeto(idBanco, idCofre, pin, idPertence);
				}
			}
			break;
		case 2:
			voltar("/banco/cofre", idBanco, idCofre, bancos.get(idBanco).getCofres().get(idCofre).getPIN());
			break;
		}
		voltar("/banco/cofre", idBanco, idCofre, bancos.get(idBanco).getCofres().get(idCofre).getPIN());
	}
	
	public static void imprimirMensagem(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	public static void abrirMenu(String init) {
		String[] opcoes = OPCOES_INICIAIS;
		int op = lerOpcao(
				"Gerenciador Banc�rio",
				"<html>"
				+ "<h1 style='font-size: 16px; font-weight: 'bold'; padding: -10 0 0 0;'>Seja bem-vindo ao Gerenciador Banc�rio</h1>"
				+ "<p style='font-size: 12px; margin-bottom: 8px'>Escolha uma op��o</p>"
				+ "</html>",
				opcoes
				);
		if(op != -1) {
			selecionarOpcao(null, op);
		}
		else {
			if(confirmarSaida()) {
				System.exit(0);
			}
			abrirMenu();
		}
	}
	public static void abrirMenu() {
		String[] opcoes = OPCOES_INICIAIS;
		int op = lerOpcao(
				"Gerenciador Banc�rio",
				"<html>"
				+ "<h1 style='font-size: 16px; font-weight: 'bold'; padding: -10 0 0 0;'>Gerenciador Banc�rio</h1>"
				+ "<p style='font-size: 12px; margin-bottom: 8px'>Escolha uma op��o</p>"
				+ "</html>",
				opcoes
				);
		if(op != -1) {
			selecionarOpcao(null, op);
		}
		else {
			if(confirmarSaida()) {
				System.exit(0);
			}
			abrirMenu();
		}
	}
	
	public static void voltar(String rotas) {
		if(rotas.equals("/")) {
			abrirMenu();
		}
		else {
			System.out.println("ERROR");
		}
	}
	public static void voltar(String rotas, int idBanco, int pin) {
		if(rotas.equals("/banco")) {
			String[] opcoes = OPCOES_BANCO;
			selecionarOpcao(idBanco, pin, lerOpcao(("Banco " + bancos.get(idBanco).nomeAgencia + " " + bancos.get(idBanco).codBanco), "Escolha uma op��o:", opcoes));
		}
		else {
			System.out.println("ERROR");
		}
	}
	public static void voltar(String rotas, int idBanco, int idCofre, int pin) {
		if(rotas.equals("/banco/cofre")) {
			String[] opcoes = OPCOES_COFRE;
			selecionarOpcao(idBanco, idCofre, pin, lerOpcao(("Cofre " + bancos.get(idBanco).getCofres().get(idCofre).nomeCofre + " " + bancos.get(idBanco).getCofres().get(idCofre).id), "Escolha uma op��o:", opcoes));
		}
	}
	
	//---------------------------------------------------------------------------------------//
	
	//------------------------------------------ Metodos especificos-------------------------//
	public static void criarBanco() {
		Banco banco = new Banco();
		int bancoCredenciado = lerOpcao("Bancos Credenciados", "Escolha um banco credenciado: ", banco.bancosCredenciados);
		banco.nome = banco.bancosCredenciados[bancoCredenciado];
		banco.codBanco = banco.codigoBancosCredenciados[bancoCredenciado];
		banco.nomeAgencia = lerOpcao("Informe o nome da sua ag�ncia: ");
		
		int response = banco.setPIN(Integer.parseInt(lerOpcao("Escolha uma senha: ")));
		
		while(response == 1) {
			response = banco.setPIN(Integer.parseInt(lerOpcao("A senha deve conter no m�nimo 4 n�meros e no m�ximo 5 n�meros: ")));
		}
		
		bancos.add(banco);
	}
	public static void mostrarBancosCriados() {
		if(bancos.size() != 0) {
			String conteudo =  "<html><h3>Bancos existentes</h3><hr/>";
			
			for(int i = 0; i < bancos.size(); i++) {
				conteudo += "<p style='margin-right=-10px;'> -> " + bancos.get(i).nome + " " + bancos.get(i).codBanco + " - " + bancos.get(i).nomeAgencia  + "</p>";

			}
			conteudo += "</html>";
			imprimirMensagem(conteudo);
		}
		else {
			String conteudo = "Nenhum banco foi cadastrado ainda";
			imprimirMensagem(conteudo);
		}
	}
	public static void excluirBanco() {
		if(bancos.size() == 0) {
			imprimirMensagem("Nenhum banco foi cadastrado ainda");
		}
		else {
			int id = lerOpcao("Excluir Banco", "Escolha qual banco deseja excluir", formatarNomeBanco());
			int pin = lerOpcao("Confirmar exclus�o do banco", "<html><p>Voc� tem certeza que deseja excluir esse banco.</p><p> Todos os dados do banco ser�o excluidos </p></html>");
			if(pin == 0) {
				bancos.remove(id);
			}
		}
	}
	public static void selecionarBanco() {
		if(bancos.size() != 0) {
			int id = lerOpcao("Escolher Banco", "Escolha um banco", formatarNomeBanco());
			String idMenu = "Escolha uma Op��o";
			int pin = Integer.parseInt(lerOpcao("Digite sua senha: "));
			
			if(pin == bancos.get(id).getPIN()) {
				selecionarOpcao(id, pin, lerOpcao(idMenu, "Escolha uma op��o", OPCOES_BANCO));
			}
			else {
				imprimirMensagem("Senha incorreta, tente novamente");
			}
		}
		else {
			imprimirMensagem("Nenhum banco foi cadastrado ainda");
		}
	}
	public static String[] formatarNomeBanco() {
		String[] conteudoFormatado = new String[bancos.size()];
		for(int i = 0; i < conteudoFormatado.length; i++) {
			conteudoFormatado[i] = bancos.get(i).nome + " " + bancos.get(i).codBanco + " - " + bancos.get(i).nomeAgencia;
		}
		return conteudoFormatado;
	}
	//--------------------------------------------------------------------------------------//
	public static void criarCofre(int idBanco) {
		String nome = lerOpcao("Insira o nome do Cofre");
		String id = Integer.toString(bancos.get(idBanco).getCofres().size() + 1);
		int pin = Integer.parseInt(lerOpcao("Escolha uma senha: "));
		int response = bancos.get(idBanco).adicionarCofre(nome, id, pin );
		
		while(response != 0) {
			if(response == 1) {
				imprimirMensagem("A senha deve conter entre a 4 caracteres n�mericos a 6 caracteres num�ricos: ");
				pin = Integer.parseInt(lerOpcao("Digite uma senha, novamente: "));
			}
			else {
				imprimirMensagem("N�o existe mais nenhum cofre dispon�vel");
			}
			response = bancos.get(idBanco).adicionarCofre(nome, id, pin );
		}
	}
	public static void mostrarCofresExistentes(int idBanco) {
		imprimirMensagem(bancos.get(idBanco).toString() == "3" ? "Nenhum cofre foi cadastrado ainda" : bancos.get(idBanco).toString());
	}
	public static void selecionarCofre(int idBanco) {
		if(bancos.get(idBanco).getCofres().size() != 0) {
			int idCofre = lerOpcao("Escolher Cofre", "Escolha um cofre", formatarNomeCofre(idBanco));
			String idMenu = bancos.get(idBanco).nome + " " + bancos.get(idBanco).codBanco + " - " + bancos.get(idBanco).nomeAgencia;
			int pin = Integer.parseInt(lerOpcao("Digite a senha do cofre: "));
						
			if(pin == bancos.get(idBanco).getCofres().get(idCofre).getPIN()) {
				selecionarOpcao(idBanco, idCofre, pin, lerOpcao(idMenu, "Escolha uma op��o", OPCOES_COFRE));
			}
			else {
				imprimirMensagem("Senha incorreta, tente novamente");
			}
		}
		else {
			imprimirMensagem("Nenhum cofre foi cadastrado no " + bancos.get(idBanco).nome + " " + bancos.get(idBanco).codBanco + " - " + bancos.get(idBanco).nomeAgencia + ", ainda");
		}
		
	}
	public static void excluirCofre(int idBanco) {
		if(bancos.get(idBanco).getCofres().size() != 0) {
			int idCofre = lerOpcao("Excluir Cofre", "Escolha o cofre que deseja excluir", formatarNomeCofre(idBanco));
			int pin = Integer.parseInt(lerOpcao("Digite sua senha para confirmar a opera��o: "));
			int response = bancos.get(idBanco).excluirCofre(pin, idCofre);
			while(response == 1) {
				pin = Integer.parseInt(lerOpcao("Senha incorrespondente, por favor digite novamente: "));
				response = bancos.get(idBanco).excluirCofre(pin, idCofre);
			}
		}
		else {
			imprimirMensagem("Nenhum cofre foi cadastrado no " + bancos.get(idBanco).nome + " " + bancos.get(idBanco).codBanco + " - " + bancos.get(idBanco).nomeAgencia + ", ainda");
		}
	}
	public static String[] formatarNomeCofre(int idBanco) {
		String[] conteudoFormatado = new String[bancos.get(idBanco).getCofres().size()];
		for(int i = 0; i < conteudoFormatado.length; i++) {
			conteudoFormatado[i] = "Cofre " + bancos.get(i).getCofres().get(i).nomeCofre + " " + bancos.get(i).getCofres().get(i).id;
		}
		return conteudoFormatado;
	}
	//---------------------------------Metodos cofre----------------------------------------//
	public static void adicionarDinheiro(int idBanco, int idCofre, String tipoMoeda, double total) {
		int response = bancos.get(idBanco).setCofre(idCofre, tipoMoeda, total);
		while(response == 2) {
			imprimirMensagem("Sem espa�o no cofre para adicionar");
		}
	}
	public static void adicionarObjeto(int idBanco, int idCofre, String nomePertence, String tipoMoeda, double total) {
		int response = bancos.get(idBanco).setCofre(idCofre, nomePertence, tipoMoeda, total);
		while(response == 2) {
			imprimirMensagem("Sem espa�o no cofre para adicionar");
		}
	}
	public static void retirarDinheiro(int idBanco, int idCofre, int pin, String tipoMoeda, Double totalValor) {
			int response = bancos.get(idBanco).setCofre(idCofre, pin, tipoMoeda, totalValor);
			while(response == 3) {
				imprimirMensagem("Sem saldo suficiente");
			}
	}
	public static void retirarObjeto(int idBanco, int idCofre, int pin, int idObjeto) {
		int response = bancos.get(idBanco).setCofre(idCofre, pin, idObjeto);
		while(response == 1) {
			imprimirMensagem("Senha incorreta, tente novamente");
		}
	}
	private static ArrayList<String> quantidadeDeGavetas(int idBanco, int idCofre) {
		ArrayList<String> gavetas = new ArrayList<>();
		for(int i = 0 ; i < bancos.get(idBanco).getCofres().get(idCofre).getPertence().size(); i++){
			gavetas.add("Gaveta " + (1 + i));
		}
		return gavetas;
	}
	//--------------------------------------------------------------------------------------//
	public static void main(String[] args) {
		int resp = lerOpcao("id", "escolhe");
		System.out.println(resp);
		//abrirMenu("init");
	}
}
