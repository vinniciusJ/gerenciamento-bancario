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
	public static boolean confirmarSaida() {
		return lerOpcao("Sair", "Você realmente deseja sair?") == 0 ? true : false;
	}
	public static void selecionarOpcao(Object iniciar, int opcao) {
		switch(opcao) {
		case 0:
			// Criar Banco
			break;
		case 1:
			// Mostrar os bancos criados
			break;
		case 2:
			// Selecionar banco
			break;
		case 3:
			// Excluir Banco Existente
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
	}
	public static void selecionarOpcao(int idBanco, int opcao) {
		switch(opcao) {
		case 0:
			// Criar cofre
			break;
		case 1:
			// Mostrar os cofres criados
			break;
		case 2:
			// Escolher um cofre existente
			break;
		case 3:
			// Excluir um cofre existente
			break;
		case 4:
			abrirMenu();
			break;
		}
	}
	public static void selecionarOpcao(int idBanco, int idCofre, int opcao) {
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
			String[] opcoes = OPCOES_BANCO;
			String idMenu = bancos.get(idBanco).nomeAgencia + " " + bancos.get(idBanco).nome + " " + bancos.get(idBanco).codBanco;
			selecionarOpcao(idBanco, lerOpcao(idMenu, "<html><p style='font-size: 12px;'>Escolha uma opção:</p></html>", opcoes));
			break;
		default:
			
		}
	}
	
	public static void imprimirMensagemErro(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	public static void abrirMenu() {
		String[] opcoes = OPCOES_INICIAIS;
		int op = lerOpcao(
				"Gerenciador Bancário",
				"<html>"
				+ "<h1 style='font-size: 16px; color: #020202; padding: -10 0 0 0;'>Seja bem-vindo ao Gerenciador Bancário</h1>"
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
	
	
	//---------------------------------------------------------------------------------------//
	
	public static void main(String[] args) {
		abrirMenu();
	}
}
