package gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import model.Exame;
import model.Paciente;
import service.ExameService;
import service.PacienteService;

public class TelaPrincipal extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private JMenuBar barraMenu;
	private JMenu menuPaciente;
	private JMenuItem menuItemAdicionarPaciente;
	private JMenuItem menuItemAtualizarPaciente;
	private JMenu menuExame;
	private JMenuItem menuItemAtualizarExame;
	private JMenuItem menuItemAdicionarExame;
	private JScrollPane scrollPane;
	private JTable tablePacientes;
	private JTable tableExames;
	private PacienteService pacService;
	private JTabbedPane tabbed;
	private ExameService exameService;

	
	public TelaPrincipal(PacienteService pacService, ExameService exameService) {
		this.pacService = pacService;
		this.exameService = exameService;
		
		setTitle("Gerência de Pacientes e Exames!");
		setSize(480,360);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		barraMenu = new JMenuBar();
		
		// Menu Paciente
		menuPaciente = new JMenu("Paciente");
		barraMenu.add(menuPaciente);
		menuItemAdicionarPaciente = new JMenuItem("Cadastrar Paciente");
		menuItemAdicionarPaciente.addActionListener(e -> new TelaCadastrarPaciente(pacService, this));
		menuItemAtualizarPaciente = new JMenuItem("Atualizar Paciente");
		menuItemAtualizarPaciente.addActionListener(e -> new TelaAtualizarPaciente(pacService, this));
		menuPaciente.add(menuItemAdicionarPaciente);
		menuPaciente.add(menuItemAtualizarPaciente);
		
		// Menu Exame
        menuExame = new JMenu("Exame");
        menuItemAdicionarExame = new JMenuItem("Adicionar Exame");
        menuItemAdicionarExame.addActionListener(e -> {
            List<Paciente> pacientes = pacService.getPacientes(); 
            new TelaCadastrarExame(exameService, this, pacientes); 
        });
        menuItemAtualizarExame = new JMenuItem("Atualizar Exame");
        menuItemAtualizarExame.addActionListener(e -> new TelaAtualizarExame()); //erro 
        menuExame.add(menuItemAdicionarExame);
        menuExame.add(menuItemAtualizarExame);
        
        //estilo menu
        barraMenu.add(menuPaciente);
        barraMenu.add(menuExame);
        setJMenuBar(barraMenu);
		
		// Tabelas de Pacientes e Exames
		tablePacientes = new JTable();
        JScrollPane scrollPanePacientes = new JScrollPane(tablePacientes);
        tableExames = new JTable();
        JScrollPane scrollPaneExames = new JScrollPane(tableExames);
		 
		// Adicionando abas
        tabbed = new JTabbedPane();
        tabbed.addTab("Pacientes", scrollPanePacientes);
        tabbed.addTab("Exames", scrollPaneExames);
        add(tabbed, BorderLayout.CENTER);
        
		loadTablePaciente();
		loadTableExames();
		
		tablePacientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { 
                    int row = tablePacientes.rowAtPoint(e.getPoint());
                    if (row >= 0) { 
                        Paciente pacienteSelecionado = pacService.getPacientes().get(row);
                        new TelaRemoverPaciente(pacService, TelaPrincipal.this, pacienteSelecionado);
                    }
                }
            }
        });
	}
	
	protected void loadTableExames() {
		// TODO Auto-generated method stub
		List<Exame> exames = exameService.getExames();
		if (exames == null) {
	        exames = new ArrayList<>(); // Caso exames seja null, inicialize uma lista vazia
	    }
	    tableExames.setModel(new TabelaExameModel(exames));

	}

	protected void loadTablePaciente() {
		List<Paciente> itens = pacService.getPacientes();
		System.out.println(itens);
		tablePacientes.setModel(new TabelaPacienteModel(itens));
	}

}
