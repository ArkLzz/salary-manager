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
	// �������
	JLabel jLsalaryInfoTable = null;//������Ϣ��
	JLabel jLSelectQueryField = null;//ѡ���ѯ�ֶ�
	JLabel jLEqual = null;//=
	JLabel jLid = null; //��¼���
	JLabel jLperson = null;//Ա����
	JLabel jLyearmonth = null;//����
	JLabel jLtype = null;//����
	JLabel jLname = null;//��¼��
	JLabel jLmoney = null;//���
	JLabel jLtip= null;
	
	JTextField jTFQueryField;
	JTextField jTFid = null;
	JTextField jTFperson = null;
	JTextField jTFyearmonth = null;
	JTextField jTFtype = null;
	JTextField jTFname = null;
	JTextField jTFmoney = null;
	
	JButton jBQuery = null;//��ѯ
	JButton jBQueryAll = null;//��ѯ���м�¼
	
	JComboBox<String> jCBSelectQueryField = null;//��ѯ�ֶ�
	JPanel jP1, jP2,jP3,jP4,jP5= null;      
	JPanel jPTop, jPBottom = null;
	DefaultTableModel studentTableModel = null;
	JTable studentJTable = null;
	JScrollPane studentJScrollPane = null;
	Vector personVector = null;
	Vector titleVector = null;
	
	private static DbProcess dbProcess;
	String SelectQueryFieldStr = "��¼���";
	
	// ���캯��
	public salary_other() {
		// �������
		jLsalaryInfoTable = new JLabel("������ϸ��");            //��ǩ
		jLSelectQueryField = new JLabel("ѡ���ѯ�ֶ�");
		jLEqual = new JLabel(" = ");
		jLid = new JLabel("��¼���");
		jLperson = new JLabel("Ա����");
		jLyearmonth = new JLabel("����");
		jLname = new JLabel("��¼��");
		jLtype = new JLabel("����");
		jLmoney = new JLabel("���");
		jLtip = new JLabel("Tips:�����С�0��Ϊ������1��Ϊ������2��Ϊ�۷�");
		
		jTFQueryField = new JTextField(10);             //�ı��򳤶�
		jTFid = new JTextField(10);
		jTFperson = new JTextField(10);
		jTFyearmonth = new JTextField(10);
		jTFname = new JTextField(10);
		jTFtype = new JTextField(10);
		jTFmoney = new JTextField(10);
		
		jBQuery = new JButton("��ѯ");                //��ť
		jBQueryAll = new JButton("��ѯ���м�¼");
	
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
					queryProcess(sQueryField);
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
			queryAllProcess();
		}
		});

		jCBSelectQueryField = new JComboBox<String>();//��ѯ�ֶ�                   //ѡ���ѯ
		jCBSelectQueryField.addItem("��¼���");   
		jCBSelectQueryField.addItem("Ա����");
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
		titleVector.add("����");
		titleVector.add("��¼��");
		titleVector.add("���");
		titleVector.add("ʱн");
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
				jTFtype.setText((String) v.get(3));
				jTFname.setText((String) v.get(4));
				jTFmoney.setText(Integer.toString((int) v.get(5)));
			}
		});

 //����
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
		this.setTitle("���ʹ���ϵͳ");
		this.setSize(800, 350); //�����С
		this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		dbProcess = new DbProcess();   //��������
	}
	
	
	public void queryProcess(String sQueryField)   //��ѯ����
	{
		try{
			// ������ѯ����
			String sql = "select * from salary_other where ";
			String queryFieldStr = jCBSelectQueryFieldTransfer(SelectQueryFieldStr);
			sql = sql + queryFieldStr;
			sql = sql + " = ";
			sql = sql + "'" + sQueryField + "');";
			
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
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void queryAllProcess()
	{
		try{
			// ������ѯ����
			String sql = "select * from salary_other;";
			System.out.println("queryAllProcess(). sql = " + sql);
	
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
		}else if(InputStr.equals("Ա����")){
			outputStr = "person";
		}
		System.out.println("jCBSelectQueryFieldTransfer(). outputStr = " + outputStr);
		return outputStr;
	}

	
}

