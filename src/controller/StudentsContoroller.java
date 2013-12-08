package controller;

import java.util.List;

import javax.servlet.ServletContext;

import calam.BaseBeans;
import calam.Students;

public class StudentsContoroller extends AppricationController {

	ServletContext contextParam;
	public StudentsContoroller(ServletContext context) {
		contextParam = context;
	}

	public List<BaseBeans> index()
	{
		Students student = new Students(contextParam);
		System.out.println(student);
		List<BaseBeans> studentList = student.findAll();
		System.out.println(studentList);
		return studentList;
	}

	public void delete(int id)
	{
		Students student = new Students(contextParam);
		student.setId(id);
		student.delete();
	}

	public void insert(Students st)
	{
		Students student  = st;
		student.insert();
	}

	public void update(Students st)
	{
		Students student = st;
		st.update();
	}

}
