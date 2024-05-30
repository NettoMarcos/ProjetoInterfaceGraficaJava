import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProdutoView extends JFrame {
    private ProdutoController controller; // Referência ao controlador
    private ProdutoTableModel tableModel; // Modelo da tabela de produtos
    private JTable produtoTable; // Tabela de produtos

    private JButton adicionarButton; // Botão para adicionar produto
    private JButton editarButton; // Botão para editar produto
    private JButton excluirButton; // Botão para excluir produto

    public ProdutoView(ProdutoController controller) {
        this.controller = controller;
        setTitle("Cadastro de Produtos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        // Inicializa o modelo da tabela com a lista atual de produtos
        tableModel = new ProdutoTableModel(controller.listarProdutos());
        // Cria a tabela com base no modelo
        produtoTable = new JTable(tableModel);

        // Adiciona um ouvinte de seleção à tabela para habilitar/desabilitar botões de edição e exclusão
        produtoTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && produtoTable.getSelectedRow() != -1) {
                editarButton.setEnabled(true);
                excluirButton.setEnabled(true);
            } else {
                editarButton.setEnabled(false);
                excluirButton.setEnabled(false);
            }
        });

        // Cria botões de ação para adicionar, editar e excluir produtos
        adicionarButton = new JButton("Adicionar");
        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarProduto();
            }
        });

        editarButton = new JButton("Editar");
        editarButton.setEnabled(false);
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarProduto();
            }
        });

        excluirButton = new JButton("Excluir");
        excluirButton.setEnabled(false);
        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirProduto();
            }
        });

        // Painel para agrupar os botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(adicionarButton);
        buttonPanel.add(editarButton);
        buttonPanel.add(excluirButton);

        // Define o layout da janela principal
        setLayout(new BorderLayout());
        // Adiciona a tabela ao centro e os botões ao sul da janela
        add(new JScrollPane(produtoTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void adicionarProduto() {
        // Cria um novo produto com valores padrão
        Produto produto = new Produto(0, "Novo Produto", 0.0, 0);
        // Abre uma caixa de diálogo para adicionar/editar os detalhes do produto
        ProdutoEditDialog dialog = new ProdutoEditDialog(this, produto);
        dialog.setVisible(true);

        // Se a adição for confirmada na caixa de diálogo, adiciona o produto ao controlador e atualiza a tabela
        if (dialog.isConfirmed()) {
            produto = dialog.getProduto();
            controller.adicionarProduto(produto);
            atualizarTabela();
        }
    }

    private void editarProduto() {
        // Obtém o índice da linha selecionada na tabela
        int selectedRow = produtoTable.getSelectedRow();
        // Obtém o produto correspondente ao índice selecionado
        Produto produto = tableModel.getProdutoAt(selectedRow);
        // Abre uma caixa de diálogo para adicionar/editar os detalhes do produto
        ProdutoEditDialog dialog = new ProdutoEditDialog(this, produto);
        dialog.setVisible(true);

        // Se a edição for confirmada na caixa de diálogo, atualiza o produto no controlador e na tabela
        if (dialog.isConfirmed()) {
            produto = dialog.getProduto();
            controller.atualizarProduto(produto.getId(), produto);
            atualizarTabela();
        }
    }

    private void excluirProduto() {
        // Obtém o índice da linha selecionada na tabela
        int selectedRow = produtoTable.getSelectedRow();
        // Verifica se uma linha está selecionada
        if (selectedRow != -1) {
            // Exibe uma caixa de diálogo de confirmação antes de excluir o produto
            int option = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este produto?", "Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                // Obtém o produto correspondente ao índice selecionado
                Produto produto = tableModel.getProdutoAt(selectedRow);
                // Remove o produto do controlador e atualiza a tabela
                controller.excluirProduto(produto.getId());
                atualizarTabela();
            }
        }
    }

    private void atualizarTabela() {
        // Obtém a lista atualizada de produtos do controlador e atualiza o modelo da tabela
        List<Produto> produtos = controller.listarProdutos();
        tableModel.setProdutos(produtos);
    }

    public static void main(String[] args) {
        // Cria uma instância do controlador e da view e exibe a janela
        ProdutoController controller = new ProdutoController();
        ProdutoView view = new ProdutoView(controller);
        view.setVisible(true);
    }
}
