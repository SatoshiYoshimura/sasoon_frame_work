package db.base;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import calam.CalamName;
import calam.TableList;

/**
 * 簡単にSQL作成できる神クラス
 * @author OWNER
 *
 */
public class CreateSQL {

	/**
	 * SQL生成用の変数
	 */
	private String SelectPart;
	private boolean SelectFlg;
	private String FromPart;
	private String FromTable;
	private boolean FromFlg;
	private String InsertPart;
	private boolean InsertFlg;
	private String ValuesPart;
	private boolean ValuesFlg;
	private String UpdatePart;
	private boolean UpdateFlg;
	private String DeletePart;
	private boolean DeleteFlg;
	private String JoinPart;
	private boolean JoinFlg;
	private String JoinTable;
	private String OnPart;
	private boolean OnFlg;

	//UpdateのUXをいいものにするためにListを用意
	//カラム名用
	List<CalamName> calamList;
	List<String> stringCalamList;
	private boolean useStringCulumListFlg;
	//setで指定する値用
	List<Object> setValueList;
	private String WherePart;
	private boolean WhereFlg;

	//TODO Whereは用途別？

	/**
	 * コンストラクタ
	 * 各種プロパティを初期化
	 */
	public CreateSQL()
	{
		SelectPart = "";
		SelectFlg = false;
		FromPart = "";
		FromFlg = false;
		FromTable = "";
		InsertPart = "";
		InsertFlg = false;
		ValuesPart = "";
		ValuesFlg = false;
		UpdatePart = "";
		UpdateFlg = false;
		WherePart = "";
		WhereFlg = false;
		DeletePart = "";
		DeleteFlg = false;
		JoinPart = "";
		JoinFlg = false;
		JoinTable = "";
		OnPart = "";
		OnFlg = false;
	}

	/**
	 *	セレクトと取得する要素指定
	 * @param str
	 * @return
	 */
	public CreateSQL Select(String... str)
	{
		int count = 0;
		SelectPart = "SELECT ";
		//最後は,付けない
		for (String s : str)
		{
			SelectPart += s;
			count++;
			if (count != str.length)
			{
				SelectPart += ",";
			}
		}
		SelectFlg = true;

		return this;
	}

	/**
	 *	セレクトと取得する要素指定
	 * @param str
	 * @return
	 */
	public CreateSQL Select(CalamName... clum)
	{
		int count = 0;
		SelectPart = "SELECT ";
		//最後は,付けない
		for (CalamName c : clum)
		{
			SelectPart += c;
			count++;
			if (count != clum.length)
			{
				SelectPart += ",";
			}
		}
		SelectFlg = true;

		return this;
	}
//	SELECT * FROM TableA
//	INNER JOIN TableB
//	ON TableA.name = TableB.name

