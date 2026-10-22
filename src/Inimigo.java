public class Inimigo extends Base
{
    protected int boss;

    public Inimigo(String nome, int hp, int atk, int spd, int classificação)
    {
        super(nome, hp, atk, spd);
        this.boss = classificação;
    }

    public int atkEspecial(int turno)
    {
        if(this.boss == 1 && turno % 3 == 0)
        {
            return 1;
        }
        return 0;
    }
    
}