import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class Student_info extends Frame implements ActionListener {
	public static final int  NONE    = 0;
	public static final int  ADD    = 1;
	public static final int  DELETE = 2;
	public static final int  UPDATE = 3;
	public static final int  VIEW  = 4;
	public static final int  TOTAL  = 5;
	
	Object[] title = {"학번","이름","학과(학부)","전화번호"};
	
	TextArea display;
	TextField stu_id, name, dep, tel;
	Label id_label, name_label, dep_label, tel_label;
	Button add, delete, update, view, cancel, total;
	JTable table, list_table = null;
	JScrollPane scroll;
	DefaultTableModel tablemodel;
	
	Connection conn;
	Statement stat;
	ResultSet rs;
	int        cmd;
	
	public Student_info() {
		super("SWE Project#1");
		setLayout(new BorderLayout());
		
		Panel display_panel = new Panel();
		display_panel.setLayout(new GridLayout(1,1));
		display = new TextArea();
		display.setEditable(false);
		display.setPreferredSize(new Dimension(700,40));
		
		Panel stu_info = new Panel();	//모든 패널들을 포함하여 학생의 정보를 나타낼 stu_info를 선언하여 각 패널 add
		stu_info.setLayout(new GridLayout(7,1));
		
		Panel pid = new Panel();	//id_label과 stu_id를 포함할 패널 pid를 선언하여 add		
		Label id_label = new Label();
		id_label.setText("학번");
		id_label.setFont(new Font("돋움", Font.BOLD,12));
		pid.add(id_label);
		pid.add(stu_id = new TextField(10));
		pid.setPreferredSize(new Dimension(200,60));
		
		Panel pname = new Panel();	//name_label과 name을 포함할 패널 pname를 선언하여 add		
		Label name_label = new Label();
		name_label.setText("이름");
		name_label.setFont(new Font("돋움", Font.BOLD,12));
		pname.add(name_label);
		pname.add(name = new TextField(10));
		
		Panel pdep = new Panel();	//dep_label과 dep을 포함할 패널 pdep를 선언하여 add		
		Label dep_label = new Label();
		dep_label.setText("학과");
		dep_label.setFont(new Font("돋움", Font.BOLD,12));
		pdep.add(dep_label);
		pdep.add(dep = new TextField(10));
		
		Panel ptel = new Panel();	//tel_label과 tel을 포함할 패널 ptel를 선언하여 add		
		Label tel_label = new Label();
		tel_label.setText("전화번호");
		tel_label.setFont(new Font("돋움", Font.BOLD,12));
		ptel.add(tel_label);
		ptel.add(tel = new TextField(10));
		
		stu_info.add(pid);
		stu_info.add(pname);
		stu_info.add(pdep);
		stu_info.add(ptel);
		
		tablemodel = new DefaultTableModel(title, 0);
		table = new JTable(tablemodel);
		scroll = new JScrollPane(table);
		scroll.getViewport().setBackground(Color.white);
		//scroll.setPreferredSize(new Dimension(500,580));
		display_panel.add(scroll);
		
		Panel bottom = new Panel();
		bottom.add(total = new Button("전체학생보기"));
		total.addActionListener(this);
		bottom.add(add = new Button("등록"));
		add.addActionListener(this);
		bottom.add(update = new Button("갱신"));
		update.addActionListener(this);
		bottom.add(delete = new Button("삭제"));
		delete.addActionListener(this);
		bottom.add(view= new Button("출력"));
		view.addActionListener(this);
		bottom.add(cancel = new Button("취소"));
		cancel.addActionListener(this);
		bottom.add(display);
		
		addWindowListener(new WindowAdapter() { //상태표시줄의 'X'버튼 누르면 창 닫히게 설정
			public void windowClosing(WindowEvent e) {
				destroy();
				setVisible(false);
				dispose();
				System.exit(0);
			}
		});
		
		add("Center", display_panel);
		add("West", stu_info);
		add("South", bottom);
		
		init();
	}
	
	private void init() {
		try {
			 //JDBC 드라이버를 DriverManager에 등록
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://localhost:3306/sw_db"; //데이터베이스 이름

			//해당 Driver로 부터 Connection 객체 획득
			conn = DriverManager.getConnection(url, "sinwoo", "8105");
			//Connection 객체로 부터 Statement 객체 획득
			stat = conn.createStatement();
			initialize();
			System.out.println("DataBase 연결에 성공했습니다.");
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
			case UPDATE:
				stu_id.setEditable(true);
				tel.setEditable(true);
			case DELETE:
				stu_id.setEditable(true);
				break;
			case VIEW:
				stu_id.setEditable(true);
				break;
		}	
	}
	
	public void setEnable(int n) {
		add.setEnabled(false);
		update.setEnabled(false);
		delete.setEnabled(false);
		view.setEnabled(false);
		total.setEnabled(false);
		switch(n) {
			case ADD:
				add.setEnabled(true);
				setEditable(ADD);
				cmd = ADD;
				break;
			case UPDATE:
				update.setEnabled(true);
				setEditable(UPDATE);
				cmd = UPDATE;
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
				update.setEnabled(true);
				delete.setEnabled(true);
				view.setEnabled(true);
			case TOTAL:
				total.setEnabled(true);
				cmd = TOTAL;
				break;
		}
	}
	
	public void actionPerformed(ActionEvent e) {
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
						display.setText("이미 사용 중인 사용자ID 입니다.");
						setEnable(NONE);
						clear();
						cmd = NONE;
						initialize();
				}
				
				String sql = "insert into stu_info values(?,?,?,?)";	//삽입
				//Statement의 메소드를 이용해서 SQL문의 실행
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
		else if(c==update){
			if(cmd != UPDATE)
				setEnable(UPDATE);
			else{
				String vid = stu_id.getText().trim();
				String vtel = tel.getText().trim();
				if(vid == null || vtel == null || vid.length() == 0 || vtel.length() == 0)
					return;
				idnum = Integer.parseInt(vid);
				
				ResultSet rs2=stat.executeQuery("select ID from stu_info where ID="+idnum);
				if(!rs2.next()) { 	
						display.setText("존재하지 않는 사용자ID 입니다.");
						
						setEnable(NONE);
						clear();
						cmd = NONE;
						initialize();
						return;
				}
				stat.executeUpdate("update stu_info set phone_num='" + vtel + "'where ID='" + vid + "'");
				rs2=stat.executeQuery("select ID from stu_info where ID="+idnum);
				if(!rs2.next()) { 	
					display.setText("정보갱신이 완료되었습니다.");
				}
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
				//데이터베이스에 동일한 사용자ID가 있는지 확인
				ResultSet rs2=stat.executeQuery("select ID from stu_info where ID="+idnum);
				if(!rs2.next()) { 	
						display.setText("존재하지 않는 사용자ID 입니다.");
						
						setEnable(NONE);
						clear();
						cmd = NONE;
						initialize();
						return;
				}
				stat.executeUpdate("delete from stu_info where ID='" + idnum + "'");
				rs2=stat.executeQuery("select ID from stu_info where ID="+idnum);
				if(!rs2.next()) { 	
					display.setText("삭제가 완료되었습니다.");
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
				//데이터베이스에 동일한 사용자ID가 있는지 확인
				ResultSet rs2=stat.executeQuery("select ID from stu_info where ID="+idnum);
				if(!rs2.next()) { 	
						display.setText("존재하지 않는 사용자ID 입니다.");
						
						setEnable(NONE);
						clear();
						cmd = NONE;
						initialize();
						return;
				}
				
				rs = stat.executeQuery("select * from stu_info where ID='"+ idnum + "'");
				
				if(rs.next()) {
					//tablemodel.setNumRows(0);
					tablemodel.setNumRows(0);
					do {
						String stu_id = rs.getString(1);
						String stu_name = rs.getString(2);
						String stu_dep = rs.getString(3);
						String stu_tel = rs.getString(4);
					
						Object rowData[] = {stu_id, stu_name, stu_dep, stu_tel};
						//tablemodel.addRow(rowData);
						tablemodel.addRow(rowData);
					} while(rs.next());
				}
				else{
					display.setText("자료가 없습니다.");
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
		else if(c==total)
		{
			//String sql = "select * from MEMBER order by UserID";
			rs = stat.executeQuery("select * from stu_info order by ID");
			
			if(rs.next())
			{
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
				display.setText("자료가 없습니다.");
				tablemodel.setNumRows(0);
			}
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
