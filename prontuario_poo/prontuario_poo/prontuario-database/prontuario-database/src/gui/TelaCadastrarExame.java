package gui;

import java.awt.BorderLayout;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.util.List;
import service.ExameService;
import model.Exame;
import model.Paciente;

public class TelaCadastrarExame extends JDialog {

    private static final long serialVersionUID = 1L;
    
    private ExameService exameService;
    private TelaPrincipal main;
    private JPanel painelForm;
    private JPanel painelBotoes;
    private JButton btnSalvar;
    private JButton btnLimpar;
    private JButton btnSair;
    private JLabel lblDescricao;
    private JLabel lblDataExame;
    private JLabel lblPaciente;
    private JTextField txfDescricao;
    private JTextField txfDataExame;
    private JComboBox<Paciente> cbxPaciente;
    
    public TelaCadastrarExame(ExameService exameService, TelaPrincipal main, List<Paciente> pacientes) {
        this.exameService = exameService;
        this.main = main;
        
        setSize(360, 250);
        setResizable(false);
        setTitle("Cadastrar Exame");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        painelForm = new JPanel();
        lblDescricao = new JLabel("Descrição: ");
        lblDataExame = new JLabel("Data do Exame: ");
        lblPaciente = new JLabel("Paciente: ");
        
        txfDescricao = new JTextField(24);
        txfDataExame = new JTextField(24);
        
        cbxPaciente = new JComboBox<>();
        for (Paciente p : pacientes) {
            cbxPaciente.addItem(p);
        }

        painelForm.add(lblDescricao);
        painelForm.add(txfDescricao);
        painelForm.add(lblDataExame);
        painelForm.add(txfDataExame);
        painelForm.add(lblPaciente);
        painelForm.add(cbxPaciente);
        
        add(painelForm, BorderLayout.CENTER);
        
        painelBotoes = new JPanel();
        btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> fecharTela());
        btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> limparCampos());
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> addExame());
        
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnSair);
        add(painelBotoes, BorderLayout.SOUTH);
        
        setModal(true);
        setVisible(true);
    }
    
    private void fecharTela() {
        this.dispose();
    }
    
    private void limparCampos() {
        txfDescricao.setText("");
        txfDataExame.setText("");
        cbxPaciente.setSelectedIndex(0);
    }
    
    private void addExame() {
        Paciente pacienteSelecionado = (Paciente) cbxPaciente.getSelectedItem();
        if (pacienteSelecionado == null) {
            JOptionPane.showMessageDialog(null, "Selecione um paciente!");
            return;
        }
        LocalDate dataExame = null;
        try {
            dataExame = LocalDate.parse(txfDataExame.getText());
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Data inválida! Use o formato yyyy-MM-dd.");
            return;
        }
        Exame exame = new Exame(0L, txfDescricao.getText(), dataExame, pacienteSelecionado);
        exameService.adicionarExame(exame);
        JOptionPane.showMessageDialog(null, "Exame cadastrado com sucesso!");
        limparCampos();
        main.loadTableExames();
        fecharTela();
    }

}
