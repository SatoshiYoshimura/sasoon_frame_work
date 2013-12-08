/**
 *
 */
package db.base;

/**
 * @author OWNER
 *接続用のプロパティのビーンズ
 */
public class DBProp {

	/**
	 *
	 */
	public DBProp() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public int getJndiFlag() {
		return jndiFlag;
	}

	public void setJndiFlag(int jndiFlag) {
		this.jndiFlag = jndiFlag;
	}


	protected String url;
	protected String driver;
	protected String user;
	protected String pass;
	protected int jndiFlag;

}
