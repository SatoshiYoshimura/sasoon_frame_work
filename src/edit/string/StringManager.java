/**
 *
 */
package edit.string;

import java.util.ArrayList;
import java.util.List;

import MyArrayList.IntArrayList;
import MyArrayList.StringArrayList;

/**
 * @author OWNER
 * 文字列をうまい具合に整形したい場合に使うクラス
 */
public class StringManager {

	/**
	 *　コンストｈ飯間のとこなし
	 */
	public StringManager() {

	}

	/**
	 * アンダーバーが消え去る
	 * @param calamName
	 * @return
	 */
	public String calamToBean(String calamName)
	{
		//アンダーバー抜く作業
		return calamName.replaceAll("_", "");
	}

	/**
	 *文字列を引数に　アンダーバー以前の文字列を取得 最後の要素
	 * @param str _を含む文字列
	 * @param int 文字列内前回出現した_の位置　
	 * 	 * @param int 文字列内今回出現した_の位置　
	 * @return str
	 */
	public String GetBefore_String(String str, int beforeIndex, boolean lastflg)
	{
		String comp;
		//インデックスリストに追加
		comp = str.substring(beforeIndex + 1, str.length());

		return comp;
	}

	/**
	 *文字列を引数に　アンダーバー以前の文字列を取得 2回目の_以降
	 * @param str _を含む文字列
	 * @param int 文字列内前回出現した_の位置　
	 * 	 * @param int 文字列内今回出現した_の位置　
	 * @return str
	 */
	public String GetBefore_String(String str, int beforeIndex, int thisIndex)
	{
		String comp;
		//インデックスリストに追加
		comp = str.substring(beforeIndex + 1, thisIndex);

		return comp;
	}

	/**
	 *文字列を引数に　アンダーバー以前の文字列を取得 最初の_まで
	 * @param str _を含む文字列
	 *
	 * @param int 文字列内今回出現した_の位置　
	 * @return str
	 */
	private String GetBefore_String(String str, int thisIndex)
	{
		String comp;
		//インデックスリストに追加
		comp = str.substring(0, thisIndex);
		return comp;
	}

	/**
	 * 文字列を入れたら、中の_数を取得する
	 * @param calamName
	 * @return
	 */
	public int get_Count(String str)
	{
		int cnt = str.replaceAll("[^_]", "").length();

		return cnt;
	}

	/**
	 * 指定した文字列の中の_の位置をIndexArrayListに全て追加
	 * @param indexList
	 * @param str
	 * @param indexcount
	 * @return　indexList
	 */
	private IntArrayList set_Index(IntArrayList indexList, String str, int indexcount)
	{
		//_の出てきたindexを追加
		for (int i = 0; i < indexcount; i++)
		{
			//最初の時
			if (i == 0)
			{
				indexList.add(str.indexOf("_"));
			}
			//2個目以降
			else
			{
				indexList.add(str.indexOf("_", indexList.get(i - 1) + 1));
			}

		}
		return indexList;
	}

	/**
	 * 最初の一文字だけ大文字にして文字列をかええします
	 * @param str
	 * @return
	 */
	public String FirstUpper(String str)
	{
		String firstUpperString = str;

		firstUpperString = firstUpperString.substring(0, 1).toUpperCase() + firstUpperString.substring(1);

		return firstUpperString;
	}

