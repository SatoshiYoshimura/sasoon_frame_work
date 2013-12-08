/**
 *
 */
package calam;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import db.base.CreateSQL;
import db.base.UseDelete;
import db.base.UseInsert;
import db.base.UseSelect;
import db.base.UseUpdate;
import db.base.error.DBAccessException;
import edit.string.StringManager;

/**
 * @author OWNER
 *
 */
public class BaseBeans {

	//このクラス内に基本的SQL実行オブジェクトを保持
	//正直依存関係やばい気がする
	protected UseDelete deleteObj;
	protected UseSelect<BaseBeans> selectObj;
	protected UseInsert insertObj;
	protected UseUpdate updateObj;
    protected CreateSQL createSQL;
    protected String beanClassName;
    protected String beanClassNameInPackage;
    protected StringManager stringManager;
    protected Class<?> clazz;
    protected ServletContext contextParam = null;

    //全てのカラムにIDつける
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	/**
	 *サーブレットじゃない時
	 *callingClasss = Beansname
	 */
	public BaseBeans() {
		insertObj = new UseInsert();
		updateObj = new UseUpdate();
		createSQL = new CreateSQL();
		stringManager = new StringManager();
        beanClassName = stringManager.pullClassName(new Throwable().getStackTrace()[1].getClassName());
        beanClassNameInPackage = new Throwable().getStackTrace()[1].getClassName();
        clazz = loadChildClass();
		selectObj = new UseSelect<BaseBeans>();
	}

	/**
	 *コンテキストパラム欲しい時
	 *callingClasss "Beansname"
	 */
	public BaseBeans(ServletContext context) {
		contextParam = context;
		selectObj = new UseSelect<BaseBeans>(context);
		insertObj = new UseInsert(context);
		updateObj = new UseUpdate(context);
		createSQL = new CreateSQL();
		stringManager = new StringManager();
        beanClassName = stringManager.pullClassName(new Throwable().getStackTrace()[1].getClassName());
        beanClassNameInPackage = new Throwable().getStackTrace()[1].getClassName();
        clazz = loadChildClass();
	}

	/***
	 * ヨビダシタBeanクラスをロード
	 * @return ロードしたクラスの型//これももっておく
	 */
	private Class<?> loadChildClass()
	{
		Class<?> clazz = null;
		try {
			clazz = Class.forName(beanClassNameInPackage);
		} catch (ClassNotFoundException e) {
			System.out.println("クラスが見つかりません");
			e.printStackTrace();
		}

		return clazz;
	}

	/***
	 * 全件取得
	 *
	 */
	//TODO findAll でbasebean basebeanでDAO ループ
	public  List findAll()
	{
		if(contextParam != null)
		{
			selectObj = new UseSelect<BaseBeans>(contextParam);
		}
		else
		{
			selectObj = new UseSelect<BaseBeans>();
		}
		List<BaseBeans> BeanArray = new ArrayList<BaseBeans>();
		String sql = createSQL.Select("*").From(beanClassName).GenerateAlltoString();
		try {
			clazz = Class.forName(beanClassNameInPackage);
		} catch (ClassNotFoundException e1) {
			System.out.println("findAllクラスないよ");
			e1.printStackTrace();
		}

		try {
			BeanArray =  selectObj.DoSelect(sql, clazz);
		} catch (DBAccessException e) {
			System.out.println("データベースアクセスエラー:findAll");
			e.printStackTrace();
		}
		return BeanArray;
	}

	public void delete()
	{
		if(contextParam.equals(null))
		{
			deleteObj = new UseDelete();
		}
		else
		{
			deleteObj = new UseDelete(contextParam);
		}
		String sql = "";
		sql = createSQL.Delete().From(beanClassName).Where(BaseColumns.id, "=", this.getId()).GenerateAlltoString();
		System.out.println(sql);
		deleteObj.doDelete(sql);
	}

