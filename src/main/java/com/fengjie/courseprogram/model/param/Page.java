package com.fengjie.courseprogram.model.param;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fengjie
 * @date 2019:05:03
 */
@Data
public class Page {

    public Page(){}

    public Page(Integer pageNum){
        this.pageNum = pageNum;
    }

    public Page(Integer pageNum,Integer pageSize){
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    private Integer pageNum;

    private Integer pageSize;

}
