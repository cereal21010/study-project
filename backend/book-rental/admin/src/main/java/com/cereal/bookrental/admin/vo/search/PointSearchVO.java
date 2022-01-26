package com.cereal.bookrental.admin.vo.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointSearchVO {
    private long rentalSeq;
    private long customerSeq;
    private String state;
    private String customerId;
}
