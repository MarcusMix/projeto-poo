package view;

import java.util.Scanner;

import model.vo.TipoUsuarioVO;
import model.vo.UsuarioVO;

public class Menu {
	private static final int OPCAO_MENU_VENDA = 1;
	private static final int OPCAO_MENU_PRODUTO = 2;
	private static final int OPCAO_MENU_RELATORIO = 3;
	private static final int OPCAO_MENU_USUARIO = 4;
	private static final int OPCAO_MENU_VOLTAR = 9;

	Scanner teclado = new Scanner(System.in);

	public void apresentarMenu(UsuarioVO usuarioVO) {

		int opcao = this.apresentarOpcoesMenu(usuarioVO);
		while (opcao != OPCAO_MENU_VOLTAR) {
			switch (opcao) {
			case OPCAO_MENU_VENDA: {
				MenuVenda menuVenda = new MenuVenda();
				menuVenda.apresentarMenuVenda(usuarioVO);
				break;
			}
			case OPCAO_MENU_PRODUTO: {
				if (usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.FUNCIONARIO) || usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.ADMINISTRADOR)) {
					MenuProduto menuProduto = new MenuProduto();
					menuProduto.apresentarMenuProduto();
				}
				break;
			}
			case OPCAO_MENU_RELATORIO: {
				if (usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.FUNCIONARIO) || usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.ADMINISTRADOR)) {
					MenuRelatorio menuRelatorio = new MenuRelatorio();
					menuRelatorio.apresentarMenuRelatorio();
				}
				break;
			}
			case OPCAO_MENU_USUARIO: {
				if (usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.ADMINISTRADOR)) {
					MenuUsuario menuUsuario = new MenuUsuario();
					menuUsuario.apresentarMenuUsuario();
				}

				break;
			}
			default: {
				System.out.println("Opção Inválida");
			}
			}
			opcao = this.apresentarOpcoesMenu(usuarioVO);
		}
	}

	private int apresentarOpcoesMenu(UsuarioVO usuarioVO) {
		System.out.println("\n---------Sistema Foodtruck---------");
		System.out.println("\n---------Menu Principal------------");
		System.out.println("\n---------Opções--------------------");
		System.out.println(OPCAO_MENU_VENDA + " - Menu Vendas");
		if (usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.FUNCIONARIO) || usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.ADMINISTRADOR)) {
			System.out.println(OPCAO_MENU_PRODUTO + " - Menu Produto");
			System.out.println(OPCAO_MENU_RELATORIO + " - Menu Relatórios");
		}
		if (usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.ADMINISTRADOR)) {
			System.out.println(OPCAO_MENU_USUARIO + " - Menu Usuários");
		}
		System.out.println(OPCAO_MENU_VOLTAR + " - Menu Voltar");
		System.out.print("\nDigite uma opção: ");

		return Integer.parseInt(teclado.nextLine());
	}
}
