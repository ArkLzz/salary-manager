package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class salary_other extends JFrame{
	// 定义组件
	JLabel jLsalaryInfoTable = null;//个人信息表
	JLabel jLSelectQueryField = null;//选择查询字段
	JLabel jLEqual = null;//=
	JLabel jLid = null; //记录编号
	JLabel jLperson = null;//员工号
	JLabel jLyearmonth = null;//年月
	JLabel jLtype = null;//类型
	JLabel jLname = null;//记录名
	JLabel jLmoney = null;//金额
	JLabel jLtip= null;
	
	JTextField jTFQueryField;
	JTextField jTFid = null;
	JTextField jTFperson = null;
	JTextField jTFyearmonth = null;
	JTextField jTFtype = null;
	JTextField jTFname = null;
	JTextField jTFmoney = null;
	
	JButton jBQuery = null;//查询
	JButton jBQueryAll = null;//查询所有记录
	
	JComboBox<String> jCBSelectQueryField = null;//查询字段
	JPanel jP1, jP2,jP3,jP4,jP5= null;      
	JPanel jPTop, jPBottom = null;
	DefaultTableModel studentTableModel = null;
	JTable studentJTable = null;
	JScrollPane studentJScrollPane = null;
	Vector personVector = null;
	Vector titleVector = null;
	
	private static DbProcess dbProcess;
	String SelectQueryFieldStr = "记录编号";
	
	// 构造函数
	public salary_other() {
		// 创建组件
		jLsalaryInfoTable = new JLabel("奖惩明细表");            //标签
		jLSelectQueryField = new JLabel("选择查询字段");
		jLEqual = new JLabel(" = ");
		jLid = new JLabel("记录编号");
		jLperson = new JLabel("员工号");
		jLyearmonth = new JLabel("年月");
		jLname = new JLabel("记录名");
		jLtype = new JLabel("类型");
		jLmoney = new JLabel("金额");
		jLtip = new JLabel("Tips:类型中‘0’为福利‘1’为津贴‘2’为扣发");
		
		jTFQueryField = new JTextField(10);             //文本框长度
		jTFid = new JTextField(10);
		jTFperson = new JTextField(10);
		jTFyearmonth = new JTextField(10);
		jTFname = new JTextField(10);
		jTFtype = new JTextField(10);
		jTFmoney = new JTextField(10);
		
		jBQuery = new JButton("查询");                //按钮
		jBQueryAll = new JButton("查询所有记录");
	
		// 设置监听
		jBQuery.addActionListener(new ActionListener()
				{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if(!jTFQueryField.getText().isEmpty())
					{
						System.out.println("actionPerformed(). 查询");
					String sQueryField = jTFQueryField.getText().trim();
					queryProcess(sQueryField);
					jTFQueryField.setText("");
					}
					else
					{
						JFrame frame = new JFrame();
						frame.setTitle("ERROR");
						frame.setSize(400,150);
						frame.setLocationRelativeTo(null); 
						JLabel error=new JLabel("不能为空！");
						error.setFont((new Font("宋体", Font.BOLD, 20)));
						frame.add(error);
						error.setBounds(100, 50, 200, 30);
						frame.setVisible(true);
						frame.setResizable(false);
					}
				}
		});
		jBQueryAll.addActionListener(new ActionListener()
		{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			queryAllProcess();
		}
		});

		jCBSelectQueryField = new JComboBox<String>();//查询字段                   //选择查询
		jCBSelectQueryField.addItem("记录编号");   
		jCBSelectQueryField.addItem("员工号");
		jCBSelectQueryField.addItem("年月");
		jCBSelectQueryField.addItemListener(new ItemListener() {//下拉框事件监听  
            public void itemStateChanged(ItemEvent event) {  
                switch (event.getStateChange()) {  
                case ItemEvent.SELECTED:  
                	SelectQueryFieldStr = (String) event.getItem();  
                    System.out.println("选中：" + SelectQueryFieldStr);  
                    break;  
                case ItemEvent.DESELECTED:  
                    System.out.println("取消选中：" + event.getItem());  
                    break;  
                }  
            }  
        });
	
		personVector = new Vector();
		titleVector = new Vector();
		
		// 定义表头
		titleVector.add("记录编号");
		titleVector.add("员工号");
		titleVector.add("年月");
		titleVector.add("类型");
		titleVector.add("记录名");
		titleVector.add("金额");
		titleVector.add("时薪");
		//studentTableModel = new DefaultTableModel(tableTitle, 15);
		studentJTable = new JTable(personVector, titleVector);
		studentJTable.setPreferredScrollableViewportSize(new Dimension(600,160));
		studentJScrollPane = new JScrollPane(studentJTable);
		//分别设置水平和垂直滚动条自动出现
		studentJScrollPane.setHorizontalScrollBarPolicy(                
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		studentJScrollPane.setVerticalScrollBarPolicy(                
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		//为表格添加监听器 
		studentJTable.addMouseListener(new MouseAdapter()      //鼠标选择
		{ 
			public void mouseClicked(MouseEvent e) 
			{ 
				int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint()); // 获得行位置
				System.out.println("mouseClicked(). row = " + row);
				Vector v = new Vector();
				v = (Vector) personVector.get(row);

				jTFid.setText((String) v.get(0));
				jTFperson.setText((String) v.get(1));
				jTFyearmonth.setText((String) v.get(2));
				jTFtype.setText((String) v.get(3));
				jTFname.setText((String) v.get(4));
				jTFmoney.setText(Integer.toString((int) v.get(5)));
			}
		});

 //布局
		jP1 = new JPanel();    
		jP2 = new JPanel();		
		jP3 = new JPanel();	
		jP4 = new JPanel();
		jP5 = new JPanel();
		jPTop = new JPanel();
		jPBottom = new JPanel();
		
		jP1.add(jLsalaryInfoTable,BorderLayout.SOUTH);
		jP2.add(studentJScrollPane);
		
		jP3.add(jLSelectQueryField);
		jP3.add(jCBSelectQueryField);
		jP3.add(jLEqual);
		jP3.add(jTFQueryField);
		jP3.add(jBQuery);
		jP3.add(jBQueryAll);
		jP3.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP3.setPreferredSize(new Dimension(20,20));
		
		jP4.add(jLid);
		jP4.add(jTFid);
		jP4.add(jLperson);
		jP4.add(jTFperson);
		jP4.add(jLyearmonth);
		jP4.add(jTFyearmonth);
		jP4.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP4.setPreferredSize(new Dimension(20,20));
	
		jP5.add(jLtype);
		jP5.add(jTFtype);
		jP5.add(jLname);
		jP5.add(jTFname);
		jP5.add(jLmoney);
		jP5.add(jTFmoney);
		jP5.add(jLtip);
		jP5.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP5.setPreferredSize(new Dimension(20,20));
	
		jPTop.add(jP1);
		jPTop.add(jP2);
		
		jPBottom.setLayout(new GridLayout(3, 1));
		jPBottom.add(jP3);
		jPBottom.add(jP4);
		jPBottom.add(jP5);
		
		
		JFrame w = new JFrame();
		
		this.add("North", jPTop);
		this.add("South", jPBottom);
	
		this.setLayout(new GridLayout(2, 1));
		this.setTitle("工资管理系统");
		this.setSize(800, 350); //界面大小
		this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		dbProcess = new DbProcess();   //加载驱动
	}
	
	
	public void queryProcess(String sQueryField)   //查询操作
	{
		try{
			// 建立查询条件
			String sql = "select * from salary_other where ";
			String queryFieldStr = jCBSelectQueryFieldTransfer(SelectQueryFieldStr);
			sql = sql + queryFieldStr;
			sql = sql + " = ";
			sql = sql + "'" + sQueryField + "');";
			
			System.out.println("queryProcess(). sql = " + sql);
	
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
	
			// 将查询获得的记录数据，转换成适合生成JTable的数据形式
			personVector.clear();
			while(rs.next()){
				Vector v = new Vector();
				v.add(rs.getString("id"));
				v.add(rs.getString("person"));
				v.add(rs.getString("yearmonth"));
				v.add(rs.getString("type"));
				v.add(rs.getString("name"));
				v.add(Integer.valueOf(rs.getInt("money")));
				v.add(Integer.valueOf(rs.getInt("salary")));
				personVector.add(v);
			}
			
			studentJTable.updateUI();

			dbProcess.disconnect();
		}catch(SQLException sqle){
			System.out.println("sqle = " + sqle);
			JOptionPane.showMessageDialog(null,
				"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void queryAllProcess()
	{
		try{
			// 建立查询条件
			String sql = "select * from salary_other;";
			System.out.println("queryAllProcess(). sql = " + sql);
	
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
			formula x=new formula();
			// 将查询获得的记录数据，转换成适合生成JTable的数据形式
			personVector.clear();
			while(rs.next()){
				Vector v = new Vector();
				v.add(rs.getString("id"));
				v.add(rs.getString("person"));
				v.add(rs.getString("yearmonth"));
				v.add(rs.getString("type"));
				v.add(rs.getString("name"));
				v.add(Integer.valueOf(rs.getInt("money")));
				v.add(Integer.valueOf(rs.getInt("salary")));
				personVector.add(v);
			}
			
			studentJTable.updateUI();

			dbProcess.disconnect();
		}catch(SQLException sqle){
			System.out.println("sqle = " + sqle);
			JOptionPane.showMessageDialog(null,
				"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public String jCBSelectQueryFieldTransfer(String InputStr)
	{
		String outputStr = "";
		System.out.println("jCBSelectQueryFieldTransfer(). InputStr = " + InputStr);
		
		if(InputStr.equals("记录编号")){
			outputStr = "id";
		}else if(InputStr.equals("年月")){
			outputStr = "yearmonth";
		}else if(InputStr.equals("员工号")){
			outputStr = "person";
		}
		System.out.println("jCBSelectQueryFieldTransfer(). outputStr = " + outputStr);
		return outputStr;
	}

	
}

