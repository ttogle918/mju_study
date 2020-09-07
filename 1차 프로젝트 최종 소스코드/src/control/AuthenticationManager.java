package control;
import model.UserInfo;

public class AuthenticationManager {
	public enum EAutheticationState { idNotExist, invalidPassword, authenticated, idExist, signUpComplete};
	private Member member;
	
	public AuthenticationManager(){
		this.member = new Member();
	}

	public EAutheticationState authenticate(UserInfo userInfo) throws Exception{
		if (!this.member.assertUserID(userInfo.getId())) {
			return EAutheticationState.idNotExist;
		}
		String password = this.member.getPassword(userInfo.getId());
		if (!password.equals(userInfo.getPassword())){
			return EAutheticationState.invalidPassword;
		}
		return EAutheticationState.authenticated;
	}
	
	public EAutheticationState signUp(UserInfo userInfo) throws Exception{
		if (this.member.assertUserID(userInfo.getId())) {
			return EAutheticationState.idExist;
		}
		this.member.SignUp(userInfo);
		return EAutheticationState.signUpComplete;
	}
}