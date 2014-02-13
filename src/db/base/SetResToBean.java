package db.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import calam.BaseBeans;
import calam.CalamManager;
// extends BaseBeans
/**
 * ジェネリック型でなんかしたいとき
 * @author OWNER
 *
 * @param <T> 指定したいクラス
 */
public class SetResToBean<T> {

	public SetResToBean() {
	}

	ServletContext contextparam;

	public SetResToBean(ServletContext context) {
		contextparam = context;
	}


	/**
	 *　インスタンス化するときに指定した型の
	 *　ArrayListにresultの中身を詰める
	 * @param result DBから取得した、resultset
	 * @param clazz 中身を詰めたいBeans
	 * @return BeansのつまったArraylist
	 */
	@SuppressWarnings("unchecked")
	public List<BaseBeans> set(ResultSet result, Class<?> clazz)
	{
		//びーんんつめるアレイリスト
		ArrayList<BaseBeans> list = new ArrayList<BaseBeans>();
		//び－ンを扱うクラス
		CalamManager cm = new CalamManager();
		//データベースを扱う必須クラス
//		DataAccess bdb = new DataAccess();

		//リザルトメタ
		ResultSetMetaData rsmd = null;
		try {
			rsmd = result.getMetaData();
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("SetResToBeanのgetMatadataError");
		}
		//DBカラム名
		String fName = null;
		//カラム型名
		String typeName = "";
		//bean フィールド名
		Field pFileld = null;
		//ビーン
		Object bean = null;

		// 結果行をループ
		try {
			while (result.next())
			{
				//インスタンス化
				Constructor constructor = null;
				try {
					if(this.contextparam == null)
					{
						bean = (T) clazz.newInstance();
					}
					else
					{
						try {
							constructor = clazz.getConstructor(ServletContext.class);
							System.out.println("コンテキスト有でインスタンス化したぜ");
						} catch (SecurityException e1) {
							System.out.println("SecurityExceptionコンストラクター取得できません");
							e1.printStackTrace();
						} catch (NoSuchMethodException e1) {
							System.out.println("NoSuchMethodExceptionコンストラクター取得できません");
							e1.printStackTrace();
						}
						try {
							bean = constructor.newInstance(contextparam);
						} catch (IllegalArgumentException e) {
							System.out.println("コンストラクターからインスタンス化できません");
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							System.out.println("コンストラクターからインスタンス化できません");
							e.printStackTrace();
						}
					}
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

				//列の数だけ列名を取得
				for (int i = 1; i < rsmd.getColumnCount() + 1; i++)
				{
					//DBフィールド名取得
					fName = rsmd.getColumnName(i);
					//ビーンのフィールド取得
					pFileld = cm.GetBeanField(fName,bean);
					//DB型名取得
					typeName = rsmd.getColumnTypeName(i);

					//型ごとの取得関数でデータ取得
					if (typeName == "INT")
					{
						bean = (T) this.setValuetoBean(bean, pFileld, result.getInt(fName));
					}
					if(typeName == "NUMBER")
					{
						bean = (T) this.setValuetoBean(bean, pFileld, result.getInt(fName));
					}
					if (typeName == "VARCHAR")
					{
						bean = (T) this.setValuetoBean(bean, pFileld, result.getString(fName));
					}
					if(typeName == "VARCHAR2")
					{
						bean = (T) this.setValuetoBean(bean, pFileld, result.getString(fName));
					}
					if(typeName == "DATE")
					{
						bean = (T) this.setValuetoBean(bean, pFileld, result.getDate(fName));
					}
				}
				//リストに追加
				list.add((BaseBeans)bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}



	/**
	 * Beansの指定したフィールドに値を突っ込み、突っ込んだ後のBeansを返す
	 * @param o Beans
	 * @param field BeansField
	 * @param value int
	 * @return Beans
	 */
	public Object setValuetoBean(Object o, Field field, int value)
	{
		CalamManager cm = new CalamManager();
		try {
			//Beansに値を入れる
			//setメソッドを取得して値を入れる

			cm.getBeanSetter(o, field, int.class).invoke(o, value);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			System.out.println("DataAccess.setValuetoBean そもそものメソッドが例外してるで");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println("DataAccess.setValuetoBean なんかおかしいで");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.out.println("DataAccess.setValuetoBean そのメソッドにはアクセスできない");
		}
		return o;
	}

	/**
	 * Beansの指定したフィールドに値を突っ込み、突っ込んだ後のBeansを返す
	 * @param o Beans
	 * @param field BeansField
	 * @param value int
	 * @return Beans
	 */
	public Object setValuetoBean(Object o, Field field, String value)
	{
		CalamManager cm = new CalamManager();
		try {
			//Beansに値を入れる
			//setメソッドを取得して値を入れる
			cm.getBeanSetter(o, field, String.class).invoke(o, value);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			System.out.println("DataAccess.setValuetoBean そもそものメソッドが例外してるで");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println("DataAccess.setValuetoBean なんかおかしいで");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.out.println("DataAccess.setValuetoBean そのメソッドにはアクセスできない");
		}
		return o;
	}

	/**
	 * Beansの指定したフィールドに値を突っ込み、突っ込んだ後のBeansを返す
	 * @param o Beans
	 * @param field BeansField
	 * @param value int
	 * @return Beans
	 */
	public Object setValuetoBean(Object o, Field field, Date value)
	{
		CalamManager cm = new CalamManager();
		try {
			//Beansに値を入れる
			//setメソッドを取得して値を入れる
			cm.getBeanSetter(o, field, Date.class).invoke(o, value);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			System.out.println("DataAccess.setValuetoBean そもそものメソッドが例外してるで");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println("DataAccess.setValuetoBean なんかおかしいで");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.out.println("DataAccess.setValuetoBean そのメソッドにはアクセスできない");
		}
		return o;
	}

}
