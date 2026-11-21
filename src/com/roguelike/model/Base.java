package com.roguelike.model;

public abstract class Base {
    protected String nome;
    protected int hp;
    protected int hpmax;
    protected int mp;
    protected int mpmax;
    protected int atk;
    protected int spd;

    public Base(String nome, int hp, int mp, int atk, int spd) {
        this.nome = nome;
        this.hp = hp;
        this.hpmax = hp;
        this.mp = mp;
        this.mpmax = mp;
        this.atk = atk;
        this.spd = spd;
    }

    public Base(String nome, int hp, int atk, int spd) {
        this(nome, hp, 0, atk, spd);
    }

    public boolean Vivo() { return this.hp > 0; }
    public void receberDano(int dano) { this.hp = Math.max(0, this.hp - dano); }
    public void curar(int valor) { this.hp = Math.min(this.hpmax, this.hp + valor); }
    public void recuperarMP(int valor) { this.mp = Math.min(this.mpmax, this.mp + valor); }
    public void perdeMP(int valor) { this.mp = Math.max(0, this.mp - valor); }

    public void melhoraHPM(int valor) { this.hpmax += valor; }
    public void melhoraMPM(int valor) { this.mpmax += valor; }
    public void melhoraATK(int valor) { this.atk += valor; }
    public void melhoraSPD(int valor) { this.spd += valor; }

    public String getNome() { return nome; }
    public int getHP() { return hp; }
    public int getHPMax() { return hpmax; }
    public int getMP() { return mp; }
    public int getMPMax() { return mpmax; }
    public int getATK() { return atk; }
    public int getVelocidade() { return spd; }
}
