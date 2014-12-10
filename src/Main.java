import otp.Totp;
import otp.api.Clock;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by ben on 14/12/10.
 */
public class Main
{
    private static final String SECRET_KEY = "SECRETKEEEEEEEY";
    private static final int INIT_INTERVAL = 30;

    public static void main(String[] args)
    {
        //30秒換一次密碼，建構子沒有參數也套用30秒
        Clock clock = new Clock(INIT_INTERVAL);
        //Secret key在Vendor Server Side從DB查詢 在App則存在Sqlite
        Totp totp = new Totp(SECRET_KEY, clock);

        //對於現在的時間產生OTP密碼
        String otp = totp.getOTP();
        System.out.println("正確驗證碼" + otp);

        //同步時區並產生timestamp(含毫秒)
        Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
        long stampTime = calendar.getTimeInMillis();

        //時間完全一樣

        if (totp.verify(otp, stampTime)) {
            System.out.println("驗證成功");
        } else {
            System.out.println("驗證失敗");
        }


        //TOTP的性質
        //11秒後，只要不是剛好在每分鐘的0秒和30秒就會相同密碼，故此有可能True 有可能False
        long maybeRightOrWrongTime = stampTime + 11000;

        if (totp.verify(otp, maybeRightOrWrongTime)) {
            System.out.println("驗證成功");
        } else {
            System.out.println("驗證失敗");
        }

        //31秒後一定會更換密碼，此區一定會false
        long wrongTime = stampTime + 31000;

        if (totp.verify(otp, wrongTime)) {
            System.out.println("驗證成功");
        } else {
            System.out.println("驗證失敗");
        }


        //動態更改時間區間設定，每10秒更換一次密碼
        totp.getClock().setIntervalSecond(10);

        System.out.println("－－－－－－－－－－－－區間10秒－－－－－－－－－－－");
        //指定對於某一個時間產生OTP密碼
        String otp2 = totp.getOTP(stampTime);
        System.out.println("新區間產生新密碼" + otp2);

        //重新測試，前三次測試

        //時間區間改變，時間同步下，成功
        if (totp.verify(otp2, stampTime)) {
            System.out.println("驗證成功");
        } else {
            System.out.println("驗證失敗");
        }

        //現在時間區間為10秒，故11秒後密碼一定不同，必False

        if (totp.verify(otp2, maybeRightOrWrongTime)) {
            System.out.println("驗證成功");
        } else {
            System.out.println("驗證失敗");
        }

        //31秒後一定會更換密碼，此區一定會False

        if (totp.verify(otp2, wrongTime)) {
            System.out.println("驗證成功");
        } else {
            System.out.println("驗證失敗");
        }
    }
}
