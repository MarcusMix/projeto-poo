package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import model.vo.TipoUsuarioVO;
import model.vo.UsuarioVO;

public class UsuarioDAO {

	public UsuarioVO realizarLoginDAO(UsuarioVO usuarioVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		
		 String query = "SELECT u.idusuario, tipo.descricao, u.nome, u.cpf, u.email, "
				+ " u.telefone, u.datacadastro, u.dataexpiracao "
				+ "FROM USUARIO u, TIPOUSUARIO tipo "
				+ "WHERE u.login like '" + usuarioVO.getLogin() + "' "
				+ "AND u.senha like '" + usuarioVO.getSenha() + "' "
				+ "AND u.idtipousuario = tipo.idtipousuario";
		
		
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				usuarioVO.setIdUsuario(Integer.parseInt(resultado.getString(1)));
				usuarioVO.setTipoUsuarioVO(TipoUsuarioVO.valueOf(resultado.getString(2)));
				usuarioVO.setNome(resultado.getString(3));
				usuarioVO.setCpf(resultado.getString(4));
				usuarioVO.setEmail(resultado.getString(5));
				usuarioVO.setTelefone(resultado.getString(6));
				usuarioVO.setDataCadrasto(LocalDateTime.parse(resultado.getString(7), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				if(resultado.getString(8) != null) {
					usuarioVO.setDataExpiracao(LocalDateTime.parse(resultado.getString(8), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
					
				}
				
			}
			
		} catch (SQLException error) {
			System.out.println("Erro ao executar a query no método realizarLoginDAO! ");
			System.out.println("Erro: " + error.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		
		return usuarioVO;
	}

	public ArrayList<TipoUsuarioVO> consultarTipoUsuariosDAO() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		ArrayList<TipoUsuarioVO> listaTipoUsuarioVO = new ArrayList<TipoUsuarioVO>();
		
		String query = "SELECT descricao FROM tipousuario";
		
		try {
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				TipoUsuarioVO tipoUsuarioVO = TipoUsuarioVO.valueOf(resultado.getString(1));
				listaTipoUsuarioVO.add(tipoUsuarioVO);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método consultarTipoUsuariosDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaTipoUsuarioVO;
	}
	
	public UsuarioVO cadastrarUsuarioDAO(UsuarioVO usuarioVO) {
		String query = "INSERT INTO usuario (idtipousuario, nome, cpf, email, telefone, "
				+ "datacadastro, login, senha) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		
		try {
			pstmt.setInt(1, usuarioVO.getTipoUsuarioVO().getValor());
			pstmt.setString(2, usuarioVO.getNome());
			pstmt.setString(3, usuarioVO.getCpf());
			pstmt.setString(4, usuarioVO.getEmail());
			pstmt.setString(5, usuarioVO.getTelefone());
			pstmt.setObject(6, usuarioVO.getDataCadrasto());
			pstmt.setString(7, usuarioVO.getLogin());
			pstmt.setString(8, usuarioVO.getSenha());
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			if(resultado.next()) {
				usuarioVO.setIdUsuario(resultado.getInt(1));
			}
		}catch (SQLException erro){
			System.out.println("Erro ao executar a query do método cadastrarUsuarioDAO! ");
			System.out.println("Erro: " + erro.getMessage());
		}finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return usuarioVO;
	}

	public boolean verificarExistenciaRegistroPorCpfDAO(UsuarioVO usuarioVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;
		
		String query = "SELECT cpf FROM usuario WHERE cpf = '" + usuarioVO.getCpf() + "'";
		
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				retorno = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método verificarExistenciaRegistroPorCpfDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public boolean verificarExistenciaRegistroPorIdUsuarioDAO(int idUsuario) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;
		
		String query = "SELECT idusuario FROM usuario WHERE idusuario = " + idUsuario;
				
		
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				retorno = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método verificarExistenciaRegistroPorIdUsuarioDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public boolean verificarDesligamentoDeUsuarioPorIdUsuarioDAO(int idUsuario) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;
		
		String query = "SELECT dataexpiracao FROM usuario WHERE idusuario = " + idUsuario;
				
		
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				String dataEspiracao = resultado.getString(1);
				if(dataEspiracao != null) {
					retorno = true;					
				}
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método verificarDesligamentoDeUsuarioPorIdUsuarioDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}


	public boolean excluirUsuarioDAO(UsuarioVO usuarioVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean retorno = false;
		
		String query = "UPDATE usuario SET dataexpiracao = '" + usuarioVO.getDataExpiracao()
					   +"' WHERE idusuario = " + usuarioVO.getIdUsuario();
				
		
		try {
			if(stmt.executeUpdate(query) == 1) {
				retorno = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método excluirUsuarioDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public boolean atualizarUsuarioDAO(UsuarioVO usuarioVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean retorno = false;
		
		String query = "UPDATE usuario SET idtipousuario = " + usuarioVO.getTipoUsuarioVO().getValor() 
				+ ", nome = '" + usuarioVO.getNome()
				+ "', cpf = '" + usuarioVO.getCpf()
				+ "', email = '" + usuarioVO.getEmail()
				+ "', telefone = '" + usuarioVO.getTelefone()
				+ "', login = '" + usuarioVO.getLogin()
				+ "', senha = '" + usuarioVO.getSenha()
				+ "' WHERE idusuario = " + usuarioVO.getIdUsuario();
		try {
			if(stmt.executeUpdate(query) == 1) {
				retorno = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método atualizarUsuarioDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public ArrayList<UsuarioVO> consultarTodosUsuariosDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	public UsuarioVO consultarUsuarioDAO(UsuarioVO usuarioVO) {
		// TODO Auto-generated method stub
		return null;
	}

}
