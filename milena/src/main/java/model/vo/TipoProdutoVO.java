package model.vo;

public enum TipoProdutoVO {

	COMIDA (1),
	BEBIDA (2),
	SOBREMESA (3);

	private int valor;

	private TipoProdutoVO(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public static TipoProdutoVO getTipoProdutoVOPorValor(int valor) { //busca atraves do numero na lista equivalente
		TipoProdutoVO tipoProdutoVO = null;
		for(TipoProdutoVO elemento : TipoProdutoVO.values()) {
			// 1a vez elemento é Comida
			if(elemento.getValor() == valor) {
				tipoProdutoVO = elemento;
			}
		}
		return tipoProdutoVO;
	}
}
