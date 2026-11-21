package com.roguelike.model;

public class Inimigo extends Base {
    protected int boss;

    public Inimigo(String nome, int hp, int atk, int spd, int classificacao) {
        super(nome, hp, atk, spd);
        this.boss = classificacao;
    }

    public boolean atkEspecial(int turno) {
        return this.boss == 1 && turno % 3 == 0;
    }
    public boolean isBoss() {
        return this.boss == 1;
    }
}