package com.cereal.bookrental.admin.vo.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerSearchVO {

    private String id;
    private String name;
    private int birthday;
    private int phoneNumber;
    private String sort;
    private String order = "desc";
    private int currentPage = 1;
    private int prePage = 10;

    public int getStartInPage() {
        return (this.currentPage - 1 < 0 ? 0 : this.currentPage - 1) * this.prePage;
    }

}
