/**
 *
 */
package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import calam.EmployeeColums;
import calam.JobsColumns;
import calam.StudentColums;
import calam.Students;
import calam.TableList;
import db.base.CreateSQL;

public class Test<T> {

	/**
	 *
	 */
	public Test() {
		// TODO 自動生成されたコンストラクター・スタブ
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

		//test2();



		//List<BaseBeans> StudentsList = new ArrayList<BaseBeans>();
//		List<String> stclumNameList = st.getBeanFields(st);
//		stclumNameList = st.pullBeanFieldsString(st);


//		StudentsList = st.findAll();
//		for(BaseBeans stu: StudentsList){
////			stu.update(stu);
//			//System.out.println(((Students) stu).getClassName());
//		}

	}

	private void test2() {
//

		Students st = new Students();
		List<Students> stList =  st.findAll();
		for(Students student : stList )
		{
			System.out.println(student.getId());
		}
		System.out.println();
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
