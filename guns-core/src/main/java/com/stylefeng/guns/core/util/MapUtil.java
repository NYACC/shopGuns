package com.stylefeng.guns.core.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapUtil {

	private static String KEY = "e5562a338377e9f16a826f8c1638cb87";
	private static Pattern pattern = Pattern.compile("\"location\":\"(\\d+\\.\\d+),(\\d+\\.\\d+)\"");

	public static double[] addressToGPS(String address) {
		try {
			String url = String.format("http://restapi.amap.com/v3/geocode/geo?&s=rsv3&address=%s&key=%s", address,
					KEY);
			URL myURL = null;
			URLConnection httpsConn = null;
			try {
				myURL = new URL(url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			InputStreamReader insr = null;
			BufferedReader br = null;
			httpsConn = (URLConnection) myURL.openConnection();// 不使用代理
			if (httpsConn != null) {
				insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
				br = new BufferedReader(insr);
				String data = "";
				String line = null;
				while ((line = br.readLine()) != null) {
					data += line;
				}
				Matcher matcher = pattern.matcher(data);
				if (matcher.find() && matcher.groupCount() == 2) {
					double[] gps = new double[2];
					gps[0] = Double.valueOf(matcher.group(1));
					gps[1] = Double.valueOf(matcher.group(2));
					return gps;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	@SuppressWarnings("unused")
	public static double getDistance(double lat1, double lng1, double lat2, double lng2)
	{
	    double distance = 0;
	    double lonRes = 102900, latRes = 110000;
	    distance = Math.sqrt( Math.abs( lat1 - lat2 ) * latRes * Math.abs( lat1 - lat2 ) * latRes +
	            Math.abs( lng1 - lng2 ) * lonRes * Math.abs( lng1 - lng2 ) * lonRes );
	    //System.out.println( "两点间距离:" + distance );
	    return distance;
	}
}
