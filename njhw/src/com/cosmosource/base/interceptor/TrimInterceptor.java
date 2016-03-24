/**
* <p>文件名: TrimInterceptor.java</p>
* <p>描述：去掉空格的拦截器</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2011-02-22 下午07:01:02 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.base.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
/**
* @类描述: 提交表单时输入框的值去掉前后空格
* @作者： WXJ
*/
public class TrimInterceptor extends MethodFilterInterceptor {   
  
	private static final long serialVersionUID = 8824436445846826668L;
	private List<String> excluded = new ArrayList<String>();   
    @Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {   
        Map<String, Object> parameters = invocation.getInvocationContext().getParameters();   
        if(parameters!=null){
            for (String param : parameters.keySet()) {   
                if (isIncluded(param)) {   
                    Object[] vals = (Object[]) parameters.get(param); 
                    if(vals!=null){
                        boolean allNull = true;   
                        for (int i = 0; i < vals.length; i++) {   
                        	if(vals[i] instanceof String){
                        		vals[i] = StringUtils.trimToEmpty((String)vals[i]); 
                        		allNull = allNull && (vals[i] == null);   
                        	}
                        }   
                        if(allNull){   
                            parameters.put(param, null);   
                        }  	
                    }
                }   
            }   
        }
        return invocation.invoke();   
    }   
    private boolean isIncluded(String param) {   
        for (String exclude : excluded) {   
            if (param.startsWith(exclude)) {   
                return false;   
            }   
        }   
        return true;   
    }   
    public void setExcludedParams(String excludedParams) {   
        for (String s : StringUtils.split(excludedParams, ",")) {   
            excluded.add(s.trim());   
        }   
    }
}   