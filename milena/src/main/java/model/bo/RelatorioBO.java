package model.bo;

import java.util.ArrayList;

import model.dao.RelatorioDAO;
import model.dto.VendasCanceladaDTO;

public class RelatorioBO {

	public ArrayList<VendasCanceladaDTO> gerarRelatorioVendasCanceladasBO() {
		RelatorioDAO relatorioDAO = new RelatorioDAO();
		return relatorioDAO.gerarRelatorioVendasCanceladasDAO();
	}

}
