package sample;

import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: FERREL JOHN FERNANDO
 * Date: 03/10/2019.
 * Time: 3:01.
 * To change this template use File | Settings | File Templates.
 */
public class Person implements Serializable {
    private String username;
    private String password;
    private String phone;
    private Integer id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
