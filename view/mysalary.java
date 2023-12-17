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

public class mysalary extends JFrame{
	// �������
	JLabel jLsalaryInfoTable = null;//������Ϣ��
	JLabel jLSelectQueryField = null;//ѡ���ѯ�ֶ�
	JLabel jLEqual = null;//=
	JLabel jLid = null; //��¼���
	JLabel jLperson = null;//Ա����
	JLabel jLyearmonth = null;//����
	JLabel jLbasic = null;//��������
	JLabel jLbonus = null;//����
	JLabel jLadd_total = null;//����Ӧ��
	JLabel jLsub_total = null;//�۷��ܶ�
	JLabel jLtotal = null;//ʵ�ʹ���
	JLabel jlsalary = null;

	JTextField jTFQueryField = null;
	JTextField jTFid = null;
	JTextField jTFperson = null;
	JTextField jTFyearmonth = null;
	JTextField jTFtotal = null;
	JTextField jTFbasic = null;
	JTextField jTFbonus = null;
	JTextField jTFadd_total = null;
	JTextField jTFsub_total = null;
	
	JButton jBQuery = null;//��ѯ
	JButton jBQueryAll = null;//��ѯ���м�¼
	JButton jBInsert = null;//����
	JButton jBUpdate = null;//����
	JButton jBDeleteCurrentRecord = null;//ɾ����ǰ��¼
	JButton jBDeleteAllRecords = null;//ɾ�����м�¼
	
	//JComboBox jCBSelectQueryField = null;
	JComboBox<String> jCBSelectQueryField = null;//��ѯ�ֶ�
	JPanel jP1, jP2,jP3,jP4,jP5,jP6 = null;      
	JPanel jPTop, jPBottom = null;
	DefaultTableModel studentTableModel = null;
	JTable studentJTable = null;
	JScrollPane studentJScrollPane = null;
	Vector personVector = null;
	Vector titleVector = null;
	
	private static DbProcess dbProcess;
	String SelectQueryFieldStr = "��¼���";
	
