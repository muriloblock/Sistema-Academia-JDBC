package usermanagement;

import DAO.*;
import entity.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class UsuarioManager {
    public static class Exercicios{
        public static void alterarExercicio(ExercicioDAO exercicioDAO) {
            MusculoDAO musculoDAO = new MusculoDAO();
            Scanner scanner = new Scanner(System.in);
            MusculoExercicioDAO musculoExercicioDAO = new MusculoExercicioDAO();

            System.out.print("Digite o índice do exercício que deseja alterar: ");
            exercicioDAO.listarExercicios(); // Função para listar os exercícios disponíveis
            int indiceExercicio = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            Exercicio exercicio = exercicioDAO.buscarExercicioPorIndice(indiceExercicio);
            if (exercicio != null) {
                boolean sair = false;
                while (!sair) {
                    System.out.println("Escolha uma opção:");
                    System.out.println("1. Alterar nome do exercício");
                    System.out.println("2. Desassociar músculo");
                    System.out.println("3. Associar musculo");
                    System.out.println("4. Voltar");

                    int opcao = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer

                    switch (opcao) {
                        case 1:
                            System.out.print("Digite o novo nome do exercício: ");
                            String novoNome = scanner.nextLine();
                            exercicio.setNome(novoNome);
                            exercicioDAO.atualizarNomeExercicio(exercicio);
                            System.out.println("Nome do exercício alterado com sucesso.");
                            break;
                        case 2:
                            System.out.println("Músculos associados ao exercício:");
                            exercicioDAO.listarMusculosAssociados(exercicio);
                            System.out.print("Digite o nome do músculo a ser desassociado: ");
                            String nomeMusculo = scanner.nextLine();
                            int id = musculoDAO.buscaIdPorNome(nomeMusculo);
                            Musculo musculo =  musculoDAO.buscarMusculoPorIndice(id);

                            if (musculo != null) {
                                musculoExercicioDAO.desassociarMusculoExercicio(musculo, exercicio); // Desassocia o músculo do exercício
                                System.out.println("Músculo desassociado do exercício com sucesso.");
                            } else {
                                System.out.println("Músculo não encontrado.");
                            }
                            break;

                        case 4:
                            sair = true;
                            break;
                        default:
                            System.out.println("Opção inválida. Digite novamente.");
                            break;
                    }
                }
            } else {
                System.out.println("Exercício não encontrado.");
            }
        }
        public static void listarExercicios(ExercicioDAO exercicioDAO) {
            List<Exercicio> exercicios = exercicioDAO.buscarTodosExercicios();

            for (Exercicio exercicio : exercicios) {
                System.out.println("Exercício: " + exercicio.getNome());
                exercicioDAO.listarMusculosAssociados(exercicio);
                System.out.println();
            }
        }
        public static void deletarExercicio(ExercicioDAO exercicioDAO){
            Scanner scanner = new Scanner(System.in);

            System.out.print("Digite o Index do exercício qual deseja deletar:\n ");
            exercicioDAO.listarExercicios();
            int idExercicio = Integer.parseInt(scanner.nextLine());
            exercicioDAO.deletarExercicio(idExercicio);
        }
        public static void listarIdExercicios(ExercicioDAO exercicioDAO){
            List<Exercicio> exercicios = exercicioDAO.buscarTodosExercicios();

            System.out.println();
            for (Exercicio exercicio : exercicios) {
                System.out.println("ID = "+ exercicio.getId() + " Exercício: " + exercicio.getNome());
            }
        }
        public static void cadastrarExercicio(ExercicioDAO exercicioDAO, MusculoDAO musculoDAO) {
            Scanner scanner = new Scanner(System.in);
            MusculoExercicioDAO musculoExercicioDAO = new MusculoExercicioDAO();

            System.out.print("Digite o nome do exercício: ");
            String nomeExercicio = scanner.nextLine();

            System.out.print("Digite quantas series BASE tera o exercicio: ");
            int seriesBase = Integer.parseInt(scanner.nextLine());

            System.out.print("Digite quantas repeticoes BASE tera o exercicio: ");
            int repsBase = Integer.parseInt(scanner.nextLine());

            System.out.print("Digite o peso BASE para cada repeticao: ");
            int pesoBase = Integer.parseInt(scanner.nextLine());

            // Criar o exercício
            Exercicio exercicio = new Exercicio();
            exercicio.setNome(nomeExercicio);
            exercicio.setPeso(pesoBase);
            exercicio.setRepeticoes(repsBase);
            exercicio.setSeries(seriesBase);
            exercicioDAO.cadastrarExercicio(exercicio);
            exercicio.setId(exercicioDAO.buscaIdPorNome(nomeExercicio));

            System.out.print("Quantos músculos deseja associar a este exercício? ");
            int numMusculos = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            for (int i = 0; i < numMusculos; i++) {
                System.out.println("Digite o índice referente ao músculo:");
                musculoDAO.listarMusculos();
                System.out.print("Ou digite 'n' para adicionar um novo músculo: ");

                String input = scanner.nextLine();
                if (input.equals("n")) {
                    System.out.print("Digite o nome do novo músculo: ");
                    String nomeMusculo = scanner.nextLine();

                    // Criar o músculo
                    Musculo musculo = new Musculo();
                    musculo.setNome(nomeMusculo);
                    musculoDAO.cadastrarMusculo(musculo);
                    musculo.setId(musculoDAO.buscaIdPorNome(nomeMusculo));

                    // Associar músculo ao exercício na tabela musculo_exercicio
                    musculoExercicioDAO.associarMusculoExercicio(musculo, exercicio);
                } else {
                    int indiceMusculo = Integer.parseInt(input);
                    Musculo musculo = musculoDAO.buscarMusculoPorIndice(indiceMusculo);
                    if (musculo != null) {
                        // Associar músculo existente ao exercício na tabela musculo_exercicio
                        musculoExercicioDAO.associarMusculoExercicio(musculo, exercicio);
                    } else {
                        System.out.println("Índice de músculo inválido.");
                    }
                }
            }
        }
    }
    public static class Planos{
        public static void deletarPlano(PlanoDAO planoDAO){
            Plano plano = new Plano();
            listarTodosPlanos(planoDAO);
            plano =  planoDAO.buscarPlanoPorId();
            planoDAO.deletarPlano(plano.getId());
        }
        public static void cadastrarPlano(PlanoDAO planoDAO){
            Scanner scanner = new Scanner(System.in);

            System.out.print("Digite o nome do plano: ");
            String nome = scanner.nextLine();
            System.out.print("Digite o valor do plano: ");
            float valor_mensal = Float.parseFloat(scanner.nextLine());

            Plano plano = new Plano();
            plano.setNome(nome);
            plano.setValorMensal(valor_mensal);

            planoDAO.cadastrarPlano(plano);
        }

        public static void listarTodosPlanos(PlanoDAO planoDAO) {
            List<Plano> todosPlanos = planoDAO.buscarTodosPlanos();
            if (todosPlanos.isEmpty()) {
                System.out.println("Não há alunos cadastrados.");
            } else {
                System.out.println("Lista de Todos os Alunos:");
                for (Plano plano : todosPlanos) {
                    System.out.println("ID: " + plano.getId());
                    System.out.println("Nome: " + plano.getNome());
                    System.out.println("Valor Mensal: " + plano.getValorMensal());
                    System.out.println(); // Pular uma linha entre cada aluno
                }
            }
        }
        public static void atualizarPlano(PlanoDAO planoDAO){
            Plano plano = new Plano();
            listarTodosPlanos(planoDAO);
            plano = planoDAO.buscarPlanoPorId();
            Scanner scanner = new Scanner(System.in);

            System.out.println("Selecione o que deseja atualizar:");
            System.out.println("1. Nome");
            System.out.println("2. Valor Mensal");
            System.out.print("Escolha a opção desejada: ");

            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print("Digite o novo Nome: ");
                    String novoNome = scanner.nextLine();
                    plano.setNome(novoNome);
                    break;
                case 2:
                    System.out.print("Digite o novo Valor Mensal: ");
                    Float valorMensal = Float.valueOf(scanner.nextLine());
                    plano.setValorMensal(valorMensal);
                    break;
                default:
                    System.out.println("Opção inválida.");
                    return;
            }

            planoDAO.atualizarPlano(plano);
            System.out.println("Aluno atualizado com sucesso.");
        }
        public static void associarPlano(AlunoPlanoDAO alunoPlanoDAO, PlanoDAO planoDAO, AlunoDAO alunoDAO){
            Scanner scanner = new Scanner(System.in);

            Instrutor.listarTodosUsuarios(alunoDAO);
            Aluno aluno = alunoDAO.buscarAlunoPorId();

            listarTodosPlanos(planoDAO);
            Plano plano = planoDAO.buscarPlanoPorId();

            System.out.print("Digite a data do cadastro (no formato yyyy-MM-dd): ");
            String dataCadastroStr = scanner.next();
            Date dataCadastro = Date.valueOf(dataCadastroStr);

            System.out.print("Digite os dados do cartão: ");
            String dadosCartao = scanner.next();

            alunoPlanoDAO.associarAlunoPlano(aluno,plano,dataCadastro,dadosCartao);
        }
        public static void desassociarPlano(AlunoPlanoDAO alunoPlanoDAO, AlunoDAO alunoDAO){
            Scanner scanner = new Scanner(System.in);

            Instrutor.listarTodosUsuarios(alunoDAO);
            Aluno aluno = alunoDAO.buscarAlunoPorId();

            alunoPlanoDAO.desassociarAlunoPlano(aluno);
        }
    }
    public static class Treinos{
        public static void listarExerciciosPeso(Treino treino){
            ExercicioTreinoDAO exercicioTreinoDAO = new ExercicioTreinoDAO();

            List<Exercicio> exercicios = exercicioTreinoDAO.buscarExerciciosPorTreino(treino.getId());

            System.out.println("Exercicios: ");
            for (Exercicio exercicio : exercicios) {
                System.out.println("Nome: "+exercicio.getNome());
                System.out.println("\tSeries: "+exercicio.getSeries());
                System.out.println("\tRepeticoes: "+exercicio.getRepeticoes());
                System.out.println("\tPeso: "+exercicio.getPeso()+"Kg");
                System.out.println("\tTempo de descanso: "+exercicio.getTempo_descanso());
                System.out.print("\tConcluido: ");
                if(exercicio.getConcluido() == 1){
                    System.out.println("Sim");
                } else {
                    System.out.println("Nao");
                }
                System.out.println(" ");
            }
        }
        public static void concluirExercicio(int id_data) {
            AlunosExerciciosInfoDAO alunosExerciciosInfoDAO = new AlunosExerciciosInfoDAO();
            Scanner scanner = new Scanner(System.in);

            List<Exercicio> exercicios = alunosExerciciosInfoDAO.buscarTodosExercicios(id_data);

            System.out.println("Exercicios : ");
            for (Exercicio exercicio : exercicios) {
                System.out.println(exercicio.getId() + " - Exercício: " + exercicio.getNome());
            }

            System.out.print("Digite o ID do exercício a ser concluído: ");
            int idExercicio = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha após o número

            alunosExerciciosInfoDAO.concluirExercicio(idExercicio, id_data);
        }
        public static void cadastrarTreino(TreinoDAO treinoDAO, ExercicioDAO exercicioDAO) {
            Scanner scanner = new Scanner(System.in);
            ExercicioTreinoDAO exercicioTreinoDAO = new ExercicioTreinoDAO();

            System.out.print("Digite o nome do treino: ");
            String nome = scanner.nextLine();
            Treino treino = new Treino();
            treino.setNome(nome);
            treinoDAO.cadastrarTreino(treino);
            treino.setId(treinoDAO.buscaIdPorNome(nome));

            System.out.print("Digite quantos exercícios vão ter no treino: ");
            int numExercicios = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < numExercicios; i++) {
                System.out.print("Digite o ID referente ao exercício:");
                Exercicios.listarIdExercicios(exercicioDAO);
                System.out.print("Ou digite 'n' para adicionar um novo exercicio: ");

                String input = scanner.nextLine();
                if(input.equals("n")){
                    //cadastrarExercicio(exercicioDAO,);
                } else {
                    int indiceExercicio = Integer.parseInt(input);
                    Exercicio exercicio = exercicioDAO.buscarExercicioPorIndice(indiceExercicio);
                    if (exercicio != null) {
                        // Associar músculo existente ao exercício na tabela musculo_exercicio
                        exercicioTreinoDAO.associarExercicioTreino(exercicio,treino);
                    } else {
                        System.out.println("Índice de músculo inválido.");
                    }
                }
            }
        }
        public static void deletarTreino(TreinoDAO treinoDAO){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Treinos: ");
            listarIdTreinos();
            System.out.println("Digite o ID do treino qual deseja deletar: ");
            int idTreino = Integer.parseInt(scanner.nextLine());

            treinoDAO.deletarTreino(idTreino);
        }
        public static void atualizarTreino(TreinoDAO treinoDAO){
            ExercicioTreinoDAO exercicioTreinoDAO = new ExercicioTreinoDAO();
            Treino treino = new Treino();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Treinos: ");
            listarIdTreinos();
            System.out.println("Digite o ID do treino qual deseja alterar: ");
            treino.setId(Integer.parseInt(scanner.nextLine()));

            System.out.println("Selecione o que deseja atualizar:");
            System.out.println("1. Nome");
            System.out.println("2. Adicionar exercicio");
            System.out.println("3. Deletar exercicio");
            System.out.print("Escolha a opção desejada: ");

            ExercicioDAO exercicioDAO = new ExercicioDAO();
            Exercicio exercicio = new Exercicio();

            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.println("Digite o novo nome: ");
                    treino.setNome(scanner.nextLine());
                    treinoDAO.atualizarNome(treino);
                    break;
                case 2:
                    Exercicios.listarIdExercicios(exercicioDAO);
                    System.out.println("Digite o ID do exercicio qual deseja adicionar: ");
                    exercicio.setId(scanner.nextInt());

                    exercicioTreinoDAO.associarExercicioTreino(exercicio,treino);
                    break;
                case 3:
                    Exercicios.listarIdExercicios(exercicioDAO);
                    System.out.println("Digite o ID do exercicio qual deseja deletar: ");
                    exercicio.setId(scanner.nextInt());

                    exercicioTreinoDAO.deletarExercicio(exercicio,treino);
                    break;
                default:
                    System.out.println("Opção inválida.");
                    return;
            }
        }
        public static void listarIdTreinos(){
            TreinoDAO treinoDAO = new TreinoDAO();
            List<Treino> treinos = treinoDAO.buscarTodosTreinos();

            System.out.println();
            for (Treino treino : treinos) {
                System.out.println("ID = "+ treino.getId() + " Treino: " + treino.getNome());
            }
        }
        public static void alterarCarga(Aluno aluno, Treino treino, int id_data) {
            Scanner scanner = new Scanner(System.in);
            Exercicio exercicio = new Exercicio();
            AlunosExerciciosInfoDAO alunosExerciciosInfoDAO = new AlunosExerciciosInfoDAO();

            System.out.println("Exercicios: ");
            listarExerciciosAlterarInfo(treino);
            System.out.print("Digite o ID do exercício que deseja alterar: ");
            int idExercicio = scanner.nextInt();

            System.out.println("Digite o que você deseja alterar:");
            System.out.println("1 - Séries");
            System.out.println("2 - Repetições");
            System.out.println("3 - Peso");
            System.out.println("4 - Tempo de descanso");
            int opcao = scanner.nextInt();

            System.out.println("Digite o novo valor:");
            int novoValor = scanner.nextInt();

            exercicio = alunosExerciciosInfoDAO.buscarInfoPorId(idExercicio, id_data, aluno.getId());
            switch (opcao) {
                case 1:
                    exercicio.setSeries(novoValor);
                    break;
                case 2:
                    exercicio.setRepeticoes(novoValor);
                    break;
                case 3:
                    exercicio.setPeso(novoValor);
                    break;
                case 4:
                    exercicio.setTempo_descanso(novoValor);
                    break;
                default:
                    System.out.println("Opção inválida.");
                    return;
            }
            alunosExerciciosInfoDAO.atualizarInfo(aluno,exercicio,id_data);
        }
        public static void associarInfo(Treino treino, Aluno aluno, int id_data) {
            ExercicioTreinoDAO exercicioTreinoDAO = new ExercicioTreinoDAO();
            AlunosExerciciosInfoDAO alunosExerciciosInfoDAO = new AlunosExerciciosInfoDAO();

            List<Exercicio> exerciciosDoTreino = exercicioTreinoDAO.buscarExerciciosPorTreino(treino.getId());

            for (Exercicio exercicio : exerciciosDoTreino) {
                exercicio = alunosExerciciosInfoDAO.verificaInfoAnterior(aluno,exercicio);
                alunosExerciciosInfoDAO.associarInfo(aluno,exercicio,id_data);
            }
        }
        public static void listarExerciciosAlterarInfo(Treino treino){
            ExercicioTreinoDAO exercicioTreinoDAO = new ExercicioTreinoDAO();

            List<Exercicio> exercicios = exercicioTreinoDAO.buscarExerciciosPorTreino(treino.getId());

            System.out.println(" ");
            for (Exercicio exercicio : exercicios) {
                System.out.println("ID = "+ exercicio.getId() + " Exercicio: " + exercicio.getNome());
            }
        }
    }
    public static class Instrutor{
        public static void cadastrarUsuario(AlunoDAO alunoDAO) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Digite o CPF do aluno: ");
            String cpf = scanner.nextLine();
            System.out.print("Digite o nome do aluno: ");
            String nome = scanner.nextLine();
            System.out.print("Digite a data de nascimento (AAAA-MM-DD) do aluno: ");
            String dataNascimentoStr = scanner.nextLine();

            Date dataNascimento = Date.valueOf(dataNascimentoStr);
            Aluno aluno = new Aluno();
            aluno.setCpf(cpf);
            aluno.setNome(nome);
            aluno.setDataNascimento(dataNascimento);

            alunoDAO.cadastrarAluno(aluno);
            System.out.println("Aluno cadastrado com sucesso.");
        }
        public static void deletarAluno(AlunoDAO alunoDAO){
            Aluno aluno = new Aluno();
            aluno = alunoDAO.buscarAlunoPorId();
            alunoDAO.deletarAluno(aluno.getId());
        }
        public static void listarTodosUsuarios(AlunoDAO alunoDAO) {
            List<Aluno> todosAlunos = alunoDAO.buscarTodosAlunos();
            if (todosAlunos.isEmpty()) {
                System.out.println("Não há alunos cadastrados.");
            } else {
                System.out.println("Lista de Todos os Alunos:");
                for (Aluno aluno : todosAlunos) {
                    System.out.println("ID: " + aluno.getId());
                    System.out.println("CPF: " + aluno.getCpf());
                    System.out.println("Nome: " + aluno.getNome());
                    System.out.println("Data de Nascimento: " + aluno.getDataNascimento());
                    System.out.println(); // Pular uma linha entre cada aluno
                }
            }
        }
        public static void atualizarAluno(AlunoDAO alunoDAO){
            Aluno aluno = new Aluno();
            aluno = alunoDAO.buscarAlunoPorId();
            Scanner scanner = new Scanner(System.in);

            System.out.println("Selecione o que deseja atualizar:");
            System.out.println("1. CPF");
            System.out.println("2. Nome");
            System.out.println("3. Data de Nascimento");
            System.out.println("4. Plano");
            System.out.print("Escolha a opção desejada: ");

            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print("Digite o novo CPF: ");
                    String novoCpf = scanner.nextLine();
                    aluno.setCpf(novoCpf);
                    break;
                case 2:
                    System.out.print("Digite o novo nome: ");
                    String novoNome = scanner.nextLine();
                    aluno.setNome(novoNome);
                    break;
                case 3:
                    System.out.print("Digite a nova data de nascimento (AAAA-MM-DD): ");
                    String novaDataNascimentoStr = scanner.nextLine();
                    Date novaDataNascimento = Date.valueOf(novaDataNascimentoStr);
                    aluno.setDataNascimento(novaDataNascimento);
                    break;
                case 4:
                    System.out.print("Digite o novo plano: ");
                    String novoPlano = scanner.nextLine();
                    break;
                default:
                    System.out.println("Opção inválida.");
                    return;
            }

            alunoDAO.atualizarAluno(aluno);
            System.out.println("Aluno atualizado com sucesso.");
        }
    }
    public static class Relatorios{
        public static void relatorioDatas(Aluno aluno){
            Scanner scanner = new Scanner(System.in);
            TreinoDataDAO treinoDataDAO = new TreinoDataDAO();

            System.out.println("Digite o intervalo de datas no formato AAAA-MM-DD para o relatório:");
            String dataInicioStr = scanner.nextLine();
            String dataFinalStr = scanner.nextLine();

            Date dataInicio = Date.valueOf(dataInicioStr);
            Date dataFinal = Date.valueOf(dataFinalStr);

            List<Date> datas = treinoDataDAO.geraRelatorio(aluno, dataInicio, dataFinal);

            System.out.println("O aluno compareceu nos dias:");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Date data : datas) {
                String dataFormatada = sdf.format(data);
                System.out.println(dataFormatada);
            }

        }
        public static void relatorioCargas(Aluno aluno) {
            Scanner scanner = new Scanner(System.in);
            TreinoDataDAO treinoDataDAO = new TreinoDataDAO();
            ExercicioDAO exercicioDAO = new ExercicioDAO();
            AlunosExerciciosInfoDAO alunosExerciciosInfoDAO = new AlunosExerciciosInfoDAO();

            System.out.println("Digite o exercício para ver o relatório:");
            Exercicios.listarIdExercicios(exercicioDAO);
            int idExercicio = scanner.nextInt();

            List<Integer> pesos = alunosExerciciosInfoDAO.geraRelatorio(aluno, idExercicio);

            System.out.println("O aluno desempenhou da seguinte maneira:");
            for (int peso : pesos) {
                System.out.println(peso);
            }
        }
    }
}