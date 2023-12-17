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

	String SelectQueryFieldStr = "Ա��";
	public login()
	{
		JLusername=new JLabel("�û���");
		JLusername.setFont(new Font("����", Font.BOLD, 20));
		JLpasswd=new JLabel("����");
		JLpasswd.setFont(new Font("����", Font.BOLD, 20));
		JLlogin=new JLabel("��¼����");
		JLlogin.setFont(new Font("����", Font.BOLD, 40));
		jltip=new JLabel("��������������ϵ����Ա");
		jltip.setFont(new Font("����", Font.BOLD, 15));

		JLjob=new JLabel("���");
		JLjob.setFont((new Font("����", Font.BOLD, 15)));
	
		JTFusername=new JTextField(15);
		JTFusername.setFont((new Font("����", Font.BOLD, 20)));
		JPFpasswd=new JPasswordField(15);
		JPFpasswd.setFont((new Font("����", Font.BOLD, 20)));
	
		JBlogin=new JButton("��¼");
		JBlogin.setFont((new Font("����", Font.BOLD, 20)));
	//	JBlogin.setPreferredSize(new Dimension(90,30));
		JBcannel=new JButton("�˳�");
		JBcannel.setFont((new Font("����", Font.BOLD, 20)));
	//	JBcannel.setPreferredSize(new Dimension(90,30));
		JBlogin.addActionListener(this);
		JBcannel.addActionListener(this);
	
		jCBRoleSelectField =new JComboBox<String>();
		jCBRoleSelectField.setFont((new Font("����", Font.BOLD, 15)));
		jCBRoleSelectField.addItem("Ա��");  
		jCBRoleSelectField.addItem("����Ա");  
		jCBRoleSelectField.addItemListener(new ItemListener() {//�������¼�����  
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
		this.setTitle("���ʹ���ϵͳ_��¼");
		this.setSize(600, 500); //�����С
		this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
	}
	@Override
	public void actionPerformed(ActionEvent e) {    //��ť����
		if(e.getActionCommand().equals("��¼")
				&& !JTFusername.getText().isEmpty()
				&& !JPFpasswd.getText().isEmpty()){
			String username=JTFusername.getText();
			String passwd=JPFpasswd.getText();
			String[] flag=check(username,passwd);
			if(flag!=null)         //������ȷ
			{
				new Menu(flag[0],flag[1]);
			//	if(flag.equals("����Ա"))new DatabaseCourseDesign1();
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
				JLabel error=new JLabel("�������");
				error.setFont((new Font("����", Font.BOLD, 20)));
				frame.add(error);
				error.setBounds(100, 50, 200, 30);
				frame.setVisible(true);
				frame.setResizable(false);
			}
		}
		else if(e.getActionCommand().equals("�˳�"))System.exit(0);
		else 
			{
				JFrame frame = new JFrame();
				frame.setTitle("ERROR");
				frame.setSize(400,150);
				frame.setLocationRelativeTo(null); 
				JLabel error=new JLabel("�˺Ż�����Ϊ�գ�");
				error.setFont((new Font("����", Font.BOLD, 20)));
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
	public String[] check(String uname,String pword)     //�����룬����ȷ���ظ��û�jobֵ����ݣ�
	{
		try{
			// ������ѯ����
			String sql = "select * from person where id = "+uname+" and passwd = "+pword +";";
			String[] res;
			res= new String[2];
		
			dbProcess = new DbProcess();
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
			
			// ����ѯ��õļ�¼���ݣ�ת�����ʺ�����JTable��������ʽ
			while(rs.next())
			{
				res[0]=rs.getString("authority");
				res[1]=rs.getString("name");
				return res;
			}
				

		}catch(SQLException sqle){
			System.out.println("sqle = " + sqle);
			JOptionPane.showMessageDialog(null,
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
}

