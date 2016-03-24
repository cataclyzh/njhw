package com.cosmosource.app.dining.action;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.cosmosource.app.dining.service.FoodManagementManager;
import com.cosmosource.app.entity.FsDishes;
import com.cosmosource.app.utils.PictureUtils;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.ConvertUtils;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.EncodeUtil;
import com.cosmosource.base.util.PropertiesUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@SuppressWarnings("serial")
public class FoodManagementAction extends BaseAction<FsDishes> {

	private FsDishes fsDishes = new FsDishes();
	private Page<FsDishes> page = new Page<FsDishes>(Constants.PAGESIZE); //默认是10页
	private FoodManagementManager foodManagementManager;
	private String fdId;
	private String _chk[];//选中记录的ID数组
	private File file;
	private String fileFileName;
	private String fileFileContentType;
	
	@Override
	protected void prepareModel() throws Exception {
	}
	@Override
	public FsDishes getModel() {
		return fsDishes;
	}
	
	/**
	 * 
	* @title: foodManagementInput 
	* @description: 修改菜肴信息
	* @author sqs
	* @return
	* @date 2013-3-19 下午09:12:58     
	* @throws
	 */
	public String foodManagementInput() {

		try {
			Long fdId = fsDishes.getFdId();
			if (fdId != null) {
				fsDishes = (FsDishes) foodManagementManager.findById(
						FsDishes.class, fdId);
			} else {
				fsDishes = new FsDishes();
			}
			getRequest().setAttribute("fdPhoto1", fsDishes.getFdPhoto1());
			fsDishes.setFdPhoto1(PictureUtils.getBase64Pic(fsDishes
					.getFdPhoto1()));

			Map<String, String> fsClassMap = new LinkedHashMap<String, String>();
			fsClassMap = foodManagementManager.getFsClassMap();
			getRequest().setAttribute("fsClassMap", fsClassMap);

			getRequest().setAttribute("fsDishes", fsDishes);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}
	
	/**
	 * 
	* @title: dishesAdd 
	* @description: 增加菜肴信息
	* @author sqs
	* @return
	* @date 2013-3-19 下午09:12:58     
	* @throws
	 */
	public String dishesAdd(){
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: initTemporaryCard 
	* @description: 初始化录入页面
	* @author sqs
	* @return
	* @date 2013-3-19 下午09:12:39     
	* @throws
	 */
	public String initFoodManagement(){
		return INIT;
	}
	
	/**
	 * 
	* @title: saveDishes 
	* @description: 保存或修改菜肴信息
	* @author sqs
	* @return
	* @date 2013-3-19 下午09:12:58     
	* @throws
	 */
	public String saveDishes(){
		try {
			String fdame = getParameter("fdame");
			String fdbak1 = getParameter("fdBak1");
			String fdDesc = getParameter("fdDesc");
			String fdId = Struts2Util.getParameter("fdId");
			String fdPhoto1 = getParameter("fdPhoto1");
			if(fdId !=null && !fdId.equals("")){
				fsDishes = (FsDishes)foodManagementManager.findById(FsDishes.class, Long.parseLong(fdId));
			}else{
				fsDishes = new FsDishes();
			}
			fsDishes.setFdName(fdame);
			fsDishes.setFdBak1(fdbak1);
	    	fsDishes.setFdDesc(fdDesc);
	    	fsDishes.setFdPhoto1(fdPhoto1);
	    	fsDishes.setInsertDate(DateUtil.getSysDate());
			foodManagementManager.saveUpdateDishes(fsDishes);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @title: inputDishes 
	 * @description: 保存或修改菜肴信息
	 * @author sqs
	 * @return
	 * @date 2013-3-19 下午09:12:58     
	 * @throws
	 */
	public String inputDishes(){
		try {
			String fdId = getParameter("fdId");
			String fdbak1 = getParameter("fdBak1");
			String fdName = getParameter("fdName");
			String fdDesc = getParameter("fdDesc");
			String fdClass = getParameter("fdClass");
			String fdPhoto1 = getParameter("fdPhoto1");
			if(StringUtil.isNotBlank(fdId)){
				fsDishes = (FsDishes)foodManagementManager.findById(FsDishes.class, Long.parseLong(fdId));
			}else{
				fsDishes = new FsDishes();
			}
			fsDishes.setFdName(fdName);
			fsDishes.setFdBak1(fdbak1);
			fsDishes.setFdDesc(fdDesc);
			fsDishes.setFdClass(fdClass);
	    	fsDishes.setFdPhoto1(fdPhoto1);
			foodManagementManager.saveUpdateDishes(fsDishes);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: queryDishes 
	* @description: 查询按钮使用
	* @author sqs
	* @return
	* @date 2013-3-19 下午09:19:19     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public String queryDishes() throws Exception {
		try {
			Map localMap = ConvertUtils.pojoToMap(fsDishes);
			page = foodManagementManager.queryDishes(page, localMap);
			Map<String, String> fsClassMap = new LinkedHashMap<String, String>();
			fsClassMap  = foodManagementManager.getFsClassMap();
			getRequest().setAttribute("fsClassMap", fsClassMap);
			List fdList = page.getResult();
			if (fdList != null && fdList.size()>0) {
				for (int i=0; i < fdList.size(); i++) {
					Map fd = (Map) fdList.get(i);
					if (fd.get("FD_CLASS")!= null && StringUtil.isNotBlank(fd.get("FD_CLASS").toString())) {
						fd.put("FD_CLASS", fsClassMap.get(fd.get("FD_CLASS")));
					}
				}
			}
			
			return SUCCESS;
		} catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 
	* @title: queryPublishDishes 
	* @description: 发布菜单页面
	* @author hj
	* @return
	* @date 2013-08-12
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public String queryPublishDishes() throws Exception {
		try {
			Map<String, String> fsClassMap = new LinkedHashMap<String, String>();
			fsClassMap  = foodManagementManager.getFsClassMap();
			getRequest().setAttribute("fsClassMap", fsClassMap);
			page.setPageSize(16);
			String fdiFlag = getRequest().getParameter("fdiFlagFlag");
			String fdiType = getRequest().getParameter("fdiTypeType");
			this.getRequest().setAttribute("fdiFlagFlag", fdiFlag);
			this.getRequest().setAttribute("fdiTypeType", fdiType);
			
			String selectArr = foodManagementManager.getSelectDish(fdiType, fdiFlag);
			this.getRequest().setAttribute("selectArr", selectArr);
			return SUCCESS;
		} catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 
	* @title: queryPublishDishes 
	* @description: 发布菜单List页面
	* @author hj
	* @return
	* @date 2013-08-12
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public String ajaxRefreshMenu() throws Exception {
		Map<String, String> fsClassMap = new LinkedHashMap<String, String>();
		fsClassMap  = foodManagementManager.getFsClassMap();
		getRequest().setAttribute("fsClassMap", fsClassMap);	
		
		Map localMap = ConvertUtils.pojoToMap(fsDishes);
		page.setPageSize(16);
		String p1 = this.getRequest().getParameter("pageNo");
		page.setPageNo(Integer.valueOf(p1));
		localMap.put("selectArr", this.getRequest().getParameter("selectArr"));
		page = foodManagementManager.queryDishes(page, localMap);
		List fdList = page.getResult();
		String noFoodPic = getRequest().getContextPath()+"/app/integrateservice/images/food_no.jpg";

		if (fdList != null && fdList.size()>0) {
			for (int i=0; i < fdList.size(); i++) {
				Map fd = (Map) fdList.get(i);
				if(null != fd.get("FD_PHOTO1") && !StringUtil.isEmpty(showPic(fd.get("FD_PHOTO1").toString()))){
					fd.put("FD_PHOTO1", showPic(fd.get("FD_PHOTO1").toString()));
				}else{
					fd.put("FD_PHOTO1", noFoodPic);
				}
				
				if (fd.get("FD_CLASS")!= null && StringUtil.isNotBlank(fd.get("FD_CLASS").toString())) {
					fd.put("FD_CLASS", fsClassMap.get(fd.get("FD_CLASS")));
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: showMenuPreview 
	* @description: 发布菜单预览页面
	* @author hj
	* @return
	* @date 2013-08-13
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public String showMenuPreview() throws Exception {
		String fdIds = this.getRequest().getParameter("fdIds");
		
		List result = foodManagementManager.queryPreviewDishes(fdIds);
		Map<String, String> fsClassMap = new LinkedHashMap<String, String>();
		fsClassMap  = foodManagementManager.getFsClassMap();
		
		String noFoodPic = getRequest().getContextPath()+"/app/integrateservice/images/food_no.jpg";

		if (result != null && result.size()>0) {
			for (int i=0; i < result.size(); i++) {
				Map fd = (Map) result.get(i);
				if(null != fd.get("FD_PHOTO1") && !StringUtil.isEmpty(showPic(fd.get("FD_PHOTO1").toString()))){
					fd.put("FD_PHOTO1", showPic(fd.get("FD_PHOTO1").toString()));
				}else{
					fd.put("FD_PHOTO1", noFoodPic);
				}
				
				if (fd.get("FD_CLASS")!= null && StringUtil.isNotBlank(fd.get("FD_CLASS").toString())) {
					fd.put("FD_CLASS", fsClassMap.get(fd.get("FD_CLASS")));
				}
			}
		}
		
		getRequest().setAttribute("result", result);	
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: deleteDishes 
	* @description: 批量删除选中的菜肴
	* @author sqs
	* @return
	* @throws Exception
	* @date 2013-3-19 下午06:19:21     
	* @throws
	 */
	public String deleteDishes() throws Exception {
		try {
			foodManagementManager.deleteDishes(_chk);
			addActionMessage("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("菜已发布，不能删除！");
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: saveImg 
	* @description: 上传菜肴的照片
	* @author sqs
	* @return
	* @throws Exception
	* @date 2013-3-19 下午06:19:21     
	* @throws
	 */
	public String saveImg() throws IOException, JSONException {
		JSONObject json = new JSONObject();
		FileOutputStream fos = null;
		FileInputStream fis = null;
		String fdName = Struts2Util.getParameter("fdName");
		String type = Struts2Util.getParameter("type");
		fdName = new String (fdName.getBytes("iso8859-1"),"utf-8");
		String fdId = Struts2Util.getParameter("fdId");
		
		Date date =new Date();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=sdf.format(date);
		String times = time.replaceAll(":","-").trim();
		try {
			String name = times+this.getFileFileName();
			File uepPhoto = this.getFile();
			fis = new FileInputStream(uepPhoto);	
			String rootpath=PropertiesUtil.getAnyConfigProperty("dir.pic.dish",PropertiesUtil.NJHW_CONFIG);
			File rootFile = new File(rootpath);
			  if(!rootFile.exists()){
		         rootFile.mkdirs();
				 }
			fos = new FileOutputStream(rootpath + "/" + name);
			
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
			
			/*上传成功的时候，保存或修改照片url*/
				
			    if(fdName!=null&&!fdName.equals("")){
					if(fdId !=null && !fdId.equals("")){
						fsDishes = (FsDishes)foodManagementManager.findById(FsDishes.class, Long.parseLong(fdId));
					}else{
						fsDishes = new FsDishes();
					}
			    	fsDishes.setFdName(fdName);
			    	if( "1".equals(type)){
			    		fsDishes.setFdPhoto1(rootpath + "/" + name);
			    	}
			    	if("2".equals(type)){
			    		fsDishes.setFdPhoto2(rootpath + "/" + name);
			    	}
			    	if("3".equals(type)){
			    		fsDishes.setFdPhoto3(rootpath + "/" + name);
			    	}
			    	foodManagementManager.saveUpdateDishes(fsDishes);
			    	json.put("fdId", fsDishes.getFdId());//返回	id
			    	json.put("fdame", fsDishes.getFdName());//返回	name
			    	json.put("truee", "上传成功！");
			    }else{
			    	json.put("error","上传失败！");
			    }
		
	    	  String imgSrc1= showPic(rootpath + "/" + name);
			  json.put("imgSrc", imgSrc1);	
			  String imgSrc2= showPic(rootpath + "/" + name);
			  json.put("imgSrc", imgSrc2);	
			  String imgSrc3= showPic(rootpath + "/" + name);
			  json.put("imgSrc", imgSrc3);	
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(fos, fis);
		}
		Struts2Util.render("text/html", json.toString(), "no-cache:true");
		
		return null;
	}
	
	/**
	 * 
	 * @title: updateImg 
	 * @description: 修改菜肴的照片
	 * @author sqs
	 * @return
	 * @throws Exception
	 * @date 2013-3-19 下午06:19:21     
	 * @throws
	 */
	public String updateImg() throws IOException, JSONException {
		JSONObject json = new JSONObject();
		
		try {
			String name = UUID.randomUUID().toString() + ".jpg";
			File uepPhoto = this.getFile();
			String rootpath=PropertiesUtil.getAnyConfigProperty("dir.pic.dish",PropertiesUtil.NJHW_CONFIG);
			File rootFile = new File(rootpath);
			if(!rootFile.exists()){
				rootFile.mkdirs();
			}
			reduceImg(uepPhoto, rootpath + name, 118, 78);
			
			/*上传成功的时候，保存或修改照片url*/
			
			json.put("fdPhoto1",rootpath + name);
			String imgSrc= showPic(rootpath + name);
			json.put("imgSrc", imgSrc);	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		Struts2Util.render("text/html", json.toString(), "no-cache:true");
		
		return null;
	}
	
	/**
	 * 图像缩放 jpg格式
	 * 
	 * @param imgsrc
	 *            :原图片文件路径
	 * @param imgdist
	 *            :生成的缩略图片文件路径
	 * @param widthdist
	 *            :生成图片的比例宽度
	 * @param heightdist
	 *            :生成图片的高度
	 */
	public void reduceImg(File srcfile, String imgdist, int widthdist,
			int heightdist) {
		try {
			Image src = ImageIO.read(srcfile);
			BufferedImage tag = new BufferedImage((int) widthdist,
					(int) heightdist, BufferedImage.TYPE_INT_RGB);

			/*
			 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
			 */
			tag.getGraphics().drawImage(
					src.getScaledInstance(widthdist, heightdist,
							Image.SCALE_SMOOTH), 0, 0, null);

			FileOutputStream out = new FileOutputStream(imgdist);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(tag);
			out.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 读取照片
	* @title: showPic 
	* @description: TODO
	* @author sqs
	* @param userPhoto
	* @return
	* @date 2013-4-17 上午11:07:05     
	* @throws
	 */
	private String showPic(String userPhoto){
		String contents= "" ;
		try {
			File f=new File(userPhoto);
			if(f.exists()) {
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(userPhoto));
		        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		        byte[] temp = new byte[1024*1024];
		        int size = 0;         
		        while ((size = in.read(temp)) != -1) {
		            out.write(temp, 0, size);         
		        }
		        in.close();
		        byte[] content = out.toByteArray();
		        contents = EncodeUtil.base64Encode(content);
		        contents = "data:image/x-icon;base64,"+contents;
			} else {
				System.out.println("文件不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contents;
	}
	private void close(FileOutputStream fos, FileInputStream fis) {
		if (fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				System.out.println("FileInputStream关闭失败");
				e.printStackTrace();
			}
		}
		if (fos != null) {
			try {
				fos.close();
			} catch (IOException e) {
				System.out.println("FileOutputStream关闭失败");
				e.printStackTrace();
			}
		}
	
	}
	
	public String[] get_chk() {
		return _chk;
	}
	public void set_chk(String[] chk) {
		_chk = chk;
	}
	public FsDishes getFsDishes() {
		return fsDishes;
	}
	public void setFsDishes(FsDishes fsDishes) {
		this.fsDishes = fsDishes;
	}
	public FoodManagementManager getFoodManagementManager() {
		return foodManagementManager;
	}
	public void setFoodManagementManager(FoodManagementManager foodManagementManager) {
		this.foodManagementManager = foodManagementManager;
	}
	public String getFdId() {
		return fdId;
	}
	public void setFdId(String fdId) {
		this.fdId = fdId;
	}
	public Page<FsDishes> getPage() {
		return page;
	}
	public void setPage(Page<FsDishes> page) {
		this.page = page;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileFileContentType() {
		return fileFileContentType;
	}
	public void setFileFileContentType(String fileFileContentType) {
		this.fileFileContentType = fileFileContentType;
	}
	
}
