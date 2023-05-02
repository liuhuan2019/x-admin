package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Contactus;
import com.example.service.ContactusService;
import com.example.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.example.exception.CustomException;
import cn.hutool.core.util.StrUtil;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Blob;
import java.util.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/contactus")
public class ContactusController {
    @Resource
    private ContactusService contactusService;
    @Resource
    private HttpServletRequest request;

    public User getUser() {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException("-1", "请登录");
        }
        return user;
    }

    @PostMapping
    public Result<?> save(@RequestBody Contactus contactus) {
        return Result.success(contactusService.save(contactus));
    }

    @PutMapping
    public Result<?> update(@RequestBody Contactus contactus) {
        return Result.success(contactusService.updateById(contactus));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        contactusService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(contactusService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(contactusService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Contactus> query = Wrappers.<Contactus>lambdaQuery().orderByDesc(Contactus::getId);
        if (StrUtil.isNotBlank(name)) {
            query.like(Contactus::getName, name);
        }
        return Result.success(contactusService.page(new Page<>(pageNum, pageSize), query));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<Contactus> all = contactusService.list();
        for (Contactus obj : all) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("", obj.getId());
            row.put("", obj.getMainimage());
            row.put("", obj.getMessagename());
            row.put("", obj.getMessagenumber());
            row.put("", obj.getMessageemail());
            row.put("", obj.getMessagewrite());

            list.add(row);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("关于我们信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);
    }

    @GetMapping("/upload/{fileId}")
    public Result<?> upload(@PathVariable String fileId) {
        String basePath = System.getProperty("user.dir") + "/src/main/resources/static/file/";
        List<String> fileNames = FileUtil.listFileNames(basePath);
        String file = fileNames.stream().filter(name -> name.contains(fileId)).findAny().orElse("");
        List<List<Object>> lists = ExcelUtil.getReader(basePath + file).read(1);
        List<Contactus> saveList = new ArrayList<>();
        for (List<Object> row : lists) {
            Contactus obj = new Contactus();
            obj.setMainimage((Blob) row.get(1));
            obj.setMessagename((String) row.get(2));
            obj.setMessagenumber((String) row.get(3));
            obj.setMessageemail((String) row.get(4));
            obj.setMessagewrite((String) row.get(5));

            saveList.add(obj);
        }
        contactusService.saveBatch(saveList);
        return Result.success();
    }

}
