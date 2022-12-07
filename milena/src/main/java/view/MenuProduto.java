package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import controller.ProdutoController;
import model.vo.ProdutoVO;
import model.vo.TipoProdutoVO;

public class MenuProduto {
	private static final int OPCAO_MENU_CADASTRAR_PRODUTO = 1;
	private static final int OPCAO_MENU_CONSULTAR_PRODUTO = 2;
	private static final int OPCAO_MENU_ATUALIZAR_PRODUTO = 3;
	private static final int OPCAO_MENU_EXCLUIR_PRODUTO = 4;
	private static final int OPCAO_MENU_PRODUTO_VOLTAR = 9;

	private static final int OPCAO_MENU_CONSULTAR_TODOS_PRODUTOS = 1;
	private static final int OPCAO_MENU_CONSULTAR_UM_PRODUTOS = 2;
	private static final int OPCAO_MENU_CONSULTAR_PRODUTO_VOLTAR = 9;

	Scanner teclado = new Scanner(System.in);

	public void apresentarMenuProduto() {
		int opcao = this.apresentarOpcoesMenu();
		while (opcao != OPCAO_MENU_PRODUTO_VOLTAR) {
			switch (opcao) {
			case OPCAO_MENU_CADASTRAR_PRODUTO: {
				ProdutoVO produtoVO = new ProdutoVO();
				this.cadastrarProduto(produtoVO);
				break;
			}
			case OPCAO_MENU_CONSULTAR_PRODUTO: {
				this.consultarProduto();
				break;
			}
			case OPCAO_MENU_ATUALIZAR_PRODUTO: {
				this.atualizarProduto();
				break;
			}
			case OPCAO_MENU_EXCLUIR_PRODUTO: {
				this.excluirProduto();
				break;
			}
			default: {
				System.out.println("\nOpção Inválida");
			}
			}
			opcao = this.apresentarOpcoesMenu();
		}
	}

	private int apresentarOpcoesMenu() {
		System.out.println("\n--------- Sistema Foodtruck --------");
		System.out.println("--------- Menu de Produto ------------");
		System.out.println("\nOpções: ");
		System.out.println(OPCAO_MENU_CADASTRAR_PRODUTO + " - Cadastrar Produto");

		System.out.println(OPCAO_MENU_CONSULTAR_PRODUTO + " - Consultar Produto");

		System.out.println(OPCAO_MENU_ATUALIZAR_PRODUTO + " - Atualizar Produto");

		System.out.println(OPCAO_MENU_EXCLUIR_PRODUTO + " - Excluir Produto");

		System.out.println(OPCAO_MENU_PRODUTO_VOLTAR + " - Voltar");

		System.out.println("\nDigite uma opção: ");
		return Integer.parseInt(teclado.nextLine());
	}

	private void cadastrarProduto(ProdutoVO produtoVO) {
		if (produtoVO.getTipoProdutoVO() == null) {// verificando se quem tá chamando é o adm
			do {
				produtoVO
						.setTipoProdutoVO(TipoProdutoVO.getTipoProdutoVOPorValor(this.apresentarOpcoesTipoProdutoVO()));
			} while (produtoVO.getTipoProdutoVO() == null);
		}
		System.out.println("\nDigite o Nome: ");
		produtoVO.setNome(teclado.nextLine());
		System.out.println("\nDigite o Preço: ");
		produtoVO.setPreco(Double.parseDouble(teclado.nextLine()));
		produtoVO.setDataCadastro(LocalDateTime.now());

		if (this.validarCamposCadastro(produtoVO)) {
			ProdutoController ProdutoController = new ProdutoController();
			produtoVO = ProdutoController.cadastrarProdutoController(produtoVO);
			if (produtoVO.getIdProduto() != 0) { // se ele tiver uma chave primaria == ele foi cadastrado
				System.out.println("Produto foi cadastrado com sucesso!");
			} else {
				System.out.println("Não foi possivel cadastrar o Produto!");
			}

		}
	}

	private int apresentarOpcoesTipoProdutoVO() {
		ProdutoController produtoController = new ProdutoController();
		ArrayList<TipoProdutoVO> listaTipoProdutoVO = produtoController.consultarTipoProdutos();
		System.out.println("\n--------- Tipos de Produtos ---------");
		System.out.println("Opções: ");
		for (TipoProdutoVO element : listaTipoProdutoVO) {
			System.out.println(element.getValor() + " - " + element);
			// get i pega o numero que esta no ENUM (1 2 3 4 as classes de pessoas dos
			// sistema)
		}

		System.out.print("\nDigite uma opção: ");
		return Integer.parseInt(teclado.nextLine());
	}

	private boolean validarCamposCadastro(ProdutoVO produtoVO) {
		boolean resultado = true;
		if (produtoVO.getNome() == null || produtoVO.getNome().isEmpty()) { // get.Nome é String que é Referencia
			System.out.println("O campo nome é obrigatório.");
			resultado = false;
		}
		if (produtoVO.getPreco() <= 0) {
			System.out.println("O campo preço é obrigatório.");
			resultado = false;
		}

		if (produtoVO.getDataCadastro() == null) { //
			System.out.println("O campo data de cadastro é obrigatório.");
			resultado = false;
		}
		return resultado;
	}

	private void consultarProduto() {
		// TODO Auto-generated method stub

	}

	private void atualizarProduto() {
		// TODO Auto-generated method stub

	}

	private void excluirProduto() {
		ProdutoVO produtoVO = new ProdutoVO();
		System.out.print("\nInforme o código do produto: ");
		produtoVO.setIdProduto(Integer.parseInt(teclado.nextLine()));
		System.out.print("\nDigite a Data de expiração no formado dd/MM/yyyy HH:mm:ss");
		produtoVO.setDataExclusao(
				LocalDateTime.parse(teclado.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

		if (produtoVO.getIdProduto() == 0 || produtoVO.getDataExclusao() == null) { // nao preencheu as informaçoes
			System.out.println("Os campos código do produto e data de exclusão são obrigatórios.");
		} else {
			ProdutoController produtoController = new ProdutoController();
			boolean resultado = ProdutoController.excluirProdutoController(produtoVO);
			if (resultado) {
				System.out.println("\nProduto excluído com sucesso!");
			} else {
				System.out.println("\nNão foi possível excluir o Produto.");
			}
		}
	}

}
