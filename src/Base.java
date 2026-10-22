public class Base
{
    protected String nome;
    protected int hp;
    protected int hpmax;
    protected int mp;
    protected int mpmax;
    protected int atk;
    protected int spd;

    public Base(String nome, int hp, int mp, int atk, int spd)
    {
        this.nome = nome;
        this.hp = hp;
        this.hpmax = hp;
        this.mp = mp;
        this.mpmax = mp;
        this.atk = atk;
        this.spd = spd;
    }

    public Base(String nome, int hp, int atk, int spd)
    {
        this.nome = nome;
        this.hp = hp;
        this.hpmax = hp;
        this.mp = 0;
        this.mpmax = 0;
        this.atk = atk;
        this.spd = spd;
    }

    public int Vivo()
    {
        if(this.hp > 0)
        {
            return 1;
        }
        return 0;
    }

    public void receberDano(int dano) 
    {
        this.hp -= dano;
        if(this.hp <= 0)
        {
            this.hp = 0;
        }
    }

    public void curar(int valor)
    {
        this.hp += valor;
        if(this.hp > this.hpmax)
        {
            this.hp = this.hpmax;
        }
    }

    public void recuperarMP(int valor)
    {
        this.mp += valor;
        if(this.mp > this.mpmax)
        {
            this.mp = this.mpmax;
        }
    }

    public void perdeMP(int valor)
    {
        this.mp -= valor;
        if(this.mp < 0)
        {
            this.mp = 0;
        }
    }

    public void melhoraHPM(int valor)
    {
        this.hpmax += valor;
    }

    public void melhoraMPM(int valor)
    {
        this.mpmax += valor;
    }

    public void melhoraATK(int valor)
    {
        this.atk += valor;
    }

    public void melhoraSPD(int valor)
    {
        this.spd += valor;
    }

    public String getNome()
    { 
        return nome; 
    }

    public int getHP()
    {
        return hp;
    }

    public int getHPMax()
    {
        return hpmax;
    }

    public int getMP()
    {
        return mp;
    }

    public int getMPMax()
    {
        return mpmax;
    }

    public int getATK()
    {
        return atk;
    }

    public int getVelocidade() 
    { 
        return spd; 
    }
}