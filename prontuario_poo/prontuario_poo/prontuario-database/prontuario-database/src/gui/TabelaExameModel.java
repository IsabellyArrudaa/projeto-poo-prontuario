package gui;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Exame;

public class TabelaExameModel extends AbstractTableModel{

	/**	
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Exame> exames;
    private String[] colunas = {"ID", "Descrição", "Data do Exame", "Paciente ID"};

    public TabelaExameModel(List<Exame> exames) {
        this.exames = exames;
    }

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		 return exames.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		 return colunas.length;
	}
	
	@Override
	public String getColumnName(int index) {
		return colunas[index];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
        Exame exame = exames.get(rowIndex);
        switch (columnIndex) {
            case 0:return exame.getId(); 
            case 1:return exame.getDescricao(); 
            case 2: return exame.getDataExame(); 
            case 3:return exame.getId(); 
            default:
                return null;
        }
    }
}
