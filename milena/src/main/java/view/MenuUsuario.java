package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import controller.UsuarioController;
import model.vo.TipoUsuarioVO;
import model.vo.UsuarioVO;

public class MenuUsuario {
	// atributos de classe
	private static final int OPCAO_MENU_CADASTRAR_USUARIO = 1;
	private static final int OPCAO_MENU_CONSULTAR_USUARIO = 2;
	private static final int OPCAO_MENU_ATUALIZAR_USUARIO = 3;
	private static final int OPCAO_MENU_EXCLUIR_USUARIO = 4;
	private static final int OPCAO_MENU_USUARIO_VOLTAR = 9;

	private static final int OPCAO_MENU_CONSULTAR_TODOS_USUARIOS = 1;
	private static final int OPCAO_MENU_CONSULTAR_UM_USUARIO = 2;
	private static final int OPCAO_MENU_CONSULTAR_USUARIO_VOLTAR = 9;

	Scanner teclado = new Scanner(System.in);

	public void apresentarMenuUsuario() {
		int opcao = this.apresentarOpcoesMenu();
		while (opcao != OPCAO_MENU_USUARIO_VOLTAR) {
			switch (opcao) {
			case OPCAO_MENU_CADASTRAR_USUARIO: {
				UsuarioVO usuarioVO = new UsuarioVO();
				this.cadastrarUsuario(usuarioVO);
				break;
			}
			case OPCAO_MENU_CONSULTAR_USUARIO: {
				this.consultarUsuario();
				break;
			}
			case OPCAO_MENU_ATUALIZAR_USUARIO: {
				this.atualizarUsuario();
				break;
			}
			case OPCAO_MENU_EXCLUIR_USUARIO: {
				this.excluirUsuario();
				break;
			}
			default: {
				System.out.println("\nOpção Inválida");
			}
			}
			opcao = this.apresentarOpcoesMenu();
		}
	}

	private void consultarUsuario() {
		int opcao = this.apresentarOpcoesConsulta();
		UsuarioController usuarioController = new UsuarioController();
		while (opcao != OPCAO_MENU_CONSULTAR_USUARIO_VOLTAR) {
			switch (opcao) {
			case OPCAO_MENU_CONSULTAR_TODOS_USUARIOS: {
				opcao = OPCAO_MENU_CONSULTAR_USUARIO_VOLTAR;
				ArrayList<UsuarioVO> listaUsuariosVO = usuarioController.consultarTodosUsuariosController();
				// Lista montada
				System.out.println("---------- RESULTADO DA CONSULTA ---------");
				System.out.printf("\n%3s  %-13s  %-20s  %-11s  %-25s  %-13s  %-24s  %-24s  %-10s  %-10s  ", "ID",
						"TIPO USUARIO", "NOME", "CPF", "E-MAIL", "TELEFONE", "DATA CADASTRO", "DATA EXPIRAÇÃO", "LOGIN",
						"SENHA");
				for (UsuarioVO element : listaUsuariosVO) {
					element.imprimir();
				}
				System.out.println();
				break;
			}

			case OPCAO_MENU_CONSULTAR_UM_USUARIO: {
				opcao = OPCAO_MENU_CONSULTAR_USUARIO_VOLTAR;
				UsuarioVO usuarioVO = new UsuarioVO(); // instanciado objeto
				System.out.print("\nInforme o código do usuário: ");
				usuarioVO.setIdUsuario(Integer.parseInt(teclado.nextLine()));
				if (usuarioVO.getIdUsuario() != 0) {
					UsuarioVO usuario = usuarioController.consultarUsuarioController(usuarioVO);
					System.out.println("---------- RESULTADO DA CONSULTA ---------");
					System.out.printf("\n%3s  %-13s  %-20s  %-11s  %-25s  %-13s  %-24s  %-24s  %-10s  %-10s  ", "ID",
							"TIPO USUARIO", "NOME", "CPF", "E-MAIL", "TELEFONE", "DATA CADASTRO", "DATA EXPIRAÇÃO",
							"LOGIN", "SENHA");

					usuario.imprimir();

					System.out.println();
				} else {
					System.out.println("O campo código do usuário é obrigatório!");
				}
				break;
			}
			default: {
				System.out.println("\nOpção Inválida.");
				opcao = this.apresentarOpcoesConsulta();
			}

			}
		}

	}

