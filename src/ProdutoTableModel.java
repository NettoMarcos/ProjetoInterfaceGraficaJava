import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProdutoTableModel extends AbstractTableModel {
    private List<Produto> produtos; // Lista de produtos exibidos na tabela
    private final String[] colunas = {"ID", "Nome", "Preço", "Quantidade"}; // Nomes das colunas da tabela

    // Construtor da classe ProdutoTableModel
    public ProdutoTableModel(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public int getRowCount() {
        return produtos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Produto produto = produtos.get(rowIndex); // Obtém o produto na linha especificada
        switch (columnIndex) {
            case 0:
                return produto.getId();
            case 1:
                return produto.getNome();
            case 2:
                return produto.getPreco();
            case 3:
                return produto.getQuantidade();
            default:
                return null;
        }
    }

    // Método para definir a lista de produtos exibidos na tabela
    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
        fireTableDataChanged(); // Notifica a tabela que os dados foram alterados
    }

    // Métodos para adicionar, atualizar e remover produtos na tabela
    public void addProduto(Produto produto) {
        produtos.add(produto);
        fireTableRowsInserted(produtos.size() - 1, produtos.size() - 1);
    }

    public void updateProduto(int rowIndex, Produto produto) {
        produtos.set(rowIndex, produto);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void removeProduto(int rowIndex) {
        produtos.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    // Método para obter o produto em uma determinada linha da tabela
    public Produto getProdutoAt(int rowIndex) {
        return produtos.get(rowIndex);
    }
}
