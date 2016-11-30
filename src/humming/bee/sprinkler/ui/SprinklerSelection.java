/**
 * 
 */
package humming.bee.sprinkler.ui;

import humming.bee.sprinkler.service.Sprinkler;
import humming.bee.sprinkler.service.SprinklerService;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 *
 */
public class SprinklerSelection extends JPanel {

	private SprinklerService sprinklerService=new SprinklerService();
	
	private JComboBox<String> cmbSprinklerGroup;
	private JComboBox<String> cmbSprinkler;
	private JButton btnShow;
	
	private SprinklerSelectionListener selectionListener;
	
	//setter
	public void setSelectionListener(SprinklerSelectionListener selectionListener) {
		this.selectionListener = selectionListener;
	}


	/**
	 * Create the panel.
	 */
	public SprinklerSelection() {

		
		setLayout(new FlowLayout(FlowLayout.CENTER,30,5));
		
		cmbSprinklerGroup=new JComboBox<String>();
		DefaultComboBoxModel<String> groupModel=new DefaultComboBoxModel<String>();
		//groupModel.addElement("Select");
		groupModel.addElement("North");
		groupModel.addElement("East");
		groupModel.addElement("West");
		groupModel.addElement("South");
		cmbSprinklerGroup.setModel(groupModel);
		cmbSprinklerGroup.setSelectedIndex(0);
		
		
		cmbSprinkler=new JComboBox();
		cmbSprinkler.addItem("Select");
		populateSprinklers();
		
		
		/*DefaultComboBoxModel<String> sprinklerModel=new DefaultComboBoxModel<String>();
		sprinklerModel.addElement("1N");
		sprinklerModel.addElement("2N");
		sprinklerModel.addElement("3N");
		cmbSprinkler.setModel(sprinklerModel);*/
		//cmbSprinkler.setSelectedIndex(0);
		
		btnShow=new JButton("Show");
		btnShow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// display weekly schedule for the selected sprinkler/group
				if(cmbSprinkler.getSelectedIndex()==0)//yes, only group is selected.
				{
					String selectedGroup=cmbSprinklerGroup.getSelectedItem().toString();
					selectionListener.selectionChanged(selectedGroup,false);
				}
				else//sprinkler is also selected
				{
					String selectedSprinkler=cmbSprinkler.getSelectedItem().toString();
					selectionListener.selectionChanged(selectedSprinkler,true);
				}
			}
		});
		
		
		//Populate sprinklers by group name 
		//when a new group is selected
		cmbSprinklerGroup.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				//repopulate Sprinkler combobox when a new group is selected
				if (e.getStateChange() == ItemEvent.SELECTED) {
			          // get sprinklers in newly selected group
					  populateSprinklers();
			          
			       }
				
			}
		});
		
		JPanel groupPane=new JPanel();
		groupPane.add(new JLabel("Group:"));
		groupPane.add(cmbSprinklerGroup);
		
		JPanel sprinklerPane=new JPanel();
		sprinklerPane.add(new JLabel("Sprinkler:"));
		sprinklerPane.add(cmbSprinkler);
		
		add(groupPane);
		add(sprinklerPane);
		add(btnShow);
		
		
		/*
		add(new JLabel("Group:"));
		add(cmbSprinklerGroup);
		add(new JLabel("Sprinkler"));
		add(cmbSprinkler);
		add(btnShow);*/
		
	}
	
	private void populateSprinklers()
	{
		List<Sprinkler> sprinklers=sprinklerService.getSprinklersByGroup(cmbSprinklerGroup.getSelectedItem().toString());
        cmbSprinkler.removeAllItems();//remove all sprinklers currently in combobox
        cmbSprinkler.addItem("Select");
        //add new list of sprinklers to combobox
        for(Sprinkler sprinkler:sprinklers)
        {
      	  cmbSprinkler.addItem(sprinkler.getSprinklerName());
        }
	}

}
