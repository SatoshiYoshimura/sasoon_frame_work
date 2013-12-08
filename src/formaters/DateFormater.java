package formaters;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormater {

	public DateFormater() {
	}

	/**
	 * 引数に与えた文字列をsql.date型にして返す
	 * @param String string_date
	 * @return sql.Date
	 */
	public java.sql.Date stringToSqlDate(String string_date)
	{
		java.util.Date date = null;
		date = this.stringToUtilDate(string_date);

		java.sql.Date sqlDate = new java.sql.Date(date.getTime());

		System.out.println("sql" + sqlDate);

		return sqlDate;
	}

	/**
	 * 引数に与えた文字列をutil.dateにして返す
	 * @param String string_date
	 * @return util.Date
	 */
	public java.util.Date stringToUtilDate(String string_date)
	{
		System.out.println("string" + string_date);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		//TODO utildateへの変換 今はHTML5のdateタグを使ってるからうまく言ってるだけ
//		format.setLenient( true );
//		format.applyPattern("yyyy/MM/dd");

		java.util.Date date = null;
		try {
			date = format.parse(string_date);
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.out.println("文字をutildateに変換できない");
		}

		System.out.println( "util" + date);

		return date;

	}

}
