package humming.bee.sprinkler.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import humming.bee.sprinkler.service.Sprinkler;
import humming.bee.sprinkler.service.SprinklerService;

public class HomeScreen {

	private JFrame frame;
	private JFrame welcomeScreen;

	private JFrame configFrame;
	private JPanel titlePane;
	private JPanel statusPane;
	private JPanel functionalityPane;
	private JPanel waterConsumptionGraphPane;
	private JComboBox<String> Select;
	private JButton btnOverrideButton;
	private JButton btnNextScreen;
	private JButton btnSetTemp;
	private JPanel northPane;
	private JPanel eastPane;
	private JPanel westPane;
	private JPanel southPane;

	// URL iconURL =
	// getClass().getResource("/hb-sprinkler/src/humming/bird/sprinkler/res/WateringCan.png");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					// UIManager.put("nimbusBase", Color.nimbusOrange);
					UIManager.put("nimbusBlueGrey", new Color(128, 255, 102));
					// UIManager.put("control", new Color(204,255,229));
					UIManager.put("control", new Color(255, 255, 255));

					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
				} catch (Exception e) {
					// If Nimbus is not available, you can set the GUI to
					// another look and feel.
				}
				try {
					HomeScreen window = new HomeScreen();
					// window.welcomeScreen.setVisible(true);
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
	public HomeScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dim = toolkit.getScreenSize();

		JFrame.setDefaultLookAndFeelDecorated(false);

		/*******************************************************************************************************************************************************/
		// Frame with Box Layout and 4 panels
		frame = new JFrame();
		configFrame = new JFrame();
		/*******************************************************************************************************************************************************/
		// Welcome Scren with Border Layout and 4 panels
		/*
		 * welcomeScreen = new JFrame("welcomeScreen");
		 * 
		 * welcomeScreen.setSize(dim.width, dim.height);
		 * welcomeScreen.setLayout(new BorderLayout()); JLabel background=new
		 * JLabel(new ImageIcon("/res/welcome.jpg"));
		 * welcomeScreen.add(background); background.setLayout(new
		 * FlowLayout());
		 * 
		 * welcomeScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 * welcomeScreen.getContentPane(); JButton enter = new JButton("ENTER");
		 * enter.addMouseListener(new MouseAdapter() { public void
		 * mouseClicked(MouseEvent e) { frame.setVisible(true); } });
		 * welcomeScreen.add(enter,BorderLayout.SOUTH);
		 */

		// Gets the current Screen size and set it to the Frame
		frame.setSize(dim.width, dim.height);
		frame.setTitle("Garden Sprinkler System simulation  by Aarthi and Priya");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

		titlePane();
		buttonPane();
		statusPane();
		waterConsumptionGraph();

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, statusPane, functionalityPane);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		splitPane.setResizeWeight(0.8f);
	}

	/*******************************************************************************************************************************************************/
	// Title panel to display the Title,Time and temperature
	private void titlePane() {

		titlePane = new JPanel();

		// This part is for getting System Date
		DateFormat dateFormat = new SimpleDateFormat("h:mm a  MMM d, ''yy");
		Date date = new Date();

		EmptyBorder border = new EmptyBorder(3, 0, 6, 50);
		EmptyBorder border1 = new EmptyBorder(3, 0, 6, 450);

		titlePane.setPreferredSize(new Dimension(150, 50));
		titlePane.setBorder(new LineBorder(new Color(102, 0, 0), 2));
		
		// Get the URL of image to set icon
		ImageIcon icon = new ImageIcon(getClass().getResource("/res/wateringcan.png"));
		JLabel title = new JLabel();
		title.setIcon(icon);
		title.setText("HUMMING BEE HOME GARDEN SPRINKLER SYSTEM");
		title.setIconTextGap(5);
		title.setBorder(border1);
		titlePane.add(title, titlePane.CENTER_ALIGNMENT);

		ImageIcon time = new ImageIcon(getClass().getResource("/res/time.png"));
		JLabel lblTime = new JLabel(dateFormat.format(date));
		lblTime.setIcon(time);
		lblTime.setIconTextGap(5);
		lblTime.setBorder(border);
		titlePane.add(lblTime);

		ImageIcon weather = new ImageIcon(getClass().getResource("/res/Weather-icon.png"));
		JLabel lblWeather = new JLabel("57 ° F");
		lblWeather.setIcon(weather);
		lblWeather.setIconTextGap(5);
		lblWeather.setBorder(border);

		titlePane.add(lblWeather, titlePane.RIGHT_ALIGNMENT);

		/*
		 * title.setSize(new Dimension(150, 20)); titlePane.add(title);
		 */
		frame.getContentPane().add(titlePane, BorderLayout.NORTH);

	}

	/*******************************************************************************************************************************************************/
	// Status panel to display the status of Sprinklers
	private void statusPane() {
		statusPane = new JPanel();
		statusPane.setPreferredSize(new Dimension(200, 200));
		statusPane.setBorder(new TitledBorder(new LineBorder(new Color(102, 0, 0), 2), "Sprinkler Status",
				TitledBorder.CENTER, TitledBorder.TOP, null, null));

		// Set Grid Layout for the Status panel with 2 Rows and 2 Columns
		statusPane.setLayout(new GridLayout(2, 2));

		northPane();
		eastPane();
		westPane();
		southPane();

	}

	/*------------------------------------------------------------------------------------------------------------------- ----------------*/
	// North pane of the Status panel

	private void northPane() {

		List<Sprinkler> sprinklerList = new ArrayList<Sprinkler>();
		SprinklerService service = new SprinklerService();
		sprinklerList = service.getStatus();
		ImageIcon iconOn = new ImageIcon(getClass().getResource("/res/ON.png"));
		ImageIcon iconOff = new ImageIcon(getClass().getResource("/res/off.png"));
		ImageIcon iconNotWorking = new ImageIcon(getClass().getResource("/res/not-working.png"));

		northPane = new JPanel();
		northPane.setPreferredSize(new Dimension(100, 100));

		northPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 102, 0), 2), "North Group",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		// Set Grid Layout for the Status panel with 2 Rows and 3 Columns
		northPane.setLayout(new GridLayout(3, 4, 3, 3));

		for (int i = 0; i < sprinklerList.size(); i++) {
			if (sprinklerList.get(i).getGroupId() == 10) {

				JPanel sprinklerpane = new JPanel();
				sprinklerpane.setLayout(new GridLayout(1, 2));

				JButton btnSprinkler = new JButton();
				btnSprinkler.setText(sprinklerList.get(i).getSprinklerName());
				// btnSprinkler.setIcon(icon);
				/*
				 * btnSprinkler.setBorderPainted(false);
				 * btnSprinkler.setFocusPainted(false);
				 * btnSprinkler.setContentAreaFilled(false);
				 * btnSprinkler.setOpaque(false);
				 */
				btnSprinkler.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						configFrame.setVisible(true);
						configFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					}
				});
				// iconON
				JLabel statusSpklr = new JLabel();
				// statusSpklr.setText(sprinklerList.get(i).getSprinklerStatus());
				if (sprinklerList.get(i).getSprinklerStatus().equals("ON")) {
					statusSpklr.setIcon(iconOn);
				} else if (sprinklerList.get(i).getSprinklerStatus().equals("OFF")) {
					statusSpklr.setIcon(iconOff);
				} else if (sprinklerList.get(i).getSprinklerStatus().equals("NOTFUNCTIONAL")) {
					statusSpklr.setIcon(iconNotWorking);
				}

				statusSpklr.setHorizontalAlignment(SwingConstants.LEFT);
				statusSpklr.setBorder(new EmptyBorder(0, 10, 0, 0));
				sprinklerpane.add(btnSprinkler);
				sprinklerpane.add(statusSpklr);
				northPane.add(sprinklerpane);
			}
		}
		// Adding the North Pane to the Status Panel Grid
		statusPane.add(northPane);
	}

	/*------------------------------------------------------------------------------------------------------------------- ----------------*/
	// East pane of the status pane
	private void eastPane() {
		eastPane = new JPanel();
		eastPane.setPreferredSize(new Dimension(100, 100));

		eastPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 102, 0), 2), "East Group", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		// Set GridLayout for the East Pane with 2 Rows and 3 Columns
		eastPane.setLayout(new GridLayout(3, 4, 2, 3));

		ImageIcon icon = new ImageIcon(getClass().getResource("/res/ON.png"));

		java.util.List<String> sprinklerNames = new ArrayList<String>();
		sprinklerNames.add("SprinklerE1");
		sprinklerNames.add("SprinklerE2");
		sprinklerNames.add("SprinklerE3");
		sprinklerNames.add("SprinklerE4");
		sprinklerNames.add("SprinklerE5");
		sprinklerNames.add("SprinklerE6");

		for (String sprinklerName : sprinklerNames) {
			JPanel sprinklerpane = new JPanel();
			sprinklerpane.setLayout(new GridLayout(1, 2));
			// sprinklerpane.setBackground(new Color(255, 255, 255));

			JButton btnSprinkler = new JButton(sprinklerName);
			btnSprinkler.setOpaque(true);
			btnSprinkler.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					configFrame.setVisible(true);
					configFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				}
			});

			JLabel statusSpklr = new JLabel(icon);
			statusSpklr.setHorizontalAlignment(SwingConstants.LEFT);
			statusSpklr.setBorder(new EmptyBorder(0, 10, 0, 0));
			sprinklerpane.add(btnSprinkler);
			sprinklerpane.add(statusSpklr);
			eastPane.add(sprinklerpane);
		}

		// Adding the East Pane to the Status Panel Grid
		statusPane.add(eastPane);
	}

	/*------------------------------------------------------------------------------------------------------------------- ----------------*/
	// West pane of the status pane
	private void westPane() {
		westPane = new JPanel();
		westPane.setPreferredSize(new Dimension(100, 100));

		westPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 102, 0), 2), "West Group", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		// Set GridLayout for the West Pane with 2 Rows and 3 Columns
		westPane.setLayout(new GridLayout(3, 4, 2, 3));
		ImageIcon icon = new ImageIcon(getClass().getResource("/res/off.png"));

		java.util.List<String> sprinklerNames = new ArrayList<String>();
		sprinklerNames.add("SprinklerW1");
		sprinklerNames.add("SprinklerW2");
		sprinklerNames.add("SprinklerW3");
		sprinklerNames.add("SprinklerW4");
		sprinklerNames.add("SprinklerW5");
		sprinklerNames.add("SprinklerW6");

		for (String sprinklerName : sprinklerNames) {
			JPanel sprinklerpane = new JPanel();
			sprinklerpane.setLayout(new GridLayout(1, 2));
			// 128,255,128

			JButton btnSprinkler = new JButton(sprinklerName);
			btnSprinkler.setOpaque(true);
			btnSprinkler.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					configFrame.setVisible(true);
					configFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				}
			});

			JLabel statusSpklr = new JLabel(icon);
			statusSpklr.setHorizontalAlignment(SwingConstants.LEFT);
			statusSpklr.setBorder(new EmptyBorder(0, 10, 0, 0));
			sprinklerpane.add(btnSprinkler);
			sprinklerpane.add(statusSpklr);
			westPane.add(sprinklerpane);
		}

		// Adding the West Pane to the Status Panel Grid
		statusPane.add(westPane);
	}

	/*------------------------------------------------------------------------------------------------------------------- ----------------*/
	// South pane of the status pane
	private void southPane() {
		southPane = new JPanel();
		southPane.setPreferredSize(new Dimension(100, 100));

		southPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 102, 0), 2), "South Group",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		// Set GridLayout for the southPane with 2 Rows and 3 Columns
		southPane.setLayout(new GridLayout(3, 4, 3, 2));

		java.util.List<String> sprinklerNames = new ArrayList<String>();
		sprinklerNames.add("SprinklerS1");
		sprinklerNames.add("SprinklerS2");
		sprinklerNames.add("SprinklerS3");
		sprinklerNames.add("SprinklerS4");
		sprinklerNames.add("SprinklerS5");
		sprinklerNames.add("SprinklerS6");

		for (String sprinklerName : sprinklerNames) {
			JPanel sprinklerpane = new JPanel();
			sprinklerpane.setLayout(new GridLayout(1, 2));

			JButton btnSprinkler = new JButton(sprinklerName);
			btnSprinkler.setOpaque(true);
			btnSprinkler.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					configFrame.setVisible(true);
					configFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				}
			});

			JLabel statusSpklr = new JLabel("ON");
			statusSpklr.setHorizontalAlignment(SwingConstants.LEFT);
			statusSpklr.setBorder(new EmptyBorder(0, 10, 0, 0));
			sprinklerpane.add(btnSprinkler);
			sprinklerpane.add(statusSpklr);
			southPane.add(sprinklerpane);
		}

		// Adding the West Pane to the Status Panel Grid
		statusPane.add(southPane);
	}

	/*******************************************************************************************************************************************************/

	/*
	 * Panel for water consumption graph with a combo box to choose between
	 * Sprinkler, Sprinkler Group, Sprinkler System and it has a BoxLayout
	 */
	private void waterConsumptionGraph() {

		JPanel waterConsumptionGraphPane = new JPanel();
		waterConsumptionGraphPane.setPreferredSize(new Dimension(150, 300));
		waterConsumptionGraphPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 102, 0), 2),
				"Water Consumption Graph", TitledBorder.CENTER, TitledBorder.TOP, null, null));

		// waterConsumptionGraphPane = new
		// JScrollPane(graphPane,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
		// ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// JComboBox with vector values Sprinkler, Sprinkler Group, Sprinkler
		// System
		Vector<String> v = new Vector<String>();

		v.add("Sprinkler System");
		v.add("Sprinkler");
		v.add("SprinklerGroup");

		Select = new JComboBox<String>(v);
		waterConsumptionGraphPane.add(Select);
		frame.add(waterConsumptionGraphPane, BorderLayout.SOUTH);

	}

	// Panel for temperature Settings with Flowlayout and it has 3 buttons
	private void buttonPane() {
		functionalityPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));

		functionalityPane.setPreferredSize(new Dimension(150, 90));
		functionalityPane.setBorder(new LineBorder(new Color(0, 102, 0), 2, true));

		JPanel overrideTemp = new JPanel();
		overrideTemp.setLayout(new GridLayout(2, 3));

		overrideTemp.add(new JLabel("Upper_Limit"));
		Vector<String> v = new Vector<String>();
		v.add("75° F");
		v.add("80° F");
		v.add("85° F");
		JComboBox<String> upperLimit = new JComboBox<String>(v);
		overrideTemp.add(upperLimit);

		overrideTemp.add(new JLabel("Lower_Limit"));
		Vector<String> v1 = new Vector<String>();
		v1.add("40° F");
		v1.add("45° F");
		v1.add("50° F");
		JComboBox<String> lowerLimit = new JComboBox<String>(v1);
		overrideTemp.add(lowerLimit);
		overrideTemp.add(new JLabel("Frequency"));
		overrideTemp.add(new JTextField("Frequency"));
		overrideTemp.add(new JLabel("Duration"));
		overrideTemp.add(new JTextField("duration"));

		btnOverrideButton = new JButton("Temperature Setting");
		btnOverrideButton.setActionCommand("");
		btnOverrideButton.setPreferredSize(new Dimension(180, 50));
		// btnOverrideButton.setBackground(new Color(128, 255, 128));
		btnOverrideButton.setOpaque(true);
		btnOverrideButton.setBorderPainted(false);

		btnOverrideButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(null, overrideTemp, "Temperature Setting",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			}
		});
		functionalityPane.add(btnOverrideButton);

		JPanel changeTemperature = new JPanel();
		changeTemperature.setLayout(new GridLayout(1, 1));
		changeTemperature.add(new JLabel("Enter the new Temperature"));
		changeTemperature.add(new JTextField());

		btnSetTemp = new JButton("Change Temperature");
		btnSetTemp.setPreferredSize(new Dimension(180, 50));
		// btnSetTemp.setBackground(new Color(128, 255, 128));
		btnSetTemp.setOpaque(true);
		btnSetTemp.setBorderPainted(false);

		btnSetTemp.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(null, changeTemperature, "Change Temperature",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			}
		});
		functionalityPane.add(btnSetTemp);

		btnNextScreen = new JButton("Sprinkler Config");
		btnNextScreen.setPreferredSize(new Dimension(180, 50));
		// btnNextScreen.setBackground(new Color(128, 255, 128));
		btnNextScreen.setOpaque(true);
		btnNextScreen.setBorderPainted(false);
		btnNextScreen.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				configFrame.setVisible(true);
				configFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			}
		});
		functionalityPane.add(btnNextScreen);

	}

}
