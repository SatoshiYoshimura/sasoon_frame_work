/**
 *
 */
package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;

import calam.BaseBeans;
import calam.EmployeeColums;
import calam.JobsColumns;
import calam.StudentColums;
import calam.Students;
import calam.TableList;
import calam.error.IdNullException;
import controller.AppricationController;
import controller.LoadProjectProp;
import controller.ProjectProp;
import db.base.CreateSQL;
import db.base.DBProp;
import db.base.LoadProperty;
import edit.string.StringManager;

@SuppressWarnings("unused")
public class Test<T> {

	/**
	 *
	 */
	public Test() {
	}

	int i;
	/**
	 * @param args
	 * @throws SQLException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws ChoseGetResultSecException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws SQLException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
//		(new Test<Students>()).test();
		(new Test<Students>()).test2();
	}

	private void test() throws SQLException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		// テスト用メイン
		List<String> al = new ArrayList<String>();
		al.add("a");
		//SQLGenerater
		CreateSQL cSQL = new CreateSQL();
		Connection connection = null;
		InitialContext ic;
		try {
			ic = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource)ic.lookup("java:comp/env/jdbc/kadai08");

			try {
				connection = ds.getConnection();
				System.out.println("せつぞくうううう");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (NamingException e) {

			e.printStackTrace();
		}

		String sql = cSQL.Select(EmployeeColums.FIRST_NAME,EmployeeColums.SALARY,EmployeeColums.JOB_ID).From(TableList.STUDENTS).InnerJoin(TableList.STUDENTS).On(EmployeeColums.SALARY, "=",JobsColumns.MIN_SALARY).Where(StudentColums.CLASS_NAME, "=", "qq").GenerateAlltoString();

		System.out.println(sql);
		sql ="";
		CreateSQL cSql2 = new CreateSQL();
		//sql = cSQL.InsertInto(TableList.STUDENTS, StudentColums.CLASS_NAME).Values("a","a").GenerateAlltoString();
		System.out.println(sql);
//		new Throwable().getStackTrace()[0].getMethodName();




		//List<BaseBeans> StudentsList = new ArrayList<BaseBeans>();
//		List<String> stclumNameList = st.getBeanFields(st);
//		stclumNameList = st.pullBeanFieldsString(st);


//		StudentsList = st.findAll();
//		for(BaseBeans stu: StudentsList){
////			stu.update(stu);
//			//System.out.println(((Students) stu).getClassName());
//		}

	}

	/**
	 * ここで随時必要なテストを行う
	 */
	private void test2() {
//		//ProjectPropertiesテスト
		this.projectPropertiesTest();
		this.deleteProjectURL();
		this.getControllerName();
		this.newInstanceController();
		this.getActionName();
		this.getBeforeString();
		this.getAfterString();
		this.splitString();
		this.queryToHash();
//		this.getParentClass();
		this.testNullorPlane();
//		this.testUpdate();
	//	this.testFreeUpdate();
//		this.testFreeInsert();
		this.testUpdateBean();
		this.testDelete();
		this.testInsert();
		this.testExecuteSelect();
		this.testFind();
	}

	//インサートテスト
	private void testInsert(){
		Students st = new  Students();
		st.setClassName("IH13B");
		st.setName("やまｄふぁ");
		st.setStNum("30");
		st.insert();
	}

	//デリートテスト
	private void testDelete(){
		Students st = new Students();
		st.setId(9);
		try {
			st.delete();
		} catch (IdNullException e) {
			e.printStackTrace();
		}
	}

	private void testUpdateBean(){

		Students st = new Students();
		st.setId(4);
		st.setName("おでろん");
		try {
			st.update();
		} catch (IdNullException e) {
			e.printStackTrace();
		}
	}

	//update文テスト
	private void testUpdate(){
		CreateSQL cs = new CreateSQL();
		List<String> strL = new ArrayList<String>();
		strL.add("name");
		strL.add("class_name");
		ArrayList<Object> nameL = new ArrayList<Object>();
		nameL.add("kasu");
		nameL.add(null);
		nameL.add(null);
		nameL.add("unkokko");
		String sql = cs.Update("students", strL).Set(nameL).Where(StudentColums.ID, "=", 2).GenerateAlltoString();
		System.out.println(sql);
	}

