package com.example.service;

import com.example.entity.Contactus;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.ContactusMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ContactusService extends ServiceImpl<ContactusMapper, Contactus> {

    @Resource
    private ContactusMapper contactusMapper;

}
