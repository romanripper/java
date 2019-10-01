package ripper.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.List;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ripper.dao.PersonDaoImpl;
import ripper.exception.MyException;
import ripper.model.ExhibitionLengthType;
import ripper.model.IndividualExhibition;
import ripper.model.Permanent;
import ripper.model.Person;
import ripper.model.Role;
import ripper.utils.MyUtils;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.awt.event.ActionEvent;

public class PlanExhibitionForm extends JFrame {

	private JPanel contentPane;
	private JTextField title_textField;
	private JTextField open_date_textField;
	private JTextField close_date_textField;
	private JTextField ticket_price_textField;
	private JTextArea keywords_textArea;
	private JLabel error_label;
	private JComboBox<Person> artist_comboBox;
	private JComboBox<Person> curator_comboBox;
	private JTextField electronicCatalogueUrl_textField;
	private List<Person> artists = null;
	private List<Person> curators = null;
	private PersonDaoImpl personDaoImpl;
	
	private IndividualExhibition individualExhibition;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlanExhibitionForm frame = new PlanExhibitionForm(null);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PlanExhibitionForm(IndividualExhibition individualExhibition) {
		
		initializeForm();
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 855, 743);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 250, 210));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(250, 250, 210));
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setHgap(40);
		flowLayout.setVgap(10);
		contentPane.add(panel, BorderLayout.CENTER);

		JPanel main_panel = new JPanel();
		main_panel.setBackground(new Color(250, 250, 210));
		panel.add(main_panel);
		main_panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		main_panel.setLayout(new GridLayout(8, 2, 5, 10));

		JLabel title_label = new JLabel("Title:");
		title_label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		main_panel.add(title_label);

		title_textField = new JTextField();
		title_textField.setText("SomeTitle");
		title_textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		main_panel.add(title_textField);
		title_textField.setColumns(15);

		JLabel open_date_label = new JLabel("Opening date (in \"dd.MM.yyyy\" format):");
		open_date_label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		main_panel.add(open_date_label);

		open_date_textField = new JTextField();
		open_date_textField.setText("20.02.2015");
		open_date_textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		main_panel.add(open_date_textField);
		open_date_textField.setColumns(10);

		JLabel close_date_label = new JLabel("Closing date (in \"dd.MM.yyyy\" format):");
		close_date_label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		main_panel.add(close_date_label);

		close_date_textField = new JTextField();
		close_date_textField.setText("20.05.2020");
		close_date_textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		main_panel.add(close_date_textField);
		close_date_textField.setColumns(10);

		JLabel ticket_price_label = new JLabel("Ticket price:");
		ticket_price_label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		main_panel.add(ticket_price_label);

		ticket_price_textField = new JTextField();
		ticket_price_textField.setText("30");
		ticket_price_textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		main_panel.add(ticket_price_textField);
		ticket_price_textField.setColumns(10);

		JLabel curator_label = new JLabel("Curator:");
		curator_label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		main_panel.add(curator_label);

		curator_comboBox.setBackground(new Color(250, 250, 210));
		curator_comboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		main_panel.add(curator_comboBox);

		JLabel keywords_label = new JLabel("Keywords (separated by \",\"):");
		keywords_label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		main_panel.add(keywords_label);

		keywords_textArea = new JTextArea();
		keywords_textArea.setText("nice, cool");
		keywords_textArea.setRows(2);
		keywords_textArea.setBackground(new Color(255, 255, 255));
		keywords_textArea.setColumns(10);
		keywords_textArea.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JScrollPane scrollPane = new JScrollPane(keywords_textArea);
		main_panel.add(scrollPane);

		JLabel artist_label = new JLabel("Artist:");
		artist_label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		main_panel.add(artist_label);

		artist_comboBox.setBackground(new Color(250, 250, 210));
		artist_comboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		main_panel.add(artist_comboBox);

		JLabel electronicCatalogueUrl_label = new JLabel("Electronic catalogue URL:");
		electronicCatalogueUrl_label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		main_panel.add(electronicCatalogueUrl_label);

		ButtonGroup type_button_group = new ButtonGroup();

		ButtonGroup length_type_button_group = new ButtonGroup();

		electronicCatalogueUrl_textField = new JTextField();
		electronicCatalogueUrl_textField.setText("http://ashfgasj");
		electronicCatalogueUrl_textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		main_panel.add(electronicCatalogueUrl_textField);
		electronicCatalogueUrl_textField.setColumns(13);

		JPanel label_panel = new JPanel();
		label_panel.setBackground(new Color(124, 252, 0));
		FlowLayout fl_label_panel = (FlowLayout) label_panel.getLayout();
		fl_label_panel.setHgap(0);
		fl_label_panel.setVgap(15);
		contentPane.add(label_panel, BorderLayout.NORTH);

		JLabel plan_exhibition_label = new JLabel("Plan Individual Exhibition");
		plan_exhibition_label.setFont(new Font("Tahoma", Font.PLAIN, 17));
		label_panel.add(plan_exhibition_label);

		JPanel button_panel = new JPanel();
		button_panel.setBackground(new Color(250, 250, 210));
		button_panel.setBorder(new EmptyBorder(5, 3, 3, 3));
		contentPane.add(button_panel, BorderLayout.SOUTH);
		button_panel.setLayout(new BorderLayout(0, 0));

		JButton confirm_button = new JButton("Confirm");
		confirm_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					confirmButtonClick();
				} catch (MyException e1) {
					e1.printStackTrace();
				}
			}
		});
		confirm_button.setBackground(new Color(124, 252, 0));
		confirm_button.setFont(new Font("Tahoma", Font.PLAIN, 17));
		confirm_button.setHorizontalAlignment(SwingConstants.RIGHT);
		button_panel.add(confirm_button, BorderLayout.EAST);

		error_label = new JLabel("");
		error_label.setForeground(new Color(165, 42, 42));
		error_label.setHorizontalAlignment(SwingConstants.LEFT);
		button_panel.add(error_label, BorderLayout.WEST);
		
		if(individualExhibition != null) {
			title_textField.setText(individualExhibition.getTitle());
			open_date_textField.setText(MyUtils.convertDateToString(individualExhibition.getOpenDate()));
			close_date_textField.setText(MyUtils.convertDateToString(individualExhibition.getCloseDate()));
			ticket_price_textField.setText(String.valueOf(individualExhibition.getTicketPrice()));
			keywords_textArea.setText(MyUtils.getKeywordsFromCollection(individualExhibition.getKeywords()));
			electronicCatalogueUrl_textField.setText(((Permanent)individualExhibition.getExhibitionLengthType()).getElectronicCatalogueURL());
			System.out.println("here1");
		}
	}

	private void initializeForm() {
		personDaoImpl = new PersonDaoImpl();
		artists = personDaoImpl.getPersonsByRole(Role.ARTIST);
		curators = personDaoImpl.getPersonsByRole(Role.CURATOR);

		System.out.println(artists);

		curator_comboBox = new JComboBox(curators.toArray());
		artist_comboBox = new JComboBox(artists.toArray());

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				personDaoImpl.closeConnection();
			}
		});
	}

	private void confirmButtonClick() throws MyException {
		try {
			String title = title_textField.getText();
			if (title.trim().equals("")) {
				throw new MyException("Please provide title");
			}
			LocalDate openDate = getOpenDateFromForm(); 
			LocalDate closeDate = getCloseDateFromForm();

			if (openDate.isAfter(closeDate)) {
				throw new MyException("Openning date can not be after closing date");
			}
		
			Double ticketPrice = getTicketPriceFromForm();
			
			Person curator = curators.get(curator_comboBox.getSelectedIndex());
			Person artist = artists.get(artist_comboBox.getSelectedIndex());
			
			Set<String> keywords = getKeywordsFromForm();
			
			String electronicCatalogueUrl = electronicCatalogueUrl_textField.getText();
			try {
				MyUtils.checkUrl(electronicCatalogueUrl);
			} catch (Exception e) {
				throw new MyException(e.getMessage());
				
			}
			
			System.out.println("safgja 1");
			IndividualExhibition individualExhibition = new IndividualExhibition(title, openDate, closeDate,
					ticketPrice, artist, curator, keywords);
			ExhibitionLengthType legth_type = new Permanent(individualExhibition, electronicCatalogueUrl);

			
			System.out.println("safgja 2");
			ChooseExhibitsForExhibitionForm frame = new ChooseExhibitsForExhibitionForm(individualExhibition);
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
			personDaoImpl.closeConnection();
			this.dispose();
		} catch (Exception e) {
			e.printStackTrace();
			error_label.setText(e.getMessage());
		}

	}

	private Set<String> getKeywordsFromForm() throws MyException {
		Set<String> keywords;
		try {
			keywords = MyUtils.getKeywordsFRomTextArea(keywords_textArea.getText());
		} catch (Exception e) {
			throw new MyException(e.getMessage());
		}
		return keywords;
	}

	private Double getTicketPriceFromForm() throws MyException {
		Double ticketPrice;
		try {
			ticketPrice = Double.valueOf(ticket_price_textField.getText());
		} catch (Exception e) {
			throw new MyException("Please provide valid ticket price");
		}
		return ticketPrice;
	}

	private LocalDate getCloseDateFromForm() throws MyException {
		LocalDate closeDate;
		try {
			closeDate = MyUtils.convertStringToDate(close_date_textField.getText());
		} catch (DateTimeParseException e) {
			throw new MyException("Please provide valid closing date");
		}
		return closeDate;
	}

	private LocalDate getOpenDateFromForm() throws MyException {
		LocalDate openDate = null;
		try {
			openDate = MyUtils.convertStringToDate(open_date_textField.getText());
		} catch (DateTimeParseException e) {
			throw new MyException("Please provide valid openning date");
		}
		return openDate;
	}

	private void close() {
		WindowEvent windowEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(windowEvent);
	}

}
