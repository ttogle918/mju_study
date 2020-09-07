package control;

import model.UserInfo;

public class AuthenticationManager {
	public enum EAutheticationState {
		idNotExist, invalidPassword, authenticatedseller, authenticatedbuyer, idExist, signUpComplete
	};

	private Member member;

	public AuthenticationManager() {
		this.member = new Member();
	}

	public EAutheticationState authenticate(UserInfo userInfo) throws Exception {
		if (!this.member.assertUserID(userInfo.getId())) {
			return EAutheticationState.idNotExist;
		}
		String password = this.member.getPassword();
		if (!password.equals(userInfo.getPassword())) {
			return EAutheticationState.invalidPassword;
		}
		if (!password.equals(userInfo.getPassword())) {
			return EAutheticationState.invalidPassword;
		}
		if (this.member.getUserType().equals("ÆÇ¸ÅÀÚ")) {
			return EAutheticationState.authenticatedseller;
		} else {
			return EAutheticationState.authenticatedbuyer;
		}
	}

	public String getAddress() {
		return this.member.getAddress();
	}

	public EAutheticationState signUp(UserInfo userInfo) throws Exception {
		if (this.member.assertUserID(userInfo.getId())) {
			return EAutheticationState.idExist;
		}
		this.member.SignUp(userInfo);
		return EAutheticationState.signUpComplete;
	}
}