	// ���캯��
	public mysalary(String flag,String username) {
		// �������
		jLsalaryInfoTable = new JLabel("������ϸ��");            //��ǩ
		jLSelectQueryField = new JLabel("ѡ���ѯ�ֶ�");
		jLEqual = new JLabel(" = ");
		jLid = new JLabel("��¼���");
		jLperson = new JLabel("Ա����");
		jLyearmonth = new JLabel("����");
		jLbasic = new JLabel("��������");
		jLsub_total = new JLabel("�۷��ܶ�");
		jLbonus = new JLabel("����");
		jLadd_total = new JLabel("����Ӧ��");
		jLtotal = new JLabel("ʵ�ʹ���");
		jlsalary = new JLabel();
		jlsalary.setText("��ǰ�ҵ�ʱн:"+querysalary(username));
		jlsalary.setFont((new Font("����", Font.BOLD, 20)));
		
		jTFQueryField = new JTextField(10);             //�ı��򳤶�
		jTFid = new JTextField(10);
		jTFperson = new JTextField(10);
		jTFyearmonth = new JTextField(10);
		jTFtotal = new JTextField(10);
		jTFbasic = new JTextField(10);
		jTFbonus = new JTextField(10);
		jTFadd_total = new JTextField(10);
		jTFsub_total = new JTextField(10);
		
		jBQuery = new JButton("��ѯ");                //��ť
		jBQueryAll = new JButton("��ѯ���м�¼");
		jBInsert = new JButton("����");
		jBUpdate = new JButton("����");
		jBDeleteCurrentRecord = new JButton("ɾ����ǰ��¼");
		jBDeleteAllRecords = new JButton("ɾ�����м�¼");
		// ���ü���
		jBQuery.addActionListener(new ActionListener()
				{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if(!jTFQueryField.getText().isEmpty())
					{
						System.out.println("actionPerformed(). ��ѯ");
					String sQueryField = jTFQueryField.getText().trim();
					queryProcess(sQueryField,username);
					jTFQueryField.setText("");
					}
					else
					{
						JFrame frame = new JFrame();
						frame.setTitle("ERROR");
						frame.setSize(400,150);
						frame.setLocationRelativeTo(null); 
						JLabel error=new JLabel("����Ϊ�գ�");
						error.setFont((new Font("����", Font.BOLD, 20)));
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
			myqueryProcess(username);
		}
		});

		jCBSelectQueryField = new JComboBox<String>();//��ѯ�ֶ�                   //ѡ���ѯ
		jCBSelectQueryField.addItem("��¼���");   
		jCBSelectQueryField.addItem("����");
		jCBSelectQueryField.addItemListener(new ItemListener() {//�������¼�����  
            public void itemStateChanged(ItemEvent event) {  
                switch (event.getStateChange()) {  
                case ItemEvent.SELECTED:  
                	SelectQueryFieldStr = (String) event.getItem();  
                    System.out.println("ѡ�У�" + SelectQueryFieldStr);  
                    break;  
                case ItemEvent.DESELECTED:  
                    System.out.println("ȡ��ѡ�У�" + event.getItem());  
                    break;  
                }  
            }  
        });
	
		personVector = new Vector();
		titleVector = new Vector();
		
		// �����ͷ
		titleVector.add("��¼���");
		titleVector.add("Ա����");
		titleVector.add("����");
		titleVector.add("ʵ�ʹ���");
		titleVector.add("��������");
		titleVector.add("����");
		titleVector.add("����Ӧ��");
		titleVector.add("�۷��ܶ�");
		//studentTableModel = new DefaultTableModel(tableTitle, 15);
		studentJTable = new JTable(personVector, titleVector);
		studentJTable.setPreferredScrollableViewportSize(new Dimension(600,160));
		studentJScrollPane = new JScrollPane(studentJTable);
		//�ֱ�����ˮƽ�ʹ�ֱ�������Զ�����
		studentJScrollPane.setHorizontalScrollBarPolicy(                
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		studentJScrollPane.setVerticalScrollBarPolicy(                
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		//Ϊ�����Ӽ����� 
		studentJTable.addMouseListener(new MouseAdapter()      //���ѡ��
		{ 
			public void mouseClicked(MouseEvent e) 
			{ 
				int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint()); // �����λ��
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

 //����
		jP1 = new JPanel();    
		jP2 = new JPanel();		
		jP3 = new JPanel();	
		jP4 = new JPanel();
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
		
		jP4.add(jlsalary);
	
		jPTop.add(jP1);
		jPTop.add(jP2);
		
		jPBottom.setLayout(new GridLayout(2, 1));
		jPBottom.add(jP3);
		jPBottom.add(jP4);
		
		
		
		JFrame w = new JFrame();
		
		this.add("North", jPTop);
		this.add("South", jPBottom);
	
		this.setLayout(new GridLayout(2, 1));
		this.setTitle("���ʹ���ϵͳ");
		this.setSize(800, 400); //�����С
		this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		dbProcess = new DbProcess();   //��������
		myqueryProcess(username);
	}
	
	
	public void queryProcess(String sQueryField,String username)   //��ѯ����
	{
		try{
			// ������ѯ����
			String sql = "select * from salary where ";
			String queryFieldStr = jCBSelectQueryFieldTransfer(SelectQueryFieldStr);
			sql = sql + queryFieldStr;
			sql = sql + " = ";
			sql = sql + "'" + sQueryField + "' and person = (select id from person where name = '";
			sql = sql + username + "');";
			
			System.out.println("queryProcess(). sql = " + sql);
	
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
	
			// ����ѯ��õļ�¼���ݣ�ת�����ʺ�����JTable��������ʽ
			personVector.clear();
			while(rs.next()){
				Vector v = new Vector();
				v.add(rs.getString("id"));
				v.add(rs.getString("person"));
				v.add(rs.getString("yearmonth"));
				v.add(Integer.valueOf(rs.getInt("total")));
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
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void myqueryProcess(String sQueryField)   //��ѯ����
	{
		try{
			// ������ѯ����
			String sql = "select salary.* from person,salary where person.name='"+sQueryField+"' and person.id = salary.person";
			System.out.println("queryProcess(). sql = " + sql);
	
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
			formula x=new formula();
	
			// ����ѯ��õļ�¼���ݣ�ת�����ʺ�����JTable��������ʽ
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
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public String jCBSelectQueryFieldTransfer(String InputStr)
	{
		String outputStr = "";
		System.out.println("jCBSelectQueryFieldTransfer(). InputStr = " + InputStr);
		
		if(InputStr.equals("��¼���")){
			outputStr = "id";
		}else if(InputStr.equals("����")){
			outputStr = "yearmonth";
		}
		System.out.println("jCBSelectQueryFieldTransfer(). outputStr = " + outputStr);
		return outputStr;
	}
	
	public String querysalary(String username)
	{
		String res=null;
		try{
			// ������ѯ����
			String sql = "select * from salary_set where person = (select id from person where "
					+ "name = '"+username+"');";
			System.out.println("queryAllProcess(). sql = " + sql);
			dbProcess = new DbProcess();   //��������
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
			// ����ѯ��õļ�¼���ݣ�ת�����ʺ�����JTable��������ʽ
			while(rs.next())
			{
				int i=rs.getInt("salary");
				res=Integer.toString(i);
				return res;
			}
			dbProcess.disconnect();
		}catch(SQLException sqle){
			System.out.println("sqle = " + sqle);
			JOptionPane.showMessageDialog(null,
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
	
}

