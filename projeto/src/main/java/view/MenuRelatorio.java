package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.RelatorioController;
import model.dto.VendasCanceladaDTO;

public class MenuRelatorio {
	private static final int OPCAO_MENU_RELATORIO_VENDAS_CANCELADAS = 1;
	private static final int OPCAO_MENU_VOLTAR = 9;

	Scanner teclado = new Scanner(System.in);

	public void apresentarMenuRelatorio() {
		int opcao = this.apresentarOpcoesMenu();
		while (opcao != OPCAO_MENU_VOLTAR) {
			switch (opcao) {
				case OPCAO_MENU_RELATORIO_VENDAS_CANCELADAS: {
					this.gerarRelatorioVendasCanceladas();
					break;
				}
				default: {
					System.out.println("\nOpção Inválida");
				}
			}
			opcao = this.apresentarOpcoesMenu();
		}
	}

	private int apresentarOpcoesMenu() {
		System.out.println("\n--------- Sistema Foodtruck --------");
		System.out.println("--------- Menu de Relatório ------------");
		System.out.println("\nOpções: ");
		System.out.println(OPCAO_MENU_RELATORIO_VENDAS_CANCELADAS + " - Relatório de Vendas Canceladas");
		System.out.println(OPCAO_MENU_VOLTAR + " - Voltar");

		System.out.println("\nDigite uma opção: ");
		return Integer.parseInt(teclado.nextLine());
	}

	private void gerarRelatorioVendasCanceladas() {
		RelatorioController relatorioController = new RelatorioController();
		ArrayList<VendasCanceladaDTO> listaVendasCanceladaDTO = relatorioController
				.gerarRelatorioVendasCanceladasController();
		System.out.println("\n---------- RESULTADO DA CONSULTA ----------");
		System.out.printf("\n%-20s  %-24s  %12s  %12s  %12s  ", "NOME", "DATA CANCELAMENTO", "SUBTOTAL", "TAXA ENTREGA",
				"TOTAL");
		for (VendasCanceladaDTO element : listaVendasCanceladaDTO) {
			element.imprimir();
		}
	}

}