	private int apresentarOpcoesConsulta() {
		System.out.println("\nInforme o tipo de consulta a ser realizada: ");
		System.out.println(OPCAO_MENU_CONSULTAR_TODOS_USUARIOS + " - Consultar todos os usuários");
		System.out.println(OPCAO_MENU_CONSULTAR_UM_USUARIO + " - Consultar um usuário específico");
		System.out.println(OPCAO_MENU_CONSULTAR_USUARIO_VOLTAR + " - Voltar");
		System.out.print("\nDigite uma opção: ");
		return Integer.parseInt(teclado.nextLine());
	}

	private int apresentarOpcoesMenu() {
		System.out.println("\n--------- Sistema Foodtruck --------");
		System.out.println("--------- Menu de Usuário ------------");
		System.out.println("\nOpções: ");
		System.out.println(OPCAO_MENU_CADASTRAR_USUARIO + " - Cadastrar Usuário");

		System.out.println(OPCAO_MENU_CONSULTAR_USUARIO + " - Consultar Usuário");

		System.out.println(OPCAO_MENU_ATUALIZAR_USUARIO + " - Atualizar Usuário");

		System.out.println(OPCAO_MENU_EXCLUIR_USUARIO + " - Excluir Usuário");

		System.out.println(OPCAO_MENU_USUARIO_VOLTAR + " - Voltar");

		System.out.println("\nDigite uma opção: ");
		return Integer.parseInt(teclado.nextLine());
	}

	public void cadastrarNovoUsuario(UsuarioVO usuarioVO) { // um setado o tipo do cliente, sendo um usuario externo
		this.cadastrarUsuario(usuarioVO);

	}

	private void cadastrarUsuario(UsuarioVO usuarioVO) { // este metodo só poder ser chamado pelo adm
		if (usuarioVO.getTipoUsuarioVO() == null) {// verificando se quem tá chamando é o adm
			do {
				usuarioVO
						.setTipoUsuarioVO(TipoUsuarioVO.getTipoUsuarioVOPorValor(this.apresentarOpcoesTipoUsuarioVO()));
			} while (usuarioVO.getTipoUsuarioVO() == null);
		}
		System.out.println("\nDigite o Nome: ");
		usuarioVO.setNome(teclado.nextLine());
		System.out.println("\nDigite o CPF: ");
		usuarioVO.setCpf(teclado.nextLine());
		System.out.println("\nDigite o e-mail: ");
		usuarioVO.setEmail(teclado.nextLine());
		System.out.println("\nDigite o telefone: ");
		usuarioVO.setTelefone(teclado.nextLine());

		usuarioVO.setDataCadastro(LocalDateTime.now());

		System.out.println("\nDigite o login: ");
		usuarioVO.setLogin(teclado.nextLine());
		System.out.println("\nDigite a senha: ");
		usuarioVO.setSenha(teclado.nextLine());

		if (this.validarCamposCadastro(usuarioVO)) {
			UsuarioController usuarioController = new UsuarioController();
			usuarioVO = usuarioController.cadastrarUsuarioController(usuarioVO);
			if (usuarioVO.getIdUsuario() != 0) { // se ele tiver uma chave primaria
				System.out.println("Usuário foi cadastrado com sucesso!");
			} else {
				System.out.println("Não foi possivel cadastrar o usuário!");
			}

		}
	}

