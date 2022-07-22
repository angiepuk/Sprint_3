import org.apache.commons.lang3.RandomStringUtils;

public class Courier {
    private String login;
    private String password;
    private String firstName;


    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static Courier getRandomCourier(){
        String login = RandomStringUtils.randomAlphabetic(6);
        String password = RandomStringUtils.randomAlphabetic(5);
        String firstName = RandomStringUtils.randomAlphabetic(7);
        return new Courier(login, password, firstName);
    }

    public static Courier getRandomCourierWithoutFields(){
        return new Courier(null, null, null);
    }

    public static Courier getRandomCourierWithoutLogin(){
        String password = RandomStringUtils.randomAlphabetic(5);
        String firstName = RandomStringUtils.randomAlphabetic(7);
        return new Courier(null, password, firstName);
    }

    public static Courier getRandomCourierWithSimilaryLogin(){
        String login = RandomStringUtils.randomAlphabetic(6);
        String password = RandomStringUtils.randomAlphabetic(5);
       return new Courier(login, password, null);
    }
    public Courier(){

}
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
