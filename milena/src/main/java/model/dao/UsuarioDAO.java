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
		Connection conn = Banco.getConnection(); // cria a conexão
		Statement stmt = Banco.getStatement(conn);// objeto executor de trafegar o sql
		ResultSet resultado = null; // recupera a resposta do banco

		String query = "SELECT u.idusuario, tipo.descricao, u.nome, u.cpf, u.email, "
				+ "u.telefone, u.datacadastro, u.dataexpiracao " + "from USUARIO u, TIPOUSUARIO tipo "
				+ "WHERE u.login like '" + usuarioVO.getLogin() + "' " + "AND u.senha like '" + usuarioVO.getSenha()
				+ "' " + "AND u.idtipousuario = tipo.idtipousuario";
		// escrever a query

		try {
			resultado = stmt.executeQuery(query);// resultado coletando a resposta
			// if pq so retorna 1 senão seria while
			if (resultado.next()) {// dando true é pq corresponte
				usuarioVO.setIdUsuario(Integer.parseInt(resultado.getString(1)));
				usuarioVO.setTipoUsuarioVO(TipoUsuarioVO.valueOf(resultado.getString(2)));
				usuarioVO.setNome(resultado.getString(3));
				usuarioVO.setCpf(resultado.getString(4));
				usuarioVO.setEmail(resultado.getString(5));
				usuarioVO.setTelefone(resultado.getString(6));
				usuarioVO.setDataCadastro(LocalDateTime.parse(resultado.getString(7),
						DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))); // yyyy-MM-dd HH:mm:ss.S (depende a
																				// versao)
				if (resultado.getString(8) != null) {
					usuarioVO.setDataExpiracao(LocalDateTime.parse(resultado.getString(8),
							DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				}
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query no método realizarLoginDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally { // desalocando o banco de dados
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
			while (resultado.next()) {// pois retornará varios registros
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

	public boolean verificarExistenciaRegistroPorCpfDAO(UsuarioVO usuarioVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;
		String query = "SELECT cpf FROM usuario WHERE cpf = '" + usuarioVO.getCpf() + "'";
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
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

	public UsuarioVO cadastrarUsuarioDAO(UsuarioVO usuarioVO) { // o que vai voltar já tem a PK cadastrada
		String query = "INSERT INTO usuario (idtipousuario, nome, cpf, email, telefone, "
				+ "datacadastro, login, senha) VALUES (?, ?, ? ,? ,? ,? ,? ,?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			pstmt.setInt(1, usuarioVO.getTipoUsuarioVO().getValor()); // mascara "?" será trocada pelo Valor int em
																		// tempo de execucao
			pstmt.setString(2, usuarioVO.getNome());
			pstmt.setString(3, usuarioVO.getCpf());
			pstmt.setString(4, usuarioVO.getEmail());
			pstmt.setString(5, usuarioVO.getTelefone());
			pstmt.setObject(6, usuarioVO.getDataCadastro());
			pstmt.setString(7, usuarioVO.getLogin());
			pstmt.setString(8, usuarioVO.getSenha());

			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys(); // devolucao da chave primaria
			if (resultado.next()) {
				usuarioVO.setIdUsuario(resultado.getInt(1));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a quarto do médoto cadastrarUsuarioDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return usuarioVO;
	}

	public boolean verificarExistenciaRegistroPorIdUsuarioDAO(int idUsuario) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;
		String query = "SELECT idusuario FROM usuario WHERE idusuario = " + idUsuario;
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
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

	public boolean verificarDesligamentoUsuarioPorIdUsuarioDAO(int idUsuario) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;
		String query = "SELECT dataexpiracao FROM usuario WHERE idusuario = " + idUsuario;
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				String dataExpiracao = resultado.getString(1);
				if (dataExpiracao != null) {
					retorno = true;
				}
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método verificarDesligamentoUsuarioPorIdUsuarioDAO");
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
		String query = "UPDATE usuario SET dataexpiracao = '" + usuarioVO.getDataExpiracao() + "' WHERE idusuario = "
				+ usuarioVO.getIdUsuario();
		try {
			if (stmt.executeUpdate(query) == 1) {// verifica o workbench retorne uma linha afetada
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
		String query = "UPDATE usuario SET idtipousuario = " + usuarioVO.getTipoUsuarioVO().getValor() + ", nome = '"
				+ usuarioVO.getNome() + "', cpf = '" + usuarioVO.getCpf() + "', email = '" + usuarioVO.getEmail()
				+ "', telefone = '" + usuarioVO.getTelefone() + "', login = '" + usuarioVO.getLogin() + "', senha = '"
				+ usuarioVO.getSenha() + "' WHERE idusuario = " + usuarioVO.getIdUsuario();

		try {
			if (stmt.executeUpdate(query) == 1) {// verifica o workbench retorne uma linha afetada
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
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		ArrayList<UsuarioVO> listaUsuariosVO = new ArrayList<UsuarioVO>();
		String query = "SELECT u.idUsuario, tipo.descricao, u.nome, u.cpf, u.email, u.telefone, u.dataCadastro, u.dataExpiracao, u.login, u.senha "
				+ "FROM usuario u, tipoUsuario tipo "
				+ "WHERE u.idTipoUsuario = tipo.idTipoUsuario";
	try {
		resultado = stmt.executeQuery(query);
		while(resultado.next()) {
			UsuarioVO usuarioVO = new UsuarioVO();
			usuarioVO.setIdUsuario(Integer.parseInt(resultado.getString(1)));
			usuarioVO.setTipoUsuarioVO(TipoUsuarioVO.valueOf(resultado.getString(2)));
			usuarioVO.setNome(resultado.getString(3));
			usuarioVO.setCpf(resultado.getString(4));
			usuarioVO.setEmail(resultado.getString(5));
			usuarioVO.setTelefone(resultado.getString(6));
			usuarioVO.setDataCadastro(LocalDateTime.parse(resultado.getString(7), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			if(resultado.getString(8)!=null) {
				usuarioVO.setDataExpiracao(LocalDateTime.parse(resultado.getString(8), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			}
			usuarioVO.setLogin(resultado.getString(9));
			usuarioVO.setSenha(resultado.getString(10));
			listaUsuariosVO.add(usuarioVO);
		}
	} catch (SQLException e) {
		System.out.println("Erro ao executar a query do método consultarTodosUsuariosDAO!");
		System.out.println("Erro: "+e.getMessage());
	} finally {
		Banco.closeResultSet(resultado);
		Banco.closeStatement(stmt);
		Banco.closeConnection(conn);
	}
	return listaUsuariosVO;
	}

	public UsuarioVO consultarUsuarioDAO(UsuarioVO usuarioVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		UsuarioVO usuario = new UsuarioVO();
		String query = "SELECT u.idUsuario, tipo.descricao, u.nome, u.cpf, u.email, u.telefone, u.dataCastro, "
				+"u.dataExpiracao, u.login, u.senha "
				+"WHERE u.idTipoUsuario = tipo.idTipoUsuario"
				+"AND u.idUsuario = "+usuarioVO.getIdUsuario();
	try {
		resultado = stmt.executeQuery(query);
		if(resultado.next()) {
			usuario.setIdUsuario(Integer.parseInt(resultado.getString(1)));
			usuario.setTipoUsuarioVO(TipoUsuarioVO.valueOf(resultado.getString(2)));
			usuario.setNome(resultado.getString(3));
			usuario.setCpf(resultado.getString(4));
			usuario.setEmail(resultado.getString(5));
			usuario.setTelefone(resultado.getString(6));
			usuario.setDataCadastro(LocalDateTime.parse(resultado.getString(7), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			if(resultado.getString(8)!=null) {
				usuario.setDataExpiracao(LocalDateTime.parse(resultado.getString(8), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			}
			usuarioVO.setLogin(resultado.getString(9));
			usuarioVO.setSenha(resultado.getString(10));
		}
	} catch (SQLException e) {
		System.out.println("Erro ao executar a query do método consultarTodosUsuariosDAO!");
		System.out.println("Erro: "+e.getMessage());
	} finally {
		Banco.closeResultSet(resultado);
		Banco.closeStatement(stmt);
		Banco.closeConnection(conn);
	}
	return usuario;
	}

	public ArrayList<UsuarioVO> consultarListaEntregadores() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		ArrayList<UsuarioVO> listaUsuarioVO = new ArrayList<UsuarioVO>();
		String query = "SELECT u.idUsuario, tipo.descricao, u.nome, u.cpf, u.email, u.telefone, u.dataCadastro, "
				+"u.login, u.senha "
				+"FROM usuario u, tipoUsuario tipo "
				+"WHERE u.idTipoUsuario = tipo.idTipoUsuario "
				+"AND u.dataExpiracao is NULL "
				+"AND tipo.descricao like '"+TipoUsuarioVO.ENTREGADOR.toString()+"'";
		try {
			resultado = stmt.executeQuery(query);
			while (resultado.next()) {// pois retornará varios registros
				UsuarioVO usuarioVO = new UsuarioVO();
				usuarioVO.setIdUsuario(Integer.parseInt(resultado.getString(1)));
				usuarioVO.setTipoUsuarioVO(TipoUsuarioVO.valueOf(resultado.getString(2)));
				usuarioVO.setNome(resultado.getString(3));
				usuarioVO.setCpf(resultado.getString(4));
				usuarioVO.setEmail(resultado.getString(5));
				usuarioVO.setTelefone(resultado.getString(6));
				usuarioVO.setDataCadastro(LocalDateTime.parse(resultado.getString(7), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				usuarioVO.setLogin(resultado.getString(8)); //9
				usuarioVO.setSenha(resultado.getString(9)); //10


			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método consultarTipoUsuariosDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}

		return listaUsuarioVO;
	}
}
