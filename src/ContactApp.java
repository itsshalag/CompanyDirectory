/**
 * This class represents the user interface. 
 * 
 * @author Yudith Mendoza & Shala Gutierrez
 */
public class ContactApp extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ContactApp frame = new ContactApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ContactApp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 650);
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setForeground(new Color(128, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		newTopPanel(panel);
		panel.setLayout(null);
		
		
		JLabel companyLabel = newCompanyLbl();
		panel.add(companyLabel);
		
		JButton newButton = newCompanyLogo();
		panel.add(newButton);		
		
		
	}

	private JButton newCompanyLogo() {
		JButton newButton = new JButton();
		newButton.setIcon(new ImageIcon(ContactApp.class.getResource("/img/SSLogo.png")));
		newButton.setBounds(22, 23, 64, 64);
		return newButton;
	}

	private JLabel newCompanyLbl() {
		JLabel newLbl = new JLabel("Small Shops Directory");
		newLbl.setHorizontalAlignment(SwingConstants.CENTER);
		newLbl.setFont(new Font("PingFang HK", Font.PLAIN, 27));
		newLbl.setBounds(98, 39, 298, 48);
		return newLbl;
	}

	private void newTopPanel(JPanel panel) {
		panel.setBounds(6, 6, 838, 93);
		contentPane.add(panel);
	}
}
