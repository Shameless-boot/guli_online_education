package com.shaun.commonutils.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Shaun
 * @Date 2022/2/9 16:14
 * @Description: 用于用户评论的实体类
 */

@Data
public class MemberVo {
    @ApiModelProperty("昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty("用户头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("手机号")
    @TableField("mobile")
    private String mobile;
}
