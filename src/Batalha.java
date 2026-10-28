import java.util.Scanner;

public class Batalha 
{

    public static String limpartela = "\033[H\033[2J";

    public static boolean executarBatalha(Jogador j, Inimigo i, Scanner leitor, int progresso) 
    {
        int turno = 1;
        int menu = 1; 
        String mensagem = "Batalha contra " + i.getNome() + " iniciada!";
        int hab3G = -1;
        int hab3GBase;
        int hab1L = -1;
        int valorj;
        int danoi;

        while(j.Vivo() && i.Vivo()) 
        {
            if(j.getClasse() == 1 && hab3G == 4)
            {
                hab3GBase = j.getATK();
                j.melhoraATK(3);
                hab3G--;
            }
            if(hab3G == 0)
            {
                j.melhoraATK(-3);
                hab3G--;
            }

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
            else if(menu == 2) 
            { 
                String[] habs = getNomesHabilidades(j.getClasse());
                System.out.println("| (1) " + habs[0] + " (2) " + habs[1] + " (0) Voltar   |");
                System.out.println("| (3) " + habs[2] + " (4) " + habs[3] + "                |");
            } 
            else if(menu == 3) 
            { 
                System.out.println("| (1) Poção HP (x" + j.pocaohp + ")   (2) Poção MP (x" + j.pocaomp + ")   (3) Bomba (x" + j.bomba + ") (0) Voltar |");
            }

            System.out.println("+-------------------------------------------------------------+");
            System.out.println(mensagem);
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
                    if(j.getVelocidade() >= i.getVelocidade() || hab1L == 1)
                    {
                        i.receberDano(j.getATK());
                        valorj = j.getATK();
                        if(!i.Vivo())
                        {
                            continue;
                        }

                        if(hab1L == 1)
                        {
                            mensagem = i.getNome() + " recebeu " + valorj + " de dano!\n";
                            hab1L--;
                            continue;
                        }

                        if(i.atkEspecial(turno))
                        {
                            j.receberDano(i.getATK() * 2);
                            danoi = i.getATK() * 2;
                        }
                        else
                        {
                            j.receberDano(i.getATK());
                            danoi = i.getATK();
                        }
                        mensagem = i.getNome() + " recebeu " + valorj + " de dano!\n" + j.getNome() + " recebeu " + danoi + " de dano!";
                    }
                    else
                    {
                        if(i.atkEspecial(turno))
                        {
                            j.receberDano(i.getATK() * 2);
                            danoi = i.getATK() * 2;
                        }
                        else
                        {
                            j.receberDano(i.getATK());
                            danoi = i.getATK();
                        }
                        if(!j.Vivo())
                        {
                            continue;
                        }

                        i.receberDano(j.getATK());
                        valorj = j.getATK();
                        mensagem = j.getNome() + " recebeu " + danoi + " de dano!\n" + i.getNome() + " recebeu " + valorj + " de dano!";
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
                            if(j.getVelocidade() >= i.getVelocidade())
                            {
                                j.habilidade1(j.getClasse(), i);
                                valorj = j.getATK() * 1.5;

                                if(!i.Vivo())
                                {
                                    continue;
                                }
                            
                                if(i.atkEspecial(turno))
                                {
                                    j.receberDano(i.getATK() * 2);
                                    danoi = i.getATK() * 2;
                                }
                                else
                                {
                                    j.receberDano(i.getATK());
                                    danoi = i.getATK();
                                }
                                mensagem = i.getNome() + " recebeu " + valorj + " de dano!\n" + j.getNome() + " recebeu " + danoi + " de dano!";
                            }
                            else
                            {
                                if(i.atkEspecial(turno))
                                {
                                    j.receberDano(i.getATK() * 2);
                                    danoi = i.getATK() * 2;
                                }
                                else
                                {
                                    j.receberDano(i.getATK());
                                    danoi = i.getATK();
                                }

                                if(!j.Vivo())
                                {
                                    continue;
                                }

                                j.habilidade1(j.getClasse(), i);
                                valorj = j.getATK() * 1.5;

                                mensagem = j.getNome() + " recebeu " + danoi + " de dano!\n" + i.getNome() + " recebeu " + valorj + " de dano!";
                            }
                            acao = true;
                            menu = 1;
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
                            if(hab1L < 0)
                            {
                                hab1L = 1;
                                j.perdeMP(5);
                                menu = 1;
                                mensagem = "Ataque Duplo ativado! " + j.nome + " possui duas ações neste turno";
                            }
                            else
                            {
                                mensagem = "[!] Habilidade indisponível no momento!";   
                            }
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
                            if(j.getVelocidade() >= i.getVelocidade())
                            {
                                j.habilidade1(j.getClasse(), i);
                                valorj = j.getATK() * 2;

                                if(!i.Vivo())
                                {
                                    continue;
                                }
                            
                                if(i.atkEspecial(turno))
                                {
                                    j.receberDano(i.getATK() * 2);
                                    danoi = i.getATK() * 2;
                                }
                                else
                                {
                                    j.receberDano(i.getATK());
                                    danoi = i.getATK();
                                }

                                mensagem = i.getNome() + " recebeu " + valorj + " de dano!\n" + j.getNome() + " recebeu " + danoi + " de dano!";
                            }
                            else
                            {
                                if(i.atkEspecial(turno))
                                {
                                    j.receberDano(i.getATK() * 2);
                                    danoi = i.getATK() * 2;
                                }
                                else
                                {
                                    j.receberDano(i.getATK());
                                    danoi = i.getATK();
                                }
                                if(!j.Vivo())
                                {
                                    continue;
                                }

                                j.habilidade1(j.getClasse(), i);
                                valorj = j.getATK() * 2;

                                mensagem = j.getNome() + " recebeu " + danoi + " de dano!\n" + i.getNome() + " recebeu " + valorj + " de dano!";
                            }
                            menu = 1;
                            acao = true;
                        }
                        else 
                        {
                            mensagem = "[!] MP insuficiente para usar esta habilidade!";
                        }
                    }
                } 
                else if(opcao == 2)
                {
                    if(j.getClasse() == 1)
                    {
                        if(j.getMP() >= 6) 
                        {
                            if(i.atkEspecial(turno))
                            {
                                j.receberDano(i.getATK());
                                danoi = i.getATK();
                                if(!j.Vivo())
                                {
                                    continue;
                                }
                                i.receberDano(j.getATK());
                                valorj = j.getATK();
                                mensagem = j.getNome() + " recebeu " + danoi + " de dano!\n" + i.getNome() + " recebeu " + valorj + " de dano!";
                            }
                            else
                            {
                                j.receberDano(i.getATK() / 2);
                                danoi = i.getATK() / 2;
                                if(!j.Vivo())
                                {
                                    continue;
                                }
                                i.receberDano(j.getATK());
                                valorj = j.getATK();
                                mensagem = j.getNome() + " recebeu " + danoi + " de dano!\n" + i.getNome() + " recebeu " + valorj + " de dano!";
                            }
                            j.perdeMP(6);
                            menu = 1;
                            acao = true;
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
                            j.habilidade2(j.getClasse(), i);
                            valorj = j.getATK() * 1.5;
                            mensagem = i.getNome() + " recebeu " + valorj + " de dano!";
                            hab1L--;
                            menu = 1;
                            acao = true;
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
                            if(j.getVelocidade() >= i.getVelocidade())
                            {
                                j.habilidade2(j.getClasse(), i);
                            
                                if(i.atkEspecial(turno))
                                {
                                    j.receberDano(i.getATK() * 2);
                                    danoi = i.getATK() * 2;
                                }
                                else
                                {
                                    j.receberDano(i.getATK());
                                    danoi = i.getATK();
                                }
                                mensagem = j.getNome() + " curou 20 de HP!\n" + j.getNome() + " recebeu " + danoi + " de dano!";
                            }
                            else
                            {
                                if(i.atkEspecial(turno))
                                {
                                    j.receberDano(i.getATK() * 2);
                                }
                                else
                                {
                                    j.receberDano(i.getATK());
                                }
                                if(!j.Vivo())
                                {
                                    continue;
                                }

                                j.habilidade2(j.getClasse(), i);

                                mensagem = j.getNome() + " recebeu " + danoi + " de dano!\n" + j.getNome() + " curou 20 de HP!";
                            }
                            menu = 1;
                            acao = true;
                        }
                        else 
                        {
                            mensagem = "[!] MP insuficiente para usar esta habilidade!";
                        }
                    }
                }
                else if(opcao == 3)
                {
                    if(progresso >= 2)
                    {
                        if(j.getClasse() == 1)
                        {
                            if(j.getMP() >= 8) 
                            {
                                if(hab3G <= 0)
                                {
                                    hab3G = 5;
                                    j.perdeMP(8);
                                    if(i.atkEspecial(turno))
                                    {
                                        j.receberDano(i.getATK() * 2);
                                        danoi = i.getATK() * 2;
                                    }
                                    else
                                    {
                                        j.receberDano(i.getATK());
                                        danoi = i.getATK();
                                    }

                                    mensagem = "Grito de Guerra ativado! O ATK foi aumentado em +3 por 3 turnos.\n" + j.getNome() + " recebeu " + danoi + " de dano!";
                                    menu = 1;
                                    acao = true;
                                }
                                else
                                {
                                    mensagem = "[!] Habilidade indisponível no momento!";
                                }
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
                                if(j.getVelocidade() >= i.getVelocidade() || hab1L == 1)
                                {
                                    j.habilidade3(j.getClasse(), i);
                                    valorj = j.getATK();

                                    if(!i.Vivo())
                                    {
                                        continue;
                                    }

                                    if(hab1L == 1)
                                    {
                                        mensagem = j.getNome() + " curou " + valorj + " de HP e " + i.getNome() + " recebeu " + valorj + " de dano!";
                                        hab1L--;
                                        continue;
                                    }
                                
                                    if(i.atkEspecial(turno))
                                    {
                                        j.receberDano(i.getATK() * 2);
                                        danoi = i.getATK() * 2;
                                    }
                                    else
                                    {
                                        j.receberDano(i.getATK());
                                        danoi = i.getATK();
                                    }
                                    mensagem = j.getNome() + " curou " + valorj + " de HP e " + i.getNome() + " recebeu " + valorj + " de dano!\n" + j.getNome() + " recebeu " + danoi + " de dano!";
                                }
                                else
                                {
                                    if(i.atkEspecial(turno))
                                    {
                                        j.receberDano(i.getATK() * 2);
                                        danoi = i.getATK() * 2;
                                    }
                                    else
                                    {
                                        j.receberDano(i.getATK());
                                        danoi = i.getATK();
                                    }

                                    if(!j.Vivo())
                                    {
                                        continue;
                                    }

                                    j.habilidade3(j.getClasse(), i);
                                    valorj = j.getATK();

                                    mensagem = j.getNome() + " recebeu " + danoi + " de dano!\n" + j.getNome() + " curou " + valorj + " de HP e " + i.getNome() + " recebeu " + valorj + " de dano!";
                                }
                                menu = 1;
                                acao = true;
                            }
                            else 
                            {
                                mensagem = "[!] MP insuficiente para usar esta habilidade!";
                            }
                        }
                        else if(j.getClasse() == 3)
                        {
                            if(j.getVelocidade() >= i.getVelocidade())
                            {
                                j.habilidade3(j.getClasse(), i);
                                valorj = j.getATK() * 0.5;
                                if(!i.Vivo())
                                {
                                    continue;
                                }
                            
                                if(i.atkEspecial(turno))
                                {
                                    j.receberDano(i.getATK() * 2);
                                    danoi = i.getATK() * 2;
                                }
                                else
                                {
                                    j.receberDano(i.getATK());
                                    danoi = i.getATK();
                                }
                                mensagem = j.getNome() + " recurou 15 de MP e " + i.getNome() + " recebeu " + valorj + " de dano!\n" + j.getNome() + " recebeu " + danoi + " de dano!";
                            }
                            else
                            {
                                if(i.atkEspecial(turno))
                                {
                                    j.receberDano(i.getATK() * 2);
                                    danoi = i.getATK() * 2;
                                }
                                else
                                {
                                    j.receberDano(i.getATK());
                                    danoi = i.getATK();
                                }

                                if(!j.Vivo())
                                {
                                    continue;
                                }

                                j.habilidade3(j.getClasse(), i);
                                valorj = j.getATK() * 0.5;
                                mensagem = j.getNome() + " recebeu " + danoi + " de dano!\n" + j.getNome() + " recurou 15 de MP e " + i.getNome() + " recebeu " + valorj + " de dano!";
                            }
                            menu = 1;
                            acao = true;
                        }
                    }
                    else
                    {
                        mensagem = "[!] Habilidade 3 bloqueada e indisponível para uso!";
                    }
                }
                else if(opcao == 4)
                {
                    if(progresso >= 5)
                    {
                        if(j.getClasse() == 1)
                        {
                            if(j.getMP() >= 15) 
                            {
                                if(j.getVelocidade() >= i.getVelocidade())
                                {
                                    j.habilidade4(j.getClasse(), i);
                                    valorj = j.getATK() * 2.5;
                                    if(!i.Vivo())
                                    {
                                        continue;
                                    }
                                
                                    if(i.atkEspecial(turno))
                                    {
                                        j.receberDano(i.getATK() * 2);
                                        danoi = i.getATK() * 2;
                                    }
                                    else
                                    {
                                        j.receberDano(i.getATK());
                                        danoi = i.getATK();
                                    }
                                    mensagem = i.getNome() + " recebeu " + valorj + " de dano!\n" + j.getNome() + " recebeu " + danoi + " de dano!";
                                }
                                else
                                {
                                    if(i.atkEspecial(turno))
                                    {
                                        j.receberDano(i.getATK() * 2);
                                        danoi = i.getATK() * 2;
                                    }
                                    else
                                    {
                                        j.receberDano(i.getATK());
                                        danoi = i.getATK();
                                    }

                                    if(!j.Vivo())
                                    {
                                        continue;
                                    }

                                    j.habilidade4(j.getClasse(), i);
                                    valorj = j.getATK() * 2.5;
                                    mensagem = j.getNome() + " recebeu " + danoi + " de dano!\n" + i.getNome() + " recebeu " + valorj + " de dano!";
                                }
                                menu = 1;
                                acao = true;
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
                                if(j.getVelocidade() >= i.getVelocidade() || hab1L == 1)
                                {
                                    j.habilidade4(j.getClasse(), i);
                                    valorj = j.getATK() * 3;

                                    if(!i.Vivo())
                                    {
                                        continue;
                                    }

                                    if(hab1L == 1)
                                    {
                                        mensagem = i.getNome() + " recebeu " + valorj + " de dano!";
                                        hab1L--;
                                        continue;
                                    }
                                
                                    if(i.atkEspecial(turno))
                                    {
                                        j.receberDano(i.getATK() * 2);
                                        danoi = i.getATK() * 2;
                                    }
                                    else
                                    {
                                        j.receberDano(i.getATK());
                                        danoi = i.getATK();
                                    }
                                    mensagem = i.getNome() + " recebeu " + valorj + " de dano!\n" + j.getNome() + " recebeu " + danoi + " de dano!";
                                }
                                else
                                {
                                    if(i.atkEspecial(turno))
                                    {
                                        j.receberDano(i.getATK() * 2);
                                        danoi = i.getATK() * 2;
                                    }
                                    else
                                    {
                                        j.receberDano(i.getATK());
                                        danoi = i.getATK();
                                    }

                                    if(!j.Vivo())
                                    {
                                        continue;
                                    }

                                    j.habilidade4(j.getClasse(), i);
                                    valorj = j.getATK() * 3;
                                    mensagem = j.getNome() + " recebeu " + danoi + " de dano!\n" + i.getNome() + " recebeu " + valorj + " de dano!";
                                }
                                menu = 1;
                                acao = true;
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
                                if(j.getVelocidade() >= i.getVelocidade())
                                {
                                    j.habilidade4(j.getClasse(), i);
                                    valorj = j.getATK() * 3;

                                    if(!i.Vivo())
                                    {
                                        continue;
                                    }
                                
                                    if(i.atkEspecial(turno))
                                    {
                                        j.receberDano(i.getATK() * 2);
                                        danoi = i.getATK() * 2;
                                    }
                                    else
                                    {
                                        j.receberDano(i.getATK());
                                        danoi = i.getATK();
                                    }
                                    mensagem = i.getNome() + " recebeu " + valorj + " de dano!\n" + j.getNome() + " recebeu " + danoi + " de dano!";
                                }
                                else
                                {
                                    if(i.atkEspecial(turno))
                                    {
                                        j.receberDano(i.getATK() * 2);
                                    }
                                    else
                                    {
                                        j.receberDano(i.getATK());
                                    }

                                    if(!j.Vivo())
                                    {
                                        continue;
                                    }

                                    j.habilidade4(j.getClasse(), i);
                                    valorj = j.getATK() * 3;
                                    mensagem = j.getNome() + " recebeu " + danoi + " de dano!\n" + i.getNome() + " recebeu " + valorj + " de dano!";
                                }
                                menu = 1;
                                acao = true;
                            }
                            else 
                            {
                                mensagem = "[!] MP insuficiente para usar esta habilidade!";
                            }
                        }
                    }
                    else
                    {
                        mensagem = "[!] Habilidade 4 bloqueada e indisponível para uso!";
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
                        if(j.getVelocidade() >= i.getVelocidade() || hab1L == 1)
                        {
                            j.usaPocHP();

                            if(hab1L == 1)
                            {
                                mensagem = j.getNome() + " usou Poção de HP! Vida recuperada.";
                                hab1L--;
                                continue;
                            }

                            if(i.atkEspecial(turno))
                            {
                                j.receberDano(i.getATK() * 2);
                                danoi = i.getATK() * 2;
                            }
                            else
                            {
                                j.receberDano(i.getATK());
                                danoi = i.getATK();
                            }
                            mensagem = j.getNome() + " usou Poção de HP! Vida recuperada.\n" + j.getNome() + " recebeu " + danoi + " de dano!";
                        }
                        else
                        {
                            if(i.atkEspecial(turno))
                            {
                                j.receberDano(i.getATK() * 2);
                                danoi = i.getATK() * 2;
                            }
                            else
                            {
                                j.receberDano(i.getATK());
                                danoi = i.getATK();
                            }

                            if(!j.Vivo())
                            {
                                continue;
                            }

                            j.usaPocHP();
                            mensagem = j.getNome() + " recebeu " + danoi + " de dano!\n" + j.getNome() + " usou Poção de HP! Vida recuperada.";
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
                        if(j.getVelocidade() >= i.getVelocidade() || hab1L == 1)
                        {
                            j.usaPocMP();

                            if(hab1L == 1)
                            {
                                mensagem = j.getNome() + " usou Poção de MP! Mana recuperada.";
                                hab1L--;
                                continue;
                            }

                            if(i.atkEspecial(turno))
                            {
                                j.receberDano(i.getATK() * 2);
                                danoi = i.getATK() * 2;
                            }
                            else
                            {
                                j.receberDano(i.getATK());
                                danoi = i.getATK();
                            }

                            mensagem = j.getNome() + " usou Poção de MP! Mana recuperada.\n" + j.getNome() + " recebeu " + danoi + " de dano!";
                        }
                        else
                        {
                            if(i.atkEspecial(turno))
                            {
                                j.receberDano(i.getATK() * 2);
                                danoi = i.getATK() * 2;
                            }
                            else
                            {
                                j.receberDano(i.getATK());
                                danoi = i.getATK();
                            }

                            if(!j.Vivo())
                            {
                                continue;
                            }

                            j.usaPocMP();

                            mensagem = j.getNome() + " recebeu " + danoi + " de dano!\n" + j.getNome() + " usou Poção de MP! Mana recuperada.";
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
                        hab1L--;
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

            if(acao)
            {
                hab3G--;
                turno++;
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

//ver habilidade 3 guerreiro e habilidade 1 ladino possiveis bugs