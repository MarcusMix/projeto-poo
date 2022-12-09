 package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import controller.EntregaController;
import controller.ProdutoController;
import controller.VendaController;
import model.vo.ItemVendaVO;
import model.vo.ProdutoVO;
import model.vo.TipoUsuarioVO;
import model.vo.UsuarioVO;
import model.vo.VendaVO;

public class MenuVenda {
	private static final int OPCAO_MENU_CADASTRAR_VENDA = 1;
	private static final int OPCAO_MENU_CANCELAR_VENDA = 2; // VAMOS FAZER
	private static final int OPCAO_MENU_SITUACAO_ENTREGA = 3; // MUDAR STAUS
	private static final int OPCAO_MENU_VENDA_VOLTAR = 9;

	private static int NUMERO_PEDIDO = 0; // simbolizar senha de
	private static double PERCENTUAL = 0.05; // taxa de 5%

	Scanner teclado = new Scanner(System.in);

	public void apresentarMenuVenda(UsuarioVO usuarioVO) {
		int opcao = this.apresentarOpcoesMenu(usuarioVO);
		while (opcao != OPCAO_MENU_VENDA_VOLTAR) {
			switch (opcao) {
			case OPCAO_MENU_CADASTRAR_VENDA: {
				if (!usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.ENTREGADOR)) {
					this.cadastrarVenda(usuarioVO);
				}
				break;
			}
			case OPCAO_MENU_CANCELAR_VENDA: {
				if (!usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.ENTREGADOR)) {
					this.cancelarVenda();
				}
				break;
			}
			case OPCAO_MENU_SITUACAO_ENTREGA: {
				if (!usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.CLIENTE)) {
					this.atualizarSituacaoEntrega();
				}
				break;
			}

