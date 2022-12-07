package controller;

import model.bo.EntregaBO;
import model.vo.VendaVO;

public class EntregaController {

	public boolean atualizarSituacaoEntregaController(VendaVO vendaVO) {
		EntregaBO entregaBO = new EntregaBO();
		return entregaBO.atualizarSituacaoEntregaBO(vendaVO);
	}

}
