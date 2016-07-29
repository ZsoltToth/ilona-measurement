package uni.miskolc.ips.ilona.tracking.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class contains the login information after a login request.<br>
 * This POJO can be used to hold the userid and password information after a login request.<br>
 * This class holds the actual login request information in a convenient form instead of a two strings( userid/password ) pair.<br>
 * <b>This class stores the password in raw format!</b><br>
 * @author Patrik / A5USL0
 *
 */
public final class UsernameAndPasswordLoginData {

	/**
	 * This field holds the userid of the current login request. <br>
	 * <b>This value can be null is the userid is not populated with the request!</b>
	 */
	private String userid;
	
	/**
	 * This field hold the password of the current login request.<br>
	 * This is the password what the user typed in the form in the login page.<br>
	 * <b>This class stores the password in raw format!</b>
	 */
	private String password;

	/**
	 * Parameterless constructor because of:<br>
	 * - serialization<br>
	 * - modellattribute parameterless initialization<br>
	 * - Spring IoC<br>
	 * - other elements if they need one
	 */
	public UsernameAndPasswordLoginData() {
		
	}
	
	/**
	 * Instantiate with two parameters.
	 * @param userid The userid parameter of the current login request.
	 * @param password The password parameter of the current login request.
	 */
	public UsernameAndPasswordLoginData(String userid, String password) {
		super();
		this.userid = userid;
		this.password = password;
	}

	/**
	 * This method gives back the stores userid.
	 * @return The stored userid.<br>
	 * <b>This value can be null is the userid is not populated with the request!</b>
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getUserid() {
		return userid;
	}

	/**
	 * Hashcode is generated by the userid.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		return result;
	}

	/**
	 * Two UsernameAndPasswordLoginData is equal if the userids are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsernameAndPasswordLoginData other = (UsernameAndPasswordLoginData) obj;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}

	/**
	 * This method gives back the string form of the current details.
	 */
	@Override
	public String toString() {
		return "UsernameAndPasswordLoginData [userid=" + userid + ", password=" + "[PROTECTED]" + "]";
	}

	/**
	 * Create a shallow copy(byte to byte copy) from the current object.<br>
	 * @return A copied version of the current object.<br>
	 * <b>If the copy is unsuccessful the return value will be null!</b>
	 */
	public UsernameAndPasswordLoginData shallowCopy() {
		try {
			return (UsernameAndPasswordLoginData) this.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	
	/**
	 * Create a deep copy from the current object. This will serialize and
	 * deserialize the current object to make a completely new version from
	 * it.<br>
	 * 
	 * @return A total new copy of the current object. (except static of
	 *         course)<br>
	 *         <b>If an error occurs, the return value will be null!</b>
	 */

	public UsernameAndPasswordLoginData deepCopy() {
		/*
		 * Stream initalizing!
		 */
		ByteArrayOutputStream bos = null;
		ObjectOutputStream out = null;
		ObjectInputStream ois = null;
		ByteArrayInputStream bis = null;
		UsernameAndPasswordLoginData logindata = null;

		try {
			/*
			 * Serialization part.
			 */
			bos = new ByteArrayOutputStream();
			out = new ObjectOutputStream(bos);
			out.writeObject(this);
			out.flush();
			// out.close();

			/*
			 * Deserialization part.
			 */
			bis = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bis);
			logindata = (UsernameAndPasswordLoginData) ois.readObject();

		} catch (Exception e) {
			logindata = null;
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception a) {

				}
			}

			if (out != null) {
				try {
					out.close();
				} catch (Exception a) {

				}
			}

			if (ois != null) {
				try {
					ois.close();
				} catch (Exception a) {

				}
			}

			if (bis != null) {
				try {
					bis.close();
				} catch (Exception a) {

				}
			}
		}

		return logindata;
	}
	
}