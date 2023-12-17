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

public class create_salary extends JFrame{
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
	String SelectQueryFieldStr = "记录编号";
	
	// 构造函数
	public create_salary(String username) {
		// 创建组件
		jLsalaryInfoTable = new JLabel(username+"工资明细表");            //标签

	
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
				jTFtotal.setText((String) v.get(3));
				jTFbasic.setText((String) v.get(4));
				jTFbonus.setText((String) v.get(5));
				jTFadd_total.setText((String) v.get(6));
				jTFsub_total.setText((String) v.get(7));
			}
		});

 //布局
		jP1 = new JPanel();    
		jP2 = new JPanel();			
		jPTop = new JPanel();
		jPBottom = new JPanel();
		
		jP1.add(jLsalaryInfoTable,BorderLayout.SOUTH);
		jP2.add(studentJScrollPane);
		
	
		jPTop.add(jP1);
		jPTop.add(jP2);
				
		JFrame w = new JFrame();
		
		this.add("North", jPTop);
		this.add("South", jPBottom);
	
		this.setLayout(new GridLayout(2, 1));
		this.setTitle("工资报表");
		this.setSize(800, 400); //界面大小
		this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		dbProcess = new DbProcess();   //加载驱动
		queryProcess(username);
	}
	
	public void queryProcess(String sQueryField)   //查询操作
	{
		try{
			// 建立查询条件
			String sql = "select * from salary where yearmonth='"+sQueryField+"'; ";
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
		
}

