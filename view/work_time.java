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

public class work_time extends JFrame implements ActionListener {
	// �������
	JLabel jLsalaryInfoTable = null;//������Ϣ��
	JLabel jLSelectQueryField = null;//ѡ���ѯ�ֶ�
	JLabel jLEqual = null;//=
	JLabel jLid = null; //��¼���
	JLabel jLperson = null;//Ա����
	JLabel jLyearmonth = null;//����
	JLabel jLwork_hour = null;//����ʱ
	JLabel jLover_hour = null;//�Ӱ�ʱ
	JLabel jLleave_day = null;//�����
	JLabel jLerrand_day = null;//������

	JTextField jTFQueryField = null;
	JTextField jTFid = null;
	JTextField jTFperson = null;
	JTextField jTFyearmonth = null;
	JTextField jTFwork_hour = null;
	JTextField jTFover_hour = null;
	JTextField jTFleave_day = null;
	JTextField jTFerrand_day = null;
	
	JButton jBQuery = null;//��ѯ
	JButton jBQueryAll = null;//��ѯ���м�¼
	JButton jBInsert = null;//����
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
	public work_time() {
		// �������
		jLsalaryInfoTable = new JLabel("������ϸ��");            //��ǩ
		jLSelectQueryField = new JLabel("ѡ���ѯ�ֶ�");
		jLEqual = new JLabel(" = ");
		jLid = new JLabel("��¼���");
		jLperson = new JLabel("Ա����");
		jLyearmonth = new JLabel("����");
		jLwork_hour = new JLabel("����ʱ��");
		jLover_hour = new JLabel("�Ӱ�ʱ��");
		jLleave_day = new JLabel("�������");
		jLerrand_day = new JLabel("��������");
		
		jTFQueryField = new JTextField(10);             //�ı��򳤶�
		jTFid = new JTextField(10);
		jTFperson = new JTextField(10);
		jTFyearmonth = new JTextField(10);
		jTFwork_hour = new JTextField(10);
		jTFover_hour = new JTextField(10);
		jTFleave_day = new JTextField(10);
		jTFerrand_day = new JTextField(10);
		
		jBQuery = new JButton("��ѯ");                //��ť
		jBQueryAll = new JButton("��ѯ���м�¼");
		jBInsert = new JButton("����");
		jBDeleteCurrentRecord = new JButton("ɾ����ǰ��¼");
		jBDeleteAllRecords = new JButton("ɾ�����м�¼");
		// ���ü���
		jBQuery.addActionListener(this);
		jBQueryAll.addActionListener(this);
		jBInsert.addActionListener(this);
		jBDeleteCurrentRecord.addActionListener(this);
		jBDeleteAllRecords.addActionListener(this);
		
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
		titleVector.add("����ʱ��");
		titleVector.add("�Ӱ�ʱ��");
		titleVector.add("�������");
		titleVector.add("��������");
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
				jTFwork_hour.setText(Integer.toString((int) v.get(3)));
				jTFover_hour.setText(Integer.toString((int) v.get(4)));
				jTFleave_day.setText(Integer.toString((int) v.get(5)));
				jTFerrand_day.setText(Integer.toString((int) v.get(6)));
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
		jP4.add(jLwork_hour);
		jP4.add(jTFwork_hour);
		jP4.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP4.setPreferredSize(new Dimension(20,20));
	
		jP5.add(jLover_hour);
		jP5.add(jTFover_hour);
		jP5.add(jLleave_day);
		jP5.add(jTFleave_day);
		jP5.add(jLerrand_day);
		jP5.add(jTFerrand_day);
		jP5.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP5.setPreferredSize(new Dimension(20,20));
		
		jP6.add(jBInsert);
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
				&& !jTFperson.getText().isEmpty()
				&& !jTFyearmonth.getText().isEmpty()
				&& !jTFwork_hour.getText().isEmpty()
				&& !jTFleave_day.getText().isEmpty()
				&& !jTFover_hour.getText().isEmpty()
				&& !jTFerrand_day.getText().isEmpty()){
			System.out.println("actionPerformed(). ����");
			insertProcess();
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
			String sql = "select * from worktime where ";
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
				v.add(Integer.valueOf(rs.getInt("work_hour")));
				v.add(Integer.valueOf(rs.getInt("over_hour")));
				v.add(Integer.valueOf(rs.getInt("leave_day")));
				v.add(Integer.valueOf(rs.getInt("errand_day")));
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
			String sql = "select * from worktime;";
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
				v.add(Integer.valueOf(rs.getInt("work_hour")));
				v.add(Integer.valueOf(rs.getInt("over_hour")));
				v.add(Integer.valueOf(rs.getInt("leave_day")));
				v.add(Integer.valueOf(rs.getInt("errand_day")));
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
		int salary=0;
		int basic=0;
		int add=0;
		int sub=0;
		int total=0;
		int bonus=0;
		String id = jTFid.getText().trim();
		String person = jTFperson.getText().trim();
		String yearmonth = jTFyearmonth.getText().trim();
		String work_hour = jTFwork_hour.getText().trim();
		String over_hour = jTFover_hour.getText().trim();
		String leave_day = jTFleave_day.getText().trim();
		String errand_day = jTFerrand_day.getText().trim();
		String sql = "insert into worktime(id,person,yearmonth,work_hour,over_hour,leave_day,errand_day) values('";
		sql = sql + id + "','";
		sql = sql + person + "','";
		sql = sql + yearmonth + "',";
		sql = sql + work_hour + ",";
		sql = sql + over_hour + ",";
		sql = sql + leave_day + ",";
		sql = sql + errand_day + ");";
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
		
		try{
			// ������ѯ����
			sql = "select * from salary_set where person ='"+person+"';";
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
			while(rs.next()){
				salary=rs.getInt("salary");
			}
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
		
		String type=null;
		String name=null;
		int money=0;
		if(Integer.parseInt(over_hour)!=0)         //����salary_other�н�����¼
		{
			type="0";
			id=yearmonth+person+type;
			name="�Ӱร��";
			money=salary*Integer.parseInt(over_hour);
			bonus=money;
			sql="insert into salary_other values('"+id+"','"+yearmonth+"','"
			+person+"','"+type+"','"+name+"',"+String.valueOf(money)+","+String.valueOf(salary)+");";
		//	System.out.println(Integer.parseInt(over_hour)+","+salary);
			try{
				if (dbProcess.executeUpdate(sql) < 1) {
					System.out.println("insertProcess(). insert database failed.");
				}
			}catch(Exception e){
				System.out.println("e = " + e);
				JOptionPane.showMessageDialog(null,
					"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
			}
		}
		if(Integer.parseInt(errand_day)!=0)             //����salary_other�н�����¼
		{
			type="1";
			id=yearmonth+person+type;
			name="�������";
			money=salary*Integer.parseInt(errand_day)*10;
			add=money;
			sql="insert into salary_other values('"+id+"','"+yearmonth+"','"
			+person+"','"+type+"','"+name+"',"+String.valueOf(money)+","+String.valueOf(salary)+");";
			try{
				if (dbProcess.executeUpdate(sql) < 1) {
					System.out.println("insertProcess(). insert database failed.");
				}
			}catch(Exception e){
				System.out.println("e = " + e);
				JOptionPane.showMessageDialog(null,
					"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
			}
		}
		if(Integer.parseInt(leave_day)!=0)               //����salary_other�п۷���¼
		{
			type="2";
			id=yearmonth+person+type;
			name="ȱ�ڿ۷�";
			money=salary*Integer.parseInt(leave_day)*10;
			sub=money;
			sql="insert into salary_other values('"+id+"','"+yearmonth+"','"
			+person+"','"+type+"','"+name+"',"+String.valueOf(money)+","+String.valueOf(salary)+");";
			try{
				if (dbProcess.executeUpdate(sql) < 1) {
					System.out.println("insertProcess(). insert database failed.");
				}
			}catch(Exception e){
				System.out.println("e = " + e);
				JOptionPane.showMessageDialog(null,
					"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
			}
		}

		id=yearmonth+person;                                 //����salary���м�¼
		money=salary*Integer.parseInt(work_hour);
		basic=money;
		total=basic-sub+add+bonus;
		sql="insert into salary values('"+id+"','"+yearmonth+"','"
		+person+"',"+String.valueOf(basic)+","+String.valueOf(bonus)+","+String.valueOf(add)
		+","+String.valueOf(sub)+","+String.valueOf(total)+");";
		try{
			if (dbProcess.executeUpdate(sql) < 1) {
				System.out.println("insertProcess(). insert database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}
	}


	public void deleteCurrentRecordProcess()
	{
		String idx=null;
		String id = jTFid.getText().trim();
		String person = jTFperson.getText().trim();
		String yearmonth = jTFyearmonth.getText().trim();
		String work_hour = jTFwork_hour.getText().trim();
		String over_hour = jTFover_hour.getText().trim();
		String leave_day = jTFleave_day.getText().trim();
		String errand_day = jTFerrand_day.getText().trim();
		String sql = null;
		if(Integer.parseInt(over_hour)!=0)     //ɾ��salary_other�н�����¼
		{
			idx=yearmonth+person+"0";
			sql="delete from salary_other where id ='"+idx+"';";
			try{
				if (dbProcess.executeUpdate(sql) < 1) {
					System.out.println("deleteCurrentRecordProcess(). delete database failed.");
				}
			}catch(Exception e){
				System.out.println("e = " + e);
				JOptionPane.showMessageDialog(null,
					"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
			}
		} 
		if(Integer.parseInt(errand_day)!=0)   //ɾ��salary_other�н�����¼
		{
			idx=yearmonth+person+"1";
			sql="delete from salary_other where id ='"+idx+"';";
			try{
				if (dbProcess.executeUpdate(sql) < 1) {
					System.out.println("deleteCurrentRecordProcess(). delete database failed.");
				}
			}catch(Exception e){
				System.out.println("e = " + e);
				JOptionPane.showMessageDialog(null,
					"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
			}
		}
		if(Integer.parseInt(leave_day)!=0)      //ɾ��salary_other�п۷���¼
		{
			idx=yearmonth+person+"2";
			sql="delete from salary_other where id ='"+idx+"';";
			try{
				if (dbProcess.executeUpdate(sql) < 1) {
					System.out.println("deleteCurrentRecordProcess(). delete database failed.");
				}
			}catch(Exception e){
				System.out.println("e = " + e);
				JOptionPane.showMessageDialog(null,
					"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		idx = yearmonth + person ;         //ɾ��salary����Ӧ��¼
		sql = "delete from salary where id = '"+idx+"';";
		try{
			if (dbProcess.executeUpdate(sql) < 1) {
				System.out.println("deleteCurrentRecordProcess(). delete database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}
		
		// ����ɾ������
		sql = "delete from worktime where id = '" + id + "';";
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

	public void deleteAllRecordsProcess()
	{
		// ����ɾ������
		String sql = "delete from worktime;";
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
		sql = "delete from salary;";
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
		sql = "delete from salary_other;";
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
		}
		System.out.println("jCBSelectQueryFieldTransfer(). outputStr = " + outputStr);
		return outputStr;
	}
}

