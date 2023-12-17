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

public class person2 extends JFrame implements ActionListener {
	// 定义组件
	JLabel jLpersonInfoTable = null;//个人信息表
	JLabel jLSelectQueryField = null;//选择查询字段
	JLabel jLEqual = null;//=
	JLabel jLid = null;//员工号
	JLabel jLname = null;//姓名
	JLabel jLsex = null;//性别
	JLabel jLpasswd = null;//密码
	JLabel jLauthority = null;//权限
	JLabel jLbirthday = null;//生日
	JLabel jLdepartment = null;//所在部门
	JLabel jLjob = null;//职位
	JLabel jLedu_level = null;//教育程度
	JLabel jLspeciaty=null;//专业技能
	JLabel jLaddress=null;//地址
	JLabel jLtel=null;//电话
	JLabel jLemail=null;//电子邮箱
	JLabel jLstate=null;//当前状态
	JLabel jLremake=null;//备注

	JTextField jTFQueryField = null;//查询字段
	JTextField jTFid = null;//员工号
	JTextField jTFname = null;//姓名
	JTextField jTFauthority = null;//权限
	JTextField jTFdepartment = null;//所在部门
	JTextField jTFstate = null;//当前状态
	JTextField jTFjob = null;//职位
	
	JButton jBQuery = null;//查询
	JButton jBQueryAll = null;//查询所有记录
	JButton jBInsert = null;//插入
	JButton jBUpdate = null;//更新
	JButton jBDeleteCurrentRecord = null;//删除当前记录
	JButton jBDeleteAllRecords = null;//删除所有记录
	
	//JComboBox jCBSelectQueryField = null;
	JComboBox<String> jCBSelectQueryField = null;//查询字段
	JPanel jP1, jP2,jP3,jP4,jP5,jP6 = null;      
	JPanel jPTop, jPBottom = null;
	DefaultTableModel studentTableModel = null;
	JTable studentJTable = null;
	JScrollPane studentJScrollPane = null;
	Vector personVector = null;
	Vector titleVector = null;
	
	private static DbProcess dbProcess;
	String SelectQueryFieldStr = "员工号";
	