	//簡易findtest
	private  void testFind(){
		Students st = new Students();
		st = (Students) st.find(4);
		System.out.println(st.getName());
	}

	class Kas{
		Kas(int i){
		 unko = i;
		}

		public int unko = 2;

		public Field[] getDeclaredFields() {
			return null;
		}
	}

	private void testExecuteDelete(){

	}

	/**
	 * 自由なインサート文テスト
	 */
	private void testFreeInsert(){
		Students t = new Students();
		t.freeInsert("insert into students (class_name,st_num,name) values('IH13B',13,'kitada')");
	}

	/**
	 *自由なアップデート文テスト
	 */
	private void testFreeUpdate(){
		Students st = new Students();
		st.freeUpdate("update students set name = 'unko' where id = 4");
	}

	/**
	 * 自由なデリート文テスト
	 */
	private void testFreeDelete(){
		Students st = new Students();
		st.freeDelete("delete from students where id = 6");
	}

	/***
	 * 自由なSelect文テスト
	 */
	private void  testExecuteSelect(){
		Students st = new Students();
		List<BaseBeans> stList = st.freeSelect("select * from students");
		for(BaseBeans stu: stList){
			Students stude = (Students)stu;
			System.out.println("executeSelectTest:id:"+stude.getId());
			System.out.println("executeSelectTest:name:"+stude.getName());
		}
	}

	/**
	 *
	 */
	private void testNullorPlane(){
		StringManager sm = new StringManager();
		System.out.println("falseが出る:"+sm.allRightNullorPlane(null));
		System.out.println("falseが出る:"+sm.allRightNullorPlane(""));
		System.out.println("trueが出る:"+sm.allRightNullorPlane("dadsadasd"));
	}

