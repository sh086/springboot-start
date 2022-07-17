package com.shooter.springboot.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel("响应数据")
public class RestResult{

    /**
     * 标识代码，0表示成功，非0表示出错
     * */
    @ApiModelProperty("标识代码,0表示成功，非0表示出错")
    private Integer code;

    /**
     * 提示信息，通常供报错时使用
     * */
    @ApiModelProperty("提示信息,供报错时使用")
    private String msg;

    public RestResult(Integer status, String msg) {
        this.code = status;
        this.msg = msg;
    }

    /**
     * 返回成功数据
     * */
    public static RestResult success() {
        return new RestResult(0, "操作成功");
    }

    /**
     * 返回失败数据
     * */
    public static RestResult error(String errorMsg) {
        return new RestResult(0,  "操作失败"+errorMsg);
    }
}