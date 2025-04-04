package ma.ensao.backend_pfa.util;

import org.springframework.stereotype.Component;

@Component
public class GenerateCodeUtil {
	public String generateVerificationCode() {
	    return String.valueOf((int)(Math.random() * 900000) + 100000); 
	}
}