	/**
	 * 呼び出し元クラス取得
	 *  呼び出し元のクラスのフィールドの値取得
	 */
	private void getParentClass(){
		Class clazz;
		StackTraceElement[] traces = new Throwable().getStackTrace();
		System.out.println("Testのtest2がでてればOK");
		for (StackTraceElement trace : traces) {
		  System.out.format("class name : %s, method name : %s\n",
		      trace.getClassName(), trace.getMethodName());
		  	Kas un = new Kas(5);
		  	clazz =  un.getClass();
			 System.out.println("classは:"+clazz);
			Field f = null;
			Field[] fields = clazz.getFields();
			for(Field fe: fields ){
				System.out.println("フィールドはこれら:"+fe);
			}

			try {
				System.out.println("クラスは:"+clazz);
				f = clazz.getField("unko");
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
			//フィールド名を指定
			int i = 0;
			try {
				int n = f.getInt(un);
//				int n = (Integer)f.get(i);
				System.out.println("取れた値は:"+n);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}	//JDK1.5（自動ボクシングを使用）
		}


	}

	/***
	 * ハッシュマップの中身見るよう
	 * @author OWNER
	 *
	 */
	class MapGetDemo {
		public void get(Map map) {
			// map.keySet()からIteratorを取得
			Iterator it = map.keySet().iterator();
			Object obj;
			while (it.hasNext()) {	// 次の要素があるならブロック内を実行
				obj = it.next();	// 次の要素を取り出す
				System.out.println("	\t" + obj + ": " + map.get(obj));
			}
		}
	}

	/**
	 * "id=1&name=sa&unko=ds"この状態の文字列を
	 * id:1,name:sa,unko:ds
	 * にする
	 */
	private void testSplitArray(){
		StringManager sm = new StringManager();
		String str = "id=1&name=sa&unko=ds";
		String indexStr = "&";
		String[] strAry = sm.queryToArray(str, indexStr);

		ArrayList<String[]> strList = sm.splitArray(strAry,"=");
		for(String[] s :strList){
			for(String st :s){
				System.out.println("=分割後の中身は:"+st);
			}
		}
	}

	/**
	 * クエリストリングの文字列をハッシュマップにして返す
	 */
	private void queryToHash(){

		String str = "id=1&name=sa&unko=ds";
		String indexStr = "&";
		StringManager sm = new StringManager();
		String[] strAry = sm.queryToArray(str, indexStr);
		ArrayList<HashMap> queryList = new ArrayList<HashMap>();
		queryList = sm.queryToHashList(str);

		MapGetDemo getDemo = new MapGetDemo();
		for (HashMap h: queryList){
			System.out.println("撮れてるかな:");
			getDemo.get(h);
		}

	}

	//ちゃんと単体テスト書いてくよ
	/**
	 *指定した文字で分割する
	 */
	private void splitString(){
		String str = "id=1&name=sa&unko=ds";
		String indexStr = "&";
		StringManager sm = new StringManager();
		String[] strAry = sm.queryToArray(str, indexStr);
		 //分割された文字列の表示
	    for (int i=0; i<strAry.length; i++) {
	      System.out.println("クエリ:"+strAry[i]);
	    }
	}

	/**
	 *指定した文字以前の文字列を返す
	 *
	 */
	private void getBeforeString(){
		StringManager sm = new StringManager();
		char ch = '&';
		String str = "id=1&name=sa";
		System.out.println("&以前:"+sm.getBeforeIndex(str, ch));
		ch = '=';
		System.out.println("=以前:"+sm.getBeforeIndex(str, ch));
	}

	/**
	 * 指定した文字以前の文字列を返す
	 */
	private void getAfterString(){
		StringManager sm = new StringManager();
		String str = "id=1&name=sa&unko=ew";
		char ch = '&';
		String ok = sm.getAfterindex(str, ch);
		System.out.println("いこう文字列:"+ok);
	}

	/**
	 *コントローラ名からコントローラインスタンス化
	 *
	 */
	private void newInstanceController() {
		ServletContext serv = new ServletContext() {

			@Override
			public void setSessionTrackingModes(Set<SessionTrackingMode> arg0) throws IllegalStateException,
					IllegalArgumentException {

			}

			@Override
			public boolean setInitParameter(String arg0, String arg1) {
				return false;
			}

			@Override
			public void setAttribute(String arg0, Object arg1) {

			}

			@Override
			public void removeAttribute(String arg0) {

			}

			@Override
			public void log(String arg0, Throwable arg1) {

			}

			@Override
			public void log(Exception arg0, String arg1) {

			}

			@Override
			public void log(String arg0) {


			}

			@Override
			public SessionCookieConfig getSessionCookieConfig() {

				return null;
			}

			@Override
			public Enumeration<Servlet> getServlets() {
				return null;
			}

			@Override
			public Map<String, ? extends ServletRegistration> getServletRegistrations() {

				return null;
			}

			@Override
			public ServletRegistration getServletRegistration(String arg0) {
				return null;
			}

			@Override
			public Enumeration<String> getServletNames() {
				return null;
			}

			@Override
			public String getServletContextName() {
				return null;
			}

			@Override
			public Servlet getServlet(String arg0) throws ServletException {
				return null;
			}

			@Override
			public String getServerInfo() {
				return null;
			}

			@Override
			public Set<String> getResourcePaths(String arg0) {
				return null;
			}

			@Override
			public InputStream getResourceAsStream(String arg0) {
				return null;
			}

			@Override
			public URL getResource(String arg0) throws MalformedURLException {
				return null;
			}

			@Override
			public RequestDispatcher getRequestDispatcher(String arg0) {
				return null;
			}

			@Override
			public String getRealPath(String arg0) {
				return null;
			}

			@Override
			public RequestDispatcher getNamedDispatcher(String arg0) {
				return null;
			}

			@Override
			public int getMinorVersion() {
				return 0;
			}

			@Override
			public String getMimeType(String arg0) {
				return null;
			}

			@Override
			public int getMajorVersion() {
				return 0;
			}

			@Override
			public JspConfigDescriptor getJspConfigDescriptor() {
				return null;
			}

			@Override
			public Enumeration<String> getInitParameterNames() {
				return null;
			}

			@Override
			public String getInitParameter(String arg0) {
				return null;
			}

			@Override
			public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
				return null;
			}

			@Override
			public FilterRegistration getFilterRegistration(String arg0) {
				return null;
			}

			@Override
			public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
				return null;
			}

			@Override
			public int getEffectiveMinorVersion() {
				return 0;
			}

			@Override
			public int getEffectiveMajorVersion() {
				return 0;
			}

			@Override
			public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
				return null;
			}

			@Override
			public String getContextPath() {
				return null;
			}

			@Override
			public ServletContext getContext(String arg0) {
				return null;
			}

			@Override
			public ClassLoader getClassLoader() {
				return null;
			}

