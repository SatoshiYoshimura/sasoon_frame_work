package test;

public class chai {

	public chai() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public chai chain()
	{
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		return this;
	}

	String gMethodName()
	{
		return Thread.currentThread().getStackTrace()[1].getMethodName();
	}

	String gClassName()
	{
		return Thread.currentThread().getStackTrace()[1].getClassName();
	}
	
	
}
