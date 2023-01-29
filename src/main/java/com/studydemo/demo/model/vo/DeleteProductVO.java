package com.studydemo.demo.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/29 18:38
 */
@Data
@ApiModel("删除产品传入VO类")
public class DeleteProductVO implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 8073662434406951441L;

    @ApiModelProperty(value = "产品ID集合")
    private List<String> pids;

}
