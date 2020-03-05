package hfad.com.seminar_message;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String SENT = "SMS_SENT";
    private String DELIVERED = "SMS_DELIVERED";
    private String FAILURE = "GENERIC_ERROR";
    private String NO_SERVICE = "NO_SERVICE";
    private String NULL_PDU = "NULL_PDU";
    private String RADIO_OFF = "RADIO_OFF";
    private String NOT_DELIVERED = "SMS_NOT_DELIVERED";
    private PendingIntent sentPI;
    private PendingIntent deliveredPI;
    private BroadcastReceiver smsSentReceiver;
    private BroadcastReceiver smsDeliveredReceiver;
    private IntentFilter filter;
    private String SMS_RECEIVED = "SMS_RECEIVED_ACTION";
    private boolean registerIntentReceiver;
    private boolean registerSendReceiver;
    private boolean registerDeliveredReceiver;
    private TextView txtMessge;
    private EditText edtPhoneNumber;
    private EditText edtMessage;
    public static int status;

    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            txtMessge = (TextView) findViewById(R.id.txtSMSMessage);
            txtMessge.setText(intent.getExtras().getString("sms").toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtMessage = findViewById(R.id.txtMessage);
        edtPhoneNumber = findViewById(R.id.txtPhoneNum);
        txtMessge = (TextView) findViewById(R.id.txtSMSMessage);
        status = 2;
        if (status == 2) {
            //sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
            //deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
            filter = new IntentFilter();
            filter.addAction(SMS_RECEIVED);

            registerReceiver(intentReceiver, filter);
            registerIntentReceiver = true;
        }
    }

    public void clickToSendSMS(View view) {
        status = 0;
        String phoneNo = edtPhoneNumber.getText().toString();
        String content = edtPhoneNumber.getText().toString();
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, content, null, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (status == 2) {
//            Log.d("onResume", "onResume");
//            BroadcastReceiver smsReceiver = new BroadcastReceiver() {
//                @Override
//                public void onReceive(Context context, Intent intent) {
//                    switch (getResultCode()) {
//                        case Activity.RESULT_OK:
//                            Toast.makeText(getBaseContext(), SENT, Toast.LENGTH_LONG).show();
//                            Log.d("sent", SENT);
//                            break;
//                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                            Toast.makeText(getBaseContext(), FAILURE, Toast.LENGTH_LONG).show();
//                            Log.d("sent", FAILURE);
//                            break;
//                        case SmsManager.RESULT_ERROR_NO_SERVICE:
//                            Toast.makeText(getBaseContext(), FAILURE, Toast.LENGTH_LONG).show();
//                            Log.d("sent", FAILURE);
//                            break;
//                        case SmsManager.RESULT_ERROR_NULL_PDU:
//                            Toast.makeText(getBaseContext(), NULL_PDU, Toast.LENGTH_LONG).show();
//                            Log.d("sent", NULL_PDU);
//                            break;
//                    }
//                }
//            };
//        }
    }

    public void clickToSendSMSIntent(View view) {
        status = 0;
        Uri uri = Uri.parse("smsto:" + edtPhoneNumber.getText().toString());
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", edtMessage.getText().toString());
        startActivity(intent);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.putExtra("address", edtPhoneNumber.getText().toString());
//        intent.putExtra("sms_body", edtMessage.getText().toString());
//        intent.setType("vnd.android-dir/mms-sms");
//        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if (status != 0) {
//            Log.d("onPause", "onPause");
//            if (intentReceiver != null && registerIntentReceiver) {
//                unregisterReceiver(intentReceiver);
//                registerIntentReceiver = true;
//            }
//            if (smsSentReceiver != null && registerSendReceiver) {
//                unregisterReceiver(smsSentReceiver);
//                registerSendReceiver = false;
//            }
//            if (smsDeliveredReceiver != null && registerDeliveredReceiver) {
//                unregisterReceiver(smsDeliveredReceiver);
//                registerDeliveredReceiver = false;
//            }
//
//            status = 0;
//        }
    }

    public void clickToSendSMSFB(View view) {
        status = 1;
        SmsManager sms = SmsManager.getDefault();

        sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);

        filter = new IntentFilter();
        filter.addAction(SMS_RECEIVED);

        registerReceiver(intentReceiver, filter);
        registerIntentReceiver = true;

        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "smsSentReceiver: " + SENT, Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "smsSentReceiver: " + FAILURE, Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "smsSentReceiver: " + FAILURE, Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "smsSentReceiver: " + NULL_PDU, Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "smsSentReceiver: " + RADIO_OFF, Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };
        smsDeliveredReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "smsDeliveredReceiver: " + DELIVERED, Toast.LENGTH_LONG).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "smsDeliveredReceiver: " + NOT_DELIVERED, Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };
        registerReceiver(smsSentReceiver, new IntentFilter(SENT));
        registerReceiver(smsDeliveredReceiver, new IntentFilter(SENT));
        sms.sendTextMessage(edtPhoneNumber.getText().toString(), null,
                edtMessage.getText().toString(), sentPI, deliveredPI);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (intentReceiver != null && registerIntentReceiver) {
            unregisterReceiver(intentReceiver);
            registerIntentReceiver = false;
        }
        if (smsSentReceiver != null && registerSendReceiver) {
            unregisterReceiver(smsSentReceiver);
            registerSendReceiver = false;
        }
        if (smsDeliveredReceiver != null && registerDeliveredReceiver) {
            unregisterReceiver(smsDeliveredReceiver);
            registerDeliveredReceiver = false;
        }
    }

    private void sendEmail(String[] adress, String[] cc, String subject, String msg) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, adress);
        emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, msg);
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Email"));
    }

    public void clickToSendEmail(View view) {
        String[] to = {"danhltnse130015@fpt.edu.vn"};
        String[] cc = {"danhltnse130015@fpt.edu.vn"};
        sendEmail(to, cc, "Email from Android", "Test of Email Msg\n DanhLTN");
    }
}
