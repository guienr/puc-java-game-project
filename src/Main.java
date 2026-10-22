import java.util.InputMismatchException;
import java.util.Scanner;

public class Main
{
    public static void vitoria(Jogador j, Inimigo i)
    {
        System.out.println(i.getNome() + " derrotado!");
        System.out.println("Vitória de " + j.getNome() + "!\n");
    }

    public static void derrota(Jogador j, int progresso)
    {
        System.out.println("DERROTA!");
        System.out.println("GAME OVER\n");
        System.out.println("Relatório de progressão do jogo:");
        System.out.println("Nome: " + j.getNome());
        if(j.getClasse() == 1)
        {
            System.out.println("Classe: GUERREIRO");
        }
        else if(j.getClasse() == 2)
        {
            System.out.println("Classe: LADINO");
        }
        else
        {
            System.out.println("Classe: MAGO");
        }
        System.out.println("HP MÁX: " + j.getHPMax());
        System.out.println("MP MÁX: " + j.getMPMax());
        System.out.println("ATK: " + j.getATK());
        System.out.println("SPD: " + j.getVelocidade());
        System.out.println("Poções de HP: " + j.pocaohp);
        System.out.println("Poções de MP: " + j.pocaomp);
        System.out.println("Bomba: " + j.bomba + "\n");

        System.out.println("Melhorias: ");
        //colocar melhorias

        System.out.println("Quantidade de inimigos derrotados: " + progresso);

        System.exit(0);
    }

