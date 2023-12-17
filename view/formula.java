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

public class formula {
	static int a=1;
	static int b=1;
	static int c=1;
	static int d=1;
	JTextField jtf1=null;
	JTextField jtf2=null;
	JTextField jtf3=null;
	JTextField jtf4=null;
	public void setformula(){
		JFrame w=new JFrame("工资公式设置");
		JLabel jl1=new JLabel("基础工资系数");
		JLabel jl2=new JLabel("奖金系数");
		JLabel jl3=new JLabel("其他应发系数");
		JLabel jl4=new JLabel("扣发总额系数");
		jtf1=new JTextField(5);
		jtf2=new JTextField(5);
		jtf3=new JTextField(5);
		jtf4=new JTextField(5);
		JButton jb1=new JButton("确定");
		JButton jb2=new JButton("关闭");
		JPanel jp1=new JPanel();
		JPanel jp2=new JPanel();
		JPanel jp3=new JPanel();
		jb1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) { 
				if(!jtf1.getText().isEmpty()
					&&!jtf2.getText().isEmpty()
					&&!jtf3.getText().isEmpty()
					&&!jtf4.getText().isEmpty()){
						update();
						JFrame frame = new JFrame();
						frame.setTitle("弹窗");
						frame.setSize(300,100);
						frame.setLocationRelativeTo(null); 
						JLabel error=new JLabel("设置成功！");
						error.setFont((new Font("宋体", Font.BOLD, 20)));
						frame.add(error);
						error.setBounds(100, 50, 200, 30);
						frame.setVisible(true);
						frame.setResizable(false);
				}else 
				{
					JFrame frame = new JFrame();
					frame.setTitle("ERROR");
					frame.setSize(300,100);
					frame.setLocationRelativeTo(null); 
					JLabel error=new JLabel("请输入系数！");
					error.setFont((new Font("宋体", Font.BOLD, 20)));
					frame.add(error);
					error.setBounds(100, 50, 200, 30);
					frame.setVisible(true);
					frame.setResizable(false);
				}
			}
		});
		jb2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) { 
				w.dispose();
			}
		});
		
		jp1.add(jl1);jp1.add(jtf1);
		jp1.add(jl2);jp1.add(jtf2);
		jp2.add(jl3);jp2.add(jtf3);
		jp2.add(jl4);jp2.add(jtf4);
		jp3.add(jb1);jp3.add(jb2);
		w.add(jp1);
		w.add(jp2);
		w.add(jp3);
		
		w.setLayout(new GridLayout(3, 1));
		w.setSize(400, 200);
		w.setLocationRelativeTo(null); 
		w.setVisible(true);
		w.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		w.setResizable(false);
		
	}
	public int operation(int basic,int bonus,int add_total,int sub_total)
	{
		queryformula();
		return basic*a+b*bonus+add_total*c-sub_total*d;
	}
	public void showformula()
	{
		queryformula();
		JLabel jlformula=new JLabel("当前工资公式：实际工资=基础工资*"+a+"+奖金"+b+"+其他应发*"+c+"-扣罚总额*"+d);
		JFrame frame = new JFrame();
		frame.setTitle("当前工资公式");
		frame.setSize(550,100);
		frame.setLocationRelativeTo(null); 
		jlformula.setFont((new Font("宋体", Font.BOLD, 15)));
		frame.add(jlformula);
		jlformula.setBounds(0, 50, 300, 50);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	public void update(){
		DbProcess dbProcess = new DbProcess();
		String a1=jtf1.getText().trim();
		String b1=jtf2.getText().trim();
		String c1=jtf3.getText().trim();
		String d1=jtf4.getText().trim();
		
		String sql = "update formula set a = ";
		sql = sql + a1 + ", b = ";
		sql = sql + b1 + ", c = ";
		sql = sql + c1 + ", d = ";
		sql = sql + d ;
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
	}
	public void queryformula() {
		try{
			DbProcess dbProcess = new DbProcess();
			String sql = "select * from formula";
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
			while(rs.next()){
				a=rs.getInt("a");
				b=rs.getInt("b");
				c=rs.getInt("c");
				d=rs.getInt("d");
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
	}
}
