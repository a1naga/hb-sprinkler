package humming.bird.sprinkler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class HomeScreenNew {

	private JFrame frame;
	private JFrame configFrame;

	private JPanel statusPane;
	private JPanel FunctionalityPane;
	private JPanel WaterConsumptionGraphPane;
	private JComboBox<String> Select;
	private JButton btnOverrideButton;
	private JButton btnNextScreen;
	private JButton btnAdjustTemp;
	private JPanel northPane;
	private JPanel eastPane;
	private JPanel westPane;
	private JPanel southPane;

	//URL iconURL = getClass().getResource("/resources/WateringCan.png");
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeScreenNew window = new HomeScreenNew();
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
	public HomeScreenNew() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		// this code for frame icon
		
		//ImageIcon img = new ImageIcon(ImageIO.read("/Resources/WateringCan.png"));
		
		// This part is for getting System Date
		DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, ''yy  h:mm a");
		Date date = new Date();

		/*******************************************************************************************************************************************************/
		// Frame with Box Layout and 3 panels
		frame = new JFrame();

		// Gets the current Screen size and set it to the Frame
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dim = toolkit.getScreenSize();
		frame.setSize(dim.width, dim.height);
		Image icon  = Toolkit.getDefaultToolkit().getImage("/res/WateringCan.png");
		frame.setIconImage(icon);
		

		// Set the Title for Frame and Display the Date on the title bar of the
		// frame with above mentioned format
		frame.setTitle(
				"Humming Bee Home Garden Sprinkler System                            " + dateFormat.format(date));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set the Icon for the frame
		
	    
		//frame.setIconImage(ImageIO.read("/Resources/WateringCan.png"));
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));

		/*******************************************************************************************************************************************************/
		// Status panel to display the status of Sprinklers
		statusPane = new JPanel();
		statusPane.setPreferredSize(new Dimension(150, 300));
		statusPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Sprinkler Status",
				TitledBorder.CENTER, TitledBorder.TOP, null, null));
		statusPane.setBackground(new Color(128, 255, 128));

		// Set Grid Layout for the Status panel with 2 Rows and 2 Columns
		statusPane.setLayout(new GridLayout(2, 2));

		// Add the Status Panel to the Frame
		frame.getContentPane().add(statusPane);

		/*------------------------------------------------------------------------------------------------------------------- ----------------*/
		// North pane of the Status panel
		northPane = new JPanel();
		northPane.setPreferredSize(new Dimension(20, 20));
		northPane.setBackground(new Color(128, 255, 128));
		northPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "North Sprinkelr Group",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		// Set Grid Layout for the Status panel with 2 Rows and 3 Columns
		northPane.setLayout(new GridLayout(3, 4, 2, 3));

		JEditorPane jep = new JEditorPane("text/html", "<a href='test'>SprinklerN1</a>");
		jep.setEditable(false);
		jep.setOpaque(false);
		//to place it center
		SimpleAttributeSet attrs=new SimpleAttributeSet();
        StyleConstants.setAlignment(attrs,StyleConstants.ALIGN_CENTER);
        StyledDocument doc=(StyledDocument)jep.getDocument();
        
        doc.setParagraphAttributes(0,doc.getLength()-1,attrs,false);

		jep.addHyperlinkListener(new HyperlinkListener() {
			
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
					System.out.println(e.getURL());
					}
				
			}
		});
		// The Sprinkler N1 in North pane
		/**JLabel lblSprinklerN1 = new JLabel("Sprinkler N1");
		lblSprinklerN1.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerN1.setToolTipText("Sprinkler N1");**/
		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		/**lblSprinklerN1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);
				configFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				configFrame.setVisible(true);

			}
		});**/
		
		JLabel statusSpklrN1 = new JLabel("sprinkler N1");
		statusSpklrN1.setHorizontalAlignment(SwingConstants.CENTER);
		northPane.add(jep);
		northPane.add(statusSpklrN1);

		// The Sprinkler N2 in North pane
		JLabel lblSprinklerN2 = new JLabel("Sprinkler N2");
		lblSprinklerN2.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerN2.setToolTipText("Sprinkler N2");
		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerN2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);
				configFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				configFrame.setVisible(true);

			}
		});
		
		JLabel statusSpklrN2 = new JLabel("sprinkler N2");
		statusSpklrN2.setHorizontalAlignment(SwingConstants.CENTER);
		northPane.add(lblSprinklerN2);
		northPane.add(statusSpklrN2);

		// The Sprinkler N3 in North pane
		JLabel lblSprinklerN3 = new JLabel("Sprinkler N3");
		lblSprinklerN3.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerN3.setToolTipText("Sprinkler N3");
		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerN3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);
				configFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				configFrame.setVisible(true);

			}
		});
		
		JLabel statusSpklrN3 = new JLabel("sprinkler N3");
		statusSpklrN3.setHorizontalAlignment(SwingConstants.CENTER);
		northPane.add(lblSprinklerN3);
		northPane.add(statusSpklrN3);

		// The Sprinkler N4 in North pane
		JLabel lblSprinklerN4 = new JLabel("Sprinkler N4");
		lblSprinklerN4.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerN4.setToolTipText("Sprinkler N4");
		
		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerN4.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);
				configFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				configFrame.setVisible(true);

			}
		});
		
		JLabel statusSpklrN4 = new JLabel("sprinkler N4");
		statusSpklrN4.setHorizontalAlignment(SwingConstants.CENTER);
		northPane.add(lblSprinklerN4);
		northPane.add(statusSpklrN4);

		// The Sprinkler N5 in North pane
		JLabel lblSprinklerN5 = new JLabel("Sprinkler N5");
		lblSprinklerN5.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerN5.setToolTipText("Sprinkler N5");
		
		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerN5.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);
				configFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				configFrame.setVisible(true);

			}
		});
		
		JLabel statusSpklrN5 = new JLabel("sprinkler N5");
		statusSpklrN5.setHorizontalAlignment(SwingConstants.CENTER);
		northPane.add(lblSprinklerN5);
		northPane.add(statusSpklrN5);

		// The Sprinkler N6 in North pane
		JLabel lblSprinklerN6 = new JLabel("Sprinkler N6");
		lblSprinklerN6.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerN6.setToolTipText("Sprinkler N6");
		JLabel statusSpklrN6 = new JLabel("sprinkler N6");
		
		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerN6.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);
				configFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				configFrame.setVisible(true);

			}
		});

		statusSpklrN6.setHorizontalAlignment(SwingConstants.CENTER);
		northPane.add(lblSprinklerN6);
		northPane.add(statusSpklrN6);
		// Adding the North Pane to the Status Panel Grid
		statusPane.add(northPane);

		/*------------------------------------------------------------------------------------------------------------------- ----------------*/
		// East pane of the status pane
		eastPane = new JPanel();
		eastPane.setPreferredSize(new Dimension(20, 20));
		eastPane.setBackground(new Color(128, 255, 128));
		eastPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "East Sprinkelr Group",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		// Set GridLayout for the East Pane with 2 Rows and 3 Columns
		eastPane.setLayout(new GridLayout(3, 4, 2, 3));

		// The Sprinkler E1 in East pane
		JLabel lblSprinklerE1 = new JLabel("Sprinkler E1");
		lblSprinklerE1.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerE1.setToolTipText("Sprinkler E1");

		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerE1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);
				configFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				configFrame.setVisible(true);

			}
		});

		JLabel statusSpklrE1 = new JLabel("sprinkler E1");
		statusSpklrE1.setHorizontalAlignment(SwingConstants.CENTER);
		eastPane.add(lblSprinklerE1);
		eastPane.add(statusSpklrE1);

		// The Sprinkler E2 in East pane
		JLabel lblSprinklerE2 = new JLabel("Sprinkler E2");
		lblSprinklerE2.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerE2.setToolTipText("Sprinkler E2");

		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerE2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);
				configFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				configFrame.setVisible(true);

			}
		});

		JLabel statusSpklrE2 = new JLabel("sprinkler E2");
		statusSpklrE2.setHorizontalAlignment(SwingConstants.CENTER);
		eastPane.add(lblSprinklerE2);
		eastPane.add(statusSpklrE2);

		// The Sprinkler E3 in East pane
		JLabel lblSprinklerE3 = new JLabel("Sprinkler E3");
		lblSprinklerE3.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerE3.setToolTipText("Sprinkler E3");

		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerE3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);
				configFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				configFrame.setVisible(true);

			}
		});

		JLabel statusSpklrE3 = new JLabel("sprinkler E3");
		statusSpklrE3.setHorizontalAlignment(SwingConstants.CENTER);
		eastPane.add(lblSprinklerE3);
		eastPane.add(statusSpklrE3);

		// The Sprinkler E4 in East pane
		JLabel lblSprinklerE4 = new JLabel("Sprinkler E4");
		lblSprinklerE4.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerE4.setToolTipText("Sprinkler E4");

		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerE4.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);
				configFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				configFrame.setVisible(true);

			}
		});

		JLabel statusSpklrE4 = new JLabel("sprinkler E4");
		statusSpklrE4.setHorizontalAlignment(SwingConstants.CENTER);
		eastPane.add(lblSprinklerE4);
		eastPane.add(statusSpklrE4);

		// The Sprinkler E5 in East pane
		JLabel lblSprinklerE5 = new JLabel("Sprinkler E5");
		lblSprinklerE5.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerE5.setToolTipText("Sprinkler E5");

		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerE5.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);
				configFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				configFrame.setVisible(true);

			}
		});

		JLabel statusSpklrE5 = new JLabel("sprinkler E5");
		statusSpklrE5.setHorizontalAlignment(SwingConstants.CENTER);
		eastPane.add(lblSprinklerE5);
		eastPane.add(statusSpklrE5);

		// The Sprinkler E6 in East pane
		JLabel lblSprinklerE6 = new JLabel("Sprinkler E6");
		lblSprinklerE6.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerE6.setToolTipText("Sprinkler E6");

		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerE6.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);
				configFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				configFrame.setVisible(true);

			}
		});

		JLabel statusSpklrE6 = new JLabel("sprinkler E6");
		statusSpklrE6.setHorizontalAlignment(SwingConstants.CENTER);
		eastPane.add(lblSprinklerE6);
		eastPane.add(statusSpklrE6);

		// Adding the East Pane to the Status Panel Grid
		statusPane.add(eastPane);

		/*------------------------------------------------------------------------------------------------------------------- ----------------*/
		// West pane of the status pane
		westPane = new JPanel();
		westPane.setPreferredSize(new Dimension(20, 20));
		westPane.setBackground(new Color(128, 255, 128));
		westPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "West Sprinkelr Group",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		// Set GridLayout for the West Pane with 2 Rows and 3 Columns
		westPane.setLayout(new GridLayout(3, 4, 2, 3));

		// The Sprinkler W1 in West pane
		JLabel lblSprinklerW1 = new JLabel("Sprinkler W1");
		lblSprinklerW1.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerW1.setToolTipText("Sprinkler W1");

		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerW1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);
				configFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				configFrame.setVisible(true);

			}
		});

		JLabel statusSpklrW1 = new JLabel("sprinkler W1");
		statusSpklrW1.setHorizontalAlignment(SwingConstants.CENTER);
		westPane.add(lblSprinklerW1);
		westPane.add(statusSpklrW1);

		// The Sprinkler W2 in West pane
		JLabel lblSprinklerW2 = new JLabel("Sprinkler W2");
		lblSprinklerW2.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerW2.setToolTipText("Sprinkler W2");
		
		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerW2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);
				configFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				configFrame.setVisible(true);

			}
		});
		
		JLabel statusSpklrW2 = new JLabel("sprinkler W2");
		statusSpklrW2.setHorizontalAlignment(SwingConstants.CENTER);
		westPane.add(lblSprinklerW2);
		westPane.add(statusSpklrW2);

		// The Sprinkler W3 in West pane
		JLabel lblSprinklerW3 = new JLabel("Sprinkler W3");
		lblSprinklerW3.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerW3.setToolTipText("Sprinkler W3");
		
		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerW3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);

				configFrame.setVisible(true);

			}
		});
		
		JLabel statusSpklrW3 = new JLabel("sprinkler W3");
		statusSpklrW3.setHorizontalAlignment(SwingConstants.CENTER);
		westPane.add(lblSprinklerW3);
		westPane.add(statusSpklrW3);

		// The Sprinkler W4 in West pane
		JLabel lblSprinklerW4 = new JLabel("Sprinkler W4");
		lblSprinklerW4.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerW4.setToolTipText("Sprinkler W4");
		
		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerW4.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);

				configFrame.setVisible(true);

			}
		});
		
		JLabel statusSpklrW4 = new JLabel("sprinkler W4");
		statusSpklrW4.setHorizontalAlignment(SwingConstants.CENTER);
		westPane.add(lblSprinklerW4);
		westPane.add(statusSpklrW4);

		// The Sprinkler W5 in West pane
		JLabel lblSprinklerW5 = new JLabel("Sprinkler W5");
		lblSprinklerW5.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerW5.setToolTipText("Sprinkler W5");
		
		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerW5.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);

				configFrame.setVisible(true);

			}
		});
		
		
		JLabel statusSpklrW5 = new JLabel("sprinkler W5");
		statusSpklrW5.setHorizontalAlignment(SwingConstants.CENTER);
		westPane.add(lblSprinklerW5);
		westPane.add(statusSpklrW5);

		// The Sprinkler W6 in West pane
		JLabel lblSprinklerW6 = new JLabel("Sprinkler W6");
		lblSprinklerW6.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerW6.setToolTipText("Sprinkler W6");
		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerW6.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);

				configFrame.setVisible(true);

			}
		});
		
		
		JLabel statusSpklrW6 = new JLabel("sprinkler W6");
		statusSpklrW6.setHorizontalAlignment(SwingConstants.CENTER);
		westPane.add(lblSprinklerW6);
		westPane.add(statusSpklrW6);

		// Adding the West Pane to the Status Panel Grid
		statusPane.add(westPane);

		/*------------------------------------------------------------------------------------------------------------------- ----------------*/
		// South pane of the status pane
		southPane = new JPanel();
		southPane.setPreferredSize(new Dimension(20, 20));
		southPane.setBackground(new Color(128, 255, 128));
		southPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "South Sprinkelr Group",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		// Set GridLayout for the southPane with 2 Rows and 3 Columns
		southPane.setLayout(new GridLayout(3, 4, 2, 3));

		// The Sprinkler S1 in South pane
		JLabel lblSprinklerS1 = new JLabel("Sprinkler S1");
		lblSprinklerS1.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerS1.setToolTipText("Sprinkler S1");

		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerS1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);

				configFrame.setVisible(true);

			}
		});

		JLabel statusSpklrS1 = new JLabel("sprinkler S1");
		statusSpklrS1.setHorizontalAlignment(SwingConstants.CENTER);
		southPane.add(lblSprinklerS1);
		southPane.add(statusSpklrS1);

		// The Sprinkler S2 in South pane
		JLabel lblSprinklerS2 = new JLabel("Sprinkler S2");
		lblSprinklerS2.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerS2.setToolTipText("Sprinkler S2");
		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerS2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);

				configFrame.setVisible(true);

			}
		});
		
		JLabel statusSpklrS2 = new JLabel("sprinkler S2");
		statusSpklrS2.setHorizontalAlignment(SwingConstants.CENTER);
		southPane.add(lblSprinklerS2);
		southPane.add(statusSpklrS2);

		// The Sprinkler S3 in South pane
		JLabel lblSprinklerS3 = new JLabel("Sprinkler S3");
		lblSprinklerS3.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerS3.setToolTipText("Sprinkler S3");
		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerS3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);

				configFrame.setVisible(true);

			}
		});
		
		JLabel statusSpklrS3 = new JLabel("sprinkler S3");
		statusSpklrS3.setHorizontalAlignment(SwingConstants.CENTER);
		southPane.add(lblSprinklerS3);
		southPane.add(statusSpklrS3);

		// The Sprinkler S4 in South pane
		JLabel lblSprinklerS4 = new JLabel("Sprinkler S4");
		lblSprinklerS4.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerS4.setToolTipText("Sprinkler S4");
		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerS4.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);

				configFrame.setVisible(true);

			}
		});
		
		JLabel statusSpklrS4 = new JLabel("sprinkler S4");
		statusSpklrS4.setHorizontalAlignment(SwingConstants.CENTER);
		southPane.add(lblSprinklerS4);
		southPane.add(statusSpklrS4);

		// The Sprinkler S5 in South pane
		JLabel lblSprinklerS5 = new JLabel("Sprinkler S5");
		lblSprinklerS5.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerS5.setToolTipText("Sprinkler S5");
		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerS5.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);

				configFrame.setVisible(true);

			}
		});
		
		JLabel statusSpklrS5 = new JLabel("sprinkler S5");
		statusSpklrS5.setHorizontalAlignment(SwingConstants.CENTER);
		southPane.add(lblSprinklerS5);
		southPane.add(statusSpklrS5);

		// The Sprinkler S6 in South pane
		JLabel lblSprinklerS6 = new JLabel("Sprinkler S6");
		lblSprinklerS6.setBorder(
				new BevelBorder(BevelBorder.RAISED, UIManager.getColor("Label.background"), null, null, null));
		lblSprinklerS6.setToolTipText("Sprinkler S6");
		/*
		 * Opens the Sprinkler Configuration Frame when this label is clicked by
		 * the user
		 */
		lblSprinklerS6.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				configFrame = new JFrame("Sprinkler Configuartion");
				configFrame.setSize(dim.width, dim.height);

				configFrame.setVisible(true);

			}
		});
		
		JLabel statusSpklrS6 = new JLabel("sprinkler S6");
		statusSpklrS6.setHorizontalAlignment(SwingConstants.CENTER);
		southPane.add(lblSprinklerS6);
		southPane.add(statusSpklrS6);

		// Adding the West Pane to the Status Panel Grid

		statusPane.add(southPane);

		/*******************************************************************************************************************************************************/

		/*
		 * Panel for water consumption graph with a combo box to choose between
		 * Sprinkler, Sprinkler Group, Sprinkler System and it has a BoxLayout
		 */
		WaterConsumptionGraphPane = new JPanel();
		WaterConsumptionGraphPane.setPreferredSize(new Dimension(150, 300));
		WaterConsumptionGraphPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2),
				"Water Consumption Graph", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		WaterConsumptionGraphPane.setBackground(new Color(128, 255, 128));

		// JComboBox with vector values Sprinkler, Sprinkler Group, Sprinkler
		// System
		Vector<String> v = new Vector<String>();

		v.add("Sprinkler System");
		v.add("Sprinkler");
		v.add("SprinklerGroup");

		Select = new JComboBox<String>(v);
		WaterConsumptionGraphPane.add(Select);
		frame.getContentPane().add(WaterConsumptionGraphPane);

		// Panel for temperature Settings with Flowlayout and it has 3 buttons

		FunctionalityPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
		FunctionalityPane.setBackground(new Color(128, 255, 128));
		FunctionalityPane.setPreferredSize(new Dimension(150, 90));
		FunctionalityPane.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));

		btnOverrideButton = new JButton("Override Temperature");
		btnOverrideButton.setActionCommand("");
		btnOverrideButton.setPreferredSize(new Dimension(200, 40));
		FunctionalityPane.add(btnOverrideButton);
		btnAdjustTemp = new JButton("Adjust Temperature");
		btnAdjustTemp.setPreferredSize(new Dimension(200, 40));
		FunctionalityPane.add(btnAdjustTemp);
		btnNextScreen = new JButton("Sprinkler Configuration");
		btnNextScreen.setPreferredSize(new Dimension(200, 40));
		btnNextScreen.setFocusPainted(true);
		FunctionalityPane.add(btnNextScreen);

		frame.getContentPane().add(FunctionalityPane);

		frame.setVisible(true);

	}

	private void setIconImage(BufferedImage read) {
		// TODO Auto-generated method stub

	}

}
