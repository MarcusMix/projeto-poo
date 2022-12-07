package controller;

import model.bo.VendaBO;
import model.vo.VendaVO;

public class VendaController {

	public VendaVO cadastrarVendaController(VendaVO vendaVO) {
		VendaBO vendaBO = new VendaBO();
		return vendaBO.cadastrarVendaBO(vendaVO);
	}
	public boolean cancelarVendaController(VendaVO vendaVO) {
		VendaBO vendaBO = new VendaBO();
		return vendaBO.cancelarVendaBO(vendaVO);
	}

}
