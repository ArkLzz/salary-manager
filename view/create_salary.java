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
	public create_salary(String username) {
		// �������
		jLsalaryInfoTable = new JLabel(username+"������ϸ��");            //��ǩ

	
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
		this.setTitle("���ʱ���");
		this.setSize(800, 400); //�����С
		this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		dbProcess = new DbProcess();   //��������
		queryProcess(username);
	}
	
	public void queryProcess(String sQueryField)   //��ѯ����
	{
		try{
			// ������ѯ����
			String sql = "select * from salary where yearmonth='"+sQueryField+"'; ";
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
		
}

