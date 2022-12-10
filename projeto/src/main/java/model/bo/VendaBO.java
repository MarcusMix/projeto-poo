package model.bo;

import model.dao.EntregaDAO;
import model.dao.ProdutoDAO;
import model.dao.UsuarioDAO;
import model.dao.VendaDAO;
import model.vo.ItemVendaVO;
import model.vo.VendaVO;

public class VendaBO {

	public VendaVO cadastrarVendaBO(VendaVO vendaVO) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		if (usuarioDAO.verificarExistenciaRegistroPorIdUsuarioDAO(vendaVO.getIdUsuario())) {
			ProdutoDAO produtoDAO = new ProdutoDAO();
			boolean listaItemsEValida = true;
			for (ItemVendaVO itemVendaVO : vendaVO.getListaItemVendaVO()) {
				if (!produtoDAO.verificarExistenciaRegistroPorIdProdutoDAO(itemVendaVO.getIdProduto())) {
					System.out
							.println("O produto de id " + itemVendaVO.getIdProduto() + " não existe na base de dados.");
					listaItemsEValida = false;
				}
			}
			if (listaItemsEValida) {
				VendaDAO vendaDAO = new VendaDAO();
				vendaVO = vendaDAO.cadastraVendaDAO(vendaVO);
				if (vendaVO.getIdUsuario() != 0) {
					boolean resultado = vendaDAO.cadastrarItemVendaDAO(vendaVO);
					if (!resultado) {
						System.out.println("\nNão foi possível incluir algum item do produto.");
					}
					if (vendaVO.isFlagEntrega()) {
						EntregaBO entregaBO = new EntregaBO();
						resultado = entregaBO.cadastrarEntregaBO(vendaVO.getIdVenda());
						if (!resultado) {
							System.out.println("\nNão foi possível cadastrar a Entrega.");
						}
					}
				} else {
					System.out.println("\nNão foi possível cadastrar a Venda.");
				}
			}
		} else {
			System.out.println("O usuário desta venda não existe na base de dados.");
		}
		return vendaVO;
	}
	
	
	public boolean cancelarVendaBO(VendaVO vendaVO) {
		boolean resultado = false;
		VendaDAO vendaDAO = new VendaDAO();
		if(vendaDAO.verificarExistenciaRegistroPorIdVendaDAO(vendaVO.getIdVenda())) { // verificar se a venda existe
			if(vendaDAO.verificarCancelamentoPorIdVendaDAO(vendaVO.getIdVenda())) {
				System.out.println("\nProduto já se encontra deletado da base de dados!");
			}else {
				resultado = vendaDAO.excluirVendaDAO(vendaVO);
			}
		}else {
			System.out.println("\nVenda não existe na base de dados!");
		}
		return resultado;
	}
//		VendaDAO vendaDAO = new VendaDAO();
//		boolean resultado = false;
//		if (vendaDAO.verificarCancelamentoPorIdVendaDAO(vendaVO.getIdVenda()) == true) {
//			resultado = vendaDAO.excluirVendaDAO(vendaVO);
//			} else {
//				System.out.println("\nVenda não existe na base de dados!");
//			}
//		return resultado;
	
	

	public boolean verificarVendaParaAtualizarSituacaoEntrega(VendaVO vendaVO) {
		VendaDAO vendaDAO = new VendaDAO();
		EntregaDAO entregaDAO = new EntregaDAO();
		boolean retorno = false;
		if (vendaDAO.verificarExistenciaRegistroPorIdVendaDAO(vendaVO.getIdVenda())) {
			if (vendaDAO.verificarVendaPossuiEntrega(vendaVO.getIdVenda())) {
				if (!vendaDAO.verificarCancelamentoPorIdVendaDAO(vendaVO.getIdVenda())) {
					if (entregaDAO.consultarEntregaPorIdVendaDAO(vendaVO.getIdVenda()).getDataEntrega() == null) {
						retorno = true;
					} else {
						System.out.println("\nVenda já teve a entrega realizada.");
					}
				} else {
					System.out.println("\nVenda já se encontra cancelada na base de dados.");
				}
			} else {
				System.out.println("\nVenda não possui entrega.");
			}
		} else {
			System.out.println("\nVenda não localizada na base de dados.");
		}
		return retorno;
	}

}
