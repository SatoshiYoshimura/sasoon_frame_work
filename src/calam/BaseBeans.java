/**
 *
 */
package calam;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import calam.error.IdNullException;
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
		this.newSelectObj();
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

	/***
	 * SQLを手打ち入力して自由にインサート
	 * @param sql
	 */
	public void freeInsert(String sql){

		this.newInsertObj();
		String thisSql = sql;
		insertObj.DoInsert(thisSql);
	}

	/**
	 * インサートオブジェクトインスタンス化
	 */
	private void newInsertObj(){
		if(contextParam != null)
		{
			insertObj = new UseInsert(contextParam);
		}
		else
		{
			insertObj = new UseInsert();
		}
	}

	/**
	 * 自由にアップデート文を行う
	 * 戻り値はない
	 * @param sql
	 */
	public void freeUpdate(String sql){
		this.newUpdateObj();
		String thisSql = sql;

		updateObj.DoUpdate(thisSql);

	}


	/**
	 * Select文を自由に実行するクラス
	 * @param sql
	 * @return List<BaseBeans>
	 */
	public List<BaseBeans> freeSelect(String sql){

		this.newSelectObj();

		List<BaseBeans> BeanArray = new ArrayList<BaseBeans>();
		String thisSql = sql;
		try {
			clazz = Class.forName(beanClassNameInPackage);
		} catch (ClassNotFoundException e1) {
			System.out.println("findAllクラスないよ");
			e1.printStackTrace();
		}

		try {
			BeanArray =  selectObj.DoSelect(thisSql, clazz);
		} catch (DBAccessException e) {
			System.out.println("データベースアクセスエラー:executeSelect");
			e.printStackTrace();
		}

		return BeanArray;
	}

	/**
	 * 自由にデリート文を実行する
	 * @param sql
	 * 戻り値なし
	 */
	public void freeDelete(String sql){
		this.newDeleteObj();
		String thisSql = sql;
		deleteObj.doDelete(thisSql);
	}

	/**
	 * デリートオブジェクトインスタンス化
	 */
	private void newDeleteObj(){
		if(contextParam == null)
		{
			deleteObj = new UseDelete();
		}
		else
		{
			deleteObj = new UseDelete(contextParam);
		}
	}

	/**
	 * デリート実行
	 * @throws IdNullException
	 */
	public void delete() throws IdNullException
	{
		int id = 0;
		id = this.getId();

		if(id == 0){
			throw new IdNullException();
		}
		this.newDeleteObj();
		String sql = "";
		sql = createSQL.Delete().From(beanClassName).Where(BaseColumns.id, "=", this.getId()).GenerateAlltoString();
		deleteObj.doDelete(sql);
	}

	private List<Object> getBeanValueList(BaseBeans beans)
	{
		List <Object> valueList  = new ArrayList<Object>();

		for (Field field : beans.getClass().getDeclaredFields())
		{
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
			beanFieldNameList.add(stringManager.pullClassName(field));
		}

		return beanFieldNameList;
	}

	/**
	 * upDateオブジェクトを条件に基づきインスタンス化
	 */
	private void newUpdateObj(){
		if(contextParam != null){
			updateObj = new UseUpdate(contextParam);
		}else{
			updateObj = new UseUpdate();
		}
	}

	private void newSelectObj(){
		if(contextParam != null){
			selectObj = new UseSelect<BaseBeans>(contextParam);
		}else{
			selectObj = new UseSelect<BaseBeans>();
		}
	}

	/**
	 * 簡易的にfind実装 Model = Model.find(id);
	 * で返値に値が詰まってる
	 * @param int id
	 * @return 値の詰まったモデル
	 */
	public BaseBeans find(int idParam){
		List<BaseBeans> BeanArray = new ArrayList<BaseBeans>();
		String thisSql = createSQL.Select("*").From(beanClassName).Where("ID", "=", idParam).GenerateAlltoString();
		try {
			clazz = Class.forName(beanClassNameInPackage);
		} catch (ClassNotFoundException e1) {
			System.out.println("findクラスないよ");
			e1.printStackTrace();
		}

		try {
			BeanArray =  selectObj.DoSelect(thisSql, clazz);
		} catch (DBAccessException e) {
			System.out.println("データベースアクセスエラー:executeSelect");
			e.printStackTrace();
		}
		BaseBeans thisBean = BeanArray.get(0);
		return thisBean;
	}

	/**
	 * 指定したフィールドのゲッター取得
	 * @param fie
	 * @return
	 */
	private Method getGetter(Field fie,Class<? extends BaseBeans> clazz){
		Method getter = null;
		Class thisClass = clazz;
		try {
			getter = thisClass.getMethod("get"+stringManager.FirstUpper(fie.getName()) , null);
			Class fC = fie.getGenericType().getClass();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		}

		return getter;
	}


	/***
	 * 指定したフィールドのセッター取得
	 * @param fie
	 * @param clazz
	 * @return
	 */
	private Method getSetter(Field fie,Class<? extends BaseBeans> clazz){
		Method setter = null;
		try {
			setter = clazz.getMethod("set"+stringManager.FirstUpper(fie.getName()) , (Class<?>)fie.getGenericType());
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		}

		return setter;
	}

	/***
	 * 指定したビーンズの指定したゲッターから値取得
	 * @param bean
	 * @param getter
	 * @return
	 */
	private Object getGetterInvokeValue(BaseBeans bean,Method getter){
		Object ret = null;
		try {
			ret = getter.invoke(bean, null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/***
	 * update メソッド
	 * ビーンズの状態でDB更新
	 * ビーンズにIDが指定されていないとエラー
	 * @throws IdNullException
	 */
	public void update() throws IdNullException
	{
		int id = 0;
		id = this.getId();
		System.out.println("idチェック"+id);
		//idがセットされていないとこのメソッドは使えない
		if(id == 0){
			throw new IdNullException();
		}

		this.newUpdateObj();
		this.newSelectObj();

		//更新前の自分の値取得
		BaseBeans thisBean = this.find(this.getId());

		Field[] fields;
		Class<? extends BaseBeans> thisClass = this.getClass();
		fields = thisClass.getDeclaredFields();
		for(Field field:fields){
			Method getter = null;
			Method setter = null;
			//セッターとゲッター取得
			getter = this.getGetter(field, thisClass);
			setter = this.getSetter(field, thisClass);
			Object ret = null;
			ret = this.getGetterInvokeValue(this, getter);

			//セッターに値詰めるのはあえてここでやる
			if(ret != null){
				try {
					setter.invoke(thisBean, ret);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}

		List<Object> valueList = new ArrayList<Object>();
		valueList = this.getBeanValueList(thisBean);
		List<String> clumNameList = new ArrayList<String>();
		clumNameList = this.getCulumNameList();
		String sql = "";
		sql =  createSQL.Update(this.beanClassName, clumNameList).Set(valueList).Where(BaseColumns.id, "=", this.getId()).GenerateAlltoString();
		System.out.println("baebeasnのupdate:"+sql);

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
		this.newInsertObj();

		List<Object> valueList = new ArrayList<Object>();
		valueList = this.getBeanValueList(this);
		List<String> clumNameList = new ArrayList<String>();
		clumNameList = this.getCulumNameList();
		String sql = "";
		sql = createSQL.InsertInto(beanClassName,clumNameList).Values(this.getBeanValueList(this)).GenerateAlltoString();
		insertObj.DoInsert(sql);
	}

}
