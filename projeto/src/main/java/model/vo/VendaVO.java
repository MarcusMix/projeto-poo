package model.vo;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class VendaVO {

	private int idVenda;
	private int idUsuario;
	private ArrayList<ItemVendaVO> listaItemVendaVO;
	private int numeroPedido;
	private LocalDateTime dataVenda;
	private LocalDateTime dataCancelamento;
	private boolean flagEntrega;
	private double taxaEntrega; 

	public VendaVO(int idVenda, int idUsuario, ArrayList<ItemVendaVO> listaItemVendaVO, int numeroPedido,
			LocalDateTime dataVenda, LocalDateTime dataCancelamento, boolean flagEntrega, double taxaEntrega) {
		super();
		this.idVenda = idVenda;
		this.idUsuario = idUsuario;
		this.listaItemVendaVO = listaItemVendaVO;
		this.numeroPedido = numeroPedido;
		this.dataVenda = dataVenda;
		this.dataCancelamento = dataCancelamento;
		this.flagEntrega = flagEntrega;
		this.taxaEntrega = taxaEntrega;
	}

	public VendaVO() {
		super();
	}

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public ArrayList<ItemVendaVO> getListaItemVendaVO() {
		return listaItemVendaVO;
	}

	public void setListaItemVendaVO(ArrayList<ItemVendaVO> listaItemVendaVO) {
		this.listaItemVendaVO = listaItemVendaVO;
	}

	public int getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(int numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public LocalDateTime getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(LocalDateTime dataVenda) {
		this.dataVenda = dataVenda;
	}

	public LocalDateTime getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(LocalDateTime dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public boolean isFlagEntrega() {
		return flagEntrega;
	}

	public void setFlagEntrega(boolean flagEntrega) {
		this.flagEntrega = flagEntrega;
	}

	public double getTaxaEntrega() {
		return taxaEntrega;
	}

	public void setTaxaEntrega(double taxaEntrega) {
		this.taxaEntrega = taxaEntrega;
	}

}