    public static void main(String args[])
    {
        Scanner leitor = new Scanner(System.in);

        System.out.println("Bem vindo ao Roguelike!");
        System.out.println("Qual o nome do seu personagem?");
        System.out.print("Nome: ");
        String nome = leitor.nextLine();

        System.out.println("\nQual classe voce quer jogar?");
        System.out.println("(1) Guerreiro | HP: 120 | MP: 20 | ATK: 10 | SPD: 5");
        System.out.println("Habilidade 1: Golpe Pesado: Custa 5 MP. Causa 1,5x de dano.");
        System.out.println("Habilidade 2: Defesa de Ferro: Custa 6 MP. O Guerreiro recebe apenas metade do dano no próximo turno e da um contra-ataque.");
        System.out.println("Habilidade 3: Grito de Guerra: Custa 8 MP. Aumenta o próprio ataque em +3 por 3 turnos.");
        System.out.println("Habilidade 4: Investida Final: Custa 15 MP. Causa 2,5x de dano.\n");
        System.out.println("(2) Ladino | HP: 70 | MP: 30 | ATK: 16 | SPD: 9");
        System.out.println("Habilidade 1: Ataque Duplo: Custa 5 MP. Tem duas ações no mesmo turno.");
        System.out.println("Habilidade 2: Golpe Sujo: Custa 6 MP. Um ataque rápido que causa 1,5x de dano e pula a rodada do inimigo");
        System.out.println("Habilidade 3: Roubo de Vida: Custa 8 MP. Causa dano igual ao ataque normal, mas cura o Ladino no mesmo valor do dano causado.");
        System.out.println("Habilidade 4: Ataque Furtivo: Custa 14 MP. Causa 3x de dano.\n");
        System.out.println("(3) Mago | HP: 60 | MP: 60 | ATK: 12 | SPD: 6");
        System.out.println("Habilidade 1: Bola de Fogo: Custa 8 MP. Causa 2x de dano.");
        System.out.println("Habilidade 2: Cura Básica: Custa 10 MP. Recupera 20 de HP.");
        System.out.println("Habilidade 3: Dreno de Alma: Custa 0 MP. Causa 0,5x de dano, mas recupera 15 de MP do Mago.");
        System.out.println("Habilidade 4: Meteoro: Custa 20 MP. Causa 3x de dano.\n");
        System.out.println("OBS: As habilidades 3 e 4 só são desbloqueadas ao derrotar o boss 1 e 2, respectivamente");

        boolean c = false;
        int classe;
        
        do
        {
            System.out.print("\nClasse: ");
            try
            {
                classe = leitor.nextInt();

                if(classe >= 1 && classe <= 3) 
                {
                    c = true; 
                } 
                else 
                {
                    System.out.println("\nOpção inválida! Escolha a classe 1, 2 ou 3.\n");
                }

            } 
            catch(InputMismatchException e) 
            {
                System.out.println("\nPor favor, digite apenas um número inteiro!\n");
            }
        } while(!c);
        
        if(classe == 1) 
        {
            Jogador j = new Jogador(nome, 120, 20, 10, 5, classe);
        }
        else if(classe == 2)
        {
            Jogador j = new Jogador(nome, 70, 30, 16, 9, classe);
        }
        else if(classe == 3)
        {
            Jogador j = new Jogador(nome, 60, 60, 12, 6, classe);
        }
        int progresso = 0;
        booolean v = false;

        Inimigo minion1 = new Inimigo("m1", 30, 6, 4, 0);
        v = executarBatalha(j, minion1, leitor);

        limpartela();
        if(v == true)
        {
            vitoria(j, minion1);
            progresso++;
        }
        else
        {
            derrota(j, progresso);
        }

        
        Inimigo boss1 = new Inimigo("b1", 80, 10, 5, 1);
        v = executarBatalha(j, boss1, leitor);

        limpartela();
        if(v == true)
        {
            vitoria(j, boss1);
            progresso++;
        }
        else
        {
            derrota(j, progresso);
        }

        Inimigo minion2 = new Inimigo("m2", 40, 7, 5, 0);
        v = executarBatalha(j, minion2, leitor);

        limpartela();
        if(v == true)
        {
            vitoria(j, boss1);
            progresso++;
        }
        else
        {
            derrota(j, progresso);
        }

        Inimigo minion3 = new Inimigo("m3", 45, 8, 5, 0);
        v = executarBatalha(j, minion3, leitor);

        limpartela();
        if(v == true)
        {
            vitoria(j, boss1);
            progresso++;
        }
        else
        {
            derrota(j, progresso);
        }

        Inimigo boss2 = new Inimigo("b2", 130, 13, 6, 1);
        v = executarBatalha(j, boss2, leitor);

        limpartela();
        if(v == true)
        {
            vitoria(j, boss1);
            progresso++;
        }
        else
        {
            derrota(j, progresso);
        }

        Inimigo minion4 = new Inimigo("m4", 55, 9, 6, 0);
        v = utarBatalha(j, minion4, leitor);

        limpartela();
        if(v == true)
        {
            vitoria(j, boss1);
            progresso++;
        }
        else
        {
            derrota(j, progresso);
        }

        Inimigo minion5 = new Inimigo("m5", 60, 10, 6, 0);
        v = executarBatalha(j, minion5, leitor);

        limpartela();
        if(v == true)
        {
            vitoria(j, boss1);
            progresso++;
        }
        else
        {
            derrota(j, progresso);
        }

        Inimigo minion6 = new Inimigo("m6", 65, 11, 7, 0);
        v = executarBatalha(j, minion6, leitor);

        limpartela();
        if(v == true)
        {
            vitoria(j, boss1);
            progresso++;
        }
        else
        {
            derrota(j, progresso);
        }

        Inimigo boss3 = new Inimigo("b3", 110, 16, 7, 1);
        v = executarBatalha(j, boss3, leitor);

        limpartela();
        if(v == true)
        {
            vitoria(j, boss1);
            progresso++;
        }
        else
        {
            derrota(j, progresso);
        }

        Inimigo boss4 = new Inimigo("b3 2 fase", 110, 20, 7, 1);
        v = executarBatalha(j, boss4, leitor);  

        limpartela();
        if(v == false)
        {
            derrota(j, progresso);
        }
        
        System.out.println("Você derrotou o último chefe!");
        System.out.println("Parabéns você terminou o jogo!!!");

        System.out.println("Relatório de progressão do jogo:");
        System.out.println("Nome: " + j.getNome());
        if(j.getClasse() == 1)
        {
            System.out.println("Classe: GUERREIRO");
        }
        else if(j.getClasse() == 2)
        {
            System.out.println("Classe: LADINO");
        }
        else
        {
            System.out.println("Classe: MAGO");
        }
        System.out.println("HP MÁX: " + j.getHPMax());
        System.out.println("MP MÁX: " + j.getMPMax());
        System.out.println("ATK: " + j.getATK());
        System.out.println("SPD: " + j.getVelocidade());
        System.out.println("Poções de HP: " + j.pocaohp);
        System.out.println("Poções de MP: " + j.pocaomp);
        System.out.println("Bomba: " + j.bomba + "\n");

        System.out.println("Melhorias: ");
        //colocar melhorias

        System.out.println("Quantidade de inimigos derrotados: " + progresso+ "\n");
        System.out.println("Obrigado por jogar!");
    }
}

// criar recompensas
// escrever habilidades
// arrumar batalha