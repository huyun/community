package com.aplikata.community.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PaginationDTO<T> {
	private List<T> data;
	private boolean showPrevious;
	private boolean showFirstPage;
	private boolean showNext;
	private boolean showEndPage;
	private Integer page;
	private Integer totalPage;
	private List<Integer> pages = new ArrayList<>();

	public void setPagination(Integer tolalCount, Integer page, Integer size) {
		if (page < 1) {
			page = 1;
		}

		totalPage = tolalCount % size == 0 ? tolalCount / size : (tolalCount / size + 1);

		if (page > totalPage) {
			page = totalPage;
		}
		this.page = page;
		
		// 是否展示上一页
		if (page == 1) {
			showPrevious = false;
		} else {
			showPrevious = true;
		}

		// 是否展示下一页
		if (page == totalPage) {
			showNext = false;
		} else {
			showNext = true;
		}

		pages.add(page);
		for (int i = 1; i <= 3; i++) {
			if (page - i > 0) {
				pages.add(0, page - i);
			}

			if (page + i <= totalPage) {
				pages.add(page + i);
			}
		}

		// 是否展示第一页
		if (pages.contains(1)) {
			showFirstPage = false;
		} else {
			showFirstPage = true;
		}

		// 是否展示最后一页
		if (pages.contains(totalPage)) {
			showEndPage = false;
		} else {
			showEndPage = true;
		}
	}
}
