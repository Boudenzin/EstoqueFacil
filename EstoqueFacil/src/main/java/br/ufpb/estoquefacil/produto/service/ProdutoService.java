package br.ufpb.estoquefacil.produto.service;

import br.ufpb.estoquefacil.produto.model.Produto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProdutoService {


    private static final Logger logger = LogManager.getLogger(ProdutoService.class);

    private final List<Produto> produtos = new ArrayList<>();

    public void salvar(Produto produto) {
        try {
            logger.info("Tentando cadastrar produto: {} (Marca: {})", produto.getNome(), produto.getMarca());

            produtos.add(produto);

            logger.info("Produto cadastrado com sucesso! ID gerado: {}", produto.getId());
        } catch (IllegalArgumentException e) {
            logger.warn("Falha ao cadastrar produto: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Erro crítico ao salvar produto", e);
            throw e;
        }
    }

    public List<Produto> listarAtivos() {
        logger.debug("Listando todos os produtos ativos. Total em memória: {}", produtos.size());
        return produtos.stream()
                .filter(Produto::isAtivo)
                .collect(Collectors.toList());
    }

    public Optional<Produto> buscarPorId(int id) {
        logger.debug("Buscando produto com ID: {}", id);
        return produtos.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    public void desativarProduto(int id) {
        buscarPorId(id).ifPresentOrElse(
                p -> {
                    p.setAtivo(false);
                    logger.info("Produto ID {} foi desativado (Soft Delete).", id);
                },
                () -> logger.warn("Tentativa de desativar produto inexistente. ID: {}", id)
        );
    }
}
