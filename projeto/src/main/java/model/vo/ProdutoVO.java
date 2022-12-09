package model.vo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProdutoVO {
	public int idProduto;
	public TipoProdutoVO tipoProdutoVO;
	public String nome; // varchar(255), e Unique??
	public double preco; // numeric(10,2) ?
	public LocalDateTime dataCadastro; // datetime
	public LocalDateTime dataExclusao; // datetime

	public ProdutoVO(int idProduto, TipoProdutoVO tipoProdutoVO, String nome, double preco, LocalDateTime dataCadastro,
			LocalDateTime dataExclusao) {
		super();
		this.idProduto = idProduto;
		this.tipoProdutoVO = tipoProdutoVO;
		this.nome = nome;
		this.preco = preco;
		this.dataCadastro = dataCadastro;
		this.dataExclusao = dataExclusao;
	}

	public ProdutoVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public TipoProdutoVO getTipoProdutoVO() {
		return tipoProdutoVO;
	}

	public void setTipoProdutoVO(TipoProdutoVO tipoProdutoVO) {
		this.tipoProdutoVO = tipoProdutoVO;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public LocalDateTime getDataExclusao() {
		return dataExclusao;
	}

	public void setDataExclusao(LocalDateTime dataExclusao) {
		this.dataExclusao = dataExclusao;
	}

	public void imprimir() {
		System.out.printf("\n%3d  %-13s  %-20s  %-7s  %-24s  %-24s", this.getIdProduto(), this.getTipoProdutoVO(),
				this.getNome(), this.getPreco(), this.validarData(this.getDataCadastro()),
				this.validarData(this.getDataExclusao()));

	}

	private String validarData(LocalDateTime data) {
		String resultado = "";
		if(data != null) {
			resultado = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
		}
		return resultado;
	}

}
