/**
 *プロパティ読み込みクラス
 */
package db.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author OWNER
 *
 */
public class LoadProperty {

	/**
	 * これはエンティティ格納用クラス
	 */
	DBProp propEntity;

	/**
	 *どうでもよい
	 */
	public LoadProperty() {
		propEntity = new DBProp();
	}

	/**
	 *プロパティの読み込み
	 *
	 */
	public void load(String pass)
	{
		// プロパティの読み込み
		  Properties prop = new Properties();
		  try {
			  System.out.println(pass);
			prop.load(new InputStreamReader(new FileInputStream(pass) , "UTF-8"));
//			prop.load((new FileInputStream(pass)));
		} catch (FileNotFoundException e) {
			// ファイルないとき
			e.printStackTrace();
			System.out.println("ファイルないで");
		} catch (IOException e) {
			// ＩOおかしいとき
			e.printStackTrace();
			System.out.println("IOおかしいで");
		}

		  //格納開始
		  propEntity.setUrl(prop.getProperty("db.url"));
		  propEntity.setDriver(prop.getProperty("db.driver"));
		  propEntity.setUser(prop.getProperty("db.user"));
		  propEntity.setPass(prop.getProperty("db.pass"));
		  System.out.println(prop.getProperty("db.jndiFlag"));
		  propEntity.setJndiFlag(Integer.parseInt(prop.getProperty("db.jndiFlag")));
	}

	/**
	 *プロパティ読み込んだ後、エンティティクラスを返す
	 */
	public DBProp GiveDBprop()
	{
		return propEntity;
	}


}
