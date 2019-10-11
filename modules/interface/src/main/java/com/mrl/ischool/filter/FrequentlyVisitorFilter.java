package com.mrl.ischool.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class FrequentlyVisitorFilter implements Filter{

	public static long INTERVAL= 10000; //默认5秒钟
	public static int MAXCOUNT = 2;//默认最大访问次数
	public static String VISITORINFO = "visitor_info";
	public static String FREQUENTLYVISIT = "Frequently visits,please wait 10s";

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//---------------A filter to prevent malicious refresh-------- 
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		String ip = request.getRemoteAddr(); //Get the client IP address
		Date now =new Date();
		Visitor vis = (Visitor) req.getSession().getAttribute(VISITORINFO);
		if(vis!= null) {//If the visitor is found, the user is revisited
			//Less than 10 seconds, but access more than 10 times
			vis.getRequestTimeQueue().insert(now.getTime());//Insert the current request time
			//The difference between the last and the first time 
			Long span = vis.getRequestTimeQueue().getLast() - vis.getRequestTimeQueue().getFirst();
			if(span < INTERVAL && vis.getRequestTimeQueue().getLast() != 0) {
				System.out.println("not pass:"+ip);
				//Jump to nopass.jsp
//				res.sendRedirect("./views/nopass.jsp");
				PrintWriter out = res.getWriter();
				out.print(FREQUENTLYVISIT);
				out.flush();
				out.close();
			}else {
				System.out.println("pass:"+ip);
				chain.doFilter(request, response);
			}
		} else {
			//The current visitor is the first visit
			ArrayTime timeQueue = new ArrayTime();
			timeQueue.setLength(MAXCOUNT);
			timeQueue.init();
			vis=new Visitor();
			vis.setIp(ip);
			vis.setRequestTimeQueue(timeQueue);
			vis.getRequestTimeQueue().insert(now.getTime());
			req.getSession().setAttribute(VISITORINFO, vis);
			System.out.println("new visitor:"+ip);
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
