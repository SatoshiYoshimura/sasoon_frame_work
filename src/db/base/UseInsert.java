package db.base;

import javax.servlet.ServletContext;

import db.base.error.RollbackException;

public class UseInsert extends UseSQL {

	public UseInsert() {
		dataAccess = new DataAccess();
	}

	/**
	 * サーブレットで使うときはこっち使わなうんこになる
	 * @param contextParam
	 */
	public UseInsert(ServletContext contextParam)
	{
		this.context = contextParam;
		dataAccess = new DataAccess(context);
	}

	/**
	 *インサート文使うときのメソッド
	 * @param sql
	 * @return
	 */
	public int DoInsert(String sql)
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
