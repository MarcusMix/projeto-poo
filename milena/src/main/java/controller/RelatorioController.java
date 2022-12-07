package controller;

import java.util.ArrayList;

import model.bo.RelatorioBO;
import model.dto.VendasCanceladaDTO;

public class RelatorioController {

	public ArrayList<VendasCanceladaDTO> gerarRelatorioVendasCanceladasController() {
		RelatorioBO relatorioBO = new RelatorioBO();
		return relatorioBO.gerarRelatorioVendasCanceladasBO();
	}

}
