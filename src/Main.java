import DAO.*;
import entity.Aluno;
import entity.Treino;
import usermanagement.UsuarioManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AlunoDAO alunoDAO = new AlunoDAO();
        PlanoDAO planoDAO = new PlanoDAO();

        while (true) {
            System.out.println("Menu:");
            System.out.println("1 - Aluno");
            System.out.println("2 - Instrutor");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            System.out.println();

            switch (opcao) {
                case 1:
                    menuAluno(alunoDAO);
                    break;
                case 2:
                    menuInstrutor(alunoDAO, planoDAO);
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
    public static void menuAluno(AlunoDAO alunoDAO) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Primeiramente informe seu CPF:");
        String cpf = scanner.nextLine();
        Aluno aluno = alunoDAO.buscarAlunoPorCPF(cpf);

        while (true) {

            System.out.println("Menu de Alunos:");
            System.out.println("1 - Iniciar treino");
            System.out.println("2 - Relatorio data");
            System.out.println("3 - Relatorio cargas");
            System.out.println("0 - Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    menuTreinoIniciado(aluno);
                    break;
                case 2:
                    UsuarioManager.relatorioDatas(aluno);
                    break;
                case 3:
                    UsuarioManager.relatorioCargas(aluno);
                    break;
                case 0:
                    return; // Retorna ao menu principal
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
    public static void menuTreinoIniciado(Aluno aluno){
        Scanner scanner = new Scanner(System.in);
        TreinoDataDAO treinoDataDAO = new TreinoDataDAO();
        AlunosExerciciosInfoDAO alunosExerciciosInfoDAO = new AlunosExerciciosInfoDAO();

        Treino treino = new Treino();
        System.out.print("Treinos: ");
        UsuarioManager.listarIdTreinos();
        System.out.print("Digite o ID do treino: ");
        int idTreino = scanner.nextInt();
        treino.setId(idTreino);

        int pk = treinoDataDAO.cadastrarTreino(treino,aluno);
        UsuarioManager.associarInfo(treino,aluno,pk);

        int opcao = 0;
        while (opcao != 4) {
            System.out.println();
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Consultar exercícios");
            System.out.println("2 - Concluir exercício");
            System.out.println("3 - Alterar carga");
            System.out.print("4 - Finalizar treino");

            opcao = scanner.nextInt();
            System.out.println(" ");

            switch (opcao) {
                case 1:
                    UsuarioManager.listarExerciciosPeso(treino);
                    break;
                case 2:
                    UsuarioManager.concluirExercicio(pk);
                    break;
                case 3:
                    UsuarioManager.alterarCarga(aluno,treino,pk);
                    break;
                case 4:
                    treinoDataDAO.finalizarTreino(pk);
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
    public static void menuInstrutor(AlunoDAO alunoDAO, PlanoDAO planoDAO) {
        Scanner scanner = new Scanner(System.in);
        ExercicioDAO exercicioDAO = new ExercicioDAO();
        TreinoDAO treinoDAO = new TreinoDAO();
        while (true) {
            System.out.println("Menu de Instrutor:");
            System.out.println("1 - Gerenciar Alunos");
            System.out.println("2 - Gerenciar Planos");
            System.out.println("3 - Gerenciar Treinos");
            System.out.println("4 - Gerenciar Exercicios");
            System.out.println("5 - Associar/Desassociar Plano Aluno");
            System.out.println("0 - Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    menuAlunos(alunoDAO);
                    break;
                case 2:
                    menuPlanos(planoDAO);
                    break;
                case 3:
                    menuTreinos(treinoDAO);
                case 4:
                    menuExercicios(exercicioDAO);
                    break;
                case 5:
                    menuAssociarDesassociarPlano(alunoDAO,planoDAO);
                case 0:
                    return; // Retorna ao menu principal
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
    public static void menuAlunos(AlunoDAO alunoDAO) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu de Alunos:");
            System.out.println("1 - Cadastrar Aluno");
            System.out.println("2 - Deletar Aluno");
            System.out.println("3 - Atualizar Aluno");
            System.out.println("4 - Listar Todos Alunos");
            System.out.println("0 - Voltar ao Menu de Instrutor");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    UsuarioManager.cadastrarUsuario(alunoDAO);
                    break;
                case 2:
                    UsuarioManager.deletarAluno(alunoDAO);
                    break;
                case 3:
                    UsuarioManager.atualizarAluno(alunoDAO);
                    break;
                case 4:
                    UsuarioManager.listarTodosUsuarios(alunoDAO);
                    break;
                case 0:
                    return; // Retorna ao menu de instrutor
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
    public static void menuAssociarDesassociarPlano(AlunoDAO alunoDAO, PlanoDAO planoDAO) {
        AlunoPlanoDAO alunoPlanoDAO = new AlunoPlanoDAO();
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 3) {
            System.out.println("Menu de Associação/Desassociação de Plano:");
            System.out.println("1 - Associar Plano a Aluno");
            System.out.println("2 - Desassociar Plano de Aluno");
            System.out.println("3 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    UsuarioManager.associarPlano(alunoPlanoDAO, planoDAO, alunoDAO);
                    break;
                case 2:
                    UsuarioManager.desassociarPlano(alunoPlanoDAO,alunoDAO);
                    break;
                case 3:
                    return; // Retorna ao menu anterior
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
    public static void menuExercicios(ExercicioDAO exercicioDAO){
        Scanner scanner = new Scanner(System.in);
        MusculoDAO musculoDAO = new MusculoDAO();

        while (true){
            System.out.println("Menu de Exercicios:");
            System.out.println("1 - Cadastrar Exercicio");
            System.out.println("2 - Deletar Exercicio");
            System.out.println("3 - Alterar Exercicio");
            System.out.println("4 - Listar Exercicio");
            System.out.println("0 - Voltar ao Menu de Instrutor");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    UsuarioManager.cadastrarExercicio(exercicioDAO,musculoDAO);
                    break;
                case 2:
                    UsuarioManager.deletarExercicio(exercicioDAO);
                    break;
                case 3:
                    UsuarioManager.alterarExercicio(exercicioDAO);
                case 4:
                    UsuarioManager.listarExercicios(exercicioDAO);
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
    public static void menuPlanos(PlanoDAO planoDAO) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu de Planos:");
            System.out.println("1 - Cadastrar Plano");
            System.out.println("2 - Deletar Plano");
            System.out.println("3 - Alterar Plano");
            System.out.println("4 - Listar Planos");
            System.out.println("0 - Voltar ao Menu de Instrutor");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    UsuarioManager.cadastrarPlano(planoDAO);
                    break;
                case 2:
                    UsuarioManager.deletarPlano(planoDAO);
                    break;
                case 3:
                    UsuarioManager.atualizarPlano(planoDAO);
                    break;
                case 4:
                    UsuarioManager.listarTodosPlanos(planoDAO);
                    break;
                case 0:
                    return; // Retorna ao menu de instrutor
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
    public static void menuTreinos(TreinoDAO treinoDAO){
        Scanner scanner = new Scanner(System.in);

        ExercicioDAO exercicioDAO = new ExercicioDAO();

        while (true) {
            System.out.println("Menu de Treinos:");
            System.out.println("1 - Cadastrar Treino");
            System.out.println("2 - Deletar Treino");
            System.out.println("3 - Alterar Treino");
            System.out.println("4 - Listar Treinos");
            System.out.println("0 - Voltar ao Menu de Instrutor");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    UsuarioManager.cadastrarTreino(treinoDAO, exercicioDAO);
                    break;
                case 2:
                    UsuarioManager.deletarTreino(treinoDAO);
                    break;
                case 3:
                    UsuarioManager.atualizarTreino(treinoDAO);
                    break;
                case 4:
                    UsuarioManager.listarIdTreinos();
                    break;
                case 0:
                    return; // Retorna ao menu de instrutor
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

    }
}
