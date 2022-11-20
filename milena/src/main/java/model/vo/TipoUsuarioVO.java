package model.vo;

public enum TipoUsuarioVO {
	
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
	
	public static TipoUsuarioVO getTipoUsuarioVOPorValor(int valor) {
		TipoUsuarioVO tipoUsuarioVO = null;
		for(TipoUsuarioVO elemento : TipoUsuarioVO.values()) {
			if(elemento.getValor() == valor) {
				tipoUsuarioVO = elemento;
			}
		}
		return tipoUsuarioVO;
	}
}
