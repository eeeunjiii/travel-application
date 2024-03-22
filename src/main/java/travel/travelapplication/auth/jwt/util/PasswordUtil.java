package travel.travelapplication.auth.jwt.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class PasswordUtil {

    public static String createPassword() {
        int idx=0;
        char[] c=new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
                'W', 'X', 'Y', 'Z'
        };

        StringBuffer pwd=new StringBuffer();
        Random random=new Random();

        for(int i=0;i<12;i++) {
            double num= random.nextDouble();
            idx=(int)(c.length*num);

            pwd.append(c[idx]);
        }
        log.info("Password: {}", pwd);
        return pwd.toString();
    }
}
