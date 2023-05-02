package com.example.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Blob;


@Data
@TableName("contactus")
public class Contactus extends Model<Contactus> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    private Blob mainimage;

    private  String messagename;

    private String messagenumber;

    private String messageemail;

    private String messagewrite;

    public Contactus() {
    }


    public Contactus(Long id, Blob mainimage, String messagename, String messagenumber, String messageemail, String messagewrite) {
        this.id = id;
        this.mainimage = mainimage;
        this.messagename = messagename;
        this.messagenumber = messagenumber;
        this.messageemail = messageemail;
        this.messagewrite = messagewrite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Blob getMainimage() {
        return mainimage;
    }

    public void setMainimage(Blob mainimage) {
        this.mainimage = mainimage;
    }

    public String getMessagename() {
        return messagename;
    }

    public void setMessagename(String messagename) {
        this.messagename = messagename;
    }

    public String getMessagenumber() {
        return messagenumber;
    }

    public void setMessagenumber(String messagenumber) {
        this.messagenumber = messagenumber;
    }

    public String getMessageemail() {
        return messageemail;
    }

    public void setMessageemail(String messageemail) {
        this.messageemail = messageemail;
    }

    public String getMessagewrite() {
        return messagewrite;
    }

    public void setMessagewrite(String messagewrite) {
        this.messagewrite = messagewrite;
    }

    @Override
    public String toString() {
        return "Contactus{" +
                "id=" + id +
                ", mainimage=" + mainimage +
                ", messagename='" + messagename + '\'' +
                ", messagenumber='" + messagenumber + '\'' +
                ", messageemail='" + messageemail + '\'' +
                ", messagewrite='" + messagewrite + '\'' +
                '}';
    }

    public static Object getName(Contactus contactus) {
        return null;
    }
}