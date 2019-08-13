package cn.learn.web.aop.learn.demo03;

import java.util.Date;
import java.util.Objects;

/**
 * ProjectName : web-learn
 * Description : []
 * @author : Fly365
 * CreateDate : 2019年-07月-31日
 */
public class UserDTO {

    private Integer id;
    private String name;
    private String address;
    private Date birthday;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    // jdk1.7+
    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) &&
                Objects.equals(name, userDTO.name) &&
                Objects.equals(address, userDTO.address) &&
                Objects.equals(birthday, userDTO.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, birthday);
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        if (id != null ? !id.equals(userDTO.id) : userDTO.id != null) return false;
        if (name != null ? !name.equals(userDTO.name) : userDTO.name != null) return false;
        if (address != null ? !address.equals(userDTO.address) : userDTO.address != null) return false;
        return birthday != null ? birthday.equals(userDTO.birthday) : userDTO.birthday == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        return result;
    }
}
