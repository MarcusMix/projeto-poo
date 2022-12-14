package model.vo;

public enum SituacaoEntregaVO {

	PEDIDO_REALIZADO(1),
	PREPARANDO_PEDIDO(2),
	EM_ROTA_DE_ENTREGA(3),
	PEDIDO_ENTREGUE(4),
	PEDIDO_CANCELADO(8),
	ENTREGA_CANCELADA(9);

	private int valor;

	private SituacaoEntregaVO(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return valor;

	}

	public static SituacaoEntregaVO getSituacaoEntregaporValor(int valor) {
		SituacaoEntregaVO situacaoEntregaVO = null;
		for (SituacaoEntregaVO elemento : SituacaoEntregaVO.values()) {
			if (elemento.getValor() == valor) {
				situacaoEntregaVO = elemento;
			}
		}
		return situacaoEntregaVO;
	}
}