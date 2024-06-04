import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProdutoEditDialog extends JDialog {
    private JTextField nomeField;
    private JTextField precoField;
    private JTextField quantidadeField;

    private JButton salvarButton;
    private JButton cancelarButton;

    private boolean confirmed = false;
    private Produto produto;

    public ProdutoEditDialog(Frame parent, Produto produto) {
        super(parent, "Produto", true);
        this.produto = produto;

        initComponents();
        setupFields();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        nomeField = new JTextField(20);
        precoField = new JTextField(20);
        quantidadeField = new JTextField(20);

        salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarProduto();
            }
        });

        cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarEdicao();
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Nome:"));
        inputPanel.add(nomeField);
        inputPanel.add(new JLabel("Preço:"));
        inputPanel.add(precoField);
        inputPanel.add(new JLabel("Quantidade:"));
        inputPanel.add(quantidadeField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(salvarButton);
        buttonPanel.add(cancelarButton);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupFields() {
        nomeField.setText(produto.getNome());
        precoField.setText(String.valueOf(produto.getPreco()));
        quantidadeField.setText(String.valueOf(produto.getQuantidade()));
    }

    private void salvarProduto() {
        String nome = nomeField.getText();
        double preco;
        int quantidade;
        try {
            preco = Double.parseDouble(precoField.getText());
            quantidade = Integer.parseInt(quantidadeField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço e quantidade devem ser números válidos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setQuantidade(quantidade);

        confirmed = true;
        dispose();
    }

    private void cancelarEdicao() {
        dispose();
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Produto getProduto() {
        return produto;
    }
}
