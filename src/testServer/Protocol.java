package testServer;

public class Protocol {
	
	public static final String REGISTER = "100"; // 회원가입(request)

    public static final String IDCHECK = "120"; // ID찾기 Join (request)
    
    public static final String LOGIN = "110"; // 로그인
    
    public static final String LOGOUT = "115"; // 로그인
    
    public static final String ENTERLOGIN_NO = "119"; // 로그인 실패
    
    public static final String ENTERLOGIN_OK = "111"; // 로그인 실패
    
    public static final String ENTERSIGNUP_OK = "101"; // 로그인 실패
}
