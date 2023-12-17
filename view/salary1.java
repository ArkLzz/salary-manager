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

public class salary1 extends JFrame implements ActionListener {
	// 定义组件
	JLabel jLsalaryInfoTable = null;//个人信息表
	JLabel jLSelectQueryField = null;//选择查询字段
	JLabel jLEqual = null;//=
	JLabel jLid = null; //记录编号
	JLabel jLperson = null;//员工号
	JLabel jLyearmonth = null;//年月
	JLabel jLbasic = null;//基础工资
	JLabel jLbonus = null;//奖金
	JLabel jLadd_total = null;//其他应发
	JLabel jLsub_total = null;//扣发总额
	JLabel jLtotal = null;//实际工资


	JTextField jTFQueryField = null;
	JTextField jTFid = null;
	JTextField jTFperson = null;
	JTextField jTFyearmonth = null;
	JTextField jTFtotal = null;
	JTextField jTFbasic = null;
	JTextField jTFbonus = null;
	JTextField jTFadd_total = null;
	JTextField jTFsub_total = null;
	
	JButton jBQuery = null;//查询
	JButton jBQueryAll = null;//查询所有记录
	
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
	String SelectQueryFieldStr = "记录编号";
	
	// 构造函数
	public salary1() {
		// 创建组件
		jLsalaryInfoTable = new JLabel("工资明细表");            //标签
		jLSelectQueryField = new JLabel("选择查询字段");
		jLEqual = new JLabel(" = ");
		jLid = new JLabel("记录编号");
		jLperson = new JLabel("员工号");
		jLyearmonth = new JLabel("年月");
		jLbasic = new JLabel("基础工资");
		jLsub_total = new JLabel("扣发总额");
		jLbonus = new JLabel("奖金");
		jLadd_total = new JLabel("其他应发");
		jLtotal = new JLabel("实际工资");
		
		jTFQueryField = new JTextField(10);             //文本框长度
		jTFid = new JTextField(10);
		jTFperson = new JTextField(10);
		jTFyearmonth = new JTextField(10);
		jTFtotal = new JTextField(10);
		jTFbasic = new JTextField(10);
		jTFbonus = new JTextField(10);
		jTFadd_total = new JTextField(10);
		jTFsub_total = new JTextField(10);
		
		jBQuery = new JButton("查询");                //按钮
		jBQueryAll = new JButton("查询所有记录");

		// 设置监听
		jBQuery.addActionListener(this);
		jBQueryAll.addActionListener(this);
		
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
		titleVector.add("实际工资");
		titleVector.add("基础工资");
		titleVector.add("奖金");
		titleVector.add("其他应发");
		titleVector.add("扣发总额");
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
				jTFtotal.setText(Integer.toString((int) v.get(3)));
				jTFbasic.setText(Integer.toString((int) v.get(4)));
				jTFbonus.setText(Integer.toString((int) v.get(5)));
				jTFadd_total.setText(Integer.toString((int) v.get(6)));
				jTFsub_total.setText(Integer.toString((int) v.get(7)));
			}
		});

 //布局
		jP1 = new JPanel();    
		jP2 = new JPanel();		
		jP3 = new JPanel();		
		jP4 = new JPanel();
		jP5 = new JPanel();
		jP6 = new JPanel();
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
		jP4.add(jLtotal);
		jP4.add(jTFtotal);
		jP4.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP4.setPreferredSize(new Dimension(20,20));
	
		jP5.add(jLbasic);
		jP5.add(jTFbasic);
		jP5.add(jLbonus);
		jP5.add(jTFbonus);
		jP5.add(jLadd_total);
		jP5.add(jTFadd_total);
		jP5.add(jLsub_total);
		jP5.add(jTFsub_total);
		jP5.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP5.setPreferredSize(new Dimension(20,20));
		
		
		jPTop.add(jP1);
		jPTop.add(jP2);
		
		jPBottom.setLayout(new GridLayout(4, 1));
		jPBottom.add(jP3);
		jPBottom.add(jP4);
		jPBottom.add(jP5);
		
		JFrame w = new JFrame();
		
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
		}else if(e.getActionCommand().equals("插入")
				&& !jTFid.getText().isEmpty()
				&& !jTFperson.getText().isEmpty()
				&& !jTFyearmonth.getText().isEmpty()
			//	&& !jTFtotal.getText().isEmpty()
				&& !jTFbasic.getText().isEmpty()
				&& !jTFbonus.getText().isEmpty()
				&& !jTFadd_total.getText().isEmpty()
				&& !jTFsub_total.getText().isEmpty()){
			System.out.println("actionPerformed(). 插入");
			insertProcess();
		}else if(e.getActionCommand().equals("更新")
				&& !jTFid.getText().isEmpty()
				&& !jTFperson.getText().isEmpty()
				&& !jTFyearmonth.getText().isEmpty()
			//	&& !jTFtotal.getText().isEmpty()
				&& !jTFbasic.getText().isEmpty()
				&& !jTFbonus.getText().isEmpty()
				&& !jTFadd_total.getText().isEmpty()
				&& !jTFsub_total.getText().isEmpty()){
			System.out.println("actionPerformed(). 更新");
			updateProcess();
		}else if(e.getActionCommand().equals("删除当前记录")){
			System.out.println("actionPerformed(). 删除当前记录");
			deleteCurrentRecordProcess();
		}else if(e.getActionCommand().equals("删除所有记录")){
			System.out.println("actionPerformed(). 删除所有记录");
			deleteAllRecordsProcess();
		}
	}
	
	
	
	public void queryProcess(String sQueryField)   //查询操作
	{
		try{
			// 建立查询条件
			String sql = "select * from salary where ";
			String queryFieldStr = jCBSelectQueryFieldTransfer(SelectQueryFieldStr);
			sql = sql + queryFieldStr;
			sql = sql + " = ";
			sql = sql + "'" + sQueryField + "';";
			
			
			System.out.println("queryProcess(). sql = " + sql);
	
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
				v.add(x.operation(Integer.valueOf(rs.getInt("basic")),Integer.valueOf(rs.getInt("bonus")),
						Integer.valueOf(rs.getInt("add_total")), Integer.valueOf(rs.getInt("sub_total"))));
				v.add(Integer.valueOf(rs.getInt("basic")));
				v.add(Integer.valueOf(rs.getInt("bonus")));
				v.add(Integer.valueOf(rs.getInt("add_total")));
				v.add(Integer.valueOf(rs.getInt("sub_total")));
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
			String sql = "select * from salary;";
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
				v.add(x.operation(Integer.valueOf(rs.getInt("basic")),Integer.valueOf(rs.getInt("bonus")),
						Integer.valueOf(rs.getInt("add_total")), Integer.valueOf(rs.getInt("sub_total"))));
				v.add(Integer.valueOf(rs.getInt("basic")));
				v.add(Integer.valueOf(rs.getInt("bonus")));
				v.add(Integer.valueOf(rs.getInt("add_total")));
				v.add(Integer.valueOf(rs.getInt("sub_total")));
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
	
	public void insertProcess()
	{
		String id = jTFid.getText().trim();
		String person = jTFperson.getText().trim();
		String yearmonth = jTFyearmonth.getText().trim();
		String basic = jTFbasic.getText().trim();
		String bonus = jTFbonus.getText().trim();
		String add_total = jTFadd_total.getText().trim();
		String sub_total = jTFsub_total.getText().trim();
		String total = Integer.toString(Integer.parseInt(basic)+Integer.parseInt(bonus)+
				Integer.parseInt(add_total)-Integer.parseInt(sub_total));
		
		// 建立插入条件
		String sql = "insert into salary(id,person,yearmonth,total,basic,bonus,add_total,sub_total) values('";
		sql = sql + id + "','";
		sql = sql + person + "','";
		sql = sql + yearmonth + "',";
		sql = sql + total + ",";
		sql = sql + basic + ",";
		sql = sql + bonus + ",";
		sql = sql + add_total + ",";
		sql = sql + sub_total + ");";

		System.out.println("insertProcess(). sql = " + sql);
		try{
			if (dbProcess.executeUpdate(sql) < 1) {
				System.out.println("insertProcess(). insert database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}
		queryAllProcess();
	}

	public void updateProcess()
	{
		String id = jTFid.getText().trim();
		String person = jTFperson.getText().trim();
		String yearmonth = jTFyearmonth.getText().trim();
		String basic = jTFbasic.getText().trim();
		String bonus = jTFbonus.getText().trim();
		String add_total = jTFadd_total.getText().trim();
		String sub_total = jTFsub_total.getText().trim();
		String total = Integer.toString(Integer.parseInt(basic)+Integer.parseInt(bonus)+
			Integer.parseInt(add_total)-Integer.parseInt(sub_total));
		
		// 建立更新条件
		String sql = "update salary set person = '";
		sql = sql + person + "', yearmonth = '";
		sql = sql + yearmonth + "', total = ";
		sql = sql + total + ", basic = ";
		sql = sql + basic + ", add_total = ";
		sql = sql + add_total + ", sub_total = ";
		sql = sql + sub_total ;
		sql = sql + " WHERE id = '" + id + "';";
		System.out.println("updateProcess(). sql = " + sql);
		try{
			if (dbProcess.executeUpdate(sql) < 1) {
				System.out.println("updateProcess(). update database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}
		queryAllProcess();
	}

	public void deleteCurrentRecordProcess()
	{
		String id = jTFid.getText().trim();
		
		// 建立删除条件
		String sql = "delete from salary where id = '" + id + "';";
		System.out.println("deleteCurrentRecordProcess(). sql = " + sql);
		try{
			if (dbProcess.executeUpdate(sql) < 1) {
				System.out.println("deleteCurrentRecordProcess(). delete database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}
		queryAllProcess();
	}

	public static void main(String[] args) {
        // TODO Auto-generated method stub
		DatabaseCourseDesign1 getcon = new  DatabaseCourseDesign1();
    }
	public void deleteAllRecordsProcess()
	{
		// 建立删除条件
		String sql = "delete from salary;";
		System.out.println("deleteAllRecordsProcess(). sql = " + sql);
		try{
			if (dbProcess.executeUpdate(sql) < 1) {
				System.out.println("deleteAllRecordsProcess(). delete database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}
		queryAllProcess();
	}
	
	public String jCBSelectQueryFieldTransfer(String InputStr)
	{
		String outputStr = "";
		System.out.println("jCBSelectQueryFieldTransfer(). InputStr = " + InputStr);
		
		if(InputStr.equals("记录编号")){
			outputStr = "id";
		}else if(InputStr.equals("员工号")){
			outputStr = "person";
		}else if(InputStr.equals("年月")){
			outputStr = "yearmonth";
		}else if(InputStr.equals("实际工资")){
			outputStr = "total";
		}else if(InputStr.equals("基础工资")){
			outputStr = "basic";
		}else if(InputStr.equals("奖金")){
			outputStr = "bonus";
		}else if(InputStr.equals("其他应发")){
			outputStr = "add_total";
		}else if(InputStr.equals("扣发总额")){
			outputStr = "sub_total";
		}
		System.out.println("jCBSelectQueryFieldTransfer(). outputStr = " + outputStr);
		return outputStr;
	}
}

