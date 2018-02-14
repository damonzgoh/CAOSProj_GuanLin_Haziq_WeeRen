

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;



import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class GUI {
	
	Information information = null;
	private ArrayList<Information> info = new ArrayList<Information>();	
	private boolean isMemory;
	private Controller controller = new Controller();

	private JFrame frmCaos;
	private JTable table;
	private JTable table_1;
	private JTable table_Mem;
	private JTextField textField;
	private JComboBox comboBox_1,comboBox_3,comboBox_MemSelect, comboBox_Processes;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel lblNewLabel_5, lblNewLabel_6;

	//Gantt Chart GUI
	private JFrame GC_MM;
	private JScrollPane pane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmCaos.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCaos = new JFrame();
		frmCaos.setTitle("CAOS");
		frmCaos.setBounds(100, 100, 627, 426);
		frmCaos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmCaos.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 600, 365);
		panel.add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("CPU Scheduling", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CPU Scheduling");
		lblNewLabel.setBounds(238, 5, 119, 23);
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 18));
		panel_1.add(lblNewLabel);
		
		table = new JTable();
		table.setBounds(203, 71, 382, 96);
		panel_1.add(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Process No.", "Arrival Time", "Burst Time", "Final Time"},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"Process No.", "Arrival time", "Burst time", "Priority"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		
		
		JLabel lblNewLabel_3 = new JLabel("No of process.");
		lblNewLabel_3.setFont(new Font("Calibri", Font.BOLD, 12));
		lblNewLabel_3.setBounds(25, 67, 87, 14);
		panel_1.add(lblNewLabel_3);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for(int y = 0;y<4;y++){
					for(int x = 0;x<5;x++)
					{
						
								table.getModel().setValueAt("", x+1, y);
								table_1.getModel().setValueAt("", x+1, y);
						
					}
				}
				
				
				textField_1.setText("");
				textField_2.setText("");
				comboBox_3.setSelectedIndex(0);
				comboBox_1.setSelectedIndex(0);
				info.clear();
				System.gc();
				
				
			}
		});
		btnClear.setFont(new Font("Calibri", Font.BOLD, 13));
		btnClear.setBounds(510, 181, 75, 23);
		panel_1.add(btnClear);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{"Process No.", "Priority", "Waiting Time", "Turnaround"},
				{null, null, null,null},
				{null, null, null,null},
				{null, null, null,null},
				{null, null, null,null},
				{null, null, null,null},
			
			},
			new String[] {
					"Process No.", "Priority", "Waiting Time", "Turnaround"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_1.setBounds(272, 234, 313, 92);
		panel_1.add(table_1);
		
		
		JLabel lblAvgWaitingTime = new JLabel("Avg. Waiting Time:");
		lblAvgWaitingTime.setFont(new Font("Calibri", Font.BOLD, 12));
		lblAvgWaitingTime.setBounds(25, 234, 106, 14);
		panel_1.add(lblAvgWaitingTime);
		
		JLabel lblAvgTurnaroundTime = new JLabel("Avg. Turnaround Time:");
		lblAvgTurnaroundTime.setFont(new Font("Calibri", Font.BOLD, 12));
		lblAvgTurnaroundTime.setBounds(25, 256, 119, 14);
		panel_1.add(lblAvgTurnaroundTime);
		
		JButton btnGanttChart = new JButton("Gantt Chart");
		btnGanttChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				callGanttChart();
			}
		});
		btnGanttChart.setFont(new Font("Calibri", Font.BOLD, 13));
		btnGanttChart.setBounds(25, 275, 106, 23);
		panel_1.add(btnGanttChart);
		
		comboBox_3 = new JComboBox();
		comboBox_3.setFont(new Font("Calibri", Font.BOLD, 13));
		comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"FCFS","Round Robin", "SJF (Premptive)","SJF (Non-Premptive)", "Priority"}));
		comboBox_3.setBounds(25, 36, 87, 20);
		panel_1.add(comboBox_3);
		
		JLabel lblNewLabel_4 = new JLabel("Random Timing generator");
		lblNewLabel_4.setBounds(25, 108, 166, 14);
		panel_1.add(lblNewLabel_4);
		
		JLabel lblNo = new JLabel("No 1.");
		lblNo.setBounds(25, 133, 46, 14);
		panel_1.add(lblNo);
		
	    textField_1 = new JTextField();
		textField_1.setBounds(63, 133, 86, 20);
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNo_1 = new JLabel("No 2.");
		lblNo_1.setBounds(25, 158, 46, 14);
		panel_1.add(lblNo_1);
		
	    textField_2 = new JTextField();
		textField_2.setBounds(63, 158, 86, 20);
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Run");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				passInfo();
			}
		});
		btnNewButton_1.setBounds(35, 189, 89, 23);
		panel_1.add(btnNewButton_1);
		
		 comboBox_1 = new JComboBox();
		 for(int x=1;x<6;x++)
			 comboBox_1.addItem(x);

		comboBox_1.setBounds(25, 81, 46, 20);
		panel_1.add(comboBox_1);
		
		 lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setBounds(141, 234, 66, 14);
		panel_1.add(lblNewLabel_5);
		
		 lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setBounds(141, 255, 66, 14);
		panel_1.add(lblNewLabel_6);
		
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Main Memory", null, panel_2, null);
		panel_2.setLayout(null);
		
		comboBox_MemSelect = new JComboBox();
		comboBox_MemSelect.setModel(new DefaultComboBoxModel(new String[] {"Best Fit", "First Fit", "Worst Fit"}));
		comboBox_MemSelect.setBounds(76, 68, 87, 20);
		comboBox_MemSelect.setFont(new Font("Calibri", Font.BOLD, 13));
		panel_2.add(comboBox_MemSelect);
		
		JLabel lblNewLabel_1 = new JLabel("Main Memory");
		lblNewLabel_1.setBounds(238, 11, 108, 23);
		lblNewLabel_1.setFont(new Font("Calibri", Font.BOLD, 18));
		panel_2.add(lblNewLabel_1);
		
		ButtonGroup rbg = new ButtonGroup();
		
		table_Mem = new JTable();
		table_Mem.setModel(new DefaultTableModel(
			new Object[][] {
				{"Process No.", "Size"},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"Process No.", "Size"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_Mem.getColumnModel().getColumn(0).setResizable(false);
		table_Mem.getColumnModel().getColumn(1).setResizable(false);
		table_Mem.setBounds(328, 68, 157, 176);
		panel_2.add(table_Mem);
		
		JLabel lblNoOfProcess = new JLabel("No of Process");
		lblNoOfProcess.setFont(new Font("Calibri", Font.BOLD, 12));
		lblNoOfProcess.setBounds(76, 124, 76, 14);
		panel_2.add(lblNoOfProcess);
		
		comboBox_Processes = new JComboBox();
		comboBox_Processes.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		comboBox_Processes.setBounds(173, 120, 38, 20);
		panel_2.add(comboBox_Processes);
		
		JLabel lblProcessSize = new JLabel("No of Partitions:");
		lblProcessSize.setFont(new Font("Calibri", Font.BOLD, 12));
		lblProcessSize.setBounds(76, 162, 87, 14);
		panel_2.add(lblProcessSize);
		
		JComboBox comboBox_Part = new JComboBox();
		comboBox_Part.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5"}));
		comboBox_Part.setBounds(173, 158, 38, 20);
		panel_2.add(comboBox_Part);
		
		JButton btnMemClear = new JButton("Clear");
		btnMemClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for(int y = 0;y<2;y++){
					for(int x = 0;x<10;x++)
					{
						
								table_Mem.getModel().setValueAt("", x+1, y);
								//table_1.getModel().setValueAt("", x+1, y);
						
					}
				}
				comboBox_Processes.setSelectedIndex(0);
				comboBox_Part.setSelectedIndex(0);
				info.clear();
				System.gc();
			}
		});
		btnMemClear.setBounds(388, 267, 97, 25);
		panel_2.add(btnMemClear);
		

		
		JButton btnMemRun = new JButton("Run");
		btnMemRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isMemory = true;
				controller.setPartition(Integer.parseInt(comboBox_Part.getSelectedItem().toString()));
				String memoryAllocation="";
				if (comboBox_MemSelect.getSelectedItem().equals("First Fit"))
				memoryAllocation="First Fit";
				if (comboBox_MemSelect.getSelectedItem().equals("Best Fit"))
				memoryAllocation="Best Fit";
				if (comboBox_MemSelect.getSelectedItem().equals("Worst Fit"))
				memoryAllocation="Worst Fit";
				
				//controller.setMemoryAllocation(memoryAllocation);
				controller.Memory(true);
				passMem(memoryAllocation);
			}
		});
		btnMemRun.setBounds(76, 267, 97, 25);
		panel_2.add(btnMemRun);
		

		
		JButton btnMemoryMap = new JButton("Memory Map");
		btnMemoryMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				memoryMap();
			}
		});
		btnMemoryMap.setBounds(215, 267, 108, 25);
		panel_2.add(btnMemoryMap);
	}
	
	
	//Functions 
	
	public class ganttImg extends JPanel{
		public void paint(Graphics g){
			ArrayList<Integer> processOrder= controller.getProcessOrder();
			ArrayList<Integer> time= controller.getTime();
			for(int x = 0;x<processOrder.size();x++)
			{
				g.drawRect(10+(x*60), 10, 60, 40);
				if(processOrder.get(x)==0)
				g.drawString("Idle", 30+(x*60), 35);
				else
					g.drawString("P"+processOrder.get(x), 30+(x*60), 35);
			}
			for(int x = 0;x<time.size();x++)
			{
				g.drawString(""+time.get(x), (x*60), 70);
			}
		}
	}
	
	public void callGanttChart()
	{
		GC_MM = new JFrame("Gantt Chart");
		GC_MM.setSize(450, 150);
		GC_MM.getContentPane().add(new ganttImg());
		//GanttChart.add(new MemoryImg());
		pane = new JScrollPane();
		pane.setVisible(true);
		GC_MM.getContentPane().add(pane.getHorizontalScrollBar(),BorderLayout.SOUTH);
		GC_MM.setVisible(true);
	}
	public class MemoryImg extends JPanel{
		public void paint(Graphics g){
			int x,ifrag=0,efrag=0;
			ArrayList<Integer> temp ,temps;
			String intFrag,extFrag = "External Fragmentation: ",tempFrag; 
			intFrag = "Internal Fragmentation: ";
			for(x = 0;x<controller.noOfPartition();x++)
			{
				tempFrag="";
				ifrag += controller.MEMORYMAP[x];
				tempFrag +="("+controller.MEMORYMAP[x];
				g.drawRect(10, 10+(x*50), 300, 50);
				temp=controller.getIndexByLocation(x);
				temps=controller.getSizeByLocation(x);
				g.drawString(""+controller.MEMORYMAP[x], 320, 35+(x*50));
				for(int y = 0;y<temp.size();y++)
				{
					tempFrag+=" - "+temps.get(y);
					ifrag-=temps.get(y);
					g.drawString("P"+temp.get(y), 30+(y*60), 35+(x*50));
				}
				if(temp.size()==0)
				{
				extFrag+=controller.MEMORYMAP[x]+" + ";
				efrag+=controller.MEMORYMAP[x];
				}
				else
				{
					tempFrag+=") +";
					intFrag+=tempFrag;
				}
					
			}
			temp=controller.getIndexByLocation(-1);
			g.drawString(intFrag+" = "+ifrag, 10, 35+(controller.noOfPartition()*50));
			if(temp.size() == 0)
				g.drawString("External Fragmentation = 0 ", 10, 35+((controller.noOfPartition()+1)*50));
			else
				g.drawString(extFrag+" = "+efrag, 10, 35+((controller.noOfPartition()+1)*50));
				
			
			
			String noLoc="Processes not allocated:";
			for(int y = 0;y<temp.size();y++)
			{
				noLoc+="P"+temp.get(y)+",";
			}
			g.drawString(noLoc,10, 35+((controller.noOfPartition()+2)*50));
			
		}
	}
	public void memoryMap()
	{
		GC_MM = new JFrame("Memory Map");
		GC_MM.setSize(450, 400);
		GC_MM.getContentPane().add(new MemoryImg());
		//GanttChart.add(new MemoryImg());
		pane = new JScrollPane();
		pane.setVisible(true);
		GC_MM.getContentPane().add(pane.getHorizontalScrollBar(),BorderLayout.SOUTH);
		GC_MM.setVisible(true);
	}
	
	public void passInfo()
	{
		int noofProcess = 0;
		String algorithm="";
		if(comboBox_3.getSelectedItem().equals("FCFS"))
			algorithm="FCFS";		
		if(comboBox_3.getSelectedItem().equals("Round Robin"))
			algorithm="Round Robin";
		if(comboBox_3.getSelectedItem().equals("SJF (Premptive)"))
			algorithm="SJF (Premptive)";
		if(comboBox_3.getSelectedItem().equals("SJF (Non-Premptive)"))
			algorithm="SJF (Non-Premptive)";
		if(comboBox_3.getSelectedItem().equals("Priority"))
			algorithm="Priority";
		//if(comboBox_3.getSelectedItem().equals("FCFS"))
		

			noofProcess = comboBox_1.getSelectedIndex()+1;
		
		System.out.println(comboBox_1.getSelectedIndex()+1);
		System.out.println(Integer.parseInt(textField_1.getText()));
		System.gc();
		System.out.println(Integer.parseInt(textField_2.getText()));
		controller.generate(noofProcess,Integer.parseInt(textField_1.getText()),Integer.parseInt(textField_2.getText()),algorithm);
		ArrayList index = controller.getIndexOfProcesses();
		ArrayList aTime = controller.getArrivalTime();
		ArrayList bTime = controller.getBurstTime();
		ArrayList sTime = controller.getSize();
		ArrayList fTime = controller.getFinishTime();
		ArrayList wTime = controller.getWaitingTime();
		ArrayList tTime = controller.getTurnaroundTime();
		ArrayList priority = controller.getPriority();
		System.out.println(aTime.size());
		System.gc();
		for(int x = 0;x<index.size();x++)
		{
			table.getModel().setValueAt(index.get(x), x+1, 0);
			table_1.getModel().setValueAt(index.get(x), x+1, 0);
		}
		for(int x = 0;x<aTime.size();x++)
		{
			table.getModel().setValueAt(aTime.get(x), x+1, 1);
		}
		if(isMemory){
			for(int x = 0;x<sTime.size();x++)
			{
				table_Mem.getModel().setValueAt(sTime.get(x), x+1, 2);
			}
		}
		else
		{
		for(int x = 0;x<bTime.size();x++)
		{
			table.getModel().setValueAt(bTime.get(x), x+1, 2);
		}	
		}
		
		for(int x = 0;x<fTime.size();x++)
		{
			if(isMemory)
			{
				table_Mem.getModel().isCellEditable(x+1, 3);
			}
			else{
					table.getModel().setValueAt(fTime.get(x), x+1, 3);
			}
		}
		float wait=0, wait2=0, wait3 = wTime.size() , wait4 =0;
		for(int x = 0;x<wTime.size();x++)
		{
			
			wait2 = (int) wTime.get(x);
			wait = wait + wait2;
		}
		wait4 = wait/wait3;
		lblNewLabel_5.setText(" " + wait4);
		for(int x = 0;x<wTime.size();x++)
		{if(isMemory)
		{
			table_Mem.getModel().isCellEditable(x+1, 2);
		}
		else{
			table_1.getModel().setValueAt(wTime.get(x), x+1, 2);
			}
		}
		float turn=0, turn2=0, turn3 = tTime.size(), turnf =0;
		for(int x = 0;x<tTime.size();x++)
		{
			
			turn2 = (int) tTime.get(x);
			turn = turn + turn2;
		}
		turnf = turn/turn3;
		lblNewLabel_6.setText(" " + turnf);
		for(int x = 0;x<tTime.size();x++)
		{
			if(isMemory)
			{
				table_Mem.getModel().isCellEditable(x+1, 3);
			}
			else{
				table_1.getModel().setValueAt(tTime.get(x), x+1, 3);
				
				}
		}
		for(int x = 0;x<priority.size();x++)
		{
			if(isMemory)
			{
			}
			else{
				table_1.getModel().setValueAt(priority.get(x), x+1, 1);
				
				}
		}
		
	}
	
	public void passMem(String algorithm)
	{
		System.gc();
		int noofProcess = 0;
		//String algorithm="";
		/*if(comboBox_3.getSelectedItem().equals("FCFS"))
			algorithm="FCFS";		
		if(comboBox_3.getSelectedItem().equals("Round Robin"))
			algorithm="Round Robin";
		if(comboBox_3.getSelectedItem().equals("SJF (Premptive)"))
			algorithm="SJF (Premptive)";
		if(comboBox_3.getSelectedItem().equals("SJF (Non-Premptive)"))
			algorithm="SJF (Non-Premptive)";
		if(comboBox_3.getSelectedItem().equals("Priority"))
			algorithm="Priority";
		//if(comboBox_3.getSelectedItem().equals("FCFS"))
		 * */
		 
		
	
		noofProcess = Integer.parseInt(comboBox_Processes.getSelectedItem().toString());
	
		
		//System.out.println(comboBox_1.getSelectedIndex()+1);
		//System.out.println(Integer.parseInt(textField_1.getText()));
		
		//System.out.println(Integer.parseInt(textField_2.getText()));
		controller.generateMem(noofProcess,algorithm);
		ArrayList index = controller.getIndexOfProcesses();
		//ArrayList aTime = controller.getArrivalTime();
		//ArrayList bTime = controller.getBurstTime();
		ArrayList sTime = controller.getSize();
		//ArrayList fTime = controller.getFinishTime();
		//ArrayList wTime = controller.getWaitingTime();
		//ArrayList tTime = controller.getTurnaroundTime();
		//ArrayList priority = controller.getPriority();
		//System.out.println(aTime.size());
		for(int x = 0;x<index.size();x++)
		{
			//table_Mem.getModel().isCellEditable(x+1, 3);
			table_Mem.getModel().setValueAt(sTime.get(x), x+1, 1);
			//table_1.getModel().setValueAt(index.get(x), x+1, 0);
		}
		for(int x = 0;x<noofProcess;x++)
		{
			table_Mem.getModel().setValueAt(x+1, x+1, 0);
		}
		isMemory = false;
	}

	
	
}
	
	