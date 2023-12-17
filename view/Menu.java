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

public class Menu{
	DbProcess dbProcess;
	String SelectQueryFieldStr = new String("Ա����");
	DefaultTableModel personTableModel = null;
	JTable personJTable = null;
	JScrollPane personJScrollPane = null;
	Vector personVector = null;
	Vector titleVector = null;
	formula x=new formula();
	JComboBox<String> jCBSelectQueryField;
	JButton jbformula=null;
	
	public Menu(String flag,String username)
	{	
		JMenuBar jmenubar=new JMenuBar();
		dbProcess = new DbProcess(); 
		
		JLabel jltitle=new JLabel("��ӭ��¼���ʹ���ϵͳ��"+flag);
		jltitle.setFont(new Font("����", Font.BOLD, 30));
		JLabel jluser = new JLabel("��ǰ�û���"+username);
		jluser.setFont(new Font("����", Font.BOLD, 20));
		JLabel jltips = new JLabel("ע�⣡��������ʱ���԰���ʽ���룬  ���磺202301Ϊ2023��1��");
		jluser.setFont(new Font("����", Font.BOLD, 15));
		
		JMenu jmenu=new JMenu("����");
		
		JMenuItem it1=new JMenuItem("������Ϣ��");
		JMenuItem it2=new JMenuItem("������ϸ��");
		JMenuItem it3=new JMenuItem("ʱн���ñ�");
		JMenuItem it4=new JMenuItem("��ʱ�����");
		JMenuItem it5=new JMenuItem("������ϸ��");

		JButton jBexit = new JButton("�˳�");//��ѯ
		JButton jBpasswd = new JButton("�޸�����");
		JButton jBmysalary = new JButton("�ҵĹ���");//��ѯ���м�¼
		JButton jBmyworktime = new JButton("�ҵĹ�ʱ");
		jbformula = new JButton("���ʹ�ʽ");
		JFrame w=new JFrame();
        
		w.add(jltitle);
		jltitle.setBounds(150, 50,600, 60);
		w.add(jluser);
		jluser.setBounds(300,120,300,50);
		w.add(jltips);
		jltips.setBounds(200,400,600,30);
		
		w.add(jbformula);
		jbformula.addActionListener(new ActionListener(){         //��ѯperson
			@Override
			public void actionPerformed(ActionEvent e) { 
				x.showformula();
			}
		});
		jbformula.setBounds(200, 280, 100, 30);
		w.add(jBmysalary);
		w.add(jBmyworktime);
		jBmysalary.setBounds(200,350,100,30);
		jBmyworktime.setBounds(200,210,100,30);
		jBmysalary.addActionListener(new ActionListener(){         //��ѯperson
			@Override
			public void actionPerformed(ActionEvent e) { 
				new mysalary(flag,username);
			}
		});
		jBmyworktime.addActionListener(new ActionListener(){         //��ѯperson
			@Override
			public void actionPerformed(ActionEvent e) { 
				new myworktime(flag,username);
			}
		});
		w.add(jBexit);
		jBexit.setBounds(600,350,90,30);
		jBexit.addActionListener(new ActionListener(){         //��ѯperson
				@Override
				public void actionPerformed(ActionEvent e) { 
					System.exit(0);      
				}
			});
		
		
		w.setLayout(null);
		w.setTitle("���ʹ���ϵͳ");
		w.setSize(800, 500);
		w.setLocationRelativeTo(null); 
		w.setVisible(true);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setResizable(false);
		
		if(flag.equals("����Ա"))
		{
			jmenu.add(it1);
			jmenu.add(it2);
			jmenu.add(it3);
			jmenu.add(it4);
			jmenu.add(it5);
			jmenubar.add(jmenu);
			w.setJMenuBar(jmenubar);
			w.add(jBpasswd);
			jBpasswd.setBounds(600,280,90,30);
			jBpasswd.addActionListener(new ActionListener(){         //��ѯperson
				@Override
				public void actionPerformed(ActionEvent e) { 
					JFrame s=new JFrame("��������");
					JLabel jlusername= new JLabel("�û���");
					JLabel jlpasswd=new JLabel("���룺");
					JLabel jltip=new JLabel("���벻Ϊ���Ҳ�����10λ");
					JTextField jtfpasswd=new JTextField(10);
					JTextField jtfusername=new JTextField(10);
					JButton jb1= new JButton("ȷ��");
					JButton jb2 = new JButton("�ر�");
					jb1.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							String pw=jtfpasswd.getText();
							String un=jtfusername.getText();
							updatepasswd1(pw,un);
						}
					});
					jb2.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							s.dispose();
						}
					});
					JPanel jp4=new JPanel();
					JPanel jp1=new JPanel();
					JPanel jp2=new JPanel();
					JPanel jp3=new JPanel();
					jp4.add(jlusername);
					jp4.add(jtfusername);
					jp1.add(jlpasswd);
					jp1.add(jtfpasswd);
					jp2.add(jb1);
					jp2.add(jb2);
					jp3.add(jltip);
					s.add(jp4);
					s.add(jp1);
					s.add(jp3);
					s.add(jp2);
					
					s.setLayout(new GridLayout(4, 1));
					s.setSize(400, 200);
					s.setLocationRelativeTo(null); 
					s.setVisible(true);
					s.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					s.setResizable(false);
				}
			});
			it1.addActionListener(new ActionListener(){         //��ѯperson
				@Override
				public void actionPerformed(ActionEvent e) { 
					new person1();        
				}
			});
			it2.addActionListener(new ActionListener(){          //�޸�salary
				@Override
				public void actionPerformed(ActionEvent e) { 
					new salary1();
				}
			});
			it3.addActionListener(new ActionListener(){          //�޸�salary
				@Override
				public void actionPerformed(ActionEvent e) { 
					new salary_set();
				}
			});
			it4.addActionListener(new ActionListener(){          //�޸�salary
				@Override
				public void actionPerformed(ActionEvent e) { 
					new work_time();
				}
			});
			it5.addActionListener(new ActionListener(){          //�޸�salary
				@Override
				public void actionPerformed(ActionEvent e) { 
					new salary_other();
				}
			});
			
			JButton jbtable=new JButton("���ɹ��ʱ���");
			w.add(jbtable);
			jbtable.setBounds(350 , 350 , 125 , 30);
			jbtable.addActionListener(new ActionListener(){         //��ѯperson
				@Override
				public void actionPerformed(ActionEvent e) { 
					JFrame a=new JFrame("����������");
					JButton jb1=new JButton("ȷ��");
					JButton jb2=new JButton("�ر�");
					JTextField jtfym=new JTextField(10);
					JPanel jp1=new JPanel();
					JPanel jp2=new JPanel();
					jp1.add(jtfym);
					jtfym.setFont((new Font("����", Font.BOLD, 20)));
					a.add(jp1);
					jp2.add(jb1);
					jp2.add(jb2);
					jp1.setBounds(0, 30, 400, 30);
					a.add(jp2);
					jp2.setBounds(0, 100, 200, 50);
					jb1.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e) { 
							String text=jtfym.getText().trim();
							if(jtfym.getText().isEmpty()){
									JFrame frame = new JFrame();
									frame.setTitle("ERROR");
									frame.setSize(300,100);
									frame.setLocationRelativeTo(null); 
									JLabel error=new JLabel("���������£�");
									error.setFont((new Font("����", Font.BOLD, 20)));
									frame.add(error);
									error.setBounds(100, 50, 200, 30);
									frame.setVisible(true);
									frame.setResizable(false);
							}else new create_salary(text);
						}
					});
					jb2.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e) { 
							a.dispose();
						}
					});
					
					a.setSize(400, 200);
					a.setLocationRelativeTo(null); 
					a.setVisible(true);
					a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					a.setResizable(false);
				}
			});
			JButton jbformula= new JButton("���ù��ʹ�ʽ");
			jbformula.setBounds(350 , 280 , 125 , 30);
			w.add(jbformula);
			jbformula.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) { 
					x.setformula();
				}
			});
			
		}
		if(flag.equals("Ա��"))
		{
			jmenu.add(it1);
			jmenubar.add(jmenu);
			w.setJMenuBar(jmenubar);
			it1.addActionListener(new ActionListener(){         //��ѯperson
				@Override
				public void actionPerformed(ActionEvent e) { 
					new person2();        
				}
			});
			w.add(jBpasswd);
			jBpasswd.setBounds(600,280,90,30);
			jBpasswd.addActionListener(new ActionListener(){         //��ѯperson
				@Override
				public void actionPerformed(ActionEvent e) { 
					JFrame s=new JFrame("��������");
					JLabel jlpasswd=new JLabel("���룺");
					JLabel jltip=new JLabel("���벻Ϊ���Ҳ�����10λ");
					JTextField jtfpasswd=new JTextField(10);
					JButton jb1= new JButton("ȷ��");
					JButton jb2 = new JButton("�ر�");
					jb1.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							String pw=jtfpasswd.getText();
							updatepasswd2(pw,username);
						}
					});
					jb2.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							s.dispose();
						}
					});
					JPanel jp1=new JPanel();
					JPanel jp2=new JPanel();
					JPanel jp3=new JPanel();
					jp1.add(jlpasswd);
					jp1.add(jtfpasswd);
					jp2.add(jb1);
					jp2.add(jb2);
					jp3.add(jltip);
					s.add(jp1);
					s.add(jp3);
					s.add(jp2);
					
					s.setLayout(new GridLayout(3, 1));
					s.setSize(400, 200);
					s.setLocationRelativeTo(null); 
					s.setVisible(true);
					s.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					s.setResizable(false);
				}
			});
		}
	}
	public void updatepasswd2(String wd,String name)
	{
		
		// ������������
		String sql="update person set passwd = '"+wd+"' where name = '"+name+"';";
		System.out.println("updateProcess(). sql = " + sql);
		try{
			if (dbProcess.executeUpdate(sql) < 1) {
				System.out.println("updateProcess(). update database failed.");
			
			}else {
				JFrame frame = new JFrame();
				frame.setTitle("����");
				frame.setSize(300,100);
				frame.setLocationRelativeTo(null); 
				JLabel error=new JLabel("���óɹ���");
				error.setFont((new Font("����", Font.BOLD, 20)));
				frame.add(error);
				error.setBounds(100, 50, 200, 30);
				frame.setVisible(true);
				frame.setResizable(false);
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}
	}
	public void updatepasswd1(String wd,String name)
	{
		
		// ������������
		String sql="update person set passwd = '"+wd+"' where id = '"+name+"';";
		System.out.println("updateProcess(). sql = " + sql);
		try{
			if (dbProcess.executeUpdate(sql) < 1) {
				System.out.println("updateProcess(). update database failed.");
			
			}else {
				JFrame frame = new JFrame();
				frame.setTitle("����");
				frame.setSize(300,100);
				frame.setLocationRelativeTo(null); 
				JLabel error=new JLabel("���óɹ���");
				error.setFont((new Font("����", Font.BOLD, 20)));
				frame.add(error);
				error.setBounds(100, 50, 200, 30);
				frame.setVisible(true);
				frame.setResizable(false);
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}
	}
}
