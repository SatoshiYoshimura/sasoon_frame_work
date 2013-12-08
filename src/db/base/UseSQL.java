package db.base;

import java.sql.SQLException;

import javax.servlet.ServletContext;

public class UseSQL {

	//DAO必須クラス
	DataAccess dataAccess;
	public ServletContext context;

	public void CloseAccess()
	{
		try {
			this.dataAccess.Close();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.out.println("DB閉じれていない");
		}
	}

	@Override
	  protected void finalize() throws Throwable {
	    try {
	      super.finalize();
	    } finally {
	      destruction();
	    }
	  }

	  private void destruction() {
	    // 解放とか、破棄とか
		  this.CloseAccess();
	  }
}
