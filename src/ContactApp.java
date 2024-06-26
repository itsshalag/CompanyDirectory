package companyDirectory;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

/**
 * This class represents the user interface.
 * 
 *  @author Yudith Mendoza & Shala Gutierrez
 */
public class ContactApp extends JFrame {
	private JPanel contentPane;
	private Graph contactGraph;
	private JPanel mainPanel;
	private CardLayout cardLayout;

	/**
	 * Creates JPanel with multiple elements to create a company directory.
	 * Based on a graph created from two files-- one with all employee information
	 * and the other representing all direct relationships
	 * @param contactFilename
	 * @param directContactFilename
	 */
	public ContactApp(String contactFilename, String directContactFilename) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 650);
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setForeground(new Color(128, 128, 128));
		contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		contentPane.add(topPanel, BorderLayout.NORTH);

		JLabel companyLabel = new JLabel("Small Shops Directory", SwingConstants.CENTER);
		companyLabel.setFont(new Font("PingFang HK", Font.PLAIN, 27));
		topPanel.add(companyLabel, BorderLayout.CENTER);

		JButton logoButton = new JButton();
		logoButton.setIcon(new ImageIcon("img/SSLogo.png"));
		logoButton.setBorderPainted(false);
		logoButton.setContentAreaFilled(false);
		logoButton.addActionListener(e -> cardLayout.show(mainPanel, "Employees"));
		topPanel.add(logoButton, BorderLayout.WEST);

		// Initialize the graph with contacts and connections
		contactGraph = new Graph(contactFilename, directContactFilename);
		mainPanel = new JPanel(new CardLayout());
		cardLayout = (CardLayout) mainPanel.getLayout();
		contentPane.add(mainPanel, BorderLayout.CENTER);

		// panel to display all employees
		JPanel employeePanel = new JPanel(new GridLayout(0, 1, 10, 10));
		for (Map.Entry<Integer, Contact> entry : contactGraph.getAllContacts().entrySet()) {
			Contact contact = entry.getValue();
			String buttonText = contact.getFirstName() + " " + contact.getLastName();
			JButton button = new JButton(buttonText);
			button.addActionListener(e -> displayDirectContacts(entry.getKey()));
			employeePanel.add(button);
		}
		mainPanel.add(new JScrollPane(employeePanel), "Employees");

		cardLayout.show(mainPanel, "Employees");
	}

	/**
	 * Creates contact card for employee that includes their picture, name,
	 * email, and phone number
	 * @param contact
	 * @return
	 */
	public JPanel createContactCard(Contact contact) {
		JPanel contactCard = new JPanel();
		contactCard.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		contactCard.setLayout(new MigLayout("", "[16px][170px]", "[16px]"));
		
		JButton contactBtn = new JButton(new ImageIcon("img/" + contact.getId() + ".png"));
		contactBtn.setBorder(new EmptyBorder(0, 0, 0, 20));
		contactCard.add(contactBtn, "cell 0 0,alignx left,aligny center");
		contactBtn.addActionListener(e -> displayDirectContacts(contact.getId()));		
		
		String contactInfo = contact.getFirstName() + " " + contact.getLastName() + " - " + contact.getPosition();
		JLabel name = new JLabel(contactInfo);
		contactCard.add(name, "flowy,cell 1 0,alignx left,aligny center");
		
	
		JLabel emailLbl = new JLabel(contact.getEmail());
		contactCard.add(emailLbl, "cell 1 0");
		
		JLabel phoneLbl = new JLabel(contact.getPhoneNumber());
		contactCard.add(phoneLbl, "cell 1 0");
		
		return contactCard;
	}
	/**
	 * Makes all contact cards for the employees that have a direct relationship
	 * with selected employee
	 * @param employeeId
	 */
	private void displayDirectContacts(int employeeId) {
		JPanel cPanel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new EmptyBorder(0, 0, 0, 780));
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(e -> cardLayout.show(mainPanel, "Employees"));
		buttonPanel.add(backButton);
		cPanel.add(buttonPanel, BorderLayout.NORTH);
		
		JPanel contactsPanel = new JPanel(new GridLayout(0, 1, 10, 10));

		for (Contact contact : contactGraph.getDirectContacts(employeeId)) {
			JPanel contactCard = createContactCard(contact);
			contactsPanel.add(contactCard);
		}
		
		cPanel.add(contactsPanel, BorderLayout.CENTER);
		
		contentPane.add(cPanel);
		mainPanel.add(new JScrollPane(cPanel), "Contacts");
		cardLayout.show(mainPanel, "Contacts");
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				ContactApp frame = new ContactApp("Resources/SmallShopsDirectory.txt", "Resources/DirectContact.txt");
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}
