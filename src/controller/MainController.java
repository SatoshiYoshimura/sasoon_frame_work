/**
 *
 */
package controller;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edit.string.StringManager;

/**
 * @author OWNER
 *
 */

@WebServlet("/c/*")
public class MainController extends ServletController {

		private static final long serialVersionUID = 1L;
		public ServletContext context;
	    public ArrayList<HashMap> queryList = null;
		/**
	     * @see HttpServlet#HttpServlet()
	     */

		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String uri = request.getRequestURI();
			String jsp = "";

			//プロジェクト名取得
			//サーブレットパス取得
			//TODO サーブレットパス使う場合とそうでない場合

	        context = request.getServletContext();
			ProjectProp PP = this.getProjecProp(context);

			//ここからURLを削っていく
			StringManager stringM = new StringManager();
			String tmpUri = stringM.deleteProperty(uri, PP.name);
			if(PP.servletPath != null){
				tmpUri = stringM.deleteProperty(tmpUri, PP.servletPath);
			}
			//コントローラ名取得
			String contName = "";
			contName = stringM.getControllerName(tmpUri);
			//ディスパッチ用に小文字も取っておく
			String folderName = contName;
			//最初大文字に
			contName = stringM.FirstUpper(contName);
			contName += "Controller";

			//アクション名取得
			String actionName = "";
			actionName = stringM.getActionName(tmpUri);

			//クエリストリング取得
			queryList = new ArrayList<HashMap>();
			String query = request.getQueryString();
			if(stringM.allRightNullorPlane(query)){
				//ハッシュマップに変換
				queryList = stringM.queryToHashList(query);
			}


			//URLに対応したコントローラインスタンス化
			AppricationController appController = null;
			try {
				Class<?> Ap = Class.forName("controller."+contName);
				try {
					Class<?>[] types = { this.getClass() };
					Constructor<?> constructor = null;
					try {
						constructor = Ap.getConstructor(types);
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
					Object args =  this ;
					System.out.println("これ渡してる："+args);
					try {
						appController = (AppricationController) constructor.newInstance(args);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			//アクションの実行
			Method method;
			try {
				System.out.println("アクション名："+actionName);
				method = appController.getClass().getMethod(actionName, null);
			} catch (SecurityException e) {
				throw new RuntimeException(e);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e);
			}

			Object ret; //戻り値
			try {
				ret = method.invoke(appController, null);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			}

			//動的にディスパッチャーを割り当てる
			StringBuilder stringB = new StringBuilder();
			stringB.append("/WEB-INF/jsp");
			stringB.append(tmpUri);
			stringB.append(".jsp");
			jsp = stringB.toString();

			/************* ***************/
			RequestDispatcher rd = request.getRequestDispatcher(jsp);
			rd.forward(request, response);

		}

		/**
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
		}

		/**
		 * プロジェクト情報取得
		 * @param context
		 * @return
		 */
		private ProjectProp getProjecProp(ServletContext context){
			LoadProjectProp LPP = new LoadProjectProp();
	        String proppass = context.getRealPath("WEB-INF/Properties/Project.properties");
			LPP.load(proppass);
			ProjectProp PP = LPP.giveProjectProp();

			return PP;
		}

	}
