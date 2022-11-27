package controler;

import java.util.ArrayList;

import model.bo.ProdutoBO;
import model.vo.ProdutoVO;

public class ProdutoController {

	public ArrayList<ProdutoVO> consultarTodosProdutosVigentesController() {
		ProdutoBO produtoBO = new ProdutoBO();
		return produtoBO.cosultarTodosProdutosVigentesBO();
		
	}

	
}
