package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;


/***
 *
 * @author OWNER
 *	プロジェクト固有の設定をロード
 * TODO DBの方と抽象か
 */
public class LoadProjectProp {

	/**
	 * これはプロパティ格納用クラス
	 */
	ProjectProp propEntity;

	public LoadProjectProp() {
		propEntity = new ProjectProp();
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
		propEntity.setName(prop.getProperty("project.name"));
		propEntity.setServletPath(prop.getProperty("project.servletPath"));
	}


	/**
	 *プロパティ読み込んだ後、エンティティクラスを返す
	 */
	public ProjectProp giveProjectProp()
	{
		return propEntity;
	}


}
