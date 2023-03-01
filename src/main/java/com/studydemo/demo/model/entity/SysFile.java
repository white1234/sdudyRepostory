package com.studydemo.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/23 18:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysFile {
    private String id;
    private String url;
    private String fileName;
    private Date insertTime;
    private Date updateTime;
}
