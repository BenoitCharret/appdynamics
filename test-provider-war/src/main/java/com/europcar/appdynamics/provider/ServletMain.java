package com.europcar.appdynamics.provider;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class ServletMain extends AbstractServlet {
	
	private static final Logger LOG=Logger.getLogger(ServletMain.class);

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.service(req, resp);
		
		try {
			checkParameterToThrowException(req);
		} catch (MonException e) {
			LOG.error("j'ai trappé mon exception");
		}catch(MonExceptionANePasLoggue e){
			e.printStackTrace();
		}
		//Basic servlet only display path that were called
		PrintWriter pw=resp.getWriter();
		pw.write(req.getRequestURL().toString() + "--> OK");
		
		
				
	}
	
	private void checkParameterToThrowException(HttpServletRequest req) throws IOException,MonException,MonExceptionANePasLoggue{
		if (req.getParameter("launche")!=null){			
			throw new RuntimeException("ca ne va pas du tout");			
		}
		if (req.getParameter("launchs")!=null){
			int value=Integer.parseInt(req.getParameter("launchs"));
			switch (value){
			case 1:
				throw new IOException("je lance une IO");
			case 2:
				LOG.error("je log en erreur");
				break;
			case 3:
				LOG.error("je log en erreur avec une exception", new IOException("exception speciale logger"));
			case 4:	
				throw new MonException("mo exception est lancée");
			case 5:
				LOG.error("je loggue mon exception",new MonException("le log c'est bien"));
			case 6:	
				throw new MonExceptionANePasLoggue("mo exception est lancée");
			case 7:
				LOG.error("je loggue mon exception",new MonExceptionANePasLoggue("le log c'est bien"));
			default:
				break;
			}
		}
	}
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		service(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		service(req, resp);
	}

	
	
}