	/**
	 * InnerJoin部の テーブル名を指定するところまで
	 * @param tableName TableList
	 * @return this
	 */
	public CreateSQL InnerJoin(TableList tableName)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(" INNER JOIN ").append(tableName).append(" ");
		this.JoinPart = sb.toString();
		this.JoinTable += tableName;
		JoinFlg = true;
		return this;
	}

	/**
	 * Joinの後のOn句 From()で入力したテーブルの持つカラム名をclum1 operandは演算子 clum2はJoin()で入力したテーブルの持つカラム名
	 * @param clum  CalamName
	 * @param operand String
	 * @param clum2 CalamName
	 * @return
	 */
	public CreateSQL On(CalamName clum1,String operand,CalamName clum2)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("ON ").append(FromTable).append(".");
		sb.append(clum1).append(" ").append(operand).append(" ").append(JoinTable).append(".").append(clum2);
		OnPart = sb.toString();
		OnFlg = true;

		return this;
	}


	/**
	 * フロム要素を指定する為のメソッド
	 * @param c
	 * @return
	 */
	public CreateSQL From(TableList c)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(" FROM ").append(c);
		FromPart = sb.toString();
		FromTable = c.name();
		FromFlg = true;
		return this;
	}

	/**
	 * フロム要素を指定する為のメソッド
	 * @param c
	 * @return
	 */
	public CreateSQL From(String s)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(" FROM ").append(s);
		FromPart = sb.toString();
		FromTable = s;
		FromFlg = true;
		return this;
	}

	//TODO ここ演算子の引数も演算子そのまま渡したい

	/**
	 * WHERE句の部分　指定したカラム名と指定した値を指定した演算で条件付け
	 * @param culm　CalamName.判定するカラム名
	 * @param operater　条件の演算子
	 * @param value 条件の値
	 * @return this
	 */
	public CreateSQL Where(CalamName culm,String operater,Object value)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(" WHERE ").append(culm).append(" ").append(operater).append(" ");
		WherePart = sb.toString();

		//判断用
		Class<? extends Object> C = value.getClass();
		String s = "";
		Date d = new Date(0);
		//値がString型もしくはデート型の場合
		if(C == s.getClass() || C == d.getClass() )
		{
			WherePart += "'" + value + "'";
		}
		else
		{
			WherePart += value;
		}

		WhereFlg = true;

		return this;
	}

	/**
	 * インサート文のやつ　入れたいテーブルと入れるカラム名を指定
	 * @param t TableList.テーブル名
	 * @param clum テーブル名Colums.カラム名
	 * @return　this
	 */
	public CreateSQL InsertInto(TableList t, CalamName... clum)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT into ").append(t).append("( ");

		int count = 0;
		//最後は,付けない
		for (CalamName c : clum)
		{
			sb.append(c);
			count++;
			if (count != clum.length)
			{
				sb.append(",");
			}
		}
		sb.append(" )");
		InsertPart = sb.toString();
		InsertFlg = true;
		return this;
	}

	/**
	 * インサート文のやつ　入れたいテーブルと入れるカラム名を指定 String版
	 * @param t String テーブル名
	 * @param clum String カラム名
	 * @return　this
	 */
	public CreateSQL InsertInto(String t, String... clum)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT into ").append(t).append("( ");

		int count = 0;
		//最後は,付けない
		for (String c : clum)
		{
			sb.append(c);
			count++;
			if (count != clum.length)
			{
				sb.append(",");
			}
		}
		sb.append(" )");
		InsertPart = sb.toString();
		InsertFlg = true;
		return this;
	}

	/**
	 * インサート文のやつ　入れたいテーブルと入れるカラム名を指定 StringList版
	 * @param t String テーブル名
	 * @param beanFieldLis tString List フィールドをカラムに変換したもの
	 * @return　this
	 */
	public CreateSQL InsertInto(String t, List<String> beanFieldList)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT into ").append(t).append("( ");

		int count = 0;
		//最後は,付けない
	    for(String c: beanFieldList){
	    	sb.append(c);
	    	count++;
			if (count != beanFieldList.size())
			{
				sb.append(",");
			}
	    }
		sb.append(" )");
		InsertPart = sb.toString();
		InsertFlg = true;
		return this;
	}

	/***
	 * BaseBeansに全ての基本処理を持たせようとするとこのメソッドが必要になる
	 * インサートの前半部分
	 * tがテーブル名 ArrayListは入れたいカラム名
	 * @param <T>
	 *
	 */
