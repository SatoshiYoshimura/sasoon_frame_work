package controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

public class AppricationController extends HttpServlet {

	protected ServletContext contextParam;
	//クエリストリング格納用
	protected ArrayList<HashMap> queryList;
	public AppricationController() {

	}

	public AppricationController(ServletContext context){
		contextParam = context;
	}


}