	/**
	 * カラム名をBeansのフィールド名にしてしまう これ黒魔術使いました
	 * @param String calamName
	 * @return String BeanFieldName
	 */
	public String calamNametoBeanField(String calamName)
	{
		//index用arraylist
		IntArrayList index = new IntArrayList();
		//_で区切って前後を格納リスト
		StringArrayList compList = new StringArrayList();
		//文字列

		//_が出てきた回数を取得
		int cnt = get_Count(calamName);

		//一旦全部小文字に
		calamName = calamName.toLowerCase();

		//_が出てきたら
		if (cnt > 0)
		{
			//_の出てきたindexを全て追加
			index = this.set_Index(index, calamName, cnt);

			//_のindex個数分+1回す（最後の部分切れてたから + 1
			for (int i = 0; i < index.size() + 1; i++)
			{
				//最初
				if (i == 0)
				{
					//コンポーネントリストに追加
					compList.add(this.GetBefore_String(calamName, index.get(i)));
				}
				else if (i >= 1 && i < index.size())
				{
					//2個目以降
					compList.add(this.FirstUpper(GetBefore_String(calamName, index.get(i - 1), index.get(i))));
				}
				else if (i == index.size())
				{
					//最後の終端コンポーネント
					compList.add(this.FirstUpper(GetBefore_String(calamName, index.get(i - 1), true)));
				}
			}
		}
		else
		{
			compList.add(calamName);
		}

		//一つの文字列にする
		String str2 = "";
		for (int i = 0; i < compList.size(); i++)
		{
			str2 += compList.get(i);
		}
		return str2;
	}

	/***
	 * フィールド名をカラム名に変換してしまう
	 * @param String BeanFieldNamcalamName
	 * @return String calamName
	 */
	public String beanFieldNameToCalamName(String calamName)
	{
		String beanFieldName = "";
		beanFieldName = vest_String(this.splitStringFromUpper(calamName));
		beanFieldName = beanFieldName.toLowerCase();
		return beanFieldName;
	}

	/**
	 * 全部の大文字の数を取得
	 * 引数：文字列
	 * 戻り値:文字列内の大文字数
	 */
	public int getUpperCount(String str)
	{
		int count = 0;
		for(int i = 0;i < str.length();i++)
		{
			 if(Character.isUpperCase(str.charAt(i)))
			 {
				 count++;
			 }
		}
		return count;
	}

	/**
	 *　文字列を大文字部分前以降に分割する
	 * @param str
	 * @return　List<String>
	 */
	public List<String> splitStringFromUpper(String str)
	{
		List<String> splitStringList = new ArrayList<String>();
		List <StringBuilder> sbList = new ArrayList<StringBuilder>();
		sbList.add(new StringBuilder());
		int count = 0;
		for(int i = 0;i < str.length();i++)
		{
			 if(Character.isUpperCase(str.charAt(i)))
			 {
				 count++;
				 sbList.add(new StringBuilder());
				 sbList.get(count).append(str.charAt(i));
			 }
			 else
			 {
				 sbList.get(count).append(str.charAt(i));
			 }
		}

		//sbを一個一個文字列にしていく
		for(int i = 0;i < sbList.size();i++)
		{
			splitStringList.add(sbList.get(i).toString());
		}
		return splitStringList;
	}

	/**
	 * 分割された文字に_を追加する
	 * 引数 List <String>
	 * 戻り値 String _付き
	 */
	public String vest_String(List<String> strList)
	{
		String vestString = "";
		StringBuilder sb = new StringBuilder();
		for(String str: strList){
			sb.append(str).append("_");
	    }
		vestString = sb.toString();
		//最後の_を取る
		if(vestString.endsWith("_"))
		{
			vestString = vestString.substring(0, vestString.length()-1);
		}
		return vestString;
	}

	/**
	 * fieldListをclumListに詰め替えるメソッド
	 * 引数 beanのfieldlist
	 * 戻り値 clumList
	 */
	public List<String> refillBeanToClum(List<String> beanFieldList)
	{
		List<String> clumNameList = new ArrayList<String>();
		for(String fieldName: beanFieldList)
		{
			clumNameList.add(this.beanFieldNameToCalamName(fieldName));
		}

		return clumNameList;
	}

	/***
	 * 	getClassNameのパッケージ名を抜く作業
	 * 引数：Thread.currentThread().getStackTrace()[0].getClassName()
	 * 戻り値:パッケージ名抜いたクラス名
	 */
	public String pullClassName(String originName)
	{
		String className = "";
		String str = originName;
		int index = originName.lastIndexOf(".");
		className = str.substring(index+1);

		return className;
	}


}