			@Override
			public Enumeration<String> getAttributeNames() {
				return null;
			}

			@Override
			public Object getAttribute(String arg0) {
				return null;
			}

			@Override
			public void declareRoles(String... arg0) {

			}

			@Override
			public <T extends Servlet> T createServlet(Class<T> arg0) throws ServletException {
				return null;
			}

			@Override
			public <T extends EventListener> T createListener(Class<T> arg0) throws ServletException {
				return null;
			}

			@Override
			public <T extends Filter> T createFilter(Class<T> arg0) throws ServletException {
				return null;
			}

			@Override
			public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0, Class<? extends Servlet> arg1) {
				return null;
			}

			@Override
			public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0, Servlet arg1) {
				return null;
			}

			@Override
			public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0, String arg1) {
				return null;
			}

			@Override
			public <T extends EventListener> void addListener(T arg0) {

			}

			@Override
			public void addListener(String arg0) {

			}

			@Override
			public void addListener(Class<? extends EventListener> arg0) {

			}

			@Override
			public Dynamic addFilter(String arg0, Class<? extends Filter> arg1) {
				return null;
			}

			@Override
			public Dynamic addFilter(String arg0, Filter arg1) {
				return null;
			}

			@Override
			public Dynamic addFilter(String arg0, String arg1) {
				return null;
			}
		};
		String str = "Students";
		AppricationController apc = null;
		str += "Controller";
		try {
			Class<?> Ap = Class.forName("controller."+str);
			System.out.println("クラス型取得:"+Ap);
			try {
				Class<?>[] types = { ServletContext.class };
				Constructor<?> constructor = null;
				try {
					constructor = Ap.getConstructor(types);
					System.out.println("コンストラクター取得:"+constructor);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				Object[] args = { serv };
				try {
					apc = (AppricationController) constructor.newInstance(args);
					System.out.println("インスタンス完了:"+apc);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/***
	 *アクション名取得
	 */
	private void getActionName(){
		StringManager Sm = new StringManager();
		String str = Sm.getActionName("/students/index");
		System.out.println("アクション名は:"+str);
	}

	/***
	 *コントローラ名取得
	 */
	private void getControllerName(){
		StringManager Sm = new StringManager();
		String str = Sm.getControllerName("/students/index");
		System.out.println("コントローラー名は:"+str);
	}

	/***
	 * URLの最初からプロパティ名の部分を消す
	 */
	private void deleteProjectURL(){
		StringManager Sm = new StringManager();
		String url = Sm.deleteProperty("/c/students/index", "c");
		System.out.println("削除後の文字列:" + url);
	}

	/***
	 * プロジェクトプロパティを読み込めるかテスト
	 * @param string_date
	 * @throws ParseException
	 */
	private void projectPropertiesTest(){
		LoadProjectProp LPP = new LoadProjectProp();
		java.io.File file = new java.io.File(".");
		String proppass = file.getAbsolutePath()+"./kadai08_b_31/WebContent/WEB-INF/Properties/Project.properties";
		LPP.load(proppass);
	 	ProjectProp PP = LPP.giveProjectProp();
	 	System.out.println("プロジェクト名:"+PP.getName());
	 	System.out.println("サーブレットパス:"+PP.getServletPath());
	}

	/**
	 * DBプロパティテスト
	 * @param string_date
	 * @throws ParseException
	 */
	private void dbPropTest(){
		LoadProperty LP = new LoadProperty();
		java.io.File file = new java.io.File(".");
		String proppass = file.getAbsolutePath()+"./kadai08_b_31/WebContent/WEB-INF/Properties/DB.properties";
		LP.load(proppass);
		DBProp DBP = LP.GiveDBprop();
		System.out.println("DBDriver:"+DBP.getDriver());
	 	System.out.println("ユーザ:"+DBP.getUser());
	}

	public void dateTest(String string_date) throws ParseException
	{
		System.out.println("string" + string_date);

		SimpleDateFormat format=new SimpleDateFormat();
		format.setLenient( true );
		format.applyPattern( "yyyy/MM/dd" );

		java.util.Date date = format.parse( "2004/12/20");

		System.out.println( "util" + date);

		java.sql.Date sqlDate = new java.sql.Date(date.getTime());

		System.out.println("sql" + sqlDate);


	}
}
