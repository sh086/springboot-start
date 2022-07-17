package com.shooter.springboot.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class RestObjectResult<T> extends RestResult{

    /**
     * 正常返回时返回的数据
     * */
    @ApiModelProperty("返回的数据")
    private T data;

    public RestObjectResult(Integer status, String msg, T data) {
        super(status,msg);
        this.data = data;
    }

    /**
     * 返回成功数据
     * */
    public RestObjectResult<T> success(T data) {
        return new RestObjectResult<>(0, "操作成功", data);
    }

}