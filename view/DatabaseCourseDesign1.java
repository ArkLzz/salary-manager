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

public class DatabaseCourseDesign1 extends JFrame implements ActionListener {
	// �������
	JLabel jLpersonInfoTable = null;//������Ϣ��
	JLabel jLSelectQueryField = null;//ѡ���ѯ�ֶ�
	JLabel jLEqual = null;//=
	JLabel jLid = null;//Ա����
	JLabel jLname = null;//����
	JLabel jLsex = null;//�Ա�
	JLabel jLpasswd = null;//����
	JLabel jLauthority = null;//Ȩ��
	JLabel jLbirthday = null;//����
	JLabel jLdepartment = null;//���ڲ���
	JLabel jLjob = null;//ְλ
	JLabel jLedu_level = null;//�����̶�
	JLabel jLspeciaty=null;//רҵ����
	JLabel jLaddress=null;//��ַ
	JLabel jLtel=null;//�绰
	JLabel jLemail=null;//��������
	JLabel jLstate=null;//��ǰ״̬
	JLabel jLremake=null;//��ע

	JTextField jTFQueryField = null;//��ѯ�ֶ�
	JTextField jTFid = null;//Ա����
	JTextField jTFname = null;//����
	JTextField jTFauthority = null;//Ȩ��
	JTextField jTFdepartment = null;//���ڲ���
	JTextField jTFstate = null;//��ǰ״̬
	JTextField jTFjob = null;//ְλ
	
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
	String SelectQueryFieldStr = "Ա����";
	
