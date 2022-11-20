package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

}
