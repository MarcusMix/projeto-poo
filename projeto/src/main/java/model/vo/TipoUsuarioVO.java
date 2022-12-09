package model.vo;
//"constante" que posso usar em qualquer parte do projeto
public enum TipoUsuarioVO { //LISTA

	ADMINISTRADOR (1),
	CLIENTE (2),
	FUNCIONARIO (3),
	ENTREGADOR (4);

	private int valor;

	private TipoUsuarioVO(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}

	public static TipoUsuarioVO getTipoUsuarioVOPorValor(int valor) { //busca atraves do numero na lista equivalente
		TipoUsuarioVO tipoUsuarioVO = null;
		for(TipoUsuarioVO elemento : TipoUsuarioVO.values()) {
			// apos : é a minha lista que retorna lista de tipos de usuarios
			// 1a vez elemento é ADMINSTRADOR
			if(elemento.getValor() == valor) {
				tipoUsuarioVO = elemento;
			}
		}
		return tipoUsuarioVO;
	}
}
