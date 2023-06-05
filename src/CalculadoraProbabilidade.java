import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class CalculadoraProbabilidade {
	
	

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);
        Locale.setDefault(Locale.US);
        int acertos = 0;
        int erros = 0;

        for (int i = 1; i <= 20; i++) {
            System.out.println("Jogo " + i);
            System.out.println("Informe o nome da Equipe A:");
            String equipeA = scanner.nextLine();

            System.out.println("Informe o nome da Equipe B:");
            String equipeB = scanner.nextLine();

            System.out.println("Informe a média de gols marcados pela " + equipeA);
            double golsA = scanner.nextDouble();

            System.out.println("Informe a média de gols marcados pela " + equipeB);
            double golsB = scanner.nextDouble();

            System.out.println("Informe a média de gols sofridos pela " + equipeA);
            double sofridosA = scanner.nextDouble();

            System.out.println("Informe a média de gols sofridos pela " + equipeB);
            double sofridosB = scanner.nextDouble();

            scanner.nextLine(); // Limpar o buffer de leitura

            // Cálculo da probabilidade de vitória usando o modelo de Poisson
            double probabilidadeEquipeA = calcularProbabilidade(golsA, sofridosB);
            double probabilidadeEquipeB = calcularProbabilidade(golsB, sofridosA);

            System.out.println("Probabilidade de vitória para " + equipeA + ": " + probabilidadeEquipeA + "%");
            System.out.println("Probabilidade de vitória para " + equipeB + ": " + probabilidadeEquipeB + "%");

            // Verificar o possível vencedor com base na probabilidade
            String possivelVencedor = determinarPossivelVencedor(equipeA, equipeB, probabilidadeEquipeA, probabilidadeEquipeB);

            System.out.println("O possível vencedor é: " + possivelVencedor);

            System.out.println("Informe o resultado da partida (A/B/Empate):");
            String resultado = scanner.nextLine();

            if (resultado.equalsIgnoreCase("A") && possivelVencedor.equalsIgnoreCase(equipeA)
                    || resultado.equalsIgnoreCase("B") && possivelVencedor.equalsIgnoreCase(equipeB)
                    || resultado.equalsIgnoreCase("Empate") && possivelVencedor.equalsIgnoreCase("Empate")) {
                System.out.println("Resultado correto!");
                acertos++;
            } else {
                System.out.println("Resultado incorreto!");
                erros++;
            }

            // Salvar o resultado em um arquivo de texto
            salvarResultadoEmArquivo(equipeA, equipeB, probabilidadeEquipeA, probabilidadeEquipeB, possivelVencedor, resultado);
        }

        // Exibir o resumo dos acertos
        int totalJogos = acertos + erros;
        double porcentagemAcertos = (double) acertos / totalJogos * 100;

        System.out.println("Resumo dos resultados:");
        System.out.println("Acertos: " + acertos);
        System.out.println("Erros: " + erros);
        System.out.println("Porcentagem de acertos: " + porcentagemAcertos + "%");
    }

    public static double calcularProbabilidade(double golsMarcados, double golsSofridos) {
        // Média de gols esperados por jogo
        double mediaGols = golsMarcados + golsSofridos;

        // Probabilidade de um time marcar X gols em um jogo
        double probabilidade = Math.pow(mediaGols, 0) / fatorial(0) * Math.exp(-mediaGols);

        // Probabilidade de marcar menos de X gols em um jogo
        for (int i = 1; i <= 2; i++) {
            probabilidade += Math.pow(mediaGols, i) / fatorial(i) * Math.exp(-mediaGols);
        }

        // Probabilidade de marcar X ou mais gols em um jogo
        probabilidade = 1 - probabilidade;

        return probabilidade * 100;
    }

    public static int fatorial(int n) {
        if (n <= 1) {
            return 1;
        } else {
            return n * fatorial(n - 1);
        }
    }

    public static String determinarPossivelVencedor(String equipeA, String equipeB,
                                                    double probabilidadeEquipeA, double probabilidadeEquipeB) {
        if (probabilidadeEquipeA > probabilidadeEquipeB) {
            return equipeA;
        } else if (probabilidadeEquipeB > probabilidadeEquipeA) {
            return equipeB;
        } else {
            return "Empate";
        }
    }

    public static boolean verificarResultado(String resultado, String possivelVencedor) {
        resultado = resultado.toLowerCase(); // Converter para letras minúsculas
        possivelVencedor = possivelVencedor.toLowerCase(); // Converter para letras minúsculas

        if (resultado.equalsIgnoreCase("A") && possivelVencedor.equalsIgnoreCase("Equipe A")) {
            return true;
        } else if (resultado.equalsIgnoreCase("B") && possivelVencedor.equalsIgnoreCase("Equipe B")) {
            return true;
        } else if (resultado.equalsIgnoreCase("Empate") && possivelVencedor.equalsIgnoreCase("Empate")) {
            return true;
        } else {
            return false;
        }
    }

    public static void salvarResultadoEmArquivo(String equipeA, String equipeB, double probabilidadeEquipeA,
                                                double probabilidadeEquipeB, String possivelVencedor, String resultado) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("resultados.txt", true));

            writer.write("Equipe A: " + equipeA);
            writer.newLine();
            writer.write("Equipe B: " + equipeB);
            writer.newLine();
            writer.write("Probabilidade de vitória para " + equipeA + ": " + probabilidadeEquipeA + "%");
            writer.newLine();
            writer.write("Probabilidade de vitória para " + equipeB + ": " + probabilidadeEquipeB + "%");
            writer.newLine();
            writer.write("Possível vencedor: " + possivelVencedor);
            writer.newLine();
            writer.write("Resultado informado: " + resultado);
            writer.newLine();
            writer.newLine();

            writer.close();
            System.out.println("Resultado salvo com sucesso no arquivo resultados.txt");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o resultado em arquivo: " + e.getMessage());
        }
    }
}