//	public <T> CreateSQL InsertInto(String t, ArrayList<T> clumList) {
//
//		StringBuilder sb = new StringBuilder();
//		sb.append("INSERT into ").append(t).append("( ");
//
//		for(T culumName: clumList){
//			System.out.println(hogeBean.getHoge());
//		}
//
//		int count = 0;
//		//最後は,付けない
//		for (String c : clumList)
//		{
//			sb.append(c);
//			count++;
//			if (count != clum.length)
//			{
//				sb.append(",");
//			}
//		}
//		sb.append(" )");
//		InsertPart = sb.toString();
//		InsertFlg = true;
//
//
//		return this;
//	}

	/**
	 * valueのリストを与えたらvalue部分sql生成
	 * @param valueList
	 * @return this
	 */
	public CreateSQL Values(List<Object> valueList)
	{
		int count = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("VALUES ( ");

		//型ごとに処理を分ける
		for (Object obj : valueList)
		{
			if (obj instanceof Integer)
			{
				sb.append(String.format("%d ", obj));
			}
			if (obj instanceof String)
			{
				sb.append("'").append(obj).append("'");
			}
			if (obj instanceof Date)
			{
				sb.append("'").append(obj).append("'");
			}

			//最後は,付けないで)つける
			count++;
			if (count != valueList.size())
			{
				sb.append(",");
			}
			else
			{
				sb.append(")");
			}
		}

		ValuesPart = sb.toString();
		ValuesFlg = true;
		return this;
	}

	/**
	 * TODO Object型がやばいので、こっちはコメントアウト　いつか間に型はさんで実装
	 * Values部分 指定した値を入れる
	 * @param o 入れたい値
	 * @return this
	 */
//	public CreateSQL Values(Object... o)
//	{
//		int count = 0;
//		StringBuilder sb = new StringBuilder();
//		sb.append("VALUES ( ");
//
//		//型ごとに処理を分ける
//		for (Object obj : o)
//		{
//			if (obj instanceof Integer)
//			{
//				sb.append(String.format("%d ", obj));
//			}
//			if (obj instanceof String)
//			{
//				sb.append("'").append(obj).append("'");
//			}
//			if (obj instanceof Date)
//			{
//				sb.append("'").append(obj).append("'");
//			}
//
//			//最後は,付けないで)つける
//			count++;
//			if (count != o.length)
//			{
//				sb.append(",");
//			}
//			else
//			{
//				sb.append(")");
//			}
//		}
//
//		ValuesPart = sb.toString();
//		ValuesFlg = true;
//		return this;
//	}

	/**
	 *　アップデート文使うとき用のメソッド　ここだけSQLライクじゃなくて申し訳ない
	 * @param tableName TableName.テーブル名
	 * @param clum CalamName.セットしたいカラム名
	 * @return this
	 */
	public CreateSQL Update(TableList tableName, CalamName... clum)
	{
		this.UpdatePart += "UPDATE " + tableName + " SET ";
		calamList = new ArrayList<CalamName>();
		for (CalamName c : clum)
		{
			calamList.add(c);
		}
		UpdateFlg = true;
		return this;
	}

	/**
	 *アップデート文使うとき用のメソッド　ここだけSQLライクじゃなくて申し訳ない
	 * @param tableName TableName.テーブル名
	 * @param clum clumNameList.セットしたいカラム名LIST String
	 * @return this
	 */
	public CreateSQL Update(String tableName,List<String> clumNameList)
	{
		this.UpdatePart += "UPDATE " + tableName + " SET ";
		stringCalamList = new ArrayList<String>();
		for (String clumName : clumNameList)
		{
			stringCalamList.add(clumName);
		}
		UpdateFlg = true;
		useStringCulumListFlg = true;
		return this;
	}

