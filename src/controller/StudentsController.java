package controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import calam.BaseBeans;
import calam.Students;
import calam.error.IdNullException;

public class StudentsController extends AppricationController {

	public StudentsController(ServletContext context) {
		contextParam = context;
	}

	public StudentsController(MainController mainC) {
		contextParam = mainC.context;
		queryList = mainC.queryList;
	}

	public StudentsController() {

	}

	//一覧表示
	//URL//http://localhost:8080/kadai08_b_31/c/students/index
	public void index()
	{
		//インスタンス化するときにコンテキストパラムを与えないとサーブレットでは動かない
		Students student = new Students(contextParam);
		//findAllでリスト取得
		List<BaseBeans> studentList = student.findAll();

		//contextParam.setAttribute()でセットアトリビュートできる
		contextParam.setAttribute("studentList", studentList);
	}

	//個別表示
	//URL//http://localhost:8080/kadai08_b_31/c/students/show
	public void show(){
		//インスタンス化するときにコンテキストパラムを与えないとサーブレットでは動かない
		Students student = new Students(contextParam);
		//個別findはキャストしてモデルに代入
		//引き数に与えるのはidのみ
		student = (Students) student.find(2);

		//contextParam.setAttribute()でセットアトリビュートできる
		contextParam.setAttribute("student", student);
	}

	//削除
	//URL//http://localhost:8080/kadai08_b_31/c/students/delete
	public void delete()
	{
		//インスタンス化するときにコンテキストパラムを与えないとサーブレットでは動かない
		Students student = new Students(contextParam);
		//idをセットしないとエラーをはく
		//セットしたidのデータが消えます
		student.setId(2);
		try {
			//id=2の人が消えた
			student.delete();
		} catch (IdNullException e) {
			e.printStackTrace();
		}
	}

	//追加
	//URL//http://localhost:8080/kadai08_b_31/c/students/insert
	public void insert()
	{
		//インスタンス化するときにコンテキストパラムを与えないとサーブレットでは動かない
		Students student  = new Students(contextParam);
		//ID以外の全てのカラムに値をセットしないと追加されない仕様
		student.setName("無敵");
		student.setClassName("IH13A");
		student.setStNum("22");
		student.insert();
	}

	//更新
	//URL//http://localhost:8080/kadai08_b_31/c/students/update
	public void update()
	{
		//インスタンス化するときにコンテキストパラムを与えないとサーブレットでは動かない
		Students student = new Students(contextParam);
		for(HashMap h:queryList){
			//クエリストリングからidセット
			student.setId(Integer.parseInt((String) h.get("id")));
		}
		//モデルのnameにうんこセット
		student.setName("うんこ");
		try {
			//これでnameがうんこに更新される
			student.update();
		} catch (IdNullException e) {
			e.printStackTrace();
		}
	}

}
