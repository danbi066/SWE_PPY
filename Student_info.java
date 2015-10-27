import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.sun.xml.internal.ws.api.Component;


public class Student_info extends Frame implements ActionListener {
	public static final int  NONE    = 0;
	public static final int  ADD    = 1;
	public static final int  DELETE = 2;
	public static final int  UPDATE = 3;
	public static final int  VIEW  = 4;
	
	Object[] title = {"�й�","�̸�","�а�(�к�)","��ȭ��ȣ"};
	
	//TextArea display;
	TextField stu_id, name, dep, tel;
	Label id_label, name_label, dep_label, tel_label;
	Button add, delete, update, view, cancel;
	JTable table = null;
	JScrollPane scroll;
	DefaultTableModel tablemodel;
	
	Connection conn;
	Statement stat;
	int        cmd;
	
	public Student_info() {
		super("SWE Project#1");
		//���̾ƿ� �κ�
	}
	
	private void init() {
		try {
			 //JDBC ����̹��� DriverManager�� ���
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			String url = "jdbc:mysql://localhost:3306/sw_db"; //�����ͺ��̽� �̸�

			//�ش� Driver�� ���� Connection ��ü ȹ��
			conn = DriverManager.getConnection(url, "root", "z8403521");
			//Connection ��ü�� ���� Statement ��ü ȹ��
			stat = conn.createStatement();
			initialize();
			System.out.println("DataBase ���ῡ �����߽��ϴ�.");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
	
	public void initialize() {
		stu_id.setEditable(false);
		name.setEditable(false);
		dep.setEditable(false);
		tel.setEditable(false);
	}
	
	public void clear() {
		stu_id.setText("");
		name.setText("");
		dep.setText("");
		tel.setText("");	
	}
	
	public void cancle()
	{
		stu_id.setText(" ");
		name.setText(" ");
		dep.setText(" ");
		tel.setText(" ");	
	}
	
	private void destroy() {
		try {
			if(stat != null) {
				stat.close();
			}
			if(conn != null) {
				conn.close();
			}
		} catch(Exception ex) { }
	}
	
	public void setEditable(int n) {
		switch(n) {
			case ADD:
				stu_id.setEditable(true);
				name.setEditable(true);
				dep.setEditable(true);
				tel.setEditable(true);
				break;
			case DELETE:
				stu_id.setEditable(true);
				break;
			case VIEW:
				stu_id.setEditable(true);
				break;
			case NONE:
		}	
	}
	
	public void setEnable(int n) {
		add.setEnabled(false);
		delete.setEnabled(false);
		view.setEnabled(false);
		switch(n) {
			case ADD:
				add.setEnabled(true);
				setEditable(ADD);
				cmd = ADD;
				break;
			case DELETE:
				delete.setEnabled(true);
				setEditable(DELETE);
				cmd = DELETE;
				break;
			case VIEW:
				view.setEnabled(true);
				setEditable(VIEW);
				cmd = VIEW;
				break;
			case NONE:
				add.setEnabled(true);
				delete.setEnabled(true);
				view.setEnabled(true);
		}
	}

	public void actionPerformed(ActionEvent e) {
		ResultSet rs = null;
		int idnum = 0;
		Component c = (Component) e.getSource();
	try{
		if(c==add)
		{
			if(cmd != ADD)
			{
				setEnable(ADD);
			}
			else
			{
				String vid = stu_id.getText().trim();
				String vname = name.getText().trim();
				String vdep = dep.getText().trim();
				String vtel = tel.getText().trim();
				if(vid == null || vname == null || vdep == null || vtel == null || 
					vid.length() == 0 || vname.length() == 0 || vdep.length() == 0 || vtel.length() == 0 )
					return ;
				idnum = Integer.parseInt(vid);
				
				ResultSet rs2=stat.executeQuery("select ID from stu_info where ID="+idnum);
				if(rs2.next()) { 	
						display.setText("�̹� ��� ���� �����ID �Դϴ�.");
						setEnable(NONE);
						clear();
						cmd = NONE;
						initialize();
				}
				
				String sql = "insert into MEMBER values(?,?,?,?,?,?)";
				//Statement�� �޼ҵ带 �̿��ؼ� SQL���� ����
				PreparedStatement stat=conn.prepareStatement(sql);
				stat.setInt(1, idnum);
				stat.setString(2, vname);
				stat.setString(3, vdep);
				stat.setString(4, vtel);
				stat.executeUpdate();
				setEnable(NONE);
				clear();
				cmd = NONE;
				initialize();
			}
		}
		else if(c==delete)
		{
			if(cmd != DELETE)
				setEnable(DELETE);
			else {
				String vid = stu_id.getText().trim();
				if(vid == null || vid.length() == 0)
					return ;
				idnum = Integer.parseInt(vid);
				//�����ͺ��̽��� ������ �����ID�� �ִ��� Ȯ��
				ResultSet rs2=stat.executeQuery("select ID from stu_info where ID="+idnum);
				if(!rs2.next()) { 	
						display.setText("�������� �ʴ� �����ID �Դϴ�.");
						
						setEnable(NONE);
						clear();
						cmd = NONE;
						initialize();
						return;
				}
				stat.executeUpdate("delete from stu_info where ID='" + idnum + "'");
				rs2=stat.executeQuery("select ID from stu_info where ID="+idnum);
				if(!rs2.next()) { 	
					display.setText("������ �Ϸ�Ǿ����ϴ�.");
				}
				setEnable(NONE);
				clear();
				cmd = NONE;
				initialize();
			}
		}
		else if(c==view)
		{
			if(cmd != VIEW)
				setEnable(VIEW);
			else{
				String vid = stu_id.getText().trim();
				if(vid == null || vid.length() == 0)
					return ;
				idnum = Integer.parseInt(vid);
				//�����ͺ��̽��� ������ �����ID�� �ִ��� Ȯ��
				ResultSet rs2=stat.executeQuery("select ID from stu_info where ID="+idnum);
				if(!rs2.next()) { 	
						display.setText("�������� �ʴ� �����ID �Դϴ�.");
						
						setEnable(NONE);
						clear();
						cmd = NONE;
						initialize();
						return;
				}
				
				rs = stat.executeQuery("select * from stu_info where ID='"+ idnum + "'");
				
				if(rs.next()) {
					tablemodel.setNumRows(0);
					do {
						String stu_id = rs.getString(1);
						String stu_name = rs.getString(2);
						String stu_dep = rs.getString(3);
						String stu_tel = rs.getString(4);
					
						Object rowData[] = {stu_id, stu_name, stu_dep, stu_tel};
						tablemodel.addRow(rowData);					
					} while(rs.next());
				}
				else{
					display.setText("�ڷᰡ �����ϴ�.");
					tablemodel.setNumRows(0);
				}
				setEnable(NONE);
				clear();
				cmd = NONE;
				initialize();
			}
		}
		
		else if(c==cancel)
		{
			setEnable(NONE);
			cancle();
			initialize();
			cmd = NONE;
		}
		
	} catch(Exception ex) { }
	
	return;
	}
	
	
	public static void main(String[] args) {
		Student_info screen = new Student_info();
		screen.setSize(700, 500);
		screen.setVisible(true);
		screen.setResizable(false);

	}
}