			default: {
				System.out.println("\nOpção Inválida");
			}
			}
			opcao = this.apresentarOpcoesMenu(usuarioVO);
		}
	}

	private int apresentarOpcoesMenu(UsuarioVO usuarioVO) {
		System.out.println("\n--------- Sistema Foodtruck --------");
		System.out.println("--------- Menu de Produto ------------");
		System.out.println("\nOpções: ");
		if (!usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.ENTREGADOR)) { // qualquer perfil menos Entregador
			System.out.println(OPCAO_MENU_CADASTRAR_VENDA + " - Cadastrar Venda");
			System.out.println(OPCAO_MENU_CANCELAR_VENDA + " - Cancelar Venda");
		}
		if (!usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.CLIENTE)) { // qualquer perfil menos Cliente
			System.out.println(OPCAO_MENU_SITUACAO_ENTREGA + " - Situação da Entrega");
		}
		System.out.println(OPCAO_MENU_VENDA_VOLTAR + " - Voltar");
		System.out.println("\nDigite uma opção: ");
		return Integer.parseInt(teclado.nextLine());
	}

	private void cadastrarVenda(UsuarioVO usuarioVO) {
		ArrayList<ProdutoVO> listaProdutosVO = this.listarProdutos();
		VendaVO vendaVO = new VendaVO();
		if (usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.CLIENTE)) {// proprio cliente comprando
			vendaVO.setIdUsuario(usuarioVO.getIdUsuario());
		} else {
			System.out.print("Informe o código do cliente: ");
			vendaVO.setIdUsuario(Integer.parseInt(teclado.nextLine()));
		}
		vendaVO.setNumeroPedido(this.gerarNumeroPedido());
		vendaVO.setDataVenda(LocalDateTime.now());
		double subTotal = 0;
		ArrayList<ItemVendaVO> listaItemVendaVO = new ArrayList<ItemVendaVO>();
		String continuar = "N";
		do {
			ItemVendaVO itemVendaVO = new ItemVendaVO();
			subTotal += this.cadastrarItemVendaVO(itemVendaVO, listaProdutosVO);
			listaItemVendaVO.add(itemVendaVO);
			System.out.print("Deseja incluir mais um item? [S - N]: ");
			continuar = teclado.nextLine();
		} while (continuar.equalsIgnoreCase("S"));
		vendaVO.setListaItemVendaVO(listaItemVendaVO);

		System.out.print("Pedido é para Entregar [S - N]");
		String opcaoEntrega = teclado.nextLine();
		if (opcaoEntrega.toUpperCase().equals("S") || opcaoEntrega.toUpperCase().equals("N")) {
			double taxaEntrega = subTotal * PERCENTUAL;
			double totalConta = subTotal;
			if (opcaoEntrega.toUpperCase().equals("S")) {
				vendaVO.setFlagEntrega(true);
				vendaVO.setTaxaEntrega(taxaEntrega);
				totalConta += taxaEntrega;
			}
			System.out.println("Total da Conta - R$ " + totalConta);
			if (this.validarCamposCadastro(vendaVO)) {
				VendaController vendaController = new VendaController();
				vendaVO = vendaController.cadastrarVendaController(vendaVO);
				if (vendaVO.getIdVenda() != 0) {
					System.out.println("\nVenda cadastrada com Sucesso.");
				} else {
					System.out.println("Não foi possível cadastrar a venda.");
				}
			}
		} else {
			System.out.println("Você digitou uma opção inválida.");
		}
	}

	private double cadastrarItemVendaVO(ItemVendaVO itemVendaVO, ArrayList<ProdutoVO> listaProdutosVO) {
		System.out.print("Informe o código do produto: ");
		itemVendaVO.setIdProduto(Integer.parseInt(teclado.nextLine()));
		System.out.print("Informe a quantidade do produto: ");
		itemVendaVO.setQuantidade(Integer.parseInt(teclado.nextLine()));
		double valor = 0;
		for (ProdutoVO produto : listaProdutosVO) {
			if (produto.getIdProduto() == itemVendaVO.getIdProduto()) {
				valor = produto.getPreco() * itemVendaVO.getQuantidade();
			}
		}
		return valor;
	}

	private ArrayList<ProdutoVO> listarProdutos() {
		ProdutoController produtoController = new ProdutoController();
		ArrayList<ProdutoVO> listaProdutosVO = produtoController.consultarTodosProdutosVigentesController();
		System.out.println("\n---------- Lista de Produtos ----------");
		System.out.printf("\n%3s  %-13s  %-20s  %-7s  %-24s  ", "ID", "TIPO PRODUTO", "NOME", "PRECO", "DATACADASTRO");
		for (ProdutoVO element : listaProdutosVO) {
			element.imprimir();
		}
		System.out.println("\n");
		return listaProdutosVO;
	}

	private boolean validarCamposCadastro(VendaVO vendaVO) {
		boolean resultado = true;
		System.out.println();
		if (vendaVO.getIdUsuario() == 0) {
			System.out.println("O campo código do usuário é obrigatório.");
			resultado = false;
		}
		if (vendaVO.getListaItemVendaVO() == null || vendaVO.getListaItemVendaVO().isEmpty()) {
			System.out.println("O campo dos produtos vendidos é obrigatório.");
			resultado = false;
		}
		return resultado;
	}

	private int gerarNumeroPedido() {
		if (NUMERO_PEDIDO == 99) {
			NUMERO_PEDIDO = 0;
		} else {
			NUMERO_PEDIDO++;
		}
		return NUMERO_PEDIDO;
	}

	private void cancelarVenda() {
		VendaVO vendaVO = new VendaVO();
		System.out.print("\nInforme o código da venda: ");
		vendaVO.setIdVenda(Integer.parseInt(teclado.nextLine()));
		System.out.print("Digite a data do cancelamento no formato dd/MM/yyyy HH:mm:ss ");
		vendaVO.setDataCancelamento(
				LocalDateTime.parse(teclado.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
		if (vendaVO.getIdVenda() == 0 || vendaVO.getDataCancelamento() == null) {
			System.out.println("Os campos código da venda e data de cancelamento são obrigaótiros.");
		} else {
			VendaController vendaController = new VendaController();
			boolean resultado = vendaController.cancelarVendaController(vendaVO);
			if (resultado) {
				System.out.println("\nVenda cancelada com Sucesso.");
			} else {
				System.out.println("Não foi possível cancelar a venda.");
			}

		}
	}

	private void atualizarSituacaoEntrega() {
		VendaVO vendaVO = new VendaVO();
		System.out.print("\nInforme o código da venda: ");
		vendaVO.setIdVenda(Integer.parseInt(teclado.nextLine()));

		EntregaController entregaController = new EntregaController();
		boolean resultado = entregaController.atualizarSituacaoEntregaController(vendaVO);
		if (resultado) {
			System.out.println("\nSituação da entrega da venda atualizada com sucesso.");
		} else {
			System.out.println("Não foi possível atualizar a situação da entrega na venda.");
		}
	}

}
