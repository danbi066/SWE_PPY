import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Student extends Frame implements ActionListener{

	public static final int  NONE   = 0;
	public static final int  ADD    = 1;
	public static final int  DELETE = 2;
	public static final int  UPDATE = 3;
	public static final int  TOTAL  = 4;
	
	Object[] title = {"학번","이름","학과(학부)","전화번호"};
	
	//TextArea display;
	TextField stu_id, name, dep, tel;
	Label id_label, name_label, dep_label, tel_label;
	Button add, delete, update, view, cancel;
	JTable table = null;
	JScrollPane scroll;
	DefaultTableModel tablemodel;
	
	public Student() //생성자
	{
		super("SWE Project#1");
		setLayout(new BorderLayout());
		
		Panel display_panel = new Panel();
		display_panel.setLayout(new GridLayout(1,1));
		//display = new TextArea();
		//display.setEditable(false);
		//display.setPreferredSize(new Dimension(700,30));
		
		Panel stu_info = new Panel();	//모든 패널들을 포함하여 학생의 정보를 나타낼 stu_info를 선언하여 각 패널 add
		stu_info.setLayout(new GridLayout(7,1));
		
		Panel pid = new Panel();	//id_label과 stu_id를 포함할 패널 pid를 선언하여 add		
		Label id_label = new Label();
		id_label.setText("학번");
		id_label.setFont(new Font("돋움", Font.BOLD,12));
		pid.add(id_label);
		pid.add(stu_id = new TextField(10));
		//pid.setPreferredSize(new Dimension(200,60));
		
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
		bottom.add(add = new Button("등록"));
		bottom.add(update = new Button("갱신"));
		bottom.add(delete = new Button("삭제"));
		bottom.add(view= new Button("출력"));
		bottom.add(cancel = new Button("취소"));
		
		addWindowListener(new WindowAdapter() { //상태표시줄의 'X'버튼 누르면 창 닫히게 설정
			public void windowClosing(WindowEvent e) {
				//destroy();
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
	
	private void init() { //My_SQL과 이클립스  DB연동하고 initialize함수 호출
		initialize();
	}
	
	public void initialize() { //기본적으로 사용자가 임의로 작성하지 못하게 초기화
		stu_id.setEditable(false);
		name.setEditable(false);
		dep.setEditable(false);
		tel.setEditable(false);
	}
	
	public static void main(String[] args) {
		Student screen = new Student();
		screen.setSize(700, 500);
		screen.setVisible(true);
		screen.setResizable(false);
	}
	
	public void actionPerformed(ActionEvent e) {
	}
}