	// ���캯��
	public DatabaseCourseDesign1() {
		// �������
		jLpersonInfoTable = new JLabel("������Ϣ��");            //��ǩ
		jLSelectQueryField = new JLabel("ѡ���ѯ�ֶ�");
		jLEqual = new JLabel(" = ");
		jLid = new JLabel("Ա����");
		jLname = new JLabel("����");
		jLauthority = new JLabel("Ȩ��");
		jLdepartment = new JLabel("����");
		jLstate = new JLabel("��ǰ״̬");
		jLjob = new JLabel("ְλ");
		
		jTFQueryField = new JTextField(10);             //�ı��򳤶�
		jTFid = new JTextField(10);//ѧ��
		jTFname = new JTextField(10);//����
		jTFauthority = new JTextField(10);
		jTFdepartment = new JTextField(10);
		jTFstate = new JTextField(10);
		jTFjob = new JTextField(10);
		
		jBQuery = new JButton("��ѯ");                //��ť
		jBQueryAll = new JButton("��ѯ���м�¼");
		jBInsert = new JButton("����");
		jBUpdate = new JButton("����");
		jBDeleteCurrentRecord = new JButton("ɾ����ǰ��¼");
		jBDeleteAllRecords = new JButton("ɾ�����м�¼");
		// ���ü���
		jBQuery.addActionListener(this);
		jBQueryAll.addActionListener(this);
		jBInsert.addActionListener(this);
		jBUpdate.addActionListener(this);
		jBDeleteCurrentRecord.addActionListener(this);
		jBDeleteAllRecords.addActionListener(this);
		
		jCBSelectQueryField = new JComboBox<String>();//��ѯ�ֶ�                   //ѡ���ѯ
		jCBSelectQueryField.addItem("Ա����");  
		jCBSelectQueryField.addItem("����");  
		jCBSelectQueryField.addItem("Ȩ��");
		jCBSelectQueryField.addItem("����");
		jCBSelectQueryField.addItem("��ǰ״̬");
		jCBSelectQueryField.addItem("ְλ");
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
		titleVector.add("Ա����");
		titleVector.add("����");
		titleVector.add("Ȩ��");
		titleVector.add("����");
		titleVector.add("��ǰ״̬");
		titleVector.add("ְλ");
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

				jTFid.setText((String) v.get(0));// ѧ��
				jTFname.setText((String) v.get(1));// ����
				jTFauthority.setText((String) v.get(2));// �Ա�
				jTFdepartment.setText((String) v.get(3));// ����
				jTFstate.setText((String) v.get(4));// רҵ
				jTFjob.setText((String) v.get(5));// סַ
			}
		});

 //����
		jP1 = new JPanel();    //����
		jP2 = new JPanel();		//��
		jP3 = new JPanel();		
		jP4 = new JPanel();
		jP5 = new JPanel();
		jP6 = new JPanel();
		jPTop = new JPanel();
		jPBottom = new JPanel();
		
		jP1.add(jLpersonInfoTable,BorderLayout.SOUTH);
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
		jP4.add(jLname);
		jP4.add(jTFname);
		jP4.add(jLauthority);
		jP4.add(jTFauthority);
		jP4.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP4.setPreferredSize(new Dimension(20,20));
	
		jP5.add(jLdepartment);
		jP5.add(jTFdepartment);
		jP5.add(jLstate);
		jP5.add(jTFstate);
		jP5.add(jLjob);
		jP5.add(jTFjob);
		jP5.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP5.setPreferredSize(new Dimension(20,20));
		
		jP6.add(jBInsert);
		jP6.add(jBUpdate);
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
				&& !jTFname.getText().isEmpty()
				&& !jTFauthority.getText().isEmpty()
				&& !jTFdepartment.getText().isEmpty()
				&& !jTFstate.getText().isEmpty()
				&& !jTFjob.getText().isEmpty()){
			System.out.println("actionPerformed(). ����");
			insertProcess();
		}else if(e.getActionCommand().equals("����")
				&& !jTFid.getText().isEmpty()
				&& !jTFname.getText().isEmpty()
				&& !jTFdepartment.getText().isEmpty()
				&& !jTFauthority.getText().isEmpty()
				&& !jTFstate.getText().isEmpty()
				&& !jTFjob.getText().isEmpty()){
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
			String sql = "select * from person where ";
			String queryFieldStr = jCBSelectQueryFieldTransfer(SelectQueryFieldStr);
		
			if(queryFieldStr.equals("sAge")){//int sAge.
				sql = sql + queryFieldStr;
				sql = sql + " = " + sQueryField;
			}else{
				sql = sql + queryFieldStr;
				sql = sql + " = ";
				sql = sql + "'" + sQueryField + "';";
			}
			
			System.out.println("queryProcess(). sql = " + sql);
	
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
	
			// ����ѯ��õļ�¼���ݣ�ת�����ʺ�����JTable��������ʽ
			personVector.clear();
			while(rs.next()){
				Vector v = new Vector();
				v.add(rs.getString("id"));
				v.add(rs.getString("name"));
				v.add(rs.getString("authority"));
				v.add(rs.getString("department"));
				v.add(rs.getString("state"));
				v.add(rs.getString("job"));
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
			String sql = "select * from person;";
			System.out.println("queryAllProcess(). sql = " + sql);
	
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);

			// ����ѯ��õļ�¼���ݣ�ת�����ʺ�����JTable��������ʽ
			personVector.clear();
			while(rs.next()){
				Vector v = new Vector();
				v.add(rs.getString("id"));
				v.add(rs.getString("name"));
				v.add(rs.getString("authority"));
				v.add(rs.getString("department"));
				v.add(rs.getString("state"));
				v.add(rs.getString("job"));
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
		String name = jTFname.getText().trim();
		String authority = jTFauthority.getText().trim();
		String department = jTFdepartment.getText().trim();
		String state = jTFstate.getText().trim();
		String job = jTFjob.getText().trim();
		
		// ������������
		String sql = "insert into person(id,name,authority,department,state,job) values('";
		sql = sql + id + "','";
		sql = sql + name + "','";
		sql = sql + authority + "','";
		sql = sql + department + "','";
		sql = sql + state + "','";
		sql = sql + job + "');";

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
		String name = jTFname.getText().trim();
		String authority = jTFauthority.getText().trim();
		String department = jTFdepartment.getText().trim();
		String state = jTFstate.getText().trim();
		String job = jTFjob.getText().trim();
		
		// ������������
		String sql = "update person set name = '";
		sql = sql + name + "', authority = '";
		sql = sql + authority + "', department = ";
		sql = sql + department + ", state = '";
		sql = sql + state + "', job = '";
		sql = sql + job + "'";
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
		String sql = "delete from person where id = '" + id + "';";
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
		String sql = "delete from person;";
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
		
		if(InputStr.equals("Ա����")){
			outputStr = "id";
		}else if(InputStr.equals("����")){
			outputStr = "name";
		}else if(InputStr.equals("Ȩ��")){
			outputStr = "authority";
		}else if(InputStr.equals("����")){
			outputStr = "department";
		}else if(InputStr.equals("��ǰ״̬")){
			outputStr = "state";
		}else if(InputStr.equals("ְλ")){
			outputStr = "job";
		}
		System.out.println("jCBSelectQueryFieldTransfer(). outputStr = " + outputStr);
		return outputStr;
	}
}

