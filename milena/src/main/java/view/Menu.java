package view;

import java.util.Scanner;

import model.vo.TipoUsuarioVO;
import model.vo.UsuarioVO;

public class Menu {
	private static final int OPCAO_MENU_VENDA = 1;
	private static final int OPCAO_MENU_PRODUTO = 2;
	private static final int OPCAO_MENU_RELATORIO = 3;
	private static final int OPCAO_MENU_USUARIO = 4;
	private static final int OPCAO_MENU_VOLTAR = 5;

	Scanner teclado = new Scanner(System.in);

	public void apresentarMenu(UsuarioVO usuarioVO) {

		int opcao = this.apresentarOpcoesMenu(usuarioVO);
		while (opcao != OPCAO_MENU_VOLTAR) {
			switch (opcao) {
			case OPCAO_MENU_VENDA: {// todos
				MenuVenda menuVenda = new MenuVenda();
				menuVenda.apresentarMenuVendas(usuarioVO);
				System.out.println("Acessando Menu de Vendas....");
				break;
			}
			case OPCAO_MENU_PRODUTO: {// adm e funcionario
				if (usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.FUNCIONARIO)
						|| usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.ADMINISTRADOR)) {
					System.out.println("Acessando Menu de Produto....");
					
				}
				break;
			}
			case OPCAO_MENU_RELATORIO: {// adm e funcionario
				if (usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.FUNCIONARIO)
						|| usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.ADMINISTRADOR)) {
					System.out.println("Acessando Menu de Relat�rios....");					
				}
				break;
			}
			case OPCAO_MENU_USUARIO: {// s� adm
				if (usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.ADMINISTRADOR)){
					MenuUsuario menuUsuario = new MenuUsuario();
					menuUsuario.apresentarMenuUsuario();
				}
				break;
			}
			default: {
				System.out.println("Opção inválida!");
			}
			}
			opcao = this.apresentarOpcoesMenu(usuarioVO);
		}
	}

	private int apresentarOpcoesMenu(UsuarioVO usuarioVO) {
		System.out.println("\n---------Sistema Foodtruck---------");
		System.out.println("\n---------Menu Principal---------");
		System.out.println("\n---------Opções---------");
		System.out.println(OPCAO_MENU_VENDA + " Menu Vendas");
		if (usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.FUNCIONARIO)
				|| usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.ADMINISTRADOR)) {
			System.out.println(OPCAO_MENU_PRODUTO + " Menu Produto");
			System.out.println(OPCAO_MENU_RELATORIO + " Menu Relatórios");
		}
		if (usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.ADMINISTRADOR)){
			System.out.println(OPCAO_MENU_USUARIO + " Menu Usuário");
		}
		
		
		System.out.println(OPCAO_MENU_VOLTAR + " Menu Voltar");
		System.out.print("\nDigite uma opção: ");

		return Integer.parseInt(teclado.nextLine());
	}
}
