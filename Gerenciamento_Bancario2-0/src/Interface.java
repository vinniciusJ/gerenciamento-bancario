import java.util.ArrayList;
import javax.swing.JOptionPane;

// REGRA DE ERROR
// -1 -> OPCAO INVALIDA

public class Interface {
	private static ArrayList<Banco> bancos = new ArrayList<>();
	private static final String[] OPCOES_INICIAIS = {"Criar Banco", "Mostrar Bancos Criados", "Escolher Banco", "Excluir Banco", "Sair"};
	private static final String[] OPCOES_BANCO = {"Criar Cofre", "Mostrar Cofres Criados", "Escolher Cofre", "Excluir Cofre", "Voltar"};
	private static final String[] OPCOES_COFRE = {"Adicionar", "Retirar", "Valor estimado", "Cotação Atual", "Voltar"};
	
	//---------------------------- Métodos de interface --------------------------------------//
	
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
		return lerOpcao("Sair", "Você realmente deseja sair?") == 0 ? true : false;
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
		switch(opcao) {
		case 0:
			// Adicionar algo no cofre
			break;
		case 1:
			// Retirar algo
			break;
		case 2:
			// Mostar valor estimado cofre
			break;
		case 3:
			// Mostrar Cotação Atual
			break;
		case 4:
			break;
		default:
			
		}
	}
	
	public static void imprimirMensagem(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	public static void abrirMenu(String init) {
		String[] opcoes = OPCOES_INICIAIS;
		int op = lerOpcao(
				"Gerenciador Bancário",
				"<html>"
				+ "<h1 style='font-size: 16px; font-weight: 'bold'; padding: -10 0 0 0;'>Seja bem-vindo ao Gerenciador Bancário</h1>"
				+ "<p style='font-size: 12px; margin-bottom: 8px'>Escolha uma opção</p>"
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
				"Gerenciador Bancário",
				"<html>"
				+ "<h1 style='font-size: 16px; font-weight: 'bold'; padding: -10 0 0 0;'>Gerenciador Bancário</h1>"
				+ "<p style='font-size: 12px; margin-bottom: 8px'>Escolha uma opção</p>"
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
			selecionarOpcao(idBanco, pin, lerOpcao(("Banco " + bancos.get(idBanco).nomeAgencia + " " + bancos.get(idBanco).codBanco), "Escolha uma opção:", opcoes));
		}
		else {
			System.out.println("ERROR");
		}
	}
	public static void voltar(String rotas, int idBanco, int idCofre, int pin) {
		if(rotas.equals("/banco/cofre")) {
			String[] opcoes = OPCOES_COFRE;
			selecionarOpcao(idBanco, idCofre, pin, lerOpcao(("Cofre " + bancos.get(idBanco).getCofres().get(idCofre).nomeCofre + " " + bancos.get(idBanco).getCofres().get(idCofre).id), "Escolha uma opção:", opcoes));
		}
	}
	
	//---------------------------------------------------------------------------------------//
	
	//------------------------------------------ Metodos especificos-------------------------//
	public static void criarBanco() {
		Banco banco = new Banco();
		int bancoCredenciado = lerOpcao("Bancos Credenciados", "Escolha um banco credenciado: ", banco.bancosCredenciados);
		banco.nome = banco.bancosCredenciados[bancoCredenciado];
		banco.codBanco = banco.codigoBancosCredenciados[bancoCredenciado];
		banco.nomeAgencia = lerOpcao("Informe o nome da sua agência: ");
		
		int response = banco.setPIN(Integer.parseInt(lerOpcao("Escolha uma senha: ")));
		
		while(response == 1) {
			response = banco.setPIN(Integer.parseInt(lerOpcao("A senha deve conter no mínimo 4 números e no máximo 5 números: ")));
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
			int pin = lerOpcao("Confirmar exclusão do banco", "<html><p>Você tem certeza que deseja excluir esse banco.</p><p> Todos os dados do banco serão excluidos </p></html>");
			if(pin == 0) {
				bancos.remove(id);
			}
		}
	}
	public static void selecionarBanco() {
		if(bancos.size() != 0) {
			int id = lerOpcao("Escolher Banco", "Escolha um banco", formatarNomeBanco());
			String idMenu = "Escolha uma Opção";
			int pin = Integer.parseInt(lerOpcao("Digite sua senha: "));
			
			if(pin == bancos.get(id).getPIN()) {
				selecionarOpcao(id, pin, lerOpcao(idMenu, "Escolha uma opção", OPCOES_BANCO));
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
				imprimirMensagem("A senha deve conter entre a 4 caracteres númericos a 6 caracteres numéricos: ");
				pin = Integer.parseInt(lerOpcao("Digite uma senha, novamente: "));
			}
			else {
				imprimirMensagem("Não existe mais nenhum cofre disponível");
			}
			response = bancos.get(idBanco).adicionarCofre(nome, id, pin );
		}
	}
	public static void mostrarCofresExistentes(int idBanco) {
		imprimirMensagem(bancos.get(idBanco).toString() == "3" ? "Nenhum cofre foi cadastrado ainda" : bancos.get(idBanco).toString());
	}
	public static void selecionarCofre(int idBanco) {
		System.out.println(bancos.get(idBanco).getCofres().size() != 0);
		if(bancos.get(idBanco).getCofres().size() != 0) {
			int idCofre = lerOpcao("Escolher Cofre", "Escolha um cofre", formatarNomeCofre(idBanco));
			String idMenu = bancos.get(idBanco).nome + " " + bancos.get(idBanco).codBanco + " - " + bancos.get(idBanco).nomeAgencia;
			int pin = Integer.parseInt(lerOpcao("Digite a senha do cofre: "));
			
			System.out.println("AOBA");
			
			if(pin == bancos.get(idBanco).getCofres().get(idCofre).getPIN()) {
				selecionarOpcao(idBanco, idCofre, pin, lerOpcao(idMenu, "Escolha uma opção", OPCOES_COFRE));
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
			int pin = Integer.parseInt(lerOpcao("Digite sua senha para confirmar a operação: "));
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
	
	public static void main(String[] args) {
		abrirMenu("init");
	}
}
