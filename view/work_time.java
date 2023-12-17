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

public class work_time extends JFrame implements ActionListener {
	// 定义组件
	JLabel jLsalaryInfoTable = null;//个人信息表
	JLabel jLSelectQueryField = null;//选择查询字段
	JLabel jLEqual = null;//=
	JLabel jLid = null; //记录编号
	JLabel jLperson = null;//员工号
	JLabel jLyearmonth = null;//年月
	JLabel jLwork_hour = null;//工作时
	JLabel jLover_hour = null;//加班时
	JLabel jLleave_day = null;//请假天
	JLabel jLerrand_day = null;//出差天

	JTextField jTFQueryField = null;
	JTextField jTFid = null;
	JTextField jTFperson = null;
	JTextField jTFyearmonth = null;
	JTextField jTFwork_hour = null;
	JTextField jTFover_hour = null;
	JTextField jTFleave_day = null;
	JTextField jTFerrand_day = null;
	
	JButton jBQuery = null;//查询
	JButton jBQueryAll = null;//查询所有记录
	JButton jBInsert = null;//插入
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
	String SelectQueryFieldStr = "记录编号";
	
	// 构造函数
	public work_time() {
		// 创建组件
		jLsalaryInfoTable = new JLabel("工资明细表");            //标签
		jLSelectQueryField = new JLabel("选择查询字段");
		jLEqual = new JLabel(" = ");
		jLid = new JLabel("记录编号");
		jLperson = new JLabel("员工号");
		jLyearmonth = new JLabel("年月");
		jLwork_hour = new JLabel("工作时长");
		jLover_hour = new JLabel("加班时长");
		jLleave_day = new JLabel("请假天数");
		jLerrand_day = new JLabel("出差天数");
		
		jTFQueryField = new JTextField(10);             //文本框长度
		jTFid = new JTextField(10);
		jTFperson = new JTextField(10);
		jTFyearmonth = new JTextField(10);
		jTFwork_hour = new JTextField(10);
		jTFover_hour = new JTextField(10);
		jTFleave_day = new JTextField(10);
		jTFerrand_day = new JTextField(10);
		
		jBQuery = new JButton("查询");                //按钮
		jBQueryAll = new JButton("查询所有记录");
		jBInsert = new JButton("插入");
		jBDeleteCurrentRecord = new JButton("删除当前记录");
		jBDeleteAllRecords = new JButton("删除所有记录");
		// 设置监听
		jBQuery.addActionListener(this);
		jBQueryAll.addActionListener(this);
		jBInsert.addActionListener(this);
		jBDeleteCurrentRecord.addActionListener(this);
		jBDeleteAllRecords.addActionListener(this);
		
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
		titleVector.add("工作时长");
		titleVector.add("加班时长");
		titleVector.add("请假天数");
		titleVector.add("出差天数");
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
				jTFwork_hour.setText(Integer.toString((int) v.get(3)));
				jTFover_hour.setText(Integer.toString((int) v.get(4)));
				jTFleave_day.setText(Integer.toString((int) v.get(5)));
				jTFerrand_day.setText(Integer.toString((int) v.get(6)));
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
		jP4.add(jLwork_hour);
		jP4.add(jTFwork_hour);
		jP4.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP4.setPreferredSize(new Dimension(20,20));
	
		jP5.add(jLover_hour);
		jP5.add(jTFover_hour);
		jP5.add(jLleave_day);
		jP5.add(jTFleave_day);
		jP5.add(jLerrand_day);
		jP5.add(jTFerrand_day);
		jP5.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP5.setPreferredSize(new Dimension(20,20));
		
		jP6.add(jBInsert);
		jP6.add(jBDeleteCurrentRecord);
		jP6.add(jBDeleteAllRecords);
		jP6.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP6.setPreferredSize(new Dimension(20,20));
		
		jPTop.add(jP1);
		jPTop.add(jP2);
		
		jPBottom.setLayout(new GridLayout(4, 1));
		jPBottom.add(jP3);
		jPBottom.add(jP4);
		jPBottom.add(jP5);
		jPBottom.add(jP6);
		
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
				&& !jTFwork_hour.getText().isEmpty()
				&& !jTFleave_day.getText().isEmpty()
				&& !jTFover_hour.getText().isEmpty()
				&& !jTFerrand_day.getText().isEmpty()){
			System.out.println("actionPerformed(). 插入");
			insertProcess();
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
			String sql = "select * from worktime where ";
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
				v.add(Integer.valueOf(rs.getInt("work_hour")));
				v.add(Integer.valueOf(rs.getInt("over_hour")));
				v.add(Integer.valueOf(rs.getInt("leave_day")));
				v.add(Integer.valueOf(rs.getInt("errand_day")));
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
			String sql = "select * from worktime;";
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
				v.add(Integer.valueOf(rs.getInt("work_hour")));
				v.add(Integer.valueOf(rs.getInt("over_hour")));
				v.add(Integer.valueOf(rs.getInt("leave_day")));
				v.add(Integer.valueOf(rs.getInt("errand_day")));
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
		int salary=0;
		int basic=0;
		int add=0;
		int sub=0;
		int total=0;
		int bonus=0;
		String id = jTFid.getText().trim();
		String person = jTFperson.getText().trim();
		String yearmonth = jTFyearmonth.getText().trim();
		String work_hour = jTFwork_hour.getText().trim();
		String over_hour = jTFover_hour.getText().trim();
		String leave_day = jTFleave_day.getText().trim();
		String errand_day = jTFerrand_day.getText().trim();
		String sql = "insert into worktime(id,person,yearmonth,work_hour,over_hour,leave_day,errand_day) values('";
		sql = sql + id + "','";
		sql = sql + person + "','";
		sql = sql + yearmonth + "',";
		sql = sql + work_hour + ",";
		sql = sql + over_hour + ",";
		sql = sql + leave_day + ",";
		sql = sql + errand_day + ");";
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
		
		try{
			// 建立查询条件
			sql = "select * from salary_set where person ='"+person+"';";
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
			while(rs.next()){
				salary=rs.getInt("salary");
			}
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
		
		String type=null;
		String name=null;
		int money=0;
		if(Integer.parseInt(over_hour)!=0)         //生成salary_other中奖励记录
		{
			type="0";
			id=yearmonth+person+type;
			name="加班福利";
			money=salary*Integer.parseInt(over_hour);
			bonus=money;
			sql="insert into salary_other values('"+id+"','"+yearmonth+"','"
			+person+"','"+type+"','"+name+"',"+String.valueOf(money)+","+String.valueOf(salary)+");";
		//	System.out.println(Integer.parseInt(over_hour)+","+salary);
			try{
				if (dbProcess.executeUpdate(sql) < 1) {
					System.out.println("insertProcess(). insert database failed.");
				}
			}catch(Exception e){
				System.out.println("e = " + e);
				JOptionPane.showMessageDialog(null,
					"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		if(Integer.parseInt(errand_day)!=0)             //生成salary_other中津贴记录
		{
			type="1";
			id=yearmonth+person+type;
			name="出差津贴";
			money=salary*Integer.parseInt(errand_day)*10;
			add=money;
			sql="insert into salary_other values('"+id+"','"+yearmonth+"','"
			+person+"','"+type+"','"+name+"',"+String.valueOf(money)+","+String.valueOf(salary)+");";
			try{
				if (dbProcess.executeUpdate(sql) < 1) {
					System.out.println("insertProcess(). insert database failed.");
				}
			}catch(Exception e){
				System.out.println("e = " + e);
				JOptionPane.showMessageDialog(null,
					"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		if(Integer.parseInt(leave_day)!=0)               //生成salary_other中扣发记录
		{
			type="2";
			id=yearmonth+person+type;
			name="缺勤扣发";
			money=salary*Integer.parseInt(leave_day)*10;
			sub=money;
			sql="insert into salary_other values('"+id+"','"+yearmonth+"','"
			+person+"','"+type+"','"+name+"',"+String.valueOf(money)+","+String.valueOf(salary)+");";
			try{
				if (dbProcess.executeUpdate(sql) < 1) {
					System.out.println("insertProcess(). insert database failed.");
				}
			}catch(Exception e){
				System.out.println("e = " + e);
				JOptionPane.showMessageDialog(null,
					"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
			}
		}

		id=yearmonth+person;                                 //生成salary表中记录
		money=salary*Integer.parseInt(work_hour);
		basic=money;
		total=basic-sub+add+bonus;
		sql="insert into salary values('"+id+"','"+yearmonth+"','"
		+person+"',"+String.valueOf(basic)+","+String.valueOf(bonus)+","+String.valueOf(add)
		+","+String.valueOf(sub)+","+String.valueOf(total)+");";
		try{
			if (dbProcess.executeUpdate(sql) < 1) {
				System.out.println("insertProcess(). insert database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}
	}


	public void deleteCurrentRecordProcess()
	{
		String idx=null;
		String id = jTFid.getText().trim();
		String person = jTFperson.getText().trim();
		String yearmonth = jTFyearmonth.getText().trim();
		String work_hour = jTFwork_hour.getText().trim();
		String over_hour = jTFover_hour.getText().trim();
		String leave_day = jTFleave_day.getText().trim();
		String errand_day = jTFerrand_day.getText().trim();
		String sql = null;
		if(Integer.parseInt(over_hour)!=0)     //删除salary_other中奖励记录
		{
			idx=yearmonth+person+"0";
			sql="delete from salary_other where id ='"+idx+"';";
			try{
				if (dbProcess.executeUpdate(sql) < 1) {
					System.out.println("deleteCurrentRecordProcess(). delete database failed.");
				}
			}catch(Exception e){
				System.out.println("e = " + e);
				JOptionPane.showMessageDialog(null,
					"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
			}
		} 
		if(Integer.parseInt(errand_day)!=0)   //删除salary_other中津贴记录
		{
			idx=yearmonth+person+"1";
			sql="delete from salary_other where id ='"+idx+"';";
			try{
				if (dbProcess.executeUpdate(sql) < 1) {
					System.out.println("deleteCurrentRecordProcess(). delete database failed.");
				}
			}catch(Exception e){
				System.out.println("e = " + e);
				JOptionPane.showMessageDialog(null,
					"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		if(Integer.parseInt(leave_day)!=0)      //删除salary_other中扣发记录
		{
			idx=yearmonth+person+"2";
			sql="delete from salary_other where id ='"+idx+"';";
			try{
				if (dbProcess.executeUpdate(sql) < 1) {
					System.out.println("deleteCurrentRecordProcess(). delete database failed.");
				}
			}catch(Exception e){
				System.out.println("e = " + e);
				JOptionPane.showMessageDialog(null,
					"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		idx = yearmonth + person ;         //删除salary中相应记录
		sql = "delete from salary where id = '"+idx+"';";
		try{
			if (dbProcess.executeUpdate(sql) < 1) {
				System.out.println("deleteCurrentRecordProcess(). delete database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}
		
		// 建立删除条件
		sql = "delete from worktime where id = '" + id + "';";
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

	public void deleteAllRecordsProcess()
	{
		// 建立删除条件
		String sql = "delete from worktime;";
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
		sql = "delete from salary;";
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
		sql = "delete from salary_other;";
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
		}
		System.out.println("jCBSelectQueryFieldTransfer(). outputStr = " + outputStr);
		return outputStr;
	}
}

