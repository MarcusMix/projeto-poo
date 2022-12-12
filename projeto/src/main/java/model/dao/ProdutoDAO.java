package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import model.vo.ProdutoVO;
import model.vo.TipoProdutoVO;
import model.vo.TipoUsuarioVO;

public class ProdutoDAO {

	public boolean excluirProdutoDAO(ProdutoVO produtoVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean retorno = false;
		String query = "UPDATE produto SET dataexclusao = '" + produtoVO.getDataExclusao()
				+ "' WHERE idproduto = "
				+ produtoVO.getIdProduto();
		try {
			if (stmt.executeUpdate(query) == 1) {
				retorno = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método excluirProdutoDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public boolean verificarRemocaoPorIdProdutoDAO(int idProduto) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;
		String query = "SELECT dataexclusao FROM produto WHERE idproduto = " + idProduto;
		try {
			resultado = stmt.executeQuery(query);
            if (resultado.next()){
                String dataExpiracao = resultado.getString(1);
                if(dataExpiracao != null) {
                    retorno = true;
                }
            }
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método verificarRemocaoPorIdProdutoDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public boolean verificarExistenciaRegistroPorIdProdutoDAO(int i) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;
		String query = "SELECT idproduto FROM produto WHERE idproduto = " + i;
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				retorno = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método verificarExistenciaRegistroPorIdProdutoDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}

		return retorno;
	}

	public boolean verificarExistenciaRegistroPorNomeDAO(ProdutoVO produtoVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;
		String query = "SELECT nome FROM produto WHERE nome = '" + produtoVO.getNome() + "'";
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				retorno = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método verificarExistenciaRegistroPorNomeDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}

		return retorno;
	}

	public ProdutoVO cadastrarProdutosDAO(ProdutoVO produtoVO) {
		String query = "INSERT INTO produto (idtipoproduto, nome, preco, datacadastro) VALUES (?, ?, ?, ?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			pstmt.setInt(1, produtoVO.getTipoProdutoVO().getValor());
			pstmt.setString(2, produtoVO.getNome());
			pstmt.setDouble(3, produtoVO.getPreco());
			pstmt.setObject(4, produtoVO.getDataCadastro());
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys(); 
			if (resultado.next()) {
				produtoVO.setIdProduto(resultado.getInt(1));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a quarto do médoto cadastrarProdutoDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return produtoVO;
	}

	public ArrayList<TipoProdutoVO> consultarTipoProdutosDAO() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		ArrayList<TipoProdutoVO> listaTipoProdutoVO = new ArrayList<TipoProdutoVO>();
		String query = "SELECT descricao FROM tipoproduto";
		try {
			resultado = stmt.executeQuery(query);
			while (resultado.next()) {
				TipoProdutoVO tipoProdutoVO = TipoProdutoVO.valueOf(resultado.getString(1));
				listaTipoProdutoVO.add(tipoProdutoVO);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método consultarTipoProdutosDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
	
		return listaTipoProdutoVO;
	}

	
	public ArrayList<ProdutoVO> consultarTodosProdutosVigentesDAO() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		ArrayList<ProdutoVO> listaProdutosVO = new ArrayList<ProdutoVO>();
		String query = "SELECT p.idProduto, tipo.descricao, p.nome, p.preco, p.dataCadastro "
				+ "FROM produto p, tipoProduto tipo " + "WHERE p.idTipoProduto = tipo.idTipoProduto "
				+ "AND p.dataExclusao is NULL";
		try {
			resultado = stmt.executeQuery(query);
			while (resultado.next()) {
				ProdutoVO produtoVO = new ProdutoVO();
				produtoVO.setIdProduto(Integer.parseInt(resultado.getString(1)));
				produtoVO.setTipoProdutoVO(TipoProdutoVO.valueOf(resultado.getString(2)));
				produtoVO.setNome(resultado.getString(3));
				produtoVO.setPreco(Double.parseDouble(resultado.getString(4)));
				produtoVO.setDataCadastro(LocalDateTime.parse(resultado.getString(5),
						DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				listaProdutosVO.add(produtoVO);

			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método consultarTodosProdutosVigentesDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}

		return listaProdutosVO;
	}
	
	public ProdutoVO consultarProdutoDAO(ProdutoVO produtoVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		String query = "SELECT p.idProduto, tipo.descricao, p.nome, p.preco, " 
				+ "p.dataCadastro, p.dataExclusao "
				+ "FROM produto p, tipoProduto tipo " + "WHERE p.idTipoProduto = tipo.idTipoProduto "
				+ "AND p.idProduto = " + produtoVO.getIdProduto();
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				produtoVO.setIdProduto(Integer.parseInt(resultado.getString(1)));
				produtoVO.setTipoProdutoVO(TipoProdutoVO.valueOf(resultado.getString(2)));
				produtoVO.setNome(resultado.getString(3));
				produtoVO.setPreco(Double.parseDouble(resultado.getString(4)));
				produtoVO.setDataCadastro(LocalDateTime.parse(resultado.getString(5),
						DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				if (resultado.getString(6) != null) {
					produtoVO.setDataExclusao(LocalDateTime.parse(resultado.getString(6),
							DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				}
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método consultarUsuarioDAO!");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return produtoVO;

	}

	public boolean atualizarProdutoDAO(ProdutoVO produtoVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean retorno = false;
		String query = "UPDATE produto SET idtipoproduto = " + produtoVO.getTipoProdutoVO().getValor() + ", nome = '"
				+ produtoVO.getNome() + "', preco = '" + produtoVO.getPreco() +  "' WHERE idproduto = " + produtoVO.getIdProduto();

		try {
			if (stmt.executeUpdate(query) == 1) {
				retorno = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método atualizarProdutoDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

}
