package br.ufscar.dc.dsw.util;

import java.util.ArrayList;
import java.util.List;

// Classe utilitária para acumular mensagens de erro durante a validação de dados
public class Erro {
    private List<String> erros = new ArrayList<>();

    public void add(String mensagem) {
        erros.add(mensagem);
    }

    public boolean isExisteErros() {
        return !erros.isEmpty();
    }

    public List<String> getErros() {
        return erros;
    }
}
