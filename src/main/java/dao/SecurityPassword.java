package dao;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component

public class SecurityPassword {

    public SecurityPassword() {
        // Constructeur sans argument
    }

    public String CryptPwd(String rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
        return pwdEncoder.encode(rawPassword);
    }

    public boolean verifyPassword(String plainPassword, String hashedPassword) 
    {
        BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
        boolean b=pwdEncoder.matches(plainPassword, hashedPassword);
         return b;
    }

}