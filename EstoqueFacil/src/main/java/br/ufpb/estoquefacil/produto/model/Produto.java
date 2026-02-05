package br.ufpb.estoquefacil.produto.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Produto {
    private static int contadorId = 1;

    private final int id;
    private String nome;
    private String marca;
    private String categoria;
    private BigDecimal precoCusto;
    private BigDecimal precoVenda;
    private String caminhoFoto;
    private boolean ativo;

    public Produto(String nome, String marca, String categoria, BigDecimal precoCusto, BigDecimal precoVenda, String caminhoFoto) {

        validarNome(nome);
        validarPrecos(precoCusto, precoVenda);

        this.id = contadorId++;
        this.nome = nome;
        this.marca = (marca == null || marca.isBlank()) ? "Sem Marca" : marca;
        this.categoria = (categoria == null || categoria.isBlank()) ? "Geral" : categoria;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
        this.caminhoFoto = (caminhoFoto != null) ? caminhoFoto : "default.png";
        this.ativo = true;
    }


    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }
        if (nome.length() < 3) {
            throw new IllegalArgumentException("O nome do produto deve ter pelo menos 3 caracteres.");
        }
    }

    private void validarPrecos(BigDecimal custo, BigDecimal venda) {
        if (custo == null || venda == null) {
            throw new IllegalArgumentException("Os preços não podem ser nulos.");
        }
        if (custo.compareTo(BigDecimal.ZERO) < 0 || venda.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Os preços não podem ser negativos.");
        }
        if (venda.compareTo(custo) < 0) {
            throw new IllegalArgumentException("O preço de venda não pode ser menor que o preço de custo.");
        }
    }



    public int getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) {
        validarNome(nome);
        this.nome = nome;
    }

    public BigDecimal getPrecoCusto() { return precoCusto; }
    public void setPrecoCusto(BigDecimal precoCusto) {
        validarPrecos(precoCusto, this.precoVenda);
        this.precoCusto = precoCusto;
    }

    public BigDecimal getPrecoVenda() { return precoVenda; }
    public void setPrecoVenda(BigDecimal precoVenda) {
        validarPrecos(this.precoCusto, precoVenda);
        this.precoVenda = precoVenda;
    }

    public String getMarca() { return marca; }
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public boolean isAtivo() { return ativo; }

    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public BigDecimal getMargemLucro() {
        return this.precoVenda.subtract(this.precoCusto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return id == produto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}