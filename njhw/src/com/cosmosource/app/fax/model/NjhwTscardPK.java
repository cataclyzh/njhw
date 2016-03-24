 
/** 
 * 文件名：NjhwTscardPK.java 
 * 
 * 版本信息： 
 * 日期：2013-8-7 
 * Copyright 足下 Corporation 2013 
 * 版权所有 
 * 
 */ 
    
package com.cosmosource.app.fax.model;

import java.io.Serializable;

 
/** 
 * 此类描述的是： 
 * @author: zhangquanwei
 * @version: 2013-8-7 下午04:17:44 
 */
/**
 * 用户角色表的复合主键
 * @author Michael sun
 */
public class NjhwTscardPK implements Serializable {

    public NjhwTscardPK() {

    }

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4901479789268752591L;

    /**
     * 用户名
     */
    private Long userId;

    /**
     * 角色ID
     */
    private String cardId;

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @return the cardId
     */
    public String getcardId() {
        return cardId;
    }

    /**
     * @param pUserId the userId to set
     */
    public void setUserId(Long pUserId) {
        userId = pUserId;
    }

    /**
     * @param pcardId the cardId to set
     */
    public void setcardId(String pcardId) {
        cardId = pcardId;
    }

    /**
     * overrides hashCode()
     * @return int
     */
    public int hashCode() {
        int result;
        result = userId.hashCode();
        result = 29 * result + cardId.hashCode();
        return result;
    }

    /**
     * overrides equals
     * @see java.lang.Object#equals(java.lang.Object)
     */

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj) {
            return false;
        }
        if (!(obj instanceof NjhwTscardPK)) {
            return false;
        }

        final NjhwTscardPK pko = (NjhwTscardPK) obj;
        if (!userId.equals(pko.userId)) {
            return false;
        }
        if (null == cardId || cardId != pko.cardId) {
            return false;
        }
        return true;
    }

}



