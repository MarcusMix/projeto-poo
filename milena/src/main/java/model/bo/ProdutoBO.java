package model.bo;

import java.util.ArrayList;

import model.dao.ProdutoDAO;
import model.vo.ProdutoVO;

public class ProdutoBO {

	public ArrayList<ProdutoVO> cosultarTodosProdutosVigentesBO() {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		ArrayList<ProdutoVO> listaProdutosVO = produtoDAO.consultarTodosProdutosVigentesDAO();
		if(listaProdutosVO.isEmpty()) {
			System.out.println("\nLista de Produtos está vazia!");
		}
		return listaProdutosVO;
	}

	

}
