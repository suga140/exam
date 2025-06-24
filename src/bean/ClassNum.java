package bean;

public class ClassNum {
	private String class_num;
    private School school;

    // コンストラクタ
    public ClassNum(String class_num, School school) {
    	this.class_num = class_num;
    	this.school = school;
    }

    // ゲッターとセッター
    public String getClass_num() {
        return class_num;
    }

    public void setClassNum(String class_num) {
        this.class_num = class_num;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
