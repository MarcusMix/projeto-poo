package view;


import java.io.IOException;
import java.util.Scanner;
//responsabilidade de logar credenciais
import controller.UsuarioController;
import model.dao.Banco;
import model.vo.TipoUsuarioVO;
import model.vo.UsuarioVO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

public class Login {

	Scanner teclado = new Scanner(System.in);
	private static final int OPCAO_MENU_LOGIN = 1; 
	private static final int OPCAO_MENU_CRIAR_CONTA = 2;
	private static final int OPCAO_MENU_SAIR = 4;

	public void apresentarMenuLogin() {
		int opcao = this.apresentarOpcoesMenu();
		while (opcao != OPCAO_MENU_SAIR) { 
			switch (opcao) {
			case OPCAO_MENU_LOGIN: {
				UsuarioVO usuarioVO = this.realizarLogin(); 
				if (usuarioVO.getIdUsuario() != 0 && usuarioVO.getDataExpiracao() == null) {
					System.out.println("\nUsuário Logado: "+ usuarioVO.getLogin());
					System.out.println("Perfil: "+ usuarioVO.getTipoUsuarioVO());
					Menu menu = new Menu();
					menu.apresentarMenu(usuarioVO);

				}
				break;
			}
			case OPCAO_MENU_CRIAR_CONTA: {
				this.cadastrarNovoUsuario();
				break;
			}
			
			default: {
				System.out.println("\nOpção Inválida!");
			}
			}
			opcao = this.apresentarOpcoesMenu(); 
		}
	}

	private int apresentarOpcoesMenu() {
		System.out.println("\n---------- Sistema FoodTruck ----------");
		System.out.println("\nOpções: ");
		System.out.println(OPCAO_MENU_LOGIN + " - Entrar");
		System.out.println(OPCAO_MENU_CRIAR_CONTA + " - Criar Conta");
		System.out.println(OPCAO_MENU_SAIR + " - Sair");
		System.out.print("\nDigite uma opção:");
		return Integer.parseInt(teclado.nextLine());
	}

	private UsuarioVO realizarLogin() {
		UsuarioVO usuarioVO = new UsuarioVO();
		System.out.println("\n ---------- Informações ----------");
		System.out.print("Login: ");
		usuarioVO.setLogin(teclado.nextLine());
		System.out.print("Senha: ");
		usuarioVO.setSenha(teclado.nextLine());

		if (usuarioVO.getLogin().isEmpty() || usuarioVO.getSenha().isEmpty()) {
			System.out.println("Os campos Login e Senha são obrigatórios!");
		} else {
			UsuarioController usuarioController = new UsuarioController();
			usuarioVO = usuarioController.realizarLoginController(usuarioVO);
			if (usuarioVO.getIdUsuario() == 0) {
				System.out.println("Usuário não encontrado!");
			}
			if (usuarioVO.getDataExpiracao() != null) {
				System.out.println("Usuário expirado!");
			}
		}
		
		return usuarioVO;
	}
	
	private void cadastrarNovoUsuario() {
		UsuarioVO usuarioVO = new UsuarioVO();
		usuarioVO.setTipoUsuarioVO(TipoUsuarioVO.CLIENTE); 
		
		MenuUsuario menuUsuario = new MenuUsuario();
		menuUsuario.cadastrarNovoUsuario(usuarioVO);
	}
}

