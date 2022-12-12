package controller;

import java.util.ArrayList;

import model.bo.ProdutoBO;
import model.vo.ProdutoVO;
import model.vo.TipoProdutoVO;

public class ProdutoController {

	public ProdutoVO cadastrarProdutoController(ProdutoVO produtoVO) {
		ProdutoBO produtoBO = new ProdutoBO();
		return produtoBO.cadastrarProdutosBO(produtoVO);
	}

	public boolean atualizarProdutoController(ProdutoVO produtoVO) {
		ProdutoBO produtoBO = new ProdutoBO();
		return produtoBO.atualizarProdutoBO(produtoVO);
	}

	public static boolean excluirProdutoController(ProdutoVO produtoVO) {
		ProdutoBO produtoBO = new ProdutoBO();
		return produtoBO.excluirUsuarioBO(produtoVO);
	}

	public ArrayList<TipoProdutoVO> consultarTipoProdutos() {
		ProdutoBO produtoBO = new ProdutoBO();
		return produtoBO.consultarTipoProdutosBO();
	}

	public ProdutoVO consultarProdutoController(ProdutoVO produtoVO) {
		ProdutoBO produtoBO = new ProdutoBO();
		return produtoBO.consultarProdutosBO(produtoVO);
	}

	public ArrayList<ProdutoVO> consultarTodosProdutosVigentesController() {
		ProdutoBO produtoBO = new ProdutoBO();
		return produtoBO.consultarTodosProdutosVigentesBO();
	}
}
