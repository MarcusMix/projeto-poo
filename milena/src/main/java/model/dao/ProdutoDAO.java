package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


import model.vo.ProdutoVO;
import model.vo.TipoProdutoVO;

public class ProdutoDAO {

	public ArrayList<ProdutoVO> consultarTodosProdutosVigentesDAO() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		ArrayList<ProdutoVO> listaProdutosVO = new ArrayList<ProdutoVO>();
		String query = "SELECT p.idProduto, tipo.descricao, p.nome, p.preco, p.dataCadastro "
				+ "FROM produto p, tipoProduto tipo "
				+ "WHERE p.idTipoProduto = tipo.idTipoProduto "
				+ "AND p.dataExclusao is NULL";
		try {
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				ProdutoVO produtoVO = new ProdutoVO();
				produtoVO.setIdProduto(Integer.parseInt(resultado.getString(1)));
				produtoVO.setTipoProduto(TipoProdutoVO.valueOf(resultado.getString(2)));
				produtoVO.setNome(resultado.getString(3));
				produtoVO.setPreco(Double.parseDouble(resultado.getString(4)));
				produtoVO.setDataCadastro(LocalDateTime.parse(resultado.getString(5), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				listaProdutosVO.add(produtoVO);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query do método consultarTodosProdutosVigentesDAO!");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaProdutosVO;
	}

}
