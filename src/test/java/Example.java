import model.DBJsonDetails;
import util.DBHelper;

import static common.Constants.*;

public class Example {
    public static void main(String[] args) {
        DBHelper dbHelper = new DBHelper(DB_URL, DB_USERNAME, DB_PASSWORD);
        System.out.println(dbHelper.getUserDetails("chathumal"));
        System.out.println(dbHelper.getJsonDetails("Lily Bush"));
    }
}
