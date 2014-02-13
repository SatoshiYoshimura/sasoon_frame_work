package db.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;

import calam.BaseBeans;
import calam.TableList;
import db.base.error.DBAccessException;
import db.base.error.RollbackException;

/**
 * SELECT文専用　SQLを投げて、beansを指定してやると、beansのつまったSQL結果のリストが返ってくる メソッドしかない
 * @author OWNER
 *
 * @param <T> beans
 */
public class UseSelect<T> extends UseSQL {


	public UseSelect() {
		dataAccess = new DataAccess();
	}

	/**
	 * サーブレットから呼び出すときは、コンテキストパスを与えなければ
	 *  引数ServletContext context = Servlet.getServletContext();
	 * @param context
	 */
	public UseSelect(ServletContext contextParam) {
		this.context = contextParam;

		dataAccess = new DataAccess(context);
	}


	/**
	 * SELECT文専用　SQLを投げて、beans.classを指定してやると、SQL結果のリストが返ってくる
	 * @param SQL
	 * @param o エンティティ
	 * @throws DBAccessException
	 */
	public List<BaseBeans> DoSelect(String SQL, Class<?> clazz) throws DBAccessException
	{
		ResultSet resultSet = null;
		List<BaseBeans> resultList = null;

		try {
			resultSet = dataAccess.SelectSQL(SQL);
		} catch (RollbackException e1) {
			System.out.println("ロールバックえらー");
			e1.printStackTrace();
		}
		//可変長引数は配列ごと渡す
		//リザルトセット取得クラス
		SetResToBean<T> sr = null;
		if(this.context != null )
		{
			sr = new SetResToBean<T>(context);
		}
		else
		{
			sr = new SetResToBean<T>();
		}
		resultList = sr.set(resultSet, clazz);
		try {
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("れざるとセット閉じられへん");
		}

		return resultList;
	}


	/**
	 * 全件表示メソッドテーブル名を指定してやるだけ
	 * @param tableName TableList
	 * @param clazz Beansの型
	 * @return
	 */
	public List<BaseBeans> getAllData(TableList tableName,Class<T> clazz)
	{
		CreateSQL cSql = new CreateSQL();
		String sql = cSql.Select("*").From(tableName).GenerateAlltoString();
		ResultSet resultSet = null;

		try {
			resultSet = dataAccess.SelectSQL(sql);
		} catch (DBAccessException e) {
			e.printStackTrace();
			System.out.println("DBアクセスエラー");
		} catch (RollbackException e) {
			e.printStackTrace();
			System.out.println("ロールバックエラー");
		}

		SetResToBean<T> sr = new SetResToBean<T>();
		List<BaseBeans> resultList = sr.set(resultSet, clazz);

		try {
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("リざるとセット閉じられへん");
		}

		return resultList;
	}

	/**
	 * 全件表示メソッドテーブル名を指定してやるだけ String版
	 * @param tableName String
	 * @param clazz Beansの型
	 * @return resultList
	 */
	public List<BaseBeans> getAllData(String tableName,Class<T> clazz)
	{
		CreateSQL cSql = new CreateSQL();
		String sql = cSql.Select("*").From(tableName).GenerateAlltoString();
		ResultSet resultSet = null;

		try {
			resultSet = dataAccess.SelectSQL(sql);
		} catch (DBAccessException e) {
			e.printStackTrace();
			System.out.println("DBアクセスエラー");
		} catch (RollbackException e) {
			e.printStackTrace();
			System.out.println("ロールバックエラー");
		}

		SetResToBean<T> sr = new SetResToBean<T>();
		List<BaseBeans> resultList = sr.set(resultSet, clazz);

		try {
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("リざるとセット閉じられへん");
		}

		return resultList;
	}

}
