package com.cosmosource.app.energyint.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.olap4j.Axis;
import org.olap4j.Cell;
import org.olap4j.CellSet;
import org.olap4j.CellSetAxis;
import org.olap4j.OlapException;
import org.olap4j.Position;
import org.olap4j.metadata.Member;

import com.eyeq.pivot4j.PivotModel;
import com.eyeq.pivot4j.impl.PivotModelImpl;

/**
 * MDX语句执行类
 *
 */
public class MdxExecutor {

	private PivotModel model;

	public PivotModel getModel() {
		return model;
	}

	public void setModel(PivotModel model) {
		this.model = model;
	}

	/**
	 * 执行MDX语句
	 * @param mdx
	 */
	private void executeMdx(String mdx) {
		if (StringUtils.isEmpty(mdx)) {
			if (model != null && model.isInitialized()) {
				model.destroy();
			}
		} else {

			if (model == null) {
				model = new PivotModelImpl(BIDataSourceManager
						.getBIDataSourceManager().getPooleddataSource());
			}

			if (model != null) {
				model.setMdx(mdx);

				if (!model.isInitialized()) {
					model.initialize();
				}
			}
		}
	}

	private void destory() {
		model.destroy();
	}

	private boolean isValid() {
		if (model == null || !model.isInitialized()) {
			return false;
		}

		List<CellSetAxis> axes = null;
		try {
			axes = model.getCellSet().getAxes();
		} catch (Exception e) {
			return false;
		}

		return axes.get(0).getPositionCount() > 0
				&& axes.get(1).getPositionCount() > 0;
	}

	private CellSet getData() {
		if (model == null || !model.isInitialized()) {
			return null;
		}

		CellSet cellSetResult = null;

		try {
			cellSetResult = model.getCellSet();
		} catch (Exception e) {
			return null;
		}

		return cellSetResult;
	}

	/**
	 * 获取结果（double型）
	 * @param mdxStr
	 * @return
	 */
	public double getResultDouble(String mdxStr) {
		double resultValue = 0.0;

		executeMdx(mdxStr);
		if (!isValid()) {
			destory();
			return 0.0;
		}

		CellSet result = getData();
		if (result == null) {
			destory();
			return 0.0;
		}
		List<CellSetAxis> cellSetAxes = result.getAxes();

		CellSetAxis columnsAxis = cellSetAxes.get(Axis.COLUMNS.ordinal());
		CellSetAxis rowsAxis = cellSetAxes.get(Axis.ROWS.axisOrdinal());
		int cellOrdinal = 0;
		for (Position rowPosition : rowsAxis.getPositions()) {

			for (Position columnPosition : columnsAxis.getPositions()) {
				// Access the cell via its ordinal. The ordinal is kept in step
				// because we increment the ordinal once for each row and
				// column.
				Cell cell = result.getCell(cellOrdinal);

				try {
					resultValue = cell.getDoubleValue();
				} catch (OlapException e) {
					resultValue = 0.0;
				}

				++cellOrdinal;
			}

		}

		destory();

		return resultValue;
	}

	/**
	 * 获取结果（HashMap型）
	 * @param mdxStr
	 * @return
	 */
	public TreeMap<Integer, Double> getResultHashMap(String mdxStr) {
		TreeMap<Integer, Double> resultHashMap = new TreeMap<Integer, Double>();

		executeMdx(mdxStr);
		if (!isValid()) {
			destory();
			return resultHashMap;
		}
		CellSet result = getData();
		if (result == null) {
			destory();
			return resultHashMap;

		}

		List<CellSetAxis> cellSetAxes = result.getAxes();

		CellSetAxis columnsAxis = cellSetAxes.get(0);

		// Print rows.
		CellSetAxis rowsAxis = cellSetAxes.get(1);
		int cellOrdinal = 0;
		String day = null;
		double value = 0.0;

		for (Position rowPosition : rowsAxis.getPositions()) {
			for (Member member : rowPosition.getMembers()) {
				day = member.getName();
			}

			// Print the value of the cell in each column.
			for (Position columnPosition : columnsAxis.getPositions()) {
				// Access the cell via its ordinal. The ordinal is kept in step
				// because we increment the ordinal once for each row and
				// column.
				Cell cell = result.getCell(cellOrdinal);

				++cellOrdinal;

				try {
					value = cell.getDoubleValue();
				} catch (OlapException e) {
					value = 0.0;
				}
			}
			resultHashMap.put(Integer.valueOf(day), value);
		}

		destory();

		return resultHashMap;
	}

}