	// 构造函数
	public person2() {
		// 创建组件
		jLpersonInfoTable = new JLabel("个人信息表");            //标签
		jLSelectQueryField = new JLabel("选择查询字段");
		jLEqual = new JLabel(" = ");
		jLid = new JLabel("员工号");
		jLname = new JLabel("姓名");
		jLauthority = new JLabel("权限");
		jLdepartment = new JLabel("部门");
		jLstate = new JLabel("当前状态");
		jLjob = new JLabel("职位");
		
		jTFQueryField = new JTextField(10);             //文本框长度
		jTFid = new JTextField(10);//学号
		jTFname = new JTextField(10);//姓名
		jTFauthority = new JTextField(10);
		jTFdepartment = new JTextField(10);
		jTFstate = new JTextField(10);
		jTFjob = new JTextField(10);
		
		jBQuery = new JButton("查询");                //按钮
		jBQueryAll = new JButton("查询所有记录");
		jBInsert = new JButton("插入");
		jBUpdate = new JButton("更新");
		jBDeleteCurrentRecord = new JButton("删除当前记录");
		jBDeleteAllRecords = new JButton("删除所有记录");
		// 设置监听
		jBQuery.addActionListener(this);
		jBQueryAll.addActionListener(this);
		jBInsert.addActionListener(this);
		jBUpdate.addActionListener(this);
		jBDeleteCurrentRecord.addActionListener(this);
		jBDeleteAllRecords.addActionListener(this);
		
		jCBSelectQueryField = new JComboBox<String>();//查询字段                   //选择查询
		jCBSelectQueryField.addItem("员工号");  
		jCBSelectQueryField.addItem("姓名");  
		jCBSelectQueryField.addItem("权限");
		jCBSelectQueryField.addItem("部门");
		jCBSelectQueryField.addItem("当前状态");
		jCBSelectQueryField.addItem("职位");
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
		titleVector.add("员工号");
		titleVector.add("姓名");
		titleVector.add("权限");
		titleVector.add("部门");
		titleVector.add("当前状态");
		titleVector.add("职位");
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

						jTFid.setText((String) v.get(0));// 学号
						jTFname.setText((String) v.get(1));// 姓名
						jTFauthority.setText((String) v.get(2));// 性别
						jTFdepartment.setText((String) v.get(3));// 年龄
						jTFstate.setText((String) v.get(4));// 专业
						jTFjob.setText((String) v.get(5));// 住址
					}
				});

 //布局
		jP1 = new JPanel();    //表名
		jP2 = new JPanel();		//表
		jP3 = new JPanel();		
		jP4 = new JPanel();
		jP5 = new JPanel();
		jP6 = new JPanel();
		jPTop = new JPanel();
		jPBottom = new JPanel();
		
		jP1.add(jLpersonInfoTable,BorderLayout.SOUTH);
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
		jP4.add(jLname);
		jP4.add(jTFname);
		jP4.add(jLauthority);
		jP4.add(jTFauthority);
		jP4.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP4.setPreferredSize(new Dimension(20,20));
	
		jP5.add(jLdepartment);
		jP5.add(jTFdepartment);
		jP5.add(jLstate);
		jP5.add(jTFstate);
		jP5.add(jLjob);
		jP5.add(jTFjob);
		jP5.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP5.setPreferredSize(new Dimension(20,20));
		
		jPTop.add(jP1);
		jPTop.add(jP2);
		
		jPBottom.setLayout(new GridLayout(3, 1));
		jPBottom.add(jP3);
		jPBottom.add(jP4);
		jPBottom.add(jP5);
		
		this.add("North", jPTop);
		this.add("South", jPBottom);
	
		this.setLayout(new GridLayout(2, 1));
		this.setTitle("工资管理系统");
		this.setSize(800, 500); //界面大小
		this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		dbProcess = new DbProcess();   //加载驱动
	}

	@Override
	public void actionPerformed(ActionEvent e) {    //按钮操作
		if(e.getActionCommand().equals("查询")  
			&& !jTFQueryField.getText().isEmpty()){
			System.out.println("actionPerformed(). 查询");
			String sQueryField = jTFQueryField.getText().trim();
			queryProcess(sQueryField);
			jTFQueryField.setText("");
		}else if(e.getActionCommand().equals("查询所有记录")) {
			System.out.println("actionPerformed(). 查询所有记录");
			queryAllProcess();
		}else if(!jTFQueryField.getText().isEmpty())
		{
			JFrame frame = new JFrame();
			frame.setTitle("ERROR");
			frame.setSize(400,150);
			frame.setLocationRelativeTo(null); 
			JLabel error=new JLabel("没有权限操作！");
			error.setFont((new Font("宋体", Font.BOLD, 20)));
			frame.add(error);
			error.setBounds(100, 50, 200, 30);
			frame.setVisible(true);
			frame.setResizable(false);
		}
	}
	
	
	
	public void queryProcess(String sQueryField)   //查询操作
	{
		try{
			// 建立查询条件
			String sql = "select * from person where ";
			String queryFieldStr = jCBSelectQueryFieldTransfer(SelectQueryFieldStr);
		
			if(queryFieldStr.equals("sAge")){//int sAge.
				sql = sql + queryFieldStr;
				sql = sql + " = " + sQueryField;
			}else{
				sql = sql + queryFieldStr;
				sql = sql + " = ";
				sql = sql + "'" + sQueryField + "';";
			}
			
			System.out.println("queryProcess(). sql = " + sql);
	
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
	
			// 将查询获得的记录数据，转换成适合生成JTable的数据形式
			personVector.clear();
			while(rs.next()){
				Vector v = new Vector();
				v.add(rs.getString("id"));
				v.add(rs.getString("name"));
				v.add(rs.getString("authority"));
				v.add(rs.getString("department"));
				v.add(rs.getString("state"));
				v.add(rs.getString("job"));
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
			String sql = "select * from person;";
			System.out.println("queryAllProcess(). sql = " + sql);
	
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);

			// 将查询获得的记录数据，转换成适合生成JTable的数据形式
			personVector.clear();
			while(rs.next()){
				Vector v = new Vector();
				v.add(rs.getString("id"));
				v.add(rs.getString("name"));
				v.add(rs.getString("authority"));
				v.add(rs.getString("department"));
				v.add(rs.getString("state"));
				v.add(rs.getString("job"));
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
	
	


	public static void main(String[] args) {
        // TODO Auto-generated method stub
		DatabaseCourseDesign1 getcon = new  DatabaseCourseDesign1();
    }
	
	public String jCBSelectQueryFieldTransfer(String InputStr)
	{
		String outputStr = "";
		System.out.println("jCBSelectQueryFieldTransfer(). InputStr = " + InputStr);
		
		if(InputStr.equals("员工号")){
			outputStr = "id";
		}else if(InputStr.equals("姓名")){
			outputStr = "name";
		}else if(InputStr.equals("权限")){
			outputStr = "authority";
		}else if(InputStr.equals("部门")){
			outputStr = "department";
		}else if(InputStr.equals("当前状态")){
			outputStr = "state";
		}else if(InputStr.equals("职位")){
			outputStr = "job";
		}
		System.out.println("jCBSelectQueryFieldTransfer(). outputStr = " + outputStr);
		return outputStr;
	}
}

