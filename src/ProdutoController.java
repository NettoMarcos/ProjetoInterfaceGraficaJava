import java.util.ArrayList;
import java.util.List;

public class ProdutoController {
    private List<Produto> produtos; // Lista de produtos
    private int proximoId; // Próximo ID disponível

    public ProdutoController() {
        this.produtos = new ArrayList<>(); // Inicializa a lista de produtos vazia
        this.proximoId = 1; // Inicializa o próximo ID como 1
    }

    // Método para adicionar um novo produto à lista
    public void adicionarProduto(Produto produto) {
        produto.setId(proximoId++); // Define o ID do produto como o próximo ID disponível e incrementa o próximo ID
        produtos.add(produto);
    }

    // Método para atualizar um produto na lista pelo ID
    public void atualizarProduto(int id, Produto novoProduto) {
        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);
            if (produto.getId() == id) { // Se encontrar o produto pelo ID
                produtos.set(i, novoProduto); // Atualiza o produto na lista
                break;
            }
        }
    }

    // Método para excluir um produto da lista pelo ID
    public void excluirProduto(int id) {
        produtos.removeIf(produto -> produto.getId() == id);
    }

    // Método para listar todos os produtos
    public List<Produto> listarProdutos() {
        return produtos;
    }
}