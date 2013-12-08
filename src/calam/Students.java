package calam;

import javax.servlet.ServletContext;


public class Students extends BaseBeans {

//	/**
//	 * baseBeansのコンスト
//	 * @param context
//	 * @param callingClass
//	 */
	public Students(ServletContext context) {
		super(context);
	}
//
//	/***
//	 * baseBeansのコンスト
//	 * @param callingClass
//	 */
//	public Students(String callingClass) {
//		super();
//	}



	public Students() {

	}


	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getStNum() {
		return stNum;
	}
	public void setStNum(String stNum) {
		this.stNum = stNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String className;
	private String stNum;
	private String name;
	private int id;
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}
}