	private boolean validarCamposCadastro(UsuarioVO usuarioVO) {
		boolean resultado = true;
		if (usuarioVO.getNome() == null || usuarioVO.getNome().isEmpty()) { // get.Nome é String que é Referencia
			System.out.println("O campo nome é obrigatório.");
			resultado = false;
		}
		if (usuarioVO.getCpf() == null || usuarioVO.getCpf().isEmpty()) {
			System.out.println("O campo CPF é obrigatório.");
			resultado = false;
		}
		if (usuarioVO.getEmail() == null || usuarioVO.getEmail().isEmpty()) {
			System.out.println("O campo e-mail é obrigatório.");
			resultado = false;
		}
		if (usuarioVO.getTelefone() == null || usuarioVO.getTelefone().isEmpty()) {
			System.out.println("O campo telefone é obrigatório.");
			resultado = false;
		}

		if (usuarioVO.getLogin() == null || usuarioVO.getLogin().isEmpty()) {
			System.out.println("O campo login é obrigatório.");
			resultado = false;
		}
		if (usuarioVO.getSenha() == null || usuarioVO.getSenha().isEmpty()) {
			System.out.println("O campo senha é obrigatório.");
			resultado = false;
		}

		return resultado;
	}

	private int apresentarOpcoesTipoUsuarioVO() {
		UsuarioController usuarioController = new UsuarioController();
		ArrayList<TipoUsuarioVO> listaTipoUsuarioVO = usuarioController.consultarTipoUsuarios();
		System.out.println("\n--------- Tipos de Usuários ---------");
		System.out.println("Opções: ");
		for (TipoUsuarioVO element : listaTipoUsuarioVO) {
			System.out.println(element.getValor() + " - " + element);
			// get i pega o numero que esta no ENUM (1 2 3 4 as classes de pessoas dos
			// sistema)
		}

		System.out.print("\nDigite uma opção: ");
		return Integer.parseInt(teclado.nextLine());
	}

	private void atualizarUsuario() {
		UsuarioVO usuarioVO = new UsuarioVO();
		System.out.print("\nInforme o código do usuário: ");
		usuarioVO.setIdUsuario(Integer.parseInt(teclado.nextLine()));
		do {
			usuarioVO.setTipoUsuarioVO(TipoUsuarioVO.getTipoUsuarioVOPorValor(this.apresentarOpcoesTipoUsuarioVO()));
		} while (usuarioVO.getTipoUsuarioVO() == null);

		System.out.println("\nDigite o Nome: ");
		usuarioVO.setNome(teclado.nextLine());
		System.out.println("\nDigite o CPF: ");
		usuarioVO.setCpf(teclado.nextLine());
		System.out.println("\nDigite o e-mail: ");
		usuarioVO.setEmail(teclado.nextLine());
		System.out.println("\nDigite o telefone: ");
		usuarioVO.setTelefone(teclado.nextLine());

		System.out.println("\nDigite o login: ");
		usuarioVO.setLogin(teclado.nextLine());
		System.out.println("\nDigite a senha: ");
		usuarioVO.setSenha(teclado.nextLine());

		if (this.validarCamposCadastro(usuarioVO)) {
			UsuarioController usuarioController = new UsuarioController();
			boolean resultado = usuarioController.atualizarUsuarioController(usuarioVO);
			if (resultado) { // se ele tiver uma chave primaria
				System.out.println("Usuário atualizado com sucesso!");
			} else {
				System.out.println("Não foi possivel atualizar o usuário!");
			}
		}
	}

	private void excluirUsuario() {
		UsuarioVO usuarioVO = new UsuarioVO();
		System.out.print("\nInforme o código do usuário: ");
		usuarioVO.setIdUsuario(Integer.parseInt(teclado.nextLine()));
		System.out.print("\nDigite a Data de expiração no formado dd/MM/yyyy HH:mm:ss");
		usuarioVO.setDataExpiracao(
				LocalDateTime.parse(teclado.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

		if (usuarioVO.getIdUsuario() == 0 || usuarioVO.getDataExpiracao() == null) { // nao preencheu as informaçoes
			System.out.println("Os campos código do usuario e data de expiração são obrigatórios.");
		} else {
			UsuarioController usuarioController = new UsuarioController();
			boolean resultado = UsuarioController.excluirUsuarioController(usuarioVO);
			if (resultado) {
				System.out.println("\nUsuário excluído com sucesso!");
			} else {
				System.out.println("\nNão foi possível excluir o usuário.");
			}
		}
	}

}
