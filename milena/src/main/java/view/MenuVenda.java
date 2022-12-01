package view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import controler.ProdutoController;
import model.vo.ProdutoVO;
import model.vo.TipoUsuarioVO;
import model.vo.UsuarioVO;
import model.vo.VendaVO;

public class MenuVenda {
	
	private static final int OPCAO_MENU_CADASTRAR_VENDA = 1;
	private static final int OPCAO_MENU_CANCELAR_VENDA = 2;
	private static final int OPCAO_MENU_SITUACAO_ENTREGA = 3;
	private static final int OPCAO_MENU_VENDA_VOLTAR = 9;
	
	private static int NUMERO_PEDIDO = 0;
	private static double PERCENTUAL = 0.05;
	
	Scanner teclado = new Scanner(System.in);

	public void apresentarMenuVendas(UsuarioVO usuarioVO) {
		int opcao = this.apresentarMenuOpcoes(usuarioVO);
		while(opcao != OPCAO_MENU_VENDA_VOLTAR) {
			switch(opcao) {
				case OPCAO_MENU_CADASTRAR_VENDA: {
					if(!usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.ENTREGADOR)) {
						this.cadastrarVenda(usuarioVO);
					}
					break;
				}
				case OPCAO_MENU_CANCELAR_VENDA: {
					if(!usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.ENTREGADOR)) {
						this.cancelarVenda();
					}
					break;
				}
				
				case OPCAO_MENU_SITUACAO_ENTREGA: {
					if(!usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.CLIENTE)) {
						this.atualizarSituacaoEntrega();
					}
					break;
				}
				default: {
					System.out.println("\nOpção Inválida!");
				}
			}
			opcao = this.apresentarMenuOpcoes(usuarioVO);
		}
	}


	private int apresentarMenuOpcoes(UsuarioVO usuarioVO) {
		System.out.println("\n----------Sistema Foodtruck---------");
		System.out.println("----------Menu Vendas --------");
		System.out.println("\nOpções: ");
		if(!usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.ENTREGADOR)) {
			System.out.println(OPCAO_MENU_CADASTRAR_VENDA + " - Cadastrar Venda");
			System.out.println(OPCAO_MENU_CANCELAR_VENDA + " - Cancelar Venda");
		}
		if(!usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.CLIENTE)) {
			System.out.println(OPCAO_MENU_SITUACAO_ENTREGA + " - Situação da entrega");
		}
		System.out.println(OPCAO_MENU_VENDA_VOLTAR + " - Voltar");
		System.out.println("\nDigite uma opção: ");
		
		return Integer.parseInt(teclado.nextLine());
	}
	
	

	private void cadastrarVenda(UsuarioVO usuarioVO) {
		ArrayList<ProdutoVO> listaProdutosVO = this.listarProdutos();
		VendaVO vendaVO = new VendaVO();
		if(usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.CLIENTE)) {
			vendaVO.setIdUsuario(usuarioVO.getIdUsuario());
		} else {
			System.out.println("Informe o c[odigo do cliente: ");
			vendaVO.setIdUsuario(Integer.parseInt(teclado.nextLine()));
		}
//		vendaVO.setNumeroPedido(this.gerarNumeroPedido());
//		vendaVO.setDataVenda(LocalDateTime.now());
//		double subTotal = 0;
//		ArrayList<ItemVendaVO>
 	}
	
	

	private ArrayList<ProdutoVO> listarProdutos() {
		ProdutoController produtoController = new ProdutoController();
		ArrayList<ProdutoVO> listaProdutosVO = produtoController.consultarTodosProdutosVigentesController();
		System.out.println("\n---------------Lista de Produtos--------------");
		System.out.printf("\n%3s %-13s %-20s %-7s %-24s " ,
				 "ID",  "TIPO PRODUTO", "NOME", "PREÇO", "DATA CADASTRO");
		for(int i = 0; i < listaProdutosVO.size(); i++) {
			listaProdutosVO.get(i).imprimir();
		}
		System.out.println("\n");
		return listaProdutosVO;
	}


	private void atualizarSituacaoEntrega() {
		// TODO Auto-generated method stub
		
	}

	private void cancelarVenda() {
		// TODO Auto-generated method stub
		
	}

}
