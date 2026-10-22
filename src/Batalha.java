import java.util.Scanner;

public class Batalha 
{

    public static String limpartela = "\033[H\033[2J";

    public static boolean executarBatalha(Jogador j, Inimigo i, Scanner leitor) 
    {
        int turno = 1;
        int menu = 1; 
        String mensagem = "Batalha contra " + i.getNome() + " iniciada!";

        while(j.Vivo() == 1 && i.Vivo() == 1) 
        {

            System.out.print(limpartela);
            System.out.flush();

            System.out.println("TURNO " + turno);
            System.out.println("+-------------------------------------------------------------+");
            System.out.println("| [ " + j.getNome() + " ] HP: " + j.getHP() + "/" + j.getHPMax() + " MP: " + j.getMP() + "/" + j.getMPMax() + " | [ " + i.getNome() + " ] HP: " + i.getHP() + "   |");
            System.out.println("+-------------------------------------------------------------+");

            if(menu == 1) 
            { 
                System.out.println("| (1) Atacar          (2) Habilidades       (3) Itens         |");
            } 
            else if(menuAtual == 2) 
            { 
                String[] habs = getNomesHabilidades(j.getClasse());
                System.out.println("| (1) " + habs[0] + " (2) " + habs[1] + " (0) Voltar   |");
                System.out.println("| (3) " + habs[2] + " (4) " + habs[3] + "                |");
            } 
            else if(menuAtual == 3) 
            { 
                System.out.println("| (1) Poção HP (x" + j.pocaohp + ")   (2) Poção MP (x" + j.pocaomp + ")   (3) Bomba (x" + j.bomba + ") (0) Voltar |");
            }

            System.out.println("+-------------------------------------------------------------+");
            System.out.printfln(mensagem);
            System.out.println("+-------------------------------------------------------------+");

            System.out.print("Escolha uma opção: ");
            int opcao = -1;
            
            try 
            {
                opcao = Integer.parseInt(leitor.nextLine());
            } 
            catch(Exception e) 
            {
                mensagem = "[!] Opção inválida! Digite apenas números.";
                continue; 
            }

            boolean acao = false;

            if(menu == 1) 
            {
                if(opcao == 1) 
                { 
                    if(j.spd >= i.spd)
                    {
                        i.receberDano(j.getATK());

                        if(i.atkEspecial(turno))
                        {
                            j.receberDano(i.getATK() * 2);
                            mensagem = i.getNome() + " recebeu " + j.getATK() + " de dano!\n" + j.getNome() + " recebeu " + i.getATK() * 2 + " de dano!";
                        }
                        else
                        {
                            j.receberDano(i.getATK());
                            mensagem = i.getNome() + " recebeu " + j.getATK() + " de dano!\n" + j.getNome() + " recebeu " + i.getATK() + " de dano!";
                        }
                        
                    }
                    else
                    {
                        if(i.atkEspecial(turno))
                        {
                            j.receberDano(i.getATK() * 2);
                            mensagem = j.getNome() + " recebeu " + i.getATK() * 2 + " de dano!\n" + i.getNome() + " recebeu " + j.getATK() + " de dano!";
                        }
                        else
                        {
                            j.receberDano(i.getATK());
                            mensagem = j.getNome() + " recebeu " + i.getATK() + " de dano!\n" + i.getNome() + " recebeu " + j.getATK() + " de dano!";
                        }

                        i.receberDano(j.getATK());
                    }
                    
                    acao = true;
                } 
                else if(opcao == 2) 
                {  
                    menu = 2;
                    mensagem = "Escolha uma habilidade (ou 0 para voltar):";
                } 
                else if(opcao == 3) 
                {
                    menu = 3;
                    mensagem = "Escolha um item (ou 0 para voltar):";
                } 
                else 
                {
                    mensagem = "[!] Opção inválida! Escolha 1, 2 ou 3.";
                }
            } 

            else if(menu == 2) 
            {
                if(opcao == 0) 
                {
                    menu = 1;
                    mensagem = "Voltou ao menu principal.";
                } 
                else if(opcao == 1) 
                {
                    if(j.getClasse() == 1)
                    {
                        if(j.getMP() >= 5) 
                        {
                            j.habilidade1(j.getClasse(), i);
                        }
                        else 
                        {
                            mensagem = "[!] MP insuficiente para usar esta habilidade!";
                        }
                    }
                    else if(j.getClasse() == 2)
                    {
                        if(j.getMP() >= 5) 
                        {

                        }
                        else 
                        {
                            mensagem = "[!] MP insuficiente para usar esta habilidade!";
                        }
                    }
                    else if(j.getClasse() == 3)
                    {
                        if(j.getMP() >= 8) 
                        {
                            j.habilidade1(j.getClasse(), i);
                        }
                        else 
                        {
                            mensagem = "[!] MP insuficiente para usar esta habilidade!";
                        }
                    }
                    /* 
                    mensagem = "Usou Habilidade " + opcao + "! Causou " + dano + " de dano.";
                    menu = 1; 
                    acao = true;
                    */ 
                } 
                else if(opcao == 2)
                {
                    if(j.getClasse() == 1)
                    {
                        if(j.getMP() >= 6) 
                        {
                            //fazer
                        }
                        else 
                        {
                            mensagem = "[!] MP insuficiente para usar esta habilidade!";
                        }
                    }
                    else if(j.getClasse() == 2)
                    {
                        if(j.getMP() >= 6) 
                        {
                            //fazer
                        }
                        else 
                        {
                            mensagem = "[!] MP insuficiente para usar esta habilidade!";
                        }
                    }
                    else if(j.getClasse() == 3)
                    {
                        if(j.getMP() >= 10) 
                        {
                            j.habilidade2(j.getClasse(), i);
                        }
                        else 
                        {
                            mensagem = "[!] MP insuficiente para usar esta habilidade!";
                        }
                    }
                }
                else if(opcao == 3)
                {
                    if(j.getClasse() == 1)
                    {
                        if(j.getMP() >= 8) 
                        {
                            //fazer
                        }
                        else 
                        {
                            mensagem = "[!] MP insuficiente para usar esta habilidade!";
                        }
                    }
                    else if(j.getClasse() == 2)
                    {
                        if(j.getMP() >= 8) 
                        {
                            j.habilidade3(j.getClasse(), i);
                        }
                        else 
                        {
                            mensagem = "[!] MP insuficiente para usar esta habilidade!";
                        }
                    }
                    else if(j.getClasse() == 3)
                    {
                        j.habilidade3(j.getClasse(), i);
                    }
                }
                else if(opcao == 4)
                {
                    if(j.getClasse() == 1)
                    {
                        if(j.getMP() >= 15) 
                        {
                            j.habilidade4(j.getClasse(), i);
                        }
                        else 
                        {
                            mensagem = "[!] MP insuficiente para usar esta habilidade!";
                        }
                    }
                    else if(j.getClasse() == 2)
                    {
                        if(j.getMP() >= 14) 
                        {
                            j.habilidade4(j.getClasse(), i);
                        }
                        else 
                        {
                            mensagem = "[!] MP insuficiente para usar esta habilidade!";
                        }
                    }
                    else if(j.getClasse() == 3)
                    {
                        if(j.getMP() >= 20) 
                        {
                            j.habilidade4(j.getClasse(), i);
                        }
                        else 
                        {
                            mensagem = "[!] MP insuficiente para usar esta habilidade!";
                        }
                    }
                }
                else 
                {
                    mensagem = "[!] Opção inválida! Escolha entre 1 e 4 (ou 0 para voltar).";
                }
            } 
            else if(menu == 3) 
            {
                if(opcao == 0) 
                {
                    menu = 1;
                    mensagem = "Voltou ao menu principal.";
                } 
                else if(opcao == 1) 
                { 
                    if(j.verificaPocHP()) 
                    {
                        if(j.spd >= i.spd)
                        {
                            j.usaPocHP();

                            if(i.atkEspecial(turno))
                            {
                                j.receberDano(i.getATK() * 2);
                                mensagem = j.getNome() + " usou Poção de HP! Vida recuperada.\n" + j.getNome() + " recebeu " + i.getATK() * 2 + " de dano!";
                            }
                            else
                            {
                                j.receberDano(i.getATK());
                                mensagem = j.getNome() + " usou Poção de HP! Vida recuperada.\n" + j.getNome() + " recebeu " + i.getATK() + " de dano!";
                            }
                        }
                        else
                        {
                            if(i.atkEspecial(turno))
                            {
                                j.receberDano(i.getATK() * 2);
                                mensagem = j.getNome() + " recebeu " + i.getATK() * 2 + " de dano!\n" + j.getNome() + " usou Poção de HP! Vida recuperada.\n";
                            }
                            else
                            {
                                j.receberDano(i.getATK());
                                mensagem = j.getNome() + " recebeu " + i.getATK() + " de dano!\n" + j.getNome() + " usou Poção de HP! Vida recuperada.\n";
                            }

                            j.usaPocHP();
                        }
                        menu = 1;
                        acao = true;
                    }   
                    else 
                    {
                        mensagem = "[!] Você não possui Poções de HP!";
                    }
                } 
                else if (opcao == 2) 
                {
                    if(j.verificaPocMP()) 
                    {
                        if(j.spd >= i.spd)
                        {
                            j.usaPocMP();

                            if(i.atkEspecial(turno))
                            {
                                j.receberDano(i.getATK() * 2);
                                mensagem = j.getNome() + " usou Poção de MP! Mana recuperada.\n" + j.getNome() + " recebeu " + i.getATK() * 2 + " de dano!";
                            }
                            else
                            {
                                j.receberDano(i.getATK());
                                mensagem = j.getNome() + " usou Poção de MP! Mana recuperada.\n" + j.getNome() + " recebeu " + i.getATK() + " de dano!";
                            }
                        }
                        else
                        {
                            if(i.atkEspecial(turno))
                            {
                                j.receberDano(i.getATK() * 2);
                                mensagem = j.getNome() + " recebeu " + i.getATK() * 2 + " de dano!\n" + j.getNome() + " usou Poção de MP! Mana recuperada.\n";
                            }
                            else
                            {
                                j.receberDano(i.getATK());
                                mensagem = j.getNome() + " recebeu " + i.getATK() + " de dano!\n" + j.getNome() + " usou Poção de MP! Mana recuperada.\n";
                            }

                            j.usaPocMP();
                        }
                        menu = 1;
                        acao = true;
                    }   
                    else 
                    {
                        mensagem = "[!] Você não possui Poções de MP!";
                    }
                } 
                else if(opcao == 3) 
                {  
                    if(j.verificaBomba()) 
                    {
                        j.usaBomba();
                        i.receberDano(25);
                        mensagem = i.getNome() + " recebeu 25 de dano!";
                        menu = 1;
                        acao = true;
                    }   
                    else 
                    {
                        mensagem = "[!] Você não possui Bombas!";
                    }
                } 
                else 
                {
                    mensagem = "[!] Opção inválida! Escolha 1, 2, 3 (ou 0 para voltar).";
                }
            }

        }

        if(j.Vivo())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private static String[] getNomesHabilidades(int classe) 
    {
        if(classe == 1) 
        {
            return new String[]{"Golpe Pesado", "Defesa Ferro", "Grito Guerra", "Inv Final"};
        }
        if(classe == 2)
        {
            return new String[]{"Ataque Duplo", "Golpe Sujo", "Roubo de Vida", "Atq Furtivo"};
        } 
        return new String[]{"Bola de Fogo", "Cura Básica", "Dreno Alma", "Meteoro"};
    }

}

//verificar vivo e se ação foi realizada no turno
//fazer outras habilidades
//rever partes do código