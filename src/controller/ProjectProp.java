package controller;

/***
 *
 * @author OWNER
 * プロジェクトの設定を記述するファイル
 */
public class ProjectProp {

	public ProjectProp() {

	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getServletPath() {
		return servletPath;
	}
	public void setServletPath(String servletPath) {
		this.servletPath = servletPath;
	}

	protected String name;
	protected String servletPath;
}
