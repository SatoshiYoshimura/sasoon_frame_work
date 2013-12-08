/**
 *
 */
package calam;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import edit.string.StringManager;

/**
 * @author OWNER
 * Beanのフィールド名やセッターを取得
 */
public class CalamManager {

	StringManager stringM = new StringManager();
	/**
	 *
	 */
	public CalamManager() {

	}

	/**
	 *enumカラム名とbeans与えたらbeansの指定したフィールドを取得
	 * @param calam
	 * @param o
	 * @return
	 */
	public Field GetBeanField(String calamName, Object o)
	{
		Field field = null;
		try
		{
			//フィールド取得
			System.out.println("beanは" + o);
			field = o.getClass().getDeclaredField(stringM.calamNametoBeanField(calamName));
		} catch (SecurityException e1)
		{
			e1.printStackTrace();
			System.out.println("せきゅやばいGetBeanFieldで");
		} catch (NoSuchFieldException e1)
		{
			e1.printStackTrace();
			System.out.println("んなフィールドねえよGetBeanField");
			System.out.println(calamName);
		}

		return field;
	}

	/**
	 * 型とフィールドとbeansを渡してやればそのセッターを返す 最後の引数はプリミティブ型.classで
	 * @param o Beans
	 * @param field フィールド
	 * @param type 型
	 * @return
	 */
	public Method getBeanSetter(Object o,Field field,Class<?> type)
	{
		Method m = null;
		System.out.println( stringM.FirstUpper(field.getName()));
		try {
			m = o.getClass().getDeclaredMethod("set" + stringM.FirstUpper(field.getName()) ,type);
			System.out.println(m);
		} catch (SecurityException e) {
			e.printStackTrace();
			System.out.println("セキュやばいgetBeanSetter");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();

			System.out.println("んなメソッドないgetBeanSetter");
		}
		return m;
	}
}