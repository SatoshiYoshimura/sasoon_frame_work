package db.base;

import javax.servlet.ServletContext;

import db.base.error.RollbackException;

/**
 * デリート行うとき用クラス
 * @author OWNER
 *
 */
public class UseDelete extends UseSQL {

	public UseDelete() {
		dataAccess = new DataAccess();
	}


	/**
	 * サーブレットから呼び出すときは、コンテキストパスを与えなければ
	 *  引数ServletContext context = Servlet.getServletContext();
	 * @param context
	 */
	public UseDelete(ServletContext contextParam) {
		this.context = contextParam;
		dataAccess = new DataAccess(context);
	}


	/**
	 *デリート文使うときのメソッド
	 * @param sql
	 * @return
	 */
	public int doDelete(String sql)
	{
		int resultRow = 0;
		try {
			resultRow = this.dataAccess.NonQuerySQL(sql);
		} catch (RollbackException e) {
			e.printStackTrace();
			System.out.println("ロールバックエラー");
		}

		return resultRow;
	}


}
