/**
 * 
 */
package humming.bee.sprinkler.ui;

import humming.bee.sprinkler.service.Sprinkler;
import humming.bee.sprinkler.service.SprinklerGroup;
import humming.bee.sprinkler.service.SprinklerService;
import humming.bee.sprinkler.service.SprinklerStatus;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;


/**
 * 
 *
 */
public class Settings extends JFrame {

	JFrame frame;
	Container contentPane;
	
	//Week newWeek;
	//WeeklySchedule wSchedule;
	
	Schedule schedule;
	
	AddSetting addSetting;
	
	
	private JLabel lblStatus;
	//private JCheckBox chkOff;
	//private JCheckBox chkOn;
	private JButton btnOn;
	private JButton btnOff;
	private JCheckBox chkFunctional;
	private JButton btnSave;
	private JButton btnCancel;
	
	private JPanel schedulePane;
	
	JPanel settingPane;
	
	private SprinklerSelection selectSprinkler;
	private String selectedId;//to identify which sprinkler/group is selected in SprinklerSelection component.
	private boolean isSprinklerSelected=false;//to identify whether group or sprinkler is selected in SprinklerSelection component.
	
	SprinklerService sprinklerService=new SprinklerService();
	SprinklerGroup sprinklerGroup=new SprinklerGroup();
	
	
	private final int turnOffDelayMinutes=1;

	/**
	 * Create the frame.
	 */
	public Settings() {
		
		super("Humming Bee - Configuration");
		
		frame=this;//current frame
		setLayout(new BorderLayout());
		contentPane = getContentPane();
		
		//set default size and position for this frame		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	int height = screenSize.height;
    	int width = screenSize.width;
    	frame.setSize(screenSize.width, screenSize.height);
    	//setMaximumSize(new Dimension(width*3/4, height*3/4));
    	setLocationRelativeTo(null);
    	setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    	//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
		
		
		//add group and sprinkler dropdowns and show button
		selectSprinkler=new SprinklerSelection();
		//show button click
		selectSprinkler.setSelectionListener(new SprinklerSelectionListener() {
			
			@Override
			public void selectionChanged(String id,boolean isSprinkler) {
				// set selectedId and group/sprinkler flag to populate controls
				selectedId=id;
				isSprinklerSelected=isSprinkler;
				//get sprinkler/group details and populate controls
				
				if(isSprinklerSelected)//populate selected sprinkler details
				{
					//System.out.println("s");
					//reload Schedule panel and update panel
					
					//repopulate week panel(JTables)
					//newWeek.refreshWeek();
					schedulePane.removeAll();
					schedulePane.add(new Schedule(selectedId, isSprinklerSelected));
					schedulePane.repaint();
					
					//repopulate update panel
					populateSprinklerStatus();
					
				}
				else//populate selected group details
				{
					
					//System.out.println("g");
					//reload Week panel and update panel
					
					//repopulate week panel(JTables)
					//newWeek.refreshWeek();
					//System.out.println("selectionChanged:"+selectedId);
					schedulePane.removeAll();
					schedulePane.add(new Schedule(selectedId, isSprinklerSelected));
					schedulePane.repaint();
					
					//repopulate update panel
					populateGroupStatus();
				}
				
			}
		});
		contentPane.add(selectSprinkler,BorderLayout.NORTH);
			
		//add the update setting panel
		createUpdatePanel();
		createSchedulePanel();
		
		
		//*************************************
		//set default selected group as North
		this.selectedId="North";
		this.isSprinklerSelected=false;
		//populate schedule panel and update panel accordingly
		schedulePane.removeAll();
		schedulePane.add(new Schedule(selectedId, isSprinklerSelected));
		schedulePane.repaint();
		//repopulate update panel
		populateGroupStatus();
		//*************************************
		
				
	}
	
	/**
	 * Creates and adds schedule panel
	 * Schedule panel displays the schedule settings for selected sprinkler/group
	 */
	private void createSchedulePanel()
	{
		//newWeek=new Week(selectedId);
		//contentPane.add(newWeek,BorderLayout.SOUTH);
		//System.out.println("first time load:"+selectedId);
		schedule=new Schedule(selectedId, isSprinklerSelected);
		schedulePane=new JPanel();
		schedulePane.setLayout(new FlowLayout(FlowLayout.CENTER));
		schedulePane.add(schedule);
		contentPane.add(schedulePane,BorderLayout.CENTER);//add to content pane
	}
	
