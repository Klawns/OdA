package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Login implements ActionListener {
    JFrame frame;
    JLabel llogin, lsenha, lemail;
    JPasswordField pfsenha;
    JTextField temail;
    JButton enterbut, bregister;

    public Login() {
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);

        // Adicionando o painel e formatando
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(240, 240, 240)); // Cor de fundo do painel
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);  // Borda arredondada
            }
        };
        panel.setPreferredSize(new Dimension(500, 300));
        frame.add(panel, BorderLayout.CENTER);
        GridBagConstraints gbc = new GridBagConstraints();

        // Criação dos componentes
        llogin = new JLabel("Login");
        lemail = new JLabel("Email");
        temail = new JTextField(15);
        lsenha = new JLabel("Senha");
        pfsenha = new JPasswordField(15);
        enterbut = new JButton("Entrar");
        bregister = new JButton("Se registre");

        // Configuração da fonte para os componentes
        Font font = new Font("Montserrat", Font.BOLD | Font.PLAIN, 35);
        Font font1 = new Font("Montserrat", Font.PLAIN, 20);
        Font font2 = new Font("Source Sans Pro", Font.PLAIN, 20);
        Font font3 = new Font("Montserrat", Font.BOLD | Font.PLAIN, 15);

        llogin.setFont(font);
        lemail.setFont(font1);
        temail.setFont(font2);
        lsenha.setFont(font1);
        pfsenha.setFont(font2);
        enterbut.setFont(font3);
        bregister.setFont(font3);

        // Configuração e adição dos componentes ao painel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(llogin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(lemail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.CENTER;
        panel.add(temail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(lsenha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4; // Alterado para 4 para alinhar com a senha
        gbc.fill = GridBagConstraints.CENTER;
        panel.add(pfsenha, gbc); // Usei JPasswordField para senha

        // Arredondando os campos de texto e senha
        temail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        pfsenha.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Ajuste para os botões ficarem lado a lado
        JPanel buttonPanel = new JPanel();  // Um painel adicional para os botões
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0)); // Fluxo horizontal com espaçamento
        buttonPanel.setBackground(new Color(240, 240, 240)); // Fundo claro para o painel dos botões

        // Configuração do botão "Entrar"
        enterbut.setBackground(new Color(0, 123, 255));  // Cor de fundo azul
        enterbut.setForeground(Color.WHITE);  // Cor da fonte em contraste
        enterbut.setFont(new Font("Montserrat", Font.BOLD, 18)); // Maior e em negrito
        enterbut.setPreferredSize(new Dimension(150, 50));  // Tamanho maior
        enterbut.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        enterbut.addActionListener(this);

        // Configuração do botão "Se registre"
        bregister.setOpaque(true);
        bregister.setContentAreaFilled(true);
        bregister.setBorderPainted(true);
        bregister.setBackground(Color.WHITE);  // Cor branca
        bregister.setFont(new Font("Montserrat", Font.PLAIN, 14));
        bregister.setForeground(new Color(0, 123, 255)); // Cor azul
        //bregister.setText("<html><u>Se registre</u></html>");  // Texto sublinhado
        bregister.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        bregister.addActionListener(this);

        // Adicionando os botões ao painel de botões
        buttonPanel.add(enterbut);
        buttonPanel.add(bregister);

        // Adicionando o painel de botões ao painel principal
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;  // Faz os botões ocuparem duas colunas
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonPanel, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enterbut) {  // Verificando o botão de login
            String email = temail.getText();
            String senha = new String(pfsenha.getPassword());

            if (verificarCredenciais(email, senha)) {
                JOptionPane.showMessageDialog(frame, "Login bem-sucedido!");
                telaprin();  // Só chama a tela principal se o login for bem-sucedido
            } else {
                JOptionPane.showMessageDialog(frame, "Email ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == bregister) {
            frame.dispose();
            new Register();
        }
    }

    // Método para verificar as credenciais no arquivo usuarios.txt
    public boolean verificarCredenciais(String email, String senha) {
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(":"); // Divide a linha em email e senha
                if (dados.length == 2) {
                    String emailArquivo = dados[0];
                    String senhaArquivo = dados[1];

                    // Verifica se o email e a senha correspondem
                    if (emailArquivo.equals(email) && senhaArquivo.equals(senha)) {
                        return true; // Credenciais corretas
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao ler o arquivo de usuários.");
        }
        return false; // Se não encontrou credenciais correspondentes
    }

    public void telaprin() {
        frame.dispose();
        new Principal();
    }
}