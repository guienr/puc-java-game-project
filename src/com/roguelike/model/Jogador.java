package com.roguelike.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jogador extends Base {
    protected int classe;
    public int pocaohp = 0;
    public int pocaomp = 0;
    public int bomba = 0;

    private Random random = new Random();
    private List<String> powerUpsAdquiridos = new ArrayList<>();
    private int bossesDerrotados = 0;

    public Jogador(String nome, int hp, int mp, int atk, int spd, int classe) {
        super(nome, hp, mp, atk, spd);
        this.classe = classe;
    }

    public void habilidade1(int classe, Inimigo i) {
        switch (classe) {
            case 1:
                i.receberDano((int) (this.getATK() * 1.5));
                this.perdeMP(5);
                break;
            case 3:
                i.receberDano(this.getATK() * 2);
                this.perdeMP(8);
                break;
        }
    }

    public void habilidade2(int classe, Inimigo i) {
        switch (classe) {
            case 2:
                i.receberDano((int) (this.getATK() * 1.5));
                this.perdeMP(6);
                break;
            case 3:
                this.curar(20);
                this.perdeMP(10);
                break;
        }
    }

    public void habilidade3(int classe, Inimigo i) {
        switch (classe) {
            case 2:
                i.receberDano(this.getATK());
                this.curar(this.getATK());
                this.perdeMP(8);
                break;
            case 3:
                i.receberDano((int) (this.getATK() * 0.5));
                this.recuperarMP(15);
                break;
        }
    }

    public void habilidade4(int classe, Inimigo i) {
        switch (classe) {
            case 1:
                i.receberDano((int) (this.getATK() * 2.5));
                this.curar(this.getATK());
                this.perdeMP(15);
                break;
            case 2:
                i.receberDano(this.getATK() * 3);
                this.perdeMP(14);
                break;
            case 3:
                i.receberDano(this.getATK() * 3);
                this.perdeMP(20);
                break;
        }
    }

    public void ganhaPocHP() { this.pocaohp++; }
    public void ganhaPocMP() { this.pocaomp++; }
    public void ganhaBomba() { this.bomba++; }

    public String ganharItemAleatorio() {
        int sorteio = random.nextInt(3);
        if (sorteio == 0) {
            ganhaPocHP();
            return "Poção de HP";
        } else if (sorteio == 1) {
            ganhaPocMP();
            return "Poção de MP";
        } else {
            ganhaBomba();
            return "Bomba";
        }
    }

    public boolean verificaPocHP() { return this.pocaohp > 0; }
    public void usaPocHP() { this.curar(40); this.pocaohp--; }

    public boolean verificaPocMP() { return this.pocaomp > 0; }
    public void usaPocMP() { this.recuperarMP(25); this.pocaomp--; }

    public boolean verificaBomba() { return this.bomba > 0; }
    public void usaBomba() { this.bomba--; }

    public int getClasse() { return classe; }
    public int getPocaoHP() { return pocaohp; }
    public int getPocaoMP() { return pocaomp; }
    public int getBomba() { return bomba; }

    public boolean jaPossuiPowerUp(String id) {
        return powerUpsAdquiridos.contains(id);
    }

    public void registrarPowerUp(String id) {
        powerUpsAdquiridos.add(id);
    }

    public int getBossesDerrotados() {
        return bossesDerrotados;
    }

    public void incrementarBossesDerrotados() {
        this.bossesDerrotados++;
    }
}