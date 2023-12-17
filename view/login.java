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

public class login extends JFrame implements ActionListener {
	JLabel JLusername=null;
	JLabel JLpasswd=null;
	JLabel JLlogin=null;
	JLabel JLjob=null;
	JLabel jltip=null;
	JTextField JTFusername=null;
	JPasswordField JPFpasswd=null;
	JButton JBlogin=null;
	JButton JBcannel=null;
	JComboBox<String>jCBRoleSelectField = null ;
	JPanel jp1,jp2,jp3,jp4,jp5=null;
	JPanel jptop,jpbottom=null;
//	private static DbProcess dbProcess;
	private static DbProcess dbProcess;

	String SelectQueryFieldStr = "员工";
	public login()
	{
		JLusername=new JLabel("用户名");
		JLusername.setFont(new Font("宋体", Font.BOLD, 20));
		JLpasswd=new JLabel("密码");
		JLpasswd.setFont(new Font("宋体", Font.BOLD, 20));
		JLlogin=new JLabel("登录界面");
		JLlogin.setFont(new Font("宋体", Font.BOLD, 40));
		jltip=new JLabel("若忘记密码请联系管理员");
		jltip.setFont(new Font("宋体", Font.BOLD, 15));

		JLjob=new JLabel("身份");
		JLjob.setFont((new Font("宋体", Font.BOLD, 15)));
	
		JTFusername=new JTextField(15);
		JTFusername.setFont((new Font("宋体", Font.BOLD, 20)));
		JPFpasswd=new JPasswordField(15);
		JPFpasswd.setFont((new Font("宋体", Font.BOLD, 20)));
	
		JBlogin=new JButton("登录");
		JBlogin.setFont((new Font("宋体", Font.BOLD, 20)));
	//	JBlogin.setPreferredSize(new Dimension(90,30));
		JBcannel=new JButton("退出");
		JBcannel.setFont((new Font("宋体", Font.BOLD, 20)));
	//	JBcannel.setPreferredSize(new Dimension(90,30));
		JBlogin.addActionListener(this);
		JBcannel.addActionListener(this);
	
		jCBRoleSelectField =new JComboBox<String>();
		jCBRoleSelectField.setFont((new Font("宋体", Font.BOLD, 15)));
		jCBRoleSelectField.addItem("员工");  
		jCBRoleSelectField.addItem("管理员");  
		jCBRoleSelectField.addItemListener(new ItemListener() {//下拉框事件监听  
			public void itemStateChanged(ItemEvent event) {  
				switch (event.getStateChange()) {  
				case ItemEvent.SELECTED:    
					SelectQueryFieldStr = (String) event.getItem();  
					break;  
				case ItemEvent.DESELECTED:  
					break;  
				}  
			}  
		});
		
		jp1=new JPanel();
		jp1.add(JLlogin,BorderLayout.SOUTH);

		
		jp2=new JPanel();
		jp2.add(JLusername);
		jp2.add(JTFusername);
		
		jp3=new JPanel();
		jp3.add(JLpasswd);
		jp3.add(JPFpasswd);
		
		
		jp5=new JPanel();
		jp5.add(JBlogin);
		jp5.add(JBcannel);
		
		jp4=new JPanel();
		jp4.add(jltip);
		
		this.add(jp1);
		jp1.setBounds(0, 50, 600, 50);
		this.add(jp2);
		jp2.setBounds(0, 150, 600, 40);
		this.add(jp3);
		jp3.setBounds(0, 190, 600, 40);
		this.add(jp5);
		jp5.setBounds(0, 270, 600, 50);
		this.add(jp4);
		jp4.setBounds(0, 320, 600, 50);
		
		this.setLayout(null);
		this.setTitle("工资管理系统_登录");
		this.setSize(600, 500); //界面大小
		this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
	}
	@Override
	public void actionPerformed(ActionEvent e) {    //按钮操作
		if(e.getActionCommand().equals("登录")
				&& !JTFusername.getText().isEmpty()
				&& !JPFpasswd.getText().isEmpty()){
			String username=JTFusername.getText();
			String passwd=JPFpasswd.getText();
			String[] flag=check(username,passwd);
			if(flag!=null)         //密码正确
			{
				new Menu(flag[0],flag[1]);
			//	if(flag.equals("管理员"))new DatabaseCourseDesign1();
			//	else new DatabaseCourseDesign2();
				System.out.println(flag[0]);
				this.dispose();
			}
			else
			{
				JFrame frame = new JFrame();
				frame.setTitle("ERROR");
				frame.setSize(400,150);
				frame.setLocationRelativeTo(null); 
				JLabel error=new JLabel("密码错误！");
				error.setFont((new Font("宋体", Font.BOLD, 20)));
				frame.add(error);
				error.setBounds(100, 50, 200, 30);
				frame.setVisible(true);
				frame.setResizable(false);
			}
		}
		else if(e.getActionCommand().equals("退出"))System.exit(0);
		else 
			{
				JFrame frame = new JFrame();
				frame.setTitle("ERROR");
				frame.setSize(400,150);
				frame.setLocationRelativeTo(null); 
				JLabel error=new JLabel("账号或密码为空！");
				error.setFont((new Font("宋体", Font.BOLD, 20)));
				frame.add(error);
				error.setBounds(100, 50, 200, 30);
				frame.setVisible(true);
				frame.setResizable(false);
			}
}
	public static void main(String[] args)
	{
		login lo=new login();
	}
	public String[] check(String uname,String pword)     //检测登入，若正确返回该用户job值（身份）
	{
		try{
			// 建立查询条件
			String sql = "select * from person where id = "+uname+" and passwd = "+pword +";";
			String[] res;
			res= new String[2];
		
			dbProcess = new DbProcess();
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
			
			// 将查询获得的记录数据，转换成适合生成JTable的数据形式
			while(rs.next())
			{
				res[0]=rs.getString("authority");
				res[1]=rs.getString("name");
				return res;
			}
				

		}catch(SQLException sqle){
			System.out.println("sqle = " + sqle);
			JOptionPane.showMessageDialog(null,
				"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
}

