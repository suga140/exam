package bean;

import java.io.Serializable;

public class Subject extends User implements Serializable {
	/**
	 * 教員ID:String
	 */
	private String cd;

	/**
	 * パスワード:String
	 */
	private String school;

	/**
	 * 教員名:String
	 */
	private String name;

	/**
	 * 所属校:School
	 */
	private Subject Subject;

	/**
	 * ゲッター・セッター
	 */
	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Subject getSubject() {
		return Subject;
	}

	public void setSubject(Subject Subject) {
		this.Subject = Subject;
	}
}


