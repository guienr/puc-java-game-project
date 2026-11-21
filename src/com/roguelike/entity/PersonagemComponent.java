package com.roguelike.entity;

import com.almasb.fxgl.entity.component.Component;
import com.roguelike.model.Base;
import com.roguelike.model.Inimigo;
import com.roguelike.model.Jogador;

public class PersonagemComponent extends Component {
    private Base personagem;

    public PersonagemComponent(Base personagem) {
        this.personagem = personagem;
    }

    public Jogador getJogador() { return (Jogador) personagem; }
    public Inimigo getInimigo() { return (Inimigo) personagem; }
}