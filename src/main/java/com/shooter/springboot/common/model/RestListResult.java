package com.shooter.springboot.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RestListResult<T> extends RestResult{

    /**
     * 正常返回时返回的数据
     * */
    @ApiModelProperty("返回的数据")
    private List<T> data;

    public RestListResult(Integer status, String msg, List<T> data) {
        super(status,msg);
        this.data = data;
    }

    /**
     * 返回成功数据
     * */
    public RestListResult<T> success(List<T> data) {
        return new RestListResult<>(0, "操作成功", data);
    }
}