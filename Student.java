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
	
	Object[] title = {"�й�","�̸�","�а�(�к�)","��ȭ��ȣ"};
	
	//TextArea display;
	TextField stu_id, name, dep, tel;
	Label id_label, name_label, dep_label, tel_label;
	Button add, delete, update, view, cancel;
	JTable table = null;
	JScrollPane scroll;
	DefaultTableModel tablemodel;
	
	public Student() //������
	{
		super("SWE Project#1");
		setLayout(new BorderLayout());
		
		Panel display_panel = new Panel();
		display_panel.setLayout(new GridLayout(1,1));
		//display = new TextArea();
		//display.setEditable(false);
		//display.setPreferredSize(new Dimension(700,30));
		
		Panel stu_info = new Panel();	//��� �гε��� �����Ͽ� �л��� ������ ��Ÿ�� stu_info�� �����Ͽ� �� �г� add
		stu_info.setLayout(new GridLayout(7,1));
		
		Panel pid = new Panel();	//id_label�� stu_id�� ������ �г� pid�� �����Ͽ� add		
		Label id_label = new Label();
		id_label.setText("�й�");
		id_label.setFont(new Font("����", Font.BOLD,12));
		pid.add(id_label);
		pid.add(stu_id = new TextField(10));
		//pid.setPreferredSize(new Dimension(200,60));
		
		Panel pname = new Panel();	//name_label�� name�� ������ �г� pname�� �����Ͽ� add		
		Label name_label = new Label();
		name_label.setText("�̸�");
		name_label.setFont(new Font("����", Font.BOLD,12));
		pname.add(name_label);
		pname.add(name = new TextField(10));
		
		Panel pdep = new Panel();	//dep_label�� dep�� ������ �г� pdep�� �����Ͽ� add		
		Label dep_label = new Label();
		dep_label.setText("�а�");
		dep_label.setFont(new Font("����", Font.BOLD,12));
		pdep.add(dep_label);
		pdep.add(dep = new TextField(10));
		
		Panel ptel = new Panel();	//tel_label�� tel�� ������ �г� ptel�� �����Ͽ� add		
		Label tel_label = new Label();
		tel_label.setText("��ȭ��ȣ");
		tel_label.setFont(new Font("����", Font.BOLD,12));
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
		bottom.add(add = new Button("���"));
		bottom.add(update = new Button("����"));
		bottom.add(delete = new Button("����"));
		bottom.add(view= new Button("���"));
		bottom.add(cancel = new Button("���"));
		
		addWindowListener(new WindowAdapter() { //����ǥ������ 'X'��ư ������ â ������ ����
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
	
	private void init() { //My_SQL�� ��Ŭ����  DB�����ϰ� initialize�Լ� ȣ��
		initialize();
	}
	
	public void initialize() { //�⺻������ ����ڰ� ���Ƿ� �ۼ����� ���ϰ� �ʱ�ȭ
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
