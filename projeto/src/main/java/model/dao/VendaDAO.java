package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.bo.VendaBO;
import model.vo.ItemVendaVO;
import model.vo.VendaVO;

public class VendaDAO {

	public VendaVO cadastraVendaDAO(VendaVO vendaVO) {
		String query = "INSERT INTO venda (idUsuario, numeroPedido, dataVenda, flagEntrega";
		if (vendaVO.isFlagEntrega()) {
			query += ", taxaEntrega) VALUES (?, ?, ?, ?, ?)";
		} else {
			query += ") VALUES (?, ?, ?, ?)";
		}
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			pstmt.setInt(1, vendaVO.getIdUsuario());
			pstmt.setInt(2, vendaVO.getNumeroPedido());
			pstmt.setObject(3, vendaVO.getDataVenda());
			if (vendaVO.isFlagEntrega()) {
				pstmt.setInt(4, 1);
				pstmt.setDouble(5, vendaVO.getTaxaEntrega());
			} else {
				pstmt.setInt(4, 0);
			}
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			if (resultado.next()) {
				vendaVO.setIdVenda(resultado.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query do método cadastrarVendaDAO!");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return vendaVO;
	}

	public VendaVO consultarVendaDAO(VendaVO vendaVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		VendaVO venda = new VendaVO();
		String query = "SELECT idVenda, idUsuario, numeroPedido, dataVenda, dataCancelamento, flagEntrega, taxaEntrega "
				+ "FROM venda " + "WHERE idVenda = " + vendaVO.getIdVenda();
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				venda.setIdVenda(Integer.parseInt(resultado.getString(1)));
				venda.setIdUsuario(Integer.parseInt(resultado.getString(2)));
				venda.setNumeroPedido(Integer.parseInt(resultado.getString(3)));
				venda.setDataVenda(LocalDateTime.parse(resultado.getString(4),
						DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				if (resultado.getString(5) != null) {
					venda.setDataCancelamento(LocalDateTime.parse(resultado.getString(5),
							DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				}
				if (Integer.parseInt(resultado.getString(6)) == 1) {
					venda.setFlagEntrega(true);
					venda.setTaxaEntrega(Double.parseDouble(resultado.getString(7)));
				} else {

				}
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query do método cadastrarVendaDAO!");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conn);
		}
		return venda;
	}

	public boolean cadastrarItemVendaDAO(VendaVO vendaVO) {
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = null;
		int contador = 0;
		boolean retorno = false;
		ResultSet resultado = null;
		try {
			for (ItemVendaVO item : vendaVO.getListaItemVendaVO()) {
				String query = "INSERT INTO itemVenda (idVenda, idProduto, quantidade) VALUES (?, ?, ?)";
				pstmt = Banco.getPreparedStatementWithPk(conn, query);
				pstmt.setInt(1, vendaVO.getIdVenda());
				pstmt.setInt(2, item.getIdProduto());
				pstmt.setInt(3, item.getQuantidade());
				pstmt.execute();
				resultado = pstmt.getGeneratedKeys();
				if (resultado.next()) {
					item.setIdItemVenda(resultado.getInt(1));
					contador++;
				}
			}
			if (contador == vendaVO.getListaItemVendaVO().size()) {
				retorno = true;
				System.out.println("Venda cadastrada! ");
			} else {
				System.out.println("Nem todos os produtos foram cadastrados.");
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query do método cadastrarItemVendaDAO!");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public boolean verificarExistenciaRegistroPorIdVendaDAO(int idVenda) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;
		String query = "SELECT idvenda FROM venda WHERE idvenda = " + idVenda;
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				retorno = true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query do método verificarExistenciaRegistroPorIdVendaDAO!");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public boolean verificarVendaPossuiEntrega(int idVenda) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;
		String query = "SELECT flagEntrega FROM venda WHERE idvenda = " + idVenda;
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				if (resultado.getString(1).equals("1")) {
					retorno = true;
				}
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query do método verificarVendaPossuiEntrega!");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public boolean verificarCancelamentoPorIdVendaDAO(int idVenda) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;
		String query = "SELECT datacancelamento FROM venda WHERE idvenda = " + idVenda;
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.getString(1) != null) {
				retorno = true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query do método verificarCancelamentoPorIdVendaDAO!");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public boolean excluirVendaDAO(VendaVO vendaVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean retorno = false;
		String query = "UPDATE venda SET datacancelamento = '" + vendaVO.getDataCancelamento()
		+ "' WHERE idvenda = "
		+ vendaVO.getIdVenda();
		try {
			if (stmt.executeUpdate(query) == 1) {
				retorno = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método excluirVendaDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}


}
