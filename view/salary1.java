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
	public salary1() {
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

		// ���ü���
		jBQuery.addActionListener(this);
		jBQueryAll.addActionListener(this);
		
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
				jTFtotal.setText(Integer.toString((int) v.get(3)));
				jTFbasic.setText(Integer.toString((int) v.get(4)));
				jTFbonus.setText(Integer.toString((int) v.get(5)));
				jTFadd_total.setText(Integer.toString((int) v.get(6)));
				jTFsub_total.setText(Integer.toString((int) v.get(7)));
			}
		});

 //����
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
		this.setTitle("���ʹ���ϵͳ");
		this.setSize(800, 500); //�����С
		this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		dbProcess = new DbProcess();   //��������
	}

	@Override
	public void actionPerformed(ActionEvent e) {    //��ť����
		if(e.getActionCommand().equals("��ѯ")  
			&& !jTFQueryField.getText().isEmpty()){
			System.out.println("actionPerformed(). ��ѯ");
			String sQueryField = jTFQueryField.getText().trim();
			queryProcess(sQueryField);
			jTFQueryField.setText("");
		}else if(e.getActionCommand().equals("��ѯ���м�¼")) {
			System.out.println("actionPerformed(). ��ѯ���м�¼");
			queryAllProcess();
		}else if(e.getActionCommand().equals("����")
				&& !jTFid.getText().isEmpty()
				&& !jTFperson.getText().isEmpty()
				&& !jTFyearmonth.getText().isEmpty()
			//	&& !jTFtotal.getText().isEmpty()
				&& !jTFbasic.getText().isEmpty()
				&& !jTFbonus.getText().isEmpty()
				&& !jTFadd_total.getText().isEmpty()
				&& !jTFsub_total.getText().isEmpty()){
			System.out.println("actionPerformed(). ����");
			insertProcess();
		}else if(e.getActionCommand().equals("����")
				&& !jTFid.getText().isEmpty()
				&& !jTFperson.getText().isEmpty()
				&& !jTFyearmonth.getText().isEmpty()
			//	&& !jTFtotal.getText().isEmpty()
				&& !jTFbasic.getText().isEmpty()
				&& !jTFbonus.getText().isEmpty()
				&& !jTFadd_total.getText().isEmpty()
				&& !jTFsub_total.getText().isEmpty()){
			System.out.println("actionPerformed(). ����");
			updateProcess();
		}else if(e.getActionCommand().equals("ɾ����ǰ��¼")){
			System.out.println("actionPerformed(). ɾ����ǰ��¼");
			deleteCurrentRecordProcess();
		}else if(e.getActionCommand().equals("ɾ�����м�¼")){
			System.out.println("actionPerformed(). ɾ�����м�¼");
			deleteAllRecordsProcess();
		}
	}
	
	
	
	public void queryProcess(String sQueryField)   //��ѯ����
	{
		try{
			// ������ѯ����
			String sql = "select * from salary where ";
			String queryFieldStr = jCBSelectQueryFieldTransfer(SelectQueryFieldStr);
			sql = sql + queryFieldStr;
			sql = sql + " = ";
			sql = sql + "'" + sQueryField + "';";
			
			
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
	
	public void queryAllProcess()
	{
		try{
			// ������ѯ����
			String sql = "select * from salary;";
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
		
		// ������������
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
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
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
		
		// ������������
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
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}
		queryAllProcess();
	}

	public void deleteCurrentRecordProcess()
	{
		String id = jTFid.getText().trim();
		
		// ����ɾ������
		String sql = "delete from salary where id = '" + id + "';";
		System.out.println("deleteCurrentRecordProcess(). sql = " + sql);
		try{
			if (dbProcess.executeUpdate(sql) < 1) {
				System.out.println("deleteCurrentRecordProcess(). delete database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}
		queryAllProcess();
	}

	public static void main(String[] args) {
        // TODO Auto-generated method stub
		DatabaseCourseDesign1 getcon = new  DatabaseCourseDesign1();
    }
	public void deleteAllRecordsProcess()
	{
		// ����ɾ������
		String sql = "delete from salary;";
		System.out.println("deleteAllRecordsProcess(). sql = " + sql);
		try{
			if (dbProcess.executeUpdate(sql) < 1) {
				System.out.println("deleteAllRecordsProcess(). delete database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}
		queryAllProcess();
	}
	
	public String jCBSelectQueryFieldTransfer(String InputStr)
	{
		String outputStr = "";
		System.out.println("jCBSelectQueryFieldTransfer(). InputStr = " + InputStr);
		
		if(InputStr.equals("��¼���")){
			outputStr = "id";
		}else if(InputStr.equals("Ա����")){
			outputStr = "person";
		}else if(InputStr.equals("����")){
			outputStr = "yearmonth";
		}else if(InputStr.equals("ʵ�ʹ���")){
			outputStr = "total";
		}else if(InputStr.equals("��������")){
			outputStr = "basic";
		}else if(InputStr.equals("����")){
			outputStr = "bonus";
		}else if(InputStr.equals("����Ӧ��")){
			outputStr = "add_total";
		}else if(InputStr.equals("�۷��ܶ�")){
			outputStr = "sub_total";
		}
		System.out.println("jCBSelectQueryFieldTransfer(). outputStr = " + outputStr);
		return outputStr;
	}
}

