package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;

public class Register implements ActionListener {
    JFrame frame;
    JLabel lemail, lsenha, lsenhacon;
    JPasswordField pfsenha, pfsenhacon;
    JTextField tfemail;
    JButton bregistrar;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem miVoltar;

    public Register() {
        frame = new JFrame("Cadastro");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500); // Tamanho semelhante ao da tela de login
        frame.setLocationRelativeTo(null);

        // Adicionando o painel e formatando
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(240, 240, 240)); // Cor de fundo do painel
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Borda arredondada
            }
        };
        panel.setPreferredSize(new Dimension(500, 300));
        frame.add(panel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20); // Adicionando espaçamento entre os componentes

        // Componentes do formulário de registro
        lemail = new JLabel("Email");
        tfemail = new JTextField(15);
        lsenha = new JLabel("Senha");
        pfsenha = new JPasswordField(15);
        lsenhacon = new JLabel("Repita a senha");
        pfsenhacon = new JPasswordField(15);
        bregistrar = new JButton("Registrar");

        // Configuração de fontes
        Font fontDefault = new Font("Montserrat", Font.PLAIN, 20);
        Font fontField = new Font("Montserrat", Font.PLAIN, 18);
        Font fontButton = new Font("Montserrat", Font.PLAIN, 14);

        lemail.setFont(fontDefault);
        tfemail.setFont(fontField);
        lsenha.setFont(fontDefault);
        pfsenha.setFont(fontField);
        lsenhacon.setFont(fontDefault);
        pfsenhacon.setFont(fontField);
        bregistrar.setFont(fontButton);

        // Estilizando os campos de texto
        tfemail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        pfsenha.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        pfsenhacon.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Adicionando os componentes ao painel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lemail, gbc);

        gbc.gridx = 1;
        panel.add(tfemail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lsenha, gbc);

        gbc.gridx = 1;
        panel.add(pfsenha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lsenhacon, gbc);

        gbc.gridx = 1;
        panel.add(pfsenhacon, gbc);

        // Botão de registrar
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(bregistrar, gbc);

        // Estilizando o botão
        bregistrar.setBackground(new Color(0, 123, 255)); // Cor de fundo azul
        bregistrar.setForeground(Color.WHITE); // Texto branco
        bregistrar.setFont(new Font("Montserrat", Font.BOLD, 18)); // Fonte do botão
        bregistrar.setPreferredSize(new Dimension(150, 50)); // Tamanho maior
        bregistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Cursor de mão

        // Efeito de hover no botão
        bregistrar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                bregistrar.setBackground(new Color(0, 102, 204)); // Cor mais escura quando passar o mouse
            }

            public void mouseExited(MouseEvent e) {
                bregistrar.setBackground(new Color(0, 123, 255)); // Cor original
            }
        });

        // Criando a barra de menu
        menuBar = new JMenuBar();
        menu = new JMenu("Opções");
        miVoltar = new JMenuItem("Voltar");

        // Adicionando o item "Voltar" ao menu
        menu.add(miVoltar);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        // Associando ActionListener aos botões e itens
        bregistrar.addActionListener(this);
        miVoltar.addActionListener(this);

        // Exibe a janela
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bregistrar) {
            String email = tfemail.getText();
            String senha = new String(pfsenha.getPassword());
            String senhacon = new String(pfsenhacon.getPassword());

            // Validação de email
            if (email.isEmpty() || senha.isEmpty() || senhacon.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Preencha os campos", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (!senha.equals(senhacon)) {
                JOptionPane.showMessageDialog(frame, "As senhas não são iguais.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (!email.matches("^[a-zA-Z0-9._%+-]+@acad\\.ifma\\.edu\\.br$")) {
                JOptionPane.showMessageDialog(frame, "Insira um email válido (domínio @acad.ifma.edu.br).", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                salvarDados(email, senha); // Salva a senha em texto simples
                JOptionPane.showMessageDialog(frame, "Usuário registrado com sucesso!");
                teladelogin(); // Redireciona para a tela de login
            }
        }

        if (e.getSource() == miVoltar) {
            buttonvoltar(); // Redireciona para a tela de login
        }
    }

    //Método para salvar os dados do usuário
    public void salvarDados(String email, String senha) {
        try (FileWriter writer = new FileWriter("usuarios.txt", true)) {
            writer.write(email + ":" + senha + "\n"); // Formato: email:senha
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao salvar os dados.");
        }
    }

    // Método para voltar para a tela de login
    public void teladelogin() {
        frame.dispose();
        new Login();
    }

    // Método para o botão "Voltar" no menu voltar para a tela de login
    public void buttonvoltar() {
        frame.dispose();
        new Login();
    }
}
