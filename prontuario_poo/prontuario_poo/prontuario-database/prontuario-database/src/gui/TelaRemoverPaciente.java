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

public class TelaRemoverPaciente extends JDialog {

    private static final long serialVersionUID = 1L;

    private PacienteService pacService;
    private TelaPrincipal main;
    private JPanel painelForm;
    private JPanel painelBotoes;
    private JButton btnExcluir;
    private JButton btnCancelar;
    private JLabel lblNome;
    private JLabel lblCpf;
    private JTextField txfNome;
    private JTextField txfCpf;
    private Paciente pacienteSelecionado;

    public TelaRemoverPaciente(PacienteService pacService, TelaPrincipal main, Paciente pacienteSelecionado) {
        this.pacService = pacService;
        this.main = main;
        this.pacienteSelecionado = pacienteSelecionado;
        setSize(360, 200);
        setResizable(false);
        setTitle("Excluir Paciente");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        painelForm = new JPanel();
        lblNome = new JLabel("Nome: ");
        lblCpf = new JLabel("CPF: ");
        txfNome = new JTextField(24);
        txfCpf = new JTextField(24);

        txfNome.setText(pacienteSelecionado.getNome());
        txfCpf.setText(pacienteSelecionado.getCpf());
        txfNome.setEditable(false); 
        txfCpf.setEditable(false);

        painelForm.add(lblNome);
        painelForm.add(txfNome);
        painelForm.add(lblCpf);
        painelForm.add(txfCpf);
        add(painelForm, BorderLayout.CENTER);

        painelBotoes = new JPanel();
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> fecharTela());
        btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> excluirPaciente());

        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnCancelar);
        add(painelBotoes, BorderLayout.SOUTH);
        setModal(true);
        setVisible(true);
    }

    private void fecharTela() {
        this.dispose(); 
    }

    private void excluirPaciente() {
        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir o paciente " + pacienteSelecionado.getNome() + "?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
        );
        if (confirmacao == JOptionPane.YES_OPTION) {
            Paciente paciente = pacService.localizarPacientePorId(pacienteSelecionado.getId());
            if (paciente != null) {
                pacService.deletarPaciente(paciente); 
                JOptionPane.showMessageDialog(this, "Paciente excluído com sucesso!");
                main.loadTablePaciente();
                fecharTela();
            } else {
                JOptionPane.showMessageDialog(this, "Paciente não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}