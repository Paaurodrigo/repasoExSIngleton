package repasoExamen;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


import javax.swing.JTextField;
import javax.swing.JButton;


public class RepasoExamen {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RepasoExamen window = new RepasoExamen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	/**
	 * Create the application.
	 */
	public RepasoExamen() {
		initialize();
	}

	
	public static void mostrarTabla(DefaultTableModel model) {
		if (model.getRowCount() > 0) {
			model.setRowCount(0);
		}
		try {
			Connection con = ConnectionSingleton.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM persona");
			while (rs.next()) {
				Object[] row = new Object[3];
				row[0] = rs.getInt("id");
				row[1] = rs.getString("nombre");
				row[2] = rs.getInt("edad");
				
				model.addRow(row);
			}
			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException ex) { // Caso erróneo
			JOptionPane.showMessageDialog(null, "No se a podido cargar los datos/n" + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 660, 451);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		DefaultTableModel model = new DefaultTableModel();

		model.addColumn("ID Usuario");
		model.addColumn("Nombre");
		model.addColumn("Edad");
		
		mostrarTabla(model);
		JTable table = new JTable(model);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int index = table.getSelectedRow();

				TableModel model= table.getModel();

				textField.setText(model.getValueAt(index, 0).toString());
				textField_1.setText(model.getValueAt(index, 1).toString());
				textField_2.setText(model.getValueAt(index, 2).toString());
				
			}
		});
		frame.getContentPane().setLayout(null);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(234, 6, 323, 188);
		frame.getContentPane().add(scrollPane);
		
		textField = new JTextField();
		textField.setBounds(391, 229, 130, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(391, 279, 130, 26);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(391, 330, 130, 26);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnCrear = new JButton("Crear");
		btnCrear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					Connection con = ConnectionSingleton.getConnection();
					PreparedStatement ins_pstmt = con
							.prepareStatement("insert into persona (id,nombre, edad) VALUES (?,?,?)");
					ins_pstmt.setInt(1, Integer.parseInt(textField.getText()));
					ins_pstmt.setString(2, textField_1.getText()); // Primer “?”
					ins_pstmt.setInt(3, Integer.parseInt(textField_2.getText())); // Segundo “?”
					ins_pstmt.executeUpdate();
				

					mostrarTabla(model);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnCrear.setBounds(160, 373, 117, 29);
		frame.getContentPane().add(btnCrear);
		
		JButton btnNewAct = new JButton("Actualizar");
		btnNewAct.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					Connection con = ConnectionSingleton.getConnection();
					PreparedStatement ins_pstmt = con.prepareStatement(
							"update persona set nombre=?,edad=? where id=?");
					ins_pstmt.setString(1, textField_1.getText());
					ins_pstmt.setInt(2,Integer.parseInt(textField_2.getText()));
					ins_pstmt.setInt(3, Integer.valueOf(textField.getText()));
					ins_pstmt.executeUpdate();
				

					mostrarTabla(model);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnNewAct.setBounds(284, 373, 117, 29);
		frame.getContentPane().add(btnNewAct);
		
		JButton btnBorr = new JButton("Borrar");
		btnBorr.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					Connection con = ConnectionSingleton.getConnection();
					PreparedStatement ins_pstmt = con.prepareStatement("delete from persona where id=?");
					ins_pstmt.setInt(1, Integer.parseInt(textField.getText()));
					ins_pstmt.executeUpdate();
					

					mostrarTabla(model);

				} catch (SQLException e1) {
					e1.printStackTrace();
					
				}
				
			}
		});
		btnBorr.setBounds(417, 373, 117, 29);
		frame.getContentPane().add(btnBorr);
		
	}
}
