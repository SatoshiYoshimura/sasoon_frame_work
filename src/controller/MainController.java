/**
 *
 */
package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import calam.BaseBeans;

/**
 * @author OWNER
 *
 */

@WebServlet("/c/*")
public class MainController extends AppricationController {

		private static final long serialVersionUID = 1L;

	    /**
	     * @see HttpServlet#HttpServlet()
	     */

		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//TODO ここも後で抽象化
			String uri = request.getRequestURI();
			System.out.println(uri);
			String jsp = "";
			if(uri.equals("/kadai08_b_31/c/students/index"))
			{
				StudentsContoroller sc = new StudentsContoroller(request.getServletContext());
				List<BaseBeans> studentList = sc.index();
				System.out.println(studentList);
				jsp ="/WEB-INF/jsp/students/index.jsp";
				request.setAttribute("studentList", studentList);
			}
			else if (uri.equals("/kadai08_b_31/c/index"))
			{
				jsp ="/WEB-INF/jsp/index.jsp";
			}
		    /************* ***************/
			RequestDispatcher rd = request.getRequestDispatcher(jsp);
			System.out.println(jsp);
			rd.forward(request, response);

		}

		/**
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
		}

	}
