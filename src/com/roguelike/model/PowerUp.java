package com.roguelike.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class PowerUp {
    private String id;
    private String nome;
    private String descricao;
    private Consumer<Jogador> efeito;

    public PowerUp(String id, String nome, String descricao, Consumer<Jogador> efeito) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.efeito = efeito;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public void aplicar(Jogador j) { efeito.accept(j); }

    public static List<PowerUp> sortearOpcoes(Jogador j) {
        List<PowerUp> pool = new ArrayList<>();

        // === 6 POWER-UPS COMUNS (Disponíveis para todas as classes) ===
        pool.add(new PowerUp("C1", "Vitalidade Aprimorada", "+25 HP Máx e Cura 25 HP", p -> {
            p.melhoraHPM(25);
            p.curar(25);
        }));
        pool.add(new PowerUp("C2", "Concentração Clara", "+20 MP Máx e +20 MP", p -> {
            p.melhoraMPM(20);
            p.recuperarMP(20);
        }));
        pool.add(new PowerUp("C3", "Treinamento de Força", "+4 de ATK Permanente", p -> {
            p.melhoraATK(4);
        }));
        pool.add(new PowerUp("C4", "Agilidade nos Pés", "+4 de Velocidade (SPD)", p -> {
            p.melhoraSPD(4);
        }));
        pool.add(new PowerUp("C5", "Bolsa de Sobrevivência", "+1 Poção HP, +1 MP e +1 Bomba", p -> {
            p.ganhaPocHP();
            p.ganhaPocMP();
            p.ganhaBomba();
        }));
        pool.add(new PowerUp("C6", "Resiliência", "+15 HP Máx, +10 MP Máx e +2 ATK", p -> {
            p.melhoraHPM(15);
            p.curar(15);
            p.melhoraMPM(10);
            p.recuperarMP(10);
            p.melhoraATK(2);
        }));

        // === 3 POWER-UPS ESPECIAIS POR CLASSE (Levemente Apelões) ===
        if (j.getClasse() == 1) { // Guerreiro
            pool.add(new PowerUp("G1", "Titã de Aço", "+50 HP Máx, +4 ATK e Cura Total", p -> {
                p.melhoraHPM(50);
                p.melhoraATK(4);
                p.curar(9999);
            }));
            pool.add(new PowerUp("G2", "Sangue Fervendo", "+6 ATK e +5 Velocidade", p -> {
                p.melhoraATK(6);
                p.melhoraSPD(5);
            }));
            pool.add(new PowerUp("G3", "Investida Brutal", "+14 ATK e -8 MP Máx", p -> {
                p.melhoraATK(14);
                p.melhoraMPM(-8);
                p.perdeMP(8);
            }));
        } else if (j.getClasse() == 2) { // Ladino
            pool.add(new PowerUp("L1", "Reflexos Sobrenaturais", "+8 Velocidade e +3 ATK", p -> {
                p.melhoraSPD(8);
                p.melhoraATK(3);
            }));
            pool.add(new PowerUp("L2", "Assassino Letal", "+7 ATK e +20 MP Máx", p -> {
                p.melhoraATK(7);
                p.melhoraMPM(20);
                p.recuperarMP(20);
            }));
            pool.add(new PowerUp("L3", "Passos do Fantasma", "+5 Velocidade e +35 HP Máx", p -> {
                p.melhoraSPD(5);
                p.melhoraHPM(35);
                p.curar(35);
            }));
        } else { // Mago (Classe 3)
            pool.add(new PowerUp("M1", "Foco Arcano Absoluto", "+45 MP Máx e Restaura Todo MP", p -> {
                p.melhoraMPM(45);
                p.recuperarMP(9999);
            }));
            pool.add(new PowerUp("M2", "Sobrecarga Elemental", "+8 ATK e -15 HP Máx", p -> {
                p.melhoraATK(8);
                p.melhoraHPM(-15);
                if (p.getHP() > p.getHPMax()) {
                    p.receberDano(p.getHP() - p.getHPMax());
                }
            }));
            pool.add(new PowerUp("M3", "Bênção Mana-Vida", "+30 HP Máx, +30 MP Máx e +3 ATK", p -> {
                p.melhoraHPM(30);
                p.curar(30);
                p.melhoraMPM(30);
                p.recuperarMP(30);
                p.melhoraATK(3);
            }));
        }

        // Remove habilidades que o jogador já escolheu em bosses anteriores
        pool.removeIf(pu -> j.jaPossuiPowerUp(pu.getId()));

        // Embaralha e seleciona 3 opções aleatórias dentre as 9
        Collections.shuffle(pool);
        return pool.subList(0, Math.min(3, pool.size()));
    }
}