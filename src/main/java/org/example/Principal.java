package org.example;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;

public class Principal implements ActionListener {
    Font myFont = new Font("MONOSPACED", Font.PLAIN | Font.BOLD, 18);
    JPanel panelPrincipal, panelForm, panelTable;
    JLabel lMateria, lAtv, lProf, lData;
    JTextField tMateria, tAtv, tProf;
    JFormattedTextField tData;
    JTable tabela;
    JButton bAdd, bRemove, bExportPDF;
    DefaultTableModel model;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem miVoltar;
    JFrame frame;

    public Principal() {
        // Criando o JFrame
        frame = new JFrame("OdA");
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Painéis principais
        panelPrincipal = new JPanel(new BorderLayout());
        panelForm = new JPanel(new GridBagLayout());
        panelTable = new JPanel(new BorderLayout());

        // Layout e constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Espaçamento entre componentes
        gbc.anchor = GridBagConstraints.WEST;

        // Labels e TextFields
        lMateria = new JLabel("Matéria: ");
        lAtv = new JLabel("Atividade: ");
        lProf = new JLabel("Professor: ");
        lData = new JLabel("Data: ");

        tMateria = new JTextField(15);  // Define o número de colunas para controlar o tamanho
        tAtv = new JTextField(15);
        tProf = new JTextField(15);

        // Campo formatado para data (dd/MM/yyyy)
        try {
            MaskFormatter formatoData = new MaskFormatter("##/##/####");
            formatoData.setPlaceholderCharacter('_');
            tData = new JFormattedTextField(formatoData);
            tData.setColumns(15);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        bAdd = new JButton("Adicionar");
        bAdd.addActionListener(this);
        bRemove = new JButton("Remover");
        bRemove.addActionListener(this);

        // Botão para exportar como PDF
        bExportPDF = new JButton("Exportar PDF");
        bExportPDF.addActionListener(this);
        styleButton(bExportPDF);

        // Definir fontes
        lMateria.setFont(myFont);
        lAtv.setFont(myFont);
        lProf.setFont(myFont);
        lData.setFont(myFont);

        tMateria.setFont(myFont);
        tAtv.setFont(myFont);
        tProf.setFont(myFont);
        tData.setFont(myFont);

        // Estilo dos componentes
        tMateria.setBorder(BorderFactory.createLineBorder(new Color(0, 123, 255), 2));
        tAtv.setBorder(BorderFactory.createLineBorder(new Color(0, 123, 255), 2));
        tProf.setBorder(BorderFactory.createLineBorder(new Color(0, 123, 255), 2));
        tData.setBorder(BorderFactory.createLineBorder(new Color(0, 123, 255), 2));

        // Botões
        styleButton(bAdd);
        styleButton(bRemove);

        // Criação da barra de menu e itens
        menuBar = new JMenuBar();
        menu = new JMenu("Opções");
        miVoltar = new JMenuItem("Voltar");
        menu.add(miVoltar);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        // Ação para o item "Voltar"
        miVoltar.addActionListener(this);

        // Adiciona componentes ao painel de formulário
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelForm.add(lMateria, gbc);
        gbc.gridx = 1;
        panelForm.add(tMateria, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelForm.add(lAtv, gbc);
        gbc.gridx = 1;
        panelForm.add(tAtv, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelForm.add(lProf, gbc);
        gbc.gridx = 1;
        panelForm.add(tProf, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelForm.add(lData, gbc);
        gbc.gridx = 1;
        panelForm.add(tData, gbc);

        // Adicionar botões
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        panelForm.add(bAdd, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        panelForm.add(bRemove, gbc);

        gbc.gridx = 3;
        gbc.gridy = 4;
        panelForm.add(bExportPDF, gbc);

        // Tabela
        String[] columnNames = {"Matéria", "Atividade", "Professor", "Data de Entrega", "Status"};
        model = new DefaultTableModel(columnNames, 0);
        tabela = new JTable(model);
        tabela.setFont(new Font("MONOSPACED", Font.PLAIN, 16));
        tabela.setRowHeight(25);

        String[] statusOptions = {"Concluído", "Não Iniciado", "Em Andamento"};
        JComboBox comboBox = new JComboBox(statusOptions);

        TableColumn statusColumn = tabela.getColumnModel().getColumn(4);
        statusColumn.setCellEditor(new DefaultCellEditor(comboBox));

        // Adicionar a tabela com scroll
        panelTable.add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Adicionar os painéis ao frame principal
        panelPrincipal.add(panelForm, BorderLayout.NORTH);
        panelPrincipal.add(panelTable, BorderLayout.CENTER);

        // Adiciona o painel principal ao JFrame
        frame.add(panelPrincipal);
        frame.setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(0, 123, 255));  // Cor azul
        button.setForeground(Color.WHITE);  // Cor branca
        button.setFont(new Font("Montserrat", Font.BOLD, 16));  // Fonte mais legível
        button.setPreferredSize(new Dimension(150, 40));  // Tamanho mais adequado
        button.setFocusPainted(false);  // Remover o contorno ao clicar
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));  // Aumentar o tamanho da borda
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));  // Cursor de mão
        button.setBorderPainted(false);  // Remover borda padrão
        button.setOpaque(true);  // Garantir que o fundo seja sólido
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bAdd) {
            String materia = tMateria.getText();
            String atividade = tAtv.getText();
            String professor = tProf.getText();
            String data = tData.getText();

            if (materia.isEmpty() || atividade.isEmpty() || data.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, preencha os dados!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            model.addRow(new Object[]{materia, atividade, professor, data, "Não Iniciado"});

            // Limpar os campos
            tMateria.setText("");
            tAtv.setText("");
            tProf.setText("");
            tData.setText("");
        }
        if (e.getSource() == bRemove) {
            int selectedRow = tabela.getSelectedRow();
            if (selectedRow != -1) {
                model.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(null, "Selecione uma linha", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == miVoltar) {
            buttonvoltar();
        }
        if (e.getSource() == bExportPDF) {
            exportarPDF();
        }
    }

    private void buttonvoltar() {
        // Ação para o item Voltar
        int option = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja sair?", "Confirmar saída", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            frame.dispose(); // Fecha o JFrame
        }
    }
    public void exportarPDF() {
        try {
            // Verificar se a tabela não está vazia
            if (tabela.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "A tabela está vazia! Adicione dados antes de exportar.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Abrir o JFileChooser para o usuário escolher o local de salvamento
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salvar PDF");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            // Configurar filtro para salvar como PDF
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivo PDF", "pdf"));

            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();

                // Garantir que o arquivo tenha a extensão ".pdf"
                if (!filePath.endsWith(".pdf")) {
                    filePath += ".pdf";
                }

                // Criar o documento PDF
                Document documento = new Document();
                PdfWriter.getInstance(documento, new FileOutputStream(filePath));
                documento.open();

                PdfPTable pdfTabela = new PdfPTable(tabela.getColumnCount());

                // Adicionar cabeçalhos da tabela
                for (int i = 0; i < tabela.getColumnCount(); i++) {
                    pdfTabela.addCell(new PdfPCell(new com.itextpdf.text.Paragraph(tabela.getColumnName(i))));
                }

                // Adicionar as linhas da tabela ao PDF
                for (int i = 0; i < tabela.getRowCount(); i++) {
                    for (int j = 0; j < tabela.getColumnCount(); j++) {
                        pdfTabela.addCell(new PdfPCell(new com.itextpdf.text.Paragraph(tabela.getValueAt(i, j).toString())));
                    }
                }

                // Adicionar a tabela no documento PDF
                documento.add(pdfTabela);

                // Fechar o documento
                documento.close();

                // Exibir mensagem de sucesso
                JOptionPane.showMessageDialog(null, "PDF gerado com sucesso e salvo em: " + filePath, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar PDF: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


}