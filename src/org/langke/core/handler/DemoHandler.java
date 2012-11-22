package org.langke.core.handler;

import org.apache.commons.lang.math.RandomUtils;
import org.langke.common.bdb.BrekeleyDB;
import org.langke.common.server.Handler;
import org.langke.common.server.NettyHttpRequest;
import org.langke.common.server.resp.Resp;
import org.langke.common.server.resp.RespData;
import org.langke.core.server.SpringApplicationContext;

public class DemoHandler implements Handler{

	BrekeleyDB bdb = (BrekeleyDB) SpringApplicationContext.getInstance().getService("brekeleyDB");
	@Override
	public Resp handleRequest(NettyHttpRequest request) {
		RespData data = new RespData();
		data.setContent(request.params());
		String label = request.param("label","");
		if(label.equalsIgnoreCase("bdb")){
			data.setContent(bdbTest(request.param("key","key"), request.param("value","value")));
		}
		return new Resp(data);
	}

	/**
	 * http://localhost:9090/demo/bdb?key=value
	 * @param key
	 * @param value
	 * @return
	 */
	private Object bdbTest(String key,String value){
		int random = RandomUtils.nextInt();
		bdb.put(key+random, value+random);
		return bdb.get(10);
	}
}
