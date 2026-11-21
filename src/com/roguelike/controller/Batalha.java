package com.roguelike.controller;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.roguelike.entity.PersonagemComponent;
import com.roguelike.model.Inimigo;
import com.roguelike.model.Jogador;
import com.roguelike.model.PowerUp;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class Batalha {

    private Jogador j;
    private Inimigo i;
    private int progresso;

    private int turno = 1;
    private int hab3G = -1;
    private int hab1L = -1;
    private String mensagem;

    private Runnable aoVencer;
    private Runnable aoPerder;
    private Runnable aoAtualizarUI;

    public Batalha(Entity entidadeJogador, Entity entidadeInimigo, int progresso,
                   Runnable aoVencer, Runnable aoPerder, Runnable aoAtualizarUI) {
        this.j = entidadeJogador.getComponent(PersonagemComponent.class).getJogador();
        this.i = entidadeInimigo.getComponent(PersonagemComponent.class).getInimigo();
        this.progresso = progresso;
        this.aoVencer = aoVencer;
        this.aoPerder = aoPerder;
        this.aoAtualizarUI = aoAtualizarUI;
        this.mensagem = "Batalha contra " + i.getNome() + " iniciada!";
    }

    private void verificarBuffGuerreiroInicio() {
        if (j.getClasse() == 1 && hab3G == 4) {
            j.melhoraATK(5);
            hab3G--;
        }
        if (hab3G == 0) {
            j.melhoraATK(-5);
            hab3G--;
        }
    }

    private void finalizarAcao(boolean avancarTurno) {
        if (avancarTurno) {
            hab3G--;
            turno++;
        }

        if (!i.Vivo()) {
            // Se a luta acabar com o buff do Guerreiro (Grito de Guerra) ativo, remove o bônus temporário
            if (j.getClasse() == 1 && hab3G >= 0 && hab3G < 4) {
                j.melhoraATK(-5);
            }

            String itemGanho = j.ganharItemAleatorio();
            mensagem += "\n" + i.getNome() + " foi derrotado!";
            aoAtualizarUI.run();

            FXGL.getDialogService().showMessageBox(
                "Vitória!\nVocê ganhou 1x " + itemGanho + "!",
                () -> {
                    if (i.isBoss() && j.getBossesDerrotados() < 3) {
                        j.incrementarBossesDerrotados();
                        mostrarTelaPowerUp();
                    } else {
                        aoVencer.run();
                    }
                }
            );
        } else if (!j.Vivo()) {
            aoAtualizarUI.run();
            aoPerder.run();
        } else {
            aoAtualizarUI.run();
        }
    }

    private void mostrarTelaPowerUp() {
        List<PowerUp> opcoes = PowerUp.sortearOpcoes(j);

        StackPane overlay = new StackPane();
        overlay.setPrefSize(FXGL.getAppWidth(), FXGL.getAppHeight());
        overlay.setStyle("-fx-background-color: rgba(25, 25, 25, 0.96);");

        Circle portalExterno = new Circle(45, Color.DODGERBLUE);
        Circle portalInterno = new Circle(25, Color.rgb(10, 20, 40));
        StackPane portal = new StackPane(portalExterno, portalInterno);

        Text titulo = new Text("Selecione uma habilidade");
        titulo.setFill(Color.WHITE);
        titulo.setFont(Font.font("Consolas", FontWeight.BOLD, 26));

        HBox boxBotoes = new HBox(25);
        boxBotoes.setAlignment(Pos.CENTER);

        for (PowerUp pu : opcoes) {
            Button btn = new Button(pu.getNome() + "\n\n" + pu.getDescricao());
            btn.setPrefSize(220, 130);
            btn.setWrapText(true);
            btn.setTextAlignment(TextAlignment.CENTER);
            btn.setStyle(
                "-fx-background-color: #111111; " +
                "-fx-text-fill: #00d4ff; " +
                "-fx-font-family: 'Consolas'; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-border-color: #0088ff; " +
                "-fx-border-width: 2px; " +
                "-fx-border-radius: 8px; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand;"
            );

            btn.setOnAction(e -> {
                pu.aplicar(j);
                j.registrarPowerUp(pu.getId());
                FXGL.getGameScene().removeUINode(overlay);
                aoAtualizarUI.run();
                aoVencer.run();
            });

            boxBotoes.getChildren().add(btn);
        }

        VBox layoutCentral = new VBox(35, portal, titulo, boxBotoes);
        layoutCentral.setAlignment(Pos.CENTER);
        overlay.getChildren().add(layoutCentral);

        FXGL.getGameScene().addUINode(overlay);
    }

    public void atacar() {
        verificarBuffGuerreiroInicio();
        int valorj;
        int danoi;

        if (j.getVelocidade() >= i.getVelocidade() || hab1L == 1) {
            i.receberDano(j.getATK());
            valorj = j.getATK();
            if (!i.Vivo()) {
                mensagem = i.getNome() + " recebeu " + valorj + " de dano!";
                finalizarAcao(false);
                return;
            }
            if (hab1L == 1) {
                mensagem = i.getNome() + " recebeu " + valorj + " de dano! (Ação extra consumida)";
                hab1L--;
                finalizarAcao(false);
                return;
            }
            danoi = i.atkEspecial(turno) ? i.getATK() * 2 : i.getATK();
            j.receberDano(danoi);
            mensagem = i.getNome() + " recebeu " + valorj + " de dano!\n" + j.getNome() + " recebeu " + danoi + " de dano!";
        } else {
            danoi = i.atkEspecial(turno) ? i.getATK() * 2 : i.getATK();
            j.receberDano(danoi);
            if (!j.Vivo()) {
                mensagem = j.getNome() + " recebeu " + danoi + " de dano!";
                finalizarAcao(false);
                return;
            }
            i.receberDano(j.getATK());
            valorj = j.getATK();
            mensagem = j.getNome() + " recebeu " + danoi + " de dano!\n" + i.getNome() + " recebeu " + valorj + " de dano!";
        }
        finalizarAcao(true);
    }

    public void usarHabilidade(int opcao) {
        verificarBuffGuerreiroInicio();
        int valorj;
        int danoi;
        boolean acao = false;

        if (opcao == 1) {
            if (j.getClasse() == 1) {
                if (j.getMP() >= 5) {
                    valorj = (int) (j.getATK() * 1.5);
                    if (j.getVelocidade() >= i.getVelocidade()) {
                        j.habilidade1(j.getClasse(), i);
                        if (!i.Vivo()) {
                            mensagem = i.getNome() + " recebeu " + valorj + " de dano!";
                            finalizarAcao(false);
                            return;
                        }
                        danoi = i.atkEspecial(turno) ? i.getATK() * 2 : i.getATK();
                        j.receberDano(danoi);
                        mensagem = i.getNome() + " recebeu " + valorj + " de dano!\n" + j.getNome() + " recebeu " + danoi + " de dano!";
                    } else {
                        danoi = i.atkEspecial(turno) ? i.getATK() * 2 : i.getATK();
                        j.receberDano(danoi);
                        if (!j.Vivo()) {
                            mensagem = j.getNome() + " recebeu " + danoi + " de dano!";
                            finalizarAcao(false);
                            return;
                        }
                        j.habilidade1(j.getClasse(), i);
                        mensagem = j.getNome() + " recebeu " + danoi + " de dano!\n" + i.getNome() + " recebeu " + valorj + " de dano!";
                    }
                    acao = true;
                } else {
                    mensagem = "[!] MP insuficiente para usar esta habilidade!";
                }
            } else if (j.getClasse() == 2) {
                if (j.getMP() >= 5) {
                    if (hab1L < 0) {
                        hab1L = 1;
                        j.perdeMP(5);
                        mensagem = "Ataque Duplo ativado! " + j.getNome() + " possui duas ações neste turno.";
                    } else {
                        mensagem = "[!] Habilidade indisponível no momento!";
                    }
                } else {
                    mensagem = "[!] MP insuficiente para usar esta habilidade!";
                }
            } else if (j.getClasse() == 3) {
                if (j.getMP() >= 8) {
                    valorj = j.getATK() * 2;
                    if (j.getVelocidade() >= i.getVelocidade()) {
                        j.habilidade1(j.getClasse(), i);
                        if (!i.Vivo()) {
                            mensagem = i.getNome() + " recebeu " + valorj + " de dano!";
                            finalizarAcao(false);
                            return;
                        }
                        danoi = i.atkEspecial(turno) ? i.getATK() * 2 : i.getATK();
                        j.receberDano(danoi);
                        mensagem = i.getNome() + " recebeu " + valorj + " de dano!\n" + j.getNome() + " recebeu " + danoi + " de dano!";
                    } else {
                        danoi = i.atkEspecial(turno) ? i.getATK() * 2 : i.getATK();
                        j.receberDano(danoi);
                        if (!j.Vivo()) {
                            mensagem = j.getNome() + " recebeu " + danoi + " de dano!";
                            finalizarAcao(false);
                            return;
                        }
                        j.habilidade1(j.getClasse(), i);
                        mensagem = j.getNome() + " recebeu " + danoi + " de dano!\n" + i.getNome() + " recebeu " + valorj + " de dano!";
                    }
                    acao = true;
                } else {
                    mensagem = "[!] MP insuficiente para usar esta habilidade!";
                }
            }
        } else if (opcao == 2) {
            if (j.getClasse() == 1) {
                if (j.getMP() >= 6) {
                    int danoOriginal = i.atkEspecial(turno) ? i.getATK() * 2 : i.getATK();
                    danoi = i.atkEspecial(turno) ? i.getATK() : i.getATK() / 4;
                    int danoBloqueado = danoOriginal - danoi;

                    j.receberDano(danoi);
                    if (!j.Vivo()) {
                        mensagem = j.getNome() + " defendeu " + danoBloqueado + " de dano e recebeu " + danoi + " de dano!";
                        finalizarAcao(false);
                        return;
                    }
                    i.receberDano(j.getATK());
                    valorj = j.getATK();
                    j.perdeMP(6);
                    mensagem = j.getNome() + " defendeu " + danoBloqueado + " de dano e recebeu " + danoi + " de dano!\n" + i.getNome() + " recebeu " + valorj + " de dano!";
                    acao = true;
                } else {
                    mensagem = "[!] MP insuficiente para usar esta habilidade!";
                }
            } else if (j.getClasse() == 2) {
                if (j.getMP() >= 6) {
                    j.habilidade2(j.getClasse(), i);
                    valorj = (int) (j.getATK() * 1.5);
                    mensagem = i.getNome() + " recebeu " + valorj + " de dano e perdeu o turno!";
                    hab1L--;
                    acao = true;
                } else {
                    mensagem = "[!] MP insuficiente para usar esta habilidade!";
                }
            } else if (j.getClasse() == 3) {
                if (j.getMP() < 10) {
                    mensagem = "[!] MP insuficiente para usar esta habilidade!";
                } else if (j.getHP() >= j.getHPMax()) {
                    mensagem = "[!] Não é possível usar Cura Básica pois a vida já está cheia!";
                } else {
                    int qtdCura = 20;
                    danoi = i.atkEspecial(turno) ? i.getATK() * 2 : i.getATK();
                    if (j.getVelocidade() >= i.getVelocidade()) {
                        j.habilidade2(j.getClasse(), i);
                        j.receberDano(danoi);
                        mensagem = j.getNome() + " curou " + qtdCura + " de HP!\n" + j.getNome() + " recebeu " + danoi + " de dano!";
                    } else {
                        j.receberDano(danoi);
                        if (!j.Vivo()) {
                            mensagem = j.getNome() + " recebeu " + danoi + " de dano!";
                            finalizarAcao(false);
                            return;
                        }
                        j.habilidade2(j.getClasse(), i);
                        mensagem = j.getNome() + " recebeu " + danoi + " de dano!\n" + j.getNome() + " curou " + qtdCura + " de HP!";
                    }
                    acao = true;
                }
            }
        } else if (opcao == 3) {
            if (progresso >= 2) {
                if (j.getClasse() == 1) {
                    if (j.getMP() >= 8) {
                        if (hab3G <= 0) {
                            hab3G = 5;
                            j.perdeMP(8);
                            danoi = i.atkEspecial(turno) ? i.getATK() * 2 : i.getATK();
                            j.receberDano(danoi);
                            mensagem = "Grito de Guerra ativado! O ATK foi aumentado em +5 por 3 turnos.\n" + j.getNome() + " recebeu " + danoi + " de dano!";
                            acao = true;
                        } else {
                            mensagem = "[!] Habilidade indisponível no momento!";
                        }
                    } else {
                        mensagem = "[!] MP insuficiente para usar esta habilidade!";
                    }
                } else if (j.getClasse() == 2) {
                    if (j.getMP() >= 8) {
                        valorj = j.getATK();
                        if (j.getVelocidade() >= i.getVelocidade() || hab1L == 1) {
                            j.habilidade3(j.getClasse(), i);
                            if (!i.Vivo()) {
                                mensagem = j.getNome() + " curou " + valorj + " de HP e " + i.getNome() + " recebeu " + valorj + " de dano!";
                                finalizarAcao(false);
                                return;
                            }
                            if (hab1L == 1) {
                                mensagem = j.getNome() + " curou " + valorj + " de HP e " + i.getNome() + " recebeu " + valorj + " de dano!";
                                hab1L--;
                                finalizarAcao(false);
                                return;
                            }
                            danoi = i.atkEspecial(turno) ? i.getATK() * 2 : i.getATK();
                            j.receberDano(danoi);
                            mensagem = j.getNome() + " curou " + valorj + " de HP e " + i.getNome() + " recebeu " + valorj + " de dano!\n" + j.getNome() + " recebeu " + danoi + " de dano!";
                        } else {
                            danoi = i.atkEspecial(turno) ? i.getATK() * 2 : i.getATK();
                            j.receberDano(danoi);
                            if (!j.Vivo()) {
                                mensagem = j.getNome() + " recebeu " + danoi + " de dano!";
                                finalizarAcao(false);
                                return;
                            }
                            j.habilidade3(j.getClasse(), i);
                            mensagem = j.getNome() + " recebeu " + danoi + " de dano!\n" + j.getNome() + " curou " + valorj + " de HP e " + i.getNome() + " recebeu " + valorj + " de dano!";
                        }
                        acao = true;
                    } else {
                        mensagem = "[!] MP insuficiente para usar esta habilidade!";
                    }
                } else if (j.getClasse() == 3) {
                    valorj = (int) (j.getATK() * 0.5);
                    if (j.getVelocidade() >= i.getVelocidade()) {
                        j.habilidade3(j.getClasse(), i);
                        if (!i.Vivo()) {
                            mensagem = j.getNome() + " recuperou 15 de MP e " + i.getNome() + " recebeu " + valorj + " de dano!";
                            finalizarAcao(false);
                            return;
                        }
                        danoi = i.atkEspecial(turno) ? i.getATK() * 2 : i.getATK();
                        j.receberDano(danoi);
                        mensagem = j.getNome() + " recuperou 15 de MP e " + i.getNome() + " recebeu " + valorj + " de dano!\n" + j.getNome() + " recebeu " + danoi + " de dano!";
                    } else {
                        danoi = i.atkEspecial(turno) ? i.getATK() * 2 : i.getATK();
                        j.receberDano(danoi);
                        if (!j.Vivo()) {
                            mensagem = j.getNome() + " recebeu " + danoi + " de dano!";
                            finalizarAcao(false);
                            return;
                        }
                        j.habilidade3(j.getClasse(), i);
                        mensagem = j.getNome() + " recebeu " + danoi + " de dano!\n" + j.getNome() + " recuperou 15 de MP e " + i.getNome() + " recebeu " + valorj + " de dano!";
                    }
                    acao = true;
                }
            } else {
                mensagem = "[!] Habilidade 3 bloqueada! Derrote o Boss 1 para desbloquear.";
            }
        } else if (opcao == 4) {
            if (progresso >= 5) {
                int custoMp = (j.getClasse() == 1) ? 15 : (j.getClasse() == 2 ? 14 : 20);
                if (j.getMP() >= custoMp) {
                    valorj = (j.getClasse() == 1) ? (int) (j.getATK() * 2.5) : j.getATK() * 3;
                    String textoCura = (j.getClasse() == 1) ? "\n" + j.getNome() + " curou " + j.getATK() + " de HP!" : "";

                    if (j.getVelocidade() >= i.getVelocidade() || (j.getClasse() == 2 && hab1L == 1)) {
                        j.habilidade4(j.getClasse(), i);
                        if (!i.Vivo()) {
                            mensagem = i.getNome() + " recebeu " + valorj + " de dano!" + textoCura;
                            finalizarAcao(false);
                            return;
                        }
                        if (j.getClasse() == 2 && hab1L == 1) {
                            mensagem = i.getNome() + " recebeu " + valorj + " de dano!" + textoCura;
                            hab1L--;
                            finalizarAcao(false);
                            return;
                        }
                        danoi = i.atkEspecial(turno) ? i.getATK() * 2 : i.getATK();
                        j.receberDano(danoi);
                        mensagem = i.getNome() + " recebeu " + valorj + " de dano!" + textoCura + "\n" + j.getNome() + " recebeu " + danoi + " de dano!";
                    } else {
                        danoi = i.atkEspecial(turno) ? i.getATK() * 2 : i.getATK();
                        j.receberDano(danoi);
                        if (!j.Vivo()) {
                            mensagem = j.getNome() + " recebeu " + danoi + " de dano!";
                            finalizarAcao(false);
                            return;
                        }
                        j.habilidade4(j.getClasse(), i);
                        mensagem = j.getNome() + " recebeu " + danoi + " de dano!\n" + i.getNome() + " recebeu " + valorj + " de dano!" + textoCura;
                    }
                    acao = true;
                } else {
                    mensagem = "[!] MP insuficiente para usar esta habilidade!";
                }
            } else {
                mensagem = "[!] Habilidade 4 bloqueada! Derrote o Boss 2 para desbloquear.";
            }
        }
        finalizarAcao(acao);
    }

    public void usarItem(int opcao) {
        verificarBuffGuerreiroInicio();
        int danoi;
        boolean acao = false;

        if (opcao == 1) {
            if (!j.verificaPocHP()) {
                mensagem = "[!] Não possui Poções de HP!";
            } else if (j.getHP() >= j.getHPMax()) {
                mensagem = "[!] Não é possível usar a Poção de HP pois a vida já está cheia!";
            } else {
                int curaPocao = 40;
                if (j.getVelocidade() >= i.getVelocidade() || hab1L == 1) {
                    j.usaPocHP();
                    if (hab1L == 1) {
                        mensagem = j.getNome() + " usou Poção de HP e curou " + curaPocao + " de HP!";
                        hab1L--;
                        finalizarAcao(false);
                        return;
                    }
                    danoi = i.atkEspecial(turno) ? i.getATK() * 2 : i.getATK();
                    j.receberDano(danoi);
                    mensagem = j.getNome() + " usou Poção de HP e curou " + curaPocao + " de HP!\n" + j.getNome() + " recebeu " + danoi + " de dano!";
                } else {
                    danoi = i.atkEspecial(turno) ? i.getATK() * 2 : i.getATK();
                    j.receberDano(danoi);
                    if (!j.Vivo()) {
                        mensagem = j.getNome() + " recebeu " + danoi + " de dano!";
                        finalizarAcao(false);
                        return;
                    }
                    j.usaPocHP();
                    mensagem = j.getNome() + " recebeu " + danoi + " de dano!\n" + j.getNome() + " usou Poção de HP e curou " + curaPocao + " de HP!";
                }
                acao = true;
            }
        } else if (opcao == 2) {
            if (!j.verificaPocMP()) {
                mensagem = "[!] Não possui Poções de MP!";
            } else if (j.getMP() >= j.getMPMax()) {
                mensagem = "[!] Não é possível usar a Poção de MP pois a mana já está cheia!";
            } else {
                int mpPocao = 25;
                if (j.getVelocidade() >= i.getVelocidade() || hab1L == 1) {
                    j.usaPocMP();
                    if (hab1L == 1) {
                        mensagem = j.getNome() + " usou Poção de MP e recuperou " + mpPocao + " de MP!";
                        hab1L--;
                        finalizarAcao(false);
                        return;
                    }
                    danoi = i.atkEspecial(turno) ? i.getATK() * 2 : i.getATK();
                    j.receberDano(danoi);
                    mensagem = j.getNome() + " usou Poção de MP e recuperou " + mpPocao + " de MP!\n" + j.getNome() + " recebeu " + danoi + " de dano!";
                } else {
                    danoi = i.atkEspecial(turno) ? i.getATK() * 2 : i.getATK();
                    j.receberDano(danoi);
                    if (!j.Vivo()) {
                        mensagem = j.getNome() + " recebeu " + danoi + " de dano!";
                        finalizarAcao(false);
                        return;
                    }
                    j.usaPocMP();
                    mensagem = j.getNome() + " recebeu " + danoi + " de dano!\n" + j.getNome() + " usou Poção de MP e recuperou " + mpPocao + " de MP!";
                }
                acao = true;
            }
        } else if (opcao == 3) {
            if (j.verificaBomba()) {
                j.usaBomba();
                i.receberDano(25);
                mensagem = j.getNome() + " usou Bomba!\n" + i.getNome() + " recebeu 25 de dano da Bomba!";
                hab1L--;
                acao = true;
            } else {
                mensagem = "[!] Não possui Bombas!";
            }
        }
        finalizarAcao(acao);
    }

    public int getTurno() { return turno; }
    public String getMensagem() { return mensagem; }

    public static String[] getNomesHabilidades(int classe) {
        if (classe == 1) return new String[]{"Golpe Pesado (5 MP)", "Defesa Ferro (6 MP)", "Grito Guerra (8 MP)", "Inv Final (15 MP)"};
        if (classe == 2) return new String[]{"Ataque Duplo (5 MP)", "Golpe Sujo (6 MP)", "Roubo de Vida (8 MP)", "Atq Furtivo (14 MP)"};
        return new String[]{"Bola de Fogo (8 MP)", "Cura Básica (10 MP)", "Dreno Alma (0 MP)", "Meteoro (20 MP)"};
    }

    public static String[] getNomesItens(Jogador j) {
        return new String[]{
            "Poção HP (+40 HP) [" + j.getPocaoHP() + "]",
            "Poção MP (+25 MP) [" + j.getPocaoMP() + "]",
            "Bomba (25 Dano) [" + j.getBomba() + "]"
        };
    }
}
