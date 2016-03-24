package com.cosmosource.app.port.serviceimpl;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cosmosource.app.entity.ExtInBlacklist;
import com.cosmosource.app.entity.NjhwTscard;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.port.model.PersonNotRightCard;
import com.cosmosource.app.transfer.serviceimpl.PersonCardQueryToSystemService;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.util.PropertiesUtil;
import com.cosmosource.base.util.StringUtil;

/**
 * @ClassName:PersonCardQueryToAppService
 * @Description：市民卡接口实现
 * @Author：hp
 * @Date:2013-3-18
 */
public class PersonCardQueryToAppService extends BaseManager{
	
	private PersonCardQueryToSystemService personCardQueryToSystemService;
	private static final Log log = LogFactory.getLog(PersonCardQueryToAppService.class);

	private static final String TRADETYPE = "03";//行业类型
	private static final String OPTYPE = "01";//行业类型
	private static final String COMPANYID = "80000000001";//公司代码
	private static final String STATIONID = "8010001";//网点代码
	private static final String TERMID = "80010001";//终端号
	private static final String OPERATORID ="11111"; //操作员号
	
	
	/**
	* @Description：查询市民卡信息
	* @Author：hp
	* @Date：2013-3-19
	* @param card
	* @return
	* @throws Exception
	**/
	public NjhwTscard queryPersonCard(String card,String orgId) throws Exception {
		if (card == null) {
			log.info("市民卡号为空!!");
			return null;
		}
		String AliasCardNo = card.trim();//卡面号
		String now = getDateStr();//现在的时间
		//这里整理解析结果值放在数据库中或是返回给tcard对象
		NjhwTscard njhwTscard = null;
		try {
			String paramCard = buildPersonCardParam(now);
			paramCard = paramCard+",AliasCardNo="+AliasCardNo+",QueryTime="+now;
			String cardInfo = personCardQueryToSystemService.queryPersonCardInfo(paramCard);
			njhwTscard = parseToEntity(parsePersonCardResult(cardInfo),orgId);
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return njhwTscard;
	}
	
	
	/**
	* @Description：查询市民卡信息
	* @Author：hp
	* @Date：2013-3-19
	* @param card
	* @return
	* @throws Exception
	**/
	public NjhwTscard queryPersonCard(String card) throws Exception {
		if (card == null) {
			log.info("市民卡号为空!!");
			return null;
		}
		String AliasCardNo = card.trim();//卡面号
		String now = getDateStr();//现在的时间
		//这里整理解析结果值放在数据库中或是返回给tcard对象
		NjhwTscard njhwTscard = null;
		try {
			String paramCard = buildPersonCardParam(now);
			paramCard = paramCard+",AliasCardNo="+AliasCardNo+",QueryTime="+now;
			String cardInfo = personCardQueryToSystemService.queryPersonCardInfo(paramCard);
			njhwTscard = parseToEntity(parsePersonCardResult(cardInfo));
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return njhwTscard;
	}
	
	public Map queryPersonCardForVisitor(String card) throws Exception {
		if (card == null) {
			log.info("市民卡号为空!!");
			return null;
		}
		String AliasCardNo = card.trim();//卡面号
		String now = getDateStr();//现在的时间
		String paramCard = buildPersonCardParam(now);
		paramCard = paramCard+",AliasCardNo="+AliasCardNo+",QueryTime="+now;
		String cardInfo = personCardQueryToSystemService.queryPersonCardInfo(paramCard);
		
		Map result = parsePersonCardResult(cardInfo);
		
		if(result != null){
			List<NjhwTscard> list = dao.findByProperty(NjhwTscard.class,"cardId", card);
			
			if(list != null && list.size() > 0){
				NjhwTscard njhwCard = list.get(0);
				result.put("userDiff", njhwCard.getUserDiff());
			}
		}
		
		return result;
	}
	
	public Map queryPersonCardForLost(String card) throws Exception {
		if (card == null) {
			log.info("市民卡号为空!!");
			return null;
		}
		String AliasCardNo = card.trim();//卡面号
		String now = getDateStr();//现在的时间
		String paramCard = buildPersonCardParam(now);
		paramCard = paramCard+",AliasCardNo="+AliasCardNo+",QueryTime="+now;
		String cardInfo = personCardQueryToSystemService.queryPersonCardInfo(paramCard);
		Map result = parsePersonCardResult(cardInfo);
		return result;
	}
	
	/**
	* @Description：查询市民卡信息
	* @Author：hp
	* @Date：2013-3-19
	* @param card
	* @return
	* @throws Exception
	**/
	public String queryPersonCardForPubCard(String card) throws Exception {
		if (card == null) {
			log.info("市民卡号为空!!");
			return null;
		}
		String AliasCardNo = card.trim();//卡面号
		String now = getDateStr();//现在的时间
		//这里整理解析结果值放在数据库中或是返回给tcard对象
		String cardInfo = new String();
		try {
			String paramCard = buildPersonCardParam(now);
			paramCard = paramCard+",AliasCardNo="+AliasCardNo+",QueryTime="+now;
			cardInfo = personCardQueryToSystemService.queryPersonCardInfo(paramCard);
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return cardInfo;
	}
	
	
	
	
	/**
	* @Description：查询市民卡对应的图片
	* @Author：hp
	* @Date：2013-4-18
	* @param card
	**/
	public void queryPersonMap(String card){
		try {
			NjhwTscard njhwTscard = (NjhwTscard) this.findByHQL("from NjhwTscard t where t.cardId = ?", card.trim());
			if(njhwTscard != null){
				String paramCard = buildPersonCardParam(getDateStr());
				//从对象中得到所需信息，这里需要修改，因为库表字段更改过，类的字段对应不上
				paramCard = paramCard +",CustType="+njhwTscard.getCardType()+",CustCode="+njhwTscard.getCustcode()+",AliasCardNo="+njhwTscard.getCardId()+",QueryTime="+getDateStr();
				byte[] b = personCardQueryToSystemService.queryPersonCardMap(paramCard);
				savePhoteToServer(njhwTscard,b);
			}
		} catch (NoSuchAlgorithmException e) {
			log.info(e.getMessage()+"=======市民卡查询图片");
			e.printStackTrace();
		} catch (RemoteException e) {
			log.info(e.getMessage()+"=======市民卡查询图片");
			e.printStackTrace();
		} catch (MalformedURLException e) {
			log.info(e.getMessage()+"=======市民卡查询图片");
			e.printStackTrace();
		} catch (ServiceException e) {
			log.info(e.getMessage()+"=======市民卡查询图片");
			e.printStackTrace();
		}
	}

	/**
	* @Description：组合市民参数值
	* @Author：hp
	* @Date：2013-3-19
	* @param card
	* @return
	* @throws NoSuchAlgorithmException
	**/
	public String buildPersonCardParam(String QueryTime) throws NoSuchAlgorithmException {
		String text = TRADETYPE+OPTYPE+"001"+"001"+QueryTime;
		String ValidateCode = Md5(text);//验证码 md5加密
		String buildStr = "tradetype="+TRADETYPE+",optype="+OPTYPE+",companyid="+COMPANYID+",stationid="+STATIONID+",termid="+TERMID+",operatorid="+OPERATORID+",validatecode="+ValidateCode;
		return buildStr;
	}

	
	/**
	* @Description：解析查询结果值
	* @Author：hp
	* @Date：2013-4-11
	* @param resultXml
	* @return
	**/
	@SuppressWarnings("unchecked")
	public Map parsePersonCardResult(String resultXml){//这个地方有可能只返回几个数字
		String status = resultXml.substring(0,4);
		Map map = null;
		if("0000".equals(status)){
			map = new HashMap();
			map.put("errorCode", status);
			String result = resultXml.substring(5,resultXml.length());
			String[] results = result.split(",");
			for (String res : results) {
				String[] result2 = res.split(":");
				String key = result2[0]==""?"":result2[0];
				String value = result2[1]==""?"":result2[1];
				map.put(key, value);
			}
		}
		
		return map;
	}
	
	
	/**
	* @Description：保存字节流到指定的地址中
	* @Author：hp
	* @Date：2013-4-18
	* @param b
	**/
	public void savePhoteToServer(NjhwTscard njhwTscard,byte[] b){
		String photoPath = PropertiesUtil.getAnyConfigProperty("dir.personcard.photo", PropertiesUtil.NJHW_CONFIG);
		try {
			FileUtils.writeByteArrayToFile(new File(photoPath+njhwTscard.getCardId()+".jpg"), b);
			//更新库表
			njhwTscard.setUserPhoto(photoPath);
			dao.update(njhwTscard);
		} catch (IOException e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	* @Description：解析返回值map放在市民卡对象中
	* @Author：hp
	* @Date：2013-4-11
	* @param map
	* @return
	**/
	@SuppressWarnings("unchecked")
	public NjhwTscard parseToEntity(Map map,String orgId){
		if(map == null){
			log.info("传递的map为空!");
			return null;
		}
		
		// 查询跟单位的id 
		Map<String,String> pMap =new HashMap<String, String>();
		pMap.put("orgId", orgId);
		
		List<Map> listOrg = this.findListBySql("PersonnelSQL.getRootOrgId", pMap);
		
		NjhwTscard card = new NjhwTscard();
		
		if(null != listOrg && listOrg.size()>0)
		{
		    List<NjhwTscard> cardList = dao.findByHQL("Select  t  from NjhwTscard t where cardId = ? and cardExp1=?",  map.get("AliasCardNo").toString(), String.valueOf(listOrg.get(0).get("ROOTORGID")));
			if(null != cardList && cardList.size()> 0 )
			{
				card  = cardList.get(0);
			}
			else
			{
				List<NjhwUsersExp> userList =dao.findByHQL("select t from  NjhwUsersExp t where t.tmpCard=?", map.get("AliasCardNo").toString());
				if(null != userList && userList.size()> 0)
				{
					card = new NjhwTscard();
					// 110 不可能存在的userid 避免校验本身
					card.setUserId(userList.get(0).getUserid());
				}
			}
		}
		
		
		//下面就是把返回的map对应到类中返回
		card.setCardId(map.get("AliasCardNo").toString());//市民卡面号
		card.setSocitype(StringUtil.nullToEmpty(map.get("SociType")));//证件类型
		card.setResidentNo(StringUtil.nullToEmpty(map.get("SociCode")));//证件号码
		card.setCardType(map.get("CustType").toString());//市民卡类型
		card.setCustcode(map.get("CustCode").toString());//市民卡内号
		card.setUserName(map.get("CustName").toString());//用户姓名
		card.setCustsex(map.get("CustSex").toString());//性别
//		card.setDomaddr(map.get("Domaddr").toString());//户籍地址
//		card.setComaddr(map.get("Comaddr").toString());//居住地址
//		card.setMoblie(map.get("Moblie").toString());//手机号码
		card.setPuicstatus(map.get("PuicStatus").toString());//通卡状态
		card.setCardstatus(map.get("CardStatus").toString());//卡状态
		card.setCardLosted(map.get("LoseStatus").toString());//挂失状态
//		dao.saveOrUpdate(card);
		return card;
	}
	
	
	/**
	* @Description：解析返回值map放在市民卡对象中
	* @Author：hp
	* @Date：2013-4-11
	* @param map
	* @return
	**/
	@SuppressWarnings("unchecked")
	public NjhwTscard parseToEntity(Map map){
		if(map == null){
			log.info("传递的map为空!");			
			return null;
		}
		NjhwTscard card =null;
		List<NjhwTscard> list =super.dao.findByProperty(NjhwTscard.class,"cardId", map.get("AliasCardNo").toString());
		if(list!=null && list.size()>=1){
			card = (NjhwTscard)list.get(0);
		} 
		if (card == null)  card = new NjhwTscard();
		//下面就是把返回的map对应到类中返回
		card.setCardId(map.get("AliasCardNo").toString());//市民卡面号
		card.setSocitype(map.get("SociType").toString());//证件类型
		card.setResidentNo(map.get("SociCode").toString());//证件号码
		card.setCardType(map.get("CustType").toString());//市民卡类型
		card.setCustcode(map.get("CustCode").toString());//市民卡内号
		card.setUserName(map.get("CustName").toString());//用户姓名
		card.setCustsex(map.get("CustSex").toString());//性别
//		card.setDomaddr(map.get("Domaddr").toString());//户籍地址
//		card.setComaddr(map.get("Comaddr").toString());//居住地址
//		card.setMoblie(map.get("Moblie").toString());//手机号码
		card.setPuicstatus(map.get("PuicStatus").toString());//通卡状态
		card.setCardstatus(map.get("CardStatus").toString());//卡状态
		card.setCardLosted(map.get("LoseStatus").toString());//挂失状态
//		dao.saveOrUpdate(card);
		return card;
	}
	
	/**
	* @Description：得到时间字符串
	* @Author：hp
	* @Date：2013-4-18
	* @return
	**/
	public String getDateStr(){
		// 得到当前时间
		Date time = new Date();
		String now = new SimpleDateFormat("yyyyMMddHHmmss").format(time);
		return now;
	}
	
	
	/**
	* @Description：MD5加密市民卡参数中的值
	* @Author：hp
	* @Date：2013-3-19
	* @param plainText
	* @return
	* @throws NoSuchAlgorithmException
	**/
	public String Md5(String plainText) throws NoSuchAlgorithmException {
		StringBuffer buf = new StringBuffer();
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(plainText.getBytes());
		byte b[] = md.digest();
		int i;
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
//			System.out.println("result: " + buf.toString());// 32位的加密
//			System.out.println("result: " + buf.toString().substring(8, 24));// 16位的加密
		return buf.toString();
	}
	
	
	
	/**
	* @throws ParseException 
	 * @Description：读取.flg文件中黑名单的头
	* @Author：hp
	* @Date：2013-5-4
	**/
	public PersonNotRightCard readCardHeader(String filepath) throws ParseException{
		FileInputStream fis = null;
		PersonNotRightCard card = new PersonNotRightCard();
		try {
			fis = new FileInputStream(new File(filepath));
			DataInputStream dis = new DataInputStream(fis);
			byte[] b = new byte[20];
			// 数据类型
			dis.read(b, 0, 2);
			String dataType = new String(b, 0, 2);
			card.setDataType(dataType);
			// 黑名单数量
			dis.read(b, 0, 6);
			String dataCount = new String(b, 0, 6);
			card.setDataCount(dataCount);
			// 版本号
			dis.read(b, 0, 8);
			String version = new String(b, 0, 8);
			card.setVersion(version);
			// 时间
			dis.read(b, 0, 14);
			String dataTime = new String(b, 0, 14);
			card.setDataTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dataTime));
			// 记录类型
			dis.read(b, 0, 2);
			String infoType = new String(b, 0, 2);
			card.setInfoType(infoType);
			// 记录流水号
			dis.read(b, 0, 6);
			String record = new String(b, 0, 6);
			card.setRecord(record);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return card;
	}

	
	/**
	* @Description：从.flg黑名单文件中读取十六进制的数据
	* @Author：hp
	* @Date：2013-5-4
	* @return
	* @throws IOException
	**/
	public String readOriginal2Hex(String filepath) throws IOException {
		FileInputStream fin = new FileInputStream(new File(filepath));
		StringWriter sw = new StringWriter();

		int len = 1;
		byte[] temp = new byte[len];

		/* 16进制转化模块 */
		for (; (fin.read(temp, 0, len)) != -1;) {
			if (temp[0] > 0xf && temp[0] <= 0xff) {
				sw.write(Integer.toHexString(temp[0]));
			} else if (temp[0] >= 0x0 && temp[0] <= 0xf) {
				// 对于只有1位的16进制数前边补“0”
				sw.write("0" + Integer.toHexString(temp[0]));
			} else { 
				// 对于int<0的位转化为16进制的特殊处理，因为Java没有Unsigned int，所以这个int可能为负数
				sw.write(Integer.toHexString(temp[0]).substring(6));
			}
		}
		return sw.toString().toUpperCase();
	}
	
	
	/**
	* @Description：得到黑名单中卡号
	* @Author：hp
	* @Date：2013-5-5
	* @param filePath
	* @return
	* @throws IOException
	**/
	public List<String> getNotRightPersonCard(String filePath,int counts) throws IOException{
		String hex = readOriginal2Hex(filePath);
		String result= hex.substring(92,hex.length());
		int temp = 0;
		String personCard = "";
		List<String> cards = new ArrayList<String>();
		for (int i = 0; i < counts; i++) {
			String card = result.substring(temp,temp+16);
			personCard = String.valueOf(Long.parseLong(card,16));
			temp += 16;
			cards.add("0000"+personCard);
		}
		return cards;
	}
	
	
	/**
	* @Description：从市民卡传递的文件中解析黑名单并保存市民卡黑名单信息到数据库
	* @Author：hp
	* @Date：2013-5-5
	* @param filePath
	* @return
	**/
	public String saveBlackCard(String filePath){
		if(StringUtils.isNotBlank(filePath)){
			log.info("输入的文件路径为空!");
			return null;
		}
		String flag = "false";
		try {
			PersonNotRightCard blackCard;
			blackCard = readCardHeader(filePath);
			String counts = blackCard.getDataCount();
			List<String> cards = null;
			if(!StringUtils.isNotBlank(counts)){
				//从给的.flg文件中得到所有的卡号
				cards = getNotRightPersonCard(filePath,Integer.parseInt(counts));
				//保存卡号到数据库中
				for (String card : cards) {
					ExtInBlacklist personBlack = new ExtInBlacklist();
					personBlack.setInsertDate(new Date());
					personBlack.setBlDate(blackCard.getDataTime());
					personBlack.setBlVersion(blackCard.getVersion());
					personBlack.setBlCardId(card);
					dao.saveOrUpdate(personBlack);
					flag = "true";
				}
			}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} 
		log.info("成功保存市民卡黑名单!");
		return flag;
	}
	

	public PersonCardQueryToSystemService getPersonCardQueryToSystemService() {
		return personCardQueryToSystemService;
	}

	public void setPersonCardQueryToSystemService(
			PersonCardQueryToSystemService personCardQueryToSystemService) {
		this.personCardQueryToSystemService = personCardQueryToSystemService;
	}



}
