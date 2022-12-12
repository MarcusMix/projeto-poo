package model.vo;

public class ItemVendaVO {
	private int idItemVenda;
	private int idProduto;
	private VendaVO vendaVO;
	private ProdutoVO produtoVO;
	private int quantidade; 
	public ItemVendaVO(int idItemVenda, int idProduto, VendaVO vendaVO, ProdutoVO produtoVO, int quantidade) {
		super();
		this.idItemVenda = idItemVenda;
		this.idProduto = idProduto;
		this.vendaVO = vendaVO;
		this.produtoVO = produtoVO;
		this.quantidade = quantidade;
	}
	public ItemVendaVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getIdItemVenda() {
		return idItemVenda;
	}
	public void setIdItemVenda(int idItemVenda) {
		this.idItemVenda = idItemVenda;
	}
	public int getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}
	public VendaVO getVendaVO() {
		return vendaVO;
	}
	public void setVendaVO(VendaVO vendaVO) {
		this.vendaVO = vendaVO;
	}
	public ProdutoVO getProdutoVO() {
		return produtoVO;
	}
	public void setProdutoVO(ProdutoVO produtoVO) {
		this.produtoVO = produtoVO;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

}