	/**
	 * Creates and adds updatePane and addSettingPane
	 * updatePane shows: on/off status, functional/not-functional status and allows to change these settings
	 * addSettingPane allows to add new schedule setting for selected sprinkler/group
	 */
	private void createUpdatePanel()
	{
		settingPane=new JPanel();
		settingPane.setLayout(new BoxLayout(settingPane, BoxLayout.Y_AXIS));
		
		//************************update pane*****************************//
		JPanel updatePane=new JPanel();
		updatePane.setLayout(new FlowLayout(FlowLayout.CENTER,30,5));
		Border setBorder=BorderFactory.createTitledBorder("Settings");
		updatePane.setBorder(setBorder);
		
		lblStatus=new JLabel();
		
		//chkOff=new JCheckBox("OFF");
		//chkOn=new JCheckBox("ON");
		btnOn=new JButton("ON");
		btnOff=new JButton("OFF");
		
		//On button click
		btnOn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String message;
				if(isSprinklerSelected)//Sprinkler
				{
					message="Are you sure you want to turn on this sprinkler now?";
					//turn on the sprinkler for 1 hour
					int response = JOptionPane.showConfirmDialog(null, message, "Confirm",
					        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					
					if(response==JOptionPane.YES_OPTION)
					{
						
						//update status=on to db
						sprinklerService.updateSprinklerStatus(selectedId, SprinklerStatus.ON.toString());
						
						int sprinklerId=sprinklerService.getSprinklerByName(selectedId).getSprinklerId();
						
						//add sprinklerRunTime - start time.
						int runTimeId=sprinklerService.addSprinklerRunTime(sprinklerId);
						//set timer to turn off this sprinkler after a fixed time
						turnOffDelayTimer(runTimeId);
						
						//refresh update pane for sprinkler
						populateSprinklerStatus();
						
					}
				}
				else//group
				{
					message="Are you sure you want to turn on this sprinkler group now?";
					//turn on the sprinkler for 1 hour
					int response = JOptionPane.showConfirmDialog(null, message, "Confirm",
					        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					
					if(response==JOptionPane.YES_OPTION)
					{
						//update status=on to db
						sprinklerGroup.updateSprinklerGroupStatus(selectedId, SprinklerStatus.ON.toString());
						//set timer to turn off this group after a fixed time
						
						SprinklerGroup gService=new SprinklerGroup();
						int groupId=gService.getSprinklerGroupByName(selectedId).getGroupId();
						//add groupRunTime - start time
						int gRunTimeId=sprinklerService.addGroupRunTime(groupId);
						turnOffDelayTimer(gRunTimeId);
						
						
						//refresh update pane for group
						populateGroupStatus();
						
					}
				}
				
				
				
			}
		});
		
		//Off button click
		btnOff.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// turn off the sprinkler/group
				
