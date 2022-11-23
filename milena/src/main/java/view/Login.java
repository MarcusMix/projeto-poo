package view;


import java.util.Scanner;

import controler.UsuarioController;
import model.vo.TipoUsuarioVO;
import model.vo.UsuarioVO;

public class Login {

	Scanner teclado = new Scanner(System.in);
	private static final int OPCAO_MENU_LOGIN = 1;
	private static final int OPCAO_MENU_CRIAR_LOGIN = 2;
	private static final int OPCAO_MENU_SAIR = 3;
	
	public void apresentarMenuLogin() {
		int opcao = this.apresentarOpcoesMenu();
		while(opcao != OPCAO_MENU_SAIR) {
			switch(opcao) {
				case OPCAO_MENU_LOGIN: {
					System.out.println("Realizando Login");
					UsuarioVO usuarioVO = this.realizarLogin();
					if(usuarioVO.getIdUsuario() != 0 && usuarioVO.getDataExpiracao() == null) {
						System.out.println("\n Usuário Logado: " + usuarioVO.getLogin());
						System.out.println("\n Perfil " + usuarioVO.getIdUsuario());
						Menu menu = new Menu();
						menu.apresentarMenu(usuarioVO);
					}
					break;
				}
				case OPCAO_MENU_CRIAR_LOGIN: {
					this.cadastrarNovoUsuario();
					break;
				}
				default: {
					System.out.println("\n Opção inválida!");
				}
			}
			opcao = this.apresentarOpcoesMenu();
		}
	}

	

	private int apresentarOpcoesMenu() {
		System.out.println("\n ---------------- Sistema FoodTruck ----------------");
		System.out.println("\nOpções");
		System.out.println(OPCAO_MENU_LOGIN + "- Entrar");
		System.out.println(OPCAO_MENU_CRIAR_LOGIN + "- Criar conta");
		System.out.println(OPCAO_MENU_SAIR + "- Sair");
		System.out.println("\nDigite uma opção: ");
		return Integer.parseInt(teclado.nextLine());
	}
	
	private UsuarioVO realizarLogin() {
		UsuarioVO usuarioVO = new UsuarioVO();
		System.out.println("\n ------------ Informações -----------");
		System.out.print("Login: ");
		usuarioVO.setLogin(teclado.nextLine());
		System.out.print("Senha: ");
		usuarioVO.setSenha(teclado.nextLine());
		
		if(usuarioVO.getLogin().isEmpty() || usuarioVO.getSenha().isEmpty()) {
			System.out.println("Os campos senha e login sao obrigatórios!");
		} else {
			UsuarioController  usuarioController = new UsuarioController();
			usuarioVO = usuarioController.realizarLoginController(usuarioVO);
			if(usuarioVO.getIdUsuario() == 0) {
				System.out.println("Usuário não encontrado!");
			} if (usuarioVO.getDataExpiracao() != null) {
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
