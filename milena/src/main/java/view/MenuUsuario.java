package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import controler.UsuarioController;
import model.vo.TipoUsuarioVO;
import model.vo.UsuarioVO;

public class MenuUsuario {
	
	
	private static final int OPCAO_MENU_CADASTRAR_USUARIO = 1;
	private static final int OPCAO_MENU_CONSULTAR_USUARIO = 2;
	private static final int OPCAO_MENU_ATUALIZAR_USUARIO = 3;
	private static final int OPCAO_MENU_EXCLUIR_USUARIO = 4;
	private static final int OPCAO_MENU_USUARIO_VOLTAR= 5;
	
	
	
	private static final int OPCAO_MENU_CONSULTAR_TODOS_USUARIO = 1;
	private static final int OPCAO_MENU_CONSULTAR_UM_USUARIO = 2;
	private static final int OPCAO_MENU_CONSULTAR_USUARIO_VOLTAR= 3;

	
	Scanner teclado = new Scanner(System.in);
	
	
	public void apresentarMenuUsuario() {
		int opcao = this.apresentarOpcoesMenu();
		
		while(opcao != OPCAO_MENU_USUARIO_VOLTAR) {
			switch(opcao) {
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
					System.out.println("\nOpção inválida");
				}
			
			}
			opcao = this.apresentarOpcoesMenu();
		}
		
	}
	

	void cadastrarNovoUsuario(UsuarioVO usuarioVO) {
		this.cadastrarUsuario(usuarioVO);
		
	}
	
	private void cadastrarUsuario(UsuarioVO usuarioVO) {
		if(usuarioVO.getTipoUsuarioVO() == null) {
			do {
				usuarioVO.setTipoUsuarioVO(TipoUsuarioVO.getTipoUsuarioVOPorValor(this.apresentarOpcoesTipoUsuarioVO()));
			} while(usuarioVO.getTipoUsuarioVO() == null);
		}
		System.out.print("\nDigite o nome: ");
		usuarioVO.setNome(teclado.nextLine());
		System.out.print("\nDigite o CPF: ");
		usuarioVO.setCpf(teclado.nextLine());
		System.out.print("\nDigite o e-mail: ");
		usuarioVO.setEmail(teclado.nextLine());
		System.out.print("\nDigite o telefone: ");
		usuarioVO.setTelefone(teclado.nextLine());
		usuarioVO.setDataCadrasto(LocalDateTime.now());
		System.out.print("\nDigite o login: ");
		usuarioVO.setLogin(teclado.nextLine());
		System.out.print("\nDigite a senha: ");
		usuarioVO.setSenha(teclado.nextLine());
		
		if(this.validarCamposCadastro(usuarioVO)) {
			UsuarioController usuarioController = new UsuarioController();
			usuarioVO = usuarioController.cadastrarUsuarioController(usuarioVO);
		} if(usuarioVO.getIdUsuario() != 0) {
			System.out.println("Usuário atualizado com sucesso!");
		} else {
			System.out.println("Não foi possível atualizar o usuário!");
		}
		
	}
	


	private boolean validarCamposCadastro(UsuarioVO usuarioVO) {
		boolean resultado = true;
		if(usuarioVO.getNome() == null || usuarioVO.getNome().isEmpty()) {
			System.out.println("O campo nome é obrigatório!");
			resultado = false;
		}
		if(usuarioVO.getCpf() == null || usuarioVO.getCpf().isEmpty()) {
			System.out.println("O campo CPF é obrigatório!");
			resultado = false;
		}
		if(usuarioVO.getEmail() == null || usuarioVO.getEmail().isEmpty()) {
			System.out.println("O campo email é obrigatório!");
			resultado = false;
		}
		if(usuarioVO.getTelefone() == null || usuarioVO.getTelefone().isEmpty()) {
			System.out.println("O campo telefone é obrigatório!");
			resultado = false;
		}
		if(usuarioVO.getLogin() == null || usuarioVO.getLogin().isEmpty()) {
			System.out.println("O campo login é obrigatório!");
			resultado = false;
		}
		if(usuarioVO.getSenha() == null || usuarioVO.getSenha().isEmpty()) {
			System.out.println("O campo senha é obrigatório!");
			resultado = false;
		}
		return resultado;
	}


	private int apresentarOpcoesTipoUsuarioVO() {
		UsuarioController usuarioController = new UsuarioController();
		ArrayList<TipoUsuarioVO> listaTipoUsuarioVO = usuarioController.consultarTiposUsuarios();
		System.out.println("\n --------------Tipos Usuário------------");
		System.out.println("Opções: ");
		for(int i = 0; i < listaTipoUsuarioVO.size(); i++) {
			System.out.println(listaTipoUsuarioVO.get(i).getValor() + " - " + listaTipoUsuarioVO.get(i));
		}
		System.out.print("\nDigite uma opção: ");
		return Integer.parseInt(teclado.nextLine());
	}


	private void consultarUsuario() {
		int opcao = this.apresentarOpcoesConsulta();
		UsuarioController usuarioController = new UsuarioController();
		while(opcao != OPCAO_MENU_CONSULTAR_USUARIO_VOLTAR) {
			switch(opcao) {
				case OPCAO_MENU_CONSULTAR_TODOS_USUARIO: {
					opcao = OPCAO_MENU_CONSULTAR_USUARIO_VOLTAR;
					ArrayList<UsuarioVO> listaUsuariosVO = usuarioController.consultarTodosUsuarioController();
					System.out.println("--------Resultado da Consulta--------");
					System.out.printf("\n%3s  %-13s  %-20s  %-11s  %-25s  %-13s  %-24s  %-24s  %-10s  %-10s  ",
							"ID", "TIPO USUARIO", "NOME", "CPF", "E-MAIL", "TELEFONE", "DATA CADASTRO", 
							"DATA EXPIRAÇÃO", "LOGIN", "SENHA");
					for(int i = 0; i < listaUsuariosVO.size(); i++) {
						listaUsuariosVO.get(i).imprimir();
					}
					System.out.println();
					break;
				}
				case OPCAO_MENU_CONSULTAR_UM_USUARIO: {
					opcao = OPCAO_MENU_CONSULTAR_USUARIO_VOLTAR;
					UsuarioVO usuarioVO = new UsuarioVO();
					System.out.print("\nInforme o código do usuário: ");
					usuarioVO.setIdUsuario(Integer.parseInt(teclado.nextLine()));
					if(usuarioVO.getIdUsuario() != 0) {
						UsuarioVO usuario = usuarioController.consultarUsuarioController(usuarioVO);
						System.out.println("--------Resultado da Consulta--------");
						System.out.printf("\n%3s  %-13s  %-20s  %-11s  %-25s  %-13s  %-24s  %-24s  %-10s  %-10s  ",
								"ID", "TIPO USUARIO", "NOME", "CPF", "E-MAIL", "TELEFONE", "DATA CADASTRO", 
								"DATA EXPIRAÇÃO", "LOGIN", "SENHA");
						
						usuario.imprimir();
						System.out.println();
					}else {
						System.out.println("O campo código do usuário é obrigatório!");
					}
					break;
				}
				default: {
					System.out.println("\nOpção não encontrada!");
					opcao = this.apresentarOpcoesConsulta();
				}
			}
		}
	}


	private int apresentarOpcoesConsulta() {
		System.out.println("\nInforme o tipo de consulta a ser realizada: ");
		System.out.println(OPCAO_MENU_CONSULTAR_TODOS_USUARIO + " - Consultar todos os usuários");
		System.out.println(OPCAO_MENU_CONSULTAR_UM_USUARIO + " - Consultar um usuário específico");
		System.out.println(OPCAO_MENU_CONSULTAR_USUARIO_VOLTAR +  " - Voltar");
		System.out.println("Digite uma opção: ");
		return Integer.parseInt(teclado.nextLine());
	}


	private void atualizarUsuario() {
		UsuarioVO usuarioVO = new UsuarioVO();
		System.out.print("\nInforma o código do usuário: ");
		usuarioVO.setIdUsuario(Integer.parseInt(teclado.nextLine()));
			do {
				usuarioVO.setTipoUsuarioVO(TipoUsuarioVO.getTipoUsuarioVOPorValor(this.apresentarOpcoesTipoUsuarioVO()));
			} while(usuarioVO.getTipoUsuarioVO() == null);
			
			System.out.print("\nDigite o nome: ");
			usuarioVO.setNome(teclado.nextLine());
			System.out.print("\nDigite o CPF: ");
			usuarioVO.setCpf(teclado.nextLine());
			System.out.print("\nDigite o e-mail: ");
			usuarioVO.setEmail(teclado.nextLine());
			System.out.print("\nDigite o telefone: ");
			usuarioVO.setTelefone(teclado.nextLine());
			usuarioVO.setDataCadrasto(LocalDateTime.now());
			System.out.print("\nDigite o login: ");
			usuarioVO.setLogin(teclado.nextLine());
			System.out.print("\nDigite a senha: ");
			usuarioVO.setSenha(teclado.nextLine());
			
			if(this.validarCamposCadastro(usuarioVO)) {
				UsuarioController usuarioController = new UsuarioController();
				boolean resultado = usuarioController.atualizarUsuarioController(usuarioVO);
				if(resultado) {
					System.out.println("Usuário cadastrado com sucesso!");
				} else {
					System.out.println("Não foi possível cadastrar usuário!");
			} 
			}
			
		}
		
	
		private void excluirUsuario() {
		UsuarioVO usuarioVO = new UsuarioVO();
		System.out.println("\nInforme o código do usuário: ");
		usuarioVO.setIdUsuario(Integer.parseInt(teclado.nextLine()));
		System.out.println("Digite a data de expiração no formato dd/MM/yy HH:mm:ss");
		usuarioVO.setDataExpiracao(LocalDateTime.parse(teclado.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss")));
		
		if(usuarioVO.getIdUsuario() == 0 || usuarioVO.getDataExpiracao() == null) {
			System.out.println("Os campos código do usuário e a data de expiração são obrigatórios!");
		} else {
			UsuarioController usuarioController = new  UsuarioController ();
			boolean resultado = usuarioController.excluirUsuarioController(usuarioVO);  
			if(resultado) {
				System.out.println("\nUsuário excluído com sucesso!");
			} else {
				System.out.println("\nNão foi possível excluir usuário.");
			}
		}
	}


	private int apresentarOpcoesMenu() {	
		
		System.out.println("----------------Sistema Foodtruck------------------");
		System.out.println("-------------Menu de Usuário----------------");
		System.out.println("\nOpções");
		System.out.println(OPCAO_MENU_CADASTRAR_USUARIO + " - Cadastrar usuário.");
		System.out.println(OPCAO_MENU_CONSULTAR_USUARIO + " - Consultar usuário.");
		System.out.println(OPCAO_MENU_ATUALIZAR_USUARIO + " - Atualizar usuário.");
		System.out.println(OPCAO_MENU_EXCLUIR_USUARIO + " - Excluir usuário.");
		System.out.println(OPCAO_MENU_USUARIO_VOLTAR + " - Voltar.");
		
		System.out.println("Digite uma opção: ");
		
		return Integer.parseInt(teclado.nextLine());
	}
}
