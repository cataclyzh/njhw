package com.cosmosource.common.action;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.common.entity.TAcCaapply;
import com.cosmosource.common.entity.TAcCauserapply;
import com.cosmosource.common.model.CaapplyAuditQueryModel;
import com.cosmosource.common.service.CAMgrManager;

/**
 * @类描述: CA资料审核类
 * 
 * @作者： fengfj
 */
public class TAcCaapplyAuditAction extends BaseAction<TAcCaapply> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4845186406990529173L;

	private TAcCaapply entity = new TAcCaapply();
	private Page<TAcCaapply> page = new Page<TAcCaapply>(Constants.PAGESIZE);
	private TAcCauserapply entityCauser = new TAcCauserapply();
	private Page<TAcCauserapply> pageCauser = new Page<TAcCauserapply>(20);
	private CaapplyAuditQueryModel caapplyQueryModel = new CaapplyAuditQueryModel();
	private CAMgrManager camgrManager;

	/**
	 * 进入ca资料审核页面
	 * 
	 * @return
	 * @throws Exception
	 *             ffj 2011-11-23
	 */
	public String init() throws Exception {
		caapplyQueryModel.setStartTime(DateUtil.getStrMonthFirstDay());
		caapplyQueryModel.setEndTime(DateUtil.getDateTime("yyyy-MM-dd"));
		return INIT;
	}

	/**
	 * ca资料审核查询列表（分页）
	 * 
	 * @return
	 * @throws Exception
	 *             ffj 2011-11-24
	 */
	public String query() throws Exception {
		try {
			page = camgrManager.findCaapplyList(page, entity,
					caapplyQueryModel, null);
		} catch (Exception e) {
			logger.error("ca资料审核查询", e);
			return ERROR;
		}
		return LIST;
	}

	/**
	 * 进入ca资料详细页面
	 * 
	 * @return
	 * @throws Exception
	 *             ffj 2011-11-24
	 */
	public String detail() throws Exception {
		return DETAIL;
	}

	/**
	 * CA资料审核提交
	 * 
	 * @return
	 * @throws Exception
	 *             ffj 2011-11-24
	 */
	public String update() throws Exception {
		try {
			// 判断要审核的CA资料是否存在
			TAcCaapply newEntity = camgrManager.findById(entity.getCaid());
			System.out
					.println("newEntity>>删除标志>>>>" + newEntity.getDelstatus());
			System.out.println("newEntity>>审核状态>>>>"
					+ newEntity.getAuditstatus());
			if (newEntity != null && "0".equals(newEntity.getDelstatus())) {
				// 判断审核状态是否被修改
				if ("0".equals(newEntity.getAuditstatus())) {
					entity.setAudituser((String) getSession().getAttribute(
							Constants.LOGIN_NAME));
					entity.setAuditdate(DateUtil.getSysDate());
					entity.setAuditstatus(caapplyQueryModel.getAuditstatus());
					camgrManager.update(entity);
					setIsSuc("success");
				} else {
					logger.error("审核的CA资料已被审核，请重新选择！");
					setIsSuc("edit");
				}
			} else {
				logger.error("审核的CA资料已被删除，请重新选择！");
				setIsSuc("del");
			}
		} catch (Exception e) {
			logger.error("CA审核错误", e);
			return ERROR;
		}
		return "success";
	}

	/**
	 * 进入CA审核页面
	 * 
	 * @return
	 * @throws Exception
	 *             ffj 2011-11-24
	 */
	public String edit() throws Exception {
		return EDIT;
	}

	/**
	 * 前台显示图片用
	 * @return
	 * @throws Exception
	 * ffj 2011-11-25
	 */
	public String getImgById() throws Exception {
		String caid = getParameter("caid");
		TAcCaapply entitys = camgrManager.findById(Long.parseLong(caid));
		Blob blob = entitys.getHandlerstamp();
		if (blob != null) {
			getResponse().setContentType("image/jpg");
			OutputStream outs = getResponse().getOutputStream();
			InputStream pi = blob.getBinaryStream();
			int blobsize = (int) blob.length();
			byte[] blobbytes = new byte[blobsize];
			int bytesRead = 0;
			while ((bytesRead = pi.read(blobbytes)) != -1) {
				outs.write(blobbytes, 0, bytesRead);
			}
			pi.close();
			outs.flush();
		}
		return null;
	}
	
	public String downloadImgById() throws Exception {
		String caid = getParameter("caid");
		TAcCaapply entitys = camgrManager.findById(Long.parseLong(caid));
		Blob blob = entitys.getHandlerstamp();
		if (blob != null) {
			this.getResponse().setContentType("image/jpeg;charset=gb2312");
			this.getResponse().addHeader("Content-Disposition","attachment;filename=image.jpg");
			OutputStream out = getResponse().getOutputStream();
			InputStream pi = blob.getBinaryStream();
			int blobsize = (int) blob.length();
			byte[] blobbytes = new byte[blobsize];
			int bytesRead = 0;
			while ((bytesRead = pi.read(blobbytes)) != -1) {
				out.write(blobbytes, 0, bytesRead);
			}
			pi.close();
			out.flush();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void prepareModel() throws Exception {
		if (entity.getCaid() != null) {
			entity = camgrManager.findById(entity.getCaid());
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("applyno", entity.getApplyno());
			pageCauser = (Page<TAcCauserapply>) camgrManager.findCaUserByNo(pageCauser, values);
		}
	}

	public TAcCaapply getModel() {
		return entity;
	}

	public Page<TAcCaapply> getPage() {
		return page;
	}

	public void setPage(Page<TAcCaapply> page) {
		this.page = page;
	}

	public CaapplyAuditQueryModel getCaapplyQueryModel() {
		return caapplyQueryModel;
	}

	public void setCaapplyQueryModel(CaapplyAuditQueryModel caapplyQueryModel) {
		this.caapplyQueryModel = caapplyQueryModel;
	}

	public CAMgrManager getCamgrManager() {
		return camgrManager;
	}

	public void setCamgrManager(CAMgrManager camgrManager) {
		this.camgrManager = camgrManager;
	}

	public Page<TAcCauserapply> getPageCauser() {
		return pageCauser;
	}

	public void setPageCauser(Page<TAcCauserapply> pageCauser) {
		this.pageCauser = pageCauser;
	}

}