//  一旦コメントアウト TODO ここも対応する
//	/**
//	 * Updateの後のＳｅｔ で使う用　入れたい値を順番通りに入れていく
//	 * @param value
//	 * @return
//	 */
//	public CreateSQL Set(Object... value)
//	{
//		setValueList = new ArrayList<Object>();
//		for (Object o : value)
//		{
//			//Listだったらnosyori
//			if(o.getClass()s == List.class)
//			{
//				for()
//				{
//
//				}
//			}
//			setValueList.add(o);
//		}
//		return this;
//	}

	/**
	 * Updateの後のＳｅｔ で使う用 値の入ったリスト順にセットしていく
	 * @param value
	 * @return this
	 */
	public CreateSQL Set(List<Object> ValueList)
	{
		setValueList = new ArrayList<Object>();
		for(Object value: ValueList){
			setValueList.add(value);
		}

		return this;
	}

	/**
	 * デリート文用
	 * @return
	 */
	public CreateSQL Delete()
	{
		DeletePart += "DELETE";
		DeleteFlg = true;
		return this;
	}

	/**
	 * 自信の持つすべてのパートのフラグをふぉるすにする
	 */
	private void AllflgFalse()
	{
		this.SelectFlg = false;
		this.FromFlg = false;
		this.InsertFlg = false;
		this.UpdateFlg = false;
		this.WhereFlg = false;
		this.DeleteFlg = false;
		this.JoinFlg = false;
		this.OnFlg = false;
		this.useStringCulumListFlg = false;
	}

	/**
	 * 自信の持つすべてのパートの文字列を空にする
	 */
	private void AllPartFormat()
	{
		this.SelectPart = "";
		this.FromPart = "";
		this.FromTable = "";
		this.InsertPart = "";
		this.ValuesPart = "";
		this.UpdatePart = "";
		this.WherePart = "";
		this.DeletePart = "";
		this.JoinPart = "";
		this.JoinTable = "";
		this.OnPart = "";
	}

	/**
	 * 最終な一つのSQLにする
	 * @return String SQL
	 */
	public String GenerateAlltoString()
	{
		//TODO ここ命令ごとに絶対ない組み合わせとか考えなあかん
		//各パートに入れた文を組み合わせる
		String SQL = "";
		StringBuilder sb = new StringBuilder();

		if (SelectFlg)
		{
			sb.append(SelectPart);
		}
		if(DeleteFlg)
		{
			sb.append(DeletePart);
		}

		if (FromFlg)
		{
			sb.append(FromPart);
		}
		//どんな順番で追加していくか考える
		if(JoinFlg)
		{
			sb.append(JoinPart);
		}

		if(OnFlg)
		{
			sb.append(OnPart);
		}

		if (InsertFlg)
		{
			sb.append(InsertPart);
		}

		if (ValuesFlg)
		{
			sb.append(ValuesPart);
		}

		//UpdateのとこUpdateとセットまとめる
		int count = 0;
		sb = updatePartSetting(sb);

		//Where
		if(WhereFlg)
		{
			sb.append(WherePart);
		}

		AllflgFalse();
		AllPartFormat();

		SQL = sb.toString();
		return SQL;
	}

	/**
	 * アップデートの最終的整形
	 * 引数 sb 今まで追加してきたStringBuilder
	 * 戻り値 アップデート文が追加された StringBuilder
	 */
	private StringBuilder updatePartSetting(StringBuilder sb)
	{
		StringBuilder upsb = sb;
		//UpdateのとこUpdateとセットまとめる
		int count = 0;
		if (UpdateFlg)
		{
			upsb.append(UpdatePart);
			if(useStringCulumListFlg)
			{
				for (String c : stringCalamList)
				{
					upsb.append(c).append(" = ");
					//判断用
					System.out.println(setValueList.size() + "size");
					System.out.println(count + "count");

					Object value = setValueList.get(count);
					System.out.println(value);
					Class C = value.getClass();

					String s = "";
					Date d = new Date(0);
					//値がString型もしくはデート型の場合
					if (C == s.getClass() || C == d.getClass())
					{
						upsb.append("'").append(value).append("'");
					}
					else
					{
						upsb.append(value);
					}
					if (count != stringCalamList.size() - 1)
					{
						upsb.append(",");
					}
					count++;
				}
			}
			else{
				for (CalamName c : calamList)
				{
					upsb.append(c).append(" = ");
					//判断用
					Class C = setValueList.get(count).getClass();
					String s = "";
					Date d = new Date(0);
					//値がString型もしくはデート型の場合
					if (C == s.getClass() || C == d.getClass())
					{
						upsb.append("'").append(setValueList.get(count)).append("'");
					}
					else
					{
						upsb.append(setValueList.get(count));
					}
					if (count != calamList.size() - 1)
					{
						upsb.append(",");
					}
					count++;
				}
			}
		}
		return upsb;
	}

}
