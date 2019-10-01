package ripper.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;

import ripper.dao.ExhibitDaoImpl;
import ripper.dao.ExhibitionDaoImpl;
import ripper.dao.PersonDaoImpl;
import ripper.model.Exhibit;
import ripper.model.IndividualExhibition;
import ripper.model.Person;
import ripper.model.Role;

public class ChooseExhibitsForExhibitionForm extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private ExhibitDaoImpl exhibitDaoImpl;
	private IndividualExhibition individualExhibition;
	private List<Exhibit> exhibits = null;
	private ExhibitionDaoImpl exhibitionDaoImpl;
	private JLabel error_label;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ChooseExhibitsForExhibitionForm frame = new ChooseExhibitsForExhibitionForm();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public ChooseExhibitsForExhibitionForm(IndividualExhibition individualExhibition) {
		this.individualExhibition = individualExhibition;

		initializeForm();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1025, 670);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 250, 210));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel label_panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) label_panel.getLayout();
		flowLayout.setVgap(15);
		label_panel.setBackground(new Color(250, 250, 210));
		contentPane.add(label_panel, BorderLayout.NORTH);

		JLabel lblChooseArtistsExhibits = new JLabel("Choose artist`s exhibits for exhibition");
		lblChooseArtistsExhibits.setFont(new Font("Tahoma", Font.PLAIN, 17));
		label_panel.add(lblChooseArtistsExhibits);

		JPanel table_panel = new JPanel();
		table_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		table_panel.setLayout(new BorderLayout(0, 0));

		error_label = new JLabel("");
		error_label.setForeground(new Color(139, 0, 0));
		error_label.setBackground(new Color(240, 248, 255));
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table.setModel(new MyTableModel());
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.setBackground(new Color(250, 250, 210));
		
		if(exhibits.isEmpty()) {
			table.setVisible(false);
			error_label.setText("Sorry there are no free exhibits of " + individualExhibition.getArtist().toString());
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		table_panel.add(scrollPane, BorderLayout.CENTER);
		
		contentPane.add(table_panel, BorderLayout.CENTER);
		
		setSizeOfColumns();
		
		
		JPanel button_panel = new JPanel();
		button_panel.setBorder(new EmptyBorder(5, 3, 3, 3));
		button_panel.setBackground(new Color(250, 250, 210));
		contentPane.add(button_panel, BorderLayout.SOUTH);
		button_panel.setLayout(new BorderLayout(0, 0));
		
		button_panel.add(error_label, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(250, 250, 210));
		button_panel.add(panel, BorderLayout.EAST);
				panel.setLayout(new FlowLayout(FlowLayout.CENTER, 70, 5));
				
				JButton backButton = new JButton("Back");
				backButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						backClick();
					}
				});
				backButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
				backButton.setBackground(new Color(124, 252, 0));
				panel.add(backButton);
		
				JButton confirm_button = new JButton("Confirm");
				panel.add(confirm_button);
				confirm_button.setFont(new Font("Tahoma", Font.PLAIN, 17));
				confirm_button.setBackground(new Color(124, 252, 0));
				confirm_button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						confirmClick();
					}
				});
				confirm_button.setHorizontalAlignment(SwingConstants.RIGHT);
	}

	private void initializeForm() {
		
		System.out.println("chooseExhibitForm");
		exhibitDaoImpl = new ExhibitDaoImpl();
		
		exhibitionDaoImpl = new ExhibitionDaoImpl();

		exhibits = new ArrayList<Exhibit>(exhibitDaoImpl.getFreeExhibits(individualExhibition.getArtist()));

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				exhibitDaoImpl.closeConnection();
				exhibitionDaoImpl.closeConnection();
			}
		});
	}

	private void setSizeOfColumns() {
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(10);
		//table.getColumnModel().getColumn(0).setPreferredWidth(20);
	}
	
	private void confirmClick() {
		int[] rows = table.getSelectedRows();
		if(rows.length > 0) {
			for (int i : rows) {
				//System.out.println(i);
				individualExhibition.addExhibit(exhibits.get(i));
				//System.out.println(exhibits.get(i).getName());
			}		
			
			System.out.println(individualExhibition.getExhibits());
			
			//exhibitionDaoImpl.create(individualExhibition);
			exhibitDaoImpl.update(new HashSet<>(individualExhibition.getExhibits().values()));
			JOptionPane.showMessageDialog(null, "Exhibition successfully planned");
			close();
		}else {
			error_label.setText("To submit form you need to specify at least one exhibit");
		}
	}
	
	private void backClick() {
		PlanExhibitionForm frame = new PlanExhibitionForm(individualExhibition);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		dispose();
	}
	
	
	
	private class MyTableModel extends AbstractTableModel {
		@Override
		public int getRowCount() {
			return exhibits.size();
		}

		@Override
		public int getColumnCount() {
			return 3;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return exhibits.get(rowIndex).getId();
			case 1:
				return exhibits.get(rowIndex).getName();
			case 2:
				return exhibits.get(rowIndex).getDescription();
			default:
				return null;
			}
		}

		@Override
		public String getColumnName(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return "Id";
			case 1:
				return "Name";
			case 2:
				return "Description";
			default:
				return null;
			}

		}
	}

	private void close() {
		WindowEvent windowEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(windowEvent);
	}
}