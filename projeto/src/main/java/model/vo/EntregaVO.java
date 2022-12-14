package model.vo;

import java.time.LocalDateTime;

public class EntregaVO {
	private int idEntrega;
	private int idVenda;
	private int idEntregador;
	private SituacaoEntregaVO situacaoEntrega; 
	private LocalDateTime dataEntrega;
	public EntregaVO(int idEntrega, int idVenda, int idEntregador, SituacaoEntregaVO situacaoEntrega,
			LocalDateTime dataEntrega) {
		super();
		this.idEntrega = idEntrega;
		this.idVenda = idVenda;
		this.idEntregador = idEntregador;
		this.situacaoEntrega = situacaoEntrega;
		this.dataEntrega = dataEntrega;
	}
	public EntregaVO() {
		super();
	}
	public int getIdEntrega() {
		return idEntrega;
	}
	public void setIdEntrega(int idEntrega) {
		this.idEntrega = idEntrega;
	}
	public int getIdVenda() {
		return idVenda;
	}
	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}
	public int getIdEntregador() {
		return idEntregador;
	}
	public void setIdEntregador(int idEntregador) {
		this.idEntregador = idEntregador;
	}
	public SituacaoEntregaVO getSituacaoEntrega() {
		return situacaoEntrega;
	}
	public void setSituacaoEntrega(SituacaoEntregaVO situacaoEntrega) {
		this.situacaoEntrega = situacaoEntrega;
	}
	public LocalDateTime getDataEntrega() {
		return dataEntrega;
	}
	public void setDataEntrega(LocalDateTime dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

}
