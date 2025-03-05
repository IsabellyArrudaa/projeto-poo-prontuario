package gui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Paciente;
import service.PacienteService;

public class TelaAtualizarPaciente extends JDialog {

    private static final long serialVersionUID = 1L;

    private PacienteService pacService;
    private TelaPrincipal main;
    private JPanel painelForm;
    private JPanel painelBotoes;
    private JButton btnSalvar;
    private JButton btnLimpar;
    private JButton btnSair;
    private JLabel lblNome;
    private JLabel lblCpf;
    private JTextField txfNome;
    private JTextField txfCpf;

    public TelaAtualizarPaciente(PacienteService pacService, TelaPrincipal main) {
        this.pacService = pacService;
        this.main = main;
        setSize(360, 200);
        setResizable(false);
        setTitle("Atualizar Paciente");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        painelForm = new JPanel();
        lblNome = new JLabel("Nome: ");
        lblCpf = new JLabel("CPF: ");
        txfNome = new JTextField(24);
        txfCpf = new JTextField(24);

        painelForm.add(lblNome);
        painelForm.add(txfNome);
        painelForm.add(lblCpf);
        painelForm.add(txfCpf);
        add(painelForm, BorderLayout.CENTER);

        painelBotoes = new JPanel();
        btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> fecharTela());
        btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> limparCampos());
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> atualizarPaciente());

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
        txfNome.setText("");
        txfCpf.setText("");
    }
    
    private void atualizarPaciente() {
        String cpf = txfCpf.getText().trim(); 
        String nome = txfNome.getText().trim(); 

        if (cpf.isEmpty() || nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "CPF e Nome são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Paciente paciente = pacService.localizarPacientePorCpf(cpf);

        if (paciente != null) {
            paciente.setNome(nome);
            paciente.setCpf(cpf);
            pacService.atualizarPaciente(paciente);
            JOptionPane.showMessageDialog(this, "Paciente atualizado com sucesso!");
            main.loadTablePaciente();
            fecharTela();
        } else {
            JOptionPane.showMessageDialog(this, "Paciente não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}