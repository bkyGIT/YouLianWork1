package com.yl.java;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class BrowseClient {
	
	public static void main(String[] args) {
		
		try {
			URI uri = new URI("http://111.26.164.245:9090/csc/login.jsp");
			Desktop desktop = null;
			if(Desktop.isDesktopSupported()){
				desktop = Desktop.getDesktop();
			}
			if(desktop != null){
				desktop.browse(uri);
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
