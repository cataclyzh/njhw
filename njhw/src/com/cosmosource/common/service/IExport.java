package com.cosmosource.common.service;

import java.io.OutputStream;
import java.util.Map;

import com.cosmosource.common.entity.TAcCaapply;

public interface IExport {

	public void setDictMap(Map<String, Map<String, String>> dictMap);
	
	public boolean export(TAcCaapply apply, OutputStream os);

}