	private List<Object> getBeanValueList(BaseBeans beans)
	{
		List <Object> valueList  = new ArrayList<Object>();

		for (Field field : beans.getClass().getDeclaredFields())
		{
			System.out.println("->[" +field.getName() + "]");
			boolean accessible = field.isAccessible();
			try {
			        field.setAccessible(true);
			        Object value = null;
					try {
						value = field.get(beans);
					} catch (IllegalArgumentException e) {
						System.out.println("フィールドがゲットできません");
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						System.out.println("フィールドにアクセスできません");
						e.printStackTrace();
					}
					System.out.println("-->[" + value + "]");
					valueList.add(value);
				}
			finally {
			        field.setAccessible(accessible);
			}
		}

		return valueList;
	}

	/***
	 * フィールド名をString型のリストにして返す
	 * @param beanはインスタンス
	 * @return List<String>
	 */
	public List<String> getBeanFields(BaseBeans bean)
	{
		List <String> beanFieldList = new ArrayList<String>();
		Field[] fieldArray;
		fieldArray = bean.getClass().getDeclaredFields();

//まずは文字列も対応させる
		for (Field field : fieldArray)
		{
			beanFieldList.add(field.toGenericString());
		}

		return beanFieldList;
	}

	/***
	 * 取得したフィールドをフィールド名だけに整形
	 * @param bean
	 * @return
	 */
	public List<String> pullBeanFieldsString(BaseBeans bean)
	{
		List <String> beanFieldNameList = new ArrayList<String>();
		List <String> packFieldList = getBeanFields(bean);
		for(String field: packFieldList){
			System.out.println("->[" + field + "]");
			beanFieldNameList.add(stringManager.pullClassName(field));
		}

		return beanFieldNameList;
	}

	/***
	 * update メソッド
	 * 普通に引数でアップロードさせる
	 * 引数
	 * Beans : インスタンスかした自分 それ以外を入れると
	 * ビーンズの状態でDB更新
	 * 現在できていない、createSQLでアップデートが機能しないListから値がとれない
	 */
	public void update()
	{
		if(contextParam != null)
		{
			updateObj = new UseUpdate(contextParam);
		}
		else
		{
			updateObj = new UseUpdate();
		}

		List<Object> valueList = new ArrayList<Object>();
		valueList = this.getBeanValueList(this);
		List<String> clumNameList = new ArrayList<String>();
		clumNameList = this.getCulumNameList();
		String sql = "";

		//SetメソッドをListでも行けるようにする
		sql =  createSQL.Update("Students", clumNameList).Set(valueList).Where(BaseColumns.id, "=", this.getId()).GenerateAlltoString();
		System.out.println(sql);

		updateObj.DoUpdate(sql);
	}

	/**
	 * DBに対応するculumNameを取得
	 * 引き数なし
	 * 戻り値：DBのカラムの名前リス
	 */
	public List<String> getCulumNameList()
	{
		List<String> fieldNameList = new ArrayList<String>();
		fieldNameList = this.pullBeanFieldsString(this);
		List<String> clumNameList = new ArrayList<String>();
		clumNameList = stringManager.refillBeanToClum(fieldNameList);

		return clumNameList;
	}

	/***
	 * インサート
	 * 自分の持つ値が登録される
	 * TODO saveメソッドにして、updateと統合
	 */
	public void insert()
	{
		if(contextParam != null)
		{
			insertObj = new UseInsert(contextParam);
		}
		else
		{
			insertObj = new UseInsert();
		}

		List<Object> valueList = new ArrayList<Object>();
		valueList = this.getBeanValueList(this);
		List<String> clumNameList = new ArrayList<String>();
		clumNameList = this.getCulumNameList();
		String sql = "";
		sql = createSQL.InsertInto(beanClassName,clumNameList).Values(this.getBeanValueList(this)).GenerateAlltoString();
		insertObj.DoInsert(sql);
	}



}
