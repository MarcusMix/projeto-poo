package model.bo;

import java.util.ArrayList;

import model.dao.ProdutoDAO;
import model.dao.UsuarioDAO;
import model.vo.ProdutoVO;
import model.vo.TipoProdutoVO;

public class ProdutoBO {

	public boolean excluirUsuarioBO(ProdutoVO produtoVO) {
		boolean resultado = false;
		ProdutoDAO produtoDAO = new ProdutoDAO();
		if(produtoDAO.verificarExistenciaRegistroPorIdProdutoDAO(produtoVO.getIdProduto())) { 
			if(produtoDAO.verificarRemocaoPorIdProdutoDAO(produtoVO.getIdProduto())) {
				System.out.println("\nProduto já se encontra deletado da base de dados!");
			}else {
				resultado = produtoDAO.excluirProdutoDAO(produtoVO);
			}
		}else {
			System.out.println("\nProduto não existe na base de dados!");
		}
		return resultado;
	}

	public ProdutoVO cadastrarProdutosBO(ProdutoVO produtoVO) {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		if(produtoDAO.verificarExistenciaRegistroPorNomeDAO(produtoVO)) {
			System.out.println("\nProduto já cadastrado!");
		} else {
			produtoVO = produtoDAO.cadastrarProdutosDAO(produtoVO);
		}
		return produtoVO;
	}

	public ArrayList<TipoProdutoVO> consultarTipoProdutosBO() {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		return produtoDAO.consultarTipoProdutosDAO();
	}



	public ProdutoVO consultarProdutosBO(ProdutoVO produtoVO) {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		ProdutoVO produto = produtoDAO.consultarProdutoDAO(produtoVO);
		if(produto == null) {
			System.out.println("\nUsuário não localizado.");
		}
		return produto;
	}
	
	public boolean atualizarProdutoBO(ProdutoVO produtoVO) {
		boolean resultado = false;
		ProdutoDAO produtoDAO = new ProdutoDAO();
		resultado = produtoDAO.atualizarProdutoDAO(produtoVO);
		if (resultado) {
			System.out.println("Produto atualizado com sucesso!");
		} else {
			System.out.println("Não foi possível atualizar o produto!");
		}
		return resultado;
	}
	
	public ArrayList<ProdutoVO> consultarTodosProdutosVigentesBO() {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		ArrayList<ProdutoVO> listaProdutosVO = produtoDAO.consultarTodosProdutosVigentesDAO();
		if(listaProdutosVO.isEmpty()) {
			System.out.println("\nLista de Produtos está vazia.");
		}
		return listaProdutosVO;
	}
}
