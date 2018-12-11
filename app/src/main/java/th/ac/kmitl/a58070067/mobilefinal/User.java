package th.ac.kmitl.a58070067.mobilefinal;

public class User {
    private String user_id;
    private String name;
    private int age;
    private String password;

    public User(String id, String name, int age, String password) {
        this.user_id = id;
        this.name = name;
        this.age = age;
        this.password = password;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