				String message;
				if(isSprinklerSelected)//Sprinkler
				{
					message="Are you sure you want to turn off this sprinkler now?";
					//turn on the sprinkler for 1 hour
					int response = JOptionPane.showConfirmDialog(null, message, "Confirm",
					        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					
					if(response==JOptionPane.YES_OPTION)
					{
						//update status=off to db
						sprinklerService.updateSprinklerStatus(selectedId, SprinklerStatus.OFF.toString());
						//update sprinkler run time - end_time
						int sprinklerId=sprinklerService.getSprinklerByName(selectedId).getSprinklerId();
						int runTimeId=sprinklerService.getSprinklerRunTimeToUpdate(sprinklerId).getId();
						sprinklerService.updateSprinklerRunTime(runTimeId);
						//refresh update pane for sprinkler
						populateSprinklerStatus();
						
					}
				}
				else//group
				{
					message="Are you sure you want to turn off this sprinkler group now?";
					//turn on the sprinkler for 1 hour
					int response = JOptionPane.showConfirmDialog(null, message, "Confirm",
					        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					
					if(response==JOptionPane.YES_OPTION)
					{
						//update status=off to db
						sprinklerGroup.updateSprinklerGroupStatus(selectedId, SprinklerStatus.OFF.toString());
						
						//update group run time - end_time
						int groupId=sprinklerGroup.getSprinklerGroupByName(selectedId).getGroupId();
						int gRunTimeId=sprinklerService.getGroupRunTimeToUpdate(groupId).getGroupId();
						sprinklerService.updateGroupRunTime(gRunTimeId);
						
						//refresh update pane for group
						populateGroupStatus();
						
					}
				}
			}
		});
		
		chkFunctional=new JCheckBox("Functional");
		btnSave=new JButton("Save");
		//save button click - only for sprinklers
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(isSprinklerSelected)//sprinkler functional data save
				{
					int response;
					int fn=(chkFunctional.isSelected())?1:0;//functional=1, not-functional=0
					//handle condition for chkFunctional 
					if(chkFunctional.isSelected())//made it functional
					{
						response = JOptionPane.showConfirmDialog(null, "Are you sure you want to set this sprinkler as Functional?", "Confirm",
						        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						
						if(response==JOptionPane.YES_OPTION)
						{
							//save functional to db
							sprinklerService.updateSprinklerFunctional(selectedId, fn);
							//show addSetting and schedule panes
							addSetting.setVisible(true);
							//newWeek.setVisible(true);
							schedule.setVisible(true);
						}
						
					}
					else//made it not-functional
					{
						response = JOptionPane.showConfirmDialog(null, "Are you sure you want to set this sprinkler as Not Functional?", "Confirm",
						        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						
						if(response==JOptionPane.YES_OPTION)
						{
							//save not functional and status=off to db
							sprinklerService.updateSprinklerFunctional(selectedId, fn);
							//disable addSetting and schedule panes
							addSetting.setVisible(false);
							//newWeek.setVisible(false);
							schedule.setVisible(false);
						}
						
					}
				}
				
				
			}
				
				
		});
		
		/*btnCancel=new JButton("Cancel");
		//cancel button click
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Do not update any details. Close the window.
				frame.dispose();			
			}
		});*/
		
		
		//group controls in panels
		JPanel textPane=new JPanel();
		textPane.add(lblStatus);
		JPanel statusPane=new JPanel();
		statusPane.add(btnOff);//statusPane.add(chkOff);
		statusPane.add(btnOn);//statusPane.add(chkOn);
		JPanel functionalPane=new JPanel();
		functionalPane.add(chkFunctional);
		
		
		/*updatePane.add(lblStatus);
		updatePane.add(chkOff);
		updatePane.add(chkOn);
		updatePane.add(chkFunctional);*/
		
		//add controls to updatepanel
		updatePane.add(textPane);
		updatePane.add(statusPane);
		updatePane.add(functionalPane);
		//updatePane.add(btnCancel);
		updatePane.add(btnSave);
		//**************************************************************//
		
		//*********************addSetting pane**************************//
		addSetting=new AddSetting(selectedId,isSprinklerSelected);
		
		addSetting.setScheduleListener(new ScheduleListener() {
			
			@Override
			public void scheduleAdded() {
				// refresh Schedule panel
				System.out.println("3."+selectedId);
				schedulePane.removeAll();
				schedulePane.add(new Schedule(selectedId, isSprinklerSelected));
				schedulePane.validate();
				schedulePane.repaint();
				
			}
		});
		
		//**************************************************************//
		
		//add components to setting pane
		settingPane.add(updatePane);
		
		
		settingPane.add(addSetting);
		
		
		
		//add setting pane to content pane
		contentPane.add(settingPane,BorderLayout.SOUTH);
		
		
	}
	
	
	/**
	 * Populate update pane with sprinkler details
	 * 
	 */
	private void populateSprinklerStatus()
	{
		//get data
		Sprinkler newSprinkler=sprinklerService.getSprinklerByName(selectedId);
		
		chkFunctional.setVisible(true);
		btnSave.setVisible(true);
		//btnCancel.setVisible(true);
		
		if(newSprinkler.isFunctional())
		{
			chkFunctional.setSelected(true);//checks functional checkbox.
			addSetting=new AddSetting(selectedId, isSprinklerSelected);
			addSetting.setScheduleListener(new ScheduleListener() {
				
				@Override
				public void scheduleAdded() {
					// refresh Schedule panel
					System.out.println("1."+selectedId);
					schedulePane.removeAll();
					schedulePane.add(new Schedule(selectedId, isSprinklerSelected));
					schedulePane.validate();
					schedulePane.repaint();
					
				}
			});
			//settingPane.removeAll();
			settingPane.remove(1);
			settingPane.add(addSetting, 1);
			addSetting.setVisible(true);
			//newWeek.setVisible(true);
			schedule.setVisible(true);
			
			if(newSprinkler.getSprinklerStatus().equals("OFF"))
			{
				//if sprinkler is off, show ON option 
				////chkOff.setSelected(true);
				btnOff.setVisible(false);//chkOff.setVisible(false);
				btnOn.setVisible(true);//chkOn.setSelected(false);
				//chkOn.setVisible(true);
				
				//show status as text
				lblStatus.setText("Status:OFF");
				//show 'off' icon 
				
				
			}
			else if(newSprinkler.getSprinklerStatus().equals("ON"))
			{
				//if sprinkler is on, show OFF option 
				btnOff.setVisible(true);//chkOff.setSelected(false);
				btnOn.setVisible(false);//chkOff.setVisible(true);
				////chkOn.setSelected(true);
				//chkOn.setVisible(false);
				//show status as text
				lblStatus.setText("Status:ON");
				//show 'on' icon 
			}
			
		}
		else//sprinkler is not functional
		{
			//uncheck functional checkbox
			chkFunctional.setSelected(false);
			//lblstatus=off and not functional
			lblStatus.setText("Status:OFF, Not Functional");
			//icon=not functional image
			
			//disable add settings/weekly schedule panel
			addSetting.setVisible(false);
			//newWeek.setVisible(false);
			schedule.setVisible(false);
			//only the functional checkbox and save button will be accessible
			btnOff.setVisible(false);//chkOn.setVisible(false);
			btnOn.setVisible(false);//chkOff.setVisible(false);

		}
	}
	
	/**
	 * Populates updatePane with sprinkler group details
	 * 
	 */
	private void populateGroupStatus()
	{
		//System.out.println("inside populateGroupStatus");
		//get data
		SprinklerGroup newGroup=sprinklerGroup.getSprinklerGroupByName(selectedId);
		
		chkFunctional.setVisible(false);//No functional checkbox in this case.
		btnSave.setVisible(false);
		//btnCancel.setVisible(false);
		
		//initialize addSetting
		addSetting=new AddSetting(selectedId, isSprinklerSelected);
		addSetting.setScheduleListener(new ScheduleListener() {
			
			@Override
			public void scheduleAdded() {
				// refresh Schedule panel
				System.out.println("2."+selectedId);
				schedulePane.removeAll();
				schedulePane.add(new Schedule(selectedId, isSprinklerSelected));
				schedulePane.validate();
				schedulePane.repaint();
				
			}
		});
		settingPane.remove(1);
		settingPane.add(addSetting, 1);
		addSetting.setVisible(true);
		
		//newWeek.setVisible(true);
		schedule.setVisible(true);
		//on/off button and status text management
		if(newGroup.getStatus().equals(SprinklerStatus.OFF.toString()))
		{
			//if group is off, show ON option 
			
			btnOff.setVisible(false);
			btnOn.setVisible(true);
			
			
			//show status as text
			lblStatus.setText("Status:OFF");
			//show 'off' icon 
			
			
		}
		else if(newGroup.getStatus().equals(SprinklerStatus.ON.toString()))
		{
			//if group is on, show OFF option 
			btnOff.setVisible(true);
			btnOn.setVisible(false);
			
			//show status as text
			lblStatus.setText("Status:ON");
			//show 'on' icon 
		}
	}
	
	/**
	 * Turn off sprinkler/group after a delay
	 * @param runTimeId
	 */
	private void turnOffDelayTimer(int runTimeId)
	{
        Thread worker = new Thread() {
        	 
            public void run(){
         
            try
            { 
            	Thread.sleep(1000*60); //1 minutes delay
            }
            catch(InterruptedException ex){}

            SwingUtilities.invokeLater( new Runnable()
            {
              public void run()
              {
            	  //if sprinkler,
            	  if(isSprinklerSelected)
            	  {
	            	  //turn off sprinkler after a fixed time
	          			
	          			//set status=off if sprinkler is ON
	            	  if(sprinklerService.getSprinklerByName(selectedId).getSprinklerStatus().equals(SprinklerStatus.ON.toString()))
	            	    sprinklerService.updateSprinklerStatus(selectedId, SprinklerStatus.OFF.toString());
	          			//update sprinklerRunTime end_time=currenttime
	            	    sprinklerService.updateSprinklerRunTime(runTimeId);
            	  }
            	  else//group
            	  {
            		 //turn off group after a fixed time
            		  
            		  //set status=off if group is ON
            		  SprinklerGroup gService=new SprinklerGroup();
            		  if(gService.getSprinklerGroupByName(selectedId).getStatus().equals(SprinklerStatus.ON.toString()))
            		  {
            			  
            			  gService.updateSprinklerGroupStatus(selectedId, SprinklerStatus.OFF.toString());
            			  //update groupRunTime end_time=currenttime
            			  sprinklerService.updateGroupRunTime(runTimeId);
            		  }
            		  
            	  }
            	  
              }
             });
            }
            };

             worker.start(); 

      	
	}

}
