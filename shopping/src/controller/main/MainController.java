package controller.main;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.goods.GoodsListPage;

public class MainController extends HttpServlet 
	implements Servlet{
	public void doProcess(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(
				contextPath.length());
		/// uri = /shopping/index.html
		///       0123456789
		/// context = /shopping
		///           123456789
		if(command.equals("/main.sm")) {
			GoodsListPage action = new GoodsListPage();
			action.goodsList(request);
			RequestDispatcher dispatcher =
					request.getRequestDispatcher("main/home.jsp");
			dispatcher.forward(request, response);
		}else if(command.equals("/login.sm")) {
			LoginPage action = new LoginPage();
			action.login(request,response);
			response.sendRedirect("main.sm");
		}else if(command.equals("/logout.sm")) {
			Cookie cookie = new Cookie("autoLogin","");
			cookie.setPath("/");
			cookie.setMaxAge(0); //로그아웃하면 자동로그인 캐쉬 삭제
			response.addCookie(cookie);//유저 컴퓨터 웹브러우저에 쿠키 전달
			
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("main.sm");
		}
		
				
	}
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req,resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req,resp);
	}
}